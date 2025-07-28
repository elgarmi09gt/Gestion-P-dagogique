package responsable_etab_dpt;

import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;

public class RespEtabDptBean {
    private RichSelectBooleanCheckbox resp_struct;
    private RichSelectBooleanCheckbox resp_dpt;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();

    private String date_deb_strc;
    private String date_fin_strc;
    private String date_deb_dpt;
    private String date_fin_dpt;

    public RespEtabDptBean() {
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

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setResp_struct(RichSelectBooleanCheckbox resp_struct) {
        this.resp_struct = resp_struct;
    }

    public RichSelectBooleanCheckbox getResp_struct() {
        return resp_struct;
    }

    public void setResp_dpt(RichSelectBooleanCheckbox resp_dpt) {
        this.resp_dpt = resp_dpt;
    }

    public RichSelectBooleanCheckbox getResp_dpt() {
        return resp_dpt;
    }

    public void onChangeRespStruct(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void onChangeRespDeprt(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }
    @SuppressWarnings("unchecked")
    public void refreshRespEtabDpt(){

        DCIteratorBinding iter_strc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("StructuresIterator");
        Row row_strc = iter_strc.getCurrentRow();
        
        if(row_strc != null){
            System.out.println("IdStructure "+row_strc.getAttribute("IdStructure"));
            
        }
    }
}
