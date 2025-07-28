package view.beans.evaluation.anonymat;

import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import view.beans.aes.AESBean;

public class AnonymatBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Integer historique = Integer.parseInt(sessionScope.get("id_hs").toString());
    private RichPopup popupNoChekedParcrs;
    private RichTable anonymatTable;
    private RichPopup popupNoPrcrs;
    private RichPopup popupManyPrcrs;
    private RichPopup popupConfirmInit;
    private RichPopup popupNoAnoDefineYet;
    private RichPopup popupOkConfirmInit;
    private RichTable anogenTable;

    public AnonymatBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveParamsClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        //Get Rule
        DCIteratorBinding iterR = (DCIteratorBinding) BindingContext.getCurrent()
                                                                    .getCurrentBindingsEntry()
                                                                    .get("ReglesAnonymatVOIterator");
        Row currentR = iterR.getCurrentRow();
        Integer rule = Integer.parseInt(currentR.getAttribute("IdRegleAnonymat").toString());
        //System.out.println("Regle : " + rule);
        try {
            OperationBinding operationgetSelected = bindings.getOperationBinding("getSelectedParcours");
            // Integer is_all_note_mod_ens_closed = (Integer) is_closed_all_note_mod_ens.execute();
            ArrayList<Long> result = (ArrayList<Long>) operationgetSelected.execute();
            //System.out.println("Parcours selected size : " + result.size());
            if (result.size() != 0) {
                for (int counter = 0; counter < result.size(); counter++) {
                    //verify if rule is used
                    OperationBinding isused = BindingContext.getCurrent()
                                                            .getCurrentBindingsEntry()
                                                            .getOperationBinding("IsRuleUsed");
                    isused.getParamsMap().put("id_an", getAnne_univers());
                    isused.getParamsMap().put("id_sem", getSemestre());
                    isused.getParamsMap().put("id_nfp", result.get(counter));
                    isused.getParamsMap().put("id_sess", getSession());
                    isused.getParamsMap().put("id_r", rule);
                    isused.execute();
                    DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("IsRuleUsedIterator");
                    //System.out.println("Size : " + iter.size());
                    //System.out.println("Is rule used : " + (iter.getCurrentRow() == null));
                    Row crnt = iter.getCurrentRow();
                    if (crnt != null) {
                        //Pass this config because it is in used
                        System.out.println("Pass this config because it is in used");
                    } else {
                        // appelle de la methode CreategroupeSaisieEtudiant
                        //System.out.println(result.get(counter));
                        OperationBinding op = bindings.getOperationBinding("AddRuleToParcrs");
                        op.getParamsMap().put("IdRegleAnonymat", rule);
                        op.getParamsMap().put("IdAnneesUnivers", getAnne_univers());
                        op.getParamsMap().put("IdNiveauFormationParcours", result.get(counter));
                        op.getParamsMap().put("IdMaquette", result.get(counter));
                        op.getParamsMap().put("IdSemestre", getSemestre());
                        op.getParamsMap().put("IdSession", getSession());
                        op.getParamsMap().put("IsActivate", 1);
                        op.getParamsMap().put("UtiCree", getUtilisateur());
                        Object resul = op.execute();
                    }
                }
            } else {
                //System.out.println("Pas de parcours selectionné");
                RichPopup popup = this.getPopupNoChekedParcrs();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        OperationBinding operationCommit = bindings.getOperationBinding("Commit");
        Object result = operationCommit.execute();
        OperationBinding operationAno = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetAnonymatUsed");
        operationAno.getParamsMap().put("annee", getAnne_univers());
        operationAno.getParamsMap().put("id_hs", getHistorique());
        operationAno.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAnonymatTable());
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

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setPopupNoChekedParcrs(RichPopup popupNoChekedParcrs) {
        this.popupNoChekedParcrs = popupNoChekedParcrs;
    }

    public RichPopup getPopupNoChekedParcrs() {
        return popupNoChekedParcrs;
    }

    public void setAnonymatTable(RichTable anonymatTable) {
        this.anonymatTable = anonymatTable;
    }

    public RichTable getAnonymatTable() {
        return anonymatTable;
    }

    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        //System.out.println("Key Generate " + n + " : " + key);
        return key;
    }

    @SuppressWarnings("unchecked")
    public void OnGenerateClicked(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        //Cas Possible a l'avenir :
        // 1.Certains etudients du meme percours peuvent ne pas avoir de numéro
        //Solution : regenerer des numero différnt des numeros existantes(qui seront recupérer et misent dans une liste NumeroExistant);
        //NB : les numeros nouvellement générer peuvent s'inserer dans cette liste
        //Integer sessionkey = getSession();
        BindingContainer bindings = getBindings();
        List<Long> studentExists = new ArrayList<>();
        List<Long> aleat = new ArrayList<>();
        DCIteratorBinding Parcoursiter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("ParcoursIterator");
        Row current = Parcoursiter.getCurrentRow();
        Integer parcours = Integer.parseInt(current.getAttribute("IdNiveauFormationParcours").toString());
        //System.out.println("Parcours : " + parcours);

        OperationBinding getrule = BindingContext.getCurrent()
                                                 .getCurrentBindingsEntry()
                                                 .getOperationBinding("GetRuleUsed");
        getrule.getParamsMap().put("id_an", getAnne_univers());
        getrule.getParamsMap().put("id_sem", getSemestre());
        getrule.getParamsMap().put("id_nfp", parcours);
        getrule.getParamsMap().put("id_sess", getSession());
        Object res = getrule.execute();
        // Construction de la clé de cryptage de l'anonymat

        String key =
            parcours + "" + getSession() + "" + getSemestre() + "" + getAnne_univers() + "" + getAnne_univers() + "" +
            getSemestre() + "" + getSession() + "" + parcours + "" + getSemestre() + "" + getSession() + "" + parcours +
            "" + getAnne_univers() + "" + getSession() + "" + parcours + "" + getAnne_univers() + "" + getSemestre();

        String ValideKey = key.substring(0, 16);
        //System.out.println("Key : " + key);
        //System.out.println("ValideKey : " + ValideKey);
        AESBean aESBean = new AESBean(ValideKey);
        String encData, decData, anonymat;
        try {
            encData = aESBean.encrypt("1");
            //System.out.println("Data crypted : " + encData);
            decData = aESBean.decrypt(encData);
            //System.out.println("Data decrypted : " + decData);
        } catch (Exception e) {
            Logger.getLogger(AESBean.class.getName()).log(Level.SEVERE, null, e);
        }

        DCIteratorBinding RuleUsediter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("gerRuleUsedIterator");
        Long size = RuleUsediter.getEstimatedRowCount();

        if (size == 0) {
            RichPopup popup = this.getPopupNoPrcrs();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } else {
            if (size > 1) {
                RichPopup popup = this.getPopupManyPrcrs();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                //IdAnonymat

                //Liste des etudiant ayant un numero anonyme
                OperationBinding existsstudents = BindingContext.getCurrent()
                                                                .getCurrentBindingsEntry()
                                                                .getOperationBinding("ExistsEtuAno");
                existsstudents.getParamsMap().put("id_an", getAnne_univers());
                existsstudents.getParamsMap().put("id_sem", getSemestre());
                existsstudents.getParamsMap().put("id_nfp", parcours);
                existsstudents.getParamsMap().put("id_sess", getSession());
                existsstudents.execute();
                DCIteratorBinding ExistEtuAnoiter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                      .getCurrentBindingsEntry()
                                                                                      .get("IsExistEtuAnoIterator");
                RowSetIterator rsis = ExistEtuAnoiter.getViewObject().createRowSetIterator(null);
                while (rsis.hasNext()) {
                    Row nextRow = rsis.next();
                    studentExists.add(Long.parseLong(nextRow.getAttribute("IdEtudiant").toString()));
                    aleat.add(Long.parseLong(nextRow.getAttribute("Anonymat").toString()));

                }
                //System.out.println("Student width ano num " + studentExists.size());
                //################################################################################
                //Id_Annonymat_used
                OperationBinding curentAno = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("GetAnoUsed");
                curentAno.getParamsMap().put("id_an", getAnne_univers());
                curentAno.getParamsMap().put("id_sem", getSemestre());
                curentAno.getParamsMap().put("id_nfp", parcours);
                curentAno.getParamsMap().put("id_sess", getSession());
                curentAno.execute();
                DCIteratorBinding AnoUsediter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                  .getCurrentBindingsEntry()
                                                                                  .get("UsedAnoIterator");
                Row currentAno = AnoUsediter.getCurrentRow();
                Long id_ano = Long.parseLong(currentAno.getAttribute("IdAnonymat").toString());
                //System.out.println("Ano Used " + id_ano);
                //System.out.println("Generating ...");
                OperationBinding students = BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .getOperationBinding("StudentParcrsSemSessAn");
                students.getParamsMap().put("id_an", getAnne_univers());
                students.getParamsMap().put("id_sem", getSemestre());
                students.getParamsMap().put("id_nfp", parcours);
                students.getParamsMap().put("id_sess", getSession());
                students.execute();
                DCIteratorBinding Studentsiter =
                    (DCIteratorBinding) BindingContext.getCurrent()
                                                                                   .getCurrentBindingsEntry()
                                                                                   .get("StudentParcrsAnSemSessIterator");
                Long studentsize = Studentsiter.getEstimatedRowCount();
                //System.out.println("Student size : " + studentsize);
                if (studentsize != 0) {
                    Long value;
                    RowSetIterator rsi = Studentsiter.getViewObject().createRowSetIterator(null);
                    while (rsi.hasNext()) {
                        Row nextRows = rsi.next();
                        //System.out.println("Id Etudiant : " + nextRows.getAttribute("IdEtudiant"));
                        //System.out.println("Is Student exist : " + aleat.contains(nextRows.getAttribute("IdEtudiant")));
                        if (studentExists.contains(nextRows.getAttribute("IdEtudiant"))) {
                            System.out.println("Pass this student has Ano_number");
                        } else {
                            value = new Long(Math.round(new Random().nextFloat() * studentsize));
                            //Tant que la valeur générée n'est pas différente des valeur déja générer ou 0
                            while ((aleat.contains(value)) || (value == 0)) {
                                value = new Long(Math.round(new Random().nextFloat() * studentsize));
                            }
                            //System.out.println("Value generated " + value);
                            aleat.add(value);
                            //System.out.println("id Etudiant No Ano : "+nextRows.getAttribute("IdEtudiant"));
                            try {
                                anonymat = aESBean.encrypt(value.toString());
                                //System.out.println("Data crypted : " + anonymat);
                                OperationBinding opCreateAno = BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .getOperationBinding("GenerateAnonymat");
                                opCreateAno.getParamsMap().put("IdAnonymat", id_ano);
                                opCreateAno.getParamsMap().put("IdEtudiant", nextRows.getAttribute("IdEtudiant"));
                                opCreateAno.getParamsMap().put("Anonymat", anonymat);
                                opCreateAno.getParamsMap().put("UtiCree", getUtilisateur());
                                opCreateAno.execute();
                            } catch (Exception e) {
                                Logger.getLogger(AESBean.class.getName()).log(Level.SEVERE, null, e);
                            }

                        }
                    }
                    rsi.closeRowSetIterator();
                    OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                    Object result = operationCommit.execute();
                    OperationBinding operationAno = BindingContext.getCurrent()
                                                                  .getCurrentBindingsEntry()
                                                                  .getOperationBinding("GetAnoGenParcours");
                    operationAno.getParamsMap().put("annee", getAnne_univers());
                    operationAno.getParamsMap().put("parcous", parcours);
                    operationAno.getParamsMap().put("semestre", getSemestre());
                    operationAno.getParamsMap().put("sess", getSession());
                    operationAno.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAnogenTable());
                }
            }
        }
    }

    public void setPopupNoPrcrs(RichPopup popupNoPrcrs) {
        this.popupNoPrcrs = popupNoPrcrs;
    }

    public RichPopup getPopupNoPrcrs() {
        return popupNoPrcrs;
    }

    public void setPopupManyPrcrs(RichPopup popupManyPrcrs) {
        this.popupManyPrcrs = popupManyPrcrs;
    }

    public RichPopup getPopupManyPrcrs() {
        return popupManyPrcrs;
    }

    public void OnInitClicked(ActionEvent actionEvent) {
        // GetAnoUsed
        //System.out.println("Initialisation");
        RichPopup popup = this.getPopupConfirmInit();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopupConfirmInit(RichPopup popupConfirmInit) {
        this.popupConfirmInit = popupConfirmInit;
    }

    public RichPopup getPopupConfirmInit() {
        return popupConfirmInit;
    }

    @SuppressWarnings("unchecked")
    public void OnDialogAction(DialogEvent dialogEvent) {
        Integer sess = getSession();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            this.getPopupConfirmInit().hide();
            BindingContainer bindings = getBindings();
            DCIteratorBinding Parcoursiter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                               .getCurrentBindingsEntry()
                                                                               .get("ParcoursIterator");
            Row currentPrcrs = Parcoursiter.getCurrentRow();
            Integer parcours = Integer.parseInt(currentPrcrs.getAttribute("IdNiveauFormationParcours").toString());

            OperationBinding curentAno = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("GetAnoUsed");
            curentAno.getParamsMap().put("id_an", getAnne_univers());
            curentAno.getParamsMap().put("id_sem", getSemestre());
            curentAno.getParamsMap().put("id_nfp", parcours);
            curentAno.getParamsMap().put("id_sess", getSession());
            curentAno.execute();
            DCIteratorBinding UseAnoiter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                             .getCurrentBindingsEntry()
                                                                             .get("UsedAnoIterator");
            Long size = UseAnoiter.getEstimatedRowCount();
            if (size == 0) {
                //System.out.println("Pas d'ano définit pour ce parcours");
                RichPopup popup = this.getPopupNoAnoDefineYet();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                Row currentano = UseAnoiter.getCurrentRow();
                Integer anonymat = Integer.parseInt(currentano.getAttribute("IdAnonymat").toString());
                OperationBinding operationInit = BindingContext.getCurrent()
                                                               .getCurrentBindingsEntry()
                                                               .getOperationBinding("initAno");
                operationInit.getParamsMap().put("id_ano", anonymat);
                operationInit.execute();

                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                OperationBinding operationAno = BindingContext.getCurrent()
                                                              .getCurrentBindingsEntry()
                                                              .getOperationBinding("GetAnoGenParcours");
                operationAno.getParamsMap().put("annee", getAnne_univers());
                operationAno.getParamsMap().put("parcous", parcours);
                operationAno.getParamsMap().put("semestre", getSemestre());
                operationAno.getParamsMap().put("sess", getSession());
                operationAno.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAnogenTable());
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
                RichPopup popup = this.getPopupOkConfirmInit();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopupNoAnoDefineYet(RichPopup popupNoAnoDefineYet) {
        this.popupNoAnoDefineYet = popupNoAnoDefineYet;
    }

    public RichPopup getPopupNoAnoDefineYet() {
        return popupNoAnoDefineYet;
    }

    public void setPopupOkConfirmInit(RichPopup popupOkConfirmInit) {
        this.popupOkConfirmInit = popupOkConfirmInit;
    }

    public RichPopup getPopupOkConfirmInit() {
        return popupOkConfirmInit;
    }

    @SuppressWarnings("unchecked")
    public void onShowRulesClicked(ActionEvent actionEvent) {
        // Add event code here...GetAnonymatUsed
        OperationBinding operationAno = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetAnonymatUsed");
        operationAno.getParamsMap().put("annee", getAnne_univers());
        operationAno.getParamsMap().put("id_hs", getHistorique());
        operationAno.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAnonymatTable());
    }

    public void setHistorique(Integer historique) {
        this.historique = historique;
    }

    public Integer getHistorique() {
        return historique;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationParcours");
        Integer Id = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        //System.out.println("Id Parcours " + Id);

        OperationBinding operationAno = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("GetAnoGenParcours");
        operationAno.getParamsMap().put("annee", getAnne_univers());
        operationAno.getParamsMap().put("parcous", Id);
        operationAno.getParamsMap().put("semestre", getSemestre());
        operationAno.getParamsMap().put("sess", getSession());
        operationAno.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAnogenTable());
    }

    public void setAnogenTable(RichTable anogenTable) {
        this.anogenTable = anogenTable;
    }

    public RichTable getAnogenTable() {
        return anogenTable;
    }

    public void OnParamAnoRuleListener(ActionEvent actionEvent) {
        // Add event code here...
    }
}
