package view.beans.paramSup.accesformation;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;

import view.beans.admin.AdministrationBean;

public class NiveauAccesBean {
    private RichSelectOneChoice inputStructure;
    private RichOutputFormatted idStructure;
    private RichPopup pupupNotUserDetails;
    private RichPopup popupNotNivDetails;
    private RichTable niveauxFormationTable;
    private RichPanelGroupLayout utiAccessTable;
    private RichSelectOneChoice inputSpecialite;
    private RichSelectOneChoice inputOption;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer hs_id = Integer.parseInt(sessionScope.get("id_hs").toString());
    private RichSelectOneChoice inputNivForm;
    private RichTable utiNivForm;
    private RichSelectOneRadio roleselected;
    private RichPopup notNivyet;
    private RichPopup popupUpdateRole;
    private RichPopup popupUpdateResponsable;
    private RichPopup popupRoleAllowed;

    @SuppressWarnings("unchecked")
    public NiveauAccesBean() {
        /* OperationBinding lesformations = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("GetFormations");
        lesformations.getParamsMap().put("s_id", 1);
        lesformations.getParamsMap().put("annee", 2);
        lesformations.execute();*/
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnStructureValueChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdStructure");
        Integer Id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        //System.out.println("Id- " + Id);
        OperationBinding lesEtudiants = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetUserByStructure");
        //lesEtudiants.getParamsMap().put("s_id", Id);
        lesEtudiants.execute();

    }

    public void setInputStructure(RichSelectOneChoice inputStructure) {
        this.inputStructure = inputStructure;
    }

    public RichSelectOneChoice getInputStructure() {
        return inputStructure;
    }

    public void setIdStructure(RichOutputFormatted idStructure) {
        this.idStructure = idStructure;
    }

    public RichOutputFormatted getIdStructure() {
        return idStructure;
    }

