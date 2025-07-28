package grades;

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

public class GradesBean {
    private RichPanelCollection tableCollection;
    private RichPopup popNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";

    public GradesBean() {
    }

    public void setTableCollection(RichPanelCollection tableCollection) {
        this.tableCollection = tableCollection;
    }

    public RichPanelCollection getTableCollection() {
        return tableCollection;
    }

    public void setPopNew(RichPopup popNew) {
        this.popNew = popNew;
    }

    public RichPopup getPopNew() {
        return popNew;
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        RichPopup popup = this.getPopNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("GradesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onGradesNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("GradesIterator");
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

    public void onBradesDelete(DialogEvent dialogEvent) {
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
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
                return ;
            }
        }
    }
}
