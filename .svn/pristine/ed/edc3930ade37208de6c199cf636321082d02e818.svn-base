package view.beans.typesection;

import java.util.Map;

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

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import view.beans.jsfutils.JSFUtils;

public class TypeSectionBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichTable typeSectionTable;
    private RichPopup popupNewTypeSection;
    private RichPopup popupEditTypeSection;
    private RichPopup popupDeleteTypeSection;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichPopup popupEtabExist;


    public TypeSectionBean() {
    }

    public void setTypeSectionTable(RichTable typeSectionTable) {
        this.typeSectionTable = typeSectionTable;
    }

    public RichTable getTypeSectionTable() {
        return typeSectionTable;
    }

    public void setPopupNewTypeSection(RichPopup popupNewTypeSection) {
        this.popupNewTypeSection = popupNewTypeSection;
    }

    public RichPopup getPopupNewTypeSection() {
        return popupNewTypeSection;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNewClick(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TypeSectionsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewTypeSection();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewTypeSection().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTypeSectionTable());
        }
    }

    public void OnDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewTypeSection();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TypeSectionsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTypeSectionTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void setPopupEditTypeSection(RichPopup popupEditTypeSection) {
        this.popupEditTypeSection = popupEditTypeSection;
    }

    public RichPopup getPopupEditTypeSection() {
        return popupEditTypeSection;
    }

    public void OnEditClick(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditTypeSection();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void setPopupDeleteTypeSection(RichPopup popupDeleteTypeSection) {
        this.popupDeleteTypeSection = popupDeleteTypeSection;
    }

    public RichPopup getPopupDeleteTypeSection() {
        return popupDeleteTypeSection;
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTypeSectionTable());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding tsIdBinding = (AttributeBinding) bindings.getControlBinding("IdTypeSection");
            Integer ts_id = (Integer.parseInt(tsIdBinding.getInputValue().toString()));
            OperationBinding isEtabExist = bindings.getOperationBinding("IsEtabTypeSectExist");
            isEtabExist.getParamsMap().put("ts_id", ts_id);
            isEtabExist.execute();
            DCIteratorBinding iterEtabTs = (DCIteratorBinding) bindings.get("IsEtabTypeSectExistIterator");
            Row crntts = iterEtabTs.getCurrentRow();
            if (crntts == null) {
                OnDeleteClick();
            }else{
                //popup formation exist
                RichPopup popup = this.getPopupEtabExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void onEditTypeSection(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditTypeSection().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTypeSectionTable());
        }
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setPopupEtabExist(RichPopup popupEtabExist) {
        this.popupEtabExist = popupEtabExist;
    }

    public RichPopup getPopupEtabExist() {
        return popupEtabExist;
    }
}