    @SuppressWarnings("unchecked")
    public void AllowedAccesTo(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        //get current row and save its rowKey in view scope for later use
        String specialite = this.getInputSpecialite()
                                .getValue()
                                .toString();
        //String option = this.getInputOption().getValue().toString();
        //System.out.println("Specialité : " + specialite);
        try {
            // Recupére les utilisateur selectionner
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
            ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();

            if (users.size() != 0) {
                // Pour chaque utilisateur lui accorder les accees choisies
                OperationBinding niveauxSelected = bindings.getOperationBinding("getSelectedNiveauFormations");
                ArrayList<Long> niveaux = (ArrayList<Long>) niveauxSelected.execute();
                if (niveaux.size() != 0) {
                    for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                        for (int nivcounter = 0; nivcounter < niveaux.size(); nivcounter++) {
                            OperationBinding isAllowAcces = bindings.getOperationBinding("IsNivFormAccesAllowed");
                            //isAllowAcces.getParamsMap().put("s", 1);
                            isAllowAcces.getParamsMap().put("uti", users.get(usercounter));
                            isAllowAcces.getParamsMap().put("niv", niveaux.get(nivcounter));
                            //isAllowAcces.getParamsMap().put("annee", getAnne_univers());

                            Object resul = isAllowAcces.execute();
                            DCIteratorBinding iter =
                                (DCIteratorBinding) BindingContext.getCurrent()
                                                                                       .getCurrentBindingsEntry()
                                                                                       .get("IsNivFormAccesAllowedIterator");
                            Row crnt = iter.getCurrentRow();
                            if (crnt == null) {
                                OperationBinding opAllowAcces = bindings.getOperationBinding("AllowedAccessTo");
                                opAllowAcces.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                                opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                opAllowAcces.getParamsMap().put("IdNiveauFormation", niveaux.get(nivcounter));
                                //recuperer la spécialité et l'option s'il y en a
                                opAllowAcces.getParamsMap().put("IdNiveauFormationSpecialite", null);
                                opAllowAcces.getParamsMap().put("IdNivFormSpecOption", null);
                                resul = opAllowAcces.execute();
                            }
                        }
                    }
                    // Tout ce passe bien On commit
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    Object result = operationCommit.execute();

                    // Réexecuter les VO pour actualiser les tableaux
                    OperationBinding lesNiveaux = BindingContext.getCurrent()
                                                                .getCurrentBindingsEntry()
                                                                .getOperationBinding("GetFormations");
                    //lesNiveaux.getParamsMap().put("annee", getAnne_univers());
                    //lesNiveaux.getParamsMap().put("s_id", 1);
                    lesNiveaux.execute();

                    OperationBinding lesAcces = BindingContext.getCurrent()
                                                              .getCurrentBindingsEntry()
                                                              .getOperationBinding("GetUtilisateurNivForm");
                    //lesAcces.getParamsMap().put("s_id", 1);
                    //lesAcces.getParamsMap().put("id_annee", getAnne_univers());
                    lesAcces.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getNiveauxFormationTable());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccessTable());
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtudiantTable());

                } else {
                    //Si aucun niveau de formation n'est selectionné
                    RichPopup popup = this.getPopupNotNivDetails();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } else {
                // Si aucun utilisateur n'est selectionner
                RichPopup popup = this.getPupupNotUserDetails();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPupupNotUserDetails(RichPopup pupupNotUserDetails) {
        this.pupupNotUserDetails = pupupNotUserDetails;
    }

    public RichPopup getPupupNotUserDetails() {
        return pupupNotUserDetails;
    }

    public void setPopupNotNivDetails(RichPopup popupNotNivDetails) {
        this.popupNotNivDetails = popupNotNivDetails;
    }

    public RichPopup getPopupNotNivDetails() {
        return popupNotNivDetails;
    }

    public void setNiveauxFormationTable(RichTable niveauxFormationTable) {
        this.niveauxFormationTable = niveauxFormationTable;
    }

    public RichTable getNiveauxFormationTable() {
        return niveauxFormationTable;
    }

    public void setUtiAccessTable(RichPanelGroupLayout utiAccessTable) {
        this.utiAccessTable = utiAccessTable;
    }

    public RichPanelGroupLayout getUtiAccessTable() {
        return utiAccessTable;
    }

    @SuppressWarnings("unchecked")
    public void OnSpecialitevalueChange(ValueChangeEvent valueChangeEvent) {
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        //get current row and save its rowKey in view scope for later use
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("SpecialiteROVO1Iterator");
        Row currentSpecialite = dciter.getCurrentRow();
        //System.out.print("XXXX " + currentSpecialite.getAttribute("IdSpecialite"));

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("GetOptionBySpecialite");
        operationBinding.getParamsMap().put("sp", currentSpecialite.getAttribute("IdNiveauFormationSpecialite"));
        Object result = operationBinding.execute();
    }

    public void setInputSpecialite(RichSelectOneChoice inputSpecialite) {
        this.inputSpecialite = inputSpecialite;
    }

    public RichSelectOneChoice getInputSpecialite() {
        return inputSpecialite;
    }

    public void setInputOption(RichSelectOneChoice inputOption) {
        this.inputOption = inputOption;
    }

    public RichSelectOneChoice getInputOption() {
        return inputOption;
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

    @SuppressWarnings("unchecked")
    public void OnUsersValueChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Integer IdUser = 0;
        Integer IdDept = 0;
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdUtilisateur");
        AttributeBinding depIdBinding = (AttributeBinding) container.getControlBinding("IdHistoriquesStructure");
        if(!(attrIdBinding.getInputValue().equals(null))){
            IdUser = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        }
        if(!(depIdBinding.getInputValue().equals(null))){
            IdDept = (Integer.parseInt(depIdBinding.getInputValue().toString()));
        }
        //System.out.println("Id uder : "+IdUser+" Id dept : "+IdDept);
        OperationBinding lesNivform = bindings.getOperationBinding("GetNiveauFormation");
        //lesNivform.getParamsMap().put("annee", getAnne_univers());
        lesNivform.getParamsMap().put("s_id", IdDept);
        lesNivform.getParamsMap().put("id_user", IdUser);
        lesNivform.execute();

        OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
        //lesUserNivform.getParamsMap().put("id_annee", getAnne_univers());
        //lesUserNivform.getParamsMap().put("id_hs", getHs_id());
        lesUserNivform.getParamsMap().put("id_user", IdUser);
        lesUserNivform.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());
    }

    public void OnNiveauFormationValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setInputNivForm(RichSelectOneChoice inputNivForm) {
        this.inputNivForm = inputNivForm;
    }

    public RichSelectOneChoice getInputNivForm() {
        return inputNivForm;
    }

    public void setHs_id(Integer hs_id) {
        this.hs_id = hs_id;
    }

    public Integer getHs_id() {
        return hs_id;
    }

    @SuppressWarnings("unchecked")
    public void OnSaveNiveauFormationAcces(ActionEvent actionEvent) {
        Integer idSpec = null;
        Integer idSpecOpt = null;
        String operation = null;
        String iter = null;
        String operationisresp = null;
        String iterisresp = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterUser = (DCIteratorBinding) BindingContext.getCurrent()
                                                                       .getCurrentBindingsEntry()
                                                                       .get("UtilisateursROVO1Iterator");
        Row currentuser = iterUser.getCurrentRow();
        Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
        //System.out.println("User id : " + currentuser.getAttribute("IdUtilisateur").toString());
        DCIteratorBinding iterNiv = (DCIteratorBinding) BindingContext.getCurrent()
                                                                      .getCurrentBindingsEntry()
                                                                      .get("AccesNiveauFormationIterator");
        Row currentniv = iterNiv.getCurrentRow();
        if (currentniv != null) {
            operation = "IsNivFormAccesAllowed";
            iter = "isNivFormAccessAllowedIterator";
            operationisresp = "isRespNivFormExist";
            iterisresp = "isRespNivFormExistIterator";
            //System.out.println("Niv id : "+currentniv.getAttribute("IdNiveauFormation").toString());
            DCIteratorBinding iterSpec = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("NivFormSpecialiteROVOIterator");
            Row currentSpec = iterSpec.getCurrentRow();
            if (currentSpec != null) {
                idSpec = Integer.parseInt(currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                operation = "IsNivFormSpecAccesAllowed";
                iter = "isNivFormSpecAccessAllowedIterator";
                operationisresp = "isRespNivFormSpecExist";
                iterisresp = "isRespNivFormSpecExistIterator";
                //System.out.println("Spec id : "+currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                DCIteratorBinding iterOpt = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("NivFormSpecOptionROVOIterator");
                Row currentOpt = iterOpt.getCurrentRow();
                if (currentOpt != null) {
                    idSpecOpt = Integer.parseInt(currentOpt.getAttribute("IdNivFormSpecOption").toString());
                    operation = "IsNivFormSpecOptAccesAllowed";
                    iter = "isNivFormSpecOptAccessAllowedIterator";
                    operationisresp = "isRespNivFormSpecOptExist";
                    iterisresp = "isRespNivFormSpecOptExistIterator";
                    //System.out.println("Opt id : "+currentOpt.getAttribute("IdNivFormSpecOption").toString());
                } 
            } 
        } else {
            RichPopup popup = this.getNotNivyet();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        //Verify if acces is allowed
        OperationBinding isallowacces = bindings.getOperationBinding(operation);
        /*       Integer
         Integer*/
        isallowacces.getParamsMap()
            .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
        isallowacces.getParamsMap().put("id_user", IdUser);
        //isallowacces.getParamsMap().put("id_annee", getAnne_univers());
        /*if (idSpec != null) {
            isallowacces.getParamsMap().put("id_spec", idSpec);
        }
        if (idSpecOpt != null) {
            isallowacces.getParamsMap().put("id_op", idSpecOpt);
        }*/
        isallowacces.execute();

        DCIteratorBinding iteraccess = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get(iter);
        Row current = iteraccess.getCurrentRow();
        if (0 == iteraccess.getEstimatedRowCount()) {
            if (!(this.getRoleselected().getValue().toString().equals("ACCES SIMPLE"))) {
                //verify if responsable is defined
                //OperationBinding isrespexist = bindings.getOperationBinding(operationisresp);
                OperationBinding isrespexist = bindings.getOperationBinding("isRespNivFormExist");
                isrespexist.getParamsMap()
                    .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                //isallowacces.getParamsMap().put("id_niv_fr", IdUser);
                //isrespexist.getParamsMap().put("id_annee", getAnne_univers());
                
                //If Responsable is for spécialité and option
                /*if (idSpec != null) {
                    isrespexist.getParamsMap().put("id_spec", idSpec);
                }
                if (idSpecOpt != null) {
                    isrespexist.getParamsMap().put("id_op", idSpecOpt);
                }*/
                isrespexist.execute();
                
                /*DCIteratorBinding iterResp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get(iterisresp);*/
                DCIteratorBinding iterResp = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry()
                                                                                            .get("isRespNivFormExistIterator");
                Row currentResp = iterResp.getCurrentRow();
                if (currentResp == null) {
                    OperationBinding allowacces = bindings.getOperationBinding("AllowedAccessTo");
                    allowacces.getParamsMap()
                        .put("IdNiveauFormation",
                             Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                    allowacces.getParamsMap().put("IdUtilisateur", IdUser);
                    allowacces.getParamsMap().put("UtiCree", getUtilisateur());
                    allowacces.getParamsMap().put("Role", this.getRoleselected()
                                                              .getValue()
                                                              .toString());
                    allowacces.getParamsMap().put("IdNiveauFormationSpecialite", idSpec);
                    allowacces.getParamsMap().put("IdNivFormSpecOption", idSpecOpt);
                    allowacces.execute();
                    
                    OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                    allowuserRle.getParamsMap().put("user_id", IdUser);
                    allowuserRle.getParamsMap().put("role_id", new Long(62)); // Responsable niveau_formation
                    allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                    allowuserRle.execute();
                    //assign weblogic role
                    try {
                        OperationBinding opusername = bindings.getOperationBinding("getusername");
                        opusername.getParamsMap().put("user_id", new Long(IdUser));
                        String username = (String) opusername.execute();
                        OperationBinding oprole = bindings.getOperationBinding("getrole");
                        oprole.getParamsMap().put("role_id", new Long(62));
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
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                } else {
                    RichPopup popup = this.getPopupUpdateResponsable();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } else {
                OperationBinding allowacces = bindings.getOperationBinding("AllowedAccessTo");
                allowacces.getParamsMap()
                    .put("IdNiveauFormation",
                         Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                allowacces.getParamsMap().put("IdUtilisateur", IdUser);
                allowacces.getParamsMap().put("UtiCree", getUtilisateur());
                allowacces.getParamsMap().put("Role", this.getRoleselected()
                                                          .getValue()
                                                          .toString());
                allowacces.getParamsMap().put("IdNiveauFormationSpecialite", idSpec);
                allowacces.getParamsMap().put("IdNivFormSpecOption", idSpecOpt);
                allowacces.execute();
            }
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object result = operationCommit.execute();

            OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
            //lesUserNivform.getParamsMap().put("id_annee", getAnne_univers());
            //lesUserNivform.getParamsMap().put("id_hs", getHs_id());
            lesUserNivform.getParamsMap().put("id_user", IdUser);
            lesUserNivform.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());

        } else {
            sessionScope.put("uti_niv_fr", current.getAttribute("IdUtilisNiveauFormation"));
            if (!(this.getRoleselected().getValue().toString().equals("ACCES SIMPLE")) && (current.getAttribute("Role")
                                                          .toString().equals("ACCES SIMPLE"))) {
                RichPopup popup = this.getPopupUpdateRole();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }else{
                RichPopup popup = this.getPopupRoleAllowed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            
        }

    }

    public void onSpecChangeValue(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationSpecialite");
        Integer idSpec = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        //System.out.println("idSpec : " + idSpec);
    }

    public void setUtiNivForm(RichTable utiNivForm) {
        this.utiNivForm = utiNivForm;
    }

    public RichTable getUtiNivForm() {
        return utiNivForm;
    }

    public void setRoleselected(RichSelectOneRadio roleselected) {
        this.roleselected = roleselected;
    }

    public RichSelectOneRadio getRoleselected() {
        return roleselected;
    }

    public void setNotNivyet(RichPopup notNivyet) {
        this.notNivyet = notNivyet;
    }

    public RichPopup getNotNivyet() {
        return notNivyet;
    }

    @SuppressWarnings("unchecked")
    public void OnChangeRoleClicked(DialogEvent dialogEvent) {
        Integer idSpec = null;
        Integer idSpecOpt = null;
        String operationisresp = null;
        String iterisresp = null;
        this.getPopupUpdateRole().hide();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            //verified if responsable exist
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterUser = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("UtilisateursIterator");
            Row currentuser = iterUser.getCurrentRow();
            Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
            //System.out.println("User id : " + currentuser.getAttribute("IdUtilisateur").toString());
            DCIteratorBinding iterNiv = (DCIteratorBinding) BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .get("AccesNiveauFormationIterator");
            Row currentniv = iterNiv.getCurrentRow();
            operationisresp = "isRespNivFormExist";
            iterisresp = "isRespNivFormExistIterator";
            //System.out.println("Niv id : "+currentniv.getAttribute("IdNiveauFormation").toString());
            DCIteratorBinding iterSpec = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("NivFormSpecialiteROVOIterator");
            Row currentSpec = iterSpec.getCurrentRow();
            if (currentSpec != null) {
                idSpec = Integer.parseInt(currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                operationisresp = "isRespNivFormSpecExist";
                iterisresp = "isRespNivFormSpecExistIterator";
                //System.out.println("Spec id : "+currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                DCIteratorBinding iterOpt = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("NivFormSpecOptionROVOIterator");
                Row currentOpt = iterOpt.getCurrentRow();
                if (currentOpt != null) {
                    idSpecOpt = Integer.parseInt(currentOpt.getAttribute("IdNivFormSpecOption").toString());
                    operationisresp = "isRespNivFormSpecOptExist";
                    iterisresp = "isRespNivFormSpecOptExistIterator";
                    //System.out.println("Opt id : "+currentOpt.getAttribute("IdNivFormSpecOption").toString());
                }
            }
            //OperationBinding isrespexist = bindings.getOperationBinding(operationisresp);
            OperationBinding isrespexist = bindings.getOperationBinding("isRespNivFormExist");
            isrespexist.getParamsMap()
                .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
            //isallowacces.getParamsMap().put("id_niv_fr", IdUser);
            //isrespexist.getParamsMap().put("id_annee", getAnne_univers());
            
            //If Responsable is to Specialite or Option
            /*if (idSpec != null) {
                isrespexist.getParamsMap().put("id_spec", idSpec);
            }
            if (idSpecOpt != null) {
                isrespexist.getParamsMap().put("id_op", idSpecOpt);
            }*/
            isrespexist.execute();
            /* DCIteratorBinding iterResp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get(iterisresp); */
            DCIteratorBinding iterResp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("isRespNivFormExistIterator");
            Row currentResp = iterResp.getCurrentRow();
            if (currentResp == null) {
                //get_uti_niv_form
                OperationBinding getUtiNivForm = bindings.getOperationBinding("IsNivFormAccesAllowed");
                getUtiNivForm.getParamsMap()
                    .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                getUtiNivForm.getParamsMap().put("id_user", IdUser);
                //getUtiNivForm.getParamsMap().put("id_annee", getAnne_univers());
                getUtiNivForm.execute();
                DCIteratorBinding iterUti = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("isNivFormAccessAllowedIterator");
                Row currentUti = iterUti.getCurrentRow();
                OperationBinding opupdate = bindings.getOperationBinding("UpdateRoleNivFormation");
                opupdate.getParamsMap()
                    .put("idNivFormation", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                opupdate.getParamsMap().put("idUser", IdUser);
                opupdate.getParamsMap().put("role", this.getRoleselected()
                                                        .getValue()
                                                        .toString());
                opupdate.getParamsMap().put("utimodif", getUtilisateur());
                opupdate.getParamsMap()
                    .put("id_uti_niv_formation",
                         //Integer.parseInt(currentUti.getAttribute("IdUtilisNiveauFormation").toString())
                         Integer.parseInt(sessionScope.get("uti_niv_fr").toString())
                         ); 
                Object up = opupdate.execute();
                sessionScope.remove("uti_niv_fr");
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();

                OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
                //lesUserNivform.getParamsMap().put("id_annee", getAnne_univers());
                lesUserNivform.getParamsMap().put("id_user", IdUser);
                lesUserNivform.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());
            } else {
                RichPopup popup = this.getPopupUpdateResponsable();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }else{
            sessionScope.remove("uti_niv_fr");
        }
    }

    public void setPopupUpdateRole(RichPopup popupUpdateRole) {
        this.popupUpdateRole = popupUpdateRole;
    }

    public RichPopup getPopupUpdateRole() {
        return popupUpdateRole;
    }

    public void setPopupUpdateResponsable(RichPopup popupUpdateResponsable) {
        this.popupUpdateResponsable = popupUpdateResponsable;
    }

    public RichPopup getPopupUpdateResponsable() {
        return popupUpdateResponsable;
    }

    @SuppressWarnings("unchecked")
    public void OnChangeRespClcked(DialogEvent dialogEvent) {
        this.getPopupUpdateResponsable().hide();
        Integer idSpec = null;
        Integer idSpecOpt = null;
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterUser = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("UtilisateursROVO1Iterator");
            Row currentuser = iterUser.getCurrentRow();
            Integer IdUser = Integer.parseInt(currentuser.getAttribute("IdUtilisateur").toString());
            //System.out.println("Current User : " + currentuser.getAttribute("IdUtilisateur").toString());
            DCIteratorBinding iterNiv = (DCIteratorBinding) BindingContext.getCurrent()
                                                                          .getCurrentBindingsEntry()
                                                                          .get("AccesNiveauFormationIterator");
            Row currentniv = iterNiv.getCurrentRow();
            //System.out.println("Current Niv : " + currentniv.getAttribute("IdNiveauFormation").toString());
            //If User have acces Updated it
            //Update Responsable to Simple for other user

            //Get Old_Resp
            OperationBinding old_resp = bindings.getOperationBinding("isRespNivFormExist");
            old_resp.getParamsMap()
                .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
            //old_resp.getParamsMap().put("id_annee", getAnne_univers());
            old_resp.execute();

            DCIteratorBinding iterOld_Resp = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("isRespNivFormExistIterator");
            Row oldResp = iterOld_Resp.getCurrentRow();
            //System.out.println("Old Responsable " + oldResp.getAttribute("IdUtilisateur").toString());
            //Update role to SIMPLE
            if (Integer.parseInt(oldResp.getAttribute("IdUtilisateur").toString()) != IdUser) {
                //System.out.println("Updating Because OLD resp != new Resp");
                OperationBinding opupdateold = bindings.getOperationBinding("UpdateRoleNivFormation");
                opupdateold.getParamsMap()
                    .put("idNivFormation", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                opupdateold.getParamsMap()
                    .put("idUser", Integer.parseInt(oldResp.getAttribute("IdUtilisateur").toString()));
                opupdateold.getParamsMap().put("role", "ACCES SIMPLE");
                opupdateold.getParamsMap().put("utimodif", getUtilisateur());
                opupdateold.getParamsMap()
                    .put("id_uti_niv_formation",
                         Integer.parseInt(oldResp.getAttribute("IdUtilisNiveauFormation").toString()));
                Object up = opupdateold.execute();
                
                //Delete roleResponsable
                OperationBinding deleteuserRle = bindings.getOperationBinding("DeleteUserRole");
                deleteuserRle.getParamsMap().put("user_id", IdUser);
                deleteuserRle.getParamsMap().put("role_id", new Long(62)); // Responsable niveau_formation
                deleteuserRle.execute();
                //GetUtiNivFormAcces
                OperationBinding getUtiNivForm = bindings.getOperationBinding("IsNivFormAccesAllowed");
                getUtiNivForm.getParamsMap()
                    .put("id_niv_fr", Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                getUtiNivForm.getParamsMap().put("id_user", IdUser);
                //getUtiNivForm.getParamsMap().put("id_annee", getAnne_univers());
                getUtiNivForm.execute();
                DCIteratorBinding iterUti = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("isNivFormAccessAllowedIterator");
                Row currentUti = iterUti.getCurrentRow();
                if (currentUti == null) { //User has not role
                    //Get Specialite and option
                    DCIteratorBinding iterSpec = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                .get("NivFormSpecialiteROVOIterator");
                    Row currentSpec = iterSpec.getCurrentRow();
                    if (currentSpec != null) {
                        idSpec = Integer.parseInt(currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                        //System.out.println("Spec id : "+currentSpec.getAttribute("IdNiveauFormationSpecialite").toString());
                        DCIteratorBinding iterOpt = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                      .getCurrentBindingsEntry()
                                                                                      .get("NivFormSpecOptionROVOIterator");
                        Row currentOpt = iterOpt.getCurrentRow();
                        if (currentOpt != null) {
                            idSpecOpt = Integer.parseInt(currentOpt.getAttribute("IdNivFormSpecOption").toString());
                            }
                        }
                    //Add role responsable to this
                    OperationBinding allowacces = bindings.getOperationBinding("AllowedAccessTo");
                    allowacces.getParamsMap()
                        .put("IdNiveauFormation",
                             Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                    allowacces.getParamsMap().put("IdUtilisateur", IdUser);
                    allowacces.getParamsMap().put("UtiCree", getUtilisateur());
                    allowacces.getParamsMap().put("Role", "RESPONSABLE NIVEAU");
                    allowacces.getParamsMap().put("IdNiveauFormationSpecialite", idSpec);
                    allowacces.getParamsMap().put("IdNivFormSpecOption", idSpecOpt);
                    allowacces.execute();
                    
                    OperationBinding allowuserRle = bindings.getOperationBinding("CreateOrUpdateUserRole");
                    allowuserRle.getParamsMap().put("user_id", IdUser);
                    allowuserRle.getParamsMap().put("role_id", new Long(62)); // Responsable niveau_formation
                    allowuserRle.getParamsMap().put("utilisateur", getUtilisateur());
                    allowuserRle.execute();
                } else {
                    OperationBinding opupdate = bindings.getOperationBinding("UpdateRoleNivFormation");
                    opupdate.getParamsMap()
                        .put("idNivFormation",
                             Integer.parseInt(currentniv.getAttribute("IdNiveauFormation").toString()));
                    opupdate.getParamsMap().put("idUser", IdUser);
                    opupdate.getParamsMap().put("role", "RESPONSABLE NIVEAU");
                    opupdate.getParamsMap().put("utimodif", getUtilisateur());
                    opupdate.getParamsMap()
                        .put("id_uti_niv_formation",
                             Integer.parseInt(currentUti.getAttribute("IdUtilisNiveauFormation").toString()));
                    opupdate.execute();
                }
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();

                OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
                //lesUserNivform.getParamsMap().put("id_annee", getAnne_univers());
                lesUserNivform.getParamsMap().put("id_user", IdUser);
                lesUserNivform.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());
            }
        }
    }

    public void setPopupRoleAllowed(RichPopup popupRoleAllowed) {
        this.popupRoleAllowed = popupRoleAllowed;
    }

    public RichPopup getPopupRoleAllowed() {
        return popupRoleAllowed;
    }

    @SuppressWarnings("unchecked")
    public void OnDeptChanged(SelectionEvent selectionEvent) {
        // Add event code here...
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        BindingContainer bindings = getBindings();
        Integer IdUser = 0;
        Integer IdDept = 0;
        AttributeBinding attrIdBinding = (AttributeBinding) bindings.getControlBinding("IdUtilisateur");
        AttributeBinding depIdBinding = (AttributeBinding) bindings.getControlBinding("IdHistoriquesStructure");
        
        if(!(attrIdBinding.getInputValue().equals(null))){
            IdUser =  (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        }
        if(!(depIdBinding.getInputValue().equals(null))){
            IdDept = (Integer.parseInt(depIdBinding.getInputValue().toString()));
        }
        //System.out.println("Id uder : "+IdUser+" Id dept : "+IdDept);
        try{
            OperationBinding lesNivform = bindings.getOperationBinding("GetNiveauFormation");
            //lesNivform.getParamsMap().put("annee", getAnne_univers());
            lesNivform.getParamsMap().put("s_id", IdDept);
            lesNivform.getParamsMap().put("id_user", IdUser);
            lesNivform.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
            lesUserNivform.getParamsMap().put("id_user", IdUser);
            lesUserNivform.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());
    }

    @SuppressWarnings("unchecked")
    public void useraccesnivform(){
        BindingContainer bindings = getBindings();
        Integer IdUser = 0;
        Integer IdDept = 0;
        AttributeBinding depIdBinding = (AttributeBinding) bindings.getControlBinding("IdHistoriquesStructure");
        AttributeBinding attrIdBinding = (AttributeBinding) bindings.getControlBinding("IdUtilisateur");        
        if(!(depIdBinding.getInputValue().equals(null))){
            IdDept = (Integer.parseInt(depIdBinding.getInputValue().toString()));
        }
        if(!(null == attrIdBinding.getInputValue())){
            IdUser =  (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        }
        try{
            OperationBinding lesNivform = bindings.getOperationBinding("GetNiveauFormation");
            //lesNivform.getParamsMap().put("annee", getAnne_univers());
            lesNivform.getParamsMap().put("s_id", IdDept);
            lesNivform.getParamsMap().put("id_user", IdUser);
            lesNivform.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            OperationBinding lesUserNivform = bindings.getOperationBinding("GetUtilisateurNivForm");
            lesUserNivform.getParamsMap().put("id_user", IdUser);
            lesUserNivform.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNivForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiAccessTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUtiNivForm());
    }
}
