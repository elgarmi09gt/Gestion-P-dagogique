package view.beans.integrationfr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.entities.java.NiveauSection;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.NavigatableRowIterator;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import view.beans.admin.AdministrationBean;
import view.beans.entities.Parcours;
//import view.beans.entities.NiveauSection;

public class IntegrationFormationBean {
    //private List<ScolUe> scolUeList;
    private static final long R_FR = new Long(61);
    private static final long R_NIV_FR = new Long(62);
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Long anne_univers = Long.parseLong(sessionScope.get("id_annee").toString());
    //private Long semestre = Long.parseLong(sessionScope.get("id_smstre").toString());
    private Long str_id = Long.parseLong(sessionScope.get("id_str").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    //private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    private RichPanelHeader scolmaqpan;
    private Long semeste_first_id;
    private Long semeste_last_id;
    private RichPanelGroupLayout scolpaninsc;
    private RichButton btnimportmaq;
    private RichButton btnimprtetd;
    private RichPanelGroupLayout panBtn;
    private RichOutputFormatted insc_first;
    private RichPopup popSuccess;
    private RichPopup popFailed;
    private RichPopup popGradeSemNotExist;
    private RichPopup popNivOrGradeNotExiste;
    private RichPopup popImportFailed;
    private RichPanelGroupLayout panIntegratedNivFr;
    private RichPanelGroupLayout panGrpNotIntegNivFr;
    private RichPanelCollection panColNotIntegNivFr;
    private RichTable tabNotIntegNivFr;
    private RichPanelCollection panColIntegNivFr;
    private RichTable tabIntegNivFr;
    private RichSelectBooleanCheckbox is_form;
    private RichShowDetailItem is_prcrs;
    private HtmlSelectBooleanCheckbox is_prcrsFr;
    private RichPanelGroupLayout panGrpPrcrsNotIntegrated;
    private RichPanelCollection panColPrcrsNotIntegrated;
    private RichTable tabPrcrsNotIntegrated;
    private RichSelectBooleanCheckbox isPrcrs_Form;
    private RichPanelGroupLayout panGrpPrcrsIntegrated;
    private RichPanelCollection panColPrcrsIntegrated;
    private RichTable tabPrcrsIntegrated;
    private RichPopup popDfSection;
    private RichPopup popNoPrcrsSelected;
    private RichPopup popNoPrcrsNotInt;

    public IntegrationFormationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNivSecChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        this.getScolmaqpan().setVisible(false);
        this.getScolpaninsc().setVisible(false);
        this.getInsc_first().setVisible(false);
        //this.getBtnimprtetd().setVisible(false);
        //this.getBtnimportmaq().setVisible(false);
    }

