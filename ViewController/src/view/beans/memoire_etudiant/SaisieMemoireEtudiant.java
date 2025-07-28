package view.beans.memoire_etudiant;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class SaisieMemoireEtudiant {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichTable etdTable;
    private RichPanelCollection etdPanCol;
    private RichPanelGroupLayout etdPanGrp;
    private RichPopup popupNewMemoire;
    private RichPopup popupEditMemoire;
    private RichPopup popupNoStudent;

    public SaisieMemoireEtudiant() {
    }

    public void OnNewMemoireAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MemoireEtudiantsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateMemoire");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewMemoire(); //p2
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return;
    }

    public void OnEditMemoireAction(ActionEvent actionEvent) {
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditMemoire();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void GetEtudiants() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ParcoursAttSpecialeIterator");
        Row currentRow = dciter.getCurrentRow();
        System.out.println(currentRow.getAttribute("IdParcoursMaquetAnnee").toString());
        
        OperationBinding op_get = bindings.getOperationBinding("getEtudiant");
        op_get.getParamsMap().put("parcours_maq", currentRow.getAttribute("IdParcoursMaquetAnnee"));
        Object result = op_get.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdTable());
    }

    public void setEtdTable(RichTable etdTable) {
        this.etdTable = etdTable;
    }

    public RichTable getEtdTable() {
        return etdTable;
    }

    public void setEtdPanCol(RichPanelCollection etdPanCol) {
        this.etdPanCol = etdPanCol;
    }

    public RichPanelCollection getEtdPanCol() {
        return etdPanCol;
    }

    public void setEtdPanGrp(RichPanelGroupLayout etdPanGrp) {
        this.etdPanGrp = etdPanGrp;
    }

    public RichPanelGroupLayout getEtdPanGrp() {
        return etdPanGrp;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setPopupNewMemoire(RichPopup popupNewMemoire) {
        this.popupNewMemoire = popupNewMemoire;
    }

    public RichPopup getPopupNewMemoire() {
        return popupNewMemoire;
    }

    public void OnNewMemoireConfirmAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupNewMemoire().hide();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MemoireEtudiantsIterator");
            ViewObject vo = dciter.getViewObject();
            vo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanCol());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdTable());
        }
    }

    public void OnDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewMemoire().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MemoireEtudiantsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
        /*OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();*/
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopupEditMemoire(RichPopup popupEditMemoire) {
        this.popupEditMemoire = popupEditMemoire;
    }

    public RichPopup getPopupEditMemoire() {
        return popupEditMemoire;
    }

    public void OnEditMemoireAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupEditMemoire().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdPanCol());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdTable());
        }
    }

    public void onSearchEtudiantListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding idetd = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        AttributeBinding p_num = (AttributeBinding) getBindings().getControlBinding("p_numero");
        System.out.println("p_numero : "+p_num.getInputValue().toString());
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("EtudiantMemoireIterator");
        if(dciter.getEstimatedRowCount() == 0){
            idetd.setInputValue(null);
            RichPopup popup = this.getPopupNoStudent();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }else{
            Row curr = dciter.getCurrentRow();
            idetd.setInputValue(curr.getAttribute("IdEtudiant"));
        }
        System.out.println("nbre : "+dciter.getEstimatedRowCount());
    }

    public void setPopupNoStudent(RichPopup popupNoStudent) {
        this.popupNoStudent = popupNoStudent;
    }

    public RichPopup getPopupNoStudent() {
        return popupNoStudent;
    }
}
