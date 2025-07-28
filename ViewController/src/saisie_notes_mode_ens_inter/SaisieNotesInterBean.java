package saisie_notes_mode_ens_inter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.security.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPanelWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.dss.util.BASE64Encoder;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaisieNotesInterBean {
    private RichTable tableSaisieNotesInter;
    private RichPopup popup;
    private RichPopup popCloture;
    private RichPopup popImport;
    private RichInputFile fileUpload;
    private UploadedFile uploadedFile;
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionFlowScope = adfCtx.getSessionScope();
    Long cal = Long.parseLong(sessionFlowScope.get("id_calendrier").toString());
    Long parcours_maq = Long.parseLong(sessionFlowScope.get("prcrs_maq_an").toString());
    private Long utilisateur = Long.parseLong(sessionFlowScope.get("id_user").toString());

    String code_niv = sessionFlowScope.get("niv_sec").toString();
    String fr_acc = sessionFlowScope.get("id_fr_acc").toString();
    private static final String ALGO = "AES";
    private byte[] keyvalue;
    String parcours = sessionFlowScope.get("id_niv_form_parcours").toString();
    String sess = sessionFlowScope.get("id_session").toString();
    String sem = sessionFlowScope.get("id_smstre").toString();
    String an = sessionFlowScope.get("id_annee").toString();
    String crypted="";
    String key =
        getParcours() + "" + getSess() + "" + getSem() + "" + getAn() + "" + getAn() + "" +
        getSem() + "" + getSess() + "" + getParcours() + "" + getSem() + "" + getSess() + "" + getParcours() +
        "" + getAn() + "" + getSess() + "" + getParcours() + "" + getAn() + "" + getSem();
    private RichPanelWindow panelbtnwindow;
    private RichPanelGroupLayout panelbtngroupe;
    private RichPopup popupConfirmOpenInter;
    private RichPopup popupSuccessOpen;
    private RichPopup popEcClosed;
    private RichPopup popNotStudent;
    private RichPopup popSaisieSaved;
    private RichPopup popNoteDuplicate;
    private RichPopup popManyCntrlType;
    private RichPopup popupEcClosed;
    private RichPopup pop_hb;
    private RichInputText noteVal;

    String brds = "solid";
    String brdc = "#c00";
    String brdw = "2px";
    private RichPopup popupErrCheck;
    private RichPanelCollection panColSaisieNote;
    private RichPanelGroupLayout panGrp;
    private RichTable tabNoteInter;
    private RichInputText etdNum;
    private RichPanelGroupLayout panGrpDetEtd;
    private RichPanelGroupLayout panGrpForm;
    private RichPanelFormLayout panFormDetEtd;
    private RichPanelGroupLayout panHightGroup;
    private RichPanelCollection panHightCollection;
    private RichTable tabHightEtd;
    private RichPanelGroupLayout panelGrp;
    private RichSelectOneChoice ecEvalTpeCntrl;
    private RichSelectBooleanCheckbox sans_notes_checkbox;
    private RichPopup popupUeClosed;
    private RichPopup popUeCloseed;

    public void setParcours(String parcours) {
        this.parcours = parcours;
    }

    public String getParcours() {
        return parcours;
    }

    public void setSess(String sess) {
        this.sess = sess;
    }

    public String getSess() {
        return sess;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSem() {
        return sem;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getAn() {
        return an;
    }


    public void setCal(Long cal) {
        this.cal = cal;
    }

    public Long getCal() {
        return cal;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    @SuppressWarnings("unchecked")
    public SaisieNotesInterBean() {
    }


    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setTableSaisieNotesInter(RichTable tableSaisieNotesInter) {
        this.tableSaisieNotesInter = tableSaisieNotesInter;
    }

    public RichTable getTableSaisieNotesInter() {
        return tableSaisieNotesInter;
    }

   

    @SuppressWarnings("unchecked")
    public void EnregistrerSaisieNotes(ActionEvent actionEvent) {
        /*BindingContext bindingContext = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer nbr = 0;
        ArrayList<Long> tab_err = new ArrayList<Long>();
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Verifier s'il y a une note vide et une cage non coché
            if ((nextRow.getAttribute("Note") == null) && (Integer.parseInt(nextRow.getAttribute("IsAbsent").toString()) == 0) ){
                tab_err.add(Long.parseLong(nextRow.getAttribute("IdNoteModeEnsInter").toString()));
                nextRow.setAttribute("Style", "border-style: solid; border-color: #c00; border-width: 2px;");
            }
        }

        if(tab_err.size() > 0 ){
            RichPopup popup = this.getPopupErrCheck();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }else{
            RichPopup popup = this.getPopup();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }*/
        RichPopup popup = this.getPopup();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }
    
    public void fetchNoteValue() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iterator = bindings.findIteratorBinding("EtudiantSaisieNoteInterNewIterator");

        // Récupérer la ligne sélectionnée
        Row selectedRow = iterator.getCurrentRow();
        if (selectedRow != null) {
            // Récupérer la valeur de "Note" depuis la vue modifiable
            Object noteValue = selectedRow.getAttribute("NotesModeEnseignementInterNewVO.Note");
            
        }
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
       
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            executeOperation("Commit").execute();
            
            try{
                OperationBinding opfilter = getBindings().getOperationBinding("resetEtudiantSansNote");
                opfilter.execute();
                this.getSans_notes_checkbox().setDisabled(true);
            }catch(Exception e){
                System.out.println(e);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());

            RichPopup popup = this.getPopSaisieSaved();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPopup();
        popup.hide();
        this.getPopup().hide();
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void setPopCloture(RichPopup popCloture) {
        this.popCloture = popCloture;
    }

    public RichPopup getPopCloture() {
        return popCloture;
    }

    public void onDialogCancelCloture(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopCloture().hide();
        this.getPopupConfirmOpenInter().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogCloture(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            this.getPopCloture().hide();
            BindingContext bindingContext = BindingContext.getCurrent();
            DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
            DCIteratorBinding iterInter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator");
            RowSetIterator rsi = iterInter.getViewObject().createRowSetIterator(null);
            Integer nbr = 0;
            ArrayList<Long> tab_err = new ArrayList<Long>();
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                //Verifier s'il y a une note vide et une cage non coché
                if ((nextRow.getAttribute("Note") == null) && (Integer.parseInt(nextRow.getAttribute("IsAbsent").toString()) == 0) ){
                    tab_err.add(Long.parseLong(nextRow.getAttribute("IdNoteModeEnsInter").toString()));
                    nextRow.setAttribute("Style", "border-style: solid; border-color: #c00; border-width: 2px;");
                }else
                nextRow.setAttribute("Style","");
            }

            if(tab_err.size() > 0 ){
                RichPopup popup = this.getPopupErrCheck();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }else{
                AttributeBinding idtype_controle = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
                AttributeBinding IdModeControleEc =
                    (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
                AttributeBinding IdFiliereUeSemstreEc =
                    (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
                String action = "O";
                OperationBinding clotureSaisieNotesInter = getBindings().getOperationBinding("clotureSaisieNotesInter");
                clotureSaisieNotesInter.getParamsMap().put("fil_sem_ec", IdFiliereUeSemstreEc.getInputValue());
                clotureSaisieNotesInter.getParamsMap().put("type_control", idtype_controle.getInputValue());
                clotureSaisieNotesInter.getParamsMap().put("mode_control", IdModeControleEc.getInputValue());
                clotureSaisieNotesInter.getParamsMap().put("calendrier", getCal());
                clotureSaisieNotesInter.getParamsMap().put("action", action);
                clotureSaisieNotesInter.getParamsMap()
                    .put("utilisateur", Integer.parseInt(sessionFlowScope.get("id_user").toString()));
                Integer result = (Integer) clotureSaisieNotesInter.execute();
                DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EcEvalTypeCntrModeCntrllNewIterator");
                ViewObject vo = iter.getViewObject();
                vo.executeQuery();
                System.out.println(" result: "+result);
                if (result == 1) {
                    OperationBinding calcul_moy_ec_tc =
                        getBindings().getOperationBinding("CalculerMoyenneEcTypeControle");
                    calcul_moy_ec_tc.getParamsMap().put("calendrier", getCal());
                    calcul_moy_ec_tc.getParamsMap().put("parcours_maq", getParcours_maq());
                    calcul_moy_ec_tc.getParamsMap().put("fil_ec", IdFiliereUeSemstreEc.getInputValue());
                    calcul_moy_ec_tc.getParamsMap().put("type_cntrl", idtype_controle.getInputValue());
                    calcul_moy_ec_tc.getParamsMap().put("utilisateur", getUtilisateur());
                    calcul_moy_ec_tc.execute();
                // 2 : nme closed
                // 0 : note existe
                // 4 : all inter not closed yet
                } else if(result == 2) {
                    RichPopup popup = this.getPopEcClosed();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                // 3 : all inter closed calcul moyenne ec
                else if(result == 3) {
                    try{
                    OperationBinding calcul_moy_ec_tc =
                        getBindings().getOperationBinding("CalculerMoyenneEcTypeControle");
                    calcul_moy_ec_tc.getParamsMap().put("calendrier", getCal());
                    calcul_moy_ec_tc.getParamsMap().put("parcours_maq", getParcours_maq());
                    calcul_moy_ec_tc.getParamsMap().put("fil_ec", IdFiliereUeSemstreEc.getInputValue());
                    calcul_moy_ec_tc.getParamsMap().put("type_cntrl", idtype_controle.getInputValue());
                    calcul_moy_ec_tc.getParamsMap().put("utilisateur", getUtilisateur());
                    calcul_moy_ec_tc.execute();
                }catch(Exception e){
                        System.out.println(e);
                }
                try{
                    OperationBinding clotureSaisieNotes = getBindings().getOperationBinding("clotureSaisieNotes");
                    clotureSaisieNotes.getParamsMap().put("fil_sem_ec", IdFiliereUeSemstreEc.getInputValue());
                    clotureSaisieNotes.getParamsMap().put("type_control", idtype_controle.getInputValue());
                    clotureSaisieNotes.getParamsMap().put("calendrier", getCal());
                    clotureSaisieNotes.getParamsMap().put("action", action);
                    clotureSaisieNotes.getParamsMap().put("utilisateur",Long.parseLong(sessionFlowScope.get("id_user").toString()));
                    clotureSaisieNotes.getParamsMap().put("prcrs_maq",getParcours_maq());
                    Integer result1 = (Integer) clotureSaisieNotes.execute();
                    System.out.println("result1 : "+result1);
                    if (result1 == 1) {
                        OperationBinding calcul_moy_ec = getBindings().getOperationBinding("CalculerMoyenneEc");
                        calcul_moy_ec.getParamsMap().put("calendrier", getCal());
                        calcul_moy_ec.getParamsMap().put("parcours_maq", getParcours_maq());
                        calcul_moy_ec.getParamsMap().put("fil_ec", IdFiliereUeSemstreEc.getInputValue());
                        calcul_moy_ec.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        calcul_moy_ec.execute();
                    }
            }catch(Exception e){
                System.out.println(e);
            }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtngroupe());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());
                
            }
        }
    }

    public void setPopImport(RichPopup popImport) {
        this.popImport = popImport;
    }

    public RichPopup getPopImport() {
        return popImport;
    }

    @SuppressWarnings("unchecked")
    public void readNProcessExcelx1(InputStream xlsx) throws IOException {
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        AttributeBinding CodificationUE = (AttributeBinding) getBindings().getControlBinding("CodificationUE");
        AttributeBinding CodificationEC = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        AttributeBinding LibelleCourtCtrl = (AttributeBinding) getBindings().getControlBinding("LibelleCourtCtrl");
        AttributeBinding LibModeCtrl = (AttributeBinding) getBindings().getControlBinding("LibModeCtrl");
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterNewIterator");
        //RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        //int totRows = ((int) iter.getEstimatedRowCount()) + 1;
        //Here 2 is the number of columns
        HashMap<String,String> ligne_colonne = new HashMap<String,String>();
        ArrayList<HashMap<String,String>> tab_erreur = new ArrayList<HashMap<String,String>>();
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        String pass = getAn()+""+id_fil_ec.getInputValue().toString()+""+id_type_ctrl.getInputValue().toString()+""+id_mode_ctrl_ec.getInputValue().toString()+"" +""+getSem()+""+getSess()+""+getCal();
        try {
            ZipSecureFile.setMinInflateRatio(0);
            WorkBook = new XSSFWorkbook(xlsx);
            try {
                XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
                if (sheet.validateSheetPassword(pass)) {
                    Integer skipRw = 10;
                    Integer skipcnt = 1;
                    //verifier si le fichier contient des erreurs
                    for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                        if (skipcnt > skipRw) {
                            int Index = 4;
                            for (int column = 4; column < tempRow.getLastCellNum(); column++) {
                                Cell MytempCell = tempRow.getCell(column);
                                if (MytempCell != null) {
                                    Index = MytempCell.getColumnIndex();
                                } else {
                                    Index++;
                                }
                                /*if(Index == 5){
                                    System.out.println(MytempCell.getStringCellValue());
                                }*/
                                if (Index == 9) {
                                    Integer ligne = tempRow.getRowNum() + 1;
                                    if (MytempCell.getCellType().toString() == "BLANK") {
                                        ligne_colonne.put(ligne.toString(),
                                                          "Une Note ne doit pas être nulle ! Mettez 'ABS' si pas de note");
                                        tab_erreur.add(ligne_colonne);
                                    } else if (MytempCell.getCellType().toString() == "STRING") {
                                        if (isCellEmpty(MytempCell) || !MytempCell.getStringCellValue()
                                                                                  .toUpperCase()
                                                                                  .equals("ABS")) {
                                            ligne_colonne.put(ligne.toString(),
                                                              "Veuillez mettre 'ABS' à la place de '"+MytempCell.getStringCellValue()+"' si pas de note !");
                                            tab_erreur.add(ligne_colonne);
                                            //System.out.println(MytempCell.getStringCellValue());
                                        }
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if ((MytempCell.getNumericCellValue() > 20) ||
                                            (MytempCell.getNumericCellValue() < 0)) {
                                            ligne_colonne.put(ligne.toString(),
                                                              "Note doit etre comprise entre 0 et 20");
                                            tab_erreur.add(ligne_colonne);
                                        }
                                    }
                                }
                            }
                        }
                        skipcnt++;
                    }
                    WorkBook.close();
                } else {
                    FacesMessage msg =
                        new FacesMessage("Veuillez choisir le fichier original du nom " +
                                         CodificationUE.getInputValue() + "_" + CodificationEC.getInputValue() + "_" +
                                         LibelleCourtCtrl.getInputValue() + "_" + LibModeCtrl.getInputValue() +
                                         ".xlsx");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    executeOperation("Rollback").execute();
                }
                //Si tout est ok
                if (tab_erreur.size() == 0) {
                    sheet = WorkBook.getSheetAt(sheetIndex);
                    if (sheet.validateSheetPassword(pass)) {
                        Integer skipRw = 10;
                        Integer skipcnt = 1;
                        for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                            if (skipcnt > skipRw) {
                                int Index = 4;
                                String numero = null;
                                for (int column = 4; column < tempRow.getLastCellNum(); column++) {
                                    Cell MytempCell = tempRow.getCell(column);
                                    if (MytempCell != null) {
                                        Index = MytempCell.getColumnIndex();
                                    } else {
                                        Index++;
                                    }
                                    
                                    if (Index == 4) {
                                        numero = MytempCell.getStringCellValue();
                                    }
                                    if (Index == 9) {
                                        if (MytempCell.getCellType().toString() == "NUMERIC") {
                                            OperationBinding opfindupdate =
                                                getBindings().getOperationBinding("FindAndUpdateNote");
                                            opfindupdate.getParamsMap()
                                                .put("id_mode_ctrl_ec", id_mode_ctrl_ec.getInputValue());
                                            opfindupdate.getParamsMap().put("calendrier", getCal());
                                            opfindupdate.getParamsMap()
                                                .put("id_type_ctrl", id_type_ctrl.getInputValue());
                                            opfindupdate.getParamsMap().put("numero", numero);
                                            opfindupdate.getParamsMap().put("note", MytempCell.getNumericCellValue());
                                            opfindupdate.getParamsMap().put("utilisateur", getUtilisateur());
                                            opfindupdate.execute();
                                        } else if (MytempCell.getCellType().toString() == "STRING") {
                                            OperationBinding opfindupdate =
                                                getBindings().getOperationBinding("ClearNote");
                                            opfindupdate.getParamsMap()
                                                .put("id_mode_ctrl_ec", id_mode_ctrl_ec.getInputValue());
                                            opfindupdate.getParamsMap().put("calendrier", getCal());
                                            opfindupdate.getParamsMap()
                                                .put("id_type_ctrl", id_type_ctrl.getInputValue());
                                            opfindupdate.getParamsMap().put("numero", numero);
                                            opfindupdate.getParamsMap().put("utilisateur", getUtilisateur());
                                            opfindupdate.execute();
                                        }
                                    }
                                }
                            }
                            skipcnt++;
                        }
                        WorkBook.close();
                        getPopImport().hide();
                        executeOperation("Commit").execute();
                        ViewObject vo = iter.getViewObject();
                        vo.executeQuery();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        String refreshpage = fc.getViewRoot().getViewId();
                        ViewHandler ViewH = fc.getApplication().getViewHandler();
                        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
                        UIV.setViewId(refreshpage);
                        fc.setViewRoot(UIV);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtngroupe());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());
                    }
                } else {
                    for (String current : tab_erreur.get(0).keySet()) {
                        FacesMessage msg =
                            new FacesMessage("Ligne n° " + current + " et Colonne  J :" +
                                             tab_erreur.get(0).get(current));
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } /*finally {
            xlsx.close();
        }*/
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            xlsx.close();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void readNProcessExcelx2(InputStream xlsx) throws IOException {
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        AttributeBinding CodificationUE = (AttributeBinding) getBindings().getControlBinding("CodificationUE");
        AttributeBinding CodificationEC = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        AttributeBinding LibelleCourtCtrl = (AttributeBinding) getBindings().getControlBinding("LibelleCourtCtrl");
        AttributeBinding LibModeCtrl = (AttributeBinding) getBindings().getControlBinding("LibModeCtrl");
        /*DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterVO1Iterator");
        //RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        int totRows = ((int) iter.getEstimatedRowCount()) + 1;*/
        //Here 2 is the number of columns
        HashMap<String,String> ligne_colonne = new HashMap<String,String>();
        ArrayList<String> tab_hors_base = new ArrayList<String>();
        ArrayList<HashMap<String,String>> tab_erreur = new ArrayList<HashMap<String,String>>();
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        String pass = getAn()+""+id_fil_ec.getInputValue().toString()+""+
        id_type_ctrl.getInputValue().toString()+""+id_mode_ctrl_ec.getInputValue().toString()+"" +
            ""+getSem()+""+getSess()+""+getCal();
        try {
            ZipSecureFile.setMinInflateRatio(0);
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {
            System.out.println(e);
        }
        XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
        if(sheet.validateSheetPassword(pass)){
            //Integer derniere_ligne = totRows + 10;
            //Integer premiere_ligne = ((derniere_ligne-totRows) + 1);
            Integer skipRw = 9;
            Integer skipcnt = 1;
            //verifier si le fichier contient des erreurs
            for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                if (skipcnt > skipRw) {
                    int Index = 4;
                    String numero = null;
                    //Iterate over row's columns
                    for (int column = 4; column < tempRow.getLastCellNum(); column++) {
                        Cell MytempCell = tempRow.getCell(column);
                        if (MytempCell != null) {
                            Index = MytempCell.getColumnIndex();  
                        }else {
                            Index++;
                        }
                        if (Index == 4) {
                           numero = MytempCell.getStringCellValue();
                        }
                        if (0 != numero.length()) {
                            if (Index == 9) {
                                Integer ligne = tempRow.getRowNum() + 1;
                                //if (!isCellEmpty(MytempCell)) { // remplir le tab d'erreur
                                if ((null != MytempCell) && (!isCellEmpty(MytempCell))) {
                                    try {
                                        if (MytempCell.getCellType().toString() == "NUMERIC") {
                                            if ((MytempCell.getNumericCellValue() > 20) ||
                                                (MytempCell.getNumericCellValue() < 0)) {
                                                ligne_colonne.put(ligne.toString(),"Note doit etre comprise entre 0 et 20");
                                                tab_erreur.add(ligne_colonne);
                                            } else {
                                                OperationBinding opfindupdate =
                                                    getBindings().getOperationBinding("FindAndUpdateNoteEtd");
                                                opfindupdate.getParamsMap().put("id_mode_ctrl_ec", id_mode_ctrl_ec.getInputValue());
                                                opfindupdate.getParamsMap().put("calendrier", getCal());
                                                opfindupdate.getParamsMap().put("id_type_ctrl", id_type_ctrl.getInputValue());
                                                opfindupdate.getParamsMap().put("numero", numero);
                                                opfindupdate.getParamsMap().put("note", MytempCell.getNumericCellValue());
                                                opfindupdate.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long id_nt = (Long) opfindupdate.execute();
                                                if (0 == id_nt) {
                                                    tab_hors_base.add(numero);
                                                }
                                            }
                                        } else {
                                            OperationBinding opfindupdate = getBindings().getOperationBinding("FindAndUpdateNoteEtd");
                                            opfindupdate.getParamsMap().put("id_mode_ctrl_ec", id_mode_ctrl_ec.getInputValue());
                                            opfindupdate.getParamsMap().put("calendrier", getCal());
                                            opfindupdate.getParamsMap().put("id_type_ctrl", id_type_ctrl.getInputValue());
                                            opfindupdate.getParamsMap().put("numero", numero);
                                            opfindupdate.getParamsMap().put("note", null);
                                            opfindupdate.getParamsMap().put("utilisateur", getUtilisateur());
                                            Long id_nt = (Long) opfindupdate.execute();
                                            if (0 == id_nt) {
                                                tab_hors_base.add(numero);
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println(e);
                                        ligne_colonne.put(ligne.toString(), "La note doit etre un nombre");
                                        tab_erreur.add(ligne_colonne);
                                    }
                                    //else{
                                    //  note = MytempCell.getNumericCellValue();
                                    //}
                                } //else{
                                //note = null;
                                //}
                            }
                        }
                    }
                }
                skipcnt++;
            }
            WorkBook.close();
            getPopImport().hide();
            
            sessionFlowScope.put("tab_hors_base",tab_hors_base);
            
            //Pas d'erreur noté
            executeOperation("Commit").execute();
            FacesContext fc = FacesContext.getCurrentInstance();
            String refreshpage = fc.getViewRoot().getViewId();
            ViewHandler ViewH =  fc.getApplication().getViewHandler();
            UIViewRoot UIV = ViewH.createView(fc,refreshpage);
            UIV.setViewId(refreshpage);
            fc.setViewRoot(UIV);
            
            if (tab_hors_base.size() > 0) {
                RichPopup popup = this.getPop_hb();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            FacesMessage msg = new FacesMessage("Veuillez choisir le fichier original du nom "+CodificationUE.getInputValue()+"_"+CodificationEC.getInputValue()+"_"+LibelleCourtCtrl.getInputValue()+"_"+LibModeCtrl.getInputValue()+".xlsx");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            executeOperation("Rollback").execute();
            
            WorkBook.close();
            this.getPopImport().hide();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void readNProcessExcelx(InputStream xlsx) throws IOException {
        
        AttributeBinding idtype_controle = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding IdModeControleEc = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        
        AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnnee");
        AttributeBinding libelle_ue = (AttributeBinding) getBindings().getControlBinding("UeEvalNew");
        AttributeBinding libelle_ec = (AttributeBinding) getBindings().getControlBinding("EcEvalNew");
        AttributeBinding libelle_type_ctrl = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrlNew");
        AttributeBinding libelle_mode_ens = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrModeCntrllNew");
        
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
        AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");

        String [][] entete = new String[10][2];        
        entete[0][0] = "Niveau de Formation :";
        entete[0][1] = lib_niv_form.getInputValue().toString();
        entete[1][0] = "UE :";
        entete[1][1] = libelle_ue.getInputValue().toString();
        entete[2][0] = "EC :";
        entete[2][1] = libelle_ec.getInputValue().toString();
        entete[3][0] = "Type de Controle :";
        entete[3][1] = libelle_type_ctrl.getInputValue().toString();
        
        entete[4][0] = "Semestre :";
        entete[4][1] = getSem();

        entete[5][0] = "Session : ";
        entete[5][1] = getSess();
        
        entete[6][0] = "Id FilUeSemEc";
        entete[6][1] = id_fil_ec.getInputValue().toString();
        entete[7][0] = "Id Type Ctrl";
        entete[7][1] = id_type_ctrl.getInputValue().toString();
        
        entete[8][0] = "Mode Enseignement :";
        entete[8][1] = libelle_mode_ens.getInputValue().toString()+"("+id_mode_ctrl_ec.getInputValue().toString()+")";
                
        entete[9][0] = "Année Universitaire :";
        entete[9][1] = lib_annee.getInputValue().toString();

        //Use XSSFWorkbook for XLS file
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;

        try {
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {

        }
        // WorkBook.
        OperationBinding op_refresh = getBindings().getOperationBinding("ExecuteWithParamsNoteModeEnsInter");
        op_refresh.getParamsMap().put("id_mode_ctrl_ec_var", IdModeControleEc.getInputValue());
        op_refresh.getParamsMap().put("id_type_ctrl_var", idtype_controle.getInputValue());
        op_refresh.getParamsMap().put("id_cal_delib_var", getCal());
        op_refresh.execute();
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterVO1Iterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        int totRows = ((int) iter.getEstimatedRowCount()) + 1;
        //Here 2 is the number of columns
        HashMap<String,String> ligne_colonne = new HashMap<String,String>();
        ArrayList<HashMap<String,String>> tab_erreur = new ArrayList<HashMap<String,String>>();
        XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
        try{
        }
        catch(Exception e) {
        }
        if( sheet.getRow(5).getCell(5) == null || sheet.getRow(6).getCell(5) == null || sheet.getRow(7).getCell(5) == null || sheet.getRow(8).getCell(5) == null || sheet.getRow(9).getCell(5) == null){
            FacesMessage msg1 = new FacesMessage("Veuillez choisir le fichier qui contient : ");
            msg1.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg1);
            for(int i=0;i<10;i++){
                FacesMessage msg = new FacesMessage("+ "+entete[i][0]+" "+entete[i][1]);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            getPopImport().hide();
        }
        else{
            Long id_sem = Long.parseLong( sheet.getRow(5).getCell(5).getStringCellValue().toString());
            Long id_sess = Long.parseLong( sheet.getRow(6).getCell(5).getStringCellValue().toString());
            Long id_ec = Long.parseLong( sheet.getRow(7).getCell(5).getStringCellValue().toString());
            Long id_ty_ctrl = Long.parseLong( sheet.getRow(8).getCell(5).getStringCellValue().toString());
            String lib_mode_ens = sheet.getRow(9).getCell(5).getStringCellValue().toString();
            String lib_annee_excel = sheet.getRow(10).getCell(5).getStringCellValue().toString();

            OperationBinding op_getAnnee = getBindings().getOperationBinding("getIdAnne");
            op_getAnnee.getParamsMap().put("lib_annee",  lib_annee_excel);
            op_getAnnee.execute();
            String lib_mode = lib_mode_ens.substring(lib_mode_ens.indexOf("(") + 1, lib_mode_ens.indexOf(")"));
            Long id_mode_excel = Long.parseLong(lib_mode);
            AttributeBinding id_annee_excel = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
            Long ec = Long.parseLong( id_fil_ec.getInputValue().toString());
            Long type_controle = Long.parseLong( id_type_ctrl.getInputValue().toString());
            Long id_annee = Long.parseLong(id_annee_excel.getInputValue().toString());
            //le mot de passe annee,fil_ec,type_control,id_mode_ens,semestre,session (recuperer à partir du fichier excel)
            String password_read = id_annee+""+id_ec+""+id_ty_ctrl+""+id_mode_excel+""+id_sem+""+id_sess;

            if(!sheet.validateSheetPassword(password_read)){
                AttributeBinding CodificationUE = (AttributeBinding) getBindings().getControlBinding("CodificationUE");
                AttributeBinding CodificationEC = (AttributeBinding) getBindings().getControlBinding("CodificationEC");
                AttributeBinding LibelleCourtCtrl = (AttributeBinding) getBindings().getControlBinding("LibelleCourtCtrl");               
                AttributeBinding LibModeCtrl = (AttributeBinding) getBindings().getControlBinding("LibModeCtrl");
                FacesMessage msg = new FacesMessage("Veuillez choisir le fichier original du nom "+CodificationUE.getInputValue()+"_"+CodificationEC.getInputValue()+"_"+LibelleCourtCtrl.getInputValue()+"_"+LibModeCtrl.getInputValue()+".xlsx");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
               //si le fichier est bon
                if(type_controle != id_ty_ctrl || ec != id_ec || id_sem != Long.parseLong(getSem()) || id_sess != Long.parseLong(getSess()) || id_mode_excel != Long.parseLong(id_mode_ctrl_ec.getInputValue().toString())) {
                    FacesMessage msg1 = new FacesMessage("Veuillez choisir le fichier qui contient :");
                    msg1.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg1);
                    for(int i=0;i<10;i++){
                        FacesMessage msg = new FacesMessage("+ "+entete[i][0]+" "+entete[i][1]);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    getPopImport().hide();
                }
                else{
                    Integer derniere_ligne = totRows + 12;
                    Integer premiere_ligne = ((derniere_ligne-totRows) + 1);
                    //existence d'anonymat
                    OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
                    isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong( getParcours()));
                    isGenereAnonymat.getParamsMap().put("anne", Long.parseLong( getAn()));
                    isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong( getSem()));
                    isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong( getSess()));
                    Integer is_anomymat = (Integer)isGenereAnonymat.execute();
                    //s'il ya anonymat
                    if(is_anomymat > 0){ 
                        String anonymat = "";
                        Double note = null;
                        for (Integer row_current = premiere_ligne; row_current <derniere_ligne; row_current++) { 
                            int Index = 4;
                            //Iterate over row's columns
                            for (int column = 4; column < 6; column++) {
                                Cell MytempCell = sheet.getRow(row_current).getCell(column);
                                if (MytempCell != null) {
                                    Index = MytempCell.getColumnIndex();
                                } else {
                                    Index++;
                                }
                                if (Index == 4) {
                                    anonymat = MytempCell.getStringCellValue();                    
                                } 
                                else if (Index == 5) { 
                                    Integer ligne = row_current +1;
                                    try{
                                    if(!isCellEmpty(MytempCell)){       // remplir le tab d'erreur
                                            if((MytempCell.getNumericCellValue() > 20)||(MytempCell.getNumericCellValue() < 0) ){
                                                ligne_colonne.put(ligne.toString(),"Note doit etre comprise entre 0 et 20");
                                                tab_erreur.add(ligne_colonne);
                                            }
                                            else
                                                note = MytempCell.getNumericCellValue();
                                        }
                                        else
                                            note=null;
            
                                    }catch(Exception e) {
                                        ligne_colonne.put(ligne.toString(),"La note doit etre un nombre");
                                        tab_erreur.add(ligne_colonne);
                                    }
                                }   
                            }
                        }
                        // verifier si tab d'erreur n'est pas vide
                        if(tab_erreur.size() > 0){
                            for (String current : tab_erreur.get(0).keySet()) {
                                FacesMessage msg = new FacesMessage("Ligne n° "+current+" et Colonne n° 6 :"+tab_erreur.get(0).get(current));
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                            getPopImport().hide();
                        }
                        // pas d'erreur dans le fichier d'excel
                        if(tab_erreur.size() == 0){
                            //for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                            anonymat = "";
                            note = null;
                            for (Integer row_current = premiere_ligne; row_current < derniere_ligne; row_current++) { 
                                int Index = 4;
                                //Iterate over row's columns
                                for (int column = 4; column < 6; column++) {
                                    Cell MytempCell = sheet.getRow(row_current).getCell(column);
                                    if (MytempCell != null) {
                                        Index = MytempCell.getColumnIndex();
                                    } else {
                                        Index++;
                                    }
                                    if (Index == 4) {
                                        anonymat = MytempCell.getStringCellValue();                       
                                    } 
                                    else if (Index == 5) {                      
                                        if(!isCellEmpty(MytempCell))
                                            note=MytempCell.getNumericCellValue();
                                        else
                                            note=null;
                                    }   
                                }
                                try {
                                    key = key.substring(0, 16);
                                    crypted = encrypt(anonymat,key);       //ré-encrypter l'anonymat recupéré                    
                                    OperationBinding op_getAnonymat = getBindings().getOperationBinding("getAnonymat");
                                    op_getAnonymat.getParamsMap().put("anonymat_var",  crypted);
                                    op_getAnonymat.execute();
                                    //id_etudiant de l'anonymat encrypté
                                    //id_etudiant de l'anonymat encrypté
                                    AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
    
                                    OperationBinding op_getIdNoteModeEns = getBindings().getOperationBinding("getIdNoteModeEnsInter");
                                    op_getIdNoteModeEns.getParamsMap().put("id_etud",Long.parseLong(id_etud.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_fil_ue_sem",  Long.parseLong(IdFiliereUeSemstre.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_type_ctrl",  Long.parseLong(idtype_controle.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_mode_ctrl_ec",  Long.parseLong(IdModeControleEc.getInputValue().toString()));
                                    op_getIdNoteModeEns.execute();
                                    
                                    //id_note_mode_enseignement_intermediaire
                                    AttributeBinding id_note_mode_inter = (AttributeBinding) getBindings().getControlBinding("IdNoteModeEnsInter");
                                    //pour faire la mise à jour du note recupérée dans le fichier Excel
                                    
                                    OperationBinding findUpdateNoteInter = getBindings().getOperationBinding("findAndUpdateNoteInter");
                                    findUpdateNoteInter.getParamsMap().put("idNoteModeEnsInter", Long.parseLong(id_note_mode_inter.getInputValue().toString()));
                                    findUpdateNoteInter.getParamsMap().put("note", note);
                                    findUpdateNoteInter.execute();
                                    
                                } 
                                catch (Exception e) {
                                }   
                            }
                            executeOperation("Commit").execute();    //validation des données importées
                                    
                            //refresh la page apres import
                            FacesContext fc = FacesContext.getCurrentInstance();
                            String refreshpage = fc.getViewRoot().getViewId();
                            ViewHandler ViewH =
                            fc.getApplication().getViewHandler();
                            UIViewRoot UIV = ViewH.createView(fc,refreshpage);
                            UIV.setViewId(refreshpage);
                            fc.setViewRoot(UIV);
                        }
                    }
                //pas d'anonymat
                    else{
                        String anonymat = "";
                        Double note = null;
                        for (Integer row_current = premiere_ligne; row_current < derniere_ligne; row_current++) { 
                            int Index = 4;
                            //Iterate over row's columns
                            for (int column = 4; column < 8; column++) {
                                Cell MytempCell = sheet.getRow(row_current).getCell(column);
                                if (MytempCell != null) {
                                    Index = MytempCell.getColumnIndex();
                                } else {
                                    Index++;
                                }
                                if (Index == 4) {
                                    anonymat = MytempCell.getStringCellValue();                    
                                } 
                                else if (Index == 7) { 
                                    Integer ligne = row_current +1;
                                    try{
                                    if(!isCellEmpty(MytempCell)){       // remplir le tab d'erreur
                                            if((MytempCell.getNumericCellValue() > 20)||(MytempCell.getNumericCellValue() < 0) ){
                                                ligne_colonne.put(ligne.toString(),"Note doit etre comprise entre 0 et 20");
                                                tab_erreur.add(ligne_colonne);
                                            }
                                            else
                                                note = MytempCell.getNumericCellValue();
                                        }
                                        else
                                            note=null;
                        
                                    }catch(Exception e) {
                                        ligne_colonne.put(ligne.toString(),"La note doit etre un nombre");
                                        tab_erreur.add(ligne_colonne);
                                    }
                                }   
                            }
                        }
                        // verifier si tab d'erreur n'est pas vide
                        if(tab_erreur.size() > 0){
                            for (String current : tab_erreur.get(0).keySet()) {
                                FacesMessage msg = new FacesMessage("Ligne n° "+current+" et Colonne n° 8 :"+tab_erreur.get(0).get(current));
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                            getPopImport().hide();
                        }
                        // pas d'erreur dans le fichier d'excel
                        if(tab_erreur.size() == 0){
                            //for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                            String numero = "";
                            note = null;
                            for (Integer row_current = premiere_ligne; row_current < derniere_ligne; row_current++) { 
                                int Index = 4;
                                //Iterate over row's columns
                                for (int column = 4; column < 8; column++) {
                                    Cell MytempCell = sheet.getRow(row_current).getCell(column);
                                    if (MytempCell != null) {
                                        Index = MytempCell.getColumnIndex();
                                    } else {
                                        Index++;
                                    }
                                    if (Index == 4) {
                                        numero = MytempCell.getStringCellValue();                       
                                    } 
                                    else if (Index == 7) {                      
                                        if(!isCellEmpty(MytempCell))
                                            note=MytempCell.getNumericCellValue();
                                        else
                                            note=null;
                                    }   
                                }
                                try {               
                                    OperationBinding op_getAnonymat = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudiant");
                                    op_getAnonymat.getParamsMap().put("num_etud",  numero);
                                    op_getAnonymat.execute();
    
                                    //id_etudiant à partir du numéro
                                    
                                    AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant1");
                                    //id_etudiant de l'anonymat encrypté
    
                                    OperationBinding op_getIdNoteModeEns = getBindings().getOperationBinding("getIdNoteModeEnsInter");
                                    op_getIdNoteModeEns.getParamsMap().put("id_etud",Long.parseLong(id_etud.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_fil_ue_sem",  Long.parseLong(IdFiliereUeSemstre.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_type_ctrl",  Long.parseLong(idtype_controle.getInputValue().toString()));
                                    op_getIdNoteModeEns.getParamsMap().put("id_mode_ctrl_ec",  Long.parseLong(IdModeControleEc.getInputValue().toString()));
                                    op_getIdNoteModeEns.execute();
                                    
                                    //id_note_mode_enseignement_intermediaire
                                    AttributeBinding id_note_mode_inter = (AttributeBinding) getBindings().getControlBinding("IdNoteModeEnsInter");
                                    
                                    //pour faire la mise à jour du note recupérée dans le fichier Excel
                                    
                                    OperationBinding findUpdateNoteInter = getBindings().getOperationBinding("findAndUpdateNoteInter");
                                    findUpdateNoteInter.getParamsMap().put("idNoteModeEnsInter", Long.parseLong(id_note_mode_inter.getInputValue().toString()));
                                    findUpdateNoteInter.getParamsMap().put("note", note);
                                    findUpdateNoteInter.execute();
                                    
                                } 
                                catch (Exception e) {
                                }   
                            }
                            executeOperation("Commit").execute();    //validation des données importées
                                    
                            //refresh la page apres import
                            FacesContext fc = FacesContext.getCurrentInstance();
                            String refreshpage = fc.getViewRoot().getViewId();
                            ViewHandler ViewH =
                            fc.getApplication().getViewHandler();
                            UIViewRoot UIV = ViewH.createView(fc,refreshpage);
                            UIV.setViewId(refreshpage);
                            fc.setViewRoot(UIV);
                        }
                    }
                }  
            } //sheet
        }
    }

    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }


    @SuppressWarnings("unchecked")
    public void readNProcessExcel(InputStream xls) throws IOException {
        
        AttributeBinding idtype_controle = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding IdModeControleEc = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
              
        //Use HSSFWorkbook for XLS file
        HSSFWorkbook WorkBook = null;
    
        int sheetIndex = 0;

        try {
            WorkBook = new HSSFWorkbook(xls);
        } catch (IOException e) {
            System.out.println("Exception : " + e);
        }

        HSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);

        Integer skipRw = 1;
        Integer skipcnt = 1;
        //Iterate over excel rows
        for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
            String anonymat = "";
            Double note = null;
            if (skipcnt > skipRw) { //skip first n row for labels.
                //Create new row in table
                int Index = 0;           
                for (int column = 0; column < tempRow.getPhysicalNumberOfCells(); column++) {
                    Cell MytempCell = tempRow.getCell(column);
                    if (MytempCell != null) {
                        Index = MytempCell.getColumnIndex();
                    } 
                    else {
                        Index++;
                    }
                    if (Index == 0) {
                        anonymat = MytempCell.getStringCellValue();
                       
                    } 
                    else if (Index == 1) {
                            if(!isCellEmpty(MytempCell))
                                note = MytempCell.getNumericCellValue();
                            else
                                note = null;
                    }
                 }
                try {
                    key = key.substring(0, 16);
                    crypted = encrypt(anonymat,key);       //ré-encrypter l'anonymat recupéré
                    
                    OperationBinding op_getAnonymat = getBindings().getOperationBinding("getAnonymat");
                    op_getAnonymat.getParamsMap().put("anonymat_var",  crypted);
                    op_getAnonymat.execute();
                    
                    //id_etudiant de l'anonymat encrypté
                    AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");

                    OperationBinding op_getIdNoteModeEns = getBindings().getOperationBinding("getIdNoteModeEnsInter");
                    op_getIdNoteModeEns.getParamsMap().put("id_etud",Long.parseLong(id_etud.getInputValue().toString()));
                    op_getIdNoteModeEns.getParamsMap().put("id_fil_ue_sem",  Long.parseLong(IdFiliereUeSemstre.getInputValue().toString()));
                    op_getIdNoteModeEns.getParamsMap().put("id_type_ctrl",  Long.parseLong(idtype_controle.getInputValue().toString()));
                    op_getIdNoteModeEns.getParamsMap().put("id_mode_ctrl_ec",  Long.parseLong(IdModeControleEc.getInputValue().toString()));
                    op_getIdNoteModeEns.execute();
                    
                    //id_note_mode_enseignement_intermediaire
                    AttributeBinding id_note_mode_inter = (AttributeBinding) getBindings().getControlBinding("IdNoteModeEnsInter");
                    
                    //pour faire la mise à jour du note recupérée dans le fichier Excel
                    
                    OperationBinding findUpdateNoteInter = getBindings().getOperationBinding("findAndUpdateNoteInter");
                    findUpdateNoteInter.getParamsMap().put("idNoteModeEnsInter", Long.parseLong(id_note_mode_inter.getInputValue().toString()));
                    findUpdateNoteInter.getParamsMap().put("note", note);
                    findUpdateNoteInter.execute();
                    
                } 
                catch (Exception e) {
                }

            }

            skipcnt++;
        }
        executeOperation("Commit").execute();    //validation des données importées
        
        //refresh la page apres import
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH =
        fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);   
        
    }

    public static boolean isCellEmpty(final Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }

        if (cell.getCellType() == CellType.BLANK && cell.getStringCellValue().isEmpty()) {
            return true;
        }

        return false;
    }

    public void onDialogCanImport(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopImport().hide();
    }

    public void onDialogImport(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void onUploadExcel(ActionEvent actionEvent) throws IOException {
        // Add event code here...
        
        UploadedFile file =  uploadedFile;

        try {
            //Check if file is XLSX
            if (file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                file.getContentType().equalsIgnoreCase("application/xlsx")) {

                //readNProcessExcelx(file.getInputStream()); //for xlsx
                readNProcessExcelx1(file.getInputStream());
            
            }
            //Check if file is XLS
            else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                    if (file.getFilename().toUpperCase().endsWith(".XLS")) {
                        readNProcessExcel(file.getInputStream()); //for xls
                    }

            } else {
                FacesMessage msg = new FacesMessage("Fichier non supporté.--Charger un fichier .XLS ou .XLSX--");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            file.getInputStream().close();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableSaisieNotesInter);

        } catch (IOException e) {
            // TODO
            System.out.println(e);
            file.getInputStream().close();
        }
       finally{          
          file.getInputStream().close();
      }
        this.getPopImport().hide();
    }

    public void setFileUpload(RichInputFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public RichInputFile getFileUpload() {
        return fileUpload;
    }

    public void onValueChangeFile(ValueChangeEvent valueChangeEvent) {
       this.uploadedFile = (UploadedFile)valueChangeEvent.getNewValue();
    }

    public String encrypt(String data, String keys) throws Exception{
        keys = keys.substring(0, 16);
        keyvalue = keys.getBytes();
        Key key = new SecretKeySpec(keyvalue, ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    @SuppressWarnings("unchecked")
    public void exportToExcel(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        try{
            AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
            AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
            OperationBinding opExport = getBindings().getOperationBinding("getEtuSaisieNoteInter");
            opExport.getParamsMap().put("mde_cntrl_id", id_mode_ctrl_ec.getInputValue());
            opExport.getParamsMap().put("cal_id", getCal());
            opExport.getParamsMap().put("tcntrl_id", id_type_ctrl.getInputValue());

            opExport.execute();
        
        //DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterNewIterator");
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudiantSaisieNoteInter1Iterator");
                    
        //ViewObject vo=iter.getViewObject();
        //vo.executeQuery();
        //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        //Create  Workbook object
        XSSFWorkbook xwb = new XSSFWorkbook();//xwb.setWorkbookPassword("refonte", arg1);
        //Create Sheet in Workbook
        XSSFSheet sheet = xwb.createSheet("Liste des Etudiants");
        //Style de base
        CellStyle basestyle = xwb.createCellStyle();
        basestyle.setBorderBottom(BorderStyle.MEDIUM);
        basestyle.setBorderLeft(BorderStyle.MEDIUM);
        basestyle.setBorderRight(BorderStyle.MEDIUM);
        basestyle.setBorderTop(BorderStyle.MEDIUM);
        
        //No of total rows+ 1 for array sizing
        int totRows = ((int) iter.getEstimatedRowCount()) + 1;
        //Here 2 is the number of columns
        
        
        AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnnee");
        AttributeBinding libelle_ue = (AttributeBinding) getBindings().getControlBinding("UeEvalNew");
        AttributeBinding libelle_ec = (AttributeBinding) getBindings().getControlBinding("EcEvalNew");
        AttributeBinding libelle_type_ctrl = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrlNew");
        //AttributeBinding libelle_mode_ens = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrModeCntrllNew");
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        //AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
        //AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        AttributeBinding id_crt = (AttributeBinding) getBindings().getControlBinding("IdCohorte");
        AttributeBinding Lib_crt = (AttributeBinding) getBindings().getControlBinding("Libelle_crt");

            //le mot de passe annee,fil_ec,type_control,id_mode_ens,semestre,session
        String mot_passe = getAn()+""+id_fil_ec.getInputValue().toString()+""+id_type_ctrl.getInputValue().toString()+""+id_mode_ctrl_ec.getInputValue().toString()+""+getSem()+""+getSess()+""+getCal();
        sheet.protectSheet(mot_passe);      // mot de passe du fichier
        
        //fusion de colone
        //sheet.addMergedRegion(rowFrom, rowTo, colFrom, colTo);
        //sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
        /*sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 9));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 4, 9));*/
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 4, 5));
        String [][] entete = new String[9][2];
        entete[0][0] = "Niveau de Formation";
        entete[0][1] = lib_niv_form.getInputValue().toString();
        entete[1][0] = "Unité d'Enseignement";
        entete[1][1] = libelle_ue.getInputValue().toString();
        entete[2][0] = "Elément Constitutif";
        entete[2][1] = libelle_ec.getInputValue().toString();
        entete[3][0] = "Type de Controle";
        entete[3][1] = libelle_type_ctrl.getInputValue().toString();
        entete[4][0] = "Semestre";
        entete[4][1] = getSem();
        entete[5][0] = "Session";
        entete[5][1] = Integer.parseInt(getSess())  == 1 ? "NORMALE"  : "RATTRAPAGE";
        /*entete[6][0] = "Id FilUeSemEc";
        entete[6][1] = id_fil_ec.getInputValue().toString();
        entete[7][0] = "Id Type Ctrl";
        entete[7][1] = id_type_ctrl.getInputValue().toString();
        entete[8][0] = "Mode Enseignement :";
        entete[8][1] = libelle_mode_ens.getInputValue().toString()+"("+id_mode_ctrl_ec.getInputValue().toString()+")";
        */
        entete[6][0] = "Année Universitaire";
        entete[6][1] = lib_annee.getInputValue().toString();
        entete[7][0] = "Cohorte";
        entete[7][1] = Lib_crt.getInputValue().toString();
        /*if (1 != Integer.parseInt( id_crt.getInputValue().toString())){
            
        }*/
        int rowNum = 1;
        //Iterate over Object array for each row
        for (Object[] datatype : entete) {
            //Creating row in Excel Sheet
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            //Set data in column of a row
            int colNum = 4;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                CellStyle cellStyle = xwb.createCellStyle();
                
                // Style the cell with borders and border color
                CellStyle style = xwb.createCellStyle();
                style.setBorderBottom(BorderStyle.MEDIUM);
                style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderRight(BorderStyle.MEDIUM);
                style.setRightBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setTopBorderColor(IndexedColors.BLUE.getIndex());
                //cell.setCellStyle(style);
                // verrouillage des cellules d'entéte
                //cellStyle.setLocked(true); 
                cell.setCellValue("Veuillez mettre 'ABS' si l'étudiant est absent"); //ligne de séparation de l'entete et des donnees
                /*if( rowNum == 8)
                    cell.setCellValue("");*/
                cell.setCellStyle(cellStyle);
                //tailles des cellules de l'entete
                sheet.setColumnWidth(4, 8000); 
                sheet.setColumnWidth(5, 10000);
                sheet.setVerticallyCenter(true);
                if(colNum == 4 && rowNum == 1){
                    XSSFCellStyle style2 = xwb.createCellStyle(); 
                    style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                    // and solid fill pattern produces solid grey cell fill
                    style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                  
                    cell.setCellStyle(style2); 
                }                  
                row.setHeight((short) 450); //hauteur des lignes_colonne de l'entete
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                    cell.setCellStyle(style);
                } else if (field instanceof Float) {
                    cell.setCellValue((Float) field);
                } 
            }
        }
        
        OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
        isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong( getParcours()));
        isGenereAnonymat.getParamsMap().put("anne", Long.parseLong( getAn()));
        isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong( getSem()));
        isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong( getSess()));
        Integer id_anomymat = (Integer)isGenereAnonymat.execute();
        //pas d'anonymat
        if(id_anomymat == 0){           
            //Here 4 is the number of columns
            Object[][] content_not_ano = new Object[totRows][6];
            int column = 6;
            //Set header text in first row of table in PDF
            content_not_ano[0][0] = "Numéro";
            content_not_ano[0][1] = "Prénom";
            content_not_ano[0][2] = "Nom";
            content_not_ano[0][3] = "Date de Naissance";
            content_not_ano[0][4] = "Lieu de Naissance";
            content_not_ano[0][5] = "Note";

            int i = 1;
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                for (int j = 0; j < column; j++) {
                    if (j == 0 && nextRow.getAttribute("Numero") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Numero").toString();
                    }
                    if (j == 1 && nextRow.getAttribute("Prenom") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Prenom").toString();
                    }
                    if (j == 2 && nextRow.getAttribute("Nom") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Nom").toString();
                    }
                    if (j == 3 && nextRow.getAttribute("DateNaissance") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("DateNaissance").toString();
                        //java.util.Date utilDate = new java.util.Date(((Date)nextRow.getAttribute("DateNaissance")).getTime());
                        
                        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        //String date1 = dateFormat.format(utilDate);
                        //content_not_ano[i][j] = date1;
                    }
                    if (j == 4 && nextRow.getAttribute("LieuNaissance") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("LieuNaissance").toString();
                    }
                    if (j == 5) { // && nextRow.getAttribute("Note") != null
                        //content_not_ano[i][j] = Float.parseFloat(nextRow.getAttribute("Note").toString());
                        content_not_ano[i][j] = (nextRow.getAttribute("Note") != null ? Float.parseFloat(nextRow.getAttribute("Note").toString()) : (Integer.parseInt(nextRow.getAttribute("IsAbsent").toString()) != 0 ? "ABS" : ""));
                    }
                }
                i++;
            }
            //Close RowSetIterator
            rsi.closeRowSetIterator();
            //Set data in Excel Sheet from Object array
            int rowNum_not_ano = 10;
            //Iterate over Object array for each row
            for (Object[] datatype : content_not_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum_not_ano++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderTop(BorderStyle.MEDIUM);    
                    style.setBorderRight(BorderStyle.MEDIUM);  
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);  
                    
                    cellStyle.setBorderTop(BorderStyle.MEDIUM);
                    cellStyle.setBorderRight(BorderStyle.MEDIUM);
                    cellStyle.setBorderBottom(BorderStyle.MEDIUM);
                    cellStyle.setBorderLeft(BorderStyle.MEDIUM);
                    //cellStyle.setLocked(true);
                    //cell.setCellValue("Veuillez mettre 'ABS' si pas de note");//pour les cellules sans notes ni ABS
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(6, 3000);
                    sheet.setColumnWidth(7, 5000);
                    sheet.setColumnWidth(8, 8000);
                     sheet.setVerticallyCenter(true);
                    if(colNum == 4 && rowNum_not_ano == 9){
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                          
                        cell.setCellStyle(style2); 
                    }
                      
                    row.setHeight((short) 800);
                    if(colNum == 10){
                        if (field instanceof String) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setDataFormat(format.getFormat("00.00"));
                            style_cell.setBorderBottom(BorderStyle.MEDIUM);
                            style_cell.setBorderLeft(BorderStyle.MEDIUM);
                            style_cell.setBorderRight(BorderStyle.MEDIUM);
                            style_cell.setBorderTop(BorderStyle.MEDIUM); 
                            style_cell.setLocked(false);
                            cell.setCellStyle(style_cell);
                            
                        } else if (field instanceof Float) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setBorderBottom(BorderStyle.MEDIUM);
                            style_cell.setBorderLeft(BorderStyle.MEDIUM);
                            style_cell.setBorderRight(BorderStyle.MEDIUM);
                            style_cell.setBorderTop(BorderStyle.MEDIUM); 
                            style_cell.setLocked(false);
                            cell.setCellValue("");
                            style_cell.setDataFormat(format.getFormat("00.00"));
                            cell.setCellStyle(style_cell);
                        } 
                        
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    }
                }
            } 
        }
        //anonymat
        else{
            Object[][] content_ano = new Object[totRows][2];
            int column_ano = 2;
            //Set header text in first row of table in PDF
            content_ano[0][0] = "Anonymat";
            content_ano[0][1] = "Note";
            
            int i = 1;
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                for (int j = 0; j < column_ano; j++) {
                    if (j == 0 && nextRow.getAttribute("Anonymat") != null) {
                        content_ano[i][j] = nextRow.getAttribute("Anonymat").toString();
                    }
                    if (j == 1 && nextRow.getAttribute("Note") != null) {
                        content_ano[i][j] = Float.parseFloat(nextRow.getAttribute("Note").toString());
                    }
                }
                i++;
            }
            rsi.closeRowSetIterator();
            rowNum = 12;
            //Iterate over Object array for each row
            for (Object[] datatype1 : content_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype1) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.MEDIUM);
                    style.setBorderTop(BorderStyle.MEDIUM); 
                    
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(4, 5000);
                    sheet.setColumnWidth(5, 3000);
                    sheet.setVerticallyCenter(true); 
                    
                    if(colNum == 4 && rowNum == 9){
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                      
                        cell.setCellStyle(style2); 
                        sheet.autoSizeColumn(2);
                    }                  
                    row.setHeight((short) 800);
                    // deverouiller la colunne 2 (note)
                    if(colNum == 6){
                        DataFormat format = xwb.createDataFormat();
                        CellStyle style_cell = xwb.createCellStyle();
                        style_cell.setBorderBottom(BorderStyle.MEDIUM);
                        style_cell.setBorderLeft(BorderStyle.MEDIUM);
                        style_cell.setBorderRight(BorderStyle.MEDIUM);
                        style_cell.setBorderTop(BorderStyle.MEDIUM); 
                        
                        style_cell.setLocked(false);
                        cell.setCellValue("");
                        style_cell.setDataFormat(format.getFormat("00.00"));
                        cell.setCellStyle(style_cell);
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    } 
                }
            }
        }   
        facesContext.responseComplete();
        try {
            xwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            facesContext.responseComplete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        FacesContext fc = facesContext;
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
        xwb.close();
            
        }catch(Exception e){
            System.out.println(e);
        }
        
    }


    @SuppressWarnings("unchecked")
    public void exportToExcel1(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        
        try{
        //DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterVO1Iterator");
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterNewIterator");
        //ViewObject vo=iter.getViewObject();
        //vo.executeQuery();
        //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        //Creation du fichier excel
        XSSFWorkbook xwb = new XSSFWorkbook();//xwb.setWorkbookPassword("refonte", arg1);
        //Creation d'une feuille excel
        XSSFSheet sheet = xwb.createSheet("Liste des Etudiants");
        
        //No of total rows+ 1 for array sizing
        int totRows = ((int) iter.getEstimatedRowCount()) + 1;
        //Here 2 is the number of columns
        
        AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnnee");
        AttributeBinding libelle_ue = (AttributeBinding) getBindings().getControlBinding("UeEvalNew");
        AttributeBinding libelle_ec = (AttributeBinding) getBindings().getControlBinding("EcEvalNew");
        AttributeBinding libelle_type_ctrl = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrlNew");
        AttributeBinding libelle_mode_ens = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCntrModeCntrllNew");
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
        AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        //le mot de passe annee,fil_ec,type_control,id_mode_ens,semestre,session
        String mot_passe = getAn()+""+id_fil_ec.getInputValue().toString()+""+id_type_ctrl.getInputValue().toString()+""+id_mode_ctrl_ec.getInputValue().toString()+""+getSem()+""+getSess()+""+getCal();
        sheet.protectSheet(mot_passe);      // mot de passe du fichier
        String [][] entete = new String[8][2];
        entete[0][0] = "Niveau de Formation";
        entete[0][1] = lib_niv_form.getInputValue().toString();
        entete[1][0] = "Unité d'Enseignement";
        entete[1][1] = libelle_ue.getInputValue().toString();
        entete[2][0] = "Elément Constitutif";
        entete[2][1] = libelle_ec.getInputValue().toString();
        entete[3][0] = "Type de Controle";
        entete[3][1] = libelle_type_ctrl.getInputValue().toString();
        entete[4][0] = "Semestre";
        entete[4][1] = getSem();
        entete[5][0] = "Session";
        entete[5][1] = Integer.parseInt(getSess())  == 1 ? "NORMAL"  : "RATTRAPAGE";
        /*entete[6][0] = "Id FilUeSemEc";
        entete[6][1] = id_fil_ec.getInputValue().toString();
        entete[7][0] = "Id Type Ctrl";
        entete[7][1] = id_type_ctrl.getInputValue().toString();
        entete[8][0] = "Mode Enseignement :";
        entete[8][1] = libelle_mode_ens.getInputValue().toString()+"("+id_mode_ctrl_ec.getInputValue().toString()+")";
        */
        entete[6][0] = "Année Universitaire";
        entete[6][1] = lib_annee.getInputValue().toString();
            
        int rowNum = 1;
        //Iterate over Object array for each row
        //Stylisation de l'entête
        for (Object[] datatype : entete) {
            //Creating row in Excel Sheet
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            //Set data in column of a row
            int colNum = 4;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                CellStyle cellStyle = xwb.createCellStyle();
                // Style the cell with borders and border color
                CellStyle style = xwb.createCellStyle();
                style.setBorderBottom(BorderStyle.MEDIUM);
                style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderRight(BorderStyle.MEDIUM);
                style.setRightBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setTopBorderColor(IndexedColors.BLUE.getIndex());
                //cell.setCellStyle(style);
                // verrouillage des cellules d'entéte
                //cellStyle.setLocked(true);          
                cell.setCellValue(""); //ligne de séparation de l'entete et des donnees
                cell.setCellStyle(cellStyle);
                //tailles des cellules de l'entete
                sheet.setColumnWidth(4, 8000); 
                sheet.setColumnWidth(5, 10000);
                sheet.setVerticallyCenter(true);
                if(colNum == 4 && rowNum == 1){
                    cell.setCellValue("ab"); 
                    XSSFCellStyle style2 = xwb.createCellStyle(); 
                    style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                    // and solid fill pattern produces solid grey cell fill
                    style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                  
                    cell.setCellStyle(style2); 
                }                  
                row.setHeight((short) 400); //hauteur des lignes_colonne de l'entete
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                    cell.setCellStyle(style);
                } else if (field instanceof Float) {
                    cell.setCellValue((Float) field);
                } 
            }
        }
        //end stylisation entête
        //Verification de l'anonymat    
        OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
        isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong( getParcours()));
        isGenereAnonymat.getParamsMap().put("anne", Long.parseLong( getAn()));
        isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong( getSem()));
        isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong( getSess()));
        Integer id_anomymat = (Integer)isGenereAnonymat.execute();
        //pas d'anonymat
        if(id_anomymat == 0){           
            //Here 4 is the number of columns
            Object[][] content_not_ano = new Object[totRows][6];
            int column = 6;
            //Set header text in first row of table in PDF
            content_not_ano[0][0] = "Numéro";
            content_not_ano[0][1] = "Prénom";
            content_not_ano[0][2] = "Nom";
            content_not_ano[0][3] = "Date de Naissance";
            content_not_ano[0][4] = "Lieu de Naissance";
            content_not_ano[0][5] = "Note";

            int i = 1;
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                for (int j = 0; j < column; j++) {
                    if (j == 0 && nextRow.getAttribute("Numero") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Numero").toString();
                    }
                    if (j == 1 && nextRow.getAttribute("Prenom") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Prenom").toString();
                    }
                    if (j == 2 && nextRow.getAttribute("Nom") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("Nom").toString();
                    }
                    if (j == 3 && nextRow.getAttribute("DateNaissance") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("DateNaissance").toString();
                        //java.util.Date utilDate = new java.util.Date(((Date)nextRow.getAttribute("DateNaissance")).getTime());
                        
                        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        //String date1 = dateFormat.format(utilDate);
                        //content_not_ano[i][j] = date1;
                    }
                    if (j == 4 && nextRow.getAttribute("LieuNaissance") != null) {
                        content_not_ano[i][j] = nextRow.getAttribute("LieuNaissance").toString();
                    }
                    if (j == 5) {
                        content_not_ano[i][j] = (nextRow.getAttribute("Note") != null ?  Float.parseFloat(nextRow.getAttribute("Note").toString()) : (Integer.parseInt(nextRow.getAttribute("IsAbsent").toString()) == 1 ? "ABS" : null));
                    }
                }
                i++;
            }
            //Close RowSetIterator
            rsi.closeRowSetIterator();
            //Set data in Excel Sheet from Object array
            int rowNum_not_ano = 10;
            //Iterate over Object array for each row
            for (Object[] datatype : content_not_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum_not_ano++);
                //ajouter les données pour chaque colonne de la ligne
                int colNum = 4; //nombre de colonne a sauter oubien la premiere colonne a partir de laquelle on doit ecrire
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderTop(BorderStyle.MEDIUM);    
                    style.setBorderRight(BorderStyle.MEDIUM);  
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);            
                    
                    //cellStyle.setLocked(true);
                    cell.setCellValue("");//pour les cellules sans notes ni ABS
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(6, 3000);
                    sheet.setColumnWidth(7, 5000);
                    sheet.setColumnWidth(8, 8000);
                     sheet.setVerticallyCenter(true);
                    if(colNum == 5 && rowNum_not_ano == 9){//4
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                          
                        cell.setCellStyle(style2); 
                    }
                      
                    row.setHeight((short) 600);
                    if(colNum == 10){
                        if (field instanceof String) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setBorderBottom(BorderStyle.DOTTED);
                            style_cell.setBorderLeft(BorderStyle.DOTTED);
                            style_cell.setBorderRight(BorderStyle.DOTTED);
                            style_cell.setBorderTop(BorderStyle.DOTTED); 
                            style_cell.setLocked(false);
                            cell.setCellStyle(style_cell);
                            
                        } else if (field instanceof Float) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setBorderBottom(BorderStyle.DOTTED);
                            style_cell.setBorderLeft(BorderStyle.DOTTED);
                            style_cell.setBorderRight(BorderStyle.DOTTED);
                            style_cell.setBorderTop(BorderStyle.DOTTED); 
                            style_cell.setLocked(false);
                            cell.setCellValue("");
                            style_cell.setDataFormat(format.getFormat("00.00"));
                            cell.setCellStyle(style_cell);
                        } 
                        
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    } 
                }
            } 
        }
        //anonymat
        else{
            Object[][] content_ano = new Object[totRows][2];
            int column_ano = 2;
            //Set header text in first row of table in PDF
            content_ano[0][0] = "Anonymat";
            content_ano[0][1] = "Note";
            
            int i = 1;
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                for (int j = 0; j < column_ano; j++) {
                    if (j == 0 && nextRow.getAttribute("Anonymat") != null) {
                        content_ano[i][j] = nextRow.getAttribute("Anonymat").toString();
                    }
                    if (j == 1 && nextRow.getAttribute("Note") != null) {
                        content_ano[i][j] = Float.parseFloat(nextRow.getAttribute("Note").toString());
                    }
                }
                i++;
            }
            rsi.closeRowSetIterator();
            rowNum = 12;
            //Iterate over Object array for each row
            for (Object[] datatype1 : content_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype1) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.MEDIUM);
                    style.setBorderTop(BorderStyle.MEDIUM); 
                    
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(4, 5000);
                    sheet.setColumnWidth(5, 3000);
                    sheet.setVerticallyCenter(true); 
                    
                    if(colNum == 4 && rowNum == 9){
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                      
                        cell.setCellStyle(style2); 
                        sheet.autoSizeColumn(2);
                    }                  
                    row.setHeight((short) 800);
                    // deverouiller la colunne 2 (note)
                    if(colNum == 6){
                        if (field instanceof String) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setBorderBottom(BorderStyle.MEDIUM);
                            style_cell.setBorderLeft(BorderStyle.MEDIUM);
                            style_cell.setBorderRight(BorderStyle.MEDIUM);
                            style_cell.setBorderTop(BorderStyle.MEDIUM); 
                            
                            style_cell.setLocked(false);
                            cell.setCellStyle(style_cell);
                        } else if (field instanceof Float) {
                            DataFormat format = xwb.createDataFormat();
                            CellStyle style_cell = xwb.createCellStyle();
                            style_cell.setBorderBottom(BorderStyle.MEDIUM);
                            style_cell.setBorderLeft(BorderStyle.MEDIUM);
                            style_cell.setBorderRight(BorderStyle.MEDIUM);
                            style_cell.setBorderTop(BorderStyle.MEDIUM); 
                            
                            style_cell.setLocked(false);
                            cell.setCellValue("");
                            style_cell.setDataFormat(format.getFormat("00.00"));
                            cell.setCellStyle(style_cell);
                        } 
                        DataFormat format = xwb.createDataFormat();
                        CellStyle style_cell = xwb.createCellStyle();
                        style_cell.setBorderBottom(BorderStyle.MEDIUM);
                        style_cell.setBorderLeft(BorderStyle.MEDIUM);
                        style_cell.setBorderRight(BorderStyle.MEDIUM);
                        style_cell.setBorderTop(BorderStyle.MEDIUM); 
                        
                        style_cell.setLocked(false);
                        cell.setCellValue("");
                        style_cell.setDataFormat(format.getFormat("00.00"));
                        cell.setCellStyle(style_cell);
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    } 
                }
            }
        }   
        facesContext.responseComplete();
        try {
            xwb.write(outputStream);
            outputStream.flush();
            outputStream.close();facesContext.responseComplete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        FacesContext fc = facesContext;
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
        xwb.close();
            
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    public void setPanelbtnwindow(RichPanelWindow panelbtnwindow) {
        this.panelbtnwindow = panelbtnwindow;
    }

    public RichPanelWindow getPanelbtnwindow() {
        return panelbtnwindow;
    }

    public void setPanelbtngroupe(RichPanelGroupLayout panelbtngroupe) {
        this.panelbtngroupe = panelbtngroupe;
    }

    public RichPanelGroupLayout getPanelbtngroupe() {
        return panelbtngroupe;
    }

    public void setPopupConfirmOpenInter(RichPopup popupConfirmOpenInter) {
        this.popupConfirmOpenInter = popupConfirmOpenInter;
    }

    public RichPopup getPopupConfirmOpenInter() {
        return popupConfirmOpenInter;
    }

    @SuppressWarnings("unchecked")
    public void OnReopenInterAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        DCIteratorBinding iterUe = (DCIteratorBinding) getBindings().get("UeEvalNewIterator");
        Row currentUeRow = iterUe.getCurrentRow();
        DCIteratorBinding iterEc = (DCIteratorBinding) getBindings().get("EcEvalNewIterator");
        Row currentEcRow = iterEc.getCurrentRow();
        DCIteratorBinding iterTc = (DCIteratorBinding) getBindings().get("EcEvalTypeCntrlNewIterator");
        Row currentTcRow = iterTc.getCurrentRow();
        DCIteratorBinding iterMc = (DCIteratorBinding) getBindings().get("EcEvalTypeCntrModeCntrllNewIterator");
        Row currentMcRow = iterMc.getCurrentRow();
        this.getPopupConfirmOpenInter().hide();
        if (outcome == Outcome.yes) {
            OperationBinding opopen = getBindings().getOperationBinding("reouvrirFilEc");
            opopen.getParamsMap()
                .put("fil_ue", Long.parseLong(currentUeRow.getAttribute("IdFiliereUeSemstre").toString()));
            opopen.getParamsMap()
                .put("fil_sem_ec", Long.parseLong(currentEcRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            opopen.getParamsMap()
                .put("type_control", Long.parseLong(currentTcRow.getAttribute("IdTypeControle").toString()));
            opopen.getParamsMap().put("calendrier", getCal());
            opopen.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            opopen.getParamsMap().put("prcrs_maq", getParcours_maq());
            Integer result = (Integer) opopen.execute();
            //System.out.println("result : "+result);
            if (-1 != result) {
                try {
                    OperationBinding openInter = getBindings().getOperationBinding("reouvrirInter");
                    openInter.getParamsMap().put("fil_sem_ec", currentEcRow.getAttribute("IdFiliereUeSemstreEc"));
                    //Long.parseLong(.toString())
                    openInter.getParamsMap().put("type_control", currentTcRow.getAttribute("IdTypeControle"));
                    openInter.getParamsMap().put("mode_control", currentMcRow.getAttribute("IdModeControleEc"));
                    openInter.getParamsMap().put("calendrier", getCal());
                    openInter.getParamsMap().put("utilisateur", getUtilisateur());
                    Integer result1 = (Integer) openInter.execute();
                    DCIteratorBinding iter =
                        (DCIteratorBinding) getBindings().get("EcEvalTypeCntrModeCntrllNewIterator");
                    ViewObject vo = iter.getViewObject();
                    vo.executeQuery();
                    //System.out.println("result1 : "+result1);
                    if (-1 == result1) {
                        RichPopup popup = this.getPopupEcClosed();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtngroupe());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());
                
            } else {
                RichPopup pop = this.getPopUeCloseed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                pop.show(hints);
            }
            }
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setPopupSuccessOpen(RichPopup popupSuccessOpen) {
        this.popupSuccessOpen = popupSuccessOpen;
    }

    public RichPopup getPopupSuccessOpen() {
        return popupSuccessOpen;
    }

    public void setPopEcClosed(RichPopup popEcClosed) {
        this.popEcClosed = popEcClosed;
    }

    public RichPopup getPopEcClosed() {
        return popEcClosed;
    }

    public void setPopNotStudent(RichPopup popNotStudent) {
        this.popNotStudent = popNotStudent;
    }

    public RichPopup getPopNotStudent() {
        return popNotStudent;
    }

    public void setPopSaisieSaved(RichPopup popSaisieSaved) {
        this.popSaisieSaved = popSaisieSaved;
    }

    public RichPopup getPopSaisieSaved() {
        return popSaisieSaved;
    }

    @SuppressWarnings("unchecked")
    public void onNoteInterChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        sessionFlowScope.put("is_saisie_refrechable", 0);
        AttributeBinding uti = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        uti.setInputValue(getUtilisateur());
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void export(FacesContext facesContext, OutputStream outputStream) throws IOException {
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

    public void onExportClicked(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudiantSaisieNoteInter1Iterator");
        ViewObject vo=iter.getViewObject();
        vo.executeQuery();
    }
    

    public void onExcelUploadAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.unchecked-conversion-or-cast" })
    public void duplicateNoteAction(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding type_cntrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding prcrs_maq = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetteAnnee");
        
        OperationBinding opNbreControle = getBindings().getOperationBinding("getNbreControle");
        opNbreControle.getParamsMap().put("fil_ec", fil_ec.getInputValue());
        opNbreControle.getParamsMap().put("type_cntrl", type_cntrl.getInputValue());
        opNbreControle.getParamsMap().put("prcrs_maq", prcrs_maq.getInputValue());
        Integer nbre = (Integer)opNbreControle.execute();
        if(1 != nbre){
            if(1 < nbre){
                sessionFlowScope.put("error_message_duplicat", "Impossible de dupliquer : Beaucoups de modes de controles paramètrées");
            }else{
                sessionFlowScope.put("error_message_duplicat", "Impossible de dupliquer : Pas d'autre mode de controle paramètrée");
            }
            RichPopup popup = this.getPopManyCntrlType();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }else{
            try{
                OperationBinding opduplicated = getBindings().getOperationBinding("DupliquerNoteSess2");
                opduplicated.getParamsMap().put("calendrier", getCal());
                opduplicated.getParamsMap().put("fil_ec", fil_ec.getInputValue());
                opduplicated.getParamsMap().put("prcrs_mq", prcrs_maq.getInputValue());
                opduplicated.getParamsMap().put("utilisateur", getUtilisateur());
                opduplicated.execute();
            RichPopup popup = this.getPopNoteDuplicate();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void setPopNoteDuplicate(RichPopup popNoteDuplicate) {
        this.popNoteDuplicate = popNoteDuplicate;
    }

    public RichPopup getPopNoteDuplicate() {
        return popNoteDuplicate;
    }

    public void setPopManyCntrlType(RichPopup popManyCntrlType) {
        this.popManyCntrlType = popManyCntrlType;
    }

    public RichPopup getPopManyCntrlType() {
        return popManyCntrlType;
    }

    public void exportto(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        
    }

    public void setPopupEcClosed(RichPopup popupEcClosed) {
        this.popupEcClosed = popupEcClosed;
    }

    public RichPopup getPopupEcClosed() {
        return popupEcClosed;
    }

    @SuppressWarnings("unchecked")
    public void OnLoadAncAction(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding fil_ue = (AttributeBinding) getBindings().getControlBinding("Codification");
        AttributeBinding type_cntrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding ecAnc = (AttributeBinding) getBindings().getControlBinding("AncienCode");
        
        OperationBinding opload = getBindings().getOperationBinding("ChargerNoteAnc");
        opload.getParamsMap().put("calendrier", getCal());
        opload.getParamsMap().put("parcours", getParcours_maq());
        //opload.getParamsMap().put("id_annee", Long.parseLong(getAn()));
        opload.getParamsMap().put("codifUe", fil_ue.getInputValue());
        opload.getParamsMap().put("code_ec", ecAnc.getInputValue());
        opload.getParamsMap().put("sess", Long.parseLong(getSess()));
        opload.getParamsMap().put("type_cntrle", type_cntrl.getInputValue());
        opload.getParamsMap().put("utilisateur", getUtilisateur());
        opload.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtngroupe());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());
        
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH =  fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void onRefreshAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setParcours_maq(Long parcours_maq) {
        this.parcours_maq = parcours_maq;
    }

    public Long getParcours_maq() {
        return parcours_maq;
    }

    @SuppressWarnings("unchecked")
    public void exportToExcelBis(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        try{
         //Create  Workbook object
        XSSFWorkbook xwb = new XSSFWorkbook();//xwb.setWorkbookPassword("refonte", arg1);
        //Create Sheet in Workbook
        XSSFSheet sheet = xwb.createSheet("Liste des Etudiants");
        
        //No of total rows+ 1 for array sizing
        int totRows = 4500;
        //Here 2 is the number of columns
        
        AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnnee");
        AttributeBinding libelle_ue = (AttributeBinding) getBindings().getControlBinding("UeEvalRO");
        AttributeBinding libelle_ec = (AttributeBinding) getBindings().getControlBinding("EcEvalRO");
        AttributeBinding libelle_type_ctrl = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCtrlRO");
        AttributeBinding libelle_mode_ens = (AttributeBinding) getBindings().getControlBinding("EcEvalTypeCtrlModeCtrlRO");
        
        AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
        AttributeBinding id_type_ctrl = (AttributeBinding) getBindings().getControlBinding("IdTypeControle");
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
        AttributeBinding id_mode_ctrl_ec = (AttributeBinding) getBindings().getControlBinding("IdModeControleEc2");
        //le mot de passe annee,fil_ec,type_control,id_mode_ens,semestre,session
        String mot_passe = getAn()+""+id_fil_ec.getInputValue().toString()+""+id_type_ctrl.getInputValue().toString()+""+id_mode_ctrl_ec.getInputValue().toString()+""+getSem()+""+getSess()+""+getCal();
        sheet.protectSheet(mot_passe);      // mot de passe du fichier
        String [][] entete = new String[8][2];
        entete[0][0] = "Niveau de Formation";
        entete[0][1] = lib_niv_form.getInputValue().toString();
        entete[1][0] = "Unité d'Enseignement";
        entete[1][1] = libelle_ue.getInputValue().toString();
        entete[2][0] = "Elément Constitutif";
        entete[2][1] = libelle_ec.getInputValue().toString();
        entete[3][0] = "Type de Controle";
        entete[3][1] = libelle_type_ctrl.getInputValue().toString();
        entete[4][0] = "Semestre";
        entete[4][1] = getSem();
        entete[5][0] = "Session";
        entete[5][1] = Integer.parseInt(getSess())  == 1 ? "Normale"  : "Rattrapage";
        /*entete[6][0] = "Id FilUeSemEc";
        entete[6][1] = id_fil_ec.getInputValue().toString();
        entete[7][0] = "Id Type Ctrl";
        entete[7][1] = id_type_ctrl.getInputValue().toString();
        entete[8][0] = "Mode Enseignement :";
        entete[8][1] = libelle_mode_ens.getInputValue().toString()+"("+id_mode_ctrl_ec.getInputValue().toString()+")";
        */
        entete[6][0] = "Année Universitaire";
        entete[6][1] = lib_annee.getInputValue().toString();
            
        int rowNum = 1;
        //Iterate over Object array for each row
        for (Object[] datatype : entete) {
            //Creating row in Excel Sheet
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            //Set data in column of a row
            int colNum = 4;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                CellStyle cellStyle = xwb.createCellStyle();
                
                // Style the cell with borders and border color
                CellStyle style = xwb.createCellStyle();
                style.setBorderBottom(BorderStyle.MEDIUM);
                style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderRight(BorderStyle.MEDIUM);
                style.setRightBorderColor(IndexedColors.BLUE.getIndex());
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setTopBorderColor(IndexedColors.BLUE.getIndex());
                //cell.setCellStyle(style);
                
                cellStyle.setLocked(true);          // verrouillage des cellules
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(4, 8000);
                sheet.setColumnWidth(5, 6000);
                sheet.setVerticallyCenter(true);
                if(colNum == 4 && rowNum == 1){
                    XSSFCellStyle style2 = xwb.createCellStyle(); 
                    style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                    // and solid fill pattern produces solid grey cell fill
                    style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                  
                    cell.setCellStyle(style2); 
                }                  
                row.setHeight((short) 400);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                    cell.setCellStyle(style);
                } else if (field instanceof Float) {
                    cell.setCellValue((Float) field);
                } 
            }
        }
        
        OperationBinding isGenereAnonymat = getBindings().getOperationBinding("isGenereAnonymat");
        isGenereAnonymat.getParamsMap().put("parcours", Long.parseLong( getParcours()));
        isGenereAnonymat.getParamsMap().put("anne", Long.parseLong( getAn()));
        isGenereAnonymat.getParamsMap().put("semestre", Long.parseLong( getSem()));
        isGenereAnonymat.getParamsMap().put("sessions", Long.parseLong( getSess()));
        Integer id_anomymat = (Integer)isGenereAnonymat.execute();
        //pas d'anonymat
        if(id_anomymat == 0){           
            //Here 4 is the number of columns
            Object[][] content_not_ano = new Object[totRows][6];
            int column = 6;
            //Set header text in first row of table in PDF
            content_not_ano[0][0] = "Numéro";
            content_not_ano[0][1] = "Prénom";
            content_not_ano[0][2] = "Nom";
            content_not_ano[0][3] = "Date de Naissance";
            content_not_ano[0][4] = "Lieu de Naissance";
            content_not_ano[0][5] = "Note";

            int i = 1;
            /*while (i < 5000) {
                for (int j = 0; j < column; j++) {
                    if (j == 0 ) {
                        content_not_ano[i][j] = null;
                    }
                    if (j == 1 ) {
                        content_not_ano[i][j] = null;
                    }
                    if (j == 2 ) {
                        content_not_ano[i][j] = null;
                    }
                    if (j == 3 ) {
                        content_not_ano[i][j] = null;
                        //java.util.Date utilDate = new java.util.Date(((Date)nextRow.getAttribute("DateNaissance")).getTime());
                        
                        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        //String date1 = dateFormat.format(utilDate);
                        //content_not_ano[i][j] = date1;
                    }
                    if (j == 4 ) {
                        content_not_ano[i][j] = null;
                    }
                    if (j == 5 ) {
                        content_not_ano[i][j] = null;
                    }
                }
                i++;
            }*/
            //Set data in Excel Sheet from Object array
            int rowNum_not_ano = 10;
            //Iterate over Object array for each row
            for (Object[] datatype : content_not_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum_not_ano++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.MEDIUM);
                    style.setBorderTop(BorderStyle.MEDIUM);
                    
                    cellStyle.setLocked(false);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(5, 10000);
                    sheet.setColumnWidth(6, 5000);
                    sheet.setColumnWidth(7, 5000);
                    sheet.setColumnWidth(8, 5000);
                     sheet.setVerticallyCenter(true);
                    if(colNum == 4 && rowNum_not_ano == 9){
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                          
                        cell.setCellStyle(style2); 
                    }
                      
                    row.setHeight((short) 800);
                    if(colNum == 10){
                        DataFormat format = xwb.createDataFormat();
                        CellStyle style_cell = xwb.createCellStyle();
                        /*style_cell.setBorderBottom(BorderStyle.MEDIUM);
                        style_cell.setBorderLeft(BorderStyle.MEDIUM);
                        style_cell.setBorderRight(BorderStyle.MEDIUM);
                        style_cell.setBorderTop(BorderStyle.MEDIUM);*/ 
                        cell.setCellValue("");
                        style_cell.setLocked(false);
                        style_cell.setDataFormat(format.getFormat("00.00"));
                        cell.setCellStyle(style_cell);
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    } 
                }
            } 
        }
        //anonymat
        /*else{
            Object[][] content_ano = new Object[totRows][2];
            int column_ano = 2;
            //Set header text in first row of table in PDF
            content_ano[0][0] = "Anonymat";
            content_ano[0][1] = "Note";
            
            int i = 1;
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                for (int j = 0; j < column_ano; j++) {
                    if (j == 0 && nextRow.getAttribute("Anonymat") != null) {
                        content_ano[i][j] = nextRow.getAttribute("Anonymat").toString();
                    }
                    if (j == 1 && nextRow.getAttribute("Note") != null) {
                        content_ano[i][j] = Float.parseFloat(nextRow.getAttribute("Note").toString());
                    }
                }
                i++;
            }
            rsi.closeRowSetIterator();
            rowNum = 12;
            //Iterate over Object array for each row
            for (Object[] datatype1 : content_ano) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype1) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.MEDIUM);
                    style.setBorderTop(BorderStyle.MEDIUM); 
                    
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(4, 5000);
                    sheet.setColumnWidth(5, 3000);
                    sheet.setVerticallyCenter(true); 
                    
                    if(colNum == 4 && rowNum == 9){
                        XSSFCellStyle style2 = xwb.createCellStyle(); 
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);          
                      
                        cell.setCellStyle(style2); 
                        sheet.autoSizeColumn(2);
                    }                  
                    row.setHeight((short) 800);
                    // deverouiller la colunne 2 (note)
                    if(colNum == 6){
                        DataFormat format = xwb.createDataFormat();
                        CellStyle style_cell = xwb.createCellStyle();
                        style_cell.setBorderBottom(BorderStyle.MEDIUM);
                        style_cell.setBorderLeft(BorderStyle.MEDIUM);
                        style_cell.setBorderRight(BorderStyle.MEDIUM);
                        style_cell.setBorderTop(BorderStyle.MEDIUM); 
                        
                        style_cell.setLocked(false);
                        cell.setCellValue("");
                        style_cell.setDataFormat(format.getFormat("00.00"));
                        cell.setCellStyle(style_cell);
                    }
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    } 
                }
            }
        }   */
        facesContext.responseComplete();
        try {
            xwb.write(outputStream);
            outputStream.flush();
            outputStream.close();facesContext.responseComplete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        FacesContext fc = facesContext;
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc,refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
        xwb.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void setPop_hb(RichPopup pop_hb) {
        this.pop_hb = pop_hb;
    }

    public RichPopup getPop_hb() {
        return pop_hb;
    }


    public boolean refreshNeed(){
        return true;
    }

    public void onIsAbsentChanged(ValueChangeEvent valueChangeEvent) {
        BindingContext bindingContext = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator"); 
        Row currentR = iter.getCurrentRow();
        if (null != currentR){
            if(null != currentR.getAttribute("Note")){
                currentR.setAttribute("Style", null);
                if(valueChangeEvent.getNewValue().equals(true)){
                    currentR.setAttribute("IdTypeVerrou", 3);
                }else{
                    currentR.setAttribute("IdTypeVerrou", 1);
                }
            }else{
                //Si la note est vide et que le champs est décoché
                if(valueChangeEvent.getNewValue().equals(false)){
                    currentR.setAttribute("Style", "border-style: solid; border-color: #c00; border-width: 2px;");
                }else{
                    currentR.setAttribute("Style", null);
                }
            }
        }
    }

    public void setNoteVal(RichInputText noteVal) {
        this.noteVal = noteVal;
    }

    public RichInputText getNoteVal() {
        return noteVal;
    }

    public void onbtn1Test(ActionEvent actionEvent) {
        // Add event code here...
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iterator = bindings.findIteratorBinding("EtudiantSaisieNoteInterNewROVOIterator");

            // Récupérer la ligne sélectionnée
            Row selectedRow = iterator.getCurrentRow();
            if (selectedRow != null) {
                // Récupérer la valeur de "Note" depuis la vue modifiable
                Object noteValue = selectedRow.getAttribute("NotesModeEnseignementInterNewVO1.Note");
                
            }
    }

    public void setBrds(String brds) {
        this.brds = brds;
    }

    public String getBrds() {
        return brds;
    }

    public void setBrdc(String brdc) {
        this.brdc = brdc;
    }

    public String getBrdc() {
        return brdc;
    }

    public void setBrdw(String brdw) {
        this.brdw = brdw;
    }

    public String getBrdw() {
        return brdw;
    }

    public void setPopupErrCheck(RichPopup popupErrCheck) {
        this.popupErrCheck = popupErrCheck;
    }

    public RichPopup getPopupErrCheck() {
        return popupErrCheck;
    }

    public void onNoteChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator"); 
        Row currentR = iter.getCurrentRow();
            if (null != currentR){
                if(valueChangeEvent.getNewValue()== null){
                    currentR.setAttribute("Style", "border-style: solid; border-color: #c00; border-width: 2px;");
                }else{
                    currentR.setAttribute("Style", null);
                }
                
            }
    }

    public void setPanColSaisieNote(RichPanelCollection panColSaisieNote) {
        this.panColSaisieNote = panColSaisieNote;
    }

    public RichPanelCollection getPanColSaisieNote() {
        return panColSaisieNote;
    }

    public void setPanGrp(RichPanelGroupLayout panGrp) {
        this.panGrp = panGrp;
    }

    public RichPanelGroupLayout getPanGrp() {
        return panGrp;
    }

    public void setTabNoteInter(RichTable tabNoteInter) {
        this.tabNoteInter = tabNoteInter;
    }

    public RichTable getTabNoteInter() {
        return tabNoteInter;
    }

    @SuppressWarnings("unchecked")
    public void onDetailEtudiantAction(ActionEvent actionEvent) {
        String message = "";
        String fr = getFr_acc();
        if(null != getEtdNum().getValue()){
            OperationBinding opDet = getBindings().getOperationBinding("getDetailEtudiant");
            opDet.getParamsMap().put("p_num", getEtdNum().getValue().toString());
            opDet.getParamsMap().put("p_an", Long.parseLong(getAn()));
            opDet.getParamsMap().put("p_fr_acc", getFr_acc());
            opDet.execute();
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EtudiantEtatInscrIterator");
            Long nbr = iter.getEstimatedRowCount();
            if(0 ==  iter.getEstimatedRowCount()){
                message = "Aucun étudiant trouvé !";
            }
            OperationBinding opDetEnj = getBindings().getOperationBinding("getDetailEtudiantEnj");
            opDetEnj.getParamsMap().put("p_num", getEtdNum().getValue().toString());
            opDetEnj.getParamsMap().put("p_an", Long.parseLong(getAn()));
            opDetEnj.getParamsMap().put("p_fr_acc", getFr_acc());
            opDetEnj.execute();
            //getDetailEtudiantEnj
        }else{
            message = "Veuillez entrer un numéro s'il vous plait !";
        }
        sessionFlowScope.put("detail_etd", message);
        this.getEtdNum().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDetEtd());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanFormDetEtd());

    }

    public void setEtdNum(RichInputText etdNum) {
        this.etdNum = etdNum;
    }

    public RichInputText getEtdNum() {
        return etdNum;
    }

    public void setPanGrpDetEtd(RichPanelGroupLayout panGrpDetEtd) {
        this.panGrpDetEtd = panGrpDetEtd;
    }

    public RichPanelGroupLayout getPanGrpDetEtd() {
        return panGrpDetEtd;
    }

    public void setPanGrpForm(RichPanelGroupLayout panGrpForm) {
        this.panGrpForm = panGrpForm;
    }

    public RichPanelGroupLayout getPanGrpForm() {
        return panGrpForm;
    }

    public void setPanFormDetEtd(RichPanelFormLayout panFormDetEtd) {
        this.panFormDetEtd = panFormDetEtd;
    }

    public RichPanelFormLayout getPanFormDetEtd() {
        return panFormDetEtd;
    }

    public void setCode_niv(String code_niv) {
        this.code_niv = code_niv;
    }

    public String getCode_niv() {
        return code_niv;
    }

    public void setFr_acc(String fr_acc) {
        this.fr_acc = fr_acc;
    }

    public String getFr_acc() {
        return fr_acc;
    }

    @SuppressWarnings("unchecked")
    public void onCanceledAction(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        OperationBinding opDet = getBindings().getOperationBinding("getDetailEtudiant");
        opDet.getParamsMap().put("p_num", null);
        opDet.getParamsMap().put("p_an", new Long (0));
        opDet.getParamsMap().put("p_fr_acc", null);
        opDet.execute();
        
        OperationBinding opDetEnj = getBindings().getOperationBinding("getDetailEtudiantEnj");
        opDetEnj.getParamsMap().put("p_num", null);
        opDetEnj.getParamsMap().put("p_an", new Long(0));
        opDetEnj.getParamsMap().put("p_fr_acc", null);
        opDetEnj.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDetEtd());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanFormDetEtd());
    }

    public void exportListEtudiants(FacesContext facesContext, OutputStream outputStream) {
        try {
            //DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NotesModeEnseignementInterVO1Iterator");
            DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("EtudiantExportListIterator");
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
            //Create  Workbook object
            XSSFWorkbook xwb = new XSSFWorkbook(); //xwb.setWorkbookPassword("refonte", arg1);
            //Create Sheet in Workbook
            XSSFSheet sheet = xwb.createSheet("Liste des Etudiants");
            //Style de base
            CellStyle basestyle = xwb.createCellStyle();
            basestyle.setBorderBottom(BorderStyle.MEDIUM);
            basestyle.setBorderLeft(BorderStyle.MEDIUM);
            basestyle.setBorderRight(BorderStyle.MEDIUM);
            basestyle.setBorderTop(BorderStyle.MEDIUM);

            //No of total rows+ 1 for array sizing
            int totRows = ((int) iter.getEstimatedRowCount()) + 1;
            //Here 2 is the number of columns
            //System.out.println("totRows : "+totRows);

            AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnnee");
            AttributeBinding libelle_ue = (AttributeBinding) getBindings().getControlBinding("UeEvalExportListEtd");
            AttributeBinding libelle_ec = (AttributeBinding) getBindings().getControlBinding("EcEvalExportEtd");
            
            AttributeBinding id_fil_ec = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstreEc");
            AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");

            //le mot de passe annee,fil_ec,type_control,id_mode_ens,semestre,session
            /*String mot_passe =
                getAn() + "" + id_fil_ec.getInputValue().toString() + "" + id_type_ctrl.getInputValue().toString() +
                "" + id_mode_ctrl_ec.getInputValue().toString() + "" + getSem() + "" + getSess() + "" + getCal();*/
            //sheet.protectSheet(mot_passe); // mot de passe du fichier
            String[][] entete = new String[8][2];
            entete[0][0] = "Niveau de Formation";
            entete[0][1] = lib_niv_form.getInputValue().toString();
            entete[1][0] = "Unité d'Enseignement";
            entete[1][1] = libelle_ue.getInputValue().toString();
            entete[2][0] = "Elément Constitutif";
            entete[2][1] = libelle_ec.getInputValue().toString();
            /*entete[3][0] = "Type de Controle";
            entete[3][1] = libelle_type_ctrl.getInputValue().toString();*/
            entete[3][0] = "Semestre";
            entete[3][1] = getSem();
            entete[4][0] = "Session";
            entete[4][1] = Integer.parseInt(getSess()) == 1 ? "NORMAL" : "RATTRAPAGE";
            /*entete[6][0] = "Id FilUeSemEc";
        entete[6][1] = id_fil_ec.getInputValue().toString();
        entete[7][0] = "Id Type Ctrl";
        entete[7][1] = id_type_ctrl.getInputValue().toString();
        entete[8][0] = "Mode Enseignement :";
        entete[8][1] = libelle_mode_ens.getInputValue().toString()+"("+id_mode_ctrl_ec.getInputValue().toString()+")";
        */
            entete[5][0] = "Année Universitaire";
            entete[5][1] = lib_annee.getInputValue().toString();

            int rowNum = 1;
            //Iterate over Object array for each row
            for (Object[] datatype : entete) {
                //Creating row in Excel Sheet
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                //Set data in column of a row
                int colNum = 4;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    CellStyle cellStyle = xwb.createCellStyle();
                    // Style the cell with borders and border color
                    CellStyle style = xwb.createCellStyle();
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
                    style.setBorderRight(BorderStyle.MEDIUM);
                    style.setRightBorderColor(IndexedColors.BLUE.getIndex());
                    style.setBorderTop(BorderStyle.MEDIUM);
                    style.setTopBorderColor(IndexedColors.BLUE.getIndex());
                    //cell.setCellStyle(style);
                    // verrouillage des cellules d'entéte
                    //cellStyle.setLocked(true);
                    cell.setCellValue(""); //ligne de séparation de l'entete et des donnees
                    cell.setCellStyle(cellStyle);
                    //tailles des cellules de l'entete
                    sheet.setColumnWidth(4, 8000);
                    sheet.setColumnWidth(5, 10000);
                    sheet.setVerticallyCenter(true);
                    if (colNum == 4 && rowNum == 1) {
                        XSSFCellStyle style2 = xwb.createCellStyle();
                        style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                        // and solid fill pattern produces solid grey cell fill
                        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        cell.setCellStyle(style2);
                    }
                    row.setHeight((short) 600); //hauteur des lignes_colonne de l'entete
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                        //cell.setCellStyle(style);
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    }
                }
            }
                //Here 4 is the number of columns
                Object[][] content_not_ano = new Object[totRows][5];
                int column = 5;
                //Set header text in first row of table in PDF
                content_not_ano[0][0] = "Numéro";
                content_not_ano[0][1] = "Prénom";
                content_not_ano[0][2] = "Nom";
                content_not_ano[0][3] = "Date de Naissance";
                content_not_ano[0][4] = "Lieu de Naissance";

                int i = 1;
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    for (int j = 0; j < column; j++) {
                        if (j == 0 && nextRow.getAttribute("Numero") != null) {
                            content_not_ano[i][j] = nextRow.getAttribute("Numero").toString();
                        }
                        if (j == 1 && nextRow.getAttribute("Prenoms") != null) {
                            content_not_ano[i][j] = nextRow.getAttribute("Prenoms").toString();
                        }
                        if (j == 2 && nextRow.getAttribute("Nom") != null) {
                            content_not_ano[i][j] = nextRow.getAttribute("Nom").toString();
                        }
                        if (j == 3 && nextRow.getAttribute("DateNaissance") != null) {
                            content_not_ano[i][j] = nextRow.getAttribute("DateNaissance").toString();
                            //java.util.Date utilDate = new java.util.Date(((Date)nextRow.getAttribute("DateNaissance")).getTime());

                            //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            //String date1 = dateFormat.format(utilDate);
                            //content_not_ano[i][j] = date1;
                        }
                        if (j == 4 && nextRow.getAttribute("LieuNaissance") != null) {
                            content_not_ano[i][j] = nextRow.getAttribute("LieuNaissance").toString();
                        }
                        /*if (j ==
                            5) { // && nextRow.getAttribute("Note") != null
                            //content_not_ano[i][j] = Float.parseFloat(nextRow.getAttribute("Note").toString());
                            content_not_ano[i][j] =
                           (nextRow.getAttribute("Note") != null ?
                            Float.parseFloat(nextRow.getAttribute("Note").toString()) :
                            (Integer.parseInt(nextRow.getAttribute("IsAbsent").toString()) != 0 ? "ABS" : ""));
                        }*/
                    }
                    i++;
                }
                //Close RowSetIterator
                rsi.closeRowSetIterator();
                //Set data in Excel Sheet from Object array
                int rowNum_not_ano = 8;
                //Iterate over Object array for each row
                for (Object[] datatype : content_not_ano) {
                    //Creating row in Excel Sheet
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum_not_ano++);
                    //Set data in column of a row
                    int colNum = 4;
                    for (Object field : datatype) {
                        Cell cell = row.createCell(colNum++);
                        CellStyle cellStyle = xwb.createCellStyle();
                        /*CellStyle style = xwb.createCellStyle();
                        style.setBorderTop(BorderStyle.MEDIUM);
                        style.setBorderRight(BorderStyle.MEDIUM);
                        style.setBorderBottom(BorderStyle.MEDIUM);
                        style.setBorderLeft(BorderStyle.MEDIUM);
*/
                        cellStyle.setBorderTop(BorderStyle.MEDIUM);
                        cellStyle.setBorderRight(BorderStyle.MEDIUM);
                        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
                        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
                        //cellStyle.setLocked(true);
                        cell.setCellValue(""); //pour les cellules sans notes ni ABS
                        cell.setCellStyle(cellStyle);
                        sheet.setColumnWidth(6, 3000);
                        sheet.setColumnWidth(7, 5000);
                        sheet.setColumnWidth(8, 8000);
                        sheet.setVerticallyCenter(true);
                        if (colNum == 4 && rowNum_not_ano == 8) {
                            XSSFCellStyle style2 = xwb.createCellStyle();
                            style2.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
                            // and solid fill pattern produces solid grey cell fill
                            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                            cell.setCellStyle(style2);
                        }

                        row.setHeight((short) 800);
                        /*if (colNum == 10) {
                            if (field instanceof String) {
                                DataFormat format = xwb.createDataFormat();
                                CellStyle style_cell = xwb.createCellStyle();
                                style_cell.setDataFormat(format.getFormat("00.00"));
                                style_cell.setBorderBottom(BorderStyle.MEDIUM);
                                style_cell.setBorderLeft(BorderStyle.MEDIUM);
                                style_cell.setBorderRight(BorderStyle.MEDIUM);
                                style_cell.setBorderTop(BorderStyle.MEDIUM);
                                //style_cell.setLocked(false);
                                cell.setCellStyle(style_cell);

                            } else if (field instanceof Float) {
                                DataFormat format = xwb.createDataFormat();
                                CellStyle style_cell = xwb.createCellStyle();
                                style_cell.setBorderBottom(BorderStyle.MEDIUM);
                                style_cell.setBorderLeft(BorderStyle.MEDIUM);
                                style_cell.setBorderRight(BorderStyle.MEDIUM);
                                style_cell.setBorderTop(BorderStyle.MEDIUM);
                                style_cell.setLocked(false);
                                cell.setCellValue("");
                                style_cell.setDataFormat(format.getFormat("00.00"));
                                cell.setCellStyle(style_cell);
                            }

                        }*/
                        if (field instanceof String) {
                            cell.setCellValue((String) field);
                        } else if (field instanceof Float) {
                            cell.setCellValue((Float) field);
                        }
                    }
                }
            facesContext.responseComplete();
            try {
                xwb.write(outputStream);
                outputStream.flush();
                outputStream.close();
                facesContext.responseComplete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            FacesContext fc = facesContext;
            String refreshpage = fc.getViewRoot().getViewId();
            ViewHandler ViewH = fc.getApplication().getViewHandler();
            UIViewRoot UIV = ViewH.createView(fc, refreshpage);
            UIV.setViewId(refreshpage);
            fc.setViewRoot(UIV);
            xwb.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setPanHightGroup(RichPanelGroupLayout panHightGroup) {
        this.panHightGroup = panHightGroup;
    }

    public RichPanelGroupLayout getPanHightGroup() {
        return panHightGroup;
    }

    public void setPanHightCollection(RichPanelCollection panHightCollection) {
        this.panHightCollection = panHightCollection;
    }

    public RichPanelCollection getPanHightCollection() {
        return panHightCollection;
    }

    public void setTabHightEtd(RichTable tabHightEtd) {
        this.tabHightEtd = tabHightEtd;
    }

    public RichTable getTabHightEtd() {
        return tabHightEtd;
    }

    public void setPanelGrp(RichPanelGroupLayout panelGrp) {
        this.panelGrp = panelGrp;
    }

    public RichPanelGroupLayout getPanelGrp() {
        return panelGrp;
    }

    public void setEcEvalTpeCntrl(RichSelectOneChoice ecEvalTpeCntrl) {
        this.ecEvalTpeCntrl = ecEvalTpeCntrl;
    }

    public RichSelectOneChoice getEcEvalTpeCntrl() {
        return ecEvalTpeCntrl;
    }

    public void onFiltreChanged(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue().equals("Tous")){
            //System.out.println("Tousssss");            
            try{
                OperationBinding opfilter = getBindings().getOperationBinding("resetEtudiantSansNote");
                opfilter.execute();
                this.getSans_notes_checkbox().setDisabled(true);
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            //System.out.println("Filteerrrr");
            try{
                OperationBinding opfilter = getBindings().getOperationBinding("getEtudiantSansNote");
                opfilter.execute();
                DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementInterNewIterator");
                System.out.print("iter.getEstimatedRowCount() : "+iter.getEstimatedRowCount());
                if(iter.getEstimatedRowCount() != 0)
                    this.getSans_notes_checkbox().setDisabled(false);
            }catch(Exception e){
                System.out.println(e);
            }
            /*DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NotesModeEnseignementInterNewIterator");
            System.out.println("nbre : "+iter.getEstimatedRowCount()); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtngroupe());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd());        }
        
    }

    public void setSans_notes_checkbox(RichSelectBooleanCheckbox sans_notes_checkbox) {
        this.sans_notes_checkbox = sans_notes_checkbox;
    }

    public RichSelectBooleanCheckbox getSans_notes_checkbox() {
        return sans_notes_checkbox;
    }

    public void oncheckedSansNote(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterInter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator");
        System.out.println("iterInter.getEstimatedRowCount : "+iterInter.getEstimatedRowCount());
        if(iterInter.getEstimatedRowCount() != 0){
            RowSetIterator rsi = iterInter.getViewObject().createRowSetIterator(null);
            if(valueChangeEvent.getNewValue().equals(true)){
                System.out.println("true");
                 while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("IsAbsent",1);
                    }
            }else if(valueChangeEvent.getNewValue().equals(false)){
                System.out.println("false");
                while (rsi.hasNext()) {
                   Row nextRow = rsi.next();
                   nextRow.setAttribute("IsAbsent",0);
                   }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanHightCollection());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabHightEtd()); 
        }
    }

    public void setPopupUeClosed(RichPopup popupUeClosed) {
        this.popupUeClosed = popupUeClosed;
    }

    public RichPopup getPopupUeClosed() {
        return popupUeClosed;
    }

    public void setPopUeCloseed(RichPopup popUeCloseed) {
        this.popUeCloseed = popUeCloseed;
    }

    public RichPopup getPopUeCloseed() {
        return popUeCloseed;
    }
}
