package view.beans.evaluation.deliberation_ann;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DeliberationAnnuelelBean {
    private RichInputText first;
    private RichInputText second;
    private RichTable delibAnTable;
    private RichPanelHeader resAn;
    private RichPopup popInf;
    private RichButton deliberer;
    private RichButton pvprovisoire;
    private RichButton cloturer;
    private RichTable deliberationTable;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer calendrier = Integer.parseInt(sessionScope.get("id_calendrier").toString());
    private Integer formation = Integer.parseInt(sessionScope.get("id_fr").toString());
    private Long prcrs_maq = Long.parseLong(sessionScope.get("prcrs_maq_an").toString());
    private Long niv_sec = Long.parseLong(sessionScope.get("niv_sec").toString());
    private RichPanelCollection pancollect;
    private RichColumn delibAnTablee;
    private RichPopup popConfirmOpen;
    private RichPopup popAnOpened;
    private RichPopup popAnDelbSuccess;
    private RichPanelGroupLayout resAnPanGrp;
    private RichTable anTab;
    private RichPanelGroupLayout resanPangrp2;
    private RichPanelGroupLayout panGrp;
    private RichTable pvAnTab;
    private RichPanelCollection pvAnPancol;
    private RichPanelGroupLayout pvAnPanGrp;
    private RichButton btnDeliberer;
    private RichButton btnpvprovisoir;
    private RichPanelGroupLayout pvAnPanGrp1;
    private RichPopup popInfClose;
    private RichPopup popConfirmClose;
    private RichPopup popNivSup;
    private RichPopup popSuccess;
    private RichPopup popend;
    private RichButton btnPvSess1;
    private RichButton btnPvSess2;
    private RichPopup popNivSupOpt;
    private RichPopup popNivSupOptSess2;
    private RichPopup popNivSupSess2;
    private RichPopup popConfirmClosedDoc;
    private RichPopup popinfCloseDoc;
    private RichPopup popCloseDocOk;
    private RichPopup popCloseDocErr;

    public DeliberationAnnuelelBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void Search(ActionEvent actionEvent) {

        Integer moyenne1 = 0;
        Integer moyenne2 = 20;
        Integer credit1 = 0;
        Integer credit2 = 60;
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();

        DCIteratorBinding dciter_filtre = (DCIteratorBinding) cbinding.get("FiltreAnuelleROVO1Iterator");
        Row currentFiltre = dciter_filtre.getCurrentRow();
        //System.out.println("libelle currentFiltre"+currentFiltre.getAttribute("libelle"));

        DCIteratorBinding dciter_filtre1 = (DCIteratorBinding) cbinding.get("FiltreModeDelibAn1Iterator");
        Row currentFiltre1 = dciter_filtre1.getCurrentRow();


        if (currentFiltre.getAttribute("libelle").equals("Moyenne")) {
            if (this.first.getValue() != null) {
                moyenne1 = Integer.parseInt(this.first
                                                .getValue()
                                                .toString());
            }
            if (this.second.getValue() != null) {
                moyenne2 = Integer.parseInt(this.second
                                                .getValue()
                                                .toString());
            }
            //System.out.println("SortByMoyenne");
        }
        if (currentFiltre.getAttribute("libelle").equals("Crédit")) {
            if (this.first.getValue() != null) {
                credit1 = Integer.parseInt(this.first
                                               .getValue()
                                               .toString());
            }
            if (this.second.getValue() != null) {
                credit2 = Integer.parseInt(this.second
                                               .getValue()
                                               .toString());
            }
        }
        if (moyenne1 > moyenne2) {
            Integer m = moyenne1;
            moyenne1 = moyenne2;
            moyenne2 = m;
        }
        if (credit1 > credit2) {
            Integer c = credit1;
            credit1 = credit2;
            credit2 = c;
        }
        /*System.out.println("moyenne1 "+moyenne1);
        System.out.println("moyenne2 "+moyenne2);
        System.out.println("credit1 "+credit1);
        System.out.println("credit2 "+credit2);*/
        //System.out.println("currentFiltre.getAttribute(\"libelle\") "+currentFiltre.getAttribute("libelle"));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");

        //Variables doivent etre recupérer à partir de la session
        operationBinding.getParamsMap().put("annee", getAnne_univers());
        operationBinding.getParamsMap().put("calendrier", getCalendrier());
        operationBinding.getParamsMap().put("parcours", getParcours());

        //Variable recupéré à partir du formulaire
        operationBinding.getParamsMap().put("moy1", moyenne1);
        operationBinding.getParamsMap().put("moy2", moyenne2);
        operationBinding.getParamsMap().put("cred1", credit1);
        operationBinding.getParamsMap().put("cred2", credit2);
        @SuppressWarnings("unused")
        Object result = operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPancollect());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDeliberationTable());
    }

    public void setFirst(RichInputText first) {
        this.first = first;
    }

    public RichInputText getFirst() {
        return first;
    }

    public void setSecond(RichInputText second) {
        this.second = second;
    }

    public RichInputText getSecond() {
        return second;
    }

    public void setDelibAnTable(RichTable delibAnTable) {
        this.delibAnTable = delibAnTable;
    }

    public RichTable getDelibAnTable() {
        return delibAnTable;
    }

    @SuppressWarnings("unchecked")
    public void Deliberer(ActionEvent actionEvent) {
        // L'objectif general de cette methode est : Appel de calculMoyenneAnnuelProc() aprés que toutes le necessaire est fait
        // S'assurer que les semestres sont déja publiés
        BindingContainer bindings = getBindings();

        // Vérifier si les semestres sont publié
        OperationBinding is_all_sem_publised = bindings.getOperationBinding("isPublishedAllSemestre");
        is_all_sem_publised.getParamsMap().put("parcours", getParcours());
        is_all_sem_publised.getParamsMap().put("annee", getAnne_univers());
        is_all_sem_publised.getParamsMap().put("session", getSession());
        Integer is_published_all_sem = (Integer) is_all_sem_publised.execute();
        // System.out.println("is_closed : "+is_closed);
        if (is_published_all_sem == 0) { // les deux semestres ne sont pas encore cloturer : donc impossible de délibérer
            System.out.println("Publier toutes les semestres avant de faire la délibération annuelle");
            RichPopup popup = this.getPopInf();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
            return;
        } else { // Semestre cloturer donc délibération possible
            OperationBinding operationCalculMoyenne = bindings.getOperationBinding("CalculMoyenneAnnuel");
            //Variable de Session
            operationCalculMoyenne.getParamsMap().put("anne_univers", getAnne_univers());
            operationCalculMoyenne.getParamsMap().put("parcours", getParcours());
            operationCalculMoyenne.getParamsMap().put("utilisateur", getUtilisateur());
            Object moyenne = operationCalculMoyenne.execute();
            pvprovisoire.setDisabled(false);
            cloturer.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvprovisoire());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCloturer());

            // Rafraichissement du tableau
            OperationBinding operationRefresh = bindings.getOperationBinding("ExecuteWithParams");
            //Variables doivent etre recupérer à partir de la session
            operationRefresh.getParamsMap().put("annee", getAnne_univers());
            operationRefresh.getParamsMap().put("calendrier", getCalendrier());
            operationRefresh.getParamsMap().put("parcours", getParcours());

            //Variable fixe pour recupérer toutes les lignes apres la délibération
            operationRefresh.getParamsMap().put("moy1", 0);
            operationRefresh.getParamsMap().put("moy2", 20);
            operationRefresh.getParamsMap().put("cred1", 0);
            operationRefresh.getParamsMap().put("cred2", 60);
            Object res1 = operationRefresh.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDelibAnTable());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResAn());
        }
    }

    public void setResAn(RichPanelHeader resAn) {
        this.resAn = resAn;
    }

    public RichPanelHeader getResAn() {
        return resAn;
    }

    @SuppressWarnings("unchecked")
    public void Cloturer(ActionEvent actionEvent) {
        System.out.println("Cloturer");
        //Creer année suivante si pas existante
        //Initialiser calendrier délibération sem 1 sess 1 parcours courant nouvelle année

        BindingContainer bindings = getBindings();
        // Cloturer la premiere session if session normale
        // ""       "" seconde           ""    ""   2
        String action = "O";
        if (1 == getSession()) {

        } else {
            // reconduireNoteToAn
        }
    }

    public void setPopInf(RichPopup popInf) {
        this.popInf = popInf;
    }

    public RichPopup getPopInf() {
        return popInf;
    }

    public void setDeliberer(RichButton deliberer) {
        this.deliberer = deliberer;
    }

    public RichButton getDeliberer() {
        return deliberer;
    }

    public void setPvprovisoire(RichButton pvprovisoire) {
        this.pvprovisoire = pvprovisoire;
    }

    public RichButton getPvprovisoire() {
        return pvprovisoire;
    }

    public void setCloturer(RichButton cloturer) {
        this.cloturer = cloturer;
    }

    public RichButton getCloturer() {
        return cloturer;
    }

    public void CloseSession(ActionEvent actionEvent) {
        // Si c'est la premiere session, on reconduit à la session 2 (ReconduireToSess2) ouverture de la sess2
        // Si c'est la deuxiéme session, on reconduit à l'annee suivant (ReconduireToAnSuivant)
        System.out.println("Fermeture de la session session ... ");
        if (getSession() == 1) {
            // call procedure reconduireRedoublantToSess2(anne, parcours, sess, user)



        } else {
            // call procedure reconduireRedoublantToAnSuivant(anne, parcours, sess, user)
        }
    }

    @SuppressWarnings("unchecked")
    public void DeliberationAnnuelle(ActionEvent actionEvent) {
        String action = "O";
        BindingContainer bindings = getBindings();
        // Vérifier si les semestres sont publié
        OperationBinding is_all_sem_publised = bindings.getOperationBinding("isPublishedAllSemestre");
        is_all_sem_publised.getParamsMap().put("parcours_maq", getPrcrs_maq());
        is_all_sem_publised.getParamsMap().put("sess", new Long(getSession()));
        Integer is_published_all_sem = (Integer) is_all_sem_publised.execute();
        //System.out.println("is_published_all_sem : "+is_published_all_sem);
        if (0 == is_published_all_sem) {
            RichPopup popup = this.getPopInf();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
            return;
        } else {
            try {
                OperationBinding opdelib = bindings.getOperationBinding("Deliberer");
                opdelib.getParamsMap().put("parcours_maq", getPrcrs_maq());
                opdelib.getParamsMap().put("session_id", getSession());
                opdelib.getParamsMap().put("action", action);
                opdelib.getParamsMap().put("utilisateur", getUtilisateur());
                Integer id_delib = (Integer) opdelib.execute();
                if (0 != id_delib) {
                    try {
                        OperationBinding operationCalculMoyenne = bindings.getOperationBinding("DelibererAnnee");
                        operationCalculMoyenne.getParamsMap().put("annee", new Long(getAnne_univers()));
                        operationCalculMoyenne.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        operationCalculMoyenne.getParamsMap().put("formation", new Long(getFormation()));
                        operationCalculMoyenne.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        operationCalculMoyenne.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    try {
                        OperationBinding operationUpdate = bindings.getOperationBinding("UpdateEnjembiste");
                        operationUpdate.getParamsMap().put("annee", getAnne_univers());
                        operationUpdate.getParamsMap().put("parcours", getParcours());
                        operationUpdate.getParamsMap().put("formation", getFormation());
                        operationUpdate.getParamsMap().put("utilisateur", getUtilisateur());
                        operationUpdate.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    /*try {
                        OperationBinding operationRefresh = bindings.getOperationBinding("DeliberationAnnuelle");

                         * anne,parcours,session

                        operationRefresh.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/

                    try {
                        OperationBinding operationPublish = bindings.getOperationBinding("publierAnnee");
                        operationPublish.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        operationPublish.getParamsMap().put("session_id", getSession());
                        operationPublish.getParamsMap().put("action", action);
                        operationPublish.getParamsMap().put("utilisateur", getUtilisateur());
                        operationPublish.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    RichPopup popup = this.getPopAnDelbSuccess();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                OperationBinding operationPublish = bindings.getOperationBinding("getPv");
                operationPublish.getParamsMap().put("parcours_maq", getPrcrs_maq());
                operationPublish.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnPanGrp());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnPancol());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnTab());

        }
    }

    public void setDeliberationTable(RichTable deliberationTable) {
        this.deliberationTable = deliberationTable;
    }

    public RichTable getDeliberationTable() {
        return deliberationTable;
    }

    public String OnPrintReleveClicked() {
        System.out.println("Printing Action ...");
        return null;
    }

    public void OnPrintReleveClick(ActionEvent actionEvent) {
        System.out.println("Printing ActionListener ...");
        DCIteratorBinding locIter = (DCIteratorBinding) getBindings().get("DélibérationAnnuelleIterator");
        String num = locIter.getCurrentRow()
                            .getAttribute("Numero")
                            .toString();
        System.out.println("Numero Etudiant : " + num);
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    public void setParcours(Integer parcours) {
        this.parcours = parcours;
    }

    public Integer getParcours() {
        return parcours;
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

    public void setCalendrier(Integer calendrier) {
        this.calendrier = calendrier;
    }

    public Integer getCalendrier() {
        return calendrier;
    }

    public void setPancollect(RichPanelCollection pancollect) {
        this.pancollect = pancollect;
    }

    public RichPanelCollection getPancollect() {
        return pancollect;
    }

    public void setDelibAnTablee(RichColumn delibAnTablee) {
        this.delibAnTablee = delibAnTablee;
    }

    public RichColumn getDelibAnTablee() {
        return delibAnTablee;
    }

    public void setFormation(Integer formation) {
        this.formation = formation;
    }

    public Integer getFormation() {
        return formation;
    }

    public void setPopConfirmOpen(RichPopup popConfirmOpen) {
        this.popConfirmOpen = popConfirmOpen;
    }

    public RichPopup getPopConfirmOpen() {
        return popConfirmOpen;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmOpenAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            OperationBinding opopen = getBindings().getOperationBinding("reouvrirPrcrsAn");
            opopen.getParamsMap().put("parcours", new Long(getParcours()));
            opopen.getParamsMap().put("calendrier", new Long(getCalendrier()));
            opopen.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
            Integer result = (Integer) opopen.execute();
            if (1 == result) {
                RichPopup popup = this.getPopAnOpened();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelbtn());
            } else if (-1 == result) {
                System.out.println("-1");
            } else {
                System.out.println("Other");
            }
        }
    }

    public void setPopAnOpened(RichPopup popAnOpened) {
        this.popAnOpened = popAnOpened;
    }

    public RichPopup getPopAnOpened() {
        return popAnOpened;
    }

    public void OnYearOpenAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPrcrs_maq(Long prcrs_maq) {
        this.prcrs_maq = prcrs_maq;
    }

    public Long getPrcrs_maq() {
        return prcrs_maq;
    }

    public void setPopAnDelbSuccess(RichPopup popAnDelbSuccess) {
        this.popAnDelbSuccess = popAnDelbSuccess;
    }

    public RichPopup getPopAnDelbSuccess() {
        return popAnDelbSuccess;
    }

    public void setResAnPanGrp(RichPanelGroupLayout resAnPanGrp) {
        this.resAnPanGrp = resAnPanGrp;
    }

    public RichPanelGroupLayout getResAnPanGrp() {
        return resAnPanGrp;
    }

    public void setAnTab(RichTable anTab) {
        this.anTab = anTab;
    }

    public RichTable getAnTab() {
        return anTab;
    }

    public void setResanPangrp2(RichPanelGroupLayout resanPangrp2) {
        this.resanPangrp2 = resanPangrp2;
    }

    public RichPanelGroupLayout getResanPangrp2() {
        return resanPangrp2;
    }

    public void setPanGrp(RichPanelGroupLayout panGrp) {
        this.panGrp = panGrp;
    }

    public RichPanelGroupLayout getPanGrp() {
        return panGrp;
    }

    public void setPvAnTab(RichTable pvAnTab) {
        this.pvAnTab = pvAnTab;
    }

    public RichTable getPvAnTab() {
        return pvAnTab;
    }

    public void setPvAnPancol(RichPanelCollection pvAnPancol) {
        this.pvAnPancol = pvAnPancol;
    }

    public RichPanelCollection getPvAnPancol() {
        return pvAnPancol;
    }

    public void setPvAnPanGrp(RichPanelGroupLayout pvAnPanGrp) {
        this.pvAnPanGrp = pvAnPanGrp;
    }

    public RichPanelGroupLayout getPvAnPanGrp() {
        return pvAnPanGrp;
    }

    public void setBtnDeliberer(RichButton btnDeliberer) {
        this.btnDeliberer = btnDeliberer;
    }

    public RichButton getBtnDeliberer() {
        return btnDeliberer;
    }

    public void setBtnpvprovisoir(RichButton btnpvprovisoir) {
        this.btnpvprovisoir = btnpvprovisoir;
    }

    public RichButton getBtnpvprovisoir() {
        return btnpvprovisoir;
    }

    public void setPvAnPanGrp1(RichPanelGroupLayout pvAnPanGrp1) {
        this.pvAnPanGrp1 = pvAnPanGrp1;
    }

    public RichPanelGroupLayout getPvAnPanGrp1() {
        return pvAnPanGrp1;
    }

    public void setPopInfClose(RichPopup popInfClose) {
        this.popInfClose = popInfClose;
    }

    public RichPopup getPopInfClose() {
        return popInfClose;
    }

    public void setPopConfirmClose(RichPopup popConfirmClose) {
        this.popConfirmClose = popConfirmClose;
    }

    public RichPopup getPopConfirmClose() {
        return popConfirmClose;
    }

    public void onConfirmClose(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            this.getPopConfirmClose().hide();
            RichPopup popup = this.getPopInfClose();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    @SuppressWarnings("unchecked")
    public void onClosed(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            String op, iter1, oprdbt = null;
            if (1 == getSession()) {
                op = "getListAutorisationSess1";
                iter1 = "ListAutorisationSess1Iterator";
            } else {
                op = "getListAutorisationSess2";
                iter1 = "ListAutorisationSess2Iterator";
                oprdbt = "ChargerResultatRdbt";
            }
            Integer nbre = 0;
            try {
                OperationBinding opnbre = getBindings().getOperationBinding("getNbreNivSup");
                opnbre.getParamsMap().put("niv_sec", getNiv_sec());
                nbre = (Integer) opnbre.execute();

            } catch (Exception e) {
                System.out.println(e);
            }
            if (nbre > 1) {
                try {
                    OperationBinding opNivSupOpt = getBindings().getOperationBinding("getNiveauxSupOptions");
                    opNivSupOpt.getParamsMap().put("code_niv_s", getNiv_sec());
                    //opNivSupOpt.getParamsMap().put("an_id", getAnne_univers()+1);
                    opNivSupOpt.execute();
                    if (1 == getSession()) {
                        OperationBinding opres = getBindings().getOperationBinding("getListAutorisationSess1");
                        opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        opres.execute();
                        RichPopup popup = this.getPopNivSupOpt();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    } else {
                        OperationBinding opres = getBindings().getOperationBinding("getListAutorisationSess2");
                        opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        opres.execute();

                        RichPopup popup = this.getPopNivSupOptSess2();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                //verifier les options
                try {
                    OperationBinding opNivSupOpt = getBindings().getOperationBinding("getNiveauxSupOptions");
                    opNivSupOpt.getParamsMap().put("code_niv_s", getNiv_sec());
                    //opNivSupOpt.getParamsMap().put("an_id", getAnne_univers()+1);
                    opNivSupOpt.execute();
                    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NiveauxSectionsSupIterator");
                    if (1 >= iter.getEstimatedRowCount()) {
                        AttributeBinding attrIdBinding =
                            (AttributeBinding) getBindings().getControlBinding("OpCodeSup");
                        try {
                            OperationBinding opres = getBindings().getOperationBinding("ChargerResultat");
                            opres.getParamsMap().put("prcrs_maq_id", getPrcrs_maq());
                            opres.getParamsMap().put("code_niv_sec", getNiv_sec());
                            opres.getParamsMap().put("an_id", getAnne_univers());
                            opres.getParamsMap().put("sess_id", getSession());
                            if (null != attrIdBinding.getInputValue()) {
                                opres.getParamsMap().put("op_code_sup", attrIdBinding.getInputValue().toString());
                            } else {
                                opres.getParamsMap().put("op_code_sup", null);
                            }
                            opres.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        try {
                            OperationBinding opres = getBindings().getOperationBinding(op);
                            opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                            opres.execute();
                            DCIteratorBinding iterEtd = (DCIteratorBinding) getBindings().get(iter1);
                            if (0 == iterEtd.getEstimatedRowCount()) {
                                try {
                                    opres = getBindings().getOperationBinding("cloturerAnnee");
                                    opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                                    opres.getParamsMap().put("session_id", getSession());
                                    opres.getParamsMap().put("action", "O");
                                    opres.getParamsMap().put("utilisateur", getUtilisateur());
                                    opres.execute();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        RichPopup popup = this.getPopSuccess();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    } else {
                        if (1 == getSession()) {
                            OperationBinding opres = getBindings().getOperationBinding("getListAutorisationSess1");
                            opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                            RichPopup popup = this.getPopNivSupOpt();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        } else {
                            OperationBinding opres = getBindings().getOperationBinding("getListAutorisationSess2");
                            opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                            opres.execute();

                            RichPopup popup = this.getPopNivSupOptSess2();
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    public void setNiv_sec(Long niv_sec) {
        this.niv_sec = niv_sec;
    }

    public Long getNiv_sec() {
        return niv_sec;
    }

    public void setPopNivSup(RichPopup popNivSup) {
        this.popNivSup = popNivSup;
    }

    public RichPopup getPopNivSup() {
        return popNivSup;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmeNivSup(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            this.getPopend().hide();
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NiveauxSectionsSupIterator");
            Row currentnivsec = iter.getCurrentRow();
            try {
                OperationBinding opres = getBindings().getOperationBinding("ChargerResultatSpec");
                opres.getParamsMap().put("prcrs_maq_id", getPrcrs_maq());
                opres.getParamsMap().put("code_niv_sec", currentnivsec.getAttribute("CodeNivSec"));
                opres.getParamsMap().put("code_niv_sec_sup", currentnivsec.getAttribute("CodeNivSecSup"));
                opres.getParamsMap().put("an_id", getAnne_univers());
                opres.getParamsMap().put("sess_id", getSession());
                opres.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                OperationBinding opres = getBindings().getOperationBinding("cloturerAnnee");
                opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                opres.getParamsMap().put("session_id", getSession());
                opres.getParamsMap().put("action", "O");
                opres.getParamsMap().put("utilisateur", getUtilisateur());
                opres.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            RichPopup popup = this.getPopSuccess();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void onNivSupChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
    }

    public void setPopSuccess(RichPopup popSuccess) {
        this.popSuccess = popSuccess;
    }

    public RichPopup getPopSuccess() {
        return popSuccess;
    }

    @SuppressWarnings("unchecked")
    public void isOperationsAn() {
        BindingContainer bindings = getBindings();
        /*String action = "O";
        Integer is_published_all_sem = 0;
        // Vérifier si les semestres sont publié
        /*try{
            OperationBinding is_all_sem_publised = bindings.getOperationBinding("isPublishedAllSemestre");
            is_all_sem_publised.getParamsMap().put("parcours_maq", getPrcrs_maq());
            is_all_sem_publised.getParamsMap().put("sess", new Long(getSession()));
            is_published_all_sem = (Integer) is_all_sem_publised.execute();
            //System.out.println("is_published_all_sem : "+is_published_all_sem);
        }catch(Exception e){
            System.out.println(e);
        }
        if (0 != is_published_all_sem) {
            try {
                OperationBinding opdelib = bindings.getOperationBinding("Deliberer");
                opdelib.getParamsMap().put("parcours_maq", getPrcrs_maq());
                opdelib.getParamsMap().put("session_id", getSession());
                opdelib.getParamsMap().put("action", action);
                opdelib.getParamsMap().put("utilisateur", getUtilisateur());
                Integer id_delib = (Integer) opdelib.execute();
                if (0 != id_delib) {
                    try {
                        OperationBinding operationCalculMoyenne = bindings.getOperationBinding("DelibererAnnee");
                        operationCalculMoyenne.getParamsMap().put("annee", new Long(getAnne_univers()));
                        operationCalculMoyenne.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        operationCalculMoyenne.getParamsMap().put("formation", new Long(getFormation()));
                        operationCalculMoyenne.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        operationCalculMoyenne.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    try {
                        OperationBinding operationUpdate = bindings.getOperationBinding("UpdateEnjembiste");
                        operationUpdate.getParamsMap().put("annee", getAnne_univers());
                        operationUpdate.getParamsMap().put("parcours", getParcours());
                        operationUpdate.getParamsMap().put("formation", getFormation());
                        operationUpdate.getParamsMap().put("utilisateur", getUtilisateur());
                        operationUpdate.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                   try {
                        OperationBinding operationPublish = bindings.getOperationBinding("publierAnnee");
                        operationPublish.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        operationPublish.getParamsMap().put("session_id", getSession());
                        operationPublish.getParamsMap().put("action", action);
                        operationPublish.getParamsMap().put("utilisateur", getUtilisateur());
                        operationPublish.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                   RichPopup popup = this.getPopAnDelbSuccess();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                OperationBinding operationPublish = bindings.getOperationBinding("getPv");
                operationPublish.getParamsMap().put("parcours_maq", getPrcrs_maq());
                operationPublish.execute();
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        */
        /* try {
            OperationBinding opisdelibarate = bindings.getOperationBinding("IsDelibarateAn");
            opisdelibarate.getParamsMap().put("parcours_maq", getPrcrs_maq());
            opisdelibarate.getParamsMap().put("session_id", getSession());
            Integer isdelibarate = (Integer) opisdelibarate.execute();
            sessionScope.put("isdelibeAn", isdelibarate); //0 et n
        } catch (Exception e) {
            System.out.println(e);
        }*/
        try {
            OperationBinding opisclose = bindings.getOperationBinding("IsClosedAn");
            opisclose.getParamsMap().put("parcours_maq", getPrcrs_maq());
            opisclose.getParamsMap().put("session_id", getSession());
            Integer isclosed = (Integer) opisclose.execute();
            sessionScope.put("iscloseAn", isclosed); //0 et n
        } catch (Exception e) {
            System.out.println(e);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnPanGrp());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnPancol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPvAnTab());

    }

    public void setPopend(RichPopup popend) {
        this.popend = popend;
    }

    public RichPopup getPopend() {
        return popend;
    }

    public void onConfirmaurization(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            this.getPopNivSup().hide();
            RichPopup popup = this.getPopend();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setBtnPvSess1(RichButton btnPvSess1) {
        this.btnPvSess1 = btnPvSess1;
    }

    public RichButton getBtnPvSess1() {
        return btnPvSess1;
    }

    public void setBtnPvSess2(RichButton btnPvSess2) {
        this.btnPvSess2 = btnPvSess2;
    }

    public RichButton getBtnPvSess2() {
        return btnPvSess2;
    }

    public void setPopNivSupOpt(RichPopup popNivSupOpt) {
        this.popNivSupOpt = popNivSupOpt;
    }

    public RichPopup getPopNivSupOpt() {
        return popNivSupOpt;
    }

    @SuppressWarnings("unchecked")
    public void OnAutorisedOptAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        String opiter, op = null;
        if (outcome == Outcome.ok) {
            DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("NiveauxSuperieursOptionsIterator");
            Row currentNivOpt = dciter.getCurrentRow();
            //System.out.println("NivSup : "+currentNivOpt.getAttribute("CodeNivSecSup")+" opCode : "+currentNivOpt.getAttribute("OpCodeSup"));
            if (1 == getSession()) {
                opiter = "ListAutorisationSess1Iterator";
                op = "getListAutorisationSess1";
            } else {
                opiter = "ListAutorisationSess2Iterator";
                op = "getListAutorisationSess2";
            }
            //System.out.println("opiter : "+opiter);
            DCIteratorBinding iterstd = (DCIteratorBinding) getBindings().get(opiter);
            //System.out.println("Nbre : "+iterstd.getEstimatedRowCount());

            RowSetIterator rsi = iterstd.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                //System.out.println("is_checked : "+nextRow.getAttribute("isChecked"));
                if (null != nextRow.getAttribute("IsSelected")) {
                    //System.out.println("IsSelected : "+nextRow.getAttribute("IsSelected").toString());
                    if (Integer.parseInt(nextRow.getAttribute("IsSelected").toString()) == 1) {
                        try {
                            OperationBinding opres = getBindings().getOperationBinding("ChargerResultatEtuOpt");
                            opres.getParamsMap().put("prcrs_maq_id", getPrcrs_maq());
                            opres.getParamsMap().put("code_niv_sec", getNiv_sec());
                            opres.getParamsMap()
                                .put("code_niv_sec_sup", currentNivOpt.getAttribute("CodeNivSecSup").toString());
                            opres.getParamsMap().put("an_id", getAnne_univers());
                            opres.getParamsMap().put("sess_id", getSession());
                            opres.getParamsMap()
                                .put("op_code",
                                     currentNivOpt.getAttribute("OpCodeSup") != null ?
                                     currentNivOpt.getAttribute("OpCodeSup").toString() : null);
                            opres.getParamsMap().put("p_num", nextRow.getAttribute("Numero").toString());
                            opres.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }
            rsi.closeRowSetIterator();

            try {
                OperationBinding opres = getBindings().getOperationBinding(op);
                opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                opres.execute();
                DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(opiter);
                if (0 == iter.getEstimatedRowCount()) {
                    try {
                        OperationBinding opresRdblt = getBindings().getOperationBinding("ChargerResultatRdbt");
                        opresRdblt.getParamsMap().put("prcrs_maq_id", getPrcrs_maq());
                        opresRdblt.getParamsMap().put("code_niv_sec", getNiv_sec());
                        opresRdblt.getParamsMap().put("an_id", getAnne_univers());
                        opresRdblt.getParamsMap().put("sess_id", getSession());
                        opresRdblt.getParamsMap()
                            .put("op_code",
                                 currentNivOpt.getAttribute("OpCodeSup") != null ?
                                 currentNivOpt.getAttribute("OpCodeSup").toString() : null);
                        opres.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        opres = getBindings().getOperationBinding("cloturerAnnee");
                        opres.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        opres.getParamsMap().put("session_id", getSession());
                        opres.getParamsMap().put("action", "O");
                        opres.getParamsMap().put("utilisateur", getUtilisateur());
                        opres.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            if (1 == getSession()) {
                RichPopup popup = this.getPopNivSupOpt();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                RichPopup popup = this.getPopNivSupOptSess2();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopNivSupOptSess2(RichPopup popNivSupOptSess2) {
        this.popNivSupOptSess2 = popNivSupOptSess2;
    }

    public RichPopup getPopNivSupOptSess2() {
        return popNivSupOptSess2;
    }

    public void onNivSupOptChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
    }

    public void setPopNivSupSess2(RichPopup popNivSupSess2) {
        this.popNivSupSess2 = popNivSupSess2;
    }

    public RichPopup getPopNivSupSess2() {
        return popNivSupSess2;
    }

    public void onOpenEtatAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            this.getPopConfirmClosedDoc().hide();
            RichPopup popup = this.getPopinfCloseDoc();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopConfirmClosedDoc(RichPopup popConfirmClosedDoc) {
        this.popConfirmClosedDoc = popConfirmClosedDoc;
    }

    public RichPopup getPopConfirmClosedDoc() {
        return popConfirmClosedDoc;
    }

    public void setPopinfCloseDoc(RichPopup popinfCloseDoc) {
        this.popinfCloseDoc = popinfCloseDoc;
    }

    public RichPopup getPopinfCloseDoc() {
        return popinfCloseDoc;
    }

    @SuppressWarnings("unchecked")
    public void onConfirmCloseDoc(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            try {
                OperationBinding opres = getBindings().getOperationBinding("openDoc");
                opres.getParamsMap().put("prcrs_maq_id", getPrcrs_maq());
                opres.getParamsMap().put("sess_id", getSession());
                Integer r = (Integer)opres.execute();
                if ( 0 != r ){
                    RichPopup popup = this.getPopCloseDocOk();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }else {
                    RichPopup popup = this.getPopCloseDocErr();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setPopCloseDocOk(RichPopup popCloseDocOk) {
        this.popCloseDocOk = popCloseDocOk;
    }

    public RichPopup getPopCloseDocOk() {
        return popCloseDocOk;
    }

    public void setPopCloseDocErr(RichPopup popCloseDocErr) {
        this.popCloseDocErr = popCloseDocErr;
    }

    public RichPopup getPopCloseDocErr() {
        return popCloseDocErr;
    }
}
