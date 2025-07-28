package levee_suspension;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class LeveeSuspenduBean {
    private RichSelectBooleanCheckbox check;
    private Integer nombreInsc;
    private RichPopup pop;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String utilisateur = sessionScope.get("id_user").toString();

    public LeveeSuspenduBean() {
    }

    public void setNombreInsc(Integer nombreInsc) {
        this.nombreInsc = nombreInsc;
    }

    public Integer getNombreInsc() {
        return nombreInsc;
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


    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    i++;
                }
            }
        }
        return i;
    }
    
    public String onLeveeSuspension() {
        // Add event code here...
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("LesSuspenduROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        if(rsi.getRowCount()==0){
            
            FacesMessage msg = new FacesMessage(" Aucun étudiant suspendu ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{                 
            if(nombreCaseCoche("LesSuspenduROIterator")==0){
                FacesMessage msg = new FacesMessage(" Veuillez cocher au moins une suspension à annuler ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
            }
            else{
                //
                setNombreInsc(nombreCaseCoche("LesSuspenduROIterator"));
                
                RichPopup popup = this.getPop();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        return null;
    }
    
    public void onSelectAll(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(check.getValue());
        if(Boolean.parseBoolean(check.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("LesSuspenduROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
            }
        }
        else{
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("LesSuspenduROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    public void setCheck(RichSelectBooleanCheckbox check) {
        this.check = check;
    }

    public RichSelectBooleanCheckbox getCheck() {
        return check;
    }

    public void onCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop().hide();
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("LesSuspenduROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        
        Integer nbr = 0;
                
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    System.out.println(" levee "+nextRow.getAttribute("Levee"));
                    //System.out.println(" util levee "+nextRow.getAttribute("UtiLevee"));
                    OperationBinding op_levee_susp = getBindings().getOperationBinding("UpdateSuspension");
                    op_levee_susp.getParamsMap().put("id_susp",  Long.parseLong(nextRow.getAttribute("IdSuspension").toString()));                   
                    op_levee_susp.getParamsMap().put("levee",  1);
                    op_levee_susp.getParamsMap().put("util",  getUtilisateur());
                    op_levee_susp.execute();
                    nbr ++;
                }
            }
        }
        if(nbr > 0){
            OperationBinding op_aut = getBindings().getOperationBinding("RefreshSuspendu");
            op_aut.execute();
            FacesMessage msg = new FacesMessage( nbr+" Suspension(s) levée(s) avec succès ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }
}