    @SuppressWarnings("unchecked")
    public void OnGetMaquetteNivSecAction(ActionEvent actionEvent) {
        // Add event code here...
        Boolean is_resp_fil_ue = true;
        Boolean is_resp_fil_ec = true;
        Long niv_id = new Long(0);
        Long gf_id = new Long(0);
        BindingContainer bindings = getBindings();
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciterfr = (DCIteratorBinding) bindings.get("ScolFormationROVO1Iterator");
        Row currentFr = dciterfr.getCurrentRow();
        /*DCIteratorBinding dciternivfr = (DCIteratorBinding) bindings.get("ScolFormationROVO1Iterator");
        Row CurrentNivFormation = dciterfr.getCurrentRow();*/

        DCIteratorBinding dciternivfr = (DCIteratorBinding) bindings.get("ScolNivFormationROVO1Iterator");
        Row currentnivFr = dciternivfr.getCurrentRow();
        //Operation to get ue list by year and niv sec
        OperationBinding lesFilUes = BindingContext.getCurrent()
                                                   .getCurrentBindingsEntry()
                                                   .getOperationBinding("GetScolFilUe");
        lesFilUes.getParamsMap().put("an_id", getAnne_univers());
        lesFilUes.getParamsMap().put("niv_form", Long.parseLong(currentnivFr.getAttribute("CodeNivSec").toString()));
        lesFilUes.execute();
        try {
            OperationBinding opniv = bindings.getOperationBinding("getNiveau");
            opniv.getParamsMap().put("anc_code", currentnivFr.getAttribute("NiveauCode"));
            niv_id = (Long) opniv.execute();

            OperationBinding opgf = bindings.getOperationBinding("getgradeFormation");
            opgf.getParamsMap().put("anc_code", currentFr.getAttribute("CodeGradeDiplome"));
            gf_id = (Long) opgf.execute();
            if ((0 != niv_id) && (0 != gf_id)) {
                //getsemestreBygradeSemestre
                OperationBinding opgs = bindings.getOperationBinding("getsemestregrade");
                opgs.getParamsMap().put("niv_id", niv_id);
                opgs.getParamsMap().put("gf_id", gf_id);
                opgs.execute();
                DCIteratorBinding itersem = (DCIteratorBinding) bindings.get("getsemestregradeIterator");
                Row currentsem = itersem.getCurrentRow();
                if (null != currentsem) {
                    Long currtsem = Long.parseLong(itersem.getCurrentRow()
                                                          .getAttribute("IdSemestre")
                                                          .toString());
                    if (currtsem % 2 == 0) {
                        setSemeste_first_id(currtsem - 1);
                        setSemeste_last_id(currtsem);
                    } else {
                        setSemeste_first_id(currtsem);
                        setSemeste_last_id(currtsem + 1);
                    }
                    sessionScope.put("first_semestre", getSemeste_first_id());
                    sessionScope.put("last_semestre", getSemeste_last_id());
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        //System.out.println("first_semestre : "+getSemeste_first_id()+" last_semestre : "+getSemeste_last_id());
        //Nombre d'atudiants inscritent pour les deux semestres
        OperationBinding lesEtudiantsFirst = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("ScolStudentDefInsc");
        lesEtudiantsFirst.getParamsMap().put("an_id", getAnne_univers()); //new Long(31)
        lesEtudiantsFirst.getParamsMap()
            .put("niv_form", Long.parseLong(currentnivFr.getAttribute("CodeNivSec").toString())); //new Long(744)
        lesEtudiantsFirst.getParamsMap().put("sem_id", getSemeste_first_id()); //new Long(1)
        lesEtudiantsFirst.getParamsMap()
            .put("fr_id", Long.parseLong(currentFr.getAttribute("CodeFormation").toString())); //new Long(327)
        lesEtudiantsFirst.execute();
        DCIteratorBinding iterfirst = (DCIteratorBinding) bindings.get("ScolStudentDefInscIterator");
        sessionScope.put("inscrit_first_sem", iterfirst.getEstimatedRowCount());
        Long nbre_f = iterfirst.getEstimatedRowCount();
        OperationBinding lesEtudiantsLast = BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .getOperationBinding("ScolStudentDefInsc");
        lesEtudiantsLast.getParamsMap().put("an_id", getAnne_univers()); //new Long(31)
        lesEtudiantsLast.getParamsMap()
            .put("niv_form", Long.parseLong(currentnivFr.getAttribute("CodeNivSec").toString())); //new Long(744)
        lesEtudiantsLast.getParamsMap().put("sem_id", getSemeste_last_id()); //new Long(2)
        lesEtudiantsLast.getParamsMap()
            .put("fr_id", Long.parseLong(currentFr.getAttribute("CodeFormation").toString()));
        lesEtudiantsLast.execute();
        DCIteratorBinding iterlast = (DCIteratorBinding) bindings.get("ScolStudentDefInscIterator");
        Long nbre_l = iterlast.getEstimatedRowCount();
        sessionScope.put("inscrit_last_sem", iterlast.getEstimatedRowCount());
        this.getScolmaqpan().setVisible(true);
        this.getScolpaninsc().setVisible(true);
        this.getInsc_first().setVisible(true);
        if ((nbre_l + nbre_f) > 0) {
            this.getBtnimprtetd().setVisible(true);
        }
        //Verification si les responsables des ues et ecs existent et bloquer l'import si necessaire
        DCIteratorBinding iterfilUe = (DCIteratorBinding) bindings.get("ScolFilUeIterator");
        if (iterfilUe.getEstimatedRowCount() > 0) {
            NavigatableRowIterator nv = iterfilUe.getNavigatableRowIterator();
            RowSetIterator rsi = iterfilUe.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
                if (false == is_resp_fil_ue || false == is_resp_fil_ec) {
                    break;
                }
                Row nextRow = rsi.next();
                if (null == nextRow.getAttribute("ResponsableId")) {
                    is_resp_fil_ue = false;
                    break;
                } else {
                    DCIteratorBinding iterfilEc = (DCIteratorBinding) bindings.get("ScolFilEcIterator");
                    if (iterfilEc.getEstimatedRowCount() > 0) {
                        RowSetIterator rsifec = iterfilEc.getViewObject().createRowSetIterator(null);
                        while (rsifec.hasNext()) {
                            Row nextRowec = rsifec.next();
                            if (null == nextRowec.getAttribute("ResponsableId")) {
                                is_resp_fil_ec = false;
                                break;
                            }
                        }
                    } else {
                        is_resp_fil_ec = false;
                    }
                }
                nv.next();
            }
            rsi.closeRowSetIterator();
        } else {
            is_resp_fil_ue = false;
        }
        //System.out.println("is_resp_fil_ue : " + is_resp_fil_ue + " is_resp_fil_ec : " + is_resp_fil_ec);
        if ((true == is_resp_fil_ue) && (true == is_resp_fil_ec)) {
            this.getBtnimportmaq().setDisabled(false);
            this.getBtnimprtetd().setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScolmaqpan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScolpaninsc());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanBtn());
    }

    /* @SuppressWarnings("unchecked")
    public List<ScolUe> getScolFilUe(Long annee, Long nivForm) {
        scolUeList = new ArrayList();
        OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetScolFilUe");
        lesEtudiants.getParamsMap().put("an_id", annee);
        lesEtudiants.getParamsMap().put("niv_form", nivForm);
        lesEtudiants.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ScolFilUeIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return scolUeList;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //scolUeList.add(new ScolUe(nextRow.getAttribute("arg0").toString(),));
            //etudiantlists.add(new Etudiant(nextRow.getAttribute("Numero").toString(),
             //                              nextRow.getAttribute("PrenomNom").toString()));
        }
        //System.out.print(etudiantlists.toString());
        return scolUeList;
    }*/
    public void setAnne_univers(Long anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Long getAnne_univers() {
        return anne_univers;
    }

    public void setScolmaqpan(RichPanelHeader scolmaqpan) {
        this.scolmaqpan = scolmaqpan;
    }

    public RichPanelHeader getScolmaqpan() {
        return scolmaqpan;
    }

    public void OnDepartementValueChange(ValueChangeEvent valueChangeEvent) {
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        this.getScolmaqpan().setVisible(false);
        this.getScolpaninsc().setVisible(false);
        //this.getBtnimprtetd().setVisible(false);
        //this.getBtnimportmaq().setVisible(false);
    }

    public void OnFormationValueChange(ValueChangeEvent valueChangeEvent) {
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        this.getScolmaqpan().setVisible(false);
        this.getScolpaninsc().setVisible(false);
        this.getInsc_first().setVisible(false);
        //this.getBtnimprtetd().setVisible(false);
        //this.getBtnimportmaq().setVisible(false);
    }

    /*public void setSemestre(Long semestre) {
        this.semestre = semestre;
    }

    public Long getSemestre() {
        return semestre;
    }*/

    public void setSemeste_first_id(Long semeste_first_id) {
        this.semeste_first_id = semeste_first_id;
    }

    public Long getSemeste_first_id() {
        return semeste_first_id;
    }

    public void setSemeste_last_id(Long semeste_last_id) {
        this.semeste_last_id = semeste_last_id;
    }

    public Long getSemeste_last_id() {
        return semeste_last_id;
    }

    public void setScolpaninsc(RichPanelGroupLayout scolpaninsc) {
        this.scolpaninsc = scolpaninsc;
    }

    public RichPanelGroupLayout getScolpaninsc() {
        return scolpaninsc;
    }

    public void setBtnimportmaq(RichButton btnimportmaq) {
        this.btnimportmaq = btnimportmaq;
    }

    public RichButton getBtnimportmaq() {
        return btnimportmaq;
    }

    public void setBtnimprtetd(RichButton btnimprtetd) {
        this.btnimprtetd = btnimprtetd;
    }

    public RichButton getBtnimprtetd() {
        return btnimprtetd;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void OnImportMaquetteNivSectionClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        HashMap<Long, String> filUe = new HashMap<Long, String>(); //pour les inscriptions filue
        HashMap<String, String> userStruct = new HashMap<String, String>(); // matricule + etablissement
        HashMap<Long, String> RespfilUe = new HashMap<Long, String>(); //pour les responsables Ue
        HashMap<Long, String> RespfilEc = new HashMap<Long, String>(); //Pour les responsables Ec
        Long form_id = new Long(0);
        Long niv_form_id = new Long(0);
        Long niv_form_crt_id = new Long(0);
        Long niv_form_prcrs_id = new Long(0);
        Long mq_id = new Long(0);
        Long niv_mq_id = new Long(0);
        Long prcrs_mq_id = new Long(0);
        Long ue_id = new Long(0);
        Long ec_id = new Long(0);
        Long fil_ue_id = new Long(0);
        Long fil_ec_id = new Long(0);
        String Lib = null;
        Long user_id = new Long(0);
        Long niv_id = new Long(0);
        Long gf_id = new Long(0);
        HashMap<Integer, String> err = new HashMap<Integer, String>();
        String msg = "";
        DCIteratorBinding iterFormation = (DCIteratorBinding) bindings.get("ScolFormationROVO1Iterator");
        Row CurrentFormation = iterFormation.getCurrentRow();
        if (CurrentFormation != null) {
            try {
                OperationBinding intgrateForm = bindings.getOperationBinding("integrateForm");
                intgrateForm.getParamsMap().put("anc_code", CurrentFormation.getAttribute("CodeFormation"));
                intgrateForm.getParamsMap().put("libelle", CurrentFormation.getAttribute("LibelleLong"));
                intgrateForm.getParamsMap().put("departement", CurrentFormation.getAttribute("CodeDepartement"));
                intgrateForm.getParamsMap().put("type_fr", CurrentFormation.getAttribute("TypeFormation"));
                intgrateForm.getParamsMap().put("payante", CurrentFormation.getAttribute("Payante"));
                intgrateForm.getParamsMap().put("presentielle", CurrentFormation.getAttribute("Presentielle"));
                intgrateForm.getParamsMap().put("grade_fr", CurrentFormation.getAttribute("CodeGradeDiplome"));
                intgrateForm.getParamsMap().put("mention", CurrentFormation.getAttribute("CodeMention"));
                intgrateForm.getParamsMap().put("ouvert", CurrentFormation.getAttribute("Ouvert"));
                intgrateForm.getParamsMap().put("valide", CurrentFormation.getAttribute("Valide"));
                intgrateForm.getParamsMap()
                    .put("professionalisante", CurrentFormation.getAttribute("Professionnalisante"));
                intgrateForm.getParamsMap().put("cycl", CurrentFormation.getAttribute("CycleCode"));
                intgrateForm.getParamsMap().put("reconnue", CurrentFormation.getAttribute("FormationReconnue"));
                intgrateForm.getParamsMap().put("obs", CurrentFormation.getAttribute("Observations"));
                intgrateForm.getParamsMap().put("spec", CurrentFormation.getAttribute("IdSpecialite"));
                intgrateForm.getParamsMap().put("opt", CurrentFormation.getAttribute("IdOption"));
                intgrateForm.getParamsMap().put("utilisateur", getUtilisateur());
                form_id = (Long) intgrateForm.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (0 != form_id) {
                try {
                    //createOrUpdateUserForm : donner l'acces à l'admin
                    OperationBinding opUserForm = bindings.getOperationBinding("createOrUpdateUserForm");
                    opUserForm.getParamsMap().put("user_", 85);
                    opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                    opUserForm.getParamsMap().put("form_", form_id);
                    opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                    opUserForm.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                DCIteratorBinding iterNivFormation = (DCIteratorBinding) bindings.get("ScolNivFormationROVO1Iterator");
                Row CurrentNivFormation = iterNivFormation.getCurrentRow();
                if (CurrentNivFormation != null) {
                    try {
                        OperationBinding intgrateNivForm = bindings.getOperationBinding("integrateNiveauForm");
                        intgrateNivForm.getParamsMap().put("anc_code", CurrentNivFormation.getAttribute("CodeNivSec"));
                        intgrateNivForm.getParamsMap().put("libelle", CurrentNivFormation.getAttribute("LibelleLong"));
                        intgrateNivForm.getParamsMap().put("abrev", CurrentNivFormation.getAttribute("LibelleCourt"));
                        intgrateNivForm.getParamsMap().put("a_select", CurrentNivFormation.getAttribute("ASelection"));
                        intgrateNivForm.getParamsMap()
                            .put("aut_permise", CurrentNivFormation.getAttribute("AutorisationPermise"));
                        intgrateNivForm.getParamsMap()
                            .put("presentielle", CurrentNivFormation.getAttribute("Presentielle"));
                        intgrateNivForm.getParamsMap()
                            .put("nbr_insc_permis", CurrentNivFormation.getAttribute("NbreInsPermises"));
                        intgrateNivForm.getParamsMap()
                            .put("diplomante", CurrentNivFormation.getAttribute("Diplomante"));
                        intgrateNivForm.getParamsMap()
                            .put("formation", CurrentNivFormation.getAttribute("CodeFormation"));
                        intgrateNivForm.getParamsMap().put("ouvert", CurrentNivFormation.getAttribute("Ouvert"));
                        intgrateNivForm.getParamsMap().put("niveau", CurrentNivFormation.getAttribute("NiveauCode"));
                        intgrateNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                        niv_form_id = (Long) intgrateNivForm.execute();

                        OperationBinding opniv = bindings.getOperationBinding("getNiveau");
                        opniv.getParamsMap().put("anc_code", CurrentNivFormation.getAttribute("NiveauCode"));
                        niv_id = (Long) opniv.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (niv_form_id != 0) {
                        try {
                            //createOrUpdateUserNivForm : donner l'acces à l'admin
                            OperationBinding opUserNivForm = bindings.getOperationBinding("createOrUpdateUserNivForm");
                            opUserNivForm.getParamsMap().put("user_", 85);
                            opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                            opUserNivForm.getParamsMap().put("niv_form_", niv_form_id);
                            opUserNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                            opUserNivForm.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding intgrateNivFormCrt =
                                bindings.getOperationBinding("integrateNiveauFormCrt");
                            intgrateNivFormCrt.getParamsMap().put("niv_fr", niv_form_id);
                            intgrateNivFormCrt.getParamsMap().put("crt_code", CurrentNivFormation.getAttribute("Code"));
                            intgrateNivFormCrt.getParamsMap().put("actif", CurrentNivFormation.getAttribute("Actif"));
                            intgrateNivFormCrt.getParamsMap()
                                .put("nbr_etd", CurrentNivFormation.getAttribute("NombreEtudiant"));
                            intgrateNivFormCrt.getParamsMap().put("utilisateur", getUtilisateur());
                            niv_form_crt_id = (Long) intgrateNivFormCrt.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //System.out.println("niv_form_crt_id : " + niv_form_crt_id);

                        if (niv_form_crt_id != 0) {
                            try {
                                OperationBinding intgrateNivFormPrcrs =
                                    bindings.getOperationBinding("integrateNiveauFormPrcrs");
                                intgrateNivFormPrcrs.getParamsMap().put("fr_id", form_id);
                                intgrateNivFormPrcrs.getParamsMap().put("niv_form_crt", niv_form_crt_id);
                                intgrateNivFormPrcrs.getParamsMap()
                                    .put("spec", CurrentFormation.getAttribute("IdSpecialite"));
                                intgrateNivFormPrcrs.getParamsMap()
                                    .put("opt", CurrentFormation.getAttribute("IdOption"));
                                intgrateNivFormPrcrs.getParamsMap().put("utilisateur", getUtilisateur());
                                niv_form_prcrs_id = (Long) intgrateNivFormPrcrs.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (niv_form_prcrs_id != 0) {
                                try {
                                    OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                                    generateMaq.getParamsMap().put("niv_form_prcrt", niv_form_prcrs_id);
                                    generateMaq.getParamsMap().put("str_id", getStr_id());
                                    generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                    mq_id = (Long) generateMaq.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                //System.out.println("niv_form_prcrs_id : "+niv_form_prcrs_id);
                                if (mq_id != 0) {
                                    try {
                                        OperationBinding opNivFormMaqAn =
                                            bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                                        opNivFormMaqAn.getParamsMap().put("niv_form", niv_form_id);
                                        opNivFormMaqAn.getParamsMap().put("mq", mq_id);
                                        opNivFormMaqAn.getParamsMap().put("an", getAnne_univers());
                                        opNivFormMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                                        niv_mq_id = (Long) opNivFormMaqAn.execute();

                                        OperationBinding opPrcrsMaqAn =
                                            bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                                        opPrcrsMaqAn.getParamsMap().put("parcours", niv_form_prcrs_id);
                                        opPrcrsMaqAn.getParamsMap().put("mq", mq_id);
                                        opPrcrsMaqAn.getParamsMap().put("an", getAnne_univers());
                                        opPrcrsMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                                        prcrs_mq_id = (Long) opPrcrsMaqAn.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    if (niv_mq_id != 0 && prcrs_mq_id != 0) {
                                        sessionScope.put("pma_id", prcrs_mq_id);
                                        DCIteratorBinding iterfilue =
                                            (DCIteratorBinding) bindings.get("ScolFilUeIterator");
                                        //Row current = iterfilue.getCurrentRow();
                                        NavigatableRowIterator nv = iterfilue.getNavigatableRowIterator();
                                        RowSetIterator rsi = iterfilue.getViewObject().createRowSetIterator(null);
                                        while (rsi.hasNext()) {
                                            Row nextRow = rsi.next();
                                            try {
                                                Lib = nextRow.getAttribute("LibLongUe").toString();
                                                if (Lib.contains("$")) {
                                                    String parts[] = Lib.split("[$]");
                                                    Lib = parts[0]; // i want to strip part after  +
                                                }
                                                //System.out.println("Lib : " + Lib);
                                                OperationBinding opUe =
                                                    bindings.getOperationBinding("createOrUpdateUe");
                                                opUe.getParamsMap().put("anc_code", nextRow.getAttribute("CodeUe"));
                                                opUe.getParamsMap().put("libelle", Lib);
                                                opUe.getParamsMap().put("prefix", nextRow.getAttribute("Prefixe"));
                                                opUe.getParamsMap().put("com", nextRow.getAttribute("Commentaire"));
                                                opUe.getParamsMap().put("utilisateur", getUtilisateur());
                                                ue_id = (Long) opUe.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            if (ue_id != 0) {
                                                //insertFilUeSem
                                                try {
                                                    OperationBinding opFilUe =
                                                        bindings.getOperationBinding("createOrUpdateFilUe");
                                                    opFilUe.getParamsMap()
                                                        .put("anc_code", nextRow.getAttribute("CodeFiliereUeSemestre"));
                                                    opFilUe.getParamsMap().put("ue_id", ue_id);
                                                    opFilUe.getParamsMap()
                                                        .put("sem_id",
                                                             Long.parseLong(nextRow.getAttribute("CodeSemestre")
                                                                            .toString()));
                                                    opFilUe.getParamsMap()
                                                        .put("cat_ue_id", nextRow.getAttribute("CodeCategorieUe"));
                                                    opFilUe.getParamsMap()
                                                        .put("codif", nextRow.getAttribute("CodificationUe"));
                                                    opFilUe.getParamsMap()
                                                        .put("cred",
                                                             Long.parseLong(nextRow.getAttribute("CreditUe")
                                                                            .toString()));
                                                    opFilUe.getParamsMap()
                                                        .put("coef",
                                                             Float.parseFloat(nextRow.getAttribute("Coefue")
                                                                                                               .toString()
                                                                                                               .replace(",",
                                                                                                                        ".")));
                                                    opFilUe.getParamsMap().put("maq", mq_id);
                                                    opFilUe.getParamsMap()
                                                        .put("moy_v",
                                                             Integer.parseInt(nextRow.getAttribute("MoyenneValidation")
                                                                              .toString()));
                                                    if (null == nextRow.getAttribute("MoyenneEliminatoire")) {
                                                        opFilUe.getParamsMap().put("moy_e", 0);
                                                    } else {
                                                        opFilUe.getParamsMap()
                                                            .put("moy_e",
                                                                 Integer.parseInt(nextRow.getAttribute("MoyenneEliminatoire")
                                                                                  .toString()));
                                                    }
                                                    opFilUe.getParamsMap()
                                                        .put("nat", nextRow.getAttribute("Fondamental"));
                                                    opFilUe.getParamsMap()
                                                        .put("comp", nextRow.getAttribute("Compensable"));
                                                    opFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                                    fil_ue_id = (Long) opFilUe.execute();
                                                    if (0 != fil_ue_id) {
                                                        //recupérer les filiere ue non enregistré et faire un traitement

                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            } else {
                                                //recupérer les ues non enregistrés
                                            }
                                            //A decommenter une fois les responsables renseigner
                                            if ((0 != fil_ue_id) && (null != nextRow.getAttribute("CodeMatricule"))) {
                                                try {
                                                    /*System.out.println("Email ucad scolFilUe : " +
                                                                       nextRow.getAttribute("Emailucad"));*/
                                                    OperationBinding opUser =
                                                        bindings.getOperationBinding("createOrUpdateUser");
                                                    opUser.getParamsMap()
                                                        .put("email", nextRow.getAttribute("Emailucad").toString());
                                                    opUser.getParamsMap()
                                                        .put("struct_",
                                                             nextRow.getAttribute("CodeEtablissement").toString());
                                                    opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                                    user_id = (Long) opUser.execute();
                                                    if (0 !=
                                                        user_id) {
                                                        //System.out.println("User created : fil ue");
                                                        try {
                                                            //createOrUpdateUserForm
                                                            //System.out.println("Inserting user form fil_ue");
                                                            OperationBinding opUserForm =
                                                           bindings.getOperationBinding("createOrUpdateUserForm");
                                                       //createOrUpdateUserForm(Long user_, String role, Long form_, Integer utilisateur)
                                                       opUserForm.getParamsMap().put("user_", user_id);
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap().put("form_", form_id);
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            Long id_user_fr = (Long) opUserForm.execute();
                                                            /*if (0 != id_user_fr)
                                                                System.out.println("User form fil_ue inserted " +
                                                                                   id_user_fr);
                                                            else
                                                                System.out.println("User form fil_ue not inserted");*/
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            //createOrUpdateUserNivForm
                                                            //System.out.println("Inserting user niv form fil_ue");
                                                            OperationBinding opUserNivForm =
                                                                bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                            opUserNivForm.getParamsMap().put("user_", user_id);
                                                            opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserNivForm.getParamsMap().put("niv_form_", niv_form_id);
                                                            opUserNivForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            Long id_user_niv_fr = (Long) opUserNivForm.execute();
                                                            /*if (0 != id_user_niv_fr)
                                                                System.out.println("User niv form fil_ue inserted : " +
                                                                                   id_user_niv_fr);
                                                            else
                                                                System.out.println("User niv form fil_ue not inserted : ");*/
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            //createOrUpdateUserFilUe
                                                            //System.out.println("Inserting user fil ue fil_ue");
                                                            OperationBinding opUserFilUe =
                                                                bindings.getOperationBinding("createOrUpdateUserFilUe");
                                                            opUserFilUe.getParamsMap().put("user_", user_id);
                                                            opUserFilUe.getParamsMap().put("role", "RESPONSABLE UE");
                                                            opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                                            opUserFilUe.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            Long id_user_fil_ue = (Long) opUserFilUe.execute();
                                                            /*if (0 != id_user_fil_ue)
                                                                System.out.println("User fil ue fil_ue inserted : " +
                                                                                   id_user_fil_ue);
                                                            else
                                                                System.out.println("User fil ue fil_ue not inserted");*/
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                filUe.put(fil_ue_id,
                                                          nextRow.getAttribute("CodeFiliereUeSemestre").toString());
                                                //A decommenter
                                                /*RespfilUe.put(fil_ue_id, nextRow.getAttribute("CodeMatricule").toString());
                                            userStruct.put(nextRow.getAttribute("CodeMatricule").toString(), nextRow.getAttribute("CodeEtablissement").toString());
                                            */
                                            }

                                            DCIteratorBinding iterfilec =
                                                (DCIteratorBinding) bindings.get("ScolFilEcIterator");
                                            //System.out.println("count fil ec : " + iterfilec.getEstimatedRowCount());
                                            RowSetIterator rsie = iterfilec.getViewObject().createRowSetIterator(null);
                                            while (rsie.hasNext()) {
                                                Row nextRowFilEc = rsie.next();
                                                try {
                                                    //System.out.println("Inside while");
                                                    OperationBinding opEc =
                                                        bindings.getOperationBinding("createOrUpdateEc");
                                                    opEc.getParamsMap()
                                                        .put("anc_code", nextRowFilEc.getAttribute("CodeEc"));
                                                    opEc.getParamsMap()
                                                        .put("libelle", nextRowFilEc.getAttribute("LibLongEc"));
                                                    opEc.getParamsMap().put("utilisateur", getUtilisateur());
                                                    ec_id = (Long) opEc.execute();
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                if (ec_id != 0) {
                                                    //insertFilUeSemEc
                                                    try {
                                                        OperationBinding opFilEc =
                                                            bindings.getOperationBinding("createOrUpdateFilEc");
                                                        opFilEc.getParamsMap()
                                                            .put("anc_code",
                                                                 nextRowFilEc.getAttribute("CodeFiliereUeSemestreEc"));
                                                        opFilEc.getParamsMap().put("ec_id", ec_id);
                                                        opFilEc.getParamsMap()
                                                            .put("nat", nextRowFilEc.getAttribute("CodeNatureEc"));
                                                        opFilEc.getParamsMap()
                                                            .put("codif", nextRowFilEc.getAttribute("CodificationEc"));
                                                        opFilEc.getParamsMap()
                                                            .put("coef",
                                                                 Float.parseFloat(nextRowFilEc.getAttribute("Coefficient")
                                                                                                                        .toString()
                                                                                                                        .replace(",",
                                                                                                                                 ".")));
                                                        opFilEc.getParamsMap()
                                                            .put("moy_v",
                                                                 Float.parseFloat(nextRowFilEc.getAttribute("MoyenneValidation")
                                                                                                                         .toString()
                                                                                                                         .replace(",",
                                                                                                                                  ".")));
                                                        if (null == nextRowFilEc.getAttribute("MoyenneEliminatoire")) {
                                                            opFilEc.getParamsMap().put("moy_e", new Float(0));
                                                        } else {
                                                            opFilEc.getParamsMap()
                                                                .put("moy_e",
                                                                     Float.parseFloat(nextRowFilEc.getAttribute("MoyenneEliminatoire")
                                                                                                                             .toString()
                                                                                                                             .replace(",",
                                                                                                                                      ".")));
                                                        }
                                                        opFilEc.getParamsMap()
                                                            .put("cc",
                                                                 Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                                                                      .toString()
                                                                                                                      .replace(",",
                                                                                                                               ".")));
                                                        opFilEc.getParamsMap()
                                                            .put("ct",
                                                                 Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCt")
                                                                                                                      .toString()
                                                                                                                      .replace(",",
                                                                                                                               ".")));
                                                        opFilEc.getParamsMap().put("fus", fil_ue_id);
                                                        opFilEc.getParamsMap()
                                                            .put("hcm", nextRowFilEc.getAttribute("HeureCm"));
                                                        opFilEc.getParamsMap()
                                                            .put("htd", nextRowFilEc.getAttribute("HeureTd"));
                                                        opFilEc.getParamsMap()
                                                            .put("htp", nextRowFilEc.getAttribute("HeureTp"));
                                                        opFilEc.getParamsMap()
                                                            .put("htpe", nextRowFilEc.getAttribute("HeureTpe"));
                                                        opFilEc.getParamsMap()
                                                            .put("hs", nextRowFilEc.getAttribute("HeureStage"));
                                                        opFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                                        fil_ec_id = (Long) opFilEc.execute();
                                                        //generer les modalites de saisie en fonction des %CC et %CT
                                                        if ((0 != fil_ec_id)) {
                                                            if (0 != Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                                                  .toString()
                                                                                                  .replace(",", "."))) {
                                                                try {
                                                                    //System.out.println("Generating CC and CT");
                                                                    OperationBinding opmdecntrl =
                                                                        bindings.getOperationBinding("CreateOrUpdateModeCntrlEC");
                                                                    opmdecntrl.getParamsMap().put("fil_ec", fil_ec_id);
                                                                    opmdecntrl.getParamsMap().put("tc", 1);
                                                                    opmdecntrl.getParamsMap().put("mc", 1);
                                                                    opmdecntrl.getParamsMap().put("pma", prcrs_mq_id);
                                                                    opmdecntrl.getParamsMap().put("coef", 1);
                                                                    opmdecntrl.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    Long idcc = (Long) opmdecntrl.execute();
                                                                    //System.out.println("CC : "+idcc);

                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                            }
                                                            if (0 != Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCt")
                                                                                                  .toString()
                                                                                                  .replace(",", "."))) {
                                                                try {
                                                                    OperationBinding opmdecntrl1 =
                                                                        bindings.getOperationBinding("CreateOrUpdateModeCntrlEC");
                                                                    opmdecntrl1.getParamsMap().put("fil_ec", fil_ec_id);
                                                                    opmdecntrl1.getParamsMap().put("tc", 2);
                                                                    opmdecntrl1.getParamsMap().put("mc", 1);
                                                                    opmdecntrl1.getParamsMap().put("pma", prcrs_mq_id);
                                                                    opmdecntrl1.getParamsMap().put("coef", 1);
                                                                    opmdecntrl1.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    Long idct = (Long) opmdecntrl1.execute();
                                                                    //System.out.println("CT : "+idct);
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                            }
                                                        }
                                                        if ((0 != fil_ec_id) &&
                                                            (null != nextRowFilEc.getAttribute("CodeMatricule"))) {
                                                            try {
                                                                /*System.out.println("Email ucad ucad fil ec : " +
                                                                                   nextRowFilEc.getAttribute("Emailucad"));*/
                                                                OperationBinding opUser =
                                                                    bindings.getOperationBinding("createOrUpdateUser");
                                                                opUser.getParamsMap()
                                                                    .put("email",
                                                                         nextRowFilEc.getAttribute("Emailucad")
                                                                         .toString());
                                                                opUser.getParamsMap()
                                                                    .put("struct_",
                                                                         nextRowFilEc.getAttribute("CodeEtablissement")
                                                                         .toString());
                                                                opUser.getParamsMap()
                                                                    .put("utilisateur", getUtilisateur());
                                                                user_id = (Long) opUser.execute();
                                                                if (0 !=
                                                                    user_id) {
                                                                    //System.out.println("User fil ec created");
                                                                    try {
                                                                        //createOrUpdateUserForm
                                                                        //System.out.println("Inserting user form fil_ec");
                                                                        OperationBinding opUserForm =
                                                                       bindings.getOperationBinding("createOrUpdateUserForm");
                                                                        opUserForm.getParamsMap().put("user_", user_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserForm.getParamsMap().put("form_", form_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        //createOrUpdateUserNivForm
                                                                        OperationBinding opUserNivForm =
                                                                            bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                                        opUserNivForm.getParamsMap()
                                                                            .put("user_", user_id);
                                                                        opUserNivForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserNivForm.getParamsMap()
                                                                            .put("niv_form_", niv_form_id);
                                                                        opUserNivForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserNivForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        //createOrUpdateUserFilUe
                                                                        OperationBinding opUserFilUe =
                                                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                                                        opUserFilUe.getParamsMap()
                                                                            .put("user_", user_id);
                                                                        opUserFilUe.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserFilUe.getParamsMap()
                                                                            .put("fil_ue_", fil_ue_id);
                                                                        opUserFilUe.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserFilUe.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        //createOrUpdateUserFilEc
                                                                        OperationBinding opUserFilEc =
                                                                            bindings.getOperationBinding("createOrUpdateUserFilEc");
                                                                        opUserFilEc.getParamsMap()
                                                                            .put("user_", user_id);
                                                                        opUserFilEc.getParamsMap()
                                                                            .put("role", "RESPNSABLE EC");
                                                                        opUserFilEc.getParamsMap()
                                                                            .put("fil_ec_", fil_ec_id);
                                                                        opUserFilEc.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserFilEc.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                } else {
                                                                    msg = "Erreur utilisateur";
                                                                    sessionScope.put("msg", msg);
                                                                    RichPopup popup = this.getPopFailed();
                                                                    RichPopup.PopupHints hints =
                                                                        new RichPopup.PopupHints();
                                                                    //empty hints renders dialog in center of screen
                                                                    popup.show(hints);
                                                                }
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                                //A decommenter
                                                /*RespfilEc.put(fil_ec_id, nextRowFilEc.getAttribute("CodeMatricule").toString());
                                                userStruct.put(nextRowFilEc.getAttribute("CodeMatricule").toString(), nextRowFilEc.getAttribute("CodeEtablissement").toString());*/
                                            }
                                            rsie.closeRowSetIterator();
                                            //System.out.println("RespfilEc : " + RespfilEc);
                                            //nv.next();
                                            nv.next();
                                        }
                                        rsi.closeRowSetIterator();
                                        /* System.out.println("filUe : "+filUe);
                                    System.out.println("RespfilUe : "+RespfilUe);
                                    System.out.println("RespfilEc : "+RespfilEc);
                                   if(!RespfilUe.isEmpty()){
                                        RespfilUe.forEach((filiereUe, matricule) -> {
                                            System.out.format("Filiere Ue: %d, Matricule: %s%n", filiereUe, matricule);
                                            userStruct.forEach((mat,etab)-> {
                                                if(mat == matricule){
                                                    try {
                                                        OperationBinding opUser = bindings.getOperationBinding("createOrUpdateUser");
                                                        opUser.getParamsMap().put("matricule",matricule);
                                                        opUser.getParamsMap().put("struct_",etab);
                                                        opUser.getParamsMap().put("utilisateur",getUtilisateur());
                                                        fil_ec_id = (Long) opUser.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                            });
                                        });
                                    }*/
                                    }
                                } else {
                                    msg = "Erreur maquette";
                                    sessionScope.put("msg", msg);
                                    RichPopup popup = this.getPopFailed();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    //empty hints renders dialog in center of screen
                                    popup.show(hints);
                                }
                            } else {
                                msg = "Erreur niveau formation parcours ";
                                sessionScope.put("msg", msg);
                                RichPopup popup = this.getPopFailed();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                //empty hints renders dialog in center of screen
                                popup.show(hints);
                            }
                            try {
                                //System.out.println("Create calendrier");
                                OperationBinding opniv = bindings.getOperationBinding("getNiveau");
                                opniv.getParamsMap().put("anc_code", CurrentNivFormation.getAttribute("NiveauCode"));
                                niv_id = (Long) opniv.execute();

                                OperationBinding opgf = bindings.getOperationBinding("getgradeFormation");
                                opgf.getParamsMap().put("anc_code", CurrentFormation.getAttribute("CodeGradeDiplome"));
                                gf_id = (Long) opgf.execute();
                                if ((0 != niv_id) && (0 != gf_id)) {
                                    //getsemestreBygradeSemestre
                                    //System.out.println("niv_id : " + niv_id + " gf_id : " + gf_id);
                                    OperationBinding opgs = bindings.getOperationBinding("getsemestregrade");
                                    opgs.getParamsMap().put("niv_id", niv_id);
                                    opgs.getParamsMap().put("gf_id", gf_id);
                                    opgs.execute();
                                    DCIteratorBinding itersem =
                                        (DCIteratorBinding) bindings.get("getsemestregradeIterator");
                                    if (itersem.getCurrentRow() != null) {
                                        RowSetIterator rsie = itersem.getViewObject().createRowSetIterator(null);
                                        while (rsie.hasNext()) {
                                            Row nextRowSem = rsie.next();
                                            //System.out.println("Semestre : " + nextRowSem.getAttribute("IdSemestre"));
                                            OperationBinding is_exist =
                                                bindings.getOperationBinding("isCalendrierExist");
                                            is_exist.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                            is_exist.getParamsMap()
                                                .put("sem_id",
                                                     Long.parseLong(nextRowSem.getAttribute("IdSemestre").toString()));
                                            is_exist.getParamsMap().put("an_id", getAnne_univers());
                                            Integer nbr_cal = (Integer) is_exist.execute();
                                            if (0 ==
                                                nbr_cal) {
                                                //System.out.println("nbr_cal : " + nbr_cal);
                                                OperationBinding opnewcal =
                                               bindings.getOperationBinding("CreateOrUpdateCalendar");
                                                opnewcal.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                                opnewcal.getParamsMap()
                                                    .put("sem_id",
                                                         Long.parseLong(nextRowSem.getAttribute("IdSemestre")
                                                                        .toString()));
                                                opnewcal.getParamsMap().put("an_id", getAnne_univers());
                                                opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long cal_id = (Long) opnewcal.execute();
                                                //System.out.println("cal_id : " + cal_id);
                                                if (0 == cal_id) {
                                                    err.put(Integer.parseInt(nextRowSem.getAttribute("IdSemestre")
                                                                             .toString()), "Calendrier not created");
                                                }
                                            }
                                        }
                                        rsie.closeRowSetIterator();
                                        //System.out.println(err);
                                        if (err.isEmpty()) {
                                            RichPopup popup = this.getPopSuccess();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        } else {
                                            msg = "Erreur de calendrier";
                                            sessionScope.put("msg", msg);
                                            RichPopup popup = this.getPopFailed();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        }
                                    } else {
                                        msg = "Erreur grade semestre";
                                        sessionScope.put("msg", msg);
                                        RichPopup popup = this.getPopFailed();
                                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                        //empty hints renders dialog in center of screen
                                        popup.show(hints);
                                    }
                                } else {
                                    msg = "Erreur niveau ou grade formation";
                                    sessionScope.put("msg", msg);
                                    RichPopup popup = this.getPopFailed();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    //empty hints renders dialog in center of screen
                                    popup.show(hints);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            msg = "Erreur niveau formation cohorte";
                            sessionScope.put("msg", msg);
                            RichPopup popup = this.getPopFailed();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        }
                    } else {
                        msg = "erreur niveau formation : verifier les données";
                        sessionScope.put("msg", msg);
                        RichPopup popup = this.getPopFailed();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                }
            } else {
                msg = "erreur formation : verifier les données";
                sessionScope.put("msg", msg);
                RichPopup popup = this.getPopFailed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
        filUe.clear();
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void ontest(ActionEvent actionEvent) {
        // Add event code here...
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("A", "A");
        hm.put("B", "B");
        hm.put("A", "A");
        hm.put("C", "A");
        System.out.println(hm);
        String st = "Développement personnel $$";
        String s0 = "Développement personnel $$,.+";
        String st1 = "Outils de base$$";
        String st2 = "STAGE$$";
        String to_num = "1,5";
        String reg = "\\s*,;.+$";
        System.out.println("st containts $ : " + st.contains("$"));
        System.out.println("st1 containts $ : " + st1.contains("$"));
        System.out.println("st2 containts $ : " + st2.contains("$"));
        String part[] = s0.split(reg);
        s0 = part[0];
        /*if(st.contains ("$"))
            {
                String parts[] = st.split("[$]");
                st =  parts[0]; // i want to strip part after  +
            }
        if(st1.contains ("$"))
            {
                String parts[] = st1.split("[$]");
                st1 =  parts[0]; // i want to strip part after  +
            }
        if(st2.contains ("$"))
            {
                String parts[] = st2.split("[$]");
                st2 =  parts[0]; // i want to strip part after  +
            }*/
        System.out.println("s0 splited : " + s0);
        System.out.println(Float.parseFloat("1.5"));
        System.out.println(Float.parseFloat(to_num.replace(",", ".")));
        /*System.out.println("st splited : "+st);
        System.out.println("st1 splited : "+st1);
        System.out.println("st2 splited : "+st2);*/
    }

    public void setPanBtn(RichPanelGroupLayout panBtn) {
        this.panBtn = panBtn;
    }

    public RichPanelGroupLayout getPanBtn() {
        return panBtn;
    }

    public void setInsc_first(RichOutputFormatted insc_first) {
        this.insc_first = insc_first;
    }

    public RichOutputFormatted getInsc_first() {
        return insc_first;
    }

    @SuppressWarnings("unchecked")
    public void OnImportFormationClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        Long form_id = new Long(0);
        Long niv_form_id = new Long(0);
        Long niv_form_crt_id = new Long(0);
        Long niv_form_prcrs_id = new Long(0);
        String Lib = null;
        Long user_id = new Long(0);
        Long niv_id = new Long(0);
        Long gf_id = new Long(0);
        String msg = "";
        HashMap<Integer, String> err = new HashMap<Integer, String>();
        DCIteratorBinding iterFormation = (DCIteratorBinding) bindings.get("ScolFormationROVO1Iterator");
        Row CurrentFormation = iterFormation.getCurrentRow();
        if (CurrentFormation != null) {
            try {
                OperationBinding intgrateForm = bindings.getOperationBinding("integrateForm");
                intgrateForm.getParamsMap().put("anc_code", CurrentFormation.getAttribute("CodeFormation"));
                intgrateForm.getParamsMap().put("libelle", CurrentFormation.getAttribute("LibelleLong"));
                intgrateForm.getParamsMap().put("departement", CurrentFormation.getAttribute("CodeDepartement"));
                intgrateForm.getParamsMap().put("type_fr", CurrentFormation.getAttribute("TypeFormation"));
                intgrateForm.getParamsMap().put("payante", CurrentFormation.getAttribute("Payante"));
                intgrateForm.getParamsMap().put("presentielle", CurrentFormation.getAttribute("Presentielle"));
                intgrateForm.getParamsMap().put("grade_fr", CurrentFormation.getAttribute("CodeGradeDiplome"));
                intgrateForm.getParamsMap().put("mention", CurrentFormation.getAttribute("CodeMention"));
                intgrateForm.getParamsMap().put("ouvert", CurrentFormation.getAttribute("Ouvert"));
                intgrateForm.getParamsMap().put("valide", CurrentFormation.getAttribute("Valide"));
                intgrateForm.getParamsMap()
                    .put("professionalisante", CurrentFormation.getAttribute("Professionnalisante"));
                intgrateForm.getParamsMap().put("cycl", CurrentFormation.getAttribute("CycleCode"));
                intgrateForm.getParamsMap().put("reconnue", CurrentFormation.getAttribute("FormationReconnue"));
                intgrateForm.getParamsMap().put("obs", CurrentFormation.getAttribute("Observations"));
                intgrateForm.getParamsMap().put("spec", CurrentFormation.getAttribute("IdSpecialite"));
                intgrateForm.getParamsMap().put("opt", CurrentFormation.getAttribute("IdOption"));
                intgrateForm.getParamsMap().put("utilisateur", getUtilisateur());
                form_id = (Long) intgrateForm.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (0 != form_id) {
                try {
                    //createOrUpdateUserForm : donner l'acces à l'admin
                    OperationBinding opUserForm = bindings.getOperationBinding("createOrUpdateUserForm");
                    opUserForm.getParamsMap().put("user_", 85);
                    opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                    opUserForm.getParamsMap().put("form_", form_id);
                    opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                    opUserForm.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                DCIteratorBinding iterNivFormation = (DCIteratorBinding) bindings.get("ScolNivFormationROVO1Iterator");
                Row CurrentNivFormation = iterNivFormation.getCurrentRow();
                if (CurrentNivFormation != null) {
                    try {
                        OperationBinding intgrateNivForm = bindings.getOperationBinding("integrateNiveauForm");
                        intgrateNivForm.getParamsMap().put("anc_code", CurrentNivFormation.getAttribute("CodeNivSec"));
                        intgrateNivForm.getParamsMap().put("libelle", CurrentNivFormation.getAttribute("LibelleLong"));
                        intgrateNivForm.getParamsMap().put("abrev", CurrentNivFormation.getAttribute("LibelleCourt"));
                        intgrateNivForm.getParamsMap().put("a_select", CurrentNivFormation.getAttribute("ASelection"));
                        intgrateNivForm.getParamsMap()
                            .put("aut_permise", CurrentNivFormation.getAttribute("AutorisationPermise"));
                        intgrateNivForm.getParamsMap()
                            .put("presentielle", CurrentNivFormation.getAttribute("Presentielle"));
                        intgrateNivForm.getParamsMap()
                            .put("nbr_insc_permis", CurrentNivFormation.getAttribute("NbreInsPermises"));
                        intgrateNivForm.getParamsMap()
                            .put("diplomante", CurrentNivFormation.getAttribute("Diplomante"));
                        intgrateNivForm.getParamsMap()
                            .put("formation", CurrentNivFormation.getAttribute("CodeFormation"));
                        intgrateNivForm.getParamsMap().put("ouvert", CurrentNivFormation.getAttribute("Ouvert"));
                        intgrateNivForm.getParamsMap().put("niveau", CurrentNivFormation.getAttribute("NiveauCode"));
                        intgrateNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                        niv_form_id = (Long) intgrateNivForm.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (niv_form_id != 0) {
                        try {
                            //createOrUpdateUserNivForm : donner l'acces à l'admin
                            OperationBinding opUserNivForm = bindings.getOperationBinding("createOrUpdateUserNivForm");
                            opUserNivForm.getParamsMap().put("user_", 85);
                            opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                            opUserNivForm.getParamsMap().put("niv_form_", niv_form_id);
                            opUserNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                            opUserNivForm.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            //System.out.println("CodeNivSec : " + CurrentNivFormation.getAttribute("CodeNivSec"));
                            OperationBinding intgrateNivFormCrt =
                                bindings.getOperationBinding("integrateNiveauFormCrt");
                            intgrateNivFormCrt.getParamsMap().put("niv_fr", niv_form_id);
                            intgrateNivFormCrt.getParamsMap().put("crt_code", CurrentNivFormation.getAttribute("Code"));
                            intgrateNivFormCrt.getParamsMap().put("actif", CurrentNivFormation.getAttribute("Actif"));
                            intgrateNivFormCrt.getParamsMap()
                                .put("nbr_etd", CurrentNivFormation.getAttribute("NombreEtudiant"));
                            intgrateNivFormCrt.getParamsMap().put("utilisateur", getUtilisateur());
                            niv_form_crt_id = (Long) intgrateNivFormCrt.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //System.out.println("niv_form_crt_id : " + niv_form_crt_id);
                        if (niv_form_crt_id != 0) {
                            try {
                                OperationBinding intgrateNivFormPrcrs =
                                    bindings.getOperationBinding("integrateNiveauFormPrcrs");
                                intgrateNivFormPrcrs.getParamsMap().put("fr_id", form_id);
                                intgrateNivFormPrcrs.getParamsMap().put("niv_form_crt", niv_form_crt_id);
                                intgrateNivFormPrcrs.getParamsMap()
                                    .put("spec", CurrentFormation.getAttribute("IdSpecialite"));
                                intgrateNivFormPrcrs.getParamsMap()
                                    .put("opt", CurrentFormation.getAttribute("IdOption"));
                                intgrateNivFormPrcrs.getParamsMap().put("utilisateur", getUtilisateur());
                                niv_form_prcrs_id = (Long) intgrateNivFormPrcrs.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                try {
                                    OperationBinding opniv = bindings.getOperationBinding("getNiveau");
                                    opniv.getParamsMap()
                                        .put("anc_code", CurrentNivFormation.getAttribute("NiveauCode"));
                                    niv_id = (Long) opniv.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                try {
                                    OperationBinding opgf = bindings.getOperationBinding("getgradeFormation");
                                    opgf.getParamsMap()
                                        .put("anc_code", CurrentFormation.getAttribute("CodeGradeDiplome"));
                                    gf_id = (Long) opgf.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                if ((0 != niv_id) && (0 != gf_id)) {
                                    //getsemestreBygradeSemestre
                                    try {
                                        OperationBinding opgs = bindings.getOperationBinding("getsemestregrade");
                                        opgs.getParamsMap().put("niv_id", niv_id);
                                        opgs.getParamsMap().put("gf_id", gf_id);
                                        opgs.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    DCIteratorBinding itersem =
                                        (DCIteratorBinding) bindings.get("getsemestregradeIterator");
                                    if (itersem.getCurrentRow() != null) {
                                        RowSetIterator rsie = itersem.getViewObject().createRowSetIterator(null);
                                        while (rsie.hasNext()) {
                                            Row nextRowSem = rsie.next();
                                            Integer nbr_cal = 0;
                                            try {
                                                OperationBinding is_exist =
                                                    bindings.getOperationBinding("isCalendrierExist");
                                                is_exist.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                                is_exist.getParamsMap()
                                                    .put("sem_id",
                                                         Long.parseLong(nextRowSem.getAttribute("IdSemestre")
                                                                        .toString()));
                                                is_exist.getParamsMap().put("an_id", getAnne_univers());
                                                nbr_cal = (Integer) is_exist.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            if (0 == nbr_cal) {
                                                Long cal_id = new Long(0);
                                                try {
                                                    OperationBinding opnewcal =
                                                        bindings.getOperationBinding("CreateOrUpdateCalendar");
                                                    opnewcal.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                                    opnewcal.getParamsMap()
                                                        .put("sem_id",
                                                             Long.parseLong(nextRowSem.getAttribute("IdSemestre")
                                                                            .toString()));
                                                    opnewcal.getParamsMap().put("an_id", getAnne_univers());
                                                    opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opnewcal.getParamsMap().put("sess_id", 1);
                                                    cal_id = new Long((Integer) opnewcal.execute());
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                //System.out.println("cal_id : " + cal_id);
                                                if (0 == cal_id) {
                                                    err.put(0, "erreur");
                                                }
                                            }
                                        }
                                        rsie.closeRowSetIterator();
                                        if (err.isEmpty()) {
                                            RichPopup popup = this.getPopSuccess();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        } else {
                                            msg = "Erreur de calendrier";
                                            RichPopup popup = this.getPopFailed();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        }
                                    } else {
                                        msg = "Erreur grade semestre";
                                        RichPopup popup = this.getPopFailed();
                                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                        //empty hints renders dialog in center of screen
                                        popup.show(hints);
                                    }

                                } else {
                                    msg = "Erreur niveau ou grade formation";
                                    RichPopup popup = this.getPopFailed();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    //empty hints renders dialog in center of screen
                                    popup.show(hints);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            msg = "Erreur niveau formation cohorte";
                            RichPopup popup = this.getPopFailed();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        }
                    } else {
                        msg = "Erreur niveau formation";
                        RichPopup popup = this.getPopFailed();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                } /* else {
                msg = "Erreur niveau formation";
                RichPopup popup = this.getPopFailed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } */
            } else {
                msg = "Erreur formation";
                RichPopup popup = this.getPopFailed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
        //System.out.println(msg);
    }

    public void setPopSuccess(RichPopup popSuccess) {
        this.popSuccess = popSuccess;
    }

    public RichPopup getPopSuccess() {
        return popSuccess;
    }

    public void setPopFailed(RichPopup popFailed) {
        this.popFailed = popFailed;
    }

    public RichPopup getPopFailed() {
        return popFailed;
    }

    public void OnImportEtudiantClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        String msg = "";
        ArrayList<Long> listfilUeSem = new ArrayList<Long>();
        if (null == sessionScope.get("pma_id")) {
            OperationBinding get_prcrs = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("getPrcrsMaqAnnee");
            get_prcrs.getParamsMap().put("anne_id", this.getAnne_univers());
            //get_prcrs.getParamsMap().put("nfp_id", this.getParcours());
            get_prcrs.execute();

            DCIteratorBinding iterPrcrs = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("getParcoursMaqAnnee1Iterator");
            if (iterPrcrs.getEstimatedRowCount() == 1) {
                sessionScope.put("pma_id", iterPrcrs.getCurrentRow().getAttribute("IdParcoursMaquetAnnee"));
            }
        }
        OperationBinding op_get_fil_ue = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("getFilUeSem");
        op_get_fil_ue.getParamsMap().put("pma_id", Long.parseLong(sessionScope.get("pma_id").toString()));
        op_get_fil_ue.execute();
        DCIteratorBinding iterfilue = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("getFilUeSemestre1Iterator");
        RowSetIterator rsi = iterfilue.getViewObject().createRowSetIterator(null);
        //getFilEcInspedSemUeEtudiant
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if (!(listfilUeSem.contains(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString())))) {
                listfilUeSem.add(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
                //System.out.println("IdFiliereUeSemstre : " + nextRow.getAttribute("IdFiliereUeSemstre"));
            }
        }
        rsi.closeRowSetIterator();

        DCIteratorBinding iteretd = (DCIteratorBinding) bindings.get("ScolStudentDefInscIterator");
        RowSetIterator rsie = iteretd.getViewObject().createRowSetIterator(null);
        while (rsie.hasNext()) {
            Long etd_id = new Long(0);
            Row nextRowEtd = rsie.next();
            /*System.out.println("Numero : " + nextRowEtd.getAttribute("Numero") + " NumeroTable : " +
                               nextRowEtd.getAttribute("NumeroTable"));
            System.out.println("pma_id : " + sessionScope.get("pma_id"));*/
            try {
                OperationBinding opetd = bindings.getOperationBinding("createOrUpdateEtudiant");
                opetd.getParamsMap().put("num_table", nextRowEtd.getAttribute("NumeroTable"));
                opetd.getParamsMap().put("num_etd", nextRowEtd.getAttribute("Numero"));
                opetd.getParamsMap().put("utilisateur", getUtilisateur());
                etd_id = (Long) opetd.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (etd_id != 0) {
                //System.out.println("Ins_admin etudiant " + etd_id + " ...");
                Long ins_ad_id = new Long(0);
                try {
                    OperationBinding opinsAd = bindings.getOperationBinding("createOrUpdateInsAdmin");
                    opinsAd.getParamsMap().put("etu_id", etd_id);
                    opinsAd.getParamsMap().put("tfr_anc_code", nextRowEtd.getAttribute("TypeFormation"));
                    opinsAd.getParamsMap().put("gfr_anc_code", nextRowEtd.getAttribute("CodeGradeDiplome"));
                    opinsAd.getParamsMap().put("an_id", getAnne_univers());
                    opinsAd.getParamsMap().put("ins_en_ligne", nextRowEtd.getAttribute("InsEnLigne"));
                    opinsAd.getParamsMap().put("utilisateur", getUtilisateur());
                    ins_ad_id = (Long) opinsAd.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 != ins_ad_id) {
                    /*System.out.println("Ins_ped ins_admin " + ins_ad_id);
                    System.out.println("pma " + sessionScope.get("pma_id"));*/
                    Long ins_ped_id = new Long(0);
                    try {

                        OperationBinding opinsPed = bindings.getOperationBinding("createOrUpdateInsPed");
                        /*UTI_CREE    ID_COHORTE
                        UTI_PERMET_DOUBL_INS
                        ID_OPERATEUR      DERNIER_INSCRIPTION
                        ID_INSCRIPTION_ADMIN   ID_HORAIRES_TD
                        ID_STATUT
                        ID_INSCRIPTION_PEDAGOGIQUE*/
                        opinsPed.getParamsMap().put("ins_ad_id", ins_ad_id);
                        opinsPed.getParamsMap().put("h_td", nextRowEtd.getAttribute("HoraireTdCode"));
                        opinsPed.getParamsMap().put("opt", nextRowEtd.getAttribute("CodeOption"));
                        //opinsPed.getParamsMap().put("crt", nextRowEtd.getAttribute("Cohorte"));
                        opinsPed.getParamsMap().put("crt", nextRowEtd.getAttribute("TypeCohorte"));
                        opinsPed.getParamsMap().put("motif", nextRowEtd.getAttribute("Motif"));
                        opinsPed.getParamsMap().put("rdblmt", nextRowEtd.getAttribute("Redoublement"));
                        opinsPed.getParamsMap().put("etat_ins", nextRowEtd.getAttribute("EtatInscription"));
                        opinsPed.getParamsMap().put("quitance", nextRowEtd.getAttribute("Quittance"));
                        opinsPed.getParamsMap().put("nbr_insc", nextRowEtd.getAttribute("NbInsc"));
                        opinsPed.getParamsMap().put("pm_dl_insc", nextRowEtd.getAttribute("PermetDoubleInscription"));
                        opinsPed.getParamsMap().put("crt_tire", nextRowEtd.getAttribute("Cartetiree"));
                        opinsPed.getParamsMap().put("ord_inc", nextRowEtd.getAttribute("OrdreInscription"));
                        opinsPed.getParamsMap().put("cde_a_cons", nextRowEtd.getAttribute("CodeAConserver"));
                        opinsPed.getParamsMap().put("dte_edi_crt", nextRowEtd.getAttribute("DateEditionCarte"));
                        opinsPed.getParamsMap()
                            .put("prcrs_mq_an", Long.parseLong(sessionScope.get("pma_id").toString()));
                        opinsPed.getParamsMap().put("ins_en_ligne", nextRowEtd.getAttribute("InsEnLigne"));
                        opinsPed.getParamsMap().put("utilisateur", getUtilisateur());
                        opinsPed.getParamsMap().put("stat", nextRowEtd.getAttribute("StatutCode"));
                        ins_ped_id = (Long) opinsPed.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (0 != ins_ped_id) {
                        //System.out.println("ins_ped_id " + ins_ped_id);
                        Long sem_first = new Long(0);
                        Long sem_last = new Long(0);
                        /*if (0 == getSemestre() % 2) {
                            sem_first = getSemestre() - 1;
                            sem_last = getSemestre();
                        } else {
                            sem_first = getSemestre();
                            sem_last = getSemestre() + 1;
                        }*/
                        //System.out.println("Sem first : " + sem_first + " sem last : " + sem_last);
                        for (Long i = sem_first; i <= sem_last; i++) {
                            //System.out.println("Ins_ped_sem insped " + ins_ped_id + " semestre " + i);
                            Long ins_ped_sem_id = new Long(0);
                            try {
                                OperationBinding opinsPedSem = bindings.getOperationBinding("createOrUpdateInsPedSem");

                                opinsPedSem.getParamsMap().put("ins_ped", ins_ped_id);
                                opinsPedSem.getParamsMap().put("sem_id", i);
                                opinsPedSem.getParamsMap()
                                    .put("ins_pd_valide", nextRowEtd.getAttribute("InsPedValide"));
                                opinsPedSem.getParamsMap().put("pass_exam", nextRowEtd.getAttribute("PasseExam"));
                                opinsPedSem.getParamsMap().put("cumul_cred", nextRowEtd.getAttribute("CumulCredit"));
                                opinsPedSem.getParamsMap().put("dette_cred", nextRowEtd.getAttribute("DetteCredit"));
                                opinsPedSem.getParamsMap().put("utilisateur", getUtilisateur());
                                ins_ped_sem_id = (Long) opinsPedSem.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 != ins_ped_sem_id) {
                                //System.out.println("ins_ped_sem_ue insped_sem " + ins_ped_sem_id);
                                if (0 != listfilUeSem.size()) {
                                    for (int ii = 0; ii < listfilUeSem.size(); ii++) {
                                        Long ins_ped_ue_id = new Long(0);
                                        try {
                                            OperationBinding op_new_insped =
                                                bindings.getOperationBinding("createOrUpdateInsPedSemUe");
                                            op_new_insped.getParamsMap().put("ins_ped_sem_id", ins_ped_sem_id);
                                            op_new_insped.getParamsMap().put("fil_ue_id", listfilUeSem.get(ii));
                                            op_new_insped.getParamsMap().put("utilisateur", getUtilisateur());
                                            ins_ped_ue_id = (Long) op_new_insped.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        if (0 != ins_ped_ue_id) {
                                            RichPopup popup = this.getPopSuccess();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        } else {
                                            msg = "Erreur inscription pédagogique ue";
                                            sessionScope.put("msg", msg);
                                            RichPopup popup = this.getPopFailed();
                                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                            //empty hints renders dialog in center of screen
                                            popup.show(hints);
                                        }
                                    }
                                }
                            } else {
                                RichPopup popup = this.getPopFailed();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                //empty hints renders dialog in center of screen
                                popup.show(hints);
                            }
                        }
                    } else {
                        msg = "Erreur inscription pédagogique";
                        sessionScope.put("msg", msg);
                    }
                } else {
                    msg = "Erreur incription admin";
                    sessionScope.put("msg", msg);
                }
            } else {
                msg = "Erreur création etudiant";
                sessionScope.put("msg", msg);
            }
        }
    }

    /*public void setParcours(Integer parcours) {
        this.parcours = parcours;
    }

    public Integer getParcours() {
        return parcours;
    }*/

    public void setStr_id(Long str_id) {
        this.str_id = str_id;
    }

    public Long getStr_id() {
        return str_id;
    }

    public void OnIntegratedNiveauAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        ArrayList<Long> niv_sec_integrated = new ArrayList<>();
        OperationBinding opniveaux = getBindings().getOperationBinding("getSelectedNiveauSections");
        ArrayList<NiveauSection> niv_sec = (ArrayList<NiveauSection>) opniveaux.execute();
        //ArrayList<Long> niv_sec = (ArrayList<Long>) opniveaux.execute();
        if (0 != niv_sec.size()) {
            for (int nivcounter = 0; nivcounter < niv_sec.size(); nivcounter++) {
                try {
                    OperationBinding opCreteOrUpdateDept =
                        getBindings().getOperationBinding("CreateOrUpdateDeptByNiveau");
                    opCreteOrUpdateDept.getParamsMap()
                        .put("niv_sec", new Long(niv_sec.get(nivcounter).getCode_niv_sec()));
                    opCreteOrUpdateDept.getParamsMap().put("utilisateur", getUtilisateur());
                    Long dept_id = (Long) opCreteOrUpdateDept.execute();
                    if (0 != dept_id) {
                        try {
                            OperationBinding opCreteOrUpdateForm =
                                getBindings().getOperationBinding("CreateOrUpdateFormByNiv");
                            opCreteOrUpdateForm.getParamsMap()
                                .put("niv_sec", new Long(niv_sec.get(nivcounter).getCode_niv_sec()));
                            opCreteOrUpdateForm.getParamsMap()
                                .put("form_", new Long(niv_sec.get(nivcounter).getCode_fr()));
                            opCreteOrUpdateForm.getParamsMap().put("utilisateur", getUtilisateur());
                            Long fr_id = (Long) opCreteOrUpdateForm.execute();
                            if (0 != fr_id) {
                                if (-1 != fr_id) {
                                    try {
                                        OperationBinding opCreteOrUpdateNivForm =
                                            getBindings().getOperationBinding("CreateOrUpdateNivFormByNiv");
                                        /*niv_sec java.lang.String
                                        form_   java.lang.String
                                        utilisateur     java.lang.Integer    */
                                        opCreteOrUpdateNivForm.getParamsMap()
                                            .put("niv_sec",
                                                 new Long(niv_sec.get(nivcounter)
                                                                                                             .getCode_niv_sec()
                                                                                                             .replaceAll("^\\s+",
                                                                                                                         "")));
                                        opCreteOrUpdateNivForm.getParamsMap()
                                            .put("form_",
                                                 new Long(niv_sec.get(nivcounter)
                                                                                                           .getCode_fr()
                                                                                                           .replaceAll("^\\s+",
                                                                                                                       "")));
                                        opCreteOrUpdateNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                                        Long niv_id = (Long) opCreteOrUpdateNivForm.execute();
                                        if (0 != niv_id) {
                                            niv_sec_integrated.add(niv_id);
                                            try {
                                                DCIteratorBinding iterHightLevel =
                                                    (DCIteratorBinding) bindings.get("HightLevelRolesIterator");
                                               if (null != iterHightLevel.getCurrentRow()) {
                                                    RowSetIterator rsie =
                                                        iterHightLevel.getViewObject().createRowSetIterator(null);
                                                    while (rsie.hasNext()) {
                                                        Row nextRowHight = rsie.next();
                                                        try {
                                                            OperationBinding opUserForm =
                                                                getBindings()
                                                                .getOperationBinding("createOrUpdateUserForm");
                                                            opUserForm.getParamsMap()
                                                                .put("user_",
                                                                     nextRowHight.getAttribute("IdUtilisateur"));
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap().put("form_", fr_id);
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            OperationBinding opUserForm =
                                                                getBindings()
                                                                .getOperationBinding("createOrUpdateUserNivForm");
                                                            opUserForm.getParamsMap()
                                                                .put("user_",
                                                                     nextRowHight.getAttribute("IdUtilisateur"));
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap().put("niv_form_", niv_id);
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                    rsie.closeRowSetIterator();
                                                }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            try {
                                                OperationBinding opAdmin =
                                                    getBindings().getOperationBinding("getAdminEtab");
                                                opAdmin.execute();
                                                DCIteratorBinding iterAdm =
                                                    (DCIteratorBinding) bindings.get("AdminEtabIterator");
                                                if (null != iterAdm.getCurrentRow()) {
                                                    RowSetIterator rsie =
                                                        iterAdm.getViewObject().createRowSetIterator(null);
                                                    while (rsie.hasNext()) {
                                                        Row nextRowAdm = rsie.next();
                                                        try {
                                                            OperationBinding opUserForm =
                                                                getBindings()
                                                                .getOperationBinding("createOrUpdateUserForm");
                                                            opUserForm.getParamsMap()
                                                                .put("user_", nextRowAdm.getAttribute("IdUtilisateur"));
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap().put("form_", fr_id);
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            OperationBinding opUserForm =
                                                                getBindings()
                                                                .getOperationBinding("createOrUpdateUserNivForm");
                                                            opUserForm.getParamsMap()
                                                                .put("user_", nextRowAdm.getAttribute("IdUtilisateur"));
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap().put("niv_form_", niv_id);
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                    rsie.closeRowSetIterator();
                                                }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            try {
                                                OperationBinding intgrateNivFormCrt =
                                                    getBindings().getOperationBinding("integrateNiveauFormCrt");
                                                intgrateNivFormCrt.getParamsMap().put("niv_fr", niv_id);
                                                intgrateNivFormCrt.getParamsMap()
                                                    .put("crt_code", niv_sec.get(nivcounter).getCrt_code());
                                                intgrateNivFormCrt.getParamsMap()
                                                    .put("actif", niv_sec.get(nivcounter).getActif());
                                                intgrateNivFormCrt.getParamsMap()
                                                    .put("nbr_etd", niv_sec.get(nivcounter).getNbreEtudiant());
                                                intgrateNivFormCrt.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long niv_form_crt_id = (Long) intgrateNivFormCrt.execute();
                                                if (0 != niv_form_crt_id) {
                                                    try {
                                                        OperationBinding intgrateNivFormPrcrs =
                                                            getBindings()
                                                            .getOperationBinding("integrateNiveauFormPrcrs");
                                                        intgrateNivFormPrcrs.getParamsMap().put("fr_id", fr_id);
                                                        intgrateNivFormPrcrs.getParamsMap()
                                                            .put("niv_form_crt", niv_form_crt_id);
                                                        intgrateNivFormPrcrs.getParamsMap()
                                                            .put("spec", niv_sec.get(nivcounter).getSpec());
                                                        intgrateNivFormPrcrs.getParamsMap()
                                                            .put("opt", niv_sec.get(nivcounter).getOpt());
                                                        intgrateNivFormPrcrs.getParamsMap()
                                                            .put("utilisateur", getUtilisateur());
                                                        Long niv_form_prcrs_id = (Long) intgrateNivFormPrcrs.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }

                                                    try {
                                                        String msg = null;
                                                        Long niveau_id = new Long(0);
                                                        Long gf_id = new Long(0);
                                                        try {
                                                            OperationBinding opniv =
                                                                getBindings().getOperationBinding("getNiveau");
                                                            opniv.getParamsMap()
                                                                .put("anc_code", niv_sec.get(nivcounter).getNiveau());
                                                            niveau_id = (Long) opniv.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            OperationBinding opgf =
                                                                getBindings().getOperationBinding("getgradeFormation");
                                                            opgf.getParamsMap()
                                                                .put("anc_code", niv_sec.get(nivcounter).getGrade());
                                                            gf_id = (Long) opgf.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        if ((0 != niveau_id) && (0 != gf_id)) {
                                                            //getsemestreBygradeSemestre
                                                            try {
                                                                OperationBinding opgs =
                                                                    getBindings()
                                                                    .getOperationBinding("getsemestregrade");
                                                                opgs.getParamsMap().put("niv_id", niveau_id);
                                                                opgs.getParamsMap().put("gf_id", gf_id);
                                                                opgs.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            DCIteratorBinding itersem =
                                                                (DCIteratorBinding) getBindings()
                                                                .get("getsemestregradeIterator");
                                                            if (itersem.getCurrentRow() != null) {
                                                                RowSetIterator rsie =
                                                                    itersem.getViewObject().createRowSetIterator(null);
                                                                while (rsie.hasNext()) {
                                                                    Row nextRowSem = rsie.next();
                                                                    Integer nbr_cal = 0;
                                                                    try {
                                                                        OperationBinding is_exist =
                                                                            getBindings()
                                                                            .getOperationBinding("isCalendrierExist");
                                                                        is_exist.getParamsMap()
                                                                            .put("niv_crt_id", niv_form_crt_id);
                                                                        is_exist.getParamsMap()
                                                                            .put("sem_id",
                                                                                 nextRowSem.getAttribute("IdSemestre"));
                                                                        is_exist.getParamsMap()
                                                                            .put("an_id", getAnne_univers());
                                                                        nbr_cal = (Integer) is_exist.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    if (0 == nbr_cal) {
                                                                        Long cal_id = new Long(0);
                                                                        try {
                                                                            OperationBinding opnewcal =
                                                                                getBindings()
                                                                                .getOperationBinding("CreateOrUpdateCalendar");
                                                                            opnewcal.getParamsMap()
                                                                                .put("niv_crt_id", niv_form_crt_id);
                                                                            opnewcal.getParamsMap()
                                                                                .put("sem_id",
                                                                                     nextRowSem.getAttribute("IdSemestre"));
                                                                            opnewcal.getParamsMap()
                                                                                .put("an_id", getAnne_univers());
                                                                            opnewcal.getParamsMap()
                                                                                .put("utilisateur", getUtilisateur());
                                                                            opnewcal.getParamsMap().put("sess_id", 1);
                                                                            cal_id =
                                                                                new Long((Integer) opnewcal.execute());
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                    }
                                                                }
                                                                rsie.closeRowSetIterator();

                                                            } else {
                                                                msg =
                                                                    "Veuillez rattacher le Grade de Formation aux semestres correspondants";
                                                                sessionScope.put("msg", msg);
                                                                System.out.println("Veuillez rattacher le Grade de Formation aux semestres correspondants");
                                                                //RichPopup popup = this.getPopImportFailed();
                                                                //RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                                                //empty hints renders dialog in center of screen
                                                                //popup.show(hints);
                                                            }

                                                        } else {
                                                           msg =
                                                                "Veuillez vous rassurer que le Niveau et le Grade correspondent";
                                                            sessionScope.put("msg", msg);
                                                            //RichPopup popup = this.getPopImportFailed();
                                                            //RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                                            //empty hints renders dialog in center of screen
                                                            //popup.show(hints);
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            //Parametrer le responsable du niveau de formation

                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                    //#####################################################################################################################
                                    if (null != niv_sec.get(nivcounter).getEmail()) {
                                        String role = null;
                                        try {
                                            OperationBinding oprole = bindings.getOperationBinding("getrole");
                                            oprole.getParamsMap().put("role_id", R_FR);
                                            role = (String) oprole.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        try {
                                            OperationBinding opUser =
                                                getBindings().getOperationBinding("createOrUpdateUser");
                                            opUser.getParamsMap().put("email", niv_sec.get(nivcounter).getEmail());
                                            opUser.getParamsMap().put("struct_", niv_sec.get(nivcounter).getSrt());
                                            opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                            Long user_id = (Long) opUser.execute();
                                            if (0 != user_id) {
                                                try {
                                                    OperationBinding opAncResp =
                                                        getBindings().getOperationBinding("getResponsableFr");
                                                    opAncResp.getParamsMap().put("fr_id", fr_id);
                                                    Long u_resp_id = (Long) opAncResp.execute();
                                                    if (0 != u_resp_id) {
                                                        try {
                                                            OperationBinding opAncRespUser =
                                                                getBindings()
                                                                .getOperationBinding("getUserResponsableFr");
                                                            opAncRespUser.getParamsMap().put("u_fr_id", u_resp_id);
                                                            Long old_user_resp_id = (Long) opAncRespUser.execute();
                                                            //System.out.println("old_user_resp_id : "+old_user_resp_id);
                                                            if (user_id != old_user_resp_id) {
                                                                //Acces Simple a l'ancien
                                                                try {
                                                                    OperationBinding opUserForm =
                                                                        getBindings()
                                                                        .getOperationBinding("createOrUpdateUserForm1");
                                                                    opUserForm.getParamsMap()
                                                                        .put("user_", old_user_resp_id);
                                                                    opUserForm.getParamsMap()
                                                                        .put("role", "ACCES SIMPLE");
                                                                    opUserForm.getParamsMap().put("form_", fr_id);
                                                                    opUserForm.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    Long old_user_fr = (Long) opUserForm.execute();
                                                                    OperationBinding opisuserresp =
                                                                        bindings.getOperationBinding("isUserResponsable");
                                                                    opisuserresp.getParamsMap()
                                                                        .put("user_id", old_user_resp_id);
                                                                    opisuserresp.execute();
                                                                    DCIteratorBinding iterResp =
                                                                        (DCIteratorBinding) bindings.get("isUserResponsableIterator");
                                                                    if (0 == iterResp.getEstimatedRowCount()) {
                                                                        try {
                                                                            //delete role responsable formation to user
                                                                            OperationBinding opdeluserRole =
                                                                                bindings.getOperationBinding("DeleteUserRole");
                                                                            opdeluserRole.getParamsMap()
                                                                                .put("user_id", old_user_resp_id);
                                                                            opdeluserRole.getParamsMap()
                                                                                .put("role_id", R_FR);
                                                                            opdeluserRole.execute();
                                                                            //revoke weblogic role responsable formation to user
                                                                            //System.out.println("revoking role to user in weblogic...");
                                                                            OperationBinding opusername =
                                                                                bindings.getOperationBinding("getusername");
                                                                            opusername.getParamsMap()
                                                                                .put("user_id", old_user_resp_id);
                                                                            String username =
                                                                                (String) opusername.execute();
                                                                            if (null != role && null != username) {
                                                                                role =
                                                                                    role.substring(0, 1) +
                                                                                    role.substring(1).toLowerCase();
                                                                                try {
                                                                                    AdministrationBean.revokeWlsRole2User(role,
                                                                                                                          username);
                                                                                    //System.out.println("Role revoked success !");
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
                                                    try {
                                                        OperationBinding opUserForm =
                                                            getBindings().getOperationBinding("createOrUpdateUserForm");
                                                        opUserForm.getParamsMap().put("user_", user_id);
                                                        opUserForm.getParamsMap().put("role", "RESPONSABLE FORMATION");
                                                        opUserForm.getParamsMap().put("form_", fr_id);
                                                        opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                        Long new_user_fr = (Long) opUserForm.execute();
                                                        if (0 != new_user_fr) {
                                                            //Creer le role responsable formation pour cet utilisateur
                                                            OperationBinding allowuserRle =
                                                                bindings.getOperationBinding("CreateOrUpdateUserRole");
                                                            allowuserRle.getParamsMap().put("user_id", user_id);
                                                            allowuserRle.getParamsMap()
                                                                .put("role_id", R_FR); // Responsable formation
                                                            allowuserRle.getParamsMap()
                                                                .put("utilisateur",
                                                                     Integer.parseInt(getUtilisateur().toString()));
                                                            Integer ur = (Integer) allowuserRle.execute();
                                                            if (0 != ur) {
                                                                //assign weblogic role
                                                                try {
                                                                    OperationBinding opusername =
                                                                        bindings.getOperationBinding("getusername");
                                                                    opusername.getParamsMap()
                                                                        .put("user_id", new Long(user_id));
                                                                    String username = (String) opusername.execute();
                                                                    if (null != role && null != username) {
                                                                        role =
                                                                            role.substring(0, 1) +
                                                                            role.substring(1).toLowerCase();
                                                                        char[] password = new char[100];
                                                                        try {
                                                                            //String login = niv_sec.get(nivcounter).getEmail().toString().split("@")[0];
                                                                            String matricule = niv_sec.get(nivcounter)
                                                                                                      .getMatricule()
                                                                                                      .toString();
                                                                            //matricule = "passer123*";
                                                                            password = matricule.toCharArray();
                                                                            AdministrationBean.createWlsUser(username,
                                                                                                             password);
                                                                            AdministrationBean.assignWlsRole2User(role,
                                                                                                                  username);
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                    } 
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
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
                                    //######################################################################################################################
                                } else {
                                    System.out.println("Formation acc et niveau section dans différentes sections ! Corriger ");
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
            /*if(niv_sec.size() == niv_sec_integrated.size()){
                System.out.println("All niveau are imported successfully");
            }*/
            DCIteratorBinding iterBind = (DCIteratorBinding) getBindings().get("ScolNivFormIntegratedIterator");
            ViewObject vo = iterBind.getViewObject();
            vo.executeQuery();

            iterBind = (DCIteratorBinding) getBindings().get("ScolNivFormNotIntegratedIterator");
            vo = iterBind.getViewObject();
            vo.executeQuery();
            this.getIs_form().setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpNotIntegNivFr());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColNotIntegNivFr());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabNotIntegNivFr());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanIntegratedNivFr());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColIntegNivFr());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabIntegNivFr());
        }

        /*if(0 != niv_sec.size()){
            for (int i=0; i<niv_sec.size(); i++){
                System.out.println(niv_sec.get(i));
                System.out.println("Code niv sec "+niv_sec.get(i).getCode_niv_sec());
            }
        }*/

    }

    public void setPopGradeSemNotExist(RichPopup popGradeSemNotExist) {
        this.popGradeSemNotExist = popGradeSemNotExist;
    }

    public RichPopup getPopGradeSemNotExist() {
        return popGradeSemNotExist;
    }

    public void setPopNivOrGradeNotExiste(RichPopup popNivOrGradeNotExiste) {
        this.popNivOrGradeNotExiste = popNivOrGradeNotExiste;
    }

    public RichPopup getPopNivOrGradeNotExiste() {
        return popNivOrGradeNotExiste;
    }

    public void setPopImportFailed(RichPopup popImportFailed) {
        this.popImportFailed = popImportFailed;
    }

    public RichPopup getPopImportFailed() {
        return popImportFailed;
    }

    public void setPanIntegratedNivFr(RichPanelGroupLayout panIntegratedNivFr) {
        this.panIntegratedNivFr = panIntegratedNivFr;
    }

    public RichPanelGroupLayout getPanIntegratedNivFr() {
        return panIntegratedNivFr;
    }

    public void setPanGrpNotIntegNivFr(RichPanelGroupLayout panGrpNotIntegNivFr) {
        this.panGrpNotIntegNivFr = panGrpNotIntegNivFr;
    }

    public RichPanelGroupLayout getPanGrpNotIntegNivFr() {
        return panGrpNotIntegNivFr;
    }

    public void setPanColNotIntegNivFr(RichPanelCollection panColNotIntegNivFr) {
        this.panColNotIntegNivFr = panColNotIntegNivFr;
    }

    public RichPanelCollection getPanColNotIntegNivFr() {
        return panColNotIntegNivFr;
    }

    public void setTabNotIntegNivFr(RichTable tabNotIntegNivFr) {
        this.tabNotIntegNivFr = tabNotIntegNivFr;
    }

    public RichTable getTabNotIntegNivFr() {
        return tabNotIntegNivFr;
    }

    public void setPanColIntegNivFr(RichPanelCollection panColIntegNivFr) {
        this.panColIntegNivFr = panColIntegNivFr;
    }

    public RichPanelCollection getPanColIntegNivFr() {
        return panColIntegNivFr;
    }

    public void setTabIntegNivFr(RichTable tabIntegNivFr) {
        this.tabIntegNivFr = tabIntegNivFr;
    }

    public RichTable getTabIntegNivFr() {
        return tabIntegNivFr;
    }

    public void onSelectedAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ScolNivFormNotIntegratedIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //nextRow.setAttribute("isNivSelected", valueChangeEvent.getNewValue());
            nextRow.setAttribute("isChecked", valueChangeEvent.getNewValue());
        }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanGrpNotIntegNivFr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanColNotIntegNivFr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTabNotIntegNivFr());
    }

    public void setIs_form(RichSelectBooleanCheckbox is_form) {
        this.is_form = is_form;
    }

    public RichSelectBooleanCheckbox getIs_form() {
        return is_form;
    }

    public void initTopForm() {
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ScolNivFormNotIntegratedIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if (null == nextRow.getAttribute("isNivSelected")) {
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if (null != nextRow.getAttribute("isNivSelected")) {
                if (nextRow.getAttribute("isNivSelected").equals(false)) {
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_form().setValue(is_);
    }
    //Permet de tout cocher si "le cocher tout" est active
    public void initForm() {
        if (null != this.getIs_form().getValue()) {
            if (this.getIs_form()
                    .getValue()
                    .equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("ScolNivFormNotIntegratedIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isNivSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPanGrpNotIntegNivFr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPanColNotIntegNivFr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getTabNotIntegNivFr());
            }
        }
    }

    public void initTopPrcrsForm() {
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ScolNivFormPrcrsNotIntegratedIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            //if(null == nextRow.getAttribute("isNivSelected")){
            if (null == nextRow.getAttribute("isChecked")) {
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if (null != nextRow.getAttribute("isChecked")) {
                if (nextRow.getAttribute("isChecked").equals(false)) {
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIsPrcrs_Form().setValue(is_);
    }
    //Permet de tout cocher si "le cocher tout" est active
    public void initPrcrsForm() {
        if (null != this.getIsPrcrs_Form().getValue()) {
            if (this.getIsPrcrs_Form()
                    .getValue()
                    .equals(true)) {
                DCIteratorBinding iter =
                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("ScolNivFormPrcrsNotIntegratedIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    //nextRow.setAttribute("isNivSelected", true);
                    nextRow.setAttribute("isChecked", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPanGrpPrcrsNotIntegrated());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPanColPrcrsNotIntegrated());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getTabPrcrsNotIntegrated());
            }
        }
    }

    public void setIs_prcrs(RichShowDetailItem is_prcrs) {
        this.is_prcrs = is_prcrs;
    }

    public RichShowDetailItem getIs_prcrs() {
        return is_prcrs;
    }

    public void setIs_prcrsFr(HtmlSelectBooleanCheckbox is_prcrsFr) {
        this.is_prcrsFr = is_prcrsFr;
    }

    public HtmlSelectBooleanCheckbox getIs_prcrsFr() {
        return is_prcrsFr;
    }

    public void OnIntegratedPrcrs(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ScolNivFormPrcrsNotIntegratedIterator");
        ArrayList<Long> niv_sec_integrated = new ArrayList<>();
        ArrayList<Parcours> niv_sec_not_integrated = new ArrayList<Parcours>();
        Boolean v_isChecked = false;
        Integer is_ch = 0;
        if (null != iterPrcrs.getCurrentRow()) {
            niv_sec_not_integrated.clear();
            //System.out.println("size : "+niv_sec_not_integrated.size());
            Parcours p = new Parcours();
            RowSetIterator rsi = iterPrcrs.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                //System.out.println("is_checked : "+nextRow.getAttribute("isChecked"));
                if (null != nextRow.getAttribute("isChecked")) {
                    if (Integer.parseInt(nextRow.getAttribute("isChecked").toString()) == 1) {
                        if (null != nextRow.getAttribute("Emailucad")) {
                            v_isChecked = true;
                            is_ch = 1;
                            try {
                                OperationBinding opCreteOrUpdateDept =
                                    getBindings().getOperationBinding("CreateOrUpdateDeptByNiveau");
                                opCreteOrUpdateDept.getParamsMap().put("niv_sec", nextRow.getAttribute("CodeNivSec"));
                                opCreteOrUpdateDept.getParamsMap().put("utilisateur", getUtilisateur());
                                Long dept_id = (Long) opCreteOrUpdateDept.execute();
                                //System.out.println("dept_id : " + dept_id);
                                if (0 != dept_id) {
                                    try {
                                        /*System.out.println("Fr_id_scol : "+nextRow.getAttribute("CodeFormation"));
                                    System.out.println("code_niv_sec : " + nextRow.getAttribute("CodeNivSec"));*/
                                        OperationBinding opCreteOrUpdateForm =
                                            getBindings().getOperationBinding("CreateOrUpdateFormByNiv");
                                        opCreteOrUpdateForm.getParamsMap()
                                            .put("niv_sec", nextRow.getAttribute("CodeNivSec"));
                                        opCreteOrUpdateForm.getParamsMap()
                                            .put("form_", nextRow.getAttribute("CodeFormation"));
                                        opCreteOrUpdateForm.getParamsMap().put("utilisateur", getUtilisateur());
                                        Long fr_id = (Long) opCreteOrUpdateForm.execute();
                                        //System.out.println("Fr ref : "+fr_id);
                                        if (0 != fr_id) {
                                            if (-1 != fr_id) {
                                                //creation du responsable de formation
                                                //creation du niveau
                                                try {
                                                    OperationBinding opCreteOrUpdateNivForm =
                                                        getBindings().getOperationBinding("CreateOrUpdateNivFormByNiv");
                                                    opCreteOrUpdateNivForm.getParamsMap()
                                                        .put("niv_sec", nextRow.getAttribute("CodeNivSec"));
                                                    opCreteOrUpdateNivForm.getParamsMap()
                                                        .put("form_", nextRow.getAttribute("CodeFormation"));
                                                    opCreteOrUpdateNivForm.getParamsMap().put("id_fr", fr_id);
                                                    opCreteOrUpdateNivForm.getParamsMap()
                                                        .put("utilisateur", getUtilisateur());

                                                    Long niv_id = (Long) opCreteOrUpdateNivForm.execute();
                                                    //System.out.println("niv_id : "+niv_id);
                                                    if (0 != niv_id) {
                                                        //###################### RESPONSABLE NIVEAU FORMATION ####################################################################
                                                        if (null != nextRow.getAttribute("Emailucad")) {
                                                            String role = null;
                                                            //System.out.println("Responsable niveau fr");
                                                            try {
                                                                OperationBinding oprole =
                                                                    bindings.getOperationBinding("getrole");
                                                                oprole.getParamsMap()
                                                                    .put("role_id", R_NIV_FR); //resp niv fr
                                                                role = (String) oprole.execute();
                                                                //System.out.println("Role : " + role);
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            try {
                                                                OperationBinding opUser =
                                                                    getBindings()
                                                                    .getOperationBinding("createOrUpdateUser");
                                                                opUser.getParamsMap()
                                                                    .put("email", nextRow.getAttribute("Emailucad"));
                                                                opUser.getParamsMap()
                                                                    .put("struct_",
                                                                         nextRow.getAttribute("Etablissement"));
                                                                opUser.getParamsMap()
                                                                    .put("utilisateur", getUtilisateur());
                                                                Long user_id = (Long) opUser.execute();
                                                                if (0 != user_id) {
                                                                    try {
                                                                        //Ancien responsable
                                                                        OperationBinding opAncResp =
                                                                            getBindings()
                                                                            .getOperationBinding("getUserRespNivFr");
                                                                        opAncResp.getParamsMap()
                                                                            .put("niv_fr_id", niv_id);
                                                                        Long u_resp_id = (Long) opAncResp.execute();
                                                                        if (-1 != u_resp_id) {
                                                                            if (!(user_id.equals(u_resp_id))) {
                                                                                //System.out.println("Responsable nivfr differents");
                                                                                try {
                                                                                    OperationBinding opupd =
                                                                                        getBindings()
                                                                                        .getOperationBinding("UpdateResponsableNivFr");
                                                                                    opupd.getParamsMap()
                                                                                        .put("p_old", u_resp_id);
                                                                                    opupd.getParamsMap()
                                                                                        .put("p_new", user_id);
                                                                                    opupd.getParamsMap()
                                                                                        .put("p_niv_fr", niv_id);
                                                                                    opupd.getParamsMap()
                                                                                        .put("p_user",
                                                                                             getUtilisateur());
                                                                                    Long res = (Long) opupd.execute();
                                                                                    if (-1 ==
                                                                                        res) {
                                                                                        //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                                        if ((null != role) &&
                                                                                            (null !=
                                                                                             nextRow.getAttribute("Emailucad"))) {
                                                                                            String username = null;
                                                                                            try {
                                                                                                OperationBinding opusername =
                                                                                                    bindings.getOperationBinding("getusername");
                                                                                                opusername.getParamsMap()
                                                                                                    .put("user_id",
                                                                                                         u_resp_id);
                                                                                                username =
                                                                                                    (String) opusername.execute();
                                                                                            } catch (Exception e) {
                                                                                                System.out.println(e);
                                                                                            }
                                                                                            role =
                                                                                                role.substring(0, 1) +
                                                                                                role.substring(1)
                                                                                                .toLowerCase();
                                                                                            char[] password =
                                                                                                new char[100];
                                                                                            try {
                                                                                                String matricule =
                                                                                                    nextRow.getAttribute("CodeMatricule")
                                                                                                    .toString();
                                                                                                //matricule = "passer123*";
                                                                                                password =
                                                                                                    matricule.toCharArray();
                                                                                                if (null != username) {
                                                                                                    AdministrationBean.revokeWlsRole2User(role,
                                                                                                                                          username);
                                                                                                }
                                                                                                AdministrationBean.createWlsUser(nextRow.getAttribute("Emailucad")
                                                                                                                                 .toString(),
                                                                                                                                 password);
                                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                                      nextRow.getAttribute("Emailucad")
                                                                                                                                      .toString());
                                                                                            } catch (Exception e) {
                                                                                                System.out.println(e);
                                                                                            }
                                                                                        }
                                                                                    } else if (0 == res) {
                                                                                        System.out.println("erreur dans le parametrage du nouveau resp");
                                                                                    } else {
                                                                                        //System.out.println("Tjrs responsable de formation RAS");
                                                                                        if ((null != role) &&
                                                                                            (null !=
                                                                                             nextRow.getAttribute("Emailucad"))) {
                                                                                            role =
                                                                                                role.substring(0, 1) +
                                                                                                role.substring(1)
                                                                                                .toLowerCase();
                                                                                            char[] password =
                                                                                                new char[100];
                                                                                            try {
                                                                                                String matricule =
                                                                                                    nextRow.getAttribute("CodeMatricule")
                                                                                                    .toString();
                                                                                                //matricule = "passer123*";
                                                                                                password =
                                                                                                    matricule.toCharArray();
                                                                                                AdministrationBean.createWlsUser(nextRow.getAttribute("Emailucad")
                                                                                                                                 .toString(),
                                                                                                                                 password);
                                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                                      nextRow.getAttribute("Emailucad")
                                                                                                                                      .toString());
                                                                                            } catch (Exception e) {
                                                                                                System.out.println(e);
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                } catch (Exception e) {
                                                                                    System.out.println(e);
                                                                                }
                                                                            }
                                                                        } else {
                                                                            System.out.println("Plusieurs responsables veuillez corriger");
                                                                        }
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }

                                                                }
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                        //################################# End Responsable Niveau ############
                                                        niv_sec_integrated.add(niv_id);
                                                        try {
                                                            DCIteratorBinding iterHightLevel =
                                                                (DCIteratorBinding) bindings.get("HightLevelRolesIterator");
                                                            //System.out.println("Hight level size : "+iterHightLevel.getEstimatedRowCount());
                                                            if (null != iterHightLevel.getCurrentRow()) {
                                                                RowSetIterator rsie =
                                                                    iterHightLevel.getViewObject()
                                                                    .createRowSetIterator(null);
                                                                while (rsie.hasNext()) {
                                                                    Row nextRowHight = rsie.next();
                                                                    try {
                                                                        OperationBinding opUserForm =
                                                                            getBindings()
                                                                            .getOperationBinding("createOrUpdateUserForm");
                                                                        opUserForm.getParamsMap()
                                                                            .put("user_",
                                                                                 nextRowHight.getAttribute("IdUtilisateur"));
                                                                        opUserForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserForm.getParamsMap().put("form_", fr_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        OperationBinding opUserForm =
                                                                            getBindings()
                                                                            .getOperationBinding("createOrUpdateUserNivForm");
                                                                        opUserForm.getParamsMap()
                                                                            .put("user_",
                                                                                 nextRowHight.getAttribute("IdUtilisateur"));
                                                                        opUserForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserForm.getParamsMap()
                                                                            .put("niv_form_", niv_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                }
                                                                rsie.closeRowSetIterator();
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            OperationBinding opAdmin =
                                                                getBindings().getOperationBinding("getAdminEtab");
                                                            opAdmin.execute();
                                                            DCIteratorBinding iterAdm =
                                                                (DCIteratorBinding) bindings.get("AdminEtabIterator");
                                                            if (null != iterAdm.getCurrentRow()) {
                                                                RowSetIterator rsie =
                                                                    iterAdm.getViewObject().createRowSetIterator(null);
                                                                while (rsie.hasNext()) {
                                                                    Row nextRowAdm = rsie.next();
                                                                    try {
                                                                        OperationBinding opUserForm =
                                                                            getBindings()
                                                                            .getOperationBinding("createOrUpdateUserForm");
                                                                        opUserForm.getParamsMap()
                                                                            .put("user_",
                                                                                 nextRowAdm.getAttribute("IdUtilisateur"));
                                                                        opUserForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserForm.getParamsMap().put("form_", fr_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        OperationBinding opUserForm =
                                                                            getBindings()
                                                                            .getOperationBinding("createOrUpdateUserNivForm");
                                                                        opUserForm.getParamsMap()
                                                                            .put("user_",
                                                                                 nextRowAdm.getAttribute("IdUtilisateur"));
                                                                        opUserForm.getParamsMap()
                                                                            .put("role", "ACCES SIMPLE");
                                                                        opUserForm.getParamsMap()
                                                                            .put("niv_form_", niv_id);
                                                                        opUserForm.getParamsMap()
                                                                            .put("utilisateur", getUtilisateur());
                                                                        opUserForm.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                }
                                                                rsie.closeRowSetIterator();
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        try {
                                                            OperationBinding intgrateNivFormCrt =
                                                                getBindings()
                                                                .getOperationBinding("integrateNiveauFormCrt");
                                                            intgrateNivFormCrt.getParamsMap().put("niv_fr", niv_id);
                                                            intgrateNivFormCrt.getParamsMap()
                                                                .put("crt_code", nextRow.getAttribute("Cohorte"));
                                                            intgrateNivFormCrt.getParamsMap()
                                                                .put("actif",
                                                                     nextRow.getAttribute("Actif") != null ?
                                                                     nextRow.getAttribute("Actif").toString() :
                                                                     "0"); //rw.getAttribute("Actif") != null ? rw.getAttribute("Actif").toString() : "0"
                                                            intgrateNivFormCrt.getParamsMap()
                                                                .put("nbr_etd",
                                                                     null != nextRow.getAttribute("NombreEtudiant") ?
                                                                     nextRow.getAttribute("NombreEtudiant").toString() :
                                                                     "0");
                                                            intgrateNivFormCrt.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            Long niv_form_crt_id = (Long) intgrateNivFormCrt.execute();
                                                            //System.out.println(" niv_form_crt_id : " + niv_form_crt_id);
                                                            Long niv_form_spec_id = new Long(0);
                                                            Long niv_form_opt_id = new Long(0);
                                                            if (0 !=
                                                                niv_form_crt_id) {
                                                                //createNiveauFormationSpecialite if scolSpec is not null
                                                                if (null !=
                                                                    nextRow.getAttribute("CodeSpecialiteDiplome")) {
                                                                    OperationBinding intgrateNivFormSpec =
                                                                        getBindings()
                                                                        .getOperationBinding("integrateNiveauFormSpec");
                                                                    intgrateNivFormSpec.getParamsMap()
                                                                        .put("niv_fr", niv_id);
                                                                    intgrateNivFormSpec.getParamsMap()
                                                                        .put("spec_code",
                                                                             nextRow.getAttribute("CodeSpecialiteDiplome"));
                                                                    intgrateNivFormSpec.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    niv_form_spec_id =
                                                                        (Long) intgrateNivFormSpec.execute();
                                                                }
                                                                if (null !=
                                                                    nextRow.getAttribute("CodeOption")) { //CodeOption
                                                                    OperationBinding intgrateNivFormOpt =
                                                                getBindings()
                                                                .getOperationBinding("integrateNiveauFormOpt");
                                                                    intgrateNivFormOpt.getParamsMap()
                                                                        .put("niv_fr", niv_id);
                                                                    intgrateNivFormOpt.getParamsMap()
                                                                        .put("niv_fr_spec", niv_form_spec_id);
                                                                    intgrateNivFormOpt.getParamsMap()
                                                                        .put("opt_code",
                                                                             nextRow.getAttribute("CodeOption"));
                                                                    intgrateNivFormOpt.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    niv_form_opt_id =
                                                                        (Long) intgrateNivFormOpt.execute();
                                                                }
                                                                try {
                                                                    OperationBinding intgrateNivFormPrcrs =
                                                                        getBindings()
                                                                        .getOperationBinding("integrateNivFormPrcrs");
                                                                    intgrateNivFormPrcrs.getParamsMap()
                                                                        .put("niv_form_crt", niv_form_crt_id);
                                                                    intgrateNivFormPrcrs.getParamsMap()
                                                                        .put("niv_form_spec", niv_form_spec_id);
                                                                    intgrateNivFormPrcrs.getParamsMap()
                                                                        .put("niv_form_opt", niv_form_opt_id);
                                                                    intgrateNivFormPrcrs.getParamsMap()
                                                                        .put("utilisateur", getUtilisateur());
                                                                    Long niv_form_prcrs_id =
                                                                        (Long) intgrateNivFormPrcrs.execute();
                                                                    //System.out.println(" niv_form_prcrs_id : "+niv_form_prcrs_id);
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                                try {
                                                                    String msg = null;
                                                                    Long niveau_id = new Long(0);
                                                                    Long gf_id = new Long(0);
                                                                    //System.out.println("Create calendrier");
                                                                    try {
                                                                        OperationBinding opniv =
                                                                            getBindings()
                                                                            .getOperationBinding("getNiveau");
                                                                        opniv.getParamsMap()
                                                                            .put("anc_code",
                                                                                 nextRow.getAttribute("NiveauCode"));
                                                                        niveau_id = (Long) opniv.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        OperationBinding opgf =
                                                                            getBindings()
                                                                            .getOperationBinding("getgradeFormation");
                                                                        opgf.getParamsMap()
                                                                            .put("anc_code",
                                                                                 nextRow.getAttribute("CodeGradeDiplome"));
                                                                        gf_id = (Long) opgf.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    //System.out.println("niveau_id : " + niveau_id + " gf_id : " + gf_id);
                                                                    if ((0 != niveau_id) && (0 != gf_id)) {
                                                                        //getsemestreBygradeSemestre
                                                                        try {
                                                                            OperationBinding opgs =
                                                                                getBindings()
                                                                                .getOperationBinding("getsemestregrade");
                                                                            opgs.getParamsMap()
                                                                                .put("niv_id", niveau_id);
                                                                            opgs.getParamsMap().put("gf_id", gf_id);
                                                                            opgs.execute();
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        DCIteratorBinding itersem =
                                                                            (DCIteratorBinding) getBindings()
                                                                            .get("getsemestregradeIterator");
                                                                        //System.out.println("grade semestre count : " + itersem.getEstimatedRowCount());
                                                                        if (itersem.getCurrentRow() != null) {
                                                                            RowSetIterator rsie =
                                                                                itersem.getViewObject()
                                                                                .createRowSetIterator(null);
                                                                            while (rsie.hasNext()) {
                                                                                Row nextRowSem = rsie.next();
                                                                                Integer nbr_cal = 0;
                                                                                try {
                                                                                    OperationBinding is_exist =
                                                                                        getBindings()
                                                                                        .getOperationBinding("isCalendrierExist");
                                                                                    is_exist.getParamsMap()
                                                                                        .put("niv_crt_id",
                                                                                             niv_form_crt_id);
                                                                                    is_exist.getParamsMap()
                                                                                        .put("sem_id",
                                                                                             nextRowSem.getAttribute("IdSemestre"));
                                                                                    is_exist.getParamsMap()
                                                                                        .put("an_id",
                                                                                             getAnne_univers());
                                                                                    nbr_cal =
                                                                                        (Integer) is_exist.execute();
                                                                                } catch (Exception e) {
                                                                                    System.out.println(e);
                                                                                }
                                                                                if (0 == nbr_cal) {
                                                                                    Long cal_id = new Long(0);
                                                                                    try {
                                                                                        OperationBinding opnewcal =
                                                                                            getBindings()
                                                                                            .getOperationBinding("CreateOrUpdateCalendar");
                                                                                        opnewcal.getParamsMap()
                                                                                            .put("niv_crt_id",
                                                                                                 niv_form_crt_id);
                                                                                        opnewcal.getParamsMap()
                                                                                            .put("sem_id",
                                                                                                 nextRowSem.getAttribute("IdSemestre"));
                                                                                        opnewcal.getParamsMap()
                                                                                            .put("an_id",
                                                                                                 getAnne_univers());
                                                                                        opnewcal.getParamsMap()
                                                                                            .put("utilisateur",
                                                                                                 getUtilisateur());
                                                                                        opnewcal.getParamsMap()
                                                                                            .put("sess_id", 1);
                                                                                        cal_id =
                                                                                            new Long((Integer) opnewcal.execute());
                                                                                    } catch (Exception e) {
                                                                                        System.out.println(e);
                                                                                    }
                                                                                }
                                                                            }
                                                                            rsie.closeRowSetIterator();
                                                                        } else {
                                                                            msg =
                                                                                "Veuillez rattacher le Grade de Formation aux semestres correspondants";
                                                                            sessionScope.put("msg", msg);

                                                                        }
                                                                    } else {
                                                                        //System.out.println("niveau_id : " + niveau_id + " gf_id : " + gf_id);
                                                                        msg =
                                                                            "Veuillez vous rassurer que le Niveau et le Grade correspondent";
                                                                        sessionScope.put("msg", msg);
                                                                        //System.out.println("Veuillez vous rassurer que le Niveau et le Grade correspondent");

                                                                    }
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        //Parametrer le responsable du niveau de formation
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }

                                                //###################### RESPONSABLE FORMATION ####################################################################
                                                if (null != nextRow.getAttribute("Emailucad")) {
                                                    //System.out.println("Responsable Fr");
                                                    //System.out.println("Etablissement : "+nextRow.getAttribute("Etablissement"));
                                                    String role = null;
                                                    try {
                                                        OperationBinding oprole =
                                                            bindings.getOperationBinding("getrole");
                                                        oprole.getParamsMap().put("role_id", R_FR); //resp fr
                                                        role = (String) oprole.execute();
                                                        //System.out.println("Role : " + role);
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    try {
                                                        OperationBinding opUser =
                                                            getBindings().getOperationBinding("createOrUpdateUser");
                                                        opUser.getParamsMap()
                                                            .put("email", nextRow.getAttribute("Emailucad"));
                                                        opUser.getParamsMap()
                                                            .put("struct_", nextRow.getAttribute("Etablissement"));
                                                        opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                                        Long user_id = (Long) opUser.execute();
                                                        if (0 != user_id) {
                                                            try {
                                                                //Ancien responsable
                                                                OperationBinding opAncResp =
                                                                    getBindings().getOperationBinding("getUserRespFr");
                                                                opAncResp.getParamsMap().put("fr_id", fr_id);
                                                                Long u_resp_id = (Long) opAncResp.execute();
                                                                //System.out.println("new resp id : " + user_id);
                                                                //System.out.println("Anc resp id : "+u_resp_id);
                                                                if (-1 != u_resp_id) {
                                                                    if (!(user_id.equals(u_resp_id))) {
                                                                        //System.out.println("Responsable fr differents");
                                                                        try {
                                                                            OperationBinding opupd =
                                                                                getBindings()
                                                                                .getOperationBinding("UpdateResponsableFr");
                                                                            opupd.getParamsMap()
                                                                                .put("p_old", u_resp_id);
                                                                            opupd.getParamsMap().put("p_new", user_id);
                                                                            opupd.getParamsMap().put("p_fr", fr_id);
                                                                            opupd.getParamsMap()
                                                                                .put("p_user", getUtilisateur());
                                                                            Long res = (Long) opupd.execute();
                                                                            //System.out.println("resultat update resp : " + res);
                                                                            if (-1 ==
                                                                                res) {
                                                                                //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                                if (null != role &&
                                                                                    null !=
                                                                                    nextRow.getAttribute("Emailucad")) {
                                                                                    String username = null;
                                                                                    try {
                                                                                        OperationBinding opusername =
                                                                                            bindings.getOperationBinding("getusername");
                                                                                        opusername.getParamsMap()
                                                                                            .put("user_id", u_resp_id);
                                                                                        username =
                                                                                            (String) opusername.execute();
                                                                                        //System.out.println("Login anc responsable : " + username);
                                                                                    } catch (Exception e) {
                                                                                        System.out.println(e);
                                                                                    }
                                                                                    role =
                                                                                        role.substring(0, 1) +
                                                                                        role.substring(1).toLowerCase();
                                                                                    char[] password = new char[100];
                                                                                    try {
                                                                                        String matricule =
                                                                                            nextRow.getAttribute("CodeMatricule")
                                                                                            .toString();
                                                                                        //matricule = "passer123*";
                                                                                        password =
                                                                                            matricule.toCharArray();
                                                                                        if (null != username) {
                                                                                            AdministrationBean.revokeWlsRole2User(role,
                                                                                                                                  username);
                                                                                        }
                                                                                        AdministrationBean.createWlsUser(nextRow.getAttribute("Emailucad")
                                                                                                                         .toString(),
                                                                                                                         password);
                                                                                        AdministrationBean.assignWlsRole2User(role,
                                                                                                                              nextRow.getAttribute("Emailucad")
                                                                                                                              .toString());
                                                                                    } catch (Exception e) {
                                                                                        System.out.println(e);
                                                                                    }
                                                                                }
                                                                            } else if (0 == res) {
                                                                                System.out.println("erreur dans le parametrage du nouveau resp");
                                                                            } else {
                                                                                //System.out.println("Tjrs responsable de formation RAS");
                                                                                if (null != role &&
                                                                                    null !=
                                                                                    nextRow.getAttribute("Emailucad")) {
                                                                                    role =
                                                                                        role.substring(0, 1) +
                                                                                        role.substring(1).toLowerCase();
                                                                                    char[] password = new char[100];
                                                                                    try {
                                                                                        String matricule =
                                                                                            nextRow.getAttribute("CodeMatricule")
                                                                                            .toString();
                                                                                        //matricule = "passer123*";
                                                                                        password =
                                                                                            matricule.toCharArray();
                                                                                        AdministrationBean.createWlsUser(nextRow.getAttribute("Emailucad")
                                                                                                                         .toString(),
                                                                                                                         password);
                                                                                        AdministrationBean.assignWlsRole2User(role,
                                                                                                                              nextRow.getAttribute("Emailucad")
                                                                                                                              .toString());
                                                                                    } catch (Exception e) {
                                                                                        System.out.println(e);
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.out.println("Plusieurs responsables veuillez corriger");
                                                                }
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }

                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }

                                            } else {
                                                //System.out.println("Code section différent formation accredite et niveau section ! Corriger");
                                                RichPopup popup = this.getPopDfSection();
                                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                                //empty hints renders dialog in center of screen
                                                popup.show(hints);
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
                        else{
                            try{
                                p.setCode(Long.parseLong(nextRow.getAttribute("CodeNivSec").toString()));
                                p.setLibelle(nextRow.getAttribute("LibelleLong").toString());
                                niv_sec_not_integrated.add(p);
                            }catch(Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                }
            }
            rsi.closeRowSetIterator();
            //System.out.println("size : "+niv_sec_not_integrated.size());
            if (v_isChecked) {
                //System.out.println("Is Selected ok");
                DCIteratorBinding iterBind =
                    (DCIteratorBinding) getBindings().get("ScolNivFormPrcrsNotIntegratedIterator");
                ViewObject vo = iterBind.getViewObject();
                vo.executeQuery();

                iterBind = (DCIteratorBinding) getBindings().get("ScolNivFormPrcrsIntegratedIterator");
                vo = iterBind.getViewObject();
                vo.executeQuery();
            } else {
                RichPopup popup = this.getPopNoPrcrsSelected();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            //this.getIsPrcrs_Form().setValue(false);
            if(0 != niv_sec_not_integrated.size()){
                //sessionScope.put("prcrs_not_int", niv_sec_not_integrated);
                RichPopup popup = this.getPopNoPrcrsNotInt();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpPrcrsNotIntegrated());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColPrcrsNotIntegrated());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabPrcrsNotIntegrated());

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpPrcrsIntegrated());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColPrcrsIntegrated());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabPrcrsIntegrated());
        } 
    }

    public void setPanGrpPrcrsNotIntegrated(RichPanelGroupLayout panGrpPrcrsNotIntegrated) {
        this.panGrpPrcrsNotIntegrated = panGrpPrcrsNotIntegrated;
    }

    public RichPanelGroupLayout getPanGrpPrcrsNotIntegrated() {
        return panGrpPrcrsNotIntegrated;
    }

    public void setPanColPrcrsNotIntegrated(RichPanelCollection panColPrcrsNotIntegrated) {
        this.panColPrcrsNotIntegrated = panColPrcrsNotIntegrated;
    }

    public RichPanelCollection getPanColPrcrsNotIntegrated() {
        return panColPrcrsNotIntegrated;
    }

    public void setTabPrcrsNotIntegrated(RichTable tabPrcrsNotIntegrated) {
        this.tabPrcrsNotIntegrated = tabPrcrsNotIntegrated;
    }

    public RichTable getTabPrcrsNotIntegrated() {
        return tabPrcrsNotIntegrated;
    }

    public void onSelectedPrcrsAction(ValueChangeEvent valueChangeEvent) {
        //System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ScolNivFormPrcrsNotIntegratedIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //nextRow.setAttribute("isNivSelected", valueChangeEvent.getNewValue());
            nextRow.setAttribute("isChecked", valueChangeEvent.getNewValue());
        }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanGrpPrcrsNotIntegrated());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanColPrcrsNotIntegrated());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTabPrcrsNotIntegrated());
    }

    public void setIsPrcrs_Form(RichSelectBooleanCheckbox isPrcrs_Form) {
        this.isPrcrs_Form = isPrcrs_Form;
    }

    public RichSelectBooleanCheckbox getIsPrcrs_Form() {
        return isPrcrs_Form;
    }

    public void setPanGrpPrcrsIntegrated(RichPanelGroupLayout panGrpPrcrsIntegrated) {
        this.panGrpPrcrsIntegrated = panGrpPrcrsIntegrated;
    }

    public RichPanelGroupLayout getPanGrpPrcrsIntegrated() {
        return panGrpPrcrsIntegrated;
    }

    public void setPanColPrcrsIntegrated(RichPanelCollection panColPrcrsIntegrated) {
        this.panColPrcrsIntegrated = panColPrcrsIntegrated;
    }

    public RichPanelCollection getPanColPrcrsIntegrated() {
        return panColPrcrsIntegrated;
    }

    public void setTabPrcrsIntegrated(RichTable tabPrcrsIntegrated) {
        this.tabPrcrsIntegrated = tabPrcrsIntegrated;
    }

    public RichTable getTabPrcrsIntegrated() {
        return tabPrcrsIntegrated;
    }

    public void onTestType(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding opTest = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("testType");
        opTest.execute();
    }

    public void setPopDfSection(RichPopup popDfSection) {
        this.popDfSection = popDfSection;
    }

    public RichPopup getPopDfSection() {
        return popDfSection;
    }

    public void setPopNoPrcrsSelected(RichPopup popNoPrcrsSelected) {
        this.popNoPrcrsSelected = popNoPrcrsSelected;
    }

    public RichPopup getPopNoPrcrsSelected() {
        return popNoPrcrsSelected;
    }

    public void setPopNoPrcrsNotInt(RichPopup popNoPrcrsNotInt) {
        this.popNoPrcrsNotInt = popNoPrcrsNotInt;
    }

    public RichPopup getPopNoPrcrsNotInt() {
        return popNoPrcrsNotInt;
    }
}
