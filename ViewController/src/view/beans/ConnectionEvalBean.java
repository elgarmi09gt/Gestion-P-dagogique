package view.beans;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class ConnectionEvalBean {
    private RichPopup popupNoCalDefineDetails;
    private RichPopup popupSuccessConnect;
    private RichPanelGroupLayout prcrsPan;
    private RichPanelGroupLayout conPan;
    private RichListView menuLView;
    ADFContext adfctx = ADFContext.getCurrent();
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String username = sessionScope.get("userName").toString();
    private RichPanelGroupLayout deptPan;
    private RichPanelGroupLayout semPan;
    private RichPopup popupParamChanged;
    private RichPanelGroupLayout dashPan;
    private RichPanelGroupLayout menuPan;
    private RichPanelGroupLayout panFonction;
    private RichTable menuTable;
    private RichPanelGroupLayout dashDapPan;
    private RichPanelGroupLayout conDapPan;
    private RichPanelGroupLayout conInscPan;
    private RichPanelGroupLayout dashInscPan;
    private RichPanelGroupLayout userPangrp;
    private RichPanelGroupLayout usrPanGrp;
    private RichPanelGroupLayout pangrpMaqAn;
    private RichPanelGroupLayout panGrpBtn;

    public ConnectionEvalBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    // Dependent LOV base on valueChange attribute
    @SuppressWarnings("unchecked")
    public void OnDepartementValueChange(ValueChangeEvent valueChangeEvent) {
        //System.out.println("Departement Value Changed");
        BindingContainer bindings = getBindings();
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
//<<<<<<< .mine
        DCIteratorBinding dciterDept = (DCIteratorBinding) bindings.get("HistoriqueStructureByUserNameIterator");
        Row currentDept = dciterDept.getCurrentRow();
        if(currentDept != null){
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
            operationBinding.getParamsMap().put("id_user", sessionScope.get("id_user"));
            operationBinding.getParamsMap()
                .put("idDept", Integer.parseInt(currentDept.getAttribute("IdHistoriquesStructure").toString()));
            operationBinding.execute();
            DCIteratorBinding dciterPrcrs = (DCIteratorBinding) bindings.get("ParcoursIterator");
            Row currentPrcrs = dciterPrcrs.getCurrentRow();
            if(currentPrcrs != null){
                OperationBinding opSemestre = bindings.getOperationBinding("GetGradeSemestre");
                 opSemestre.getParamsMap()
                     .put("nfp_id", Long.parseLong(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString()));
                 opSemestre.execute();
                DCIteratorBinding dciterSem = (DCIteratorBinding) bindings.get("GradeSemestreROVOIterator");
                //System.out.println("Count Semestre : "+dciterSem.getEstimatedRowCount());
            }
        }
/*=======
        //get current row and save its rowKey in view scope for later use
        //DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("HistoriquesStructuresIterator");
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("HistoriquesStructuresVO1Iterator"); 
        Row currentDept = dciter.getCurrentRow();
        //System.out.println("XXXX " + currentDept.getAttribute("IdHistoriquesStructure"));
        //System.out.println("LibParcours " + currentDept.getAttribute("LibParcours"));
        //User
        OperationBinding operationUser = bindings.getOperationBinding("UserByUserName");
        Object result = operationUser.execute();
        DCIteratorBinding dciter_user = (DCIteratorBinding) cbinding.get("UserByUserNameIterator");
        Row currentUser = dciter_user.getCurrentRow();
        //Integer user_id = Integer.parseInt(currentUser.getAttribute("IdUtilisateur").toString());
        //System.out.println("user_id : "+currentUser.getAttribute("IdUtilisateur"));
        //sessionApp.put("id_user", currentUser.getAttribute("IdUtilisateur"));
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.getParamsMap().put("id_user", Integer.parseInt(currentUser.getAttribute("IdUtilisateur").toString()) );
        operationBinding.getParamsMap().put("idDept", Integer.parseInt(currentDept.getAttribute("IdHistoriquesStructure").toString()));
        operationBinding.execute();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreEcTable());
        AttributeBinding attrIdBinding = (AttributeBinding) bindings.getControlBinding("LibParcours");
        //System.out.println("LibParcours " + attrIdBinding.getInputValue().toString());
        AttributeBinding nivprcrs = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours");
        //System.out.println("IdNiveauFormationParcours " + nivprcrs.getInputValue());
        AttributeBinding nivform = (AttributeBinding) bindings.getControlBinding("Niveauformation");
        //System.out.println("Niveauformation " + nivform.getInputValue());
        
>>>>>>> .r768*/
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void OnContinuAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        ADFContext adfctx = ADFContext.getCurrent();
        Map sessionApp = adfctx.getSessionScope();
       /* BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();*/
        DCIteratorBinding dciter_s = (DCIteratorBinding) bindings.get("StructureAccesByUsernameIterator");
        Row currents = dciter_s.getCurrentRow();
        //############################ Get_departement_id ############################
        DCIteratorBinding dciter_hs = (DCIteratorBinding) bindings.get("DepartementUserIterator");
        Row currenths = dciter_hs.getCurrentRow();
        // ################### Get Values Selected ###################################
        //  Année Univers
        DCIteratorBinding dciter_annee_univers = (DCIteratorBinding) bindings.get("AnneeUniversIterator");
        Row currentAnne = dciter_annee_univers.getCurrentRow();
        // Session
        DCIteratorBinding dciter_session = (DCIteratorBinding) bindings.get("SessionsIterator");
        Row currentSession = dciter_session.getCurrentRow();
        // Semestre
        DCIteratorBinding dciter_semestre = (DCIteratorBinding) bindings.get("GradeSemestreIterator");
        Row currentSemestre = dciter_semestre.getCurrentRow();
        // Niveau formation Cohorte, // Niveau Formation // Parcours
        DCIteratorBinding dciter_parcours = (DCIteratorBinding) bindings.get("ParcoursUserIterator");
        Row currentParcous = dciter_parcours.getCurrentRow();
        // Parcours_maquette_annee
        DCIteratorBinding dciter_parcours_mq = (DCIteratorBinding) bindings.get("MaquetteParcoursAnneeIterator");
        Row currentParcousMaq = dciter_parcours_mq.getCurrentRow();
        //Calendrier Délibération
        if (currents != null && currenths != null && currentAnne != null && currentSession != null &&
            currentSemestre != null && currentParcous != null) {
            /*System.out.println("Structure " + currents.getAttribute("IdStructure"));
            System.out.println("Historique Structure " + currenths.getAttribute("IdHistoriquesStructure"));
            System.out.println("IdNiveauFormationCohorte " + currentParcous.getAttribute("IdNiveauFormationCohorte"));
            System.out.println("IdNiveauParcours " + currentParcous.getAttribute("IdNiveauFormationParcours"));
            System.out.println("IdNiveauFormation " + currentParcous.getAttribute("IdNiveauFormation"));
            System.out.println("IdAnneesUnivers " + currentAnne.getAttribute("IdAnneesUnivers"));
            System.out.println("IdSession " + currentSession.getAttribute("IdSession"));
            System.out.println("Grade Semestre Count : " + dciter_semestre.getEstimatedRowCount());
            System.out.println("IdSemestre " + currentSemestre.getAttribute("IdSemestre"));*/
            try {
                OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParamsCalendrier");
                operationBinding.getParamsMap().put("id_annee", currentAnne.getAttribute("IdAnneesUnivers"));
                operationBinding.getParamsMap().put("id_session", currentSession.getAttribute("IdSession"));
                operationBinding.getParamsMap().put("id_smstre", currentSemestre.getAttribute("IdSemestre"));
                operationBinding.getParamsMap()
                    .put("id_niv_form", currentParcous.getAttribute("IdNiveauFormationCohorte"));
                Object result1 = operationBinding.execute();
                DCIteratorBinding dciter_calendrier =
                    (DCIteratorBinding) bindings.get("CalendrierDeliberationsIterator");
                Row currentCalendrier = dciter_calendrier.getCurrentRow();
                if (currentCalendrier == null) {
                    //System.out.println("IdCalendrierDeliberation pas définit pour vous !!!");
                    RichPopup popup = this.getPopupNoCalDefineDetails();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                } else {
                    /*System.out.println("IdCalendrierDeliberation " +
                                       currentCalendrier.getAttribute("IdCalendrierDeliberation"));*/
                    // ################## Put Variable in SessionScope ####################################
                    sessionApp.put("id_str", currents.getAttribute("IdStructure"));
                    sessionApp.put("id_str_anc", currents.getAttribute("AncienCode") != null ? currents.getAttribute("AncienCode") : 0);
                    sessionApp.put("id_str_grh", currents.getAttribute("CodeGrh")  != null ? currents.getAttribute("CodeGrh") : 0);
                    sessionApp.put("id_annee", currentAnne.getAttribute("IdAnneesUnivers"));
                    sessionApp.put("id_session", currentSession.getAttribute("IdSession"));
                    sessionApp.put("id_smstre", currentSemestre.getAttribute("IdSemestre"));
                    sessionApp.put("id_fr", currentParcous.getAttribute("IdFormation"));
                    sessionApp.put("id_fr_acc", currentParcous.getAttribute("FormAcc") != null ? currentParcous.getAttribute("FormAcc") : 0);
                    sessionApp.put("id_niv_form_cohorte", currentParcous.getAttribute("IdNiveauFormationCohorte"));
                    sessionApp.put("id_niv_form", currentParcous.getAttribute("IdNiveauFormation"));
                    sessionApp.put("niv_sec", currentParcous.getAttribute("CodeNivSec") != null ? currentParcous.getAttribute("CodeNivSec") : 0);
                    sessionApp.put("id_niv_form_parcours", currentParcous.getAttribute("IdNiveauFormationParcours"));
                    sessionApp.put("id_calendrier", currentCalendrier.getAttribute("IdCalendrierDeliberation"));
                    sessionApp.put("id_hs", currenths.getAttribute("IdHistoriquesStructure"));
                    sessionApp.put("prcrs_maq_an", currentParcousMaq.getAttribute("IdParcoursMaquetAnnee"));
                    sessionApp.put("id_maquette", currentParcousMaq.getAttribute("IdMaquette"));
                    sessionApp.put("code_option", currentParcousMaq.getAttribute("CodeOption") != null ? currentParcousMaq.getAttribute("CodeOption") : 0);
                    sessionApp.put("id_cal", 1);
                    //sessionApp.put("is_parm_enter", true);
                    if (sessionApp.get("is_parm_enter").equals(false)) {
                        /*System.out.println("first_connection : go to dashboard !!!");*/
                        sessionApp.put("is_parm_enter", true);
                        try {
                            //System.out.println("UserName : "+sessionScope.get("userName"));
                            //System.out.println("Module : "+sessionScope.get("module_selected"));
                            OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                            opfonction.getParamsMap().put("log", sessionScope.get("userName"));
                            opfonction.getParamsMap().put("module", sessionScope.get("module_selected"));
                            opfonction.execute();
                            DCIteratorBinding iterf = (DCIteratorBinding) bindings.get("FonctionnalitesUsersIterator");
                            //System.out.println("Count fonction : "+iterf.getEstimatedRowCount());
                        } catch (Exception e) {
                            System.out.println(e);
                            System.out.println("Catch FonctionnalitesUsers");
                        }
                        //System.out.println("&&&&&&&&&&&&&&&&&&&& 1 ");
                        this.getConPan().setVisible(false);
                        //System.out.println("&&&&&&&&&&&&&&&&&&&& 2 ");
                        this.getDashPan().setVisible(true);
                        //System.out.println("&&&&&&&&&&&&&&&&&&&& 3 ");
                        this.getMenuTable().setVisible(true);
                        //System.out.println("&&&&&&&&&&&&&&&&&&&& 4 ");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getConPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getDashPan());
                        
                    } else {
                        try {
                            OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                            opfonction.getParamsMap().put("log", sessionScope.get("userName"));
                            opfonction.getParamsMap().put("module", sessionScope.get("module_selected"));
                            opfonction.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                            System.out.println("Catch FonctionnalitesUsers 2");
                        }
                        this.getConPan().setVisible(false);
                        this.getDashPan().setVisible(true);
                        this.getMenuTable().setVisible(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getConPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getDashPan());
                    }
                    /*RichPopup popup = this.getPopupSuccessConnect();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);*/
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMenuLView());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setPopupNoCalDefineDetails(RichPopup popupNoCalDefineDetails) {
        this.popupNoCalDefineDetails = popupNoCalDefineDetails;
    }

    public RichPopup getPopupNoCalDefineDetails() {
        return popupNoCalDefineDetails;
    }

    public void setPopupSuccessConnect(RichPopup popupSuccessConnect) {
        this.popupSuccessConnect = popupSuccessConnect;
    }

    public RichPopup getPopupSuccessConnect() {
        return popupSuccessConnect;
    }
    
    @SuppressWarnings("unchecked")
    public void getDepartement(){
        //System.out.println("Getting Departement Iterator ...");
        sessionScope.put("is_parm_enter", false);
        
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterStr = (DCIteratorBinding) bindings.get("StructureAccesByUsernameIterator");
        Row currentStr = dciterStr.getCurrentRow();
        if(currentStr != null){
            OperationBinding opDept = bindings.getOperationBinding("HistoriqueStructureByUserName");
            opDept.getParamsMap().put("log",getUsername());
            opDept.getParamsMap().put("str_id", Long.parseLong(currentStr.getAttribute("IdStructure").toString()));
            opDept.execute();
            DCIteratorBinding dciterDept = (DCIteratorBinding) bindings.get("HistoriqueStructureByUserNameIterator");
            Row currentDept = dciterDept.getCurrentRow();
            if(currentDept != null){
                OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
                operationBinding.getParamsMap().put("id_user", sessionScope.get("id_user"));
                operationBinding.getParamsMap()
                    .put("idDept", Integer.parseInt(currentDept.getAttribute("IdHistoriquesStructure").toString()));
                operationBinding.execute();
                DCIteratorBinding dciterPrcrs = (DCIteratorBinding) bindings.get("ParcoursIterator");
                Row currentPrcrs = dciterPrcrs.getCurrentRow();
                if(currentPrcrs != null){
                    OperationBinding opSemestre = bindings.getOperationBinding("GetGradeSemestre");
                     opSemestre.getParamsMap()
                         .put("nfp_id", Long.parseLong(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString()));
                     opSemestre.execute();
                    DCIteratorBinding dciterSem = (DCIteratorBinding) bindings.get("GradeSemestreROVOIterator");
                    //System.out.println("Count Semestre : "+dciterSem.getEstimatedRowCount());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPrcrsPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSemPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMenuTable());
    }
    
    public void init(){
        sessionScope.put("is_parm_enter", false);
        //this.getMenuTable().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMenuTable());
    }
    


    @SuppressWarnings("unchecked")
    public void getParcours(){
        //System.out.println("Getting Parcours ...");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HistoriqueStructureByUserNameIterator");
        Row currentDept = dciter.getCurrentRow();
        if(currentDept != null){
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
            operationBinding.getParamsMap().put("id_user", Integer.parseInt(sessionScope.get("id_user").toString()));
            operationBinding.getParamsMap()
                .put("idDept", Integer.parseInt(currentDept.getAttribute("IdHistoriquesStructure").toString()));
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPrcrsPan());
        }
    }

    @SuppressWarnings("unchecked")
    public void getSemestre(){
        //System.out.println("Getting Semestre ...");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentPrcrs = dciterP.getCurrentRow();
        if(currentPrcrs != null){
           OperationBinding operationBinding = bindings.getOperationBinding("GetGradeSemestre");
            operationBinding.getParamsMap()
                .put("nfp_id", Long.parseLong(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString()));
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPrcrsPan());
        }
    }

    public void setPrcrsPan(RichPanelGroupLayout prcrsPan) {
        this.prcrsPan = prcrsPan;
    }

    public RichPanelGroupLayout getPrcrsPan() {
        return prcrsPan;
    }

    public void setConPan(RichPanelGroupLayout conPan) {
        this.conPan = conPan;
    }

    public RichPanelGroupLayout getConPan() {
        return conPan;
    }

    public void setMenuLView(RichListView menuLView) {
        this.menuLView = menuLView;
    }

    public RichListView getMenuLView() {
        return menuLView;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setDeptPan(RichPanelGroupLayout deptPan) {
        this.deptPan = deptPan;
    }

    public RichPanelGroupLayout getDeptPan() {
        return deptPan;
    }

    @SuppressWarnings("unchecked")
    public void OnStructureValueChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //System.out.println("Structure Value Changed");
        BindingContainer bindings = getBindings();   
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding dciterStr = (DCIteratorBinding) bindings.get("StructureAccesByUsernameIterator");
        Row currentStr = dciterStr.getCurrentRow();
        if(currentStr != null){
            //System.out.println("Getting Departement On value structure changed ...");
            OperationBinding opDept = bindings.getOperationBinding("HistoriqueStructureByUserName");
            opDept.getParamsMap().put("log",getUsername());
            opDept.getParamsMap().put("str_id", Long.parseLong(currentStr.getAttribute("IdStructure").toString()));
            opDept.execute();
            DCIteratorBinding dciterDept = (DCIteratorBinding) bindings.get("HistoriqueStructureByUserNameIterator");
            Row currentDept = dciterDept.getCurrentRow();
            if(currentDept != null){
                OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
                operationBinding.getParamsMap().put("id_user", Integer.parseInt(sessionScope.get("id_user").toString()));
                operationBinding.getParamsMap()
                    .put("idDept", Integer.parseInt(currentDept.getAttribute("IdHistoriquesStructure").toString()));
                operationBinding.execute();
                DCIteratorBinding dciterPrcrs = (DCIteratorBinding) bindings.get("ParcoursIterator");
                Row currentPrcrs = dciterPrcrs.getCurrentRow();
                if(currentPrcrs != null){
                //System.out.println("Getting Semestre On value structure changed ...");
                    OperationBinding opSemestre = bindings.getOperationBinding("GetGradeSemestre");
                     opSemestre.getParamsMap()
                         .put("nfp_id", Long.parseLong(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString()));
                     opSemestre.execute();
                    DCIteratorBinding dciterSem = (DCIteratorBinding) bindings.get("GradeSemestreROVOIterator");
                    //System.out.println("Count Semestre : "+dciterSem.getEstimatedRowCount());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeptPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPrcrsPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSemPan());
    }

    public void OnParcoursValueChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //System.out.println("Parcours value changed");
        BindingContainer bindings = getBindings();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentPrcrs = dciterP.getCurrentRow();
        if(currentPrcrs != null){
            //System.out.println("Getting semestre Parcours changed");
           OperationBinding operationBinding = bindings.getOperationBinding("GetGradeSemestre");
            operationBinding.getParamsMap()
                .put("nfp_id", Long.parseLong(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString()));
            operationBinding.execute();
            DCIteratorBinding dciterS = (DCIteratorBinding) bindings.get("GradeSemestreROVOIterator");
            //System.out.println("Count Sem Parcours Changed : "+dciterS.getEstimatedRowCount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSemPan());
        }
    }

    public void setSemPan(RichPanelGroupLayout semPan) {
        this.semPan = semPan;
    }

    public RichPanelGroupLayout getSemPan() {
        return semPan;
    }

    public void setPopupParamChanged(RichPopup popupParamChanged) {
        this.popupParamChanged = popupParamChanged;
    }

    public RichPopup getPopupParamChanged() {
        return popupParamChanged;
    }

    public void setDashPan(RichPanelGroupLayout dashPan) {
        this.dashPan = dashPan;
    }

    public RichPanelGroupLayout getDashPan() {
        return dashPan;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public void AfterSpecifyRoleAction(ActionEvent actionEvent) {
        String from = "/roleCheck/role-check-tf.xml#role-check-tf";
        String to = "/evaluation/connection/connect-eval-params.xml#enter-params";
        //String to = "/testTf/test-tf.xml#test-tf";
        try {
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterrole = (DCIteratorBinding) bindings.get("UtiRolesROVOIterator");
            Row currentRole = iterrole.getCurrentRow();
            //System.out.println("Selected role : " + currentRole.getAttribute("IdRole"));
            sessionScope.put("role_selected", currentRole.getAttribute("IdRole"));
            /*try {
                OperationBinding opfonction = bindings.getOperationBinding("ExecuteWithParams");
                opfonction.getParamsMap().put("role", Long.parseLong(currentRole.getAttribute("IdRole").toString()));
                opfonction.execute();
                DCIteratorBinding iterfonction = (DCIteratorBinding) bindings.get("FontionalitesUsersIterator");
                ViewObject vo=iterfonction.getViewObject();
                vo.executeQuery();
                System.out.println("Fonctionnalités count : "+iterfonction.getEstimatedRowCount());
            } catch (Exception e) {
                System.out.println(e);
            }*/
            try {
                OperationBinding opmodule = bindings.getOperationBinding("getModuleUser");
                opmodule.getParamsMap().put("log", sessionScope.get("userName"));
                opmodule.getParamsMap().put("role", Long.parseLong(currentRole.getAttribute("IdRole").toString()));
                opmodule.execute();
                /*DCIteratorBinding itermodule = (DCIteratorBinding) bindings.get("ModuleUsersIterator");
                System.out.println("Module count : "+itermodule.getEstimatedRowCount());*/
            } catch (Exception e) {
                System.out.println(e);
            }
            TaskFlowId.parse("/evaluation/connection/connect-eval-params.xml#enter-params");
            sessionScope.put("TfID", to);
            sessionScope.put("from", from);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuPan());
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuLView());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setMenuPan(RichPanelGroupLayout menuPan) {
        this.menuPan = menuPan;
    }

    public RichPanelGroupLayout getMenuPan() {
        return menuPan;
    }

    @SuppressWarnings("unchecked")
    public void AfterModuleSpecify(ActionEvent actionEvent) {
        String from = "/roleCheck/role-check-tf.xml#role-check-tf";
        //String to = "/evaluation/connection/connect-eval-params.xml#enter-params";
        //String to = "/testTf/test-tf.xml#test-tf";
        try {
            BindingContainer bindings = getBindings();
            DCIteratorBinding itermod = (DCIteratorBinding) bindings.get("ModulesROVOIterator");
            Row currentRole = itermod.getCurrentRow();
            sessionScope.put("module_selected", currentRole.getAttribute("IdModule"));
            sessionScope.put("module", currentRole.getAttribute("LibelleLong"));
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPangrp());
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getUsrPanGrp());
            try {
                OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                opfonction.getParamsMap().put("log", sessionScope.get("userName"));
                opfonction.getParamsMap().put("module", sessionScope.get("module_selected"));
                opfonction.execute();
                DCIteratorBinding iterf = (DCIteratorBinding) bindings.get("FonctionnalitesUsersIterator");
                //System.out.println("Fonction Count : " + iterf.getEstimatedRowCount());
            } catch (Exception e) {
                System.out.println("in FonctionnalitesUsers catch");
                System.out.println(e);
            }
            //if (sessionApp.get("is_parm_enter").equals(false)) {
            //System.out.println("sessionScope.get(id_user) : "+sessionScope.get("id_user"));
            if (null == sessionScope.get("id_user")) {
                try {
                    OperationBinding opuser = bindings.getOperationBinding("UserByUserName");
                    opuser.getParamsMap().put("username", sessionScope.get("userName"));
                    opuser.execute();
                    DCIteratorBinding iteruser = (DCIteratorBinding) bindings.get("UserByUserNameIterator");
                    Row currentUser = iteruser.getCurrentRow();
                    sessionScope.put("id_user", currentUser.getAttribute("IdUtilisateur"));
                    sessionScope.put("id_user_str", currentUser.getAttribute("IdStructure"));
                    if(null != currentUser.getAttribute("Prenonnom"))
                        sessionScope.put("user", currentUser.getAttribute("Prenonnom"));
                    /*System.out.println("id_user : " + currentUser.getAttribute("IdUtilisateur"));
                    System.out.println("id_user_str : " + currentUser.getAttribute("IdStructure"));
                    System.out.println("user : " + currentUser.getAttribute("Prenonnom"));*/
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPangrp());
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(getUsrPanGrp());
                } catch (Exception e) {
                    System.out.println("in UserByUserName catch");
                    System.out.println(e);
                }
            }
            
            TaskFlowId.parse(currentRole.getAttribute("UrlConnection").toString());
            sessionScope.put("TfID", currentRole.getAttribute("UrlConnection"));
            sessionScope.put("from", from);

            //System.out.println("Selected module : " + sessionScope.get("module_selected"));
            //System.out.println("url connection Module selected: " + sessionScope.get("TfID"));
            
            this.getMenuTable().setVisible(false);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getConPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPangrp());
            /*AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuLView());*/
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String onCheckModule(Long module) {
        // Add event code here...
        System.out.println("module Action : "+module);
        return null;
    }

    public void setPanFonction(RichPanelGroupLayout panFonction) {
        this.panFonction = panFonction;
    }

    public RichPanelGroupLayout getPanFonction() {
        return panFonction;
    }

    public void setMenuTable(RichTable menuTable) {
        this.menuTable = menuTable;
    }

    public RichTable getMenuTable() {
        return menuTable;
    }

    @SuppressWarnings("unchecked")
    public void onContinuePedAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        ADFContext adfctx = ADFContext.getCurrent();
        Map sessionScope = adfctx.getSessionScope();
        // ################### Get Values Selected ###################################
        //  Année Univers
        DCIteratorBinding dciter_annee_univers = (DCIteratorBinding) bindings.get("AnneeUniverROVOIterator");
        Row currentAnne = dciter_annee_univers.getCurrentRow();
        //System.out.println("Annee : "+currentAnne.getAttribute("IdAnneesUnivers"));
        //Etab
        DCIteratorBinding dciter_s = (DCIteratorBinding) bindings.get("StructuresVO1Iterator");
        Row currents = dciter_s.getCurrentRow();
        //System.out.println("Str : "+currents.getAttribute("IdStructure"));
        try {
            sessionScope.put("id_str", currents.getAttribute("IdStructure"));
            sessionScope.put("id_str_anc", currents.getAttribute("AncienCode") != null ? currents.getAttribute("AncienCode") : 0);
            sessionScope.put("id_str_grh", currents.getAttribute("CodeGrh")  != null ? currents.getAttribute("CodeGrh") : 0);
            sessionScope.put("id_annee", currentAnne.getAttribute("IdAnneesUnivers"));
            sessionScope.put("id_cal", 1);
            if (sessionScope.get("is_parm_enter").equals(false)) {
                //System.out.println("first_connection : go to dashboard !!!");
                sessionScope.put("is_parm_enter", true);
                try {
                    OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                    opfonction.getParamsMap().put("log", sessionScope.get("userName"));
                    opfonction.getParamsMap().put("module", sessionScope.get("module_selected"));
                    opfonction.execute();
                    DCIteratorBinding iterf = (DCIteratorBinding) bindings.get("FonctionnalitesUsersIterator");
                    //System.out.println("Fonction count : "+iterf.getEstimatedRowCount());
                } catch (Exception e) {
                    System.out.println("In FonctionnalitesUsers catch");
                    System.out.println(e);
                }
                
                this.getConDapPan().setVisible(false);
                this.getDashDapPan().setVisible(true);
                this.getMenuTable().setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getConDapPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getDashDapPan());
            } else {
                try {
                    OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                    opfonction.getParamsMap().put("log", sessionScope.get("userName"));
                    opfonction.getParamsMap().put("module", sessionScope.get("module_selected"));
                    opfonction.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                this.getConDapPan().setVisible(false);
                this.getDashDapPan().setVisible(true);
                this.getMenuTable().setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getConDapPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getDashDapPan());
                
                /*System.out.println("Connection param changed");
                RichPopup popup = this.getPopupParamChanged();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);*/
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setDashDapPan(RichPanelGroupLayout dashDapPan) {
        this.dashDapPan = dashDapPan;
    }

    public RichPanelGroupLayout getDashDapPan() {
        return dashDapPan;
    }

    public void setConDapPan(RichPanelGroupLayout conDapPan) {
        this.conDapPan = conDapPan;
    }

    public RichPanelGroupLayout getConDapPan() {
        return conDapPan;
    }

    public void setConInscPan(RichPanelGroupLayout conInscPan) {
        this.conInscPan = conInscPan;
    }

    public RichPanelGroupLayout getConInscPan() {
        return conInscPan;
    }

    public void setDashInscPan(RichPanelGroupLayout dashInscPan) {
        this.dashInscPan = dashInscPan;
    }

    public RichPanelGroupLayout getDashInscPan() {
        return dashInscPan;
    }

    @SuppressWarnings("unchecked")
    public void onContinueInscAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        ADFContext adfctx = ADFContext.getCurrent();
        Map sessionApp = adfctx.getSessionScope();
        // ################### Get Values Selected ###################################
        //  Année Univers
        DCIteratorBinding dciter_annee_univers = (DCIteratorBinding) bindings.get("AnneeUniverROVOIterator");
        Row currentAnne = dciter_annee_univers.getCurrentRow();
        DCIteratorBinding dciter_s = (DCIteratorBinding) bindings.get("StructureAccesByUsernameIterator");
        Row currents = dciter_s.getCurrentRow();
        //############################ Get_departement_id ############################
        DCIteratorBinding dciter_hs = (DCIteratorBinding) bindings.get("DepartementUserIterator");
        Row currenths = dciter_hs.getCurrentRow();
        // ############################### Parcours ################################
        DCIteratorBinding dciter_parcours = (DCIteratorBinding) bindings.get("ParcoursUserIterator");
        Row currentParcous = dciter_parcours.getCurrentRow();
        // Parcours_maquette_annee
        /*DCIteratorBinding dciter_parcours_mq = (DCIteratorBinding) bindings.get("MaquetteParcoursAnneeIterator");
        Row currentParcousMaq = dciter_parcours_mq.getCurrentRow();*/
        if (currents != null && currenths != null && currentAnne != null && currentParcous != null) {
            try {
                // ################## Put Variable in SessionScope ####################################
                sessionApp.put("id_str", currents.getAttribute("IdStructure"));
                sessionApp.put("id_str_anc", currents.getAttribute("AncienCode") != null ? currents.getAttribute("AncienCode") : 0);
                sessionApp.put("id_str_grh", currents.getAttribute("CodeGrh")  != null ? currents.getAttribute("CodeGrh") : 0);
                sessionApp.put("id_annee", currentAnne.getAttribute("IdAnneesUnivers"));
                sessionApp.put("id_fr", currentParcous.getAttribute("IdFormation"));
                sessionApp.put("id_fr_acc", currentParcous.getAttribute("FormAcc")   != null ? currentParcous.getAttribute("FormAcc") : 0);
                sessionApp.put("id_niv_form_cohorte", currentParcous.getAttribute("IdNiveauFormationCohorte"));
                sessionApp.put("id_niv_form", currentParcous.getAttribute("IdNiveauFormation"));
                sessionApp.put("niv_sec", currentParcous.getAttribute("CodeNivSec")   != null ? currentParcous.getAttribute("CodeNivSec") : 0);
                sessionApp.put("id_niv_form_parcours", currentParcous.getAttribute("IdNiveauFormationParcours"));
                sessionApp.put("id_hs", currenths.getAttribute("IdHistoriquesStructure"));
                //sessionApp.put("prcrs_maq_an", currentParcousMaq.getAttribute("IdParcoursMaquetAnnee"));
                //sessionApp.put("id_maquette", currentParcousMaq.getAttribute("IdMaquette"));
                sessionApp.put("id_cal", 1);
                if (sessionApp.get("is_parm_enter").equals(false)) {
                    sessionApp.put("is_parm_enter", true);
                    try {
                        OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                        opfonction.getParamsMap().put("log", sessionApp.get("userName"));
                        opfonction.getParamsMap().put("module", sessionApp.get("module_selected"));
                        opfonction.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                this.getConInscPan().setVisible(false);
                this.getDashInscPan().setVisible(true);
                this.getMenuTable().setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getConInscPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getDashInscPan());
                } else {
                    OperationBinding opfonction = bindings.getOperationBinding("FonctionnalitesUsers");
                    opfonction.getParamsMap().put("log", sessionApp.get("userName"));
                    opfonction.getParamsMap().put("module", sessionApp.get("module_selected"));
                    opfonction.execute();

                    this.getConInscPan().setVisible(false);
                    this.getDashInscPan().setVisible(true);
                    this.getMenuTable().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getConInscPan());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getMenuTable());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getDashInscPan());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            System.out.println("Something is wrong...");
        }
    }

    public void setUserPangrp(RichPanelGroupLayout userPangrp) {
        this.userPangrp = userPangrp;
    }

    public RichPanelGroupLayout getUserPangrp() {
        return userPangrp;
    }

    public void setUsrPanGrp(RichPanelGroupLayout usrPanGrp) {
        this.usrPanGrp = usrPanGrp;
    }

    public RichPanelGroupLayout getUsrPanGrp() {
        return usrPanGrp;
    }

    public void setPangrpMaqAn(RichPanelGroupLayout pangrpMaqAn) {
        this.pangrpMaqAn = pangrpMaqAn;
    }

    public RichPanelGroupLayout getPangrpMaqAn() {
        return pangrpMaqAn;
    }

    @SuppressWarnings("unchecked")
    public void OnUpdatableMaquettePrcrsListener(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding id_an = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers");
        AttributeBinding id_str = (AttributeBinding) bindings.getControlBinding("IdStructure");
        AttributeBinding prcrs = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours1");
        /*System.out.println("valueChangeEvent "+valueChangeEvent.getNewValue());
        System.out.println("str : "+id_str.getInputValue().toString());
        System.out.println("An attribute binding : "+id_an.getInputValue().toString());
        System.out.println("Pacours : "+prcrs.getInputValue().toString());
        */
        try {
            OperationBinding opmqprcrs = bindings.getOperationBinding("ExecuteWithParams3");
            opmqprcrs.getParamsMap().put("an_id", Long.parseLong(id_an.getInputValue().toString()));
            opmqprcrs.getParamsMap().put("str_id", Long.parseLong(id_str.getInputValue().toString()));
            opmqprcrs.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPangrpMaqAn());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanGrpBtn());
    }

    public void setPanGrpBtn(RichPanelGroupLayout panGrpBtn) {
        this.panGrpBtn = panGrpBtn;
    }

    public RichPanelGroupLayout getPanGrpBtn() {
        return panGrpBtn;
    }
}
