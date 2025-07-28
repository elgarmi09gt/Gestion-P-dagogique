package view.beans.evaluation.deliberation_ue;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import model.entities.java.EtudiantsEcs;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;

import saisie_notes_mode_ens.NoteModeEns;

import view.beans.entities.Controle;
import view.beans.entities.Ec;
import view.beans.entities.Etudiant;
import view.beans.entities.EtudiantBis;
import view.beans.entities.EtudiantDelibSem;
import view.beans.entities.EtudiantDelibUE;
import view.beans.entities.FilEcRepecheUe;
import view.beans.entities.FilEcTypeCntrle;
import view.beans.entities.FiliereEc;
import view.beans.entities.ModeCntrlEc;

public class DeliberatioUeBean {

    private List<EtudiantBis> etudiantbislists;
    private List<NoteModeEns> etud_note_ens;
    private List<Etudiant> etudiantlists;
    private List<EtudiantDelibSem> etudiantSemlists;
    private List<EtudiantDelibUE> etudiantUelists;
    private List<Ec> eclists;
    private List<ModeCntrlEc> mclists;
    private List<FilEcTypeCntrle> tclists;
    private RichSelectOneChoice btnUe;
    private HashMap<String, String> list;
    private List<HashMap<String, String>> reslists;
    private RichSelectOneChoice btnDeliberer;
    private RichSelectOneChoice inputDeliberer;
    private RichOutputFormatted inputFilUe;
    private RichPopup popupShowDetUeClose;
    private RichPopup popupShowDetNotModEnsSaisieClosedAll;
    private RichPopup popupShowDetUeDelibSuccess;
    private RichPopup popupShowDetUeNotDelib;
    private RichPopup popupShowDetUeCloseSuccess;
    private RichPopup popupSuccessCloseAllEc;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer calendrier = Integer.parseInt(sessionScope.get("id_calendrier").toString());
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Integer historique_str = Integer.parseInt(sessionScope.get("id_hs").toString());
    private Long struct = Long.parseLong(sessionScope.get("id_str").toString());
    private Long prcrs_maq_an = Long.parseLong(sessionScope.get("prcrs_maq_an").toString());
    /*Integer.parseInt(sessionScope.get("id_annee").toString(),0)*/
    private static final String ALGO = "AES";
    private byte[] keyvalue;
    private RichInputNumberSpinbox noteInf;
    private RichInputNumberSpinbox noteSup;
    private RichTable resTable;
    private EtudiantsEcs etdtEc = new EtudiantsEcs();
    ArrayList<Long> listInsPedUe = new ArrayList<Long>();
    ArrayList<Long> listAllradyCalculateEc = new ArrayList<Long>();
    ArrayList<Long> listInsPedUeAllradyCalculate = new ArrayList<Long>();
    private Float moyenneUe = new Float(0);
    private RichTable detailsTable;
    private RichPanelCollection detailsPanelColection;
    private RichOutputFormatted moyUe;
    private RichInputText inputMoyenneUe;
    private RichPopup popupSaveRepeche;
    private RichPopup popupEnterInterval;
    //private RichPopup popupConfirmcloseAllEc;
    private RichPopup popupsess1notclosed;
    private RichPanelGroupLayout btnpan;
    private RichPanelGroupLayout btnpanue;
    private RichPopup jurynotyet;
    private RichPopup onlypdtcandelib;
    private RichPopup popupConfirmCloseUe;
    private RichPopup popupUeClosed;
    private RichPopup popupOtherError;
    private RichPopup popupAllEcClosed;
    private RichPanelGroupLayout panelbtn;
    private RichPopup popupConfirmcloseAllEc;
    private RichPopup popupNoGroup;
    private RichPopup popupConfirmOpen;
    private RichPopup popupUeOpened;
    private RichPopup popupSemClosed;
    private RichPanelGroupLayout resultatPan;
    private RichPanelCollection resultatPanCol;
    private RichPopup popIntervalNote;
    private RichPopup popSuccessRep;
    private RichPopup popConfirmRepeche;
    private RichPopup popDetailsRepech;
    private RichPanelCollection resultatPanColMed;
    private RichPopup popConfirmRepGrpMed;
    private RichPopup popConfirmRepIndivMed;
    private RichPanelStretchLayout panStrech;
    private RichPanelGroupLayout panGrpDelib;
    private RichPanelCollection panColectDelib;
    private RichTable tabDelib;
    private RichPopup popUePublish;
    private RichPopup popUeNotPublish;
    private RichPanelGroupLayout panGrpSaisieInter;

    @SuppressWarnings("unchecked")
    public DeliberatioUeBean() {

    }

    public String decryptAnonymat(String encdata, String keys) throws Exception {
        /*keys = keys.substring(0, 16);
        keyvalue = keys.getBytes();
        Key key = new SecretKeySpec(keyvalue, ALGO);
        //Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encdata);
        byte[] decVal = c.doFinal(decodedValue);
        String decryptValue = new String(decVal);
        return decryptValue;*/
        return "";
    }

    public void setEtud_note_ens(List<NoteModeEns> etud_note_ens) {
        this.etud_note_ens = etud_note_ens;
    }

    public List<NoteModeEns> getEtud_note_ens() {
        return etud_note_ens;
    }

    public void setEtudiantlists(List<Etudiant> etudiantlists) {
        this.etudiantlists = etudiantlists;
    }

    public List<Etudiant> getEtudiantlists() {
        return etudiantlists;
    }

    public void setEtudiantbislists(List<EtudiantBis> etudiantbislists) {
        this.etudiantbislists = etudiantbislists;
    }

    public List<EtudiantBis> getEtudiantbislists() {
        return etudiantbislists;
    }

    public void setEclists(List<Ec> eclists) {
        this.eclists = eclists;
    }

    public List<Ec> getEclists() {
        return eclists;
    }

    public void setMclists(List<ModeCntrlEc> mclists) {
        this.mclists = mclists;
    }

    public List<ModeCntrlEc> getMclists() {
        return mclists;
    }

    @SuppressWarnings("unchecked")
    public String getAuto(String id_etud, String nivForm) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";

