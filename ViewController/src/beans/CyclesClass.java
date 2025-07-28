package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

public class CyclesClass {
    private RichPopup popupNew;
    private RichPanelCollection tableCollectCycle;
    private RichPopup popupEdit;
    private RichPopup popupDelete;

    public CyclesClass() {
    }

    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public void onDialogNew(DialogEvent dialogEvent) {
        // Add event code here...
        DialogEvent.Outcome outcome = dialogEvent.getOutcome();
        if (outcome == DialogEvent.Outcome.ok) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollectCycle());
    }
  }

    public void onDialogCancelNew(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopupNew();
        popup.hide();   
        DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlHierBinding object = (JUCtrlHierBinding)bind.findCtrlBinding("Cycles"); // or get ViewObject form Iterator in ADFUtils.
        ViewObject Vo = object.getViewObject(); 
        Vo.executeQuery();
        OperationBinding operationBinding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback");
        Object result = operationBinding.execute();        
        if(!operationBinding.getErrors().isEmpty()){
            return;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollectCycle());  
    }

    public void setTableCollectCycle(RichPanelCollection tableCollectCycle) {
        this.tableCollectCycle = tableCollectCycle;
    }

    public RichPanelCollection getTableCollectCycle() {
        return tableCollectCycle;
    }

    public String newCycles() {
        // Add event code here...
        RichPopup popup = this.getPopupNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public String editCycle() {
        // Add event code here...
        RichPopup popup = this.getPopupEdit();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public void onDialogEdit(DialogEvent dialogEvent) {
        // Add event code here...
        DialogEvent.Outcome outcome = dialogEvent.getOutcome();
        if (outcome == DialogEvent.Outcome.ok) {
            //commit
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollectCycle());
     }
  }

    public void setPopupEdit(RichPopup popupEdit) {
        this.popupEdit = popupEdit;
    }

    public RichPopup getPopupEdit() {
        return popupEdit;
    }

    public void onDialogCancelEdit(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopupEdit();
        popup.hide();   
        DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlHierBinding object = (JUCtrlHierBinding)bind.findCtrlBinding("Cycles"); // or get ViewObject form Iterator in ADFUtils.
        ViewObject Vo = object.getViewObject(); 
        Vo.executeQuery();
        OperationBinding operationBinding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback");
        Object result = operationBinding.execute();        
        if(!operationBinding.getErrors().isEmpty()){
            return;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollectCycle());  
    }

    public String deleteCycle() {
        // Add event code here...
        RichPopup popup = this.getPopupDelete();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public void setPopupDelete(RichPopup popupDelete) {
        this.popupDelete = popupDelete;
    }

    public RichPopup getPopupDelete() {
        return popupDelete;
    }

    public void onDialogDelete(DialogEvent dialogEvent) {
        // Add event code here...
        DialogEvent.Outcome outcome = dialogEvent.getOutcome();
        if (outcome == DialogEvent.Outcome.ok) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationDelete = bindings.getOperationBinding("Delete");
        Object result = operationDelete.execute();
        if (!operationDelete.getErrors().isEmpty()) {
            return;
        }
        OperationBinding operationCommit = bindings.getOperationBinding("Commit");
        Object resultCommit = operationCommit.execute();
        if (!operationCommit.getErrors().isEmpty()) {
            return;
        } 
            
      }
    }
    public void onDialogCancelDelete(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopupDelete();
        popup.hide();   
        DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlHierBinding object = (JUCtrlHierBinding)bind.findCtrlBinding("Cycles"); // or get ViewObject form Iterator in ADFUtils.
        ViewObject Vo = object.getViewObject(); 
        Vo.executeQuery();
        OperationBinding operationBinding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback");
        Object result = operationBinding.execute();        
        if(!operationBinding.getErrors().isEmpty()){
            return;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollectCycle());  
    }
}
