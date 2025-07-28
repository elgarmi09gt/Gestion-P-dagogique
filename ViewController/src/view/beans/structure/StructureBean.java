package view.beans.structure;

import java.io.IOException;
import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.io.UnsupportedEncodingException;

import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.adfinternal.view.faces.event.rich.FileDownloadActionListener;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.ViewObject;

import view.beans.admin.AdministrationBean;
import view.beans.jsfutils.JSFUtils;

public class StructureBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    final int CHEF_DEPT = 4;
    final int ADM_ETAB = 23;
    private RichTable structureTable;
    private RichPopup popupNewStructure;
    private RichPopup popupEditStructure;
    private RichPopup popupDeleteStructure;
    private RichTable hstructureTable;
    private RichPopup popupNewHStruct;
    private RichPopup popupEditHStructure;
    private RichPopup popupDeleteHStructure;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    Map pageScope = ADFContext.getCurrent().getPageFlowScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private String login = sessionScope.get("userName").toString();
    private RichPopup popupDeptExist;
    private RichPopup popupUtiExist;
    private RichPopup popupFormExist;
    private RichPopup popupStructExist;
    private RichPopup popupDeptExistYet;
    private RichPopup popupcheckts;
    private RichPopup popupImportStr;
    private RichPopup popupImportSucceded;
    private RichPanelGroupLayout etabPanGrp;
    private RichPanelCollection etabPanCol;
    private RichTable etabTab;
    private RichPopup popupImportFailed;
    private RichPanelCollection etabRefPanCol;
    private RichPanelHeader etabRefPanHdr;
    private RichPanelGroupLayout etabRefPanGrp;
    private RichInputText m_;
    private RichPanelCollection detAgentPanCol;
    private RichPopup userNotFound;
    private RichPanelGroupLayout panGrpResult;
    private RichPopup adminEtabcreated;
    private RichPopup adminEtabfailed;
    private RichPopup popupShowAdmin;
    private RichPopup popEnterEmail;
    private RichPanelGroupLayout searchPanGrp;
    private RichButton btnExport;
    private RichPanelGroupLayout searchDeptPanGrp;
    private RichInputText matricule;
    private RichPanelGroupLayout deptPanGrp;
    private RichPanelCollection deptPanCollect;
    private RichPopup popChefDeptCreated;
    private RichPopup popChefDeptExiste;

    public StructureBean() {
    }

    public void setStructureTable(RichTable structureTable) {
        this.structureTable = structureTable;
    }

    public RichTable getStructureTable() {
        return structureTable;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNewStructureClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("StructuresIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateStructure");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewStructure();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void setPopupNewStructure(RichPopup popupNewStructure) {
        this.popupNewStructure = popupNewStructure;
    }

    public RichPopup getPopupNewStructure() {
        return popupNewStructure;
    }

    // For All New & Edit Dialog ok action
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
            this.getPopupNewStructure().hide();
            this.getPopupNewHStruct().hide();
            this.getPopupEditStructure().hide();
            this.getPopupEditHStructure().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
        }
    }

    // New & Edit Structure
    public void OnStructureDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewStructure();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("StructuresIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void OnEditStructureClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditStructure();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public void setPopupEditStructure(RichPopup popupEditStructure) {
        this.popupEditStructure = popupEditStructure;
    }

    public RichPopup getPopupEditStructure() {
        return popupEditStructure;
    }

    public void setPopupDeleteStructure(RichPopup popupDeleteStructure) {
        this.popupDeleteStructure = popupDeleteStructure;
    }

    public RichPopup getPopupDeleteStructure() {
        return popupDeleteStructure;
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteStructureAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding strIdBinding = (AttributeBinding) bindings.getControlBinding("IdStructure");
            Integer str_id = (Integer.parseInt(strIdBinding.getInputValue().toString()));
            OperationBinding isDeptExist = bindings.getOperationBinding("IsDeptEtabExist");
            isDeptExist.getParamsMap().put("str_id", str_id);
            isDeptExist.execute();
            DCIteratorBinding iterDeptEtab = (DCIteratorBinding) bindings.get("IsDeptEtabExistIterator");
            Row crntstr = iterDeptEtab.getCurrentRow();
            if (crntstr == null) {
                OperationBinding isUtiExist = bindings.getOperationBinding("IsUtiEtabExist");
                isUtiExist.getParamsMap().put("str_id", str_id);
                isUtiExist.execute();
                DCIteratorBinding iterUtiEtab = (DCIteratorBinding) bindings.get("IsUtiEtabExistIterator");
                Row crntuti = iterUtiEtab.getCurrentRow();
                if (crntuti == null) {
                    OnDeleteStructureClicked();
                }else{
                //popup uti exist
                RichPopup popup = this.getPopupUtiExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
            }else{
                //popup formation exist
                RichPopup popup = this.getPopupDeptExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
            
        }
    }

    public String OnDeleteStructureClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteStructure");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
           // AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
            return null;
        }
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

    public void setPopupEditHStructure(RichPopup popupEditHStructure) {
        this.popupEditHStructure = popupEditHStructure;
    }

    public RichPopup getPopupEditHStructure() {
        return popupEditHStructure;
    }

    public void setPopupDeleteHStructure(RichPopup popupDeleteHStructure) {
        this.popupDeleteHStructure = popupDeleteHStructure;
    }

    public RichPopup getPopupDeleteHStructure() {
        return popupDeleteHStructure;
    }

    public void OnNewHStructClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HistoriquesStructuresStructIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateHStructure");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewHStruct();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditHStructClick(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie1");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditHStructure();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);

    }

    public String OnDeleteHStructClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteHStructure");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
            return null;
        }
    }

    // Historique Structure Cancel Dialog
    public void OnHStructDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewHStruct();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HistoriquesStructuresStructIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        if(adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR) != null){
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteHStructAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding hsIdBinding = (AttributeBinding) bindings.getControlBinding("IdHistoriquesStructure");
            Integer hs_id = (Integer.parseInt(hsIdBinding.getInputValue().toString()));
            OperationBinding isFormExist = bindings.getOperationBinding("IsFormDeptExist");
            isFormExist.getParamsMap().put("hs_id", hs_id);
            isFormExist.execute();
            DCIteratorBinding iterFormDept = (DCIteratorBinding) bindings.get("IsFormDeptExistIterator");
            Row crntfr = iterFormDept.getCurrentRow();
            if (crntfr == null) {
                OnDeleteHStructClicked();
            } else {
                //popup uti exist
                RichPopup popup = this.getPopupFormExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onNewStructureAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree.inputValue}", getUtilisateur());
            DCIteratorBinding iterstruct = (DCIteratorBinding) bindings.get("StructuresIterator");
            Row crntstr = iterstruct.getCurrentRow();
            
            OperationBinding isstructExist = bindings.getOperationBinding("IsStructExist");
            isstructExist.getParamsMap().put("lib", crntstr.getAttribute("LibelleLong").toString());
            isstructExist.getParamsMap().put("abrev", crntstr.getAttribute("LibelleCourt").toString());
            isstructExist.execute();
            DCIteratorBinding iterstructt = (DCIteratorBinding) bindings.get("IsStructExistIterator");
            Row crntstrr = iterstructt.getCurrentRow();
            if (crntstrr == null) {
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
                this.getPopupNewStructure().hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
            }else{
                RichPopup popup = this.getPopupStructExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void onEditStructAction(DialogEvent dialogEvent) {
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
            this.getPopupEditStructure().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
        }
    }

    @SuppressWarnings("unchecked")
    public void onNewHistoriqueAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree1.inputValue}", getUtilisateur());
            DCIteratorBinding iterhstruct = (DCIteratorBinding) bindings.get("HistoriquesStructuresStructIterator");
            Row crnthstr = iterhstruct.getCurrentRow();
            
            OperationBinding ishstructExist = bindings.getOperationBinding("IsHStructExist");
            ishstructExist.getParamsMap().put("lib", crnthstr.getAttribute("LibelleLong").toString());
            ishstructExist.getParamsMap().put("abrev", crnthstr.getAttribute("LibelleCourt").toString());
            ishstructExist.execute();
            DCIteratorBinding iterhstructt = (DCIteratorBinding) bindings.get("IsHStructExistIterator");
            Row crnthstrr = iterhstructt.getCurrentRow();
            if (crnthstrr == null) {
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
                this.getPopupNewHStruct().hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
            }else{
                RichPopup popup = this.getPopupDeptExistYet();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void onEditHistoriqueAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie1.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditHStructure().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getHstructureTable());
        }
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setPopupDeptExist(RichPopup popupDeptExist) {
        this.popupDeptExist = popupDeptExist;
    }

    public RichPopup getPopupDeptExist() {
        return popupDeptExist;
    }

    public void setPopupUtiExist(RichPopup popupUtiExist) {
        this.popupUtiExist = popupUtiExist;
    }

    public RichPopup getPopupUtiExist() {
        return popupUtiExist;
    }

    public void setPopupFormExist(RichPopup popupFormExist) {
        this.popupFormExist = popupFormExist;
    }

    public RichPopup getPopupFormExist() {
        return popupFormExist;
    }

    public void setPopupStructExist(RichPopup popupStructExist) {
        this.popupStructExist = popupStructExist;
    }

    public RichPopup getPopupStructExist() {
        return popupStructExist;
    }

    public void setPopupDeptExistYet(RichPopup popupDeptExistYet) {
        this.popupDeptExistYet = popupDeptExistYet;
    }

    public RichPopup getPopupDeptExistYet() {
        return popupDeptExistYet;
    }

    @SuppressWarnings("unchecked")
    public void OnImportEtabClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("ScolEtabNotIntegratedIterator");
        Row CurrentStr = iterStr.getCurrentRow();
        if (null != CurrentStr) {
            try {
                OperationBinding opcreatestr = bindings.getOperationBinding("createOrUpdateStruct");
                opcreatestr.getParamsMap().put("anc_code", CurrentStr.getAttribute("CodeScol"));
                opcreatestr.getParamsMap().put("lib", CurrentStr.getAttribute("Libelle"));
                opcreatestr.getParamsMap().put("abrev", CurrentStr.getAttribute("Sigle"));
                opcreatestr.getParamsMap().put("tel", CurrentStr.getAttribute("Telephone"));
                opcreatestr.getParamsMap().put("addr", CurrentStr.getAttribute("Adresse"));
                opcreatestr.getParamsMap().put("type_etab", CurrentStr.getAttribute("TypeEtab"));
                opcreatestr.getParamsMap().put("grh_code", CurrentStr.getAttribute("Codegrh"));
                opcreatestr.getParamsMap().put("utilisateur", getUtilisateur());
                Long str_id = (Long) opcreatestr.execute();
                if(-1 == str_id ){
                    //System.out.println("Pas de type section : show popup to check type(ecole,faculté)");
                    RichPopup popup = this.getPopupcheckts();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }else if(0 != str_id){
                    //System.out.println("Import succeded !");
                    ViewObject vo=iterStr.getViewObject();  
                    vo.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanGrp());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanCol());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabTab());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanGrp());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanHdr());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanCol());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
                    RichPopup popup = this.getPopupImportSucceded();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }else{
                    //System.out.println("Import failed !");
                    ViewObject vo=iterStr.getViewObject();
                    vo.executeQuery();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanGrp());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanCol());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabTab());   
                    RichPopup popup = this.getPopupImportFailed();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }
            } catch (Exception e) {
            System.out.println(e);
            }
        }
    }

    public void setPopupcheckts(RichPopup popupcheckts) {
        this.popupcheckts = popupcheckts;
    }

    public RichPopup getPopupcheckts() {
        return popupcheckts;
    }

    public void setPopupImportStr(RichPopup popupImportStr) {
        this.popupImportStr = popupImportStr;
    }

    public RichPopup getPopupImportStr() {
        return popupImportStr;
    }

    public void setPopupImportSucceded(RichPopup popupImportSucceded) {
        this.popupImportSucceded = popupImportSucceded;
    }

    public RichPopup getPopupImportSucceded() {
        return popupImportSucceded;
    }

    @SuppressWarnings("unchecked")
    public void OnSpecifyTsAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("ScolEtabNotIntegratedIterator");
        Row CurrentStr = iterStr.getCurrentRow();
        DCIteratorBinding iterTs = (DCIteratorBinding) bindings.get("TypeSectionsIterator");
        Row CurrentTs = iterTs.getCurrentRow();
        if(null != CurrentTs){
            OperationBinding opcreatestr = bindings.getOperationBinding("createOrUpdateStruct");
            opcreatestr.getParamsMap().put("anc_code", CurrentStr.getAttribute("CodeScol"));
            opcreatestr.getParamsMap().put("lib", CurrentStr.getAttribute("Libelle"));
            opcreatestr.getParamsMap().put("abrev", CurrentStr.getAttribute("Sigle"));
            opcreatestr.getParamsMap().put("tel", CurrentStr.getAttribute("Telephone"));
            opcreatestr.getParamsMap().put("addr", CurrentStr.getAttribute("Adresse"));
            opcreatestr.getParamsMap().put("type_etab", CurrentTs.getAttribute("LibelleLong"));
            opcreatestr.getParamsMap().put("utilisateur", getUtilisateur());
            Long str_id = (Long) opcreatestr.execute();
            if (0 != str_id) {
                //System.out.println("Import succeded !");
                ViewObject vo = iterStr.getViewObject();
                vo.executeQuery();
                
                DCIteratorBinding iterSt = (DCIteratorBinding) bindings.get("StructuresIterator");
                vo = iterSt.getViewObject();
                vo.executeQuery();
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabTab());   
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
                this.getPopupcheckts().hide();
                RichPopup popup = this.getPopupImportSucceded();
                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                popup.show(ph);
            } else {
                //System.out.println("Import failed !");
                ViewObject vo=iterStr.getViewObject();  
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabTab());
                RichPopup popup = this.getPopupImportFailed();
                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                popup.show(ph);
            }
        }
    }

    public void setEtabPanGrp(RichPanelGroupLayout etabPanGrp) {
        this.etabPanGrp = etabPanGrp;
    }

    public RichPanelGroupLayout getEtabPanGrp() {
        return etabPanGrp;
    }

    public void setEtabPanCol(RichPanelCollection etabPanCol) {
        this.etabPanCol = etabPanCol;
    }

    public RichPanelCollection getEtabPanCol() {
        return etabPanCol;
    }

    public void setEtabTab(RichTable etabTab) {
        this.etabTab = etabTab;
    }

    public RichTable getEtabTab() {
        return etabTab;
    }

    public void setPopupImportFailed(RichPopup popupImportFailed) {
        this.popupImportFailed = popupImportFailed;
    }

    public RichPopup getPopupImportFailed() {
        return popupImportFailed;
    }

    public void OnPopupAddEtabCancel(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("ScolEtabNotIntegratedIterator");
        ViewObject vo=iterStr.getViewObject();  
        vo.executeQuery();
        DCIteratorBinding iterSt = (DCIteratorBinding) bindings.get("StructuresIterator");
        vo=iterSt.getViewObject(); 
        vo.executeQuery();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabTab());        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtabRefPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getStructureTable());
    }

    public void setEtabRefPanCol(RichPanelCollection etabRefPanCol) {
        this.etabRefPanCol = etabRefPanCol;
    }

    public RichPanelCollection getEtabRefPanCol() {
        return etabRefPanCol;
    }

    public void setEtabRefPanHdr(RichPanelHeader etabRefPanHdr) {
        this.etabRefPanHdr = etabRefPanHdr;
    }

    public RichPanelHeader getEtabRefPanHdr() {
        return etabRefPanHdr;
    }

    public void setEtabRefPanGrp(RichPanelGroupLayout etabRefPanGrp) {
        this.etabRefPanGrp = etabRefPanGrp;
    }

    public RichPanelGroupLayout getEtabRefPanGrp() {
        return etabRefPanGrp;
    }

    @SuppressWarnings("unchecked")
    public void onSearchUserClicked(ActionEvent actionEvent) {
        // Add event code here...
        if (null != this.getM_().getValue()) {
            //System.out.println("email : " + this.getM_().getValue());
            String name = this.getM_().getValue().toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Veuillez entrer un email ucad valide.";
            if (matcher.matches()) {
                //Do your further processing
                BindingContainer bindings = getBindings();
                DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("StructuresIterator") ;
                Row currentStr = iterStr.getCurrentRow();
                //Execute grhuserbyStructure
                if(null != currentStr.getAttribute("CodeGrh")){
                    //System.out.println("Code grh : "+currentStr.getAttribute("CodeGrh"));
                    OperationBinding opsearch = bindings.getOperationBinding("grhUserEtab");
                    opsearch.getParamsMap().put("mat", this.getM_().getValue());
                    opsearch.getParamsMap().put("grh_code", currentStr.getAttribute("CodeGrh"));
                    opsearch.getParamsMap().put("log", getLogin());
                    opsearch.execute();
                    DCIteratorBinding user_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .get("grhUserEtabIterator");
                    Row crnt_is = user_iter.getCurrentRow();
                    if (null != crnt_is) {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetAgentPanCol());
                        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetagentEtab());
                    } else {
                        //show popup user not exist
                        RichPopup popup = this.getUserNotFound();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
               
            } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        } else {
            System.out.println("Matricule empty");
        }

    }

    public void setM_(RichInputText m_) {
        this.m_ = m_;
    }

    public RichInputText getM_() {
        return m_;
    }

    public void setDetAgentPanCol(RichPanelCollection detAgentPanCol) {
        this.detAgentPanCol = detAgentPanCol;
    }

    public RichPanelCollection getDetAgentPanCol() {
        return detAgentPanCol;
    }

    public void setUserNotFound(RichPopup userNotFound) {
        this.userNotFound = userNotFound;
    }

    public RichPopup getUserNotFound() {
        return userNotFound;
    }

    @SuppressWarnings("unchecked")
    public void onAdminEtabSaved(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        Long id_user = new Long(0);
        char [] password = new char [100];
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        //penser à utiliser le role aulieu du username 
        DCIteratorBinding user_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("grhUserEtabIterator");
        Row crnt_is = user_iter.getCurrentRow();
        if (null != crnt_is) {
            //System.out.println(crnt_is.getAttribute("CodeMatricule"));
            /*try {
                System.out.println("user : "+getUtilisateur());
                System.out.println("Anc code : "+crnt_is.getAttribute("AncienCode"));
                System.out.println("Email : "+crnt_is.getAttribute("Emailucad"));
                OperationBinding opnewUser = bindings.getOperationBinding("createOrUpdateUser");
                opnewUser.getParamsMap().put("email", crnt_is.getAttribute("Emailucad").toString());
                opnewUser.getParamsMap().put("struct_", crnt_is.getAttribute("AncienCode").toString());
                opnewUser.getParamsMap().put("utilisateur", getUtilisateur());
                id_user = (Long) opnewUser.execute();*/
                try {
                    OperationBinding opnewUser = bindings.getOperationBinding("createOrUpdateUser");
                    opnewUser.getParamsMap().put("email", crnt_is.getAttribute("Emailucad"));
                    opnewUser.getParamsMap().put("struct_", crnt_is.getAttribute("AncienCode").toString());
                    opnewUser.getParamsMap().put("utilisateur", getUtilisateur());
                    id_user = (Long) opnewUser.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 != id_user) {
                    //System.out.println("id_user : "+id_user);
                    //String login = crnt_is.getAttribute("Emailucad").toString().split("@")[0];
                    String login = crnt_is.getAttribute("Emailucad").toString();
                    String matricule = crnt_is.getAttribute("CodeMatricule").toString();
                    //matricule = "passer123*";
                    password = matricule.toCharArray();
                    String role = null;
                    String username = null;
                    try {
                        AdministrationBean.createWlsUser(login, password);
                        try {
                            OperationBinding opUserRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                            opUserRole.getParamsMap().put("user_id", id_user);
                            opUserRole.getParamsMap().put("role_id", new Long(ADM_ETAB));
                            opUserRole.getParamsMap().put("utilisateur", getUtilisateur());
                            opUserRole.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                            opusername.getParamsMap().put("user_id", id_user);
                            username = (String) opusername.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding oprole = bindings.getOperationBinding("getrole");
                            oprole.getParamsMap().put("role_id", new Long(ADM_ETAB));
                            role = (String) oprole.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (null != role && null != username) {
                            role = role.substring(0, 1) + role.substring(1).toLowerCase();
                            try {
                                AdministrationBean.assignWlsRole2User(role, username);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        RichPopup popup = this.getAdminEtabcreated();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                        this.getM_().resetValue();
                        OperationBinding opsearch = bindings.getOperationBinding("grhUserEtab");
                        opsearch.getParamsMap().put("mat", "0");
                        opsearch.getParamsMap().put("grh_code", "0");
                        opsearch.getParamsMap().put("log", "0");
                        opsearch.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    RichPopup popup = this.getAdminEtabfailed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            /*} catch (Exception e) {
                System.out.println(e);
            }*/
            
        }else{
            RichPopup popup = this.getPopEnterEmail();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        OperationBinding opCommit = bindings.getOperationBinding("Commit");
        opCommit.execute();
        /*DCIteratorBinding iterBind = iterBind= (DCIteratorBinding)bindings.get(iterfresh);
        ViewObject vo=iterBind.getViewObject();  
        vo.executeQuery();*/
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpResult());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetAgentPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSearchPanGrp());
    }

    public void setPanGrpResult(RichPanelGroupLayout panGrpResult) {
        this.panGrpResult = panGrpResult;
    }

    public RichPanelGroupLayout getPanGrpResult() {
        return panGrpResult;
    }

    public void setAdminEtabcreated(RichPopup adminEtabcreated) {
        this.adminEtabcreated = adminEtabcreated;
    }

    public RichPopup getAdminEtabcreated() {
        return adminEtabcreated;
    }

    public void setAdminEtabfailed(RichPopup adminEtabfailed) {
        this.adminEtabfailed = adminEtabfailed;
    }

    public RichPopup getAdminEtabfailed() {
        return adminEtabfailed;
    }

    @SuppressWarnings("unchecked")
    public void onShowAdminEtabAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding) bindings.get("StructuresIterator");
        Row current = dc.getCurrentRow();
        if(null != current){
            sessionScope.put("lib_str", current.getAttribute("LibelleCourt"));
            OperationBinding opshow = bindings.getOperationBinding("getAdminByEtablissement");
            opshow.getParamsMap().put("str_id", current.getAttribute("IdStructure"));
            opshow.execute();
            RichPopup pop = this.getPopupShowAdmin();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            pop.show(ph);
        }
    }

    public void setPopupShowAdmin(RichPopup popupShowAdmin) {
        this.popupShowAdmin = popupShowAdmin;
    }

    public RichPopup getPopupShowAdmin() {
        return popupShowAdmin;
    }

    @SuppressWarnings("unchecked")
    public void onRetriveAdminEtabAction(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding cd = (DCIteratorBinding)getBindings().get("AdminByEtablissementIterator");
        Row currentUserRole = cd.getCurrentRow();
        if(null != currentUserRole){
            try{
                //deleteUserBdRole
                OperationBinding opdeleteBdUserRole = getBindings().getOperationBinding("DeleteUserRole");
                opdeleteBdUserRole.getParamsMap().put("user_id", currentUserRole.getAttribute("IdUtilisateur"));
                opdeleteBdUserRole.getParamsMap().put("role_id", currentUserRole.getAttribute("IdRole"));
                opdeleteBdUserRole.execute();
                //DeleteUserWeblogicRole
                OperationBinding opusername = getBindings().getOperationBinding("getusername");
                opusername.getParamsMap().put("user_id", currentUserRole.getAttribute("IdUtilisateur"));
                String username = (String)opusername.execute();
                OperationBinding oprole = getBindings().getOperationBinding("getrole");
                oprole.getParamsMap().put("user_id", currentUserRole.getAttribute("IdRole"));
                String role = (String)oprole.execute();
                if((null != username) && (null != role)){
                    AdministrationBean.revokeWlsRole2User(role, username);
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onRevokeAdminConfirm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            DCIteratorBinding cd = (DCIteratorBinding)getBindings().get("AdminByEtablissementIterator");
            //System.out.println("Count : "+cd.getEstimatedRowCount());
            Row currentUserRole = cd.getCurrentRow();
            if(null != currentUserRole){
                try{
                    //deleteUserBdRole
                    OperationBinding opdeleteBdUserRole = getBindings().getOperationBinding("DeleteUserRole");
                    opdeleteBdUserRole.getParamsMap().put("user_id", currentUserRole.getAttribute("IdUtilisateur"));
                    opdeleteBdUserRole.getParamsMap().put("role_id", currentUserRole.getAttribute("IdRole"));
                    opdeleteBdUserRole.execute();
                    //DeleteUserWeblogicRole
                    OperationBinding opusername = getBindings().getOperationBinding("getusername");
                    opusername.getParamsMap().put("user_id", currentUserRole.getAttribute("IdUtilisateur"));
                    String username = (String)opusername.execute();
                    //System.out.println("username : "+username);
                    OperationBinding oprole = getBindings().getOperationBinding("getrole");
                    oprole.getParamsMap().put("role_id", currentUserRole.getAttribute("IdRole"));
                    String role = (String)oprole.execute();
                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                    //System.out.println("role : "+role);
                    if((null != username) && (null != role)){
                        AdministrationBean.revokeWlsRole2User(role, username);
                    }
                    ViewObject vo=cd.getViewObject();  
                    vo.executeQuery();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }

    public void setPopEnterEmail(RichPopup popEnterEmail) {
        this.popEnterEmail = popEnterEmail;
    }

    public RichPopup getPopEnterEmail() {
        return popEnterEmail;
    }

    public void setSearchPanGrp(RichPanelGroupLayout searchPanGrp) {
        this.searchPanGrp = searchPanGrp;
    }

    public RichPanelGroupLayout getSearchPanGrp() {
        return searchPanGrp;
    }

    public void onExport(FacesContext facesContext, OutputStream outputStream) throws IOException {
        // Add event code here...
        OutputStreamWriter w = null;
        try {
            w = new OutputStreamWriter(outputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        try {
            w.write("Hi there!");
        } catch (IOException e) {
        }
        // The stream is automatically closed, but since we wrapped it,
        // we'd better flush our writer
        w.flush();
    }

    public void onExport(ActionEvent actionEvent) {
        // Add event code here...
        FileDownloadActionListener fileListener = new FileDownloadActionListener();
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        MethodExpression downloadMethodExpr = ef.createMethodExpression(elc, "xxx", null, new Class[] { FacesContext.class, OutputStream.class });

        fileListener.setMethod(downloadMethodExpr);

        fileListener.setFilename("test.txt");

        fileListener.setContentType("text/plain");

        btnExport.addActionListener(fileListener);
    }

    public void setBtnExport(RichButton btnExport) {
        this.btnExport = btnExport;
    }

    public RichButton getBtnExport() {
        return btnExport;
    }

    public void setSearchDeptPanGrp(RichPanelGroupLayout searchDeptPanGrp) {
        this.searchDeptPanGrp = searchDeptPanGrp;
    }

    public RichPanelGroupLayout getSearchDeptPanGrp() {
        return searchDeptPanGrp;
    }

    public void setMatricule(RichInputText matricule) {
        this.matricule = matricule;
    }

    public RichInputText getMatricule() {
        return matricule;
    }

    public void onSearchUserDept(ActionEvent actionEvent) {
        // Add event code here...
        if (null != this.getMatricule().getValue()) {
            //System.out.println("email : " + this.getM_().getValue());
            String name = this.getMatricule().getValue().toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Veuillez entrer un email ucad valide.";
            if (matcher.matches()) {
                //Do your further processing
                BindingContainer bindings = getBindings();
                DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("StructuresIterator") ;
                Row currentStr = iterStr.getCurrentRow();
                //Execute grhuserbyStructure
                if(null != currentStr.getAttribute("CodeGrh")){
                    //System.out.println("Code grh : "+currentStr.getAttribute("CodeGrh"));
                    OperationBinding opsearch = bindings.getOperationBinding("grhUserEtab");
                    opsearch.getParamsMap().put("mat", this.getMatricule().getValue());
                    opsearch.getParamsMap().put("grh_code", currentStr.getAttribute("CodeGrh"));
                    opsearch.getParamsMap().put("log", getLogin());
                    opsearch.execute();
                    DCIteratorBinding user_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .get("grhUserEtabIterator");
                    Row crnt_is = user_iter.getCurrentRow();
                    if (null != crnt_is) {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanGrp());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanCollect());
                    } else {
                        //show popup user not exist
                        RichPopup popup = this.getUserNotFound();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
               
            } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        } else {
            System.out.println("Matricule empty");
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @SuppressWarnings("unchecked")
    public void onChefDeptSearchAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterDept = (DCIteratorBinding) bindings.get("HistoriquesStructuresStructIterator");
        Row crntDept = iterDept.getCurrentRow();
        if(null != crntDept){
            try{
                OperationBinding opisChefDeptExiste = bindings.getOperationBinding("isChefDeptExiste");
                opisChefDeptExiste.getParamsMap().put("deptId", crntDept.getAttribute("IdHistoriquesStructure"));
                opisChefDeptExiste.execute();
                DCIteratorBinding iterChefDept = (DCIteratorBinding) bindings.get("isChefDeptExistIterator");
                if(0 != iterChefDept.getEstimatedRowCount()){
                    pageScope.put("chefDept", iterChefDept.getCurrentRow().getAttribute("Responsable").toString());
                    pageScope.put("ancRespUId", iterChefDept.getCurrentRow().getAttribute("IdUtilisateur").toString());
                    pageScope.put("ancRespUsername", iterChefDept.getCurrentRow().getAttribute("Login").toString());
                    //pageScope.put("ancRespUId", iterChefDept.getCurrentRow().getAttribute("IdUtilisateur").toString());
                    RichPopup popup = this.getPopChefDeptExiste();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }else{
                    Long id_user = new Long(0);
                    char [] password = new char [100];
                    DCIteratorBinding user_iter = (DCIteratorBinding) bindings.get("grhUserEtabIterator");
                    Row crnt_is = user_iter.getCurrentRow();
                    if (null != crnt_is) {
                            try {
                                OperationBinding opnewUser = bindings.getOperationBinding("createOrUpdateUser");
                                opnewUser.getParamsMap().put("email", crnt_is.getAttribute("Emailucad"));
                                opnewUser.getParamsMap().put("struct_", crnt_is.getAttribute("AncienCode").toString());
                                opnewUser.getParamsMap().put("utilisateur", getUtilisateur());
                                id_user = (Long) opnewUser.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 != id_user) {
                                String login = crnt_is.getAttribute("Emailucad").toString();
                                String matricule = crnt_is.getAttribute("CodeMatricule").toString();
                                //matricule = "passer123*";
                                password = matricule.toCharArray();
                                String role = null;
                                String username = null;
                                try {
                                    AdministrationBean.createWlsUser(login, password);
                                    try {
                                        OperationBinding opUserRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                        opUserRole.getParamsMap().put("user_id", id_user);
                                        opUserRole.getParamsMap().put("role_id", new Long(CHEF_DEPT));
                                        opUserRole.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserRole.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding opUserDept = bindings.getOperationBinding("createUserDet");
                                        opUserDept.getParamsMap().put("userId", id_user);
                                        opUserDept.getParamsMap().put("deptId", crntDept.getAttribute("IdHistoriquesStructure"));
                                        opUserDept.getParamsMap().put("role", "RESPONSABLE");
                                        opUserDept.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserDept.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                                        opusername.getParamsMap().put("user_id", id_user);
                                        username = (String) opusername.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding oprole = bindings.getOperationBinding("getrole");
                                        oprole.getParamsMap().put("role_id", new Long(CHEF_DEPT));
                                        role = (String) oprole.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    if (null != role && null != username) {
                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                        try {
                                            AdministrationBean.assignWlsRole2User(role, username);
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                    RichPopup popup = this.getPopChefDeptCreated();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    //empty hints renders dialog in center of screen
                                    popup.show(hints);
                                    this.getMatricule().resetValue();
                                    OperationBinding opsearch = bindings.getOperationBinding("grhUserEtab");
                                    opsearch.getParamsMap().put("mat", "0");
                                    opsearch.getParamsMap().put("grh_code", "0");
                                    opsearch.getParamsMap().put("log", "0");
                                    opsearch.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else {
                                RichPopup popup = this.getAdminEtabfailed();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                popup.show(hints);
                            }
                    }else{
                        RichPopup popup = this.getPopEnterEmail();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    OperationBinding opCommit = bindings.getOperationBinding("Commit");
                    opCommit.execute();
                     AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanGrp());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanCollect());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSearchDeptPanGrp());
                }
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setDeptPanGrp(RichPanelGroupLayout deptPanGrp) {
        this.deptPanGrp = deptPanGrp;
    }

    public RichPanelGroupLayout getDeptPanGrp() {
        return deptPanGrp;
    }

    public void setDeptPanCollect(RichPanelCollection deptPanCollect) {
        this.deptPanCollect = deptPanCollect;
    }

    public RichPanelCollection getDeptPanCollect() {
        return deptPanCollect;
    }

    public void setPopChefDeptCreated(RichPopup popChefDeptCreated) {
        this.popChefDeptCreated = popChefDeptCreated;
    }

    public RichPopup getPopChefDeptCreated() {
        return popChefDeptCreated;
    }

    public void setPopChefDeptExiste(RichPopup popChefDeptExiste) {
        this.popChefDeptExiste = popChefDeptExiste;
    }

    public RichPopup getPopChefDeptExiste() {
        return popChefDeptExiste;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmEditChefDept(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            Long id_user = new Long(0);
            char[] password = new char[100];
            DCIteratorBinding user_iter = (DCIteratorBinding) bindings.get("grhUserEtabIterator");
            Row crnt_is = user_iter.getCurrentRow();
            if (null != crnt_is) {
                try {
                    OperationBinding opnewUser = bindings.getOperationBinding("createOrUpdateUser");
                    opnewUser.getParamsMap().put("email", crnt_is.getAttribute("Emailucad"));
                    opnewUser.getParamsMap().put("struct_", crnt_is.getAttribute("AncienCode").toString());
                    opnewUser.getParamsMap().put("utilisateur", getUtilisateur());
                    id_user = (Long) opnewUser.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println("ancRespUsername : " + pageScope.get("ancRespUsername").toString());
                if (0 != id_user) {
                    if (id_user != Long.parseLong(pageScope.get("ancRespUId").toString())) {
                        System.out.println("id_user : " + id_user + " ancRespUId : " +
                                           pageScope.get("ancRespUId").toString());
                        String login = crnt_is.getAttribute("Emailucad").toString();
                        String matricule = crnt_is.getAttribute("CodeMatricule").toString();
                        //matricule = "passer123*";
                        password = matricule.toCharArray();
                        String role = null;
                        String username = pageScope.get("ancRespUsername").toString();
                        try {
                            AdministrationBean.createWlsUser(login, password);
                            try {
                                OperationBinding opRole = bindings.getOperationBinding("getrole");
                                opRole.getParamsMap().put("role_id", CHEF_DEPT);
                                role = (String) opRole.execute();
                                System.out.println("role : " + role);
                                if (null != role && null != username) {
                                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                    try {
                                        AdministrationBean.revokeWlsRole2User(role, username);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding opUserRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                opUserRole.getParamsMap().put("user_id", id_user);
                                opUserRole.getParamsMap().put("role_id", new Long(CHEF_DEPT));
                                opUserRole.getParamsMap().put("utilisateur", getUtilisateur());
                                opUserRole.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                DCIteratorBinding userDept =
                                    (DCIteratorBinding) bindings.get("HistoriquesStructuresStructIterator");
                                Row crntDept = userDept.getCurrentRow();
                                if (null != crntDept) {
                                    try {
                                        OperationBinding opUserDept = bindings.getOperationBinding("createUserDet");
                                        opUserDept.getParamsMap().put("userId", id_user);
                                        opUserDept.getParamsMap()
                                            .put("deptId", crntDept.getAttribute("IdHistoriquesStructure"));
                                        opUserDept.getParamsMap().put("role", "RESPONSABLE");
                                        opUserDept.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserDept.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding opUserDept = bindings.getOperationBinding("createUserDet");
                                        opUserDept.getParamsMap().put("userId", pageScope.get("ancRespUId"));
                                        opUserDept.getParamsMap()
                                            .put("deptId", crntDept.getAttribute("IdHistoriquesStructure"));
                                        opUserDept.getParamsMap().put("role", "ACCES SIMPLE");
                                        opUserDept.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserDept.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding opusername = bindings.getOperationBinding("getusername");
                                opusername.getParamsMap().put("user_id", id_user);
                                username = (String) opusername.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding oprole = bindings.getOperationBinding("getrole");
                                oprole.getParamsMap().put("role_id", new Long(CHEF_DEPT));
                                role = (String) oprole.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (null != role && null != username) {
                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                try {
                                    AdministrationBean.assignWlsRole2User(role, username);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                            RichPopup popup = this.getPopChefDeptCreated();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                            this.getMatricule().resetValue();
                            OperationBinding opsearch = bindings.getOperationBinding("grhUserEtab");
                            opsearch.getParamsMap().put("mat", "0");
                            opsearch.getParamsMap().put("grh_code", "0");
                            opsearch.getParamsMap().put("log", "0");
                            opsearch.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("same responsable");
                    }
                } else {
                    RichPopup popup = this.getAdminEtabfailed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } else {
                RichPopup popup = this.getPopEnterEmail();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            OperationBinding opCommit = bindings.getOperationBinding("Commit");
            opCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPanCollect());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSearchDeptPanGrp());
        }
    }
}
