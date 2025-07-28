package view.bean.langues;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class LanguesBean {
    private RichPanelCollection collectionLangues;
    private RichPopup popupNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());
    private Integer annee = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    public LanguesBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setCollectionLangues(RichPanelCollection collectionLangues) {
        this.collectionLangues = collectionLangues;
    }

    public RichPanelCollection getCollectionLangues() {
        return collectionLangues;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            this.getPopupNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionLangues());
        }
    }

    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public void onLanguesDelete(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            OperationBinding operationDelete = getBindings().getOperationBinding("Delete");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = getBindings().getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionLangues());
                return ;
            }
            
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopupNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("LanguesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionLangues());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onLangues(ActionEvent actionEvent) {
        // Add event code here...       
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("LanguesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        AttributeBinding utilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        utilisateur.setInputValue(getUtilisateur()); // utilisateur connecté
        
        RichPopup popup = this.getPopupNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void onDialogEdit(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
            uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            this.getPopupNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionLangues());
        }
    }
}
