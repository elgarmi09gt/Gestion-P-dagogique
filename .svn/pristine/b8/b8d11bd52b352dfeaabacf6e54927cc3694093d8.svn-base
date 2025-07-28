package view.beans.hstructure;

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

public class HistoriqueStructureBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichTable hstructureTable;
    private RichPopup popupNewHStruct;
    private RichPopup popupEditHStructure;
    private RichPopup popupDeleteHStructure;

    public HistoriqueStructureBean() {
    }

    public void setHstructureTable(RichTable hstructureTable) {
        this.hstructureTable = hstructureTable;
    }

    public RichTable getHstructureTable() {
        return hstructureTable;
    }

    public void setPopupNewHStruct(RichPopup popupNewHStruct) {
        this.popupNewHStruct = popupNewHStruct;
    }

    public RichPopup getPopupNewHStruct() {
        return popupNewHStruct;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNewHStructClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HistoriquesStructuresIterator");
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
        RichPopup popup = this.getPopupNewHStruct();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnHStructDialogAction(DialogEvent dialogEvent) {
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
            this.getPopupNewHStruct().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
        }
    }

    public void OnHStructDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewHStruct();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HistoriquesStructuresIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void setPopupEditHStructure(RichPopup popupEditHStructure) {
        this.popupEditHStructure = popupEditHStructure;
    }

    public RichPopup getPopupEditHStructure() {
        return popupEditHStructure;
    }

    public void OnEditHStructClick(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPopupDeleteHStructure(RichPopup popupDeleteHStructure) {
        this.popupDeleteHStructure = popupDeleteHStructure;
    }

    public RichPopup getPopupDeleteHStructure() {
        return popupDeleteHStructure;
    }

    public String OnDeleteHStructClicked() {
        // Add event code here...
        return null;
    }

    public void OnDeleteHStructAction(DialogEvent dialogEvent) {
        // Add event code here...
    }
}
