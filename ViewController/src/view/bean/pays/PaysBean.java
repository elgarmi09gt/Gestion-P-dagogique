package view.bean.pays;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;
import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;

//import oracle.adf.view.rich.model.CollectionModel;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
//import org.apache.myfaces.trinidad.model.RowKeySe;
public class PaysBean {
    private RichTree table;
    private RichPopup popupPays;
    private RichPopup popRegions;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popupVille;
    private RichPopup popDelPays;

    public PaysBean() {
    }


    public void setTable(RichTree table) {
        this.table = table;
    }

    public RichTree getTable() {
        return table;
    }

    public void setPopupPays(RichPopup popupPays) {
        this.popupPays = popupPays;
    }

    public RichPopup getPopupPays() {
        return popupPays;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            this.getPopupPays().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTable());
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        RichPopup popup = this.getPopupPays();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PaysVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopRegions(RichPopup popRegions) {
        this.popRegions = popRegions;
    }

    public RichPopup getPopRegions() {
        return popRegions;
    }
    public BindingContainer getBindings(){   
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public String onNew() {
        RowKeySet selectedEmp = this.getTable().getSelectedRowKeys();
        Iterator selectEmpIter = selectedEmp.iterator();
        while(selectEmpIter.hasNext()){
            List listkeys=(List)selectEmpIter.next();
            Key key=(Key)listkeys.get(0);
            JUCtrlHierBinding treeBinding=null;
           treeBinding=(JUCtrlHierBinding)((CollectionModel)getTable().getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding=treeBinding.findNodeByKeyPath(listkeys);
            String noteStruct=nodeBinding.getHierTypeBinding().getStructureDefName();
            ADFContext adfctx=ADFContext.getCurrent();
            Map sesssionApp=adfctx.getPageFlowScope();
            if(noteStruct == "model.updatableview.PaysVO"){
                BindingContainer bindings = getBindings();
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PaysVO1Iterator");
                Row oldCcurrentRow = dciter.getCurrentRow();
                ADFContext adfCtx = ADFContext.getCurrent();
                if(oldCcurrentRow != null)
                    adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    return null;
                }
                RichPopup popup = this.getPopupPays();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                
                return null;
            }
                
            else 
                if(noteStruct=="model.updatableview.RegionsVO"){
                    BindingContainer bindings = this.getBindings();
                    DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("RegionsVO1Iterator");
                    Row oldCcurrentRow = dciter.getCurrentRow();
                    ADFContext adfCtx = ADFContext.getCurrent();
                    if(oldCcurrentRow != null)
                        adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                    OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertRegion");
                    Object result = operationBinding.execute();
                    if (!operationBinding.getErrors().isEmpty()) {
                        return null;
                    }
                    RichPopup popup = this.getPopRegions();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    
                    return null;
                }
                else
                    if(noteStruct=="model.updatableview.VillesVO"){
                        BindingContainer bindings = this.getBindings();
                        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("VillesVO1Iterator");
                        Row oldCcurrentRow = dciter.getCurrentRow();
                        ADFContext adfCtx = ADFContext.getCurrent();
                        if(oldCcurrentRow != null)
                            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertVille");
                        Object result = operationBinding.execute();
                        if (!operationBinding.getErrors().isEmpty()) {
                            return null;
                        }
                        RichPopup popup = this.getPopupVille();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                        
                        return null;
                    }
            
           
            
        }   
        
        return null;
    }

    public void setPopupVille(RichPopup popupVille) {
        this.popupVille = popupVille;
    }

    public RichPopup getPopupVille() {
        return popupVille;
    }

    public String onEditNode() {
        // Add event code here...
        RowKeySet selectedEmp = this.getTable().getSelectedRowKeys();
        Iterator selectEmpIter = selectedEmp.iterator();
        while(selectEmpIter.hasNext()){
            List listkeys=(List)selectEmpIter.next();
            Key key=(Key)listkeys.get(0);
            JUCtrlHierBinding treeBinding=null;
           treeBinding=(JUCtrlHierBinding)((CollectionModel)getTable().getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding=treeBinding.findNodeByKeyPath(listkeys);
            String noteStruct=nodeBinding.getHierTypeBinding().getStructureDefName();
            ADFContext adfctx=ADFContext.getCurrent();
            Map sesssionApp=adfctx.getPageFlowScope();
            if(noteStruct == "model.updatableview.PaysVO"){
                BindingContainer bindings = getBindings();
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PaysVO1Iterator");
                Row oldCcurrentRow = dciter.getCurrentRow();
                ADFContext adfCtx = ADFContext.getCurrent();
                if(oldCcurrentRow != null)
                    adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                /*OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertVille");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    return null;
                }*/
                RichPopup popup = this.getPopupPays();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);   
                return null;
            }
                
            else 
                if(noteStruct=="model.updatableview.RegionsVO"){
                    RichPopup popup = this.getPopRegions();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);                   
                    return null;
                }
                else
                    if(noteStruct=="model.updatableview.VillesVO"){
                        RichPopup popup = this.getPopupVille();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);                      
                        return null;
                    } 
        }    
        return null;
    }

    public String onDeleteNode() {
        // Add event code here...
        RowKeySet selectedEmp = this.getTable().getSelectedRowKeys();
        Iterator selectEmpIter = selectedEmp.iterator();
        while(selectEmpIter.hasNext()){
            List listkeys=(List)selectEmpIter.next();
            Key key=(Key)listkeys.get(0);
            JUCtrlHierBinding treeBinding=null;
           treeBinding=(JUCtrlHierBinding)((CollectionModel)getTable().getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding=treeBinding.findNodeByKeyPath(listkeys);
            String noteStruct=nodeBinding.getHierTypeBinding().getStructureDefName();
            ADFContext adfctx=ADFContext.getCurrent();
            Map sesssionApp=adfctx.getPageFlowScope();
            if(noteStruct == "model.updatableview.PaysVO"){
                RichPopup popup = this.getPopDelPays();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);   
                return null;
            }
                
            else 
                if(noteStruct=="model.updatableview.RegionsVO"){
                    RichPopup popup = this.getPopRegions();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);                   
                    return null;
                }
                else
                    if(noteStruct=="model.updatableview.VillesVO"){
                        RichPopup popup = this.getPopupVille();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);                      
                        return null;
                    } 
        }    
        return null;
    }

    public void onDeleteNode(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationDelete = bindings.getOperationBinding("DeletePays");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTable());
                return ;
            }
            
        }
    }

    public void setPopDelPays(RichPopup popDelPays) {
        this.popDelPays = popDelPays;
    }

    public RichPopup getPopDelPays() {
        return popDelPays;
    }
}
