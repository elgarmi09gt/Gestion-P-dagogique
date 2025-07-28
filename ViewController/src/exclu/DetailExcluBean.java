package exclu;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

public class DetailExcluBean {
    private RichPopup pop;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private RichPopup pop_annuler;

    public DetailExcluBean() {
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("InscPedEnCoursExcluROIterator");
            RowSetIterator rsi_list_insc_ped = iter_list_insc_ped.getViewObject().createRowSetIterator(null);
            while (rsi_list_insc_ped.hasNext()) {
                Row nextRow = rsi_list_insc_ped.next();
                
                OperationBinding insc_ped = getBindings().getOperationBinding("fingAndUpdateInsPed");
                insc_ped.getParamsMap().put("id_ins_ped",  Long.parseLong(nextRow.getAttribute("IdInscriptionPedagogique").toString()));
                insc_ped.getParamsMap().put("etat",  Long.parseLong("61"));     // 61 exclu etat inscription
                insc_ped.execute();
                
            }
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getIncPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc_exclu").toString()));
            list_insc_ped.execute();
            
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop().hide();
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public Integer nombreInscExclu(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(Long.parseLong(nextRow.getAttribute("EtatInscrEtatInscrId").toString()) == 61){      // etat inscrp exclu 61               
                i++;
            }
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public String onValiderExclusion() {
        // Add event code here...
        // code_exclu 1: exclu et 2: annuler l'exclusion
        AttributeBinding code_exclu = (AttributeBinding) getBindings().getControlBinding("codeExclu");
        
        OperationBinding list_insc_ped_cours = getBindings().getOperationBinding("getInscPedEnCours");
        list_insc_ped_cours.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc_exclu").toString()));
        list_insc_ped_cours.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
        list_insc_ped_cours.execute();
        
        if(Integer.parseInt( code_exclu.getInputValue().toString()) == 0){
            FacesMessage msg = new FacesMessage("Impossible d'exclure l'étudiant du prénom & nom : "+sessionScope.get("prenom_etud")+" "+sessionScope.get("nom_etud"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Veuillez sélectionner Exclu ou Annulation de l'exclusion"));

        }
        else{
            if(Integer.parseInt( code_exclu.getInputValue().toString()) == 1){
                if(nombreInscExclu("InscPedEnCoursExcluROIterator") > 0 && Integer.parseInt( code_exclu.getInputValue().toString()) == 1){
                    FacesMessage msg = new FacesMessage("L'étudiant du prénom & nom : "+sessionScope.get("prenom_etud")+" "+sessionScope.get("nom_etud")+" est déjà exclu");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);   
                }
                else{
                    if(nombreInscExclu("InscPedEnCoursExcluROIterator") == 0 && Integer.parseInt(code_exclu.getInputValue().toString()) == 1){
                        RichPopup popup = this.getPop();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();        
                        popup.show(hints);
                    }
                }
            }
            // pour annuler l'exclusion
            else{
                if( Integer.parseInt( code_exclu.getInputValue().toString()) == 2){
                    if(nombreInscExclu("InscPedEnCoursExcluROIterator") > 0){
                        RichPopup popup = this.getPop_annuler();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();        
                        popup.show(hints);
                    }
                    else{
                        FacesMessage msg = new FacesMessage("Impossible d'annuler l'exclusion de l'étudiant du prénom & nom : "+sessionScope.get("prenom_etud")+" "+sessionScope.get("nom_etud"));
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" N'est pas dans l'état d'Exclu"));

                    }
                }
            }
        }       

        return null;
    }

    public void setPop_annuler(RichPopup pop_annuler) {
        this.pop_annuler = pop_annuler;
    }

    public RichPopup getPop_annuler() {
        return pop_annuler;
    }

    public void onDialogCanAnnuler(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop_annuler().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogAnnuler(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("InscPedEnCoursExcluROIterator");
            RowSetIterator rsi_list_insc_ped = iter_list_insc_ped.getViewObject().createRowSetIterator(null);
            while (rsi_list_insc_ped.hasNext()) {
                Row nextRow = rsi_list_insc_ped.next();
                
                OperationBinding insc_ped = getBindings().getOperationBinding("fingAndUpdateInsPed");
                insc_ped.getParamsMap().put("id_ins_ped",  Long.parseLong(nextRow.getAttribute("IdInscriptionPedagogique").toString()));
                insc_ped.getParamsMap().put("etat",  Long.parseLong("21"));     // 21 provisoire etat inscription
                insc_ped.execute();
                
            }
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getIncPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc_exclu").toString()));
            list_insc_ped.execute();
            
        }
    }
}
