package view.beans.maquette;

import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.event.graph.SelectionEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.NavigatableRowIterator;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import view.beans.admin.AdministrationBean;
import view.beans.jsfutils.JSFUtils;

public class MaquetteBean {
    private static final long R_UE = new Long(63);
    private static final long R_EC = new Long(64);
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichTable ueTable;
    private RichTable ecTable;
    private RichTable maquetteTable;
    private RichTable filiereUeSemestreTable;
    private RichTable filiereUeSemestreEcTable;
    private RichPopup popupNewUe;
    private RichPopup popupEditUe;
    private RichPopup popupNewEc;
    private RichPopup popupEditEc;
    private RichPopup popupNewMaquette;
    private RichPopup popupEditMaquette;
    private RichPopup popupNewFiliereUeSemestre;
    private RichPopup popupEditFiliereUeSemestre;
    private RichPopup popupNewFiliereUeSemestreEc;
    private RichPopup popupEditFiliereUeSemestreEc;
    private RichTable nivFormTable;
    private RichPopup popupNewNivForm;
    private RichPopup popupEditNivForm;
    private RichPopup popupDeleteNivForm;
    private UploadedFile uploadedFile;
    private UploadedFile uploadedMaqFile;
    private UploadedFile uploadEtuFile;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    //private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer annee = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    private Integer structure = Integer.parseInt(sessionScope.get("id_str").toString());
    private RichTable modaliteCntrlTable;
    private RichPopup popupEditMdCntrl;
    private RichPopup popupDeleteMdCntrl;
    private RichPopup popupNewParcMaqAnnee;
    private RichTable parcrsMaqAnTable;
    private RichPopup popupEditParcMaqAnnee;
    private RichPopup popupMaquetteUsed;
    private RichPopup popupImport;
    private RichPanelGroupLayout maqpage;
    private RichPopup popupImportEtu;
    private RichPanelGroupLayout panimport;
    private RichPopup popupFilUeExist;
    private RichPopup popupFilEcExist;
    private RichPopup popupNivFormMaqAnMaquetteExist;
    private RichPopup popupParcMaqAnMaquetteExist;
    private RichPopup popupFilUeMaquetteExist;
    private RichPopup popupValideFilUeExist;
    private RichPopup popupResFilUeExist;
    private RichPopup popupInsPedFilUeExist;
    private RichPopup popupFilEcFilUeExist;
    private RichPopup popupValideFilEcExist;
    private RichPopup popupResFilEcExist;
    private RichPopup popupModeCntrlFilEcExist;
    private RichPopup popupAccessFilUeExist;
    private RichPopup popupAccessFilEcExist;
    private RichPopup popupNoParcours;
    private RichTable maqPrcrsTable;
    private RichPopup popupNewNivFormMaqAnn;
    private RichPopup popupNivFormMaqAnnExist;
    private RichPanelHeader maqNivPan;
    private RichSelectOneChoice maqNivFormAn;
    private RichPanelHeader maqprcrspan;
    private RichPanelGroupLayout prcrsmaqpangrp;
    private RichPopup popupScolMaq;
    private RichPanelGroupLayout panBtn;
    private RichPanelGroupLayout btnPanGrp;
    private RichPanelGroupLayout btnMaqImported;
    private RichPopup popupNoStudent;
    private RichButton testbtn;
    private RichPopup popuptotalStudent;
    private RichPanelHeader filUePanHdr;
    private RichPanelCollection filUePanCol;
    private RichTable filUe;
    private RichPanelHeader filEcPanHdr;
    private RichPanelCollection filEcPanCol;
    private RichTable filEc;
    private RichPopup popupNoScolMaq;
    private RichPopup popupMaqvalidated;
    private RichPopup popupValidMaqErr;
    private RichPopup popupScolMaqOp;
    private RichPanelGroupLayout btnPanGrpOp;
    private RichPopup popupScolMaqOpANCNO;
    private RichPanelGroupLayout btnPanGrpOpANCNO;
    private RichSelectOneChoice maqimported;
    private RichPopup popupImportMaq;

    public MaquetteBean() {
    }

    public void setUeTable(RichTable ueTable) {
        this.ueTable = ueTable;
    }

    public RichTable getUeTable() {
        return ueTable;
    }

    public void setEcTable(RichTable ecTable) {
        this.ecTable = ecTable;
    }

    public RichTable getEcTable() {
        return ecTable;
    }

    public void setMaquetteTable(RichTable maquetteTable) {
        this.maquetteTable = maquetteTable;
    }

    public RichTable getMaquetteTable() {
        return maquetteTable;
    }

    public void setFiliereUeSemestreTable(RichTable filiereUeSemestreTable) {
        this.filiereUeSemestreTable = filiereUeSemestreTable;
    }

    public RichTable getFiliereUeSemestreTable() {
        return filiereUeSemestreTable;
    }

    public void setFiliereUeSemestreEcTable(RichTable filiereUeSemestreEcTable) {
        this.filiereUeSemestreEcTable = filiereUeSemestreEcTable;
    }

    public RichTable getFiliereUeSemestreEcTable() {
        return filiereUeSemestreEcTable;
    }

    public void setPopupNewUe(RichPopup popupNewUe) {
        this.popupNewUe = popupNewUe;
    }

    public RichPopup getPopupNewUe() {
        return popupNewUe;
    }

    public void setPopupEditUe(RichPopup popupEditUe) {
        this.popupEditUe = popupEditUe;
    }

    public RichPopup getPopupEditUe() {
        return popupEditUe;
    }

    public void setPopupNewEc(RichPopup popupNewEc) {
        this.popupNewEc = popupNewEc;
    }

    public RichPopup getPopupNewEc() {
        return popupNewEc;
    }

    public void setPopupEditEc(RichPopup popupEditEc) {
        this.popupEditEc = popupEditEc;
    }

    public RichPopup getPopupEditEc() {
        return popupEditEc;
    }

    public void setPopupNewMaquette(RichPopup popupNewMaquette) {
        this.popupNewMaquette = popupNewMaquette;
    }

    public RichPopup getPopupNewMaquette() {
        return popupNewMaquette;
    }

    public void setPopupEditMaquette(RichPopup popupEditMaquette) {
        this.popupEditMaquette = popupEditMaquette;
    }

    public RichPopup getPopupEditMaquette() {
        return popupEditMaquette;
    }

    public void setPopupNewFiliereUeSemestre(RichPopup popupNewFiliereUeSemestre) {
        this.popupNewFiliereUeSemestre = popupNewFiliereUeSemestre;
    }

    public RichPopup getPopupNewFiliereUeSemestre() {
        return popupNewFiliereUeSemestre;
    }

    public void setPopupEditFiliereUeSemestre(RichPopup popupEditFiliereUeSemestre) {
        this.popupEditFiliereUeSemestre = popupEditFiliereUeSemestre;
    }

    public RichPopup getPopupEditFiliereUeSemestre() {
        return popupEditFiliereUeSemestre;
    }

    public void setPopupNewFiliereUeSemestreEc(RichPopup popupNewFiliereUeSemestreEc) {
        this.popupNewFiliereUeSemestreEc = popupNewFiliereUeSemestreEc;
    }

    public RichPopup getPopupNewFiliereUeSemestreEc() {
        return popupNewFiliereUeSemestreEc;
    }

    public void setPopupEditFiliereUeSemestreEc(RichPopup popupEditFiliereUeSemestreEc) {
        this.popupEditFiliereUeSemestreEc = popupEditFiliereUeSemestreEc;
    }

