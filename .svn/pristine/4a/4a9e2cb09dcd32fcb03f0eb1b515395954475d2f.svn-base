package inscription_ancienne;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class InscriptionAncBean {

    private RichTable table;

    public InscriptionAncBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    // pour éditer la ligne courante d'une inscription ancienne
    public String onValiderModif() {
        // Add event code here...
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");  
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");

        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ListeInscAncROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        if(id_hist.getInputValue() == null){
            FacesMessage msg = new FacesMessage(" Historique Structure est obligatoire !");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        else{
            if(id_annee.getInputValue() == null){
                FacesMessage msg = new FacesMessage(" L'année Univers est obligatoire !");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                return null;
            }
            else{
                if(id_niv_form.getInputValue() == null){
                    FacesMessage msg = new FacesMessage(" Le niveau formation est obligatoire !");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    return null;
                }
                else{
                    if(rsi.getRowCount()==0){
                        FacesMessage msg = new FacesMessage(" Aucune inscription à éditer ! ");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                        return null;
                    }
                    else{
                        //recuperation des détails de la ligne courante à modifier
                        OperationBinding op_editer = getBindings().getOperationBinding("ExecuteWithParamsInsAncUp");
                        op_editer.getParamsMap().put("id_inscrp",  Long.parseLong(iter.getCurrentRow().getAttribute("IdInscriptionAncienne").toString()));
                        op_editer.execute();
                    }
                }
            }
        }

        
        return "recherche";     // pour lancer le task flow
    }

    // ajout d'une nouvelle inscription et de resultat d'un ancien étudiant
    public String onNewInscAncienne() {

        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");  
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");

        if(id_hist.getInputValue() == null){
            FacesMessage msg = new FacesMessage(" Historique Structure est obligatoire !");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            return null;
        }
        else{
            if(id_annee.getInputValue() == null){
                FacesMessage msg = new FacesMessage(" L'année Univers est obligatoire !");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                return null;
            }
            else{
                if(id_niv_form.getInputValue() == null){
                    FacesMessage msg = new FacesMessage(" Le niveau formation est obligatoire !");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    return null;
                }
                else{
                    //une ligne vide pour ajouter une nouvelle inscription ancienne
                    OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
                    Object result = operationBinding.execute();
                    if (!operationBinding.getErrors().isEmpty()) {
                        //handle errors if any
                        return null;
                    }
                }
            }
        }
        return "recherche";         // pour lancer le task flow
    }

    public void onReturnValider(ReturnEvent returnEvent) {
        // Add event code here...
       
        Object cancelFlag = returnEvent.getReturnValue();

        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTable()); //refresh la table
                
                //Ré-executer la liste des inscritpion ancienne
                OperationBinding operationBinding = getBindings().getOperationBinding("ExecuteListeInsAnc");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    return ;
                }

                FacesMessage msg = new FacesMessage(" L'inscription validée avec succès !");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }
}
