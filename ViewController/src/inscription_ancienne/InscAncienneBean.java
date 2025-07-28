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

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class InscAncienneBean {
    private RichInputText etud_update;
    private RichInputText diplome_update;

    public InscAncienneBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    //pour recupérer IdEtudiantAncien après une recherche ou un ajout de nouvel étudiant
    public void onReturnEtud(ReturnEvent returnEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        
        Object cancelFlag = pageFlowScope.get("submitOrcancelEtud");
        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                Object etudId = returnEvent.getReturnValue();
                etud_update.resetValue();
                etud_update.setValue(etudId);
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(etud_update);
            }
        }
    }

    public void setEtud_update(RichInputText etud_update) {
        this.etud_update = etud_update;
    }

    public RichInputText getEtud_update() {
        return etud_update;
    }
    
    //pour recupérer IdDiplome après une recherche ou un ajout de nouveau numéro diplome
    public void onReturnDiplome(ReturnEvent returnEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        
        Object cancelFlag = pageFlowScope.get("submitOrCanDiplom");
        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                Object numId = returnEvent.getReturnValue();
                diplome_update.resetValue();
                diplome_update.setValue(numId);
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(diplome_update);
            }
        }
    }

    public void setDiplome_update(RichInputText diplome_update) {
        this.diplome_update = diplome_update;
    }

    public RichInputText getDiplome_update() {
        return diplome_update;
    }
    // valider une nouvelle inscription et resultat d'un ancien étudiant
    public String onValiderInsAnc() {
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");  
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours1");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("IdUtilisateur");
        
        AttributeBinding id_hist_commit = (AttributeBinding) getBindings().getControlBinding("IdHistoriqueStructure");  
        AttributeBinding id_annee_commit = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_niv_form_commit = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding id_util_commit = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        
        id_hist_commit.setInputValue(Long.parseLong(id_hist.toString()));
        id_annee_commit.setInputValue(Long.parseLong(id_annee.toString()));
        id_niv_form_commit.setInputValue(Long.parseLong(id_niv_form.toString()));
        id_util_commit.setInputValue(Long.parseLong(id_util.toString()));
        
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //handle errors if any
            return null;
        }
        else{           
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("submitInsc", "submit");

        }
        
        return "return";
    }
    
}
