package view.beans.evaluation.parametragesaisieec;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import view.beans.jsfutils.JSFUtils;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.jbo.Row;

public class ParametreSaisieBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    //private Long calendrier = Long.parseLong(sessionScope.get("id_calendrier").toString());
    private RichPopup popConfExist;
    private RichPopup popConfTerExist;
    private RichPopup popModCntrlUsed;
    private RichButton commitBtn;
    private RichPanelHeader panHderMdeCntrlEc;
    private RichPopup popModeSaisieUsed;
    private RichPopup popupConfModeSaisieExist;
    private RichPopup popupSaisieModeCntrlTerExist;
    private RichPopup popupSaisieclosed;

    public ParametreSaisieBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String OnNewClicked() {
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree");
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        uticre.setInputValue(getUtilisateur());
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String OnCommitClicked() {
        BindingContainer bindings = getBindings();
        AttributeBinding fil_ec = (AttributeBinding) bindings.getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding type_cntrl = (AttributeBinding) bindings.getControlBinding("IdTypeControle");
        AttributeBinding mde_cntrl = (AttributeBinding) bindings.getControlBinding("IdModeControle");
        AttributeBinding prcr_mq_an = (AttributeBinding) bindings.getControlBinding("IdParcoursMaquetAnnee");

        OperationBinding is_param_exist = bindings.getOperationBinding("IsParamModeCntrlExist");
        is_param_exist.getParamsMap().put("fil_ec_id", Integer.parseInt(fil_ec.toString()));
        is_param_exist.getParamsMap().put("mde_cntrl_id", Integer.parseInt(mde_cntrl.toString()));
        is_param_exist.getParamsMap().put("type_cntrl_id", Integer.parseInt(type_cntrl.toString()));
        is_param_exist.getParamsMap().put("prcrs_maq_an_id", Integer.parseInt(prcr_mq_an.toString()));
        is_param_exist.execute();

        DCIteratorBinding iterExist = (DCIteratorBinding) bindings.get("IsParamModeCntrlExistROVOIterator");
        if (iterExist.getEstimatedRowCount() > 0) {
            //Rollback and popup param exist
            /*OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();*/
            RichPopup popup = this.getPopConfExist();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            if (Integer.parseInt(type_cntrl.toString()) != 1) {
                OperationBinding is_param_ter_exist = bindings.getOperationBinding("IsParamModeCntrlTerExist");
                is_param_ter_exist.getParamsMap().put("fil_ec_id", Integer.parseInt(fil_ec.toString()));
                is_param_ter_exist.getParamsMap().put("type_cntrl_id", Integer.parseInt(type_cntrl.toString()));
                is_param_ter_exist.getParamsMap().put("prcrs_maq_an_id", Integer.parseInt(prcr_mq_an.toString()));
                is_param_ter_exist.execute();

                DCIteratorBinding iterTerExist =
                    (DCIteratorBinding) bindings.get("IsParamModeCntrlTerExistROVOIterator");
                if (iterTerExist.getEstimatedRowCount() > 0) {
                    RichPopup popup = this.getPopConfTerExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                } else {
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    if (!operationBinding.getErrors().isEmpty()) {
                        return null;
                    }
                }

            } else {
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    return null;
                }
            }
        }
        return null;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
}

    public Integer getUtilisateur() {
        return utilisateur;
    }

    @SuppressWarnings("unchecked")
    public void onDeleteAction(DialogEvent dialogEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding mode_cntrl_ec = (AttributeBinding) bindings.getControlBinding("IdModeControleEc");

        OperationBinding op_is_used = bindings.getOperationBinding("IsModeControlEcUsed");
        op_is_used.getParamsMap().put("mde_cntrl_ec_id", Integer.parseInt(mode_cntrl_ec.toString()));
        op_is_used.execute();

        DCIteratorBinding iterUsed = (DCIteratorBinding) bindings.get("IsModeControlEcUsedROVOIterator");
        if (iterUsed.getEstimatedRowCount() > 0) {
            RichPopup popup = this.getPopModCntrlUsed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
            }
        }
    }

    public void setPopConfExist(RichPopup popConfExist) {
        this.popConfExist = popConfExist;
    }

    public RichPopup getPopConfExist() {
        return popConfExist;
    }

    public void setPopConfTerExist(RichPopup popConfTerExist) {
        this.popConfTerExist = popConfTerExist;
    }

    public RichPopup getPopConfTerExist() {
        return popConfTerExist;
    }

    public void setPopModCntrlUsed(RichPopup popModCntrlUsed) {
        this.popModCntrlUsed = popModCntrlUsed;
    }

    public RichPopup getPopModCntrlUsed() {
        return popModCntrlUsed;
    }

    @SuppressWarnings("unchecked")
    public void onDeleteAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding mde_cntrl_ec = (AttributeBinding) bindings.getControlBinding("IdModeControleEc");
        OperationBinding is_mde_cntrl_used = bindings.getOperationBinding("IsModeControlEcUsed");
        is_mde_cntrl_used.getParamsMap().put("mde_cntrl_ec_id", Integer.parseInt(mde_cntrl_ec.toString()));
        is_mde_cntrl_used.execute();
        
        DCIteratorBinding is_mde_cntrl_used_iter = (DCIteratorBinding) bindings.get("IsModeControlEcUsedROVOIterator");
        if(is_mde_cntrl_used_iter.getEstimatedRowCount() > 0){
            //popup impossible de supprimer
            
        }else{
            
        }
        
    }

    public String OnNewModeSaisieActioon() {
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree");
        OperationBinding operationBinding = bindings.getOperationBinding("CreateModeControleEc");
        operationBinding.execute();
        uticre.setInputValue(getUtilisateur());
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String OnCommitModeSaisieAction() {
        BindingContainer bindings = getBindings();
        AttributeBinding fil_ec = (AttributeBinding) bindings.getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding type_cntrl = (AttributeBinding) bindings.getControlBinding("IdTypeControle");
        AttributeBinding mde_cntrl = (AttributeBinding) bindings.getControlBinding("IdModeControle");
        AttributeBinding prcr_mq_an = (AttributeBinding) bindings.getControlBinding("IdParcoursMaquetteAnnee");
        System.out.println("prcr_mq_an : "+prcr_mq_an+" type_cntrl : "+type_cntrl+" fil_ec : "+fil_ec);
        try{
            OperationBinding is_param_exist = bindings.getOperationBinding("IsParamModeCntrlExist");
            is_param_exist.getParamsMap().put("fil_ec_id", Integer.parseInt(fil_ec.toString()));
            is_param_exist.getParamsMap().put("mde_cntrl_id", Integer.parseInt(mde_cntrl.toString()));
            is_param_exist.getParamsMap().put("type_cntrl_id", Integer.parseInt(type_cntrl.toString()));
            is_param_exist.getParamsMap().put("prcrs_maq_an_id", Integer.parseInt(prcr_mq_an.toString()));
            is_param_exist.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        DCIteratorBinding iterExist = (DCIteratorBinding) bindings.get("IsParamModeCntrlExistROVOIterator");
        System.out.println("Size is mode cntl exist : "+iterExist.getEstimatedRowCount());
        if (iterExist.getEstimatedRowCount() > 0) {
            RichPopup popup = this.getPopupConfModeSaisieExist();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            if (Integer.parseInt(type_cntrl.toString()) != 1) {
                try{
                    OperationBinding is_param_ter_exist = bindings.getOperationBinding("IsParamModeCntrlTerExist");
                    is_param_ter_exist.getParamsMap().put("fil_ec_id", Integer.parseInt(fil_ec.toString()));
                    is_param_ter_exist.getParamsMap().put("type_cntrl_id", Integer.parseInt(type_cntrl.toString()));
                    is_param_ter_exist.getParamsMap().put("prcrs_maq_an_id", Integer.parseInt(prcr_mq_an.toString()));
                    is_param_ter_exist.execute();
                }catch(Exception e){
                    System.out.println(e);
                }
                DCIteratorBinding iterTerExist =
                    (DCIteratorBinding) bindings.get("IsParamModeCntrlTerExistROVOIterator");
                System.out.println("Size is mode cntl ter exist : "+iterExist.getEstimatedRowCount());
                if (iterTerExist.getEstimatedRowCount() > 0) {
                    RichPopup popup = this.getPopupSaisieModeCntrlTerExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                } else {
                    try{
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        Object result = operationBinding.execute();
                        if (operationBinding.getErrors().isEmpty()) {
                            //this.getCommitBtn().setDisabled(true);
                            return null;
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }

            } else {
                try{
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    if (operationBinding.getErrors().isEmpty()) {
                        //this.getCommitBtn().setDisabled(true);
                        return null;
                    }
                    }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getPanHderMdeCntrlEc());
        return null;
    }

    @SuppressWarnings("unchecked")
    public void OnEditModeSaisieAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //verifier si moyenne ec déja calculé et bloquer la modification si le cas
        DCIteratorBinding iterModeCntrl = (DCIteratorBinding) bindings.get("ModeControleEcVOIterator");
        System.out.println("Count mode controle ec : "+iterModeCntrl.getEstimatedRowCount());
        Row current = iterModeCntrl.getCurrentRow();
        this.getCommitBtn().setDisabled(false);
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiModifie");
        uticre.setInputValue(getUtilisateur());
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getPanHderMdeCntrlEc());
        /*try{
            OperationBinding opisclose = bindings.getOperationBinding("isClosedSaisieNotesInter");
            opisclose.getParamsMap().put("fil_sem_ec", current.getAttribute("IdFiliereUeSemstreEc"));
            opisclose.getParamsMap().put("type_control", current.getAttribute("IdTypeControle"));
            opisclose.getParamsMap().put("mode_control", current.getAttribute("IdModeControle"));
            opisclose.getParamsMap().put("calendrier", getCalendrier());
            Integer is_ = (Integer) opisclose.execute();
            System.out.println("is closed saisie : "+is_);
            if(0 == is_){
                System.out.println("is not closed saisie ");
                this.getCommitBtn().setDisabled(false);
                AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiModifie");
                uticre.setInputValue(getUtilisateur());
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(this.getPanHderMdeCntrlEc());
            }else{
                //Saisie cloturé impossible de modifier
                RichPopup popup = this.getPopupSaisieclosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }catch(Exception e){
            System.out.println(e);
        }*/
        
    }

    public void setCommitBtn(RichButton commitBtn) {
        this.commitBtn = commitBtn;
    }

    public RichButton getCommitBtn() {
        return commitBtn;
    }

    public void setPanHderMdeCntrlEc(RichPanelHeader panHderMdeCntrlEc) {
        this.panHderMdeCntrlEc = panHderMdeCntrlEc;
    }

    public RichPanelHeader getPanHderMdeCntrlEc() {
        return panHderMdeCntrlEc;
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteModeSaisieAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            AttributeBinding mode_cntrl_ec = (AttributeBinding) bindings.getControlBinding("IdModeControleEc");
            OperationBinding op_is_used = bindings.getOperationBinding("IsModeControlEcUsed");
            op_is_used.getParamsMap().put("mde_cntrl_ec_id", Integer.parseInt(mode_cntrl_ec.toString()));
            op_is_used.execute();
            DCIteratorBinding iterUsed = (DCIteratorBinding) bindings.get("IsModeControlEcUsedROVOIterator");
            if (iterUsed.getEstimatedRowCount() > 0) {
                RichPopup popup = this.getPopModeSaisieUsed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                OperationBinding operationBinding = bindings.getOperationBinding("Delete");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                }
            }
        }
    }

    public void setPopModeSaisieUsed(RichPopup popModeSaisieUsed) {
        this.popModeSaisieUsed = popModeSaisieUsed;
    }

    public RichPopup getPopModeSaisieUsed() {
        return popModeSaisieUsed;
    }

    public void setPopupConfModeSaisieExist(RichPopup popupConfModeSaisieExist) {
        this.popupConfModeSaisieExist = popupConfModeSaisieExist;
    }

    public RichPopup getPopupConfModeSaisieExist() {
        return popupConfModeSaisieExist;
    }

    public void setPopupSaisieModeCntrlTerExist(RichPopup popupSaisieModeCntrlTerExist) {
        this.popupSaisieModeCntrlTerExist = popupSaisieModeCntrlTerExist;
    }

    public RichPopup getPopupSaisieModeCntrlTerExist() {
        return popupSaisieModeCntrlTerExist;
    }
/*
    public void setCalendrier(Long calendrier) {
        this.calendrier = calendrier;
    }

    public Long getCalendrier() {
        return calendrier;
    }
*/
    public void setPopupSaisieclosed(RichPopup popupSaisieclosed) {
        this.popupSaisieclosed = popupSaisieclosed;
    }

    public RichPopup getPopupSaisieclosed() {
        return popupSaisieclosed;
    }
}
