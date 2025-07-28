package view.bean.typeverrou;

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

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.binding.AttributeBinding;
import java.util.Map;

public class TypeVerrouBean {
    private RichPanelCollection collectionTypeVerrou;
    private RichPopup popupNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__"; 
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionScope = adfCtx.getSessionScope();
    Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());
    private RichPopup popupEdit;


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public TypeVerrouBean() {
    }

    public void setCollectionTypeVerrou(RichPanelCollection collectionTypeVerrou) {
        this.collectionTypeVerrou = collectionTypeVerrou;
    }

    public RichPanelCollection getCollectionTypeVerrou() {
        return collectionTypeVerrou;
    }

    public void onDialogNew(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionTypeVerrou());
        }
    }

    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        RichPopup popup = this.getPopupNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TypeVerrouIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null)
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionTypeVerrou());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDelete(DialogEvent dialogEvent) {
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
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionTypeVerrou());
                return ;
            }
        }
    }

    public void onTypeVerrou(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TypeVerrouIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onDialogEdit(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        AttributeBinding utimod = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        if (outcome == Outcome.ok) {
            //commit
            utimod.setInputValue(getUtilisateur());
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionTypeVerrou());
        }
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setPopupEdit(RichPopup popupEdit) {
        this.popupEdit = popupEdit;
    }

    public RichPopup getPopupEdit() {
        return popupEdit;
    }
}
