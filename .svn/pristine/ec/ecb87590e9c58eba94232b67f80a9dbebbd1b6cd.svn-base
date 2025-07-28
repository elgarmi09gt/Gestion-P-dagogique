package change_nationalite;

import java.util.Map;

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

public class DetailChanngeNationaliteBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private RichPopup pop;

    public DetailChanngeNationaliteBean() {
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public Row getDroitNiv(String niveau, String nationalite){
        OperationBinding getDroitNiveauPays = getBindings().getOperationBinding("getDroitNiveauPays");
        getDroitNiveauPays.getParamsMap().put("id_niveau",  Long.parseLong(niveau)); 
        getDroitNiveauPays.getParamsMap().put("id_pays_nation",  Long.parseLong(nationalite));
        getDroitNiveauPays.execute(); 
        DCIteratorBinding iter_droit_niv = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DroitNiveauPaysROIterator");
        return iter_droit_niv.getCurrentRow();
    }

    @SuppressWarnings("unchecked")
    public String onValiderNouvelleNationalite() {
        // Add event code here...
        
        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("InscPedChangeNationaliteROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        DCIteratorBinding iter_nationalite = (DCIteratorBinding)getBindings().get("PaysIterator");
        Row currentRow = iter_nationalite.getCurrentRow();
        
        if(currentRow != null && Long.parseLong(row_list_insc_ped.getAttribute("IdPaysNationalite").toString()) != Long.parseLong(currentRow.getAttribute("IdPays").toString())){
            RichPopup popup = this.getPop();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();

            popup.show(hints);
        }     
        
        return null;
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            AttributeBinding id_nouv_nationalite = (AttributeBinding) getBindings().getControlBinding("IdPays");
            
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("InscPedChangeNationaliteROIterator");
            RowSetIterator rsi_list_insc_ped = iter_list_insc_ped.getViewObject().createRowSetIterator(null);
            
            Row currentRow = iter_list_insc_ped.getCurrentRow();
            
            OperationBinding findUpdateNationalite = getBindings().getOperationBinding("findAndUpdateNationalite");
            findUpdateNationalite.getParamsMap().put("id_pers", Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));
            findUpdateNationalite.getParamsMap().put("id_nouv_nat", Long.parseLong(id_nouv_nationalite.getInputValue().toString()));
            findUpdateNationalite.execute();
            
            
            while (rsi_list_insc_ped.hasNext()) {
                Row nextRow = rsi_list_insc_ped.next();
                System.out.println("nextRow "+nextRow.getAttribute("IdInscriptionPedagogique"));
                if(Integer.parseInt(nextRow.getAttribute("EtatInscrEtatInscrId").toString()) == 21){        // 21 etat insc provisoire
                    Row droit_insc = getDroitNiv(nextRow.getAttribute("IdNiveau").toString() , id_nouv_nationalite.getInputValue().toString()); 
                    OperationBinding update_insc_ped = getBindings().getOperationBinding("findAndPaieExoneration");
                    update_insc_ped.getParamsMap().put("id_insc_ped",  Long.parseLong(nextRow.getAttribute("IdInscriptionPedagogique").toString()));
                        
                    if(droit_insc != null){   
                        if(droit_insc.getAttribute("DroitInsAdm") != null && droit_insc.getAttribute("DroitInsPed") != null){
                            update_insc_ped.getParamsMap().put("droit_insc_adm",  Integer.parseInt(droit_insc.getAttribute("DroitInsAdm").toString()));
                            update_insc_ped.getParamsMap().put("droit_insc_ped",  Integer.parseInt(droit_insc.getAttribute("DroitInsPed").toString()) );
                            update_insc_ped.getParamsMap().put("cout_form",  Integer.parseInt(droit_insc.getAttribute("DroitInsAdm").toString()) + Integer.parseInt(droit_insc.getAttribute("DroitInsPed").toString()));
                        }
                        else{                
                            if(droit_insc.getAttribute("DroitInsAdm") != null && droit_insc.getAttribute("DroitInsPed") == null){
                                update_insc_ped.getParamsMap().put("droit_insc_adm",  Integer.parseInt(droit_insc.getAttribute("DroitInsAdm").toString()));
                                update_insc_ped.getParamsMap().put("droit_insc_ped",  null );
                                update_insc_ped.getParamsMap().put("cout_form",  Integer.parseInt(droit_insc.getAttribute("DroitInsAdm").toString()));
                            }
                            else{                
                                if(droit_insc.getAttribute("DroitInsAdm") == null && droit_insc.getAttribute("DroitInsPed") != null){
                                    update_insc_ped.getParamsMap().put("droit_insc_adm",  null);
                                    update_insc_ped.getParamsMap().put("droit_insc_ped",  Integer.parseInt(droit_insc.getAttribute("DroitInsPed").toString()) );
                                    update_insc_ped.getParamsMap().put("cout_form",  Integer.parseInt(droit_insc.getAttribute("DroitInsPed").toString()));
                                }
                                else{
                                    update_insc_ped.getParamsMap().put("droit_insc_adm",  null);
                                    update_insc_ped.getParamsMap().put("droit_insc_ped",  null);
                                    update_insc_ped.getParamsMap().put("cout_form",  null);
                                }
                            }
                        }
                        //update_insc_ped.execute();
                    }
                    else{
                        update_insc_ped.getParamsMap().put("droit_insc_adm",  null);
                        update_insc_ped.getParamsMap().put("droit_insc_ped",  null);
                        update_insc_ped.getParamsMap().put("cout_form",  null);
                    }
                    update_insc_ped.execute();
                }

            }
            
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));
            list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_insc_ped.execute();
        }
    }
}
