package etud_paie_insc_ecol;

import java.util.Map;

import liste_avoir_etud.ListeAvoirEtudBean;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class EtudPaieInscBean {    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    
    public EtudPaieInscBean() {
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    @SuppressWarnings("unchecked")
    public void refreshTablePaiement(String id_form,String id_niv_form,String id_annee){
        OperationBinding op_les_paie_retard = getBindings().getOperationBinding("getEtudPaieInscription");
        op_les_paie_retard.getParamsMap().put("id_form",  id_form);
        op_les_paie_retard.getParamsMap().put("id_niv_form",  id_niv_form);
        op_les_paie_retard.getParamsMap().put("id_annee",  id_annee);
        op_les_paie_retard.execute();
    }
    

    @SuppressWarnings("unchecked")
    public String getEtudiantBu(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm) IdStructure
        AttributeBinding id_strct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        String result = "Non";
        OperationBinding op_etud_bu = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudiantBu");
        op_etud_bu.getParamsMap().put("id_struct",  id_strct.getInputValue());
        op_etud_bu.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_etud_bu.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        op_etud_bu.execute();
        DCIteratorBinding iter_etud_bu = (DCIteratorBinding) getBindings().get("EtudiantBuIterator");
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
                    result = "Oui";
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getApteEtudiant(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm)
        String result = "Non";

        OperationBinding opNote = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getAptitudeEtudiant");
        opNote.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        opNote.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AptePreinsIterator");
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
                    result = "Oui";
                    return result;
                }
                else
                    return result;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getResponsableEtud(String id_etud){    
        
        String result = "Non";
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResponsableEtud");
        op_resp_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_resp_etud.execute();
        DCIteratorBinding iter_resp_etud = (DCIteratorBinding) getBindings().get("ResponsableROIterator");
        Row currentRow_resp_etud = iter_resp_etud.getCurrentRow();
        if (currentRow_resp_etud == null) {
            return result;
        }
        else{
            result = "Oui";
            return result;         
        }
    }

    @SuppressWarnings("unchecked")
    public String getInscEnLigne(String id_insc_ped){
        String result = "Non";
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
        op_resp_etud.getParamsMap().put("id_ins_ped", Long.parseLong(id_insc_ped));
        op_resp_etud.execute();
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
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
                    result = "Oui";
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getEtatInsc(String id_insc_ped){
       String result = " ---- ";
       OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
       op_resp_etud.getParamsMap().put("id_ins_ped", Long.parseLong(id_insc_ped));
       op_resp_etud.execute();
       DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
       Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
       if (currentRow_insc_ped == null ) {
           return result;
       }
       else{
            if(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId") == null){
                return result;
            }
            else{              
               OperationBinding op_etat_insc= BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtatInscription");
               op_etat_insc.getParamsMap().put("id_etat_insc", currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId"));
               op_etat_insc.execute();
               DCIteratorBinding iter_etat_insc = (DCIteratorBinding) getBindings().get("EtatInscriptionROIterator");
               Row row_etat_insc = iter_etat_insc.getCurrentRow();
               
               result = row_etat_insc.getAttribute("Libelle").toString();
               return result;
            }

       }
    }
    
    @SuppressWarnings("unchecked")
    public String getPaiementEtudiant(String inscPed , String id_form, String id_niv_form, String id_annee) {
        String result = "Non";

        OperationBinding op_paie_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getPaiementDIPEcol");
        op_paie_etud.getParamsMap().put("id_insc_ped", inscPed);
        op_paie_etud.getParamsMap().put("id_niv_form", id_niv_form);
        op_paie_etud.getParamsMap().put("id_form", id_form);
        op_paie_etud.getParamsMap().put("id_annee", id_annee);
        op_paie_etud.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("PaiementDIPEcolROIterator");

        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return result;
        }
        else{
            if(currentRow.getAttribute("Paye") == null){
                return result;
            }
            else{
                if (Integer.parseInt(currentRow.getAttribute("Paye").toString()) == 1) {
                        result = "Oui";
                        return result;
                }
                else
                    return result;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void getResultat(String id_etud, String inscPed , String id_form, String id_niv_form, String id_annee, String etat_insc) {
        String result = "Non";
        
        String etud_bu = getEtudiantBu(id_etud, id_annee);
        String etud_apte = getApteEtudiant(id_etud, id_annee);
        String etud_resp = getResponsableEtud(id_etud);
        String etud_insc_ligne = getInscEnLigne(inscPed);
        String etud_etat_insc = getEtatInscBis(inscPed);
        String etud_paie = getPaiementEtudiant(inscPed, id_form, id_niv_form, id_annee);
        sessionScope.remove("icone_resultat_val");
        
        if(etud_bu == "Oui" && etud_apte == "Oui" && etud_resp == "Oui" && etud_etat_insc == "Oui" && etud_paie == "Oui" && etud_insc_ligne == "Oui" ){
            sessionScope.put("icone_resultat_val", "/images/commit.png");
        }
    }
    
    @SuppressWarnings("unchecked")
    public String getEtatInscBis(String id_insc_ped){
       String result = "Non";
       OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
       op_resp_etud.getParamsMap().put("id_ins_ped", Long.parseLong(id_insc_ped));
       op_resp_etud.execute();
       DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscriptionsPedagogiquesIterator");
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
                    result = "Oui";
                    return result;
                }
                else{                                 
                   return result;
                }
            }
       }
    }
    //refreshMoisPaiement
    @SuppressWarnings("unchecked")
    public void refreshMoisPaiement(String id_form,String id_niv_form,String id_annee, String id_mois_cours){
        //id_mois_deb
        /*OperationBinding op_mois_cours = getBindings().getOperationBinding("getMois");
        op_mois_cours.getParamsMap().put("id_mois_deb",  "3");
        op_mois_cours.execute();
        
        OperationBinding op_les_paie_retard = getBindings().getOperationBinding("getLesMoisFormNivFormAnn");
        op_les_paie_retard.getParamsMap().put("id_form",  id_form);
        op_les_paie_retard.getParamsMap().put("id_niv_form",  id_niv_form);
        op_les_paie_retard.getParamsMap().put("id_annee",  id_annee);
        //op_les_paie_retard.getParamsMap().put("id_mois",  3);
        op_les_paie_retard.execute();*/
        
        OperationBinding op_les_etud_paie = getBindings().getOperationBinding("getEtudAyantPaieMensualite");
        op_les_etud_paie.getParamsMap().put("id_form",  id_form);
        op_les_etud_paie.getParamsMap().put("id_niv_form",  id_niv_form);
        op_les_etud_paie.getParamsMap().put("id_annee",  id_annee);
        op_les_etud_paie.getParamsMap().put("id_mois_cours",  id_mois_cours);
        op_les_etud_paie.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_les_etud_paie.execute();
    }
}
