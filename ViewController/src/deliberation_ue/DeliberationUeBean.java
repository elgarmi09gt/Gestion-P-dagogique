package deliberation_ue;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DeliberationUeBean {
    private RichPopup popDelibUe;
    private RichTable tableDelib;
    private RichPopup popClot;
    private    HashMap<String, String> list 
                    = new HashMap<String, String>();
    private    HashMap<String, String> list1 
                    = new HashMap<String, String>();

    public DeliberationUeBean() {
       
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        
        list = getEc(IdFiliereUeSemstre.getInputValue().toString());
        
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public HashMap<String, String> getList() {
        return list;
    }

    public HashMap getModeControle(String mode){
        HashMap<String, String> list2 
                    = new HashMap<String, String>(); 
        OperationBinding lesCtrl = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ExecuteWithParamsMod");
        lesCtrl.getParamsMap().put("id_fil_ue_ec", Long.parseLong(mode));
        lesCtrl.execute();  
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeModeControlIterator");
                //Create RowSetIterato iterate over viewObject
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             list2.put(nextRow.getAttribute("IdModeControleEc").toString(),nextRow.getAttribute("LibelleLong").toString());

         }
        rsi.closeRowSetIterator();

        return list2;
    }

    public HashMap getEc(String idfil){
        HashMap<String, String> list2 
                    = new HashMap<String, String>(); 
        
        OperationBinding listeEtudiantsUe = getBindings().getOperationBinding("ExecuteWithParamsLesEc");
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        listeEtudiantsUe.getParamsMap().put("id_fil_ue", Long.parseLong(idfil));
        //System.out.println("ec fil"+IdFiliereUeSemstre.getInputValue());
        /*listeEtudiantsUe.getParamsMap().put("id_fil_sem_ue", 81);
        listeEtudiantsUe.getParamsMap().put("id_cal_delib", 1);*/
        listeEtudiantsUe.execute();
        
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeEcIterator");
                //Create RowSetIterato iterate over viewObject
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             list2.put(nextRow.getAttribute("IdFiliereUeSemstreEc").toString(),nextRow.getAttribute("Codification").toString());
         }    
         rsi.closeRowSetIterator();
        return list2;
    }

    public String getNote(String num,String mod){
        OperationBinding lesEtudiantsNotes = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ExecuteWithParamsEtudNote");
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        lesEtudiantsNotes.getParamsMap().put("id_mode_ctrl", Long.parseLong(mod));
        lesEtudiantsNotes.getParamsMap().put("id_cal", 1);
        lesEtudiantsNotes.getParamsMap().put("id_ec", 106);
        lesEtudiantsNotes.getParamsMap().put("numero_etud", num);
        lesEtudiantsNotes.execute();
                
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudiantNoteInterIterator");
            //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Row nextRow = rsi.first();
        //System.out.println("Numero Etud "+nextRow2.getAttribute("Numero").toString());
        //System.out.println("Note "+nextRow.getAttribute("Note"));
        String note;
        if(nextRow.getAttribute("Note")==null)
            note="";
        else
            note=""+(Float)nextRow.getAttribute("Note"); 
        rsi.closeRowSetIterator();
        return note;
        
        
    }
    public void refresh(){
        OperationBinding listeEtudiantsUe = getBindings().getOperationBinding("ExecuteWithParamsListeEtud");
        AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
        listeEtudiantsUe.getParamsMap().put("id_fil_sem_ue", IdFiliereUeSemstre.getInputValue());
        
        /*listeEtudiantsUe.getParamsMap().put("id_fil_sem_ue", 81);
        listeEtudiantsUe.getParamsMap().put("id_cal_delib", 1);*/
        listeEtudiantsUe.execute();
        
        
        OperationBinding lesEtudiants = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ExecuteWithParamsEtudInsc");
        //AttributeBinding IdFiliereUeSemstreEc = (AttributeBinding) BindingContext.getCurrent().getCurrentBindingsEntry().getControlBinding("IdFiliereUeSemstreEc");
        lesEtudiants.getParamsMap().put("id_fil_ue", IdFiliereUeSemstre.getInputValue());
        lesEtudiants.getParamsMap().put("semestre", 1);
        lesEtudiants.getParamsMap().put("annee", 1);
        lesEtudiants.getParamsMap().put("parcours", 42);
        
        lesEtudiants.execute(); 
 /*       
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeEtudiantInscIterator");
                //Create RowSetIterato iterate over viewObject
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            
            System.out.println("Num "+nextRow.getAttribute("Numero")+"");
        }
  */      
        
        
        
        list = getEc(IdFiliereUeSemstre.getInputValue().toString());
        System.out.println("list "+list);
        //
        OperationBinding isclosed = getBindings().getOperationBinding("isClosedUe");
        isclosed.getParamsMap().put("fileUesem", IdFiliereUeSemstre.getInputValue());
        isclosed.getParamsMap().put("calendrier", 1);
        Integer result = (Integer)isclosed.execute();
    
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionFlowScope = adfCtx.getSessionScope();
        sessionFlowScope.put("isClosed", result);
        sessionFlowScope.get("isClosed");
        System.out.println("Var "+sessionFlowScope.get("isClosed"));
        
        if(result == 1){
            FacesMessage msg = new FacesMessage("L'Ue est déja cloturée. Vous pouvez importer le PV de la délibération");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
                OperationBinding isDelibrateUe = getBindings().getOperationBinding("isDelibrateUe");
                isDelibrateUe.getParamsMap().put("fileUesem", IdFiliereUeSemstre.getInputValue());
                isDelibrateUe.getParamsMap().put("calendrier", 1);
                Integer resultDelib = (Integer)isDelibrateUe.execute();
        
                ADFContext adfCtxDelib = ADFContext.getCurrent();
                Map sessionFlowScopedelib = adfCtxDelib.getSessionScope();
                sessionFlowScopedelib.put("isClosedDelib", resultDelib);
                
                if(resultDelib == 1){
                    FacesMessage msg = new FacesMessage("L'Ue est déja délibérée. Vous pouvez effectuer à nouveau la délibération ou cloturer");
                    msg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    /*FacesMessage msg = new FacesMessage("L'Ue n'est pas encore délibérée. Veuillez cloturer avant d'effectuer la délibération");
                    msg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, msg);*/
                }
        }
    }
    public void onRefresh(ActionEvent actionEvent) {
        // Add event code here...
        refresh();
        
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDelibUe().hide();
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
            String action = "O";
            //calculMoyenneUe(Long calendrier,Long fileUesem,Long utilisateur)
            OperationBinding calculMoyenneUe = getBindings().getOperationBinding("calculMoyenneUe");
            calculMoyenneUe.getParamsMap().put("calendrier", 1);
            calculMoyenneUe.getParamsMap().put("fileUesem", IdFiliereUeSemstre.getInputValue());
            //System.out.println("fil_ue "+IdFiliereUeSemstre.getInputValue());
            calculMoyenneUe.getParamsMap().put("utilisateur", 33);
            calculMoyenneUe.execute();
            
            //deliberationUe(Long fil_ue,Long calendrier,String action,Long utilisateur)

            OperationBinding deliberationUe = getBindings().getOperationBinding("deliberationUe");
           
            deliberationUe.getParamsMap().put("fil_ue", IdFiliereUeSemstre.getInputValue());
            deliberationUe.getParamsMap().put("calendrier", 1);
            deliberationUe.getParamsMap().put("action", action);
            deliberationUe.getParamsMap().put("utilisateur", 33);
            
            Integer result = (Integer)  deliberationUe.execute();
            if (result == 1) {
                refresh();                
                FacesMessage msg = new FacesMessage("Délibération de l'Ue est effectuée avec succès");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }             
            else{
                FacesMessage msg = new FacesMessage("La saisie des EC n'est pas encore cloturée");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
            /*OperationBinding listeEtudiantsUe = getBindings().getOperationBinding("ExecuteWithParams");
            listeEtudiantsUe.getParamsMap().put("id_fil_sem_ue", IdFiliereUeSemstre.getInputValue());
            listeEtudiantsUe.getParamsMap().put("id_cal_delib", 103);
            listeEtudiantsUe.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableDelib);*/
        }
    }

    public void setPopDelibUe(RichPopup popDelibUe) {
        this.popDelibUe = popDelibUe;
    }

    public RichPopup getPopDelibUe() {
        return popDelibUe;
    }

    public void setTableDelib(RichTable tableDelib) {
        this.tableDelib = tableDelib;
    }

    public RichTable getTableDelib() {
        return tableDelib;
    }

    public void onDialogClot(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            AttributeBinding IdFiliereUeSemstre = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
            String action = "O";
            //deliberationUe(Long calendrier,Long fileUesem,Long utilisateur)
            OperationBinding clotureUe = getBindings().getOperationBinding("clotureUe");
            clotureUe.getParamsMap().put("fil_ue", IdFiliereUeSemstre.getInputValue());
            clotureUe.getParamsMap().put("calendrier", 1);
            clotureUe.getParamsMap().put("utilisateur", 33);
            clotureUe.getParamsMap().put("action", action);
            
            Integer result = (Integer) clotureUe.execute();
            if (result == 1) {
                refresh();
                FacesMessage msg = new FacesMessage("L'Ue est cloturée avec succès");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }             
            else{
                FacesMessage msg = new FacesMessage("L'Ue n'est pas cloturée");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void onDialogClotCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopClot().hide();
    }

    public void setPopClot(RichPopup popClot) {
        this.popClot = popClot;
    }

    public RichPopup getPopClot() {
        return popClot;
    }
}
