package view.beans.paramSup.accesec;

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
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

import view.beans.admin.AdministrationBean;

public class EcsAccesBean {
    private RichTable ecTable;
    private RichSelectOneRadio ecCheked;
    private RichSelectOneRadio roleChecked;
    private RichTable accesEcTable;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer id_hs = Integer.parseInt(sessionScope.get("id_hs").toString());
    private RichSelectBooleanCheckbox selecAll;
    private RichPopup noUserSelected;
    private RichPopup noEcSelected;
    private RichTable userTable;
    private RichPanelCollection userPanCol;
    private RichPanelCollection ecPanCol;
    private RichSelectBooleanCheckbox is_uti;
    private RichSelectBooleanCheckbox is_ec;
    private RichPanelHeader accessPanH;
    private RichPanelCollection accessPancol;

    public EcsAccesBean() {
    }

    @SuppressWarnings("unchecked")
    public void OnStudentValueChangeListener(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
        Integer Id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        System.out.println("Id "+Id);
        OperationBinding lesEcs = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("GetEcsAcces");
        lesEcs.getParamsMap().put("annee", getAnne_univers());
        lesEcs.getParamsMap().put("uti", Id);
        lesEcs.getParamsMap().put("s_id", getId_hs());
        lesEcs.execute();
        
        OperationBinding lesAcces = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetAccesEc");
        lesAcces.getParamsMap().put("annee", getAnne_univers());
        lesAcces.getParamsMap().put("uti", Id);
        lesAcces.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAccesEcTable());
    }

    public void setEcTable(RichTable ecTable) {
        this.ecTable = ecTable;
    }

    public RichTable getEcTable() {
        return ecTable;
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveClicked(ActionEvent actionEvent) {
        // Il peut etre important de verifier si l'acces n'est pas encore autorisé avant d'enregistrer une autorisation
        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateursROVO1Iterator");
        Row currentuser = iter.getCurrentRow();
        Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
        
        DCIteratorBinding iterNiv_form = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateurNiveauFormationRO1Iterator");
        Row currentniv = iterNiv_form.getCurrentRow();
        Integer niv_form_id = Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString());
        
        try {
            OperationBinding ecsSelected = bindings.getOperationBinding("getSelectedEcs");
            ArrayList<Long> ecs = (ArrayList<Long>) ecsSelected.execute();
            System.out.println("Size ec "+ecs.size());
            if (ecs.size() != 0) { 
                // Pour chaque utilisateur lui accorder les accees choisies
                for (int eccounter = 0; eccounter < ecs.size(); eccounter++) {
                    //Verifier si l'utilisateur a deja acces a cet ec
                    OperationBinding isAllowAcces = bindings.getOperationBinding("IsEcAccesAllowed");
                    isAllowAcces.getParamsMap().put("s_id", getId_hs());
                    isAllowAcces.getParamsMap().put("uti", IdUser);
                    isAllowAcces.getParamsMap().put("fil_ec", ecs.get(eccounter));
                    isAllowAcces.getParamsMap().put("annee", getAnne_univers());
                    Object resul = isAllowAcces.execute();
                    
                    DCIteratorBinding is_allowed_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                             .getCurrentBindingsEntry()
                                                             .get("IsEcAccesAllowedROVOIterator");
                    Row crnt_is = is_allowed_iter.getCurrentRow();
                    if (crnt_is == null) {
                        // l'utilisateur n'a pas d'acces sur cet ec
                        // Verifier le type d'acces
                        if(this.getRoleChecked().getValue().toString().equals("RESPNSABLE EC")){
                            // Verifier si l'ec n'a pas de responsable déja
                            OperationBinding is_resp_ec = bindings.getOperationBinding("IsEcResponsableExist");
                            is_resp_ec.getParamsMap().put("s_id", getId_hs());
                            //is_resp_ec.getParamsMap().put("uti", IdUser);
                            is_resp_ec.getParamsMap().put("fil_ec", ecs.get(eccounter));
                            is_resp_ec.getParamsMap().put("annee", getAnne_univers());
                            is_resp_ec.execute();
                            iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("IsEcResponsableExistIterator");
                            Row is_exist_resp = iter.getCurrentRow();
                            if (is_exist_resp == null) {
                                // Pas de responsable pour cet ec
                                OperationBinding opAllowAcces = bindings.getOperationBinding("AllowedAccessTo");
                                opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                                opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                opAllowAcces.getParamsMap().put("IdFiliereUeSemstreEc", ecs.get(eccounter));
                                opAllowAcces.getParamsMap().put("Role", this.getRoleChecked().getValue().toString());
                                resul = opAllowAcces.execute();
                                
                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                allowuserRle.getParamsMap().put("user_id", IdUser);
                                allowuserRle.getParamsMap().put("role_id", new Long(64)); // Responsable ec
                                allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                allowuserRle.execute();

                                //assign weblogic role
                                try {
                                    System.out.println("Assigning role to user in weblogic...");
                                    OperationBinding opusername = bindings.getOperationBinding("getusername");
                                    opusername.getParamsMap().put("user_id", new Long(IdUser));
                                    String username = (String) opusername.execute();
                                    OperationBinding oprole = bindings.getOperationBinding("getrole");
                                    oprole.getParamsMap().put("role_id", new Long(64));
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
                            }else{
                                // Cet ec a deja un responsble
                                System.out.println("Responsable existant pour cet ec");
                            }
                            
                        }else{
                            // Role Simple
                            OperationBinding opAllowAcces = bindings.getOperationBinding("AllowedAccessTo");
                            opAllowAcces.getParamsMap().put("IdUtilisateur", IdUser);
                            opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                            opAllowAcces.getParamsMap().put("IdFiliereUeSemstreEc", ecs.get(eccounter));
                            opAllowAcces.getParamsMap().put("Role", this.getRoleChecked().getValue().toString());
                            resul = opAllowAcces.execute();
                            }
                        }else{
                            // if new role = RESPNSABLE EC et old role = ACCES SIMPLE
                            if(!(crnt_is.getAttribute("Role").toString().equals("RESPNSABLE EC")) && 
                               (this.getRoleChecked().getValue().toString().equals("RESPNSABLE EC"))){
                                // Update possible
                                // Verifier si l'ec a un responsable
                                OperationBinding is_resp_ec = bindings.getOperationBinding("IsEcResponsableExist");
                                is_resp_ec.getParamsMap().put("s_id", getId_hs());
                                //is_resp_ec.getParamsMap().put("uti", IdUser);
                                is_resp_ec.getParamsMap().put("fil_ec", ecs.get(eccounter));
                                is_resp_ec.getParamsMap().put("annee", getAnne_univers());
                                is_resp_ec.execute();
                                iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("IsEcResponsableExistIterator");
                                Row is_exist_resp = iter.getCurrentRow();
                                if (is_exist_resp == null) {
                                    // Pas de responsable pour ce Ec
                                    // Update current user role to RESPNSABLE EC
                                    System.out.println("Update role required from ACCES SIMPLE to RESPNSABLE EC !!!");
                                    OperationBinding updateRole = bindings.getOperationBinding("UpdateResponsableEc");
                                    updateRole.getParamsMap().put("idUser", IdUser);
                                    updateRole.getParamsMap().put("idFilEc", ecs.get(eccounter));
                                    updateRole.getParamsMap().put("role", this.getRoleChecked().getValue().toString());
                                    updateRole.getParamsMap().put("utimodif", getUtilisateur());
                                    updateRole.getParamsMap().put("id_uti_fil_ec", Integer.parseInt(crnt_is.getAttribute("IdUtilisFilierUeSemEc").toString()));
                                    resul = updateRole.execute();
                                    
                                    OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                    allowuserRle.getParamsMap().put("user_id", IdUser);
                                    allowuserRle.getParamsMap().put("role_id", new Long(64)); // Responsable ec
                                    allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                                    allowuserRle.execute();
                                    
                                }else{
                                    System.out.println("Responsable deja defini pour cet ec !!!");
                                }
                            }else{
                                System.out.println("Acces déja autorisé !!!");
                            }
                    }
                }
                // Tout ce passe bien On commit
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();
                // Réexecuter les VO pour actualiser les tableaux    
                OperationBinding lesEcs = BindingContext.getCurrent()
                                                        .getCurrentBindingsEntry()
                                                        .getOperationBinding("GetEcsAcces");
                lesEcs.getParamsMap().put("annee", getAnne_univers());
                lesEcs.getParamsMap().put("uti", IdUser);
                lesEcs.getParamsMap().put("s_id", getId_hs());
                lesEcs.getParamsMap().put("niv_form", niv_form_id);
                lesEcs.execute();
                
                OperationBinding lesAcces = BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .getOperationBinding("GetAccesEc");
                lesAcces.getParamsMap().put("annee", getAnne_univers());
                lesAcces.getParamsMap().put("uti", IdUser);
                lesAcces.getParamsMap().put("id_hs", getId_hs());
                lesAcces.getParamsMap().put("niv_form", niv_form_id);
                lesAcces.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAccesEcTable());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setEcCheked(RichSelectOneRadio ecCheked) {
        this.ecCheked = ecCheked;
    }

    public RichSelectOneRadio getEcCheked() {
        return ecCheked;
    }

    public void setRoleChecked(RichSelectOneRadio roleChecked) {
        this.roleChecked = roleChecked;
    }

    public RichSelectOneRadio getRoleChecked() {
        return roleChecked;
    }

    public void setAccesEcTable(RichTable accesEcTable) {
        this.accesEcTable = accesEcTable;
    }

    public RichTable getAccesEcTable() {
        return accesEcTable;
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

    public void setSelecAll(RichSelectBooleanCheckbox selecAll) {
        this.selecAll = selecAll;
    }

    public RichSelectBooleanCheckbox getSelecAll() {
        return selecAll;
    }

    public void OnSelectDiselectAll(ValueChangeEvent valueChangeEvent) {
       System.out.println(valueChangeEvent.getNewValue());
        /*DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("UtilisateursROVOIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isSelecteduser", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserTable());*/
    }

    @SuppressWarnings("unchecked")
    public void OnNiveauFormationChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
        Integer user_id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        
        AttributeBinding attrnivformBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormation");
        Integer niv_form_id = (Integer.parseInt(attrnivformBinding.getInputValue().toString()));
        
        OperationBinding lesEcs = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("GetEcsAcces");
        lesEcs.getParamsMap().put("annee", getAnne_univers());
        lesEcs.getParamsMap().put("uti", user_id);
        lesEcs.getParamsMap().put("s_id", getId_hs());
        lesEcs.getParamsMap().put("niv_form", niv_form_id);
        lesEcs.execute();
        
        OperationBinding lesAcces = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetAccesEc");
        lesAcces.getParamsMap().put("annee", getAnne_univers());
        lesAcces.getParamsMap().put("uti", user_id);
        lesAcces.getParamsMap().put("id_hs", getId_hs());
        lesAcces.getParamsMap().put("niv_form", niv_form_id);
        lesAcces.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEcTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAccesEcTable());
    }

    @SuppressWarnings("unchecked")
    public void OnSaveAccessClicked(ActionEvent actionEvent) {
        try {
            BindingContainer bindings = getBindings();
            OperationBinding ecsSelected = bindings.getOperationBinding("getSelectedFilEcPrcrs");
            Map<Long, Long> ecs = (HashMap<Long, Long>) ecsSelected.execute();
            //System.out.println("Size ec " + ecs.size());
            if (0 != ecs.size()) {
                try {
                    OperationBinding userSelected = bindings.getOperationBinding("getSelecteduser");
                    ArrayList<Long> user = (ArrayList<Long>) userSelected.execute();
                    //System.out.println("user size " + user.size());
                    if (user.size() != 0) {
                        //getFilUeNivFrFr(fil_ec,pma) to allow simple access to user
                        for (Map.Entry<Long, Long> entry : ecs.entrySet()) {
                            try {
                                //System.out.println("pma_id : "+entry.getValue());
                                //System.out.println("fec_id : "+entry.getKey());
                                OperationBinding op_fr_niv_filUe = bindings.getOperationBinding("getFormNivFormFilUe");
                                op_fr_niv_filUe.getParamsMap().put("pma_id", new Long(entry.getValue()));
                                op_fr_niv_filUe.getParamsMap().put("fec_id", new Long(entry.getKey()));
                                op_fr_niv_filUe.getParamsMap().put("an_id", getAnne_univers());
                                op_fr_niv_filUe.execute();
                                DCIteratorBinding fr_niv_filUe_iter = (DCIteratorBinding) bindings.get("FormNivFormFilUeIterator");
                                Row crnt = fr_niv_filUe_iter.getCurrentRow();
                                for (int usercounter = 0; usercounter < user.size(); usercounter++) {
                                    try {
                                        OperationBinding op_fec = bindings.getOperationBinding("createOrUpdateUserFilEc1");
                                        op_fec.getParamsMap().put("pma_", new Long(entry.getValue()));
                                        op_fec.getParamsMap().put("fil_ec_", new Long(entry.getKey()));
                                        op_fec.getParamsMap().put("role", "ACCES SIMPLE");
                                        op_fec.getParamsMap().put("user_", user.get(usercounter));
                                        op_fec.getParamsMap().put("utilisateur",getUtilisateur());
                                        Long fil_ec_id = (Long)op_fec.execute();
                                        if(0 != fil_ec_id) {
                                            //System.out.println("fil_ec_id : "+fil_ec_id);
                                            //create user role : agentSaisie
                                            //assign role to user in weblogic
                                            try {
                                                OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                                allowuserRle.getParamsMap().put("user_id", user.get(usercounter));
                                                allowuserRle.getParamsMap().put("role_id", new Long(101)); // Agent de saisie
                                                allowuserRle.getParamsMap()
                                                    .put("utilisateur", Integer.parseInt(getUtilisateur().toString()));
                                                Integer user_role = (Integer)allowuserRle.execute();
                                                if(0 != user_role){
                                                    //assign weblogic role
                                                    try {
                                                        OperationBinding opusername =
                                                            bindings.getOperationBinding("getusername");
                                                        opusername.getParamsMap().put("user_id", user.get(usercounter));
                                                        String username = (String) opusername.execute();
                                                        OperationBinding oprole =
                                                            bindings.getOperationBinding("getrole");
                                                        oprole.getParamsMap().put("role_id", new Long(101));
                                                        String role = (String) oprole.execute();
                                                        OperationBinding opmat =
                                                            bindings.getOperationBinding("getMatricule");
                                                        opmat.getParamsMap().put("utilisateur", new Long(usercounter));
                                                        String matricule = (String) opmat.execute();
                                                        //matricule = "passer123*";
                                                        if (null != role && null != username && null != matricule) {
                                                            role =
                                                                role.substring(0, 1) + role.substring(1).toLowerCase();
                                                            try {
                                                                AdministrationBean.createWlsUser(username,
                                                                                                 matricule.toCharArray());
                                                                AdministrationBean.assignWlsRole2User(role, username);
                                                                /*System.out.println("Role <<" + role +
                                                                                   ">> assigned to <<" + username +
                                                                                   ">> in weblogic");*/
                                                            } catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                        } else {
                                                            System.out.println("username : " + username + " role : " +
                                                                               role);
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            try {
                                                OperationBinding op_fue = bindings.getOperationBinding("createOrUpdateUserFilUe1");
                                                op_fue.getParamsMap().put("pma_", new Long(entry.getValue()));
                                                op_fue.getParamsMap().put("fil_ue_", Long.parseLong(crnt.getAttribute("IdFiliereUeSemstre").toString()));
                                                op_fue.getParamsMap().put("role", "ACCES SIMPLE");
                                                op_fue.getParamsMap().put("user_", user.get(usercounter));
                                                op_fue.getParamsMap().put("utilisateur", getUtilisateur());
                                                Long fil_ue_id = (Long)op_fue.execute();
                                                if(0 != fil_ue_id) {
                                                    //System.out.println("User fil ue : "+fil_ue_id);
                                                    try {
                                                        OperationBinding op_niv_fr = bindings.getOperationBinding("createOrUpdateUserNivForm");      
                                                        op_niv_fr.getParamsMap().put("niv_form_", Long.parseLong(crnt.getAttribute("IdNiveauFormation").toString()));
                                                        op_niv_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                                        op_niv_fr.getParamsMap().put("user_", user.get(usercounter));
                                                        op_niv_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                                        Long niv_fr_id = (Long)op_niv_fr.execute();
                                                        if(0 != niv_fr_id) {
                                                            //System.out.println("User niv fr : "+niv_fr_id);
                                                            try {
                                                                OperationBinding op_fr = bindings.getOperationBinding("createOrUpdateUserForm");      
                                                                op_fr.getParamsMap().put("form_", Long.parseLong(crnt.getAttribute("IdFormation").toString()));
                                                                op_fr.getParamsMap().put("role", "ACCES SIMPLE");
                                                                op_fr.getParamsMap().put("user_", user.get(usercounter));
                                                                op_fr.getParamsMap().put("utilisateur", getUtilisateur());
                                                                Long fr_id = (Long)op_fr.execute();
                                                                //System.out.println("User fr : "+fr_id);
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
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        /*this.getIs_uti().setValue(false);
                        this.getIs_ec().setValue(false);*/
                        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("UserAccesEcIterator");
                        ViewObject vo=iter.getViewObject();
                        vo.executeQuery();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getEcPanCol());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getEcTable());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserTable());

                        AdfFacesContext.getCurrentInstance().addPartialTarget(getAccessPanH());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getAccessPancol());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getAccesEcTable());
                    }
                    else {
                        //System.out.println("Select user please !");
                        RichPopup p = this.getNoUserSelected();
                        RichPopup.PopupHints ph = new RichPopup.PopupHints();
                        p.show(ph);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("Select ec please !");
                RichPopup p = this.getNoEcSelected();
                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                p.show(ph);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setNoUserSelected(RichPopup noUserSelected) {
        this.noUserSelected = noUserSelected;
    }

    public RichPopup getNoUserSelected() {
        return noUserSelected;
    }

    public void setNoEcSelected(RichPopup noEcSelected) {
        this.noEcSelected = noEcSelected;
    }

    public RichPopup getNoEcSelected() {
        return noEcSelected;
    }

    public void setUserTable(RichTable userTable) {
        this.userTable = userTable;
    }

    public RichTable getUserTable() {
        return userTable;
    }

    public void setUserPanCol(RichPanelCollection userPanCol) {
        this.userPanCol = userPanCol;
    }

    public RichPanelCollection getUserPanCol() {
        return userPanCol;
    }

    public void OnUserSelected(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("UtilisateursROVOIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isUserSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserTable());
    }

    public void onSelectedAll(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
    }

    public void onEcSelected(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FiliereUeSemEcByRespIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isEcSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getEcPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getEcTable());
    }

    public void setEcPanCol(RichPanelCollection ecPanCol) {
        this.ecPanCol = ecPanCol;
    }

    public RichPanelCollection getEcPanCol() {
        return ecPanCol;
    }

    public void setIs_uti(RichSelectBooleanCheckbox is_uti) {
        this.is_uti = is_uti;
    }

    public RichSelectBooleanCheckbox getIs_uti() {
        return is_uti;
    }

    public void setIs_ec(RichSelectBooleanCheckbox is_ec) {
        this.is_ec = is_ec;
    }

    public RichSelectBooleanCheckbox getIs_ec() {
        return is_ec;
    }
    
    public void initTopEc(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FiliereUeSemEcByRespIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isEcSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isEcSelected")){
                if(nextRow.getAttribute("isEcSelected").equals(false)){
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_ec().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initEc(){
        if (null != this.getIs_ec().getValue()) {
            if (this.getIs_ec().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("FiliereUeSemEcByRespIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isEcSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEcPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEcTable());
            } 
        }
    }
    
    public void initTopUti(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateursROVOIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isUserSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isUserSelected")){
                if(nextRow.getAttribute("isUserSelected").equals(false)){
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_uti().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initUti(){
        if (null != this.getIs_uti().getValue()) {
            if (this.getIs_uti().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("UtilisateursROVOIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isUserSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getUserTable());
            } 
        }
    }

    public void setAccessPanH(RichPanelHeader accessPanH) {
        this.accessPanH = accessPanH;
    }

    public RichPanelHeader getAccessPanH() {
        return accessPanH;
    }

    public void setAccessPancol(RichPanelCollection accessPancol) {
        this.accessPancol = accessPancol;
    }

    public RichPanelCollection getAccessPancol() {
        return accessPancol;
    }
}
