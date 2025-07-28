package liste_avoir_etud;

import java.util.Map;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class ListeAvoirEtudBean {
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    
    public ListeAvoirEtudBean() {
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

    public void refreshTablePaiement(String id_form,String id_niv_form,String id_annee){
        OperationBinding op_les_paie_retard = getBindings().getOperationBinding("getListeAvoirEtud");
        op_les_paie_retard.getParamsMap().put("id_form",  id_form);
        op_les_paie_retard.getParamsMap().put("id_niv_form",  id_niv_form);
        op_les_paie_retard.getParamsMap().put("id_annee",  id_annee);
        op_les_paie_retard.execute();
    }
}
