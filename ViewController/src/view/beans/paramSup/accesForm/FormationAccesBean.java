package view.beans.paramSup.accesForm;

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
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import view.beans.admin.AdministrationBean;

public class FormationAccesBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer id_hs = Integer.parseInt(sessionScope.get("id_hs").toString());
    private RichSelectOneRadio roleSelected;
    private RichTable formationTable;
    private RichPopup notFormSelected;
    private RichTable utiAccesFormTable;
    private RichPopup popupDeleteAccess;
    private RichSelectOneChoice selectedRole;
    private RichPopup formRespExist;

    public FormationAccesBean() {

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterUser = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("UtilisateursROVO1Iterator");
        Row currentuser = iterUser.getCurrentRow();
        Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
        System.out.println("User id : " + currentuser.getAttribute("IdUtilisateur").toString());
        System.out.println("Role : " + this.getRoleSelected()
                                           .getValue()
                                           .toString());
        try {
            OperationBinding formationSelected = bindings.getOperationBinding("getSelectedFormations");
            @SuppressWarnings("unchecked")
            ArrayList<Long> formations = (ArrayList<Long>) formationSelected.execute();
            System.out.println("formation selected count : " + formations.size());
            if (formations.size() != 0) {
                // Pour chaque utilisateur lui accorder les accees choisies
                for (int forcounter = 0; forcounter < formations.size(); forcounter++) {
                    //Verifié si l'acces est déja autorisé
                    OperationBinding isAllowAcces = bindings.getOperationBinding("isFormationAllowedAccess");
                    isAllowAcces.getParamsMap().put("uti", IdUser);
                    isAllowAcces.getParamsMap().put("id_fr", formations.get(forcounter));
                    //isAllowAcces.getParamsMap().put("annee", getAnne_univers());
                    Object resul = isAllowAcces.execute();
                    DCIteratorBinding iterformAccess =
                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                         .getCurrentBindingsEntry()
                                                                                         .get("IsFormationAllowedAccessIterator");
                    Row crnt = iterformAccess.getCurrentRow();
                    if (crnt == null) {
                        System.out.println("L'utilisateur n'a pas encore acces a cette formation");
                        // L'utilisateur n'a pas encore acces a cette formation
                        // Verifier le type d'acces
                        if (this.getRoleSelected()
                                .getValue()
                                .toString()
                                .equals("RESPONSABLE FORMATION")) {
                            // Verifier si la formation n'a pas de responsable déja
                            OperationBinding is_resp_form = bindings.getOperationBinding("isResponsableFormExist");
                            //is_resp_form.getParamsMap().put("s_id", getId_hs());
                            is_resp_form.getParamsMap().put("id_fr", formations.get(forcounter));
                            is_resp_form.getParamsMap().put("annee", getAnne_univers());
                            is_resp_form.execute();
                            DCIteratorBinding iterIsResponsable =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                    .getCurrentBindingsEntry()
                                                                                                    .get("isRespFormationExistIterator");
                            Row is_exist_resp = iterIsResponsable.getCurrentRow();
                            if (is_exist_resp == null) {
                                System.out.println("la formation n'a pas de responsable");
                                OperationBinding opAllowAcces = bindings.getOperationBinding("AllowAccessTo");

                                opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                                opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                opAllowAcces.getParamsMap().put("IdFormation", formations.get(forcounter));
                                opAllowAcces.getParamsMap().put("Role", this.getRoleSelected()
                                                                            .getValue()
                                                                            .toString());
                                resul = opAllowAcces.execute();
                                System.out.println("Saved !!!");

                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                allowuserRle.getParamsMap().put("user_id", IdUser);
                                allowuserRle.getParamsMap().put("role_id", new Long(61)); // Responsable formation
                                allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                allowuserRle.execute();
                                //assign weblogic role
                                try {
                                    System.out.println("Assigning role to user in weblogic...");
                                    OperationBinding opusername = bindings.getOperationBinding("getusername");
                                    opusername.getParamsMap().put("user_id", new Long(IdUser));
                                    String username = (String) opusername.execute();
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", new Long(61));
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
                                            System.out.println("Role assigned !");
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
                                // Cette formation a deja un responsble
                                System.out.println("Responsable existant pour cette formation");
                            }
                        } else {
                            // Role acces simple
                            System.out.println("Saving ...");
                            OperationBinding opAllowAcces = bindings.getOperationBinding("AllowAccessTo");
                            opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                            opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                            opAllowAcces.getParamsMap().put("IdFormation", formations.get(forcounter));
                            opAllowAcces.getParamsMap().put("Role", this.getRoleSelected()
                                                                        .getValue()
                                                                        .toString());
                            resul = opAllowAcces.execute();
                        }
                    } else {
                        // if new role = RESPNSABLE formation et old role = ACCES SIMPLE
                        if (!(crnt.getAttribute("Role")
                                  .toString()
                                  .equals("RESPONSABLE FORMATION")) && (this.getRoleSelected()
                                                                            .getValue()
                                                                            .toString()
                                                                            .equals("RESPONSABLE FORMATION"))) {
                            // Update possible
                            // Verifier si la formation a un responsable
                            OperationBinding is_resp_form = bindings.getOperationBinding("isResponsableFormExist");
                            //is_resp_form.getParamsMap().put("s_id", getId_hs());
                            is_resp_form.getParamsMap().put("id_fr", formations.get(forcounter));
                            is_resp_form.getParamsMap().put("annee", getAnne_univers());
                            is_resp_form.execute();
                            DCIteratorBinding iterExist =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                            .getCurrentBindingsEntry()
                                                                                            .get("isRespFormationExistIterator");
                            Row is_exist_resp = iterExist.getCurrentRow();
                            if (is_exist_resp == null) {
                                System.out.println("Update role required from ACCES SIMPLE to RESPNSABLE Formation !!!");
                                OperationBinding updateRole =
                                    bindings.getOperationBinding("UpdateResponsableFormation");
                                updateRole.getParamsMap().put("idUser", IdUser);
                                updateRole.getParamsMap().put("idFormation", formations.get(forcounter));
                                updateRole.getParamsMap().put("role", this.getRoleSelected()
                                                                          .getValue()
                                                                          .toString());
                                updateRole.getParamsMap().put("utimodif", getUtilisateur());
                                updateRole.getParamsMap()
                                    .put("id_uti_formation",
                                         Integer.parseInt(crnt.getAttribute("IdUtilisateurFormation").toString()));
                                resul = updateRole.execute();

                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                allowuserRle.getParamsMap().put("user_id", IdUser);
                                allowuserRle.getParamsMap().put("role_id", new Long(61)); // Responsable formation
                                allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                allowuserRle.execute();

                            } else {
                                System.out.println("Responsable deja defini pour cet ue !!!");
                            }
                        } else {
                            System.out.println("Acces déja autorisé !!!");
                        }
                    }
                }
                // Tout ce passe bien On commit
                System.out.println("Comiting ...");
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();

                OperationBinding accesform = bindings.getOperationBinding("GetUtiAccesFormation");
                accesform.getParamsMap().put("uti", IdUser);
                //accesform.getParamsMap().put("id_hs", getId_hs());
                //accesform.getParamsMap().put("annee", getAnne_univers());
                accesform.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());

                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationTable());
            } else {
                System.out.println("Pas de formation selectionner");
                RichPopup popup = this.getNotFormSelected();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setRoleSelected(RichSelectOneRadio roleSelected) {
        this.roleSelected = roleSelected;
    }

    public RichSelectOneRadio getRoleSelected() {
        return roleSelected;
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

    public void setFormationTable(RichTable formationTable) {
        this.formationTable = formationTable;
    }

    public RichTable getFormationTable() {
        return formationTable;
    }

    public void setNotFormSelected(RichPopup notFormSelected) {
        this.notFormSelected = notFormSelected;
    }

    public RichPopup getNotFormSelected() {
        return notFormSelected;
    }

    @SuppressWarnings("unchecked")
    public void onUserChange(ValueChangeEvent valueChangeEvent) {
        // GetUtiAccesFormation

        BindingContainer bindings = getBindings();
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
        Integer IdUser = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        System.out.println("Id User " + IdUser);
        OperationBinding accesform = bindings.getOperationBinding("GetUtiAccesFormation");
        accesform.getParamsMap().put("uti", IdUser);
        //accesform.getParamsMap().put("id_hs", getId_hs());
        //accesform.getParamsMap().put("annee", getAnne_univers());
        accesform.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());

    }

    public void setUtiAccesFormTable(RichTable utiAccesFormTable) {
        this.utiAccesFormTable = utiAccesFormTable;
    }

    public RichTable getUtiAccesFormTable() {
        return utiAccesFormTable;
    }

    public void setId_hs(Integer id_hs) {
        this.id_hs = id_hs;
    }

    public Integer getId_hs() {
        return id_hs;
    }

    public void setPopupDeleteAccess(RichPopup popupDeleteAccess) {
        this.popupDeleteAccess = popupDeleteAccess;
    }

    public RichPopup getPopupDeleteAccess() {
        return popupDeleteAccess;
    }

    @SuppressWarnings("unchecked")
    public void OnEditAccessFormationAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            AttributeBinding acesform_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateurFormation");
            AttributeBinding form_id = (AttributeBinding) getBindings().getControlBinding("IdFormation");
            AttributeBinding uti_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateur");
            if (this.getRoleSelected()
                    .getValue()
                    .toString()
                    .equals("RESPONSABLE FORMATION")) {
                // Verifier si la formation n'a pas de responsable déja
                OperationBinding is_resp_form = bindings.getOperationBinding("isResponsableFormExist");
                is_resp_form.getParamsMap().put("id_fr", Integer.parseInt(form_id.toString()));
                is_resp_form.getParamsMap().put("annee", getAnne_univers());
                is_resp_form.execute();
                DCIteratorBinding iterIsResponsable =
                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                                        .getCurrentBindingsEntry()
                                                                                        .get("isRespFormationExistIterator");
                Row is_exist_resp = iterIsResponsable.getCurrentRow();
                if (is_exist_resp == null) {
                    //Update role to RESPONSABLE FORMATION
                    OperationBinding updateaccesform = bindings.getOperationBinding("UpdateFormationAccess");
                    updateaccesform.getParamsMap().put("id_acces", Integer.parseInt(acesform_id.toString()));
                    updateaccesform.getParamsMap().put("role", this.getSelectedRole()
                                                                   .getValue()
                                                                   .toString());
                    updateaccesform.getParamsMap().put("utimodif", getUtilisateur());
                    updateaccesform.execute();
                    //Allouer le role RESPONSABLE FORMATION a l'utilisateur
                    //-id_role
                    OperationBinding oprole_id = bindings.getOperationBinding("GetRole");
                    oprole_id.getParamsMap().put("role", this.getSelectedRole()
                                                             .getValue()
                                                             .toString());
                    oprole_id.execute();
                    DCIteratorBinding iterRole = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .get("GetRoleROVOIterator");
                    Row current_role = iterRole.getCurrentRow();
                    if (current_role != null) {
                        //--verifier si l'utilisateur n'a pas encore ce role
                        OperationBinding is_user_role_exist = bindings.getOperationBinding("IsUserRoleExist");
                        is_user_role_exist.getParamsMap()
                            .put("role_id", Integer.parseInt(current_role.getAttribute("IdRole").toString()));
                        is_user_role_exist.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                        is_user_role_exist.execute();
                        DCIteratorBinding iterRoleUser =
                            (DCIteratorBinding) BindingContext.getCurrent()
                                                                                           .getCurrentBindingsEntry()
                                                                                           .get("IsUserRoleExistROVOIterator");
                        Row current_role_user = iterRoleUser.getCurrentRow();
                        if (current_role_user == null) {
                            //Donner le role
                            OperationBinding allowRole = bindings.getOperationBinding("AllowRoleTo");
                            allowRole.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                            allowRole.getParamsMap()
                                .put("role_id", Integer.parseInt(current_role.getAttribute("IdRole").toString()));
                            allowRole.getParamsMap().put("uticre", getUtilisateur());
                            allowRole.execute();
                        }
                    }
                } else {
                    System.out.println("Responsable deja defini pour cette formation !!!");
                    RichPopup popup = this.getFormRespExist();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                }
            } else {
                OperationBinding updateaccesform = bindings.getOperationBinding("UpdateFormationAccess");
                updateaccesform.getParamsMap().put("id_acces", Integer.parseInt(acesform_id.toString()));
                updateaccesform.getParamsMap().put("role", this.getSelectedRole()
                                                               .getValue()
                                                               .toString());
                updateaccesform.getParamsMap().put("utimodif", getUtilisateur());
                updateaccesform.execute();
            }
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object result = operationCommit.execute();
            OperationBinding accesform = bindings.getOperationBinding("GetUtiAccesFormation");
            accesform.getParamsMap().put("uti", Integer.parseInt(uti_id.toString()));
            //accesform.getParamsMap().put("annee", getAnne_univers());
            accesform.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());
        }
    }

    public void setSelectedRole(RichSelectOneChoice selectedRole) {
        this.selectedRole = selectedRole;
    }

    public RichSelectOneChoice getSelectedRole() {
        return selectedRole;
    }

    public void setFormRespExist(RichPopup formRespExist) {
        this.formRespExist = formRespExist;
    }

    public RichPopup getFormRespExist() {
        return formRespExist;
    }

    @SuppressWarnings("unchecked")
    public void OnConfirmUpdateResponsableAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            AttributeBinding acesform_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateurFormation");
            AttributeBinding form_id = (AttributeBinding) getBindings().getControlBinding("IdFormation");
            AttributeBinding uti_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateur");
            OperationBinding is_resp_form = bindings.getOperationBinding("isResponsableFormExist");
            is_resp_form.getParamsMap().put("id_fr", Integer.parseInt(form_id.toString()));
            is_resp_form.getParamsMap().put("annee", getAnne_univers());
            is_resp_form.execute();
            DCIteratorBinding iterIsResponsable = (DCIteratorBinding) bindings.get("isRespFormationExistIterator");
            Row is_exist_resp = iterIsResponsable.getCurrentRow();
            /* System.out.println("New responsable : " + uti_id.toString());
            System.out.println("Old responsable : " + is_exist_resp.getAttribute("IdUtilisateur").toString()); */
            if (Integer.parseInt(is_exist_resp.getAttribute("IdUtilisateur").toString()) !=
                Integer.parseInt(uti_id.toString())) {
                //Update old to Acces Simple
                OperationBinding updateaccesform = bindings.getOperationBinding("UpdateFormationAccess");
                updateaccesform.getParamsMap()
                    .put("id_acces", Integer.parseInt(is_exist_resp.getAttribute("IdUtilisateurFormation").toString()));
                updateaccesform.getParamsMap().put("role", "ACCES SIMPLE");
                updateaccesform.getParamsMap().put("utimodif", getUtilisateur());
                updateaccesform.execute();
                //Update New to Responsable Formation
                OperationBinding updatenewaccesform = bindings.getOperationBinding("UpdateFormationAccess");
                updatenewaccesform.getParamsMap().put("id_acces", Integer.parseInt(acesform_id.toString()));
                updatenewaccesform.getParamsMap().put("role", this.getSelectedRole()
                                                                  .getValue()
                                                                  .toString());
                updatenewaccesform.getParamsMap().put("utimodif", getUtilisateur());
                updatenewaccesform.execute();

                OperationBinding oprole_id = bindings.getOperationBinding("GetRole");
                oprole_id.getParamsMap().put("role", this.getSelectedRole()
                                                         .getValue()
                                                         .toString());
                oprole_id.execute();
                DCIteratorBinding iterRole = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("GetRoleROVOIterator");
                Row current_role = iterRole.getCurrentRow();
                if (current_role != null) {
                    //--verifier si l'utilisateur n'a pas encore ce role
                    OperationBinding is_user_old_role_exist = bindings.getOperationBinding("IsUserRoleExist");
                    is_user_old_role_exist.getParamsMap()
                        .put("role_id", Integer.parseInt(current_role.getAttribute("IdRole").toString()));
                    is_user_old_role_exist.getParamsMap()
                        .put("uti_id", Integer.parseInt(is_exist_resp.getAttribute("IdUtilisateur").toString()));
                    is_user_old_role_exist.execute();
                    DCIteratorBinding iterRoleoldUser =
                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                          .getCurrentBindingsEntry()
                                                                                          .get("IsUserRoleExistROVOIterator");
                    Row current_old_role_user = iterRoleoldUser.getCurrentRow();
                    if (current_old_role_user != null) {
                        //Update role to new responsable
                        OperationBinding allowRole = bindings.getOperationBinding("UpdateUserRole");
                        allowRole.getParamsMap()
                            .put("user_role_id",
                                 Integer.parseInt(current_old_role_user.getAttribute("IdUtilisateurRole").toString()));
                        allowRole.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                        allowRole.getParamsMap().put("uti_modif", getUtilisateur());
                        allowRole.execute();
                    } else {
                        OperationBinding allowRole = bindings.getOperationBinding("AllowRoleTo");
                        allowRole.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                        allowRole.getParamsMap()
                            .put("role_id", Integer.parseInt(current_old_role_user.getAttribute("IdRole").toString()));
                        allowRole.getParamsMap().put("uticre", getUtilisateur());
                        allowRole.execute();
                    }
                    OperationBinding is_user_new_role_exist = bindings.getOperationBinding("IsUserRoleExist");
                    is_user_new_role_exist.getParamsMap()
                        .put("role_id", Integer.parseInt(current_role.getAttribute("IdRole").toString()));
                    is_user_new_role_exist.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                    is_user_new_role_exist.execute();
                    DCIteratorBinding iternewRoleUser =
                        (DCIteratorBinding) BindingContext.getCurrent()
                                                                                          .getCurrentBindingsEntry()
                                                                                          .get("IsUserRoleExistROVOIterator");
                    Row current_new_role_user = iternewRoleUser.getCurrentRow();
                    if (current_new_role_user == null) {
                        //Donner le role
                        OperationBinding allowRole = bindings.getOperationBinding("AllowRoleTo");
                        allowRole.getParamsMap().put("uti_id", Integer.parseInt(uti_id.toString()));
                        allowRole.getParamsMap()
                            .put("role_id", Integer.parseInt(current_role.getAttribute("IdRole").toString()));
                        allowRole.getParamsMap().put("uticre", getUtilisateur());
                        allowRole.execute();
                    }
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    operationCommit.execute();
                }
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();
                OperationBinding accesform = bindings.getOperationBinding("GetUtiAccesFormation");
                accesform.getParamsMap().put("uti", Integer.parseInt(uti_id.toString()));
                //accesform.getParamsMap().put("annee", getAnne_univers());
                accesform.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void OnDeleteConfirmAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            AttributeBinding acesform_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateurFormation");
            AttributeBinding uti_id = (AttributeBinding) getBindings().getControlBinding("IdUtilisateur");
            OperationBinding updatenewaccesform = getBindings().getOperationBinding("DeleteFormationAccess");
            updatenewaccesform.getParamsMap().put("id_acces", Integer.parseInt(acesform_id.toString()));
            updatenewaccesform.execute();
            OperationBinding operationCommit = getBindings().getOperationBinding("Commit");
            Object result = operationCommit.execute();
            OperationBinding accesform = getBindings().getOperationBinding("GetUtiAccesFormation");
            accesform.getParamsMap().put("uti", Integer.parseInt(uti_id.toString()));
            //accesform.getParamsMap().put("annee", getAnne_univers());
            accesform.execute();

            //Delete roleResponsable
            OperationBinding deleteuserRle = getBindings().getOperationBinding("DeleteUserRole");
            deleteuserRle.getParamsMap().put("user_id", uti_id);
            deleteuserRle.getParamsMap().put("role_id", new Long(61)); // Responsable formation
            deleteuserRle.execute();
            //retrieve weblogic role
            try {
                System.out.println("Assigning role to user in weblogic...");
                OperationBinding opusername = getBindings().getOperationBinding("getusername");
                opusername.getParamsMap().put("user_id", Long.parseLong(uti_id.toString()));
                String username = (String) opusername.execute();
                OperationBinding oprole = getBindings().getOperationBinding("getrole");
                oprole.getParamsMap().put("role_id", new Long(62));
                String role = (String) oprole.execute();
                if (null != role && null != username) {
                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                    System.out.println("username : " + username.substring(0, 1) + username.substring(1).toLowerCase() +
                                       " " + "role : " + role);
                    try {
                        AdministrationBean.revokeWlsRole2User(role, username);
                        System.out.println("Role assigned !");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    System.out.println("username : " + username + " role : " + role);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccesFormTable());
        }
    }
}
