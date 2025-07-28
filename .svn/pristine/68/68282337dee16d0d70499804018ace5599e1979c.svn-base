package operation_cmpte_form;

import java.text.NumberFormat;

import java.util.Map;

import liste_avoir_etud.ListeAvoirEtudBean;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class OperationCmpteFormBean {

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private String somme_total;
    private String somme_total_depenses;
    
    public OperationCmpteFormBean() {
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setSomme_total(String somme_total) {
        this.somme_total = somme_total;
    }

    public String getSomme_total() {
        return somme_total;
    }

    public void setSomme_total_depenses(String somme_total_depenses) {
        this.somme_total_depenses = somme_total_depenses;
    }

    public String getSomme_total_depenses() {
        return somme_total_depenses;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void refreshTableOperation(){
        
        OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
        op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
        op_form_hs.execute();
        DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
        Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
        if(row_op_form_hs != null){
            OperationBinding op_les_paie_retard = getBindings().getOperationBinding("getRecetteCmpteForm");
            op_les_paie_retard.getParamsMap().put("id_form",  row_op_form_hs.getAttribute("IdFormation"));
            op_les_paie_retard.getParamsMap().put("id_util",  getUtilisateur());
            op_les_paie_retard.getParamsMap().put("id_annee",  getAnne_univers());
            op_les_paie_retard.execute();
            
            DCIteratorBinding iter_op_det_cmpt = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("lesRecettesCmptFormROIterator");
            Row row_op_det_cmpt = iter_op_det_cmpt.getCurrentRow();
            if(row_op_det_cmpt != null){
                RowSetIterator rsi_op_det_cmpt = iter_op_det_cmpt.getViewObject().createRowSetIterator(null);
                Integer i = 0;
                Double somme = 0.00;
                while (rsi_op_det_cmpt.hasNext()) {
                    Row nextRow = rsi_op_det_cmpt.next();
                    if(nextRow.getAttribute("MontantVerse") != null){
                       somme = somme +  Double.parseDouble(nextRow.getAttribute("MontantVerse").toString());
                    }

                }
                System.out.println("somme "+somme);
                
                NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);
                System.out.println("som format "+numberFormat.format(somme));
                setSomme_total(numberFormat.format(somme.intValue()));
            }
            //getEtatActuCmptForm
            OperationBinding op_etat_actu_cmpt = getBindings().getOperationBinding("getEtatActuCmptForm");
            op_etat_actu_cmpt.getParamsMap().put("id_form",  row_op_form_hs.getAttribute("IdFormation"));
            op_etat_actu_cmpt.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
            op_etat_actu_cmpt.getParamsMap().put("id_util",  getUtilisateur());
            op_etat_actu_cmpt.getParamsMap().put("id_annee",  getAnne_univers());
            op_etat_actu_cmpt.execute();
            //getRechCmpteGlob
            OperationBinding op_cmpt_glob = getBindings().getOperationBinding("getRechCmpteGlob");
            op_cmpt_glob.getParamsMap().put("id_strct",  row_op_form_hs.getAttribute("IdStr"));
            op_cmpt_glob.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
            op_cmpt_glob.getParamsMap().put("type_cmpte",  "21");
            op_cmpt_glob.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_glob.execute();
            //lesDepensesEffectueesFrom id_strc,
            OperationBinding op_les_depenses = getBindings().getOperationBinding("lesDepensesEffectueesFrom");
            op_les_depenses.getParamsMap().put("id_form",  row_op_form_hs.getAttribute("IdFormation"));
            op_les_depenses.getParamsMap().put("id_strc",  row_op_form_hs.getAttribute("IdStr"));
            op_les_depenses.getParamsMap().put("id_util",  getUtilisateur());
            op_les_depenses.getParamsMap().put("id_annee",  getAnne_univers());
            op_les_depenses.execute();
            
            DCIteratorBinding iter_les_depenses = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("lesDepensesEffectueesFromROIterator");
            Row row_op_les_depenses = iter_les_depenses.getCurrentRow();
            if(row_op_les_depenses != null){
                RowSetIterator rsi_op_les_depenses = iter_les_depenses.getViewObject().createRowSetIterator(null);
                Integer i = 0;
                Double somme = 0.00;
                while (rsi_op_les_depenses.hasNext()) {
                    Row nextRow = rsi_op_les_depenses.next();
                    if(nextRow.getAttribute("Montant") != null){
                       somme = somme +  Double.parseDouble(nextRow.getAttribute("Montant").toString());
                    }

                }
                System.out.println("somme "+somme);
                
                NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);
                System.out.println("som format "+numberFormat.format(somme));
                setSomme_total_depenses(numberFormat.format(somme.intValue()));
            }
            
        }
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }
}
