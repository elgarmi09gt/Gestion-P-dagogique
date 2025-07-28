package view.beans.evaluation.deliberation_sem;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.entities.java.EudiantsUes;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.event.graph.SelectionEvent;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

import view.beans.entities.Controle;
import view.beans.entities.Ec;
import view.beans.entities.EcBis;
import view.beans.entities.Etudiant;
import view.beans.entities.EtudiantsEcs;
import view.beans.entities.FilEcRepecheUe;
import view.beans.entities.FiliereEc;
import view.beans.entities.FiliereUE;
import view.beans.entities.Ue;
import view.beans.entities.UeBis;

public class DeliberationSemestrielleBean {

    private List<Etudiant> etudiantlists;
    private List<Ue> uelists;
    private List<UeBis> uebislist;
    private List<Ec> eclists;
    private List<EcBis> ecbislist;
    private LinkedHashMap<String, String> list;
    private List<LinkedHashMap<String, String>> reslists;
    private boolean allowedDeliberation;
    private RichTable deliberationTable;
    private RichButton deliberationBtn;
    private RichButton compensationBtn;
    private RichButton clotureBtn;
    private RichPopup popupShowDetNotUeDelibaretedAll;
    private RichPopup popupShowDetAnClosed;
    private RichPopup popupShowSuccessDelibSem;
    private RichPopup popupShowDetNotSemCompensed;
    private RichPopup popupClosedSemOk;
    private RichPopup popupSemNotDelib;
    private RichPopup popupCompensedSemOk;
    private RichPanelCollection pancolDelibSem;
    private RichPopup popupCompensedSemFailed;

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer calendrier = Integer.parseInt(sessionScope.get("id_calendrier").toString());
    private Integer cohorte = Integer.parseInt(sessionScope.get("id_niv_form_cohorte").toString());
    private Long prcrs_maq_an = Long.parseLong(sessionScope.get("prcrs_maq_an").toString());
    private RichPopup popupAllUeClosed;
    private RichPopup popupOtherError;
    private RichPopup popupClosedAllSuccess;
    private RichInputNumberSpinbox noteSup;
    private RichInputNumberSpinbox noteInf;
    private RichTable resSearchTable;
    private RichTable resTable;
    private RichTable detailsTable;
    private Float moyenneUe = new Float(0);
    private Float moyenneSemestre = new Float(0);
    private RichTable ueTable;
    private RichInputText inputMoyenneSemestre;
    private RichTable resUeRepecheSemTable;
    private RichInputText inputMoyenneUe;
    private RichPopup popupEnterInterval;
    private RichTable resEcRepecheSem;

    ArrayList<Long> listInsPedUe = new ArrayList<Long>();
    ArrayList<Long> listAllradyCalculateEc = new ArrayList<Long>();
    ArrayList<Long> listNatureCompensed = new ArrayList<Long>();
    private RichPopup popupSaveRepeche;
    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
   // private int currentYear = (int) (new Date().getYear() + 1900);
    private RichPopup popupConfirmPublishSem;
    private RichPopup popupSemNotClosedYet;
    private RichPopup popupSemPublishSucced;
    private RichPopup getPopupsess1notclosed;
    private RichPanelGroupLayout panelbtn;
    private RichPopup popupNoRuleParametred;
    private RichPopup popupAskCreateCalSess2;
    private RichPopup popConfirmOpenSem;
    private RichDialog popupSemOpened;
    private RichPopup popupAnClosed;
    private RichPopup popSemOpened;
    private RichPanelGroupLayout panGrp;
    private RichPanelGroupLayout panGrpSup;
    private RichPanelGroupLayout panBtn;
    private RichInputNumberSpinbox repecheNote;
    private RichPanelCollection repechPanCol;
    private RichPanelGroupLayout repechPanGrp;
    private RichPanelGroupLayout noteRefPanGrp;
    private RichPopup popConfirmPubSem;
    private RichPopup popAllUeNotClosed;
    private RichInputNumberSpinbox repsemNote;
    private RichPanelCollection repsemPanCol;
    private RichPanelGroupLayout repSemPanGrp;
    private RichInputNumberSpinbox repsemnoteref;
    private RichPopup popIntervalNote;
    private RichPopup popConfirmRepeche;
    private RichPopup popSuccessRep;
    private RichPopup popDetailNote;
    private RichPopup popuprecSemOk;
    private RichPopup popMembreJury;
    private RichInputNumberSpinbox repsemcredref;
    private RichInputNumberSpinbox repsemCred;
    private RichPanelStretchLayout panStrech;
    private RichPanelGroupLayout delDataPanGroup;
    private RichPanelGroupLayout delibDataPanGrpbis;
    private RichPanelCollection delibDataPanColect;
    private RichTable delibDataTab;
    private RichPopup popupConfirmOpenSess2;
    private RichPopup popupConfirmRenonceDone;
    private RichTable repSemTab;
    private RichPopup popDateSoutSucced;
    private RichPopup popNotDateSoutDef;
    private RichPanelCollection etuDiplomPanColect;
    private RichTable etuDiplomTab;

    @SuppressWarnings("unchecked")
    public DeliberationSemestrielleBean() {
        //allowedDeliberation = true;
        etudiantlists = new ArrayList();

    }

    public void setAllowedDeliberation(boolean allowedDeliberation) {
        this.allowedDeliberation = allowedDeliberation;
    }

    public boolean isAllowedDeliberation() {
        return allowedDeliberation;
    }

    public void setEtudiantlists(List<Etudiant> etudiantlists) {
        this.etudiantlists = etudiantlists;
    }

    public List<Etudiant> getEtudiantlists() {
        return etudiantlists;
    }

    public void setUelists(List<Ue> uelists) {
        this.uelists = uelists;
    }

    public List<Ue> getUelists() {
        return uelists;
    }

    /*public void setEclists(List<Ec> eclists) {
        DeliberationSemestrielleBean.eclists = eclists;
    }
 
    public  List<Ec> getEclists() {
        return eclists;
    }*/

    public void setList(LinkedHashMap<String, String> list) {
        this.list = list;
    }

    public HashMap<String, String> getList() {
        return list;
    }

    public void setReslists(List<LinkedHashMap<String, String>> reslists) {
        this.reslists = reslists;
    }

