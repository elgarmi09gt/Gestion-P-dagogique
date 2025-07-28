package view.beans.admin;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import javax.xml.bind.DatatypeConverter;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.security.idm.IMException;
import oracle.security.idm.IdentityStore;
import oracle.security.idm.ObjectNotFoundException;
import oracle.security.idm.Role;
import oracle.security.idm.RoleManager;
import oracle.security.idm.User;
import oracle.security.idm.UserManager;
import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.JpsException;
import oracle.security.jps.service.idstore.IdentityStoreService;

public class AdministrationBean {
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private String code_grh = sessionScope.get("id_str_grh").toString();
    private Long str = Long.parseLong(sessionScope.get("id_str").toString());
    private String login = sessionScope.get("userName").toString();
    private RichPanelHeader panuserrole;
    private RichInputText onSearchClicked;
    private RichInputText m_;
    private RichPanelFormLayout details_agent;
    private RichTable detagent;
    private RichPanelCollection detAgentPan;
    private RichPopup usersuccesscreated;
    private RichPopup usercreatefailed;
    private RichPopup usernotfound;
    private RichPanelGroupLayout pangrpuser;
    private RichPanelCollection pancoluser;
    private RichTable tabuser;
    private RichPanelGroupLayout panroot;
    private RichPanelCollection detAgentEtabPan;
    private RichTable detagentEtab;
    private RichPanelCollection pancoluseretab;
    private RichTable tabuseretab;
    private RichPopup popupsuccessintegrated;
    private RichPopup popupfailedintegration;
    private RichPanelGroupLayout btnpan;
    private RichPopup popupchooseyear;
    private RichPopup popupDisiok;
    private RichPopup popupDisifailed;
    private RichPanelHeader pandetuserRole;
    private RichPanelCollection detroleuser;
    private RichPopup popupConfirmDelUserRole;
    private RichTable userRoleTable;
    private RichPopup popNotSelectedItems;
    private RichInputText mat_;
    private RichSelectOneRadio search_choice;
    private RichSelectBooleanCheckbox is_user;
    private RichSelectBooleanCheckbox is_role;
    private RichTable userTable;
    private RichPanelCollection userPanCol;
    private RichPanelHeader userPanHdr;
    private RichTable roleTable;
    private RichPanelCollection rolePanCol;
    private RichPanelHeader rolePanHdr;
    private RichInputText anc_pass;
    private RichInputText new_pass;
    private RichInputText confirm_pass;
    private String ancpw, newpw, confirmpw;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private static final String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String EMAIL_PATTERN ="[A-Za-z0-9\\._%+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,}";

    private static final Pattern patternPwd = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern patternEmail= Pattern.compile(EMAIL_PATTERN);

    private RichPopup popPassIncorrect;
    private RichPanelGroupLayout panMessage;
    private RichPanelGroupLayout panRoot;
    private RichPopup popAddUser;
    private RichPopup popupAddUserOk;
    private RichPopup popupAddUserErr;
    private RichPopup popupUserExiste;

