package view.beans.evaluation.jury;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ColumnSelectionEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;

import view.beans.admin.AdministrationBean;
import view.beans.jsfutils.JSFUtils;

/*
 * ************Garmi*****************
 */
public class JuryBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popupNewJury;
    private RichPanelFormLayout formNewJury;
    private RichTable juryTable;
    private RichPopup popupEdit;
    private RichOutputFormatted formInputParcours;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer structure = Integer.parseInt(sessionScope.get("id_hs").toString());
    private Integer annee_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private RichPopup popupManyJury;
    private RichSelectOneChoice parcoursid;
    private RichPopup popupDelete;
    private RichSelectOneChoice semestreid;
    private RichSelectOneChoice sessionid;
    private RichPopup popupJuryExist;
    private RichSelectOneChoice parcours_edit_id;
    private RichSelectOneChoice session_edit_id;
    private RichSelectOneChoice semestre_edit_id;
    private RichPopup popupMemberExist;
    private RichTable userFormJuryTable;
    private RichSelectOneChoice roleSelected;
    private RichTable membreJuryTable;
    private RichPopup popupNoUser;
    private RichPanelCollection juryPanCol;
    private RichPanelHeader juryPanHdr;
    private RichPanelGroupLayout juryPanGrp;
    private RichPanelCollection userPanCol;
    private RichPanelHeader userPanHdr;
    private RichSelectBooleanCheckbox is_user;
    private RichPanelCollection membreJuryPanCol;
    private RichPanelHeader membreJuryPanHdr;
    private RichPanelGroupLayout membreJuryPanGrp;

    public JuryBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnNewJuryClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        //get current row and save its rowKey in view scope for later use
        //Verify if nbre jury parcours_annee < 2
        //JuryParcoursAnnee
        DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentP = iterP.getCurrentRow();
        //Jury Parcours
        OperationBinding jparcours = bindings.getOperationBinding("JuryParcoursAnnee");
        jparcours.getParamsMap().put("annee", getAnnee_univers());
        jparcours.getParamsMap()
            .put("parcours", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
        jparcours.execute();
        DCIteratorBinding iterJP = (DCIteratorBinding) bindings.get("JuryParcoursAnneeROVOIterator");

        //System.out.println("Nombre Jury : " + iterJP.getEstimatedRowCount());
        if (iterJP.getEstimatedRowCount() >= 2) {
            RichPopup popup = this.getPopupManyJury();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("JuryVO1Iterator");
            Row oldCcurrentRow = dciter.getCurrentRow();
            if (oldCcurrentRow != null) {
                ADFContext adfCtx = ADFContext.getCurrent();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            }
            AttributeBinding uticre = (AttributeBinding) bindings.getControlBinding("UtiCree");
            AttributeBinding annee_univ = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers");
            //perform row create
            OperationBinding operationBinding = bindings.getOperationBinding("CreateJury");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }

            //JSFUtils.setExpressionValue("#{bindings.IdAnneesUnivers.inputValue}", getAnnee_univers());
            //JSFUtils.setExpressionValue("#{bindings.UtiCree.inputValue}", getUtilisateur());
            uticre.setInputValue(getUtilisateur());
            annee_univ.setInputValue(getAnnee_univers());
            RichPopup popup = this.getPopupNewJury();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }
    }

    public void setPopupNewJury(RichPopup popupNewJury) {
        this.popupNewJury = popupNewJury;
    }

    public RichPopup getPopupNewJury() {
        return popupNewJury;
    }

    public void setFormNewJury(RichPanelFormLayout formNewJury) {
        this.formNewJury = formNewJury;
    }

    public RichPanelFormLayout getFormNewJury() {
        return formNewJury;
    }

    @SuppressWarnings("unchecked")
    public void OnDialogAction(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            //Verifier si le jury existe déja
            //System.out.println("Session : "+Integer.parseInt(getSessionid().getValue().toString()));
            OperationBinding is_jury_exist = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("isJutyExist");
            is_jury_exist.getParamsMap().put("annee", getAnnee_univers());
            is_jury_exist.getParamsMap().put("parcours", Integer.parseInt(getParcoursid().getValue().toString()));
            is_jury_exist.getParamsMap().put("semestre", Integer.parseInt(getSemestreid().getValue().toString()));
            //is_jury_exist.getParamsMap().put("sess", Integer.parseInt(getSessionid().getValue().toString()));
            is_jury_exist.execute();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("isJuryExistROVOIterator");
            Row current = dciter.getCurrentRow();
            if (current == null) {
                //System.out.println("Jury Not Exist Yet");
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
                this.getPopupNewJury().hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
            } else {
                //System.out.println("Ce Jury existe déja : pas la peine de le recreer");
                DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
                Row currentP = dciterP.getCurrentRow();
                OperationBinding refresh = BindingContext.getCurrent()
                                                         .getCurrentBindingsEntry()
                                                         .getOperationBinding("refreshJury");
                //parcours_id
                refresh.getParamsMap()
                    .put("parcours_id",
                         Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
                refresh.execute();

                RichPopup popup = this.getPopupJuryExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setJuryTable(RichTable juryTable) {
        this.juryTable = juryTable;
    }

    public RichTable getJuryTable() {
        return juryTable;
    }

    public void OnDialogCancel(ClientEvent clientEvent) {
        BindingContainer bindings = getBindings();
        RichPopup popup = this.getPopupNewJury();
        popup.hide();

        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("JuryVO1Iterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void OnEditClicked(ActionEvent actionEvent) {
        RichPopup popup = this.getPopupEdit();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
        return;
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
            return;
        }
    }

    public void setPopupEdit(RichPopup popupEdit) {
        this.popupEdit = popupEdit;
    }

    public RichPopup getPopupEdit() {
        return popupEdit;
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteDialogAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("JuryVO1Iterator");
            Row currentJry = dciter.getCurrentRow();
            //Vérifié si le jury a des membres : IsJuryEmpty
            OperationBinding is_empty = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("IsJuryEmpty");
            //parcours_id
            is_empty.getParamsMap().put("jury_id", Integer.parseInt(currentJry.getAttribute("IdJury").toString()));
            is_empty.execute();
            DCIteratorBinding dciterEmpty = (DCIteratorBinding) bindings.get("IsJuryEmptyIterator");
            Row currentEmpty = dciterEmpty.getCurrentRow();
            if (currentEmpty == null) {
                //Pas de membre dans ce jury
                //System.out.println("Deleting...");
                onDeleteClicked();
            } else {
                //System.out.println("Delete Member Before");
                RichPopup popup = this.getPopupMemberExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);

            }
        }
    }

    @SuppressWarnings("unchecked")
    public String onDeleteClicked() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void OnPopupShow(PopupFetchEvent popupFetchEvent) {
        //System.out.println("PopupFetchEvent !!! ");
        OperationBinding parcours = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetParcours");
        parcours.getParamsMap().put("id_user", getUtilisateur());
        parcours.getParamsMap().put("idDept", getStructure());
        parcours.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPopupNewJury());

        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("ParcoursIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //System.out.println(nextRow.getAttribute("LibParcours").toString());
        }
        rsi.closeRowSetIterator();
    }

    public void onshowP(LaunchPopupEvent launchPE) {
        System.out.println("LaunchPopupEvent !!! ");
    }

    public void setFormInputParcours(RichOutputFormatted formInputParcours) {
        this.formInputParcours = formInputParcours;
    }

    public RichOutputFormatted getFormInputParcours() {
        return formInputParcours;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setStructure(Integer structure) {
        this.structure = structure;
    }

    public Integer getStructure() {
        return structure;
    }

    public void setAnnee_univers(Integer annee_univers) {
        this.annee_univers = annee_univers;
    }

    public Integer getAnnee_univers() {
        return annee_univers;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getSession() {
        return session;
    }

    public void setPopupManyJury(RichPopup popupManyJury) {
        this.popupManyJury = popupManyJury;
    }

    public RichPopup getPopupManyJury() {
        return popupManyJury;
    }

    @SuppressWarnings("unchecked")
    public void OnDialogEditAction(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //Verifier si ce jury n'existe pas encore
            OperationBinding is_jury_exist = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("isJutyExist");
            is_jury_exist.getParamsMap().put("annee", getAnnee_univers());
            is_jury_exist.getParamsMap().put("parcours", Integer.parseInt(getParcours_edit_id().getValue().toString()));
            is_jury_exist.getParamsMap().put("semestre", Integer.parseInt(getSemestre_edit_id().getValue().toString()));
            is_jury_exist.getParamsMap().put("sess", Integer.parseInt(getSession_edit_id().getValue().toString()));
            is_jury_exist.execute();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("isJuryExistROVOIterator");
            Row current = dciter.getCurrentRow();
            if (current == null) {
                //System.out.println("Modification Possible ");
                JSFUtils.setExpressionValue("#{bindings.UtiModifie.inputValue}", getUtilisateur());
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
                this.getPopupEdit().hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
            } else {
                //System.out.println("Ce jury existe déja inposible de modifier");
                RichPopup popup = this.getPopupJuryExist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
                OperationBinding refresh = BindingContext.getCurrent()
                                                         .getCurrentBindingsEntry()
                                                         .getOperationBinding("refreshJury");
                refresh.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
            }
        }
    }

    public void setParcoursid(RichSelectOneChoice parcoursid) {
        this.parcoursid = parcoursid;
    }

    public RichSelectOneChoice getParcoursid() {
        return parcoursid;
    }

    public void setPopupDelete(RichPopup popupDelete) {
        this.popupDelete = popupDelete;
    }

    public RichPopup getPopupDelete() {
        return popupDelete;
    }

    public void setSemestreid(RichSelectOneChoice semestreid) {
        this.semestreid = semestreid;
    }

    public RichSelectOneChoice getSemestreid() {
        return semestreid;
    }

    public void setSessionid(RichSelectOneChoice sessionid) {
        this.sessionid = sessionid;
    }

    public RichSelectOneChoice getSessionid() {
        return sessionid;
    }

    public void setPopupJuryExist(RichPopup popupJuryExist) {
        this.popupJuryExist = popupJuryExist;
    }

    public RichPopup getPopupJuryExist() {
        return popupJuryExist;
    }

    public void setParcours_edit_id(RichSelectOneChoice parcours_edit_id) {
        this.parcours_edit_id = parcours_edit_id;
    }

    public RichSelectOneChoice getParcours_edit_id() {
        return parcours_edit_id;
    }

    public void setSession_edit_id(RichSelectOneChoice session_edit_id) {
        this.session_edit_id = session_edit_id;
    }

    public RichSelectOneChoice getSession_edit_id() {
        return session_edit_id;
    }

    public void setSemestre_edit_id(RichSelectOneChoice semestre_edit_id) {
        this.semestre_edit_id = semestre_edit_id;
    }

    public RichSelectOneChoice getSemestre_edit_id() {
        return semestre_edit_id;
    }

    @SuppressWarnings("unchecked")
    public void OnTableRowChanged(AttributeChangeEvent attributeChangeEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentP = dciterP.getCurrentRow();
        OperationBinding refresh = BindingContext.getCurrent()
                                                 .getCurrentBindingsEntry()
                                                 .getOperationBinding("refreshJury");
        //parcours_id
        refresh.getParamsMap()
            .put("parcours_id", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
        refresh.execute();
        //System.out.println("Parcours : " + currentP.getAttribute("IdNiveauFormationParcours").toString());
    }

    public void OnColumChanged(ColumnSelectionEvent columnSelectionEvent) {
        System.out.println("Column Selected");
    }

    public void onAttributeTableChanged(AttributeChangeEvent attributeChangeEvent) {
        System.out.println("Attribute Table Changed");
    }

    public void OnRowDisclosure(RowDisclosureEvent rowDisclosureEvent) {
        System.out.println("Row Disclosed");
    }

    @SuppressWarnings("unchecked")
    public void OnSelected(SelectionEvent selectionEvent) {
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentP = dciterP.getCurrentRow();
        //System.out.println("Parcours Selected : " + currentP.getAttribute("IdNiveauFormationParcours").toString());
        OperationBinding refresh = BindingContext.getCurrent()
                                                 .getCurrentBindingsEntry()
                                                 .getOperationBinding("refreshJury");
        refresh.getParamsMap()
            .put("parcours_id", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
        refresh.execute();
        //GetUtilisateurFormationJury
        OperationBinding userFormJury = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetUtilisateurFormationJury");
        userFormJury.getParamsMap().put("annee", getAnnee_univers());
        userFormJury.getParamsMap().put("id_fr", Integer.parseInt(currentP.getAttribute("IdFormation").toString()));
        userFormJury.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserFormJuryTable());
    }

    @SuppressWarnings("unchecked")
    public void getUser(){
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentP = dciterP.getCurrentRow();
        OperationBinding userFormJury = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetUtilisateurFormationJury");
        userFormJury.getParamsMap().put("annee", getAnnee_univers());
        userFormJury.getParamsMap().put("id_fr", Integer.parseInt(currentP.getAttribute("IdFormation").toString()));
        userFormJury.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserFormJuryTable());
    }
    
    
    public void setPopupMemberExist(RichPopup popupMemberExist) {
        this.popupMemberExist = popupMemberExist;
    }

    public RichPopup getPopupMemberExist() {
        return popupMemberExist;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.unchecked-conversion-or-cast" })
    public void OnSaveMemberClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciterJ = (DCIteratorBinding) bindings.get("JuryVO1Iterator");
        Row currentJ = dciterJ.getCurrentRow();
        //System.out.println("currentJ : "+currentJ);
        try {
            OperationBinding oprole = bindings.getOperationBinding("getrole");
            oprole.getParamsMap().put("role_id", new Long(161));
            String role = (String) oprole.execute();
            //System.out.println("role : "+role);
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUserFor");
            @SuppressWarnings("unchecked")
            ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();
            //System.out.println("Users selected count : " + users.size());
            if (users.size() != 0) {
                // Pour chaque utilisateur lui accorder les accees choisies
                for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                    //Verifié si l'acces est déja autorisé
                    //System.out.println("Check Access ...");
                    OperationBinding isAllowAcces = bindings.getOperationBinding("isJuryAllowedAccess");
                    isAllowAcces.getParamsMap().put("id_jury", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                    isAllowAcces.getParamsMap().put("id_user", users.get(usercounter));
                    Object resul = isAllowAcces.execute();
                    DCIteratorBinding iterjuryAccess =  (DCIteratorBinding) bindings.get("isJuryAllowedAccessIterator");
                    Row crnt = iterjuryAccess.getCurrentRow();
                    if (crnt == null) {
                        //System.out.println("Access Not Yet ");
                        //Verifier si le role est president
                        if (!(this.getRoleSelected().getValue().toString().equals("ACCES SIMPLE"))) {
                            //Verifier si le jury n'a pas de president
                            //System.out.println("Role PRESIDENT");
                            //System.out.println("Verification si jury n'a pas encore de président en cours ...");
                            OperationBinding isjuryPresi = bindings.getOperationBinding("IsJuryPresiExist");
                            isjuryPresi.getParamsMap().put("j", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                            isjuryPresi.execute();
                            DCIteratorBinding iterpresi = (DCIteratorBinding) bindings.get("IsJuryPresidentExistIterator");
                            Row crntPresi = iterpresi.getCurrentRow();
                            if (crntPresi == null) {
                                // Pas encore de president pour ce jury
                                try{
                                    OperationBinding opAllowAcces = bindings.getOperationBinding("AllowAccessTo");
                                    opAllowAcces.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                                    opAllowAcces.getParamsMap().put("IdJury", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                                    opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                    opAllowAcces.getParamsMap().put("Role", this.getRoleSelected()
                                                                                .getValue()
                                                                                .toString());
                                    resul = opAllowAcces.execute();
                                    
                                    OperationBinding opAlloTo = bindings.getOperationBinding("AllowAcessFusFecTo");
                                    opAlloTo.getParamsMap().put("prcrs_id", currentJ.getAttribute("IdNiveauFormationParcours"));
                                    opAlloTo.getParamsMap().put("annee_id", new Long(getAnnee_univers()));
                                    opAlloTo.getParamsMap().put("sem_id", currentJ.getAttribute("IdSemestre"));
                                    opAlloTo.getParamsMap().put("pdt_id", new Long (users.get(usercounter)));
                                    opAlloTo.getParamsMap().put("user_id", new Long(getUtilisateur()));
                                    opAlloTo.execute();
                                //Attribuer le role president jury BD et weblogic
                                try {
                                    OperationBinding allowuserRle =  bindings.getOperationBinding("CreateOrUpdateUserRole");
                                    allowuserRle.getParamsMap().put("user_id", users.get(usercounter));
                                    allowuserRle.getParamsMap().put("role_id", new Long(161)); // President Jury
                                    allowuserRle.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    allowuserRle.execute();
                                    //System.out.println("Role responsable assigned in bd !");
                                    //assign weblogic role
                                    try {
                                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                                        opusername.getParamsMap().put("user_id", users.get(usercounter));
                                        String username = (String) opusername.execute();
                                        OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                        opmat.getParamsMap().put("utilisateur", new Long(usercounter));
                                        String matricule = (String) opmat.execute();
                                        //matricule = "passer123*";
                                        if (null != role && null != username && null != matricule) {
                                            role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                            try {
                                                AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                                AdministrationBean.assignWlsRole2User(role, username);
                                                //System.out.println("Role <<" + role + ">> assign to <<" + username +
                                                //                ">> in weblogic");
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
                                } catch (Exception e) {
                                    System.out.println(e);
                                }  
                            }
                        } else {
                            //Role : ACCES SIMPLE
                            OperationBinding opAllowAcces = bindings.getOperationBinding("AllowAccessTo");
                            opAllowAcces.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                            opAllowAcces.getParamsMap()
                                .put("IdJury", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                            opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                            opAllowAcces.getParamsMap().put("Role", this.getRoleSelected()
                                                                        .getValue()
                                                                        .toString());
                            resul = opAllowAcces.execute();
                        }
                    } else {
                        //System.out.println("Acces déja autorisé");
                        //Verifier Si le new Role = PRESIDENT
                        if ((crnt.getAttribute("Role")
                                 .toString()
                                 .equals("ACCES SIMPLE")) && !(this.getRoleSelected()
                                                                   .getValue()
                                                                   .toString()
                                                                   .equals("ACCES SIMPLE"))) {
                            //New role PRESI and Old Role SIMPLE
                            OperationBinding isjuryPresi = bindings.getOperationBinding("IsJuryPresiExist");
                            isjuryPresi.getParamsMap()
                                .put("j", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                            isjuryPresi.execute();
                            DCIteratorBinding iterpresi =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("IsJuryPresidentExistIterator");
                            Row crntPresi = iterpresi.getCurrentRow();
                            if (crntPresi == null) {
                                //Update Role
                                OperationBinding updateRole = bindings.getOperationBinding("UpdateRole");
                                updateRole.getParamsMap().put("idUser", users.get(usercounter));
                                updateRole.getParamsMap().put("idJury", Integer.parseInt(currentJ.getAttribute("IdJury").toString()));
                                updateRole.getParamsMap().put("role", this.getRoleSelected().getValue().toString());
                                updateRole.getParamsMap().put("utimodif", getUtilisateur());
                                resul = updateRole.execute();
                                //System.out.println("Updated");
                                try {
                                    OperationBinding allowuserRle =  bindings.getOperationBinding("CreateOrUpdateUserRole");
                                    allowuserRle.getParamsMap().put("user_id", users.get(usercounter));
                                    allowuserRle.getParamsMap().put("role_id", new Long(161)); // President Jury
                                    allowuserRle.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    allowuserRle.execute();
                                   // System.out.println("Role responsable assigned in bd !");
                                    //assign weblogic role
                                    OperationBinding opAlloTo = bindings.getOperationBinding("AllowAcessFusFecTo");
                                    opAlloTo.getParamsMap().put("prcrs_id", currentJ.getAttribute("IdNiveauFormationParcours"));
                                    opAlloTo.getParamsMap().put("annee_id", new Long(getAnnee_univers()));
                                    opAlloTo.getParamsMap().put("sem_id", currentJ.getAttribute("IdSemestre"));
                                    opAlloTo.getParamsMap().put("pdt_id", new Long (users.get(usercounter)));
                                    opAlloTo.getParamsMap().put("user_id", new Long(getUtilisateur()));
                                    opAlloTo.execute();
                                    try {
                                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                                        opusername.getParamsMap().put("user_id", users.get(usercounter));
                                        String username = (String) opusername.execute();
                                        OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                        opmat.getParamsMap().put("utilisateur", new Long(usercounter));
                                        String matricule = (String) opmat.execute();
                                        //matricule = "passer123*";
                                        if (null != role && null != username && null != matricule) {
                                            role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                            try {
                                                AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                                AdministrationBean.assignWlsRole2User(role, username);
                                                // System.out.println("Role <<" + role + ">> assign to <<" + username +
                                                //                    ">> in weblogic");
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
                        }
                    }
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    if (!operationBinding.getErrors().isEmpty()) {
                        //handle errors if any
                        //...
                        return;
                    }
                }
                this.getIs_user().setValue(false);
                DCIteratorBinding iterjury = (DCIteratorBinding) getBindings().get("UtilisateursJuryVO1Iterator");
                ViewObject vo = iterjury.getViewObject();
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserFormJuryTable());
            }else{
                //System.out.println("No Users Selected");
                RichPopup popup = this.getPopupNoUser();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUserFormJuryTable(RichTable userFormJuryTable) {
        this.userFormJuryTable = userFormJuryTable;
    }

    public RichTable getUserFormJuryTable() {
        return userFormJuryTable;
    }

    public void setRoleSelected(RichSelectOneChoice roleSelected) {
        this.roleSelected = roleSelected;
    }

    public RichSelectOneChoice getRoleSelected() {
        return roleSelected;
    }

    public void setMembreJuryTable(RichTable membreJuryTable) {
        this.membreJuryTable = membreJuryTable;
    }

    public RichTable getMembreJuryTable() {
        return membreJuryTable;
    }

    public void OnEditJuryMemberClicked(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void OnDeleteMemberAction(DialogEvent dialogEvent) {
        // DeleteMember
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("DeleteMember");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                
            } else {
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryTable());
    }

    public void setPopupNoUser(RichPopup popupNoUser) {
        this.popupNoUser = popupNoUser;
    }

    public RichPopup getPopupNoUser() {
        return popupNoUser;
    }

    public void OnListeUserClicked(ActionEvent actionEvent) {
        getUser();
    }

    @SuppressWarnings("unchecked")
    public void OnNewJuryAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterP = (DCIteratorBinding) bindings.get("ParcoursIterator");
        Row currentP = iterP.getCurrentRow();
        try {
            OperationBinding opgs = bindings.getOperationBinding("getsemestregrade");
            opgs.getParamsMap().put("niv_id", currentP.getAttribute("IdNiveau"));
            opgs.getParamsMap().put("gf_id", currentP.getAttribute("IdGradesFormation"));
            opgs.execute();
        } catch (Exception e) {
            //System.out.println("Catch grade semestre");
            System.out.println(e);
        }
        DCIteratorBinding itersem = (DCIteratorBinding) getBindings().get("getsemestregradeIterator");
        if (itersem.getCurrentRow() != null) {
            RowSetIterator rsie = itersem.getViewObject().createRowSetIterator(null);
            while (rsie.hasNext()) {
                Row nextRowSem = rsie.next();
                Long id_jury = new Long(0);
                try {
                    OperationBinding opNewJury = getBindings().getOperationBinding("CreateOrUpdateJury");
                    opNewJury.getParamsMap().put("niv_prcrs_id", currentP.getAttribute("IdNiveauFormationParcours"));
                    opNewJury.getParamsMap().put("sem_id",nextRowSem.getAttribute("IdSemestre"));
                    opNewJury.getParamsMap().put("an_id", new Long (getAnnee_univers()));
                    opNewJury.getParamsMap().put("utilisateur", new Long (getUtilisateur()));
                    id_jury = (Long) opNewJury.execute();
                    //System.out.println("id_jury : "+id_jury);
                    //getResponsableUe
                    OperationBinding opRespUe = getBindings().getOperationBinding("getResponsableUe");
                    opRespUe.getParamsMap().put("parcours", currentP.getAttribute("IdNiveauFormationParcours"));
                    opRespUe.getParamsMap().put("semestre",nextRowSem.getAttribute("IdSemestre"));
                    opRespUe.getParamsMap().put("annee", new Long (getAnnee_univers()));
                    opRespUe.execute();
                    DCIteratorBinding iterResp = (DCIteratorBinding) bindings.get("ResponsableUePrcrsMaqAn1Iterator");
                    RowSetIterator rsi = iterResp.getViewObject().createRowSetIterator(null);
                    while (rsi.hasNext()){
                        Row nextRow = rsi.next();
                        OperationBinding opuserJury = bindings.getOperationBinding("createUserJury");
                        opuserJury.getParamsMap().put("id_user", nextRow.getAttribute("IdUtilisateur"));
                        opuserJury.getParamsMap().put("idjury", id_jury);
                        opuserJury.getParamsMap().put("utilisateur", getUtilisateur());
                        opuserJury.getParamsMap().put("role_", "ACCES SIMPLE");
                        opuserJury.execute();
                    }
                    rsi.closeRowSetIterator();
                    
                } catch (Exception e) {
                    //System.out.println("Catch CreateOrUpdateJury");
                    System.out.println(e);
                }
            }
            rsie.closeRowSetIterator();
            
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        }
        DCIteratorBinding iterjury = (DCIteratorBinding) getBindings().get("JuryVO1Iterator");
        ViewObject vo=iterjury.getViewObject();  
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMembreJuryTable());
    }

    public void setJuryPanCol(RichPanelCollection juryPanCol) {
        this.juryPanCol = juryPanCol;
    }

    public RichPanelCollection getJuryPanCol() {
        return juryPanCol;
    }

    public void setJuryPanHdr(RichPanelHeader juryPanHdr) {
        this.juryPanHdr = juryPanHdr;
    }

    public RichPanelHeader getJuryPanHdr() {
        return juryPanHdr;
    }

    public void setJuryPanGrp(RichPanelGroupLayout juryPanGrp) {
        this.juryPanGrp = juryPanGrp;
    }

    public RichPanelGroupLayout getJuryPanGrp() {
        return juryPanGrp;
    }

    public void onUserSelected(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("UtilisateurFormationJuryROVOIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserFormJuryTable());
    }

    public void setUserPanCol(RichPanelCollection userPanCol) {
        this.userPanCol = userPanCol;
    }

    public RichPanelCollection getUserPanCol() {
        return userPanCol;
    }

    public void setUserPanHdr(RichPanelHeader userPanHdr) {
        this.userPanHdr = userPanHdr;
    }

    public RichPanelHeader getUserPanHdr() {
        return userPanHdr;
    }

    public void setIs_user(RichSelectBooleanCheckbox is_user) {
        this.is_user = is_user;
    }

    public RichSelectBooleanCheckbox getIs_user() {
        return is_user;
    }
    
    public void initTopJury(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateurFormationJuryROVOIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isSelected")){
                if(nextRow.getAttribute("isSelected").equals(false)){
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_user().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initJury(){
        if (null != this.getIs_user().getValue()) {
            if (this.getIs_user().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("UtilisateurFormationJuryROVOIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserFormJuryTable());
            } 
        }
    }

    public void setMembreJuryPanCol(RichPanelCollection membreJuryPanCol) {
        this.membreJuryPanCol = membreJuryPanCol;
    }

    public RichPanelCollection getMembreJuryPanCol() {
        return membreJuryPanCol;
    }

    public void setMembreJuryPanHdr(RichPanelHeader membreJuryPanHdr) {
        this.membreJuryPanHdr = membreJuryPanHdr;
    }

    public RichPanelHeader getMembreJuryPanHdr() {
        return membreJuryPanHdr;
    }

    public void setMembreJuryPanGrp(RichPanelGroupLayout membreJuryPanGrp) {
        this.membreJuryPanGrp = membreJuryPanGrp;
    }

    public RichPanelGroupLayout getMembreJuryPanGrp() {
        return membreJuryPanGrp;
    }
}
