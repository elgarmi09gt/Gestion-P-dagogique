package view.beans.etats;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.MalformedURLException;

import java.sql.Connection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class EtatsBean {
    private RichPopup popupSuccessReport;
    ADFContext adfCtx = ADFContext.getCurrent();
    Map pageFlowScope = adfCtx.getPageFlowScope();
    Map sessionFlowScope = adfCtx.getSessionScope();
    Long annee = Long.parseLong(sessionFlowScope.get("id_annee").toString());
    Long id_session = Long.parseLong(sessionFlowScope.get("id_session").toString());
    Long id_hs = Long.parseLong(sessionFlowScope.get("id_hs").toString());
    Long str = Long.parseLong(sessionFlowScope.get("id_str").toString());
    Long semestre = Long.parseLong(sessionFlowScope.get("id_smstre").toString());
    private Long calendrier = Long.parseLong(sessionFlowScope.get("id_calendrier").toString());
    private Long prcrs_maq = Long.parseLong(sessionFlowScope.get("prcrs_maq_an").toString());
    private Integer utilisateur = Integer.parseInt(sessionFlowScope.get("id_user").toString());
    private RichSelectBooleanCheckbox inputSemestre;
    private RichButton btnAttestation;
    private RichPanelGroupLayout panelformetu;
    private RichSelectBooleanCheckbox inputIndiv;
    private RichInputText inputEtudiant;
    private RichPopup popupNoNum;
    private RichPopup popupEnterNum;
    //private RichCommandButton btnReleves;
    private RichPanelGroupLayout panedetSem;
    private RichPanelGroupLayout panedetAn;
    private RichPanelGroupLayout panebtn;
    private RichPopup popupSemNotDelib;
    private RichButton btnReleves;
    private RichPopup popupAnNotDelib;
    private RichTable tabDetAn;
    private RichTable tabDetSem;
    private RichPanelCollection panColDetAn;
    private RichPanelCollection panColDetSem;
    private RichPanelGroupLayout panDetEtd;
    private RichPanelGroupLayout panGrpSem;
    private RichButton btnAttestationIndiv;

    public EtatsBean() {
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public void BtnAttestationAction(ActionEvent actionEvent) {
        Map m = new HashMap();
        m.put("parcours", new Long(2));
        m.put("annee_univers", new Long(2));
        m.put("id_hs", new Long(5));
        try {
            runReport("attestation_stand_bis.jasper", m,"attestations");
        } catch (Exception e) {
        }
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
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    @SuppressWarnings("unchecked")
    public void runReport(String repPath, java.util.Map param, String document) throws Exception {
        //System.out.println("Running ...");
            BindingContainer bindings = getBindings();
            //Get Rule
            DCIteratorBinding iterR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("ParcoursIterator");
            Row currentR = iterR.getCurrentRow();
            String doc_name =document+" ";
            String niveauForm =currentR.getAttribute("Niveauformation").toString();
            String cohorte =currentR.getAttribute("Cohorte").toString();
            doc_name=doc_name+niveauForm+" "+cohorte+" ";
            if(currentR.getAttribute("Specialite")!=null){
                doc_name=doc_name+currentR.getAttribute("Specialite").toString()+" ";
               }
            if(currentR.getAttribute("Opt")!=null){
                doc_name=doc_name+currentR.getAttribute("Opt").toString()+" ";
            }
            Connection conn = null;
            ByteArrayOutputStream baos = null;
            ServletOutputStream out = null;
            Map sessionScope = ADFContext.getCurrent().getSessionScope();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            //System.out.println(dateTime.format(formatter));
            String file_name = doc_name+dateTime.format(formatter)+".pdf";
            //System.out.println("file_name "+file_name);
            sessionScope.put("file_name", file_name);
                try {
                HttpServletResponse response = getResponse();
                out = response.getOutputStream();
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentType("application/pdf");
                ServletContext context = getContext();
                InputStream fs = context.getResourceAsStream("/reports/" + repPath);
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                //Affichage du report
                baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                out.write(baos.toByteArray());
                //téléchargement du report direct
                //JasperExportManager.exportReportToPdfFile(print,file);
                //JasperExportManager.exportReportToPdfFile(printFileName, "C://sample_report.pdf");
                
                //RichPopup popup = this.getPopupSuccessReport();
                //RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //popup.show(hints);
                baos.close();
                out.flush();
                out.close();
                FacesContext.getCurrentInstance().responseComplete();
                
            } catch (Exception jex) {
                jex.printStackTrace();
            } finally {
                //System.out.println("Finnish");
                baos.close();
                out.flush();
                out.close();
                close(conn);
            }
            //}
        }

    @SuppressWarnings("unchecked")
    public void runReportBis(String repPath, java.util.Map param, String document) throws Exception {
        //System.out.println("Running ...");
            BindingContainer bindings = getBindings();
            //Get Rule
            Connection conn = null;
            ByteArrayOutputStream baos = null;
            ServletOutputStream out = null;
            Map sessionScope = ADFContext.getCurrent().getSessionScope();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            //System.out.println(dateTime.format(formatter));
            /*String file_name = document+".pdf";
            //System.out.println("file_name "+file_name);
            sessionScope.put("file_name", file_name);*/
                try {
                HttpServletResponse response = getResponse();
                out = response.getOutputStream();
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentType("application/pdf");
                ServletContext context = getContext();
                InputStream fs = context.getResourceAsStream("/reports/" + repPath);
                JasperReport template = (JasperReport) JRLoader.loadObject(fs);
                template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                conn = getConnection();
                @SuppressWarnings("unchecked")
                JasperPrint print = JasperFillManager.fillReport(template, param, conn);
                //Affichage du report
                baos = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(print, baos);
                out.write(baos.toByteArray());
                baos.close();
                out.flush();
                out.close();
                FacesContext.getCurrentInstance().responseComplete();
                
            } catch (Exception jex) {
                jex.printStackTrace();
            } finally {
                //System.out.println("Finnish");
                baos.close();
                out.flush();
                out.close();
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

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.unchecked-conversion-or-cast",
                        "oracle.jdeveloper.java.insufficient-catch-block" })
    public String BtnAttestationAction() {
        Map m = new HashMap();
        m.put("parcours", new Long(2));
        m.put("annee_univers", new Long(2));
        m.put("id_hs", new Long(5));
        try {
            runReport("attestation_stand.jasper", m, "attestation");
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public String BtnRelevesAction() {
        Map m = new HashMap();
        m.put("parcours", new Long(2));
        m.put("annee_univers", new Long(2));
        m.put("id_hs", new Long(5));
        try {
            runReport("releves_standard.jasper", m, "releves");
        } catch (Exception e) {
        }
        return null;
    }

    public void setPopupSuccessReport(RichPopup popupSuccessReport) {
        this.popupSuccessReport = popupSuccessReport;
    }

    public RichPopup getPopupSuccessReport() {
        return popupSuccessReport;
    }

    @SuppressWarnings("unchecked")
    public void RefreshParcours(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        try{
            OperationBinding isDelibAn = bindings.getOperationBinding("IsDelibarateAn");
            isDelibAn.getParamsMap().put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
            isDelibAn.getParamsMap().put("session_id",  getId_session());
            Integer isDelib = (Integer) isDelibAn.execute();
            //System.out.println("isDelib : "+isDelib);
            if(0 != isDelib){
                try{
                    OperationBinding is_diplomante = bindings.getOperationBinding("isDiplomante");
                    is_diplomante.getParamsMap()
                        .put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                    Integer is_diplom = (Integer) is_diplomante.execute();
                    //System.out.println("is_diplom "+is_diplom);
                    if(0 == is_diplom){
                        this.getBtnAttestation().setDisabled(true);
                    }else{
                        this.getBtnAttestation().setDisabled(false);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }else{
                this.getBtnAttestation().setDisabled(true);
            }
            }catch(Exception e){
            System.out.println(e);
            }
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public void TestDownload(FacesContext facesContext, OutputStream outputStream) {
        Map m = new HashMap();
        m.put("parcours", new Long(2));
        m.put("annee_univers", new Long(2));
        m.put("id_hs", new Long(5));
        try {
            runReport("releves.jasper", m,"attestations");
        } catch (Exception e) {
        }

    }

    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void AttestationClicked(FacesContext facesContext, OutputStream outputStream) {
        //verifier si le parcours a une attestation_spéciale
        BindingContainer bindings = getBindings();
        Map m = new HashMap();
        String realpath = null;
        String att_name = null;
        try {
            realpath = getContext().getResource("/reports/").getPath();
            //System.out.println("Path to reports : "+realpath);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }

        DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentR = iterP.getCurrentRow();
        if (null != currentR) {
            try {
                //System.out.println("IdFormation : "+currentR.getAttribute("IdFormation").toString());
                OperationBinding op_update_res = bindings.getOperationBinding("UpdateResultatCycle");
                op_update_res.getParamsMap()
                    .put("code_fr", Long.parseLong(currentR.getAttribute("IdFormation").toString()));
                op_update_res.getParamsMap().put("parcours_maq", getPrcrs_maq());
                op_update_res.getParamsMap().put("annee", getAnnee());
                
                op_update_res.getParamsMap().put("utilisateur", getUtilisateur());
                op_update_res.execute();
                //System.out.println("ResultatCycle Updated");
            } catch (Exception e) {
            }
            m.put("SUBREPORT_DIR", realpath);
            m.put("annee_univers", getAnnee());
            m.put("id_hs", getId_hs());
            //System.out.println("id_hs "+getId_hs());
        }

        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        if (null != currentPM) {
            m.put("parcours", Long.parseLong(currentPM.getAttribute("IdParcoursMaquetAnnee").toString()));
            //System.out.println("Parcours Maquette : " + currentPM.getAttribute("IdParcoursMaquetAnnee"));
            Long attSpec = new Long(0);
            try {
                OperationBinding op_attSpec = bindings.getOperationBinding("isPrcrsAttSpecExist");
                op_attSpec.getParamsMap().put("parcours_maq", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                attSpec = (Long) op_attSpec.execute();
                //System.out.println("attSpec : " + attSpec);
            } catch (Exception e) {
            }
            if (0 != attSpec) {
                try {
                    runReport("attestation_speciale.jasper", m, "Attestation");
                } catch (Exception e) {
                }
            } else {
                try {
                    OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                    op_get_sigle.getParamsMap().put("str_id", getStr());
                    op_get_sigle.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                DCIteratorBinding iterSigle = (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                Row currentStr = iterSigle.getCurrentRow();
                //if(10 == Integer.parseInt(currentStr.getAttribute("IdTypeSection").toString())){
                    if(currentStr.getAttribute("Etab").toString().toUpperCase().matches("UCAD") && 
                                (currentStr.getAttribute("Sigle").toString().toUpperCase().matches("FST"))){
                        att_name = "attestation_faculte_secure";
                    }else{
                        att_name = "attestation_faculte";
                    }
                /*}else{
                    att_name = "attestation_stand_" + currentStr.getAttribute("Sigle").toString();
                }*/
                System.out.println("Attestation Name : " + att_name);
                try {
                    runReport(att_name + ".jasper", m, "Attestation");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void isclosed(){
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        //currentPM.getAttribute("IdParcoursMaquetAnnee")
        //System.out.println("Prcrs : "+currentR.getAttribute("IdNiveauFormationParcours"));
        if(null != currentPM){
            //Attestation
            try{
                /*
                OperationBinding isDelibAn = bindings.getOperationBinding("IsDelibarateAn");
                
                OperationBinding isDelibAn = bindings.getOperationBinding("IsClosedAn");
                */
                OperationBinding isDelibAn = bindings.getOperationBinding("IsOpenDoc"); 
                isDelibAn.getParamsMap().put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                isDelibAn.getParamsMap().put("session_id",  getId_session());
                Integer isDelib = (Integer) isDelibAn.execute();
                //System.out.println("isDelib111 : "+isDelib+" pma : "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
                if(0 != isDelib){
                    try{
                        OperationBinding is_diplomante = bindings.getOperationBinding("isDiplomante");
                        is_diplomante.getParamsMap()
                            .put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                        Integer is_diplom = (Integer) is_diplomante.execute();
                        //System.out.println("is_diplom11 "+is_diplom);
                        pageFlowScope.put("isDiplomante", is_diplom);
                        if(0 == is_diplom){
                            this.getBtnAttestation().setDisabled(true);
                            //this.btnAttestationIndiv.setDisabled(true);
                        }else{
                            this.getBtnAttestation().setDisabled(false);
                            //this.getBtnAttestationIndiv().setDisabled(false);
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    this.getBtnAttestation().setDisabled(true);
                    //this.getBtnReleves().setDisabled(true);
                    //this.getBtnAttestationIndiv().setDisabled(true);
                }
            }catch(Exception e){
                System.out.println(e);
            }
            //releve
            //annuel
            if ((this.getInputSemestre().getValue() == null) || (this.getInputSemestre().getValue().equals(false))) {
                //System.out.println("Releve annuel");
                try{
                    //OperationBinding isDelibAn = bindings.getOperationBinding("IsClosedAn"); 
                    OperationBinding isDelibAn = bindings.getOperationBinding("IsOpenDoc");
                    isDelibAn.getParamsMap().put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                    isDelibAn.getParamsMap().put("session_id",  getId_session());
                    Integer isDelib = (Integer) isDelibAn.execute();
                    //System.out.println("Releve annuel");
                    if(0 != isDelib){
                        this.getBtnReleves().setDisabled(false);
                    }else{
                        this.getBtnReleves().setDisabled(true);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            } 
            //semestre 
            else if(this.getInputSemestre().getValue().equals(true)){
                AttributeBinding semId = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
                //System.out.println("Releve Semestre : "+semId.getInputValue());
                try{
                    OperationBinding isDelibSem = bindings.getOperationBinding("isDelibSemestre");
                    isDelibSem.getParamsMap().put("prcrsMaqId",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                    isDelibSem.getParamsMap().put("semId",  semId.getInputValue());
                    isDelibSem.getParamsMap().put("anId",  getAnnee());
                    isDelibSem.getParamsMap().put("sessId",  getId_session());
                    Integer isDelSem = (Integer) isDelibSem.execute();
                    //System.out.println("isDelSem : "+isDelSem);
                    if(0 == isDelSem){
                        this.getBtnReleves().setDisabled(true);
                    }else{
                        this.getBtnReleves().setDisabled(false);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            /*
            OperationBinding is_sem_delib = bindings.getOperationBinding("isDelibratedSemestre");
            is_sem_delib.getParamsMap()
                .put("parcours",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
            is_sem_delib.getParamsMap().put("calendrier", getCalendrier());
            Integer is_deliberated = (Integer) is_sem_delib.execute();
            //System.out.println("is_deliberated "+is_deliberated);
            //System.out.println("prcrs_maq : "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
            sessionFlowScope.put("isdelibsem", is_deliberated);
            if(0 == is_deliberated){
                this.getBtnReleves().setDisabled(true);
                this.getBtnAttestation().setDisabled(true);
            }else{
                this.getBtnReleves().setDisabled(false);
            }*/
            
        }else{
            this.getBtnReleves().setDisabled(true);
            this.getBtnAttestation().setDisabled(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnAttestation());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnReleves());
    }
    
    public void Showpopup(){
        RichPopup popup = this.getPopupSemNotDelib();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public void RelevesClicked(FacesContext facesContext, OutputStream outputStream) {
        //System.out.println("Is Semestre Check : "+this.getInputSemestre().getValue().toString());
        Integer filEc_number = 0;
        Integer filUe_number = 0;
        Integer cc_number = 0;
        String report_name_first_sem = null;
        String report_name_last_sem = null;
        BindingContainer bindings = getBindings();
        String realpath = null;
        DCIteratorBinding iterR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .get("ParcoursIterator");
        Row currentR = iterR.getCurrentRow();
        /*String doc_name ="releves_"+(getId_session()==1 ?"première_session_":"deuxième_session");
        String niveauForm =currentR.getAttribute("Niveauformation").toString();
        String cohorte =currentR.getAttribute("Cohorte").toString();
        doc_name=doc_name+niveauForm+" "+cohorte+" ";
        if(currentR.getAttribute("Specialite")!=null){
            doc_name=doc_name+currentR.getAttribute("Specialite").toString()+" ";
           }
        if(currentR.getAttribute("Opt")!=null){
            doc_name=doc_name+currentR.getAttribute("Opt").toString()+" ";
        }
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //System.out.println(dateTime.format(formatter));
        String file_name = doc_name+dateTime.format(formatter)+".pdf";
        //System.out.println("file_name "+file_name);
        sessionScope.put("file_name", file_name);*/
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        //currentPM.getAttribute("IdParcoursMaquetAnnee")
        DCIteratorBinding iterListR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("ListRelevesChoiceIterator");
        Row currentList = iterListR.getCurrentRow();
        //System.out.println("Libelle List : "+currentList.getAttribute("Libelle"));

        try {
            OperationBinding opiscc = bindings.getOperationBinding("isCCExiste");
            opiscc.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            cc_number = (Integer) opiscc.execute();
            //System.out.println("cc_number : "+cc_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (null != currentR) {
            //System.out.println("id_hs "+getId_hs());
            //System.out.println("Parcours maq id "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
            try {
                realpath = getContext().getResource("/reports/").getPath();
                //System.out.println("Path to reports : "+realpath);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }

            Map m = new HashMap();
            /*try {
                    runReport("testojdbc7.jasper", m, "releves_semestre_" + getSemestre());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }*/
            //m.put("parcours", Long.parseLong(currentR.getAttribute("IdNiveauFormationParcours").toString()));
            m.put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            m.put("SUBREPORT_DIR", realpath);
            m.put("annee_univers", getAnnee());
            m.put("id_hs", getId_hs());
            //groupé
            if ((this.getInputIndiv().getValue() == null) || (this.getInputIndiv().getValue().equals(false))) {
                try {
                    //Annuel groupé
                    if ((this.getInputSemestre().getValue() == null) || (this.getInputSemestre().getValue().equals(false))) {
                        String rlv_name = "";
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUeMaq");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            filUe_number = (Integer) opcountmaq.execute();
                            //System.out.println("filUe_number_maq : "+filUe_number);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //getCountFilEcParcoursMaqAn
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEcMaq");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            filEc_number = (Integer) opcountmaq.execute();
                            //System.out.println("filEc_number_maq : "+filEc_number);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                            op_get_sigle.getParamsMap().put("str_id", getStr());
                            op_get_sigle.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        //System.out.println("filUe + filEc : "+(filUe_number + filEc_number));
                        DCIteratorBinding iterSigle = (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                        Row currentStr = iterSigle.getCurrentRow();
                        //Etab 
                        //System.out.println("Sigle : "+currentStr.getAttribute("Sigle").toString());
                        if (currentStr.getAttribute("Etab").toString().toUpperCase().matches("UCAD") && 
                            (currentStr.getAttribute("Sigle").toString().toUpperCase().matches("FST"))) {
                            if(filUe_number == filEc_number){
                                if (26 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_secure";
                                    else
                                        rlv_name = "releves_annuel_bis_secure_sess2";
                                    
                                } 
                                else if (30 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_sess2";
                                } 
                                else if (40 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_sess2";
                                } 
                                else {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_1_sess2";
                                }
                            }
                            else{
                                if (30 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_secure";
                                    else
                                        rlv_name = "releves_annuel_bis_secure_sess2";
                                    
                                } else if (36 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_sess2";
                                } else if (40 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_sess2";
                                }else {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_secure_1_1_1_sess2";
                                }
                            }
                        } 
                        else {
                            if(filUe_number == filEc_number){
                                if (26 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis";
                                    else
                                        rlv_name = "releves_annuel_bis_sess2";
                                } else if (30 > (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_sess2";
                                } else if(36 > (filUe_number + filEc_number)){
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_1_sess2";
                                }else{
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_1_1_sess2";
                                }

                            }
                            else{
                                if (30 >= (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis";
                                    else
                                        rlv_name = "releves_annuel_bis_sess2";
                                } else if (35 >= (filUe_number + filEc_number)) {
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_sess2";
                                } else if(40 >= (filUe_number + filEc_number)){
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_1_sess2";
                                }else{
                                    if(getId_session() == 1)
                                        rlv_name = "releves_annuel_bis_1_1_1";
                                    else
                                        rlv_name = "releves_annuel_bis_1_1_1_sess2";
                                }
                            }
                        }
                        System.out.println("rlv_name : "+rlv_name);
                        try {
                            runReport(rlv_name + ".jasper", m, "releves");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    //groupé semestre
                    else if (this.getInputSemestre().getValue().equals(true)) {
                        AttributeBinding semId = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUe");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            opcountmaq.getParamsMap().put("sem_id", semId.getInputValue());
                            filUe_number = (Integer) opcountmaq.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //getCountFilEcParcoursMaqAn
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEc");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            opcountmaq.getParamsMap().put("an_id", getAnnee());
                            opcountmaq.getParamsMap().put("sem_id", semId.getInputValue());
                            filEc_number = (Integer) opcountmaq.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (20 > (filEc_number + filUe_number)) {
                            if (0 == cc_number) {
                                report_name_first_sem = "releves_standard_smstre_fmpo_entete.jasper";
                                report_name_last_sem = "releves_standard_smstre_last_fmpo_entete.jasper";
                            } else {
                                report_name_first_sem = "releves_standard_smstre_ecole_entete.jasper";
                                report_name_last_sem = "releves_standard_smstre_last_ecole_entete.jasper";
                            }
                        } else {
                            //les releves semestre avec plus de 15 ec
                            if (0 == cc_number) {
                                report_name_first_sem = "releves_standard_smstre_fmpo_entete1.jasper";
                                report_name_last_sem = "releves_standard_smstre_last_fmpo_entete1.jasper";
                            } else {
                                report_name_first_sem = "releves_standard_smstre_ecole_entete1.jasper";
                                report_name_last_sem = "releves_standard_smstre_last_ecole_entete1.jasper";
                            }
                        }
                        //System.out.println("report_name_first_sem "+report_name_first_sem);
                        //System.out.println("report_name_last_sem " + report_name_last_sem);
                        //verify if is_[closed/delibarate/published] semestre before run report
                        Integer is_deliberated = 0;
                        try {
                            /*OperationBinding is_sem_delib = bindings.getOperationBinding("isDelibratedSemestre");
                            is_sem_delib.getParamsMap()
                                .put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            is_sem_delib.getParamsMap().put("calendrier", getCalendrier());*/
                            OperationBinding isDelibSem = bindings.getOperationBinding("isDelibSemestre");
                            isDelibSem.getParamsMap().put("prcrsMaqId",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            isDelibSem.getParamsMap().put("semId",  semId.getInputValue());
                            isDelibSem.getParamsMap().put("anId",  getAnnee());
                            isDelibSem.getParamsMap().put("sessId",  getId_session());
                            is_deliberated = (Integer) isDelibSem.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (0 == is_deliberated) {
                            RichPopup popup = this.getPopupSemNotDelib();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        } else {
                            m.put("id_semestre", Long.parseLong(semId.getInputValue().toString()));
                            //System.out.println("Sem : "+getSemestre());
                            if (0 != (Integer.parseInt(semId.getInputValue().toString())  % 2)) {
                                //count ec_number to define releve to show
                                runReport(report_name_first_sem, m, "releves_semestre_" + semId.getInputValue().toString());
                            } else {
                                try {
                                    OperationBinding is_an_delib = bindings.getOperationBinding("IsDelibarateAn");
                                    is_an_delib.getParamsMap()
                                        .put("parcours_maq", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                    is_an_delib.getParamsMap().put("session_id", getId_session());
                                    Integer is_an_del = (Integer) is_an_delib.execute();
                                    if (0 != is_an_del) {
                                        runReport(report_name_last_sem, m, "releves_semestre_" + semId.getInputValue().toString());
                                        //System.out.println(report_name_last_sem);
                                    } else {
                                        RichPopup popup = this.getPopupAnNotDelib();
                                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                        popup.show(hints);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }

                    }else {
                        System.out.println("Inconnue");
                    }
                    /*else {
                        String rlv_name = "";
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUeMaq");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            filUe_number = (Integer) opcountmaq.execute();
                            //System.out.println("filUe_number_maq : "+filUe_number);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //getCountFilEcParcoursMaqAn
                        try {
                            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEcMaq");
                            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            filEc_number = (Integer) opcountmaq.execute();
                            //System.out.println("filEc_number_maq : "+filEc_number);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                            op_get_sigle.getParamsMap().put("str_id", getStr());
                            op_get_sigle.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        DCIteratorBinding iterSigle = (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                        Row currentStr = iterSigle.getCurrentRow();
                        //System.out.println("Sigle : "+currentStr.getAttribute("Sigle").toString());
                        if (currentStr.getAttribute("Sigle")
                                      .toString()
                                      .matches("FST")) {
                            if (30 > (filUe_number + filEc_number)) {
                                rlv_name = "releves_annuel_bis_secure";
                            } else if (36 > (filUe_number + filEc_number)) {
                                rlv_name = "releves_annuel_bis_1_secure";
                            } else {
                                rlv_name = "releves_annuel_bis_1_secure_1_1";
                            }
                        } else {
                            if (30 > (filUe_number + filEc_number)) {
                                rlv_name = "releves_annuel_bis";
                            } else if (36 > (filUe_number + filEc_number)) {
                                rlv_name = "releves_annuel_bis_1";
                            } else {
                                rlv_name = "releves_annuel_bis_1_1";
                            }
                        }
                        //System.out.println("rlv_name : "+rlv_name);
                        try {
                            runReport(rlv_name + ".jasper", m, "releves");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }*/
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            //Individuel
            /*else {
                if (this.getInputIndiv().getValue().equals(true)) {
                    if (this.getInputEtudiant().getValue() != null) {
                        //System.out.println("Numero "+this.getInputEtudiant().getValue().toString());
                        OperationBinding etunum = BindingContext.getCurrent()
                                                                .getCurrentBindingsEntry()
                                                                .getOperationBinding("ExecuteWithParams");
                        etunum.getParamsMap().put("annee", getAnnee());
                        etunum.getParamsMap().put("sem", getSemestre());
                        etunum.getParamsMap()
                            .put("parcours",
                                 Integer.parseInt(currentR.getAttribute("IdNiveauFormationParcours").toString()));
                        etunum.getParamsMap().put("num_etu", this.getInputEtudiant()
                                                                 .getValue()
                                                                 .toString());
                        etunum.execute();
                        DCIteratorBinding iter =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .get("isEtudiantNumeroValideIterator");
                        if (0 == iter.getEstimatedRowCount()) {
                            //System.out.println("Popup No Student with num");
                            RichPopup popup = this.getPopupNoNum();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        } else {
                            //System.out.println("Execute report indiv");
                            try {
                                //indiv semestre
                                m.put("numero", this.getInputEtudiant().getValue().toString());
                                if (this.getInputSemestre().getValue() != null) {
                                    if (this.getInputSemestre()
                                            .getValue()
                                            .equals(true)) {
                                        m.put("id_semestre", getSemestre());
                                        runReport("releves_standard_smstre_ecole_indiv.jasper", m,
                                                  this.getInputEtudiant()
                                                                                                       .getValue()
                                                                                                       .toString() +
 "releves_semestre_" + getSemestre());
                                    } else {
                                        System.out.println("Releves standard indiv");
                                        //runReport("releves_standard_indiv.jasper", m, this.getInputEtudiant().getValue().toString()+"releves");
                                    }
                                } 
                                else {
                                    //System.out.println("Releves standard indiv");
                                    runReport("releves_annuel_bis_indiv.jasper", m, this.getInputEtudiant()
                                                                                        .getValue()
                                                                                        .toString() + "releves");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Popup enter numero etudiant");
                    }
                } 
                else {
                    System.out.println("groupé");
                    try {
                        //Annuel groupé
                        if (this.getInputSemestre().getValue() == null) {
                            String rlv_name = "";
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUeMaq");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                filUe_number = (Integer) opcountmaq.execute();
                                //System.out.println("filUe_number_maq : "+filUe_number);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //getCountFilEcParcoursMaqAn
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEcMaq");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                filEc_number = (Integer) opcountmaq.execute();
                                //System.out.println("filEc_number_maq : "+filEc_number);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                                op_get_sigle.getParamsMap().put("str_id", getStr());
                                op_get_sigle.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            DCIteratorBinding iterSigle =
                                (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                            Row currentStr = iterSigle.getCurrentRow();
                            //System.out.println("Sigle : "+currentStr.getAttribute("Sigle").toString());
                            if (currentStr.getAttribute("Sigle")
                                          .toString()
                                          .matches("FST")) {
                                if (30 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_secure";
                                } else if (40 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_1_secure";
                                } else {
                                    rlv_name = "releves_annuel_bis_1_secure_1_1";
                                }
                            } else {
                                if (30 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis";
                                } else if (40 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_1";
                                } else {
                                    rlv_name = "releves_annuel_bis_1_1";
                                }
                            }
                            //System.out.println("rlv_name : "+rlv_name);
                            try {
                                runReport(rlv_name + ".jasper", m, "releves");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        //groupé semestre
                        else if (this.getInputSemestre()
                                     .getValue()
                                     .equals(true)) {
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUe");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                opcountmaq.getParamsMap().put("sem_id", getSemestre());
                                filUe_number = (Integer) opcountmaq.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //getCountFilEcParcoursMaqAn
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEc");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                opcountmaq.getParamsMap().put("an_id", getAnnee());
                                opcountmaq.getParamsMap().put("sem_id", getSemestre());
                                filEc_number = (Integer) opcountmaq.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (20 > (filEc_number + filUe_number)) {
                                if (0 == cc_number) {
                                    report_name_first_sem = "releves_standard_smstre_fmpo_entete.jasper";
                                    report_name_last_sem = "releves_standard_smstre_last_fmpo_entete.jasper";
                                } else {
                                    report_name_first_sem = "releves_standard_smstre_ecole_entete.jasper";
                                    report_name_last_sem = "releves_standard_smstre_last_ecole_entete.jasper";
                                }
                            } else {
                                //les releves semestre avec plus de 15 ec
                                if (0 == cc_number) {
                                    report_name_first_sem = "releves_standard_smstre_fmpo_entete1.jasper";
                                    report_name_last_sem = "releves_standard_smstre_last_fmpo_entete1.jasper";
                                } else {
                                    report_name_first_sem = "releves_standard_smstre_ecole_entete1.jasper";
                                    report_name_last_sem = "releves_standard_smstre_last_ecole_entete1.jasper";
                                }
                            }
                            //System.out.println("report_name_first_sem "+report_name_first_sem);
                            //System.out.println("report_name_last_sem " + report_name_last_sem);
                            //verify if is_[closed/delibarate/published] semestre before run report
                            Integer is_deliberated = 0;
                            try {
                                OperationBinding is_sem_delib = bindings.getOperationBinding("isDelibratedSemestre");
                                is_sem_delib.getParamsMap()
                                    .put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                is_sem_delib.getParamsMap().put("calendrier", getCalendrier());
                                is_deliberated = (Integer) is_sem_delib.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 == is_deliberated) {
                                RichPopup popup = this.getPopupSemNotDelib();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                //empty hints renders dialog in center of screen
                                popup.show(hints);
                            } else {
                                m.put("id_semestre", getSemestre());
                                //System.out.println("Sem : "+getSemestre());
                                if (0 != (getSemestre() % 2)) {
                                    //count ec_number to define releve to show
                                    runReport(report_name_first_sem, m, "releves_semestre_" + getSemestre());
                                } else {
                                    try {
                                        OperationBinding is_an_delib = bindings.getOperationBinding("IsDelibarateAn");
                                        is_an_delib.getParamsMap()
                                            .put("parcours_maq", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                        is_an_delib.getParamsMap().put("session_id", getId_session());
                                        Integer is_an_del = (Integer) is_an_delib.execute();
                                        if (0 != is_an_del) {
                                            runReport(report_name_last_sem, m, "releves_semestre_" + getSemestre());
                                            //System.out.println(report_name_last_sem);
                                        } else {
                                            RichPopup popup = this.getPopupAnNotDelib();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            popup.show(hints);
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            }

                        } else {
                            String rlv_name = "";
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUeMaq");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                filUe_number = (Integer) opcountmaq.execute();
                                //System.out.println("filUe_number_maq : "+filUe_number);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //getCountFilEcParcoursMaqAn
                            try {
                                OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEcMaq");
                                opcountmaq.getParamsMap()
                                    .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                filEc_number = (Integer) opcountmaq.execute();
                                //System.out.println("filEc_number_maq : "+filEc_number);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                                op_get_sigle.getParamsMap().put("str_id", getStr());
                                op_get_sigle.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            DCIteratorBinding iterSigle =
                                (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                            Row currentStr = iterSigle.getCurrentRow();
                            //System.out.println("Sigle : "+currentStr.getAttribute("Sigle").toString());
                            if (currentStr.getAttribute("Sigle")
                                          .toString()
                                          .matches("FST")) {
                                if (30 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_secure";
                                } else if (40 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_1_secure";
                                } else {
                                    rlv_name = "releves_annuel_bis_1_secure_1_1";
                                }
                            } else {
                                if (30 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis";
                                } else if (40 > (filUe_number + filEc_number)) {
                                    rlv_name = "releves_annuel_bis_1";
                                } else {
                                    rlv_name = "releves_annuel_bis_1_1";
                                }
                            }
                            //System.out.println("rlv_name : "+rlv_name);
                            try {
                                runReport(rlv_name + ".jasper", m, "releves");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }*/
        }

    }

    public void setAnnee(Long annee) {
        this.annee = annee;
    }

    public Long getAnnee() {
        return annee;
    }

    public void setId_hs(Long id_hs) {
        this.id_hs = id_hs;
    }

    public Long getId_hs() {
        return id_hs;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public String Test() {
        String path = this.getClass().getResource("index.jsf").getPath();
        //System.out.println("Path index.jsf : "+path);
        return null;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void OnTestPathClicked(ActionEvent actionEvent) {
        String realpath=null;
        try {
                realpath = getContext().getResource("/reports/").getPath();
                //System.out.println("Path to reports : "+realpath);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
    }

    public void OnSelectDiselectSemestre(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //System.out.println("New Value semestre : " +valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue().equals(true)) {
            this.getBtnAttestation().setDisabled(true);
            this.getPanGrpSem().setVisible(true);
        } else {
            this.getBtnAttestation().setDisabled(false);
            this.getPanGrpSem().setVisible(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnAttestation());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpSem());
        
    }

    public void setInputSemestre(RichSelectBooleanCheckbox inputSemestre) {
        this.inputSemestre = inputSemestre;
    }

    public RichSelectBooleanCheckbox getInputSemestre() {
        return inputSemestre;
    }

    public void setBtnAttestation(RichButton btnAttestation) {
        this.btnAttestation = btnAttestation;
    }

    public RichButton getBtnAttestation() {
        return btnAttestation;
    }

    public void setSemestre(Long semestre) {
        this.semestre = semestre;
    }

    public Long getSemestre() {
        return semestre;
    }

    public void onSelectDiselectIndiv(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //System.out.println("New Value indiv : " +valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue().equals(true)) {
            this.getPanelformetu().setVisible(true);
            this.getPanebtn().setVisible(false);
            this.getPanedetAn().setVisible(false);
            this.getPanedetSem().setVisible(false);
        } else {
            this.getPanedetSem().setVisible(false);
            this.getPanedetAn().setVisible(false);
            this.getPanelformetu().setVisible(false);
            this.getPanebtn().setVisible(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelformetu());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanebtn());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
    }

    public void setPanelformetu(RichPanelGroupLayout panelformetu) {
        this.panelformetu = panelformetu;
    }

    public RichPanelGroupLayout getPanelformetu() {
        return panelformetu;
    }

    public void setInputIndiv(RichSelectBooleanCheckbox inputIndiv) {
        this.inputIndiv = inputIndiv;
    }

    public RichSelectBooleanCheckbox getInputIndiv() {
        return inputIndiv;
    }

    public void setInputEtudiant(RichInputText inputEtudiant) {
        this.inputEtudiant = inputEtudiant;
    }

    public RichInputText getInputEtudiant() {
        return inputEtudiant;
    }

    public void setPopupNoNum(RichPopup popupNoNum) {
        this.popupNoNum = popupNoNum;
    }

    public RichPopup getPopupNoNum() {
        return popupNoNum;
    }

    @SuppressWarnings("unchecked")
    public void onShowDetailsClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        //index des attributs commence par 0
        //System.out.println("Parcours maquette by number : "+currentPM.getAttribute(0));
        //System.out.println("Parcours maquette : "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
        /*DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentR = iterP.getCurrentRow();*/
        //System.out.println("Parcours : "+currentR.getAttribute("IdNiveauFormationParcours"));
        if (null != this.getInputIndiv().getValue()) {
            //individuel
            if (this.getInputIndiv().getValue().equals(true)) {
                //true
                if (null != this.getInputEtudiant().getValue()) {
                    //System.out.println("Numero " + this.getInputEtudiant().getValue().toString()+" pma : "+currentPM.getAttribute("IdParcoursMaquetAnnee")+" sem : "+getSemestre());
                    OperationBinding etunum = bindings.getOperationBinding("ExecuteWithParams");
                    etunum.getParamsMap().put("parcours_maq",currentPM.getAttribute("IdParcoursMaquetAnnee"));
                    etunum.getParamsMap().put("num_etu", this.getInputEtudiant().getValue().toString().toUpperCase());
                    etunum.execute();
                    DCIteratorBinding iter = (DCIteratorBinding) bindings.get("isEtudiantNumeroValideIterator");
                    if (0 == iter.getEstimatedRowCount()) {
                        //System.out.println("Popup No Student with num");
                        RichPopup popup = this.getPopupNoNum();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }else{
                        if (this.getInputSemestre().getValue() != null) {
                            if (this.getInputSemestre().getValue().equals(true)) {
                                DCIteratorBinding iterSem = (DCIteratorBinding) bindings.get("SemestresByParcousMaqIterator");
                                Row currentSem = iterSem.getCurrentRow();
                                if(null != currentSem){
                                    OperationBinding opdetails = bindings.getOperationBinding("getDetailSemEtu");
                                    opdetails.getParamsMap().put("annee", getAnnee());
                                    opdetails.getParamsMap().put("sem", Integer.parseInt(currentSem.getAttribute("IdSemestre").toString()));
                                    opdetails.getParamsMap().put("parcours_maq",currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                    opdetails.getParamsMap().put("numero", this.getInputEtudiant().getValue().toString());
                                    opdetails.execute();
                                    this.getPanedetAn().setVisible(false);
                                    this.getPanedetSem().setVisible(true);
                                    this.getPanDetEtd().setVisible(true);
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanDetEtd());
                                }
                            }
                            else{
                                //DetAnnEtu 
                                OperationBinding opdetails = bindings.getOperationBinding("GetDetailsAnEtu");
                                opdetails.getParamsMap().put("annee", getAnnee());
                                opdetails.getParamsMap().put("parcours_maq",currentPM.getAttribute("IdParcoursMaquetAnnee"));
                                opdetails.getParamsMap().put("numero", this.getInputEtudiant().getValue().toString());
                                opdetails.getParamsMap().put("sess", this.getId_session());
                                opdetails.execute();
                                this.getPanedetSem().setVisible(false);
                                this.getPanedetAn().setVisible(true);
                                this.getPanDetEtd().setVisible(true);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanDetEtd());
                            }
                        }
                        else{
                              OperationBinding opdetails = bindings.getOperationBinding("GetDetailsAnEtu");
                              opdetails.getParamsMap().put("annee", getAnnee());
                              opdetails.getParamsMap().put("parcours_maq",currentPM.getAttribute("IdParcoursMaquetAnnee"));
                              opdetails.getParamsMap().put("numero", this.getInputEtudiant().getValue().toString());
                              opdetails.getParamsMap().put("sess", this.getId_session());
                              opdetails.execute();
                              this.getPanedetSem().setVisible(false);
                              this.getPanedetAn().setVisible(true);
                              this.getPanDetEtd().setVisible(true);
                              AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
                              AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
                              AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanDetEtd());
                            }
                    }
                } else {
                    //System.out.println("Enter num");
                    RichPopup popup = this.getPopupEnterNum();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }else{
                this.getPanedetSem().setVisible(false);
                this.getPanedetAn().setVisible(false);
                this.getPanDetEtd().setVisible(false);
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanDetEtd());
            }
        }
        else{
                this.getPanedetSem().setVisible(false);
                this.getPanedetAn().setVisible(false);
                this.getPanDetEtd().setVisible(false);
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
                  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanDetEtd());
            }
    }

    public void setPopupEnterNum(RichPopup popupEnterNum) {
        this.popupEnterNum = popupEnterNum;
    }

    public RichPopup getPopupEnterNum() {
        return popupEnterNum;
    }

    /*public void setBtnReleves(RichCommandButton btnReleves) {
        this.btnReleves = btnReleves;
    }

    public RichCommandButton getBtnReleves() {
        return btnReleves;
    }*/

    public void setPanedetSem(RichPanelGroupLayout panedetSem) {
        this.panedetSem = panedetSem;
    }

    public RichPanelGroupLayout getPanedetSem() {
        return panedetSem;
    }

    public void setPanedetAn(RichPanelGroupLayout panedetAn) {
        this.panedetAn = panedetAn;
    }

    public RichPanelGroupLayout getPanedetAn() {
        return panedetAn;
    }

    public void setPanebtn(RichPanelGroupLayout panebtn) {
        this.panebtn = panebtn;
    }

    public RichPanelGroupLayout getPanebtn() {
        return panebtn;
    }

    public void setCalendrier(Long calendrier) {
        this.calendrier = calendrier;
    }

    public Long getCalendrier() {
        return calendrier;
    }

    public void setPopupSemNotDelib(RichPopup popupSemNotDelib) {
        this.popupSemNotDelib = popupSemNotDelib;
    }

    public RichPopup getPopupSemNotDelib() {
        return popupSemNotDelib;
    }

    public void setBtnReleves(RichButton btnReleves) {
        this.btnReleves = btnReleves;
    }

    public RichButton getBtnReleves() {
        return btnReleves;
    }

    public void setPopupAnNotDelib(RichPopup popupAnNotDelib) {
        this.popupAnNotDelib = popupAnNotDelib;
    }

    public RichPopup getPopupAnNotDelib() {
        return popupAnNotDelib;
    }

    public void RelevesInvivClicked(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        System.out.println("Releve an indiv");

    }

    @SuppressWarnings("unchecked")
    public void RelevesIndivSemClicked(FacesContext facesContext, OutputStream outputStream) {
        // modifier la fonction
        //System.out.println("Releve sem indiv");
        Integer filEc_number = 0;
        Integer filUe_number = 0;
        Integer cc_number = 0;
        String report_name_first_sem = null;
        String report_name_last_sem = null;
        BindingContainer bindings = getBindings();
        String realpath = null;
        /*DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentR = iterP.getCurrentRow();*/
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        //currentPM.getAttribute("IdParcoursMaquetAnnee")
        /*DCIteratorBinding iterListR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .get("ListRelevesChoiceIterator");
        Row currentList = iterListR.getCurrentRow();
        System.out.println("Libelle List : "+currentList.getAttribute("Libelle"));*/
        //getCountFilEcParcoursMaqAn
        try {
            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUe");
            opcountmaq.getParamsMap()
                .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            opcountmaq.getParamsMap().put("sem_id", getSemestre());
            filUe_number = (Integer) opcountmaq.execute();
            //System.out.println("filUe_number : "+filUe_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEc");
            opcountmaq.getParamsMap()
                .put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            opcountmaq.getParamsMap().put("an_id", getAnnee());
            opcountmaq.getParamsMap().put("sem_id", getSemestre());
            filEc_number = (Integer) opcountmaq.execute();
            //System.out.println("filEc_number : " + filEc_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            OperationBinding opiscc = bindings.getOperationBinding("isCCExiste");
            opiscc.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            cc_number = (Integer) opiscc.execute();
            //System.out.println("cc_number : " + cc_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if (null != currentPM) {
            //System.out.println("id_hs "+getId_hs());
            /*System.out.println("Parcours id "+currentR.getAttribute("IdNiveauFormationParcours").toString());*/
            try {
                realpath = getContext().getResource("/reports/").getPath();
                //System.out.println("Path to reports : "+realpath);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
            Map m = new HashMap();
            m.put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            m.put("SUBREPORT_DIR", realpath);
            m.put("annee_univers", getAnnee());
            m.put("id_hs", getId_hs());
            m.put("numero", this.getInputEtudiant().getValue().toString());
            m.put("id_semestre", getSemestre());
            m.put("id_calendrier", getCalendrier());
            //System.out.println("cal : "+getCalendrier());
            //System.out.println("sem : "+getSemestre());
            try {
                if (20 > (filUe_number+filEc_number)) {
                    if(0 == cc_number){
                        report_name_first_sem = "releves_standard_smstre_fmpo_entete_indiv.jasper";
                        report_name_last_sem = "releves_standard_smstre_last_fmpo_entete_indiv.jasper";
                    }else{
                        report_name_first_sem = "releves_standard_smstre_ecole_entete_indiv.jasper";
                        report_name_last_sem = "releves_standard_smstre_last_ecole_entete_indiv.jasper";
                    }
                } else {
                    //les releves semestre avec plus de 15 ec
                    if(0 == cc_number){
                        report_name_first_sem = "releves_standard_smstre_fmpo_entete1_indiv.jasper";
                        report_name_last_sem = "releves_standard_smstre_last_fmpo_entete1_indiv.jasper";
                    }else{
                        report_name_first_sem = "releves_standard_smstre_ecole_entete1_indiv.jasper";
                        report_name_last_sem = "releves_standard_smstre_last_ecole_entete1_indiv.jasper";
                    }
                }
                //System.out.println("report_name_first_sem " + report_name_first_sem);
                //System.out.println("report_name_last_sem " + report_name_last_sem);
                //verify if is_[closed/delibarate/published] semestre before run report
                Integer is_deliberated=0;
                try{
                    OperationBinding is_sem_delib = bindings.getOperationBinding("isDelibratedSemestre");
                is_sem_delib.getParamsMap()
                    .put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                is_sem_delib.getParamsMap().put("calendrier", getCalendrier());
                is_deliberated = (Integer) is_sem_delib.execute();
                }catch(Exception e){
                    System.out.println(e);
                }
                //System.out.println("is_deliberated : "+is_deliberated);
                if (0 == is_deliberated) {
                    RichPopup popup = this.getPopupSemNotDelib();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                } else {
                    if (0 !=  (getSemestre() % 2)) {
                        //System.out.println("report_name_first_sem : "+report_name_first_sem);
                        runReport(report_name_first_sem, m,
                                  "releves_semestre_" + getSemestre() + "_" +
                        this.getInputEtudiant().getValue().toString());
                    } else {
                        try {
                            OperationBinding is_an_delib = bindings.getOperationBinding("IsDelibarateAn");
                            is_an_delib.getParamsMap().put("parcours_maq", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                            is_an_delib.getParamsMap().put("session_id", getId_session());
                            Integer is_an_del = (Integer) is_an_delib.execute();
                            //System.out.println("is_an_del : "+is_an_del);
                            if (0 != is_an_del) {
                                //System.out.println("report_name_last_sem : " + report_name_last_sem);
                                runReport(report_name_last_sem, m,
                                          "releves_semestre_" + getSemestre() + "_" +
                                    this.getInputEtudiant().getValue().toString());
                            } else {
                                RichPopup popup = this.getPopupAnNotDelib();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                popup.show(hints);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setTabDetAn(RichTable tabDetAn) {
        this.tabDetAn = tabDetAn;
    }

    public RichTable getTabDetAn() {
        return tabDetAn;
    }

    public void setTabDetSem(RichTable tabDetSem) {
        this.tabDetSem = tabDetSem;
    }

    public RichTable getTabDetSem() {
        return tabDetSem;
    }

    public void setPanColDetAn(RichPanelCollection panColDetAn) {
        this.panColDetAn = panColDetAn;
    }

    public RichPanelCollection getPanColDetAn() {
        return panColDetAn;
    }

    public void setPanColDetSem(RichPanelCollection panColDetSem) {
        this.panColDetSem = panColDetSem;
    }

    public RichPanelCollection getPanColDetSem() {
        return panColDetSem;
    }

    public void setPanDetEtd(RichPanelGroupLayout panDetEtd) {
        this.panDetEtd = panDetEtd;
    }

    public RichPanelGroupLayout getPanDetEtd() {
        return panDetEtd;
    }

    public void setPrcrs_maq(Long prcrs_maq) {
        this.prcrs_maq = prcrs_maq;
    }

    public Long getPrcrs_maq() {
        return prcrs_maq;
    }

    @SuppressWarnings("unchecked")
    public void ReleveIndivAnClicked(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        String realpath = null;
        Integer filUe_number = 0;
        Integer filEc_number = 0;
        try {
            realpath = getContext().getResource("/reports/").getPath();
            //System.out.println("path : "+realpath);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(sessionFlowScope.get("releve_an_indiv_name"));
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        Map m = new HashMap();
        m.put("parcours", currentPM.getAttribute("IdParcoursMaquetAnnee"));
        m.put("SUBREPORT_DIR", realpath);
        m.put("annee_univers", getAnnee());
        m.put("id_hs", getId_hs());
        m.put("numero", this.getInputEtudiant().getValue().toString());
        String rlv_name = "";
        //System.out.println("numero : "+ this.getInputEtudiant().getValue().toString()+" pma : "+currentPM.getAttribute("IdParcoursMaquetAnnee")+" id_hs : "+ getId_hs());
        try {
            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilUeMaq");
            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            filUe_number = (Integer) opcountmaq.execute();
            //System.out.println("filEc_numbermaq : "+filEc_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        //getCountFilEcParcoursMaqAn
        try {
            OperationBinding opcountmaq = bindings.getOperationBinding("getCountFilEcMaq");
            opcountmaq.getParamsMap().put("prcrs_id", currentPM.getAttribute("IdParcoursMaquetAnnee"));
            filEc_number = (Integer) opcountmaq.execute();
            //System.out.println("filEc_numbermaq : "+filEc_number);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
            op_get_sigle.getParamsMap().put("str_id", getStr());
            op_get_sigle.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        DCIteratorBinding iterSigle = (DCIteratorBinding) bindings.get("StructureAttestationIterator");
        Row currentStr = iterSigle.getCurrentRow();
        //System.out.println("Sigle : "+currentStr.getAttribute("Sigle").toString());
        if (currentStr.getAttribute("Etab").toString().toUpperCase().matches("UCAD") && 
                            (currentStr.getAttribute("Sigle").toString().toUpperCase().matches("FST"))) {
            if (30 > (filUe_number + filEc_number)) {
                rlv_name = "releves_annuel_indiv_bis_secure";
            } else if (35 > (filUe_number + filEc_number)) {
                rlv_name = "releves_annuel_indiv_bis_1_secure";
            } else if (40 > (filUe_number + filEc_number)) {
                rlv_name = "releves_annuel_indiv_bis_1_secure_1_1";
            }else{
                rlv_name = "releves_annuel_indiv_bis_1_secure_1_1_1";
            }
            
            
        } else {
            if (30 > (filUe_number + filEc_number)) {
                rlv_name = "releves_annuel_indiv_bis";
            }  
            else if (35 > (filUe_number + filEc_number)) {
                rlv_name = "releves_annuel_indiv_bis_1";
            }
            else if (40 > (filUe_number + filEc_number)){
                rlv_name = "releves_annuel_indiv_bis_1_1";
            }
            else {
                rlv_name = "releves_annuel_indiv_bis_1_1_1";
            }
        }
        System.out.println("rlv_name : "+rlv_name);
        /*try {
            runReport(rlv_name + ".jasper", m, "releves");
        } catch (Exception e) {
            System.out.println(e);
        }*/
        
        try {
            runReport(rlv_name + ".jasper", m,"releve_" +this.getInputEtudiant().getValue().toString());
        } catch (Exception e) {
           System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void OnreleveAnIndiv(ActionEvent actionEvent) {
        // Add event code here...
        sessionFlowScope.put("releve_an_indiv_name", "releves_" +this.getInputEtudiant().getValue().toString());
        //System.out.println("report name : "+"releves_" +this.getInputEtudiant().getValue().toString());
    }

    public void setStr(Long str) {
        this.str = str;
    }

    public Long getStr() {
        return str;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setId_session(Long id_session) {
        this.id_session = id_session;
    }

    public Long getId_session() {
        return id_session;
    }

    @SuppressWarnings("unchecked")
    public void onMaquetteChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        try{
            OperationBinding is_sem_delib = bindings.getOperationBinding("isDelibratedSemestre");
            is_sem_delib.getParamsMap()
                .put("parcours",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
            is_sem_delib.getParamsMap().put("calendrier", getCalendrier());
            Integer is_deliberated = (Integer) is_sem_delib.execute();
            //System.out.println("is_deliberated "+is_deliberated);
            //System.out.println("prcrs_maq : "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
            if(0 == is_deliberated){
                this.getBtnReleves().setDisabled(true);
                this.getBtnAttestation().setDisabled(true);
            }else{
                this.getBtnReleves().setDisabled(false);
            try{
                OperationBinding isDelibAn = bindings.getOperationBinding("IsDelibarateAn");
                isDelibAn.getParamsMap().put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                isDelibAn.getParamsMap().put("session_id",  getId_session());
                Integer isDelib = (Integer) isDelibAn.execute();
                //System.out.println("isDelib : "+isDelib);
                if(0 != isDelib){
                    try{
                        OperationBinding is_diplomante = bindings.getOperationBinding("isDiplomante");
                        is_diplomante.getParamsMap()
                            .put("parcours_maq",  currentPM.getAttribute("IdParcoursMaquetAnnee"));
                        Integer is_diplom = (Integer) is_diplomante.execute();
                        //System.out.println("is_diplom "+is_diplom);
                        if(0 == is_diplom){
                            this.getBtnAttestation().setDisabled(true);
                        }else{
                            this.getBtnAttestation().setDisabled(false);
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    this.getBtnAttestation().setDisabled(true);
                }
                }catch(Exception e){
                System.out.println(e);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnAttestation());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnReleves());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void onSemestreChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
    }

    public void setPanGrpSem(RichPanelGroupLayout panGrpSem) {
        this.panGrpSem = panGrpSem;
    }

    public RichPanelGroupLayout getPanGrpSem() {
        return panGrpSem;
    }

    @SuppressWarnings("unchecked")
    public void OnReleveActionLister(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iterR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .get("ParcoursIterator");
        Row currentR = iterR.getCurrentRow();
        String file_name = null;
        if ((this.getInputIndiv().getValue() == null) || (this.getInputIndiv().getValue().equals(false))) {
            String doc_name ="releves_"+(getId_session()==1 ?"première_session_":"deuxième_session");
            String niveauForm =currentR.getAttribute("Niveauformation").toString();
            String cohorte =currentR.getAttribute("Cohorte").toString();
            doc_name=doc_name+niveauForm+" "+cohorte+" ";
            if(currentR.getAttribute("Specialite")!=null){
                doc_name=doc_name+currentR.getAttribute("Specialite").toString()+" ";
               }
            if(currentR.getAttribute("Opt")!=null){
                doc_name=doc_name+currentR.getAttribute("Opt").toString()+" ";
            }
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            //System.out.println(dateTime.format(formatter));
            file_name = doc_name+dateTime.format(formatter)+".pdf";
            //System.out.println("file_name "+file_name);
        }else{
            String doc_name ="releves_"+(getId_session()==1 ?"première_session_":"deuxième_session");
            String niveauForm =currentR.getAttribute("Niveauformation").toString();
            String cohorte =currentR.getAttribute("Cohorte").toString();
            doc_name=doc_name+niveauForm+" "+cohorte+" ";
            if(currentR.getAttribute("Specialite")!=null){
                doc_name=doc_name+currentR.getAttribute("Specialite").toString()+" ";
               }
            if(currentR.getAttribute("Opt")!=null){
                doc_name=doc_name+currentR.getAttribute("Opt").toString()+" ";
            }
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            //System.out.println(dateTime.format(formatter));
            file_name = doc_name+dateTime.format(formatter)+".pdf";
            //System.out.println("file_name "+file_name);
        }
        sessionFlowScope.put("file_name", file_name);
    }

    public void setBtnAttestationIndiv(RichButton btnAttestationIndiv) {
        this.btnAttestationIndiv = btnAttestationIndiv;
    }

    public RichButton getBtnAttestationIndiv() {
        return btnAttestationIndiv;
    }

    public void onAttestationIndivAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings("unchecked")
    public void OnAttestationIndivClicked(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        Map m = new HashMap();
        String realpath = null;
        String att_name = null;
        try {
            realpath = getContext().getResource("/reports/").getPath();
            //System.out.println("Path to reports : "+realpath);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }

        DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentR = iterP.getCurrentRow();
        if (null != currentR) {
            try {
                //System.out.println("IdFormation : "+currentR.getAttribute("IdFormation").toString());
                OperationBinding op_update_res = bindings.getOperationBinding("UpdateResultatCycle");
                op_update_res.getParamsMap()
                    .put("code_fr", Long.parseLong(currentR.getAttribute("IdFormation").toString()));
                op_update_res.getParamsMap().put("parcours_maq", getPrcrs_maq());
                op_update_res.getParamsMap().put("annee", getAnnee());
                
                op_update_res.getParamsMap().put("utilisateur", getUtilisateur());
                op_update_res.execute();
                //System.out.println("ResultatCycle Updated");
            } catch (Exception e) {
                System.out.println(e);
            }
            m.put("SUBREPORT_DIR", realpath);
            m.put("annee_univers", getAnnee());
            m.put("id_hs", getId_hs());
            m.put("numero", this.getInputEtudiant().getValue().toString());
            //System.out.println("id_hs "+getId_hs());
        }

        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnIterator");
        Row currentPM = iterPM.getCurrentRow();
        if (null != currentPM) {
            m.put("parcours", Long.parseLong(currentPM.getAttribute("IdParcoursMaquetAnnee").toString()));
            //System.out.println("Parcours Maquette : " + currentPM.getAttribute("IdParcoursMaquetAnnee"));
            Long attSpec = new Long(0);
            try {
                OperationBinding op_attSpec = bindings.getOperationBinding("isPrcrsAttSpecExist");
                op_attSpec.getParamsMap().put("parcours_maq", currentPM.getAttribute("IdParcoursMaquetAnnee"));
                attSpec = (Long) op_attSpec.execute();
                //System.out.println("attSpec : " + attSpec);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (0 != attSpec) {
                try {
                    runReport("attestation_speciale.jasper", m, "Attestation");
                } catch (Exception e) {
                System.out.println(e);
                }
            } else {
                try {
                    OperationBinding op_get_sigle = bindings.getOperationBinding("getSigleStructure");
                    op_get_sigle.getParamsMap().put("str_id", getStr());
                    op_get_sigle.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                DCIteratorBinding iterSigle = (DCIteratorBinding) bindings.get("StructureAttestationIterator");
                Row currentStr = iterSigle.getCurrentRow();
                //if(10 == Integer.parseInt(currentStr.getAttribute("IdTypeSection").toString())){
                    if(currentStr.getAttribute("Etab").toString().toUpperCase().matches("UCAD") && 
                                (currentStr.getAttribute("Sigle").toString().toUpperCase().matches("FST"))){
                        att_name = "attestation_faculte_secure_indiv";
                    }else{
                        att_name = "attestation_faculte_indiv";
                    }
                /*}else{
                    att_name = "attestation_stand_" + currentStr.getAttribute("Sigle").toString();
                }*/
                //System.out.println("Attestation Name : " + att_name);
                try {
                    runReport(att_name + ".jasper", m, "Attestation");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
