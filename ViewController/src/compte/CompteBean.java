package compte;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class CompteBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String historiq = sessionScope.get("id_hs").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private RichPopup popRepExisteCle;
    private RichPopup popRepTypEtb;
    private RichPopup popAucnRep;
    private RichPopup popCommitCmpEtab;
    private RichPopup popRepTypDpt;
    private RichPopup popCommitCmpDpt;
    private RichPopup popRepFormt;
    private RichPopup popFormExtDeja;
    private RichPopup popCommitCmpForm;
    private RichPopup popAucunForm;

    private String numero_table;
    private String numero_etud;
    private String numero_cin;
    private String nom;
    private String prenom;
    private String date_naiss;
    private RichPopup popEtudNotInsc;
    private RichPopup popEtudNotDef;
    private RichPopup popEtudGen;
    private RichPopup popCmptEtudCommit;
    private RichPopup popAucunEtudInsc;
    
    private String nbr_cmpt_gen;
    private String nbr_def;
    private String nbr_cmpt_deja;
    private RichPopup popConfEtudInsc;
    private RichPopup popEtatInscNotVal;
    private RichPopup popTsCmpFormGen;
    private RichPopup popCommitTsCmpForm;

    public CompteBean() {
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setNbr_cmpt_gen(String nbr_cmpt_gen) {
        this.nbr_cmpt_gen = nbr_cmpt_gen;
    }

    public String getNbr_cmpt_gen() {
        return nbr_cmpt_gen;
    }

    public void setNbr_def(String nbr_def) {
        this.nbr_def = nbr_def;
    }

    public String getNbr_def() {
        return nbr_def;
    }

    public void setNbr_cmpt_deja(String nbr_cmpt_deja) {
        this.nbr_cmpt_deja = nbr_cmpt_deja;
    }

    public String getNbr_cmpt_deja() {
        return nbr_cmpt_deja;
    }

    public void setHistoriq(String historiq) {
        this.historiq = historiq;
    }

    public void setNumero_table(String numero_table) {
        this.numero_table = numero_table;
    }
    public void getRowPers(Row row_det_pers){
        
        setPrenom(row_det_pers.getAttribute("Prenoms").toString());
        setNom(row_det_pers.getAttribute("Nom").toString());
        java.util.Date utilDate = new java.util.Date(((Date)row_det_pers.getAttribute("DateNaissance")).getTime());
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date_naissance = dateFormat.format(utilDate);
        setDate_naiss(date_naissance);
    }
    public String getNumero_table() {
        return numero_table;
    }

    public void setNumero_etud(String numero_etud) {
        this.numero_etud = numero_etud;
    }

    public String getNumero_etud() {
        return numero_etud;
    }

    public void setNumero_cin(String numero_cin) {
        this.numero_cin = numero_cin;
    }

    public String getNumero_cin() {
        return numero_cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setDate_naiss(String date_naiss) {
        this.date_naiss = date_naiss;
    }

    public String getDate_naiss() {
        return date_naiss;
    }

    public String getHistoriq() {
        return historiq;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    @SuppressWarnings("unchecked")
    public void compteDepartement(){
        // Add event code here...
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        //getCmpteEtabGen
        OperationBinding op_cmpt_dpt_gen = getBindings().getOperationBinding("getCmpteDeptGen");
        op_cmpt_dpt_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_dpt_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_dpt_gen.getParamsMap().put("id_type",  "5");    //type compte établissement
        op_cmpt_dpt_gen.execute();
        DCIteratorBinding iter_cmpt_dpt_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteDeptROIterator");
        Row row_cmpt_dpt_gen = iter_cmpt_dpt_gen.getCurrentRow();
        
        if(row_cmpt_dpt_gen != null)
            sessionScope.put("cmpte_dept_deja_generer", 1);
        else
            sessionScope.put("cmpte_dept_deja_generer", 0);
        
        
    }
    
    @SuppressWarnings("unchecked")
    public void compteEtablissement(){
        // Add event code here...
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        //getCmpteEtabGen
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getCmpteEtabGen");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.getParamsMap().put("id_type",  "4");    //type compte établissement
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtabROIterator");
        Row row_cmpt_etb_gen = iter_annee_passee.getCurrentRow();
        
        if(row_cmpt_etb_gen != null)
            sessionScope.put("cmpte_etab_deja_generer", 1);
        else
            sessionScope.put("cmpte_etab_deja_generer", 0);
        
        
    }

    @SuppressWarnings("unchecked")
    public void compteFormation(){
        // Add event code here...
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        //getCmpteEtabGen
        OperationBinding op_cmpt_form_gen = getBindings().getOperationBinding("getCmpteFormGen");
        op_cmpt_form_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_form_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_form_gen.execute();
        DCIteratorBinding iter_cmpt_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_cmpt_form_gen = iter_cmpt_form_gen.getCurrentRow();
        
        if(row_cmpt_form_gen != null)
            sessionScope.put("cmpte_form_deja_generer", 1);
        else
            sessionScope.put("cmpte_form_deja_generer", 0);
        
        
    }

    public Double totalPourcentageCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        Double somme = 0.00;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();

            if(nextRow.getAttribute("Pourcentage") != null){
               somme = somme +  Double.parseDouble(nextRow.getAttribute("Pourcentage").toString());
            }

        }
        return somme;
    }
    
    @SuppressWarnings("unchecked")
    public void genererCompteEtab(ActionEvent actionEvent) {
        //getRepRechStrAnn
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        //getCmpteEtabGen
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_cmpt_etb_gen = iter_annee_passee.getCurrentRow();
        if(row_cmpt_etb_gen != null){
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
            
            Integer nb = 0;
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                while (rsi_rep_ex_cle.hasNext()) {
                    Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                    if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 4){
                       nb ++; 
                    }
                }
                if(nb > 0){
                    //ok
                    RichPopup popup = this.getPopCommitCmpEtab();                    
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                else{
                    // la rep ne possede pas de type etab
                    RichPopup popup = this.getPopRepTypEtb();                      
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                //verifier la def de rep
                RichPopup popup = this.getPopRepExisteCle();                       
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            //strc non rattachée à une rep
            RichPopup popup = this.getPopAucnRep();                     
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Integer inscCompteEtbDpt(String type_cmpt, String lib_compte){
        Integer nb_save = 0;
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");        
        //AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); 
        //String id_hs = sessionScope.
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        OperationBinding op_annee_passee = getBindings().getOperationBinding("getAnneeUniversPassee");
        op_annee_passee.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_passee.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversPasseeROIterator");
        Row row_set_annee_passee = iter_annee_passee.getCurrentRow();
    
        System.out.println("row_set_annee_passee "+row_set_annee_passee+" lib annee "+row_set_annee_passee.getAttribute("IdLibLongPasse")+"id_annee" +row_set_annee_passee.getAttribute("IdAnneePasse"));
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeAnneeAncStrc");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  row_set_annee_passee.getAttribute("IdAnneePasse"));
        op_soldeAncien.getParamsMap().put("id_type_cmpt",  type_cmpt);
        op_soldeAncien.execute();
    
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return nb_save;
        }
        else{       
            
            if(Integer.parseInt(type_cmpt) == 2){
                //type compte Etablissement Rectorat
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue("Rectorat");
            }
            else if(Integer.parseInt(type_cmpt) == 4){
                // type compte Etablissement
                AttributeBinding lib_etab = (AttributeBinding) getBindings().getControlBinding("LibelleStrct");
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue(lib_etab.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 5){
                //Département
                AttributeBinding lib_dept = (AttributeBinding) getBindings().getControlBinding("LibHisto");
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(getHistoriq());
                proprietaire.setInputValue(lib_dept.getInputValue());
            }

            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue(type_cmpt);//id_type_cmpte.
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return nb_save;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return nb_save;
                }
                else{                
                    DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeAnneeAncStrcROIterator");
                    Row row_solde_anc = iter_solde_anc.getCurrentRow();
                    
                    if(row_solde_anc == null){
                        solde_int.setInputValue(0);
                        solde_act.setInputValue(0);
                    }
                    else{
                        id_annee_anc_type.setInputValue(row_set_annee_passee.getAttribute("IdAnneePasse"));
                        solde_int.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                        solde_act.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                    }
                    
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return nb_save;
                    }
                    else{
                        nb_save ++;
                    }
                }
            }
        }
        return nb_save;
    }

    public String form_num_compte(String num_compte){
        String [] tab_cmpte = num_compte.split(" ");
        String num_ret = null;
        if(tab_cmpte.length > 1){
            num_ret = tab_cmpte[0].substring(0, 4);
            for(int i=0; i<tab_cmpte.length ; i++){
                if(i>0){
                    String ss = tab_cmpte[i];
                    if(ss.length() > 4 ){
                        num_ret = num_ret+"_"+ ss.substring(0, 4);
                    }
                    else
                        num_ret = num_ret+"_"+ss;
                }
            }
        }
        else{
            String ss = tab_cmpte[0];
            if(ss.length() > 4 )
                num_ret = ss.substring(0, 4);
            else
                num_ret = ss;
                 
        }
        return num_ret.toUpperCase ();
    }

    public void setPopRepExisteCle(RichPopup popRepExisteCle) {
        this.popRepExisteCle = popRepExisteCle;
    }

    public RichPopup getPopRepExisteCle() {
        return popRepExisteCle;
    }

    public void setPopRepTypEtb(RichPopup popRepTypEtb) {
        this.popRepTypEtb = popRepTypEtb;
    }

    public RichPopup getPopRepTypEtb() {
        return popRepTypEtb;
    }

    public void setPopAucnRep(RichPopup popAucnRep) {
        this.popAucnRep = popAucnRep;
    }

    public RichPopup getPopAucnRep() {
        return popAucnRep;
    }

    @SuppressWarnings("unchecked")
    public void onDialogCmptEtab(DialogEvent dialogEvent) {
        // Add event code here...
        String type_cmpt = "4";
        String lib_compte = "Etablissement";
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            Integer result = inscCompteEtbDpt(type_cmpt, lib_compte);
            if(result > 0){
                compteEtablissement();
            }
            else
                sessionScope.put("cmpte_etab_deja_generer", 0);
            
        }
    }

    public void onDialogCanCmpteEtab(ClientEvent clientEvent) {
        // Add event code here...        
        this.getPopCommitCmpEtab().hide();
    }

    public void setPopCommitCmpEtab(RichPopup popCommitCmpEtab) {
        this.popCommitCmpEtab = popCommitCmpEtab;
    }

    public RichPopup getPopCommitCmpEtab() {
        return popCommitCmpEtab;
    }

    @SuppressWarnings("unchecked")
    public void genererCompteDept(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        //getCmpteEtabGen
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_cmpt_etb_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_cmpt_etb_gen = iter_cmpt_etb_gen.getCurrentRow();
        if(row_cmpt_etb_gen != null){
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
            
            Integer nb = 0;
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                while (rsi_rep_ex_cle.hasNext()) {
                    Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                    if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 5){
                       nb ++; 
                    }
                }
                if(nb > 0){
                    //ok
                    RichPopup popup = this.getPopCommitCmpDpt();                    
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                else{
                    // la rep ne possede pas de type etab
                    RichPopup popup = this.getPopRepTypDpt();                      
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                //verifier la def de rep
                RichPopup popup = this.getPopRepExisteCle();                       
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            //strc non rattachée à une rep
            RichPopup popup = this.getPopAucnRep();                     
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopRepTypDpt(RichPopup popRepTypDpt) {
        this.popRepTypDpt = popRepTypDpt;
    }

    public RichPopup getPopRepTypDpt() {
        return popRepTypDpt;
    }

    public void setPopCommitCmpDpt(RichPopup popCommitCmpDpt) {
        this.popCommitCmpDpt = popCommitCmpDpt;
    }

    public RichPopup getPopCommitCmpDpt() {
        return popCommitCmpDpt;
    }

    public void onDialogCmptDpt(DialogEvent dialogEvent) {
        // Add event code here...
        String type_cmpt = "5";
        String lib_compte = "Departement";
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            Integer result = inscCompteEtbDpt(type_cmpt, lib_compte);
            if(result > 0){
                compteDepartement();
            }
            else
                sessionScope.put("cmpte_dept_deja_generer", 0);
            
        }
    }

    public void onDialogCanCmpteDpt(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopCommitCmpDpt().hide();
    }

    public Integer nombreFormGenerer(){        
        Integer i = 0;
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        DCIteratorBinding iter_list_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_list_form_gen = iter_list_form_gen.getCurrentRow();
        RowSetIterator rsi_list_form_gen = iter_list_form_gen.getViewObject().createRowSetIterator(null);
        while(rsi_list_form_gen.hasNext()){ 
            Row rw_form = rsi_list_form_gen.next();
            OperationBinding op_cmpt_form_dja_gen = getBindings().getOperationBinding("getCompteFormDejaGen");
            op_cmpt_form_dja_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_form_dja_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_form_dja_gen.getParamsMap().put("id_form",  rw_form.getAttribute("IdFormation"));
            op_cmpt_form_dja_gen.execute();
            DCIteratorBinding iter_cmpt_form_dja_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteFormDjaGenererROIterator");
            Row row_cmpt_form_dja_gen = iter_cmpt_form_dja_gen.getCurrentRow();
            
            if(row_cmpt_form_dja_gen != null){
                i ++;
            }
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public void onGenererCmptForm(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");

        DCIteratorBinding iter_list_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_list_form_gen = iter_list_form_gen.getCurrentRow();

        if(row_list_form_gen != null){
            //getCompteFormDejaGen            
            OperationBinding op_cmpt_form_dja_gen = getBindings().getOperationBinding("getCompteFormDejaGen");
            op_cmpt_form_dja_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_form_dja_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_form_dja_gen.getParamsMap().put("id_form",  row_list_form_gen.getAttribute("IdFormation"));
            op_cmpt_form_dja_gen.execute();
            DCIteratorBinding iter_cmpt_form_dja_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteFormDjaGenererROIterator");
            Row row_cmpt_form_dja_gen = iter_cmpt_form_dja_gen.getCurrentRow();
            
            if(row_cmpt_form_dja_gen == null){
            
                OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
                op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
                op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
                op_cmpt_etb_gen.execute();
                DCIteratorBinding iter_cmpt_etb_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
                Row row_cmpt_etb_gen = iter_cmpt_etb_gen.getCurrentRow();
                if(row_cmpt_etb_gen != null){
                    OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
                    op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
                    op_rep_exist_cle.execute();
                    
                    Integer nb = 0;
                    if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                        DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                        RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                        while (rsi_rep_ex_cle.hasNext()) {
                            Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                            if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                               nb ++; 
                            }
                        }
                        if(nb > 0){
                            //ok
                            sessionScope.put("id_form_form_gene", row_list_form_gen.getAttribute("IdFormation"));
                            sessionScope.put("lib_form_form_gene", row_list_form_gen.getAttribute("LibForm"));
                            RichPopup popup = this.getPopCommitCmpForm();                    
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                        else{
                            // la rep ne possede pas de type form
                            RichPopup popup = this.getPopRepFormt()  ;                  
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                    }
                    else{
                        //verifier la def de rep
                        RichPopup popup = this.getPopRepExisteCle();                       
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    //strc non rattachée à une rep
                    RichPopup popup = this.getPopAucnRep();                     
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
               // form dja generer
               RichPopup popup = this.getPopFormExtDeja();                     
               RichPopup.PopupHints hints = new RichPopup.PopupHints();
               popup.show(hints);
            }
        }
        else{
            // Aucune form 
            RichPopup popup = this.getPopAucunForm();                    
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    @SuppressWarnings("unchecked")
    public String onGenererCmpteFormation() {
        // Add event code here...
        /*System.out.println("row78999 ");
        DCIteratorBinding iter_list_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_list_form_gen = iter_list_form_gen.getCurrentRow();
        System.out.println("row_list_form_gen45 "+row_list_form_gen);
        RowSetIterator rsi_list_form_gen = iter_list_form_gen.getViewObject().createRowSetIterator(null);*/
        
        
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");

        DCIteratorBinding iter_list_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_list_form_gen = iter_list_form_gen.getCurrentRow();
        RowSetIterator rsi_list_form_gen = iter_list_form_gen.getViewObject().createRowSetIterator(null);

        if(row_list_form_gen != null){   
            OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
            op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
            op_cmpt_etb_gen.execute();
            DCIteratorBinding iter_cmpt_etb_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
            Row row_cmpt_etb_gen = iter_cmpt_etb_gen.getCurrentRow();
            if(row_cmpt_etb_gen != null){
                OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
                op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
                op_rep_exist_cle.execute();
                
                Integer nb = 0;
                if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                    DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                    RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                    while (rsi_rep_ex_cle.hasNext()) {
                        Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                        if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                           || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                           || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                           nb ++; 
                        }
                    }
                    if(nb > 0){
                        //ok
                        Integer nb_gen = nombreFormGenerer();       // nombre de formations déjà générés
                        if(nb_gen == rsi_list_form_gen.getRowCount()){
                            //compte déjà généré pour toutes les formations de la structure
                            RichPopup popup = this.getPopTsCmpFormGen();                    
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                        else if(nb_gen < rsi_list_form_gen.getRowCount()){
                            sessionScope.put("nbr_form_gene", (rsi_list_form_gen.getRowCount()));
                            RichPopup popup = this.getPopCommitTsCmpForm();                    
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                    }
                    else{
                        // la rep ne possede pas de type form
                        RichPopup popup = this.getPopRepFormt()  ;                  
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    //verifier la def de rep
                    RichPopup popup = this.getPopRepExisteCle();                       
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                //strc non rattachée à une rep
                RichPopup popup = this.getPopAucnRep();                     
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

        }
        else{
            // Aucune form 
            RichPopup popup = this.getPopAucunForm();                    
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }        

        return null;
    }

    public void setPopRepFormt(RichPopup popRepFormt) {
        this.popRepFormt = popRepFormt;
    }

    public RichPopup getPopRepFormt() {
        return popRepFormt;
    }

    public void setPopFormExtDeja(RichPopup popFormExtDeja) {
        this.popFormExtDeja = popFormExtDeja;
    }

    public RichPopup getPopFormExtDeja() {
        return popFormExtDeja;
    }

    public void setPopCommitCmpForm(RichPopup popCommitCmpForm) {
        this.popCommitCmpForm = popCommitCmpForm;
    }

    public RichPopup getPopCommitCmpForm() {
        return popCommitCmpForm;
    }

    public void onDialogCmptForm(DialogEvent dialogEvent) {
        // Add event code here...
        Integer nb = 0;
        DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
        RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
        
        while (rsi_rep_ex_cle.hasNext()) {
            Row row_rep_ex_cle = rsi_rep_ex_cle.next();
            if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                             
               nb = nb + inscCompte(sessionScope.get("id_form_form_gene").toString(),sessionScope.get("lib_form_form_gene").toString(),row_rep_ex_cle.getAttribute("IdTypeCompte").toString(), row_rep_ex_cle.getAttribute("LibelleLong").toString());
            }
        }
        if(nb > 0)
            inscCompteGlobal(sessionScope.get("id_form_form_gene").toString(),sessionScope.get("lib_form_form_gene").toString(),"Formation Globale");
        
        compteFormation();
    }

    public void onDialogCanCmptForm(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopCommitCmpForm().hide();
    }
    @SuppressWarnings("unchecked")
    public Integer inscCompte(String id_form,String lib_form,String type_cmpt, String lib_compte){
        
        Integer nb_save = 0;
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");        
        //IdHistoriquesStructureForm
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); 
    
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        OperationBinding op_annee_passee = getBindings().getOperationBinding("getAnneeUniversPassee");
        op_annee_passee.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_passee.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversPasseeROIterator");
        Row row_set_annee_passee = iter_annee_passee.getCurrentRow();
    
        System.out.println("row_set_annee_passee "+row_set_annee_passee+" lib annee "+row_set_annee_passee.getAttribute("IdLibLongPasse")+"id_annee" +row_set_annee_passee.getAttribute("IdAnneePasse"));
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeAncien");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  row_set_annee_passee.getAttribute("IdAnneePasse"));
        op_soldeAncien.getParamsMap().put("id_type_cmpt",  type_cmpt);
        op_soldeAncien.getParamsMap().put("id_form",  id_form);
        op_soldeAncien.execute();
    
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return nb_save;
        }
        else{       
            
            if(Integer.parseInt(type_cmpt) == 6 || Integer.parseInt(type_cmpt) == 7 || Integer.parseInt(type_cmpt) == 8 ){
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                id_form_.setInputValue(id_form);
                proprietaire.setInputValue(lib_form);
            }
            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue(type_cmpt);//id_type_cmpte.
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return nb_save;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return nb_save;
                }
                else{                
                    DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeAnneeAncROIterator");
                    Row row_solde_anc = iter_solde_anc.getCurrentRow();
                    
                    if(row_solde_anc == null){
                        solde_int.setInputValue(0);
                        solde_act.setInputValue(0);
                    }
                    else{
                        id_annee_anc_type.setInputValue(row_set_annee_passee.getAttribute("IdAnneePasse"));
                        solde_int.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                        solde_act.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                    }
                    
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return nb_save;
                    }
                    else{
                        nb_save ++;
                    }
                }
            }
        }
        return nb_save;
    }
 
    @SuppressWarnings("unchecked")
    public Integer inscCompteEtud(String id_etud,String numero_etud,String type_cmpt, String lib_compte){
        
        Integer nb_save = 0;
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");        

        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_etud_att = (AttributeBinding) getBindings().getControlBinding("IdEtudiantCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return nb_save;
        }
        else{       
            
            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue(type_cmpt);//id_type_cmpte.
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            id_strc_att.setInputValue(id_strc.getInputValue());
            proprietaire.setInputValue(numero_etud);
            id_etud_att.setInputValue(id_etud);
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return nb_save;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return nb_save;
                }
                else{                
                    DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeAnneeAncROIterator");
                    Row row_solde_anc = iter_solde_anc.getCurrentRow();
                    
                    solde_int.setInputValue(0);
                    solde_act.setInputValue(0);                   
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return nb_save;
                    }
                    else{
                        nb_save ++;
                    }
                }
            }
        }
        return nb_save;
    }
    

    @SuppressWarnings("unchecked")
    public void inscCompteGlobal(String id_form,String lib_form,String lib_compte){
        
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); //LibNivForm
    
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
        Integer somme_solde_ini = 0;
        Integer somme_solde_act = 0;
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeCompteGlob");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  getAnne_univers());
        op_soldeAncien.getParamsMap().put("id_form",  id_form);
        op_soldeAncien.execute();
                
        DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeCompteGlobROIterator");
        Row row_solde = iter_solde_anc.getCurrentRow();
        RowSetIterator rsi = iter_solde_anc.getViewObject().createRowSetIterator(null);
    
        Integer i = 0;
        if(existeCompteForm("SoldeCompteGlobROIterator") > 0){
            OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
            Object result_cle_rep = op_insert_cle_rep.execute();
                                    
            if (!op_insert_cle_rep.getErrors().isEmpty()) {
                    return ;
            }
            else{                      
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                id_form_.setInputValue(id_form);
                proprietaire.setInputValue(lib_form);
                //next_seq
                OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
                Integer res_seq = (Integer)op_seq.execute();
                System.out.println("res_seq res_seq "+res_seq);
                id_type_cmpte.setInputValue("21");//compte global
                num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
                id_annee.setInputValue(getAnne_univers());
                id_util.setInputValue(getUtilisateur());
                
                OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
                Object result = op_commit_cle_rep.execute();
                if (!op_commit_cle_rep.getErrors().isEmpty()) {
                        return ;
                }
                else{
                    AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
        
                    AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                    AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                    AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                    AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                    AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                    AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
        
                    OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                    Object result_sold_anc = op_insert_sold_anc.execute();
                                            
                    if (!op_insert_cle_rep.getErrors().isEmpty()) {
                            return ;
                    }
                    else{                
                         while (rsi.hasNext()) {
                                Row row_solde_anc = rsi.next();
                                if(Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 6 || Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 7 || Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 8){ 
                                    somme_solde_ini = somme_solde_ini + Integer.parseInt(row_solde_anc.getAttribute("SoldeInitial").toString());
                                    somme_solde_act = somme_solde_act + Integer.parseInt(row_solde_anc.getAttribute("SoldeActuel").toString());
                                }
                                
                        }
    
                        id_annee_anc_type.setInputValue(row_solde.getAttribute("IdAnneesDern"));
                        solde_int.setInputValue(somme_solde_ini);
                        solde_act.setInputValue(somme_solde_act);
                        
                        id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                        id_annee_type.setInputValue(getAnne_univers());
                        id_util_type.setInputValue(getUtilisateur());
                        
                        OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                        Object result_commit_sold = op_commit_sold.execute();
                        if (!op_commit_sold.getErrors().isEmpty()) {
                                return ;
                        }
                        else{
                        }
                    }
                }
            }
        }
    }

    public Integer existeCompteForm(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 6 || Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 7 || Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 8){ 
                i++;
            }
        }
        return i;
    }

    public void setPopAucunForm(RichPopup popAucunForm) {
        this.popAucunForm = popAucunForm;
    }

    public RichPopup getPopAucunForm() {
        return popAucunForm;
    }
    
    
    @SuppressWarnings("unchecked")
    public Row recherche(){
        Row row_det_pers = null;
        if((String)this.getNumero_etud() == null && (String)this.getNumero_table() == null && (String)this.getNumero_cin() == null){

            if((String)this.getPrenom() == null && (String)this.getNom() == null 
                && (String)this.getDate_naiss() == null ){
                
                FacesMessage msg = new FacesMessage(" Veuillez saisir soit :");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro de Table"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro Etudiant"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro d'Identification Nationale (CIN)"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Les informations personnelles "));
            }

            else if((String)this.getPrenom() == null || (String)this.getNom() == null || (String)this.getDate_naiss() == null){
            
                FacesMessage msg = new FacesMessage(" Tous les champs sont obliqatoires :");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                if((String)this.getPrenom() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Prénom"));
                }
                if((String)this.getNom() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Nom"));
                }
                if((String)this.getDate_naiss() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Date Naissance"));
                }

            }
            else if((String)this.getPrenom() != null && (String)this.getNom() != null && (String)this.getDate_naiss() != null){

                // formatage de date de naiss
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                java.sql.Date date2 = null;
                try {
                    date = dateFormat.parse(getDate_naiss());
                    System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

                    String output = dateFormat.format(date);
                    System.out.println(output); // 2013-12-04
                } 
                catch (ParseException e) {
                    e.printStackTrace();
                }
                // si personne existe deja avec toutes les infos personnelles
                OperationBinding pers_existe = getBindings().getOperationBinding("getPersonneSearch");
                pers_existe.getParamsMap().put("nom_pers",  getNom());
                pers_existe.getParamsMap().put("prenom_pers",  getPrenom());
                pers_existe.getParamsMap().put("date_naiss",  getDate_naiss());
                pers_existe.execute();
                
                DCIteratorBinding iter_pers_existe = (DCIteratorBinding) getBindings().get("PersonneSearchSimpleIterator");
                Row cuurrent_row = iter_pers_existe.getCurrentRow();
                if(cuurrent_row != null){
                    row_det_pers = cuurrent_row;
                }
                else{
                    //la personne n existe pas
                    FacesMessage msg = new FacesMessage(" L'étudiant du :");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Prénom "+getPrenom()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Nom "+getNom()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Date de Naissance "+getDate_naiss()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" n'existe pas"));
                }
        }
            
        }
        else{
            if((String)this.getNumero_table() != null){
                OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString()));
                is_nouv_bac.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                Integer result = (Integer)is_nouv_bac.execute();
                //numéro table non valide
                if(result != 0){
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                    getIdPersEtudiant.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString()));
                    getIdPersEtudiant.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    row_det_pers = rsi_det_pers.first();
    
                }
                else{
                    FacesMessage msg = new FacesMessage(" Le numéro de table : "+getNumero_table()+" n'existe pas");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    setNumero_table("");
                    sessionScope.put("tableCpEtdNotNull", 0);
                }
    
            }
            else {
                if((String)this.getNumero_etud() != null ){
                    OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                    isEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());
                    
                    Integer result = (Integer)isEtudiant.execute();
                    //numéro étudiant non valide
                    if(result != 0){
                        //numéro étudiant valide
                        // recuperer id personn de l etudiant
                        OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                        getIdPersEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());                            
                        String result_getid = (String)getIdPersEtudiant.execute(); 
        
                        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                        detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                        detpers.execute();
                        
                        DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                            //Create RowSetIterato iterate over viewObject
                        RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                        row_det_pers = rsi_det_pers.first();
    
                    }
                    else{
                        FacesMessage msg = new FacesMessage(" Le numéro d'étudiant : "+getNumero_etud()+" n'existe pas");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        setNumero_etud("");
                        sessionScope.put("etudCpEtdNotNull", 0);
                    }
                }
                else {
                    if((String)this.getNumero_cin() != null ){
                        //vérifier le numéro d'identification
                        OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
                        isEtudiant.getParamsMap().put("num_cin",  (String)this.getNumero_cin());
                        
                        Integer result = (Integer)isEtudiant.execute();
                        // le numéro d'identification non valide            
                        if(result != 0){
                            // le numéro d'identification valide
                            // recuperer id personn de l etudiant
                            OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                            getIdPersEtudiant.getParamsMap().put("cin",  (String)this.getNumero_cin());  
                            String result_getid = (String)getIdPersEtudiant.execute(); 
        
                            OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                            detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                            detpers.execute();
                            
                            DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                                //Create RowSetIterato iterate over viewObject
                            RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                            row_det_pers = rsi_det_pers.first();
                        }
                        else{
                            FacesMessage msg = new FacesMessage(" Le numéro d'identification : "+getNumero_cin()+" n'existe pas");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            setNumero_cin("");
                            sessionScope.put("cinCpEtdNotNull", 0);
                        }
                    }
                }
            }
        }
        return row_det_pers;
    }


    @SuppressWarnings("unchecked")
    public void onChangeNumeroEtud(ClientEvent clientEvent) {
        // Add event code here...
        //System.out.println("value apres "+getNum_etud().getValue());
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        sessionScope.put("etudCpEtdNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("etudCpEtdNotNull", 1);
            try{
                OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                isEtudiant.getParamsMap().put("num_etud",  searchString);
                
                Integer result = (Integer)isEtudiant.execute();
                //numéro étudiant non valide
                if(result != 0){
                    //numéro étudiant valide
                    // recuperer id personn de l etudiant
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                    getIdPersEtudiant.getParamsMap().put("num_etud",  searchString);                            
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    @SuppressWarnings("unchecked")
    public void onChangeNumeroCin(ClientEvent clientEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        sessionScope.put("cinCpEtdNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("cinCpEtdNotNull", 1);
            try{
                //vérifier le numéro d'identification
                OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
                isEtudiant.getParamsMap().put("num_cin",  searchString);
                
                Integer result = (Integer)isEtudiant.execute();
                // le numéro d'identification non valide            
                if(result != 0){
                    // le numéro d'identification valide
                    // recuperer id personn de l etudiant
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                    getIdPersEtudiant.getParamsMap().put("cin",  searchString);  
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    System.out.println("result_getid" +result_getid);
                    sessionScope.put("idpers", Long.parseLong(result_getid));
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    @SuppressWarnings("unchecked")
    public void onChangeNumeroTable(ClientEvent clientEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString "+searchString);
        sessionScope.put("tableCpEtdNotNull", 0);
        sessionScope.put("etudCpEtdNotNull", 0);
        //tableNotNull
        //num_etud.setValue("");
        if(searchString != ""){
            //num_etud.setValue("");
            sessionScope.put("tableCpEtdNotNull", 1);
            try{
                OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong(searchString));
                is_nouv_bac.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                
                Integer result = (Integer)is_nouv_bac.execute();
                //numéro table non valide
                if(result != 0){
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                    getIdPersEtudiant.getParamsMap().put("num_table",  Long.parseLong(searchString)); 
                    getIdPersEtudiant.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    @SuppressWarnings("unchecked")
    public Integer getEtudiantBu(String id_etud, String id_annee) {
        AttributeBinding id_strct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        Integer result = 0;
        OperationBinding op_etud_bu = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudBuRech");
        op_etud_bu.getParamsMap().put("id_struct",  id_strct.getInputValue());
        op_etud_bu.getParamsMap().put("id_etud", id_etud);
        op_etud_bu.getParamsMap().put("id_annee", id_annee);
        op_etud_bu.execute();
        DCIteratorBinding iter_etud_bu = (DCIteratorBinding) getBindings().get("EtudBuRechROIterator");
        Row currentRow_etud_bu = iter_etud_bu.getCurrentRow();
        if (currentRow_etud_bu == null) {
        return result;
        }
        else{
            if(currentRow_etud_bu.getAttribute("EnRegle") == null){
                return result;
            }
            else{
                if(Integer.parseInt(currentRow_etud_bu.getAttribute("EnRegle").toString()) == 1){
                    result = 1;
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getApteEtudiant(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm)
        Integer result = 0;

        OperationBinding opNote = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudApteRech");
        opNote.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        opNote.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EtudApteRechROIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return result;
        }
        else{   
            if(currentRow.getAttribute("Apte") == null){
                return result;
            }
            else{
                if (Integer.parseInt(currentRow.getAttribute("Apte").toString()) == 1) {
                    result = 1;
                    return result;
                }
                else
                    return result;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getResponsableEtud(String id_etud){    
        
        Integer result = 0;
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResponsableEtud");
        op_resp_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_resp_etud.execute();
        DCIteratorBinding iter_resp_etud = (DCIteratorBinding) getBindings().get("ResponsableROIterator");
        Row currentRow_resp_etud = iter_resp_etud.getCurrentRow();
        if (currentRow_resp_etud == null) {
            return result;
        }
        else{
            result = 1;
            return result;         
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getInscEnLigne(String id_insc_ped){
        Integer result = 0;
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
        op_resp_etud.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
        op_resp_etud.execute();
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscPedagogiqueROIterator");
        Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
        if (currentRow_insc_ped == null ) {
            return result;
        }
        else{
            if (currentRow_insc_ped.getAttribute("InsEnLigne") == null ) {
                return result;
            }
            else{
                // inscription validée si InsEnLigne (oui : 1) 
                if (Integer.parseInt(currentRow_insc_ped.getAttribute("InsEnLigne").toString()) == 1) {
                    result = 1;
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getEtatInscBis(String id_insc_ped){
       Integer result = 0;
       OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
       op_resp_etud.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
       op_resp_etud.execute();
       DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscPedagogiqueROIterator");
       Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
       if (currentRow_insc_ped == null ) {
           return result;
       }
       else{
            if(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId") == null){
                return result;
            }
            else{              
                if(Long.parseLong(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId").toString()) == 22){
                    result = 1;
                    return result;
                }
                else{                                 
                   return result;
                }
            }
       }
    }

    public Integer getEtatInscEtudAnn(String inscPed , String id_etud,  String id_annee){    
        Integer result = 0 ;
        Integer etud_bu = getEtudiantBu(id_etud, id_annee);
        Integer etud_apte = getApteEtudiant(id_etud, id_annee);
        Integer etud_resp = getResponsableEtud(id_etud);
        Integer etud_insc_ligne = getInscEnLigne(inscPed);
        Integer etud_etat_insc = getEtatInscBis(inscPed);
    
    
        System.out.println("etud_bu "+etud_bu);
        System.out.println("etud_apte "+etud_apte);
        System.out.println("etud_resp "+etud_resp);
        System.out.println("etud_insc_ligne "+etud_insc_ligne);
    
        if(etud_bu == 1 && etud_apte == 1 && etud_resp == 1 &&  etud_insc_ligne == 1 ){        
            result = 1 ;
        }       
        return result ;
    }

    @SuppressWarnings("unchecked")
    public void onGenererCompteEtud(ActionEvent actionEvent) {
        // Add event code here...
        Row rw = recherche();
        if(rw != null){
            getRowPers(rw);
        
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  rw.getAttribute("IdPersonne").toString());
            list_insc_ped.getParamsMap().put("id_annee",  getAnne_univers());
            list_insc_ped.execute();
            DCIteratorBinding iter_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_insc_ped = iter_insc_ped.getCurrentRow();
            if(row_insc_ped != null){
                Integer res_etat_insc = getEtatInscEtudAnn(row_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                if(res_etat_insc == 1){
                    insertPaieMensualite(row_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                    AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
                    //getCmpteEtabGen
                    OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                    op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                    op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_insc_ped.getAttribute("IdEtudiant"));
                    op_cmpt_etud_gen.execute();
                    DCIteratorBinding iter_cmpt_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_form_gen = iter_cmpt_form_gen.getCurrentRow();
                    System.out.println("row_cmpt_form_gen "+row_cmpt_form_gen);

                    if(row_cmpt_form_gen == null){
                        String type_cmpte = "9";   //pour compte de Etudiant
                        String lib_etud = "Etudiant";
                        Integer nb_cmpte = inscCompteEtud(row_insc_ped.getAttribute("IdEtudiant").toString(),row_insc_ped.getAttribute("Numero").toString(),type_cmpte,lib_etud);
                        if(nb_cmpte > 0){
                            
                            RichPopup popup = this.getPopCmptEtudCommit();                  
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                            
                            OperationBinding op_cmpt_etud_gen_ = getBindings().getOperationBinding("getCompteEtudGen");
                            op_cmpt_etud_gen_.getParamsMap().put("id_annee",  getAnne_univers());
                            op_cmpt_etud_gen_.getParamsMap().put("id_strct",  id_struct.getInputValue());
                            op_cmpt_etud_gen_.getParamsMap().put("id_etud",  row_insc_ped.getAttribute("IdEtudiant"));
                            op_cmpt_etud_gen_.execute();
                        }
                    }
                    else{
                        // compte deja généré
                        RichPopup popup = this.getPopEtudGen();                   
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    //Insc ped n'est pas definitive
                    //insertPaieMensualite(row_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                    String inscPed = row_insc_ped.getAttribute("IdInscriptionPedagogique").toString();
                    String id_etud = row_insc_ped.getAttribute("IdEtudiant").toString();
                    Integer etud_bu = getEtudiantBu(id_etud, getAnne_univers());
                    Integer etud_apte = getApteEtudiant(id_etud, getAnne_univers());
                    Integer etud_resp = getResponsableEtud(id_etud);
                    Integer etud_insc_ligne = getInscEnLigne(inscPed);
                    Integer etud_etat_insc = getEtatInscBis(inscPed);
                    
                    if(etud_bu == 0 || etud_apte == 0 || etud_resp == 0 ||  etud_insc_ligne == 0 ){        
                        if(etud_bu == 0 ){
                            sessionScope.put("etud_bu_enRegle_cmpt", "==> En règle avec la BU : Non");
                        }
                        else{
                            sessionScope.put("etud_bu_enRegle_cmpt", "");
                        }
                        if(etud_insc_ligne == 0){
                                sessionScope.put("etud_enLigne_cmpt", "==> Inscription en ligne : Non");
                        }
                        else{
                                sessionScope.put("etud_enLigne_cmpt", "");
                        }
                        if(etud_apte == 0){
                            sessionScope.put("etud_Apte_cmpt", "==> Apte : Non");
                        }
                        else{
                            sessionScope.put("etud_Apte_cmpt", "");
                        }
                        
                        if(etud_resp == 0){
                            sessionScope.put("etud_Respnsable_cmpt", "==> Le responsable( Tuteur) : Non");
                        }
                        else{
                            sessionScope.put("etud_Respnsable_cmpt", "");
                        }
                    } 
                    
                    sessionScope.put("etud_num_etud_cmpte", row_insc_ped.getAttribute("Numero"));
                    
                    RichPopup popup = this.getPopEtatInscNotVal();                    
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                //L etud n'est pas inscrit
                RichPopup popup = this.getPopEtudNotInsc();                     
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }

    public void setPopEtudNotInsc(RichPopup popEtudNotInsc) {
        this.popEtudNotInsc = popEtudNotInsc;
    }

    public RichPopup getPopEtudNotInsc() {
        return popEtudNotInsc;
    }

    public void setPopEtudNotDef(RichPopup popEtudNotDef) {
        this.popEtudNotDef = popEtudNotDef;
    }

    public RichPopup getPopEtudNotDef() {
        return popEtudNotDef;
    }

    public void setPopEtudGen(RichPopup popEtudGen) {
        this.popEtudGen = popEtudGen;
    }

    public RichPopup getPopEtudGen() {
        return popEtudGen;
    }

    public void setPopCmptEtudCommit(RichPopup popCmptEtudCommit) {
        this.popCmptEtudCommit = popCmptEtudCommit;
    }

    public RichPopup getPopCmptEtudCommit() {
        return popCmptEtudCommit;
    }

    @SuppressWarnings("unchecked")
    public void onGenererCompteTousEtud(ActionEvent actionEvent) {
        // Add event code here...
        Integer nb_insc_def = 0;
        Integer nb_cmpt_gen = 0;
        Integer nb_cmpt_dja = 0;
        //Integer nb_insc_def = 0;
        OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
        op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
        op_form_hs.execute();
        DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
        Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
        if(row_op_form_hs != null){
            //getTousEtudInscParcAnn
            OperationBinding op_tous_etud_insc = getBindings().getOperationBinding("getTousEtudInscParcAnn");
            op_tous_etud_insc.getParamsMap().put("id_annee",  getAnne_univers());
            op_tous_etud_insc.getParamsMap().put("id_parc",  row_op_form_hs.getAttribute("IdParcoursMaquetAnnee"));
            op_tous_etud_insc.execute();
            DCIteratorBinding iter_op_tous_etud_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InsCompteEtudGenAllROIterator");
            Row row_op_tous_etud_insc = iter_op_tous_etud_insc.getCurrentRow();
            RowSetIterator rsi_op_tous_etud_insc = iter_op_tous_etud_insc.getViewObject().createRowSetIterator(null);
            
            if(row_op_tous_etud_insc != null){
            
                while (rsi_op_tous_etud_insc.hasNext()) {
    
                    Row row_etud_insc = rsi_op_tous_etud_insc.next();
                    Integer res_etat_insc = getEtatInscEtudAnn(row_etud_insc.getAttribute("IdInscriptionPedagogique").toString(), row_etud_insc.getAttribute("IdEtudiant").toString(), getAnne_univers());
                    if(res_etat_insc == 1){
                        insertPaieMensualite(row_etud_insc.getAttribute("IdInscriptionPedagogique").toString(), row_etud_insc.getAttribute("IdEtudiant").toString(), getAnne_univers());
                        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
                        //getCmpteEtabGen
                        OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                        op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                        op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                        op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_etud_insc.getAttribute("IdEtudiant"));
                        op_cmpt_etud_gen.execute();
                        DCIteratorBinding iter_cmpt_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                        Row row_cmpt_form_gen = iter_cmpt_form_gen.getCurrentRow();
                        System.out.println("row_cmpt_form_gen "+row_cmpt_form_gen);
                        if(row_cmpt_form_gen == null){
                         
                            OperationBinding op_getPaieEtud = getBindings().getOperationBinding("getPaieEtudGene");
                            op_getPaieEtud.getParamsMap().put("id_insc_ped",  row_etud_insc.getAttribute("IdInscriptionPedagogique"));
                            op_getPaieEtud.getParamsMap().put("id_annee",  getAnne_univers());
                            op_getPaieEtud.getParamsMap().put("id_etud",  row_etud_insc.getAttribute("IdEtudiant"));
                            op_getPaieEtud.execute();
                            DCIteratorBinding iter_op_paie_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEtudGenROIterator");
                            Row row_op_paie_etud =  iter_op_paie_etud.getCurrentRow();
                            if(row_op_paie_etud != null){
                                
                                String type_cmpte = "9";   //pour compte de Etudiant
                                String lib_etud = "Etudiant";
                                Integer nb_cmpte = inscCompteEtud(row_etud_insc.getAttribute("IdEtudiant").toString(),row_etud_insc.getAttribute("Numero").toString(),type_cmpte,lib_etud);
                                if(nb_cmpte > 0){
                                    
                                    nb_cmpt_gen ++ ;
        
                                }
                            }
                        }
                        else{
                            // compte deja généré
                            nb_cmpt_dja ++ ;
                        }
                    }
                    else{
                        //Insc ped n'est pas definitive
                        nb_insc_def ++;
                    }
                }
                // confirmation
                setNbr_cmpt_deja(nb_cmpt_dja+" / "+rsi_op_tous_etud_insc.getRowCount());
                setNbr_cmpt_gen(nb_cmpt_gen+" / "+rsi_op_tous_etud_insc.getRowCount());
                setNbr_def(nb_insc_def+" / "+rsi_op_tous_etud_insc.getRowCount());
                
                RichPopup popup = this.getPopConfEtudInsc();                     
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }
            else{
                //Aucun etudiant inscrit dans le niveau de formation
                RichPopup popup = this.getPopAucunEtudInsc();                     
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void insertPaieMensualite(String id_insc_ped,String id_etud,String id_annee){
        
        Row row_regle_pay = null;
        
        OperationBinding op_getPaieEtud = getBindings().getOperationBinding("getPaieEtudGene");
        op_getPaieEtud.getParamsMap().put("id_insc_ped",  id_insc_ped);
        op_getPaieEtud.getParamsMap().put("id_annee",  id_annee);
        op_getPaieEtud.getParamsMap().put("id_etud",  id_etud);
        op_getPaieEtud.execute();
        DCIteratorBinding iter_op_paie_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEtudGenROIterator");
        Row row_op_paie_etud =  iter_op_paie_etud.getCurrentRow();
        if(row_op_paie_etud == null){
            OperationBinding op_getEtatInscPed = getBindings().getOperationBinding("getInscPedFormPay");
            op_getEtatInscPed.getParamsMap().put("id_ped",  id_insc_ped);
            op_getEtatInscPed.execute();
            DCIteratorBinding iter_op_res_der = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedFormPayEcolROIterator");
            Row row_form_pay =  iter_op_res_der.getCurrentRow();
            if(row_form_pay != null){
                OperationBinding op_getFormMod = getBindings().getOperationBinding("getPaieEchelonModForm");
                op_getFormMod.getParamsMap().put("id_annee",  id_annee);
                op_getFormMod.getParamsMap().put("id_form",  row_form_pay.getAttribute("IdFormation"));
                op_getFormMod.getParamsMap().put("id_niv_form",  row_form_pay.getAttribute("IdNiveauFormation"));
                op_getFormMod.execute();
                DCIteratorBinding iter_op_form_mod = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEchelonEcolModFormROIterator");
                Row row_op_form_mod =  iter_op_form_mod.getCurrentRow();
                
                OperationBinding op_getExoEcolage = getBindings().getOperationBinding("getExoEcolage");
                op_getExoEcolage.getParamsMap().put("id_annee",  id_annee);
                op_getExoEcolage.getParamsMap().put("id_ped",  id_insc_ped);
                op_getExoEcolage.execute();
                DCIteratorBinding iter_op_exo_ecol = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ExonerationEcolageROIterator");
                Row row_op_exo_ecol =  iter_op_exo_ecol.getCurrentRow();
                
                if(row_op_form_mod != null){ 
                    AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiantPaie");
                    AttributeBinding id_form_paie = (AttributeBinding) getBindings().getControlBinding("IdFormationPaie");
                    AttributeBinding id_type_paie = (AttributeBinding) getBindings().getControlBinding("IdTypePaiement");
                    AttributeBinding id_annee_univers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversPaie");
                    AttributeBinding id_echelon_paie = (AttributeBinding) getBindings().getControlBinding("IdEchelonPaiement");
                    AttributeBinding montant_paie = (AttributeBinding) getBindings().getControlBinding("MontantPaie");
                    AttributeBinding net_a_payer = (AttributeBinding) getBindings().getControlBinding("NetAPayer");
                    AttributeBinding montant_exon = (AttributeBinding) getBindings().getControlBinding("MontantExonerationPaie");
                    AttributeBinding ref = (AttributeBinding) getBindings().getControlBinding("RefExoPaie");
                    AttributeBinding id_insc_ped_paie = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiquePaie");
                   //AnnulePaie
                    AttributeBinding annule_paie = (AttributeBinding) getBindings().getControlBinding("AnnulePaie");
                    AttributeBinding reparti_paie = (AttributeBinding) getBindings().getControlBinding("RepartiPaie");
                    AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreePaie");
                    
                    AttributeBinding complet = (AttributeBinding) getBindings().getControlBinding("Complet");
                    AttributeBinding etat = (AttributeBinding) getBindings().getControlBinding("Etat");
                    AttributeBinding paye = (AttributeBinding) getBindings().getControlBinding("Paye");
                    
                    DCIteratorBinding iter_op_form_mod1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PaieEchelonEcolModFormROIterator");
                    RowSetIterator rsi_op_form_mod = iter_op_form_mod1.getViewObject().createRowSetIterator(null);

                    while (rsi_op_form_mod.hasNext()) {
                        Integer montant = 0;
                        Integer montant_net_payer = 0;
                        Object montant_exo = 0;
                        Object ref_exo = null;
                        Row next_echol = rsi_op_form_mod.next(); System.out.println("nationalite avant");
                        
                        // IdPaysNationalite: 23 nationalite senegalaise
                        if(Integer.parseInt(row_form_pay.getAttribute("IdPaysNationalite").toString()) == 23){
                            montant = Integer.parseInt(next_echol.getAttribute("Montant").toString());
                        }
                        else{
                            montant = Integer.parseInt(next_echol.getAttribute("MontantEtr").toString());
                        }       

                        if(row_op_exo_ecol != null){
                            //pour les pupilles de la naion
                            // if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdMotifExoneration").toString()) == 1)
                            // Par monatant : 2 et Par taux : 1
                            if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());System.out.println("parti 1 ");
                                montant_net_payer = (montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString()));
                                ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                    montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);System.out.println("parti 2 ");
                                    montant_net_payer = (montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100));
                                    ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdMotifExoneration").toString()) == 1 ){
                                    montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);System.out.println("parti 3 ");
                                    montant_net_payer = (montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100));
                                    ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                            }
                            else{
                                montant_net_payer = montant;
                            }
                        }
                        else{
                            montant_net_payer = montant;
                        }

                        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertPaie");
                        Object result_cle_rep = op_insert_cle_rep.execute();
                                                
                        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                                return ;
                        }
                        else{
                            id_etudiant.setInputValue(id_etud);
                            id_insc_ped_paie.setInputValue(id_insc_ped);
                            id_form_paie.setInputValue(next_echol.getAttribute("IdFormation"));
                            
                            if(Integer.parseInt(next_echol.getAttribute("Ordre").toString()) == 0){
                                id_type_paie.setInputValue("1");        // 1: Droits Administratifs
                                if(row_op_exo_ecol != null){
                                    if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                        montant_net_payer = montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                            montant_net_payer = montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 2){
                                        montant_net_payer = montant - Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        montant_exo = Integer.parseInt(row_op_exo_ecol.getAttribute("Montant").toString());
                                        ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else if(Integer.parseInt(row_op_exo_ecol.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(row_op_exo_ecol.getAttribute("IdNatureExoneration").toString()) == 1){
                                            montant_net_payer = montant - ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            montant_exo = ((Integer.parseInt(row_op_exo_ecol.getAttribute("Taux").toString())*montant)/100);
                                            ref_exo = row_op_exo_ecol.getAttribute("RefExo");
                                    }
                                    else{
                                        montant_net_payer = montant;
                                    }
                                }
                                else{
                                    montant_net_payer = montant;
                                }
                            }
                            else{
                                id_type_paie.setInputValue("3");        // 3: Frais de Scolarite
                                
                            }
                            reparti_paie.setInputValue("0");
                            annule_paie.setInputValue("0");
                            id_annee_univers.setInputValue(id_annee);
                            id_echelon_paie.setInputValue(next_echol.getAttribute("IdEchelonPaiement"));
                            montant_paie.setInputValue(montant);
                            net_a_payer.setInputValue(montant_net_payer);
                            montant_exon.setInputValue(montant_exo);
                            
                            ref.setInputValue(ref_exo);
                            id_util.setInputValue(getUtilisateur());
                           
                            if(row_op_exo_ecol != null){
                                if(montant_net_payer == 0){
                                    complet.setInputValue(1);
                                    etat.setInputValue(1);
                                    paye.setInputValue(1);
                                }
                                else{
                                }
                            }
                            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
                            Object result = op_commit_cle_rep.execute();
                            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                                    return ;
                            }
                            else{
                            } 
                        }
                    }
                    
                }
                else{
                    // veuillez vérifier formation mod et echelonage de paiement
                }
            }
            else{
                // pas de form payante
            }
        }
        //return row_regle_pay;
    }

    public void setPopAucunEtudInsc(RichPopup popAucunEtudInsc) {
        this.popAucunEtudInsc = popAucunEtudInsc;
    }

    public RichPopup getPopAucunEtudInsc() {
        return popAucunEtudInsc;
    }

    public void setPopConfEtudInsc(RichPopup popConfEtudInsc) {
        this.popConfEtudInsc = popConfEtudInsc;
    }

    public RichPopup getPopConfEtudInsc() {
        return popConfEtudInsc;
    }

    public void setPopEtatInscNotVal(RichPopup popEtatInscNotVal) {
        this.popEtatInscNotVal = popEtatInscNotVal;
    }

    public RichPopup getPopEtatInscNotVal() {
        return popEtatInscNotVal;
    }

    public void setPopTsCmpFormGen(RichPopup popTsCmpFormGen) {
        this.popTsCmpFormGen = popTsCmpFormGen;
    }

    public RichPopup getPopTsCmpFormGen() {
        return popTsCmpFormGen;
    }

    public void setPopCommitTsCmpForm(RichPopup popCommitTsCmpForm) {
        this.popCommitTsCmpForm = popCommitTsCmpForm;
    }

    public RichPopup getPopCommitTsCmpForm() {
        return popCommitTsCmpForm;
    }

    @SuppressWarnings("unchecked")
    public void onDialogCmmTsCmpForm(DialogEvent dialogEvent) {
        // Add event code here...popCommitTsCmpForm
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        
        DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
        RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
        
        DCIteratorBinding iter_list_form_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteFormROIterator");
        Row row_list_form_gen = iter_list_form_gen.getCurrentRow();
        RowSetIterator rsi_list_form_gen = iter_list_form_gen.getViewObject().createRowSetIterator(null);
        while(rsi_list_form_gen.hasNext()){ 
            Row rw_form = rsi_list_form_gen.next();
            OperationBinding op_cmpt_form_dja_gen = getBindings().getOperationBinding("getCompteFormDejaGen");
            op_cmpt_form_dja_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_form_dja_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_form_dja_gen.getParamsMap().put("id_form",  rw_form.getAttribute("IdFormation"));
            op_cmpt_form_dja_gen.execute();
            DCIteratorBinding iter_cmpt_form_dja_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteFormDjaGenererROIterator");
            Row row_cmpt_form_dja_gen = iter_cmpt_form_dja_gen.getCurrentRow();
            
            if(row_cmpt_form_dja_gen == null){
                Integer nb = 0;
                while (rsi_rep_ex_cle.hasNext()) {
                    Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                    if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                                     
                       nb = nb + inscCompte(rw_form.getAttribute("IdFormation").toString(),rw_form.getAttribute("LibForm").toString(),row_rep_ex_cle.getAttribute("IdTypeCompte").toString(), row_rep_ex_cle.getAttribute("LibelleLong").toString());
                    }
                }
                if(nb > 0)
                    inscCompteGlobal(rw_form.getAttribute("IdFormation").toString(),rw_form.getAttribute("LibForm").toString(),"Formation Globale");
                
            }
            //else
        }
        compteFormation();
    }

    public void onDialogCanCmmTsCmpForm(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopCommitTsCmpForm().hide();
    }
}
