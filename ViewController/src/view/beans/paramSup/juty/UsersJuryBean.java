package view.beans.paramSup.juty;

import java.util.ArrayList;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class UsersJuryBean {
    private RichTable userTable;
    private RichTable juryTable;
    private RichPopup popupJuryDetails;
    private RichPopup pupupNotUserDetails;
    private RichSelectOneRadio inputRole;

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    
    public UsersJuryBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveUsersJury(ActionEvent actionEvent) {
        //System.out.println(this.getInputRole().getValue().toString().equals("PRESIDENT"));
        BindingContainer bindings = getBindings();
        try {
            OperationBinding usersSelected = bindings.getOperationBinding("getSelectedUsers");
            ArrayList<Long> users = (ArrayList<Long>) usersSelected.execute();
            if (users.size() != 0) {
                // Pour chaque utilisateur lui accorder les accees choisies
                OperationBinding jurySelected = bindings.getOperationBinding("getSelectedJury");
                ArrayList<Long> jury = (ArrayList<Long>) jurySelected.execute();
                if (jury.size() != 0) {
                    for (int usercounter = 0; usercounter < users.size(); usercounter++) {
                        for (int jurycounter = 0; jurycounter < jury.size(); jurycounter++) {
                            // Verifier si l'utilisateur n'est pas encore membre de ce jury
                            System.out.println("Verification user membre ou non de ce jury ...");
                            OperationBinding isjuryAffected = bindings.getOperationBinding("IsJuryAffected");
                            isjuryAffected.getParamsMap().put("uti", users.get(usercounter));
                            isjuryAffected.getParamsMap().put("j", jury.get(jurycounter));
                            Object resul = isjuryAffected.execute();
                           DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                     .getCurrentBindingsEntry()
                                                                     .get("IsJuryAffectedROVOIterator");
                            Row isaffected = iter.getCurrentRow();
                            // Cet utilisateur n'est pas encore dans un jury
                            if (isaffected == null) {
                                // Si on veut ajouter un president de jury, vérifié si le jury n'a pas de préside
                                System.out.println("Utilisateur pas membre de ce jury");
                                System.out.println("Ajout de ce user dans le jury en cours ...");
                                System.out.println("Vérification du role ...");
                                if(this.getInputRole().getValue().toString().equals("PRESIDENT")){
                                    System.out.println("Role PRESIDENT");
                                    System.out.println("Verification si jury n'a pas encore de président en cours ...");
                                    OperationBinding isjuryPresi = bindings.getOperationBinding("IsJuryPresiExist");
                                    isjuryPresi.getParamsMap().put("j", jury.get(jurycounter));
                                    isjuryPresi.execute();
                                    DCIteratorBinding iterpresi = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("IsJuryPresidentExistIterator");
                                     Row crntPresi = iterpresi.getCurrentRow();
                                     // Accorder le role de président
                                     if (crntPresi == null) {
                                        // Pas encore de president pour ce jury
                                        System.out.println("Pas de president pour ce jury");
                                        System.out.println("Ajout de ce user dans le jury en cours en tant que président ok");
                                        OperationBinding opAllowAcces = bindings.getOperationBinding("AffectedJuryTo");
                                        opAllowAcces.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                                        opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                        opAllowAcces.getParamsMap().put("IdJury", jury.get(jurycounter));
                                        opAllowAcces.getParamsMap().put("Role", this.getInputRole().getValue().toString());
                                        resul = opAllowAcces.execute();
                                    }
                                    else{
                                        // President deja defini
                                        System.out.println("President deja defini pour ce jury : Prenom Nom");
                                    }
                                }
                                else{
                                    // Pas encore affecter à un jury et role Simple
                                    System.out.println("Role SIMPLE");
                                    System.out.println("Ajout en cours ...");
                                    OperationBinding opAllowAcces = bindings.getOperationBinding("AffectedJuryTo");
                                    opAllowAcces.getParamsMap().put("IdUtilisateur", users.get(usercounter));
                                    opAllowAcces.getParamsMap().put("UtiCree", getUtilisateur());
                                    opAllowAcces.getParamsMap().put("IdJury", jury.get(jurycounter));
                                    opAllowAcces.getParamsMap().put("Role", this.getInputRole().getValue().toString());
                                    resul = opAllowAcces.execute();
                                }
                            }
                            else{
                                // si <<ACCES SIMPLE>> avant et <<PRESIDENT>> nouveau acces 
                                    // Si Pas Encore de president dans ce jury
                                        // Update Role to <<PRESIDENT>>
                                if((this.getInputRole().getValue().toString().equals("PRESIDENT")) && (isaffected.getAttribute("Role").toString().equals("ACCES SIMPLE"))){
                                    System.out.println("Update Possible ...");
                                    System.out.println("Vérification si ce jury a deja un President en cours ...");
                                    OperationBinding isjuryPresi = bindings.getOperationBinding("IsJuryPresiExist");
                                    isjuryPresi.getParamsMap().put("j", jury.get(jurycounter));
                                    isjuryPresi.execute();
                                    DCIteratorBinding iterpresi = (DCIteratorBinding) BindingContext.getCurrent()
                                                                              .getCurrentBindingsEntry()
                                                                              .get("IsJuryPresidentExistIterator");
                                     Row crntPresi = iterpresi.getCurrentRow();
                                     // Accorder le role de président
                                     if (crntPresi == null) {
                                        System.out.println("Pas encore de President pour ce Jury");
                                        System.out.println("Updating Role ...");
                                        OperationBinding updateRole = bindings.getOperationBinding("UpdateRole");
                                        updateRole.getParamsMap().put("idUser", users.get(usercounter));
                                        updateRole.getParamsMap().put("idJury", jury.get(jurycounter));
                                        updateRole.getParamsMap().put("role", this.getInputRole().getValue().toString());
                                        updateRole.getParamsMap().put("utimodif", getUtilisateur());
                                        resul = updateRole.execute();
                                    }
                                     else{
                                        System.out.println("President Existant");
                                        System.out.println("Updating Canceled ");
                                    }
                                }
                                //System.out.println("Acces déja accorder : IdUtilisateur "+users.get(usercounter)+" IdJury "+jury.get(jurycounter));
                            }
                        }
                    }
                    // Tout ce passe bien On commit
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    Object result = operationCommit.execute();

                    // Réexecuter les VO pour actualiser les tableaux
                    /*OperationBinding lesJury = BindingContext.getCurrent()
                                                                .getCurrentBindingsEntry()
                                                                .getOperationBinding("GetJury");
                    lesJury.getParamsMap().put("annee", 2);
                    lesJury.getParamsMap().put("parcours", 2);
                    lesJury.execute();*/

                    /*OperationBinding lesAcces = BindingContext.getCurrent()
                                                              .getCurrentBindingsEntry()
                                                              .getOperationBinding("GetUtilisateur");
                    lesAcces.getParamsMap().put("s_id", 1);
                    lesAcces.execute();*/

                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserTable());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getJuryTable());
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtudiantTable());

                } else {
                    //Si aucun niveau de formation n'est selectionné
                    RichPopup popup = this.getPopupJuryDetails();
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

    public void setUserTable(RichTable userTable) {
        this.userTable = userTable;
    }

    public RichTable getUserTable() {
        return userTable;
    }

    public void setJuryTable(RichTable juryTable) {
        this.juryTable = juryTable;
    }

    public RichTable getJuryTable() {
        return juryTable;
    }

    public void setPopupJuryDetails(RichPopup popupJuryDetails) {
        this.popupJuryDetails = popupJuryDetails;
    }

    public RichPopup getPopupJuryDetails() {
        return popupJuryDetails;
    }

    public void setPupupNotUserDetails(RichPopup pupupNotUserDetails) {
        this.pupupNotUserDetails = pupupNotUserDetails;
    }

    public RichPopup getPupupNotUserDetails() {
        return pupupNotUserDetails;
    }

    public void setInputRole(RichSelectOneRadio inputRole) {
        this.inputRole = inputRole;
    }

    public RichSelectOneRadio getInputRole() {
        return inputRole;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }
}
