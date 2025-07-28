package view.beans.gradeformation;

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

public class GradeFormationBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichTable gradeFormationTable;
    private RichPopup popupNew;
    private RichPopup popupEdit;
    private RichPopup popupDelete;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichPopup popupFormationExist;

    public GradeFormationBean() {
    }

    public void setGradeFormationTable(RichTable gradeFormationTable) {
        this.gradeFormationTable = gradeFormationTable;
    }

    public RichTable getGradeFormationTable() {
        return gradeFormationTable;
    }

    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public void setPopupEdit(RichPopup popupEdit) {
        this.popupEdit = popupEdit;
    }

    public RichPopup getPopupEdit() {
        return popupEdit;
    }

    public void setPopupDelete(RichPopup popupDelete) {
        this.popupDelete = popupDelete;
    }

    public RichPopup getPopupDelete() {
        return popupDelete;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding gfIdBinding = (AttributeBinding) bindings.getControlBinding("IdGradesFormation");
            Integer gf_id = (Integer.parseInt(gfIdBinding.getInputValue().toString()));

            OperationBinding isFormationExist = bindings.getOperationBinding("IsFormationGradeExist");

            isFormationExist.getParamsMap().put("gf_id", gf_id);
            isFormationExist.execute();

            DCIteratorBinding iterFrGr = (DCIteratorBinding) bindings.get("IsFormationGradeExistIterator");
            Row crntgr = iterFrGr.getCurrentRow();
            if (crntgr == null) {
            OnDeleteClicked();
            }else{
                //popup formation exist
                RichPopup popup = this.getPopupFormationExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
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
            this.getPopupEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getGradeFormationTable());
        }
    }

    public void OnDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNew();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("GradesFormationIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getGradeFormationTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void OnNewClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("GradesFormationIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        } //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEdit();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public String OnDeleteClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getGradeFormationTable());
            return null;
        }
    }

    public void onNewGradeAction(DialogEvent dialogEvent) {
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
            this.getPopupNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getGradeFormationTable());
        }
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setPopupFormationExist(RichPopup popupFormationExist) {
        this.popupFormationExist = popupFormationExist;
    }

    public RichPopup getPopupFormationExist() {
        return popupFormationExist;
    }
}
