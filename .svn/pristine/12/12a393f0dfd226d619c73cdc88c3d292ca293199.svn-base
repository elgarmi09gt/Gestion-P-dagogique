package processus_inscription;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import java.sql.Connection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.ServletContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowContext;
import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.TaskFlowTrainModel;
import oracle.adf.controller.TaskFlowTrainStopModel;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import model.services.evaluationAppImpl;

import model.services.inscriptionAppImpl;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.adf.model.binding.DCBindingContainer;

public class InscriptionBean implements Serializable{
    
    boolean firstStop = false;
    boolean lastStop = false;
    private RichSelectBooleanCheckbox cocher;
    private List items;
    private ArrayList list = new ArrayList();
    private RichSelectBooleanCheckbox check_all;
    private String taskFlowId = "/inscription/responsable/dynamic-tk-resp.xml#dynamic-tk-resp";
    private String taskFlowId1 = "/inscription/etudiant_bu/dynamic-etud-bu-tk.xml#dynamic-etud-bu-tk";
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private String historique = sessionScope.get("id_hs").toString();
    private RichPopup popup;
    private String taskFlowId2 = "/inscription/paiement_etudiant/dynamic-paiement-tk.xml#dynamic-paiement-tk";
    private String cumul;
    private String dette;

    public InscriptionBean() {
    }
    public List getItems() {
        return list;
    }

    public void setCumul(String cumul) {
        this.cumul = cumul;
    }

    public String getCumul() {
        return cumul;
    }

    public void setDette(String dette) {
        this.dette = dette;
    }

    public String getDette() {
        return dette;
    }

    public void setHistorique(String historique) {
        this.historique = historique;
    }

