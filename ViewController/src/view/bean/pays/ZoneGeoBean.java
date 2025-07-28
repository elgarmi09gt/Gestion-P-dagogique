package view.bean.pays;

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

public class ZoneGeoBean {
    private RichPanelCollection collectionZone;
    private RichPopup popZone;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection collectionPays;
    private RichPopup popPays;
    private RichPanelCollection collectionRegion;
    private RichPopup popRegion;
    private RichPanelCollection collectionVille;
    private RichPopup popVille;

    public ZoneGeoBean() {
    }

    public void setCollectionZone(RichPanelCollection collectionZone) {
        this.collectionZone = collectionZone;
    }

    public RichPanelCollection getCollectionZone() {
        return collectionZone;
    }
    
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void onZoneNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ZoneGeographiquesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertZone");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopZone();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopZone(RichPopup popZone) {
        this.popZone = popZone;
    }

    public RichPopup getPopZone() {
        return popZone;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopZone().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionZone());
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopZone();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ZoneGeographiquesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionZone());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteZone(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteZone");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionZone());
                return ;
            }
            
        }
    }

    public void setCollectionPays(RichPanelCollection collectionPays) {
        this.collectionPays = collectionPays;
    }

    public RichPanelCollection getCollectionPays() {
        return collectionPays;
    }

    public void onPaysNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PaysVO1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertPays");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopPays();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onDeletePays(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeletePays");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionPays());
                return ;
            }
            
        }
    }

    public void setPopPays(RichPopup popPays) {
        this.popPays = popPays;
    }

    public RichPopup getPopPays() {
        return popPays;
    }

    public void onDialogCancelPays(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopPays();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PaysVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionPays());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setCollectionRegion(RichPanelCollection collectionRegion) {
        this.collectionRegion = collectionRegion;
    }

    public RichPanelCollection getCollectionRegion() {
        return collectionRegion;
    }

    public void onRegionNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("RegionsVO1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertRegion");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopRegion();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopRegion(RichPopup popRegion) {
        this.popRegion = popRegion;
    }

    public RichPopup getPopRegion() {
        return popRegion;
    }

    public void onDialogCancelRegion(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopRegion();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("RegionsVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionRegion());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteRegion(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteRegion");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionRegion());
                return ;
            }
            
        }
    }

    public void onVilleNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("VillesVO1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertVille");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopVille();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setCollectionVille(RichPanelCollection collectionVille) {
        this.collectionVille = collectionVille;
    }

    public RichPanelCollection getCollectionVille() {
        return collectionVille;
    }

    public void setPopVille(RichPopup popVille) {
        this.popVille = popVille;
    }

    public RichPopup getPopVille() {
        return popVille;
    }

    public void onDialogCancelVille(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopVille();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("VillesVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionVille());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteVille(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteVille");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionVille());
                return ;
            }
            
        }
    }
}
