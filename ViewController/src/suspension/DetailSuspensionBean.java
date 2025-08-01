package suspension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DetailSuspensionBean {
    private Date date_debut;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private RichPopup popup;

    public DetailSuspensionBean() {
    }


    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void onChangerDuree(ClientEvent clientEvent) {
        // Add event code here...
        AttributeBinding date_fin = (AttributeBinding) getBindings().getControlBinding("DateFin");
        AttributeBinding date_debut = (AttributeBinding) getBindings().getControlBinding("DateDebut");
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        if(date_debut.getInputValue() == null){          
            
            Date today = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            Integer duree = 0;
            try{
                if(searchString != "" && Integer.parseInt(searchString) >= 0){
                    duree = Integer.parseInt(searchString);
                }
            }
            catch(Exception e){}
            // manipulate date
            cal.add(Calendar.YEAR, duree);
            System.out.println(" cal "+dateFormat.format(cal.getTime()));
            date_fin.setInputValue(cal.getTime());
        }
        else{
            Date date = null;
            Integer duree = 0;
            Calendar cal = Calendar.getInstance();
            try {
                date = (Date)date_debut.getInputValue();
                System.out.println(" CST 2013 " +dateFormat.format(date)); // Wed Dec 04 00:00:00 CST 2013
                
                String output = dateFormat.format(date);
                
                cal.setTime(date);

            
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            
            try{
                if(searchString != "" && Integer.parseInt(searchString) >= 0){
                    duree = Integer.parseInt(searchString);
                }
            }
            catch(Exception e){}
            
            cal.add(Calendar.YEAR, duree);
            date_fin.setInputValue(cal.getTime());
        }
        
        
        
    }

    public void onChangeDateDebut(ClientEvent clientEvent) {
        // Add event code here...
        AttributeBinding date_fin = (AttributeBinding) getBindings().getControlBinding("DateFin");
        AttributeBinding date_debut = (AttributeBinding) getBindings().getControlBinding("DateDebut");
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
    }

    @SuppressWarnings("unchecked")
    public String onValiderSuspension() {
        // Add event code here...
        
        AttributeBinding duree = (AttributeBinding) getBindings().getControlBinding("Duree");
        AttributeBinding date_debut = (AttributeBinding) getBindings().getControlBinding("DateDebut");
        AttributeBinding date_fin = (AttributeBinding) getBindings().getControlBinding("DateFin");
        
        if(duree.getInputValue() == null || date_debut.getInputValue() == null || date_fin.getInputValue() == null){                    
            FacesMessage msg = new FacesMessage(" Tous les champs sont obligatoires :");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            if(duree.getInputValue() == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Dur�e"));
            }
            if(date_debut.getInputValue() == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Date D�but"));
            }
            if(date_fin.getInputValue() == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Date Fin"));
            }
    
        }
        else{
            
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc").toString()));
            list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_insc_ped.execute();
            
            DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("listeInsPedIterator");
                //Create RowSetIterato iterate over viewObject

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            OperationBinding list_susp = getBindings().getOperationBinding("getListeSuspension");
            list_susp.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc").toString()));
            list_susp.execute();
            DCIteratorBinding iter_list_susp = (DCIteratorBinding)getBindings().get("ListeSuspensioneEtudROIterator");
            RowSetIterator rsi_list_susp = iter_list_susp.getViewObject().createRowSetIterator(null);
            Row row_susp = rsi_list_susp.last();
            
            if( row_susp != null){
                System.out.println(row_susp.getAttribute("DateFin").toString());
                System.out.println(row_susp.getAttribute("DateDebut").toString());
                System.out.println((Date)date_debut.getInputValue());
                row_susp.getAttribute("DateFin").toString();//DateDebut
                row_susp.getAttribute("DateDebut").toString();//
                Date date_fin_curr = (Date)row_susp.getAttribute("DateFin");
                Date date_debut_new = (Date)date_debut.getInputValue();

                if(date_fin_curr.compareTo(date_debut_new) > 0){
                    System.out.println(date_fin_curr +" > "+date_debut_new);
                    FacesMessage msg = new FacesMessage(" Une suspension est d�j� en cours jusqu'au "+dateFormat.format(date_fin_curr));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    System.out.println(date_fin_curr +" < "+date_debut_new);
                    if(iter_insc_ped.getCurrentRow() == null){
                        System.out.println(" pas d'insc ");
                        FacesMessage msg = new FacesMessage(" Impossible de suspendre l'�tudiant");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" L'�tudiant n'a pas puis achev� son inscription p�dagogique"));
                    }
                    else{
                        RichPopup popup = this.getPopup();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }

            }
            else{            
                if(iter_insc_ped.getCurrentRow() == null){
                    System.out.println(" pas d'insc 2 ");
                    FacesMessage msg = new FacesMessage(" Impossible de suspendre l'�tudiant");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" L'�tudiant n'a pas puis achev� son inscription p�dagogique"));
                }
                else{
                    RichPopup popup = this.getPopup();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
        }
        
        return null;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopup().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        
        OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
        list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc").toString()));
        list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
        list_insc_ped.execute();
        
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("listeInsPedIterator");
            //Create RowSetIterato iterate over viewObject
        if(iter_insc_ped.getCurrentRow()!=null)
            id_etud.setInputValue(iter_insc_ped.getCurrentRow().getAttribute("IdEtudiant"));
        RowSetIterator rsi_insc_ped = iter_insc_ped.getViewObject().createRowSetIterator(null);
        
        /*while (rsi_insc_ped.hasNext()) {
            Row row_get_insc_ped = rsi_insc_ped.next();
            //id_ins_ped
            //fingAndUpdateInsPed
            OperationBinding insc_ped = getBindings().getOperationBinding("fingAndUpdateInsPed");
            insc_ped.getParamsMap().put("id_ins_ped",  Long.parseLong(row_get_insc_ped.getAttribute("IdInscriptionPedagogique").toString()));
            insc_ped.getParamsMap().put("etat",  Long.parseLong("41"));     // 41 suspension etat inscription
            insc_ped.execute();
        }*/
        
        OperationBinding list_susp = getBindings().getOperationBinding("getListeSuspension");
        list_susp.getParamsMap().put("idpers",  Long.parseLong(sessionScope.get("id_pers_insc").toString()));
        list_susp.execute();
        
        AttributeBinding duree = (AttributeBinding) getBindings().getControlBinding("Duree");
        AttributeBinding date_debut = (AttributeBinding) getBindings().getControlBinding("DateDebut");
        AttributeBinding date_fin = (AttributeBinding) getBindings().getControlBinding("DateFin");
        AttributeBinding motif = (AttributeBinding) getBindings().getControlBinding("MotifSuspension");
        //MotifSuspension
        duree.setInputValue("");
        date_fin.setInputValue("");
        motif.setInputValue("");
    }
}