    public String getHistorique() {
        return historique;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setFirstStop(boolean firstStop) {
        this.firstStop = firstStop;
    }

    public boolean isFirstStop() {
        return firstStop;
    }

    public void setLastStop(boolean lastStop) {
        this.lastStop = lastStop;
    }

    public boolean isLastStop() {
        return lastStop;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String validerModifications() {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //handle errors if any
            return null;
        }
        FacesMessage msg = new FacesMessage(" Modifications validées avec succes ");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    System.out.println(nextRow.getAttribute("case_cocher"));
                    i++;
                }
            }
        }
        return i;
    }

    public Integer nombreLigne(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        return rsi.getRowCount();
    }

    // pour retourner vers la page pécédente
    public String autorisePreviousStop() {

        String prevStopAction = null;

        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel prevStop = taskFlowTrainModel.getPreviousStop(currentStop);
        while(prevStop != null){
            if(isSkipTrainStop(prevStop) == false){
                //no need to loop any further
                prevStopAction = prevStop.getOutcome();
                break;
            }
            prevStop = taskFlowTrainModel.getPreviousStop(prevStop);
        }
        //is either null or has teh value of outcome
        return prevStopAction;
    }

    private boolean isSkipTrainStop(TaskFlowTrainStopModel stop){
        String activityId = stop.getLocalActivityId();
        //get access to the managed bean (HashMap) that keeps track of the
        //train stops that should be skipped
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory expressionFactory = fctx.getApplication().getExpressionFactory();
        //if you follow a consisting naming scheme for your reusable managed bean, then
        //assumptions as in the code below will always work and simplify your development
        ValueExpression ve = expressionFactory.createValueExpression(elctx,"#{pageFlowScope.beanInsPed}", Object.class);
        BeanInsPed skipHelper = (BeanInsPed) ve.getValue(elctx);
        Boolean skip = (Boolean) skipHelper.get(activityId);
        return skip;
    }

    // pour d'aller vers la page suivante
    public String navigateNextStop() {
        // Add event code here...
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = taskFlowTrainModel.getNextStop(currentStop);

        while(nextStop != null){
            if(isSkipTrainStop(nextStop) == false){
                //no need to loop any further
                nextStopAction = nextStop.getOutcome();
                break;
            }
            nextStop = taskFlowTrainModel.getNextStop(nextStop);
        }

        //is either null or has the value of outcome
        return nextStopAction;
    }

    public String validerModInsPed() {
        // Add event code here...
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
        Object result = operationBinding1.execute();
        if (!operationBinding1.getErrors().isEmpty()) {
            //handle errors if any
            //...
            return null;
        }
        else{
            FacesMessage msg = new FacesMessage(" Modification validée avec succes ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public String onListeInscPed() {
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();
        TaskFlowTrainStopModel nextStop = null;
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        Map req = adfCtx.getRequestScope();
        sessionScope.get("idpers").toString();
        OperationBinding listeInsPed = getBindings().getOperationBinding("ExecuteWithParamsListInsPed");
        listeInsPed.getParamsMap().put("annee", Long.parseLong(sessionScope.get("id_annee").toString()));
        listeInsPed.getParamsMap().put("idpers", Long.parseLong(sessionScope.get("idpers").toString()));
        listeInsPed.execute();
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        if(rsi.getRowCount()==0){
            FacesMessage msg = new FacesMessage(" Aucune inscription Pédagogique pour l'étudiant ! ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            nextStop = taskFlowTrainModel.getNextStop(currentStop);
            if(nextStop != null){
                if(isSkipTrainStop(nextStop) == false){
                    //no need to loop any further
                    nextStopAction = nextStop.getOutcome();
                    //break;
                }
                nextStop = taskFlowTrainModel.getNextStop(nextStop);
            }
        }
        return nextStopAction;
    }

    @SuppressWarnings("unchecked")
    public String onChoiseInsPed() {
        // Add event code here...       
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = null;
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Row rw_currente = rsi.first();

        if(iter.getCurrentRow() != null){
            OperationBinding obndAdmPed = getBindings().getOperationBinding("ExecuteWithParams");                       
            obndAdmPed.getParamsMap().put("id_ins_ped",  rw_currente.getAttribute("IdInscriptionPedagogique").toString()); 
            obndAdmPed.execute();
            
            /*while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                insertPaieMensualite(nextRow.getAttribute("IdInscriptionPedagogique").toString(), nextRow.getAttribute("IdEtudiant").toString(), getAnne_univers());
            }*/
            nextStop = taskFlowTrainModel.getNextStop(currentStop);
            if(nextStop != null){
                if(isSkipTrainStop(nextStop) == false){
                    //no need to loop any further
                    nextStopAction = nextStop.getOutcome();
                    //break;
                }
                nextStop = taskFlowTrainModel.getNextStop(nextStop);
            }

        }

        /*if(nombreCaseCoche("listeInsPedIterator")!=1){
                FacesMessage msg = new FacesMessage(" Veuillez sélectionner une inscription Pédagogique pour continuer ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                if(nextRow.getAttribute("case_cocher")!=null){
                    if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                        OperationBinding obndAdmPed = getBindings().getOperationBinding("ExecuteWithParams");                       
                        obndAdmPed.getParamsMap().put("id_ins_ped",  Long.parseLong(nextRow.getAttribute("IdInscriptionPedagogique").toString())); 
                        obndAdmPed.execute();
                        insertPaieMensualite(nextRow.getAttribute("IdInscriptionPedagogique").toString(), nextRow.getAttribute("IdEtudiant").toString(), getAnne_univers());
                        nextStop = taskFlowTrainModel.getNextStop(currentStop);
                        if(nextStop != null){
                            if(isSkipTrainStop(nextStop) == false){
                                //no need to loop any further
                                nextStopAction = nextStop.getOutcome();
                                //break;
                            }
                            nextStop = taskFlowTrainModel.getNextStop(nextStop);
                        }
                        
                    }
                }
            }
        }*/
        return nextStopAction;
    }

    public String onValiderModifInsPed() {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //handle errors if any
            return null;
        }
        else{
            FacesMessage msg = new FacesMessage(" Modifications de l'inscription Pédagogique validées avec succes ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        return null;
    }

    @SuppressWarnings("unchecked")
    public void insert(Row rw_insc_ped){
        List les_semestre = semestreNiv(Integer.parseInt(rw_insc_ped.getAttribute("Niveau").toString()));
        for(int id_semestre = 0; id_semestre < les_semestre.size(); id_semestre++){
            OperationBinding get_sem_double = getBindings().getOperationBinding("getResultRedouble");
            get_sem_double.getParamsMap().put("id_parc_maq", rw_insc_ped.getAttribute("IdParcoursMaquetAnnee"));
            get_sem_double.getParamsMap().put("id_etud", rw_insc_ped.getAttribute("IdEtudiant"));
            get_sem_double.getParamsMap().put("id_sem", les_semestre.get(id_semestre));
            get_sem_double.execute();
            
            DCIteratorBinding iter_res = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SemRedoubleROIterator");        
            Row row_sem = iter_res.getCurrentRow();System.out.println("semestre_row_sem pop"+row_sem);
            if(row_sem == null){
                //nombre_sem.add(les_semestre.get(id_semestre));
                OperationBinding op_inspedSem = getBindings().getOperationBinding("getLesInscPedSem");            
                op_inspedSem.getParamsMap().put("id_insc_ped",  rw_insc_ped.getAttribute("IdInscriptionPedagogique"));
                op_inspedSem.getParamsMap().put("id_sem",  les_semestre.get(id_semestre));     
                op_inspedSem.execute();
    
                DCIteratorBinding iter_insc_sem = (DCIteratorBinding)getBindings().get("LesInscPedSemIterator");
                Row row_insc_sem = iter_insc_sem.getCurrentRow();
                if(row_insc_sem == null)
                    insc_ped_sem(rw_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), les_semestre.get(id_semestre)+"", rw_insc_ped.getAttribute("IdNiveauFormation").toString());
            }
        }
    }
    @SuppressWarnings("unchecked")
    public String onNextInscPed() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = null;
        
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiqueListeInsc");
        
        
        
            if(Integer.parseInt(sessionScope.get("val_insc_en_ligne").toString()) == 0 || Integer.parseInt(sessionScope.get("val_etat_insc").toString()) == 0 || 
                    Integer.parseInt(sessionScope.get("val_paiement").toString()) == 0 || Integer.parseInt(sessionScope.get("val_apte").toString()) == 0 || 
                   Integer.parseInt(sessionScope.get("val_etud_bu").toString()) == 0 )
            {
                
                FacesMessage msg = new FacesMessage(" Inscription Pédagogique non validée :");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                if(Integer.parseInt(sessionScope.get("val_insc_en_ligne").toString()) == 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant n'a pas effectué l'inscription en ligne"));
                }
                if(Integer.parseInt(sessionScope.get("val_etud_bu").toString()) == 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant n'est pas en règle avec la BU"));
                }
                if(Integer.parseInt(sessionScope.get("val_apte").toString()) == 0 ){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant n'est pas apte"));
                }
                if(Integer.parseInt(sessionScope.get("val_paiement").toString()) == 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Le Paiement n'est pas validé"));
                }
                if(Integer.parseInt(sessionScope.get("val_etat_insc").toString()) == 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'état d'incription n'est pas encore finalisé (définitif)"));
                }
                if(Integer.parseInt(sessionScope.get("val_insc_ped_sem").toString()) == 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant a déjà validé les deux semestres "));
                }

            }
            else{
                
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                Row rw_currente = rsi.first();
                if(iter.getCurrentRow() != null){
                    insert(rw_currente);  
                    if(rsi.getRowCount() > 1)
                        insert(rsi.last()); 
    
                    inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
                    
                    ViewObject sem_double = am.getSemestreReDoubleRO();
                    sem_double.clearCache();
                    List nombre_sem = new ArrayList();
    
                    List les_semestre = semestreNiv(Integer.parseInt(rw_currente.getAttribute("Niveau").toString()));
                    for(int id_semestre = 0; id_semestre < les_semestre.size(); id_semestre++){
                        OperationBinding get_sem_double = getBindings().getOperationBinding("getResultRedouble");
                        get_sem_double.getParamsMap().put("id_parc_maq", rw_currente.getAttribute("IdParcoursMaquetAnnee"));
                        get_sem_double.getParamsMap().put("id_etud", rw_currente.getAttribute("IdEtudiant"));
                        get_sem_double.getParamsMap().put("id_sem", les_semestre.get(id_semestre));
                        get_sem_double.execute();
                        
                        DCIteratorBinding iter_res = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SemRedoubleROIterator");        
                        Row row_sem = iter_res.getCurrentRow();
                        if(row_sem == null){
                            nombre_sem.add(les_semestre.get(id_semestre));
                        }
                    }
                    if(nombre_sem.size() > 0){
                        for(int id_semestre = 0; id_semestre < nombre_sem.size(); id_semestre++){
                            //for(int id_semestre = 0; id_semestre < les_semestre.size(); id_semestre++){
                            Row r = sem_double.createRow();                                           
                            r.setAttribute("IdSemestre",nombre_sem.get(id_semestre));
                            r.setAttribute("LibelleLong","Semestre "+nombre_sem.get(id_semestre));
                            sem_double.insertRow(r);
                        }
                        OperationBinding op_inspedSem = getBindings().getOperationBinding("getLesInscPedSem");            
                        op_inspedSem.getParamsMap().put("id_insc_ped",  rw_currente.getAttribute("IdInscriptionPedagogique"));
                        op_inspedSem.getParamsMap().put("id_sem",  nombre_sem.get(nombre_sem.size() - 1));     
                        op_inspedSem.execute();
    
                       nextStop = taskFlowTrainModel.getNextStop(currentStop);
                        if(nextStop != null){
                            if(isSkipTrainStop(nextStop) == false){
                                //no need to loop any further
                                nextStopAction = nextStop.getOutcome();
                                //break;
                            }
                        }
                    }
            }
            else{
                FacesMessage msg = new FacesMessage(" Aucune Inscription Pédagogique disponible !");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        return nextStopAction;
    }

    @SuppressWarnings("unchecked")
    public void insc_ped_sem(String id_ins_ped , String id_semes, String id_niv_form){
                           
        OperationBinding op_inspedSem = getBindings().getOperationBinding("getLesInscPedSem");            
        op_inspedSem.getParamsMap().put("id_insc_ped",  id_ins_ped);
        op_inspedSem.getParamsMap().put("id_sem",  id_semes);     
        op_inspedSem.execute();

        DCIteratorBinding iter_insc_sem = (DCIteratorBinding)getBindings().get("LesInscPedSemIterator");
        Row row_insc_sem = iter_insc_sem.getCurrentRow();

        if(row_insc_sem == null){
        
            OperationBinding op_insc_sem_anc = getBindings().getOperationBinding("getInscPedSemAncien");            
            op_insc_sem_anc.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("idpers").toString()));
            op_insc_sem_anc.getParamsMap().put("id_sem",  id_semes);
            op_insc_sem_anc.getParamsMap().put("id_niv_form",  id_niv_form);
            op_insc_sem_anc.execute();
            DCIteratorBinding iter_insc_sem_anc = (DCIteratorBinding)getBindings().get("InscPedSemAncienROIterator");
            Row row_insc_sem_anc = iter_insc_sem_anc.getCurrentRow();
            Integer credit_obtenu = 0;
            Integer credit_dette = 30;
            Integer credit = 0;
            AttributeBinding Util = (AttributeBinding) getBindings().getControlBinding("UtiCreePedSem");       
            AttributeBinding idInsPedSem = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogique1");
            AttributeBinding id_sem = (AttributeBinding) getBindings().getControlBinding("IdSemestre");//InsPedValide
            AttributeBinding insp_validee = (AttributeBinding) getBindings().getControlBinding("InsPedValide");
            
            AttributeBinding id_sem_sem = (AttributeBinding) getBindings().getControlBinding("IdSemestreSem");
            //pas de resulat ancien
            if(row_insc_sem_anc == null){
                AttributeBinding CumulCredit = (AttributeBinding) getBindings().getControlBinding("CumulCredit");
                AttributeBinding DetteCredit = (AttributeBinding) getBindings().getControlBinding("DetteCredit");
                // semestre pas validé, creer une nouvelle ligne pour preparer l'insertion  
                credit_obtenu = credit;
                credit_dette = (30 - credit);
                
                OperationBinding operationBindingPed = getBindings().getOperationBinding("CreateInsertPedSem");
                Object result_ped = operationBindingPed.execute();
                            
                if (!operationBindingPed.getErrors().isEmpty()) {
                    return ;
                }
                else{
                    idInsPedSem.setInputValue(id_ins_ped);
                    insp_validee.setInputValue(1);
                    id_sem.setInputValue(id_semes);
                    CumulCredit.setInputValue(credit_obtenu);
                    DetteCredit.setInputValue(credit_dette);
                    Util.setInputValue(Long.parseLong(getUtilisateur()));
                    OperationBinding op_sem_res_commit = getBindings().getOperationBinding("Commit");
                    Object result_sem_res_commit = op_sem_res_commit.execute();
                    if (!op_sem_res_commit.getErrors().isEmpty()) {
                        return ;
                    }                        
                    else{
                    }
                    
                }
            }
            // il ya un resulat
            else{
                // semestre deja valider
                if((row_insc_sem_anc.getAttribute("Resultat") != null && Integer.parseInt(row_insc_sem_anc.getAttribute("Resultat").toString()) == 2) || (row_insc_sem_anc.getAttribute("Note") != null && Float.parseFloat(row_insc_sem_anc.getAttribute("Note").toString()) >= 10)){
                    System.out.println(" L'étudiant a déjà validé le semestre ! ");
                    
                }
                else{
                    
                    AttributeBinding CumulCredit = (AttributeBinding) getBindings().getControlBinding("CumulCredit");
                    AttributeBinding DetteCredit = (AttributeBinding) getBindings().getControlBinding("DetteCredit");
                    
                    if(row_insc_sem_anc.getAttribute("Credit") != null)
                        credit = Integer.parseInt(row_insc_sem_anc.getAttribute("Credit").toString());
                    
                    // semestre pas validé, creer une nouvelle ligne pour preparer l'insertion  
                    System.out.println("id_niv_form  id_niv_form "+id_niv_form);
                    
                    System.out.println("Credit Obtenu "+row_insc_sem_anc.getAttribute("Credit"));
                    credit_obtenu = credit;
                    credit_dette = (30 - credit);
                    System.out.println("Result"+row_insc_sem_anc.getAttribute("Resultat"));
                    System.out.println("Dette "+ (30 - credit));
                    
                    OperationBinding operationBindingPed = getBindings().getOperationBinding("CreateInsertPedSem");
                    Object result_ped = operationBindingPed.execute();
                                
                    if (!operationBindingPed.getErrors().isEmpty()) {
                        return ;
                    }
                    else{
                        sessionScope.put("cumul_dette", 1);
                        idInsPedSem.setInputValue(id_ins_ped);
                                                                //insp_validee.setInputValue(1);
                        insp_validee.setInputValue("1");
                        id_sem.setInputValue(id_semes);
                        CumulCredit.setInputValue(credit_obtenu);
                        DetteCredit.setInputValue(credit_dette);
                        Util.setInputValue(Long.parseLong(getUtilisateur()));
                        
                        OperationBinding op_sem_res_commit = getBindings().getOperationBinding("Commit");
                        Object result_sem_res_commit = op_sem_res_commit.execute();
                        if (!op_sem_res_commit.getErrors().isEmpty()) {
                            return ;
                        }                        
                        else{
                        }
                        
                    } 
                }                
            }
    
        }
        else{ 
            
        }
    }
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
    @SuppressWarnings("unchecked")
        public List semestreNiv(Integer niv){
            List tab = new ArrayList();
            if(niv == 1){
                tab.add("2");
                tab.add("1");
            }
            else if(niv == 2){
                    tab.add("4");
                    tab.add("3");
            }
            else if(niv == 3){
                    tab.add("6");
                    tab.add("5");
            }
            else if(niv == 4){
                    tab.add("2");
                    tab.add("1");
            }
            else if(niv == 5){
                    tab.add("4");
                    tab.add("3");
            }
            return tab;
        }

    @SuppressWarnings("unchecked")
    public String onNextInsPedSem() {
        // Add event code here...
        
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = null;
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();

        AttributeBinding idSem = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        AttributeBinding idInsPedSem = (AttributeBinding) getBindings().getControlBinding("Semestres");
        idSem.setInputValue(Long.parseLong(getSemestre()));
        AttributeBinding IdParcoursMaquetAnnee = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        DCIteratorBinding op_iter_sem = (DCIteratorBinding)getBindings().get("SemestreReDoubleROIterator");
        Row row_op_sem = op_iter_sem.getCurrentRow();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Row rw_currente = rsi.first();
        if(rw_currente != null){
            if(row_op_sem != null){
                // liste des ue deja validées
                AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
                
                OperationBinding op_insc_sem_anc = getBindings().getOperationBinding("getListeUeValider");            
                op_insc_sem_anc.getParamsMap().put("idpers",  sessionScope.get("idpers").toString());
                op_insc_sem_anc.getParamsMap().put("id_sem",  row_op_sem.getAttribute("IdSemestre"));
                op_insc_sem_anc.getParamsMap().put("id_niv_form",  rw_currente.getAttribute("IdNiveauFormation"));
                op_insc_sem_anc.execute();
                // liste des ue à valider
                OperationBinding opinspedSem = getBindings().getOperationBinding("ExecuteWithParams1");
                System.out.println("IdParcoursMaquetAnnee"+IdParcoursMaquetAnnee.getInputValue().toString());
                opinspedSem.getParamsMap().put("id_parcours_maq",  rw_currente.getAttribute("IdParcoursMaquetAnnee"));
                opinspedSem.getParamsMap().put("id_sem",  row_op_sem.getAttribute("IdSemestre"));
                opinspedSem.getParamsMap().put("id_pers",  sessionScope.get("idpers"));        
                opinspedSem.execute();
                
                OperationBinding op_ue_insc = getBindings().getOperationBinding("getUeInsc");
                op_ue_insc.getParamsMap().put("id_parcours_maq",  rw_currente.getAttribute("IdParcoursMaquetAnnee"));
                op_ue_insc.getParamsMap().put("id_sem",  row_op_sem.getAttribute("IdSemestre"));
                op_ue_insc.getParamsMap().put("id_insc_ped",  rw_currente.getAttribute("IdInscriptionPedagogique"));        
                op_ue_insc.execute();
                
                DCIteratorBinding iter_ue_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("UeInscIterator");        
                Row rw_ue_insc = iter_ue_insc.getCurrentRow();
                if(rw_ue_insc == null){
                    FacesMessage msg = new FacesMessage(" L'étudiant(e) est déjà inscrit(e) à toutes les Ue du : "+row_op_sem.getAttribute("LibelleLong"));
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    nextStop = taskFlowTrainModel.getNextStop(currentStop);
                    if(nextStop != null){
                        if(isSkipTrainStop(nextStop) == false){
                            //no need to loop any further
                            nextStopAction = nextStop.getOutcome();
                            //break;
                        }
                        nextStop = taskFlowTrainModel.getNextStop(nextStop);
                    }
                }
            }
        } 

        AttributeBinding mobilite = (AttributeBinding) getBindings().getControlBinding("Mobilite");
        
        if(mobilite.getInputValue() != null && Integer.parseInt(mobilite.getInputValue().toString())==1){
            sessionScope.put("var_etud_mob", 1);
        }
        else{
            sessionScope.put("var_etud_mob", 0);
        }

        return nextStopAction;
    }

    @SuppressWarnings("unchecked")
    public String onNextInsPedSemUe() {

        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();
        TaskFlowTrainStopModel nextStop = null;

        AttributeBinding insc_ped_liste = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiqueListInsc");
        AttributeBinding id_parc_liste = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneeListInsc");
        AttributeBinding id_etud_liste = (AttributeBinding) getBindings().getControlBinding("IdEtudiantListInsc");
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscriptPedSemUeROIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        if(rsi.getRowCount() == 0){
            FacesMessage msg = new FacesMessage(" L'étudiant a déjà validé toutes les Ue  du :");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);

            DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
            RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
            Row rw_currente = rsi_insc.first();
            if(rw_currente != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("==> "+rw_currente.getAttribute("LibelleLong")));
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if(nombreCaseCoche("InscriptPedSemUeROIterator") < rsi.getRowCount()){
                FacesMessage msg = new FacesMessage(" Veuillez selectionner toutes les Ue pour continuer ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    if(nextRow.getAttribute("case_cocher")!=null){
                        if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                            list.add(nextRow.getAttribute("Codification").toString());
                        }
                    }
                }
                
                nextStop = taskFlowTrainModel.getNextStop(currentStop);
                if(nextStop != null){
                    if(isSkipTrainStop(nextStop) == false){
                        //no need to loop any further
                        nextStopAction = nextStop.getOutcome();
                        //break;
                    }
                    nextStop = taskFlowTrainModel.getNextStop(nextStop);
                }
                //
            }
        }

        return nextStopAction;
    }

    @SuppressWarnings("unchecked")
    public String validerInsUe() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();

        if(Integer.parseInt(sessionScope.get("ins_ped_val").toString()) == 0 || Integer.parseInt(sessionScope.get("val_etat_insc").toString()) == 0 || 
            Integer.parseInt(sessionScope.get("val_paiement").toString()) == 0 || Integer.parseInt(sessionScope.get("val_apte").toString()) == 0 || 
           Integer.parseInt(sessionScope.get("val_etud_bu").toString()) == 0 || Integer.parseInt(sessionScope.get("val_resp_etud").toString()) == 0 || Integer.parseInt(sessionScope.get("val_etud_tic").toString()) == 0)
        {
             
             FacesMessage msg = new FacesMessage(" La liste des erreurs :");
             msg.setSeverity(FacesMessage.SEVERITY_ERROR);
             FacesContext.getCurrentInstance().addMessage(null, msg);
             
             if(Integer.parseInt(sessionScope.get("ins_ped_val").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'inscription Pédagogique n'est pas validée"));
             }
             if(Integer.parseInt(sessionScope.get("val_etat_insc").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'état d'inscription n'est pas définitif"));
             }
             if(Integer.parseInt(sessionScope.get("val_paiement").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Le Paiement n'est pas validé"));
             }

             if(Integer.parseInt(sessionScope.get("val_apte").toString()) == 0 ){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant n'est pas apte"));
             }
             if(Integer.parseInt(sessionScope.get("val_etud_bu").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'étudiant n'est pas en règle avec la BU"));
             }
             if(Integer.parseInt(sessionScope.get("val_etud_tic").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> TIC n'est pas renseignée"));
             }
             if(Integer.parseInt(sessionScope.get("val_resp_etud").toString()) == 0){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Le responsable de l'étudiant"));
             }

         }
        else{  

            DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
            RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
            Row rw_currente = rsi_insc.first();
            
            FacesMessage msg = new FacesMessage("Inscription validée avec succes ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            if(rw_currente != null){
                String  id_insc_ped = rw_currente.getAttribute("IdInscriptionPedagogique").toString();
                String  id_etud = rw_currente.getAttribute("IdEtudiant").toString();
                String  id_struct = rw_currente.getAttribute("IdStructures").toString();
                String  num_etud = rw_currente.getAttribute("Numero").toString();
                insertUe(rw_currente);
                insertPaieMensualite(id_insc_ped,  id_etud, getAnne_univers());
                onGenererCompteEtud(id_insc_ped, id_struct, id_etud, num_etud);
            }
            if(rsi_insc.getRowCount() > 1 ){
                Row rw_insc_ped_inf = rsi_insc.last();
                insertUe(rw_insc_ped_inf);

                OperationBinding op_rech_paie = getBindings().getOperationBinding("getPaiementEtudRech");
                op_rech_paie.getParamsMap().put("id_insc_ped",  rw_insc_ped_inf.getAttribute("IdInscriptionPedagogique"));
                op_rech_paie.getParamsMap().put("niv_form_parcours",  rw_insc_ped_inf.getAttribute("IdNiveauFormationParcours"));
                op_rech_paie.getParamsMap().put("id_annee",  getAnne_univers());
                op_rech_paie.getParamsMap().put("id_etud",  rw_insc_ped_inf.getAttribute("IdEtudiant"));
                op_rech_paie.getParamsMap().put("id_histo_struct",  getHistorique());
                op_rech_paie.execute();
                        
                DCIteratorBinding iter_rech_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudPaiementROIterator");
                Row rw_rech_paie = iter_rech_paie.getCurrentRow();
                if(rw_rech_paie == null)
                    genererPaiementNivInf(rw_insc_ped_inf.getAttribute("IdInscriptionPedagogique").toString());
            }
       
            sessionScope.put("TfInsID", "/inscription/processus_inscription/task-flow-default.xml#task-flow-default");

            DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("listeInsPedIterator");
            
            Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
            
            if(currentRow_insc_ped != null){
                    sessionScope.put("id_insc_certificat_insc" ,Long.parseLong(currentRow_insc_ped.getAttribute("IdInscriptionPedagogique").toString()));
                    sessionScope.put("is_down_certificat_insc", 0);
            }
            else{
                    sessionScope.put("is_down_certificat_insc", 1);                
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public void insertUe(Row rw_ped_insc){

        AttributeBinding idInsPedSem = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedSemestre1");
        AttributeBinding id_fil_ue = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre1");
        AttributeBinding utilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Niveau Formation : "+rw_ped_insc.getAttribute("LibNivForm")));
        //LibNivForm
        OperationBinding op_ue_insc = getBindings().getOperationBinding("getInscPedSemRech");
        op_ue_insc.getParamsMap().put("id_ins_ped",  rw_ped_insc.getAttribute("IdInscriptionPedagogique"));
        op_ue_insc.execute();
                
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscriptPedSemROIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        
        while (rsi.hasNext()) {
            Row rw_ped_sen = rsi.next();
            OperationBinding op_deja_gen = getBindings().getOperationBinding("ExecuteWithParams");            
            op_deja_gen.getParamsMap().put("id_ins_ped_sem",  rw_ped_sen.getAttribute("IdInscriptionPedSemestre"));
            op_deja_gen.execute();
            DCIteratorBinding iter_deja_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscriptionPedSemUeROIterator");
            Row rw_deja_gen = iter_deja_gen.getCurrentRow();
            if(rw_deja_gen == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Semestre "+rw_ped_sen.getAttribute("IdSemestre")));                                              
         
                OperationBinding op_insc_sem_anc = getBindings().getOperationBinding("getListeUeValider");            
                op_insc_sem_anc.getParamsMap().put("idpers",  sessionScope.get("idpers").toString());
                op_insc_sem_anc.getParamsMap().put("id_sem",  rw_ped_sen.getAttribute("IdSemestre"));
                op_insc_sem_anc.getParamsMap().put("id_niv_form",  rw_ped_insc.getAttribute("IdNiveauFormation"));
                op_insc_sem_anc.execute();
                DCIteratorBinding iter_valider = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeUeValideIterator");
                Row rw_ue_valider = iter_valider.getCurrentRow();
                RowSetIterator rsi_valider = iter_valider.getViewObject().createRowSetIterator(null);
                //listeUeValideIterator
                // liste des ue à valider
                OperationBinding opinspedSem = getBindings().getOperationBinding("getInscPedSemAVal");
                opinspedSem.getParamsMap().put("id_parcours_maq",  rw_ped_insc.getAttribute("IdParcoursMaquetAnnee"));
                opinspedSem.getParamsMap().put("id_sem",  rw_ped_sen.getAttribute("IdSemestre"));
                opinspedSem.getParamsMap().put("id_pers",  sessionScope.get("idpers"));        
                opinspedSem.execute();
                DCIteratorBinding iter_a_valider = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscriptPedSemUeROIterator");
                Row rw_ue_a_valider = iter_a_valider.getCurrentRow();
                RowSetIterator rsi_a_valider = iter_a_valider.getViewObject().createRowSetIterator(null);
        
                Integer i = 0;
            
                //if(rw_ue_a_valider != null){
                                        
                    while (rsi_a_valider.hasNext()) {
                        Row rw_rsi_ue_not_sn = rsi_a_valider.next();
            
                        OperationBinding operationBindingPed = getBindings().getOperationBinding("CreateInsert");
                        Object result_ped = operationBindingPed.execute();
                                           
                        if (!operationBindingPed.getErrors().isEmpty()) {
                           return ;
                        }
                        else{
                           idInsPedSem.setInputValue((rw_ped_sen.getAttribute("IdInscriptionPedSemestre")));
                           id_fil_ue.setInputValue(rw_rsi_ue_not_sn.getAttribute("IdFiliereUeSemstre"));
                           utilisateur.setInputValue(sessionScope.get("id_user"));       // utilisateur connecté
                           OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                           Object result = operationBinding1.execute();
                           if (!operationBinding1.getErrors().isEmpty()) {
                                           //handle errors if any
                                           return ;
                           }
                           else{
                                   i++;     //nombre d'Ue coché
                           }
                        }
                        //}
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(i+" Ue(s) inscrite (s) (Ue(s) non validées)"));
            
                    Integer nb_ue_deja_val = ueDejaValidee (rw_ped_sen.getAttribute("IdInscriptionPedSemestre").toString(), rw_ped_sen.getAttribute("IdSemestre").toString());
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(nb_ue_deja_val+" Ue(s) inscrite (s) (Ue(s) déjà validées)"));
            
            //}
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Semestre "+rw_ped_sen.getAttribute("IdSemestre")+" déjà inscrit"));
        }
    }


    @SuppressWarnings("unchecked")
    public Integer ueDejaValidee (String id_insc_ped_sem, String id_sem){

    // insertion des Ue deja validées
        Integer nb = 0;            
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        
        OperationBinding op_insc_sem_anc = getBindings().getOperationBinding("getListeUeValider");            
        op_insc_sem_anc.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("idpers").toString()));
        op_insc_sem_anc.getParamsMap().put("id_sem",  id_sem);
        op_insc_sem_anc.getParamsMap().put("id_niv_form",  Long.parseLong(id_niv_form.getInputValue().toString()));
        op_insc_sem_anc.execute();
        
        AttributeBinding id_insc_ped_sem_ue = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedSemestrePedSem");
        AttributeBinding id_fil_ue_sem = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstrePedSem");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreePedSemUe");
        //CreateInsertPedSemUe
        DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeUeValideIterator");
        RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
        if(rsi_insc.getRowCount() > 0){
            while (rsi_insc.hasNext()) {
                Row nextRow = rsi_insc.next();
                if(Integer.parseInt(nextRow.getAttribute("Resultat").toString()) == 2 || Integer.parseInt(nextRow.getAttribute("Resultat").toString()) == 3 || Long.parseLong(nextRow.getAttribute("Note").toString()) > 10){
                        
                    OperationBinding operationBindingPed = getBindings().getOperationBinding("CreateInsertPedSemUe");
                    Object result_ped = operationBindingPed.execute();
                                       
                    if (!operationBindingPed.getErrors().isEmpty()) {
                       return null;
                    }
                    else{
                        id_insc_ped_sem_ue.setInputValue(id_insc_ped_sem);
                        id_fil_ue_sem.setInputValue(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
                        id_util.setInputValue(Long.parseLong(getUtilisateur()));
                        
                        OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                        Object result = operationBinding1.execute();
                        if (!operationBinding1.getErrors().isEmpty()) {
                            //handle errors if any
                            return null;
                        }
                        else{
                            AttributeBinding id_insc_ped_res = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedSemUeRes");
                            AttributeBinding note = (AttributeBinding) getBindings().getControlBinding("Note");
                            AttributeBinding credit = (AttributeBinding) getBindings().getControlBinding("Credit");
                            AttributeBinding resultat = (AttributeBinding) getBindings().getControlBinding("Resultat");
                            AttributeBinding id_util_res = (AttributeBinding) getBindings().getControlBinding("UtiCreeRes");
                            AttributeBinding id_cal = (AttributeBinding) getBindings().getControlBinding("IdCalendrierDeliberation");
                            
                            AttributeBinding id_ped_sem_ue = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedSemUe");
                            
                            OperationBinding opPed = getBindings().getOperationBinding("CreateInsertResultPedSemUe");
                            Object resul_ins_ped = opPed.execute();
                                               
                            if (!opPed.getErrors().isEmpty()) {
                               return null;
                            }
                            else{
                                //IdInscriptionPedSemUeRes
                                id_insc_ped_res.setInputValue(Long.parseLong(id_ped_sem_ue.getInputValue().toString()));
                                note.setInputValue(Float.parseFloat(nextRow.getAttribute("Note").toString()));
                                credit.setInputValue(Long.parseLong(nextRow.getAttribute("Credit").toString()));
                                resultat.setInputValue(Long.parseLong(nextRow.getAttribute("Resultat").toString()));
                                id_cal.setInputValue(Long.parseLong(nextRow.getAttribute("IdCalendrierDeliberation").toString()));
                                id_util_res.setInputValue(Long.parseLong(getUtilisateur()));
                                //
                                OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                                Object result_commit = op_commit.execute();
                                if (!op_commit.getErrors().isEmpty()) {
                                                //handle errors if any
                                                return null;
                                }
                                else{
                                        nb++;
                                }
                            }
                        }                            
                    }
                }
            }
        }
        return nb ;
    }
    
    public Connection getDataSourceConnection(String dataSourceName) throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(dataSourceName);
        return ds.getConnection();
    }

    private Connection getConnection() throws Exception {
        return getDataSourceConnection("jdbc/refDS"); // datasource name should be defined in weblogic
    }

    public ServletContext getContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void runReport(String repPath, java.util.Map param, String document) throws Exception {
        System.out.println("Running ...");
            String path0 = System.getProperty("user.home");
            System.out.println("User Directory : "+path0);
            BindingContainer bindings = getBindings();

            
            Connection conn = null;

                try {
                HttpServletResponse response = getResponse();
                ServletOutputStream out = response.getOutputStream();
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentType("application/pdf");
                ServletContext context = getContext();
                InputStream fs = this.getClass().getClassLoader().getResourceAsStream("/reports/test_jasper1.jasper");
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                //Affichage du report
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                out.write(baos.toByteArray());
                //téléchargement du report direct
                //JasperExportManager.exportReportToPdfFile(print,file);
                //JasperExportManager.exportReportToPdfFile(printFileName, "C://sample_report.pdf");
                
                //RichPopup popup = this.getPopupSuccessReport();
                //RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //popup.show(hints);
                
                out.flush();
                out.close();
        
                FacesContext.getCurrentInstance().responseComplete();
                
            } catch (Exception jex) {
                jex.printStackTrace();
            } finally {
                System.out.println("Finnish");
                close(conn);
            }
            //}
        }

        @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
        public void close(Connection con) {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }


    public void setCheck_all(RichSelectBooleanCheckbox check_all) {
        this.check_all = check_all;
    }

    public RichSelectBooleanCheckbox getCheck_all() {
        return check_all;
    }

    public void onSelectAllUe(ValueChangeEvent valueChangeEvent) {
        // cocher tous
        if(Boolean.parseBoolean(check_all.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("InscriptPedSemUeROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
            }
        }
        //décocher tous
        else{
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("InscriptPedSemUeROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    public String onValiderResponsable() {
        // Add event code here...
        OperationBinding op_pers_commit = getBindings().getOperationBinding("Commit");
        Object result_pers_commit = op_pers_commit.execute();
        if (!op_pers_commit.getErrors().isEmpty()) {
            return null;
        }
        
        else{
            FacesMessage msg = new FacesMessage(" Responsable enregistré avec succès ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    /*public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }*/

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public TaskFlowId getDynamicTaskFlowId1() {
        return TaskFlowId.parse(taskFlowId1);
    }

    public void setDynamicTaskFlowId1(String taskFlowId) {
        this.taskFlowId1 = taskFlowId;
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = null;
        
        OperationBinding op_pers_commit = getBindings().getOperationBinding("Commit");
        Object result_pers_commit = op_pers_commit.execute();
        if (!op_pers_commit.getErrors().isEmpty()) {
            return ;
        }
        
        else{
            nextStop = taskFlowTrainModel.getNextStop(currentStop);
            if(nextStop != null){
                if(isSkipTrainStop(nextStop) == false){
                    //no need to loop any further
                    nextStopAction = nextStop.getOutcome();
                    //break;
                }
            }
            nextStop = taskFlowTrainModel.getNextStop(nextStop);
            
            FacesMessage msg = new FacesMessage(" Inscription Pédagogique Semestre validée avec succès ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg); this.getPopup().hide();nextPage();
        }
        //return nextStopAction;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopup().hide();
    }
    public String nextPage(){
        String nextStopAction = null;
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();

        TaskFlowTrainStopModel nextStop = null; 
        nextStop = taskFlowTrainModel.getNextStop(currentStop);
        if(nextStop != null){
            if(isSkipTrainStop(nextStop) == false){
                //no need to loop any further
                nextStopAction = nextStop.getOutcome();
                //break;
            }
        }
        nextStop = taskFlowTrainModel.getNextStop(nextStop);
        
        return nextStopAction;
    }

    public TaskFlowId getDynamicTaskFlowId2() {
        return TaskFlowId.parse(taskFlowId2);
    }

    public void setDynamicTaskFlowId2(String taskFlowId) {
        this.taskFlowId2 = taskFlowId;
    }
  
    @SuppressWarnings("unchecked")
    public void validationInscSem(String id_etud, String parcours){   
                        //Etat Inscription
        AttributeBinding niveau = (AttributeBinding) getBindings().getControlBinding("Niveau");
        List les_semestre = semestreNiv(Integer.parseInt(niveau.getInputValue().toString()));
        List nombre_sem = new ArrayList();
    inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
    
    ViewObject sem_double = am.getSemestreReDoubleRO();
    sem_double.clearCache();
        for(int id_semestre = 0; id_semestre < les_semestre.size(); id_semestre++){
            System.out.println("parcours parcours"+parcours);
            OperationBinding get_sem_double = getBindings().getOperationBinding("getResultRedouble");
            get_sem_double.getParamsMap().put("id_parc_maq", parcours);
            get_sem_double.getParamsMap().put("id_etud", id_etud);
            get_sem_double.getParamsMap().put("id_sem", les_semestre.get(id_semestre));
            get_sem_double.execute();
            
            DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SemRedoubleROIterator");        
            Row row_sem = iter.getCurrentRow();
            if(row_sem == null){
                //System.out.println("semestre "+les_semestre.get(id_semestre)); 
                Row r = sem_double.createRow();                                           
                r.setAttribute("IdSemestre",les_semestre.get(id_semestre));
                r.setAttribute("LibelleLong","Semestre "+les_semestre.get(id_semestre));
                sem_double.insertRow(r); 
                nombre_sem.add(les_semestre.get(id_semestre));
            }
        }
        
        if(nombre_sem.size()==0){
            sessionScope.put("val_insc_ped_sem", 0);
        }
        else
            sessionScope.put("val_insc_ped_sem", 1);
        //System.out.println("val_insc_ped_sem "+sessionScope.get("val_insc_ped_sem")); 
    }

    @SuppressWarnings("unchecked")
    public void validationInscription(String id_etud, String id_insc_ped, String parcours){   
                        //Etat Inscription
        
        OperationBinding op_insc_ped = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailInscPed");
        op_insc_ped.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
        op_insc_ped.execute();
        DCIteratorBinding iter_etat = (DCIteratorBinding) getBindings().get("InscPedagogiqueROIterator");

        Row currentRow_etat = iter_etat.getCurrentRow();
        
        
        AttributeBinding id_etat_insc = (AttributeBinding) getBindings().getControlBinding("EtatInscrEtatInscrId");
        /*System.out.println("id_etat_insc id_etat_insc "+id_etat_insc.getInputValue());*/
        if(id_etat_insc.getInputValue() == null){
            sessionScope.put("val_etat_insc", 0);
            sessionScope.put("icone_etat_insc", "/images/delete_icon.png");

        }
        else{
            if(Integer.parseInt(id_etat_insc.getInputValue().toString()) == 22){        // pour etat definitif
                    sessionScope.put("val_etat_insc", 1);
                    sessionScope.put("icone_etat_insc", "/images/commit.png");
            }
            else{
                sessionScope.put("val_etat_insc", 0);
                sessionScope.put("icone_etat_insc", "/images/delete_icon.png");
            }
        }
        if(currentRow_etat == null){
            sessionScope.put("val_etat_insc", 0);
            sessionScope.put("icone_etat_insc", "/images/delete_icon.png");
            // inscription en ligne
            sessionScope.put("val_insc_en_ligne", 0);
            sessionScope.put("icone_insc_en_ligne", "/images/delete_icon.png");
        }
        else{
            /*if(currentRow_etat.getAttribute("EtatInscrEtatInscrId") == null){
                sessionScope.put("val_etat_insc", 0);
                sessionScope.put("icone_etat_insc", "/images/delete_icon.png");
            }
            else{
                if(Integer.parseInt(currentRow_etat.getAttribute("EtatInscrEtatInscrId").toString()) == 22){        // pour etat definitif
                    sessionScope.put("val_etat_insc", 1);
                    sessionScope.put("icone_etat_insc", "/images/commit.png");
                }
                else{
                    sessionScope.put("val_etat_insc", 0);
                    sessionScope.put("icone_etat_insc", "/images/delete_icon.png");
                }
            }*/
            //en ligne
            if(currentRow_etat.getAttribute("InsEnLigne") == null){
                sessionScope.put("val_insc_en_ligne", 0);
                sessionScope.put("icone_insc_en_ligne", "/images/delete_icon.png");
            }
            else{
                if(Integer.parseInt(currentRow_etat.getAttribute("InsEnLigne").toString()) == 1){        // pour etat definitif
                    sessionScope.put("val_insc_en_ligne", 1);
                    sessionScope.put("icone_insc_en_ligne", "/images/commit.png");

                }
                else{
                    sessionScope.put("val_insc_en_ligne", 0);
                    sessionScope.put("icone_insc_en_ligne", "/images/delete_icon.png");
                }
            }
        }
        System.out.println("val_insc_en_ligne"+sessionScope.get("val_insc_en_ligne"));
        System.out.println("val_etat_insc"+sessionScope.get("val_etat_insc"));



        // Paiement Etudiant
        
        OperationBinding op_paie_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getPaiementEtud");
        op_paie_etud.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
        op_paie_etud.getParamsMap().put("parcours", Long.parseLong(parcours));
        op_paie_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_paie_etud.getParamsMap().put("id_annee", Long.parseLong(getAnne_univers()));
        op_paie_etud.execute();
        DCIteratorBinding iter_paiement = (DCIteratorBinding) getBindings().get("PaiementEtudPreinsIterator");

        Row currentRow_paiement = iter_paiement.getCurrentRow();
        System.out.println("id_insc_ped"+id_insc_ped);
        System.out.println("parcours"+parcours);
        System.out.println("id_etud"+id_etud);
        System.out.println("(getAnne_univers()"+getAnne_univers());
        System.out.println("currentRow_paiement"+currentRow_paiement);
        //System.out.println("val_etat_insc"+sessionScope.get("val_etat_insc"));
        if (currentRow_paiement == null) {
            sessionScope.put("val_paiement", 0);
            sessionScope.put("icone_paiement", "/images/delete_icon.png");
        }
        else{
            if(Integer.parseInt(currentRow_paiement.getAttribute("Valider").toString()) == 1){
                sessionScope.put("val_paiement", 1);
                sessionScope.put("icone_paiement", "/images/commit.png");
            }
            else{
                sessionScope.put("val_paiement", 0);
                sessionScope.put("icone_paiement", "/images/delete_icon.png");
            }
        }
        
        // Apte
        
        OperationBinding op_apte_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getAptitudeEtudiant");
        op_apte_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_apte_etud.getParamsMap().put("id_annee", Long.parseLong(getAnne_univers()));
        op_apte_etud.execute();
        DCIteratorBinding iter_apte_etud = (DCIteratorBinding) getBindings().get("AptePreinsIterator");
        Row currentRow_apte_etud = iter_apte_etud.getCurrentRow();
        if (currentRow_apte_etud == null) {
            sessionScope.put("val_apte", 0);
            sessionScope.put("icone_apte", "/images/delete_icon.png");  
        }
        else{
            if(Integer.parseInt(currentRow_apte_etud.getAttribute("Apte").toString()) == 1){
                sessionScope.put("val_apte", 1);
                sessionScope.put("icone_apte", "/images/commit.png");
            }
            else{
                sessionScope.put("val_apte", 0);
                sessionScope.put("icone_apte", "/images/delete_icon.png");  
            }
        }

        // Etudiant Bu

        DCIteratorBinding iter_histo = (DCIteratorBinding)getBindings().get("HistoriquesStructuresIterator");
        Row row_histo = iter_histo.getCurrentRow();
        if(row_histo != null){
            OperationBinding op_etud_bu = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudiantBu");
            op_etud_bu.getParamsMap().put("id_struct",  Long.parseLong(row_histo.getAttribute("IdStructures").toString()));
            op_etud_bu.getParamsMap().put("id_etud", Long.parseLong(id_etud));
            op_etud_bu.getParamsMap().put("id_annee", Long.parseLong(getAnne_univers()));
            op_etud_bu.execute();
            DCIteratorBinding iter_etud_bu = (DCIteratorBinding) getBindings().get("EtudiantBuIterator");
            Row currentRow_etud_bu = iter_etud_bu.getCurrentRow();
            if (currentRow_etud_bu == null) {
                sessionScope.put("val_etud_bu", 0);
                sessionScope.put("icone_etud_bu", "/images/delete_icon.png");
            }
            else{
                if(Integer.parseInt(currentRow_etud_bu.getAttribute("EnRegle").toString()) == 1){
                    sessionScope.put("val_etud_bu", 1);
                    sessionScope.put("icone_etud_bu", "/images/commit.png");
                }
                else{
                    sessionScope.put("val_etud_bu", 0);
                    sessionScope.put("icone_etud_bu", "/images/delete_icon.png");  
                }           
            }
        }
        else{
            sessionScope.put("val_etud_bu", 0);
            sessionScope.put("icone_etud_bu", "/images/delete_icon.png");
        }
        System.out.println("row_histo "+row_histo);
        // Responsable Etudiant
        
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResponsableEtud");
        op_resp_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_resp_etud.execute();
        DCIteratorBinding iter_resp_etud = (DCIteratorBinding) getBindings().get("ResponsableROIterator");
        Row currentRow_resp_etud = iter_resp_etud.getCurrentRow();
        if (currentRow_resp_etud == null) {
            sessionScope.put("val_resp_etud", 0);
            sessionScope.put("icone_resp_etud", "/images/delete_icon.png");
        }
        else{
            sessionScope.put("val_resp_etud", 1);
            sessionScope.put("icone_resp_etud", "/images/commit.png");         
        }
        System.out.println("currentRow_resp_etud "+currentRow_resp_etud);
        //Insc Ped Validée
        
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
        Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
        if (currentRow_insc_ped == null ) {
            sessionScope.put("ins_ped_val", 0);
            sessionScope.put("icone_ins_ped_val", "/images/delete_icon.png");
        }
        else{
            /*|| currentRow_paiement == null  || 
                currentRow_insc_ped.getAttribute("DroitInsPed") == null || currentRow_insc_ped.getAttribute("CoutFormation") == null || currentRow_paiement.getAttribute("MontantPercu") == null */
            if (currentRow_insc_ped.getAttribute("InsEnLigne") == null || currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId") == null ) {
                sessionScope.put("ins_ped_val", 0);
                sessionScope.put("icone_ins_ped_val", "/images/delete_icon.png");
            }
            else{
                if (Integer.parseInt(currentRow_insc_ped.getAttribute("InsEnLigne").toString()) == 1 && Long.parseLong(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId").toString()) == 22) {
                    sessionScope.put("ins_ped_val", 1);
                    sessionScope.put("icone_ins_ped_val", "/images/commit.png");
                }
                else{
                    sessionScope.put("ins_ped_val", 0);
                    sessionScope.put("icone_ins_ped_val", "/images/delete_icon.png");
                }
            }
        }
        System.out.println("currentRow_insc_ped "+currentRow_insc_ped);
        // Etudiant TIC
        
        OperationBinding etud_tic = getBindings().getOperationBinding("getEtudiantTic");
        etud_tic.getParamsMap().put("id_etud",  id_etud);
        etud_tic.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        etud_tic.execute();
        
        DCIteratorBinding iter_etud_tic = (DCIteratorBinding) getBindings().get("EtudiantTicROIterator");
        
        Row currentRow_etud_tic = iter_etud_tic.getCurrentRow();
        
        if(currentRow_etud_tic != null){
            sessionScope.put("val_etud_tic", 1);
            sessionScope.put("icone_etud_tic", "/images/commit.png");
        }
        else{
            sessionScope.put("val_etud_tic", 0);
            sessionScope.put("icone_etud_tic", "/images/delete_icon.png");  
        }
    }
    public String getEtatInsc(){
        AttributeBinding id_etat_insc = (AttributeBinding) getBindings().getControlBinding("EtatInscrEtatInscrId");
        String etat="Non";
        if(id_etat_insc.getInputValue() == null){
            //sessionScope.put("val_etat_insc", 0);
            sessionScope.put("icon_etat_insc", "/images/delete_icon.png");
        }
        else{
            if(Integer.parseInt(id_etat_insc.getInputValue().toString()) == 22){        // pour etat definitif
                //sessionScope.put("val_etat_insc", 1);
                etat = "Oui";
                sessionScope.put("icon_etat_insc", "/images/commit.png");
            }
            else{
                //sessionScope.put("val_etat_insc", 0);
                sessionScope.put("icon_etat_insc", "/images/delete_icon.png");
            }
        }
        System.out.println("etatetatetat"+etat);
        return etat;
    }

    public void onChangeEtat(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding id_etat_insc = (AttributeBinding) getBindings().getControlBinding("EtatInscrEtatInscrId");
        System.out.println("etatetatetat"+id_etat_insc.getInputValue());
        if(Long.parseLong(id_etat_insc.getInputValue().toString()) == 22){
            sessionScope.put("val_etat_insc", 1);

        }
        
    }

    @SuppressWarnings("unchecked")
    public void insertPaieMensualite(String id_insc_ped,String id_etud,String id_annee){
        
        Row row_regle_pay = null;

        // creating a new object of the class Date  
       java.util.Date date = new java.util.Date();    
       System.out.println(date); 
        OperationBinding op_getPaieEtud = getBindings().getOperationBinding("getPaieEtudGene");
        op_getPaieEtud.getParamsMap().put("id_insc_ped",  id_insc_ped);
        op_getPaieEtud.getParamsMap().put("id_annee",  id_annee);
        op_getPaieEtud.getParamsMap().put("id_etud",  id_etud);
        op_getPaieEtud.execute();
        DCIteratorBinding iter_op_paie_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEtudGenROIterator");
        Row row_op_paie_etud =  iter_op_paie_etud.getCurrentRow();
        if(row_op_paie_etud == null){
             System.out.println("getPaieEtudGene");  
            OperationBinding op_getEtatInscPed = getBindings().getOperationBinding("getInscPedFormPay");
            op_getEtatInscPed.getParamsMap().put("id_ped",  id_insc_ped);
            op_getEtatInscPed.execute();
            DCIteratorBinding iter_op_res_der = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedFormPayEcolROIterator");
            Row row_form_pay =  iter_op_res_der.getCurrentRow();
            if(row_form_pay != null){System.out.println("getInscPedFormPay");  
                OperationBinding op_getFormMod = getBindings().getOperationBinding("getPaieEchelonModForm");
                op_getFormMod.getParamsMap().put("id_annee",  id_annee);
                op_getFormMod.getParamsMap().put("id_form",  row_form_pay.getAttribute("IdFormation"));
                op_getFormMod.getParamsMap().put("id_niv_form",  row_form_pay.getAttribute("IdNiveauFormation"));
                op_getFormMod.execute();
                DCIteratorBinding iter_op_form_mod = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEchelonEcolModFormROIterator");
                Row row_op_form_mod =  iter_op_form_mod.getCurrentRow();
                
                OperationBinding op_getExoEcolage = getBindings().getOperationBinding("getExoEcolage");
                op_getExoEcolage.getParamsMap().put("id_annee",  id_annee);
                op_getExoEcolage.getParamsMap().put("id_ped",  id_insc_ped);
                op_getExoEcolage.execute();
                DCIteratorBinding iter_op_exo_ecol = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ExonerationEcolageROIterator");
                Row row_op_exo_ecol =  iter_op_exo_ecol.getCurrentRow();
                
                if(row_op_form_mod != null){System.out.println("getPaieEchelonModForm"); 
                    System.out.println("row_op_exo_ecol "+row_op_exo_ecol); 
                    AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudPaie");
                    AttributeBinding id_form_paie = (AttributeBinding) getBindings().getControlBinding("IdFormationPaie");
                    AttributeBinding id_type_paie = (AttributeBinding) getBindings().getControlBinding("IdTypePaiement");
                    AttributeBinding id_annee_univers = (AttributeBinding) getBindings().getControlBinding("IdAnneeUnivPaie");
                    AttributeBinding id_echelon_paie = (AttributeBinding) getBindings().getControlBinding("IdEchelonPaiement");
                    AttributeBinding montant_paie = (AttributeBinding) getBindings().getControlBinding("MontantPaie");
                    AttributeBinding net_a_payer = (AttributeBinding) getBindings().getControlBinding("NetAPayer");
                    AttributeBinding montant_exon = (AttributeBinding) getBindings().getControlBinding("MontantExonerationPaie");
                    AttributeBinding ref = (AttributeBinding) getBindings().getControlBinding("RefExoPaie");
                    AttributeBinding id_insc_ped_paie = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedPaie");
                   //AnnulePaie
                    AttributeBinding annule_paie = (AttributeBinding) getBindings().getControlBinding("AnnulePaie");
                    AttributeBinding reparti_paie = (AttributeBinding) getBindings().getControlBinding("RepartiPaie");
                    AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreePaie");
                    
                    AttributeBinding complet = (AttributeBinding) getBindings().getControlBinding("Complet");
                    AttributeBinding etat = (AttributeBinding) getBindings().getControlBinding("Etat");
                    AttributeBinding paye = (AttributeBinding) getBindings().getControlBinding("Paye");
                    
                    DCIteratorBinding iter_op_form_mod1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEchelonEcolModFormROIterator");
                    RowSetIterator rsi_op_form_mod = iter_op_form_mod1.getViewObject().createRowSetIterator(null);
                    System.out.println("rsi_op_form_mod "+rsi_op_form_mod);
                    
                    while (rsi_op_form_mod.hasNext()) {
                        Integer montant = 0;
                        Integer montant_net_payer = 0;
                        Object montant_exo = 0;
                        Object ref_exo = null;
                        Row next_echol = rsi_op_form_mod.next(); System.out.println("nationalite avant");
                        
                        // IdPaysNationalite: 23 nationalite senegalaise
                        System.out.println("nationalite "+row_form_pay.getAttribute("IdPaysNationalite"));
                        if(Integer.parseInt(row_form_pay.getAttribute("IdPaysNationalite").toString()) == 23){
                            montant = Integer.parseInt(next_echol.getAttribute("Montant").toString());
                        }
                        else{
                            montant = Integer.parseInt(next_echol.getAttribute("MontantEtr").toString());
                        }      
                        System.out.println("montant avant "+montant);
                        if(row_op_exo_ecol != null){
                            //pour les pupilles de la naion
                            // if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdMotifExoneration").toString()) == 1)
                            // Par monatant : 2 et Par taux : 1
                            if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());System.out.println("parti 1 ");
                                montant_net_payer = (montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString()));
                                ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                    montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);System.out.println("parti 2 ");
                                    montant_net_payer = (montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100));
                                    ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdMotifExoneration").toString()) == 1 ){
                                    montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);System.out.println("parti 3 ");
                                    montant_net_payer = (montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100));
                                    ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            //ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            else{
                                montant_net_payer = montant;
                            }
                        }
                        else{
                            montant_net_payer = montant;
                        }

                        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertPaie");
                        Object result_cle_rep = op_insert_cle_rep.execute();
                                                
                        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                                return ;
                        }
                        else{
                            id_etudiant.setInputValue(id_etud);
                            id_insc_ped_paie.setInputValue(id_insc_ped);
                            id_form_paie.setInputValue(next_echol.getAttribute("IdFormation"));
                            
                            if(Integer.parseInt(next_echol.getAttribute("Ordre").toString()) == 0){
                                id_type_paie.setInputValue("1");        // 1: Droits Administratifs
                                if(row_op_exo_ecol != null){
                                    if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                        montant_net_payer = montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                            montant_net_payer = montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                        montant_net_payer = montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                            montant_net_payer = montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else{
                                        montant_net_payer = montant;
                                    }
                                }
                                else{
                                    montant_net_payer = montant;
                                }
                            }
                            else{
                                id_type_paie.setInputValue("3");        // 3: Frais de Scolarite
                                
                            }
                            reparti_paie.setInputValue("0");
                            annule_paie.setInputValue("0");
                            id_annee_univers.setInputValue(id_annee);
                            id_echelon_paie.setInputValue(next_echol.getAttribute("IdEchelonPaiement"));
                            montant_paie.setInputValue(montant);
                            net_a_payer.setInputValue(montant_net_payer);
                            montant_exon.setInputValue(montant_exo);
                            
                            ref.setInputValue(ref_exo);
                            id_util.setInputValue(getUtilisateur());
                           
                            if(row_op_exo_ecol != null){
                                if(montant_net_payer == 0){
                                    complet.setInputValue(1);
                                    etat.setInputValue(1);
                                    paye.setInputValue(1);
                                }
                                else{
                                }
                            }
                            
                            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
                            Object result = op_commit_cle_rep.execute();
                            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                                    return ;
                            }
                            else{
                            } 
                        }
                    }
                    
                }
                else{
                    // veuillez vérifier formation mod et echelonage de paiement
                }
            }
            else{
                // pas de form payante
            }
        }
        //return row_regle_pay;
    }

    @SuppressWarnings("unchecked")
    public void onChangeSem(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
  
        AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiqueListeInsc");
        DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("listeInsPedIterator");       
        RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
        Row rw_currente = rsi_insc.first();
        if(rw_currente != null){      
            //IdSemestreDoub
            DCIteratorBinding op_iter_sem = (DCIteratorBinding)getBindings().get("SemestreReDoubleROIterator");
            Row row_op_sem = op_iter_sem.getCurrentRow();
            
            OperationBinding op_inspedSem = getBindings().getOperationBinding("getLesInscPedSem");            
            op_inspedSem.getParamsMap().put("id_insc_ped",  rw_currente.getAttribute("IdInscriptionPedagogique"));
            op_inspedSem.getParamsMap().put("id_sem",  row_op_sem.getAttribute("IdSemestre"));     
            op_inspedSem.execute();
        }
        
    }
    @SuppressWarnings("unchecked")
    public void genererPaiementNivInf(String insc_ped_niv_inf){       
        OperationBinding op_paie_insc_ped_niv_inf = getBindings().getOperationBinding("getPaieEtudInscPed");
        op_paie_insc_ped_niv_inf.getParamsMap().put("id_insc_ped", insc_ped_niv_inf);
        op_paie_insc_ped_niv_inf.execute();
        DCIteratorBinding iter_insc_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEtudInscPedROIterator");
        Row rw_currente_insc_ped = iter_insc_annee_passee.getCurrentRow();

        // creer une ligne de paiement pour les emjambilistes
        AttributeBinding numero_identite = (AttributeBinding) getBindings().getControlBinding("NumeroIdentite");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversPaie");
        AttributeBinding num_quittance = (AttributeBinding) getBindings().getControlBinding("NumQuittance");
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiantPaie");
        AttributeBinding etab_code_parent = (AttributeBinding) getBindings().getControlBinding("EtabCodeParent");
        AttributeBinding id_pays_nationalite = (AttributeBinding) getBindings().getControlBinding("CodeNationalite");
        AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiquePaie");
        AttributeBinding code_quittancier = (AttributeBinding) getBindings().getControlBinding("CodeQuittancier");
        AttributeBinding id_mode_paiement = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        AttributeBinding id_operateur = (AttributeBinding) getBindings().getControlBinding("IdOperateurPaie");
        AttributeBinding montant_percu = (AttributeBinding) getBindings().getControlBinding("MontantPercu");
        AttributeBinding id_niv_parc = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcoursPaie");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreePaieEtud");
        AttributeBinding montant = (AttributeBinding) getBindings().getControlBinding("Montant");
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("ValiderPaie");
        //DatePaiement
        AttributeBinding date_paie = (AttributeBinding) getBindings().getControlBinding("DatePaiement");
        
        OperationBinding op_exo = getBindings().getOperationBinding("CreateInsertPaieEtudiant");
        Object result_op_exo = op_exo.execute();
        if (!op_exo.getErrors().isEmpty()) {
                return ;
        }
        else{
            java.util.Date date_du_jour = new java.util.Date();
            date_paie.setInputValue(date_du_jour);
            numero_identite.setInputValue(rw_currente_insc_ped.getAttribute("PieceIdentification"));
            id_annee.setInputValue(rw_currente_insc_ped.getAttribute("IdAnneesUnivers"));
            if(rw_currente_insc_ped.getAttribute("Quittance") != null){
                num_quittance.setInputValue(rw_currente_insc_ped.getAttribute("Quittance"));        //rw_currente_insc_ped.getAttribute("Quittance")
                code_quittancier.setInputValue(rw_currente_insc_ped.getAttribute("Quittance"));
            }
            else{
                num_quittance.setInputValue(0);        
                code_quittancier.setInputValue(0);
            }
            id_hist.setInputValue(rw_currente_insc_ped.getAttribute("IdHistoriquesStructure"));
            id_etud.setInputValue(rw_currente_insc_ped.getAttribute("IdEtudiant"));
            etab_code_parent.setInputValue("p");
            id_pays_nationalite.setInputValue(rw_currente_insc_ped.getAttribute("IdPaysNationalite"));
            id_insc_ped.setInputValue(insc_ped_niv_inf);
       
            if(rw_currente_insc_ped.getAttribute("IdModePaiement") == null){
                DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ModePaiementsIterator");
                Row row_paie = iter_paie.getCurrentRow();
                id_mode_paiement.setInputValue(row_paie.getAttribute("IdModePaiement"));
            }
            else
                id_mode_paiement.setInputValue(rw_currente_insc_ped.getAttribute("IdModePaiement"));    //rw_currente_insc_ped.getAttribute("IdModePaiement")
            System.out.println(" paiement "+id_mode_paiement.getInputValue());
            id_operateur.setInputValue(rw_currente_insc_ped.getAttribute("IdOperateur"));

            valide.setInputValue(1);
            id_niv_parc.setInputValue(rw_currente_insc_ped.getAttribute("IdNiveauFormationParcours"));
            id_util.setInputValue(getUtilisateur());
            
            
            Integer droit_insc_admin = 0;
            Integer droit_insc_ped = 0;
            if(rw_currente_insc_ped.getAttribute("DroitInsAdmPays") != null && rw_currente_insc_ped.getAttribute("DroitInsPedPays") == null){
                droit_insc_admin = Integer.parseInt( rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                montant_percu.setInputValue( 0 + droit_insc_admin);
                montant.setInputValue( 0 + droit_insc_admin);
            }
            else{
                if(rw_currente_insc_ped.getAttribute("DroitInsPedPays") != null && rw_currente_insc_ped.getAttribute("DroitInsAdmPays") == null){
                    droit_insc_ped = Integer.parseInt( rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                    montant_percu.setInputValue( 0 + droit_insc_ped);
                    montant.setInputValue( 0 + droit_insc_ped);
                }
                else{
                    if(rw_currente_insc_ped.getAttribute("DroitInsPedPays") == null && rw_currente_insc_ped.getAttribute("DroitInsAdmPays") == null){
                        montant_percu.setInputValue( null);
                        montant.setInputValue(null);
                    }
                    else{
                        droit_insc_admin = Integer.parseInt( rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                        droit_insc_ped = Integer.parseInt( rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                        montant_percu.setInputValue(droit_insc_admin + droit_insc_ped);
                        montant.setInputValue(droit_insc_admin + droit_insc_ped);
                    }
                }
            }

            OperationBinding op_commit = getBindings().getOperationBinding("Commit");
            Object result_op_commit = op_commit.execute();
            if (!op_commit.getErrors().isEmpty()) {
                    return ;
            }
            else{
                System.out.println(" reussi");
            }
        }
    }

    public String form_num_compte(String num_compte){
        String [] tab_cmpte = num_compte.split(" ");
        String num_ret = null;
        if(tab_cmpte.length > 1){
            num_ret = tab_cmpte[0].substring(0, 4);
            for(int i=0; i<tab_cmpte.length ; i++){
                if(i>0){
                    String ss = tab_cmpte[i];
                    if(ss.length() > 4 ){
                        num_ret = num_ret+"_"+ ss.substring(0, 4);
                    }
                    else
                        num_ret = num_ret+"_"+ss;
                }
            }
        }
        else{
            String ss = tab_cmpte[0];
            if(ss.length() > 4 )
                num_ret = ss.substring(0, 4);
            else
                num_ret = ss;
                 
        }
        return num_ret.toUpperCase ();
    }
    
    @SuppressWarnings("unchecked")
    public Integer inscCompteEtud(String id_etud,String numero_etud,String type_cmpt, String lib_compte, String id_strc){
        
        Integer nb_save = 0;
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_etud_att = (AttributeBinding) getBindings().getControlBinding("IdEtudiantCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return nb_save;
        }
        else{       
            
            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue(type_cmpt);//id_type_cmpte.
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            id_strc_att.setInputValue(id_strc);
            proprietaire.setInputValue(numero_etud);
            id_etud_att.setInputValue(id_etud);
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return nb_save;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return nb_save;
                }
                else{                
                    solde_int.setInputValue(0);
                    solde_act.setInputValue(0);                                        
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return nb_save;
                    }
                    else{
                        nb_save ++;
                    }
                }
            }
        }
        return nb_save;
    }

    @SuppressWarnings("unchecked")
    public void onGenererCompteEtud(String id_insc_ped,String id_struct,String id_etud,String num_etud) {
        // Add event code here...           
        OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
        op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct);
        op_cmpt_etud_gen.getParamsMap().put("id_etud",  id_etud);
        op_cmpt_etud_gen.execute();
        DCIteratorBinding iter_cmpt_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
        Row row_cmpt_form_gen = iter_cmpt_form_gen.getCurrentRow();
        System.out.println("row_cmpt_form_gen "+row_cmpt_form_gen);
        if(row_cmpt_form_gen == null){
         
            OperationBinding op_getPaieEtud = getBindings().getOperationBinding("getPaieEtudGene");
            op_getPaieEtud.getParamsMap().put("id_insc_ped",  id_insc_ped);//getPaieEtudInscPed
            op_getPaieEtud.getParamsMap().put("id_annee",  getAnne_univers());
            op_getPaieEtud.getParamsMap().put("id_etud",  id_etud);
            op_getPaieEtud.execute();
            DCIteratorBinding iter_op_paie_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEtudGenROIterator");
            Row row_op_paie_etud =  iter_op_paie_etud.getCurrentRow();
            if(row_op_paie_etud != null){
                
                String type_cmpte = "9";   //pour compte de Etudiant
                String lib_etud = "Etudiant";
                Integer nb_cmpte = inscCompteEtud(id_etud,num_etud,type_cmpte,lib_etud,id_struct);
                if(nb_cmpte > 0){
                    // compte généré
                }
            }
        }
        else{
            // compte deja généré
        }
    }

}
