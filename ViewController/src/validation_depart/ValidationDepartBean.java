package validation_depart;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class ValidationDepartBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private String nbr_valider;
    private RichPopup popConfVal;
    private RichPopup popNotOper;
    private RichPopup popAucunOper;

    public ValidationDepartBean() {
    }


    public void setNbr_valider(String nbr_valider) {
        this.nbr_valider = nbr_valider;
    }

    public String getNbr_valider() {
        return nbr_valider;
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
    
    public void onValidationDepart(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("dans la methode recu ");
        /*DCIteratorBinding iter_op_avalide = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAValDepartROIterator");
        Row row_op_avalide = iter_op_avalide.getCurrentRow();
        RowSetIterator rsi_op_avalide = iter_op_avalide.getViewObject().createRowSetIterator(null);
        System.out.println("row "+row_op_avalide);
        
        if(row_op_avalide != null){
            while (rsi_op_avalide.hasNext()) {
                Row row_op = rsi_op_avalide.next();
                System.out.println("OpValidee "+row_op.getAttribute("OpValidee"));
                if(row_op.getAttribute("OpValidee")!=null){
                    if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 1 ){
                        System.out.println("OpValidee idop "+row_op.getAttribute("IdOperation"));
                        System.out.println("OpValidee recu "+row_op.getAttribute("NumeroRecu"));
                    }
                }
            }

        }*/
    }

    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row row_op = rsi.next();
            if(row_op.getAttribute("OpValidee")!=null){
                if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 1 ){
                    i++;
                }
            }
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public String onValiderValDepart() {
        // Add event code here...
        System.out.println("dans la methode recu ");
        DCIteratorBinding iter_op_avalide = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAValDepartROIterator");
        Row row_op_avalide = iter_op_avalide.getCurrentRow();
        RowSetIterator rsi_op_avalide = iter_op_avalide.getViewObject().createRowSetIterator(null);
        System.out.println("row "+row_op_avalide);
        
        if(row_op_avalide != null){
            if(nombreCaseCoche("OperationAValDepartROIterator") > 0){
                setNbr_valider(nombreCaseCoche("OperationAValDepartROIterator")+"");
                RichPopup popup = this.getPopConfVal();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                /*while (rsi_op_avalide.hasNext()) {
                    Row row_op = rsi_op_avalide.next();
                    System.out.println("OpValidee "+row_op.getAttribute("OpValidee"));
                    if(row_op.getAttribute("OpValidee")!=null){
                        if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 1 ){
                            System.out.println("OpValidee idop "+row_op.getAttribute("IdOperation"));
                            System.out.println("OpValidee recu "+row_op.getAttribute("NumeroRecu"));
                            
                            OperationBinding getUpdateValOper = getBindings().getOperationBinding("findAndUpdateValOperation");
                            getUpdateValOper.getParamsMap().put("id_oper",  row_op.getAttribute("IdOperation"));  
                            getUpdateValOper.getParamsMap().put("annule",  "1");  
                            getUpdateValOper.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateValOper.execute();
                        }
                    }
                }*/
            }
            else{
                //veuillez au moins une operation à valideer
                RichPopup popup = this.getPopAucunOper();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

        }
        else{
            //Aucune opération à valider
            RichPopup popup = this.getPopNotOper();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        return null;
    }

    public void setPopConfVal(RichPopup popConfVal) {
        this.popConfVal = popConfVal;
    }

    public RichPopup getPopConfVal() {
        return popConfVal;
    }

    @SuppressWarnings("unchecked")
    public void onDialogValOper(DialogEvent dialogEvent) {
        // Add event code here...
        
        DCIteratorBinding iter_op_avalide = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAValDepartROIterator");
        Row row_op_avalide = iter_op_avalide.getCurrentRow();
        RowSetIterator rsi_op_avalide = iter_op_avalide.getViewObject().createRowSetIterator(null);
        System.out.println("row "+row_op_avalide);
        while (rsi_op_avalide.hasNext()) {
            Row row_op = rsi_op_avalide.next();
            System.out.println("OpValidee "+row_op.getAttribute("OpValidee"));
            if(row_op.getAttribute("OpValidee")!=null){
                if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 1 ){
                    System.out.println("OpValidee idop "+row_op.getAttribute("IdOperation"));
                    System.out.println("OpValidee recu "+row_op.getAttribute("NumeroRecu"));
                    
                    OperationBinding getUpdateValOper = getBindings().getOperationBinding("findAndUpdateValOperation");
                    getUpdateValOper.getParamsMap().put("id_oper",  row_op.getAttribute("IdOperation"));  
                    getUpdateValOper.getParamsMap().put("annule",  "1");  
                    getUpdateValOper.getParamsMap().put("util",  getUtilisateur());  
                    getUpdateValOper.execute();
                }
            }
        }
        OperationBinding getOperVal = getBindings().getOperationBinding("getOperationValidee");
        getOperVal.getParamsMap().put("id_annee",  getAnne_univers());  
        getOperVal.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));  
        getOperVal.getParamsMap().put("id_util",  getUtilisateur());  
        getOperVal.execute();
        
        OperationBinding getOperAVal = getBindings().getOperationBinding("getOperationAValidee");
        getOperAVal.getParamsMap().put("id_annee",  getAnne_univers());  
        getOperAVal.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));  
        getOperAVal.getParamsMap().put("id_util",  getUtilisateur()); 
        getOperAVal.execute();
    }

    public void onDialogValOperCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfVal().hide();
        
    }

    public void setPopNotOper(RichPopup popNotOper) {
        this.popNotOper = popNotOper;
    }

    public RichPopup getPopNotOper() {
        return popNotOper;
    }

    public void setPopAucunOper(RichPopup popAucunOper) {
        this.popAucunOper = popAucunOper;
    }

    public RichPopup getPopAucunOper() {
        return popAucunOper;
    }
}
