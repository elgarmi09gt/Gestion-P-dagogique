package personnes;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

public class PersonnesBean {
    private RichPopup popNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection tableCollect;
    private RichPopup popDel;
    private RichPopup popEdit;


    public PersonnesBean() {

    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
    public void setPopNew(RichPopup popNew) {
        this.popNew = popNew;
    }

    public RichPopup getPopNew() {
        return popNew;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        RichPopup popup = this.getPopNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollect());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            System.out.println("ok");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollect());
            
            //Execute pour refresh l'onglet Nouv. Bachelier
            OperationBinding op_execute_nouv_bac = bindings.getOperationBinding("ExecuteNouvBac");
            op_execute_nouv_bac.execute();
            
            //Execute pour refresh l'onglet Etudiant
            OperationBinding op_execute_etud = bindings.getOperationBinding("ExecuteEtud");
            op_execute_etud.execute();
        }
    }

    public void onDeletePersonne(DialogEvent dialogEvent) {
        // Add event code here...

        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationDelete = bindings.getOperationBinding("Delete");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollect());
                
                //Execute pour refresh l'onglet Nouv. Bachelier
                OperationBinding op_execute_nouv_bac = bindings.getOperationBinding("ExecuteNouvBac");
                op_execute_nouv_bac.execute();
                
                //Execute pour refresh l'onglet Etudiant
                OperationBinding op_execute_etud = bindings.getOperationBinding("ExecuteEtud");
                op_execute_etud.execute();
                
                return ;
            }
        }
    }

    public void onPersonneNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setTableCollect(RichPanelCollection tableCollect) {
        this.tableCollect = tableCollect;
    }

    public RichPanelCollection getTableCollect() {
        return tableCollect;
    }

    public void onDeleteCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDel().hide();
    }

    public void setPopDel(RichPopup popDel) {
        this.popDel = popDel;
    }

    public RichPopup getPopDel() {
        return popDel;
    }

    @SuppressWarnings("unchecked")
    public void onPersonneDel(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_pers = (DCIteratorBinding) bindings.get("LesPersonnesROIterator");
        Row current_pers = iter_pers.getCurrentRow();
        if(current_pers != null){
            OperationBinding op_get_pers = bindings.getOperationBinding("getPersonnesCourante");
            op_get_pers.getParamsMap().put("idpers_var",  Long.parseLong(current_pers.getAttribute("IdPersonne").toString()));
            op_get_pers.execute();
            RichPopup popup = this.getPopDel();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            FacesMessage msg = new FacesMessage(" Veuillez ajouter au moins une personne avant de supprimer ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @SuppressWarnings("unchecked")
    public void onPersonneEdit(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_pers = (DCIteratorBinding) bindings.get("LesPersonnesROIterator");
        Row current_pers = iter_pers.getCurrentRow();
        if(current_pers != null){
            OperationBinding op_get_pers = bindings.getOperationBinding("getPersonnesCourante");
            op_get_pers.getParamsMap().put("idpers_var",  Long.parseLong(current_pers.getAttribute("IdPersonne").toString()));
            op_get_pers.execute();
            RichPopup popup = this.getPopEdit();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            FacesMessage msg = new FacesMessage(" Veuillez ajouter au moins une personne avant de modifier ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onPersonneEditCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopEdit().hide();
    }

    public void setPopEdit(RichPopup popEdit) {
        this.popEdit = popEdit;
    }

    public RichPopup getPopEdit() {
        return popEdit;
    }
}