        try {
            OperationBinding auto = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("getAutorisation");
            auto.getParamsMap().put("id_etud", Long.parseLong(id_etud));
            auto.getParamsMap().put("id_niv_form", Long.parseLong(nivForm));
            auto.execute();
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AutoInscPreinsROIterator");
            Row currentRow = iter.getCurrentRow();
            if (currentRow == null) {
                return autorise;
            } else {
                autorise = "Oui";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return autorise;
    }

    @SuppressWarnings("unchecked")
    public String getValidationAuto(String id_etud, String nivForm) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";

        OperationBinding op_validation = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("getAutorisation");
        op_validation.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_validation.getParamsMap().put("id_niv_form", Long.parseLong(nivForm));
        op_validation.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AutoInscPreinsROIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return autorise;
        } else {
            if (Integer.parseInt(currentRow.getAttribute("Valide").toString()) >= 1) {
                autorise = "Oui";
                return autorise;
            } else {
                return autorise;
            }

        }
    }

    @SuppressWarnings("unchecked")
    public String getValidationAutoResp(String id_etud, String nivForm) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";

        OperationBinding op_validation_resp = BindingContext.getCurrent()
                                                            .getCurrentBindingsEntry()
                                                            .getOperationBinding("getAutorisation");
        op_validation_resp.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_validation_resp.getParamsMap().put("id_niv_form", Long.parseLong(nivForm));
        op_validation_resp.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AutoInscPreinsROIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return autorise;
        } else {
            if (Integer.parseInt(currentRow.getAttribute("Valide").toString()) >= 2) {
                autorise = "Oui";
                return autorise;
            } else {
                return autorise;
            }

        }
    }

    @SuppressWarnings("unchecked")
    public String getValidationAutoDap(String id_etud, String nivForm) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";

        OperationBinding op_validation_dap = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("getFormAutoDap");
        op_validation_dap.getParamsMap().put("id_util", getUtilisateur());
        op_validation_dap.getParamsMap().put("id_niv_form", Long.parseLong(nivForm));
        op_validation_dap.getParamsMap().put("id_annee", getAnne_univers());
        op_validation_dap.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NivFormAutoriseDapROIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return "----";
        } else {
            OperationBinding op_validation_dap_ok = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("getAutorisation");
            op_validation_dap_ok.getParamsMap().put("id_etud", Long.parseLong(id_etud));
            op_validation_dap_ok.getParamsMap().put("id_niv_form", Long.parseLong(nivForm));
            op_validation_dap_ok.execute();
            DCIteratorBinding iter_dap_ok = (DCIteratorBinding) getBindings().get("AutoInscPreinsROIterator");
            Row currentrow_dap_ok = iter_dap_ok.getCurrentRow();
            if (currentrow_dap_ok == null) {
                return autorise;
            } else {
                if (Integer.parseInt(currentrow_dap_ok.getAttribute("Valide").toString()) >= 3) {
                    autorise = "Oui";
                    return autorise;
                } else {
                    return autorise;
                }

            }

        }
    }

    @SuppressWarnings("unchecked")
    public String getEtudiantBu(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";
        //id_historique_strc
        OperationBinding op_historique_strc = BindingContext.getCurrent()
                                                            .getCurrentBindingsEntry()
                                                            .getOperationBinding("getHistStrct");
        op_historique_strc.getParamsMap().put("id_historique_strc", getHistorique_str());
        op_historique_strc.execute();
        DCIteratorBinding iter_histo = (DCIteratorBinding) getBindings().get("HistoriquesStructuresIterator");
        Row row_histo = iter_histo.getCurrentRow();
        if (row_histo != null) {
            OperationBinding op_etud_bu = BindingContext.getCurrent()
                                                        .getCurrentBindingsEntry()
                                                        .getOperationBinding("getEtudiantBu");
            op_etud_bu.getParamsMap()
                .put("id_struct", Long.parseLong(row_histo.getAttribute("IdStructures").toString()));
            op_etud_bu.getParamsMap().put("id_etud", Long.parseLong(id_etud));
            op_etud_bu.getParamsMap().put("id_annee", Long.parseLong(id_annee));
            op_etud_bu.execute();
            DCIteratorBinding iter_etud_bu = (DCIteratorBinding) getBindings().get("EtudiantBuIterator");
            Row currentRow_etud_bu = iter_etud_bu.getCurrentRow();
            if (currentRow_etud_bu == null) {
                return autorise;
            } else {
                if (currentRow_etud_bu.getAttribute("EnRegle") == null) {
                    return autorise;
                } else {
                    if (Integer.parseInt(currentRow_etud_bu.getAttribute("EnRegle").toString()) == 1) {
                        autorise = "Oui";
                        return autorise;
                    } else {
                        return autorise;
                    }
                }
            }
        } else {
            return autorise;
        }


    }

    @SuppressWarnings("unchecked")
    public String getApteEtudiant(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm)
        String autorise = "Non";

        OperationBinding opNote = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("getAptitudeEtudiant");
        opNote.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        opNote.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AptePreinsIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return autorise;
        } else {
            if (currentRow.getAttribute("Apte") == null) {
                return autorise;
            } else {
                if (Integer.parseInt(currentRow.getAttribute("Apte").toString()) == 1) {
                    autorise = "Oui";
                    return autorise;
                } else
                    return autorise;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getResponsableEtud(String id_etud) {

        String autorise = "Non";
        OperationBinding op_resp_etud = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("getResponsableEtud");
        op_resp_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_resp_etud.execute();
        DCIteratorBinding iter_resp_etud = (DCIteratorBinding) getBindings().get("ResponsableROIterator");
        Row currentRow_resp_etud = iter_resp_etud.getCurrentRow();
        if (currentRow_resp_etud == null) {
            return autorise;
        } else {
            autorise = "Oui";
            return autorise;
        }
    }

    @SuppressWarnings("unchecked")
    public String getInscPedVal(String id_insc_ped) {
        String autorise = "Non";
        OperationBinding op_resp_etud = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("getIncPedetud");
        op_resp_etud.getParamsMap().put("id_ins_ped", Long.parseLong(id_insc_ped));
        op_resp_etud.execute();
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
        Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
        if (currentRow_insc_ped == null) {
            return autorise;
        } else {
            if (currentRow_insc_ped.getAttribute("InsEnLigne") == null ||
                currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId") == null) {
                return autorise;
            } else {
                // inscription validée si InsEnLigne (oui : 1) et EtatInscription (definitif : 22)
                if (Integer.parseInt(currentRow_insc_ped.getAttribute("InsEnLigne").toString()) == 1 &&
                    Long.parseLong(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId").toString()) == 22) {
                    autorise = "Oui";
                    return autorise;
                } else {
                    return autorise;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getInscEnLigne(String id_insc_ped) {
        String autorise = "Non";
        OperationBinding op_resp_etud = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("getIncPedetud");
        op_resp_etud.getParamsMap().put("id_ins_ped", Long.parseLong(id_insc_ped));
        op_resp_etud.execute();
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
        Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
        if (currentRow_insc_ped == null) {
            return autorise;
        } else {
            if (currentRow_insc_ped.getAttribute("InsEnLigne") == null) {
                return autorise;
            } else {
                // inscription validée si InsEnLigne (oui : 1)
                if (Integer.parseInt(currentRow_insc_ped.getAttribute("InsEnLigne").toString()) == 1) {
                    autorise = "Oui";
                    return autorise;
                } else {
                    return autorise;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getEtudTic(String id_etud, String id_annee) {
        String etud_tic_var = "Non";
        OperationBinding etud_tic = getBindings().getOperationBinding("getEtudiantTic");
        etud_tic.getParamsMap().put("id_etud", id_etud);
        etud_tic.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        etud_tic.execute();

        DCIteratorBinding iter_etud_tic = (DCIteratorBinding) getBindings().get("EtudiantTicROIterator");

        Row currentRow_etud_tic = iter_etud_tic.getCurrentRow();

        if (currentRow_etud_tic != null) {
            etud_tic_var = "Oui";
            return etud_tic_var;
        } else {
            return etud_tic_var;
        }
    }

    @SuppressWarnings("unchecked")
    public String getPaiementEtudiant(String id_etud, String id_annee, String parcours, String inscPed) {
        String autorise = "Non";

        OperationBinding op_paie_etud = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("getPaiementEtud");
        op_paie_etud.getParamsMap().put("id_insc_ped", Long.parseLong(inscPed));
        op_paie_etud.getParamsMap().put("parcours", Long.parseLong(parcours));
        op_paie_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_paie_etud.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        op_paie_etud.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("PaiementEtudPreinsIterator");

        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return autorise;
        } else {
            if (currentRow.getAttribute("Valider") == null) {
                return autorise;
            } else {
                if (Integer.parseInt(currentRow.getAttribute("Valider").toString()) == 1) {
                    autorise = "Oui";
                    return autorise;
                } else
                    return autorise;
            }
        }
    }


    @SuppressWarnings("unchecked")
    public String getAnonymatDecrypte(String id_etud, String parcours, String semestre, String anne, String sessions) {
        String decrypted = "";
        //a.id_niveau_formation_parcours = parcours and a.id_semestre = semestre and  a.id_session = sessions and a.id_annees_univers = anne
        OperationBinding op_get_anonymat = BindingContext.getCurrent()
                                                         .getCurrentBindingsEntry()
                                                         .getOperationBinding("getAnonymatDecrypte");
        op_get_anonymat.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_get_anonymat.getParamsMap().put("parcours", Long.parseLong(parcours));
        op_get_anonymat.getParamsMap().put("semestre", Long.parseLong(semestre));
        op_get_anonymat.getParamsMap().put("anne", Long.parseLong(anne));
        op_get_anonymat.getParamsMap().put("sessions", Long.parseLong(sessions));
        //op_validation_resp.getParamsMap().put("parcours", Long.parseLong(parcours));
        op_get_anonymat.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("GenAnonymatNewROIterator");
        Row currentRow = iter.getCurrentRow();

        String key =
            getParcours() + "" + getSession() + "" + getSemestre() + "" + getAnne_univers() + "" + getAnne_univers() +
            "" + getSemestre() + "" + getSession() + "" + getParcours() + "" + getSemestre() + "" + getSession() + "" +
            getParcours() + "" + getAnne_univers() + "" + getSession() + "" + getParcours() + "" + getAnne_univers() +
            "" + getSemestre();
        String val_key = key.substring(0, 16);

        if (currentRow == null) {
            return decrypted;
        } else {

            try {
                decrypted = decryptAnonymat((String) currentRow.getAttribute("Anonymat").toString(), val_key);
            } catch (Exception e) {
            }
            return decrypted;

        }
    }
 
    public static Object resolvElDC(String data) {
        /*FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);*/
        return null;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.nested-assignment" })
    public void getEtudNoteModeEns(String id_fil_ec, String id_type_ctrl, String cal) {
        try {
            BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
            AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdFiliereUeSemstre");
            Integer fil_ue = (Integer.parseInt(attrIdBinding.getInputValue().toString()));

            /*System.out.println("fil_ue : " + fil_ue + " Fil_Ec : " + id_fil_ec + " calendier : " + cal +
                               " type cntrl : " + id_type_ctrl);*/
            //String decrypted="";
            OperationBinding op_get_stdt = getBindings().getOperationBinding("saisieNoteEtudiant");
            op_get_stdt.getParamsMap().put("t_cntrl_id", Long.parseLong(id_type_ctrl));
            op_get_stdt.getParamsMap().put("cal", Long.parseLong(cal));
            op_get_stdt.getParamsMap().put("fil_ec_id", Long.parseLong(id_fil_ec));
            op_get_stdt.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            op_get_stdt.execute();
            /*
            //Long fil_sem_ec,Long type_control,Long calendrier
            OperationBinding isClosedSaisieNotesInter = getBindings().getOperationBinding("isClosedSaisieNotes");
            isClosedSaisieNotesInter.getParamsMap().put("fil_sem_ec", Long.parseLong(id_fil_ec));
            isClosedSaisieNotesInter.getParamsMap().put("type_control", Long.parseLong(id_type_ctrl));
            isClosedSaisieNotesInter.getParamsMap().put("calendrier",  Long.parseLong(cal));
            Integer result = (Integer) isClosedSaisieNotesInter.execute();
            sessionScope.put("isClosed", result);*/
            /*evaluationAppImpl am = (evaluationAppImpl) resolvElDC("evaluationAppDataControl");
            ViewObject NoteModeEnsVo = am.getNotesModeEnseignement1();
            ViewCriteriaManager vcm = NoteModeEnsVo.getViewCriteriaManager();
            ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementCriteria");
            VariableValueManager vvm = vc.ensureVariableManager();
            vvm.setVariableValue("id_type_ctrl_var", Long.parseLong(id_type_ctrl));
            vvm.setVariableValue("id_cal_delib_var", Long.parseLong(cal));
            vvm.setVariableValue("id_fil_ec", Long.parseLong(id_fil_ec));
            NoteModeEnsVo.applyViewCriteria(vc);
            NoteModeEnsVo.executeQuery();*/
            //NoteModeEnsVo.setSortBy("Anonymat"); //empVo.
            //Long fil_sem_ec,Long type_control,Long calendrier
            OperationBinding isClosedSaisieNotes = getBindings().getOperationBinding("isClosedSaisieNotes");
            isClosedSaisieNotes.getParamsMap().put("fil_sem_ec", Long.parseLong(id_fil_ec));
            isClosedSaisieNotes.getParamsMap().put("type_control", Long.parseLong(id_type_ctrl));
            isClosedSaisieNotes.getParamsMap().put("calendrier", Long.parseLong(cal));
            isClosedSaisieNotes.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            Integer result = (Integer) isClosedSaisieNotes.execute();
            sessionScope.put("isClosed", result);
            /* System.out.println("isClosedSaisieNotes " + result);
            System.out.println("fil_sem_ec " + Long.parseLong(id_fil_ec) + " type_control " +
                               Long.parseLong(id_type_ctrl) + " calendrier " + Long.parseLong(cal));
*/
            OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
            isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong(getParcours().toString()));
            isGenereAnonymat.getParamsMap().put("anne", Long.parseLong(getAnne_univers().toString()));
            isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong(getSemestre().toString()));
            isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong(getSession().toString()));
            Integer is_anomymat = (Integer) isGenereAnonymat.execute();
            //pas d'anonymat
            if (is_anomymat == 0) {
                sessionScope.put("isAnonymat", 0);
            } else {
                sessionScope.put("isAnonymat", 1);
            }
            //IdFiliereUeSemstre
            OperationBinding isinterexist = getBindings().getOperationBinding("isNoteModeEnsInterExist");
            isinterexist.getParamsMap().put("fil_ec_id", Integer.parseInt(id_fil_ec));
            isinterexist.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            isinterexist.execute();
            DCIteratorBinding iterExist = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("isNoteModeEnsInterExist1Iterator");
            if (iterExist.getEstimatedRowCount() > 0) {
                sessionScope.put("isInterExist", 1);
            } else {
                sessionScope.put("isInterExist", 0);
            }
            try {
                OperationBinding isAccessAllowed = getBindings().getOperationBinding("isEcAccessAllowed");
                isAccessAllowed.getParamsMap().put("filiere_ec_id", Long.parseLong(id_fil_ec));
                isAccessAllowed.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                isAccessAllowed.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                Integer isAllowed = (Integer) isAccessAllowed.execute();
                sessionScope.put("isAccessAllowed", isAllowed);
                //System.out.println("isAccessAllowed " + sessionScope.get("isAccessAllowed"));
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In isEcAccessAllowed catch");
            }
            /*OperationBinding isResUe = getBindings().getOperationBinding("IsRespUe");
            isResUe.getParamsMap().put("fil_ue", fil_ue);
            isResUe.getParamsMap().put("user_id", getUtilisateur());
            isResUe.execute();
            DCIteratorBinding iterIsResp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .get("IsResponsableFilUeIterator");
            if (iterIsResp.getEstimatedRowCount() == 0) {
                sessionScope.put("isResponsableUe", 0);
            } else {
                sessionScope.put("isResponsableUe", 1);
            }*/

            /*OperationBinding isResEc = getBindings().getOperationBinding("IsRespEC");
            isResEc.getParamsMap().put("fil_ec", Integer.parseInt(id_fil_ec));
            isResEc.getParamsMap().put("user_id", getUtilisateur());
            isResEc.execute();
            DCIteratorBinding iterIsRespEc = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("IsResponsableFilEcIterator");
            if (iterIsRespEc.getEstimatedRowCount() == 0) {
                sessionScope.put("isResponsableEc", 0);
            } else {
                sessionScope.put("isResponsableEc", 1);
            }*/
        } catch (Exception e) {
            /*evaluationAppImpl am = (evaluationAppImpl) resolvElDC("evaluationAppDataControl");
            ViewObject NoteModeEnsVo = am.getNotesModeEnseignement();
            ViewCriteriaManager vcm = NoteModeEnsVo.getViewCriteriaManager();
            ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementVOCriteria");
            VariableValueManager vvm = vc.ensureVariableManager();
            vvm.setVariableValue("id_type_ctrl_var", new Long(0));
            vvm.setVariableValue("id_cal_delib_var", Long.parseLong(cal));
            vvm.setVariableValue("id_fil_ec", new Long(0));
            NoteModeEnsVo.applyViewCriteria(vc);
            NoteModeEnsVo.executeQuery();
            NoteModeEnsVo.setSortBy("Anonymat");*/
            OperationBinding op_get_stdt = getBindings().getOperationBinding("saisieNoteEtudiant");
            op_get_stdt.getParamsMap().put("t_cntrl_id", new Long(0));
            op_get_stdt.getParamsMap().put("cal", Long.parseLong(cal));
            op_get_stdt.getParamsMap().put("fil_ec_id", new Long(0));
            op_get_stdt.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            op_get_stdt.execute();
        }
    }

    @SuppressWarnings("unchecked")
    public void getEtudiantNoteModeInter(String type_ctrl, String mode_ctrl, String cal) {
        //System.out.println("In getEtudiantNoteModeInter");
        //System.out.println("type_ctrl : " + type_ctrl + " mode_ctrl : " + mode_ctrl + " cal : " + cal);
        try {
            BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
            //Long fil_ue=0;
            Long fil_ec = new Long(0);
            //AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdFiliereUeSemstre");
            AttributeBinding attrFec = (AttributeBinding) container.getControlBinding("IdFiliereUeSemstreEc");
            try {
                //if(null != attrIdBinding && null != fil_ue_ec && null != type_ctrl && null != mode_ctrl){
                //fil_ue = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
                fil_ec = (Long.parseLong(attrFec.getInputValue().toString()));
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In getAttribute");
            }
            /*evaluationAppImpl am = (evaluationAppImpl) resolvElDC("evaluationAppDataControl");
            ViewObject NoteModeEnsInterVo = am.getNotesModeEnseignementInter();
            ViewCriteriaManager vcm = NoteModeEnsInterVo.getViewCriteriaManager();
            ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementInterVOCriteria");
            VariableValueManager vvm = vc.ensureVariableManager();
            vvm.setVariableValue("id_type_ctrl_var", Long.parseLong(type_ctrl));
            vvm.setVariableValue("id_mode_ctrl_ec_var", Long.parseLong(mode_ctrl));
            vvm.setVariableValue("id_cal_delib_var", Long.parseLong(cal));
            NoteModeEnsInterVo.applyViewCriteria(vc);
            NoteModeEnsInterVo.executeQuery();
            //NoteModeEnsInterVo.setSortBy("Anonymat");*/
            try {
                /*System.out.println("cal "+Long.parseLong(cal));
                System.out.println("type_ctrl "+ Long.parseLong(type_ctrl));
                System.out.println("mode_ctrl "+Long.parseLong(mode_ctrl));*/
                OperationBinding etuinter = getBindings().getOperationBinding("getEtuSaisieNoteInter");

                etuinter.getParamsMap().put("cal_id", Long.parseLong(cal));
                etuinter.getParamsMap().put("tcntrl_id", Long.parseLong(type_ctrl));
                etuinter.getParamsMap().put("mde_cntrl_id", Long.parseLong(mode_ctrl));
                etuinter.execute();
                sessionScope.put("refc", 1);
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In getEtudiant catch");
                OperationBinding etuinter = getBindings().getOperationBinding("getEtuSaisieNoteInter");
                etuinter.getParamsMap().put("cal_id", new Long(0));
                etuinter.getParamsMap().put("tcntrl_id", new Long(0));
                etuinter.getParamsMap().put("mde_cntrl_id", new Long(0));
                etuinter.execute();
                sessionScope.put("refc", 1);
            }
            try {
                OperationBinding etubygroup = getBindings().getOperationBinding("GetGroupeSaisieNote");
                etubygroup.execute();
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In GetGroupeSaisieNote catch");
            }
            //Verification si la saisie est deja cloturer ou pas
            //--@garmi
            /*

        DCIteratorBinding itergrp = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("GroupeSaisieNoteROVOIterator");
        if(itergrp.getEstimatedRowCount() == 0){
            RichPopup popup = this.getPopupNoGroup();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }
        else{
        OperationBinding etugroup = getBindings().getOperationBinding("GetStudentNote");
        etugroup.getParamsMap().put("id_cal_delib_var", Long.parseLong(cal));
        etugroup.getParamsMap().put("id_mode_ctrl_ec_var", Long.parseLong(mode_ctrl));
        etugroup.getParamsMap().put("id_type_ctrl_var", Long.parseLong(type_ctrl));
        etugroup.getParamsMap().put("id_gs_var", Long.parseLong(itergrp.getCurrentRow().getAttribute("IdGroupeSaisie").toString()));
        //etugroup.getParamsMap().put("an_id", arg1);
        etugroup.execute();
        //--@Garmi
*/
            try {
                OperationBinding isClosedSaisieNotesInter =
                    getBindings().getOperationBinding("isClosedSaisieNotesInter");
                //Long fil_sem_ec,Long type_control,Long mode_control,Long calendrier
                /*System.out.println("fil_ue " + fil_ue + " fil_sem_ec " + fil_ec + " type_control " +
                               Long.parseLong(type_ctrl) + " mode_control " + Long.parseLong(mode_ctrl) +
                               " calendrier " + Long.parseLong(cal));*/
                isClosedSaisieNotesInter.getParamsMap().put("fil_sem_ec", fil_ec);
                isClosedSaisieNotesInter.getParamsMap().put("type_control", Long.parseLong(type_ctrl));
                isClosedSaisieNotesInter.getParamsMap().put("mode_control", Long.parseLong(mode_ctrl));
                isClosedSaisieNotesInter.getParamsMap().put("calendrier", Long.parseLong(cal));
                Integer result = (Integer) isClosedSaisieNotesInter.execute();
                //System.out.println("isClosedSaisieNotesInter " + result);
                sessionScope.put("isClosed_inter", result);
                //sessionScope.put("isClosed_inter", 0);
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In isClosedSaisieNotesInter catch");
                OperationBinding isClosedSaisieNotesInter =
                    getBindings().getOperationBinding("isClosedSaisieNotesInter");
                isClosedSaisieNotesInter.getParamsMap().put("fil_sem_ec", 0);
                isClosedSaisieNotesInter.getParamsMap().put("type_control", new Long(0));
                isClosedSaisieNotesInter.getParamsMap().put("mode_control", new Long(0));
                isClosedSaisieNotesInter.getParamsMap().put("calendrier", new Long(0));
                Integer result = (Integer) isClosedSaisieNotesInter.execute();
            }
            try {
                OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
                isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong(getParcours() + ""));
                isGenereAnonymat.getParamsMap().put("anne", Long.parseLong(getAnne_univers() + ""));
                isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong(getSemestre() + ""));
                isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong(getSession() + ""));
                Integer is_anomymat = (Integer) isGenereAnonymat.execute();
                //pas d'anonymat
                if (is_anomymat == 0) {
                    sessionScope.put("isAnonymat", 0);
                } else {
                    sessionScope.put("isAnonymat", 1);
                }
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In isGenereAnonymat catch");
            }
            try {
                OperationBinding isAccessAllowed = getBindings().getOperationBinding("isEcAccessAllowed");
                isAccessAllowed.getParamsMap().put("filiere_ec_id", fil_ec);
                isAccessAllowed.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                isAccessAllowed.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                Integer isAllowed = (Integer) isAccessAllowed.execute();
                sessionScope.put("isAccessAllowed", isAllowed);
                //System.out.println("isAccessAllowed "+sessionScope.get("isAccessAllowed"));
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("In isEcAccessAllowed catch");
            }
            /*try{
            OperationBinding isResUe = getBindings().getOperationBinding("IsRespUe");
            isResUe.getParamsMap().put("fil_ue", fil_ue);
            isResUe.getParamsMap().put("user_id", getUtilisateur());
            isResUe.execute();
            DCIteratorBinding iterIsResp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .get("IsResponsableFilUeIterator");
            if (iterIsResp.getEstimatedRowCount() == 0) {
                sessionScope.put("isResponsableUe", 0);
            } else {
                sessionScope.put("isResponsableUe", 1);
            }
        }catch (Exception e) {
                System.out.println(e);
                System.out.println("In IsRespUe catch");
            }
            //}

            //Etudiant semestre
            //IsRespEC
            try{
            OperationBinding isResEc = getBindings().getOperationBinding("IsRespEC");
            isResEc.getParamsMap().put("fil_ec", fil_ec);
            isResEc.getParamsMap().put("user_id", getUtilisateur());
            isResEc.execute();
            DCIteratorBinding iterIsRespEc = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("IsResponsableFilEcIterator");
            if (iterIsRespEc.getEstimatedRowCount() == 0) {
                sessionScope.put("isResponsableEc", 0);
            } else {
                sessionScope.put("isResponsableEc", 1);
            }
            }catch (Exception e) {
                    System.out.println(e);
                    System.out.println("In IsRespEC catch");
                }*/
        } catch (Exception e) {
            System.out.println(e);
            //System.out.println("In catch");
            /*evaluationAppImpl am = (evaluationAppImpl) resolvElDC("evaluationAppDataControl");
            ViewObject NoteModeEnsInterVo = am.getNotesModeEnseignementInter();
            ViewCriteriaManager vcm = NoteModeEnsInterVo.getViewCriteriaManager();
            ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementInterVOCriteria");
            VariableValueManager vvm = vc.ensureVariableManager();
            vvm.setVariableValue("id_type_ctrl_var", new Long(0));
            vvm.setVariableValue("id_mode_ctrl_ec_var", new Long(0));
            vvm.setVariableValue("id_cal_delib_var", Long.parseLong(cal));
            NoteModeEnsInterVo.applyViewCriteria(vc);
            NoteModeEnsInterVo.executeQuery();*/
            OperationBinding etuinter = getBindings().getOperationBinding("getEtuSaisieNoteInter");
            etuinter.getParamsMap().put("cal_id", new Long(0));
            etuinter.getParamsMap().put("tcntrl_id", new Long(0));
            etuinter.getParamsMap().put("mde_cntrl_id", new Long(0));
            etuinter.execute();
        }
    }
/*
    @SuppressWarnings("unchecked")
    public List<Etudiant> getStudents() {
        etudiantlists = new ArrayList();
        OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetEtudiant");
        lesEtudiants.getParamsMap().put("annee", getAnne_univers());
        lesEtudiants.getParamsMap().put("sem", getSemestre());
        lesEtudiants.getParamsMap().put("parcours", getPrcrs_maq_an());
        lesEtudiants.getParamsMap().put("sess", getSession());
        lesEtudiants.execute();
        //System.out.println("Parcours : "+getParcours());
        //System.out.println("Annee : "+getAnne_univers());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemEtudiantIterator");
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
    */

    @SuppressWarnings("unchecked")
    public List<EtudiantDelibSem> getStudents() {
        etudiantSemlists = new ArrayList();
        try{
            OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .getOperationBinding("GetEtudiant");
            lesEtudiants.getParamsMap().put("annee", getAnne_univers());
            lesEtudiants.getParamsMap().put("sem", getSemestre());
            lesEtudiants.getParamsMap().put("parcours", getPrcrs_maq_an());
            lesEtudiants.getParamsMap().put("sess", getSession());
            lesEtudiants.execute();
        }catch(Exception ex){
        }
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemEtudiantIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return etudiantSemlists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            etudiantSemlists.add(new EtudiantDelibSem(nextRow.getAttribute("Numero").toString(), nextRow.getAttribute("Prenoms").toString(), 
                                                      nextRow.getAttribute("Nom").toString(), nextRow.getAttribute("DateNaissance").toString(), 
                                                      nextRow.getAttribute("LieuNaissance").toString()));
           
        }
        rsi.closeRowSetIterator();
        return etudiantSemlists;
    }
    //getListeetudProv
    @SuppressWarnings("unchecked")
    public void isclosed(AttributeBinding fil_ue) {
        BindingContainer bindings = getBindings();
        Boolean ispdtjury = false;
        Integer isclosedue = -1;
        Integer isdelibue = -1;
        //System.out.println(fil_ue);
        try {
            OperationBinding getParcours = bindings.getOperationBinding("getParcoursInfo");
            getParcours.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            OperationBinding opjury = bindings.getOperationBinding("getUtiPdtJury");
            opjury.execute();

            DCIteratorBinding iterpdt = (DCIteratorBinding) bindings.get("UtilisateurPDTJuryIterator");
            Row current = iterpdt.getCurrentRow();
            if (null != current) {
                ispdtjury = true;
            }
        } catch (Exception e) {
            ispdtjury = false;
            System.out.println(e);
        }
        if (null != fil_ue) {
            //System.out.println("Fil_Ue : " + fil_ue.getInputValue().toString());
            try {
                OperationBinding is_closed_all_note_mod_ens = bindings.getOperationBinding("isCosedAllNoteModeEns");
                is_closed_all_note_mod_ens.getParamsMap()
                    .put("fil_ue", Integer.parseInt(fil_ue.getInputValue().toString()));
                is_closed_all_note_mod_ens.getParamsMap().put("calendrier", getCalendrier());
                is_closed_all_note_mod_ens.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                Integer is_all_note_mod_ens_closed = (Integer) is_closed_all_note_mod_ens.execute();
                sessionScope.put("isclosedAllEc", is_all_note_mod_ens_closed);
                if (is_all_note_mod_ens_closed == 0) {
                    OperationBinding opEcNotClosed = bindings.getOperationBinding("getEcNotClosed");
                    opEcNotClosed.getParamsMap().put("p_fil_ue", Long.parseLong(fil_ue.getInputValue().toString()));
                    opEcNotClosed.getParamsMap().put("p_calendrier", new Long(getCalendrier()));
                    opEcNotClosed.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
                    opEcNotClosed.execute();
                }
            } catch (Exception e) {
                sessionScope.put("isclosedAllEc", 0);
            }

            try {
                OperationBinding is_closed_ue = bindings.getOperationBinding("isCosedUe");
                is_closed_ue.getParamsMap().put("fil_ue", fil_ue.getInputValue());
                is_closed_ue.getParamsMap().put("calendrier", getCalendrier());
                is_closed_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                Integer is_ue_closed = (Integer) is_closed_ue.execute();
                isclosedue = is_ue_closed;
            } catch (Exception e) {
                OperationBinding is_closed_ue = bindings.getOperationBinding("isCosedUe");
                is_closed_ue.getParamsMap().put("fil_ue", 0);
                is_closed_ue.getParamsMap().put("calendrier", getCalendrier());
                is_closed_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                Integer is_ue_closed = (Integer) is_closed_ue.execute();
                isclosedue = is_ue_closed;
                //sessionScope.put("isclosedue", isclosedue);
            }
            try {
                OperationBinding isdelibratedUe = bindings.getOperationBinding("isDelibrateUe");
                isdelibratedUe.getParamsMap().put("fileUesem", Long.parseLong(fil_ue.getInputValue().toString()));
                isdelibratedUe.getParamsMap().put("calendrier", new Long(getCalendrier()));
                isdelibratedUe.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                Integer isdelibeUe = (Integer) isdelibratedUe.execute();
                isdelibue = isdelibeUe; //0 if not and x if it
            } catch (Exception e) {
                OperationBinding isdelibratedUe = bindings.getOperationBinding("isDelibrateUe");
                isdelibratedUe.getParamsMap().put("fileUesem", new Long(0));
                isdelibratedUe.getParamsMap().put("calendrier", new Long(getCalendrier()));
                isdelibratedUe.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                Integer isdelibeUe = (Integer) isdelibratedUe.execute();
                isdelibue = isdelibeUe;
                //sessionScope.put("isdelibue", isdelibue);
            }
            sessionScope.put("ispdtjury", ispdtjury);
            sessionScope.put("isclosedue", isclosedue);
            sessionScope.put("isdelibue", isdelibue);
        }
        else {
            sessionScope.put("isclosedAllEc", 0);
            sessionScope.put("ispdtjury", false);
            sessionScope.put("isclosedue", 0);
            sessionScope.put("isdelibue", 0);
        }
    }
    //Etudiant Ue
    /*@SuppressWarnings("unchecked")
    public List<Etudiant> getUeStudents(Integer fil_ue) {
        Boolean isresp = false;
        BindingContainer bindings = getBindings();
        etudiantlists = new ArrayList();
        OperationBinding lesEtudiants = bindings.getOperationBinding("GetEtudiantUe");
        lesEtudiants.getParamsMap().put("annee", getAnne_univers());
        lesEtudiants.getParamsMap().put("sem", getSemestre());
        lesEtudiants.getParamsMap().put("parcours", getPrcrs_maq_an());
        lesEtudiants.getParamsMap().put("sess", getSession());
        lesEtudiants.getParamsMap().put("id_fil_ue", fil_ue);
        lesEtudiants.execute();
        //System.out.println("Parcours : "+getParcours());
        //System.out.println("Annee : "+Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString()));
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("DelibSemUeEtudiantIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return etudiantlists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            /*etudiantlists.add(new Etudiant(nextRow.getAttribute("Numero").toString(),
                                           nextRow.getAttribute("PrenomNom").toString()));*/
            /*etudiantlists.add(new Etudiant(nextRow.getAttribute("Numero").toString(),
                                           nextRow.getAttribute("PrenomNom").toString(),
                                           Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString())));

        }
        rsi.closeRowSetIterator();
        //System.out.println("Filiére ue : "+fil_ue);
        return etudiantlists;
    }*/
    
    @SuppressWarnings("unchecked")
    public List<EtudiantDelibUE> getUeStudents(Integer fil_ue) {
        Boolean isresp = false;
        BindingContainer bindings = getBindings();
        etudiantUelists = new ArrayList();
        OperationBinding lesEtudiants = bindings.getOperationBinding("GetEtudiantUe");
        lesEtudiants.getParamsMap().put("annee", getAnne_univers());
        lesEtudiants.getParamsMap().put("sem", getSemestre());
        lesEtudiants.getParamsMap().put("parcours", getPrcrs_maq_an());
        lesEtudiants.getParamsMap().put("sess", getSession());
        lesEtudiants.getParamsMap().put("id_fil_ue", fil_ue);
        lesEtudiants.execute();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("DelibSemUeEtudiantIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            return etudiantUelists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            etudiantUelists.add(new EtudiantDelibUE(nextRow.getAttribute("Numero").toString(), nextRow.getAttribute("Prenoms").toString(), 
                                                    nextRow.getAttribute("Nom").toString(), nextRow.getAttribute("DateNaissance").toString(), 
                                                    nextRow.getAttribute("LieuNaissance").toString(), Long.parseLong(nextRow.getAttribute("IdInscriptionPedSemUe").toString())));

        }
        rsi.closeRowSetIterator();
        return etudiantUelists;
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
        etdtEc = (EtudiantsEcs)lesEtudiants.execute();
        System.out.println("Size : "+etdtEc.getEtudiants().size());
        return etdtEc;
    }


    @SuppressWarnings("unchecked")
    // liste des ecs pour un fil_ue_sem
    public List<Ec> getEcParcousAnSemSess(Integer fil_ue) {
        //Map sessionScope = ADFContext.getCurrent().getSessionScope();
        //System.out.println("fil_ue : " + fil_ue);
        eclists = new ArrayList();
        OperationBinding lesFilUe = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetEcsFilUeSem");
        lesFilUe.getParamsMap().put("anne", getAnne_univers());
        lesFilUe.getParamsMap().put("sem", getSemestre());
        lesFilUe.getParamsMap().put("parcours", getParcours());
        lesFilUe.getParamsMap().put("sess", getSession());
        lesFilUe.getParamsMap().put("fil_ue", fil_ue);
        lesFilUe.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibSemFilEcIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            eclists.add(new Ec(Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()),
                               nextRow.getAttribute("LibelleLong").toString(),
                               Double.parseDouble(nextRow.getAttribute("Coefficient").toString())));
        }
        rsi.closeRowSetIterator();
        return eclists;
    }

    // la liste des Ecs déja disponible dans deliberation semestrielle
    // GetModeCntrl : recuperer la liste des modes control pour un fil-ec donnée
    /*@SuppressWarnings("unchecked")
    public List<ModeCntrlEc> getModeCntrlEc(Integer fil_ec) {
        //Map sessionScope = ADFContext.getCurrent().getSessionScope();
        mclists = new ArrayList();
        OperationBinding lesModCntrl = BindingContext.getCurrent()
                                                     .getCurrentBindingsEntry()
                                                     .getOperationBinding("GetModeCntrl");
        //lesModCntrl.getParamsMap().put("annee", getAnne_univers());
        //lesModCntrl.getParamsMap().put("sem", getSemestre());
        lesModCntrl.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
        //lesModCntrl.getParamsMap().put("sess", getSession());
        lesModCntrl.getParamsMap().put("fil_ec", fil_ec);
        lesModCntrl.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibUeModeCntrlIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            //mclists.add(new ModeCntrlEc(Integer.parseInt(""),"",Integer.parseInt("")));
            return mclists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            mclists.add(new ModeCntrlEc(Integer.parseInt(nextRow.getAttribute("IdModeControleEc").toString()),
                                        nextRow.getAttribute("Modecntrl").toString()));

        }
        rsi.closeRowSetIterator();
        return mclists;
    }*/

    @SuppressWarnings("unchecked")
    public List<FilEcTypeCntrle> getTypeCntrle(Long fil_ec) {
        //Map sessionScope = ADFContext.getCurrent().getSessionScope();
        tclists = new ArrayList();
        OperationBinding lesModCntrl = BindingContext.getCurrent()
                                                     .getCurrentBindingsEntry()
                                                     .getOperationBinding("getFilEcTypeCntrle");
        //lesModCntrl.getParamsMap().put("annee", getAnne_univers());
        //lesModCntrl.getParamsMap().put("sem", getSemestre());
        lesModCntrl.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
        //lesModCntrl.getParamsMap().put("sess", getSession());
        lesModCntrl.getParamsMap().put("fil_ec", fil_ec);
        lesModCntrl.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibUeFilEcTypeCntrlIterator");
        Row currentinsped = iter.getCurrentRow();
        if (currentinsped == null) {
            //mclists.add(new ModeCntrlEc(Integer.parseInt(""),"",Integer.parseInt("")));
            return tclists;
        }
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            tclists.add(new FilEcTypeCntrle(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()),
                                            Long.parseLong(nextRow.getAttribute("IdTypeControle").toString()),
                                            nextRow.getAttribute("Typecntrl").toString()));

        }
        rsi.closeRowSetIterator();
        return tclists;
    }
    /*
    @SuppressWarnings("unchecked")
    // recuperer la note de l'etudiant pour un ec donne
    public String getNoteMcntrl(Long insped, Integer mcec) {
        //Map sessionScope = ADFContext.getCurrent().getSessionScope();
        OperationBinding opNote = getBindings().getOperationBinding("GetNoteModeCntrl");
        opNote.getParamsMap().put("calendrier", new Long(getCalendrier()));
        opNote.getParamsMap().put("parcours", new Long(getParcours()));
        opNote.getParamsMap().put("inspedsem_ue", insped);
        opNote.getParamsMap().put("mcec", new Long(mcec));
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibUeNoteModeCntrlIterator");
        Row currentnote = iter.getCurrentRow();
        try {
            return currentnote.getAttribute("Note").toString();
        } catch (Exception e) {
            return "-";
        }*/
    @SuppressWarnings("unchecked")
    // recuperer la note de l'etudiant pour un ec donne
    public String getNoteTypecntrl(Long insped, Long fil_ec, Long type_cntrle) {
        //Map sessionScope = ADFContext.getCurrent().getSessionScope();
        OperationBinding opNote = getBindings().getOperationBinding("GetNoteTypeCntrle");
       
        opNote.getParamsMap().put("p_calendrier", new Long(getCalendrier()));
        opNote.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
        opNote.getParamsMap().put("p_inspedsem_ue", insped);
        opNote.getParamsMap().put("p_fec", fil_ec);
        opNote.getParamsMap().put("p_tc", type_cntrle);
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibUeNoteTypeCntrleIterator");
        Row currentnote = iter.getCurrentRow();
        try {
            return currentnote.getAttribute("Note").toString();
        } catch (Exception e) {
            return "ABS";
        } 
    }
    
    @SuppressWarnings("unchecked")
    // recuperer la moyenne, le resultat et le credit de l'etudiant pour un insped_sem_ue donnee
    public List<HashMap<String, String>> getResultEc(Integer inspedSemUe, Integer fil_ec) {
        reslists = new ArrayList();
        list = new HashMap<String, String>();

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetMoyCredResEc");
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getParcours());
        inspedsem_.getParamsMap().put("sess", getSession());
        inspedsem_.getParamsMap().put("inspedsemue", inspedSemUe);
        //inspedsem_.getParamsMap().put("inspedsemue", fil_ue);
        inspedsem_.getParamsMap().put("fil_ec", fil_ec);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibResEcIterator");
        Row currentinsped = iter.getCurrentRow();
        /* if (currentinsped == null) {
            list.put("Moyenne", "-");
            list.put("Coeficient", "");
            reslists.add(list);
            //return reslists;
        } */
        try {
            list.put("Moyenne", currentinsped.getAttribute("Note").toString());
            list.put("Coeficient", currentinsped.getAttribute("Coefficient").toString());
            reslists.add(list);
        } catch (Exception ex) {
            list.put("Moyenne", "-");
            list.put("Coeficient", "-");
            reslists.add(list);
        }
        return reslists;
    }

    @SuppressWarnings("unchecked")
    // recuperer la moyenne, le resultat et le credit de l'etudiant pour un insped_sem_ue donnee
    public List<HashMap<String, String>> getResultatEc(Integer inspedSemUe, Integer fil_ec) {
        reslists = new ArrayList();
        list = new HashMap<String, String>();

        OperationBinding inspedsem_ = BindingContext.getCurrent()
                                                    .getCurrentBindingsEntry()
                                                    .getOperationBinding("GetMoyCredResEc");
        inspedsem_.getParamsMap().put("annee", getAnne_univers());
        inspedsem_.getParamsMap().put("sem", getSemestre());
        inspedsem_.getParamsMap().put("parcours", getPrcrs_maq_an());
        inspedsem_.getParamsMap().put("sess", getSession());
        inspedsem_.getParamsMap().put("inspedsemue", inspedSemUe);
        //inspedsem_.getParamsMap().put("inspedsemue", fil_ue);
        inspedsem_.getParamsMap().put("fil_ec", fil_ec);
        inspedsem_.execute();

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("DelibResEcIterator");
        Row currentinsped = iter.getCurrentRow();
        /* if (currentinsped == null) {
            list.put("Moyenne", "-");
            list.put("Coeficient", "");
            reslists.add(list);
            //return reslists;
        } */
        try {
            list.put("Moyenne", currentinsped.getAttribute("Note").toString());
            //list.put("Coeficient", currentinsped.getAttribute("Coefficient").toString());
            reslists.add(list);
        } catch (Exception ex) {
            list.put("Moyenne", "-");
            //list.put("Coeficient", "-");
            reslists.add(list);
        }
        return reslists;
    }

    public void setBtnUe(RichSelectOneChoice btnUe) {
        this.btnUe = btnUe;
    }

    public RichSelectOneChoice getBtnUe() {
        return btnUe;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    @SuppressWarnings("unchecked")
    public void Deliberer(ActionEvent actionEvent) {
        String action = "O";
        String fil_ue = this.getInputFilUe().getValue().toString();
        BindingContainer bindings = getBindings();
        try {
            OperationBinding deliberer_ue = bindings.getOperationBinding("DelibererUe");
            deliberer_ue.getParamsMap().put("calendrier", new Long(getCalendrier()));
            deliberer_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            deliberer_ue.getParamsMap().put("fil_ue", this.getInputFilUe().getValue());
            deliberer_ue.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            deliberer_ue.execute();

            OperationBinding delib_ue = bindings.getOperationBinding("delibereUe");
            delib_ue.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                       .getValue()
                                                                       .toString()));
            delib_ue.getParamsMap().put("calendrier", getCalendrier());
            delib_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            delib_ue.getParamsMap().put("action", action);
            delib_ue.getParamsMap().put("utilisateur", getUtilisateur());
            Integer is_delib_ue = (Integer) delib_ue.execute();
            if (is_delib_ue == 217) {
                //System.out.println("Ue délibéré avec succe");
                try {
                    OperationBinding opgetRepechable = getBindings().getOperationBinding("getEtudiantEc");
                    opgetRepechable.getParamsMap().put("filUeId", Long.parseLong(this.getInputFilUe()
                                                                                     .getValue()
                                                                                     .toString()));
                    opgetRepechable.getParamsMap().put("prcrsIaqId", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("semId", new Long(getSemestre()));
                    opgetRepechable.getParamsMap().put("sessId", new Long(getSession()));
                    opgetRepechable.getParamsMap().put("anId", new Long(getAnne_univers()));
                    etdtEc = (EtudiantsEcs) opgetRepechable.execute();
                    sessionScope.put("listEtudiansEcs", etdtEc);
                    //.listEtudiansEcs
                } catch (Exception e) {
                    System.out.println(e);
                }
                RichPopup popup = this.getPopupShowDetUeDelibSuccess();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDelib());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColectDelib());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabDelib());

            } else {
                System.out.println("Une erreur détecté !!!");
            }
        } catch (Exception e) {

            System.out.println(e);
        }
        /*
        // chek if is_all_not_mod_ens_saisie_close
        OperationBinding is_closed_all_note_mod_ens = bindings.getOperationBinding("isCosedAllNoteModeEns");
        is_closed_all_note_mod_ens.getParamsMap().put("fil_ue", Integer.parseInt(fil_ue));
        is_closed_all_note_mod_ens.getParamsMap().put("calendrier", getCalendrier());
        is_closed_all_note_mod_ens.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
        Integer is_all_note_mod_ens_closed = (Integer) is_closed_all_note_mod_ens.execute();
        if (is_all_note_mod_ens_closed == 0) {
            // saisie non cloturé : show popup detail
            //System.out.println("Toutes les saisie note mode enseignement pas encore cloturé");
            OperationBinding opEcNotClosed = bindings.getOperationBinding("getEcNotClosed");
            opEcNotClosed.getParamsMap().put("p_fil_ue", Long.parseLong(fil_ue));
            opEcNotClosed.getParamsMap().put("p_calendrier", new Long(getCalendrier()));
            opEcNotClosed.getParamsMap().put("p_prcrs_maq", getPrcrs_maq_an());
            opEcNotClosed.execute();
            RichPopup popup = this.getPopupShowDetNotModEnsSaisieClosedAll();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            // chek if ue_is_not_close yet
            OperationBinding is_closed_ue = bindings.getOperationBinding("isCosedUe");
            is_closed_ue.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                           .getValue()
                                                                           .toString()));
            is_closed_ue.getParamsMap().put("calendrier", getCalendrier());
                is_closed_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            //is_closed_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
            Integer is_ue_closed = (Integer) is_closed_ue.execute();
            if (is_ue_closed == 1) {
                // ue closed delib not possible
                //System.out.println("Ue cloturé impossible de délibérer");
                RichPopup popup = this.getPopupShowDetUeClose();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                //check the session
                if (this.getSession() != 1) {
                    //isClose session 1
                    OperationBinding is_sess1_closed = bindings.getOperationBinding("IsSess1Closed");
                    is_sess1_closed.getParamsMap().put("annee", getAnne_univers());
                    is_sess1_closed.getParamsMap().put("semestre", getSemestre());
                    //is_sess1_closed.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    is_sess1_closed.getParamsMap().put("prcrs_maq_an", getPrcrs_maq_an());

                    is_sess1_closed.execute();
                    DCIteratorBinding iterAll = (DCIteratorBinding) getBindings().get("isSess1Closed1Iterator");
                    if (iterAll.getEstimatedRowCount() > 0) {
                        // all is find : Calculer la moyenne de l'ue et délibérer
                        OperationBinding deliberer_ue = bindings.getOperationBinding("DelibererUe");
                        deliberer_ue.getParamsMap().put("calendrier", new Long(getCalendrier()));
                        deliberer_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                        deliberer_ue.getParamsMap().put("fil_ue", this.getInputFilUe().getValue());
                        deliberer_ue.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        deliberer_ue.execute();

                        OperationBinding delib_ue = bindings.getOperationBinding("delibereUe");
                        delib_ue.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                                   .getValue()
                                                                                   .toString()));
                        delib_ue.getParamsMap().put("calendrier", getCalendrier());
                        delib_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                        delib_ue.getParamsMap().put("action", action);
                        delib_ue.getParamsMap().put("utilisateur", getUtilisateur());
                        Integer is_delib_ue = (Integer) delib_ue.execute();
                        if (is_delib_ue == 217) {
                            //System.out.println("Ue délibéré avec succe");
                            try {
                                    OperationBinding opgetRepechable =
                                        getBindings().getOperationBinding("getEtudiantEc");
                                    opgetRepechable.getParamsMap()
                                        .put("filUeId",Long.parseLong(this.getInputFilUe().getValue().toString()));
                                    opgetRepechable.getParamsMap().put("prcrsIaqId", new Long(getPrcrs_maq_an()));
                                    opgetRepechable.getParamsMap().put("semId", new Long(getSemestre()));
                                    opgetRepechable.getParamsMap().put("sessId", new Long(getSession()));
                                    opgetRepechable.getParamsMap().put("anId", new Long(getAnne_univers()));
                                    etdtEc = (EtudiantsEcs) opgetRepechable.execute();
                                    sessionScope.put("listEtudiansEcs", etdtEc);
                                    //.listEtudiansEcs
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            RichPopup popup = this.getPopupShowDetUeDelibSuccess();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanStrech());
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDelib());
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColectDelib());
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabDelib());
                            
                        } else {
                            System.out.println("Une erreur détecté !!!");
                        }
                    } else {
                        RichPopup popup = this.getPopupsess1notclosed();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                } else {
                    // all is find : Calculer la moyenne de l'ue et délibérer
                    OperationBinding deliberer_ue = bindings.getOperationBinding("DelibererUe");
                    deliberer_ue.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    deliberer_ue.getParamsMap().put("parcours_maq", getPrcrs_maq_an());
                    deliberer_ue.getParamsMap().put("fil_ue", this.getInputFilUe().getValue());
                    deliberer_ue.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    deliberer_ue.execute();
                    //System.out.println("calendrier : " + getCalendrier());
                    //System.out.println("fil_ue : " + this.getInputFilUe().getValue());
                    OperationBinding delib_ue = bindings.getOperationBinding("delibereUe");
                    delib_ue.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                               .getValue()
                                                                               .toString()));
                    delib_ue.getParamsMap().put("calendrier", getCalendrier());
                        delib_ue.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                    delib_ue.getParamsMap().put("action", action);
                    delib_ue.getParamsMap().put("utilisateur", getUtilisateur());
                    Integer is_delib_ue = (Integer) delib_ue.execute();
                    if (is_delib_ue == 217) {
                        //System.out.println("Ue délibéré avec succe");
                        RichPopup popup = this.getPopupShowDetUeDelibSuccess();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                    } else {
                        System.out.println("Une erreur détecté !!!");
                    }
                }
            }
        }*/
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
    }

    @SuppressWarnings("unchecked")
    public void Cloturer(ActionEvent actionEvent) {
        String action = "O";
        // Ue délibérer : cloture possible
        BindingContainer bindings = getBindings();
        // ClosedUe(fil_ue, calendrier, action ,utilisateur)
        OperationBinding is_closed = bindings.getOperationBinding("ClosedUe");
        is_closed.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                    .getValue()
                                                                    .toString()));
        is_closed.getParamsMap().put("calendrier", getCalendrier());
        is_closed.getParamsMap().put("action", action);
        is_closed.getParamsMap().put("utilisateur", getUtilisateur());
        is_closed.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
        Integer closed = (Integer) is_closed.execute();
        if (closed == 0) {
            RichPopup popup = this.getPopupShowDetUeNotDelib();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            //System.out.println("Ue Cloturé");
            RichPopup popup = this.getPopupShowDetUeCloseSuccess();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }
    }

    public void setBtnDeliberer(RichSelectOneChoice btnDeliberer) {
        this.btnDeliberer = btnDeliberer;
    }

    public RichSelectOneChoice getBtnDeliberer() {
        return btnDeliberer;
    }

    public void setInputDeliberer(RichSelectOneChoice inputDeliberer) {
        this.inputDeliberer = inputDeliberer;
    }

    public RichSelectOneChoice getInputDeliberer() {
        return inputDeliberer;
    }

    public void setInputFilUe(RichOutputFormatted inputFilUe) {
        this.inputFilUe = inputFilUe;
    }

    public RichOutputFormatted getInputFilUe() {
        return inputFilUe;
    }

    public void setPopupShowDetUeClose(RichPopup popupShowDetUeClose) {
        this.popupShowDetUeClose = popupShowDetUeClose;
    }

    public RichPopup getPopupShowDetUeClose() {
        return popupShowDetUeClose;
    }

    public void setPopupShowDetNotModEnsSaisieClosedAll(RichPopup popupShowDetNotModEnsSaisieClosedAll) {
        this.popupShowDetNotModEnsSaisieClosedAll = popupShowDetNotModEnsSaisieClosedAll;
    }

    public RichPopup getPopupShowDetNotModEnsSaisieClosedAll() {
        return popupShowDetNotModEnsSaisieClosedAll;
    }

    public void setPopupShowDetUeDelibSuccess(RichPopup popupShowDetUeDelibSuccess) {
        this.popupShowDetUeDelibSuccess = popupShowDetUeDelibSuccess;
    }

    public RichPopup getPopupShowDetUeDelibSuccess() {
        return popupShowDetUeDelibSuccess;
    }

    public void setPopupShowDetUeNotDelib(RichPopup popupShowDetUeNotDelib) {
        this.popupShowDetUeNotDelib = popupShowDetUeNotDelib;
    }

    public RichPopup getPopupShowDetUeNotDelib() {
        return popupShowDetUeNotDelib;
    }

    public void setPopupShowDetUeCloseSuccess(RichPopup popupShowDetUeCloseSuccess) {
        this.popupShowDetUeCloseSuccess = popupShowDetUeCloseSuccess;
    }

    public RichPopup getPopupShowDetUeCloseSuccess() {
        return popupShowDetUeCloseSuccess;
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

    public void setHistorique_str(Integer historique_str) {
        this.historique_str = historique_str;
    }

    @SuppressWarnings("unchecked")
    public void OnCloseAllEcClicked(DialogEvent dialogEvent) {
        String action = "O";
        // Ue délibérer : cloture possible
        Outcome outcome = dialogEvent.getOutcome();
        //System.out.println("Outcome : " + outcome);
        /*System.out.println("Filiere Ue : " + this.getInputFilUe()
                                                 .getValue()
                                                 .toString());*/
        Boolean ok = false;
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("DelibSemFilUeIterator");
        Row currentRowUe = iter.getCurrentRow();
        this.getPopupConfirmcloseAllEc().hide();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            OperationBinding allEc = getBindings().getOperationBinding("GetAllEcFilUe");
            //Recuperer l'ensemble des fil-ec du fil_ue
            allEc.getParamsMap().put("id_fil_ue", currentRowUe.getAttribute("IdFiliereUeSemstre").toString());
            allEc.execute();
            DCIteratorBinding iterAll = (DCIteratorBinding) getBindings().get("AllEcFilUeIterator");
            if (iterAll.getEstimatedRowCount() > 0) {
                RowSetIterator rsi = iterAll.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    OperationBinding clotureSaisieNotes = getBindings().getOperationBinding("clotureSaisieNotes");
                    clotureSaisieNotes.getParamsMap()
                        .put("fil_sem_ec", Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                    clotureSaisieNotes.getParamsMap()
                        .put("type_control", Long.parseLong(nextRow.getAttribute("IdTypeControle").toString()));
                    clotureSaisieNotes.getParamsMap().put("calendrier", getCalendrier());
                    clotureSaisieNotes.getParamsMap().put("action", action);
                    clotureSaisieNotes.getParamsMap().put("utilisateur", getUtilisateur());
                    Integer result = (Integer) clotureSaisieNotes.execute();
                    if (result == 1) {
                        OperationBinding calcul_moy_ec_final =
                            getBindings().getOperationBinding("CalculMoyenneEcFinalProc");
                        calcul_moy_ec_final.getParamsMap().put("anne", getAnne_univers());
                        calcul_moy_ec_final.getParamsMap().put("semestre", getSemestre());
                        calcul_moy_ec_final.getParamsMap().put("parcours", getParcours());
                        calcul_moy_ec_final.getParamsMap()
                            .put("fileUesem", Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
                        calcul_moy_ec_final.getParamsMap()
                            .put("idFilieUeSemEc",
                                 Integer.parseInt(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                        calcul_moy_ec_final.getParamsMap().put("calendrDelib", getCalendrier());
                        calcul_moy_ec_final.getParamsMap().put("utilisateur", getUtilisateur());
                        calcul_moy_ec_final.execute();
                        ok = true;
                    } else {
                        ok = false;
                    }
                }
                //System.out.println(ok);
                rsi.closeRowSetIterator();
            }
            if (ok) {
                RichPopup popup = this.getPopupSuccessCloseAllEc();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else {
                RichPopup popup = this.getPopupOtherError();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
            /* OperationBinding is_closed = bindings.getOperationBinding("clotureEcUe");
            is_closed.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                        .getValue()
                                                                        .toString()));
            is_closed.getParamsMap().put("calendrier", getCalendrier());
            is_closed.getParamsMap().put("action", action);
            is_closed.getParamsMap().put("utilisateur", getUtilisateur());
            Integer closed = (Integer) is_closed.execute();
            if (closed == 1) {
                //Success
                RichPopup popup = this.getPopupSuccessCloseAllEc();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (closed == 2) {
                //Ue Closed
                RichPopup popup = this.getPopupUeClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else if (closed == -1) {
                //Toutes les ecs sont cloturés
                RichPopup popup = this.getPopupAllEcClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } else {
                //Other error Verifier la cloture des inter
                RichPopup popup = this.getPopupOtherError();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            } */
        }
    }
    /* }
    }
*/
    public void setPopupSuccessCloseAllEc(RichPopup popupSuccessCloseAllEc) {
        this.popupSuccessCloseAllEc = popupSuccessCloseAllEc;
    }

    public RichPopup getPopupSuccessCloseAllEc() {
        return popupSuccessCloseAllEc;
    }

    public void setPopupUeClosed(RichPopup popupUeClosed) {
        this.popupUeClosed = popupUeClosed;
    }

    public RichPopup getPopupUeClosed() {
        return popupUeClosed;
    }

    public void setPopupOtherError(RichPopup popupOtherError) {
        this.popupOtherError = popupOtherError;
    }

    public RichPopup getPopupOtherError() {
        return popupOtherError;
    }

    public void setPopupAllEcClosed(RichPopup popupAllEcClosed) {
        this.popupAllEcClosed = popupAllEcClosed;
    }

    public RichPopup getPopupAllEcClosed() {
        return popupAllEcClosed;
    }

    @SuppressWarnings("unchecked")
    public void OnRepechableSearchClicked(ActionEvent actionEvent) {
        //System.out.println("Repechage sur Ue");
        if ((this.getNoteSup().getValue() == null) || (this.getNoteInf().getValue() == null)) {
            //System.out.println("Entrer un Interval de Note");
            RichPopup popup = this.getPopupEnterInterval();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            BindingContainer bindings = this.getBindings();
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
            /*
             *         if (this.getNoteSup().getValue() != null)
            moyenneSup = (Number)(this.getNoteSup().getValue());
        if (this.getNoteInf().getValue() != null)
            moyenneInf = (Number)(this.getNoteInf().getValue());
        if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
            Number x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }
             * */
            OperationBinding opUe = bindings.getOperationBinding("RepecheUECriteria");
            opUe.getParamsMap().put("calendrier", getCalendrier());
            opUe.getParamsMap().put("superieur", moyenneSup);
            opUe.getParamsMap().put("inferieur", moyenneInf);
            opUe.execute();

            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ResultatsFilUeSemestreVOIterator");
            //System.out.println("Size Etu Repechable : "+iter.getEstimatedRowCount());
            Row currentRow = iter.getCurrentRow();
            if (currentRow == null) {
                setMoyenneUe(new Float(0));
            } else {
                setMoyenneUe(Float.parseFloat(currentRow.getAttribute("Note").toString()));
            }
            this.getResTable().setVisible(true);
            this.getDetailsTable().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
        }
    }

    public void setNoteInf(RichInputNumberSpinbox noteInf) {
        this.noteInf = noteInf;
    }

    public RichInputNumberSpinbox getNoteInf() {
        return noteInf;
    }

    public void setNoteSup(RichInputNumberSpinbox noteSup) {
        this.noteSup = noteSup;
    }

    public RichInputNumberSpinbox getNoteSup() {
        return noteSup;
    }

    public void setResTable(RichTable resTable) {
        this.resTable = resTable;
    }

    public RichTable getResTable() {
        return resTable;
    }

    public void setMoyenneUe(Float moyenneUe) {
        this.moyenneUe = moyenneUe;
    }

    public Float getMoyenneUe() {
        return moyenneUe;
    }

    @SuppressWarnings("unchecked")
    public void OnUeSelected(SelectionEvent selectionEvent) {
        // Add event code here...
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        oracle.jbo.Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterR = (DCIteratorBinding) bindings.get("ResultatsFilUeSemestreVOIterator");
        Row currentR = dciterR.getCurrentRow();
        /*System.out.println("Anonymat : " + currentR.getAttribute("Anonymat").toString() + " " + " Note : " +
                           currentR.getAttribute("Note").toString());*/
        setMoyenneUe(Float.parseFloat(currentR.getAttribute("Note").toString()));
        this.detailsTable.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsPanelColection());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
    }

    public void setDetailsTable(RichTable detailsTable) {
        this.detailsTable = detailsTable;
    }

    public RichTable getDetailsTable() {
        return detailsTable;
    }

    public void setDetailsPanelColection(RichPanelCollection detailsPanelColection) {
        this.detailsPanelColection = detailsPanelColection;
    }

    public RichPanelCollection getDetailsPanelColection() {
        return detailsPanelColection;
    }

    public void setMoyUe(RichOutputFormatted moyUe) {
        this.moyUe = moyUe;
    }

    public RichOutputFormatted getMoyUe() {
        return moyUe;
    }

    public void setInputMoyenneUe(RichInputText inputMoyenneUe) {
        this.inputMoyenneUe = inputMoyenneUe;
    }

    public RichInputText getInputMoyenneUe() {
        return inputMoyenneUe;
    }

    public void OnPointJurySet(AttributeChangeEvent attributeChangeEvent) {
        System.out.println("Attribute changed");
    }

    @SuppressWarnings("unchecked")
    public void OnPointJurySet(ValueChangeEvent valueChangeEvent) {
        //System.out.println("value changed");
        ArrayList<Long> listfilEc = new ArrayList<Long>();
        ArrayList<FilEcRepecheUe> detailsfilEc = new ArrayList<FilEcRepecheUe>();
        ArrayList<FiliereEc> filUE = new ArrayList<FiliereEc>();
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementVOIterator");
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
        //System.out.println("detailsfilEc size : " + detailsfilEc.size());

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
            //System.out.println("moyenneEc Formated : " + df.format(moyenneEc));
            //System.out.println("moyenneEc Not Formated : " + moyenneEc);
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
    public void OnRepecheDialogueAction(DialogEvent dialogEvent) {
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
                DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementVOIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    Float note;
                    //verifier si la note est nulle
                    //System.out.println("Insped sem all : " + nextRow.getAttribute("IdInscriptionPedSemUe").toString());
                    if (nextRow.getAttribute("PointJury") == null) {
                        note = new Float(0);
                    } else {
                        note = Float.parseFloat(nextRow.getAttribute("PointJury").toString());
                    }
                    OperationBinding findUpdate = getBindings().getOperationBinding("findAndUpdateNotePointJury");
                    findUpdate.getParamsMap()
                        .put("idNoteModeEns",
                             Long.parseLong(nextRow.getAttribute("IdNoteModeEnseignement").toString()));
                    findUpdate.getParamsMap().put("point", note);
                    findUpdate.execute();
                    executeOperation("Commit").execute();
                    //}
                }
                rsi.closeRowSetIterator();

                DCIteratorBinding iterUe = (DCIteratorBinding) getBindings().get("ResultatsFilUeSemestreVOIterator");
                RowSetIterator rsiUe = iterUe.getViewObject().createRowSetIterator(null);
                while (rsiUe.hasNext()) {
                    Row nextUe = rsiUe.next();
                    rsi = iter.getViewObject().createRowSetIterator(null);
                    //Recalculer la moyenne des Ecs
                    while (rsi.hasNext()) {
                        Row nextRow = rsi.next();
                        if (nextUe.getAttribute("IdInscriptionPedSemUe")
                                  .toString()
                                  .equalsIgnoreCase(nextRow.getAttribute("IdInscriptionPedSemUe").toString())) {
                            /*System.out.println("Calcul Moyenne Ec : FilEc " +
                                               nextRow.getAttribute("IdFiliereUeSemstreEc").toString());*/
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

                                /*System.out.println("Calcul Moyenne Ue : InspedUe " +
                                                   nextRow.getAttribute("IdInscriptionPedSemUe").toString());*/
                                //Integer idInspedSemUe, Integer idCalendrDelib, Integer utilisateur
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
                    rsi.closeRowSetIterator();
                }
                rsiUe.closeRowSetIterator();
                OperationBinding opUe = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("RepecheUECriteria");
                opUe.getParamsMap().put("calendrier", getCalendrier());
                opUe.getParamsMap().put("superieur", moyenneSup);
                opUe.getParamsMap().put("inferieur", moyenneInf);
                opUe.execute();

                DCIteratorBinding iterCriteria =
                    (DCIteratorBinding) getBindings().get("ResultatsFilUeSemestreVOIterator");
                Row currentRow = iterCriteria.getCurrentRow();
                if (currentRow == null) {
                    setMoyenneUe(new Float(0));
                } else {
                    setMoyenneUe(Float.parseFloat(currentRow.getAttribute("Note").toString()));
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());

                FacesMessage msg = new FacesMessage(" Les points de jury sont enregistrés avec succès  ! ");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void OnValideRepecheClicked(ActionEvent actionEvent) {
        RichPopup popup = this.getPopupSaveRepeche();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setListInsPedUe(ArrayList<Long> listInsPedUe) {
        this.listInsPedUe = listInsPedUe;
    }

    public ArrayList<Long> getListInsPedUe() {
        return listInsPedUe;
    }

    public void setPopupEnterInterval(RichPopup popupEnterInterval) {
        this.popupEnterInterval = popupEnterInterval;
    }

    public RichPopup getPopupEnterInterval() {
        return popupEnterInterval;
    }
    //}

    /* public RichPopup getPopupConfirmcloseAllEc() {
        return popupConfirmcloseAllEc;
    }*/

    public void setPopupsess1notclosed(RichPopup popupsess1notclosed) {
        this.popupsess1notclosed = popupsess1notclosed;
    }

    public RichPopup getPopupsess1notclosed() {
        return popupsess1notclosed;
    }

    public void setBtnpan(RichPanelGroupLayout btnpan) {
        this.btnpan = btnpan;
    }

    public RichPanelGroupLayout getBtnpan() {
        return btnpan;
    }

    public void setBtnpanue(RichPanelGroupLayout btnpanue) {
        this.btnpanue = btnpanue;
    }

    public RichPanelGroupLayout getBtnpanue() {
        return btnpanue;
    }

    public void setJurynotyet(RichPopup jurynotyet) {
        this.jurynotyet = jurynotyet;
    }

    public RichPopup getJurynotyet() {
        return jurynotyet;
    }

    public void setOnlypdtcandelib(RichPopup onlypdtcandelib) {
        this.onlypdtcandelib = onlypdtcandelib;
    }

    public RichPopup getOnlypdtcandelib() {
        return onlypdtcandelib;
    }

    @SuppressWarnings("unchecked")
    public void OnCloseUeClicked(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            String action = "O";
            // Ue délibérer : cloture possible
            BindingContainer bindings = getBindings();
            // ClosedUe(fil_ue, calendrier, action ,utilisateur)
            OperationBinding is_closed = bindings.getOperationBinding("ClosedUe");
            is_closed.getParamsMap().put("fil_ue", Integer.parseInt(this.getInputFilUe()
                                                                        .getValue()
                                                                        .toString()));
            is_closed.getParamsMap().put("calendrier", getCalendrier());
            is_closed.getParamsMap().put("action", action);
            is_closed.getParamsMap().put("utilisateur", getUtilisateur());
            is_closed.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
            Integer closed = (Integer) is_closed.execute();
            this.getPopupConfirmCloseUe().hide();
            if (closed == 0) {
                // saisie non cloturé : show popup detail
                //System.out.println("Ue non délibérer");
                RichPopup popup = this.getPopupShowDetUeNotDelib();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
                return;
            } else {
                //System.out.println("Ue Cloturé");
                RichPopup popup = this.getPopupShowDetUeCloseSuccess();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                return;
            }
        }
    }

    public void setPopupConfirmCloseUe(RichPopup popupConfirmCloseUe) {
        this.popupConfirmCloseUe = popupConfirmCloseUe;
    }

    public RichPopup getPopupConfirmCloseUe() {
        return popupConfirmCloseUe;
    }

    public void OnDialogConfirmCloseUeCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopupConfirmCloseUe().hide();
    }

    public void setPanelbtn(RichPanelGroupLayout panelbtn) {
        this.panelbtn = panelbtn;
    }

    public RichPanelGroupLayout getPanelbtn() {
        return panelbtn;
    }

    public Integer getHistorique_str() {
        return historique_str;
    }

    public void setPopupConfirmcloseAllEc(RichPopup popupConfirmcloseAllEc) {
        this.popupConfirmcloseAllEc = popupConfirmcloseAllEc;
    }

    public RichPopup getPopupConfirmcloseAllEc() {
        return popupConfirmcloseAllEc;
    }

    public void setPopupNoGroup(RichPopup popupNoGroup) {
        this.popupNoGroup = popupNoGroup;
    }

    public RichPopup getPopupNoGroup() {
        return popupNoGroup;
    }

    @SuppressWarnings("unchecked")
    public void getMaquetteStruct() {
        BindingContainer bindings = getBindings();
        OperationBinding opmaq = bindings.getOperationBinding("getMaquetteStruct");
        opmaq.getParamsMap().put("strId", getStruct());
        opmaq.execute();
    }

    public void setStruct(Long struct) {
        this.struct = struct;
    }

    public Long getStruct() {
        return struct;
    }

    public void onUeSelected(org.apache.myfaces.trinidad.event.SelectionEvent selectionEvent) {
        // Add event code here...
        //System.out.println("ResFilUeSelected");
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        oracle.jbo.Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterR = (DCIteratorBinding) bindings.get("ResultatsFilUeSemestreVOIterator");
        Row currentR = dciterR.getCurrentRow();
        /*System.out.println("Anonymat : " + currentR.getAttribute("Anonymat").toString() + " " + " Note : " +
                           currentR.getAttribute("Note").toString());*/
        setMoyenneUe(Float.parseFloat(currentR.getAttribute("Note").toString()));
        this.detailsTable.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsPanelColection());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetailsTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMoyenneUe());
    }

    public void ExportToExcel(FacesContext facesContext, OutputStream outputStream) {
        //System.out.println("Export to excel");
        facesContext.renderResponse();
    }

    public void setPopupConfirmOpen(RichPopup popupConfirmOpen) {
        this.popupConfirmOpen = popupConfirmOpen;
    }

    public RichPopup getPopupConfirmOpen() {
        return popupConfirmOpen;
    }

    @SuppressWarnings("unchecked")
    public void OnOpenUeAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            String fil_ue = this.getInputFilUe()
                                .getValue()
                                .toString();
            try {
                OperationBinding opopen = getBindings().getOperationBinding("reouvrirFilUe");
                opopen.getParamsMap().put("fil_ue", Long.parseLong(fil_ue));
                opopen.getParamsMap().put("parcours", getPrcrs_maq_an());
                opopen.getParamsMap().put("calendrier", new Long(getCalendrier()));
                opopen.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                Integer result = (Integer) opopen.execute();
                if (1 == result) {
                    RichPopup popup = this.getPopupUeOpened();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
                } else if (-1 == result) {
                    RichPopup popup = this.getPopupSemClosed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                } else {
                    System.out.println("Other");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setPopupUeOpened(RichPopup popupUeOpened) {
        this.popupUeOpened = popupUeOpened;
    }

    public RichPopup getPopupUeOpened() {
        return popupUeOpened;
    }

    public void setPopupSemClosed(RichPopup popupSemClosed) {
        this.popupSemClosed = popupSemClosed;
    }

    public RichPopup getPopupSemClosed() {
        return popupSemClosed;
    }

    @SuppressWarnings("unchecked")
    public void OnSearchRepechableAction(ActionEvent actionEvent) {
        BindingContainer bindings = this.getBindings();
        Number moyenneSup = 0.0;
        Number moyenneInf = 0.0;
        if (this.getNoteSup().getValue() != null)
            moyenneSup = (Number)(this.getNoteSup().getValue());
        if (this.getNoteInf().getValue() != null)
            moyenneInf = (Number)(this.getNoteInf().getValue());
        if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
            Number x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }
        if(this.getNoteSup().getValue() != null && this.getNoteInf().getValue() != null){
        //System.out.println("Sup " + moyenneSup + " Inf : " + moyenneInf);
        sessionScope.put("moy_ref", moyenneSup);
        sessionScope.put("moy_inf", moyenneInf);
        DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("RepecheFilUeIterator");
        Row currentFilUeRow = iterFilUe.getCurrentRow();
        if (null != currentFilUeRow) {
            try {
                OperationBinding opgetRegleRepeche = getBindings().getOperationBinding("getRegleRepech");
                opgetRegleRepeche.getParamsMap().put("str_id", getStruct());
                opgetRegleRepeche.getParamsMap().put("an_id", getAnne_univers());
                opgetRegleRepeche.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            DCIteratorBinding iterStrRegle = (DCIteratorBinding) getBindings().get("StructureRegleRepecheIterator");
            Row currentStrRegleRow = iterStrRegle.getCurrentRow();
            //System.out.println("Regle " + currentStrRegleRow.getAttribute("IdRegleRepechage").toString());
            if (null != currentStrRegleRow) {
                if (currentStrRegleRow.getAttribute("IdRegleRepechage")
                                      .toString()
                                      .equals("2")) {
                    //System.out.println("Repechage Complexe");
                    DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("FilEcIterator");
                    Row currentFilEcRow = iterFilEc.getCurrentRow();
                    if (null != currentFilEcRow) { 
                        //System.out.println("Fil Ec : " +                                           currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString());
                        try {
                            OperationBinding opgetRepechable =
                                getBindings().getOperationBinding("geRepechableFilUeMed");
                            opgetRepechable.getParamsMap()
                                .put("fil_ue",
                                     Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                            opgetRepechable.getParamsMap()
                                .put("fil_ec",
                                     Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                            opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                            opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                            opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                            opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                            opgetRepechable.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanColMed());
                    }
                } else {
                    //System.out.println("Repechage Simple");
                    try {
                        OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                        opgetRepechable.getParamsMap()
                            .put("fil_ue",
                                 Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                        opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                        opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                        opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                        opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                        opgetRepechable.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                }
            } else {
                //System.out.println("Repechage Simple");
                try {
                    OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                    opgetRepechable.getParamsMap()
                        .put("fil_ue",
                             Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                }
        }
        }else{
            System.out.println("Veuillez renseigner les critères.");
        }
    }

    public void setResultatPan(RichPanelGroupLayout resultatPan) {
        this.resultatPan = resultatPan;
    }

    public RichPanelGroupLayout getResultatPan() {
        return resultatPan;
    }

    public void setResultatPanCol(RichPanelCollection resultatPanCol) {
        this.resultatPanCol = resultatPanCol;
    }

    public RichPanelCollection getResultatPanCol() {
        return resultatPanCol;
    }

    public void OnRepechUeAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings("unchecked")
    public void getRegleRepech() {
        try {
            OperationBinding opgetRegleRepeche = getBindings().getOperationBinding("getRegleRepech");
            opgetRegleRepeche.getParamsMap().put("str_id", getStruct());
            opgetRegleRepeche.getParamsMap().put("an_id", getAnne_univers());
            opgetRegleRepeche.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        DCIteratorBinding iterStrRegle = (DCIteratorBinding) getBindings().get("StructureRegleRepecheIterator");
        Row currentStrRegleRow = iterStrRegle.getCurrentRow();
        if (null != currentStrRegleRow) {
            sessionScope.put("regle_repeche", currentStrRegleRow.getAttribute("IdRegleRepechage").toString());
        } else {
            sessionScope.put("regle_repeche", "0");
        }
    }

    @SuppressWarnings("unchecked")
    public void OnRepechUeConfirmAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            this.getPopConfirmRepeche().hide();
            BindingContainer bindings = this.getBindings();
            DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("ResultatFilUERepechableIterator");
            Row currentFilUeRow = iterFilUe.getCurrentRow();
            if (null != currentFilUeRow) {
                //System.out.println("parcours " + getParcours());
                //System.out.println("fil_ue " + currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
                //System.out.println("calendrier " + getCalendrier());
                Number moyenneSup = 10.0;
                Number moyenneInf = 0.0;
                if (this.getNoteSup().getValue() != null)
                    moyenneSup = (Number) (this.getNoteSup().getValue());
                if (this.getNoteInf().getValue() != null)
                    moyenneInf = (Number) (this.getNoteInf().getValue());
                if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
                    Number x = moyenneSup;
                    moyenneSup = moyenneInf;
                    moyenneInf = x;
                }
                /*int note_ref = 10;
                if(10 >= Integer.parseInt(sessionScope.get("moy_ref").toString()))
                     note_ref = 10;
                else if (12 >= Integer.parseInt(sessionScope.get("moy_ref").toString()) )
                    note_ref = 12;
                else if (14 >= Integer.parseInt(sessionScope.get("moy_ref").toString()) )
                        note_ref = 14;
                else if (16 >= Integer.parseInt(sessionScope.get("moy_ref").toString()) )
                    note_ref = 16;
                else if (18 >= Integer.parseInt(sessionScope.get("moy_ref").toString()) )
                    note_ref = 18;
                else if (20 >= Integer.parseInt(sessionScope.get("moy_ref").toString()) )
                    note_ref = 20;
                if (0 != note_ref){
                    System.out.println("note_ref : "+note_ref);*/
                try {
                    OperationBinding opgetRepeche = getBindings().getOperationBinding("RepecherUe");
                    opgetRepeche.getParamsMap().put("fil_ue", currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
                    opgetRepeche.getParamsMap().put("inspedUe", currentFilUeRow.getAttribute("IdInscriptionPedSemUe"));
                    opgetRepeche.getParamsMap()
                        .put("inspedSem", currentFilUeRow.getAttribute("IdInscriptionPedSemestre"));
                    opgetRepeche.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepeche.getParamsMap().put("moyenne_valid", 10);
                    opgetRepeche.getParamsMap().put("prcrs_maq", getPrcrs_maq_an());
                    opgetRepeche.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                    opgetRepeche.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                try {
                    OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                    opgetRepechable.getParamsMap().put("fil_ue", currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                //}
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                RichPopup popup = this.getPopSuccessRep();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopIntervalNote(RichPopup popIntervalNote) {
        this.popIntervalNote = popIntervalNote;
    }

    public RichPopup getPopIntervalNote() {
        return popIntervalNote;
    }

    public void setPopSuccessRep(RichPopup popSuccessRep) {
        this.popSuccessRep = popSuccessRep;
    }

    public RichPopup getPopSuccessRep() {
        return popSuccessRep;
    }

    public void setPopConfirmRepeche(RichPopup popConfirmRepeche) {
        this.popConfirmRepeche = popConfirmRepeche;
    }

    public RichPopup getPopConfirmRepeche() {
        return popConfirmRepeche;
    }

    @SuppressWarnings("unchecked")
    public void OnDetailNoteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("ResultatFilUERepechableIterator");
        Row currentFilUeRow = iterFilUe.getCurrentRow();
        try {
            OperationBinding opDetail = getBindings().getOperationBinding("getFilEcInvalide");
            opDetail.getParamsMap().put("filUe_id", currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
            opDetail.getParamsMap().put("inspedUe", currentFilUeRow.getAttribute("IdInscriptionPedSemUe"));
            opDetail.getParamsMap().put("calendrier", new Long(getCalendrier()));
            opDetail.getParamsMap().put("moyenne_valid", sessionScope.get("moy_ref"));
            opDetail.execute();

            try {
                OperationBinding optotalcoef = getBindings().getOperationBinding("getTotalCoefUe");
                optotalcoef.getParamsMap().put("fil_ue", currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
                Integer totalcoef = (Integer) optotalcoef.execute();
                sessionScope.put("totalcoef", totalcoef);
                //System.out.println("totalcoef "+totalcoef);
            } catch (Exception e) {
                System.out.println( e);
            }
            try {
                OperationBinding opcoefecNV = getBindings().getOperationBinding("getTotalCoefEcInvalid");
                opcoefecNV.getParamsMap().put("fil_ue", currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
                opcoefecNV.getParamsMap().put("inspedUe", currentFilUeRow.getAttribute("IdInscriptionPedSemUe"));
                opcoefecNV.getParamsMap().put("calendrier", new Long(getCalendrier()));
                opcoefecNV.getParamsMap().put("moyenne_valid", sessionScope.get("moy_ref"));
                Integer totalFilEcNV = (Integer) opcoefecNV.execute();
                sessionScope.put("totalFilEcNV", totalFilEcNV);
                //System.out.println("totalFilEcNV "+totalFilEcNV);
            } catch (Exception e) {
                System.out.println( e);
            }
            /*System.out.println("fil_ue "+currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
            System.out.println("inspedUe "+currentFilUeRow.getAttribute("IdInscriptionPedSemUe"));
            System.out.println("Calendrier "+getCalendrier());*/
            //System.out.println("inspedUe " + currentFilUeRow.getAttribute("IdInscriptionPedSemUe"));

            RichPopup popup = this.getPopDetailsRepech();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setPopDetailsRepech(RichPopup popDetailsRepech) {
        this.popDetailsRepech = popDetailsRepech;
    }

    public RichPopup getPopDetailsRepech() {
        return popDetailsRepech;
    }

    @SuppressWarnings("unchecked")
    public void onChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        sessionScope.put("is_saisie_refrechable", true);
        
    }

    @SuppressWarnings("unchecked")
    public void setunrefrsh(){
        // Add event code here...
        if(sessionScope.get("is_saisie_refrechable").equals(true))
            sessionScope.put("is_saisie_refrechable", false);
        else
            sessionScope.put("is_saisie_refrechable", true);
    }

    public void setPrcrs_maq_an(Long prcrs_maq_an) {
        this.prcrs_maq_an = prcrs_maq_an;
    }

    public Long getPrcrs_maq_an() {
        return prcrs_maq_an;
    }

    public void onExportAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setResultatPanColMed(RichPanelCollection resultatPanColMed) {
        this.resultatPanColMed = resultatPanColMed;
    }

    public RichPanelCollection getResultatPanColMed() {
        return resultatPanColMed;
    }

    @SuppressWarnings("unchecked")
    public void onEcChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
        BindingContainer bindings = this.getBindings();
        Number moyenneSup = 0.0;
        Number moyenneInf = 0.0;
        if (this.getNoteSup().getValue() != null)
            moyenneSup = (Number)(this.getNoteSup().getValue());
        if (this.getNoteInf().getValue() != null)
            moyenneInf = (Number)(this.getNoteInf().getValue());
        if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
            Number x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }
        System.out.println("Sup in Ec " + moyenneSup + " Inf : " + moyenneInf);
        sessionScope.put("moy_ref", moyenneSup);
        sessionScope.put("moy_inf", moyenneInf);
        DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("RepecheFilUeIterator");
        Row currentFilUeRow = iterFilUe.getCurrentRow();
        if (null != currentFilUeRow) {
            try {
                OperationBinding opgetRegleRepeche = getBindings().getOperationBinding("getRegleRepech");
                opgetRegleRepeche.getParamsMap().put("str_id", getStruct());
                opgetRegleRepeche.getParamsMap().put("an_id", getAnne_univers());
                opgetRegleRepeche.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            DCIteratorBinding iterStrRegle = (DCIteratorBinding) getBindings().get("StructureRegleRepecheIterator");
            Row currentStrRegleRow = iterStrRegle.getCurrentRow();
            //System.out.println("Regle " + currentStrRegleRow.getAttribute("IdRegleRepechage").toString());
            if (null != currentStrRegleRow) {
                if (currentStrRegleRow.getAttribute("IdRegleRepechage")
                                      .toString()
                                      .equals("2")) {
                    //System.out.println("Repechage Complexe");
                    DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("FilEcIterator");
                    Row currentFilEcRow = iterFilEc.getCurrentRow();
                    if (null != currentFilEcRow) {
                        //System.out.println("Fil Ec : " +                                           currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString());
                        try {
                            OperationBinding opgetRepechable =
                                getBindings().getOperationBinding("geRepechableFilUeMed");
                            opgetRepechable.getParamsMap()
                                .put("fil_ue",
                                     Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                            opgetRepechable.getParamsMap()
                                .put("fil_ec",
                                     Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                            opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                            opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                            opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                            opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                            opgetRepechable.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanColMed());
                    }
                } else {
                    //System.out.println("Repechage Simple");
                    try {
                        OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                        opgetRepechable.getParamsMap()
                            .put("fil_ue",
                                 Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                        opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                        opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                        opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                        opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                        opgetRepechable.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                }
            } else {
                //System.out.println("Repechage Simple");
                try {
                    OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                    opgetRepechable.getParamsMap()
                        .put("fil_ue", Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
            }
        }
    }

    public void OnRepecheMedAction(ActionEvent actionEvent) {
        System.out.println("Btn clicked");
    }

    @SuppressWarnings("unchecked")
    public void OnRepechGrpUeMedConfirm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        this.getPopConfirmRepGrpMed().hide();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = this.getBindings();
            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("ResultFilUeRepechableMedIterator");
            Row currentRes = iter.getCurrentRow();
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                if(1 == Integer.parseInt(nextRow.getAttribute("Resultat").toString())){
                    try {
                        OperationBinding opgetRepechable = getBindings().getOperationBinding("RepecherUeMed");
                        opgetRepechable.getParamsMap().put("parcours_maq", nextRow.getAttribute("IdParcoursMaquetAnnee"));
                        opgetRepechable.getParamsMap().put("inspedSem", nextRow.getAttribute("IdInscriptionPedSemestre"));
                        opgetRepechable.getParamsMap().put("inspedUe", nextRow.getAttribute("IdInscriptionPedSemUe"));
                        opgetRepechable.getParamsMap().put("calendrier", nextRow.getAttribute("IdCalendrierDeliberation"));
                        opgetRepechable.getParamsMap().put("fil_ue", nextRow.getAttribute("IdFiliereUeSemstre"));
                        opgetRepechable.getParamsMap().put("fil_ec", nextRow.getAttribute("IdFiliereUeSemstreEc"));
                        opgetRepechable.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        opgetRepechable.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            rsi.closeRowSetIterator();
            Number moyenneSup = 10.0;
            Number moyenneInf = 0.0;
            if (this.getNoteSup().getValue() != null)
                moyenneSup = (Number)(this.getNoteSup().getValue());
            if (this.getNoteInf().getValue() != null)
                moyenneInf = (Number)(this.getNoteInf().getValue());
            if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
                Number x = moyenneSup;
                moyenneSup = moyenneInf;
                moyenneInf = x;
            }
            DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("FilEcIterator");
            Row currentFilEcRow = iterFilEc.getCurrentRow();
            if (null != currentFilEcRow) {
                //System.out.println("Fil Ec : " +                                   currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString());
                try {
                    OperationBinding opgetRepechable =
                        getBindings().getOperationBinding("geRepechableFilUeMed");
                    opgetRepechable.getParamsMap()
                        .put("fil_ue",
                             Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstre").toString()));
                    opgetRepechable.getParamsMap()
                        .put("fil_ec",
                             Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanColMed());
            }
        }
    }

    public void setPopConfirmRepGrpMed(RichPopup popConfirmRepGrpMed) {
        this.popConfirmRepGrpMed = popConfirmRepGrpMed;
    }

    public RichPopup getPopConfirmRepGrpMed() {
        return popConfirmRepGrpMed;
    }
    
    public float getPointJury(Long inspedue, Long filEc, Long filUe, Long prcrs_maq){
        //System.out.println(" inspedue: "+inspedue);
        return 1;
    }

    public void OnRepechMedUeIndivAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings("unchecked")
    public void OnRepechIndivUeMedConfirm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        this.getPopConfirmRepGrpMed().hide();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = this.getBindings();
            Number moyenneSup = 10.0;
            Number moyenneInf = 0.0;
            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("ResultFilUeRepechableMedIterator");
            Row currentRes = iter.getCurrentRow();
            //if(1 == Integer.parseInt(currentRes.getAttribute("Resultat").toString()){
            try {
                OperationBinding opgetRepechable = getBindings().getOperationBinding("RepecherUeMed");
                opgetRepechable.getParamsMap().put("parcours_maq", currentRes.getAttribute("IdParcoursMaquetAnnee"));
                opgetRepechable.getParamsMap().put("inspedSem", currentRes.getAttribute("IdInscriptionPedSemestre"));
                opgetRepechable.getParamsMap().put("inspedUe", currentRes.getAttribute("IdInscriptionPedSemUe"));
                opgetRepechable.getParamsMap().put("calendrier", currentRes.getAttribute("IdCalendrierDeliberation"));
                opgetRepechable.getParamsMap().put("fil_ue", currentRes.getAttribute("IdFiliereUeSemstre"));
                opgetRepechable.getParamsMap().put("fil_ec", currentRes.getAttribute("IdFiliereUeSemstreEc"));
                opgetRepechable.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                opgetRepechable.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            //}
            if (this.getNoteSup().getValue() != null)
                moyenneSup = (Number)(this.getNoteSup().getValue());
            if (this.getNoteInf().getValue() != null)
                moyenneInf = (Number)(this.getNoteInf().getValue());
            if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
                Number x = moyenneSup;
                moyenneSup = moyenneInf;
                moyenneInf = x;
            }
            DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("FilEcIterator");
            Row currentFilEcRow = iterFilEc.getCurrentRow();
            if (null != currentFilEcRow) {
                //System.out.println("Fil Ec : " +                                   currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString());
                try {
                    OperationBinding opgetRepechable =
                        getBindings().getOperationBinding("geRepechableFilUeMed");
                    opgetRepechable.getParamsMap()
                        .put("fil_ue",
                             Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstre").toString()));
                    opgetRepechable.getParamsMap()
                        .put("fil_ec",
                             Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanColMed());
            }
        }
    }

    public void setPopConfirmRepIndivMed(RichPopup popConfirmRepIndivMed) {
        this.popConfirmRepIndivMed = popConfirmRepIndivMed;
    }

    public RichPopup getPopConfirmRepIndivMed() {
        return popConfirmRepIndivMed;
    }

    @SuppressWarnings("unchecked")
    public void onUeChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
        
        BindingContainer bindings = this.getBindings();
        Number moyenneSup = 0.0;
        Number moyenneInf = 0.0;
        /*if (this.getNoteSup().getValue() != null)
            moyenneSup = (Number)(this.getNoteSup().getValue());
        if (this.getNoteInf().getValue() != null)
            moyenneInf = (Number)(this.getNoteInf().getValue());
        if (moyenneInf.floatValue() > moyenneSup.floatValue()) {
            Number x = moyenneSup;
            moyenneSup = moyenneInf;
            moyenneInf = x;
        }*/
        System.out.println("Sup Ue " + moyenneSup + " Inf : " + moyenneInf);
        sessionScope.put("moy_ref", moyenneSup);
        sessionScope.put("moy_inf", moyenneInf);
        DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("RepecheFilUeIterator");
        Row currentFilUeRow = iterFilUe.getCurrentRow();
        if (null != currentFilUeRow) {
            try {
                OperationBinding opgetRegleRepeche = getBindings().getOperationBinding("getRegleRepech");
                opgetRegleRepeche.getParamsMap().put("str_id", getStruct());
                opgetRegleRepeche.getParamsMap().put("an_id", getAnne_univers());
                opgetRegleRepeche.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            DCIteratorBinding iterStrRegle = (DCIteratorBinding) getBindings().get("StructureRegleRepecheIterator");
            Row currentStrRegleRow = iterStrRegle.getCurrentRow();
            //System.out.println("Regle " + currentStrRegleRow.getAttribute("IdRegleRepechage").toString());
            if (null != currentStrRegleRow) {
                if (currentStrRegleRow.getAttribute("IdRegleRepechage")
                                      .toString()
                                      .equals("2")) {
                    //System.out.println("Repechage Complexe");
                    DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("FilEcIterator");
                    Row currentFilEcRow = iterFilEc.getCurrentRow();
                    if (null != currentFilEcRow) { 
                        //System.out.println("Fil Ec : " +
                         //                  currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString());
                        try {
                            OperationBinding opgetRepechable =
                                getBindings().getOperationBinding("geRepechableFilUeMed");
                            opgetRepechable.getParamsMap()
                                .put("fil_ue",
                                     Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                            opgetRepechable.getParamsMap()
                                .put("fil_ec",
                                     Long.parseLong(currentFilEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                            opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                            opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                            opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                            opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                            opgetRepechable.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanColMed());
                    }
                } else {
                   // System.out.println("Repechage Simple");
                    try {
                        OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                        opgetRepechable.getParamsMap()
                            .put("fil_ue",
                                 Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                        opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                        opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                        opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                        opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                        opgetRepechable.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                }
            } else {
                //System.out.println("Repechage Simple");
                try {
                    OperationBinding opgetRepechable = getBindings().getOperationBinding("geRepechableFilUe");
                    opgetRepechable.getParamsMap()
                        .put("fil_ue",
                             Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                    opgetRepechable.getParamsMap().put("parcours", new Long(getPrcrs_maq_an()));
                    opgetRepechable.getParamsMap().put("calendrier", new Long(getCalendrier()));
                    opgetRepechable.getParamsMap().put("notInf", moyenneInf);
                    opgetRepechable.getParamsMap().put("notSup", moyenneSup);
                    opgetRepechable.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPan());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatPanCol());
                }
        }
    }

    public void setEtdtEc(EtudiantsEcs etdtEc) {
        this.etdtEc = etdtEc;
    }

    public EtudiantsEcs getEtdtEc() {
        return etdtEc;
    }

    @SuppressWarnings("unchecked")
    public void OnDelibUeChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        EtudiantsEcs etdtEc = new EtudiantsEcs();
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
        DCIteratorBinding iterFilUe = (DCIteratorBinding) getBindings().get("DelibSemFilUeNewIterator");
        Row currentFilUeRow = iterFilUe.getCurrentRow();
        if(!currentFilUeRow.equals(null)){
            //System.out.println("IdFiliereUeSemstre : "+currentFilUeRow.getAttribute("IdFiliereUeSemstre"));
            try {
                OperationBinding opgetRepechable = getBindings().getOperationBinding("getEtudiantEc");
                opgetRepechable.getParamsMap().put("filUeId", Long.parseLong(currentFilUeRow.getAttribute("IdFiliereUeSemstre").toString()));
                opgetRepechable.getParamsMap().put("prcrsIaqId", new Long(getPrcrs_maq_an()));
                opgetRepechable.getParamsMap().put("semId", new Long(getSemestre()));
                opgetRepechable.getParamsMap().put("sessId", new Long(getSession()));
                opgetRepechable.getParamsMap().put("anId", new Long(getAnne_univers()));
                etdtEc = (EtudiantsEcs)opgetRepechable.execute();
                sessionScope.put("listEtudiansEcs", etdtEc);
                 //.listEtudiansEcs
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setPanStrech(RichPanelStretchLayout panStrech) {
        this.panStrech = panStrech;
    }

    public RichPanelStretchLayout getPanStrech() {
        return panStrech;
    }

    public void setPanGrpDelib(RichPanelGroupLayout panGrpDelib) {
        this.panGrpDelib = panGrpDelib;
    }

    public RichPanelGroupLayout getPanGrpDelib() {
        return panGrpDelib;
    }

    public void setPanColectDelib(RichPanelCollection panColectDelib) {
        this.panColectDelib = panColectDelib;
    }

    public RichPanelCollection getPanColectDelib() {
        return panColectDelib;
    }

    public void setTabDelib(RichTable tabDelib) {
        this.tabDelib = tabDelib;
    }

    public RichTable getTabDelib() {
        return tabDelib;
    }

    @SuppressWarnings("unchecked")
    public void onPublishUe(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding filUe = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        try {
            OperationBinding oppubUe = getBindings().getOperationBinding("publierUe");
            oppubUe.getParamsMap().put("fil_ue", filUe.getInputValue());
            oppubUe.getParamsMap().put("parcours_maq", new Long(getPrcrs_maq_an()));
            oppubUe.getParamsMap().put("calendrier", new Long(getCalendrier()));
            oppubUe.getParamsMap().put("action", "O");
            oppubUe.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            Integer res = (Integer) oppubUe.execute();
            if(1 == res){
                RichPopup popup = this.getPopUePublish();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }else{
                RichPopup popup = this.getPopUeNotPublish();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setPopUePublish(RichPopup popUePublish) {
        this.popUePublish = popUePublish;
    }

    public RichPopup getPopUePublish() {
        return popUePublish;
    }

    public void setPopUeNotPublish(RichPopup popUeNotPublish) {
        this.popUeNotPublish = popUeNotPublish;
    }

    public RichPopup getPopUeNotPublish() {
        return popUeNotPublish;
    }

    public void setPanGrpSaisieInter(RichPanelGroupLayout panGrpSaisieInter) {
        this.panGrpSaisieInter = panGrpSaisieInter;
    }

    public RichPanelGroupLayout getPanGrpSaisieInter() {
        return panGrpSaisieInter;
    }

    private static class ExpressionFactory {
    }
}