    public RichPopup getPopupEditFiliereUeSemestreEc() {
        return popupEditFiliereUeSemestreEc;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNewUeClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //UtiCree
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("UesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateUe");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewUe();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditUeClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditUe();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteUeAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding ueIdBinding = (AttributeBinding) bindings.getControlBinding("IdUe1");
            Integer ue_id = (Integer.parseInt(ueIdBinding.getInputValue().toString()));
            OperationBinding isFilUeExist = bindings.getOperationBinding("IsFilUeExist");
            isFilUeExist.getParamsMap().put("ue_id", ue_id);
            isFilUeExist.execute();
            DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("IsFilUeExistIterator");
            Row crntfue = iterFilUe.getCurrentRow();
            if (crntfue == null) {
                OnDeleteUeClicked();
            } else {
                //popup uti exist
                RichPopup popup = this.getPopupFilUeExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnNewEcClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree1");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("EcsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateEc");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewEc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditEcClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie1");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditEc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteEcClicked(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding ecIdBinding = (AttributeBinding) bindings.getControlBinding("IdEc1");
            Integer ec_id = (Integer.parseInt(ecIdBinding.getInputValue().toString()));
            OperationBinding isFilEcExist = bindings.getOperationBinding("IsFilEcExist");
            isFilEcExist.getParamsMap().put("ec_id", ec_id);
            isFilEcExist.execute();
            DCIteratorBinding iterFilEc = (DCIteratorBinding) bindings.get("IsFilEcExistIterator");
            Row crntfec = iterFilEc.getCurrentRow();
            if (crntfec == null) {
                OnDeleteEcClick();
            } else {
                //popup uti exist
                RichPopup popup = this.getPopupFilEcExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnNewMaquetteClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree2");
        AttributeBinding struct_id = (AttributeBinding) bindings.getControlBinding("IdStructure");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MaquettesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateMaquette");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        struct_id.setInputValue(getStructure());
        RichPopup popup = this.getPopupNewMaquette();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditMaquetteClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie2");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditMaquette();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }


    @SuppressWarnings("unchecked")
    public void OnDeleteMaquetteAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding mqIdBinding = (AttributeBinding) bindings.getControlBinding("IdMaquette1");
            Integer mq_id = (Integer.parseInt(mqIdBinding.getInputValue().toString()));
            OperationBinding isFilUeMaquetteExist = bindings.getOperationBinding("IsFilUeMaquetteExist");
            isFilUeMaquetteExist.getParamsMap().put("mq_id", mq_id);
            isFilUeMaquetteExist.execute();
            DCIteratorBinding iterFilUeMaq = (DCIteratorBinding) bindings.get("IsFilUeMaquetteExistIterator");
            Row crntfilUe = iterFilUeMaq.getCurrentRow();
            if (crntfilUe == null) {
                OperationBinding isParcoursMaquetteExist = bindings.getOperationBinding("IsPercMaqAnnMaquetteExist");
                isParcoursMaquetteExist.getParamsMap().put("mq_id", mq_id);
                isParcoursMaquetteExist.execute();
                DCIteratorBinding iterParcMaqAnMaq =
                    (DCIteratorBinding) bindings.get("IsPercMaqAnnMaquetteExistIterator");
                Row crntParcMaqAn = iterParcMaqAnMaq.getCurrentRow();
                if (crntParcMaqAn == null) {
                    OperationBinding isNivMaquetteExist = bindings.getOperationBinding("IsNivFromMaqAnnMaquetteExist");
                    isNivMaquetteExist.getParamsMap().put("mq_id", mq_id);
                    isNivMaquetteExist.execute();
                    DCIteratorBinding iterNivMaqAnMaq =
                        (DCIteratorBinding) bindings.get("IsNivFromMaqAnnMaquetteExistIterator");
                    Row crntNivMaqAn = iterNivMaqAnMaq.getCurrentRow();
                    if (crntNivMaqAn == null) {
                        OnDeleteMaquetteCliced();
                    } else {
                        RichPopup popup = this.getPopupNivFormMaqAnMaquetteExist();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                } else {
                    RichPopup popup = this.getPopupParcMaqAnMaquetteExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }
            } else {
                RichPopup popup = this.getPopupFilUeMaquetteExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnNewFiliereUeSemestreClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree3");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FiliereUeSemstresIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        // adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateFiliereUeSemestre");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewFiliereUeSemestre();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditFiliereUeSemestreClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie4");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditFiliereUeSemestre();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteFilUeAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding fsIdBinding = (AttributeBinding) bindings.getControlBinding("IdFiliereUeSemstre1");
            Integer fs_id = (Integer.parseInt(fsIdBinding.getInputValue().toString()));
            OperationBinding isFilUeSemEcFilUEExist = bindings.getOperationBinding("IsFilUeSemEcFilUeExist");
            isFilUeSemEcFilUEExist.getParamsMap().put("fs_id", fs_id);
            isFilUeSemEcFilUEExist.execute();
            DCIteratorBinding iterFilEcFilUe = (DCIteratorBinding) bindings.get("IsFilUeSemEcFilUeExistIterator");
            Row crntfilEc = iterFilEcFilUe.getCurrentRow();
            if (crntfilEc == null) {
                OperationBinding isInsPedSemUEFilUeExist = bindings.getOperationBinding("IsInsPedSemUeFilUeExist");
                isInsPedSemUEFilUeExist.getParamsMap().put("fs_id", fs_id);
                isInsPedSemUEFilUeExist.execute();
                DCIteratorBinding iterInsPedSemUe = (DCIteratorBinding) bindings.get("IsInsPedSemUeFilUeExistIterator");
                Row crntInsPedSemUe = iterInsPedSemUe.getCurrentRow();
                if (crntInsPedSemUe == null) {
                    OperationBinding isValideFilUeExist = bindings.getOperationBinding("IsValidationFilUeExist");
                    isValideFilUeExist.getParamsMap().put("fs_id", fs_id);
                    isValideFilUeExist.execute();
                    DCIteratorBinding iterValidFilUe =
                        (DCIteratorBinding) bindings.get("IsValidationFilUeExistIterator");
                    Row crntValFilUe = iterValidFilUe.getCurrentRow();
                    if (crntValFilUe == null) {
                        OperationBinding isAccessFilUeExist = bindings.getOperationBinding("IsAccessFilUeExist");
                        isAccessFilUeExist.getParamsMap().put("fs_id", fs_id);
                        isAccessFilUeExist.execute();
                        DCIteratorBinding iterAccessFilUe =
                            (DCIteratorBinding) bindings.get("IsAccessFilUeExistIterator");
                        Row crntAccessFilUe = iterAccessFilUe.getCurrentRow();
                        if (crntAccessFilUe == null) {
                            OperationBinding operationBinding = bindings.getOperationBinding("DeleteFiliereUeSemestre");
                            Object result = operationBinding.execute();
                            if (!operationBinding.getErrors().isEmpty()) {
                            } else {
                                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                                Object commitResult = operationCommit.execute();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());

                            }
                        } else {
                            RichPopup popup = this.getPopupAccessFilUeExist();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        }
                    } else {
                        RichPopup popup = this.getPopupValideFilUeExist();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                } else {
                    //popup uti exist
                    RichPopup popup = this.getPopupInsPedFilUeExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }
            } else {
                //popup uti exist
                RichPopup popup = this.getPopupFilEcFilUeExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnNewFiliereUeSemestreEcClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree4");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FiliereUeSemstreEcIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateFiliereUeSemestreEc");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        uticre.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupNewFiliereUeSemestreEc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void OnEditFiliereUeSemestreEcClicked(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie5");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditFiliereUeSemestreEc();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteFilEcAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding fecIdBinding = (AttributeBinding) bindings.getControlBinding("IdFiliereUeSemstreEc");
            Integer fec_id = (Integer.parseInt(fecIdBinding.getInputValue().toString()));
            OperationBinding isModeCntrlEcFilEcExist = bindings.getOperationBinding("IsModeCntrlEcFilEcExist");
            isModeCntrlEcFilEcExist.getParamsMap().put("fec_id", fec_id);
            isModeCntrlEcFilEcExist.execute();
            DCIteratorBinding iterModeCntrlEc = (DCIteratorBinding) bindings.get("IsModeCntrlEcFilEcExistIterator");
            Row crntmodCntrlEc = iterModeCntrlEc.getCurrentRow();
            if (crntmodCntrlEc == null) {
                OperationBinding isResFilEcExist = bindings.getOperationBinding("IsResultatFilEcExist");
                isResFilEcExist.getParamsMap().put("fec_id", fec_id);
                isResFilEcExist.execute();
                DCIteratorBinding iterResFilEc = (DCIteratorBinding) bindings.get("IsResultatFilEcExistIterator");
                Row crntResFilUe = iterResFilEc.getCurrentRow();
                if (crntResFilUe == null) {
                    OperationBinding isValideFilEcExist = bindings.getOperationBinding("IsValidationFilEcExist");
                    isValideFilEcExist.getParamsMap().put("fec_id", fec_id);
                    isValideFilEcExist.execute();
                    DCIteratorBinding iterValidFilEc =
                        (DCIteratorBinding) bindings.get("IsValidationFilEcExistIterator");
                    Row crntValFilEc = iterValidFilEc.getCurrentRow();
                    if (crntValFilEc == null) {
                        OperationBinding isNoteModeEnsFilEcExist =
                            bindings.getOperationBinding("IsNoteModeEnsFilEcExist");
                        isNoteModeEnsFilEcExist.getParamsMap().put("fec_id", fec_id);
                        isNoteModeEnsFilEcExist.execute();
                        DCIteratorBinding iterNoteFilEc =
                            (DCIteratorBinding) bindings.get("IsNoteModeEnsFilEcExistIterator");
                        Row crntNoteFilEc = iterNoteFilEc.getCurrentRow();
                        if (crntNoteFilEc == null) {
                            OperationBinding isAccesFilEcExist = bindings.getOperationBinding("IsAccesFilEcExist");
                            isAccesFilEcExist.getParamsMap().put("fec_id", fec_id);
                            isAccesFilEcExist.execute();
                            DCIteratorBinding iterAccesFilEc =
                                (DCIteratorBinding) bindings.get("IsAccesFilEcExistIterator");
                            Row crntAccesFilEc = iterAccesFilEc.getCurrentRow();
                            if (crntAccesFilEc == null) {
                                OperationBinding operationBinding =
                                    bindings.getOperationBinding("DeleteFiliereUeSemestreEc");
                                Object result = operationBinding.execute();
                                if (!operationBinding.getErrors().isEmpty()) {
                                } else {
                                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                                    Object commitResult = operationCommit.execute();
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());

                                }
                            } else {
                                RichPopup popup = this.getPopupAccessFilEcExist();
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                //empty hints renders dialog in center of screen
                                popup.show(hints);
                            }
                        } else {
                            RichPopup popup = this.getPopupResFilEcExist();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            //empty hints renders dialog in center of screen
                            popup.show(hints);
                        }
                    } else {
                        //popup nivformMaqAn exist
                        RichPopup popup = this.getPopupValideFilEcExist();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                } else {
                    //popup nivformMaqAn exist
                    RichPopup popup = this.getPopupResFilEcExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }

            } else {
                //popup uti exist
                RichPopup popup = this.getPopupModeCntrlFilEcExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        }
    }

    public void OnDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree4.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewUe().hide();
            this.getPopupNewEc().hide();
            this.getPopupEditUe().hide();
            this.getPopupEditEc().hide();
            this.getPopupNewMaquette().hide();
            this.getPopupNewFiliereUeSemestre().hide();
            this.getPopupEditMaquette().hide();
            this.getPopupEditFiliereUeSemestre().hide();
            this.getPopupNewFiliereUeSemestreEc().hide();
            this.getPopupEditFiliereUeSemestreEc().hide();
            this.getPopupNewNivForm().hide();
            this.getPopupEditNivForm().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreEcTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormTable());
        }
    }

    public void OnUeDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewUe().hide();
        this.getPopupEditUe().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("UesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void OnEcDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewEc().hide();
        this.getPopupEditEc().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("EcsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void OnMaquetteDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewMaquette();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MaquettesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void OnFiliereUeSemestreDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewFiliereUeSemestre();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FiliereUeSemstresIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void OnFiliereUeSemEcDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewFiliereUeSemestreEc();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("FiliereUeSemstreEcIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreEcTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public String OnDeleteUeClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteUe");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
            return null;
        }
    }

    public String OnDeleteEcClick() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteEc");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteMaquetteCliced() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MaquettesIterator");
        Row currentRow = dciter.getCurrentRow();
        //Verify if maquette is used and if fil_ue exist in maquette before deleting maquette
        OperationBinding is_used = bindings.getOperationBinding("IsMaquetteUsed");
        is_used.getParamsMap().put("maquette", Integer.parseInt(currentRow.getAttribute("IdMaquette").toString()));
        is_used.execute();
        DCIteratorBinding dciterused = (DCIteratorBinding) bindings.get("IsMaquetteUsedROVOIterator");
        if (dciterused.getEstimatedRowCount() > 0) {
            RichPopup popup = this.getPopupMaquetteUsed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        } else {
            OperationBinding operationBinding = bindings.getOperationBinding("DeleteMaquette");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
            } else {
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());

            }
        }
    }

    public void setNivFormTable(RichTable nivFormTable) {
        this.nivFormTable = nivFormTable;
    }

    public RichTable getNivFormTable() {
        return nivFormTable;
    }

    public void OnNewNivFormClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree6");
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationMaquetteAnnesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        //perform row create
        OperationBinding operationBinding = bindings.getOperationBinding("CreateNivForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
        JSFUtils.setExpressionValue("#{bindings.IdAnneesUnivers.inputValue}", getAnnee());
        RichPopup popup = this.getPopupNewNivForm();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    public void setPopupNewNivForm(RichPopup popupNewNivForm) {
        this.popupNewNivForm = popupNewNivForm;
    }

    public RichPopup getPopupNewNivForm() {
        return popupNewNivForm;
    }

    public void OnNivFormDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewNivForm();
        popup.hide();
        //the cancel operation is executed with immediate=true to bypass the
        //model update. Therefore we manually delete the new row from the
        //iterator
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("NiveauFormationMaquetteAnnesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        //we don't want to continue with the remainder of the lifecycle and
        //thus skip the rest
        fctx.renderResponse();
    }

    public void setPopupEditNivForm(RichPopup popupEditNivForm) {
        this.popupEditNivForm = popupEditNivForm;
    }

    public RichPopup getPopupEditNivForm() {
        return popupEditNivForm;
    }

    public void OnEditNivFormClicked(ActionEvent actionEvent) {
        // Add event code here...
        RichPopup popup = this.getPopupEditNivForm();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    public String OnDeleteNivFormClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteNivForm");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());
            return null;
        }
    }

    public void setPopupDeleteNivForm(RichPopup popupDeleteNivForm) {
        this.popupDeleteNivForm = popupDeleteNivForm;
    }

    public RichPopup getPopupDeleteNivForm() {
        return popupDeleteNivForm;
    }

    public void OnDeleteNivFormAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            OnDeleteNivFormClicked();
        }
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void onSaveUeAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            this.getPopupNewUe().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
        }
    }

    public void onUpdateUeAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditUe().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
        }
    }

    public void onSaveEcAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree1.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewEc().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
        }
    }

    public void onUpdateEcAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie1.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditEc().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
        }
    }

    public void onSaveMaquetteAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree2.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //...
                return;
            }
            this.getPopupNewMaquette().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());
        }
    }

    public void onUpdateMaquetteAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie2.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditMaquette().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaquetteTable());
        }
    }

    public void onSaveFilUeAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree6.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewFiliereUeSemestre().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreTable());
        }
    }

    public void onUpdateFilUeAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie4.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditFiliereUeSemestre().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreTable());
        }
    }

    public void onSaveFilUeSemEcAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree4.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewFiliereUeSemestreEc().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreEcTable());
        }
    }

    public void onUpdateFilUeSemEcAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie5.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditFiliereUeSemestreEc().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFiliereUeSemestreEcTable());
        }
    }

    public void OnNewNivFormAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiCree5.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewNivForm().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormTable());
        }
    }

    public void OnEditNivFormAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie3.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewNivForm().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNivFormTable());
        }
    }

    public String onNewModaliteCntrlClicked() {
        System.out.println("New Modalite");
        return null;
    }

    public String onCommitClicked() {
        BindingContainer bindings = getBindings();
        //JSFUtils.setExpressionValue("#{bindings.UtiCree7.inputValue}", getUtilisateur());
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String onRollbackClicked() {
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getModaliteCntrlTable());
        return null;
    }

    public void setModaliteCntrlTable(RichTable modaliteCntrlTable) {
        this.modaliteCntrlTable = modaliteCntrlTable;
    }

    public RichTable getModaliteCntrlTable() {
        return modaliteCntrlTable;
    }

    public String deleteModeCntrl() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteModeCntrl");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
            return null;
        }
    }

    public void onDeleteModeCntrlAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            deleteModeCntrl();
        }
    }

    public void onEditMdCntrlAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            //JSFUtils.setExpressionValue("#{bindings.UtiModifie6.inputValue}", getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupEditMdCntrl().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getModaliteCntrlTable());
        }
    }

    public void setPopupEditMdCntrl(RichPopup popupEditMdCntrl) {
        this.popupEditMdCntrl = popupEditMdCntrl;
    }

    public RichPopup getPopupEditMdCntrl() {
        return popupEditMdCntrl;
    }

    public void setPopupDeleteMdCntrl(RichPopup popupDeleteMdCntrl) {
        this.popupDeleteMdCntrl = popupDeleteMdCntrl;
    }

    public RichPopup getPopupDeleteMdCntrl() {
        return popupDeleteMdCntrl;
    }

    public String onEditMdCntrlClicked() {
        RichPopup popup = this.getPopupEditMdCntrl();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public void OnNewParcoursMaqAnnClicked(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("MaquettesIterator");
        Row currentMaquette = dciter.getCurrentRow();
        if (currentMaquette != null) {
            AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree8");
            AttributeBinding maq = (AttributeBinding) getBindings().getControlBinding("IdMaquette3");
            DCIteratorBinding dciterParc = (DCIteratorBinding) bindings.get("MaquettesIterator");
            Row oldCcurrentRow = dciterParc.getCurrentRow();
            if (oldCcurrentRow != null) {
                ADFContext adfCtx = ADFContext.getCurrent();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            }
            //perform row create
            OperationBinding operationBinding = bindings.getOperationBinding("CreateParcousMaqAnn");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            uticre.setInputValue(getUtilisateur());
            maq.setInputValue(Long.parseLong(currentMaquette.getAttribute("IdMaquette").toString()));
            //IdAnneesUnivers2
            JSFUtils.setExpressionValue("#{bindings.IdAnneesUnivers2.inputValue}", getAnnee());
            RichPopup popup = this.getPopupNewParcMaqAnnee();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
            return;
        }
    }

    public void OnEditPrcsMaqAnnClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie7");
        utimodif.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopupEditParcMaqAnnee();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);

    }

    public void setPopupNewParcMaqAnnee(RichPopup popupNewParcMaqAnnee) {
        this.popupNewParcMaqAnnee = popupNewParcMaqAnnee;
    }

    public RichPopup getPopupNewParcMaqAnnee() {
        return popupNewParcMaqAnnee;
    }

    public void OnSaveAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewParcMaqAnnee().hide();
            this.getPopupEditParcMaqAnnee().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getParcrsMaqAnTable());
        }
    }

    public void OnParcrsMaqAnnDialogCancel(ClientEvent clientEvent) {
        BindingContainer bindings = getBindings();
        this.getPopupNewParcMaqAnnee().hide();
        this.getPopupEditParcMaqAnnee().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ParcoursMaquetteAnneeVOIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if (oldCurrentRowKey != null) {
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getParcrsMaqAnTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setParcrsMaqAnTable(RichTable parcrsMaqAnTable) {
        this.parcrsMaqAnTable = parcrsMaqAnTable;
    }

    public RichTable getParcrsMaqAnTable() {
        return parcrsMaqAnTable;
    }

    public void setPopupEditParcMaqAnnee(RichPopup popupEditParcMaqAnnee) {
        this.popupEditParcMaqAnnee = popupEditParcMaqAnnee;
    }

    public RichPopup getPopupEditParcMaqAnnee() {
        return popupEditParcMaqAnnee;
    }

    public void setPopupMaquetteUsed(RichPopup popupMaquetteUsed) {
        this.popupMaquetteUsed = popupMaquetteUsed;
    }

    public RichPopup getPopupMaquetteUsed() {
        return popupMaquetteUsed;
    }

    public void setPopupImport(RichPopup popupImport) {
        this.popupImport = popupImport;
    }

    public RichPopup getPopupImport() {
        return popupImport;
    }

    public void onImportInputValueChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Value Changed !!!");
    }

    public void OnImportAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {

        }
    }

    public void OnImportValideAction(ActionEvent actionEvent) {
        // Add event code here...
        UploadedFile file = uploadedFile;

        try {
            //Check if file is XLSX
            if (file.getContentType()
                .equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                file.getContentType().equalsIgnoreCase("application/xlsx")) {
                readNProcessExcelx(file.getInputStream()); //for xlsx
            }
            //Check if file is XLS
            else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                if (file.getFilename()
                        .toUpperCase()
                        .endsWith(".XLS")) {
                    //readNProcessExcel(file.getInputStream()); //for xls
                }

            } else {
                FacesMessage msg = new FacesMessage("File format not supported.-- Upload XLS or XLSX file");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            file.getInputStream().close();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getMaqpage());

        } catch (IOException e) {
            // TODO
        }
        // finally{
        this.getPopupImport().hide();
        this.getPopupImport().clearInitialState();
        //  }
    }

    public void OnCancelImport(ActionEvent actionEvent) {
        // Add event code here...
        this.getPopupImport().hide();
        this.getPopupImport().clearInitialState();
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**Method to get Binding Container of current view port
     * @return
     */
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;
    }
    //readNProcessEtuExcelx

    /**
     * Method to read xlsx file and upload to table.
     * @param myxls
     * @throws IOException
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void readNProcessEtuExcelx(InputStream xlsx) throws IOException {
        DCIteratorBinding iterPers = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("PersonnesIterator");
        DCIteratorBinding iterEtu = (DCIteratorBinding) BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .get("EtudiantsVO1Iterator");

        DCIteratorBinding iterInsAdmin = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("InscriptionsAdminIterator");
        DCIteratorBinding iterInsPed = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("InscriptionsPedagogiquesIterator");
        DCIteratorBinding iterInsPedSem = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("InscriptionPedSemestreIterator");
        DCIteratorBinding iterInsPedSemUe = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("InscriptionPedSemUeIterator");

        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        ArrayList<Long> listfilUeSem = new ArrayList<Long>();
        try {
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {

        }
        OperationBinding op_get_fil_ue = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("getFilUeSem");
        op_get_fil_ue.getParamsMap().put("a_id", getAnnee());
        //op_get_fil_ue.getParamsMap().put("s_id", getSemestre());
        op_get_fil_ue.getParamsMap().put("p_id", getParcours());
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
            }
        }
        rsi.closeRowSetIterator();

        //####################################### Personnes
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            //Personne
            if (sheetIndex == 0) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_ts = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreatePersonne");
                        op_new_ts.execute();
                        Row rowPers = iterPers.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("Nom", MytempCell.getStringCellValue());
                                        rowPers.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("Prenoms", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 2) {
                                    rowPers.setAttribute("NomMarital", MytempCell.getStringCellValue());
                                } else if (Index ==
                                           3) {
                                    if ((MytempCell.getCellType().toString() == "STRING") ||
                                        (MytempCell.getCellType().toString() == "NUMERIC")) {
                                        //java.util.Date date=null ;
                                        if (MytempCell.getCellType().toString() == "NUMERIC") {
                                            java.util.Date date = MytempCell.getDateCellValue();
                                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                                            String date1 = dateFormat.format(date);
                                            try {
                                                date = dateFormat.parse(date1);
                                            } catch (ParseException e) {
                                            }
                                            rowPers.setAttribute("DateNaissance", date);
                                        }
                                        if (MytempCell.getCellType().toString() == "STRING") {
                                            java.util.Date date = null;
                                            String date1 = MytempCell.getStringCellValue();
                                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                                            try {
                                                date = dateFormat.parse(date1);
                                            } catch (ParseException e) {
                                                System.out.println(e.getMessage());
                                            }
                                            rowPers.setAttribute("DateNaissance", date);
                                        }

                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("LieuNaissance", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("Adresse", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 6) {
                                    rowPers.setAttribute("EmailInstitutionnel", MytempCell.getStringCellValue());
                                } else if (Index == 7) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("EmailPersonnel", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 8) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        rowPers.setAttribute("Telephone",
                                                             Integer.toString((int) MytempCell.getNumericCellValue()));
                                    } else if (MytempCell.getCellType().toString() == "STRING") {
                                        rowPers.setAttribute("Telephone", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 9) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        if (MytempCell.getStringCellValue() != null) {
                                            rowPers.setAttribute("Portable", MytempCell.getStringCellValue());
                                        }
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if (MytempCell.getNumericCellValue() != 0) {
                                            rowPers.setAttribute("Portable",
                                                                 Integer.toString((int) MytempCell.getNumericCellValue()));
                                        }
                                    }
                                } else if (Index == 10) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_pay = BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .getOperationBinding("getPays");
                                        op_pay.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_pay.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getPays1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowPers.setAttribute("IdPays", Long.parseLong(iterExist.getCurrentRow()
                                                                                                   .getAttribute("IdPays")
                                                                                                   .toString()));

                                        }
                                    }
                                } else if (Index == 11) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_pay = BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .getOperationBinding("getPays");
                                        op_pay.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_pay.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getPays1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowPers.setAttribute("IdPaysNationalite",
                                                                 Long.parseLong(iterExist.getCurrentRow()
                                                                                                              .getAttribute("IdPays")
                                                                                                              .toString()));
                                        }
                                    }
                                } else if (Index == 12) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_pay = BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .getOperationBinding("getCivilite");
                                        op_pay.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_pay.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getCivilite1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowPers.setAttribute("IdCivilite", Long.parseLong(iterExist.getCurrentRow()
                                                                                                       .getAttribute("IdCivilite")
                                                                                                       .toString()));
                                        }
                                    }
                                } else if (Index == 13) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_pay = BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .getOperationBinding("getSexe");
                                        op_pay.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_pay.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getSexe1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowPers.setAttribute("IdSexe", Long.parseLong(iterExist.getCurrentRow()
                                                                                                   .getAttribute("IdSexe")
                                                                                                   .toString()));
                                        }
                                    }
                                } else if (Index == 14) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        if (MytempCell.getStringCellValue() != null) {
                                            rowPers.setAttribute("PieceIdentification",
                                                                 MytempCell.getStringCellValue());
                                        }
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if (MytempCell.getNumericCellValue() != 0) {
                                            rowPers.setAttribute("PieceIdentification",
                                                                 Integer.toString((int) MytempCell.getNumericCellValue()));
                                        }
                                    }

                                } else if (Index == 15) {
                                    rowPers.setAttribute("Ine", MytempCell.getStringCellValue());
                                } else if (Index == 16) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowPers.setAttribute("DateEstimee", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 17) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        rowPers.setAttribute("NombreEpouses",
                                                             Double.parseDouble(MytempCell.getStringCellValue()));
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        rowPers.setAttribute("NombreEpouses", MytempCell.getNumericCellValue());
                                    }
                                } else if (Index == 19) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        rowPers.setAttribute("CodeBarre", MytempCell.getStringCellValue());
                                    }
                                    if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        rowPers.setAttribute("CodeBarre",
                                                             Integer.toString((int) MytempCell.getNumericCellValue()));
                                    }
                                } else if (Index == 21) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        rowPers.setAttribute("CodeEmail", MytempCell.getStringCellValue());
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        rowPers.setAttribute("CodeEmail",
                                                             Integer.toString((int) MytempCell.getNumericCellValue()));
                                    }
                                } else if (Index == 22) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        rowPers.setAttribute("PassWord", MytempCell.getStringCellValue());
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        rowPers.setAttribute("PassWord",
                                                             Integer.toString((int) MytempCell.getNumericCellValue()));
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowPers.getAttribute("Nom") == null || rowPers.getAttribute("Prenoms") == null ||
                            rowPers.getAttribute("DateNaissance") == null ||
                            rowPers.getAttribute("LieuNaissance") == null ||
                            rowPers.getAttribute("EmailPersonnel") == null || rowPers.getAttribute("Adresse") == null ||
                            rowPers.getAttribute("Portable") == null || rowPers.getAttribute("IdPays") == null ||
                            rowPers.getAttribute("IdPaysNationalite") == null ||
                            rowPers.getAttribute("IdCivilite") == null ||
                            rowPers.getAttribute("PieceIdentification") == null) {
                            //rowPers.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_pers_exist = BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .getOperationBinding("isPersExist");
                            is_pers_exist.getParamsMap().put("nom", rowPers.getAttribute("Nom").toString());
                            is_pers_exist.getParamsMap().put("p_nom", rowPers.getAttribute("Prenoms").toString());
                            is_pers_exist.getParamsMap()
                                .put("email", rowPers.getAttribute("EmailPersonnel").toString());
                            is_pers_exist.getParamsMap().put("tel", rowPers.getAttribute("Portable").toString());
                            is_pers_exist.getParamsMap()
                                .put("nin", rowPers.getAttribute("PieceIdentification").toString());
                            is_pers_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isPersExist1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowPers.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            } else {
                                try {
                                    OperationBinding op_commit = BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .getOperationBinding("Commit");
                                    op_commit.execute();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    skipcnt++;
                }
            }
        }

        //####################################### Etudiant et Inscription Administratif
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            String e_name = null;
            String e_pname = null;
            String e_email = null;
            String e_tel = null;
            String e_nin = null;
            //Etudiant
            if (sheetIndex == 0) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) {
                        //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_std = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("CreateStudent");
                        op_new_std.execute();
                        Row rowEtud = iterEtu.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);

                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        e_name = MytempCell.getStringCellValue();
                                        rowEtud.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        e_pname = MytempCell.getStringCellValue();
                                    }
                                } else if (Index == 7) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        e_email = MytempCell.getStringCellValue();
                                    }
                                } else if (Index == 9) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        if (MytempCell.getStringCellValue() != null) {
                                            e_tel = MytempCell.getStringCellValue();
                                        }
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if (MytempCell.getNumericCellValue() != 0) {
                                            e_tel = Integer.toString((int) MytempCell.getNumericCellValue());
                                        }
                                    }

                                } else if (Index == 14) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        if (MytempCell.getStringCellValue() != null) {
                                            e_nin = MytempCell.getStringCellValue();
                                        }
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if (MytempCell.getNumericCellValue() != 0) {
                                            e_nin = Integer.toString((int) MytempCell.getNumericCellValue());
                                        }
                                    }
                                } else if (Index == 23) {
                                    try {
                                        if (MytempCell.getCellType().toString() == "STRING") {
                                            if (MytempCell.getStringCellValue() != null) {
                                                rowEtud.setAttribute("Numero", MytempCell.getStringCellValue());
                                            }
                                        } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                            if (MytempCell.getNumericCellValue() != 0) {
                                                rowEtud.setAttribute("Numero",
                                                                     Integer.toString((int) MytempCell.getNumericCellValue()));
                                            }
                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                } else if (Index == 24) {
                                    try {
                                        if (MytempCell.getCellType().toString() == "STRING") {
                                            if (MytempCell.getStringCellValue() != null) {
                                                rowEtud.setAttribute("NumeroTable", MytempCell.getStringCellValue());
                                            }
                                        } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                            if (MytempCell.getNumericCellValue() != 0) {
                                                rowEtud.setAttribute("NumeroTable",
                                                                     Integer.toString((int) MytempCell.getNumericCellValue()));
                                            }
                                        } 
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if ((rowEtud.getAttribute("Numero") != null) && (e_pname != null) && (e_name != null) &&
                            (e_tel != null) && (e_nin != null)) {
                           OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isStudentExist");
                            is_exist.getParamsMap().put("num", rowEtud.getAttribute("Numero").toString());
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isStudentExist1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowEtu.removeAndRetain();
                                OperationBinding op_rbck = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rbck.execute();
                            } else {
                                OperationBinding is_pers_exist = BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .getOperationBinding("isPersExist");
                                is_pers_exist.getParamsMap().put("nom", e_name);
                                is_pers_exist.getParamsMap().put("p_nom", e_pname);
                                is_pers_exist.getParamsMap().put("email", e_email);
                                is_pers_exist.getParamsMap().put("tel", e_tel);
                                is_pers_exist.getParamsMap().put("nin", e_nin);
                                is_pers_exist.execute();
                                DCIteratorBinding iterPersExist =
                                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                    .getCurrentBindingsEntry()
                                                                                                    .get("isPersExist1Iterator");
                                if (iterPersExist.getEstimatedRowCount() > 0) {
                                    try {
                                        rowEtud.setAttribute("IdPersonne", Long.parseLong(iterPersExist.getCurrentRow()
                                                                                                       .getAttribute("IdPersonne")
                                                                                                       .toString()));

                                        OperationBinding op_commit = BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .getOperationBinding("Commit");
                                        op_commit.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                } else {
                                    OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .getOperationBinding("Rollback");
                                    op_rlbk.execute();
                                }
                            }
                        } else {
                            OperationBinding op_rlbk_std = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                            op_rlbk_std.execute();
                        }
                    }
                    skipcnt++;
                }

            }
            //Ins_Admin
            if (sheetIndex == 1) {
                String num = null;
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_std = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("CreateInsAdmin");
                        op_new_std.execute();
                        Row rowInsAdmin = iterInsAdmin.getCurrentRow();
                        int Index = 0;
                        //Parcourir les colonnes de la feuille
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        if (MytempCell.getStringCellValue() != null)
                                            num = MytempCell.getStringCellValue();
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        if (MytempCell.getNumericCellValue() != 0)
                                            num = Integer.toString((int) MytempCell.getNumericCellValue());
                                    }
                                    if (num != null) {
                                        OperationBinding is_exist =
                                            BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isStudentExist");
                                        is_exist.getParamsMap().put("num", num);
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isStudentExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsAdmin.setAttribute("IdEtudiant",
                                                                     Long.parseLong(iterExist.getCurrentRow()
                                                                                                           .getAttribute("IdEtudiant")
                                                                                                           .toString()));
                                        } else {
                                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rlbk.execute();
                                            break;
                                        }
                                        rowInsAdmin.setAttribute("IdAnneesUnivers", getAnnee());
                                        rowInsAdmin.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_tf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getTypeForm");
                                        is_exist_tf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_tf.execute();
                                        DCIteratorBinding iterExistTf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getTypeFormROVO1Iterator");
                                        if (iterExistTf.getEstimatedRowCount() > 0) {
                                            rowInsAdmin.setAttribute("IdTypeFormation",
                                                                     Long.parseLong(iterExistTf.getCurrentRow()
                                                                                                                  .getAttribute("IdTypeFormation")
                                                                                                                  .toString()));
                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getGrade");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExistGf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getGrade1Iterator");
                                        if (iterExistGf.getEstimatedRowCount() > 0) {
                                            rowInsAdmin.setAttribute("IdGrade", Long.parseLong(iterExistGf.getCurrentRow()
                                                                                                          .getAttribute("IdGradesFormation")
                                                                                                          .toString()));
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowInsAdmin.getAttribute("IdEtudiant") == null ||
                            rowInsAdmin.getAttribute("IdTypeFormation") == null ||
                            rowInsAdmin.getAttribute("IdGrade") == null ||
                            rowInsAdmin.getAttribute("IdAnneesUnivers") == null) {
                            //rowInsAdmin.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_insAdmin_exist = BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .getOperationBinding("isInsAdminExist");
                            is_insAdmin_exist.getParamsMap().put("etu_id", rowInsAdmin.getAttribute("IdEtudiant"));
                            is_insAdmin_exist.getParamsMap().put("tf_id", rowInsAdmin.getAttribute("IdTypeFormation"));
                            is_insAdmin_exist.getParamsMap().put("g_id", rowInsAdmin.getAttribute("IdGrade"));
                            is_insAdmin_exist.getParamsMap().put("a_id", rowInsAdmin.getAttribute("IdAnneesUnivers"));
                            is_insAdmin_exist.execute();
                            DCIteratorBinding iterInsAdminExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                    .getCurrentBindingsEntry()
                                                                                                    .get("isInsAdminExist1Iterator");
                            if (iterInsAdminExist.getEstimatedRowCount() > 0) {
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();

                            } else {
                                try {
                                    OperationBinding op_commit = BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .getOperationBinding("Commit");
                                    op_commit.execute();


                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    skipcnt++;
                }
            }

        }

        //###################################### Inscription Pedagogique
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            String num = null;
            if (sheetIndex == 1) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) {
                        OperationBinding op_new_insped = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("CreateInsPed");
                        op_new_insped.execute();
                        Row rowInsPed = iterInsPed.getCurrentRow();
                        int Index = 0;
                        int etd_id = 0;
                        int tf_id = 0;
                        int gf_id = 0;
                        //Parcourir les colonnes de la feuille
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {

                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        num = MytempCell.getStringCellValue();
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        num = Integer.toString((int) MytempCell.getNumericCellValue());
                                    }

                                    if (num != null) {
                                        OperationBinding is_exist =
                                            BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isStudentExist");
                                        is_exist.getParamsMap().put("num", num);
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isStudentExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            etd_id = Integer.parseInt(iterExist.getCurrentRow()
                                                                               .getAttribute("IdEtudiant")
                                                                               .toString());
                                        }
                                        rowInsPed.setAttribute("UtiCree", getUtilisateur());
                                        rowInsPed.setAttribute("UtiPermetDoublIns", getUtilisateur()); //
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_tf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getTypeForm");
                                        is_exist_tf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_tf.execute();
                                        DCIteratorBinding iterExistTf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getTypeFormROVO1Iterator");
                                        if (iterExistTf.getEstimatedRowCount() > 0) {
                                            tf_id = Integer.parseInt(iterExistTf.getCurrentRow()
                                                                                .getAttribute("IdTypeFormation")
                                                                                .toString());
                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getGrade");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExistGf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getGrade1Iterator");
                                        if (iterExistGf.getEstimatedRowCount() > 0) {
                                            gf_id = Integer.parseInt(iterExistGf.getCurrentRow()
                                                                                .getAttribute("IdGradesFormation")
                                                                                .toString());
                                          }
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getHoraireTD");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getHoraireTD1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdHorairesTd",
                                                                   Long.parseLong(iterExist.getCurrentRow()
                                                                                                           .getAttribute("IdHorairesTd")
                                                                                                           .toString()));
                                        }
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getStatuts");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getStatus1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdStatut", Long.parseLong(iterExist.getCurrentRow()
                                                                                                       .getAttribute("IdStatut")
                                                                                                       .toString()));
                                        }
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getOption");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getOption1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdOption", Long.parseLong(iterExist.getCurrentRow()
                                                                                                       .getAttribute("IdOption")
                                                                                                       .toString()));

                                        }
                                    }
                                } else if (Index == 6) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getBourse");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getBourse1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdBourse", Long.parseLong(iterExist.getCurrentRow()
                                                                                                       .getAttribute("IdBourse")
                                                                                                       .toString()));
                                        }
                                    }
                                } else if (Index == 7) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getCohorte");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getCohorte1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdCohorte", Long.parseLong(iterExist.getCurrentRow()
                                                                                                        .getAttribute("IdCohorte")
                                                                                                        .toString()));
                                        }
                                    }
                                } else if (Index == 8) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getTypeConvention");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getTypeConvention1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdTypeConvention",
                                                                   Long.parseLong(iterExist.getCurrentRow()
                                                                                                               .getAttribute("IdTypeConvention")
                                                                                                               .toString()));
                                        }
                                    }
                                } else if (Index == 9) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getOperateur");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getOperateur1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("IdOperateur", Long.parseLong(iterExist.getCurrentRow()
                                                                                                          .getAttribute("IdOperateur")
                                                                                                          .toString()));
                                        }
                                    }
                                } else if (Index == 10) {
                                    rowInsPed.setAttribute("Motif", MytempCell.getStringCellValue());
                                } else if (Index == 11) {
                                    rowInsPed.setAttribute("Redoublement", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 12) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getEtatInsc");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                      .get("getEtatInsc1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowInsPed.setAttribute("EtatInscrEtatInscrId",
                                                                   Long.parseLong(iterExist.getCurrentRow()
                                                                                                                   .getAttribute("IdEtatsInscription")
                                                                                                                   .toString()));
                                        }
                                    }
                                } else if (Index == 13) {
                                    rowInsPed.setAttribute("Quittance", MytempCell.getStringCellValue());
                                } else if (Index == 14) {
                                    rowInsPed.setAttribute("Tarif", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 15) {
                                    rowInsPed.setAttribute("NbInsc", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 16) {
                                    rowInsPed.setAttribute("DernierInscription",
                                                           (int) MytempCell.getNumericCellValue());
                                } else if (Index == 17) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowInsPed.setAttribute("PermetDoubleInscription", 1);
                                        }
                                    }
                                } else if (Index == 18) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowInsPed.setAttribute("Assimile", 1);
                                        }
                                    }
                                } else if (Index == 19) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowInsPed.setAttribute("Cartetiree", 1);
                                        }
                                    }
                                } else if (Index == 20) {
                                    rowInsPed.setAttribute("OrdreInscription", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 21) {
                                    rowInsPed.setAttribute("CodeAConserver", MytempCell.getStringCellValue());
                                } else if (Index == 22) {
                                    java.util.Date date = MytempCell.getDateCellValue();
                                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                                    String date1 = dateFormat.format(date);
                                    try {
                                        date = dateFormat.parse(date1);
                                    } catch (ParseException e) {
                                    }
                                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                    oracle.jbo.domain.Date jboDate = new oracle.jbo.domain.Date(sqlDate);
                                    rowInsPed.setAttribute("DateEditionCarte", date);
                                }
                            } else {
                                Index++;
                            }
                        }

                        if (etd_id == 0 || tf_id == 0 || gf_id == 0 || this.getAnnee() == null ||
                            rowInsPed.getAttribute("IdHorairesTd") == null ||
                            rowInsPed.getAttribute("IdStatut") == null || rowInsPed.getAttribute("IdCohorte") == null ||
                            rowInsPed.getAttribute("IdOperateur") == null ||
                            rowInsPed.getAttribute("EtatInscrEtatInscrId") == null) {
                            //rowInsPed.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding op_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("getinsAdmin");
                            op_exist.getParamsMap().put("etu", etd_id);
                            op_exist.getParamsMap().put("typef", tf_id);
                            op_exist.getParamsMap().put("grade", gf_id);
                            op_exist.getParamsMap().put("annee", getAnnee());
                            op_exist.execute();
                            DCIteratorBinding iterext = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                          .getCurrentBindingsEntry()
                                                                                          .get("getInsAdmin1Iterator");
                            if (iterext.getEstimatedRowCount() > 0) {
                                rowInsPed.setAttribute("IdInscriptionAdmin", Integer.parseInt(iterext.getCurrentRow()
                                                                                                     .getAttribute("IdInscriptionAdmin")
                                                                                                     .toString()));
                                OperationBinding get_prcrs = BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .getOperationBinding("getPrcrsMaqAnnee");
                                get_prcrs.getParamsMap().put("anne_id", this.getAnnee());
                                get_prcrs.getParamsMap().put("nfp_id", this.getParcours());
                                get_prcrs.execute();
                                DCIteratorBinding iterPrcrs =
                                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                .getCurrentBindingsEntry()
                                                                                                .get("getParcoursMaqAnnee1Iterator");
                                if (iterPrcrs.getEstimatedRowCount() == 1) {
                                    rowInsPed.setAttribute("IdParcoursMaquetAnnee",
                                                           Integer.parseInt(iterPrcrs.getCurrentRow()
                                                                                                              .getAttribute("IdParcoursMaquetAnnee")
                                                                                                              .toString()));

                                    OperationBinding is_insPed_exist =
                                        BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("isInsPedExist");
                                    is_insPed_exist.getParamsMap()
                                        .put("ins_adm_id",
                                             Integer.parseInt(iterext.getCurrentRow()
                                                                                                             .getAttribute("IdInscriptionAdmin")
                                                                                                             .toString()));
                                    is_insPed_exist.getParamsMap()
                                        .put("prcrs_maq_an_id",
                                             Integer.parseInt(iterPrcrs.getCurrentRow()
                                                                                                                    .getAttribute("IdParcoursMaquetAnnee")
                                                                                                                    .toString()));
                                    is_insPed_exist.execute();
                                    DCIteratorBinding iterInsPedExist =
                                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("isInsPedExistROVO1Iterator");
                                    if (iterInsPedExist.getEstimatedRowCount() > 0) {
                                        OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                                 .getCurrentBindingsEntry()
                                                                                 .getOperationBinding("Rollback");
                                        op_rlbk.execute();
                                    } else {
                                        try {

                                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                                       .getCurrentBindingsEntry()
                                                                                       .getOperationBinding("Commit");
                                            op_commit.execute();
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                } else {
                                    OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .getOperationBinding("Rollback");
                                    op_rlbk.execute();
                                }
                            } else {
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }

                        }
                    }
                    skipcnt++;
                }
            }
        }

        //############################ INSCRIPTION PEDAGOGIQUE SEMESTRE
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            if (sheetIndex == 1) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) {
                        OperationBinding op_new_insped = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("CreateInsPedSem");
                        op_new_insped.execute();
                        Row rowInsPedSem = iterInsPedSem.getCurrentRow();
                        int Index = 0;
                        int etd_id = 0;
                        int tf_id = 0;
                        int gf_id = 0;
                        String num = null;
                        //Parcourir les colonnes de la feuille
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        num = MytempCell.getStringCellValue();
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        num = Integer.toString((int) MytempCell.getNumericCellValue());
                                    }
                                    if (num !=
                                        null) {
                                        OperationBinding is_exist =
                                     BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isStudentExist");
                                        is_exist.getParamsMap().put("num", num);
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isStudentExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            etd_id = Integer.parseInt(iterExist.getCurrentRow()
                                                                               .getAttribute("IdEtudiant")
                                                                               .toString());
                                        }
                                        rowInsPedSem.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_tf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getTypeForm");
                                        is_exist_tf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_tf.execute();
                                        DCIteratorBinding iterExistTf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getTypeFormROVO1Iterator");
                                        if (iterExistTf.getEstimatedRowCount() > 0) {
                                            tf_id = Integer.parseInt(iterExistTf.getCurrentRow()
                                                                                .getAttribute("IdTypeFormation")
                                                                                .toString());

                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getGrade");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExistGf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getGrade1Iterator");
                                        if (iterExistGf.getEstimatedRowCount() > 0) {
                                            gf_id = Integer.parseInt(iterExistGf.getCurrentRow()
                                                                                .getAttribute("IdGradesFormation")
                                                                                .toString());
                                        }
                                    }
                                } else if (Index == 23) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowInsPedSem.setAttribute("InsPedValide", 1);
                                        }
                                    }
                                } else if (Index == 24) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowInsPedSem.setAttribute("PassExam", 1);
                                        }
                                    }
                                } else if (Index == 25) {
                                    rowInsPedSem.setAttribute("CumulCredit", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 26) {
                                    rowInsPedSem.setAttribute("DetteCredit", (int) MytempCell.getNumericCellValue());
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (etd_id == 0 || tf_id == 0 || gf_id == 0) { //|| this.getSemestre() == null
                            //rowInsPedSem.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                           //getinsPed
                            OperationBinding op_ins_ped = BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .getOperationBinding("getInsPed");
                            op_ins_ped.getParamsMap().put("etu", etd_id);
                            op_ins_ped.getParamsMap().put("typef", tf_id);
                            op_ins_ped.getParamsMap().put("gade", gf_id);
                            op_ins_ped.getParamsMap().put("annee", getAnnee());
                            op_ins_ped.execute();
                            DCIteratorBinding iterext = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                          .getCurrentBindingsEntry()
                                                                                          .get("getInsPed1Iterator");
                            if (iterext.getEstimatedRowCount() >
                                0) {
                                rowInsPedSem.setAttribute("IdInscriptionPedagogique",
                                                          Integer.parseInt(iterext.getCurrentRow()
                                                                                                              .getAttribute("IdInscriptionPedagogique")
                                                                                                              .toString()));
                                OperationBinding is_insPedSem_exist =
                                    BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .getOperationBinding("isinsPedSemExist");

                                is_insPedSem_exist.getParamsMap()
                                    .put("id_insped", Integer.parseInt(iterext.getCurrentRow().toString()));
                                is_insPedSem_exist.execute();
                                DCIteratorBinding iterInsPedSemExist =
                                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                         .getCurrentBindingsEntry()
                                                                                                         .get("isInsPedSemExist1Iterator");
                                if (iterInsPedSemExist.getEstimatedRowCount() > 0) {
                                    OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .getOperationBinding("Rollback");
                                    op_rlbk.execute();
                                } else {
                                    try {

                                        OperationBinding op_commit = BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .getOperationBinding("Commit");
                                        op_commit.execute();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }

                                }
                            } else {
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }

                        }
                    }
                    skipcnt++;
                }
            }
        }

        ////############################ INSCRIPTION PEDAGOGIQUE SEMESTRE_UE
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            if (sheetIndex == 1) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) {
                        int Index = 0;
                        int etd_id = 0;
                        int tf_id = 0;
                        int gf_id = 0;
                        String num = null;
                        //Parcourir les colonnes de la feuille
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING") {
                                        num = MytempCell.getStringCellValue();
                                    } else if (MytempCell.getCellType().toString() == "NUMERIC") {
                                        num = Integer.toString((int) MytempCell.getNumericCellValue());
                                    }
                                    if (num !=
                                        null) {
                                        OperationBinding is_exist =
                                     BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isStudentExist");
                                        is_exist.getParamsMap().put("num", num);
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isStudentExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            etd_id = Integer.parseInt(iterExist.getCurrentRow()
                                                                               .getAttribute("IdEtudiant")
                                                                               .toString());
                                        }

                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_tf =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getTypeForm");
                                        is_exist_tf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_tf.execute();
                                        DCIteratorBinding iterExistTf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getTypeFormROVO1Iterator");
                                        if (iterExistTf.getEstimatedRowCount() > 0) {
                                            tf_id = Integer.parseInt(iterExistTf.getCurrentRow()
                                                                                .getAttribute("IdTypeFormation")
                                                                                .toString());

                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_gf = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("getGrade");
                                        is_exist_gf.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_gf.execute();
                                        DCIteratorBinding iterExistGf =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("getGrade1Iterator");
                                        if (iterExistGf.getEstimatedRowCount() > 0) {
                                            gf_id = Integer.parseInt(iterExistGf.getCurrentRow()
                                                                                .getAttribute("IdGradesFormation")
                                                                                .toString());
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (listfilUeSem.size() == 0 || etd_id == 0 || tf_id == 0 || gf_id == 0 ||
                            this.getAnnee() == null || this.getParcours() == null) { //this.getSemestre() == null ||
                        } else {
                            OperationBinding inspedsem = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("getInsPedSem");
                            inspedsem.getParamsMap().put("annee", getAnnee());
                            inspedsem.getParamsMap().put("etu", etd_id);
                            inspedsem.getParamsMap().put("gade", gf_id);
                            inspedsem.getParamsMap().put("typef", tf_id);
                            inspedsem.execute();
                            DCIteratorBinding iterinsped =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                             .getCurrentBindingsEntry()
                                                                                             .get("getInsPedSem1Iterator");
                            if (iterinsped.getEstimatedRowCount() > 0) {
                                for (int i = 0; i < listfilUeSem.size(); i++) {
                                    OperationBinding op_new_insped =
                                        BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .getOperationBinding("CreateInPedSemUe");
                                    op_new_insped.execute();
                                    Row rowInsPedSemUe = iterInsPedSemUe.getCurrentRow();
                                    rowInsPedSemUe.setAttribute("UtiCree", getUtilisateur());
                                    rowInsPedSemUe.setAttribute("IdFiliereUeSemstre", listfilUeSem.get(i));
                                    rowInsPedSemUe.setAttribute("IdInscriptionPedSemestre",
                                                                Integer.parseInt(iterinsped.getCurrentRow()
                                                                                                                       .getAttribute("IdInscriptionPedSemestre")
                                                                                                                       .toString()));
                                    //isInsPedSemUeExist
                                    OperationBinding is_inspedsem_ue =
                                        BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("isInsPedSemUeExist");
                                    is_inspedsem_ue.getParamsMap()
                                        .put("ins_ped_sem_id", rowInsPedSemUe.getAttribute("IdInscriptionPedSemestre"));
                                    is_inspedsem_ue.getParamsMap()
                                        .put("fil_ue_sem_id", rowInsPedSemUe.getAttribute("IdFiliereUeSemstre"));
                                    is_inspedsem_ue.execute();
                                    DCIteratorBinding iterinspedsemue =
                                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("isInsPedSemUeExist1Iterator");
                                    if (iterinspedsemue.getEstimatedRowCount() > 0) {
                                        OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                                 .getCurrentBindingsEntry()
                                                                                 .getOperationBinding("Rollback");
                                        op_rlbk.execute();
                                    } else {
                                        try {

                                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                                       .getCurrentBindingsEntry()
                                                                                       .getOperationBinding("Commit");
                                            op_commit.execute();
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }
                            } 
                        }
                    }
                    skipcnt++;
                }
            }
        }

        WorkBook.close();
        this.getPopupImportEtu().hide();
    }

    /**
     * Method to read xlsx file and upload to table.
     * @param myxls
     * @throws IOException
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void readNProcessExcelx(InputStream xlsx) throws IOException {
        //store exist lib section
        ArrayList<String> existSectionList = new ArrayList<String>();
        ArrayList<String> existEtabList = new ArrayList<String>();
        ArrayList<String> existDeptList = new ArrayList<String>();

        DCIteratorBinding iterSect = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("TypeSectionsIterator");
        DCIteratorBinding iterStruct = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("StructuresIterator1");
        DCIteratorBinding iterHStruct = (DCIteratorBinding) BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .get("HistoriquesStructuresStructIterator");
        DCIteratorBinding iterDomaine = (DCIteratorBinding) BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .get("DomainesIterator");
        DCIteratorBinding iterMention = (DCIteratorBinding) BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .get("MentionsDomainesIterator");
        DCIteratorBinding iterSpecialite = (DCIteratorBinding) BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .get("SpecialitesMentionsIterator");
        DCIteratorBinding iterFormation = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("FormationsVO1Iterator");
        DCIteratorBinding iterNivFormation = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("NiveauxFormationsIterator");
        DCIteratorBinding iterUe = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("UesIterator");
        DCIteratorBinding iterEc = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("EcsIterator");
        DCIteratorBinding iterMaquette = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("MaquettesIterator");
        DCIteratorBinding iterFilUe = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("FiliereUeSemstresIterator");
        DCIteratorBinding iterFilEc = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("FiliereUeSemstreEcIterator");
        DCIteratorBinding iterNivFromMaqAnn =
            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .get("NiveauFormationMaquetteAnnesIterator");
        DCIteratorBinding iterDroitNivPays = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("DroitNiveauPaysVOIterator");
        DCIteratorBinding iterGrade = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("GradesFormationIterator");
        DCIteratorBinding iterOption = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("OptionsSpecialitesIterator");
        DCIteratorBinding iterFormSpec = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("FormationsSpecialitesIterator");
        //DroitNiveauPaysVOIterator
        //Use XSSFWorkbook for XLS file
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        try {
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {

        }
        for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
            Integer skipRw = 1;
            Integer skipcnt = 1;
            //Import Sections
            if (sheetIndex == 0) {
                //Section (LIBELLE_LONG,LIBELLE_COURT)
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_ts = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateTypeSection");
                        op_new_ts.execute();
                        Row rowSect = iterSect.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() !=
                                        null) {
                                        //isSectionExist
                                        OperationBinding is_exist =
                                                                                      BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isSectionExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isSectionExistROVOIterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                           OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowSect.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowSect.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        rowSect.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowSect.getAttribute("LibelleLong") == null) {
                            //rowSect.removeAndRetain();
                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rbck.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Structure(Etab)
            if (sheetIndex == 1) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_s = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("CreateStructure");
                        op_new_s.execute();
                        Row rowEtab = iterStruct.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        //isEtabExist
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isEtabExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isEtabExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowEtab.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowEtab.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("Adresse", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("Email", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("Telephone", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 6) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("Fax", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 7) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_p = BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .getOperationBinding("isEtabExist");
                                        is_exist_p.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_p.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isEtabExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowEtab.setAttribute("StructureParent",
                                                                 Integer.parseInt(iterExist.getCurrentRow()
                                                                                                              .getAttribute("IdStructure")
                                                                                                              .toString()));
                                        }
                                    }
                                } else if (Index == 8) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_t =
                                            BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .getOperationBinding("isSectionExist");
                                        is_exist_t.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_t.execute();
                                        DCIteratorBinding iterExist_s =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                          .getCurrentBindingsEntry()
                                                                                                          .get("isSectionExistROVOIterator");
                                        if (iterExist_s.getEstimatedRowCount() > 0) {
                                            rowEtab.setAttribute("IdTypeSection",
                                                                 Integer.parseInt(iterExist_s.getCurrentRow()
                                                                                                              .getAttribute("IdTypeSection")
                                                                                                              .toString()));
                                        }
                                    }
                                } else if (Index == 9) {
                                    rowEtab.setAttribute("Niveau", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 10) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("Url", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 11) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEtab.setAttribute("HoraireTravail", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }

                        if (rowEtab.getAttribute("LibelleLong") == null ||
                            rowEtab.getAttribute("IdTypeSection") == null ||
                            rowEtab.getAttribute("Telephone") == null || rowEtab.getAttribute("Url") == null ||
                            rowEtab.getAttribute("Email") == null || rowEtab.getAttribute("LibelleCourt") == null ||
                            rowEtab.getAttribute("Adresse") == null) {
                           OperationBinding op_rbk = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rbk.execute();
                        }
                        try {
                            OperationBinding op_commit_et = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Commit");
                            op_commit_et.execute();

                        } catch (Exception e) {
                            OperationBinding op_rbk = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rbk.execute();
                        }
                    }
                    skipcnt++;
                }
            }
            //Import HistoriqueStructure(Departement)
            if (sheetIndex == 2) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_hs = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateHStructure");
                        op_new_hs.execute();
                        Row rowDept = iterHStruct.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        //isEtabExist
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isDeptExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isDeptExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            //iter.getCurrentRow().remove();
                                            existDeptList.add(MytempCell.getStringCellValue());



                                            //rowDept.removeAndRetain();
                                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowDept.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowDept.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowDept.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_etab =
                                            BindingContext.getCurrent()
                                                                                       .getCurrentBindingsEntry()
                                                                                       .getOperationBinding("isEtabExist");
                                        is_exist_etab.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_etab.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isEtabExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowDept.setAttribute("IdStructures",
                                                                 Integer.parseInt(iterExist.getCurrentRow()
                                                                                                           .getAttribute("IdStructure")
                                                                                                           .toString()));
                                        }
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getDateCellValue() != null) {
                                        java.util.Date date = MytempCell.getDateCellValue();
                                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                                        String date1 = dateFormat.format(date);
                                        try {
                                            date = dateFormat.parse(date1);
                                        } catch (ParseException e) {
                                        }
                                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                        oracle.jbo.domain.Date jboDate = new oracle.jbo.domain.Date(sqlDate);
                                        rowDept.setAttribute("DateDebut", jboDate);
                                    }

                                } else if (Index == 5) {
                                    if (MytempCell.getDateCellValue() != null) {
                                        java.util.Date date = MytempCell.getDateCellValue();
                                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                                        String date1 = dateFormat.format(date);
                                        try {
                                            date = dateFormat.parse(date1);
                                        } catch (ParseException e) {
                                        }
                                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                        oracle.jbo.domain.Date jboDate = new oracle.jbo.domain.Date(sqlDate);
                                        rowDept.setAttribute("DateFin", jboDate);
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowDept.getAttribute("LibelleLong") == null) {
                            //rowDept.removeAndRetain();
                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rbck.execute();
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import GradeFormation
            if (sheetIndex == 3) {
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_ts = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateGradeForm");
                        op_new_ts.execute();
                        Row rowgrde = iterGrade.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_grade = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getGrade");
                                        op_grade.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_grade.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getGrade1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                           
                                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rlbk.execute();
                                            //rowgrde.removeAndRetain();
                                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowgrde.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowgrde.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowgrde.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowgrde.getAttribute("LibelleLong") == null ||
                            rowgrde.getAttribute("LibelleCourt") == null) {
                            //rowgrde.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Domaine
            if (sheetIndex == 4) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_dm = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateDomaine");
                        op_new_dm.execute();
                        Row rowDm = iterDomaine.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() !=
                                        null) {
                                        //isSectionExist
                                        OperationBinding is_exist =
                                                                                      BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isDomaineExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isDomaineExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() >
                                            0) {
                                            //iter.getCurrentRow().remove();
                                            //rowDm.removeAndRetain();
         OperationBinding op_rbck = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowDm.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowDm.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowDm.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowDm.getAttribute("LibelleLong") == null || rowDm.getAttribute("LibelleCourt") == null) {
                            //rowDm.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Mention
            if (sheetIndex == 5) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_s = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("CreateMention");
                        op_new_s.execute();
                        Row rowMt = iterMention.getCurrentRow();
                        int Index = 0;
                        String mnt = null;
                        int dm = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowMt.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowMt.setAttribute("UtiCree", getUtilisateur());
                                        mnt = MytempCell.getStringCellValue();
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowMt.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist_dm =
                                            BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("isDomaineExist");
                                        is_exist_dm.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist_dm.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isDomaineExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowMt.setAttribute("IdDomaine", Long.parseLong(iterExist.getCurrentRow()
                                                                                                    .getAttribute("IdDomaine")
                                                                                                    .toString()));
                                            dm = Integer.parseInt(iterExist.getCurrentRow()
                                                                           .getAttribute("IdDomaine")
                                                                           .toString());
                                           }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowMt.getAttribute("LibelleLong") == null || rowMt.getAttribute("LibelleCourt") == null ||
                            rowMt.getAttribute("IdDomaine") == null) {
                            //rowMt.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isMentionExist");
                            is_exist.getParamsMap().put("lib", rowMt.getAttribute("LibelleLong"));
                            is_exist.getParamsMap().put("domaine_id", rowMt.getAttribute("IdDomaine"));
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isMentionExist1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowMt.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Specialité
            if (sheetIndex == 6) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_sp = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateSpec");
                        op_new_sp.execute();
                        Row rowSpec = iterSpecialite.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {

                                        rowSpec.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowSpec.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowSpec.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getMention");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExistM =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                         .getCurrentBindingsEntry()
                                                                                                         .get("GetMention1Iterator");
                                        if (iterExistM.getEstimatedRowCount() > 0) {
                                            rowSpec.setAttribute("IdMention", Long.parseLong(iterExistM.getCurrentRow()
                                                                                                       .getAttribute("IdMention")
                                                                                                       .toString()));
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowSpec.getAttribute("LibelleLong") == null ||
                            rowSpec.getAttribute("LibelleCourt") == null || rowSpec.getAttribute("IdMention") == null) {
                            //rowSpec.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isSpecExist");
                            is_exist.getParamsMap().put("lib", rowSpec.getAttribute("LibelleLong"));
                            is_exist.getParamsMap().put("mention_id", rowSpec.getAttribute("IdMention"));
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isSpecialiteExistROVO1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowSpec.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }
                        }
                        try {
                            OperationBinding op_commit_sp = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Commit");
                            op_commit_sp.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Option
            if (sheetIndex == 7) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_sp = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateOpt");
                        op_new_sp.execute();
                        Row rowOpt = iterOption.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowOpt.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowOpt.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowOpt.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getSpecialite");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExistM =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                         .getCurrentBindingsEntry()
                                                                                                         .get("GetSpecialiteROVO1Iterator");
                                        if (iterExistM.getEstimatedRowCount() > 0) {
                                            rowOpt.setAttribute("IdSpecialite", Long.parseLong(iterExistM.getCurrentRow()
                                                                                                         .getAttribute("IdSpecialite")
                                                                                                         .toString()));
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowOpt.getAttribute("LibelleLong") == null || rowOpt.getAttribute("LibelleCourt") == null ||
                            rowOpt.getAttribute("IdSpecialite") == null) {
                            //rowOpt.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isOptionExist");
                            is_exist.getParamsMap().put("lib", rowOpt.getAttribute("LibelleLong"));
                            is_exist.getParamsMap().put("spec_id", rowOpt.getAttribute("IdSpecialite"));
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("IsOptionExist1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowOpt.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }
                        }
                        try {
                            OperationBinding op_commit_sp = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Commit");
                            op_commit_sp.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Formation
            if (sheetIndex == 8) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_fr = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateFormation");
                        op_new_fr.execute();
                        Row rowForm = iterFormation.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowForm.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getMention");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("GetMention1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowForm.setAttribute("IdMention", Long.parseLong(iterExist.getCurrentRow()
                                                                                                      .getAttribute("IdMention")
                                                                                                      .toString()));
                                          }
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isDeptExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isDeptExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowForm.setAttribute("IdHistoriquesStructure",
                                                                 Long.parseLong(iterExist.getCurrentRow()
                                                                                                                   .getAttribute("IdHistoriquesStructure")
                                                                                                                   .toString()));
                                         }
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_grade = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getGrade");
                                        op_grade.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_grade.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getGrade1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowForm.setAttribute("IdGradesFormation",
                                                                 Long.parseLong(iterExist.getCurrentRow()
                                                                                                              .getAttribute("IdGradesFormation")
                                                                                                              .toString()));
                                         }
                                    }
                                } else if (Index == 6) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_grade = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getCycle");
                                        op_grade.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_grade.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getCycleROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowForm.setAttribute("IdCycle", Long.parseLong(iterExist.getCurrentRow()
                                                                                                    .getAttribute("IdCycle")
                                                                                                    .toString()));
                                         }
                                    }
                                } else if (Index == 7) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("NON")) {
                                            rowForm.setAttribute("Ouvert", 0);
                                        }
                                    }
                                }  else if (Index == 9) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowForm.setAttribute("Professionalisante", 1);
                                        }
                                    }
                                } else if (Index == 10) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding op_grade = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getTypeForm");
                                        op_grade.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        op_grade.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getTypeFormROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowForm.setAttribute("IdTypeFormation",
                                                                 Long.parseLong(iterExist.getCurrentRow()
                                                                                                            .getAttribute("IdTypeFormation")
                                                                                                            .toString()));
                                        }
                                    }
                                } else if (Index == 11) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowForm.setAttribute("TroncCommun", 1);
                                        }
                                    }
                                }else if (Index == 13) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("JustificationProgramme", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 14) {
                                    //ORGANISATION PROGRAMME
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("OrganisationProgramme", MytempCell.getStringCellValue());
                                    }
                                } else if (Index ==
                                           15) {
                                    //PROFIL ACADEMIQUE
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("ProfilAcademique", MytempCell.getStringCellValue());
                                       }
                                } else if (Index == 16) {
                                    //PROFIL PROFESSIONNEL
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("ProfilProfessionnel", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 17) {
                                    //OBSERVATIONS
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowForm.setAttribute("Observations", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 18) {
                                    //PAYANTE
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowForm.setAttribute("Payante", 1);
                                        }
                                    }
                                } else if (Index == 19) {
                                    //VALIDE
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowForm.setAttribute("Valide", 1);
                                        }
                                    }
                                } else if (Index ==
                                           20) {
                                    //Presentielle
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowForm.setAttribute("Presentielle", 1);
                                         }
                                    }
                                } else if (Index ==
                                           21) {
                                    //FORMATION RECONNUE
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            try {
                                                rowForm.setAttribute("FormationReconnue", 1);
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                          }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowForm.getAttribute("LibelleLong") == null ||
                            rowForm.getAttribute("LibelleCourt") == null || rowForm.getAttribute("IdMention") == null ||
                            rowForm.getAttribute("IdTypeFormation") == null ||
                            rowForm.getAttribute("IdCycle") == null ||
                            rowForm.getAttribute("IdGradesFormation") == null ||
                            rowForm.getAttribute("IdHistoriquesStructure") == null) {
                            //rowForm.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isFormationExist");
                            is_exist.getParamsMap().put("lib", rowForm.getAttribute("LibelleLong"));
                            is_exist.getParamsMap().put("hs_id", rowForm.getAttribute("IdHistoriquesStructure"));
                            is_exist.getParamsMap().put("mnt_id", rowForm.getAttribute("IdMention"));
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isFormationExistROVO1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                               //rowForm.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Niveau Formation
            if (sheetIndex == 9) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_nfr = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("CreateNivFormation");
                        op_new_nfr.execute();
                        Row rowNivForm = iterNivFormation.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowNivForm.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowNivForm.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowNivForm.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isNiveauExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isNiveauExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowNivForm.setAttribute("IdNiveau", Long.parseLong(iterExist.getCurrentRow()
                                                                                                        .getAttribute("IdNiveau")
                                                                                                        .toString()));
                                         }
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getFromation");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getFormation1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowNivForm.setAttribute("IdFormation",
                                                                    Long.parseLong(iterExist.getCurrentRow()
                                                                                                           .getAttribute("IdFormation")
                                                                                                           .toString()));
                                           }
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowNivForm.setAttribute("ASelection", 1);
                                        }
                                    }
                                } else if (Index == 6) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowNivForm.setAttribute("AutorisationPermise", 1);
                                        }
                                    }
                                } else if (Index == 7) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowNivForm.setAttribute("Presentielle", 1);
                                        }
                                    }
                                } else if (Index == 8) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                        (int) MytempCell.getNumericCellValue() !=
                                        0) {
                                        // (int) MytempCell.getNumericCellValue());
                                        rowNivForm.setAttribute("NbreInsPermise",
                                                                (int) MytempCell.getNumericCellValue());
                                    }
                                } else if (Index == 9) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowNivForm.setAttribute("Diplomante", 1);
                                        }
                                    }
                                } else if (Index == 10) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowNivForm.setAttribute("Ouvert", 1);
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }

                        if (rowNivForm.getAttribute("LibelleLong") == null ||
                            rowNivForm.getAttribute("LibelleCourt") == null ||
                            rowNivForm.getAttribute("IdNiveau") == null ||
                            rowNivForm.getAttribute("IdFormation") == null) {
                            //rowNivForm.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        } else {
                            OperationBinding is_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isNivFormExist");
                            is_exist.getParamsMap().put("lib", rowNivForm.getAttribute("LibelleLong"));
                            is_exist.getParamsMap().put("form_id", rowNivForm.getAttribute("IdFormation"));
                            is_exist.getParamsMap().put("niv", rowNivForm.getAttribute("IdNiveau"));
                            is_exist.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isNivFormExistROVO1Iterator");
                            if (iterExist.getEstimatedRowCount() > 0) {
                                //rowNivForm.removeAndRetain();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            }
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import UE
            if (sheetIndex == 10) {
                //Section (LIBELLE_LONG,LIBELLE_COURT)
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_ue = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateUe");
                        op_new_ue.execute();
                        Row rowUe = iterUe.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        //isSectionExist
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isUeExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("IsUeExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            //iter.getCurrentRow().remove();
                                            //rowUe.removeAndRetain();
                                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowUe.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowUe.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowUe.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowUe.getAttribute("LibelleLong") == null || rowUe.getAttribute("LibelleCourt") == null) {
                            //rowUe.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Ec
            if (sheetIndex == 11) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_ec = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateEc");
                        op_new_ec.execute();
                        Row rowEc = iterEc.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        //isSectionExist
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isEcExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isEcExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            //iter.getCurrentRow().remove();
                                           //rowEc.removeAndRetain();
                                            OperationBinding op_rbck = BindingContext.getCurrent()
                                                                                     .getCurrentBindingsEntry()
                                                                                     .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowEc.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowEc.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowEc.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowEc.getAttribute("LibelleLong") == null || rowEc.getAttribute("LibelleCourt") == null) {
                            //rowEc.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Maquette
            if (sheetIndex == 12) {
                //Section (LIBELLE_LONG,LIBELLE_COURT)
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_mq = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("CreateMaquette");
                        op_new_mq.execute();
                        Row rowMq = iterMaquette.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() !=
                                        null) {
                                        //isSectionExist
                                        OperationBinding is_exist =
                                                                                      BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isMaquetteExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isMaquetteExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() >
                                            0) {
                                            //iter.getCurrentRow().remove();
                                            
         //rowMq.removeAndRetain();
         OperationBinding op_rbck = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("Rollback");
                                            op_rbck.execute();
                                            break;
                                        }
                                        rowMq.setAttribute("LibelleLong", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowMq.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowMq.setAttribute("LibelleCourt", MytempCell.getStringCellValue());
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowMq.getAttribute("LibelleLong") == null || rowMq.getAttribute("LibelleCourt") == null) {
                            //rowMq.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit = BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .getOperationBinding("Commit");
                            op_commit.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import FiliereUeSemestre
            if (sheetIndex == 13) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_s = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("CreateFiliereUeSemestre");
                        op_new_s.execute();
                        Row rowF = iterFilUe.getCurrentRow();
                        int Index = 0;
                        int ue = 0;
                        int sem = 0;
                        int maq = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        rowF.setAttribute("Codification", MytempCell.getStringCellValue());
                                        //UtiCree
                                        rowF.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() !=
                                        null) {
                                        //isEtabExist
                                        OperationBinding is_exist =
                                                                                      BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isMaquetteExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isMaquetteExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowF.setAttribute("IdMaquette", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                      .getAttribute("IdMaquette")
                                                                                                      .toString()));
                                            maq = Integer.parseInt(iterExist.getCurrentRow()
                                                                            .getAttribute("IdMaquette")
                                                                            .toString());
                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isUeExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("IsUeExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowF.setAttribute("IdUe", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                .getAttribute("IdUe")
                                                                                                .toString()));
                                            ue = Integer.parseInt(iterExist.getCurrentRow()
                                                                           .getAttribute("IdUe")
                                                                           .toString());
                                        }
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                        (int) MytempCell.getNumericCellValue() != 0) {
                                        rowF.setAttribute("IdSemestre", (int) MytempCell.getNumericCellValue());
                                        sem = (int) MytempCell.getNumericCellValue();
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist =
                                            BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getCategorieUe");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterCatExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                           .getCurrentBindingsEntry()
                                                                                                           .get("getCategorieUE1Iterator");

                                        if (iterCatExist.getEstimatedRowCount() > 0) {
                                            rowF.setAttribute("IdCategorieUe",
                                                              Integer.parseInt(iterCatExist.getCurrentRow()
                                                                                                            .getAttribute("IdCategorieUe")
                                                                                                            .toString()));
                                           }
                                    }
                                } else if (Index == 5) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                        (int) MytempCell.getNumericCellValue() != 0) {
                                        rowF.setAttribute("Credit", (int) MytempCell.getNumericCellValue());
                                    }
                                } else if (Index == 6) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                        MytempCell.getNumericCellValue() != 0) {
                                        //rowFilUe.setAttribute("Coefficient", (float)MytempCell.getNumericCellValue());
                                        rowF.setAttribute("Coefficient", MytempCell.getNumericCellValue());
                                       }
                                } else if (Index == 7) {
                                    rowF.setAttribute("MoyenneValidation", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 8) {
                                    rowF.setAttribute("MoyenneEliminatoire", (int) MytempCell.getNumericCellValue());
                                } else if (Index == 9) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getNatureUe");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getNatureUe1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowF.setAttribute("IdNatureUe", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                      .getAttribute("IdNatureUe")
                                                                                                      .toString()));
                                         }
                                    }
                                }
                                //Compensable
                                else if (Index == 10) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        if (MytempCell.getStringCellValue().equals("OUI")) {
                                            rowF.setAttribute("Compensable", 1);
                                        }
                                    }
                                }

                            } else {
                                Index++;
                            }
                        }
                        //if required_val missing
                        if (rowF.getAttribute("Codification") == null || rowF.getAttribute("IdMaquette") == null ||
                            rowF.getAttribute("IdUe") == null || rowF.getAttribute("IdSemestre") == null ||
                            rowF.getAttribute("IdCategorieUe") == null || rowF.getAttribute("Credit") == null ||
                            rowF.getAttribute("Coefficient") == null ||
                            rowF.getAttribute("MoyenneValidation").equals(null) ||
                            rowF.getAttribute("MoyenneEliminatoire").equals(null) ||
                            rowF.getAttribute("IdNatureUe") == null) {
                            //rowF.removeAndRetain();
                            OperationBinding op_commit_et = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Rollback");
                            op_commit_et.execute();
                        }
                        OperationBinding is_exist_fil_ue = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("isFilUeExist");
                        is_exist_fil_ue.getParamsMap().put("ue", ue);
                        is_exist_fil_ue.getParamsMap().put("sem", sem);
                        is_exist_fil_ue.getParamsMap().put("mq", maq);
                        is_exist_fil_ue.execute();
                        DCIteratorBinding iterExist_f =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                          .getCurrentBindingsEntry()
                                                                                          .get("isFilUeExistROVO1Iterator");
                        if (iterExist_f.getEstimatedRowCount() >
                            0) {
                            //iter.getCurrentRow().remove();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit_et = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Commit");
                            op_commit_et.execute();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import FiliereUeSemestreEc
            if (sheetIndex == 14) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_s = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("CreateFiliereUeSemestreEc");
                        op_new_s.execute();
                        Row rowFilEc = iterFilEc.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    rowFilEc.setAttribute("Codification", MytempCell.getStringCellValue());
                                    //UtiCree
                                    rowFilEc.setAttribute("UtiCree", getUtilisateur());
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getFilUe");
                                        is_exist.getParamsMap().put("cod", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getFilUeROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowFilEc.setAttribute("IdFiliereUeSemstre",
                                                                  Integer.parseInt(iterExist.getCurrentRow()
                                                                                                                  .getAttribute("IdFiliereUeSemstre")
                                                                                                                  .toString()));
                                            }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isEcExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isEcExistROVO1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowFilEc.setAttribute("IdEc", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                    .getAttribute("IdEc")
                                                                                                    .toString()));
                                           }
                                    }
                                } else if (Index == 3) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getNatureEc");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getNatureEc1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowFilEc.setAttribute("IdNatureEc", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                          .getAttribute("IdNatureEc")
                                                                                                          .toString()));
                                        }
                                    }
                                } else if (Index == 4) {
                                    if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                        MytempCell.getNumericCellValue() != 0) {
                                        rowFilEc.setAttribute("Coefficient", MytempCell.getNumericCellValue());
                                    }
                                } else if (Index == 5) {
                                    rowFilEc.setAttribute("MoyenneValidation", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 6) {
                                    rowFilEc.setAttribute("MoyenneEliminatoire",
                                                          (int) MytempCell.getNumericCellValue());
                                } else if (Index == 7) {
                                    rowFilEc.setAttribute("PourcentageCc", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 8) {
                                    rowFilEc.setAttribute("PourcentageCt", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 9) {
                                    rowFilEc.setAttribute("HeuresCm", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 10) {
                                    rowFilEc.setAttribute("HeuresTd", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 11) {
                                    rowFilEc.setAttribute("HeuresTp", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 12) {
                                    rowFilEc.setAttribute("HeuresTpe", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 13) {
                                    rowFilEc.setAttribute("HeuresStage", (int) MytempCell.getNumericCellValue());
                                }

                            } else {
                                Index++;
                            }
                        }
                        //if required_val missing
                        if ((rowFilEc.getAttribute("IdFiliereUeSemstre") == null) ||
                            (rowFilEc.getAttribute("IdEc") == null) || (rowFilEc.getAttribute("IdNatureEc") == null) ||
                            (rowFilEc.getAttribute("Coefficient") == null)) {
                            //rowFilEc.removeAndRetain();
                           OperationBinding op_r = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("Rollback");
                                op_r.execute();
                        } else {
                            OperationBinding is_exist_fil_ec = BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .getOperationBinding("isFilEcExist");
                            is_exist_fil_ec.getParamsMap().put("fs", rowFilEc.getAttribute("IdFiliereUeSemstre"));
                            is_exist_fil_ec.getParamsMap().put("ec", rowFilEc.getAttribute("IdEc"));
                            is_exist_fil_ec.execute();
                            DCIteratorBinding iterExist_f =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                              .getCurrentBindingsEntry()
                                                                                              .get("isFilEcExistROVO1Iterator");
                            if (iterExist_f.getEstimatedRowCount() >
                                0) {
                                //iter.getCurrentRow().remove();
                                OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Rollback");
                                op_rlbk.execute();
                            } else {
                                try {
                                    OperationBinding op_commit_ = BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .getOperationBinding("Commit");
                                    op_commit_.execute();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    skipcnt++;
                }
            }
            //Import MaquetteNiveau
            if (sheetIndex == 15) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_nfma = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("CreateNivForm");
                        op_new_nfma.execute();
                        Row rowMA = iterNivFromMaqAnn.getCurrentRow();
                        int Index = 0;
                        /* int an = 0;
                                                            int maq = 0;
                                                            int niv_fr = 0; */
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getNivForm");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getNivForm1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowMA.setAttribute("IdNiveauFormation",
                                                               Integer.parseInt(iterExist.getCurrentRow()
                                                                                                              .getAttribute("IdNiveauFormation")
                                                                                                              .toString()));
                                           }
                                        //UtiCree
                                        rowMA.setAttribute("UtiCree", getUtilisateur());
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist =
                                            BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isMaquetteExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isMaquetteExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowMA.setAttribute("IdMaquette", Integer.parseInt(iterExist.getCurrentRow()
                                                                                                       .getAttribute("IdMaquette")
                                                                                                       .toString()));
                                        }
                                    }
                                } else if (Index == 2) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getAnnee");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getAnneeUnivers1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowMA.setAttribute("IdAnneesUnivers",
                                                               Integer.parseInt(iterExist.getCurrentRow()
                                                                                                            .getAttribute("IdAnneesUnivers")
                                                                                                            .toString()));
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        //isNivFormMaqAnExist
                        OperationBinding is_niv_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isNivFormMaqAnneeExist");
                        is_niv_exist.getParamsMap().put("niv", rowMA.getAttribute("IdNiveauFormation"));
                        is_niv_exist.getParamsMap().put("anne", rowMA.getAttribute("IdAnneesUnivers"));
                        is_niv_exist.getParamsMap().put("maq", rowMA.getAttribute("IdMaquette"));
                        is_niv_exist.execute();
                        DCIteratorBinding iterExist =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                        .getCurrentBindingsEntry()
                                                                                        .get("isNivFormMaqAnneeExistROVO1Iterator");
                        if (iterExist.getEstimatedRowCount() > 0) {
                            OperationBinding op_rlb = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rlb.execute();
                        }

                        if (rowMA.getAttribute("IdAnneesUnivers") == null || rowMA.getAttribute("IdMaquette") == null ||
                            rowMA.getAttribute("IdNiveauFormation") == null) {
                            OperationBinding op_rlb = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rlb.execute();
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Personnes(Membres)
            //Tarifs
            if (sheetIndex == 17) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        //CreateDroitNiveauPays
                        OperationBinding op_new_ = BindingContext.getCurrent()
                                                                 .getCurrentBindingsEntry()
                                                                 .getOperationBinding("CreateDroitNiveauPays");
                        op_new_.execute();
                        Row rowDNF = iterDroitNivPays.getCurrentRow();
                        int Index = 0;
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("isNiveauExist");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("isNiveauExist1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowDNF.setAttribute("IdNiveau", Long.parseLong(iterExist.getCurrentRow()
                                                                                                    .getAttribute("IdNiveau")
                                                                                                    .toString()));
                                            rowDNF.setAttribute("UtiCree", getUtilisateur());
                                        }
                                    }
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getPays");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getPays1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowDNF.setAttribute("IdPays", Long.parseLong(iterExist.getCurrentRow()
                                                                                                  .getAttribute("IdPays")
                                                                                                  .toString()));
                                           }
                                    }
                                } else if (Index == 2) {
                                    rowDNF.setAttribute("DroitInsAdm", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 3) {
                                    rowDNF.setAttribute("DroitInsPed", (int) MytempCell.getNumericCellValue());

                                } else if (Index == 4) {
                                    rowDNF.setAttribute("CoutFormation", (int) MytempCell.getNumericCellValue());

                                }
                            } else {
                                Index++;
                            }
                        }
                        //isDroitNivPaysExist
                        OperationBinding is_niv_exist = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("isDroitNivPaysExist");
                        is_niv_exist.getParamsMap().put("niv_id", rowDNF.getAttribute("IdNiveau"));
                        is_niv_exist.getParamsMap().put("pays_id", rowDNF.getAttribute("IdPays"));
                        is_niv_exist.execute();
                        DCIteratorBinding iterExist =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                        .getCurrentBindingsEntry()
                                                                                        .get("isDroitNiveauPaysExist1Iterator");
                        if (iterExist.getEstimatedRowCount() > 0) {
                            OperationBinding op_rlb = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rlb.execute();
                        }

                        if ((rowDNF.getAttribute("IdNiveau") == null) || (rowDNF.getAttribute("IdPays") == null)) {
                            OperationBinding op_rlb = BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .getOperationBinding("Rollback");
                            op_rlb.execute();
                        }
                        try {
                            OperationBinding op_commit_s = BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .getOperationBinding("Commit");
                            op_commit_s.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
            //Import Formation_specialite
            if (sheetIndex == 18) {
                //Iterate over excel rows
                for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                    if (skipcnt > skipRw) { //skip first n row for labels.
                        //Create new row in table
                        OperationBinding op_new_fr_sp = BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .getOperationBinding("CreateFormSpec");
                        op_new_fr_sp.execute();
                        Row rowFormSpec = iterFormSpec.getCurrentRow();
                        int Index = 0;
                        //Iterate over row's columns
                        for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                            Cell MytempCell = tempRow.getCell(column);
                            if (MytempCell != null) {
                                Index = MytempCell.getColumnIndex();
                                if (Index == 0) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .getOperationBinding("getFromation");
                                        is_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_exist.execute();
                                        DCIteratorBinding iterExist =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                        .getCurrentBindingsEntry()
                                                                                                        .get("getFormation1Iterator");
                                        if (iterExist.getEstimatedRowCount() > 0) {
                                            rowFormSpec.setAttribute("IdFormation",
                                                                     Long.parseLong(iterExist.getCurrentRow()
                                                                                                            .getAttribute("IdFormation")
                                                                                                            .toString()));
                                        }
                                    }
                                    rowFormSpec.setAttribute("UtiCree", this.getUtilisateur());
                                } else if (Index == 1) {
                                    if (MytempCell.getCellType().toString() == "STRING" &&
                                        MytempCell.getStringCellValue() != null) {
                                        OperationBinding is_spec_exist =
                                            BindingContext.getCurrent()
                                                                                       .getCurrentBindingsEntry()
                                                                                       .getOperationBinding("getSpecialite");
                                        is_spec_exist.getParamsMap().put("lib", MytempCell.getStringCellValue());
                                        is_spec_exist.execute();
                                        DCIteratorBinding iterExistSpec =
                                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                            .getCurrentBindingsEntry()
                                                                                                            .get("GetSpecialiteROVO1Iterator");
                                        if (iterExistSpec.getEstimatedRowCount() > 0) {
                                            rowFormSpec.setAttribute("IdSpecialite",
                                                                     Long.parseLong(iterExistSpec.getCurrentRow()
                                                                                                                 .getAttribute("IdSpecialite")
                                                                                                                 .toString()));
                                        }
                                    }
                                }
                            } else {
                                Index++;
                            }
                        }
                        if (rowFormSpec.getAttribute("IdFormation") == null ||
                            rowFormSpec.getAttribute("IdSpecialite") == null) {
                            //rowFormSpec.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        OperationBinding is_exist = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("isFormSpecExist");
                        is_exist.getParamsMap().put("form_id", rowFormSpec.getAttribute("IdFormation"));
                        is_exist.getParamsMap().put("spec_id", rowFormSpec.getAttribute("IdSpecialite"));
                        is_exist.execute();
                        DCIteratorBinding iterExist =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                        .getCurrentBindingsEntry()
                                                                                        .get("isFormSpecExist1Iterator");
                        if (iterExist.getEstimatedRowCount() >
                            0) {
                            //rowFormSpec.removeAndRetain();
                            OperationBinding op_rlbk = BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .getOperationBinding("Rollback");
                            op_rlbk.execute();
                        }
                        try {
                            OperationBinding op_commit_sp = BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .getOperationBinding("Commit");
                            op_commit_sp.execute();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    skipcnt++;
                }
            }
        }
        WorkBook.close();
        this.getPopupImport().hide();
    }

    public void setMaqpage(RichPanelGroupLayout maqpage) {
        this.maqpage = maqpage;
    }

    public RichPanelGroupLayout getMaqpage() {
        return maqpage;
    }

    /**
     * @param actionEvent
     */
    public void OnImportEtudiantAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPopupImportEtu(RichPopup popupImportEtu) {
        this.popupImportEtu = popupImportEtu;
    }

    public RichPopup getPopupImportEtu() {
        return popupImportEtu;
    }

    public void onImportEtuValueChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void OnImportEtuValideAction(ActionEvent actionEvent) {
        // Add event code here...
        UploadedFile file = uploadEtuFile;

        try {
            //Check if file is XLSX
            if (file.getContentType()
                .equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                file.getContentType().equalsIgnoreCase("application/xlsx")) {
                readNProcessEtuExcelx(file.getInputStream()); //for xlsx
            }
            //Check if file is XLS
            else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                if (file.getFilename()
                        .toUpperCase()
                        .endsWith(".XLS")) {
                    //readNProcessExcel(file.getInputStream()); //for xls
                }

            } else {
                FacesMessage msg = new FacesMessage("File format not supported.-- Upload XLS or XLSX file");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            file.getInputStream().close();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getPanimport());

        } catch (IOException e) {
            // TODO
        }
        // finally{
        //this.getPopupImportEtu().hide();
        //this.getPopupImportEtu().clearInitialState();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanimport());
        //  }
    }

    public void setUploadEtuFile(UploadedFile uploadEtuFile) {
        this.uploadEtuFile = uploadEtuFile;
    }

    public UploadedFile getUploadEtuFile() {
        return uploadEtuFile;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setParcours(Integer parcours) {
        this.parcours = parcours;
    }

    public Integer getParcours() {
        return parcours;
    }

    /*public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getSemestre() {
        return semestre;
    }*/

    public void setPanimport(RichPanelGroupLayout panimport) {
        this.panimport = panimport;
    }

    public RichPanelGroupLayout getPanimport() {
        return panimport;
    }

    public void setPopupFilUeExist(RichPopup popupFilUeExist) {
        this.popupFilUeExist = popupFilUeExist;
    }

    public RichPopup getPopupFilUeExist() {
        return popupFilUeExist;
    }

    public void setPopupFilEcExist(RichPopup popupFilEcExist) {
        this.popupFilEcExist = popupFilEcExist;
    }

    public RichPopup getPopupFilEcExist() {
        return popupFilEcExist;
    }

    public void setPopupNivFormMaqAnMaquetteExist(RichPopup popupNivFormMaqAnMaquetteExist) {
        this.popupNivFormMaqAnMaquetteExist = popupNivFormMaqAnMaquetteExist;
    }

    public RichPopup getPopupNivFormMaqAnMaquetteExist() {
        return popupNivFormMaqAnMaquetteExist;
    }

    public void setPopupParcMaqAnMaquetteExist(RichPopup popupParcMaqAnMaquetteExist) {
        this.popupParcMaqAnMaquetteExist = popupParcMaqAnMaquetteExist;
    }

    public RichPopup getPopupParcMaqAnMaquetteExist() {
        return popupParcMaqAnMaquetteExist;
    }

    public void setPopupFilUeMaquetteExist(RichPopup popupFilUeMaquetteExist) {
        this.popupFilUeMaquetteExist = popupFilUeMaquetteExist;
    }

    public RichPopup getPopupFilUeMaquetteExist() {
        return popupFilUeMaquetteExist;
    }


    public void setPopupValideFilUeExist(RichPopup popupValideFilUeExist) {
        this.popupValideFilUeExist = popupValideFilUeExist;
    }

    public RichPopup getPopupValideFilUeExist() {
        return popupValideFilUeExist;
    }

    public void setPopupResFilUeExist(RichPopup popupResFilUeExist) {
        this.popupResFilUeExist = popupResFilUeExist;
    }

    public RichPopup getPopupResFilUeExist() {
        return popupResFilUeExist;
    }

    public void setPopupInsPedFilUeExist(RichPopup popupInsPedFilUeExist) {
        this.popupInsPedFilUeExist = popupInsPedFilUeExist;
    }

    public RichPopup getPopupInsPedFilUeExist() {
        return popupInsPedFilUeExist;
    }

    public void setPopupFilEcFilUeExist(RichPopup popupFilEcFilUeExist) {
        this.popupFilEcFilUeExist = popupFilEcFilUeExist;
    }

    public RichPopup getPopupFilEcFilUeExist() {
        return popupFilEcFilUeExist;
    }

    public void setPopupValideFilEcExist(RichPopup popupValideFilEcExist) {
        this.popupValideFilEcExist = popupValideFilEcExist;
    }

    public RichPopup getPopupValideFilEcExist() {
        return popupValideFilEcExist;
    }

    public void setPopupResFilEcExist(RichPopup popupResFilEcExist) {
        this.popupResFilEcExist = popupResFilEcExist;
    }

    public RichPopup getPopupResFilEcExist() {
        return popupResFilEcExist;
    }

    public void setPopupModeCntrlFilEcExist(RichPopup popupModeCntrlFilEcExist) {
        this.popupModeCntrlFilEcExist = popupModeCntrlFilEcExist;
    }

    public RichPopup getPopupModeCntrlFilEcExist() {
        return popupModeCntrlFilEcExist;
    }

    public void setPopupAccessFilUeExist(RichPopup popupAccessFilUeExist) {
        this.popupAccessFilUeExist = popupAccessFilUeExist;
    }

    public RichPopup getPopupAccessFilUeExist() {
        return popupAccessFilUeExist;
    }

    public void setPopupAccessFilEcExist(RichPopup popupAccessFilEcExist) {
        this.popupAccessFilEcExist = popupAccessFilEcExist;
    }

    public RichPopup getPopupAccessFilEcExist() {
        return popupAccessFilEcExist;
    }

    /**
     * @param actionEvent
     */
    @SuppressWarnings("unchecked")
    public void OnSaveParcoursMaqAnn(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //get current row and save its rowKey in view scope for later use
        AttributeBinding an_id = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers3");
        AttributeBinding mq_id = (AttributeBinding) bindings.getControlBinding("IdMaquette5");
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("NivFormParcoursROIterator");
        Row currentP = dciterP.getCurrentRow();
        try {
            OperationBinding prcrsSelected = bindings.getOperationBinding("getSelectedParcours");
            @SuppressWarnings("unchecked")
            ArrayList<Long> prcrs = (ArrayList<Long>) prcrsSelected.execute();
            if (prcrs.size() != 0) {
                for (int prcrcounter = 0; prcrcounter < prcrs.size(); prcrcounter++) {
                    //valider la maquette
                    try {
                        OperationBinding isMaquetteValidated = bindings.getOperationBinding("IsValidatedMaquette");
                        isMaquetteValidated.getParamsMap().put("AnId", Integer.parseInt(an_id.toString()));
                        isMaquetteValidated.getParamsMap().put("mqId", Integer.parseInt(mq_id.toString()));
                        isMaquetteValidated.getParamsMap().put("prcrsId", prcrs.get(prcrcounter));
                        Integer resul = (Integer) isMaquetteValidated.execute();
                        if(0 == resul){
                            try {
                                OperationBinding opValidated = bindings.getOperationBinding("ValidateMaquette");
                                opValidated.getParamsMap().put("an_id", Integer.parseInt(an_id.toString()));
                                opValidated.getParamsMap().put("maq_id", Integer.parseInt(mq_id.toString()));
                                opValidated.getParamsMap().put("parcours", prcrs.get(prcrcounter));
                                opValidated.getParamsMap().put("utilisateur", getUtilisateur());
                                opValidated.execute();
                            }catch(Exception e) {
                                System.out.println(e);
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    OperationBinding isMaquetteParcoursExist = bindings.getOperationBinding("isMaquetteParcoursExist");
                    isMaquetteParcoursExist.getParamsMap().put("id_an", Integer.parseInt(an_id.toString()));
                    isMaquetteParcoursExist.getParamsMap().put("id_mq", Integer.parseInt(mq_id.toString()));
                    isMaquetteParcoursExist.getParamsMap().put("id_prcrs", prcrs.get(prcrcounter));
                    Object resul = isMaquetteParcoursExist.execute();
                    DCIteratorBinding iterused =
                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .get("isMaquetteParcoursExistIterator");
                    Row crnt = iterused.getCurrentRow();
                    if (crnt == null) {
                        OperationBinding createPrcrsMaqAnn = bindings.getOperationBinding("createParcoursMaquetteAnn");
                        createPrcrsMaqAnn.getParamsMap().put("IdAnneesUnivers", Integer.parseInt(an_id.toString()));
                        createPrcrsMaqAnn.getParamsMap().put("IdMaquette", Integer.parseInt(mq_id.toString()));
                        createPrcrsMaqAnn.getParamsMap().put("UtiCree", getUtilisateur());
                        createPrcrsMaqAnn.getParamsMap().put("IdNiveauFormationParcours", prcrs.get(prcrcounter));
                        createPrcrsMaqAnn.execute();
                    } 
                }
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
                maqparcrs.getParamsMap()
                    .put("id_prcrs", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
                maqparcrs.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());
            } else {
                RichPopup popup = this.getPopupNoParcours();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPopupNoParcours(RichPopup popupNoParcours) {
        this.popupNoParcours = popupNoParcours;
    }

    public RichPopup getPopupNoParcours() {
        return popupNoParcours;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursChanged(SelectionEvent selectionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();

        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("NivFormParcoursROIterator");
        Row currentP = dciterP.getCurrentRow();
        OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
        maqparcrs.getParamsMap()
            .put("id_prcrs", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
        maqparcrs.execute();
        DCIteratorBinding dciterPM = (DCIteratorBinding) bindings.get("MaquettesParcoursROIterator");

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPrcrsmaqpangrp());
        /*AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqprcrspan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());*/
    }

    public void setMaqPrcrsTable(RichTable maqPrcrsTable) {
        this.maqPrcrsTable = maqPrcrsTable;
    }

    public RichTable getMaqPrcrsTable() {
        return maqPrcrsTable;
    }

    @SuppressWarnings("unchecked")
    public void OnNivFormationChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding an_id = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers3");
        AttributeBinding nf_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormation2");
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("NivFormParcoursROIterator");
        Row currentP = dciterP.getCurrentRow();
        OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
        maqparcrs.getParamsMap()
            .put("id_prcrs", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
        maqparcrs.execute();

        OperationBinding getmaqnivforman = bindings.getOperationBinding("getMaqetteNivForm");
        getmaqnivforman.getParamsMap().put("annee_id", Integer.parseInt(an_id.toString()));
        getmaqnivforman.getParamsMap().put("niv_id", Integer.parseInt(nf_id.toString()));
        getmaqnivforman.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivFormAn());

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());
    }

    public void OnNewNivFormMaqAnnClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding getmaqstr = bindings.getOperationBinding("getMaquetteStructure");
        getmaqstr.execute();
        RichPopup popup = this.getPopupNewNivFormMaqAnn();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopupNewNivFormMaqAnn(RichPopup popupNewNivFormMaqAnn) {
        this.popupNewNivFormMaqAnn = popupNewNivFormMaqAnn;
    }

    public RichPopup getPopupNewNivFormMaqAnn() {
        return popupNewNivFormMaqAnn;
    }

    @SuppressWarnings("unchecked")
    public void OnNewNivFormMaqAnnAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            AttributeBinding mq_id = (AttributeBinding) bindings.getControlBinding("IdMaquette6");
            AttributeBinding an_id = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers3");
            AttributeBinding nf_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormation2");
            AttributeBinding nfp_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours1");

            //AttributeBinding nfp_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormation3");
            //Integer mq_id = (Integer.parseInt(ueIdBinding.getInputValue().toString()));
            OperationBinding isNivFormMaqAnnExist = bindings.getOperationBinding("isNivFormMaqAnnExist");
            isNivFormMaqAnnExist.getParamsMap().put("mq_id", Integer.parseInt(mq_id.toString()));
            isNivFormMaqAnnExist.getParamsMap().put("an_id", Integer.parseInt(an_id.toString()));
            isNivFormMaqAnnExist.getParamsMap().put("nivform_id", Integer.parseInt(nf_id.toString()));
            isNivFormMaqAnnExist.execute();
            DCIteratorBinding iterFilUe = (DCIteratorBinding) bindings.get("isNivFormMaqAnnExistIterator");
            Row crntnivformaqan = iterFilUe.getCurrentRow();
            if (crntnivformaqan == null) {
                OperationBinding createNivFormmaqAn = bindings.getOperationBinding("createNivFormMaqAnn");
                createNivFormmaqAn.getParamsMap().put("IdMaquette", Integer.parseInt(mq_id.toString()));
                createNivFormmaqAn.getParamsMap().put("IdAnneesUnivers", Integer.parseInt(an_id.toString()));
                createNivFormmaqAn.getParamsMap().put("IdNiveauFormation", Integer.parseInt(nf_id.toString()));
                createNivFormmaqAn.getParamsMap().put("UtiCree", getUtilisateur());
                createNivFormmaqAn.execute();
            } else {
                RichPopup popup = this.getPopupNivFormMaqAnnExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

            OperationBinding getmaqnivforman = bindings.getOperationBinding("getMaqetteNivForm");
            getmaqnivforman.getParamsMap().put("annee_id", Integer.parseInt(an_id.toString()));
            getmaqnivforman.getParamsMap().put("niv_id", Integer.parseInt(nf_id.toString()));
            getmaqnivforman.execute();

            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
            maqparcrs.getParamsMap().put("id_prcrs", Integer.parseInt(nfp_id.toString()));
            maqparcrs.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivFormAn());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivFormAn());
    }

    public void setPopupNivFormMaqAnnExist(RichPopup popupNivFormMaqAnnExist) {
        this.popupNivFormMaqAnnExist = popupNivFormMaqAnnExist;
    }

    public RichPopup getPopupNivFormMaqAnnExist() {
        return popupNivFormMaqAnnExist;
    }

    public void setMaqNivPan(RichPanelHeader maqNivPan) {
        this.maqNivPan = maqNivPan;
    }

    public RichPanelHeader getMaqNivPan() {
        return maqNivPan;
    }

    @SuppressWarnings("unchecked")
    public void getMaquetteNivForm() {
        BindingContainer bindings = getBindings();
        /*AttributeBinding an_id = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers3");
        AttributeBinding nf_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormation2");
        AttributeBinding prcrs_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours1");
        */
        DCIteratorBinding iterNivForm = (DCIteratorBinding) bindings.get("NiveauFormationROIterator");
        Row crntnivform = iterNivForm.getCurrentRow();
        
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("NivFormParcoursROIterator");
        Row crntprcrs = iterPrcrs.getCurrentRow();
        if ((crntnivform != null)) {
            OperationBinding getmaqnivforman = bindings.getOperationBinding("getMaqetteNivForm");
            getmaqnivforman.getParamsMap().put("annee_id", getAnnee());
            getmaqnivforman.getParamsMap().put("niv_id", crntnivform.getAttribute("IdNiveauFormation"));
            getmaqnivforman.execute();

            OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
            maqparcrs.getParamsMap().put("id_prcrs", crntprcrs.getAttribute("IdNiveauFormationParcours"));
            maqparcrs.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivFormAn());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());
        }
    }

    public void setMaqNivFormAn(RichSelectOneChoice maqNivFormAn) {
        this.maqNivFormAn = maqNivFormAn;
    }

    public RichSelectOneChoice getMaqNivFormAn() {
        return maqNivFormAn;
    }

    @SuppressWarnings("unchecked")
    public void onAnChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());

        AttributeBinding an_id = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers3");
        AttributeBinding nf_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormation2");
        AttributeBinding prcrs_id = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours1");
        if ((nf_id != null) && (an_id != null)) {
            OperationBinding getmaqnivforman = bindings.getOperationBinding("getMaqetteNivForm");
            getmaqnivforman.getParamsMap().put("annee_id", Integer.parseInt(an_id.toString()));
            getmaqnivforman.getParamsMap().put("niv_id", Integer.parseInt(nf_id.toString()));
            getmaqnivforman.execute();

            OperationBinding maqparcrs = bindings.getOperationBinding("getMaquettesParcours");
            maqparcrs.getParamsMap().put("id_prcrs", Integer.parseInt(prcrs_id.toString()));
            maqparcrs.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivPan());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqNivFormAn());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqPrcrsTable());
        }
    }

    public void setMaqprcrspan(RichPanelHeader maqprcrspan) {
        this.maqprcrspan = maqprcrspan;
    }

    public RichPanelHeader getMaqprcrspan() {
        return maqprcrspan;
    }

    public void setPrcrsmaqpangrp(RichPanelGroupLayout prcrsmaqpangrp) {
        this.prcrsmaqpangrp = prcrsmaqpangrp;
    }

    public RichPanelGroupLayout getPrcrsmaqpangrp() {
        return prcrsmaqpangrp;
    }

    public void setStructure(Integer structure) {
        this.structure = structure;
    }

    public Integer getStructure() {
        return structure;
    }

    @SuppressWarnings("unchecked")
    public void OnShowMaquetteClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        if (currentPrcrs != null) {
            if (null == currentPrcrs.getAttribute("Codeoptionscol")) {
                try {
                    OperationBinding isMaq = bindings.getOperationBinding("isAncMaquetteExiste");
                    isMaq.getParamsMap().put("annee", new Long(getAnnee()));
                    isMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    isMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    Long isM = (Long) isMaq.execute();
                    if (0 == isM) {
                        //Pas d'option
                        OperationBinding lesFilUes = BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .getOperationBinding("GetScolFilUe");
                        lesFilUes.getParamsMap().put("an_id", getAnnee());
                        lesFilUes.getParamsMap()
                            .put("niv_form", Long.parseLong(currentPrcrs.getAttribute("AncienCode").toString()));
                        lesFilUes.execute();

                        DCIteratorBinding iterfue = (DCIteratorBinding) bindings.get("ScolFilUeIterator");
                        if (0 != iterfue.getEstimatedRowCount()) {
                            RichPopup popup = this.getPopupScolMaq();
                            RichPopup.PopupHints ph = new RichPopup.PopupHints();
                            popup.show(ph);
                        } else {
                            RichPopup popup = this.getPopupNoScolMaq();
                            RichPopup.PopupHints ph = new RichPopup.PopupHints();
                            popup.show(ph);
                        }
                    } 
                    else {
                        OperationBinding listOptions = bindings.getOperationBinding("ExecuteWithParams");
                        listOptions.getParamsMap().put("annee", new Long(getAnnee()));
                        listOptions.getParamsMap()
                            .put("code_niv", Long.parseLong(currentPrcrs.getAttribute("AncienCode").toString()));
                        listOptions.getParamsMap()
                            .put("code_fr", Long.parseLong(currentPrcrs.getAttribute("Scolcodefrr").toString()));
                        listOptions.execute();
                        DCIteratorBinding iterop = (DCIteratorBinding) bindings.get("OptionANCNOParcoursIterator");
                        if (0 != iterop.getEstimatedRowCount()) {
                            RichPopup popup = this.getPopupScolMaqOpANCNO();
                            RichPopup.PopupHints ph = new RichPopup.PopupHints();
                            popup.show(ph);
                        } else {
                            RichPopup popup = this.getPopupNoScolMaq();
                            RichPopup.PopupHints ph = new RichPopup.PopupHints();
                            popup.show(ph);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } 
            else {
                try {
                    OperationBinding lesFilUesop = bindings.getOperationBinding("getScolFilUeOption");
                    lesFilUesop.getParamsMap().put("an_id", getAnnee());
                    lesFilUesop.getParamsMap().put("niv_form", currentPrcrs.getAttribute("AncienCode"));
                    lesFilUesop.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
                    lesFilUesop.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    lesFilUesop.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                DCIteratorBinding iterfue = (DCIteratorBinding) bindings.get("ScolFilUeOptionsIterator");
                if (0 != iterfue.getEstimatedRowCount()) {
                    RichPopup popup = this.getPopupScolMaqOp();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                } else {
                    RichPopup popup = this.getPopupNoScolMaq();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }
            }
        }
    }

    public void setPopupScolMaq(RichPopup popupScolMaq) {
        this.popupScolMaq = popupScolMaq;
    }

    public RichPopup getPopupScolMaq() {
        return popupScolMaq;
    }

    @SuppressWarnings("unchecked")
    public void isMaquetteValide(Long parcours, String maq_id) {
        if ((null != parcours) && (null != maq_id)) {
            if(null != maq_id){
                sessionScope.put("mq_id", maq_id);
            }else{
                sessionScope.put("mq_id", 0);
                }
            try {
                OperationBinding opvalidMaq = getBindings().getOperationBinding("IsMaquetteValidated");
                opvalidMaq.getParamsMap().put("prcrs", parcours);
                opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                opvalidMaq.getParamsMap().put("mq_id", maq_id);
                Long valid_mq_id = (Long) opvalidMaq.execute();
                if (0 != valid_mq_id) {
                    sessionScope.put("is_maq_val", 1);
                } else {
                    sessionScope.put("is_maq_val", 0);
                }
                //AdfFacesContext.getCurrentInstance().addPartialTarget(getPanBtn());
            } catch (Exception e) {
                sessionScope.put("is_maq_val", 0);
                System.out.println(e);
            }
            
            try {
                OperationBinding opClosed = getBindings().getOperationBinding("IsOneInterClosed");
                opClosed.getParamsMap().put("prcrs", parcours);
                opClosed.getParamsMap().put("an_id", new Long(getAnnee()));
                opClosed.getParamsMap().put("mq_id", maq_id);
                Integer isClosed = (Integer) opClosed.execute();
                if (0 != isClosed) {
                    sessionScope.put("is_one_closed", 1);
                } else {
                    sessionScope.put("is_one_closed", 0);
                }
                //AdfFacesContext.getCurrentInstance().addPartialTarget(getPanBtn());
            } catch (Exception e) {
                sessionScope.put("is_one_closed", 0);
                System.out.println(e);
            }
        } else {
            sessionScope.put("is_one_closed", 0);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnMaqImported());
    }

    @SuppressWarnings("unchecked")
    public void OnValideMaquetteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        Map<String,Long>  validMaq = new HashMap<String,Long> ();
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        if (null != currentPrcrs) {
            if (null == currentPrcrs.getAttribute("Codeoptionscol")) {
                try {
                    OperationBinding isMaq = bindings.getOperationBinding("isAncMaquetteExiste");
                    isMaq.getParamsMap().put("annee", new Long(getAnnee()));
                    isMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    isMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    Long isM = (Long) isMaq.execute();
                    if (0 == isM) {
                        try {
                            OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                            generateMaq.getParamsMap()
                                .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                            generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                            generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                            generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                            generateMaq.getParamsMap().put("op_code", null);
                            generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                            generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                            Long mq_id = (Long) generateMaq.execute();
                            sessionScope.put("mq_id", mq_id);
                            if (mq_id != 0) {
                                validMaq.put("mq_id", mq_id);
                                //validation maq
                                try {
                                    OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                                    opvalidMaq.getParamsMap().put("maq_id", mq_id);
                                    opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                                    opvalidMaq.getParamsMap()
                                        .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                    opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                    Integer valid_mq_id = (Integer) opvalidMaq.execute();
                                    validMaq.put("valid_mq_id", new Long(valid_mq_id));
                                    
                                    if (0 != valid_mq_id) {
                                        sessionScope.put("is_maq_val", 1);
                                    } else {
                                        sessionScope.put("is_maq_val", 0);
                                    }
                                } 
                                catch (Exception e) {
                                    System.out.println(e);
                                    sessionScope.put("is_maq_val", 0);
                                    sessionScope.put("mq_id", 0);
                                }
                                //nf maq an
                                try {
                                    OperationBinding opnfMaq =
                                        bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                                    opnfMaq.getParamsMap().put("mq", mq_id);
                                    opnfMaq.getParamsMap().put("an", new Long(getAnnee()));
                                    opnfMaq.getParamsMap()
                                        .put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                                    opnfMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                    Long nf_ma_id = (Long)opnfMaq.execute();
                                    validMaq.put("nf_ma_id", nf_ma_id);
                                } 
                                catch (Exception e) {
                                    System.out.println(e);
                                    sessionScope.put("is_maq_val", 0);
                                    sessionScope.put("mq_id", 0);
                                }
                                //prcrs maq an
                                try {
                                    OperationBinding opprcrsMaq =
                                        bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                                    opprcrsMaq.getParamsMap().put("mq", mq_id);
                                    opprcrsMaq.getParamsMap().put("an", new Long(getAnnee()));
                                    opprcrsMaq.getParamsMap()
                                        .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                    opprcrsMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                    Long prcrs_mq_id = (Long) opprcrsMaq.execute();
                                    validMaq.put("prcrs_mq_id", prcrs_mq_id);
                                } 
                                catch (Exception e) {
                                    System.out.println(e);
                                    sessionScope.put("is_maq_val", 0);
                                    sessionScope.put("mq_id", 0);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            sessionScope.put("is_maq_val", 0);
                            sessionScope.put("mq_id", 0);
                        }
                    } else {
                        DCIteratorBinding iterOp = (DCIteratorBinding) bindings.get("OptionANCNOParcoursIterator");
                        Row currentOption = iterOp.getCurrentRow();
                        OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                        generateMaq.getParamsMap()
                            .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                        generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                        generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                        generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                        generateMaq.getParamsMap().put("op_code", currentOption.getAttribute("Code"));
                        generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                        generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                        Long mq_id = (Long) generateMaq.execute();
                        sessionScope.put("mq_id", mq_id);
                        if (mq_id != 0) {
                            /*try {
                                OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                                opvalidMaq.getParamsMap().put("maq_id", mq_id);
                                opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                                opvalidMaq.getParamsMap()
                                    .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                Integer valid_mq_id = (Integer) opvalidMaq.execute();
                                if (0 != valid_mq_id) {
                                    sessionScope.put("is_maq_val", 1);
                                } else {
                                    sessionScope.put("is_maq_val", 0);
                                }
                            }
                            catch (Exception e) {
                                System.out.println(e);
                                sessionScope.put("is_maq_val", 0);
                                sessionScope.put("mq_id", 0);
                            }*/


                            validMaq.put("mq_id", mq_id);
                            //validation maq
                            try {
                                OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                                opvalidMaq.getParamsMap().put("maq_id", mq_id);
                                opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                                opvalidMaq.getParamsMap()
                                    .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                Integer valid_mq_id = (Integer) opvalidMaq.execute();
                                validMaq.put("valid_mq_id", new Long(valid_mq_id));

                                if (0 != valid_mq_id) {
                                    sessionScope.put("is_maq_val", 1);
                                } else {
                                    sessionScope.put("is_maq_val", 0);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                                sessionScope.put("is_maq_val", 0);
                                sessionScope.put("mq_id", 0);
                            }
                            //nf maq an
                            try {
                                OperationBinding opnfMaq = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                                opnfMaq.getParamsMap().put("mq", mq_id);
                                opnfMaq.getParamsMap().put("an", new Long(getAnnee()));
                                opnfMaq.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                                opnfMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                Long nf_ma_id = (Long) opnfMaq.execute();
                                validMaq.put("nf_ma_id", nf_ma_id);
                            } catch (Exception e) {
                                System.out.println(e);
                                sessionScope.put("is_maq_val", 0);
                                sessionScope.put("mq_id", 0);
                            }
                            //prcrs maq an
                            try {
                                OperationBinding opprcrsMaq = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                                opprcrsMaq.getParamsMap().put("mq", mq_id);
                                opprcrsMaq.getParamsMap().put("an", new Long(getAnnee()));
                                opprcrsMaq.getParamsMap()
                                    .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                opprcrsMaq.getParamsMap().put("utilisateur", getUtilisateur());
                                Long prcrs_mq_id = (Long) opprcrsMaq.execute();
                                validMaq.put("prcrs_mq_id", prcrs_mq_id);
                            } catch (Exception e) {
                                System.out.println(e);
                                sessionScope.put("is_maq_val", 0);
                                sessionScope.put("mq_id", 0);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    sessionScope.put("mq_id", 0);
                }
            } else {
                try {
                    OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                    generateMaq.getParamsMap()
                        .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                    generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                    generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    generateMaq.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
                    generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                    Long mq_id = (Long) generateMaq.execute();
                    sessionScope.put("mq_id", mq_id);
                    if (mq_id != 0) {
                        /*try {
                            OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                            opvalidMaq.getParamsMap().put("maq_id", mq_id);
                            opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                            opvalidMaq.getParamsMap()
                                .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                            opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                            Integer valid_mq_id = (Integer) opvalidMaq.execute();
                            if (0 != valid_mq_id) {
                                sessionScope.put("is_maq_val", 1);
                            } else {
                                sessionScope.put("is_maq_val", 0);
                            }
                        }
                        catch (Exception e) {
                            System.out.println(e);
                            sessionScope.put("is_maq_val", 0);
                            sessionScope.put("mq_id", 0);
                        }*/


                        validMaq.put("mq_id", mq_id);
                        //validation maq
                        try {
                            OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                            opvalidMaq.getParamsMap().put("maq_id", mq_id);
                            opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                            opvalidMaq.getParamsMap()
                                .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                            opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                            Integer valid_mq_id = (Integer) opvalidMaq.execute();
                            validMaq.put("valid_mq_id", new Long(valid_mq_id));

                            if (0 != valid_mq_id) {
                                sessionScope.put("is_maq_val", 1);
                            } else {
                                sessionScope.put("is_maq_val", 0);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            sessionScope.put("is_maq_val", 0);
                            sessionScope.put("mq_id", 0);
                        }
                        //nf maq an
                        try {
                            OperationBinding opnfMaq = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                            opnfMaq.getParamsMap().put("mq", mq_id);
                            opnfMaq.getParamsMap().put("an", new Long(getAnnee()));
                            opnfMaq.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                            opnfMaq.getParamsMap().put("utilisateur", getUtilisateur());
                            Long nf_ma_id = (Long) opnfMaq.execute();
                            validMaq.put("nf_ma_id", nf_ma_id);
                        } catch (Exception e) {
                            System.out.println(e);
                            sessionScope.put("is_maq_val", 0);
                            sessionScope.put("mq_id", 0);
                        }
                        //prcrs maq an
                        try {
                            OperationBinding opprcrsMaq = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                            opprcrsMaq.getParamsMap().put("mq", mq_id);
                            opprcrsMaq.getParamsMap().put("an", new Long(getAnnee()));
                            opprcrsMaq.getParamsMap()
                                .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                            opprcrsMaq.getParamsMap().put("utilisateur", getUtilisateur());
                            Long prcrs_mq_id = (Long) opprcrsMaq.execute();
                            validMaq.put("prcrs_mq_id", prcrs_mq_id);
                        } catch (Exception e) {
                            System.out.println(e);
                            sessionScope.put("is_maq_val", 0);
                            sessionScope.put("mq_id", 0);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    sessionScope.put("is_maq_val", 0);
                    sessionScope.put("mq_id", 0);
                }
            }
            System.out.println(validMaq.get("valid_mq_id"));
            System.out.println(validMaq.get("prcrs_mq_id"));
            /*sessionScope.putAll(validMaq);
            Map<String,Long> vm = new HashMap<String,Long>(sessionScope.get("validMaq"));
            System.out.println();*/
        }
        
        if (0 == Integer.parseInt(sessionScope.get("is_maq_val").toString())) {
            RichPopup popup = this.getPopupValidMaqErr();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        } 
        else {
            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
            ViewObject vo = iter.getViewObject();
            vo.executeQuery();
            if (null == currentPrcrs.getAttribute("Codeoptionscol")) {
                try {
                    OperationBinding isMaq = bindings.getOperationBinding("isAncMaquetteExiste");
                    isMaq.getParamsMap().put("annee", new Long(getAnnee()));
                    isMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    isMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    Long isM = (Long) isMaq.execute();
                    if (0 == isM) {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnPanGrp());
                    } else {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnPanGrpOpANCNO());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnPanGrpOp());
            }
            RichPopup popup = this.getPopupMaqvalidated();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        }
    }

    @SuppressWarnings("unchecked")
    public void OnImportMaquetteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //getRespFr
        //getRespNivFr
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        if (currentPrcrs != null) {
            /*DCIteratorBinding iterUe = (DCIteratorBinding)bindings.get("ScolFilUeIterator");
            ViewObject voUe=iterUe.getViewObject();
            voUe.executeQuery();
            DCIteratorBinding iterEc = (DCIteratorBinding)bindings.get("ScolFilEcIterator");
            ViewObject voEc=iterEc.getViewObject();
            voEc.executeQuery();*/
            Long mq_id = new Long(0);
            Long niv_mq_id = new Long(0);
            Long prcrs_mq_id = new Long(0);
            String Lib = null;
            Long ue_id = new Long(0);
            Long fil_ue_id = new Long(0);
            Long user_id = new Long(0);
            Long ec_id = new Long(0);
            Long fil_ec_id = new Long(0);
            String msg = null;
            ArrayList<Long> semestres = new ArrayList<Long>();
            if (null != currentPrcrs.getAttribute("Codeoptionscol")) {
                try {
                    OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                    generateMaq.getParamsMap()
                        .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                    generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                    generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    generateMaq.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
                    generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                    mq_id = (Long) generateMaq.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                OperationBinding isMaq = bindings.getOperationBinding("isAncMaquetteExiste");
                isMaq.getParamsMap().put("annee", new Long(getAnnee()));
                isMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                isMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                Long isM = (Long) isMaq.execute();
                if (0 == isM) {
                    OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                    generateMaq.getParamsMap()
                        .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                    generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                    generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                    generateMaq.getParamsMap().put("op_code", null);
                    generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                    mq_id = (Long) generateMaq.execute();
                } else {
                    mq_id = new Long(0);
                }
            }
            if (mq_id != 0) {
                try {
                    OperationBinding opNivFormMaqAn = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                    opNivFormMaqAn.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                    opNivFormMaqAn.getParamsMap().put("mq", mq_id);
                    opNivFormMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opNivFormMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    niv_mq_id = (Long) opNivFormMaqAn.execute();
                    try {
                        OperationBinding opPrcrsMaqAn = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                        opPrcrsMaqAn.getParamsMap()
                            .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                        opPrcrsMaqAn.getParamsMap().put("mq", mq_id);
                        opPrcrsMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                        opPrcrsMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                        prcrs_mq_id = (Long) opPrcrsMaqAn.execute();
                    } catch (Exception e) {
                        System.out.println("In createOrUpdatePrcrsMaqAnn exception " + e);
                    }
                    if (niv_mq_id != 0 && prcrs_mq_id != 0) {
                        sessionScope.put("pma_id", prcrs_mq_id);
                        DCIteratorBinding iterfilue = null;
                        if (null != currentPrcrs.getAttribute("Codeoptionscol")) {
                            iterfilue = (DCIteratorBinding) bindings.get("ScolFilUeIterator");

                        } else {
                            iterfilue = (DCIteratorBinding) bindings.get("ScolFilUeOptionsIterator");
                        }
                        iterfilue.getViewObject().executeQuery();
                        //Row current = iterfilue.getCurrentRow();
                        //coler ici
                    }


                } catch (Exception e) {
                    System.out.println("In createOrUpdatePrcrsMaqAnn exception " + e);
                }
            }

            for (Long sem_id : semestres) {
                Integer nbr_cal = 0;
                Long gdrSem_id = new Long(0);
                try {
                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                    is_exist.getParamsMap().put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                    is_exist.getParamsMap().put("sem_id", sem_id);
                    is_exist.getParamsMap().put("an_id", new Long(getAnnee()));
                    nbr_cal = (Integer) is_exist.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 == nbr_cal) {
                    Long cal_id = new Long(0);
                    try {
                        OperationBinding opnewcal = bindings.getOperationBinding("CreateOrUpdateCalendar");
                        opnewcal.getParamsMap()
                            .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                        opnewcal.getParamsMap().put("sem_id", sem_id);
                        opnewcal.getParamsMap().put("an_id", new Long(getAnnee()));
                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                        opnewcal.getParamsMap().put("sess_id", 1);
                        cal_id = new Long((Integer) opnewcal.execute());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                try {
                    OperationBinding opgs = bindings.getOperationBinding("CreateOrUpdateGrdSemestre");
                    opgs.getParamsMap().put("niv_id", currentPrcrs.getAttribute("IdNiveau"));
                    opgs.getParamsMap().put("gf_id", currentPrcrs.getAttribute("IdGradesFormation"));
                    opgs.getParamsMap().put("sem_id", sem_id);
                    opgs.getParamsMap().put("utilisateur", getUtilisateur());
                    gdrSem_id = (Long) opgs.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }

                //supprimer CC et Update %Ct
                try {
                    OperationBinding opdelcc = bindings.getOperationBinding("DeleteCCUpdatePrc");
                    opdelcc.getParamsMap().put("pma_id", prcrs_mq_id);
                    opdelcc.getParamsMap().put("str_id", new Long(getStructure()));
                    opdelcc.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } 
        this.getPopupScolMaq().hide();
        OperationBinding opfilUE = getBindings().getOperationBinding("getFilUeMaqIntegrated");

        opfilUE.getParamsMap().put("an_id", new Long(getAnnee()));
        opfilUE.getParamsMap().put("str_id", new Long(getStructure()));
        opfilUE.getParamsMap().put("mq_id", sessionScope.get("mq_id"));
        opfilUE.execute();
        DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("MaquetteIntegratedNivSecIterator");
        ViewObject voUe = iterUe.getViewObject();
        voUe.executeQuery();
        DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("FilEcIterator");
        ViewObject voEc = iterEc.getViewObject();
        voEc.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnMaqImported());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanimport());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUe());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEc());

    }

    public void setPanBtn(RichPanelGroupLayout panBtn) {
        this.panBtn = panBtn;
    }

    public RichPanelGroupLayout getPanBtn() {
        return panBtn;
    }

    public void setBtnPanGrp(RichPanelGroupLayout btnPanGrp) {
        this.btnPanGrp = btnPanGrp;
    }

    public RichPanelGroupLayout getBtnPanGrp() {
        return btnPanGrp;
    }

    public void setBtnMaqImported(RichPanelGroupLayout btnMaqImported) {
        this.btnMaqImported = btnMaqImported;
    }

    public RichPanelGroupLayout getBtnMaqImported() {
        return btnMaqImported;
    }

    @SuppressWarnings("unchecked")
    public void onImportEtudiantScolAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iter.getCurrentRow();
        DCIteratorBinding iterMQV = (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
        Row currentMQV = iterMQV.getCurrentRow();
        if (null != currentMQV && null != currentPrcrs) {
            try {
                OperationBinding opPrcrsMaq = bindings.getOperationBinding("getParcrsMaquette");
                opPrcrsMaq.getParamsMap().put("annee", new Long(getAnnee()));
                opPrcrsMaq.getParamsMap().put("prcrs", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                opPrcrsMaq.getParamsMap().put("maq", currentMQV.getAttribute("IdMaquette"));
                opPrcrsMaq.execute();
            } catch (Exception e) {
                System.out.print(e);
            }
            DCIteratorBinding iterPMA = (DCIteratorBinding) bindings.get("getPrcrsMaquetteAnneeROVO1Iterator");
            Row currentPMA = iterPMA.getCurrentRow();
            if (null != currentPMA) {
                if (null == currentPrcrs.getAttribute("Codeoptionscol")) {
                    
                    try {
                        OperationBinding isMaq = bindings.getOperationBinding("isAncMaquetteExiste");
                        isMaq.getParamsMap().put("annee", new Long(getAnnee()));
                        isMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                        isMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                        Long isM = (Long) isMaq.execute();
                        System.out.println("isAncMaquetteExiste : "+isM);
                        if (0 == isM) {
                            try {
                                OperationBinding opimport = bindings.getOperationBinding("ImportEtudiants");
                                //opimport.getParamsMap().put("prcrs", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                opimport.getParamsMap().put("prcrs", currentPMA.getAttribute("IdParcoursMaquetAnnee"));
                                opimport.getParamsMap().put("code_niv_s", currentPrcrs.getAttribute("AncienCode"));
                                opimport.getParamsMap().put("annee", new Long(this.getAnnee()));
                                opimport.getParamsMap().put("code_op", currentPrcrs.getAttribute("Codeoptionscol"));
                                opimport.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                                opimport.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                opimport.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } 
                        else {
                            DCIteratorBinding iterMq =
                                (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
                            Row currentMq = iterMq.getCurrentRow();
                            String[] parts = currentMq.getAttribute("LibelleLong")
                                                      .toString()
                                                      .split("-");
                            try {
                                OperationBinding opimport = bindings.getOperationBinding("OptionByLibelle");
                                opimport.getParamsMap().put("lib", parts[parts.length - 1]);
                                opimport.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            DCIteratorBinding iterOption = (DCIteratorBinding) bindings.get("OptionByLibelleIterator");
                            Row currentOption = iterOption.getCurrentRow();
                            if (null != currentOption) {
                                System.out.println("opCode : "+currentOption.getAttribute("Code"));
                                try {
                                    OperationBinding opimport = bindings.getOperationBinding("ImportEtudiants");
                                    //opimport.getParamsMap().put("prcrs", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                                    opimport.getParamsMap()
                                        .put("prcrs", currentPMA.getAttribute("IdParcoursMaquetAnnee"));
                                    opimport.getParamsMap().put("code_niv_s", currentPrcrs.getAttribute("AncienCode"));
                                    opimport.getParamsMap().put("annee", new Long(this.getAnnee()));
                                    opimport.getParamsMap().put("code_op", currentOption.getAttribute("Code"));
                                    opimport.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                                    opimport.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                                    opimport.execute();

                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    } 
                    catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    try {
                        OperationBinding opimport = bindings.getOperationBinding("ImportEtudiants");
                        //opimport.getParamsMap().put("prcrs", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                        opimport.getParamsMap().put("prcrs", currentPMA.getAttribute("IdParcoursMaquetAnnee"));
                        opimport.getParamsMap().put("code_niv_s", currentPrcrs.getAttribute("AncienCode"));
                        opimport.getParamsMap().put("annee", new Long(this.getAnnee()));
                        opimport.getParamsMap().put("code_op", currentPrcrs.getAttribute("Codeoptionscol"));
                        opimport.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                        opimport.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        opimport.execute();
                        //Show popup nombre d'etudiants importé
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                try {
                    OperationBinding opcountimported = bindings.getOperationBinding("TotalImpoted");
                    //opcountimported.getParamsMap().put("prcrs", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    opcountimported.getParamsMap().put("prcrs", currentPMA.getAttribute("IdParcoursMaquetAnnee"));
                    opcountimported.getParamsMap().put("code_niv_s", currentPrcrs.getAttribute("AncienCode"));
                    //opcountimported.getParamsMap().put("annee", new Long(this.getAnnee()));
                    //opcountimported.getParamsMap().put("code_op", currentPrcrs.getAttribute("Codeoptionscol"));
                    opcountimported.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                    Integer nbre = (Integer) opcountimported.execute();
                    sessionScope.put("totalImported", nbre);
                    RichPopup pop = this.getPopuptotalStudent();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    pop.show(ph);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void setPopupNoStudent(RichPopup popupNoStudent) {
        this.popupNoStudent = popupNoStudent;
    }

    public RichPopup getPopupNoStudent() {
        return popupNoStudent;
    }

    public void setTestbtn(RichButton testbtn) {
        this.testbtn = testbtn;
    }

    public RichButton getTestbtn() {
        return testbtn;
    }

    public void onTest(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPopuptotalStudent(RichPopup popuptotalStudent) {
        this.popuptotalStudent = popuptotalStudent;
    }

    public RichPopup getPopuptotalStudent() {
        return popuptotalStudent;
    }

    public void setFilUePanHdr(RichPanelHeader filUePanHdr) {
        this.filUePanHdr = filUePanHdr;
    }

    public RichPanelHeader getFilUePanHdr() {
        return filUePanHdr;
    }

    public void setFilUePanCol(RichPanelCollection filUePanCol) {
        this.filUePanCol = filUePanCol;
    }

    public RichPanelCollection getFilUePanCol() {
        return filUePanCol;
    }

    public void setFilUe(RichTable filUe) {
        this.filUe = filUe;
    }

    public RichTable getFilUe() {
        return filUe;
    }

    public void setFilEcPanHdr(RichPanelHeader filEcPanHdr) {
        this.filEcPanHdr = filEcPanHdr;
    }

    public RichPanelHeader getFilEcPanHdr() {
        return filEcPanHdr;
    }

    public void setFilEcPanCol(RichPanelCollection filEcPanCol) {
        this.filEcPanCol = filEcPanCol;
    }

    public RichPanelCollection getFilEcPanCol() {
        return filEcPanCol;
    }

    public void setFilEc(RichTable filEc) {
        this.filEc = filEc;
    }

    public RichTable getFilEc() {
        return filEc;
    }

    public void setPopupNoScolMaq(RichPopup popupNoScolMaq) {
        this.popupNoScolMaq = popupNoScolMaq;
    }

    public RichPopup getPopupNoScolMaq() {
        return popupNoScolMaq;
    }

    public void setPopupMaqvalidated(RichPopup popupMaqvalidated) {
        this.popupMaqvalidated = popupMaqvalidated;
    }

    public RichPopup getPopupMaqvalidated() {
        return popupMaqvalidated;
    }

    public void setPopupValidMaqErr(RichPopup popupValidMaqErr) {
        this.popupValidMaqErr = popupValidMaqErr;
    }

    public RichPopup getPopupValidMaqErr() {
        return popupValidMaqErr;
    }

    public void setPopupScolMaqOp(RichPopup popupScolMaqOp) {
        this.popupScolMaqOp = popupScolMaqOp;
    }

    public RichPopup getPopupScolMaqOp() {
        return popupScolMaqOp;
    }

    @SuppressWarnings("unchecked")
    public void OnValidateMaquetteOptAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        
        try {
            OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
            generateMaq.getParamsMap().put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
            generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
            generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
            generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
            generateMaq.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
            generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
            Long mq_id = (Long) generateMaq.execute();
            if (mq_id != 0) {
                try {
                    OperationBinding opvalidMaq = bindings.getOperationBinding("ValidateMaquette");
                    opvalidMaq.getParamsMap().put("maq_id", mq_id);
                    opvalidMaq.getParamsMap().put("an_id", new Long(getAnnee()));
                    opvalidMaq.getParamsMap().put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    opvalidMaq.getParamsMap().put("utilisateur", getUtilisateur());
                    Integer valid_mq_id = (Integer) opvalidMaq.execute();
                    if (0 != valid_mq_id) {
                        sessionScope.put("is_maq_val", 1);
                    } else {
                        sessionScope.put("is_maq_val", 0);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    sessionScope.put("is_maq_val", 0);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            sessionScope.put("is_maq_val", 0);
        }
        if (0 == Integer.parseInt(sessionScope.get("is_maq_val").toString())) {
            RichPopup popup = this.getPopupValidMaqErr();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        } else {

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnPanGrp());
            RichPopup popup = this.getPopupMaqvalidated();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        }
    }

    @SuppressWarnings("unchecked")
    public void OnImportMaquetteActionListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        if (currentPrcrs != null) {
            DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("ScolFilUeIterator");
            ViewObject voUe = iterUe.getViewObject();
            voUe.executeQuery();
            DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("ScolFilEcIterator");
            ViewObject voEc = iterEc.getViewObject();
            voEc.executeQuery();
            Long mq_id = Long.parseLong(sessionScope.get("mq_id").toString());
            Long niv_mq_id = new Long(0);
            Long prcrs_mq_id = new Long(0);
            String Lib = null;
            Long ue_id = new Long(0);
            Long fil_ue_id = new Long(0);
            Long user_id = new Long(0);
            Long ec_id = new Long(0);
            Long fil_ec_id = new Long(0);
            String msg = null;
            ArrayList<Long> semestres = new ArrayList<Long>();
            /*try {
                OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                generateMaq.getParamsMap()
                    .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                generateMaq.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
                generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                mq_id = (Long) generateMaq.execute();
            } catch (Exception e) {
                System.out.println(e);
            }*/
            if (mq_id != 0) {
                try {
                    OperationBinding opNivFormMaqAn = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                    opNivFormMaqAn.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                    opNivFormMaqAn.getParamsMap().put("mq", mq_id);
                    opNivFormMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opNivFormMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    niv_mq_id = (Long) opNivFormMaqAn.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                try {
                    OperationBinding opPrcrsMaqAn = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                    opPrcrsMaqAn.getParamsMap().put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    opPrcrsMaqAn.getParamsMap().put("mq", mq_id);
                    opPrcrsMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opPrcrsMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    prcrs_mq_id = (Long) opPrcrsMaqAn.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (niv_mq_id != 0 && prcrs_mq_id != 0) {
                    sessionScope.put("pma_id", prcrs_mq_id);
                    DCIteratorBinding iterfilue = (DCIteratorBinding) bindings.get("ScolFilUeIterator");
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
                            OperationBinding opUe = bindings.getOperationBinding("createOrUpdateUe");
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
                            try {
                                OperationBinding opFilUe = bindings.getOperationBinding("createOrUpdateFilUe");
                                opFilUe.getParamsMap().put("anc_code", nextRow.getAttribute("CodeFiliereUeSemestre"));
                                opFilUe.getParamsMap().put("ue_id", ue_id);
                                opFilUe.getParamsMap()
                                    .put("sem_id", Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                                opFilUe.getParamsMap().put("cat_ue_id", nextRow.getAttribute("CodeCategorieUe"));
                                opFilUe.getParamsMap().put("codif", nextRow.getAttribute("CodificationUe"));
                                opFilUe.getParamsMap()
                                    .put("cred", Long.parseLong(nextRow.getAttribute("CreditUe").toString()));
                                opFilUe.getParamsMap().put("coef", Float.parseFloat(nextRow.getAttribute("Coefue")
                                                                                           .toString()
                                                                                           .replace(",", ".")));
                                opFilUe.getParamsMap().put("maq", mq_id);
                                //opFilUe.getParamsMap().put("moy_v",Integer.parseInt(nextRow.getAttribute("MoyenneValidation").toString()));
                                opFilUe.getParamsMap().put("moy_v", 0);
                                if (null == nextRow.getAttribute("MoyenneEliminatoire")) {
                                    opFilUe.getParamsMap().put("moy_e", 0);
                                } else {
                                    opFilUe.getParamsMap()
                                        .put("moy_e",
                                             Integer.parseInt(nextRow.getAttribute("MoyenneEliminatoire").toString()));
                                }
                                opFilUe.getParamsMap().put("nat", nextRow.getAttribute("Fondamental"));
                                opFilUe.getParamsMap().put("comp", nextRow.getAttribute("Compensable"));
                                opFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                fil_ue_id = (Long) opFilUe.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 != fil_ue_id) {
                                try {
                                    OperationBinding opUserFilUe =
                                        bindings.getOperationBinding("createOrUpdateUserFilUe");
                                    opUserFilUe.getParamsMap().put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                    opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                    opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                    opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                    opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                    opUserFilUe.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                //Donner le responsble Niveau acces aux ues
                                if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                    try {
                                        OperationBinding opUserFilUe =
                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                        opUserFilUe.getParamsMap()
                                            .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                        opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                        opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                        opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                        Long id_user_fil_ue = (Long) opUserFilUe.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                                String role = null;
                                try {
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", R_UE); //resp ue
                                    role = (String) oprole.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                //Responsable Ue
                                if (null != nextRow.getAttribute("Emailucad")) {
                                    try {
                                        OperationBinding opUser = bindings.getOperationBinding("createOrUpdateUser");
                                        opUser.getParamsMap()
                                            .put("email", nextRow.getAttribute("Emailucad").toString());
                                        opUser.getParamsMap()
                                            .put("struct_", nextRow.getAttribute("CodeEtablissement").toString());
                                        opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                        user_id = (Long) opUser.execute();
                                        if (0 != user_id) {
                                            //weblogic user
                                            try {
                                                String log = nextRow.getAttribute("Emailucad").toString();
                                                String matricule = nextRow.getAttribute("CodeMatricule").toString();
                                                //matricule = "passer123*";
                                                AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                try {
                                                    OperationBinding opUserForm =
                                                        bindings.getOperationBinding("createOrUpdateUserForm");
                                                    opUserForm.getParamsMap().put("user_", user_id);
                                                    opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                    opUserForm.getParamsMap()
                                                        .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                    opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opUserForm.execute();

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                try {
                                                    OperationBinding opUserNivForm =
                                                        bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                    opUserNivForm.getParamsMap().put("user_", user_id);
                                                    opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                    opUserNivForm.getParamsMap()
                                                        .put("niv_form_",
                                                             currentPrcrs.getAttribute("IdNiveauFormation"));
                                                    opUserNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opUserNivForm.execute();

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            try {
                                                //Ancien responsable
                                                OperationBinding opAncResp =
                                                    getBindings().getOperationBinding("getUserRespFilUe");
                                                opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                Long u_resp_id = (Long) opAncResp.execute();
                                                if (-1 != u_resp_id) {
                                                    if (!(user_id.equals(u_resp_id))) {
                                                        try {
                                                            OperationBinding opupd =
                                                                getBindings()
                                                                .getOperationBinding("UpdateResponsableUe");
                                                            opupd.getParamsMap().put("p_old", u_resp_id);
                                                            opupd.getParamsMap().put("p_new", user_id);
                                                            opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                            opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                            opupd.getParamsMap().put("p_user", getUtilisateur());
                                                            Long res = (Long) opupd.execute();
                                                            if (-1 ==
                                                                res) {
                                                                //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                if ((null != role) &&
                                                                    (null != nextRow.getAttribute("Emailucad"))) {
                                                                    role =
                                                                        role.substring(0, 1) +
                                                                        role.substring(1).toLowerCase();
                                                                    String username = null;
                                                                    try {
                                                                        OperationBinding opusername =
                                                                            bindings.getOperationBinding("getusername");
                                                                        opusername.getParamsMap()
                                                                            .put("user_id", u_resp_id);
                                                                        username = (String) opusername.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        if (null != username) {
                                                                            AdministrationBean.revokeWlsRole2User(role,
                                                                                                                  username);
                                                                        }
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
                                                                //Tjrs responsable de formation RAS;
                                                                if ((null != role) &&
                                                                    (null != nextRow.getAttribute("Emailucad"))) {
                                                                    role =
                                                                        role.substring(0, 1) +
                                                                        role.substring(1).toLowerCase();
                                                                    try {
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
                                } else {
                                    //Donner le responsable de niveau si not null les ues avec comme role responsable
                                    if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                        Long us_id =
                                            Long.parseLong(currentPrcrs.getAttribute("Responsablenivfr").toString());
                                        try {
                                            //Ancien responsable
                                            OperationBinding opAncResp =
                                                getBindings().getOperationBinding("getUserRespFilUe");
                                            opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                            Long u_resp_id = (Long) opAncResp.execute();
                                            if (0 == u_resp_id) { //mettre a jour que si le responsable n'etait pas parametrer
                                                //NB : si le responsable devrais chancher la condition serai  if (-1 != u_resp_id)
                                                try {
                                                    OperationBinding opupd =
                                                        getBindings().getOperationBinding("UpdateResponsableUe");
                                                    opupd.getParamsMap().put("p_old", u_resp_id);
                                                    opupd.getParamsMap().put("p_new", us_id);
                                                    opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                    opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                    opupd.getParamsMap().put("p_user", getUtilisateur());
                                                    Long res = (Long) opupd.execute();
                                                    if (0 == res) {
                                                        System.out.println("erreur dans le parametrage du nouveau resp");
                                                    } else {
                                                        String username = null;
                                                        try {
                                                            OperationBinding opusername =
                                                                bindings.getOperationBinding("getusername");
                                                            opusername.getParamsMap().put("user_id", us_id);
                                                            username = (String) opusername.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        if ((null != role) && (null != username)) {
                                                            role =
                                                                role.substring(0, 1) + role.substring(1).toLowerCase();
                                                            try {
                                                                AdministrationBean.assignWlsRole2User(role, username);
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
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
                                }
                            }
                            if (!semestres.contains(nextRow.getAttribute("CodeSemestre"))) {
                                semestres.add(Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                            }
                        } 
                        //Filiere Ec
                        DCIteratorBinding iterfilec = (DCIteratorBinding) bindings.get("ScolFilEcIterator");
                        RowSetIterator rsie = iterfilec.getViewObject().createRowSetIterator(null);
                        while (rsie.hasNext()) {
                            Row nextRowFilEc = rsie.next();
                            try {
                                OperationBinding opEc = bindings.getOperationBinding("createOrUpdateEc");
                                opEc.getParamsMap().put("anc_code", nextRowFilEc.getAttribute("CodeEc"));
                                opEc.getParamsMap().put("libelle", nextRowFilEc.getAttribute("LibLongEc"));
                                opEc.getParamsMap().put("utilisateur", getUtilisateur());
                                ec_id = (Long) opEc.execute();
                                //System.out.println(" ec_id : "+ec_id);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (ec_id != 0) {
                                try {
                                    OperationBinding opFilEc = bindings.getOperationBinding("createOrUpdateFilEc");
                                    opFilEc.getParamsMap()
                                        .put("anc_code", nextRowFilEc.getAttribute("CodeFiliereUeSemestreEc"));
                                    opFilEc.getParamsMap().put("ec_id", ec_id);
                                    opFilEc.getParamsMap().put("nat", nextRowFilEc.getAttribute("CodeNatureEc"));
                                    opFilEc.getParamsMap().put("codif", nextRowFilEc.getAttribute("CodificationEc"));
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
                                    opFilEc.getParamsMap().put("cc", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("ct", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCt")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("fus", fil_ue_id);
                                    opFilEc.getParamsMap().put("hcm", nextRowFilEc.getAttribute("HeureCm"));
                                    opFilEc.getParamsMap().put("htd", nextRowFilEc.getAttribute("HeureTd"));
                                    opFilEc.getParamsMap().put("htp", nextRowFilEc.getAttribute("HeureTp"));
                                    opFilEc.getParamsMap().put("htpe", nextRowFilEc.getAttribute("HeureTpe"));
                                    opFilEc.getParamsMap().put("hs", nextRowFilEc.getAttribute("HeureStage"));
                                    opFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                    fil_ec_id = (Long) opFilEc.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                //generer les modalites de saisie en fonction des %CC et %CT
                                if ((0 != fil_ec_id)) {
                                    String role = null;
                                    try {
                                        OperationBinding oprole = bindings.getOperationBinding("getrole");
                                        oprole.getParamsMap().put("role_id", R_EC); //resp ue
                                        role = (String) oprole.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }

                                    if (0 != Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                          .toString()
                                                                          .replace(",", "."))) {
                                        try {
                                            OperationBinding opmdecntrl =
                                                bindings.getOperationBinding("CreateOrUpdateModeCntrlEC");
                                            opmdecntrl.getParamsMap().put("fil_ec", fil_ec_id);
                                            opmdecntrl.getParamsMap().put("tc", 1);
                                            opmdecntrl.getParamsMap().put("mc", 1);
                                            opmdecntrl.getParamsMap().put("pma", prcrs_mq_id);
                                            opmdecntrl.getParamsMap().put("coef", 1);
                                            opmdecntrl.getParamsMap().put("utilisateur", getUtilisateur());
                                            opmdecntrl.execute();

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
                                            opmdecntrl1.getParamsMap().put("utilisateur", getUtilisateur());
                                            opmdecntrl1.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                    try {
                                        OperationBinding opUserFilEc =
                                            bindings.getOperationBinding("createOrUpdateUserFilEc");
                                        opUserFilEc.getParamsMap()
                                            .put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                        opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                        opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                        opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                        opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserFilEc.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                        try {
                                            OperationBinding opUserFilEc =
                                                bindings.getOperationBinding("createOrUpdateUserFilEc");
                                            opUserFilEc.getParamsMap()
                                                .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                            opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                            opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                            opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                            opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                            Long id = (Long) opUserFilEc.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                    //Responsable Ec
                                    if (null != nextRowFilEc.getAttribute("Emailucad")) {
                                        try {
                                            OperationBinding opUser =
                                                bindings.getOperationBinding("createOrUpdateUser");
                                            opUser.getParamsMap()
                                                .put("email", nextRowFilEc.getAttribute("Emailucad").toString());
                                            opUser.getParamsMap()
                                                .put("struct_",
                                                     nextRowFilEc.getAttribute("CodeEtablissement").toString());
                                            opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                            user_id = (Long) opUser.execute();
                                            if (0 != user_id) {
                                                try {
                                                    String log = nextRowFilEc.getAttribute("Emailucad").toString();
                                                    String matricule =
                                                        nextRowFilEc.getAttribute("CodeMatricule").toString();
                                                    //matricule = "passer123*";
                                                    AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                    try {
                                                        OperationBinding opUserForm =
                                                            bindings.getOperationBinding("createOrUpdateUserForm");
                                                        opUserForm.getParamsMap().put("user_", user_id);
                                                        opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserForm.getParamsMap()
                                                            .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                        opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                        opUserForm.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                } 
                                                if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                    try {
                                                        OperationBinding opUserNivForm =
                                                            bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                        opUserNivForm.getParamsMap().put("user_", user_id);
                                                        opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserNivForm.getParamsMap()
                                                            .put("niv_form_",
                                                                 currentPrcrs.getAttribute("IdNiveauFormation"));
                                                        opUserNivForm.getParamsMap()
                                                            .put("utilisateur", getUtilisateur());
                                                        opUserNivForm.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                } 
                                                try {
                                                    OperationBinding opUserFilUe =
                                                        bindings.getOperationBinding("createOrUpdateUserFilUe");
                                                    opUserFilUe.getParamsMap().put("user_", user_id);
                                                    opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                                    opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                                    opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                                    opUserFilUe.execute();
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                if ((null != role) &&
                                                    (null != nextRowFilEc.getAttribute("Emailucad").toString())) {
                                                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                    try {
                                                        //Ancien responsable
                                                        OperationBinding opAncResp =
                                                            getBindings().getOperationBinding("getUserRespFilEc");
                                                        opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                                        opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                        Long u_resp_id = (Long) opAncResp.execute();
                                                        if (-1 != u_resp_id) {
                                                            if (!(user_id.equals(u_resp_id))) {
                                                                try {
                                                                    OperationBinding opupd =
                                                                        getBindings()
                                                                        .getOperationBinding("UpdateResponsableEc");
                                                                    opupd.getParamsMap().put("p_old", u_resp_id);
                                                                    opupd.getParamsMap().put("p_new", user_id);
                                                                    opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                                    opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                                    opupd.getParamsMap()
                                                                        .put("p_user", getUtilisateur());
                                                                    Long res = (Long) opupd.execute();
                                                                    if (-1 == res) {
                                                                        //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                        String username = null;
                                                                        try {
                                                                            OperationBinding opusername =
                                                                                bindings.getOperationBinding("getusername");
                                                                            opusername.getParamsMap()
                                                                                .put("user_id", u_resp_id);
                                                                            username = (String) opusername.execute();
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        try {
                                                                            if (null != username) {
                                                                                AdministrationBean.revokeWlsRole2User(role,
                                                                                                                      username);
                                                                            }
                                                                            AdministrationBean.assignWlsRole2User(role,
                                                                                                                  nextRowFilEc.getAttribute("Emailucad")
                                                                                                                  .toString());
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        //}
                                                                    } else if (0 == res) {
                                                                        System.out.println("erreur dans le parametrage du nouveau resp");
                                                                    } else {
                                                                        //Tjrs responsable de formation RAS
                                                                        try {
                                                                            AdministrationBean.assignWlsRole2User(role,
                                                                                                                  nextRowFilEc.getAttribute("Emailucad")
                                                                                                                  .toString());
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
                                                }
                                            } 
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        //Mettre le responsable ue comme responsable Ec si pas de responsable
                                        OperationBinding opAncResp =
                                            getBindings().getOperationBinding("getUserRespFilEc");
                                        opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                        opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                        Long u_resp_id = (Long) opAncResp.execute();
                                        if (0 ==
                                            u_resp_id) {
                                            //update responsable que si pas de responsable
                                            OperationBinding opRespUe =
                                           getBindings().getOperationBinding("getUserRespFilUe");
                                            opRespUe.getParamsMap().put("fil_ue_id", fil_ue_id);
                                            opRespUe.getParamsMap().put("pma_id", prcrs_mq_id);
                                            Long ue_resp_id = (Long) opRespUe.execute();
                                            if (0 != ue_resp_id) {
                                                try {
                                                    OperationBinding opupd =
                                                        getBindings().getOperationBinding("UpdateResponsableEc");
                                                    opupd.getParamsMap().put("p_old", u_resp_id);
                                                    opupd.getParamsMap().put("p_new", ue_resp_id);
                                                    opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                    opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                    opupd.getParamsMap().put("p_user", getUtilisateur());
                                                    Long res = (Long) opupd.execute();
                                                    if (0 == res) {
                                                        System.out.println("erreur dans le parametrage du nouveau resp");
                                                    } else {
                                                        String username = null;
                                                        try {
                                                            OperationBinding opusername =
                                                                bindings.getOperationBinding("getusername");
                                                            opusername.getParamsMap().put("user_id", ue_resp_id);
                                                            username = (String) opusername.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        if ((null != role) && (null != username)) {
                                                            role =
                                                                role.substring(0, 1) + role.substring(1).toLowerCase();
                                                            try {
                                                                AdministrationBean.assignWlsRole2User(role, username);
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        rsie.closeRowSetIterator();
                        nv.next();
                    }
                    nv.reset();
                    rsi.closeRowSetIterator();
                }
            } 
            for (Long sem_id : semestres) {
                Integer nbr_cal = 0;
                Long gdrSem_id = new Long(0);
                //Generer les calendriers
                try {
                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                    is_exist.getParamsMap().put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                    is_exist.getParamsMap().put("sem_id", sem_id);
                    is_exist.getParamsMap().put("an_id", new Long(getAnnee()));
                    nbr_cal = (Integer) is_exist.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 == nbr_cal) {
                    Long cal_id = new Long(0);
                    try {
                        OperationBinding opnewcal = bindings.getOperationBinding("CreateOrUpdateCalendar");
                        opnewcal.getParamsMap()
                            .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                        opnewcal.getParamsMap().put("sem_id", sem_id);
                        opnewcal.getParamsMap().put("an_id", new Long(getAnnee()));
                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                        opnewcal.getParamsMap().put("sess_id", 1);
                        cal_id = (Long) opnewcal.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                }
                //Generer les grades semestre
                try {
                    OperationBinding opgs = bindings.getOperationBinding("CreateOrUpdateGrdSemestre");
                    opgs.getParamsMap().put("niv_id", currentPrcrs.getAttribute("IdNiveau"));
                    opgs.getParamsMap().put("gf_id", currentPrcrs.getAttribute("IdGradesFormation"));
                    opgs.getParamsMap().put("sem_id", sem_id);
                    opgs.getParamsMap().put("utilisateur", getUtilisateur());
                    gdrSem_id = (Long) opgs.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                //supprimer CC et Update %Ct 
                try { 
                    OperationBinding opdelcc = bindings.getOperationBinding("DeleteCCUpdatePrc");
                    opdelcc.getParamsMap().put("pma_id", prcrs_mq_id);
                    opdelcc.getParamsMap().put("str_id", new Long(getStructure()));
                    opdelcc.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //System.out.println(semestres);
        }
        this.getPopupScolMaq().hide();
        DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("MaquetteIntegratedNivSec1Iterator");
        ViewObject voUe = iterUe.getViewObject();
        voUe.executeQuery();
        DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("FilEc1Iterator");
        ViewObject voEc = iterEc.getViewObject();
        voEc.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnMaqImported());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanimport());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUe());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEc());
    }

    @SuppressWarnings("unchecked")
    public void OnImportMaquetteOpActionListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //getRespFr
        //getRespNivFr
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();
        if (currentPrcrs != null) {
            DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("ScolFilUeOptionsIterator");
            ViewObject voUe = iterUe.getViewObject();
            voUe.executeQuery();
            DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("ScolFilEc1Iterator");
            ViewObject voEc = iterEc.getViewObject();
            voEc.executeQuery();
            Long mq_id = Long.parseLong(sessionScope.get("mq_id").toString());
            Long niv_mq_id = new Long(0);
            Long prcrs_mq_id = new Long(0);
            String Lib = null;
            Long ue_id = new Long(0);
            Long fil_ue_id = new Long(0);
            Long user_id = new Long(0);
            Long ec_id = new Long(0);
            Long fil_ec_id = new Long(0);
            String msg = null;
            ArrayList<Long> semestres = new ArrayList<Long>();
            /*try {
                OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                generateMaq.getParamsMap()
                    .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                generateMaq.getParamsMap().put("op_code", currentPrcrs.getAttribute("Codeoptionscol"));
                generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                mq_id = (Long) generateMaq.execute();
            } catch (Exception e) {
                System.out.println(e);
            }*/
            if (mq_id != 0) {
                try {
                    OperationBinding opNivFormMaqAn = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                    opNivFormMaqAn.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                    opNivFormMaqAn.getParamsMap().put("mq", mq_id);
                    opNivFormMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opNivFormMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    niv_mq_id = (Long) opNivFormMaqAn.execute();

                    OperationBinding opPrcrsMaqAn = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                    opPrcrsMaqAn.getParamsMap().put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    opPrcrsMaqAn.getParamsMap().put("mq", mq_id);
                    opPrcrsMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opPrcrsMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    prcrs_mq_id = (Long) opPrcrsMaqAn.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (niv_mq_id != 0 && prcrs_mq_id != 0) {
                    sessionScope.put("pma_id", prcrs_mq_id);
                    DCIteratorBinding iterfilue = (DCIteratorBinding) bindings.get("ScolFilUeOptionsIterator");
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
                            OperationBinding opUe = bindings.getOperationBinding("createOrUpdateUe");
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
                                OperationBinding opFilUe = bindings.getOperationBinding("createOrUpdateFilUe");
                                opFilUe.getParamsMap().put("anc_code", nextRow.getAttribute("CodeFiliereUeSemestre"));
                                opFilUe.getParamsMap().put("ue_id", ue_id);
                                opFilUe.getParamsMap()
                                    .put("sem_id", Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                                opFilUe.getParamsMap().put("cat_ue_id", nextRow.getAttribute("CodeCategorieUe"));
                                opFilUe.getParamsMap().put("codif", nextRow.getAttribute("CodificationUe"));
                                opFilUe.getParamsMap()
                                    .put("cred", Long.parseLong(nextRow.getAttribute("CreditUe").toString()));
                                opFilUe.getParamsMap().put("coef", Float.parseFloat(nextRow.getAttribute("Coefue")
                                                                                           .toString()
                                                                                           .replace(",", ".")));
                                opFilUe.getParamsMap().put("maq", mq_id);
                                //opFilUe.getParamsMap().put("moy_v",Integer.parseInt(nextRow.getAttribute("MoyenneValidation").toString()));
                                opFilUe.getParamsMap().put("moy_v", 0);
                                if (null == nextRow.getAttribute("MoyenneEliminatoire")) {
                                    opFilUe.getParamsMap().put("moy_e", 0);
                                } else {
                                    opFilUe.getParamsMap()
                                        .put("moy_e",
                                             Integer.parseInt(nextRow.getAttribute("MoyenneEliminatoire").toString()));
                                }
                                opFilUe.getParamsMap().put("nat", nextRow.getAttribute("Fondamental"));
                                opFilUe.getParamsMap().put("comp", nextRow.getAttribute("Compensable"));
                                opFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                fil_ue_id = (Long) opFilUe.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 != fil_ue_id) {
                                try {
                                    OperationBinding opUserFilUe =
                                        bindings.getOperationBinding("createOrUpdateUserFilUe");
                                    opUserFilUe.getParamsMap().put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                    opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                    opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                    opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                    opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                    opUserFilUe.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                    try {
                                        OperationBinding opUserFilUe =
                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                        opUserFilUe.getParamsMap()
                                            .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                        opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                        opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                        opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                        opUserFilUe.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }

                                String role = null;
                                try {
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", R_UE); //resp ue
                                    role = (String) oprole.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                //Responsable Ue
                                if (null != nextRow.getAttribute("Emailucad")) {
                                    try {
                                        OperationBinding opUser = bindings.getOperationBinding("createOrUpdateUser");
                                        opUser.getParamsMap()
                                            .put("email", nextRow.getAttribute("Emailucad").toString());
                                        opUser.getParamsMap()
                                            .put("struct_", nextRow.getAttribute("CodeEtablissement").toString());
                                        opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                        user_id = (Long) opUser.execute();
                                        if (0 != user_id) {
                                            //weblogic user
                                            try {
                                                String log = nextRow.getAttribute("Emailucad").toString();
                                                String matricule = nextRow.getAttribute("CodeMatricule").toString();
                                                //matricule = "passer123*";
                                                AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                try {
                                                    OperationBinding opUserForm =
                                                        bindings.getOperationBinding("createOrUpdateUserForm");
                                                    opUserForm.getParamsMap().put("user_", user_id);
                                                    opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                    opUserForm.getParamsMap()
                                                        .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                    opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opUserForm.execute();

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                try {
                                                    OperationBinding opUserNivForm =
                                                        bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                    opUserNivForm.getParamsMap().put("user_", user_id);
                                                    opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                    opUserNivForm.getParamsMap()
                                                        .put("niv_form_",
                                                             currentPrcrs.getAttribute("IdNiveauFormation"));
                                                    opUserNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                    opUserNivForm.execute();

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                            try {
                                                //Ancien responsable
                                                OperationBinding opAncResp =
                                                    getBindings().getOperationBinding("getUserRespFilUe");
                                                opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                Long u_resp_id = (Long) opAncResp.execute();
                                                if (-1 != u_resp_id) {
                                                    if (!(user_id.equals(u_resp_id))) {
                                                        try {
                                                            OperationBinding opupd =
                                                                getBindings()
                                                                .getOperationBinding("UpdateResponsableUe");
                                                            opupd.getParamsMap().put("p_old", u_resp_id);
                                                            opupd.getParamsMap().put("p_new", user_id);
                                                            opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                            opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                            opupd.getParamsMap().put("p_user", getUtilisateur());
                                                            Long res = (Long) opupd.execute();
                                                            if (-1 ==
                                                                res) {
                                                                //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                if ((null != role) &&
                                                                    (null != nextRow.getAttribute("Emailucad"))) {
                                                                    role =
                                                                        role.substring(0, 1) +
                                                                        role.substring(1).toLowerCase();
                                                                    String username = null;
                                                                    try {
                                                                        OperationBinding opusername =
                                                                            bindings.getOperationBinding("getusername");
                                                                        opusername.getParamsMap()
                                                                            .put("user_id", u_resp_id);
                                                                        username = (String) opusername.execute();
                                                                    } catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    try {
                                                                        if (null != username) {
                                                                            AdministrationBean.revokeWlsRole2User(role,
                                                                                                                  username);
                                                                        }
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
                                                                //Tjrs responsable de formation RAS;
                                                                if ((null != role) &&
                                                                    (null != nextRow.getAttribute("Emailucad"))) {
                                                                    role =
                                                                        role.substring(0, 1) +
                                                                        role.substring(1).toLowerCase();
                                                                    try {
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
                                } else {
                                    //Donner le responsable de niveau si not null les ues avec comme role responsable
                                    if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                        Long us_id =
                                            Long.parseLong(currentPrcrs.getAttribute("Responsablenivfr").toString());
                                        try {
                                            //Ancien responsable
                                            OperationBinding opAncResp =
                                                getBindings().getOperationBinding("getUserRespFilUe");
                                            opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                            Long u_resp_id = (Long) opAncResp.execute();
                                            if (0 == u_resp_id) { //mettre a jour que si le responsable n'etait pas parametrer
                                                //NB : si le responsable devrais chancher la condition serai  if (-1 != u_resp_id)
                                                try {
                                                    OperationBinding opupd =
                                                        getBindings().getOperationBinding("UpdateResponsableUe");
                                                    opupd.getParamsMap().put("p_old", u_resp_id);
                                                    opupd.getParamsMap().put("p_new", us_id);
                                                    opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                    opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                    opupd.getParamsMap().put("p_user", getUtilisateur());
                                                    Long res = (Long) opupd.execute();
                                                    if (0 == res) {
                                                        System.out.println("erreur dans le parametrage du nouveau resp");
                                                    } else {
                                                        String username = null;
                                                        try {
                                                            OperationBinding opusername =
                                                                bindings.getOperationBinding("getusername");
                                                            opusername.getParamsMap().put("user_id", us_id);
                                                            username = (String) opusername.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        if ((null != role) && (null != username)) {
                                                            role =
                                                                role.substring(0, 1) + role.substring(1).toLowerCase();
                                                            try {
                                                                AdministrationBean.assignWlsRole2User(role, username);
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
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
                                }
                            }
                            if (!semestres.contains(nextRow.getAttribute("CodeSemestre"))) {
                                semestres.add(Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                            }
                        } else {
                            //recupérer les ues non enregistrés
                        }

                        DCIteratorBinding iterfilec = (DCIteratorBinding) bindings.get("ScolFilEc1Iterator");
                        RowSetIterator rsie = iterfilec.getViewObject().createRowSetIterator(null);
                        while (rsie.hasNext()) {
                            Row nextRowFilEc = rsie.next();
                            try {
                                OperationBinding opEc = bindings.getOperationBinding("createOrUpdateEc");
                                opEc.getParamsMap().put("anc_code", nextRowFilEc.getAttribute("CodeEc"));
                                opEc.getParamsMap().put("libelle", nextRowFilEc.getAttribute("LibLongEc"));
                                opEc.getParamsMap().put("utilisateur", getUtilisateur());
                                ec_id = (Long) opEc.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (ec_id != 0) {
                                //insertFilUeSemEc
                                try {
                                    OperationBinding opFilEc = bindings.getOperationBinding("createOrUpdateFilEc");
                                    opFilEc.getParamsMap()
                                        .put("anc_code", nextRowFilEc.getAttribute("CodeFiliereUeSemestreEc"));
                                    opFilEc.getParamsMap().put("ec_id", ec_id);
                                    opFilEc.getParamsMap().put("nat", nextRowFilEc.getAttribute("CodeNatureEc"));
                                    opFilEc.getParamsMap().put("codif", nextRowFilEc.getAttribute("CodificationEc"));
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
                                    opFilEc.getParamsMap().put("cc", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("ct", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCt")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("fus", fil_ue_id);
                                    opFilEc.getParamsMap().put("hcm", nextRowFilEc.getAttribute("HeureCm"));
                                    opFilEc.getParamsMap().put("htd", nextRowFilEc.getAttribute("HeureTd"));
                                    opFilEc.getParamsMap().put("htp", nextRowFilEc.getAttribute("HeureTp"));
                                    opFilEc.getParamsMap().put("htpe", nextRowFilEc.getAttribute("HeureTpe"));
                                    opFilEc.getParamsMap().put("hs", nextRowFilEc.getAttribute("HeureStage"));
                                    opFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                    fil_ec_id = (Long) opFilEc.execute();
                                    //generer les modalites de saisie en fonction des %CC et %CT
                                    if ((0 != fil_ec_id)) {
                                        String role = null;
                                        try {
                                            OperationBinding oprole = bindings.getOperationBinding("getrole");
                                            oprole.getParamsMap().put("role_id", R_EC); //resp ue
                                            role = (String) oprole.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        if (0 != Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                              .toString()
                                                                              .replace(",", "."))) {
                                            try {
                                                OperationBinding opmdecntrl =
                                                    bindings.getOperationBinding("CreateOrUpdateModeCntrlEC");
                                                opmdecntrl.getParamsMap().put("fil_ec", fil_ec_id);
                                                opmdecntrl.getParamsMap().put("tc", 1);
                                                opmdecntrl.getParamsMap().put("mc", 1);
                                                opmdecntrl.getParamsMap().put("pma", prcrs_mq_id);
                                                opmdecntrl.getParamsMap().put("coef", 1);
                                                opmdecntrl.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long idcc = (Long) opmdecntrl.execute();

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
                                                opmdecntrl1.getParamsMap().put("utilisateur", getUtilisateur());
                                                opmdecntrl1.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        }
                                        try {
                                            OperationBinding opUserFilEc =
                                                bindings.getOperationBinding("createOrUpdateUserFilEc");
                                            opUserFilEc.getParamsMap()
                                                .put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                            opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                            opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                            opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                            opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                            opUserFilEc.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                            try {
                                                OperationBinding opUserFilEc =
                                                    bindings.getOperationBinding("createOrUpdateUserFilEc");
                                                opUserFilEc.getParamsMap()
                                                    .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                                opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                                opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                                opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                                opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                                opUserFilEc.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        }
                                        //Responsable Ec
                                        if (null != nextRowFilEc.getAttribute("Emailucad")) {
                                            try {
                                                OperationBinding opUser =
                                                    bindings.getOperationBinding("createOrUpdateUser");
                                                opUser.getParamsMap()
                                                    .put("email", nextRowFilEc.getAttribute("Emailucad").toString());
                                                opUser.getParamsMap()
                                                    .put("struct_",
                                                         nextRowFilEc.getAttribute("CodeEtablissement").toString());
                                                opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                                user_id = (Long) opUser.execute();
                                                if (0 != user_id) {
                                                    try {
                                                        String log = nextRowFilEc.getAttribute("Emailucad").toString();
                                                        String matricule =
                                                            nextRowFilEc.getAttribute("CodeMatricule").toString();
                                                        //matricule = "passer123*";
                                                        AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                        try {
                                                            OperationBinding opUserForm =
                                                                bindings.getOperationBinding("createOrUpdateUserForm");
                                                            opUserForm.getParamsMap().put("user_", user_id);
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap()
                                                                .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                    if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                        try {
                                                            OperationBinding opUserNivForm =
                                                                bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                            opUserNivForm.getParamsMap().put("user_", user_id);
                                                            opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserNivForm.getParamsMap()
                                                                .put("niv_form_",
                                                                     currentPrcrs.getAttribute("IdNiveauFormation"));
                                                            opUserNivForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserNivForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                    try {
                                                        OperationBinding opUserFilUe =
                                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                                        opUserFilUe.getParamsMap().put("user_", user_id);
                                                        opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                                        opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                                        opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                                        opUserFilUe.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    if ((null != role) &&
                                                        (null != nextRowFilEc.getAttribute("Emailucad").toString())) {
                                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                        try {
                                                            //Ancien responsable
                                                            OperationBinding opAncResp =
                                                                getBindings().getOperationBinding("getUserRespFilEc");
                                                            opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                            Long u_resp_id = (Long) opAncResp.execute();
                                                            if (-1 != u_resp_id) {
                                                                if (!(user_id.equals(u_resp_id))) {
                                                                    try {
                                                                        OperationBinding opupd =
                                                                            getBindings()
                                                                            .getOperationBinding("UpdateResponsableEc");
                                                                        opupd.getParamsMap().put("p_old", u_resp_id);
                                                                        opupd.getParamsMap().put("p_new", user_id);
                                                                        opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                                        opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                                        opupd.getParamsMap()
                                                                            .put("p_user", getUtilisateur());
                                                                        Long res = (Long) opupd.execute();
                                                                        if (-1 == res) {
                                                                            //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                            String username = null;
                                                                            try {
                                                                                OperationBinding opusername =
                                                                                    bindings.getOperationBinding("getusername");
                                                                                opusername.getParamsMap()
                                                                                    .put("user_id", u_resp_id);
                                                                                username =
                                                                                    (String) opusername.execute();
                                                                            } catch (Exception e) {
                                                                                System.out.println(e);
                                                                            }
                                                                            try {
                                                                                if (null != username) {
                                                                                    AdministrationBean.revokeWlsRole2User(role,
                                                                                                                          username);
                                                                                }
                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                      nextRowFilEc.getAttribute("Emailucad")
                                                                                                                      .toString());
                                                                            } catch (Exception e) {
                                                                                System.out.println(e);
                                                                            }
                                                                            //}
                                                                        } else if (0 == res) {
                                                                            System.out.println("erreur dans le parametrage du nouveau resp");
                                                                        } else {
                                                                            //Tjrs responsable de formation RAS
                                                                            try {
                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                      nextRowFilEc.getAttribute("Emailucad")
                                                                                                                      .toString());
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
                                                    }
                                                } 
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        } else {
                                            //Mettre le responsable ue comme responsable Ec si pas de responsable
                                            OperationBinding opAncResp =
                                                getBindings().getOperationBinding("getUserRespFilEc");
                                            opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                            Long u_resp_id = (Long) opAncResp.execute();
                                            if (0 ==
                                                u_resp_id) {
                                                //update responsable que si pas de responsable
                                                OperationBinding opRespUe =
                                               getBindings().getOperationBinding("getUserRespFilUe");
                                                opRespUe.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                opRespUe.getParamsMap().put("pma_id", prcrs_mq_id);
                                                Long ue_resp_id = (Long) opRespUe.execute();
                                                if (0 != ue_resp_id) {
                                                    try {
                                                        OperationBinding opupd =
                                                            getBindings().getOperationBinding("UpdateResponsableEc");
                                                        opupd.getParamsMap().put("p_old", u_resp_id);
                                                        opupd.getParamsMap().put("p_new", ue_resp_id);
                                                        opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                        opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                        opupd.getParamsMap().put("p_user", getUtilisateur());
                                                        Long res = (Long) opupd.execute();
                                                        if (0 == res) {
                                                            System.out.println("erreur dans le parametrage du nouveau resp");
                                                        } else {
                                                            String username = null;
                                                            try {
                                                                OperationBinding opusername =
                                                                    bindings.getOperationBinding("getusername");
                                                                opusername.getParamsMap().put("user_id", ue_resp_id);
                                                                username = (String) opusername.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            if ((null != role) && (null != username)) {
                                                                role =
                                                                    role.substring(0, 1) +
                                                                    role.substring(1).toLowerCase();
                                                                try {
                                                                    AdministrationBean.assignWlsRole2User(role,
                                                                                                          username);
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                            }
                                        }

                                    }

                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }

                        }

                        rsie.closeRowSetIterator();
                        nv.next();
                    }
                    nv.reset();
                    rsi.closeRowSetIterator();
                }
            }
            for (Long sem_id : semestres) {
                Integer nbr_cal = 0;
                Long gdrSem_id = new Long(0);
                try {
                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                    is_exist.getParamsMap().put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                    is_exist.getParamsMap().put("sem_id", sem_id);
                    is_exist.getParamsMap().put("an_id", new Long(getAnnee()));
                    nbr_cal = (Integer) is_exist.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 == nbr_cal) {
                    Long cal_id = new Long(0);
                    try {
                        OperationBinding opnewcal = bindings.getOperationBinding("CreateOrUpdateCalendar");
                        opnewcal.getParamsMap()
                            .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                        opnewcal.getParamsMap().put("sem_id", sem_id);
                        opnewcal.getParamsMap().put("an_id", new Long(getAnnee()));
                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                        opnewcal.getParamsMap().put("sess_id", 1);
                        cal_id = (Long) opnewcal.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                }
                try {
                    OperationBinding opgs = bindings.getOperationBinding("CreateOrUpdateGrdSemestre");
                    opgs.getParamsMap().put("niv_id", currentPrcrs.getAttribute("IdNiveau"));
                    opgs.getParamsMap().put("gf_id", currentPrcrs.getAttribute("IdGradesFormation"));
                    opgs.getParamsMap().put("sem_id", sem_id);
                    opgs.getParamsMap().put("utilisateur", getUtilisateur());
                    gdrSem_id = (Long) opgs.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }

                //supprimer CC et Update %Ct
                try {
                    OperationBinding opdelcc = bindings.getOperationBinding("DeleteCCUpdatePrc");
                    opdelcc.getParamsMap().put("pma_id", prcrs_mq_id);
                    opdelcc.getParamsMap().put("str_id", new Long(getStructure()));
                    opdelcc.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        this.getPopupScolMaqOp().hide();
        DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("MaquetteIntegratedNivSec1Iterator");
        ViewObject voUe = iterUe.getViewObject();
        voUe.executeQuery();
        DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("FilEc1Iterator");
        ViewObject voEc = iterEc.getViewObject();
        voEc.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnMaqImported());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanimport());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUe());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEc());
    }

    public void setBtnPanGrpOp(RichPanelGroupLayout btnPanGrpOp) {
        this.btnPanGrpOp = btnPanGrpOp;
    }

    public RichPanelGroupLayout getBtnPanGrpOp() {
        return btnPanGrpOp;
    }

    public void setPopupScolMaqOpANCNO(RichPopup popupScolMaqOpANCNO) {
        this.popupScolMaqOpANCNO = popupScolMaqOpANCNO;
    }

    public RichPopup getPopupScolMaqOpANCNO() {
        return popupScolMaqOpANCNO;
    }

    public void setBtnPanGrpOpANCNO(RichPanelGroupLayout btnPanGrpOpANCNO) {
        this.btnPanGrpOpANCNO = btnPanGrpOpANCNO;
    }

    public RichPanelGroupLayout getBtnPanGrpOpANCNO() {
        return btnPanGrpOpANCNO;
    }

    @SuppressWarnings("unchecked")
    public void OnImportMaqOpANCNOActionListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        //getRespFr
        //getRespNivFr
        DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentPrcrs = iterPrcrs.getCurrentRow();

        DCIteratorBinding iterOption = (DCIteratorBinding) bindings.get("OptionANCNOParcoursIterator");
        Row currentOption = iterOption.getCurrentRow();

        if (currentPrcrs != null) {
            DCIteratorBinding iterUe = (DCIteratorBinding) bindings.get("ScolFilUeOptionsANCNOIterator");
            ViewObject voUe = iterUe.getViewObject();
            voUe.executeQuery();
            DCIteratorBinding iterEc = (DCIteratorBinding) bindings.get("ScolFilEc2Iterator");
            ViewObject voEc = iterEc.getViewObject();
            voEc.executeQuery();
            Long mq_id = Long.parseLong(sessionScope.get("mq_id").toString());
            Long niv_mq_id = new Long(0);
            Long prcrs_mq_id = new Long(0);
            String Lib = null;
            Long ue_id = new Long(0);
            Long fil_ue_id = new Long(0);
            Long user_id = new Long(0);
            Long ec_id = new Long(0);
            Long fil_ec_id = new Long(0);
            String msg = null;
            ArrayList<Long> semestres = new ArrayList<Long>();
            /*try {
                OperationBinding generateMaq = bindings.getOperationBinding("generateMaquette");
                generateMaq.getParamsMap()
                    .put("niv_form_prcrt", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                generateMaq.getParamsMap().put("str_id", new Long(getStructure()));
                generateMaq.getParamsMap().put("annee_id", new Long(getAnnee()));
                generateMaq.getParamsMap().put("code_niv_sec", currentPrcrs.getAttribute("AncienCode"));
                generateMaq.getParamsMap().put("op_code", currentOption.getAttribute("Code"));
                generateMaq.getParamsMap().put("code_fr", currentPrcrs.getAttribute("Scolcodefrr"));
                generateMaq.getParamsMap().put("utilisateur", getUtilisateur());
                mq_id = (Long) generateMaq.execute();
            } catch (Exception e) {
                System.out.println(e);
            }*/
            if (mq_id != 0) {
                try {
                    OperationBinding opNivFormMaqAn = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                    opNivFormMaqAn.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                    opNivFormMaqAn.getParamsMap().put("mq", mq_id);
                    opNivFormMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opNivFormMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    niv_mq_id = (Long) opNivFormMaqAn.execute();

                    OperationBinding opPrcrsMaqAn = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                    opPrcrsMaqAn.getParamsMap().put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    opPrcrsMaqAn.getParamsMap().put("mq", mq_id);
                    opPrcrsMaqAn.getParamsMap().put("an", new Long(getAnnee()));
                    opPrcrsMaqAn.getParamsMap().put("utilisateur", getUtilisateur());
                    prcrs_mq_id = (Long) opPrcrsMaqAn.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (niv_mq_id != 0 && prcrs_mq_id != 0) {
                    sessionScope.put("pma_id", prcrs_mq_id);
                    DCIteratorBinding iterfilue = (DCIteratorBinding) bindings.get("ScolFilUeOptionsANCNOIterator");
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
                            OperationBinding opUe = bindings.getOperationBinding("createOrUpdateUe");
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
                                OperationBinding opFilUe = bindings.getOperationBinding("createOrUpdateFilUe");
                                opFilUe.getParamsMap().put("anc_code", nextRow.getAttribute("CodeFiliereUeSemestre"));
                                opFilUe.getParamsMap().put("ue_id", ue_id);
                                opFilUe.getParamsMap()
                                    .put("sem_id", Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                                opFilUe.getParamsMap().put("cat_ue_id", nextRow.getAttribute("CodeCategorieUe"));
                                opFilUe.getParamsMap().put("codif", nextRow.getAttribute("CodificationUe"));
                                opFilUe.getParamsMap()
                                    .put("cred", Long.parseLong(nextRow.getAttribute("CreditUe").toString()));
                                opFilUe.getParamsMap().put("coef", Float.parseFloat(nextRow.getAttribute("Coefue")
                                                                                           .toString()
                                                                                           .replace(",", ".")));
                                opFilUe.getParamsMap().put("maq", mq_id);
                                //opFilUe.getParamsMap().put("moy_v",Integer.parseInt(nextRow.getAttribute("MoyenneValidation").toString()));
                                opFilUe.getParamsMap().put("moy_v", 0);
                                if (null == nextRow.getAttribute("MoyenneEliminatoire")) {
                                    opFilUe.getParamsMap().put("moy_e", 0);
                                } else {
                                    opFilUe.getParamsMap()
                                        .put("moy_e",
                                             Integer.parseInt(nextRow.getAttribute("MoyenneEliminatoire").toString()));
                                }
                                opFilUe.getParamsMap().put("nat", nextRow.getAttribute("Fondamental"));
                                opFilUe.getParamsMap().put("comp", nextRow.getAttribute("Compensable"));
                                opFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                fil_ue_id = (Long) opFilUe.execute();
                                if (0 != fil_ue_id) {
                                    try {
                                        OperationBinding opUserFilUe =
                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                        opUserFilUe.getParamsMap()
                                            .put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                        opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                        opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                        opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                        opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                        opUserFilUe.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                        try {
                                            OperationBinding opUserFilUe =
                                                bindings.getOperationBinding("createOrUpdateUserFilUe");
                                            opUserFilUe.getParamsMap()
                                                .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                            opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                            opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                            opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                            opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                            opUserFilUe.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }

                                    String role = null;
                                    try {
                                        OperationBinding oprole = bindings.getOperationBinding("getrole");
                                        oprole.getParamsMap().put("role_id", R_UE); //resp ue
                                        role = (String) oprole.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    //Responsable Ue
                                    if (null != nextRow.getAttribute("Emailucad")) {
                                        try {
                                            OperationBinding opUser =
                                                bindings.getOperationBinding("createOrUpdateUser");
                                            opUser.getParamsMap()
                                                .put("email", nextRow.getAttribute("Emailucad").toString());
                                            opUser.getParamsMap()
                                                .put("struct_", nextRow.getAttribute("CodeEtablissement").toString());
                                            opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                            user_id = (Long) opUser.execute();
                                            if (0 != user_id) {
                                                //weblogic user
                                                try {
                                                    String log = nextRow.getAttribute("Emailucad").toString();
                                                    String matricule = nextRow.getAttribute("CodeMatricule").toString();
                                                    //matricule = "passer123*";
                                                    AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                    try {
                                                        OperationBinding opUserForm =
                                                            bindings.getOperationBinding("createOrUpdateUserForm");
                                                        opUserForm.getParamsMap().put("user_", user_id);
                                                        opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserForm.getParamsMap()
                                                            .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                        opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                                                        opUserForm.execute();

                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                                if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                    try {
                                                        OperationBinding opUserNivForm =
                                                            bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                        opUserNivForm.getParamsMap().put("user_", user_id);
                                                        opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserNivForm.getParamsMap()
                                                            .put("niv_form_",
                                                                 currentPrcrs.getAttribute("IdNiveauFormation"));
                                                        opUserNivForm.getParamsMap()
                                                            .put("utilisateur", getUtilisateur());
                                                        opUserNivForm.execute();

                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                                try {
                                                    //Ancien responsable
                                                    OperationBinding opAncResp =
                                                        getBindings().getOperationBinding("getUserRespFilUe");
                                                    opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                    opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                    Long u_resp_id = (Long) opAncResp.execute();
                                                    if (-1 != u_resp_id) {
                                                        if (!(user_id.equals(u_resp_id))) {
                                                            try {
                                                                OperationBinding opupd =
                                                                    getBindings()
                                                                    .getOperationBinding("UpdateResponsableUe");
                                                                opupd.getParamsMap().put("p_old", u_resp_id);
                                                                opupd.getParamsMap().put("p_new", user_id);
                                                                opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                                opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                                opupd.getParamsMap().put("p_user", getUtilisateur());
                                                                Long res = (Long) opupd.execute();
                                                                if (-1 ==
                                                                    res) {
                                                                    //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                    if ((null != role) &&
                                                                        (null != nextRow.getAttribute("Emailucad"))) {
                                                                        role =
                                                                            role.substring(0, 1) +
                                                                            role.substring(1).toLowerCase();
                                                                        String username = null;
                                                                        try {
                                                                            OperationBinding opusername =
                                                                                bindings.getOperationBinding("getusername");
                                                                            opusername.getParamsMap()
                                                                                .put("user_id", u_resp_id);
                                                                            username = (String) opusername.execute();
                                                                        } catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        try {
                                                                            if (null != username) {
                                                                                AdministrationBean.revokeWlsRole2User(role,
                                                                                                                      username);
                                                                            }
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
                                                                    //Tjrs responsable de formation RAS;
                                                                    if ((null != role) &&
                                                                        (null != nextRow.getAttribute("Emailucad"))) {
                                                                        role =
                                                                            role.substring(0, 1) +
                                                                            role.substring(1).toLowerCase();
                                                                        try {
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
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        //Donner le responsable de niveau si not null les ues avec comme role responsable
                                        if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                            Long us_id =
                                                Long.parseLong(currentPrcrs.getAttribute("Responsablenivfr")
                                                               .toString());
                                            try {
                                                //Ancien responsable
                                                OperationBinding opAncResp =
                                                    getBindings().getOperationBinding("getUserRespFilUe");
                                                opAncResp.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                Long u_resp_id = (Long) opAncResp.execute();
                                                if (0 == u_resp_id) { //mettre a jour que si le responsable n'etait pas parametrer
                                                    //NB : si le responsable devrais chancher la condition serai  if (-1 != u_resp_id)
                                                    try {
                                                        OperationBinding opupd =
                                                            getBindings().getOperationBinding("UpdateResponsableUe");
                                                        opupd.getParamsMap().put("p_old", u_resp_id);
                                                        opupd.getParamsMap().put("p_new", us_id);
                                                        opupd.getParamsMap().put("p_ue_id", fil_ue_id);
                                                        opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                        opupd.getParamsMap().put("p_user", getUtilisateur());
                                                        Long res = (Long) opupd.execute();
                                                        if (0 == res) {
                                                            System.out.println("erreur dans le parametrage du nouveau resp");
                                                        } else {
                                                            String username = null;
                                                            try {
                                                                OperationBinding opusername =
                                                                    bindings.getOperationBinding("getusername");
                                                                opusername.getParamsMap().put("user_id", us_id);
                                                                username = (String) opusername.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            if ((null != role) && (null != username)) {
                                                                role =
                                                                    role.substring(0, 1) +
                                                                    role.substring(1).toLowerCase();
                                                                try {
                                                                    AdministrationBean.assignWlsRole2User(role,
                                                                                                          username);
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
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
                                        
                                    }
                                }
                                if (!semestres.contains(nextRow.getAttribute("CodeSemestre"))) {
                                    semestres.add(Long.parseLong(nextRow.getAttribute("CodeSemestre").toString()));
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            //recupérer les ues non enregistrés
                        }

                        DCIteratorBinding iterfilec = (DCIteratorBinding) bindings.get("ScolFilEc2Iterator");
                        RowSetIterator rsie = iterfilec.getViewObject().createRowSetIterator(null);
                        while (rsie.hasNext()) {
                            Row nextRowFilEc = rsie.next();
                            try {
                                OperationBinding opEc = bindings.getOperationBinding("createOrUpdateEc");
                                opEc.getParamsMap().put("anc_code", nextRowFilEc.getAttribute("CodeEc"));
                                opEc.getParamsMap().put("libelle", nextRowFilEc.getAttribute("LibLongEc"));
                                opEc.getParamsMap().put("utilisateur", getUtilisateur());
                                ec_id = (Long) opEc.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (ec_id != 0) {
                                //insertFilUeSemEc
                                try {
                                    OperationBinding opFilEc = bindings.getOperationBinding("createOrUpdateFilEc");
                                    opFilEc.getParamsMap()
                                        .put("anc_code", nextRowFilEc.getAttribute("CodeFiliereUeSemestreEc"));
                                    opFilEc.getParamsMap().put("ec_id", ec_id);
                                    opFilEc.getParamsMap().put("nat", nextRowFilEc.getAttribute("CodeNatureEc"));
                                    opFilEc.getParamsMap().put("codif", nextRowFilEc.getAttribute("CodificationEc"));
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
                                    opFilEc.getParamsMap().put("cc", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("ct", Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCt")
                                                                                                  .toString()
                                                                                                  .replace(",", ".")));
                                    opFilEc.getParamsMap().put("fus", fil_ue_id);
                                    opFilEc.getParamsMap().put("hcm", nextRowFilEc.getAttribute("HeureCm"));
                                    opFilEc.getParamsMap().put("htd", nextRowFilEc.getAttribute("HeureTd"));
                                    opFilEc.getParamsMap().put("htp", nextRowFilEc.getAttribute("HeureTp"));
                                    opFilEc.getParamsMap().put("htpe", nextRowFilEc.getAttribute("HeureTpe"));
                                    opFilEc.getParamsMap().put("hs", nextRowFilEc.getAttribute("HeureStage"));
                                    opFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                    fil_ec_id = (Long) opFilEc.execute();
                                    //generer les modalites de saisie en fonction des %CC et %CT
                                    if ((0 != fil_ec_id)) {
                                        String role = null;
                                        try {
                                            OperationBinding oprole = bindings.getOperationBinding("getrole");
                                            oprole.getParamsMap().put("role_id", R_EC); //resp ue
                                            role = (String) oprole.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }

                                        if (0 != Float.parseFloat(nextRowFilEc.getAttribute("PourcentageCc")
                                                                              .toString()
                                                                              .replace(",", "."))) {
                                            try {
                                                OperationBinding opmdecntrl =
                                                    bindings.getOperationBinding("CreateOrUpdateModeCntrlEC");
                                                opmdecntrl.getParamsMap().put("fil_ec", fil_ec_id);
                                                opmdecntrl.getParamsMap().put("tc", 1);
                                                opmdecntrl.getParamsMap().put("mc", 1);
                                                opmdecntrl.getParamsMap().put("pma", prcrs_mq_id);
                                                opmdecntrl.getParamsMap().put("coef", 1);
                                                opmdecntrl.getParamsMap().put("utilisateur", getUtilisateur());
                                                opmdecntrl.execute();

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
                                                opmdecntrl1.getParamsMap().put("utilisateur", getUtilisateur());
                                                opmdecntrl1.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        }
                                        try {
                                            OperationBinding opUserFilEc =
                                                bindings.getOperationBinding("createOrUpdateUserFilEc");
                                            opUserFilEc.getParamsMap()
                                                .put("user_", currentPrcrs.getAttribute("Responsablefr"));
                                            opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                            opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                            opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                            opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                            opUserFilEc.execute();
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        if (null != currentPrcrs.getAttribute("Responsablenivfr")) {
                                            try {
                                                OperationBinding opUserFilEc =
                                                    bindings.getOperationBinding("createOrUpdateUserFilEc");
                                                opUserFilEc.getParamsMap()
                                                    .put("user_", currentPrcrs.getAttribute("Responsablenivfr"));
                                                opUserFilEc.getParamsMap().put("role", "ACCES SIMPLE");
                                                opUserFilEc.getParamsMap().put("fil_ec_", fil_ec_id);
                                                opUserFilEc.getParamsMap().put("pma_", prcrs_mq_id);
                                                opUserFilEc.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long id = (Long) opUserFilEc.execute();
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        }
                                        //Responsable Ec
                                        if (null != nextRowFilEc.getAttribute("Emailucad")) {
                                            try {
                                                OperationBinding opUser =
                                                    bindings.getOperationBinding("createOrUpdateUser");
                                                opUser.getParamsMap()
                                                    .put("email", nextRowFilEc.getAttribute("Emailucad").toString());
                                                opUser.getParamsMap()
                                                    .put("struct_",
                                                         nextRowFilEc.getAttribute("CodeEtablissement").toString());
                                                opUser.getParamsMap().put("utilisateur", getUtilisateur());
                                                user_id = (Long) opUser.execute();
                                                if (0 != user_id) {
                                                    try {
                                                        String log = nextRowFilEc.getAttribute("Emailucad").toString();
                                                        String matricule =
                                                            nextRowFilEc.getAttribute("CodeMatricule").toString();
                                                        //matricule = "passer123*";
                                                        AdministrationBean.createWlsUser(log, matricule.toCharArray());

                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    if (!(user_id.equals(currentPrcrs.getAttribute("Responsablefr")))) {
                                                        try {
                                                            OperationBinding opUserForm =
                                                                bindings.getOperationBinding("createOrUpdateUserForm");
                                                            opUserForm.getParamsMap().put("user_", user_id);
                                                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserForm.getParamsMap()
                                                                .put("form_", currentPrcrs.getAttribute("IdFormation"));
                                                            opUserForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    }
                                                    if (!(user_id.equals(currentPrcrs.getAttribute("Responsablenivfr")))) {
                                                        try {
                                                            OperationBinding opUserNivForm =
                                                                bindings.getOperationBinding("createOrUpdateUserNivForm");
                                                            opUserNivForm.getParamsMap().put("user_", user_id);
                                                            opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                                            opUserNivForm.getParamsMap()
                                                                .put("niv_form_",
                                                                     currentPrcrs.getAttribute("IdNiveauFormation"));
                                                            opUserNivForm.getParamsMap()
                                                                .put("utilisateur", getUtilisateur());
                                                            opUserNivForm.execute();
                                                        } catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    } 
                                                    try {
                                                        OperationBinding opUserFilUe =
                                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                                        opUserFilUe.getParamsMap().put("user_", user_id);
                                                        opUserFilUe.getParamsMap().put("role", "ACCES SIMPLE");
                                                        opUserFilUe.getParamsMap().put("fil_ue_", fil_ue_id);
                                                        opUserFilUe.getParamsMap().put("utilisateur", getUtilisateur());
                                                        opUserFilUe.getParamsMap().put("pma_", prcrs_mq_id);
                                                        opUserFilUe.execute();
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    if ((null != role) &&
                                                        (null != nextRowFilEc.getAttribute("Emailucad").toString())) {
                                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                        try {
                                                            //Ancien responsable
                                                            OperationBinding opAncResp =
                                                                getBindings().getOperationBinding("getUserRespFilEc");
                                                            opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                                            Long u_resp_id = (Long) opAncResp.execute();
                                                            if (-1 != u_resp_id) {
                                                                if (!(user_id.equals(u_resp_id))) {
                                                                    try {
                                                                        OperationBinding opupd =
                                                                            getBindings()
                                                                            .getOperationBinding("UpdateResponsableEc");
                                                                        opupd.getParamsMap().put("p_old", u_resp_id);
                                                                        opupd.getParamsMap().put("p_new", user_id);
                                                                        opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                                        opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                                        opupd.getParamsMap()
                                                                            .put("p_user", getUtilisateur());
                                                                        Long res = (Long) opupd.execute();
                                                                        if (-1 == res) {
                                                                            //'anc resp n est plus resp de fr' supprimer le role dans wblgic
                                                                            String username = null;
                                                                            try {
                                                                                OperationBinding opusername =
                                                                                    bindings.getOperationBinding("getusername");
                                                                                opusername.getParamsMap()
                                                                                    .put("user_id", u_resp_id);
                                                                                username =
                                                                                    (String) opusername.execute();
                                                                            } catch (Exception e) {
                                                                                System.out.println(e);
                                                                            }
                                                                            try {
                                                                                if (null != username) {
                                                                                    AdministrationBean.revokeWlsRole2User(role,
                                                                                                                          username);
                                                                                }
                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                      nextRowFilEc.getAttribute("Emailucad")
                                                                                                                      .toString());
                                                                            } catch (Exception e) {
                                                                                System.out.println(e);
                                                                            }
                                                                            //}
                                                                        } else if (0 == res) {
                                                                            System.out.println("erreur dans le parametrage du nouveau resp");
                                                                        } else {
                                                                            //Tjrs responsable de formation RAS
                                                                            try {
                                                                                AdministrationBean.assignWlsRole2User(role,
                                                                                                                      nextRowFilEc.getAttribute("Emailucad")
                                                                                                                      .toString());
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
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                        } else {
                                            //Mettre le responsable ue comme responsable Ec si pas de responsable
                                            OperationBinding opAncResp =
                                                getBindings().getOperationBinding("getUserRespFilEc");
                                            opAncResp.getParamsMap().put("fil_ec_id", fil_ec_id);
                                            opAncResp.getParamsMap().put("pma_id", prcrs_mq_id);
                                            Long u_resp_id = (Long) opAncResp.execute();
                                            if (0 ==
                                                u_resp_id) {
                                                //update responsable que si pas de responsable
                                                OperationBinding opRespUe =
                                               getBindings().getOperationBinding("getUserRespFilUe");
                                                opRespUe.getParamsMap().put("fil_ue_id", fil_ue_id);
                                                opRespUe.getParamsMap().put("pma_id", prcrs_mq_id);
                                                Long ue_resp_id = (Long) opRespUe.execute();
                                                if (0 != ue_resp_id) {
                                                    try {
                                                        OperationBinding opupd =
                                                            getBindings().getOperationBinding("UpdateResponsableEc");
                                                        opupd.getParamsMap().put("p_old", u_resp_id);
                                                        opupd.getParamsMap().put("p_new", ue_resp_id);
                                                        opupd.getParamsMap().put("p_ec_id", fil_ec_id);
                                                        opupd.getParamsMap().put("pma_id", prcrs_mq_id);
                                                        opupd.getParamsMap().put("p_user", getUtilisateur());
                                                        Long res = (Long) opupd.execute();
                                                        if (0 == res) {
                                                            System.out.println("erreur dans le parametrage du nouveau resp");
                                                        } else {
                                                            String username = null;
                                                            try {
                                                                OperationBinding opusername =
                                                                    bindings.getOperationBinding("getusername");
                                                                opusername.getParamsMap().put("user_id", ue_resp_id);
                                                                username = (String) opusername.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            if ((null != role) && (null != username)) {
                                                                role =
                                                                    role.substring(0, 1) +
                                                                    role.substring(1).toLowerCase();
                                                                try {
                                                                    AdministrationBean.assignWlsRole2User(role,
                                                                                                          username);
                                                                } catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                        rsie.closeRowSetIterator();
                        nv.next();
                    }
                    nv.reset();
                    rsi.closeRowSetIterator();
                }
            }
            for (Long sem_id : semestres) {
                Integer nbr_cal = 0;
                Long gdrSem_id = new Long(0);
                try {
                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                    is_exist.getParamsMap().put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                    is_exist.getParamsMap().put("sem_id", sem_id);
                    is_exist.getParamsMap().put("an_id", new Long(getAnnee()));
                    nbr_cal = (Integer) is_exist.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 == nbr_cal) {
                    Long cal_id = new Long(0);
                    try {
                        OperationBinding opnewcal = bindings.getOperationBinding("CreateOrUpdateCalendar");
                        opnewcal.getParamsMap()
                            .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                        opnewcal.getParamsMap().put("sem_id", sem_id);
                        opnewcal.getParamsMap().put("an_id", new Long(getAnnee()));
                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                        opnewcal.getParamsMap().put("sess_id", 1);
                        cal_id = (Long) opnewcal.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                }
                try {
                    OperationBinding opgs = bindings.getOperationBinding("CreateOrUpdateGrdSemestre");
                    opgs.getParamsMap().put("niv_id", currentPrcrs.getAttribute("IdNiveau"));
                    opgs.getParamsMap().put("gf_id", currentPrcrs.getAttribute("IdGradesFormation"));
                    opgs.getParamsMap().put("sem_id", sem_id);
                    opgs.getParamsMap().put("utilisateur", getUtilisateur());
                    gdrSem_id = (Long) opgs.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }

                //supprimer CC et Update %Ct
                try {
                    OperationBinding opdelcc = bindings.getOperationBinding("DeleteCCUpdatePrc");
                    opdelcc.getParamsMap().put("pma_id", prcrs_mq_id);
                    opdelcc.getParamsMap().put("str_id", new Long(getStructure()));
                    opdelcc.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //System.out.println(semestres);
        }
        this.getPopupScolMaqOpANCNO().hide();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
        ViewObject vo = iter.getViewObject();
        vo.executeQuery();
        iter = (DCIteratorBinding) bindings.get("MaquetteIntegratedNivSec1Iterator");
        vo = iter.getViewObject();
        vo.executeQuery();
        iter = (DCIteratorBinding) bindings.get("FilEc1Iterator");
        vo = iter.getViewObject();
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnMaqImported());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanimport());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMaqimported());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUe());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEc());
    }

    public void setMaqimported(RichSelectOneChoice maqimported) {
        this.maqimported = maqimported;
    }

    public RichSelectOneChoice getMaqimported() {
        return maqimported;
    }

    public void setPopupImportMaq(RichPopup popupImportMaq) {
        this.popupImportMaq = popupImportMaq;
    }

    public RichPopup getPopupImportMaq() {
        return popupImportMaq;
    }

    public void onImportMaqValueChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void onImportMaqValdeAction(ActionEvent actionEvent) {
        // Add event code here...
        UploadedFile file = uploadedMaqFile;

        try {
            //Check if file is XLSX
            if (file.getContentType()
                .equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                file.getContentType().equalsIgnoreCase("application/xlsx")) {
                //readNProcessMaqExcelx(file.getInputStream()); 
                readNProcessEtd(file.getInputStream()); //for xlsx
            }
            //Check if file is XLS
            else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                if (file.getFilename()
                        .toUpperCase()
                        .endsWith(".XLS")) {
                    //readNProcessExcel(file.getInputStream()); //for xls
                }

            } else {
                FacesMessage msg = new FacesMessage("File format not supported.-- Upload XLS or XLSX file");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            file.getInputStream().close();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getMaqpage());

        } catch (IOException e) {
            // TODO
        }
        // finally{
        this.getPopupImportMaq().hide();
        this.getPopupImportMaq().clearInitialState();
    }

    public void setUploadedMaqFile(UploadedFile uploadedMaqFile) {
        this.uploadedMaqFile = uploadedMaqFile;
    }

    public UploadedFile getUploadedMaqFile() {
        return uploadedMaqFile;
    }

    /**
     * Method to read xlsx file and upload to table.
     * @param myxls
     * @throws IOException
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void readNProcessEtd(InputStream xlsx) throws IOException {
        //Use XSSFWorkbook for XLS file
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        Long prcrs_mq_id = new Long(0);
        String numero = null;
        String p_nom = null;
        String nom = null;
        String date_naiss = null;
        String l_naiss = null;
        String civ = null;
        String sex = null;
        String tel = null;
        String portable = null;
        String email_inst = null;
        String email_pers = null;
        String cin = null;
        String nat = null;
        ArrayList<Long> semestres = new ArrayList<Long>();
        BindingContainer bindings = getBindings();
        try {
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {
            System.out.println(e);
        }
        DCIteratorBinding iterPrcrsMaq = (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
        Row currentPrcrsMaq = iterPrcrsMaq.getCurrentRow();
        if (null != currentPrcrsMaq) {
            try {
                OperationBinding oppma = bindings.getOperationBinding("getPrcrsMaquette");
                oppma.getParamsMap().put("parcoursId", currentPrcrsMaq.getAttribute("IdNiveauFormationParcours"));
                oppma.getParamsMap().put("maquetteId", currentPrcrsMaq.getAttribute("IdMaquette"));
                oppma.getParamsMap().put("anneeId", currentPrcrsMaq.getAttribute("IdAnneesUnivers"));
                prcrs_mq_id = new Long((Integer) oppma.execute());
                if (0 != prcrs_mq_id) {
                    //System.out.println("prcrs_mq_id : "+prcrs_mq_id);
                    //opDelete tempTble
                    for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
                        XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
                        Integer skipRw = 1;
                        Integer skipcnt = 1;
                        //Import Etudiant
                        if (sheetIndex == 13) {
                            try {
                                //System.out.println("clearData...");
                                OperationBinding opClear = bindings.getOperationBinding("clearData");
                                opClear.getParamsMap().put("prcrs_maq_id", prcrs_mq_id);
                                opClear.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                                if (skipcnt > skipRw) { //skip first n row for labels.
                                    int Index = 0;
                                    for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                                        Cell MytempCell = tempRow.getCell(column);
                                        if (MytempCell != null) {
                                            Index = MytempCell.getColumnIndex();
                                            if (Index == 0) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    numero = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("numero : "+numero);
                                            } else if (Index == 1) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    p_nom = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("p_nom : "+p_nom);
                                            } else if (Index == 2) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    nom = MytempCell.getStringCellValue();
                                                }

                                                //System.out.println("nom : "+nom);
                                            } else if (Index == 3) {
                                                if (MytempCell.getCellType().toString() == "NUMERIC") {
                                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = MytempCell.getDateCellValue();
                                                    date_naiss = df.format(date);
                                                }
                                                //System.out.println("date_naiss : "+date_naiss);
                                            } else if (Index == 4) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    l_naiss = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("l_naiss : "+l_naiss);
                                            } else if (Index == 5) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    nat = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("nat : "+nat);
                                            } else if (Index == 6) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    civ = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("civ : "+civ);
                                            } else if (Index == 7) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    sex = MytempCell.getStringCellValue();
                                                }
                                                // System.out.println("sex : "+sex);
                                            } else if (Index == 8) {
                                                if (MytempCell.getCellType().toString() == "NUMERIC") {
                                                    tel = String.valueOf(MytempCell.getNumericCellValue());
                                                } else if (MytempCell.getCellType().toString() == "STRING" &&
                                                           MytempCell.getStringCellValue() != null) {
                                                    tel = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("tel : "+tel);
                                            } else if (Index == 9) {
                                                if (MytempCell.getCellType().toString() == "NUMERIC") {
                                                    portable = String.valueOf(MytempCell.getNumericCellValue());
                                                } else if (MytempCell.getCellType().toString() == "STRING" &&
                                                           MytempCell.getStringCellValue() != null) {
                                                    portable = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("portable : "+portable);
                                            } else if (Index == 10) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    email_inst = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("email_inst : "+email_inst);
                                            } else if (Index == 11) {
                                                if (MytempCell.getCellType().toString() == "STRING" &&
                                                    MytempCell.getStringCellValue() != null) {
                                                    email_pers = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("email_pers : "+email_pers);
                                            } else if (Index == 12) {
                                                if (MytempCell.getCellType().toString() == "NUMERIC") {
                                                    cin = String.valueOf(MytempCell.getNumericCellValue());
                                                } else if (MytempCell.getCellType().toString() == "STRING" &&
                                                           MytempCell.getStringCellValue() != null) {
                                                    cin = MytempCell.getStringCellValue();
                                                }
                                                //System.out.println("cin : "+cin);
                                            }

                                        }
                                    }
                                    try {
                                        //insertData
                                        OperationBinding optmp = bindings.getOperationBinding("insertTemps");
                                        optmp.getParamsMap().put("num_", numero.replace(".", "").toUpperCase());
                                        optmp.getParamsMap().put("pnom_", p_nom);
                                        optmp.getParamsMap().put("nom_", nom);
                                        optmp.getParamsMap().put("dnaiss_", date_naiss);
                                        optmp.getParamsMap().put("lnaiss_", l_naiss);
                                        optmp.getParamsMap().put("nat_", nat);
                                        optmp.getParamsMap().put("civ_", civ);
                                        optmp.getParamsMap().put("sex_", sex);
                                        optmp.getParamsMap().put("tel_", tel);
                                        optmp.getParamsMap().put("prt_", portable);
                                        optmp.getParamsMap().put("einst_", email_inst);
                                        optmp.getParamsMap().put("epers_", email_pers);
                                        optmp.getParamsMap().put("cin_", cin);
                                        optmp.getParamsMap().put("pma_", prcrs_mq_id);
                                        optmp.execute();

                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                                skipcnt++;
                                /*if (skipcnt == 3)
                                    break;*/
                            }
                            try{
                                OperationBinding opinsc = bindings.getOperationBinding("insertData");
                                opinsc.getParamsMap().put("prcrs_maq_id", prcrs_mq_id);
                                opinsc.getParamsMap().put("uti_id", getUtilisateur());
                                opinsc.execute();
                            }catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //System.out.println("Number of sheet : "+WorkBook.getNumberOfSheets());
        WorkBook.close();
        this.getPopupImportMaq().hide();

        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }
    
    /**
     * Method to read xlsx file and upload to table.
     * @param myxls
     * @throws IOException
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.insufficient-catch-block", "unchecked" })
    public void readNProcessMaqExcelx(InputStream xlsx) throws IOException {

        //Use XSSFWorkbook for XLS file
        XSSFWorkbook WorkBook = null;
        int sheetIndex = 0;
        Long id_maq = new Long(0);
        Long val_mq_id = new Long(0);
        Long niv_mq_id = new Long(0);
        Long prcrs_mq_id = new Long(0);
        Long id_ue = new Long(0);
        Long fil_ue_id = new Long(0);
        Long id_ec = new Long(0);
        Long fil_ec_id = new Long(0);
        ArrayList<Long> semestres = new ArrayList<Long>();
        BindingContainer bindings = getBindings();
        try {
            WorkBook = new XSSFWorkbook(xlsx);
        } catch (IOException e) {

        }
        if (0 < WorkBook.getNumberOfSheets()) {
            DCIteratorBinding iterPrcrs = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
            Row currentPrcrs = iterPrcrs.getCurrentRow();
            try {
                OperationBinding opmq = bindings.getOperationBinding("CreateOrUpdateMaqExcel");
                opmq.getParamsMap()
                    .put("libelle",
                         "Maquette-" + currentPrcrs.getAttribute("LibelleCourt") +
                         (currentPrcrs.getAttribute("Opt") != null ? "-" + currentPrcrs.getAttribute("Opt") : ""));
                opmq.getParamsMap().put("str", getStructure());
                opmq.getParamsMap().put("utilisateur", getUtilisateur());
                id_maq = (Long) opmq.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (0 != id_maq) {
                try {
                    OperationBinding op_valid_mq = bindings.getOperationBinding("ValidateMaquette");
                    op_valid_mq.getParamsMap().put("maq_id", id_maq);
                    op_valid_mq.getParamsMap().put("an_id", new Long(getAnnee()));
                    op_valid_mq.getParamsMap().put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                    op_valid_mq.getParamsMap().put("utilisateur", getUtilisateur());
                    val_mq_id = new Long((Integer) op_valid_mq.execute());
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 != val_mq_id) {
                    try {
                        OperationBinding op_niv_mq = bindings.getOperationBinding("createOrUpdateNivFormMaqAnn");
                        op_niv_mq.getParamsMap().put("mq", id_maq);
                        op_niv_mq.getParamsMap().put("an", new Long(getAnnee()));
                        op_niv_mq.getParamsMap().put("niv_form", currentPrcrs.getAttribute("IdNiveauFormation"));
                        op_niv_mq.getParamsMap().put("utilisateur", getUtilisateur());
                        niv_mq_id = (Long) op_niv_mq.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (0 != niv_mq_id) {
                        OperationBinding op_prcrs_mq = bindings.getOperationBinding("createOrUpdatePrcrsMaqAnn");
                        op_prcrs_mq.getParamsMap().put("mq", id_maq);
                        op_prcrs_mq.getParamsMap().put("an", new Long(getAnnee()));
                        op_prcrs_mq.getParamsMap()
                            .put("parcours", currentPrcrs.getAttribute("IdNiveauFormationParcours"));
                        op_prcrs_mq.getParamsMap().put("utilisateur", getUtilisateur());
                        prcrs_mq_id = (Long) op_prcrs_mq.execute();
                        if (0 != prcrs_mq_id) {
                            for (sheetIndex = 0; sheetIndex < WorkBook.getNumberOfSheets(); sheetIndex++) {
                                XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);
                                Integer skipRw = 1;
                                Integer skipcnt = 1;
                                //Import UE et filiere ue
                                if (sheetIndex == 0) {
                                    for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                                        if (skipcnt > skipRw) { //skip first n row for labels.
                                            int Index = 0;
                                            String codification = null;
                                            int sem = 0;
                                            int cat_ue = 0;
                                            int nat_ue = 0;
                                            int m_elim = 0;
                                            int m_valid = 0;
                                            float coef = 0;
                                            int cred = 0;
                                            int is_sout = 0;
                                            int is_comp = 0;
                                            for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                                                Cell MytempCell = tempRow.getCell(column);
                                                if (MytempCell != null) {
                                                    Index = MytempCell.getColumnIndex();
                                                    if (Index == 0) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            codification = MytempCell.getStringCellValue();
                                                        }
                                                    } else if (Index == 1) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding opUe =
                                                                    bindings.getOperationBinding("createOrUpdateUeExcel");
                                                                opUe.getParamsMap()
                                                                    .put("libelle", MytempCell.getStringCellValue());
                                                                opUe.getParamsMap()
                                                                    .put("utilisateur", getUtilisateur());
                                                                id_ue = (Long) opUe.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                    } else if (Index == 2) {
                                                        if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                                            (int) MytempCell.getNumericCellValue() != 0) {
                                                            sem = (int) MytempCell.getNumericCellValue();
                                                            if (!semestres.contains(new Long(sem))) {
                                                                semestres.add(new Long(sem));
                                                            }
                                                        }
                                                    } else if (Index == 3) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding is_exist =
                                                                    bindings.getOperationBinding("getCategorieUe");
                                                                is_exist.getParamsMap()
                                                                    .put("lib", MytempCell.getStringCellValue());
                                                                is_exist.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            DCIteratorBinding iterCatExist =
                                                                (DCIteratorBinding) bindings.get("getCategorieUE1Iterator");

                                                            if (iterCatExist.getEstimatedRowCount() > 0) {
                                                                cat_ue = Integer.parseInt(iterCatExist.getCurrentRow()
                                                                                                      .getAttribute("IdCategorieUe")
                                                                                                      .toString());

                                                            }
                                                        }
                                                    } else if (Index == 4) {
                                                        if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                                            (int) MytempCell.getNumericCellValue() != 0) {
                                                            cred = (int) MytempCell.getNumericCellValue();
                                                        }
                                                    } else if (Index == 5) {
                                                        if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                                            MytempCell.getNumericCellValue() != 0) {
                                                            coef = (float) MytempCell.getNumericCellValue();
                                                        }
                                                    } else if (Index == 6) {
                                                        m_valid = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 7) {
                                                        m_elim = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 8) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding is_exist =
                                                                    bindings.getOperationBinding("getNatureUe");
                                                                is_exist.getParamsMap()
                                                                    .put("lib", MytempCell.getStringCellValue());
                                                                is_exist.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            DCIteratorBinding iterExist =
                                                                (DCIteratorBinding) bindings.get("getNatureUe1Iterator");
                                                            if (iterExist.getEstimatedRowCount() > 0) {
                                                                nat_ue = Integer.parseInt(iterExist.getCurrentRow()
                                                                                                   .getAttribute("IdNatureUe")
                                                                                                   .toString());

                                                            }
                                                        }
                                                    } else if (Index == 9) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            if (MytempCell.getStringCellValue().equals("OUI")) {
                                                                is_comp = 1;
                                                            }
                                                        }
                                                    } else if (Index == 10) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            if (MytempCell.getStringCellValue().equals("OUI")) {
                                                                is_sout = 1;
                                                            }
                                                        }
                                                    } else {
                                                        Index++;
                                                    }
                                                }
                                            }
                                            if (0 != cat_ue && 0 != nat_ue && 0 != coef && 0 != cred && 0 != id_ue &&
                                                0 != sem && null != codification) {
                                                try {
                                                    OperationBinding op_fil_ue =
                                                        bindings.getOperationBinding("createOrUpdateFilUeExcel");
                                                    op_fil_ue.getParamsMap().put("ue_id", id_ue);
                                                    op_fil_ue.getParamsMap().put("sem_id", new Long(sem));
                                                    op_fil_ue.getParamsMap().put("cat_ue_id", new Long(cat_ue));
                                                    op_fil_ue.getParamsMap().put("codif", codification);
                                                    op_fil_ue.getParamsMap().put("cred", cred);
                                                    op_fil_ue.getParamsMap().put("coef", coef);
                                                    op_fil_ue.getParamsMap().put("maq", id_maq);
                                                    op_fil_ue.getParamsMap().put("moy_v", m_valid);
                                                    op_fil_ue.getParamsMap().put("moy_e", m_elim);
                                                    op_fil_ue.getParamsMap().put("nat", nat_ue);
                                                    op_fil_ue.getParamsMap().put("comp", is_comp);
                                                    op_fil_ue.getParamsMap().put("is_sout", is_sout);
                                                    op_fil_ue.getParamsMap().put("utilisateur", getUtilisateur());
                                                    fil_ue_id = (Long) op_fil_ue.execute();
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                        }
                                        skipcnt++;
                                    }
                                } else if (sheetIndex == 1) {
                                    for (org.apache.poi.ss.usermodel.Row tempRow : sheet) {
                                        if (skipcnt > skipRw) { //skip first n row for labels.
                                            int Index = 0;
                                            String codifEc = null;
                                            Long fu_id = new Long(0);
                                            int nat_ec = 0;
                                            int m_elim = 0;
                                            int m_valid = 0;
                                            float coef = 0;
                                            int cc = 0;
                                            int ct = 0;
                                            int hcm = 0;
                                            int htp = 0;
                                            int htd = 0;
                                            int htpe = 0;
                                            int hs = 0;
                                            for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                                                Cell MytempCell = tempRow.getCell(column);
                                                if (MytempCell != null) {
                                                    Index = MytempCell.getColumnIndex();
                                                    if (Index == 0) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            codifEc = MytempCell.getStringCellValue();
                                                        }
                                                    } else if (Index == 1) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding opUe =
                                                                    bindings.getOperationBinding("getFilUe");
                                                                opUe.getParamsMap()
                                                                    .put("cod", MytempCell.getStringCellValue());
                                                                opUe.getParamsMap().put("mq", id_maq);
                                                                opUe.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            DCIteratorBinding iterFue =
                                                                (DCIteratorBinding) bindings.get("getFilUeROVO1Iterator");
                                                            if (iterFue.getEstimatedRowCount() > 0) {
                                                                fu_id =
                                                                    (Long) iterFue.getCurrentRow()
                                                                    .getAttribute("IdFiliereUeSemstre");
                                                            }
                                                        }
                                                    } else if (Index == 2) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding opEc =
                                                                    bindings.getOperationBinding("createOrUpdateEcExcel");
                                                                opEc.getParamsMap()
                                                                    .put("libelle", MytempCell.getStringCellValue());
                                                                opEc.getParamsMap()
                                                                    .put("utilisateur", getUtilisateur());
                                                                id_ec = (Long) opEc.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                    } else if (Index == 3) {
                                                        if (MytempCell.getCellType().toString() == "STRING" &&
                                                            MytempCell.getStringCellValue() != null) {
                                                            try {
                                                                OperationBinding is_exist =
                                                                    bindings.getOperationBinding("getNatureEc");
                                                                is_exist.getParamsMap()
                                                                    .put("lib", MytempCell.getStringCellValue());
                                                                is_exist.execute();
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            DCIteratorBinding iterCatExist =
                                                                (DCIteratorBinding) bindings.get("getNatureEc1Iterator");
                                                            if (iterCatExist.getEstimatedRowCount() > 0) {
                                                                nat_ec = Integer.parseInt(iterCatExist.getCurrentRow()
                                                                                                      .getAttribute("IdNatureEc")
                                                                                                      .toString());
                                                            }
                                                        }
                                                    } else if (Index == 4) {
                                                        if (MytempCell.getCellType().toString() == "NUMERIC" &&
                                                            MytempCell.getNumericCellValue() != 0) {
                                                            coef = (float) MytempCell.getNumericCellValue();
                                                        }
                                                    } else if (Index == 5) {
                                                        m_valid = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 6) {
                                                        m_elim = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 7) {
                                                        cc = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 8) {
                                                        ct = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 9) {
                                                        hcm = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 10) {
                                                        htp = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 11) {
                                                        htd = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 12) {
                                                        htpe = (int) MytempCell.getNumericCellValue();
                                                    } else if (Index == 13) {
                                                        hs = (int) MytempCell.getNumericCellValue();
                                                    } else {
                                                        Index++;
                                                    }
                                                }
                                            }
                                            if (0 != fu_id && 0 != id_ec && 0 != nat_ec && 0 != coef) {
                                                try {
                                                    OperationBinding op_fil_ec =
                                                        bindings.getOperationBinding("createOrUpdateFilEcExcel");
                                                    op_fil_ec.getParamsMap().put("ec_id", id_ec);
                                                    op_fil_ec.getParamsMap().put("nat", nat_ec);
                                                    op_fil_ec.getParamsMap().put("codif", codifEc);
                                                    op_fil_ec.getParamsMap().put("coef", coef);
                                                    op_fil_ec.getParamsMap().put("moy_v", m_valid);
                                                    op_fil_ec.getParamsMap().put("moy_e", m_elim);
                                                    op_fil_ec.getParamsMap().put("cc", cc);
                                                    op_fil_ec.getParamsMap().put("ct", ct);
                                                    op_fil_ec.getParamsMap().put("fus", fu_id);
                                                    op_fil_ec.getParamsMap().put("hcm", hcm);
                                                    op_fil_ec.getParamsMap().put("htd", htd);
                                                    op_fil_ec.getParamsMap().put("htp", htp);
                                                    op_fil_ec.getParamsMap().put("htpe", htpe);
                                                    op_fil_ec.getParamsMap().put("hs", hs);
                                                    op_fil_ec.getParamsMap().put("utilisateur", getUtilisateur());
                                                    fil_ec_id = (Long) op_fil_ec.execute();
                                                    if (0 != fil_ec_id) {
                                                        if (0 != cc) {
                                                            try {
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

                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                        if (0 != ct) {
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
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                        }
                                        skipcnt++;
                                    }
                                }
                            }
                            for (Long sem_id : semestres) {
                                Integer nbr_cal = 0;
                                Long gdrSem_id = new Long(0);
                                try {
                                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                                    is_exist.getParamsMap()
                                        .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                                    is_exist.getParamsMap().put("sem_id", sem_id);
                                    is_exist.getParamsMap().put("an_id", new Long(getAnnee()));
                                    nbr_cal = (Integer) is_exist.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                if (0 == nbr_cal) {
                                    Long cal_id = new Long(0);
                                    try {
                                        OperationBinding opnewcal =
                                            bindings.getOperationBinding("CreateOrUpdateCalendar");
                                        opnewcal.getParamsMap()
                                            .put("niv_crt_id", currentPrcrs.getAttribute("IdNiveauFormationCohorte"));
                                        opnewcal.getParamsMap().put("sem_id", sem_id);
                                        opnewcal.getParamsMap().put("an_id", new Long(getAnnee()));
                                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                                        opnewcal.getParamsMap().put("sess_id", 1);
                                        cal_id = new Long((Integer) opnewcal.execute());
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    
                                }
                                try {
                                    OperationBinding opgs = bindings.getOperationBinding("CreateOrUpdateGrdSemestre");
                                    opgs.getParamsMap().put("niv_id", currentPrcrs.getAttribute("IdNiveau"));
                                    opgs.getParamsMap().put("gf_id", currentPrcrs.getAttribute("IdGradesFormation"));
                                    opgs.getParamsMap().put("sem_id", sem_id);
                                    opgs.getParamsMap().put("utilisateur", getUtilisateur());
                                    gdrSem_id = (Long) opgs.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                                //supprimer CC et Update %Ct
                                try {
                                    OperationBinding opdelcc = bindings.getOperationBinding("DeleteCCUpdatePrc");
                                    opdelcc.getParamsMap().put("pma_id", prcrs_mq_id);
                                    opdelcc.getParamsMap().put("str_id", new Long(getStructure()));
                                    opdelcc.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        WorkBook.close();
        //WorkBook.iterator().remove();
        this.getPopupImportMaq().hide();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("MaquetteValideImportedIterator");
        ViewObject vo = iter.getViewObject();
        vo.executeQuery();
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }
}
