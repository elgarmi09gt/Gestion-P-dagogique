package view.beans.paramSup.accesue;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import view.beans.admin.AdministrationBean;

import oracle.jbo.Row;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class UesAcessBean {
    private RichSelectOneRadio roleChecked;
    private RichTable ueTable;
    private RichTable ueAccesTable;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer id_hs = Integer.parseInt(sessionScope.get("id_hs").toString());
    private RichPopup popupConfResp;
    private RichPanelGroupLayout filUePanGrp;
    private RichPanelCollection filUePanCol;
    private RichPopup popupConfRespFilEc;
    private RichPanelGroupLayout pangrp;
    private RichPanelCollection filEcPanCol;
    private RichPanelGroupLayout filEcPanGrp;
    private RichSelectBooleanCheckbox is_grp;
    private RichColumn colcheck;
    private RichColumn colEcCheck;
    private RichSelectBooleanCheckbox isec_grp;
    final int RESP_EC = 64;
    final int RESP_UE = 63;

    public UesAcessBean() {
    }

    @SuppressWarnings("unchecked")
    public void OnUserValueChangeListener(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
        Integer Id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        System.out.println("Id User " + Id);
        System.out.println("Id_Hs " + getId_hs());
        System.out.println("Id_An " + getAnne_univers());
        OperationBinding lesUes = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("GetUeAcces");
       
        lesUes.getParamsMap().put("annee", getAnne_univers());
        lesUes.getParamsMap().put("uti", Id);
        lesUes.getParamsMap().put("id_hs", getId_hs());
        lesUes.execute();
        
        OperationBinding lesaccesUes = BindingContext.getCurrent()
                                                     .getCurrentBindingsEntry()
                                                     .getOperationBinding("GetAccesUe");
        lesaccesUes.getParamsMap().put("annee", getAnne_univers());
        lesaccesUes.getParamsMap().put("uti", Id);
        //lesaccesUes.getParamsMap().put("id_hs", getId_hs());
        lesaccesUes.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeAccesTable());
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveClicked(ActionEvent actionEvent) {
        // Il peut etre important de verifier si l'acces n'est pas encore autorisé avant d'enregistrer une autorisation
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterUser = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateursROVO1Iterator");
        Row currentuser = iterUser.getCurrentRow();
        Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
        
        DCIteratorBinding iterNiv_form = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateurNiveauFormationRO1Iterator");
        Row currentniv = iterNiv_form.getCurrentRow();
        Integer niv_id = Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString());
        
        try {
            OperationBinding uesSelected = bindings.getOperationBinding("getSelectedUes");
            ArrayList<Long> ues = (ArrayList<Long>) uesSelected.execute();
            if (ues.size() != 0) {
                // Pour chaque utilisateur lui accorder les accees choisies
                for (int uecounter = 0; uecounter < ues.size(); uecounter++) {
                    //Verifié si l'acces est déja autorisé
                    /*System.out.println("s_id : "+getId_hs());
                    System.out.println("User : "+IdUser);
                    System.out.println("Annee : "+getAnne_univers());*/
                    System.out.println("Fil_ue selected : "+ues.get(uecounter));
                    OperationBinding isAllowAcces = bindings.getOperationBinding("IsUeAccesAllowed");
                    //isAllowAcces.getParamsMap().put("s_id", getId_hs());
                    isAllowAcces.getParamsMap().put("uti", IdUser);
                    isAllowAcces.getParamsMap().put("fil_ue", ues.get(uecounter));
                    isAllowAcces.getParamsMap().put("annee", getAnne_univers());
                    /*
                    isAllowAcces.getParamsMap().put("role", this.getRoleChecked()
                                                                .getValue()
                                                                .toString()); */
                    Object resul = isAllowAcces.execute();
                    DCIteratorBinding iterUeAccess = (DCIteratorBinding) BindingContext.getCurrent()
                                                             .getCurrentBindingsEntry()
                                                             .get("IsUeAccesAllowedROVOIterator");
                    System.out.println("IsUeAccesAllowed : "+iterUeAccess.getEstimatedRowCount());
                    Row crnt = iterUeAccess.getCurrentRow();
                    if (crnt == null) {
                        // L'utilisateur n'a pas encore acces a cette ue
                        // Verifier le type d'acces
                        if (this.getRoleChecked().getValue().toString().equals("RESPONSABLE UE")) {
                            // Verifier si l'ue n'a pas de responsable déja
                            OperationBinding is_resp_ue = bindings.getOperationBinding("IsUeResponsableExist");     
                            is_resp_ue.getParamsMap().put("s_id", getId_hs());
                            is_resp_ue.getParamsMap().put("fil_ue", ues.get(uecounter));
                            is_resp_ue.getParamsMap().put("annee", getAnne_univers());
                            is_resp_ue.execute();
                            DCIteratorBinding iterIsResponsable = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("IsResponsableUeExistIterator");
                            Row is_exist_resp = iterIsResponsable.getCurrentRow();
                            if (is_exist_resp == null) {
                                System.out.println("Pas de responsable : inserting responsable... ");
                                OperationBinding opAllowAcces = bindings.getOperationBinding("AllowedAccessTo");
                                opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                                opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                opAllowAcces.getParamsMap().put("IdFiliereUeSemstre", ues.get(uecounter));
                                opAllowAcces.getParamsMap().put("Role", this.getRoleChecked().getValue().toString());
                                resul = opAllowAcces.execute();
                                
                                System.out.println("Allow role reponsable_ue to user : "+IdUser);
                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                allowuserRle.getParamsMap().put("user_id", IdUser);
                                allowuserRle.getParamsMap().put("role_id", new Long(RESP_UE)); // Responsable ue
                                allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                allowuserRle.execute();
                                System.out.println("Access Allowed !");
                                //assign_role in weblogic
                                try {
                                    System.out.println("Assigning role to user in weblogic...");
                                    OperationBinding opusername = bindings.getOperationBinding("getusername");
                                    opusername.getParamsMap().put("user_id", new Long(IdUser));
                                    String username = (String) opusername.execute();
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", new Long(RESP_UE));
                                    String role = (String) oprole.execute();
                                    OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                    opmat.getParamsMap().put("utilisateur", new Long(IdUser));
                                    String matricule = (String) opmat.execute();
                                    //matricule = "passer123*";
                                    if (null != role && null != username && null != matricule) {
                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                        try {
                                            AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                            AdministrationBean.assignWlsRole2User(role, username);
                                            //System.out.println("Role assigned !");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("username : " + username + " role : " + role);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                
                            } else {
                                // Cet ue a deja un responsble
                                System.out.println("Responsable existant pour cet ue");
                            }
                        } else {
                            // Role acces simple
                            System.out.println("Assigning role simple ...");
                            OperationBinding opAllowAcces = bindings.getOperationBinding("AllowedAccessTo");
                            opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                            opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                            opAllowAcces.getParamsMap().put("IdFiliereUeSemstre", ues.get(uecounter));
                            opAllowAcces.getParamsMap().put("Role", this.getRoleChecked().getValue().toString());
                            resul = opAllowAcces.execute();
                        }
                    } else {
                        // if new role = RESPNSABLE UE et old role = ACCES SIMPLE
                        System.out.println("Update possible");
                        if (!(crnt.getAttribute("Role").toString().equals("RESPONSABLE UE")) && (this.getRoleChecked().getValue().toString().equals("RESPONSABLE UE"))) {
                            // Update possible
                            // Verifier si l'ue a un responsable
                            OperationBinding is_resp_ue = bindings.getOperationBinding("IsUeResponsableExist");
                            is_resp_ue.getParamsMap().put("s_id", getId_hs());
                            is_resp_ue.getParamsMap().put("fil_ue", ues.get(uecounter));
                            is_resp_ue.getParamsMap().put("annee", getAnne_univers());
                            is_resp_ue.execute();
                            DCIteratorBinding iterExist = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("IsResponsableUeExistIterator");
                            Row is_exist_resp = iterExist.getCurrentRow();
                            if (is_exist_resp == null) {
                                System.out.println("Update role required from ACCES SIMPLE to RESPNSABLE UE !!!");
                                OperationBinding updateRole = bindings.getOperationBinding("UpdateResponsableUe");
                                updateRole.getParamsMap().put("idUser", IdUser);
                                updateRole.getParamsMap().put("idFilUe", ues.get(uecounter));
                                updateRole.getParamsMap().put("role", this.getRoleChecked().getValue().toString());
                                updateRole.getParamsMap().put("utimodif", getUtilisateur());
                                updateRole.getParamsMap().put("id_uti_fil_ue",Integer.parseInt(crnt.getAttribute("IdUtilisFilierUeSem").toString()));
                                resul = updateRole.execute();
                                
                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                allowuserRle.getParamsMap().put("user_id", IdUser);
                                allowuserRle.getParamsMap().put("role_id", new Long(RESP_UE)); // Responsable ue
                                allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                allowuserRle.execute();
                                
                                try {
                                    OperationBinding opusername = bindings.getOperationBinding("getusername");
                                    opusername.getParamsMap().put("user_id", new Long(IdUser));
                                    String username = (String) opusername.execute();
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", new Long(RESP_UE));
                                    String role = (String) oprole.execute();
                                    OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                    opmat.getParamsMap().put("utilisateur", new Long(IdUser));
                                    String matricule = (String) opmat.execute();
                                    //matricule = "passer123*";
                                    if (null != role && null != username && null != matricule) {
                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                        try {
                                            AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                            AdministrationBean.assignWlsRole2User(role, username);
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("username : " + username + " role : " + role);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                            } else {
                                System.out.println("Responsable deja defini pour cet ue !!!");
                            }
                        } else {
                            System.out.println("Acces déja autorisé !!!");
                        }
                    }
                }
                // Tout ce passe bien On commit
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();

                OperationBinding lesUes = BindingContext.getCurrent()
                                                        .getCurrentBindingsEntry()
                                                        .getOperationBinding("GetUeAcces");
                
                lesUes.getParamsMap().put("annee", getAnne_univers());
                lesUes.getParamsMap().put("uti", IdUser);
                lesUes.getParamsMap().put("id_hs", getId_hs());
                lesUes.getParamsMap().put("niv_form", niv_id);
                lesUes.execute();
                
                OperationBinding lesaccesUes = BindingContext.getCurrent()
                                                             .getCurrentBindingsEntry()
                                                             .getOperationBinding("GetAccesUe");
                lesaccesUes.getParamsMap().put("annee", getAnne_univers());
                lesaccesUes.getParamsMap().put("uti", IdUser);
                lesaccesUes.getParamsMap().put("id_hs", getId_hs());
                lesaccesUes.getParamsMap().put("niv_form", niv_id);
                lesaccesUes.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeAccesTable());
                
            } else {
                System.out.println("Pas d'ue selectionner");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setRoleChecked(RichSelectOneRadio roleChecked) {
        this.roleChecked = roleChecked;
    }

    public RichSelectOneRadio getRoleChecked() {
        return roleChecked;
    }

    public void setUeTable(RichTable ueTable) {
        this.ueTable = ueTable;
    }

    public RichTable getUeTable() {
        return ueTable;
    }

    public void setUeAccesTable(RichTable ueAccesTable) {
        this.ueAccesTable = ueAccesTable;
    }

    public RichTable getUeAccesTable() {
        return ueAccesTable;
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setId_hs(Integer id_hs) {
        this.id_hs = id_hs;
    }

    public Integer getId_hs() {
        return id_hs;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        try{
            AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
            Integer user_id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
            AttributeBinding attrnivformBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormation");
            Integer niv_form_id = (Integer.parseInt(attrnivformBinding.getInputValue().toString()));
                System.out.println("User_id : "+user_id);
                System.out.println("Niveau_Formation_id : "+niv_form_id);
                OperationBinding lesUes = BindingContext.getCurrent()
                                                        .getCurrentBindingsEntry()
                                                        .getOperationBinding("GetUeAcces");
                
                lesUes.getParamsMap().put("annee", getAnne_univers());
                lesUes.getParamsMap().put("uti", user_id);
                lesUes.getParamsMap().put("id_hs", getId_hs());
                lesUes.getParamsMap().put("niv_form", niv_form_id);
                lesUes.execute();
                
                OperationBinding lesaccesUes = BindingContext.getCurrent()
                                                             .getCurrentBindingsEntry()
                                                             .getOperationBinding("GetAccesUe");
                lesaccesUes.getParamsMap().put("annee", getAnne_univers());
                lesaccesUes.getParamsMap().put("uti", user_id);
                lesaccesUes.getParamsMap().put("id_hs", getId_hs());
                lesaccesUes.getParamsMap().put("niv_form", niv_form_id);
                lesaccesUes.execute();
        
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUeAccesTable());
        }catch(Exception e){
            System.out.println(e);
        }
}

    public void OnUeAccessAllowedClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        Integer error_count = 0;
        try {
            //OperationBinding selectedetd = bindings.getOperationBinding("getSelectedEtudiant");
            OperationBinding selectedfilue = bindings.getOperationBinding("getSelectedFiliereUePrcrs");
            @SuppressWarnings("unchecked")
            //ArrayList<Long> etudiants = (ArrayList<Long>) selectedetd.execute();
            HashMap<String, String> filues = (HashMap<String, String>) selectedfilue.execute();
            if (0 != filues.size()) {
                for (String i : filues.keySet()) {
                    System.out.println("codification : " + i + " parcours_maq_an: " + filues.get(i));
                }
            }else{
                System.out.println("No filiere selected");
            }
        }catch(Exception e){
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    public void onEditResponsableClicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding fil_ue_iter = (DCIteratorBinding) bindings.get("FiliereUeSemestresIterator");
        Row curent  = fil_ue_iter.getCurrentRow();
        //sessionScope.put("is_fr_param", true);
        Long fil_ue_id = Long.parseLong(curent.getAttribute("IdFiliereUeSemstre").toString());
        Long pma_id = Long.parseLong(curent.getAttribute("IdParcoursMaquetAnnee").toString());
        //responsable to update utils_filiere_ue_semestre table
        OperationBinding opresp = bindings.getOperationBinding("getResponsableFilUe");
        opresp.getParamsMap().put("fil_ue_id", fil_ue_id);
        opresp.getParamsMap().put("pma", pma_id);
        Long uti_resp_fil_ue_id = (Long) opresp.execute();
        //FiliereUe to change responsable
        sessionScope.put("fil_ue_", curent.getAttribute("LibelleLong"));
        sessionScope.put("fil_ue", fil_ue_id);
        sessionScope.put("pma_", pma_id);
        sessionScope.put("resp_fil_ue", uti_resp_fil_ue_id);
        RichPopup pop = this.getPopupConfResp();
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        pop.show(ph);
    }

    public void setPopupConfResp(RichPopup popupConfResp) {
        this.popupConfResp = popupConfResp;
    }

    public RichPopup getPopupConfResp() {
        return popupConfResp;
    }

    @SuppressWarnings("unchecked")
    public void OnResponsableConfirmClicked(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = this.getBindings();
            Long fil_ue_id = Long.parseLong(sessionScope.get("fil_ue").toString());
            Long pma_id = Long.parseLong(sessionScope.get("pma_").toString());
            Long old_user_fil_ue = Long.parseLong(sessionScope.get("resp_fil_ue").toString());
            String role = null;
            DCIteratorBinding iteruser = (DCIteratorBinding) bindings.get("UtilisateurEtabROVOIterator");
            Row currentuser = iteruser.getCurrentRow();

            DCIteratorBinding iterFilUE = (DCIteratorBinding) bindings.get("FiliereUeSemestresIterator");
            Row currentFilUE = iterFilUE.getCurrentRow();
            System.out.println("FilUeIter : " + currentFilUE.getAttribute("IdFiliereUeSemstre") + " Fil_ueSess " +
                               fil_ue_id + " pma " + pma_id);
            if (currentuser != null) {
                Long user_id = Long.parseLong(currentuser.getAttribute("IdUtilisateur").toString());
                try {
                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                    oprole.getParamsMap().put("role_id", new Long(RESP_UE));
                    role = (String) oprole.execute();
                    //Utilisateur_filiere_ue_id dont est l'ancien responsable
                    if ((0 != old_user_fil_ue)) {
                        //System.out.println("Changing old responsable...");
                        try {
                            //recuperer l'id user
                            //System.out.println("Getting old user responsabel of old_user_fil_ue : " + old_user_fil_ue);
                            OperationBinding opolduserResp = bindings.getOperationBinding("getUserResponsableFilUe");
                            opolduserResp.getParamsMap().put("u_fil_ue_id", old_user_fil_ue);
                            Long old_resp = (Long) opolduserResp.execute(); //l'id_user qui etait responsable
                            if ((0 != old_resp) &&
                                (user_id !=
                                 old_resp)) {
                                //l'ancien responsable existe et different du nouveau
                                try {
                                    //updater responsable to simple
                                    OperationBinding opoldResp =
          bindings.getOperationBinding("createOrUpdateUserFilUe1");
                                    opoldResp.getParamsMap().put("user_", old_resp);
                                    opoldResp.getParamsMap().put("role", "ACCES SIMPLE");
                                    opoldResp.getParamsMap().put("fil_ue_", fil_ue_id);
                                    opoldResp.getParamsMap().put("pma_", pma_id);
                                    opoldResp.getParamsMap()
                                        .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    opoldResp.execute();
                                    //System.out.println("Old responsable changed to simple !");
                                    //Verifier si l'user est tjrs responsable d'une filiere ue
                                    OperationBinding opisuserresp =
                                        bindings.getOperationBinding("isUserResponsableFilUe");
                                    opisuserresp.getParamsMap().put("user_id", old_resp);
                                    opisuserresp.execute();
                                    DCIteratorBinding iterResp =
                                        (DCIteratorBinding) bindings.get("isUserResponsableFilUeIterator");
                                    if (0 == iterResp.getEstimatedRowCount()) {
                                        try {
                                            //delete role responsable filiere ue to user
                                            /*System.out.println("User " + old_resp +
                                                               " has no responsable role now : deleting role");*/
                                            OperationBinding opdeluserRole =
                                                bindings.getOperationBinding("DeleteUserRole");
                                            opdeluserRole.getParamsMap().put("user_id", old_resp);
                                            opdeluserRole.getParamsMap().put("role_id", new Long(RESP_UE));
                                            opdeluserRole.execute();
                                            //System.out.println("role deleted for user " + old_resp);
                                            //revoke weblogic role responsable formation to user
                                            //System.out.println("revoking role to user in weblogic...");
                                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                                            opusername.getParamsMap().put("user_id", old_resp);
                                            String username = (String) opusername.execute();
                                            if (null != role && null != username) {
                                                //System.out.println("Bd role : "+role);
                                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                //System.out.println("weblogic role : "+role);
                                                try {
                                                    AdministrationBean.revokeWlsRole2User(role, username);
                                                    //System.out.println("Role revoked success to user : "+username);
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            } else {
                                                System.out.println("username : " + username + " role : " + role);
                                            }
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("User is responsable of other formation ! role stay");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                            } else {
                                System.out.println("Responsable is the same !");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Pas d'ancien responsable");
                    }
                    //Parametrage du nouveau responsable de la filiere avec le nouveau utilisateur
                    try {
                        //Ajouter à la table utilisateur utilis_filiere_ue ou modifier une ligne pour l'utilisateur
                        OperationBinding opResp = bindings.getOperationBinding("createOrUpdateUserFilUe");
                        opResp.getParamsMap().put("user_", user_id);
                        opResp.getParamsMap().put("role", "RESPONSABLE UE");
                        opResp.getParamsMap().put("fil_ue_", fil_ue_id);
                        opResp.getParamsMap().put("pma_", pma_id);
                        opResp.getParamsMap().put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                        Long new_resp = (Long) opResp.execute();
                        if (0 != new_resp) {
                            /*try {
                                OperationBinding op_fr_niv = bindings.getOperationBinding("getFormNivForm");

                                op_fr_niv.getParamsMap().put("pma_id",pma_id);
                                op_fr_niv.getParamsMap().put("fue_id", fil_ue_id);
                                op_fr_niv.getParamsMap().put("an_id", getAnne_univers());
                                op_fr_niv.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            DCIteratorBinding fr_niv_iter = (DCIteratorBinding) bindings.get("FormNivFormIterator");
                            Row crnt = fr_niv_iter.getCurrentRow();*/
                            try {
                                //Parametrer les acces supérieurs
                                OperationBinding op_niv_fr = bindings.getOperationBinding("createOrUpdateUserNivForm");
                                op_niv_fr.getParamsMap()
                                    .put("niv_form_", currentFilUE.getAttribute("IdNiveauFormation"));
                                op_niv_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                op_niv_fr.getParamsMap().put("user_", user_id);
                                op_niv_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                Long niv_fr_id = (Long) op_niv_fr.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            try {
                                OperationBinding op_fr = bindings.getOperationBinding("createOrUpdateUserForm");
                                op_fr.getParamsMap().put("form_", currentFilUE.getAttribute("IdFormation"));
                                op_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                op_fr.getParamsMap().put("user_", user_id);
                                op_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                Long fr_id = (Long) op_fr.execute();
                                
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //Creer le role responsable ue pour cet utilisateur
                            OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                            allowuserRle.getParamsMap().put("user_id", user_id);
                            allowuserRle.getParamsMap().put("role_id", new Long(RESP_UE)); // Responsable Fil_ue
                            allowuserRle.getParamsMap()
                                .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                            allowuserRle.execute();
                            System.out.println("Role responsable assigned in bd !");
                            //assign weblogic role
                            try {
                                OperationBinding opusername = bindings.getOperationBinding("getusername");
                                opusername.getParamsMap().put("user_id", new Long(user_id));
                                String username = (String) opusername.execute();
                                OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                opmat.getParamsMap().put("utilisateur", new Long(user_id));
                                String matricule = (String) opmat.execute();
                                //matricule = "passer123*";
                                if (null != role && null != username && null != matricule) {
                                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                    try {
                                        AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                        AdministrationBean.assignWlsRole2User(role, username);
                                        System.out.println("Role " + role + " assign to " + username + " in weblogic");
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                } else {
                                    System.out.println("username : " + username + " role : " + role);
                                }
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
                sessionScope.remove("fil_ue_");
                sessionScope.remove("fil_ue");
                sessionScope.remove("pma_");
                sessionScope.remove("resp_fil_ue");
                this.getPopupConfResp().hide();
                DCIteratorBinding iterBind = (DCIteratorBinding) bindings.get("FiliereUeSemestresIterator");
                ViewObject vo = iterBind.getViewObject();
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPangrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilUePanCol());
            }
        }
    }

    public void setFilUePanGrp(RichPanelGroupLayout filUePanGrp) {
        this.filUePanGrp = filUePanGrp;
    }

    public RichPanelGroupLayout getFilUePanGrp() {
        return filUePanGrp;
    }

    public void setFilUePanCol(RichPanelCollection filUePanCol) {
        this.filUePanCol = filUePanCol;
    }

    public RichPanelCollection getFilUePanCol() {
        return filUePanCol;
    }

    @SuppressWarnings("unchecked")
    public void 
    OnEditResponsableEcClicked(ActionEvent actionEvent) {
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding fil_ue_iter = (DCIteratorBinding) bindings.get("FiliereUeSemestreEcsIterator");
        Row curent  = fil_ue_iter.getCurrentRow();
        //sessionScope.put("is_fr_param", true);
        Long fil_ec_id = Long.parseLong(curent.getAttribute("IdFiliereUeSemstreEc").toString());
        Long pma_id = Long.parseLong(curent.getAttribute("IdParcoursMaquetAnnee").toString());
        //responsable to update utils_filiere_ue_semestre table
        OperationBinding opresp = bindings.getOperationBinding("getResponsableFilEc");
        opresp.getParamsMap().put("fil_ec_id", fil_ec_id);
        opresp.getParamsMap().put("pma", pma_id);
        Long uti_resp_fil_ec_id = (Long) opresp.execute();
        //FiliereUe to change responsable
        sessionScope.put("fil_ec_", curent.getAttribute("LibelleLong"));
        sessionScope.put("fil_ec", fil_ec_id);
        sessionScope.put("pma_", pma_id);
        sessionScope.put("resp_fil_ec", uti_resp_fil_ec_id);
        RichPopup pop = this.getPopupConfRespFilEc();
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        pop.show(ph);
    }

    public void setPopupConfRespFilEc(RichPopup popupConfRespFilEc) {
        this.popupConfRespFilEc = popupConfRespFilEc;
    }

    public RichPopup getPopupConfRespFilEc() {
        return popupConfRespFilEc;
    }

    public void setPangrp(RichPanelGroupLayout pangrp) {
        this.pangrp = pangrp;
    }

    public RichPanelGroupLayout getPangrp() {
        return pangrp;
    }

    @SuppressWarnings("unchecked")
    public void OnResponsableEcConfirmed(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = this.getBindings();
            Long fil_ec_id = Long.parseLong(sessionScope.get("fil_ec").toString());
            Long pma_id = Long.parseLong(sessionScope.get("pma_").toString());
            Long old_user_fil_ec = Long.parseLong(sessionScope.get("resp_fil_ec").toString());
            String role = null;
            DCIteratorBinding iteruser = (DCIteratorBinding) bindings.get("UtilisateursROVOIterator");
            Row currentuser = iteruser.getCurrentRow();
            /*System.out.println("Update Responsable... : New Responsable : " +
                               currentuser.getAttribute("IdUtilisateur") + " Fil_ec " + fil_ec_id+" pma "+pma_id);*/
            if (currentuser != null) {
                Long user_id = Long.parseLong(currentuser.getAttribute("IdUtilisateur").toString());
                try {
                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                    oprole.getParamsMap().put("role_id", new Long(RESP_EC));
                    role = (String) oprole.execute();
                    //Utilisateur_filiere_ec_id dont est l'ancien responsable
                    if ((0 != old_user_fil_ec)) {
                        //System.out.println("Changing old responsable...");
                        try {
                            //recuperer l'id user
                            //System.out.println("Getting old user responsabel of old_user_fil_ue : " + old_user_fil_ec);
                            OperationBinding opolduserResp = bindings.getOperationBinding("getUserResponsableFilEc");
                            opolduserResp.getParamsMap().put("u_fil_ec_id", old_user_fil_ec);
                            Long old_resp = (Long) opolduserResp.execute(); //l'id_user qui etait responsable
                            if ((0 != old_resp) && (user_id != old_resp)) {
                                //l'ancien responsable existe et different du nouveau
                                try {
                                    //updater responsable to simple
                                    OperationBinding opoldResp =  bindings.getOperationBinding("createOrUpdateUserFilEc1");
                                    opoldResp.getParamsMap().put("user_", old_resp);
                                    opoldResp.getParamsMap().put("role", "ACCES SIMPLE");
                                    opoldResp.getParamsMap().put("fil_ec_", fil_ec_id);
                                    opoldResp.getParamsMap().put("pma_", pma_id);
                                    opoldResp.getParamsMap().put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                    opoldResp.execute();
                                    //System.out.println("Old responsable changed to simple !");
                                    //Verifier si l'user est tjrs responsable d'une filiere ec
                                    OperationBinding opisuserresp =
                                        bindings.getOperationBinding("isUserResponsableFilEc");
                                    opisuserresp.getParamsMap().put("user_id", old_resp);
                                    opisuserresp.execute();
                                    DCIteratorBinding iterResp =
                                        (DCIteratorBinding) bindings.get("isUserResponsableFilEcIterator");
                                    if (0 == iterResp.getEstimatedRowCount()) {
                                        try {
                                            //delete role responsable filiere ue to user
                                            /*System.out.println("User " + old_resp +
                                                               " has no responsable role now : deleting role");*/
                                            OperationBinding opdeluserRole =
                                                bindings.getOperationBinding("DeleteUserRole");
                                            opdeluserRole.getParamsMap().put("user_id", old_resp);
                                            opdeluserRole.getParamsMap().put("role_id", new Long(RESP_EC));
                                            opdeluserRole.execute();
                                            //System.out.println("role <<"+role+">> deleted for user " + old_resp);
                                            //revoke weblogic role responsable formation to user
                                            //System.out.println("revoking role to user in weblogic...");
                                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                                            opusername.getParamsMap().put("user_id", old_resp);
                                            String username = (String) opusername.execute();
                                            if (null != role && null != username) {
                                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                                try {
                                                    AdministrationBean.revokeWlsRole2User(role, username);
                                                    //System.out.println("Role <<"+role+">> revoked success to <<"+username+">>");
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            } else {
                                                System.out.println("username : " + username + " role : " + role);
                                            }
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } 
                                    else {
                                        System.out.println("User is responsable of other Ec ! role stay");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                            } 
                            else {
                                System.out.println("Responsable is the same !");
                            }
                        } 
                        catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    else {
                        System.out.println("Pas d'ancien responsable");
                    }
                    //Parametrage du nouveau responsable de la filiere avec le nouveau utilisateur
                    try {
                        //Ajouter à la table utilisateur utilis_filiere_ue ou modifier une ligne pour l'utilisateur
                        OperationBinding opResp = bindings.getOperationBinding("createOrUpdateUserFilEc");
                        opResp.getParamsMap().put("user_", user_id);
                        opResp.getParamsMap().put("role", "RESPNSABLE EC");
                        opResp.getParamsMap().put("fil_ec_", fil_ec_id);
                        opResp.getParamsMap().put("pma_", pma_id);
                        opResp.getParamsMap().put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                        Long new_resp = (Long) opResp.execute();
                        if (0 != new_resp) {
                            try {
                                /*OperationBinding op_fr_niv_filUe = bindings.getOperationBinding("getFormNivFormFilUe");
                                op_fr_niv_filUe.getParamsMap().put("pma_id", pma_id);
                                op_fr_niv_filUe.getParamsMap().put("fec_id", fil_ec_id);
                                op_fr_niv_filUe.getParamsMap().put("an_id", getAnne_univers());
                                op_fr_niv_filUe.execute();*/
                                DCIteratorBinding filUe_iter = (DCIteratorBinding) bindings.get("FiliereUeSemestresIterator");
                                Row crnt = filUe_iter.getCurrentRow();
                                if (null != crnt) {
                                    try {
                                        OperationBinding op_fue =
                                            bindings.getOperationBinding("createOrUpdateUserFilUe");
                                        op_fue.getParamsMap().put("pma_", pma_id);
                                        op_fue.getParamsMap().put("fil_ue_", crnt.getAttribute("IdFiliereUeSemstre"));
                                        op_fue.getParamsMap().put("role", "ACCES SIMPLE");
                                        op_fue.getParamsMap().put("user_", user_id);
                                        op_fue.getParamsMap().put("utilisateur", getUtilisateur());
                                        Long fil_ue_id = (Long) op_fue.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding op_niv_fr =
                                            bindings.getOperationBinding("createOrUpdateUserNivForm");
                                        op_niv_fr.getParamsMap().put("niv_form_",crnt.getAttribute("IdNiveauFormation"));
                                        op_niv_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                        op_niv_fr.getParamsMap().put("user_", user_id);
                                        op_niv_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                        Long niv_fr_id = (Long) op_niv_fr.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    try {
                                        OperationBinding op_fr = bindings.getOperationBinding("createOrUpdateUserForm");
                                        op_fr.getParamsMap().put("form_", crnt.getAttribute("IdFormation"));
                                        op_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                        op_fr.getParamsMap().put("user_", user_id);
                                        op_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                        Long fr_id = (Long) op_fr.execute();
                                        //System.out.println("User fr : "+fr_id);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                                
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            //System.out.println("Responsable fil_ec " + fil_ec_id + " changed");
                            //Creer le role responsable formation pour cet utilisateur
                            OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                            allowuserRle.getParamsMap().put("user_id", user_id);
                            allowuserRle.getParamsMap().put("role_id", new Long(RESP_EC)); // Responsable Fil_ec
                            allowuserRle.getParamsMap()
                                .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                            Integer user_role = (Integer)allowuserRle.execute();
                            if(0 != user_role){
                                //System.out.println("Role responsable assigned in bd !");
                                //assign weblogic role
                                try {
                                    OperationBinding opusername = bindings.getOperationBinding("getusername");
                                    opusername.getParamsMap().put("user_id", new Long(user_id));
                                    String username = (String) opusername.execute();
                                    OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                    opmat.getParamsMap().put("utilisateur", new Long(user_id));
                                    String matricule = (String) opmat.execute();
                                    //matricule = "passer123*";
                                    if (null != role && null != username && null != matricule) {
                                        role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                        try {
                                            AdministrationBean.createWlsUser(username, matricule.toCharArray());
                                            AdministrationBean.assignWlsRole2User(role, username);
                                            System.out.println("Role <<" + role + ">> assigned to <<" + username +
                                                               ">> in weblogic");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("username : " + username + " role : " + role);
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    } 
                    catch (Exception e) {
                        System.out.println(e);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                sessionScope.remove("fil_ec_");
                sessionScope.remove("fil_ec");
                sessionScope.remove("pma_");
                sessionScope.remove("resp_fil_ec");
                this.getPopupConfRespFilEc().hide();
                DCIteratorBinding iterBind= (DCIteratorBinding)bindings.get("FiliereUeSemestreEcsIterator");
                ViewObject vo=iterBind.getViewObject();  
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPangrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFilEcPanCol());
            }
        }
    }

    public void onResponsableUeCancel(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
    }

    public void OnResponsableEcCancel(PopupCanceledEvent popupCanceledEvent) {
        sessionScope.remove("fil_ec_");
        sessionScope.remove("fil_ec");
        sessionScope.remove("pma_");
        sessionScope.remove("resp_fil_ec");
    }

    public void OnresponsableUeCancel(PopupCanceledEvent popupCanceledEvent) {
        sessionScope.remove("fil_ue_");
        sessionScope.remove("fil_ue");
        sessionScope.remove("pma_");
        sessionScope.remove("resp_fil_ue");
    }

    public void setFilEcPanCol(RichPanelCollection filEcPanCol) {
        this.filEcPanCol = filEcPanCol;
    }

    public RichPanelCollection getFilEcPanCol() {
        return filEcPanCol;
    }

    public void setFilEcPanGrp(RichPanelGroupLayout filEcPanGrp) {
        this.filEcPanGrp = filEcPanGrp;
    }

    public RichPanelGroupLayout getFilEcPanGrp() {
        return filEcPanGrp;
    }

    public void setIs_grp(RichSelectBooleanCheckbox is_grp) {
        this.is_grp = is_grp;
    }

    public RichSelectBooleanCheckbox getIs_grp() {
        return is_grp;
    }

    public void OnGroupedValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Ue grouped : "+valueChangeEvent.getNewValue().toString());
        this.getColcheck().setVisible((Boolean)valueChangeEvent.getNewValue());
    }

    public void setColcheck(RichColumn colcheck) {
        this.colcheck = colcheck;
    }

    public RichColumn getColcheck() {
        return colcheck;
    }

    public void AttchengedAction(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
        System.out.println("Att ue "+attributeChangeEvent.getNewValue().toString());
    }

    public void OnGroupedEcAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Ec grouped : "+valueChangeEvent.getNewValue().toString());
        this.getColEcCheck().setVisible((Boolean)valueChangeEvent.getNewValue());
    }

    public void AttEcChangedAction(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
        System.out.println("Att ec "+attributeChangeEvent.getNewValue().toString());
    }

    public void setColEcCheck(RichColumn colEcCheck) {
        this.colEcCheck = colEcCheck;
    }

    public RichColumn getColEcCheck() {
        return colEcCheck;
    }

    public void setIsec_grp(RichSelectBooleanCheckbox isec_grp) {
        this.isec_grp = isec_grp;
    }

    public RichSelectBooleanCheckbox getIsec_grp() {
        return isec_grp;
    }

    public void OnEditResponsableUeClicked(ActionEvent actionEvent) {
        // Add event code here...
    }
}
