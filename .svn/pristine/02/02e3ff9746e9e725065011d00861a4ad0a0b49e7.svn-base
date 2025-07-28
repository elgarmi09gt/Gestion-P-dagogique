package paiement_etudiant;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class PaiementEtudiantBean {
    private RichSelectBooleanCheckbox valide;

    public PaiementEtudiantBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String onValiderPaiement() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();

        AttributeBinding code_nationalite = (AttributeBinding) getBindings().getControlBinding("CodeNationalite");
        AttributeBinding operateur = (AttributeBinding) getBindings().getControlBinding("IdOperateur");
        AttributeBinding montant_recu = (AttributeBinding) getBindings().getControlBinding("MontantPercu");
        AttributeBinding cout_formation = (AttributeBinding) getBindings().getControlBinding("CoutFormation");
/*
        if(code_nationalite.getInputValue() == ""){
            FacesMessage msg = new FacesMessage(" Veuillez sélectionner une nationalité ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if(operateur.getInputValue() == null){
                FacesMessage msg = new FacesMessage(" Veuillez sélectionner un Opérateur ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                if(montant_recu.getInputValue() == null){
                    FacesMessage msg = new FacesMessage(" Veuillez saisir le montant perçu ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    if(valide.getValue() == null){
                        System.out.println(valide.getValue());
                        FacesMessage msg = new FacesMessage(" Veuillez cocher si l'étudiant est acquité des frais d'inscription ");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{ 
                        if(Long.parseLong(montant_recu.getInputValue().toString()) == Long.parseLong(cout_formation.getInputValue().toString())){*/
                            OperationBinding op_valide = getBindings().getOperationBinding("Commit");
                            Object result = op_valide.execute();
                            if (!op_valide.getErrors().isEmpty()) {
                                //handle errors if any
                                return null;
                            }
                            else{
                                sessionScope.put("is_en_regle_paie",1);
                                FacesMessage msg = new FacesMessage(" Paiement validé avec succes ");
                                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                        /*}
                        else{
                            FacesMessage msg = new FacesMessage(" Le montant perçu doit etre égal : "+cout_formation.getInputValue());
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }                      
                    }
                } 
            }
            
        }*/

        
        return null;
    }

    public void setValide(RichSelectBooleanCheckbox valide) {
        this.valide = valide;
    }

    public RichSelectBooleanCheckbox getValide() {
        return valide;
    }
}
