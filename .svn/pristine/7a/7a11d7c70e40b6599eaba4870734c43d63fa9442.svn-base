package view.beans.status;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.binding.AttributeBinding;
import java.util.Map;

public class StatusBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichTable statusTable;
    private RichPopup popupNew;
    private RichPopup popupEdit;
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionScope = adfCtx.getSessionScope();
    Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());

    public StatusBean() {
    }

    public void setStatusTable(RichTable statusTable) {
        this.statusTable = statusTable;
    }

    public RichTable getStatusTable() {
        return statusTable;
    }

    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public void OnDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNew().hide();
            this.getPopupEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStatusTable());
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String OnNewClick() {
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree");
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("StatutsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return null;
    }

    public void OnDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNew();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("StatutsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null)
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStatusTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void OnEditClick(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimod = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimod.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEdit();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void setPopupEdit(RichPopup popupEdit) {
        this.popupEdit = popupEdit;
    }

    public RichPopup getPopupEdit() {
        return popupEdit;
    }

    public void OnDeleteDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            OnDeleteClick();
        }
    }

    public String OnDeleteClick() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStatusTable());
            return null;
        }
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }
}
