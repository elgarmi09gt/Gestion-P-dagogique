package view.bean.formations;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.PollEvent;

import view.beans.admin.AdministrationBean;

public class FormationsBean {
    private RichPanelCollection collectionFormations;
    private RichPanelCollection collectionFormationSpec;
    private RichPanelCollection collectionFormationOption;
    private RichPopup popFromNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popFormSpcNew;
    private RichPopup popFormOptNew;
    private RichPanelCollection collectionFormationAcces;
    private RichPopup popFormAccesNew;
    private RichPanelCollection collectionFromCondition;
    private RichPopup popFormCondAccesNew;
    private RichPanelCollection collectionFormLang;
    private RichPopup popFLgNew;
    private RichPopup popCondAc;
    private RichPanelCollection collectionFormOrg;
    private RichPopup popOrgEtude;
    private RichPanelCollection collectionFormInstitut;
    private RichPopup popFromInst;
    private RichPanelCollection collectionNiveauxForm;
    private RichPopup popNivForm;
    private RichPanelCollection collectionNivFormCohorte;
    private RichPanelCollection collectionFormParcours;
    private RichPopup popNivFormCoh;
    private RichPopup popNivFormParc;
    private RichPopup popInfoForm;
    private RichPopup popInfoNiv;
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionScope = adfCtx.getSessionScope();
    Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());
    private RichPopup popupFormEdit;
    private RichPopup popupEditNf;
    private RichPopup popEditNivFormCohorte;
    private RichPopup popEditNivFormPrcrs;
    private RichPopup popConfirmDeleteFormation;
    private RichPopup popFormSpecEdit;
    private RichPopup popFormOptEdit;
    private RichPopup popupConfResp;
    private RichPopup popupConfirmValidResp;
    private RichPanelGroupLayout formPanGrp;
    private RichPanelHeader formPanHdr;
    private RichPanelCollection formPanCol;
    private RichPanelCollection nivFormPanCol;
    private RichPanelHeader nivFormPanHdr;
    private RichPanelGroupLayout nivFormPanGrp;
    private RichPopup popupwait;
    private RichPopup popNewForm;
    private RichPanelCollection formationsPanCollect;
    private RichPopup popEditForm;
    private RichPopup popDeleteFormation;
    private RichPopup popNewNivForm;
    private RichPanelCollection nivFormPanCollect;
    private RichPanelHeader nivFormPanelHeder;
    private RichPopup popEditNivFormation;
    private RichPopup popNewNivFormCohorte;
    private RichPanelHeader nivFormCrtPanelHeder;
    private RichPanelCollection nivFormCrtPanCollect;
    private RichPopup popEditNivFormationCohorte;
    private RichPopup popNewNivFormPrcrs;
    private RichPopup popDefltPrcrsExist;
    private RichPopup popConfirmCreateDfltPrcrs;
    private RichPanelHeader nivFormPrcrsPanelHeder;
    private RichPanelCollection nivFormPrcrsPanCollect;
    private RichPopup popPrcrsOptExist;

    public FormationsBean() {
    }

    public void setCollectionFormations(RichPanelCollection collectionFormations) {
        this.collectionFormations = collectionFormations;
    }

    public RichPanelCollection getCollectionFormations() {
        return collectionFormations;
    }
    
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    
    public void onNewFormationAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsVO3Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNewForm();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onFormationsNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsVO1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFromNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setCollectionFormationSpec(RichPanelCollection collectionFormationSpec) {
        this.collectionFormationSpec = collectionFormationSpec;
    }

    public RichPanelCollection getCollectionFormationSpec() {
        return collectionFormationSpec;
    }

    public void setCollectionFormationOption(RichPanelCollection collectionFormationOption) {
        this.collectionFormationOption = collectionFormationOption;
    }

    public RichPanelCollection getCollectionFormationOption() {
        return collectionFormationOption;
    }

    public void onFormationsSpecNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsSpecialitesVO1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormSpc");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFormSpcNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onFormationOptNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree5");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsOptionsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormOpt");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFormOptNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupFormEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void setPopFromNew(RichPopup popFromNew) {
        this.popFromNew = popFromNew;
    }

    public RichPopup getPopFromNew() {
        return popFromNew;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFromNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }
    
    public void onDialogFormCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNewForm();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsVO3Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
        /*FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();*/
    }

    public void onDeleteFromation(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteForm");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
                this.getPopConfirmDeleteFormation().hide();
                return ;
            }
        }
    }
    
    public void onDeleteFormation(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteForm");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
                this.getPopDeleteFormation().hide();
                return ;
            }
        }
    }

    public void setPopFormSpcNew(RichPopup popFormSpcNew) {
        this.popFormSpcNew = popFormSpcNew;
    }

    public RichPopup getPopFormSpcNew() {
        return popFormSpcNew;
    }

    public void onDialogCancelSpc(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFormSpcNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsSpecialitesVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationSpec());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteFromSpec(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormSpc");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationSpec());
                return ;
            }
            
        }
    }

    public void onDialogCancelOpt(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFormOptNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationsOptionsIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow!=null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationOption());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteFormOption(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormOpt");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationOption());
                return ;
            }
            
        }
    }

    public void setPopFormOptNew(RichPopup popFormOptNew) {
        this.popFormOptNew = popFormOptNew;
    }

    public RichPopup getPopFormOptNew() {
        return popFormOptNew;
    }

    public void setCollectionFormationAcces(RichPanelCollection collectionFormationAcces) {
        this.collectionFormationAcces = collectionFormationAcces;
    }

    public RichPanelCollection getCollectionFormationAcces() {
        return collectionFormationAcces;
    }

    public void onFormationAccesNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree6");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationAccesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormAcces");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFormAccesNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onDialogCancelFormAcces(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFormAccesNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationAccesIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow!=null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationAcces());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteFormAcces(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormAcces");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationAcces());
                return ;
            }
            
        }
    }

    public void setPopFormAccesNew(RichPopup popFormAccesNew) {
        this.popFormAccesNew = popFormAccesNew;
    }

    public RichPopup getPopFormAccesNew() {
        return popFormAccesNew;
    }

    public void setCollectionFromCondition(RichPanelCollection collectionFromCondition) {
        this.collectionFromCondition = collectionFromCondition;
    }

    public RichPanelCollection getCollectionFromCondition() {
        return collectionFromCondition;
    }

    public void onFormationConditionNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree7");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormCondAccesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormCondAcces");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopCondAc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopFormCondAccesNew(RichPopup popFormCondAccesNew) {
        this.popFormCondAccesNew = popFormCondAccesNew;
    }

    public RichPopup getPopFormCondAccesNew() {
        return popFormCondAccesNew;
    }

    public void onDeleteFormCondAcces(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormCondAcces");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFromCondition());
                return ;
            }
            
        }
    }

    public void setCollectionFormLang(RichPanelCollection collectionFormLang) {
        this.collectionFormLang = collectionFormLang;
    }

    public RichPanelCollection getCollectionFormLang() {
        return collectionFormLang;
    }

    public void onFormLangueNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree8");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationLangueIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormLang");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFLgNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopFLgNew(RichPopup popFLgNew) {
        this.popFLgNew = popFLgNew;
    }

    public RichPopup getPopFLgNew() {
        return popFLgNew;
    }

    public void onDialogCancelFLang(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFLgNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationLangueIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow!=null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormLang());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteFLang(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormLang");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormLang());
                return ;
            }
            
        }
    }

    public void onDialogCanCondAc(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopCondAc();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormCondAccesIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFromCondition());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopCondAc(RichPopup popCondAc) {
        this.popCondAc = popCondAc;
    }

    public RichPopup getPopCondAc() {
        return popCondAc;
    }

    public void setCollectionFormOrg(RichPanelCollection collectionFormOrg) {
        this.collectionFormOrg = collectionFormOrg;
    }

    public RichPanelCollection getCollectionFormOrg() {
        return collectionFormOrg;
    }

    public void onFormOrgEtudeNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree11");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationOrganisatioEtudeIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertOrgEtude");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopOrgEtude();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopOrgEtude(RichPopup popOrgEtude) {
        this.popOrgEtude = popOrgEtude;
    }

    public RichPopup getPopOrgEtude() {
        return popOrgEtude;
    }

    public void onDialogCancelOrgEtude(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopOrgEtude();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationOrganisatioEtudeIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormOrg());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteOrgEtude(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteOrgEtude");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormOrg());
                return ;
            }
            
        }
    }

    public void setCollectionFormInstitut(RichPanelCollection collectionFormInstitut) {
        this.collectionFormInstitut = collectionFormInstitut;
    }

    public RichPanelCollection getCollectionFormInstitut() {
        return collectionFormInstitut;
    }

    public void onFormInstNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree12");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationInstitutsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFormInst");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopFromInst();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopFromInst(RichPopup popFromInst) {
        this.popFromInst = popFromInst;
    }

    public RichPopup getPopFromInst() {
        return popFromInst;
    }

    public void onDialogCancelInst(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopFromInst();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FormationInstitutsIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormInstitut());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteInstitut(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteFormInst");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormInstitut());
                return ;
            }
            
        }
    }

    public void setCollectionNiveauxForm(RichPanelCollection collectionNiveauxForm) {
        this.collectionNiveauxForm = collectionNiveauxForm;
    }

    public RichPanelCollection getCollectionNiveauxForm() {
        return collectionNiveauxForm;
    }

    public void onNiveauxFormNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertNivForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNivForm();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopNivForm(RichPopup popNivForm) {
        this.popNivForm = popNivForm;
    }

    public RichPopup getPopNivForm() {
        return popNivForm;
    }

    public void onDialogCancelNivForm(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNivForm();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationsIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionNiveauxForm());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteNivForm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteNivForm");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionNiveauxForm());
                return ;
            }
            
        }
    }

    public void setCollectionNivFormCohorte(RichPanelCollection collectionNivFormCohorte) {
        this.collectionNivFormCohorte = collectionNivFormCohorte;
    }

    public RichPanelCollection getCollectionNivFormCohorte() {
        return collectionNivFormCohorte;
    }

    public void onNivFormCohorteNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationCohortesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree2");
        AttributeBinding nivForm = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        if(oldCcurrentRow!=null){
            //IdNiveauFormation
            nivForm.setInputValue(oldCcurrentRow.getAttribute("IdNiveauFormation"));
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            
        }
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertNivFormCoh");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNivFormCoh();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setCollectionFormParcours(RichPanelCollection collectionFormParcours) {
        this.collectionFormParcours = collectionFormParcours;
    }

    public RichPanelCollection getCollectionFormParcours() {
        return collectionFormParcours;
    }

    public void onNivFormParcours(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationParcoursIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree3");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertNivFormParc");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }

        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNivFormParc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onDialogCancelNivFormCoh(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNivFormCoh();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationCohortesIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionNivFormCohorte());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteNivFromCoh(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteNivFormCoh");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionNivFormCohorte());
                return ;
            }
            
        }
    }

    public void setPopNivFormCoh(RichPopup popNivFormCoh) {
        this.popNivFormCoh = popNivFormCoh;
    }

    public RichPopup getPopNivFormCoh() {
        return popNivFormCoh;
    }

    public void setPopNivFormParc(RichPopup popNivFormParc) {
        this.popNivFormParc = popNivFormParc;
    }

    public RichPopup getPopNivFormParc() {
        return popNivFormParc;
    }

    public void onDialogCancelNivFormParc(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNivFormParc();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationParcoursIterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormParcours());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void onDeleteNivFormParc(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteNivFormParc");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
               OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormParcours());
                return ;
            }
            
        }
    }

    public void setPopInfoForm(RichPopup popInfoForm) {
        this.popInfoForm = popInfoForm;
    }

    public RichPopup getPopInfoForm() {
        return popInfoForm;
    }

    public void onClosePoll(PollEvent pollEvent) {
        // Add event code here...
        popInfoForm.hide();
    }

    public void onClosePollNiv(PollEvent pollEvent) {
        // Add event code here...
        popInfoNiv.hide();
    }

    public void setPopInfoNiv(RichPopup popInfoNiv) {
        this.popInfoNiv = popInfoNiv;
    }

    public RichPopup getPopInfoNiv() {
        return popInfoNiv;
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setPopupFormEdit(RichPopup popupFormEdit) {
        this.popupFormEdit = popupFormEdit;
    }

    public RichPopup getPopupFormEdit() {
        return popupFormEdit;
    }

   
    public void setPopupEditNf(RichPopup popupEditNf) {
        this.popupEditNf = popupEditNf;
    }

    public RichPopup getPopupEditNf() {
        return popupEditNf;
    }

    public void onDialogNewFormation(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            //AttributeBinding uticre= (AttributeBinding) getBindings().getControlBinding("UtiCree");
            //uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopFromNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void onConfirmDialogNewForm(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            //AttributeBinding uticre= (AttributeBinding) getBindings().getControlBinding("UtiCree");
            //uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNewForm().hide();
            OperationBinding opAllowAccess = bindings.getOperationBinding("AllowAccesFr");
            result = opAllowAccess.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
            
        }
    }
    
    public void onDialogEditFormation(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupFormEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }
    public void onConfirmDialogEditForm(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding opCommit = bindings.getOperationBinding("Commit");
            Object result = opCommit.execute();
            if (!opCommit.getErrors().isEmpty()) {
                //handle errors if any
                System.out.println(opCommit.getErrors().get(0));
                return;
            }
            this.getPopEditForm().hide();
            /*OperationBinding opAllowAccess = bindings.getOperationBinding("AllowAccesFr");
            result = opAllowAccess.execute();*/
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
        }
    }

     public void onDialogNewNF(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            //AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree8");
            //uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNivForm().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }
    public void onDialogEditNF(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie1");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditNf().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void onDialogNewParcous(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            //AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree9");
            //uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNivFormParc().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void onDialogEditParcours(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie2");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopEditNivFormPrcrs().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void onDialogNewCohorte(DialogEvent dialogEvent) {
        // Add event code here...UtiCree10
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            //AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree10");
            //uticre.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNivFormCoh().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void onDialogEditNivFormCohorte(DialogEvent dialogEvent) {
        // Add event code here...UtiModifie9
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie3");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopEditNivFormCohorte().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }

    public void setPopEditNivFormCohorte(RichPopup popEditNivFormCohorte) {
        this.popEditNivFormCohorte = popEditNivFormCohorte;
    }

    public RichPopup getPopEditNivFormCohorte() {
        return popEditNivFormCohorte;
    }

    public void setPopEditNivFormPrcrs(RichPopup popEditNivFormPrcrs) {
        this.popEditNivFormPrcrs = popEditNivFormPrcrs;
    }

    public RichPopup getPopEditNivFormPrcrs() {
        return popEditNivFormPrcrs;
    }
    
    public void onDialogEditSpecialite(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie4");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopFormSpecEdit().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationSpec());
        }
    }

    public void onDialogEditOption(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie5");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopEditNivFormCohorte().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormationOption());
        }
    }

    public void onDialogEditAccesFormation(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie3");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopEditNivFormCohorte().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCollectionFormations());
        }
    }


    public void setPopConfirmDeleteFormation(RichPopup popConfirmDeleteFormation) {
        this.popConfirmDeleteFormation = popConfirmDeleteFormation;
    }

    public RichPopup getPopConfirmDeleteFormation() {
        return popConfirmDeleteFormation;
    }

    public void setPopFormSpecEdit(RichPopup popFormSpecEdit) {
        this.popFormSpecEdit = popFormSpecEdit;
    }

    public RichPopup getPopFormSpecEdit() {
        return popFormSpecEdit;
    }

    public void setPopFormOptEdit(RichPopup popFormOptEdit) {
        this.popFormOptEdit = popFormOptEdit;
    }

    public RichPopup getPopFormOptEdit() {
        return popFormOptEdit;
    }

    @SuppressWarnings("unchecked")
    public void OnAddResponsableClicked(ActionEvent actionEvent) {
        // Add event code here... FormationsVO1Iterator
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding fr_iter = (DCIteratorBinding) bindings.get("FormationsVO1Iterator");
        Row curent  = fr_iter.getCurrentRow();
        sessionScope.put("is_fr_param", true);
        Long fr_id = Long.parseLong(curent.getAttribute("IdFormation").toString());
        sessionScope.put("fr_", curent.getAttribute("LibelleLong"));
        //Formation to change responsable 
        sessionScope.put("fr_resp", fr_id);
        OperationBinding opresp = bindings.getOperationBinding("getResponsableFr");
        opresp.getParamsMap().put("fr_id", fr_id);
        Long resp_id = (Long) opresp.execute();
        //utilisateur formation
        sessionScope.put("resp_fr", resp_id);
        RichPopup pop = this.getPopupConfResp();
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        pop.show(ph);
    }

    public void setPopupConfResp(RichPopup popupConfResp) {
        this.popupConfResp = popupConfResp;
    }

    public RichPopup getPopupConfResp() {
        return popupConfResp;
    }

    public void setPopupConfirmValidResp(RichPopup popupConfirmValidResp) {
        this.popupConfirmValidResp = popupConfirmValidResp;
    }

    public RichPopup getPopupConfirmValidResp() {
        return popupConfirmValidResp;
    }

    @SuppressWarnings("unchecked")
    public void OnSaveResponsableCliked(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            this.getPopupConfResp().hide();
            BindingContainer bindings = this.getBindings();
            /*DCIteratorBinding user_iter = (DCIteratorBinding) bindings.get("UtilisateurEtabROVOIterator");
            Row curent_user  = user_iter.getCurrentRow();*/
            if (sessionScope.get("is_fr_param").equals(false)) {
                Long niv_fr_id = Long.parseLong(sessionScope.get("nv_fr_resp").toString());
                Long old_user_niv_form = Long.parseLong(sessionScope.get("resp_niv_fr").toString());
                String role = null;
                try {
                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                    oprole.getParamsMap().put("role_id", new Long(62));
                    role = (String) oprole.execute();
                    if (0 != old_user_niv_form) {
                        try {
                            //recuperer l'id user
                            /*System.out.println("Getting old user responsabel of old_user_niv_form : " +
                                               old_user_niv_form);*/
                            OperationBinding opolduserResp = bindings.getOperationBinding("getUserResponsableNivFr");
                            opolduserResp.getParamsMap().put("u_nfr_id", old_user_niv_form);
                            Long old_resp = (Long) opolduserResp.execute();
                            //Opération à faire pour l'ancien responsable
                            if (0 != old_resp) {
                                try {
                                    //updater responsable to simple
                                    OperationBinding opoldResp =
                                        bindings.getOperationBinding("createOrUpdateUserNivForm1");
                                    opoldResp.getParamsMap().put("user_", old_resp);
                                    opoldResp.getParamsMap().put("role", "ACCES SIMPLE");
                                    opoldResp.getParamsMap().put("niv_form_", niv_fr_id);
                                    opoldResp.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    opoldResp.execute();
                                    //System.out.println("Old responsable changed to simple !");
                                    //Verifier si l'user est tjrs responsable d'une formation
                                    OperationBinding opisuserresp =
                                        bindings.getOperationBinding("isUserRespNivFormation");
                                    opisuserresp.getParamsMap().put("user_id", old_resp);
                                    opisuserresp.execute();
                                    DCIteratorBinding iterResp =
                                        (DCIteratorBinding) bindings.get("isUserRespNivFormationIterator");
                                    if (0 == iterResp.getEstimatedRowCount()) {
                                        try {
                                           OperationBinding opdeluserRole =
                                                bindings.getOperationBinding("DeleteUserRole");
                                            opdeluserRole.getParamsMap().put("user_id", old_resp);
                                            opdeluserRole.getParamsMap().put("role_id", new Long(62));
                                            opdeluserRole.execute();
                                            //revoke weblogic role responsable formation to user
                                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                                            opusername.getParamsMap().put("user_id", old_resp);
                                            String username = (String) opusername.execute();
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
                                    } 
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                            //Opération pour le nouveau responsable
                            DCIteratorBinding iteruser =
                                (DCIteratorBinding) bindings.get("UtilisateurEtabROVOIterator");
                            Row currentuser = iteruser.getCurrentRow();
                           if (currentuser != null) {
                                Long user_id = Long.parseLong(currentuser.getAttribute("IdUtilisateur").toString());
                                try {
                                    //Ajouter à la table utilisateur formation ou modifier une ligne pour l'utilisateur
                                    OperationBinding opResp = bindings.getOperationBinding("createOrUpdateUserNivForm");
                                    opResp.getParamsMap().put("user_", user_id);
                                    opResp.getParamsMap().put("role", "RESPONSABLE NIVEAU");
                                    opResp.getParamsMap().put("niv_form_", niv_fr_id);
                                    opResp.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    Long new_resp = (Long) opResp.execute();
                                    if (0 != new_resp) {
                                        //FormationsVO1Iterator
                                        DCIteratorBinding iterfr =
                                            (DCIteratorBinding) bindings.get("FormationsVO1Iterator");
                                        Row currentfr = iterfr.getCurrentRow();
                                       try{
                                            OperationBinding op_fr = bindings.getOperationBinding("createOrUpdateUserForm");
                                            op_fr.getParamsMap().put("user_", user_id);
                                            op_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                            op_fr.getParamsMap().put("form_", currentfr.getAttribute("IdFormation").toString());
                                            op_fr.getParamsMap().put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                            op_fr.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        } 
                                        //Creer le role responsable formation pour cet utilisateur
                                        OperationBinding allowuserRle =
                                            bindings.getOperationBinding("CreateOrUpdateUserRole");
                                        allowuserRle.getParamsMap().put("user_id", user_id);
                                        allowuserRle.getParamsMap()
                                            .put("role_id", new Long(62)); // Responsable niveau formation
                                        allowuserRle.getParamsMap()
                                            .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                        allowuserRle.execute();
                                        //assign weblogic role
                                        try {
                                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                                            opusername.getParamsMap().put("user_id", new Long(user_id));
                                            String username = (String) opusername.execute();
                                            OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                            opmat.getParamsMap().put("utilisateur", new Long(user_id));
                                            String matricule = (String) opmat.execute();
                                            //matricule = "passer123*";
                                            if (null != role && null != username && null != matricule) {
                                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                try {
                                                    AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                                    AdministrationBean.assignWlsRole2User(role, username);
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            } 
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                } catch (Exception e) {
                    System.out.println(e);
                }
                sessionScope.remove("is_fr_param");
                sessionScope.remove("resp_niv_fr");
                sessionScope.remove("niv_fr_");
                sessionScope.remove("nv_fr_resp");
                DCIteratorBinding iterBind= (DCIteratorBinding)bindings.get("NiveauxFormationsIterator");
                ViewObject vo=iterBind.getViewObject();  
                vo.executeQuery();
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanCol());
            } 
            else {
                Long fr_id = Long.parseLong(sessionScope.get("fr_resp").toString());
                Long old_user_form = Long.parseLong(sessionScope.get("resp_fr").toString());
                String role = null;
                try {
                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                    oprole.getParamsMap().put("role_id", new Long(61));
                    role = (String) oprole.execute();
                    //OldeResponsable exist
                    if (0 != old_user_form) {
                        try {
                            //recuperer l'id user
                            OperationBinding opolduserResp = bindings.getOperationBinding("getUserResponsableFr");
                            opolduserResp.getParamsMap().put("u_fr_id", old_user_form);
                            Long old_resp = (Long) opolduserResp.execute();
                            if (0 != old_resp) {
                                try {
                                    //updater responsable to simple
                                    OperationBinding opoldResp =
                                        bindings.getOperationBinding("createOrUpdateUserForm1");
                                    opoldResp.getParamsMap().put("user_", old_resp);
                                    opoldResp.getParamsMap().put("role", "ACCES SIMPLE");
                                    opoldResp.getParamsMap().put("form_", fr_id);
                                    opoldResp.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    opoldResp.execute();
                                    //System.out.println("Old responsable changed to simple !");
                                    //Verifier si l'user est tjrs responsable d'une formation

                                    OperationBinding opisuserresp = bindings.getOperationBinding("isUserResponsable");
                                    opisuserresp.getParamsMap().put("user_id", old_resp);
                                    opisuserresp.execute();
                                    DCIteratorBinding iterResp =
                                        (DCIteratorBinding) bindings.get("isUserResponsableIterator");
                                    if (0 == iterResp.getEstimatedRowCount()) {
                                        try {
                                            //delete role responsable formation to user
                                            /*System.out.println("User " + old_resp +
                                                               " has no responsable role now : deleting role");*/
                                            OperationBinding opdeluserRole =
                                                bindings.getOperationBinding("DeleteUserRole");
                                            opdeluserRole.getParamsMap().put("user_id", old_resp);
                                            opdeluserRole.getParamsMap().put("role_id", new Long(61));
                                            opdeluserRole.execute();
                                            //System.out.println("role deleted for user " + old_resp);
                                            //revoke weblogic role responsable formation to user
                                            //System.out.println("revoking role to user in weblogic...");
                                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                                            opusername.getParamsMap().put("user_id", old_resp);
                                            String username = (String) opusername.execute();
                                            if (null != role && null != username) {
                                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                try {
                                                    AdministrationBean.revokeWlsRole2User(role, username);
                                                    //System.out.println("Role revoked success !");
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            
                                        } 
                                        catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } 
                                    
                                } 
                                catch (Exception e) {
                                    System.out.println(e);
                                }

                            }
                        } 
                        catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    
                    DCIteratorBinding iteruser = (DCIteratorBinding) bindings.get("UtilisateurEtabROVOIterator");
                    Row currentuser = iteruser.getCurrentRow();
                    if (currentuser != null) {
                        Long user_id = Long.parseLong(currentuser.getAttribute("IdUtilisateur").toString());
                        try {
                            //Ajouter à la table utilisateur formation ou modifier une ligne pour l'utilisateur
                            OperationBinding opResp = bindings.getOperationBinding("createOrUpdateUserForm");
                            opResp.getParamsMap().put("user_", user_id);
                            opResp.getParamsMap().put("role", "RESPONSABLE FORMATION");
                            opResp.getParamsMap().put("form_", fr_id);
                            opResp.getParamsMap().put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                            Long new_resp = (Long) opResp.execute();
                            if (0 != new_resp) {
                                //donner les acces niveau, ue et ec
                                //Creer le role responsable formation pour cet utilisateur
                                try {
                                    OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                    allowuserRle.getParamsMap().put("user_id", user_id);
                                    allowuserRle.getParamsMap().put("role_id", new Long(61)); // Responsable formation
                                    allowuserRle.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    allowuserRle.execute();
                                    try {
                                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                                        opusername.getParamsMap().put("user_id", new Long(user_id));
                                        String username = (String) opusername.execute();
                                        
                                        OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                        opmat.getParamsMap().put("utilisateur", new Long(user_id));
                                        String matricule = (String) opmat.execute();
                                        //matricule = "passer123*";
                                        if (null != role && null != username && null != matricule) {
                                            role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                            try {
                                                AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                                AdministrationBean.assignWlsRole2User(role, username);
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        } 
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                sessionScope.remove("resp_fr");
                sessionScope.remove("is_fr_param");
                sessionScope.remove("fr_");
                sessionScope.remove("fr_resp");
                //DCIteratorBinding iterBind= (DCIteratorBinding)bindings.get("NiveauxFormationsIterator");
                DCIteratorBinding iterBind= (DCIteratorBinding)bindings.get("FormationsVO1Iterator");
                ViewObject vo=iterBind.getViewObject();
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormPanCol());
            }
        }
    }

    public void setFormPanGrp(RichPanelGroupLayout formPanGrp) {
        this.formPanGrp = formPanGrp;
    }

    public RichPanelGroupLayout getFormPanGrp() {
        return formPanGrp;
    }

    public void setFormPanHdr(RichPanelHeader formPanHdr) {
        this.formPanHdr = formPanHdr;
    }

    public RichPanelHeader getFormPanHdr() {
        return formPanHdr;
    }

    public void setFormPanCol(RichPanelCollection formPanCol) {
        this.formPanCol = formPanCol;
    }

    public RichPanelCollection getFormPanCol() {
        return formPanCol;
    }

    @SuppressWarnings("unchecked")
    public void OnEditResponsableNivClicked(ActionEvent actionEvent) {
        // Add event code here... FormationsVO1Iterator
        /*RichPopup popw = this.getPopupwait();
        RichPopup.PopupHints phw = new RichPopup.PopupHints();
        popw.show(phw);*/
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding fr_iter = (DCIteratorBinding) bindings.get("NiveauxFormationsIterator");
        Row curent  = fr_iter.getCurrentRow();
        Long niv_fr_id = Long.parseLong(curent.getAttribute("IdNiveauFormation").toString());
        sessionScope.put("niv_fr_",curent.getAttribute("LibelleLong"));
        //Formation to change responsable
        sessionScope.put("nv_fr_resp", niv_fr_id);
        OperationBinding opresp = bindings.getOperationBinding("getResponsableNvFr");
        opresp.getParamsMap().put("niv_fr_id", niv_fr_id);
        Long resp_id = (Long) opresp.execute();
        //utilisateur formation
        sessionScope.put("resp_niv_fr", resp_id);
        sessionScope.put("is_fr_param", false);
        /*this.getPopupwait().cancel();
        this.getPopupwait().hide();*/
        RichPopup pop = this.getPopupConfResp();
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        pop.show(ph);
    }

    public void setNivFormPanCol(RichPanelCollection nivFormPanCol) {
        this.nivFormPanCol = nivFormPanCol;
    }

    public RichPanelCollection getNivFormPanCol() {
        return nivFormPanCol;
    }

    public void setNivFormPanHdr(RichPanelHeader nivFormPanHdr) {
        this.nivFormPanHdr = nivFormPanHdr;
    }

    public RichPanelHeader getNivFormPanHdr() {
        return nivFormPanHdr;
    }

    public void setNivFormPanGrp(RichPanelGroupLayout nivFormPanGrp) {
        this.nivFormPanGrp = nivFormPanGrp;
    }

    public RichPanelGroupLayout getNivFormPanGrp() {
        return nivFormPanGrp;
    }

    public void onPopupCancelClicked(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        if(sessionScope.get("is_fr_param").equals(false)) {
            sessionScope.remove("is_fr_param");
            sessionScope.remove("resp_niv_fr");
            sessionScope.remove("niv_fr_");
            sessionScope.remove("nv_fr_resp");
        }else {
            sessionScope.remove("resp_fr");
            sessionScope.remove("is_fr_param");
            sessionScope.remove("fr_");
            sessionScope.remove("fr_resp");
        }
    }

    public void setPopupwait(RichPopup popupwait) {
        this.popupwait = popupwait;
    }

    public RichPopup getPopupwait() {
        return popupwait;
    }

    public void setPopNewForm(RichPopup popNewForm) {
        this.popNewForm = popNewForm;
    }

    public RichPopup getPopNewForm() {
        return popNewForm;
    }

    public void setFormationsPanCollect(RichPanelCollection formationsPanCollect) {
        this.formationsPanCollect = formationsPanCollect;
    }

    public RichPanelCollection getFormationsPanCollect() {
        return formationsPanCollect;
    }

    public void onCancelDialogEditForm(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopEditForm();
        popup.hide();
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        Object result = operationBinding.execute();
    }

    public void setPopEditForm(RichPopup popEditForm) {
        this.popEditForm = popEditForm;
    }

    public RichPopup getPopEditForm() {
        return popEditForm;
    }

    public void setPopDeleteFormation(RichPopup popDeleteFormation) {
        this.popDeleteFormation = popDeleteFormation;
    }

    public RichPopup getPopDeleteFormation() {
        return popDeleteFormation;
    }

    public void onNewNivFormationAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationsVO2Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertNivForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNewNivForm();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopNewNivForm(RichPopup popNewNivForm) {
        this.popNewNivForm = popNewNivForm;
    }

    public RichPopup getPopNewNivForm() {
        return popNewNivForm;
    }

    public void onConfirmDialogNewNivForm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
           OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNewNivForm().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanelHeder());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanCollect());
        }
    }

    public void setNivFormPanCollect(RichPanelCollection nivFormPanCollect) {
        this.nivFormPanCollect = nivFormPanCollect;
    }

    public RichPanelCollection getNivFormPanCollect() {
        return nivFormPanCollect;
    }

    public void setNivFormPanelHeder(RichPanelHeader nivFormPanelHeder) {
        this.nivFormPanelHeder = nivFormPanelHeder;
    }

    public RichPanelHeader getNivFormPanelHeder() {
        return nivFormPanelHeder;
    }

    public void onDialogNivFormCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNewNivForm();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauxFormationsVO2Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanelHeder());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPanCollect());
        /*FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();*/
    }

    public void setPopEditNivFormation(RichPopup popEditNivFormation) {
        this.popEditNivFormation = popEditNivFormation;
    }

    public RichPopup getPopEditNivFormation() {
        return popEditNivFormation;
    }

    public void onConfirmDialogEditNivForm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie1");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding opCommit = bindings.getOperationBinding("Commit");
            Object result = opCommit.execute();
            if (!opCommit.getErrors().isEmpty()) {
                //handle errors if any
                System.out.println(opCommit.getErrors().get(0));
                return;
            }
            this.getPopEditNivFormation().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
        }
    }

    public void onCancelDialogEdiNivForm(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopEditNivFormation();
        popup.hide();
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        Object result = operationBinding.execute();
    }

    public void onNewNivFormationCrtAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationCohortesVO2Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree2");
        //AttributeBinding nivForm = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        if(oldCcurrentRow!=null){
            //IdNiveauFormation
            //nivForm.setInputValue(oldCcurrentRow.getAttribute("IdNiveauFormation"));
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        OperationBinding operationBinding = bindings.getOperationBinding("CreateNivFormCohorte");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNewNivFormCohorte();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onConfirmDialogNewNivFormCohorte(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
           OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNewNivFormCohorte().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanelHeder());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanCollect());
        }
    }

    public void setPopNewNivFormCohorte(RichPopup popNewNivFormCohorte) {
        this.popNewNivFormCohorte = popNewNivFormCohorte;
    }

    public RichPopup getPopNewNivFormCohorte() {
        return popNewNivFormCohorte;
    }

    public void setNivFormCrtPanelHeder(RichPanelHeader nivFormCrtPanelHeder) {
        this.nivFormCrtPanelHeder = nivFormCrtPanelHeder;
    }

    public RichPanelHeader getNivFormCrtPanelHeder() {
        return nivFormCrtPanelHeder;
    }

    public void setNivFormCrtPanCollect(RichPanelCollection nivFormCrtPanCollect) {
        this.nivFormCrtPanCollect = nivFormCrtPanCollect;
    }

    public RichPanelCollection getNivFormCrtPanCollect() {
        return nivFormCrtPanCollect;
    }

    public void onDialogNivFormCrtCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopNewNivFormCohorte();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationCohortesVO2Iterator");
        Row currentRow = dciter.getCurrentRow();
        if(currentRow != null)
            dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanelHeder());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanCollect());
        /*FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();*/
    }

    public void setPopEditNivFormationCohorte(RichPopup popEditNivFormationCohorte) {
        this.popEditNivFormationCohorte = popEditNivFormationCohorte;
    }

    public RichPopup getPopEditNivFormationCohorte() {
        return popEditNivFormationCohorte;
    }

    public void onConfirmDialogEditNivFormCohorte(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = this.getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie3");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopEditNivFormationCohorte().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanelHeder());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormCrtPanCollect());
        }
    }

    public void onCancelDialogEdiNivFormCrt(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopEditNivFormationCohorte();
        popup.hide();
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        Object result = operationBinding.execute();
    }

    public void onNewNivFormPrcrsAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NivFormSpecialiteOptionVOIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree3");
        //AttributeBinding nivForm = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        if(oldCcurrentRow!=null){
            //IdNiveauFormation
            //nivForm.setInputValue(oldCcurrentRow.getAttribute("IdNiveauFormation"));
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        OperationBinding opcrete = bindings.getOperationBinding("CreateInsert");
        Object result = opcrete.execute();
        if (!opcrete.getErrors().isEmpty()) {
            return ;
        }
        //System.out.println("CreateInsert");
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNewNivFormPrcrs();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopNewNivFormPrcrs(RichPopup popNewNivFormPrcrs) {
        this.popNewNivFormPrcrs = popNewNivFormPrcrs;
    }

    public RichPopup getPopNewNivFormPrcrs() {
        return popNewNivFormPrcrs;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmAddNivFormOption(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = this.getBindings();
            AttributeBinding op = (AttributeBinding) getBindings().getControlBinding("Codeoptionscol");
            AttributeBinding idNivForm = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
            AttributeBinding idNivFormCrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte");
            System.out.println(idNivFormCrt.getInputValue().toString());
            if(null == op.getInputValue()){
                try{
                    OperationBinding opNivFormOp = bindings.getOperationBinding("IsPrcrsExiste");
                    opNivFormOp.getParamsMap().put("nivFromCrtId", Long.parseLong(idNivFormCrt.getInputValue().toString()));
                    opNivFormOp.execute();
                    DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ParcoursOpNullIterator");
                    //System.out.println("size : "+dciter.getEstimatedRowCount());
                    if(0 != dciter.getEstimatedRowCount()){
                       
                        DCIteratorBinding dciterPrcrs = (DCIteratorBinding) bindings.get("NivFormSpecialiteOptionVOIterator");
                        Row currentPrcrs = dciterPrcrs.getCurrentRow();
                        if(currentPrcrs != null)
                            dciterPrcrs.removeCurrentRow();
                        ADFContext adfCtx = ADFContext.getCurrent();
                        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
                        if(oldCurrentRowKey != null){
                            dciterPrcrs.setCurrentRowWithKey(oldCurrentRowKey);
                        }
                        try{
                            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
                            Object result = operationBinding.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        RichPopup popup = this.getPopDefltPrcrsExist();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }else{
                        RichPopup popup = this.getPopConfirmCreateDfltPrcrs();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
        
                }catch(Exception e){
                    System.out.println(e);
                }
            }else{
                //verifier si le niveau option existe
                OperationBinding opNivFormOp = bindings.getOperationBinding("IsNivFormOptExiste");
                opNivFormOp.getParamsMap().put("idNivForm", Long.parseLong(idNivForm.getInputValue().toString()));
                opNivFormOp.getParamsMap().put("codeOpt", op.getInputValue().toString());
                opNivFormOp.execute();
                DCIteratorBinding iterNivOpt = (DCIteratorBinding) bindings.get("NivFormOptionsIterator");
                if(0 != iterNivOpt.getEstimatedRowCount()){
                    //supprimer la ligne creer au depart
                    DCIteratorBinding dciterPrcrs = (DCIteratorBinding) getBindings().get("NivFormSpecialiteOptionVOIterator");
                    Row currentPrcrs = dciterPrcrs.getCurrentRow();
                    if(currentPrcrs != null)
                        dciterPrcrs.removeCurrentRow();
                    ADFContext adfCtx = ADFContext.getCurrent();
                    String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
                    if(oldCurrentRowKey != null){
                        dciterPrcrs.setCurrentRowWithKey(oldCurrentRowKey);
                    }
                    //verifier si le parcours existe
                    OperationBinding opNivFormPrcrs = bindings.getOperationBinding("IsNivFormPrcrsExiste");
                    opNivFormPrcrs.getParamsMap().put("nivFromCrtId", Long.parseLong(idNivFormCrt.getInputValue().toString()));
                    opNivFormPrcrs.getParamsMap().put("nivFromOptId", Long.parseLong(iterNivOpt.getCurrentRow().getAttribute("IdNivFormSpecOption").toString()));
                    opNivFormPrcrs.execute();
                    DCIteratorBinding iterNivPrcrs = (DCIteratorBinding) bindings.get("NivFormPrcrsIterator");
                    //System.out.println("size : "+dciter.getEstimatedRowCount());
                    if(0 != iterNivPrcrs.getEstimatedRowCount()){
                       
                        RichPopup popup = this.getPopPrcrsOptExist();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    //parcours n'existe pas creer le !!!
                    else{
                        //creer le prcrs
                        try{
                            OperationBinding operationBinding = getBindings().getOperationBinding("CreatePrcrsNullOpt");
                            Object result = operationBinding.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        AttributeBinding nivcrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte1");
                        nivcrt.setInputValue(Long.parseLong(idNivFormCrt.getInputValue().toString()));
                        AttributeBinding nivopt = (AttributeBinding) getBindings().getControlBinding("IdNivformOption");
                        nivopt.setInputValue(Long.parseLong(iterNivOpt.getCurrentRow().getAttribute("IdNivFormSpecOption").toString()));
                        System.out.println("nivopt : "+nivopt.getInputValue().toString());
                        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
                        uticre.setInputValue(getUtilisateur());
                    }
                    try{
                        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                        Object result = operationBinding.execute();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
                //nivformopt n'existe pas : creation
                else{
                    AttributeBinding nivForm = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation1");
                    nivForm.setInputValue(Long.parseLong(idNivForm.getInputValue().toString()));
                    System.out.println("nivForm : "+nivForm.getInputValue().toString());
                    try{
                        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                        Object result = operationBinding.execute();
                    }catch(Exception e){
                        System.out.println(e);
                    } 
                    //creer le parcours
                    OperationBinding opNivFormOpExist = bindings.getOperationBinding("IsNivFormOptExiste");
                    opNivFormOpExist.getParamsMap().put("idNivForm", Long.parseLong(idNivForm.getInputValue().toString()));
                    opNivFormOpExist.getParamsMap().put("codeOpt", op.getInputValue().toString());
                    opNivFormOpExist.execute();
                    DCIteratorBinding iterNivOptExist = (DCIteratorBinding) bindings.get("NivFormOptionsIterator");
                    if(0 != iterNivOptExist.getEstimatedRowCount()){
                        try{
                            OperationBinding operationBinding = getBindings().getOperationBinding("CreatePrcrsNullOpt");
                            Object result = operationBinding.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        AttributeBinding nivcrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte1");
                        nivcrt.setInputValue(Long.parseLong(idNivFormCrt.getInputValue().toString()));
                        AttributeBinding nivopt = (AttributeBinding) getBindings().getControlBinding("IdNivformOption");
                        nivopt.setInputValue(Long.parseLong(iterNivOpt.getCurrentRow().getAttribute("IdNivFormSpecOption").toString()));
                        System.out.println("nivoptNew : "+nivopt.getInputValue().toString());
                        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
                        uticre.setInputValue(getUtilisateur());
                        try{
                            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                            Object result = operationBinding.execute();
                        }catch(Exception e){
                            System.out.println(e);
                        } 
                    }
                }
            }
            this.getPopNewNivFormPrcrs().hide();
            DCIteratorBinding iterBind= (DCIteratorBinding)bindings.get("NiveauxFormationParcoursVO2Query");
            ViewObject vo=iterBind.getViewObject();  
            vo.executeQuery();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPrcrsPanelHeder());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPrcrsPanCollect());
        }
    }

    public void setPopDefltPrcrsExist(RichPopup popDefltPrcrsExist) {
        this.popDefltPrcrsExist = popDefltPrcrsExist;
    }

    public RichPopup getPopDefltPrcrsExist() {
        return popDefltPrcrsExist;
    }

    public void setPopConfirmCreateDfltPrcrs(RichPopup popConfirmCreateDfltPrcrs) {
        this.popConfirmCreateDfltPrcrs = popConfirmCreateDfltPrcrs;
    }

    public RichPopup getPopConfirmCreateDfltPrcrs() {
        return popConfirmCreateDfltPrcrs;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void onConfirmCreatePrcrsNullOpt(DialogEvent dialogEvent) {
        // Add event code here...            
        //System.out.println("##.......##");
        DCIteratorBinding dciterPrcrs = (DCIteratorBinding) getBindings().get("NivFormSpecialiteOptionVOIterator");
        Row currentPrcrs = dciterPrcrs.getCurrentRow();
        if(currentPrcrs != null)
            dciterPrcrs.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciterPrcrs.setCurrentRowWithKey(oldCurrentRowKey);
        }
        //System.out.println("##.......##");
        AttributeBinding idNivFormCrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte");
        //System.out.println("idNivFormCrt : "+idNivFormCrt.getInputValue().toString());
        /*AttributeBinding nivcrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte1");
        System.out.println("nivcrt : "+nivcrt.getInputValue().toString());
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
        nivcrt.setInputValue(idNivFormCrt.getInputValue());
        System.out.println("niv crt : "+nivcrt.getInputValue().toString());*/
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
             /*try{
                OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
                Object result = operationBinding.execute();
            }catch(Exception e){
                System.out.println(e);
            }*/
            try{
                OperationBinding operationBinding = getBindings().getOperationBinding("CreatePrcrsNullOpt");
                Object result = operationBinding.execute();
            }catch(Exception e){
                System.out.println(e);
            }
            AttributeBinding nivcrt = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationCohorte1");
            nivcrt.setInputValue(Long.parseLong(idNivFormCrt.getInputValue().toString()));
            //System.out.println("nivcrt : "+nivcrt.getInputValue().toString());
            AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
            uticre.setInputValue(getUtilisateur());
            try{
                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                Object result = operationBinding.execute();
            }catch(Exception e){
                System.out.println(e);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPrcrsPanelHeder());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormPrcrsPanCollect());
        }
    }

    public void setNivFormPrcrsPanelHeder(RichPanelHeader nivFormPrcrsPanelHeder) {
        this.nivFormPrcrsPanelHeder = nivFormPrcrsPanelHeder;
    }

    public RichPanelHeader getNivFormPrcrsPanelHeder() {
        return nivFormPrcrsPanelHeder;
    }

    public void setNivFormPrcrsPanCollect(RichPanelCollection nivFormPrcrsPanCollect) {
        this.nivFormPrcrsPanCollect = nivFormPrcrsPanCollect;
    }

    public RichPanelCollection getNivFormPrcrsPanCollect() {
        return nivFormPrcrsPanCollect;
    }

    public void setPopPrcrsOptExist(RichPopup popPrcrsOptExist) {
        this.popPrcrsOptExist = popPrcrsOptExist;
    }

    public RichPopup getPopPrcrsOptExist() {
        return popPrcrsOptExist;
    }
}