    public AdministrationBean() {
    }
    
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }

    @SuppressWarnings("unchecked")
    public void onUserRoleSaved(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        try{
            OperationBinding rolesSelected = bindings.getOperationBinding("getSelectedRoles");
            ArrayList<Long> roles = (ArrayList<Long>) rolesSelected.execute();
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
            ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();
            
        }catch(Exception e){
            System.out.println(e);
        }
        try {
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
            ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();
            if (users.size() != 0) {
                OperationBinding rolesSelected = bindings.getOperationBinding("getSelectedRole");
                ArrayList<Long> roles = (ArrayList<Long>) rolesSelected.execute();
                if (roles.size() != 0) {
                    for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                        for (int rolecounter = 0; rolecounter < roles.size(); rolecounter++) {
                            try {
                                OperationBinding addRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                                addRole.getParamsMap().put("user_id", users.get(usercounter));
                                addRole.getParamsMap().put("role_id", roles.get(rolecounter));
                                addRole.getParamsMap().put("utilisateur", getUtilisateur());
                                Integer user_role_id = (Integer) addRole.execute();
                                if (0 != user_role_id) {
                                    try {
                                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                                        opusername.getParamsMap().put("user_id", users.get(usercounter));
                                        String username = (String) opusername.execute();
                                        OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                        opmat.getParamsMap().put("utilisateur", users.get(usercounter));
                                        String matricule = (String) opmat.execute();
                                        //matricule = "passer123*";
                                        OperationBinding oprole = bindings.getOperationBinding("getrole");
                                        oprole.getParamsMap().put("role_id", roles.get(rolecounter));
                                        String role = (String) oprole.execute();
                                        if (null != role && null != username && null != matricule) {
                                            role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                            try {
                                                createWlsUser(username, matricule.toCharArray());
                                                assignWlsRole2User(role, username);
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
                    }
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getPandetuserRole());                    
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getDetroleuser());
                } 
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUtilisateur(Integer utilisateur) {
            this.utilisateur = utilisateur;
        }

        public Integer getUtilisateur() {
            return utilisateur;
        }
        
        @SuppressWarnings("unchecked")
        public void onUserFonctionSaved(ActionEvent actionEvent) {
            // Add event code here...
            BindingContainer bindings = getBindings();
            
            try {
                OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
                ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();
                if (users.size() != 0) { 
                        OperationBinding fonctionsSelected = bindings.getOperationBinding("getSelectedFonction");
                    ArrayList<Long> fonctions = (ArrayList<Long>) fonctionsSelected.execute();
                        if(fonctions.size() != 0){
                            for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                                for (int fonctioncounter = 0; fonctioncounter < fonctions.size(); fonctioncounter++) {
                                    OperationBinding isFonctionUserExist = bindings.getOperationBinding("IsFonctionUserExist");
                                    isFonctionUserExist.getParamsMap().put("user_id", users.get(usercounter));
                                    isFonctionUserExist.getParamsMap().put("fonction_id", fonctions.get(fonctioncounter));
                                    Object resul = isFonctionUserExist.execute();
                                    DCIteratorBinding is_user_fonction_exist_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .get("IsFonctionUserExistIterator");
                                    Row crnt_is = is_user_fonction_exist_iter.getCurrentRow();
                                    if (crnt_is == null) {
                                        OperationBinding addFonction = bindings.getOperationBinding("AddUserFonction");
                                        addFonction.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                                        addFonction.getParamsMap().put("IdFonctionnalite", fonctions.get(fonctioncounter));
                                        addFonction.getParamsMap().put("UtiCree", getUtilisateur());
                                        resul = addFonction.execute();
                                    }
                                }
                            }
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            Object result = operationBinding.execute();
                        }
                    }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    public void OnDeleteAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            this.getPopupConfirmDelUserRole().hide();
            BindingContainer bindings = getBindings();
            try {
                OperationBinding operationBinding = bindings.getOperationBinding("Delete");
                Object result = operationBinding.execute();
                //Supprimer le weblogic role
                if (!operationBinding.getErrors().isEmpty()) {
                } else {
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    operationCommit.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanuserrole());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setPanuserrole(RichPanelHeader panuserrole) {
        this.panuserrole = panuserrole;
    }

    public RichPanelHeader getPanuserrole() {
        return panuserrole;
    }

    public void setOnSearchClicked(RichInputText onSearchClicked) {
        this.onSearchClicked = onSearchClicked;
    }

    public RichInputText getOnSearchClicked() {
        return onSearchClicked;
    }

    public void setM_(RichInputText m_) {
        this.m_ = m_;
    }

    public RichInputText getM_() {
        return m_;
    }

    @SuppressWarnings("unchecked")
    public void OnSearchClicked(ActionEvent actionEvent) {
        if (null != this.getM_().getValue()) {
            String name = this.getM_().getValue().toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Veuillez entrer un email ucad valide.";
            if (matcher.matches()) {
                //Do your further processing
                BindingContainer bindings = getBindings();
                ADFContext adfCtx = ADFContext.getCurrent();
                SecurityContext secCntx = adfCtx.getSecurityContext();
                if(secCntx.getUserPrincipal().getName().equals("admin.ucad")){
                    OperationBinding opsearch = bindings.getOperationBinding("grhUser");
                    opsearch.getParamsMap().put("mat", this.getM_().getValue());
                    opsearch.execute();
                    DCIteratorBinding user_iter = (DCIteratorBinding) bindings.get("grhUserIterator");
                        
                    Row crnt_is = user_iter.getCurrentRow();
                    if (null != crnt_is) {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetAgentPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetagent());
                    } else {
                        //show popup user not exist
                        RichPopup popup = this.getUsernotfound();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                }
                else{
                    OperationBinding opsearch = bindings.getOperationBinding("userEtabSearch");
                    opsearch.getParamsMap().put("mat", this.getM_().getValue());
                    opsearch.getParamsMap().put("grh_code", this.getCode_grh());
                    opsearch.getParamsMap().put("log", getLogin());
                    opsearch.execute();
                    DCIteratorBinding user_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .get("grhUserEtabIterator");
                    Row crnt_is = user_iter.getCurrentRow();
                    if (null != crnt_is) {
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetAgentEtabPan());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetagentEtab());
                    } else {
                        //show popup user not exist
                        RichPopup popup = this.getUsernotfound();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    }
                }
               
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

        } 

    }

    public void setDetails_agent(RichPanelFormLayout details_agent) {
        this.details_agent = details_agent;
    }

    public RichPanelFormLayout getDetails_agent() {
        return details_agent;
    }

    @SuppressWarnings("unchecked")
    public void OnSaveAgentClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        String iter = "";
        String op = "";
        String iterfresh = "";
        Long id_user = new Long(0);
        char [] password = new char [100];
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        //penser à utiliser le role aulieu du username 
        if(secCntx.getUserPrincipal().getName().equals("admin.ucad")){
            iter = "grhUserIterator";
            op = "grhUser";
            iterfresh = "UtilisateursIterator";
        }else{
            iter = "grhUserEtabIterator";
            op = "grhUserEtab";
            iterfresh = "UtilisateurEtabROVOIterator";
        }
        DCIteratorBinding user_iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get(iter);
        Row crnt_is = user_iter.getCurrentRow();
        if (null != crnt_is) {
            try {
                OperationBinding opnewUser = bindings.getOperationBinding("createOrUpdateUser");
                opnewUser.getParamsMap().put("email", crnt_is.getAttribute("Emailucad"));
                opnewUser.getParamsMap().put("struct_", crnt_is.getAttribute("AncienCode").toString());
                opnewUser.getParamsMap().put("utilisateur", getUtilisateur());
                id_user = (Long) opnewUser.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("email : "+crnt_is.getAttribute("Emailucad").toString());
            System.out.println("struct_ : "+crnt_is.getAttribute("AncienCode").toString());
            if (0 != id_user) {
                String login = crnt_is.getAttribute("Emailucad").toString();
                String matricule = crnt_is.getAttribute("CodeMatricule").toString();
                 //matricule = "passer123*";
                password = matricule.toCharArray();
                try {
                    createWlsUser(login, password);
                } catch (Exception e) {
                    System.out.println(e);
                }
                RichPopup popup = this.getUsersuccesscreated();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
                this.getM_().resetValue();
                OperationBinding opsearch = bindings.getOperationBinding(op);
                opsearch.getParamsMap().put("matricule", "0");
                if (op == "grhUserEtab")
                    opsearch.getParamsMap().put("grh_code", this.getCode_grh());
                    opsearch.getParamsMap().put("log", this.getLogin());
                opsearch.execute();
            } else {
                RichPopup popup = this.getUsercreatefailed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        OperationBinding opCommit = bindings.getOperationBinding("Commit");
        opCommit.execute();
        DCIteratorBinding iterBind = (DCIteratorBinding)bindings.get(iterfresh);
        ViewObject vo=iterBind.getViewObject();  
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanroot());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPangrpuser());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancoluser());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabuser());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancoluseretab());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTabuseretab());
    }
    
    public void setDetagent(RichTable detagent) {
        this.detagent = detagent;
    }

    public RichTable getDetagent() {
        return detagent;
    }

    public void setDetAgentPan(RichPanelCollection detAgentPan) {
        this.detAgentPan = detAgentPan;
    }

    public RichPanelCollection getDetAgentPan() {
        return detAgentPan;
    }

    public void setUsersuccesscreated(RichPopup usersuccesscreated) {
        this.usersuccesscreated = usersuccesscreated;
    }

    public RichPopup getUsersuccesscreated() {
        return usersuccesscreated;
    }

    public void setUsercreatefailed(RichPopup usercreatefailed) {
        this.usercreatefailed = usercreatefailed;
    }

    public RichPopup getUsercreatefailed() {
        return usercreatefailed;
    }

    public void setUsernotfound(RichPopup usernotfound) {
        this.usernotfound = usernotfound;
    }

    public RichPopup getUsernotfound() {
        return usernotfound;
    }

    public void setPangrpuser(RichPanelGroupLayout pangrpuser) {
        this.pangrpuser = pangrpuser;
    }

    public RichPanelGroupLayout getPangrpuser() {
        return pangrpuser;
    }

    public void setPancoluser(RichPanelCollection pancoluser) {
        this.pancoluser = pancoluser;
    }

    public RichPanelCollection getPancoluser() {
        return pancoluser;
    }

    public void setTabuser(RichTable tabuser) {
        this.tabuser = tabuser;
    }

    public RichTable getTabuser() {
        return tabuser;
    }

    public void EmailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Veuillez entrer un email ucad valide.";
            if (matcher.matches()) {
                //Do your further processing
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void setPanroot(RichPanelGroupLayout panroot) {
        this.panroot = panroot;
    }

    public RichPanelGroupLayout getPanroot() {
        return panroot;
    }

    public void setCode_grh(String code_grh) {
        this.code_grh = code_grh;
    }

    public String getCode_grh() {
        return code_grh;
    }

    public void setDetAgentEtabPan(RichPanelCollection detAgentEtabPan) {
        this.detAgentEtabPan = detAgentEtabPan;
    }

    public RichPanelCollection getDetAgentEtabPan() {
        return detAgentEtabPan;
    }

    public void setDetagentEtab(RichTable detagentEtab) {
        this.detagentEtab = detagentEtab;
    }

    public RichTable getDetagentEtab() {
        return detagentEtab;
    }

    public void setPancoluseretab(RichPanelCollection pancoluseretab) {
        this.pancoluseretab = pancoluseretab;
    }

    public RichPanelCollection getPancoluseretab() {
        return pancoluseretab;
    }

    public void setTabuseretab(RichTable tabuseretab) {
        this.tabuseretab = tabuseretab;
    }

    public RichTable getTabuseretab() {
        return tabuseretab;
    }

    @SuppressWarnings("unchecked")
    public void onIntegrateStructClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        Long str_id = new Long(0);
        Long dept_id = new Long(0);
        Long disi_dept_id = new Long(0);
        DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("ScolEtablissementIterator");
        Row CurrentStr = iterStr.getCurrentRow();
        if (null != CurrentStr) {
            try {
                OperationBinding opcreatestr = bindings.getOperationBinding("createOrUpdateStruct");
                opcreatestr.getParamsMap().put("anc_code", CurrentStr.getAttribute("CodeScol"));
                opcreatestr.getParamsMap().put("lib", CurrentStr.getAttribute("Libelle"));
                opcreatestr.getParamsMap().put("abrev", CurrentStr.getAttribute("Sigle"));
                opcreatestr.getParamsMap().put("tel", CurrentStr.getAttribute("Telephone"));
                opcreatestr.getParamsMap().put("addr", CurrentStr.getAttribute("Adresse"));
                opcreatestr.getParamsMap().put("type_etab", CurrentStr.getAttribute("TypeEtab"));
                opcreatestr.getParamsMap().put("utilisateur", getUtilisateur());
                str_id = (Long) opcreatestr.execute();
            } catch (Exception e) {
            System.out.println(e);
            }
            if ((0 != str_id) && (-1 != str_id)) {
                try {
                    DCIteratorBinding iterDept = (DCIteratorBinding) bindings.get("ScolDepartementIterator");
                    Row CurrentDept = iterDept.getCurrentRow();
                    if (null != CurrentDept) {
                        OperationBinding opcreatedept = bindings.getOperationBinding("createOrUpdateDept");
                        opcreatedept.getParamsMap().put("anc_code", CurrentDept.getAttribute("Code"));
                        opcreatedept.getParamsMap().put("lib", CurrentDept.getAttribute("LibelleLong"));
                        opcreatedept.getParamsMap().put("abrev", CurrentDept.getAttribute("LibelleCourt"));
                        opcreatedept.getParamsMap().put("str_id", str_id);
                        opcreatedept.getParamsMap().put("utilisateur", getUtilisateur());
                        dept_id = (Long) opcreatedept.execute();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 != dept_id) {
                    sessionScope.put("is_disi_creable", 1);
                    sessionScope.put("dept_id", dept_id);
                    sessionScope.put("etab_id", str_id);
                    sessionScope.put("log", CurrentStr.getAttribute("Sigle").toString().toLowerCase());
                    
                    RichPopup popup = this.getPopupsuccessintegrated();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }else{
                    sessionScope.put("is_disi_creable", 0);
                    sessionScope.put("dept_id", 0);
                    sessionScope.put("etab_id", 0);
                    sessionScope.put("log", "");
                    sessionScope.put("msg" , " Vérifier bien que les infos du département sont bien remplies !!");
                    RichPopup popup = this.getPopupfailedintegration();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    popup.show(ph);
                }
            }else if(-1 == str_id){
                sessionScope.put("is_disi_creable", 0);
                    sessionScope.put("dept_id", 0);
                sessionScope.put("msg" , " L'établissement n'est d'aucun type");
                RichPopup popup = this.getPopupfailedintegration();
                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                popup.show(ph);
            }else{
                sessionScope.put("is_disi_creable", 0);
                    sessionScope.put("dept_id", 0);
                    sessionScope.put("etab_id", 0);
                    sessionScope.put("log", "");
                sessionScope.put("msg" , " Vérifier bien que les infos de l'établissement sont bien remplies !! ");
                RichPopup popup = this.getPopupfailedintegration();
                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                popup.show(ph);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnpan());
    }

    @SuppressWarnings("unchecked")
    public void OnCreateDisiAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome otc = dialogEvent.getOutcome();
        if(otc.equals(Outcome.yes)){
            this.getPopupchooseyear().hide();
            BindingContainer bindings = getBindings();
            Long form_id = new Long(0);
            Long niv_form_id = new Long(0);
            Long niv_form_crt_id = new Long(0);
            Long niv_form_prcrs_id = new Long(0);
            Integer calendar_id = 0;
            Long dpt_id = new Long(0);
            Integer nbr_cal = 0;
            DCIteratorBinding iterAn = (DCIteratorBinding)getBindings().get("AnneeUniverROVOIterator");
            Row currentAn = iterAn.getCurrentRow();
            if( (0 != Integer.parseInt(sessionScope.get("etab_id").toString()))){
                String login = sessionScope.get("log").toString() + ".disi".toLowerCase();
                char [] password = login.toCharArray();
                Long user_d_id = new Long(0);
                try {
                    OperationBinding disidept = bindings.getOperationBinding("CreateOrUpdateDisiDept");
                    disidept.getParamsMap().put("str_id", Long.parseLong(sessionScope.get("etab_id").toString()));
                    disidept.getParamsMap().put("utilisateur", getUtilisateur());
                    dpt_id = (Long) disidept.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (0 != dpt_id) {
                    try {
                        OperationBinding disi_user = bindings.getOperationBinding("CreateOrUpdateDisiUser");
                        disi_user.getParamsMap().put("log", login.toLowerCase());
                        disi_user.getParamsMap().put("str_id", Long.parseLong(sessionScope.get("etab_id").toString()));
                        disi_user.getParamsMap().put("utilisateur", getUtilisateur());
                        user_d_id = (Long) disi_user.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        OperationBinding disiForm = bindings.getOperationBinding("CreateOrUpdateDisiForm");
                        disiForm.getParamsMap().put("hs", dpt_id);
                        disiForm.getParamsMap().put("utilisateur", getUtilisateur());
                        form_id = (Long) disiForm.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if ((0 != form_id) && (0 != user_d_id)) {
                        try {
                            OperationBinding opUserRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                            opUserRole.getParamsMap().put("user_id", user_d_id);
                            opUserRole.getParamsMap().put("role_id", new Long(23));
                            opUserRole.getParamsMap().put("utilisateur", getUtilisateur());
                            opUserRole.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //user_weblogic
                        try {
                            createWlsUser(login, password);
                            //createWlsUser(login, matricule);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //user_role_weblogic
                        try {
                            OperationBinding opusername = bindings.getOperationBinding("getusername");
                            opusername.getParamsMap().put("user_id", user_d_id);
                            String username = (String) opusername.execute();
                            OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                            opmat.getParamsMap().put("utilisateur", user_d_id);
                            String matricule = (String) opmat.execute();
                            //matricule = "passer123*";
                            OperationBinding oprole = bindings.getOperationBinding("getrole");
                            oprole.getParamsMap().put("role_id", new Long(23));
                            String role = (String) oprole.execute();
                            if (null != role && null != username && null != matricule) {
                                role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                try {
                                    createWlsUser(username, matricule.toCharArray());
                                    assignWlsRole2User(role, username);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } 
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        
                        try {
                            OperationBinding opUserForm = bindings.getOperationBinding("createOrUpdateUserForm");
                            opUserForm.getParamsMap().put("user_", user_d_id);
                            opUserForm.getParamsMap().put("role", "ACCES SIMPLE");
                            opUserForm.getParamsMap().put("form_", form_id);
                            opUserForm.getParamsMap().put("utilisateur", getUtilisateur());
                            opUserForm.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding disinivForm =
                                bindings.getOperationBinding("CreateOrUpdateDisiNivFormation");
                            disinivForm.getParamsMap().put("fr_id", form_id);
                            disinivForm.getParamsMap().put("utilisateur", getUtilisateur());
                            niv_form_id = (Long) disinivForm.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (0 != niv_form_id) {
                            try {
                                OperationBinding opUserNivForm =
                                    bindings.getOperationBinding("createOrUpdateUserNivForm");
                                opUserNivForm.getParamsMap().put("user_", user_d_id);
                                opUserNivForm.getParamsMap().put("role", "ACCES SIMPLE");
                                opUserNivForm.getParamsMap().put("niv_form_", niv_form_id);
                                opUserNivForm.getParamsMap().put("utilisateur", getUtilisateur());
                                opUserNivForm.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            try {
                                OperationBinding opNivFormCrt =
                                    bindings.getOperationBinding("CreateOrUpdateDisiNivFormCohrte");
                                opNivFormCrt.getParamsMap().put("nfr_id", niv_form_id);
                                opNivFormCrt.getParamsMap().put("utilisateur", getUtilisateur());
                                niv_form_crt_id = (Long) opNivFormCrt.execute();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (0 != niv_form_crt_id) {
                                try {
                                    OperationBinding opNivFormprcrs =
                                        bindings.getOperationBinding("CreateOrUpdateDisiNivFormPrcrs");
                                    opNivFormprcrs.getParamsMap().put("nfr_crt_id", niv_form_crt_id);
                                    opNivFormprcrs.getParamsMap().put("utilisateur", getUtilisateur());
                                    niv_form_prcrs_id = (Long) opNivFormprcrs.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                try {
                                    OperationBinding is_exist = bindings.getOperationBinding("isCalendrierExist");
                                    is_exist.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                    is_exist.getParamsMap().put("sem_id", new Long(1));
                                    is_exist.getParamsMap()
                                        .put("an_id",
                                             Long.parseLong(currentAn.getAttribute("IdAnneesUnivers").toString()));
                                    nbr_cal = (Integer) is_exist.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                if (0 == nbr_cal) {
                                    try {
                                        OperationBinding opnewcal =
                                            bindings.getOperationBinding("CreateOrUpdateCalendar");
                                        opnewcal.getParamsMap().put("niv_crt_id", niv_form_crt_id);
                                        opnewcal.getParamsMap().put("sem_id", new Long(1));
                                        opnewcal.getParamsMap().put("an_id", Long.parseLong(currentAn.getAttribute("IdAnneesUnivers").toString()));
                                        opnewcal.getParamsMap().put("utilisateur", getUtilisateur());
                                        opnewcal.getParamsMap().put("sess_id", 1);
                                        calendar_id = (Integer) opnewcal.execute();
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    if (0 != calendar_id) {
                                        RichPopup pop = this.getPopupDisiok();
                                        RichPopup.PopupHints ph = new RichPopup.PopupHints();
                                        pop.show(ph);
                                    } else {
                                        sessionScope.put("err_msg", " Erreur Calendrier");
                                        RichPopup pop = this.getPopupDisifailed();
                                        RichPopup.PopupHints ph = new RichPopup.PopupHints();
                                        pop.show(ph);
                                    }
                                } else {
                                    RichPopup pop = this.getPopupDisiok();
                                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                                    pop.show(ph);
                                }
                            }else {
                                sessionScope.put("err_msg", " Erreur Niveau Cohorte");
                                RichPopup pop = this.getPopupDisifailed();
                                RichPopup.PopupHints ph = new RichPopup.PopupHints();
                                pop.show(ph);
                            }
                        } else {
                            sessionScope.put("err_msg", " Erreur Import Niveau Formation");
                            RichPopup pop = this.getPopupDisifailed();
                            RichPopup.PopupHints ph = new RichPopup.PopupHints();
                            pop.show(ph);
                        }
                    }else {
                        sessionScope.put("err_msg", " Erreur Import Formation et/ou Création User");
                        RichPopup pop = this.getPopupDisifailed();
                        RichPopup.PopupHints ph = new RichPopup.PopupHints();
                        pop.show(ph);
                    }
                }else {
                    sessionScope.put("err_msg", " Erreur Import Département");
                    RichPopup pop = this.getPopupDisifailed();
                    RichPopup.PopupHints ph = new RichPopup.PopupHints();
                    pop.show(ph);
                }
            }
        }
    }

    public void setPopupsuccessintegrated(RichPopup popupsuccessintegrated) {
        this.popupsuccessintegrated = popupsuccessintegrated;
    }

    public RichPopup getPopupsuccessintegrated() {
        return popupsuccessintegrated;
    }

    public void setPopupfailedintegration(RichPopup popupfailedintegration) {
        this.popupfailedintegration = popupfailedintegration;
    }

    public RichPopup getPopupfailedintegration() {
        return popupfailedintegration;
    }

    @SuppressWarnings("unchecked")
    private void OnValueChanged(){
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterStr = (DCIteratorBinding) bindings.get("ScolEtablissementIterator");
        Row CurrentStr = iterStr.getCurrentRow();
        if (null != CurrentStr) {
            DCIteratorBinding iterDept = (DCIteratorBinding) bindings.get("ScolDepartementIterator");
            Row CurrentDept = iterDept.getCurrentRow();
            if (null != CurrentDept) {
                OperationBinding opetbexist = bindings.getOperationBinding("isEtbExist");
                opetbexist.getParamsMap().put("anc_code", CurrentStr.getAttribute("CodeScol"));
                opetbexist.execute();
                OperationBinding opdeptexist = bindings.getOperationBinding("isDeptExist");
                opdeptexist.getParamsMap().put("anc_code", CurrentDept.getAttribute("Code"));
                opdeptexist.execute();
                DCIteratorBinding iterEtabExist = (DCIteratorBinding) bindings.get("isEtbExistROVOIterator");
                DCIteratorBinding iterDeptExist = (DCIteratorBinding) bindings.get("isDeptExistROVOIterator");
                if ((0 < iterEtabExist.getEstimatedRowCount()) && (0 < iterDeptExist.getEstimatedRowCount())) {
                    sessionScope.put("is_disi_creable", 1);
                    sessionScope.put("dept_id", iterDeptExist.getCurrentRow().getAttribute("IdHistoriquesStructure"));
                    sessionScope.put("etab_id", iterEtabExist.getCurrentRow().getAttribute("IdStructure"));
                    sessionScope.put("log", iterEtabExist.getCurrentRow().getAttribute("LibelleCourt"));
                } else {
                    sessionScope.put("is_disi_creable", 0);
                    sessionScope.put("etab_id", 0);
                    sessionScope.put("log", "");
                    sessionScope.put("dept_id", 0);
                }
            } else {
                sessionScope.put("is_disi_creable", 0);
                sessionScope.put("dept_id", 0);
                sessionScope.put("etab_id", 0);
                sessionScope.put("log", "");
            }
        } else {
            sessionScope.put("is_disi_creable", 0);
            sessionScope.put("dept_id", 0);
            sessionScope.put("etab_id", 0);
            sessionScope.put("log", "");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBtnpan());
    }

    @SuppressWarnings("unchecked")
    public void OnEtabValueChanged(ValueChangeEvent valueChangeEvent) {
        OnValueChanged();
    }

    @SuppressWarnings("unchecked")
    public void OnDeptValueChanged(ValueChangeEvent valueChangeEvent) {
        OnValueChanged();
    }

    public void setBtnpan(RichPanelGroupLayout btnpan) {
        this.btnpan = btnpan;
    }

    public RichPanelGroupLayout getBtnpan() {
        return btnpan;
    }

    public void setPopupchooseyear(RichPopup popupchooseyear) {
        this.popupchooseyear = popupchooseyear;
    }

    public RichPopup getPopupchooseyear() {
        return popupchooseyear;
    }

    public void setPopupDisiok(RichPopup popupDisiok) {
        this.popupDisiok = popupDisiok;
    }

    public RichPopup getPopupDisiok() {
        return popupDisiok;
    }

    public void setPopupDisifailed(RichPopup popupDisifailed) {
        this.popupDisifailed = popupDisifailed;
    }

    public RichPopup getPopupDisifailed() {
        return popupDisifailed;
    }

    public static void createWlsUser(String username, char[] password) {
        try {
            //, PropertySet ps
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            UserManager mn = is.getUserManager();
            //char[] password = pass.getBytes().toString().toCharArray();
            try{
                User user = (User) is.searchUser(username.toLowerCase());
            }catch(ObjectNotFoundException ex){
                mn.createUser(username.toLowerCase(), password);
                
            }
        } catch (JpsException e) {
            e.printStackTrace();
            //   JSFUtils.addFacesErrorMessage(e.getMessage());
        } catch (IMException e) {
            e.printStackTrace();
            //      JSFUtils.addFacesErrorMessage(e.getMessage());
        }
    }

    public static void createWlsRole(String rolename) {
        try {
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            RoleManager rm = is.getRoleManager();
            try{
                Role role = is.searchRole(Role.SCOPE_APPLICATION, rolename);
            }catch(ObjectNotFoundException ex){
                rm.createRole(rolename);
            }
        } catch (JpsException e) {
            e.printStackTrace();
            //   JSFUtils.addFacesErrorMessage(e.getMessage());
        } catch (IMException e) {
            e.printStackTrace();
            //      JSFUtils.addFacesErrorMessage(e.getMessage());
        }
    }

    public static void assignWlsRole2User(String roleName, String userName) {
        try {
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            RoleManager roleManager = is.getRoleManager();
            try{
                Role role = is.searchRole(Role.SCOPE_APPLICATION, roleName);
                User user = (User) is.searchUser(userName);
                    if(false == roleManager.isGranted(role,user.getPrincipal())){
                        roleManager.grantRole(role, user.getPrincipal());
                    }
            }catch(ObjectNotFoundException ex){
                System.out.println("Role or user not existe");
            }
        } catch (JpsException e) {
            e.printStackTrace();
            //   JSFUtils.addFacesErrorMessage(e.getMessage());
        } catch (IMException e) {
            e.printStackTrace();
            //      JSFUtils.addFacesErrorMessage(e.getMessage());
        }
    }

    public static void revokeWlsRole2User(String roleName, String userName) {
        try {
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            RoleManager roleManager = is.getRoleManager();
            try {
                userName = userName.toLowerCase();
                Role role = is.searchRole(Role.SCOPE_APPLICATION, roleName);
                User user = (User) is.searchUser(userName.toLowerCase());

                if (true == roleManager.isGranted(role, user.getPrincipal())) {
                    roleManager.revokeRole(role, user.getPrincipal());
                } 
            } catch (ObjectNotFoundException ex) {
                System.out.println("Role or user not existe");
            }
        } catch (JpsException e) {
            e.printStackTrace();
            //   JSFUtils.addFacesErrorMessage(e.getMessage());
        } catch (IMException e) {
            e.printStackTrace();
            //      JSFUtils.addFacesErrorMessage(e.getMessage());
        }
    }

    public static Integer updatePassWord(String username, char[] oldpass, char[] newpass) {
        try {
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            UserManager mn = is.getUserManager();
            User user = (User) is.searchUser(username);
            user.getUserProfile().setPassword(oldpass, newpass);
            return 1;
        } catch (JpsException e) {
            System.out.println("JpsException : "+e.getMessage());
            //   JSFUtils.addFacesErrorMessage(e.getMessage());
            return 2;
        } catch (IMException e) {
            System.out.println("IMException : "+e.getMessage());
            // ancien pass incorrecte     JSFUtils.addFacesErrorMessage(e.getMessage());
            return 3;
        }catch (Exception e) {
            System.out.println("Exception : "+e.getMessage());
            return 4;
        }
    }

    public void TestSplit(ActionEvent actionEvent) {
        // Add event code here...
        String login = "papabirame2.gning@ucad.edu.sn";
        //login = login.split("@")[0];
        String mat = "passer123";
        String matricule1 = "passer123";
        char [] password = new char[100];
        char [] newpassword = new char[100];
        String role = "Enseignant";
        /*for (int i=0; i< matricule.length() ; i++){
            password[i]  = matricule.charAt(i);
        }*/
        String sha1;
        //password = matricule.toCharArray();
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(mat.getBytes("UTF-8"), 0, mat.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
            password = sha1.toCharArray();
            System.out.println("hash : "+sha1);
            System.out.println("password : "+password);
            //System.out.println("login "+login+" password : "+password.toString()+" matricule : "+matricule+" matricule 1 : "+matricule);
            //createWlsUser(login, password,ps);
            System.out.println("################## creating user...");
            createWlsUser(login, password);
            System.out.println("################## creating role...");
            createWlsRole(role);
            System.out.println("################## assigning role to user...");
            assignWlsRole2User(role, login);
            //assignWlsRole2User("myrole", login);
            /*System.out.println("################## updating password...");
            updatePassWord(login, password, newpassword);*/
            System.out.println("################## revoking role to user...");
            revokeWlsRole2User("myrole", login);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            //Logger.getLogger(Encriptacion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
        }
        //crypt password to sha1
        /*for (int i=0; i< matricule1.length() ; i++){
            newpassword[i]  = matricule1.charAt(i);
        }*/
    }

    public void setPandetuserRole(RichPanelHeader pandetuserRole) {
        this.pandetuserRole = pandetuserRole;
    }

    public RichPanelHeader getPandetuserRole() {
        return pandetuserRole;
    }

    public void setDetroleuser(RichPanelCollection detroleuser) {
        this.detroleuser = detroleuser;
    }

    public RichPanelCollection getDetroleuser() {
        return detroleuser;
    }
    
    
    public void setPopupConfirmDelUserRole(RichPopup popupConfirmDelUserRole) {
        this.popupConfirmDelUserRole = popupConfirmDelUserRole;
    }

    public RichPopup getPopupConfirmDelUserRole() {
        return popupConfirmDelUserRole;
    }

    @SuppressWarnings("unchecked")
    public void ontestSelectedAction(ActionEvent actionEvent) {
        // Add event code here...        
        BindingContainer bindings = getBindings();
        ArrayList<Long> roles = new ArrayList<Long>();
        ArrayList<Long> users = new ArrayList<Long>();
        try {
            OperationBinding rolesSelected = bindings.getOperationBinding("getSelectedRole");
            roles = (ArrayList<Long>) rolesSelected.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
            users = (ArrayList<Long>) usersSelected.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (0 == users.size()) {
            sessionScope.put("msg", "Veuillez selectionner un utilisateur");
            RichPopup pop = this.getPopNotSelectedItems();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            pop.show(ph);
        } else if (0 == roles.size()) {
            sessionScope.put("msg", "Veuillez selectionner un rôle");
            RichPopup pop = this.getPopNotSelectedItems();
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            pop.show(ph);
        } else {
            for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                for (int rolecounter = 0; rolecounter < roles.size(); rolecounter++) {
                    try {
                        OperationBinding addRole = bindings.getOperationBinding("CreateOrUpdateUserRole");
                        addRole.getParamsMap().put("user_id", users.get(usercounter));
                        addRole.getParamsMap().put("role_id", roles.get(rolecounter));
                        addRole.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        Integer user_role_id = (Integer) addRole.execute();
                        if (0 != user_role_id) {
                            try {
                                OperationBinding opusername = bindings.getOperationBinding("getusername");
                                opusername.getParamsMap().put("user_id", users.get(usercounter));
                                String username = (String) opusername.execute();
                                OperationBinding opmat = bindings.getOperationBinding("getMatricule");
                                opmat.getParamsMap().put("utilisateur", users.get(usercounter));
                                String matricule = (String) opmat.execute();
                                //matricule = "passer123*";
                                OperationBinding oprole = bindings.getOperationBinding("getrole");
                                oprole.getParamsMap().put("role_id", roles.get(rolecounter));
                                String role = (String) oprole.execute();
                                if (null != role && null != username && null != matricule) {
                                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                                    try {
                                        createWlsUser(username, matricule.toCharArray());
                                        assignWlsRole2User(role, username);
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
            }
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            /*this.getIs_user().setValue(false);
            this.getIs_role().setValue(false);*/
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserPanHdr());            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserPanCol());            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserTable());    
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRolePanHdr());            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRolePanCol());            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getRoleTable());          
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserRoleTable());
        }
        //Add partialtrigger to refresh userRoleTables
    }

    @SuppressWarnings("unchecked")
    public void onRevoqueRoleUserAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterUserRole = (DCIteratorBinding) bindings.get("UtilisateursRolesROVOIterator");
            Row current = iterUserRole.getCurrentRow();
            try {
                //Delete user role
                OperationBinding opDelUrole = bindings.getOperationBinding("DeleteUserRole");
                opDelUrole.getParamsMap().put("role_id", current.getAttribute("IdRole"));
                opDelUrole.getParamsMap().put("user_id", current.getAttribute("IdUtilisateur"));
                opDelUrole.execute();
                // Delete weblogic role
                OperationBinding opUser = bindings.getOperationBinding("getusername");
                opUser.getParamsMap().put("user_id", current.getAttribute("IdUtilisateur"));
                String username = (String)opUser.execute();
                OperationBinding opRole = bindings.getOperationBinding("getrole");
                opRole.getParamsMap().put("role_id", current.getAttribute("IdRole"));
                String role = (String)opRole.execute();
                if (null != role && null != username) {
                    role = role.substring(0, 1) + role.substring(1).toLowerCase();
                    try {
                        revokeWlsRole2User(role, username);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //Refresh table
            DCIteratorBinding iterBind = (DCIteratorBinding)getBindings().get("UtilisateursRolesROVOIterator");
            ViewObject vo=iterBind.getViewObject();  
            vo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserRoleTable());
        }
    }

    public void setUserRoleTable(RichTable userRoleTable) {
        this.userRoleTable = userRoleTable;
    }

    public RichTable getUserRoleTable() {
        return userRoleTable;
    }

    public void setPopNotSelectedItems(RichPopup popNotSelectedItems) {
        this.popNotSelectedItems = popNotSelectedItems;
    }

    public RichPopup getPopNotSelectedItems() {
        return popNotSelectedItems;
    }

    public void setMat_(RichInputText mat_) {
        this.mat_ = mat_;
    }

    public RichInputText getMat_() {
        return mat_;
    }

    public void setSearch_choice(RichSelectOneRadio search_choice) {
        this.search_choice = search_choice;
    }

    public RichSelectOneRadio getSearch_choice() {
        return search_choice;
    }

    public void onChoiceChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setIs_user(RichSelectBooleanCheckbox is_user) {
        this.is_user = is_user;
    }

    public RichSelectBooleanCheckbox getIs_user() {
        return is_user;
    }

    public void setIs_role(RichSelectBooleanCheckbox is_role) {
        this.is_role = is_role;
    }

    public RichSelectBooleanCheckbox getIs_role() {
        return is_role;
    }

    public void onUserSelected(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("UtilisateurStructureIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isUserSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserTable());
    }

    public void onRoleSelected(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RoleAAssignerIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isRoleSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getRoleTable());
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

    public void setUserPanHdr(RichPanelHeader userPanHdr) {
        this.userPanHdr = userPanHdr;
    }

    public RichPanelHeader getUserPanHdr() {
        return userPanHdr;
    }

    public void setRoleTable(RichTable roleTable) {
        this.roleTable = roleTable;
    }

    public RichTable getRoleTable() {
        return roleTable;
    }

    public void setRolePanCol(RichPanelCollection rolePanCol) {
        this.rolePanCol = rolePanCol;
    }

    public RichPanelCollection getRolePanCol() {
        return rolePanCol;
    }

    public void setRolePanHdr(RichPanelHeader rolePanHdr) {
        this.rolePanHdr = rolePanHdr;
    }

    public RichPanelHeader getRolePanHdr() {
        return rolePanHdr;
    }
    
    //Permet de cocher ou decocher le "cocher/decocher tout"
    public void initTopUser(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("UtilisateurStructureIterator");
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
                    //this.getIs_user().setValue(false);
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_user().setValue(is_);
    }
    public void initTopRole(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("RoleAAssignerIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isRoleSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isRoleSelected")){
                if(nextRow.getAttribute("isRoleSelected").equals(false)){
                    //this.getIs_user().setValue(false);
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_role().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initUser(){
        if (null != this.getIs_user().getValue()) {
            if (this.getIs_user().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("UtilisateurStructureIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isUserSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRoleTable());
            }
        }
    }
    public void initRole(){
        if (null != this.getIs_role().getValue()) {
            if (this.getIs_role().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("RoleAAssignerIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isRoleSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRolePanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRoleTable());
            } 
        }
    }

    public void testUpdatePass(ActionEvent actionEvent) {
        // Add event code here...
        String oldP = "106290/N";
        String newP = "pas";
        try{
        updatePassWord("adama1.ka", oldP.toCharArray(), newP.toCharArray());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void onChangePass(ActionEvent actionEvent) {
        //BindingContainer bindings = getBindings();
        Boolean matchAnc,matchNew, matchConfirm = false;
        String username = null;
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        username = secCntx.getUserPrincipal().getName().toString();
        Matcher matcher = patternPwd.matcher(getAncpw());
        matchAnc = matcher.matches();
        if (matchAnc) {
            matcher = patternPwd.matcher(getNewpw());
            matchNew = matcher.matches();
            matcher = patternPwd.matcher(getConfirmpw());
            matchConfirm = matcher.matches();
            if (matchNew && matchConfirm ) {
                if (this.getNewpw().equals(this.getConfirmpw())) {
                    Integer result = 0;
                    try {
                        result = updatePassWord(username, this.getAncpw().toCharArray(), this.getNewpw().toCharArray());
                        if (1 == result) {
                        } else if (2 == result) {
                        } else if (3 == result) {
                            sessionScope.put("message_", "Ancien mot de passe incorrecte !");
                        } 
                        else if (4 == result) {
                        }
                        
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }else{
                    sessionScope.put("message_", "La confirmation de mot de passe est incorrecte !");
                    this.setNewpw(null);
                    this.setConfirmpw(null);
                }
            } else {
                sessionScope.put("message_", "Saisir un mot de passe d'au moins 8 caractére composé de lettres, chiffres et caractére alpha numérique(!@#&?/*~$) !");
                this.setNewpw(null);
                this.setConfirmpw(null);
            }
        } else {
            sessionScope.put("message_", "Revoir votre ancien passe !");
            this.setAncpw(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanRoot()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanMessage());                    
        
    }

    public void setAnc_pass(RichInputText anc_pass) {
        this.anc_pass = anc_pass;
    }

    public RichInputText getAnc_pass() {
        return anc_pass;
    }

    public void setNew_pass(RichInputText new_pass) {
        this.new_pass = new_pass;
    }

    public RichInputText getNew_pass() {
        return new_pass;
    }

    public void setConfirm_pass(RichInputText confirm_pass) {
        this.confirm_pass = confirm_pass;
    }

    public RichInputText getConfirm_pass() {
        return confirm_pass;
    }

    public void setAncpw(String ancpw) {
        this.ancpw = ancpw;
    }

    public String getAncpw() {
        return ancpw;
    }

    public void setNewpw(String newpw) {
        this.newpw = newpw;
    }

    public String getNewpw() {
        return newpw;
    }

    public void setConfirmpw(String confirmpw) {
        this.confirmpw = confirmpw;
    }

    public String getConfirmpw() {
        return confirmpw;
    }

    public void setPopPassIncorrect(RichPopup popPassIncorrect) {
        this.popPassIncorrect = popPassIncorrect;
    }

    public RichPopup getPopPassIncorrect() {
        return popPassIncorrect;
    }

    public void setPanMessage(RichPanelGroupLayout panMessage) {
        this.panMessage = panMessage;
    }

    public RichPanelGroupLayout getPanMessage() {
        return panMessage;
    }

    public void setPanRoot(RichPanelGroupLayout panRoot) {
        this.panRoot = panRoot;
    }

    public RichPanelGroupLayout getPanRoot() {
        return panRoot;
    }

    public void onCreateUserAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateUser");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        RichPopup popup = this.getPopAddUser();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopAddUser(RichPopup popAddUser) {
        this.popAddUser = popAddUser;
    }

    public RichPopup getPopAddUser() {
        return popAddUser;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmAddUser(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        this.getPopAddUser().hide();
        if (outcome == Outcome.ok) {
            String password = "passer123*";
            BindingContainer bindings = this.getBindings();
            Boolean matchEmailInst,matchEmail = false;
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
            Row current = dciter.getCurrentRow();
            String emailInst = current.getAttribute("EmailInstitutionnel").toString();
            System.out.println("emailInst : "+emailInst);
            Matcher matcherInst = patternEmail.matcher(emailInst);
            matchEmailInst = matcherInst.matches();
            Matcher matcher = patternEmail.matcher(current.getAttribute("EmailPersonnel").toString());
            matchEmail = matcher.matches();
            if (matchEmailInst && matchEmail) {
                System.out.println("mutch");
                OperationBinding opIsUserExiste = bindings.getOperationBinding("isUserExiste");
                opIsUserExiste.getParamsMap().put("email_inst", emailInst);
                opIsUserExiste.execute();
                DCIteratorBinding iterUser = (DCIteratorBinding) bindings.get("isUserExisteROVO1Iterator");
                System.out.println("size0 : "+iterUser.getEstimatedRowCount());
                if(0 == iterUser.getEstimatedRowCount()){
                    try{
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        Object result = operationBinding.execute();
                        //createWlsUser(current.getAttribute("EmailInstitutionnel").toString(), password.toCharArray());
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    try{
                        opIsUserExiste = bindings.getOperationBinding("isUserExiste");
                        opIsUserExiste.getParamsMap().put("email_inst", emailInst);
                        opIsUserExiste.execute();
                        iterUser = (DCIteratorBinding) bindings.get("isUserExisteROVO1Iterator");
                        System.out.println("size1 : "+iterUser.getEstimatedRowCount());
                        if(0 != iterUser.getEstimatedRowCount()){
                            current = iterUser.getCurrentRow();
                            try{
                                OperationBinding opuser = bindings.getOperationBinding("createUser");
                                opuser.getParamsMap().put("login", current.getAttribute("EmailInstitutionnel").toString());
                                opuser.getParamsMap().put("str_id", getStr());
                                opuser.getParamsMap().put("p_id", Long.parseLong(current.getAttribute("IdPersonne").toString()));
                                opuser.getParamsMap().put("utilisateur", getUtilisateur());
                                Long id_user = (Long) opuser.execute();
                                System.out.println("id_user : "+id_user+" email : "+current.getAttribute("EmailInstitutionnel").toString());
                                
                                if(0 != id_user){
                                    createWlsUser(current.getAttribute("EmailInstitutionnel").toString(), password.toCharArray());
                                    RichPopup popup = this.getPopupAddUserOk();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    popup.show(hints);
                                }else{
                                    RichPopup popup = this.getPopupAddUserErr();
                                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                    popup.show(hints);
                                }
                            }catch(Exception e){
                                System.out.println(e);
                            }
                        }
                        //createWlsUser(current.getAttribute("EmailInstitutionnel").toString(), password.toCharArray());
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }else{
                    /*dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
                    dciter.removeCurrentRow();
                    ADFContext adfCtx = ADFContext.getCurrent();
                    String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
                    dciter.setCurrentRowWithKey(oldCurrentRowKey);
                    try{
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    }catch(Exception e){
                        System.out.println(e);
                    }*/
                    createWlsUser(current.getAttribute("EmailInstitutionnel").toString(), password.toCharArray());
                    /*RichPopup popup = this.getPopupUserExiste();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);*/
                    sessionScope.put("err_email_exist", "Cet utilisateur existe déja utilisé !");
                    RichPopup popup = this.getPopAddUser();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                sessionScope.put("err_email", null);
                //this.getPopAddUser().hide();
            }else{
                //si y'a erreur relancer le popup d'ajout
                sessionScope.put("err_email", "Entrer un email valide");
                RichPopup popup = this.getPopAddUser();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            DCIteratorBinding iterBind = (DCIteratorBinding)bindings.get("UtilisateursROVOIterator");
            ViewObject vo=iterBind.getViewObject();  
            vo.executeQuery();
        }
    }

    public void onCancelUserAdd(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        RichPopup popup = this.getPopAddUser();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PersonnesIterator");
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getFormationsPanCollect());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setStr(Long str) {
        this.str = str;
    }

    public Long getStr() {
        return str;
    }

    public void setPopupAddUserOk(RichPopup popupAddUserOk) {
        this.popupAddUserOk = popupAddUserOk;
    }

    public RichPopup getPopupAddUserOk() {
        return popupAddUserOk;
    }

    public void setPopupAddUserErr(RichPopup popupAddUserErr) {
        this.popupAddUserErr = popupAddUserErr;
    }

    public RichPopup getPopupAddUserErr() {
        return popupAddUserErr;
    }

    public void setPopupUserExiste(RichPopup popupUserExiste) {
        this.popupUserExiste = popupUserExiste;
    }

    public RichPopup getPopupUserExiste() {
        return popupUserExiste;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
