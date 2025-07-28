package view.beans.domaines;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;


import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Key;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import view.beans.jsfutils.JSFUtils;

public class DomainesBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    // private RichTree dapTree;
    private RichPopup popupNewDomaine;
    private RichPopup popupNewMention;
    private RichPopup popupNewSpecialite;
    private RichPopup popupEditDomaime;
    private RichPopup popupEditMention;
    private RichPopup popupEditSpecialite;
    private RichTable domaineTable;
    private RichTable mentionTable;
    private RichTable specialiteTable;
    private RichPopup popupDeleteDomaine;
    private RichPopup popupDeleteMention;
    private RichPopup popupDeleteSpecialite;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichPopup popupMentionExist;
    private RichPopup popupFormExist;
    private RichPopup popupSpecExist;

    public DomainesBean() {
    }

    /* public void OnNewClick(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Clicked Action Listenner");
    }

    public void setDapTree(RichTree dapTree) {
        this.dapTree = dapTree;
    }

    public RichTree getDapTree() {
        return dapTree;
    }

    public String OnNewClick() {
        // Add event code here...
        System.out.println("Clicked Action");
        RowKeySet selectedEmps = getDapTree().getSelectedRowKeys();
        Iterator selectedEmpIterator = selectedEmps.iterator();
        while (selectedEmpIterator.hasNext()) {
            List ListKey = (List) selectedEmpIterator.next();
            Key key = (Key) ListKey.get(0);
            System.out.println("key " + key);
            System.out.println("ListKey " + ListKey);
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding) ((CollectionModel) getDapTree().getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(ListKey);
            String nodeStructureDef = nodeBinding.getHierTypeBinding().getStructureDefName();
            System.out.println("nodeStructureDef " + nodeStructureDef);
            Row rw = nodeBinding.getRow();
            System.out.println("row " + rw);

            ADFContext adfCtx = ADFContext.getCurrent();
            BindingContainer bindings = getBindings();

            if (nodeStructureDef.contains("model.updatableview.DomainesVO")) {
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("DomainesIterator");
                Row oldCcurrentRow = dciter.getCurrentRow();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                //perform row create
                OperationBinding operationBinding = bindings.getOperationBinding("CreateDomaine");
                Object result = operationBinding.execute();
                RichPopup popup = this.getPopupNewDomaine();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (nodeStructureDef.contains("model.updatableview.MentionsVO")) {
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MentionsDomainesIterator");
                Row oldCcurrentRow = dciter.getCurrentRow();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                //perform row create
                OperationBinding operationBinding = bindings.getOperationBinding("CreateMension");
                Object result = operationBinding.execute();
                RichPopup popup = this.getPopupNewMention();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else {
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("SpecialitesMentionsIterator");
                Row oldCcurrentRow = dciter.getCurrentRow();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
                //perform row create
                OperationBinding operationBinding = bindings.getOperationBinding("CreateSpecialite");
                Object result = operationBinding.execute();
                RichPopup popup = this.getPopupNewSpecialite();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
        return null;
    } */

    public void setPopupNewDomaine(RichPopup popupNewDomaine) {
        this.popupNewDomaine = popupNewDomaine;
    }

    public RichPopup getPopupNewDomaine() {
        return popupNewDomaine;
    }

    public void setPopupNewMention(RichPopup popupNewMention) {
        this.popupNewMention = popupNewMention;
    }

    public RichPopup getPopupNewMention() {
        return popupNewMention;
    }

    public void setPopupNewSpecialite(RichPopup popupNewSpecialite) {
        this.popupNewSpecialite = popupNewSpecialite;
    }

    public RichPopup getPopupNewSpecialite() {
        return popupNewSpecialite;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    // For All popup : New & Edit
    public void OnDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupNewDomaine().hide();
            this.getPopupEditDomaime().hide();
            this.getPopupNewMention().hide();
            this.getPopupEditMention().hide();
            this.getPopupNewSpecialite().hide();
            this.getPopupEditSpecialite().hide();
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMentionTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable()); */
        }
    }
    // Domaine dialog : New & Edit
    public void OnDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewDomaine().hide();
        //this.getPopupEditDomaime().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("DomainesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopupEditDomaime(RichPopup popupEditDomaime) {
        this.popupEditDomaime = popupEditDomaime;
    }

    public RichPopup getPopupEditDomaime() {
        return popupEditDomaime;
    }

    public void setPopupEditMention(RichPopup popupEditMention) {
        this.popupEditMention = popupEditMention;
    }

    public RichPopup getPopupEditMention() {
        return popupEditMention;
    }

    public void setPopupEditSpecialite(RichPopup popupEditSpecialite) {
        this.popupEditSpecialite = popupEditSpecialite;
    }

    public RichPopup getPopupEditSpecialite() {
        return popupEditSpecialite;
    }

    /* public String OnEditClick() {
        // Add event code here...
        RowKeySet selectedRow = getDapTree().getSelectedRowKeys();
        Iterator selectedRowIterator = selectedRow.iterator();
        while (selectedRowIterator.hasNext()) {
            List ListKey = (List) selectedRowIterator.next();
            Key key = (Key) ListKey.get(0);
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding) ((CollectionModel) getDapTree().getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(ListKey);
            String nodeStructureDef = nodeBinding.getHierTypeBinding().getStructureDefName();

            if (nodeStructureDef.contains("model.updatableview.SpecialitesVO")) {
                RichPopup popup = this.getPopupEditSpecialite();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (nodeStructureDef.contains("model.updatableview.MentionsVO")) {
                RichPopup popup = this.getPopupEditMention();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else {
                RichPopup popup = this.getPopupEditDomaime();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
        return null;
    }

    public void OnDeleteClick(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Delete Action Listener");
    }

    public String OnClickDelete() {
        // Add event code here...
        System.out.println("Delete Action");

        // Remove Selecteds rows
        /*  RichTree t1 = this.getDapTree();

        RowKeySet rks = t1.getSelectedRowKeys();
        Iterator nodeiter = rks.iterator();

        CollectionModel treeModel = (CollectionModel) t1.getValue();
        JUCtrlHierNodeBinding treeBinding = (JUCtrlHierNodeBinding) treeModel.getWrappedData();

        while(nodeiter.hasNext()){
            List node = (List) nodeiter.next();
            JUCtrlHierBinding nodeBinding = treeBinding.findNodeByKeyPath(node);
            Row rw = nodeBinding.getRow();
            rw.remove();
            }
        if(rks.size() > 0){
            AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(t1);
        }
        return null;
    } */

    public void OnNewDomaineClick(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("DomainesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateDomaine");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewDomaine();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return;
    }

    public void setDomaineTable(RichTable domaineTable) {
        this.domaineTable = domaineTable;
    }

    public RichTable getDomaineTable() {
        return domaineTable;
    }

    public void OnEditDomaineClick(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie2");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditDomaime();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void OnNewMentionClick(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MentionsDomainesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateMension");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewMention();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditMentionClick(ActionEvent actionEvent) {
        // Add event code here...        
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie1");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditMention();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void OnNewSpecialiteClick(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree2");
        
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("SpecialitesMentionsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateSpecialite");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewSpecialite();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditSpecialiteClick(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditSpecialite();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void setMentionTable(RichTable mentionTable) {
        this.mentionTable = mentionTable;
    }

    public RichTable getMentionTable() {
        return mentionTable;
    }

    public void setSpecialiteTable(RichTable specialiteTable) {
        this.specialiteTable = specialiteTable;
    }

    public RichTable getSpecialiteTable() {
        return specialiteTable;
    }

    public void OnDialogMentionCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewMention().hide();
        //this.getPopupEditMention().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MentionsDomainesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMentionTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void OnDialogSpecialiteCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        System.out.println(bindings.getAttributeBindings());
        this.getPopupNewSpecialite().hide();
        this.getPopupEditSpecialite().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("SpecialitesMentionsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopupDeleteDomaine(RichPopup popupDeleteDomaine) {
        this.popupDeleteDomaine = popupDeleteDomaine;
    }

    public RichPopup getPopupDeleteDomaine() {
        return popupDeleteDomaine;
    }

    public void setPopupDeleteMention(RichPopup popupDeleteMention) {
        this.popupDeleteMention = popupDeleteMention;
    }

    public RichPopup getPopupDeleteMention() {
        return popupDeleteMention;
    }

    public void setPopupDeleteSpecialite(RichPopup popupDeleteSpecialite) {
        this.popupDeleteSpecialite = popupDeleteSpecialite;
    }

    public RichPopup getPopupDeleteSpecialite() {
        return popupDeleteSpecialite;
    }

    public void OnDeleteSpecialiteAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            OnDeleteSpecialiteClicked();
        }
    }

    public String OnDeleteSpecialiteClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteSpecialite");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteMentionAction(DialogEvent dialogEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupDeleteMention().hide();
            System.out.println("Deleting");
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            System.out.println("outcome yes");
            AttributeBinding mtIdBinding = (AttributeBinding) bindings.getControlBinding("IdMention2");
            Integer mt_id = (Integer.parseInt(mtIdBinding.getInputValue().toString()));
            System.out.println("mt_id : "+mt_id);
            OperationBinding isSpectExist = bindings.getOperationBinding("IsSpectMentionExist");
            isSpectExist.getParamsMap().put("mt_id", mt_id);
            isSpectExist.execute();

            DCIteratorBinding iterSpMt = (DCIteratorBinding) bindings.get("IsSpectMentionExistIterator");
            Row crntsp = iterSpMt.getCurrentRow();
            System.out.println("Nbr Spécialité : "+iterSpMt.getEstimatedRowCount());
            if (crntsp == null) {
                System.out.println("No specialite for this mention");
                OperationBinding isFormExist = bindings.getOperationBinding("IsFormationMentionExist");
                isFormExist.getParamsMap().put("mt_id", mt_id);
                isFormExist.execute();

                DCIteratorBinding iterFrMt = (DCIteratorBinding) bindings.get("IsFormationMentionExistIterator");
                Row crntfr = iterFrMt.getCurrentRow();
                System.out.println("Nbr Formation : "+iterFrMt.getEstimatedRowCount());
                if (crntfr == null) {
                    System.out.println("No formation for this mention");
                    OperationBinding operationdelMt = bindings.getOperationBinding("DeleteMension");
                    operationdelMt.execute();
                    if (!operationdelMt.getErrors().isEmpty()) {
                        
                    } else {
                        OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                        Object commitResult = operationCommit.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
                    }
                }else{
                    //popup formation existe
                    RichPopup popup = this.getPopupFormExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }
            }else{
                //popup spécialité existantes
                RichPopup popup = this.getPopupSpecExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public String OnDeleteMentionClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //OperationBinding operationBinding = bindings.getOperationBinding("isSpecialiteExist");
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteMension");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
            return null;
        }
    }

    public void onNewDomaineAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupNewDomaine().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
        }
    }

    public void onNewMentionAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree1.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupNewMention().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMentionTable());
        }
    }

    public void onNewSpecialiteAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree2.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupNewSpecialite().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
        }
    }

    public void onEditDomaineAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie2.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupEditDomaime().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
        }
    }

    public void onEditMentionAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie1.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupEditMention().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMentionTable());
        }
    }

    public void onEditSpecialiteAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                // if error
            }
            this.getPopupEditSpecialite().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSpecialiteTable());
        }
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteDomaineAction(DialogEvent dialogEvent) {
        this.getPopupDeleteDomaine().hide();
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding dmIdBinding = (AttributeBinding) bindings.getControlBinding("IdDomaine");
            Integer dm_id = (Integer.parseInt(dmIdBinding.getInputValue().toString()));

            OperationBinding isMentionExist = bindings.getOperationBinding("IsMentionDomaineExist");

            isMentionExist.getParamsMap().put("dm_id", dm_id);
            Object resul = isMentionExist.execute();

            DCIteratorBinding iterMtDm = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("IsMentionDomaineExistIterator");
            Row crnt = iterMtDm.getCurrentRow();
            if (crnt == null) {
                OperationBinding operationBinding = bindings.getOperationBinding("DeleteDomaine");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    
                } else {
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    Object commitResult = operationCommit.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDomaineTable());
                    
                }
            }else{
                //popup données fils existante
                RichPopup popup = this.getPopupMentionExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }

    }

    public void setPopupMentionExist(RichPopup popupMentionExist) {
        this.popupMentionExist = popupMentionExist;
    }

    public RichPopup getPopupMentionExist() {
        return popupMentionExist;
    }

    public void setPopupFormExist(RichPopup popupFormExist) {
        this.popupFormExist = popupFormExist;
    }

    public RichPopup getPopupFormExist() {
        return popupFormExist;
    }

    public void setPopupSpecExist(RichPopup popupSpecExist) {
        this.popupSpecExist = popupSpecExist;
    }

    public RichPopup getPopupSpecExist() {
        return popupSpecExist;
    }
}
