package saisie_resultat;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import model.readOnlyView.SaisieResultModImpl;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class SaisieResultBean {
    private RichTable table_resultat;
    private RichInputText credit;
    private String credit_ob;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    
    public SaisieResultBean() {
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

    public void setCredit_ob(String credit_ob) {
        this.credit_ob = credit_ob;
    }

    public String getCredit_ob() {
        return credit_ob;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public String getRes(String c){
        return c;
    }
    public static DCIteratorBinding getTableIterator(RichTable table) {
        DCIteratorBinding treeIterator = null;
        CollectionModel model = (CollectionModel)table.getValue();
        if (model != null) {
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
            if (treeBinding != null) {
                treeIterator = treeBinding.getDCIteratorBinding();
            }
        }
        return treeIterator;
    }

    @SuppressWarnings("unchecked")
    public String getDetailInscPed(String id_parc, String id_grade, String id_type_form, String id_annee,String id_etud){
        String ins_ped = null;
        OperationBinding getIsteEtudiantInsc = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailInsPed");
        getIsteEtudiantInsc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));   
        getIsteEtudiantInsc.getParamsMap().put("id_grade",  Long.parseLong(id_grade));  
        getIsteEtudiantInsc.getParamsMap().put("id_type_form",  Long.parseLong(id_type_form));  
        getIsteEtudiantInsc.getParamsMap().put("id_annee",  Long.parseLong(id_annee));  
        getIsteEtudiantInsc.getParamsMap().put("id_etud",  Long.parseLong(id_etud)); 
        getIsteEtudiantInsc.getParamsMap().put("id_etat_insc",  Long.parseLong("22"));      // 22 pour les etudiants ayant commme etat insc definitif
        getIsteEtudiantInsc.execute();
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListePersSaisieResultatROIterator");        
        if(iter.getCurrentRow() != null)
            ins_ped = iter.getCurrentRow().getAttribute("IdInscriptionPedagogique").toString();
        return ins_ped;
    }

    @SuppressWarnings("unchecked")
    public Row getRowInscPed(String id_parc, String id_grade, String id_type_form, String id_annee,String id_etud){
        OperationBinding getIsteEtudiantInsc = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailInsPed");
        getIsteEtudiantInsc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));   
        getIsteEtudiantInsc.getParamsMap().put("id_grade",  Long.parseLong(id_grade));  
        getIsteEtudiantInsc.getParamsMap().put("id_type_form",  Long.parseLong(id_type_form));  
        getIsteEtudiantInsc.getParamsMap().put("id_annee",  Long.parseLong(id_annee));  
        getIsteEtudiantInsc.getParamsMap().put("id_etud",  Long.parseLong(id_etud)); 
        getIsteEtudiantInsc.getParamsMap().put("id_etat_insc",  Long.parseLong("22"));      // 22 pour les etudiants ayant commme etat insc definitif
        getIsteEtudiantInsc.execute();
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListePersSaisieResultatROIterator");        
        return iter.getCurrentRow();
    }

    @SuppressWarnings("unchecked")
    public void getListeEtudiantInsc(String id_parc, String id_grade, String id_type_form, String id_annee){
        OperationBinding getIsteEtudiantInsc = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getIsteEtudiantInsc");
        getIsteEtudiantInsc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));   
        getIsteEtudiantInsc.getParamsMap().put("id_grade",  Long.parseLong(id_grade));  
        getIsteEtudiantInsc.getParamsMap().put("id_type_form",  Long.parseLong(id_type_form));  
        getIsteEtudiantInsc.getParamsMap().put("id_annee",  Long.parseLong(id_annee));  
        getIsteEtudiantInsc.getParamsMap().put("id_etat_insc",  Long.parseLong("22"));      // 22 pour les etudiants ayant commme etat insc definitif
        getIsteEtudiantInsc.execute();
               
    }
    
    public String getAnneeSup(String annee){
        String lib_anne = "";
        
        OperationBinding op_double_insc = getBindings().getOperationBinding("getAnneeSup");
        op_double_insc.getParamsMap().put("id_annee",  Long.parseLong(annee));       
        op_double_insc.execute();
        
        DCIteratorBinding iter_annee_sup = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversSupROIterator");
        Row row_annee_sup = iter_annee_sup.getCurrentRow();
        //table_resultat.
        if(row_annee_sup != null)
            lib_anne = row_annee_sup.getAttribute("IdLibLongAnneSup").toString();      
        return lib_anne;
    }

    public void setTable_resultat(RichTable table_resultat) {
        this.table_resultat = table_resultat;
    }

    public RichTable getTable_resultat() {
        return table_resultat;
    }

    @SuppressWarnings("unchecked")
    public String onValiderSaisie() {
        // Add event code here...
        //Niveau1
        AttributeBinding id_parc_sup = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneeSup");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneSup");

        AttributeBinding id_annee_anc = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding id_niveau = (AttributeBinding) getBindings().getControlBinding("IdNiveau1");
        
        //Niveau
        AttributeBinding niveau = (AttributeBinding) getBindings().getControlBinding("Niveau");
        
        AttributeBinding id_grade = (AttributeBinding) getBindings().getControlBinding("IdGrade");
        AttributeBinding id_type_form = (AttributeBinding) getBindings().getControlBinding("IdTypeFormation");
        
        DCIteratorBinding iter_res = (DCIteratorBinding) getBindings().get("ResultatIterator");        
        RowSetIterator rsi_res = iter_res.getViewObject().createRowSetIterator(null);
        //NivFormSaisieResultRO
        Integer nbr_insc = 0;
        Integer nbr_insc_red = 0;
        Integer nbr_mod = 0;
        if(rsi_res.getRowCount() == 0){
            FacesMessage msg = new FacesMessage(" Aucune inscription pédagogique à valider");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if(id_parc_sup.getInputValue() == null){
                FacesMessage msg = new FacesMessage(" Impossible d'inscrire ces "+rsi_res.getRowCount()+" étudiant(s)");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le niveau Formation à s'inscrire n'est pas défini "));
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                if(niveau.getInputValue() != null && (Long.parseLong(niveau.getInputValue().toString()) == 3 || Long.parseLong(niveau.getInputValue().toString()) == 5)){
                }
                else{
                    String lib_form_anc = ((AttributeBinding) getBindings().getControlBinding("NivFormSaisieResultRO")).getInputValue().toString();
                    String lib_form_sup = ((AttributeBinding) getBindings().getControlBinding("ResultParcoursSupRO")).getInputValue().toString();
                      
                    OperationBinding getResult = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResult");
                    getResult.getParamsMap().put("id_niveau",  Long.parseLong(id_niveau.getInputValue().toString()));   
                    getResult.getParamsMap().put("id_annee",  Long.parseLong(id_annee_anc.getInputValue().toString())); 
                    getResult.execute();
                    
                    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ResultatIterator");        
                    RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                    
                    while (rsi.hasNext()) {
                        Row nextRow = rsi.next();
                        if(nextRow.getAttribute("IdTypeResultat") != null && nextRow.getAttribute("Credit") != null && nextRow.getAttribute("IdSession") != null && nextRow.getAttribute("IdTypeMention") != null){ 
                            if((Long.parseLong(nextRow.getAttribute("IdTypeResultat").toString()) == 1 && Long.parseLong(nextRow.getAttribute("Credit").toString()) == 60) || Long.parseLong(nextRow.getAttribute("Credit").toString()) >= 42){
                                //IdInscriptionPedagogiqueSup
                                System.out.println("IdInscriptionPedagogiqueSup "+nextRow.getAttribute("IdInscriptionPedagogiqueSup"));
                                if(nextRow.getAttribute("IdInscriptionPedagogiqueSup") == null){
                                    String id_insc_ped = inscription(id_parc_sup.getInputValue().toString(), id_annee.getInputValue().toString(), nextRow.getAttribute("IdEtudiant").toString(), nextRow.getAttribute("nationalite").toString());
                                    OperationBinding op_resultat = getBindings().getOperationBinding("findAndUpdateResultat");
                                    op_resultat.getParamsMap().put("id_resultat",  Long.parseLong(nextRow.getAttribute("IdResultat").toString()));
                                    op_resultat.getParamsMap().put("insc_ped_sup",  Long.parseLong(id_insc_ped));          
                                    op_resultat.execute();
                                    nbr_insc ++;
                                }
                                else{
                                    System.out.println("deja insc au niveau formation supérieure");
                                    OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                                    op_commit.execute();
                                }
                                //
                            }
                            else{
                                //
                                AttributeBinding id_parc_maq = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
                                System.out.println("detailInscPed apres "+nextRow.getAttribute("IdInscriptionPedagogiqueSup"));
                                if(nextRow.getAttribute("IdInscriptionPedagogiqueSup") == null){
                                    String id_insc_ped = inscription(id_parc_maq.getInputValue().toString(), id_annee.getInputValue().toString(), nextRow.getAttribute("IdEtudiant").toString(), nextRow.getAttribute("nationalite").toString());
                                    OperationBinding op_resultat = getBindings().getOperationBinding("findAndUpdateResultat");
                                    op_resultat.getParamsMap().put("id_resultat",  Long.parseLong(nextRow.getAttribute("IdResultat").toString()));
                                    op_resultat.getParamsMap().put("insc_ped_sup",  Long.parseLong(id_insc_ped));          
                                    op_resultat.execute();
                                    nbr_insc_red ++;
                               }
                                else{
                                    System.out.println("deja insc au même niveau formation (Redoublant)");
                                    OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                                    op_commit.execute();
                                }
                            }
                        }
                    }
                    if( nbr_insc > 0){
                        FacesMessage msg = new FacesMessage(rsi.getRowCount() - nbr_insc+" Inscription(s) rejetée(s) "+lib_form_sup);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(nbr_insc+" Inscription(s) validée(s) avec succès "+lib_form_sup));
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    if( nbr_insc_red > 0){
                        FacesMessage msg = new FacesMessage(rsi.getRowCount() - nbr_insc_red+" Inscription(s) rejetée(s) "+lib_form_anc);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(nbr_insc_red+" Inscription(s) validée(s) avec succès "+lib_form_anc));
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }
        }
        return null;
    }

    public void setCredit(RichInputText credit) {
        this.credit = credit;
    }

    public RichInputText getCredit() {
        return credit;
    }

    @SuppressWarnings("unchecked")
    public void niveauFormSup(String niveau, String annee,String formation){
        OperationBinding op_niv_form_sup = getBindings().getOperationBinding("getNiveauFormSup");
        op_niv_form_sup.getParamsMap().put("niveau_formation",  Long.parseLong(niveau) + 1);   
        op_niv_form_sup.getParamsMap().put("id_annee",  Long.parseLong(annee)); 
        op_niv_form_sup.getParamsMap().put("id_formation",  Long.parseLong(formation));      //IdFormation
        op_niv_form_sup.execute();
        DCIteratorBinding iter_sup = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ResultParcoursSupROIterator");        
        RowSetIterator rsi_sup = iter_sup.getViewObject().createRowSetIterator(null);
        while (rsi_sup.hasNext()) {
            Row nextRow = rsi_sup.next();
            System.out.println("IdParcoursMaquetAnnee "+nextRow.getAttribute("IdParcoursMaquetAnnee"));
        }
    }

    @SuppressWarnings("unchecked")
    public String onChargerResultat() {
        // Add event code here...
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        
        AttributeBinding id_parc_maq = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        AttributeBinding id_grade = (AttributeBinding) getBindings().getControlBinding("IdGrade");
        AttributeBinding id_type_form = (AttributeBinding) getBindings().getControlBinding("IdTypeFormation");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding id_niveau = (AttributeBinding) getBindings().getControlBinding("IdNiveau1");
        
        AttributeBinding id_parc_sup = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneeSup");
        AttributeBinding id_annee_sup = (AttributeBinding) getBindings().getControlBinding("IdAnneSup");
        
        AttributeBinding niveau = (AttributeBinding) getBindings().getControlBinding("Niveau");
        System.out.println("IdAnneesUnivers1 "+id_annee.getInputValue()+" niv "+niveau.getInputValue());//getNiveauFormSup
        
        getListeEtudiantInsc(id_parc_maq.getInputValue().toString(), id_grade.getInputValue().toString(), id_type_form.getInputValue().toString(), id_annee.getInputValue().toString());
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListePersSaisieResultatROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        
        if(rsi.getRowCount() == 0){
            OperationBinding getIsteEtudiantInsc = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResult");
            getIsteEtudiantInsc.getParamsMap().put("id_niveau",  0);   
            getIsteEtudiantInsc.getParamsMap().put("id_annee",  0);  
            getIsteEtudiantInsc.execute();
        }
        else{

            OperationBinding getIsteEtudiantInsc = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResult");
            getIsteEtudiantInsc.getParamsMap().put("id_niveau",  Long.parseLong(id_niveau.getInputValue().toString()));   
            getIsteEtudiantInsc.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));  
            getIsteEtudiantInsc.execute();
            DCIteratorBinding iter_result = (DCIteratorBinding) getBindings().get("ResultatIterator");
            Row currentRow = iter_result.getCurrentRow();
            
            if(currentRow == null){
                AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
                AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogique");
                AttributeBinding id_insc_ped_adm = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogiqueAdm");
                AttributeBinding id_niveau_bn = (AttributeBinding) getBindings().getControlBinding("IdNiveau");
                AttributeBinding id_annee_bn = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers2");
                AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
                AttributeBinding id_option_bn = (AttributeBinding) getBindings().getControlBinding("IdOption");

                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();                    
                    OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
                    Object result = operationBinding.execute();
                    if (!operationBinding.getErrors().isEmpty()) {
                        return null;
                    }
                    else{
                        id_etudiant.setInputValue(Long.parseLong(nextRow.getAttribute("IdEtudiant").toString()));
                        id_insc_ped.setInputValue(Long.parseLong(nextRow.getAttribute("IdInscriptionPedagogique").toString()));
                        id_insc_ped_adm.setInputValue(Long.parseLong(nextRow.getAttribute("IdInscriptionAdmin").toString()));
                        id_niveau_bn.setInputValue(Long.parseLong(nextRow.getAttribute("IdNiveau").toString()));
                        id_annee_bn.setInputValue(Long.parseLong(id_annee.getInputValue().toString()));
                        id_util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));
                        id_option_bn.setInputValue(Long.parseLong(nextRow.getAttribute("IdOption").toString()));

                    }
                }                
            }
            else{
                
            }
            
        }        
        return null;
    }

    @SuppressWarnings("unchecked")
    public Row getDroitNiv(String niveau, String nationalite){
    //getDroitNiveauPays
        OperationBinding getDroitNiveauPays = getBindings().getOperationBinding("getDroitNiveauPays");
        getDroitNiveauPays.getParamsMap().put("id_niveau",  Long.parseLong(niveau)); 
        getDroitNiveauPays.getParamsMap().put("id_pays_nation",  Long.parseLong(nationalite));
        getDroitNiveauPays.execute(); 
        DCIteratorBinding iter_droit_niv = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DroitNiveauPaysROIterator");
        return iter_droit_niv.getCurrentRow();
    }
    
    @SuppressWarnings("unchecked")
    public String inscription(String id_parc,String annee,String id_etud,String nationalite){
        
        AttributeBinding id_parc_maq = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        AttributeBinding id_grade = (AttributeBinding) getBindings().getControlBinding("IdGrade");
        AttributeBinding id_type_form = (AttributeBinding) getBindings().getControlBinding("IdTypeFormation");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding id_niveau = (AttributeBinding) getBindings().getControlBinding("IdNiveau1");
        
        
        String insc = null;
        
        Row detailInscPed = getRowInscPed(id_parc_maq.getInputValue().toString(), id_grade.getInputValue().toString(), id_type_form.getInputValue().toString(), id_annee.getInputValue().toString(),id_etud); 
        
        OperationBinding op_info_form = getBindings().getOperationBinding("getInfoForm");
        op_info_form.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));        
        op_info_form.execute();
        
        DCIteratorBinding iter_info = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscInfoGlobleFormIterator");
        Row row_info_gl = iter_info.getCurrentRow();
        Integer niveau = Integer.parseInt(row_info_gl.getAttribute("Niveau").toString());
                
        OperationBinding op_nombre_insc = getBindings().getOperationBinding("getNombreInscription");
        op_nombre_insc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));
        op_nombre_insc.getParamsMap().put("id_etud",  Long.parseLong(id_etud));    
        op_nombre_insc.execute();
        
        DCIteratorBinding iter_nombre_insc = (DCIteratorBinding) getBindings().get("InscNombreMaxiROIterator");        
        RowSetIterator rsi_nombre_insc = iter_nombre_insc.getViewObject().createRowSetIterator(null);
   
        Row row_droit_niv_pays = getDroitNiv(niveau+"", nationalite);
        if(row_droit_niv_pays != null){
            AttributeBinding idTypeFormation = (AttributeBinding) getBindings().getControlBinding("IdTypeFormationAdmin");
            AttributeBinding id_annee_insc_admin = (AttributeBinding) getBindings().getControlBinding("IdAnneesInsAdmin");
            AttributeBinding idetud = (AttributeBinding) getBindings().getControlBinding("IdEtudiantInscAdmin");
            AttributeBinding idGrade = (AttributeBinding) getBindings().getControlBinding("IdGradeAdmin");
            AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCreeInsAdmin");
            
            OperationBinding op_insert_insc_admin = getBindings().getOperationBinding("CreateInsertInscAdmin");
            Object result_insert_insc_admin = op_insert_insc_admin.execute();
                                    
            if (!op_insert_insc_admin.getErrors().isEmpty()) {
                    return null;
            }
            else{
                idTypeFormation.setInputValue(Long.parseLong(row_info_gl.getAttribute("IdTypeFormation").toString()));
                idGrade.setInputValue(Long.parseLong(row_info_gl.getAttribute("IdGrade").toString()));
                idetud.setInputValue(Long.parseLong(id_etud));
                util.setInputValue(Long.parseLong(getUtilisateur()));
                id_annee_insc_admin.setInputValue(Long.parseLong(annee));
                
                OperationBinding op_commit_insc_admin = getBindings().getOperationBinding("Commit");
                Object result = op_commit_insc_admin.execute();
                if (!op_commit_insc_admin.getErrors().isEmpty()) {
                        return null;
                }
                // le début d'inscription Pédagogique
                else{
                    AttributeBinding id_insc_admin = (AttributeBinding) getBindings().getControlBinding("IdInscriptionAdmin");
                    AttributeBinding id_resAdm = (AttributeBinding) getBindings().getControlBinding("IdResultat");                                
                    System.out.println("id_res id_resAdm "+id_resAdm);
                    //les attributs qui seront modifier lors de l'insc
                    AttributeBinding id_insc_admin_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionAdminPed");
                    AttributeBinding id_HorairesTd = (AttributeBinding) getBindings().getControlBinding("IdHorairesTd");
                    AttributeBinding id_Statut = (AttributeBinding) getBindings().getControlBinding("IdStatut");
                    AttributeBinding id_Option = (AttributeBinding) getBindings().getControlBinding("IdOption1");
                    AttributeBinding id_Bourse = (AttributeBinding) getBindings().getControlBinding("IdBourse");
                    AttributeBinding id_Cohorte = (AttributeBinding) getBindings().getControlBinding("IdCohorte");
                    AttributeBinding id_TypeConvention = (AttributeBinding) getBindings().getControlBinding("IdTypeConvention");
                    AttributeBinding id_Operateur = (AttributeBinding) getBindings().getControlBinding("IdOperateur");
                    AttributeBinding derniere_insc = (AttributeBinding) getBindings().getControlBinding("DernierInscription");
                    
                    AttributeBinding id_parc_maq_annee = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneePed");
                    AttributeBinding nb_insc = (AttributeBinding) getBindings().getControlBinding("NbInsc");
                    AttributeBinding util_permet_double_insc = (AttributeBinding) getBindings().getControlBinding("UtiPermetDoublInsPed");
                    AttributeBinding id_etat_insc = (AttributeBinding) getBindings().getControlBinding("EtatInscrEtatInscrId");
                    AttributeBinding tarif = (AttributeBinding) getBindings().getControlBinding("Tarif");
                    AttributeBinding orde_insc = (AttributeBinding) getBindings().getControlBinding("OrdreInscription");
                    AttributeBinding utilisateur_cree = (AttributeBinding) getBindings().getControlBinding("UtiCreeInscPed");

                    AttributeBinding droit_ins_admin = (AttributeBinding) getBindings().getControlBinding("DroitInsAdm");
                    AttributeBinding droit_ins_ped = (AttributeBinding) getBindings().getControlBinding("DroitInsPed");
                    AttributeBinding cout_formation = (AttributeBinding) getBindings().getControlBinding("CoutFormation");
                    
                    OperationBinding op_insc_ped = getBindings().getOperationBinding("CreateInsertInscPed");
                    Object result_op_insc_ped = op_insc_ped.execute();
                    if (!op_insc_ped.getErrors().isEmpty()) {
                            return null;
                    }
                    else{
                        Integer droit_insc_admin = 0;
                        Integer droit_insc_ped = 0;
                        if(row_droit_niv_pays.getAttribute("DroitInsAdm") != null)
                                droit_insc_admin = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsAdm").toString());
                        if(row_droit_niv_pays.getAttribute("DroitInsPed") != null)
                                droit_insc_ped = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsPed").toString());
                        
                        id_insc_admin_ped.setInputValue(Long.parseLong(id_insc_admin.getInputValue().toString()));
                        id_HorairesTd.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdHorairesTd").toString()));
                        id_Statut.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdStatut").toString()));
                        id_Option.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdOption").toString()));
                        id_Bourse.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdBourse").toString()));
                        id_Cohorte.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdCohorte").toString()));
                        id_TypeConvention.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdTypeConvention").toString()));
                        id_Operateur.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdOperateur").toString()));
                        derniere_insc.setInputValue(Long.parseLong(detailInscPed.getAttribute("IdInscriptionPedagogique").toString()));
                        id_parc_maq_annee.setInputValue(Long.parseLong(id_parc));
                        nb_insc.setInputValue(rsi_nombre_insc.getRowCount() +1);        //rsi_nombre_insc.getRowCount() nombre d'inscription antérieure pour un même niv de formation
                        util_permet_double_insc.setInputValue(Long.parseLong(getUtilisateur())); //utilisateur connecté ou un autre
                        id_etat_insc.setInputValue(Long.parseLong("21")); // etat prvisoire
                        cout_formation.setInputValue( droit_insc_ped + droit_insc_admin);
                        utilisateur_cree.setInputValue(Long.parseLong(getUtilisateur()));
                        
                        droit_ins_admin.setInputValue(droit_insc_admin);
                        droit_ins_ped.setInputValue(droit_insc_ped);
                        
                        AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogique1");
                        OperationBinding op_insert_ped_commit = getBindings().getOperationBinding("Commit");
                        Object result_insert_ped_commit = op_insert_ped_commit.execute();
                        if (!op_insert_ped_commit.getErrors().isEmpty()) {
                                return null;
                        }                               
                        else{
                            insc = id_insc_ped.getInputValue().toString();
                        }
                    }
                }
            }

        }
       // }
    return insc;
    }
}