    public List<LinkedHashMap<String, String>> getReslists() {
        return reslists;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    // listes des ue(fil_ue_sem) du parcours
    public List<Ue> getUeParcousAnSemSess() {
        System.out.println("In getUeParcousAnSemSess");
         OperationBinding isCloseSmstre = getBindings().getOperationBinding("isClosedSemestre");
        isCloseSmstre.getParamsMap().put("parcours", Long.parseLong(getParcours().toString()));
        isCloseSmstre.getParamsMap().put("calendrier", Long.parseLong(getCalendrier().toString()));
        Integer isclosedSem = (Integer) isCloseSmstre.execute();

        sessionScope.put("isCloseSem", isclosedSem);

        OperationBinding isDelibSmstre = getBindings().getOperationBinding("isDelibratedSemestre");
        isDelibSmstre.getParamsMap().put("parcours", Long.parseLong(getParcours().toString()));
        isDelibSmstre.getParamsMap().put("calendrier", Long.parseLong(getCalendrier().toString()));
        Integer isdelibSem = (Integer) isCloseSmstre.execute();

        sessionScope.put("isNotDelibarateSem", isdelibSem); //0:notDelibarateYet : diseable
/*
        uelists = new ArrayList();
        OperationBinding lesFilUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("ListFilUeDeliSem");
        lesFilUe.getParamsMap().put("anne", getAnne_univers());
        lesFilUe.getParamsMap().put("sem", getSemestre());
        lesFilUe.getParamsMap().put("parcours", getPrcrs_maq_an());
        lesFilUe.getParamsMap().put("sess", getSession());
        lesFilUe.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ListFilUeDeliSemIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            uelists.add(new Ue(Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstre").toString()),
                               nextRow.getAttribute("LibelleLong").toString()));
        }
        rsi.closeRowSetIterator();*/
        OperationBinding lisfilUe = getBindings().getOperationBinding("getUeParcousAnSemSess");
        uelists = (List<Ue>)lisfilUe.execute();
        return uelists;
    }

    @SuppressWarnings("unchecked")
    public List<UeBis> getUeInsPedSem(Long inspedsem) {
        uebislist = new ArrayList();
        OperationBinding lesFilUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetFilUeInsPedSem");
        lesFilUe.getParamsMap().put("id_ins_ped", inspedsem);
        lesFilUe.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FilUeInsPedSemIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            uebislist.add(new UeBis(Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString()),
                                    nextRow.getAttribute("LibelleLong").toString()));
            //System.out.println("UE : " + nextRow.getAttribute("LibelleLong").toString());
        }
        rsi.closeRowSetIterator();
        return uebislist;
    }

    @SuppressWarnings("unchecked")
    // liste des ecs pour un fil_ue_sem
    public List<Ec> getEcParcousAnSemSess(Integer fil_ue) {
        eclists = new ArrayList();
        OperationBinding lesFilUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetEcsFilUeSem");
        lesFilUe.getParamsMap().put("anne", getAnne_univers());
        lesFilUe.getParamsMap().put("sem", getSemestre());
        lesFilUe.getParamsMap().put("parcours", getPrcrs_maq_an());
        lesFilUe.getParamsMap().put("sess", getSession());
        lesFilUe.getParamsMap().put("fil_ue", fil_ue);
        lesFilUe.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemFilEcIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            /*eclists.add(new Ec(Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()),
                               nextRow.getAttribute("LibelleLong").toString(),
                               Integer.parseInt(nextRow.getAttribute("Coefficient").toString())));*/
            eclists.add(new Ec(Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()),
                               Integer.parseInt(nextRow.getAttribute("IdParcoursMaquetteAnnee").toString()),
                               Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstre").toString()),
                                           nextRow.getAttribute("LibelleLong").toString(),
                                           Double.parseDouble(nextRow.getAttribute("Coefficient").toString())
                               )
                                            
                        );
        }
        rsi.closeRowSetIterator();
        return eclists;
    }

    @SuppressWarnings("unchecked")
    public List<EcBis> getEcInsPedSemUe(Long inspedsemue) {
        ecbislist = new ArrayList();
        OperationBinding lesFilUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetFilEcInsPedSemUe");
        lesFilUe.getParamsMap().put("id_ins_ped_sem_ue", inspedsemue);
        lesFilUe.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FilEcInsPedSemUeIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            ecbislist.add(new EcBis(Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString()),
                                    nextRow.getAttribute("LibelleLong").toString(),
                                    Integer.parseInt(nextRow.getAttribute("Coefficient").toString())));
            //System.out.println("Ec : " + nextRow.getAttribute("LibelleLong").toString());

        }
        rsi.closeRowSetIterator();
        return ecbislist;
    }

    @SuppressWarnings("unchecked")
    // recuperer l'inspedsem de l'etudiant
    public Integer getInsPedSemUe(String numero, Integer fil_ue_se) {

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetInsPedSemUe");
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getPrcrs_maq_an());
        inspedsem_.getParamsMap().put("sess", getSession());
        inspedsem_.getParamsMap().put("num", numero);
        inspedsem_.getParamsMap().put("fil_ue_sem", fil_ue_se);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemInsPedSemUeIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return 0;
        }
        return Integer.parseInt(currentinsped.getAttribute("IdInscriptionPedSemUe").toString());
    }

    @SuppressWarnings("unchecked")
    // recuperer la moyenne, le resultat et le credit de l'etudiant pour un insped_sem_ue donnee
    public List<LinkedHashMap<String, String>> getResultUe(Integer inspedSemUe) {
        reslists = new ArrayList();
        list = new LinkedHashMap<String, String>();

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetMoyCredResUe");
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getPrcrs_maq_an());
        inspedsem_.getParamsMap().put("sess", getSession());
        inspedsem_.getParamsMap().put("inspedsemue", inspedSemUe);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibResUeIterator");
        Row currentinsped = iter.getCurrentRow();
        /* if (currentinsped == null) {
            list.put("Moyenne", "");
            list.put("Credit", "");
            list.put("Resultat", "");
            reslists.add(list);
            return reslists;
        } */
        try{
            list.put("Moyenne", currentinsped.getAttribute("Note").toString());
        }catch(Exception ex){
            list.put("Moyenne", "-");
        }
        try {
            list.put("Credit", currentinsped.getAttribute("Credit").toString());
        } catch (Exception ex) {
            list.put("Credit", "-");
        }
        try {
            Integer res = Integer.parseInt(currentinsped.getAttribute("Resultat").toString());
            if (res == 1 || res == 0) {
                list.put("Resultat", "NV");
            }
            if (res == 2) {
                list.put("Resultat", "V");
            }
            if (res == 3) {
                list.put("Resultat", "VC");
            }
        } catch (Exception ex) {
            list.put("Resultat", "-");
        }
        reslists.add(list);
        
        return reslists;
    }

    @SuppressWarnings("unchecked")
    // recuperer la note de l'etudiant pour un ec donne
    public String getNote(Integer insped, Integer fil_ue, Integer fil_ue_se_ec) {
        OperationBinding opNote = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("GetNoteEc");
        opNote.getParamsMap().put("annee", getAnne_univers());
        opNote.getParamsMap().put("sem", getSemestre());
        opNote.getParamsMap().put("parcours", getPrcrs_maq_an());
        opNote.getParamsMap().put("sess", getSession());
        opNote.getParamsMap().put("fil_ue", fil_ue);
        opNote.getParamsMap().put("insped", insped);
        opNote.getParamsMap().put("fil_ec", fil_ue_se_ec);
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemNoteEcIterator");
        Row currentnote = iter.getCurrentRow();
        /* if (currentnote == null) {
            return "";
        } */
        try {
            return currentnote.getAttribute("Note").toString();
        } catch (Exception ex) {
            return "ABS";
        }
    }

    @SuppressWarnings("unchecked")
    // recuperer la note de l'etudiant pour un ec donne
    public String getNoteEc(String num,Long fil_ue, Long fil_ue_se_ec) {
        try {
            OperationBinding opNote = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("getNoteEc");
            opNote.getParamsMap().put("p_calendrier", getCalendrier());
            opNote.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
            //opNote.getParamsMap().put("p_inspedsem_ue", insped);
            opNote.getParamsMap().put("p_num", num);
            opNote.getParamsMap().put("p_fil_ue", fil_ue);
            opNote.getParamsMap().put("p_fec", fil_ue_se_ec);
            String note = (String)opNote.execute();
            if(note == null){
                return "ABS";
            }else if(note.equals("-1")){
                /*System.out.println("fil_ue_se_ec : "+fil_ue_se_ec);
                System.out.println("fil_ue : " + fil_ue);
                System.out.println("num : " + num);
                System.out.println("p_prcrs_maq : " + getPrcrs_maq_an());
                System.out.println("p_calendrier : " + getCalendrier());*/
                return "-";
            }
            return note;
            /*if (-1 == note) {
                return "ABS";
            }else{
                return note.toString();
            }*/
        } catch (Exception ex) {
            //System.out.println("calendrier : "+getCalendrier()+" pma : "+getPrcrs_maq_an());
            return "-";
            /*try{
            OperationBinding opNote = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("getNoteEc");
            opNote.getParamsMap().put("p_calendrier", getCalendrier());
            opNote.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
            opNote.getParamsMap().put("p_inspedsem_ue", insped);
            opNote.getParamsMap().put("p_fec", fil_ue_se_ec);
            Integer note = (Integer)opNote.execute();
            if (-1 == note) {
                return "ABS";
            }else{
                return note.toString();
            }
            }catch(Exception e){
                return "ABS";
            }*/
        }
    }

    @SuppressWarnings("unchecked")
    // recuperer l'inspedsem de l'etudiant
    public Integer getInsPedSem(String numero) {

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetInsPedSem");
          
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getPrcrs_maq_an());
        inspedsem_.getParamsMap().put("num", numero);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemInsPedSemIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return 0;
        }
        return Integer.parseInt(currentinsped.getAttribute("IdInscriptionPedSemestre").toString());
    }

    @SuppressWarnings("unchecked")
    // recuperer la moyenne, le resultat et le credit de l'etudiant pour un insped_sem donnee
    public List<LinkedHashMap<String, String>> getResultSmstre(Integer inspedSem) {
        reslists = new ArrayList();
        list = new LinkedHashMap<String, String>();

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetMoyCredResSmstre");
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getPrcrs_maq_an());
        inspedsem_.getParamsMap().put("sess", getSession());
        inspedsem_.getParamsMap().put("inspedsem", inspedSem);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemResSemIterator");
        Row currentinsped = iter.getCurrentRow();
        //System.out.println(currentinsped.getAttributeCount());
        /*  if (currentinsped == null) {
            list.put("Moyenne", "");
            list.put("Credit", "");
            list.put("Resultat", "");
            reslists.add(list);
            return reslists;
        } */
        try{
            list.put("Moyenne", currentinsped.getAttribute("Note").toString());
        }catch (Exception e) {
            list.put("Moyenne", "-");
        }
        
        try {
            list.put("Credit", currentinsped.getAttribute("Credit").toString());
        } catch (Exception e) {
            list.put("Credit", "-");
        }

        try {
            Integer res = Integer.parseInt(currentinsped.getAttribute("Resultat").toString());
            if (res == 1 || res == 0) {
                list.put("Resultat", "NV");
            } else if (res == 2) {
                list.put("Resultat", "V");
            } else if (res == 3) {
                list.put("Resultat", "ADM");
            } else {
                list.put("Resultat", "NV");
            }
        } catch (Exception e) {
            list.put("Resultat", "-");
        }
        
        reslists.add(list);
        /*try {
            
            list.put("Moyenne", currentinsped.getAttribute("Note").toString());
            list.put("Credit", currentinsped.getAttribute("Credit").toString());
            Integer res = Integer.parseInt(currentinsped.getAttribute("Resultat").toString());
            if (res == 1 || res == 0) {
                list.put("Resultat", "NV");
            } else if (res == 2) {
                list.put("Resultat", "V");
            } else if (res == 3) {
                list.put("Resultat", "ADM");
            } else {
                list.put("Resultat", "NV");
            }
            //System.out.println(list);
            reslists.add(list);
        } catch (Exception e) {
            list.put("Moyenne", "-");
            if(null != currentinsped.getAttribute("Credit")){
                list.put("Credit", currentinsped.getAttribute("Credit").toString());
            }else{
                list.put("Credit", "-");
            }
            if(null != currentinsped.getAttribute("Resultat")){
                Integer res = Integer.parseInt(currentinsped.getAttribute("Resultat").toString());
                if (res == 1 || res == 0) {
                    list.put("Resultat", "NV");
                } else if (res == 2) {
                    list.put("Resultat", "V");
                } else if (res == 3) {
                    list.put("Resultat", "ADM");
                } else {
                    list.put("Resultat", "NV");
                }
            }else {
                list.put("Resultat", "-");
            }
            reslists.add(list);
            return reslists;
        }*/
        return reslists;
    }

    public void setDeliberationTable(RichTable deliberationTable) {
        this.deliberationTable = deliberationTable;
    }

    public RichTable getDeliberationTable() {
        return deliberationTable;
    }

    public void setDeliberationBtn(RichButton deliberationBtn) {
        this.deliberationBtn = deliberationBtn;
    }

    public RichButton getDeliberationBtn() {
        return deliberationBtn;
    }

    public void setCompensationBtn(RichButton compensationBtn) {
        this.compensationBtn = compensationBtn;
    }

    public RichButton getCompensationBtn() {
        return compensationBtn;
    }

    public void setClotureBtn(RichButton clotureBtn) {
        this.clotureBtn = clotureBtn;
    }

    public RichButton getClotureBtn() {
        return clotureBtn;
    }

    @SuppressWarnings("unchecked")
    public void isOperationsSem() {
        BindingContainer bindings = getBindings();
        try {
            OperationBinding getParcours = bindings.getOperationBinding("getParcoursInfo");
            getParcours.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        /*try{
            OperationBinding iscloseSem = bindings.getOperationBinding("IsDelibarateAn");
            iscloseSem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            iscloseSem.getParamsMap().put("session_id", getSession());
            Integer isclosed = (Integer) iscloseSem.execute();
            sessionScope.put("isdelibeAn", isclosed); //0 et n
        }catch(Exception e){
            System.out.println(e);
        }*/
        
        try{
            OperationBinding iscloseSem = bindings.getOperationBinding("isClosedSemestre");
            iscloseSem.getParamsMap().put("parcours", getPrcrs_maq_an());
            iscloseSem.getParamsMap().put("calendrier", new Long(getCalendrier()));
            Integer isclosed = (Integer) iscloseSem.execute();
            sessionScope.put("isclosedsem", isclosed);
        }catch(Exception e){
            System.out.println(e);
        }
        //isDelibratedSemestre(parcours java.lang.Long,calendrier java.lang.Long)
        try{
            OperationBinding isDelibSem = bindings.getOperationBinding("isDelibratedSemestre");
            isDelibSem.getParamsMap().put("parcours", getPrcrs_maq_an());
            isDelibSem.getParamsMap().put("calendrier", new Long(getCalendrier()));
            Integer isdelib = (Integer) isDelibSem.execute();
            sessionScope.put("isdelibsem", isdelib);
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            OperationBinding isDelibSem = bindings.getOperationBinding("isCompensedSemestre");
            isDelibSem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            isDelibSem.getParamsMap().put("calendrier", new Long(getCalendrier()));
            Integer isdelib = (Integer) isDelibSem.execute();
            sessionScope.put("iscompensedsem", isdelib);
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            OperationBinding opjury = bindings.getOperationBinding("getUtilisateurPdtJury");
            opjury.getParamsMap().put("parcours", new Long(getParcours()));
            opjury.getParamsMap().put("annee", new Long(getAnne_univers()));
            opjury.getParamsMap().put("semestre", new Long(getSemestre()));
            opjury.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            opjury.execute();
        
            DCIteratorBinding iterpdt = (DCIteratorBinding) bindings.get("UtilisateurPDTJuryIterator");
            Row current = iterpdt.getCurrentRow();
            if(null != current){
                sessionScope.put("ispdtjury", true);
            }else{
                sessionScope.put("ispdtjury", false);
            }
            
        }catch(Exception e){
            System.out.println(e);
        } 
        try{
            OperationBinding opresp = bindings.getOperationBinding("IsUserRespNivForm");
            opresp.getParamsMap().put("parcours", new Long(getParcours()));
            opresp.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            opresp.execute();
        
            DCIteratorBinding iterresp = (DCIteratorBinding) bindings.get("IsUserRespNivFormIterator");
            Row current = iterresp.getCurrentRow();
            if(null != current){
                sessionScope.put("isUrespNiv", true);
            }else{
                sessionScope.put("isUrespNiv", false);
            }
            
        }catch(Exception e){
            System.out.println(e);
        } 
        
        try{
            OperationBinding is_closed_an = bindings.getOperationBinding("isClosedAnnee");
            is_closed_an.getParamsMap().put("parcours_pma", getPrcrs_maq_an());
            is_closed_an.getParamsMap().put("session_id", getSession());
            Integer is_an_closed = (Integer) is_closed_an.execute();
            if(110 == is_an_closed){
                sessionScope.put("isAnClosed", true);
            }else{
                sessionScope.put("isAnClosed", false);
            }
        }catch(Exception e){
            System.out.println(e);
        } 
        //EtatDelib
        try{
            OperationBinding etatDelibSem = bindings.getOperationBinding("getEtatDeliberation");    
            etatDelibSem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            etatDelibSem.getParamsMap().put("calendrier", new Long(getCalendrier()));
            Integer etatdelib = (Integer) etatDelibSem.execute();
            sessionScope.put("etatdelibsem", etatdelib);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Pas d'exclusion de l'UE Memoire
        OperationBinding is_closed_all_ue = bindings.getOperationBinding("isAllUe2Closed");
        is_closed_all_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
        is_closed_all_ue.getParamsMap().put("calendrier", getCalendrier());
        is_closed_all_ue.getParamsMap().put("semestre", getSemestre());
        Integer is_all_ue_closed = (Integer) is_closed_all_ue.execute();
        System.out.println("is_all_ue_closed : "+is_all_ue_closed);
        sessionScope.put("isAllUe2Closed", is_all_ue_closed);
        
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
    }
    @SuppressWarnings("unchecked")
    public void Deliberer(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        //Verifier d'abord si la régle de compensation est défini
        OperationBinding is_compense_rule_def = bindings.getOperationBinding("isCompensRuleDefined");
        is_compense_rule_def.getParamsMap().put("parcours", new Long(getParcours()));
        is_compense_rule_def.getParamsMap().put("annee", getAnne_univers());
        Integer compense_rule_def = (Integer) is_compense_rule_def.execute();
        if (0 == compense_rule_def) {
            //System.out.println("Définir d'abord une régle de compensation");
            RichPopup popup = this.getPopupNoRuleParametred();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } 
        else {
            // Avant de déliberer, s'assurer que toutes les ues sont cloturées
            String action = "O";
            OperationBinding is_closed_all_ue = bindings.getOperationBinding("isClosedAllUe");
            is_closed_all_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            is_closed_all_ue.getParamsMap().put("calendrier", getCalendrier());
            is_closed_all_ue.getParamsMap().put("semestre", getSemestre());
            Integer is_all_ue_closed = (Integer) is_closed_all_ue.execute();
            System.out.println("is_all_ue_closed : "+is_all_ue_closed);
            if (is_all_ue_closed == 0) {
                //System.out.println("Des ues ne sont pas encores cloturées");
                OperationBinding opUeNotClosed = bindings.getOperationBinding("getUeNotClosed");
                opUeNotClosed.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
                opUeNotClosed.getParamsMap().put("p_calendrier", getCalendrier());
                opUeNotClosed.execute();
                RichPopup popup = this.getPopupShowDetNotUeDelibaretedAll();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                return;
            } 
            else {
                OperationBinding is_closed_an = bindings.getOperationBinding("isClosedAnnee");
                is_closed_an.getParamsMap().put("parcours_pma", getPrcrs_maq_an());
                is_closed_an.getParamsMap().put("session_id", getSession());
                Integer is_an_closed = (Integer) is_closed_an.execute();
                System.out.println("is_an_closed : "+is_an_closed);
                if (is_an_closed == 110) {
                    RichPopup popup = this.getPopupShowDetAnClosed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    return;
                } else {
                    if (this.getSession() == 21) {
                        System.out.println("Session 2 : ");
                        OperationBinding is_sess1_closed = bindings.getOperationBinding("IsSess1Closed");
                        is_sess1_closed.getParamsMap().put("annee", getAnne_univers());
                        is_sess1_closed.getParamsMap().put("semestre", getSemestre());
                        is_sess1_closed.getParamsMap().put("prcrs_maq_an", getPrcrs_maq_an());
                        is_sess1_closed.execute();
                        DCIteratorBinding iterAll = (DCIteratorBinding) getBindings().get("isSess1Closed1Iterator");
                        if (iterAll.getEstimatedRowCount() > 0) {
                            OperationBinding deliberer_sem = bindings.getOperationBinding("DelibererSemestre");
                            deliberer_sem.getParamsMap().put("calendrier", new Long(getCalendrier()));
                            deliberer_sem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                            deliberer_sem.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                            deliberer_sem.execute();
                            //Déliberer et refresh table
                            OperationBinding delib_sem = bindings.getOperationBinding("delibererSemestre");
                            delib_sem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                            delib_sem.getParamsMap().put("calendrier", getCalendrier());
                            delib_sem.getParamsMap().put("semestre", getSemestre());
                            delib_sem.getParamsMap().put("action", action);
                            delib_sem.getParamsMap().put("utilisateur", getUtilisateur());
                            Integer is_deliberated_sem = (Integer) delib_sem.execute();
                            System.out.println("is_deliberated_sem : "+is_deliberated_sem);
                            if (is_deliberated_sem == 212) {
                                OperationBinding op_comp_sem = bindings.getOperationBinding("Compenser");
                                op_comp_sem.getParamsMap().put("calendrier", new Long(getCalendrier()));
                                op_comp_sem.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                op_comp_sem.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                op_comp_sem.execute();
                                OperationBinding compenserSemestre = bindings.getOperationBinding("CompenserSemestre");
                                compenserSemestre.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                compenserSemestre.getParamsMap().put("calendrier", getCalendrier());
                                compenserSemestre.getParamsMap().put("action", action);
                                compenserSemestre.getParamsMap().put("utilisateur", getUtilisateur());
                                Integer compenserSemstre = (Integer) compenserSemestre.execute();
                                //System.out.println("compenserSemstre : "+compenserSemstre);
                                if (compenserSemstre == 213) {
                                    try{
                                        //System.out.println("UpdateResultatSemAdm op");
                                        OperationBinding op_updte = bindings.getOperationBinding("UpdateResultatSemAdm");
                                        op_updte.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                        op_updte.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                        op_updte.execute();
                                    }catch(Exception e){
                                        System.out.println(e);
                                    }
                                    
                                    RichPopup popup = this.getPopupShowSuccessDelibSem();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    popup.show(hints);
                                    try {
                                        OperationBinding opEtudiantUE = bindings.getOperationBinding("getEtudiantUe");
                                        EudiantsUes e = (EudiantsUes) opEtudiantUE.execute();
                                        sessionScope.put("lisEtudiantUes", e);

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelDataPanGroup());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanGrpbis());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanColect());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataTab());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                                    /*AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancolDelibSem());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeliberationTable());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());*/
                                    return;
                                } 
                            } 
                            
                        } else {
                            RichPopup popup = this.getGetPopupsess1notclosed();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                            return;
                        }
                    }
                    else {
                        OperationBinding deliberer_sem = bindings.getOperationBinding("DelibererSemestre");
                        deliberer_sem.getParamsMap().put("calendrier", new Long(getCalendrier()));
                        deliberer_sem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                        deliberer_sem.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        deliberer_sem.execute();
                        //Déliberer et refresh table
                        OperationBinding delib_sem = bindings.getOperationBinding("delibererSemestre");
                        delib_sem.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                        delib_sem.getParamsMap().put("calendrier", getCalendrier());
                        delib_sem.getParamsMap().put("semestre", getSemestre());
                        delib_sem.getParamsMap().put("action", action);
                        delib_sem.getParamsMap().put("utilisateur", getUtilisateur());
                        Integer is_deliberated_sem = (Integer) delib_sem.execute();
                        System.out.println("is_deliberated_sem "+is_deliberated_sem);
                            if (is_deliberated_sem == 212) {
                                OperationBinding op_comp_sem = bindings.getOperationBinding("Compenser");
                                op_comp_sem.getParamsMap().put("calendrier", new Long(getCalendrier()));
                                op_comp_sem.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                op_comp_sem.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                op_comp_sem.execute();
                                OperationBinding compenserSemestre = bindings.getOperationBinding("CompenserSemestre");
                                compenserSemestre.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                compenserSemestre.getParamsMap().put("calendrier", getCalendrier());
                                compenserSemestre.getParamsMap().put("action", action);
                                compenserSemestre.getParamsMap().put("utilisateur", getUtilisateur());
                                Integer compenserSemstre = (Integer) compenserSemestre.execute();
                                System.out.println("compenserSemstre : "+compenserSemstre);
                                if (compenserSemstre == 213) {
                                    try{
                                        //System.out.println("UpdateResultatSemAdm op");
                                        OperationBinding op_updte = bindings.getOperationBinding("UpdateResultatSemAdm");
                                        op_updte.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                                        op_updte.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                        op_updte.execute();
                                    }catch(Exception e){
                                        System.out.println(e);
                                    }
                                    RichPopup popup = this.getPopupShowSuccessDelibSem();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    popup.show(hints);
                                try {
                                    OperationBinding opEtudiantUE = bindings.getOperationBinding("getEtudiantUe");
                                    EudiantsUes e = (EudiantsUes) opEtudiantUE.execute();
                                    sessionScope.put("lisEtudiantUes", e);

                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelDataPanGroup());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanGrpbis());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanColect());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataTab());
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                                    /*AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancolDelibSem());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeliberationTable());
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());*/
                                    return;
                                } 
                            } 
                    }
                }
            }
        }
    }

    public void setPopupShowDetNotUeDelibaretedAll(RichPopup popupShowDetNotUeDelibaretedAll) {
        this.popupShowDetNotUeDelibaretedAll = popupShowDetNotUeDelibaretedAll;
    }

    public RichPopup getPopupShowDetNotUeDelibaretedAll() {
        return popupShowDetNotUeDelibaretedAll;
    }

    public void setPopupShowDetAnClosed(RichPopup popupShowDetAnClosed) {
        this.popupShowDetAnClosed = popupShowDetAnClosed;
    }

    public RichPopup getPopupShowDetAnClosed() {
        return popupShowDetAnClosed;
    }

    public void setPopupShowSuccessDelibSem(RichPopup popupShowSuccessDelibSem) {
        this.popupShowSuccessDelibSem = popupShowSuccessDelibSem;
    }

    public RichPopup getPopupShowSuccessDelibSem() {
        return popupShowSuccessDelibSem;
    }

    @SuppressWarnings("unchecked")
    public void Compenser(ActionEvent actionEvent) {
        //System.out.println("Compenser !!!");
        String action = "O";
        BindingContainer bindings = getBindings();
        // is_delibrated_semestre(parcours, calendrier) = 1
        // Avant de compenser, s'assurer que le semestre est délibéré et que l'anné n'est pas cloturer
        // is_closed_annee2(parcours, calendrier) = 0
        OperationBinding is_delib_sem = bindings.getOperationBinding("isDelibratedSemestre");
        is_delib_sem.getParamsMap().put("parcours", getParcours());
        is_delib_sem.getParamsMap().put("calendrier", getCalendrier());
        Integer is_delib_se = (Integer) is_delib_sem.execute();
        if (is_delib_se == 0) {
            //System.out.println("Le Semestre n'est pas délibérer");
            RichPopup popup = this.getPopupSemNotDelib();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
            return;
        } else {
            // verifier si l'année n'est pas déja cloturé
            OperationBinding is_closed_an = bindings.getOperationBinding("isClosedAnnee");
            is_closed_an.getParamsMap().put("parcours_pma", getPrcrs_maq_an());
            is_closed_an.getParamsMap().put("session_id", getSession());
            Integer is_an_closed = (Integer) is_closed_an.execute();
            if (is_an_closed == 110) {
                //System.out.println("L'annee est cloturées");
                RichPopup popup = this.getPopupShowDetAnClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
                return;
            } else {
                // Compenser les Ues compensables selon les regles de compensation
                OperationBinding compenser = bindings.getOperationBinding("compenser");
                compenser.getParamsMap().put("parcours", getParcours());
                compenser.getParamsMap().put("anne", getAnne_univers());
                compenser.getParamsMap().put("calendrier", getCalendrier());
                compenser.getParamsMap().put("utilisateur", getUtilisateur());
                compenser.execute();
                // Metre a jours la table résultat semestre en recalculant la moyenne semestrielle
                OperationBinding calc_moy_sem = bindings.getOperationBinding("calculMoyenneSemestrielle");
                calc_moy_sem.getParamsMap().put("calendrier", getCalendrier());
                calc_moy_sem.getParamsMap().put("utilisateur", getUtilisateur());
                calc_moy_sem.execute();

                OperationBinding compenserSemestre = bindings.getOperationBinding("CompenserSemestre");
                compenserSemestre.getParamsMap().put("parcours", getParcours());
                compenserSemestre.getParamsMap().put("calendrier", getCalendrier());
                compenserSemestre.getParamsMap().put("semestre", getSemestre());
                compenserSemestre.getParamsMap().put("action", action);
                compenserSemestre.getParamsMap().put("utilisateur", getUtilisateur());
                //compenserSemestre.execute();
                Integer compenserSemstre = (Integer) compenserSemestre.execute();
                if (compenserSemstre == 213) {
                    //System.out.println("Semestre Compenser avec succe");
                    RichPopup popup = this.getPopupCompensedSemOk();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancolDelibSem());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeliberationTable());
                    return;
                } else {
                    //System.out.println("erreur de compensation !!!");
                    RichPopup popup = this.getPopupCompensedSemFailed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }
            }
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void Cloturer(ActionEvent actionEvent) {
        // Le semestre doit etre compenser avant d'atre cloturer : isCompensedSemestre
        String action = "O";
        BindingContainer bindings = getBindings();
        OperationBinding isClosed = bindings.getOperationBinding("isAllUeClosed");
        isClosed.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
        isClosed.getParamsMap().put("calendrier", getCalendrier());
        isClosed.getParamsMap().put("semestre", getSemestre());
        Integer is_closed_all_ue = (Integer) isClosed.execute();
        if(0 == is_closed_all_ue){
            RichPopup popup = this.getPopAllUeNotClosed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            // clotureSemestre(Long parcours,Long calendrier,Long semestre,String action,Long utilisateur)
            OperationBinding closed_semestre = bindings.getOperationBinding("closeSemestre");
            closed_semestre.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            closed_semestre.getParamsMap().put("calendrier", getCalendrier());    
            closed_semestre.getParamsMap().put("action", action);
            closed_semestre.getParamsMap().put("utilisateur", getUtilisateur());
            Integer is_closed_sem = (Integer) closed_semestre.execute();
            //System.out.println("is_closed_sem : " + is_closed_sem);
            if (is_closed_sem == 214) {
                /*if (getSession() == 1) {
                    //check if calSess2 exist getCalSess2
                    try{
                        OperationBinding opCalSess2 = bindings.getOperationBinding("getCalSess2");
                        opCalSess2.getParamsMap().put("parcours", new Long(getParcours()));
                        opCalSess2.getParamsMap().put("annee", new Long(getAnne_univers()));
                        opCalSess2.getParamsMap().put("semestre", new Long(getSemestre()));
                        Long cal_id = (Long)opCalSess2.execute();
                        if(0 == cal_id){
                            RichPopup popup = this.getPopupAskCreateCalSess2();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }else{  
                            try{
                                OperationBinding reconduireToSess2 = bindings.getOperationBinding("ReconduireToSession2");
                                
                                reconduireToSess2.getParamsMap().put("calendrier", new Long(getCalendrier()));
                                reconduireToSess2.getParamsMap().put("parcours", new Long (getParcours()));
                                reconduireToSess2.getParamsMap().put("annee", new Long(getAnne_univers()));
                                reconduireToSess2.getParamsMap().put("sess", new Long (getSession()));
                                reconduireToSess2.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                reconduireToSess2.execute();
                                //System.out.println("ReconduireToSession2...");
                                OperationBinding opclosesaisiecc = bindings.getOperationBinding("CloseSaisieCCSess2");
                                opclosesaisiecc.getParamsMap().put("annee", new Long(getAnne_univers()));
                                opclosesaisiecc.getParamsMap().put("parcours", new Long (getParcours()));
                                opclosesaisiecc.getParamsMap().put("sem", new Long(getSemestre()));
                                opclosesaisiecc.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                opclosesaisiecc.execute();
                                RichPopup popup = this.getPopupClosedSemOk();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                popup.show(hints);
                            }catch(Exception e){
                                System.out.println(e);
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
                else{*/
                    RichPopup popup = this.getPopupClosedSemOk();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    //}
                /*AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancolDelibSem());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeliberationTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());*/
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelDataPanGroup());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanGrpbis());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanColect());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataTab());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());

            } 
            else if (is_closed_sem == 110) {
                //System.out.println("Opération impossible : année déja cloturée !!!");
                RichPopup popup = this.getPopupShowDetAnClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } 
            else if (is_closed_sem == 311) {
            //System.out.println("Le semestre doit d'abord etre compenser !!!");
            RichPopup popup = this.getPopupShowDetNotSemCompensed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
            return;
        }
        }
    }

    public void setPopupShowDetNotSemCompensed(RichPopup popupShowDetNotSemCompensed) {
        this.popupShowDetNotSemCompensed = popupShowDetNotSemCompensed;
    }

    public RichPopup getPopupShowDetNotSemCompensed() {
        return popupShowDetNotSemCompensed;
    }

    public void setPopupClosedSemOk(RichPopup popupClosedSemOk) {
        this.popupClosedSemOk = popupClosedSemOk;
    }

    public RichPopup getPopupClosedSemOk() {
        return popupClosedSemOk;
    }

    public void setPopupSemNotDelib(RichPopup popupSemNotDelib) {
        this.popupSemNotDelib = popupSemNotDelib;
    }

    public RichPopup getPopupSemNotDelib() {
        return popupSemNotDelib;
    }

    public void setPopupCompensedSemOk(RichPopup popupCompensedSemOk) {
        this.popupCompensedSemOk = popupCompensedSemOk;
    }

    public RichPopup getPopupCompensedSemOk() {
        return popupCompensedSemOk;
    }

    public void setPancolDelibSem(RichPanelCollection pancolDelibSem) {
        this.pancolDelibSem = pancolDelibSem;
    }

    public RichPanelCollection getPancolDelibSem() {
        return pancolDelibSem;
    }

    public void setPopupCompensedSemFailed(RichPopup popupCompensedSemFailed) {
        this.popupCompensedSemFailed = popupCompensedSemFailed;
    }

    public RichPopup getPopupCompensedSemFailed() {
        return popupCompensedSemFailed;
    }

    public void setParcours(Integer parcours) {
        this.parcours = parcours;
    }

    public Integer getParcours() {
        return parcours;
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getSession() {
        return session;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setCalendrier(Integer calendrier) {
        this.calendrier = calendrier;
    }

    public Integer getCalendrier() {
        return calendrier;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getSemestre() {
        return semestre;
    }

   @SuppressWarnings("unchecked")
    public EtudiantsEcs getEtudiantEc(Long fil_ue) {
        BindingContainer bindings = getBindings();
        OperationBinding lesEtudiants = bindings.getOperationBinding("getEtudiantEc");
        lesEtudiants.getParamsMap().put("anId", new Long(getAnne_univers()));
        lesEtudiants.getParamsMap().put("semId", new Long(getSemestre()));
        lesEtudiants.getParamsMap().put("prcrsIaqId", getPrcrs_maq_an());
        lesEtudiants.getParamsMap().put("sessId", new Long(getSession()));
        lesEtudiants.getParamsMap().put("filUeId", fil_ue);
         EtudiantsEcs etdtEc = (EtudiantsEcs) lesEtudiants.execute();
        System.out.println("Size : " + etdtEc.getEtudiants().size());
        System.out.println("Size ec : " + etdtEc.getListeEcs().size());
        return etdtEc;
    }

    public void OnCloseAllUeAction(ActionEvent actionEvent) {
        //closeAllUe
        /*parcours        java.lang.Integer
        calendrier      java.lang.Integer
        semestre        java.lang.Integer
        action  java.lang.String
        utilisateur     java.lang.Integer  */

    }

    @SuppressWarnings("unchecked")
    public void onCloseAllUeAction(DialogEvent dialogEvent) {
        //
        String action = "O";
        // Ue délibérer : cloture possible
        Outcome outcome = dialogEvent.getOutcome();
        //System.out.println("Outcome : " + outcome);
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            OperationBinding is_closed = bindings.getOperationBinding("closeAllUe");
            is_closed.getParamsMap().put("parcours", getParcours());
            is_closed.getParamsMap().put("calendrier", getCalendrier());
            is_closed.getParamsMap().put("semestre", getSemestre());
            is_closed.getParamsMap().put("action", action);
            is_closed.getParamsMap().put("utilisateur", getUtilisateur());
            Integer closed = (Integer) is_closed.execute();
            if (closed == 1) {
                //Success
                RichPopup popup = this.getPopupClosedAllSuccess();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (closed == 2) {
                //Ue Closed
                RichPopup popup = this.getPopupShowDetAnClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (closed == -1) {
                //Toutes les ues sont cloturés
                RichPopup popup = this.getPopupAllUeClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else {
                //Other error Verifier la cloture des inter
                RichPopup popup = this.getPopupOtherError();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void setPopupAllUeClosed(RichPopup popupAllUeClosed) {
        this.popupAllUeClosed = popupAllUeClosed;
    }

    public RichPopup getPopupAllUeClosed() {
        return popupAllUeClosed;
    }

    public void setPopupOtherError(RichPopup popupOtherError) {
        this.popupOtherError = popupOtherError;
    }

    public RichPopup getPopupOtherError() {
        return popupOtherError;
    }

    public void setPopupClosedAllSuccess(RichPopup popupClosedAllSuccess) {
        this.popupClosedAllSuccess = popupClosedAllSuccess;
    }

    public RichPopup getPopupClosedAllSuccess() {
        return popupClosedAllSuccess;
    }

    @SuppressWarnings("unchecked")
    public void Repecher(ActionEvent actionEvent) {
        BigDecimal moyenneSup = new BigDecimal(this.getNoteSup()
                                                   .getValue()
                                                   .toString());
        BigDecimal moyenneInf = new BigDecimal(this.getNoteInf()
                                                   .getValue()
                                                   .toString());
        //System.out.println("Sup > Inf :" + (moyenneSup.compareTo(moyenneInf) == 1));
        if (moyenneInf.compareTo(moyenneSup) == 1) {
            BigDecimal x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }
        OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("RepecheCriteria");
        lesEtudiants.getParamsMap().put("calendrier", getCalendrier());
        lesEtudiants.getParamsMap().put("superieur", moyenneSup);
        lesEtudiants.getParamsMap().put("inferieur", moyenneInf);
        lesEtudiants.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ResultatsSemestreVOIterator");
        //System.out.println("size : " + iter.getEstimatedRowCount());
        this.getResTable().setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResTable());

    }

    @SuppressWarnings("unchecked")
    public List<Etudiant> getRepechableStudents() {
        Boolean isresp = false;
        etudiantlists = new ArrayList();
        //BigDecimal x = new BigDecimal(1);
        BigDecimal moyenneSup = BigDecimal.valueOf(Long.parseLong(this.getNoteSup()
                                                                      .getValue()
                                                                      .toString()));
        BigDecimal moyenneInf = BigDecimal.valueOf(Long.parseLong(this.getNoteInf()
                                                                      .getValue()
                                                                      .toString()));
        //System.out.println(moyenneSup.compareTo(moyenneInf) == 1);
        /* if(moyenneSup.compareTo(moyenneInf) == 1){
            BigDecimal x = moyenneInf;
            moyenneInf = moyenneSup;
            moyenneSup = x;
        } */
        OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetResultEtuSearch");
        /*      java.math.BigDecimal
             java.math.BigDecimal
          java.lang.Long  */

        /*
        lesEtudiants.getParamsMap().put("annee", getAnne_univers());
        lesEtudiants.getParamsMap().put("sem", getSemestre());
        lesEtudiants.getParamsMap().put("parcours", getParcours()); */
        lesEtudiants.getParamsMap().put("id_cal", getCalendrier());
        lesEtudiants.getParamsMap().put("inf", moyenneInf);
        lesEtudiants.getParamsMap().put("sup", moyenneSup);
        lesEtudiants.execute();
        //System.out.println("Parcours : "+getParcours());
        //System.out.println("Annee : "+Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString()));
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemEtuRepecheROVOIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return etudiantlists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            etudiantlists.add(new Etudiant(nextRow.getAttribute("Numero").toString(),
                                           nextRow.getAttribute("PrenomNom").toString()));
        }
        rsi.closeRowSetIterator();
        //System.out.print(etudiantlists.toString());
        return etudiantlists;
    }

    public void setNoteSup(RichInputNumberSpinbox noteSup) {
        this.noteSup = noteSup;
    }

    public RichInputNumberSpinbox getNoteSup() {
        return noteSup;
    }

    public void setNoteInf(RichInputNumberSpinbox noteInf) {
        this.noteInf = noteInf;
    }

    public RichInputNumberSpinbox getNoteInf() {
        return noteInf;
    }

    public void setResSearchTable(RichTable resSearchTable) {
        this.resSearchTable = resSearchTable;
    }

    public RichTable getResSearchTable() {
        return resSearchTable;
    }

    public void setResTable(RichTable resTable) {
        this.resTable = resTable;
    }

    public RichTable getResTable() {
        return resTable;
    }

    @SuppressWarnings("unchecked")
    public void OnDetailsClicked(ActionEvent actionEvent) {
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ResultatsSemestreVOIterator");
        //System.out.println("Row count : " + iter.getEstimatedRowCount());
        Row currentRow = iter.getCurrentRow();
        if (currentRow != null) {
            //System.out.println("Current InspedSem : " + currentRow.getAttribute("IdInscriptionPedSemestre").toString());
            //GetInspedSemUe, Fil_Ue from InspedSem
            OperationBinding lesFilUe = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetFilUeInsPedSem");
            lesFilUe.getParamsMap()
                .put("id_ins_ped", Long.parseLong(currentRow.getAttribute("IdInscriptionPedSemestre").toString()));
            lesFilUe.execute();
            this.detailsTable.setVisible(true);
        }
    }

    public void setDetailsTable(RichTable detailsTable) {
        this.detailsTable = detailsTable;
    }

    public RichTable getDetailsTable() {
        return detailsTable;
    }

    public void setMoyenneUe(Float moyenneUe) {
        this.moyenneUe = moyenneUe;
    }

    public Float getMoyenneUe() {
        return moyenneUe;
    }

    public void setMoyenneSemestre(Float moyenneSemestre) {
        this.moyenneSemestre = moyenneSemestre;
    }

    public Float getMoyenneSemestre() {
        return moyenneSemestre;
    }

    @SuppressWarnings("unchecked")
    public void OnSearchClicked(ActionEvent actionEvent) {
        if ((this.getNoteSup().getValue() == null) || (this.getNoteInf().getValue() == null)) {
            //System.out.println("Enter Note");
            RichPopup popup = this.getPopupEnterInterval();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            BigDecimal moyenneSup = new BigDecimal(this.getNoteSup()
                                                       .getValue()
                                                       .toString());
            BigDecimal moyenneInf = new BigDecimal(this.getNoteInf()
                                                       .getValue()
                                                       .toString());
            //System.out.println("Sup > Inf :" + (moyenneSup.compareTo(moyenneInf) == 1));
            if (moyenneInf.compareTo(moyenneSup) == 1) {
                BigDecimal x = moyenneSup;
                moyenneSup = moyenneInf;
                moyenneInf = x;
            }
            OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .getOperationBinding("RepecheCriteria");
            lesEtudiants.getParamsMap().put("calendrier", getCalendrier());
            lesEtudiants.getParamsMap().put("superieur", moyenneSup);
            lesEtudiants.getParamsMap().put("inferieur", moyenneInf);
            lesEtudiants.execute();
            DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("ResultatsSemestreVOIterator");
            //System.out.println("size : " + iter.getEstimatedRowCount());
            if (iter.getCurrentRow() != null) {
                setMoyenneSemestre(Float.parseFloat(iter.getCurrentRow()
                                                        .getAttribute("Note")
                                                        .toString()));
            }
            DCIteratorBinding iterUe = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("ResultatFilUeSemestreROVOIterator");
            if (iterUe.getCurrentRow() != null) {
                setMoyenneUe(Float.parseFloat(iterUe.getCurrentRow()
                                                    .getAttribute("Note")
                                                    .toString()));
            }
            this.getResTable().setVisible(true);
            this.getResUeRepecheSemTable().setVisible(true);
            this.getResEcRepecheSem().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneSemestre());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
        }
    }

    public void setUeTable(RichTable ueTable) {
        this.ueTable = ueTable;
    }

    public RichTable getUeTable() {
        return ueTable;
    }

    public void setInputMoyenneSemestre(RichInputText inputMoyenneSemestre) {
        this.inputMoyenneSemestre = inputMoyenneSemestre;
    }

    public RichInputText getInputMoyenneSemestre() {
        return inputMoyenneSemestre;
    }

    public void setResUeRepecheSemTable(RichTable resUeRepecheSemTable) {
        this.resUeRepecheSemTable = resUeRepecheSemTable;
    }

    public RichTable getResUeRepecheSemTable() {
        return resUeRepecheSemTable;
    }

    public void setInputMoyenneUe(RichInputText inputMoyenneUe) {
        this.inputMoyenneUe = inputMoyenneUe;
    }

    public RichInputText getInputMoyenneUe() {
        return inputMoyenneUe;
    }

    public void setPopupEnterInterval(RichPopup popupEnterInterval) {
        this.popupEnterInterval = popupEnterInterval;
    }

    public RichPopup getPopupEnterInterval() {
        return popupEnterInterval;
    }

    public void OnResultatSemSelected(SelectionEvent selectionEvent) {
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        oracle.jbo.Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterResSem = (DCIteratorBinding) bindings.get("ResultatsSemestreVOIterator");
        Row currentResSem = dciterResSem.getCurrentRow();

        DCIteratorBinding dciterResFilUe = (DCIteratorBinding) bindings.get("ResultatFilUeSemestreROVOIterator");
        Row currentResFilUe = dciterResFilUe.getCurrentRow();

        setMoyenneSemestre(Float.parseFloat(currentResSem.getAttribute("Note").toString()));
        setMoyenneUe(Float.parseFloat(currentResFilUe.getAttribute("Note").toString()));
        //this.detailsTable.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResUeRepecheSemTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResEcRepecheSem());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneSemestre());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
    }

    public void OnResFilUeSelected(SelectionEvent selectionEvent) {
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        oracle.jbo.Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterR = (DCIteratorBinding) bindings.get("ResultatFilUeSemestreROVOIterator");
        Row currentR = dciterR.getCurrentRow();
        setMoyenneUe(Float.parseFloat(currentR.getAttribute("Note").toString()));
        //this.detailsTable.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResEcRepecheSem());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
    }

    public void setResEcRepecheSem(RichTable resEcRepecheSem) {
        this.resEcRepecheSem = resEcRepecheSem;
    }

    public RichTable getResEcRepecheSem() {
        return resEcRepecheSem;
    }

    public void OnPointJuryChanged(ValueChangeEvent valueChangeEvent) {
        ArrayList<Long> listfilEc = new ArrayList<Long>();
        ArrayList<FilEcRepecheUe> detailsfilEc = new ArrayList<FilEcRepecheUe>();
        ArrayList<FiliereEc> filUE = new ArrayList<FiliereEc>();
        ArrayList<FiliereUE> filSem = new ArrayList<FiliereUE>();
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();

        //###############################################################################################
        //#################            NoteModeEnseignement        ######################################
        //###############################################################################################
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementVO1Iterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        //getFilEcInspedSemUeEtudiant
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if (!(listfilEc.contains(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString())))) {
                listfilEc.add(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            }
        }
        rsi.closeRowSetIterator();
        //for (int i = 0; i < listfilEc.size(); i++) {
        rsi = iter.getViewObject().createRowSetIterator(null);
        //System.out.println("########################### controle ###########################");
        while (rsi.hasNext()) {
            FilEcRepecheUe fue = new FilEcRepecheUe();
            Controle cntrle = new Controle();
            Row nextRow = rsi.next();
            //System.out.println("FilEc : " + nextRow.getAttribute("IdFiliereUeSemstreEc").toString());
            Float note = null;
            cntrle.setId(Long.parseLong(nextRow.getAttribute("IdTypeControle").toString()));
            //get%CC_CT
            OperationBinding opUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetPourcentFilEc");
            opUe.getParamsMap()
                .put("id_fil_ec", Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            opUe.execute();
            DCIteratorBinding iterp = (DCIteratorBinding) getBindings().get("PourcentageCC_CT_ROVOIterator");
            Row currentP = iterp.getCurrentRow();
            if (Integer.parseInt(nextRow.getAttribute("IdTypeControle").toString()) == 1) {
                //System.out.println("%CC : " + currentP.getAttribute("PourcentageCc").toString());
                cntrle.setPourcentCntrole(Integer.parseInt(currentP.getAttribute("PourcentageCc").toString()));
            } else {
                //System.out.println("%CT : " + currentP.getAttribute("PourcentageCt").toString());
                cntrle.setPourcentCntrole(Integer.parseInt(currentP.getAttribute("PourcentageCt").toString()));
            }
            //verifier si le point est nulle
            if (nextRow.getAttribute("PointJury") != null) {
                note =
                    Float.sum(Float.parseFloat(nextRow.getAttribute("PointJury").toString()),
                              Float.parseFloat(nextRow.getAttribute("Note").toString()));
                Long x = Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString());
                if (listInsPedUe.contains(x)) {
                    //System.out.println("InspedSemUe deja existant !!!");
                } else {
                    listInsPedUe.add(x);
                }
            } else {
                note = Float.parseFloat(nextRow.getAttribute("Note").toString());
                Long x = Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString());
                if (listInsPedUe.contains(x)) {
                    listInsPedUe.remove(x);
                }
            }
            //System.out.println("Note : " + note);
            cntrle.setNote(note);
            fue.setId(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            fue.setCoef(Double.parseDouble(currentP.getAttribute("Coefficient").toString()));
            fue.setControle(cntrle);
            detailsfilEc.add(fue);
        }
        rsi.closeRowSetIterator();
        for (int i = 0; i < listfilEc.size(); i++) {
            FiliereEc fec = new FiliereEc();
            Double moyenneEc = new Double(0);
            //System.out.println("FilEc : " + listfilEc.get(i));
            for (int j = 0; j < detailsfilEc.size(); j++) {
                if (listfilEc.get(i) == detailsfilEc.get(j).getId()) {
                    fec.setId(listfilEc.get(i));
                    fec.setCoef(detailsfilEc.get(j).getCoef());
                    moyenneEc +=
                        Math.round(Double.parseDouble(detailsfilEc.get(j)
                                                                           .getControle()
                                                                           .getNote()
                                                                           .toString()) *
                                   Double.parseDouble(detailsfilEc.get(j)
                                                                                                                         .getControle()
                                                                                                                         .getPourcentCntrole()
                                                                                                                         .toString())) /
                                                    100.00;
                }
            }
            fec.setNote(moyenneEc);
            filUE.add(fec);
            DecimalFormat df = new DecimalFormat("#.###");
        }
        //System.out.println("Ue sise : " + filUE.size());
        Double moyenneUe = new Double(0);
        Double sumCoef = new Double(0);
        for (int j = 0; j < filUE.size(); j++) {
            moyenneUe += Double.parseDouble(filUE.get(j)
                                                 .getNote()
                                                 .toString()) * Double.parseDouble(filUE.get(j)
                                                                                        .getCoef()
                                                                                        .toString());
            sumCoef += filUE.get(j).getCoef();
        }
        if (sumCoef != 0) {
            setMoyenneUe(new Float(moyenneUe / sumCoef));
        } else {
            setMoyenneUe(new Float(0));
        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
    }

    public void OnValidePointJury(ActionEvent actionEvent) {
        RichPopup popup = this.getPopupSaveRepeche();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopupSaveRepeche(RichPopup popupSaveRepeche) {
        this.popupSaveRepeche = popupSaveRepeche;
    }

    public RichPopup getPopupSaveRepeche() {
        return popupSaveRepeche;
    }

    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    @SuppressWarnings("unchecked")
    public void OnDialogRepecheAction(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            if ((this.getNoteSup().getValue() == null) || (this.getNoteInf().getValue() == null)) {
                //System.out.println("Enter Note");
                RichPopup popup = this.getPopupEnterInterval();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                BigDecimal moyenneSup = new BigDecimal(this.getNoteSup()
                                                           .getValue()
                                                           .toString());
                BigDecimal moyenneInf = new BigDecimal(this.getNoteInf()
                                                           .getValue()
                                                           .toString());
                if (moyenneInf.compareTo(moyenneSup) == 1) {
                    BigDecimal x = moyenneSup;
                    moyenneSup = moyenneInf;
                    moyenneInf = x;
                }
                //commit
                DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementVO1Iterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    Float note;
                    //verifier si la note est nulle
                    //System.out.println("Insped sem all : " + nextRow.getAttribute("IdInscriptionPedSemUe").toString());
                    if (nextRow.getAttribute("PointJury") != null) {
                        note = Float.parseFloat(nextRow.getAttribute("PointJury").toString());
                        OperationBinding findUpdate = getBindings().getOperationBinding("findAndUpdateNotePointJury");
                        findUpdate.getParamsMap()
                            .put("idNoteModeEns",
                                 Long.parseLong(nextRow.getAttribute("IdNoteModeEnseignement").toString()));
                        findUpdate.getParamsMap().put("point", note);
                        findUpdate.execute();
                        executeOperation("Commit").execute();
                    }
                }
                rsi.closeRowSetIterator();
                //Recalculer la moyenne des Ecs, ues, semestre
                DCIteratorBinding iterUe = (DCIteratorBinding) getBindings().get("ResultatFilUeSemestreROVOIterator");
                RowSetIterator rsiUe = iterUe.getViewObject().createRowSetIterator(null);
                while (rsiUe.hasNext()) {
                    Row currentSem = rsiUe.next();
                    //System.out.println("InsPedSem : " + currentSem.getAttribute("IdInscriptionPedSemestre").toString());
                    //System.out.println("Nature Ue : " + currentSem.getAttribute("IdNatureUe").toString());
                    DCIteratorBinding iterEc =
                        (DCIteratorBinding) getBindings().get("NotesModeEnseignementVO1Iterator");
                    RowSetIterator rsiEc = iterEc.getViewObject().createRowSetIterator(null);
                    while (rsiEc.hasNext()) {
                        Row nextRow = rsiEc.next();
                        //System.out.println("IdInscriptionPedSemUe Ec =IdInscriptionPedSemUe Ue"+nextRow.getAttribute("IdInscriptionPedSemUe").toString()
                        //.equalsIgnoreCase(currentSem.getAttribute("IdInscriptionPedSemUe").toString()));
                        if (nextRow.getAttribute("IdInscriptionPedSemUe")
                                   .toString()
                                   .equalsIgnoreCase(currentSem.getAttribute("IdInscriptionPedSemUe").toString())) {
                            //System.out.println("Calcul Moyenne Ec : FilEc " +nextRow.getAttribute("IdFiliereUeSemstreEc").toString());
                            if (listAllradyCalculateEc.contains(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc")
                                                                               .toString()))) {
                                //System.out.println("AllRady Calculate Moyeene Ec");
                            } else {
                                OperationBinding calcul_moy_ec_final =
                                    getBindings().getOperationBinding("CalculMoyenneEcRepeche");
                                calcul_moy_ec_final.getParamsMap()
                                    .put("inspedSemUe",
                                         Integer.parseInt(nextRow.getAttribute("IdInscriptionPedSemUe").toString()));
                                calcul_moy_ec_final.getParamsMap()
                                    .put("idFilieUeSemEc",
                                         Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                                calcul_moy_ec_final.getParamsMap().put("calendrier", getCalendrier());
                                calcul_moy_ec_final.getParamsMap().put("utilisateur", getUtilisateur());
                                calcul_moy_ec_final.execute();
                                //Calcul moyenne Ue
                                OperationBinding calcul_moyenne_ue =
                                    bindings.getOperationBinding("CalculMoyenneUeRepeche");
                                calcul_moyenne_ue.getParamsMap()
                                    .put("idInspedSemUe",
                                         Integer.parseInt(nextRow.getAttribute("IdInscriptionPedSemUe").toString()));
                                calcul_moyenne_ue.getParamsMap().put("idCalendrDelib", getCalendrier());
                                calcul_moyenne_ue.getParamsMap().put("utilisateur", getUtilisateur());
                                calcul_moyenne_ue.execute();
                                listAllradyCalculateEc.add(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc")
                                                                          .toString()));
                            }
                        }
                    }
                    rsiEc.closeRowSetIterator();
                }
                rsiUe.closeRowSetIterator();
                rsiUe = iterUe.getViewObject().createRowSetIterator(null);
                //System.out.println("Compensing ...");
                while (rsiUe.hasNext()) {
                    Row nextUe = rsiUe.next();
                    //getCompensationRule
                    OperationBinding compense_rule = bindings.getOperationBinding("CompensationRule");
                    compense_rule.getParamsMap().put("parcours", getParcours());
                    compense_rule.getParamsMap().put("anne", getAnne_univers());
                    Long rule = (Long) compense_rule.execute();
                    if (listNatureCompensed.contains(Long.parseLong(nextUe.getAttribute("IdNatureUe").toString()))) {
                        //System.out.println("Nature Allready compensed");
                    } else {
                        if (rule == 2) {
                            //Par Nature
                            //System.out.println("Compensation par nature : id " + rule);
                            OperationBinding compense_nature = bindings.getOperationBinding("CompenseRepecheNature");
                            compense_nature.getParamsMap()
                                .put("idInspedSem",
                                     Integer.parseInt(nextUe.getAttribute("IdInscriptionPedSemestre").toString()));
                            compense_nature.getParamsMap().put("calendrier", getCalendrier());
                            compense_nature.getParamsMap().put("utilisateur", getUtilisateur());
                            compense_nature.getParamsMap()
                                .put("nature", Integer.parseInt(nextUe.getAttribute("IdNatureUe").toString()));
                            compense_nature.execute();
                        } else if (rule == 1) {
                            //Globale
                            //System.out.println("Compensation globale : id " + rule);
                            OperationBinding compense_gble = bindings.getOperationBinding("CompenseRepecheGlobal");
                            compense_gble.getParamsMap()
                                .put("idInspedSem",
                                     Integer.parseInt(nextUe.getAttribute("IdInscriptionPedSemestre").toString()));
                            compense_gble.getParamsMap().put("calendrier", getCalendrier());
                            compense_gble.getParamsMap().put("utilisateur", getUtilisateur());
                            compense_gble.execute();
                        } else {
                            //Autre
                            //System.out.println("Autre type de compensation ou pas de regle");
                        }
                        listNatureCompensed.add(Long.parseLong(nextUe.getAttribute("IdNatureUe").toString()));
                    }
                }
                rsiUe.closeRowSetIterator();
                //System.out.println("Compensed ");
                //CalculMoyenneSem
                DCIteratorBinding iterSem = (DCIteratorBinding) getBindings().get("ResultatsSemestreVOIterator");
                Row currentSem = iterSem.getCurrentRow();
                OperationBinding calcul_moyenne_sem = bindings.getOperationBinding("CalculMoyenneSemRepech");
                calcul_moyenne_sem.getParamsMap()
                    .put("idInspedSem",
                         Integer.parseInt(currentSem.getAttribute("IdInscriptionPedSemestre").toString()));
                calcul_moyenne_sem.getParamsMap().put("idCalendrDelib", getCalendrier());
                calcul_moyenne_sem.getParamsMap().put("utilisateur", getUtilisateur());
                calcul_moyenne_sem.execute();
                //System.out.println("Calcul Moyenne Semestre ok");
                //RefreshVO
                OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                              .getCurrentBindingsEntry()
                                                              .getOperationBinding("RepecheCriteria");
                lesEtudiants.getParamsMap().put("calendrier", getCalendrier());
                lesEtudiants.getParamsMap().put("superieur", moyenneSup);
                lesEtudiants.getParamsMap().put("inferieur", moyenneInf);
                lesEtudiants.execute();
                iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                         .getCurrentBindingsEntry()
                                                         .get("ResultatsSemestreVOIterator");
                //System.out.println("ResultatSem Repechable size : " + iter.getEstimatedRowCount());
                if (iter.getCurrentRow() != null) {
                    //setCurrentMoyenneSemestre
                    setMoyenneSemestre(Float.parseFloat(iter.getCurrentRow()
                                                            .getAttribute("Note")
                                                            .toString()));
                }
                if (iterUe.getCurrentRow() != null) {
                    //setCurrentMoyenneUe
                    setMoyenneUe(Float.parseFloat(iterUe.getCurrentRow()
                                                        .getAttribute("Note")
                                                        .toString()));
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneSemestre());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());

                FacesMessage msg = new FacesMessage(" Les points de jury sont enregistrés avec succès  ! ");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void setPopupConfirmPublishSem(RichPopup popupConfirmPublishSem) {
        this.popupConfirmPublishSem = popupConfirmPublishSem;
    }

    public RichPopup getPopupConfirmPublishSem() {
        return popupConfirmPublishSem;
    }

    @SuppressWarnings("unchecked")
    public void OnPublishDialogAction(DialogEvent dialogEvent) {
        String action = "O";
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            OperationBinding is_closed_an = bindings.getOperationBinding("isClosedAnnee");
            is_closed_an.getParamsMap().put("parcours_pma", getPrcrs_maq_an());
            is_closed_an.getParamsMap().put("session_id", getSession());
            Integer is_an_closed = (Integer) is_closed_an.execute();
            if (is_an_closed == 110) {
                //System.out.println("L'annee est cloturées");
                RichPopup popup = this.getPopupShowDetAnClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                return;
            } else {
                OperationBinding publish_sem = bindings.getOperationBinding("publishSemestre");
                publish_sem.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                publish_sem.getParamsMap().put("calendrier", getCalendrier());
                publish_sem.getParamsMap().put("action", action);
                publish_sem.getParamsMap().put("utilisateur", getUtilisateur());
                publish_sem.execute();
                RichPopup popup = this.getPopupSemPublishSucced();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopupSemNotClosedYet(RichPopup popupSemNotClosedYet) {
        this.popupSemNotClosedYet = popupSemNotClosedYet;
    }

    public RichPopup getPopupSemNotClosedYet() {
        return popupSemNotClosedYet;
    }

    public void setPopupSemPublishSucced(RichPopup popupSemPublishSucced) {
        this.popupSemPublishSucced = popupSemPublishSucced;
    }

    public RichPopup getPopupSemPublishSucced() {
        return popupSemPublishSucced;
    }

    public void setGetPopupsess1notclosed(RichPopup getPopupsess1notclosed) {
        this.getPopupsess1notclosed = getPopupsess1notclosed;
    }

    public RichPopup getGetPopupsess1notclosed() {
        return getPopupsess1notclosed;
    }

    public void setPanelbtn(RichPanelGroupLayout panelbtn) {
        this.panelbtn = panelbtn;
    }

    public RichPanelGroupLayout getPanelbtn() {
        return panelbtn;
    }

    public void setPopupNoRuleParametred(RichPopup popupNoRuleParametred) {
        this.popupNoRuleParametred = popupNoRuleParametred;
    }

    public RichPopup getPopupNoRuleParametred() {
        return popupNoRuleParametred;
    }

    public void setPopupAskCreateCalSess2(RichPopup popupAskCreateCalSess2) {
        this.popupAskCreateCalSess2 = popupAskCreateCalSess2;
    }

    public RichPopup getPopupAskCreateCalSess2() {
        return popupAskCreateCalSess2;
    }

    public void onContinueCloseSemAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            //System.out.println("No ReconduireToSession2");
            RichPopup popup = this.getPopupClosedSemOk();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopConfirmOpenSem(RichPopup popConfirmOpenSem) {
        this.popConfirmOpenSem = popConfirmOpenSem;
    }

    public RichPopup getPopConfirmOpenSem() {
        return popConfirmOpenSem;
    }

    @SuppressWarnings("unchecked")
    public void OnOpenSemAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            OperationBinding opopen = getBindings().getOperationBinding("reouvrirPrcrsSem");
            opopen.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            opopen.getParamsMap().put("calendrier",new Long(getCalendrier()));
            opopen.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            Integer result = (Integer) opopen.execute();
            if(1 == result){
                  RichPopup popup = this.getPopSemOpened();
                  RichPopup.PopupHints hints = new RichPopup.PopupHints();
                  popup.show(hints);
                  //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelDataPanGroup());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanGrpbis());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataPanColect());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibDataTab());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());

            }else if(-1 == result){
                RichPopup popup = this.getPopupAnClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }else{
                System.out.println("Other");
            }
        }
    }

    public void setPopupSemOpened(RichDialog popupSemOpened) {
        this.popupSemOpened = popupSemOpened;
    }

    public RichDialog getPopupSemOpened() {
        return popupSemOpened;
    }

    public void setPopupAnClosed(RichPopup popupAnClosed) {
        this.popupAnClosed = popupAnClosed;
    }

    public RichPopup getPopupAnClosed() {
        return popupAnClosed;
    }

    public void setPopSemOpened(RichPopup popSemOpened) {
        this.popSemOpened = popSemOpened;
    }

    public RichPopup getPopSemOpened() {
        return popSemOpened;
    }

    public void setPanGrp(RichPanelGroupLayout panGrp) {
        this.panGrp = panGrp;
    }

    public RichPanelGroupLayout getPanGrp() {
        return panGrp;
    }

    public void setPanGrpSup(RichPanelGroupLayout panGrpSup) {
        this.panGrpSup = panGrpSup;
    }

    public RichPanelGroupLayout getPanGrpSup() {
        return panGrpSup;
    }

    public void setPanBtn(RichPanelGroupLayout panBtn) {
        this.panBtn = panBtn;
    }

    public RichPanelGroupLayout getPanBtn() {
        return panBtn;
    }

    @SuppressWarnings("unchecked")
    public void onSearchRepecheAction(ActionEvent actionEvent) {
        // Add event code here...
        if(null != this.getRepecheNote().getValue() ){
            Integer repechNote = Integer.parseInt(this.getRepecheNote().getValue().toString());
            //System.out.println("repechNote : "+repechNote);
            OperationBinding listRep = getBindings().getOperationBinding("getRepechable");
            listRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
            listRep.getParamsMap().put("ref_note", repechNote);
            listRep.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepechPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepechPanCol());
        }
    }

    public void setRepecheNote(RichInputNumberSpinbox repecheNote) {
        this.repecheNote = repecheNote;
    }

    public RichInputNumberSpinbox getRepecheNote() {
        return repecheNote;
    }

    public void setRepechPanCol(RichPanelCollection repechPanCol) {
        this.repechPanCol = repechPanCol;
    }

    public RichPanelCollection getRepechPanCol() {
        return repechPanCol;
    }

    public void setRepechPanGrp(RichPanelGroupLayout repechPanGrp) {
        this.repechPanGrp = repechPanGrp;
    }

    public RichPanelGroupLayout getRepechPanGrp() {
        return repechPanGrp;
    }

    public void setNoteRefPanGrp(RichPanelGroupLayout noteRefPanGrp) {
        this.noteRefPanGrp = noteRefPanGrp;
    }

    public RichPanelGroupLayout getNoteRefPanGrp() {
        return noteRefPanGrp;
    }

    @SuppressWarnings("unchecked")
    public void onSingleRepecheAction(ActionEvent actionEvent) {
        // Add event code here...
        Integer repechNote = Integer.parseInt(this.getRepecheNote().getValue().toString());
        String op = null;
        if(repechNote <= 10){
            op = "RepecherSemestre";
        }else{
            op = "RepecherMention";
        }
        if(null != op){
            String action = "O";
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterResSem = (DCIteratorBinding) bindings.get("RepecheSemestreIterator");
            Row currentResSem = iterResSem.getCurrentRow();
            try{
                OperationBinding opRep = getBindings().getOperationBinding(op);
                opRep.getParamsMap().put("result_sem", currentResSem.getAttribute("IdResultatSemestre"));
                opRep.getParamsMap().put("ref_moyenne", repechNote);
                opRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
                opRep.getParamsMap().put("utilisateur", new Long (getUtilisateur()));
                opRep.execute();
                try{
                    OperationBinding closed_semestre = bindings.getOperationBinding("repecherSemestre");
                    closed_semestre.getParamsMap().put("parcours", getParcours());
                    closed_semestre.getParamsMap().put("calendrier", getCalendrier());
                    closed_semestre.getParamsMap().put("semestre", getSemestre());
                    closed_semestre.getParamsMap().put("action", action);
                    closed_semestre.getParamsMap().put("utilisateur", getUtilisateur());
                    Integer is_closed_sem = (Integer) closed_semestre.execute();
                    /*if(1==is_closed_sem){
                        System.out.println("success");
                    }else if(0==is_closed_sem){
                        System.out.println("An closed");
                    }else{
                        System.out.println("delib not yet");
                    }*/
                }catch(Exception e){
                    System.out.println(e);
                }
            }catch(Exception e){
                System.out.println(e);
            }
            
            OperationBinding listRep = getBindings().getOperationBinding("getRepechable");
            listRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
            listRep.getParamsMap().put("ref_note", repechNote);
            listRep.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepechPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepechPanCol());
        }
    }

    public void setPopConfirmPubSem(RichPopup popConfirmPubSem) {
        this.popConfirmPubSem = popConfirmPubSem;
    }

    public RichPopup getPopConfirmPubSem() {
        return popConfirmPubSem;
    }

    public void setPopAllUeNotClosed(RichPopup popAllUeNotClosed) {
        this.popAllUeNotClosed = popAllUeNotClosed;
    }

    public RichPopup getPopAllUeNotClosed() {
        return popAllUeNotClosed;
    }

    public void setRepsemNote(RichInputNumberSpinbox repsemNote) {
        this.repsemNote = repsemNote;
    }

    public RichInputNumberSpinbox getRepsemNote() {
        return repsemNote;
    }

    @SuppressWarnings("unchecked")
    public void OnRepechableSemAction(ActionEvent actionEvent) {
        BindingContainer bindings = this.getBindings();
        Number moyenneSup = 10.0;
        Number moyenneInf = 0.0;
        Number creditSup = 30;
        Number creditInf = 0;
        if (this.getRepsemnoteref().getValue() != null)
            moyenneSup = (Number) (this.getRepsemnoteref().getValue());
        if (this.getRepsemNote().getValue() != null)
            moyenneInf = (Number) (this.getRepsemNote().getValue());
        if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
            Number x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }
        
        if (this.getRepsemcredref().getValue() != null)
            creditSup = (Number) (this.getRepsemcredref().getValue());
        if (this.getRepsemCred().getValue() != null)
            creditInf = (Number) (this.getRepsemCred().getValue());
        if (creditInf.floatValue() > creditSup.floatValue()) {
            Number x = creditSup;
            creditSup = creditInf;
            creditInf = x;
        }
        sessionScope.put("moy_ref_sem", moyenneSup);
        sessionScope.put("moy_inf_sem", moyenneInf);
        sessionScope.put("cred_ref_sem", creditSup);
        sessionScope.put("cred_inf_sem", creditInf);
        /*System.out.println("moyenneInf : "+moyenneInf+" moyenneSup : "+moyenneSup);
        System.out.println("creditInf : " + creditInf + " creditSup : " + creditSup);
        System.out.println("calendrier : "+getCalendrier());
        System.out.println("parcours_maq : "+getPrcrs_maq_an());*/
        try {
            OperationBinding listRep = bindings.getOperationBinding("getRepechable");
            listRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
            listRep.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            listRep.getParamsMap().put("notInf", moyenneInf);
            listRep.getParamsMap().put("notSup", moyenneSup);
            listRep.getParamsMap().put("credInf", creditInf);
            listRep.getParamsMap().put("credSup", creditSup);
            listRep.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        //DCIteratorBinding iterResSem = (DCIteratorBinding) bindings.get("RepechableSemestreIterator");
        //System.out.println("size : "+iterResSem.getEstimatedRowCount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepsemPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemTab());
    }

    public void setRepsemPanCol(RichPanelCollection repsemPanCol) {
        this.repsemPanCol = repsemPanCol;
    }

    public RichPanelCollection getRepsemPanCol() {
        return repsemPanCol;
    }

    public void setRepSemPanGrp(RichPanelGroupLayout repSemPanGrp) {
        this.repSemPanGrp = repSemPanGrp;
    }

    public RichPanelGroupLayout getRepSemPanGrp() {
        return repSemPanGrp;
    }

    public void setRepsemnoteref(RichInputNumberSpinbox repsemnoteref) {
        this.repsemnoteref = repsemnoteref;
    }

    public RichInputNumberSpinbox getRepsemnoteref() {
        return repsemnoteref;
    }

    public void setPopIntervalNote(RichPopup popIntervalNote) {
        this.popIntervalNote = popIntervalNote;
    }

    public RichPopup getPopIntervalNote() {
        return popIntervalNote;
    }

    @SuppressWarnings("unchecked")
    public void OnRepecheSemAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
           Double moy_ref_sem = 10.0;
            this.getPopConfirmRepeche().hide();
            BindingContainer bindings = this.getBindings();
            DCIteratorBinding iterSem = (DCIteratorBinding) bindings.get("RepechableSemestreIterator");
            Row currentResSem = iterSem.getCurrentRow();
            if(null != currentResSem){
                if(10.0 > Double.parseDouble(currentResSem.getAttribute("Note").toString()))
                    moy_ref_sem = 10.0;
                else if ((10.0 < Double.parseDouble(currentResSem.getAttribute("Note").toString())) && 
                    (12.0 > Double.parseDouble(currentResSem.getAttribute("Note").toString())))
                    moy_ref_sem = 12.0;
                else if ((12.0 < Double.parseDouble(currentResSem.getAttribute("Note").toString())) &&
                    (14.0 > Double.parseDouble(currentResSem.getAttribute("Note").toString())))
                    moy_ref_sem = 14.0;
                else if ((14.0 < Double.parseDouble(currentResSem.getAttribute("Note").toString())) &&
                    (16.0 > Double.parseDouble(currentResSem.getAttribute("Note").toString())))
                    moy_ref_sem = 16.0;
                else if ((16.0 < Double.parseDouble(currentResSem.getAttribute("Note").toString())) &&
                    (18.0 > Double.parseDouble(currentResSem.getAttribute("Note").toString())))
                    moy_ref_sem = 18.0;
                else
                    moy_ref_sem = 20.0;
                try{
                    OperationBinding opgetRepeche = getBindings().getOperationBinding("RepechSem");
                    opgetRepeche.getParamsMap().put("inspedSem", currentResSem.getAttribute("IdInscriptionPedSemestre"));
                    opgetRepeche.getParamsMap().put("calendrier",new Long(getCalendrier()));
                    opgetRepeche.getParamsMap().put("moyenne_valid", moy_ref_sem);
                    opgetRepeche.getParamsMap().put("prcrs_maq",  getPrcrs_maq_an());
                    opgetRepeche.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    opgetRepeche.execute();
                }catch(Exception e){
                   System.out.println(e); 
                }
                try{
                    OperationBinding opgetRepeche = getBindings().getOperationBinding("repecherSemestre");
                    opgetRepeche.getParamsMap().put("parcours_maq",  getPrcrs_maq_an());
                    opgetRepeche.getParamsMap().put("calendrier",getCalendrier());
                    opgetRepeche.getParamsMap().put("action", "O");
                    opgetRepeche.getParamsMap().put("utilisateur", getUtilisateur());
                    opgetRepeche.execute();
                }catch(Exception e){
                   System.out.println(e); 
                }
                Number moyenneSup = 10.0;
                Number moyenneInf = 0.0;
                Integer creditSup = 30;
                Integer creditInf = 0;
                if (this.getRepsemnoteref().getValue() != null)
                    moyenneSup = (Number) (this.getRepsemnoteref().getValue());
                if (this.getRepsemNote().getValue() != null)
                    moyenneInf = (Number) (this.getRepsemNote().getValue());
                if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
                    Number x = moyenneSup;
                    moyenneSup = moyenneInf;
                    moyenneInf = x;
                }

                if (this.getRepsemcredref().getValue() != null)
                    creditSup = (Integer) (this.getRepsemcredref().getValue());
                if (this.getRepsemCred().getValue() != null)
                    creditInf = (Integer) (this.getRepsemCred().getValue());
                if (creditInf.intValue() > creditSup.intValue()) {
                    Integer x = creditSup;
                    creditSup = creditInf;
                    creditInf = x;
                }
                sessionScope.put("moy_ref_sem", moyenneSup);
                sessionScope.put("moy_inf_sem", moyenneInf);
                sessionScope.put("cred_ref_sem", creditSup);
                sessionScope.put("cred_inf_sem", creditInf);
                try {
                    OperationBinding listRep = bindings.getOperationBinding("getRepechable");
                    listRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    listRep.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    listRep.getParamsMap().put("notInf", moyenneInf);
                    listRep.getParamsMap().put("notSup", moyenneSup);
                    listRep.getParamsMap().put("credInf", creditInf);
                    listRep.getParamsMap().put("credSup", creditSup);
                    listRep.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepsemPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemTab());
                RichPopup popup = this.getPopSuccessRep();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopConfirmRepeche(RichPopup popConfirmRepeche) {
        this.popConfirmRepeche = popConfirmRepeche;
    }

    public RichPopup getPopConfirmRepeche() {
        return popConfirmRepeche;
    }

    public void setPopSuccessRep(RichPopup popSuccessRep) {
        this.popSuccessRep = popSuccessRep;
    }

    public RichPopup getPopSuccessRep() {
        return popSuccessRep;
    }

    @SuppressWarnings("unchecked")
    public void OnDetailNoteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding iterSem = (DCIteratorBinding) bindings.get("RepechableSemestreIterator");
        Row currentResSem = iterSem.getCurrentRow();
        if(null != currentResSem){
             int note_ref = 10;
            try{
            if(10.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()))
            //Integer.parseInt(sessionScope.get("moy_ref_sem").toString()
                 note_ref = 10;
            else if (12.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()))
                note_ref = 12;
            else if (14.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()) )
                    note_ref = 14;
            else if (16.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()) )
                note_ref = 16;
            else if (18.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()) )
                note_ref = 18;
            else if (20.0 >= Float.parseFloat(currentResSem.getAttribute("Note").toString()) )
                note_ref = 20;
            }catch(Exception e){
                System.out.println(e);
                }
             try{
                OperationBinding opDetail = getBindings().getOperationBinding("getFilUeInvald");
                
                opDetail.getParamsMap().put("inspedSem", currentResSem.getAttribute("IdInscriptionPedSemestre"));
                opDetail.getParamsMap().put("calendrier",new Long(getCalendrier()));
                opDetail.getParamsMap().put("moyenne_valid", note_ref);
                opDetail.execute();
                try{
                    OperationBinding optotalcoef = getBindings().getOperationBinding("getTotalCoef");
                    optotalcoef.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    optotalcoef.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                    Integer totalcoef = (Integer)optotalcoef.execute();
                    sessionScope.put("totalcoefSem", totalcoef);
                    //System.out.println("totalcoef "+totalcoef);
                }catch(Exception e){
                   System.out.println("In getTotal catch : "+e); 
                }
                try{
                    OperationBinding opcoefueNV = getBindings().getOperationBinding("getTotalCoefUENonValid");
                    opcoefueNV.getParamsMap().put("inspedSem", currentResSem.getAttribute("IdInscriptionPedSemestre"));
                    opcoefueNV.getParamsMap().put("calendrier",new Long(getCalendrier()));
                    opcoefueNV.getParamsMap().put("moyenne_valid", sessionScope.get("moy_ref_sem"));
                    Integer totalFilUENV = (Integer)opcoefueNV.execute();
                    sessionScope.put("totalFilUENV", totalFilUENV);
                    //System.out.println("totalFilUENV "+totalFilUENV);
                }catch(Exception e){
                   System.out.println("In getTotalCoefUeInvalid catch : "+e); 
                }
                RichPopup popup = this.getPopDetailNote();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }catch(Exception e){
               System.out.println(e); 
            }
        }
    }

    public void setPopDetailNote(RichPopup popDetailNote) {
        this.popDetailNote = popDetailNote;
    }

    public RichPopup getPopDetailNote() {
        return popDetailNote;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void doOptoOpenSess2Listener(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("doOptoOpenSess2Listener ...");
        sessionScope.put("is_openable", true);
        //System.out.println("doOptoOpenSess2Listener end");
    }

    public String openSess2AListener() {
        // Add event code here...
        //is_sess1_closed
        //is_cal_sess2_def
        //generate_cal_sess2 if not
        //System.out.println("sessionScope : "+sessionScope.get("is_openable"));
        return null;
    }

    @SuppressWarnings("unchecked")
    public void onOpenSess2Confirm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        //System.out.println("Outcome : " + outcome);
        if (outcome == Outcome.yes) {
            BindingContainer bindings = this.getBindings();
            Integer calSess2 = 0 ;
            try{
                OperationBinding opCalSess2 = bindings.getOperationBinding("getCalSess2");
                opCalSess2.getParamsMap().put("parcours", new Long(getParcours()));
                opCalSess2.getParamsMap().put("annee", new Long(getAnne_univers()));
                opCalSess2.getParamsMap().put("semestre", new Long(getSemestre()));
                Long cal_id = (Long)opCalSess2.execute();
                if(0 == cal_id){
                    //generate cal sess 2
                    try{
                        OperationBinding opnewcal =   bindings.getOperationBinding("CreateOrUpdateCalendar");
                        opnewcal.getParamsMap().put("niv_crt_id", new  Long(getCohorte()));
                        opnewcal.getParamsMap().put("sem_id", new Long(getSemestre()));
                        opnewcal.getParamsMap().put("an_id", new Long(getAnne_univers()));
                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());    
                        opnewcal.getParamsMap().put("sess_id", 21);
                        calSess2 = (Integer) opnewcal.execute();
                        //System.out.println("cal_id : "+calSess2);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                } 
                try{
                    OperationBinding reconduireToSess2 = bindings.getOperationBinding("ReconduireToSession2");
                    reconduireToSess2.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    reconduireToSess2.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    reconduireToSess2.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    reconduireToSess2.execute();
                    /*OperationBinding opclosesaisiecc = bindings.getOperationBinding("CloseSaisieCCSess2");
                    opclosesaisiecc.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    opclosesaisiecc.getParamsMap().put("calsess2", new Long (calSess2));
                    opclosesaisiecc.getParamsMap().put("semestre", new Long(getSemestre()));
                    opclosesaisiecc.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    opclosesaisiecc.execute();*/
                    RichPopup popup = this.getPopuprecSemOk();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }catch(Exception e){
                    System.out.println(e);
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void setCohorte(Integer cohorte) {
        this.cohorte = cohorte;
    }

    public Integer getCohorte() {
        return cohorte;
    }

    public void setPopuprecSemOk(RichPopup popuprecSemOk) {
        this.popuprecSemOk = popuprecSemOk;
    }

    public RichPopup getPopuprecSemOk() {
        return popuprecSemOk;
    }

    public void setPrcrs_maq_an(Long prcrs_maq_an) {
        this.prcrs_maq_an = prcrs_maq_an;
    }

    public Long getPrcrs_maq_an() {
        return prcrs_maq_an;
    }

    public void OnExportPvAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void OnDeliberateCycleActionList(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings("unchecked")
    public void onMembreJuryPresenteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        OperationBinding opjury = bindings.getOperationBinding("getJury");
        opjury.getParamsMap().put("p_prcrs", new Long(getParcours()));
        opjury.getParamsMap().put("p_an", new Long(getAnne_univers()));
        opjury.getParamsMap().put("p_sem", new Long(getSemestre()));
        opjury.execute();
        DCIteratorBinding iterJury = (DCIteratorBinding) bindings.get("JuryMembrePresentIterator");
        if(0 == iterJury.getEstimatedRowCount()){
            System.out.println("Pas de jury");
        }else if (1 < iterJury.getEstimatedRowCount()) {
            System.out.println("Plusieur jury");
        }else  {
            System.out.println("Traitement");
            Row currentJury = iterJury.getCurrentRow() ;
            OperationBinding opMjury = bindings.getOperationBinding("getUserJury");
            opMjury.getParamsMap().put("p_jury_id", currentJury.getAttribute("IdJury"));
            opMjury.execute();
            RichPopup popup = this.getPopMembreJury();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }


    }

    public void setPopMembreJury(RichPopup popMembreJury) {
        this.popMembreJury = popMembreJury;
    }

    public RichPopup getPopMembreJury() {
        return popMembreJury;
    }

    public void onSaveMembreJury(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        this.getPopMembreJury().hide();
        OperationBinding opMjury = bindings.getOperationBinding("Commit");
        opMjury.execute();
    }

    public void setRepsemcredref(RichInputNumberSpinbox repsemcredref) {
        this.repsemcredref = repsemcredref;
    }

    public RichInputNumberSpinbox getRepsemcredref() {
        return repsemcredref;
    }

    public void setRepsemCred(RichInputNumberSpinbox repsemCred) {
        this.repsemCred = repsemCred;
    }

    public RichInputNumberSpinbox getRepsemCred() {
        return repsemCred;
    }

    public void setEclists(List<Ec> eclists) {
        this.eclists = eclists;
    }

    public List<Ec> getEclists() {
        return eclists;
    }

    public void setEcbislist(List<EcBis> ecbislist) {
        this.ecbislist = ecbislist;
    }

    public List<EcBis> getEcbislist() {
        return ecbislist;
    }

    public void setPanStrech(RichPanelStretchLayout panStrech) {
        this.panStrech = panStrech;
    }

    public RichPanelStretchLayout getPanStrech() {
        return panStrech;
    }

    public void setDelDataPanGroup(RichPanelGroupLayout delDataPanGroup) {
        this.delDataPanGroup = delDataPanGroup;
    }

    public RichPanelGroupLayout getDelDataPanGroup() {
        return delDataPanGroup;
    }

    public void setDelibDataPanGrpbis(RichPanelGroupLayout delibDataPanGrpbis) {
        this.delibDataPanGrpbis = delibDataPanGrpbis;
    }

    public RichPanelGroupLayout getDelibDataPanGrpbis() {
        return delibDataPanGrpbis;
    }

    public void setDelibDataPanColect(RichPanelCollection delibDataPanColect) {
        this.delibDataPanColect = delibDataPanColect;
    }

    public RichPanelCollection getDelibDataPanColect() {
        return delibDataPanColect;
    }

    public void setDelibDataTab(RichTable delibDataTab) {
        this.delibDataTab = delibDataTab;
    }

    public RichTable getDelibDataTab() {
        return delibDataTab;
    }

    public void setPopupConfirmOpenSess2(RichPopup popupConfirmOpenSess2) {
        this.popupConfirmOpenSess2 = popupConfirmOpenSess2;
    }

    public RichPopup getPopupConfirmOpenSess2() {
        return popupConfirmOpenSess2;
    }

    public void OnConfirmRenonciationDone(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        //System.out.println("Outcome : " + outcome);
        if (outcome == Outcome.yes) {
            this.getPopupConfirmRenonceDone().hide();
            RichPopup popup = this.getPopupConfirmOpenSess2();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopupConfirmRenonceDone(RichPopup popupConfirmRenonceDone) {
        this.popupConfirmRenonceDone = popupConfirmRenonceDone;
    }

    public RichPopup getPopupConfirmRenonceDone() {
        return popupConfirmRenonceDone;
    }

    public void setRepSemTab(RichTable repSemTab) {
        this.repSemTab = repSemTab;
    }

    public RichTable getRepSemTab() {
        return repSemTab;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmRepechAllSemAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
           Double moy_ref_sem = 10.0;
            this.getPopConfirmRepeche().hide();
            BindingContainer bindings = this.getBindings();
            DCIteratorBinding iterSem = (DCIteratorBinding) bindings.get("RepechableSemestreIterator");
            RowSetIterator rsi = iterSem.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
            Row nextRow = rsi.next();
                if(10.0 > Double.parseDouble(nextRow.getAttribute("Note").toString()))
                    moy_ref_sem = 10.0;
                else if ((10.0 < Double.parseDouble(nextRow.getAttribute("Note").toString())) && 
                    (12.0 > Double.parseDouble(nextRow.getAttribute("Note").toString())))
                    moy_ref_sem = 12.0;
                else if ((12.0 < Double.parseDouble(nextRow.getAttribute("Note").toString())) &&
                    (14.0 > Double.parseDouble(nextRow.getAttribute("Note").toString())))
                    moy_ref_sem = 14.0;
                else if ((14.0 < Double.parseDouble(nextRow.getAttribute("Note").toString())) &&
                    (16.0 > Double.parseDouble(nextRow.getAttribute("Note").toString())))
                    moy_ref_sem = 16.0;
                else if ((16.0 < Double.parseDouble(nextRow.getAttribute("Note").toString())) &&
                    (18.0 > Double.parseDouble(nextRow.getAttribute("Note").toString())))
                    moy_ref_sem = 18.0;
                else
                    moy_ref_sem = 20.0;
                try{
                    OperationBinding opgetRepeche = getBindings().getOperationBinding("RepechSem");
                    opgetRepeche.getParamsMap().put("inspedSem", nextRow.getAttribute("IdInscriptionPedSemestre"));
                    opgetRepeche.getParamsMap().put("calendrier",new Long(getCalendrier()));
                    opgetRepeche.getParamsMap().put("moyenne_valid", moy_ref_sem);
                    opgetRepeche.getParamsMap().put("prcrs_maq",  getPrcrs_maq_an());
                    opgetRepeche.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    opgetRepeche.execute();
                }catch(Exception e){
                   System.out.println(e); 
                }
                try{
                    OperationBinding opgetRepeche = getBindings().getOperationBinding("repecherSemestre");
                    opgetRepeche.getParamsMap().put("parcours_maq",  getPrcrs_maq_an());
                    opgetRepeche.getParamsMap().put("calendrier",getCalendrier());
                    opgetRepeche.getParamsMap().put("action", "O");
                    opgetRepeche.getParamsMap().put("utilisateur", getUtilisateur());
                    opgetRepeche.execute();
                }catch(Exception e){
                   System.out.println(e); 
                }
                Number moyenneSup = 10.0;
                Number moyenneInf = 0.0;
                Integer creditSup = 30;
                Integer creditInf = 0;
                if (this.getRepsemnoteref().getValue() != null)
                    moyenneSup = (Number) (this.getRepsemnoteref().getValue());
                if (this.getRepsemNote().getValue() != null)
                    moyenneInf = (Number) (this.getRepsemNote().getValue());
                if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
                    Number x = moyenneSup;
                    moyenneSup = moyenneInf;
                    moyenneInf = x;
                }

                if (this.getRepsemcredref().getValue() != null)
                    creditSup = (Integer) (this.getRepsemcredref().getValue());
                if (this.getRepsemCred().getValue() != null)
                    creditInf = (Integer) (this.getRepsemCred().getValue());
                if (creditInf.intValue() > creditSup.intValue()) {
                    Integer x = creditSup;
                    creditSup = creditInf;
                    creditInf = x;
                }
                sessionScope.put("moy_ref_sem", moyenneSup);
                sessionScope.put("moy_inf_sem", moyenneInf);
                sessionScope.put("cred_ref_sem", creditSup);
                sessionScope.put("cred_inf_sem", creditInf);
                try {
                    OperationBinding listRep = bindings.getOperationBinding("getRepechable");
                    listRep.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    listRep.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    listRep.getParamsMap().put("notInf", moyenneInf);
                    listRep.getParamsMap().put("notSup", moyenneSup);
                    listRep.getParamsMap().put("credInf", creditInf);
                    listRep.getParamsMap().put("credSup", creditSup);
                    listRep.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepsemPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRepSemTab());
                RichPopup popup = this.getPopSuccessRep();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onSaveDateSoutAction(ActionEvent actionEvent) {
        DCBindingContainer bindings = (DCBindingContainer) getBindings();
        int c = 0;
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EtudiantsDiplomesIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if //(
            (null != nextRow.getAttribute("Datesoutenanceref"))
            /* && (
                (null == nextRow.getAttribute("IsUpdatable")) || 
                ((null != nextRow.getAttribute("IsUpdatable")) && (0 != Integer.parseInt(nextRow.getAttribute("IsUpdatable").toString()) ))
            ))*/{
                System.out.println("numero : "+nextRow.getAttribute("Numero").toString());
                System.out.println("Date ref: "+nextRow.getAttribute("Datesoutenanceref").toString());
                
                try {
                    OperationBinding op = getBindings().getOperationBinding("saveDateSoutenance");
                    op.getParamsMap().put("etu_id", Long.parseLong(nextRow.getAttribute("IdEtudiant").toString()));
                    op.getParamsMap().put("prcrs_maq_id", Long.parseLong(nextRow.getAttribute("IdParcoursMaquetAnnee").toString()));
                    op.getParamsMap().put("d_id", nextRow.getAttribute("Datesoutenanceref").toString());
                    op.getParamsMap().put("u_id", new Long(getUtilisateur()));
                    op.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c++;
            }
        } rsi.closeRowSetIterator();
        if(0 != c){
            ViewObject vo = iter.getViewObject();
            vo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtuDiplomPanColect());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtuDiplomTab());
            RichPopup popup = this.getPopDateSoutSucced();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }else{
            RichPopup popup = this.getPopNotDateSoutDef();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopDateSoutSucced(RichPopup popDateSoutSucced) {
        this.popDateSoutSucced = popDateSoutSucced;
    }

    public RichPopup getPopDateSoutSucced() {
        return popDateSoutSucced;
    }

    public void setPopNotDateSoutDef(RichPopup popNotDateSoutDef) {
        this.popNotDateSoutDef = popNotDateSoutDef;
    }

    public RichPopup getPopNotDateSoutDef() {
        return popNotDateSoutDef;
    }

    public void setEtuDiplomPanColect(RichPanelCollection etuDiplomPanColect) {
        this.etuDiplomPanColect = etuDiplomPanColect;
    }

    public RichPanelCollection getEtuDiplomPanColect() {
        return etuDiplomPanColect;
    }

    public void setEtuDiplomTab(RichTable etuDiplomTab) {
        this.etuDiplomTab = etuDiplomTab;
    }

    public RichTable getEtuDiplomTab() {
        return etuDiplomTab;
    }

    public void onDateSoutChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("old : "+valueChangeEvent.getOldValue());
        System.out.println("new : "+valueChangeEvent.getNewValue());
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        System.out.println("old 1: "+valueChangeEvent.getOldValue());
        System.out.println("new 1: "+valueChangeEvent.getNewValue());
    }
}
