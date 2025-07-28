package enregistrement_diplome;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class EnregistrementDiplomeBean {
    private RichInputText idEtudiantUpdate;
    private RichSelectOneChoice annee;
    private RichSelectOneChoice grade_form;
    private RichSelectOneChoice specialite;
    private RichSelectOneChoice option;

    public EnregistrementDiplomeBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
// fonction qui retourne l'id de l'étudiant recherché
    public void onReturnEtudiant(ReturnEvent returnEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        
        Object cancelFlag = pageFlowScope.get("submitOrCancelFlag");
        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                Object etudId = returnEvent.getReturnValue();
                idEtudiantUpdate.resetValue();      // effacer l'ancienne valeur
                idEtudiantUpdate.setValue(etudId);      //affectation de la nouvelle valeur
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(idEtudiantUpdate);     //refresh le champ etudiant
            }
        }
    }

    public void setIdEtudiantUpdate(RichInputText idEtudiantUpdate) {
        this.idEtudiantUpdate = idEtudiantUpdate;
    }

    public RichInputText getIdEtudiantUpdate() {
        return idEtudiantUpdate;
    }

    public String onValiderEnregDiplome() {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        id_util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));        // utilisateur connecté
        
        AttributeBinding type_mention = (AttributeBinding) getBindings().getControlBinding("IdTypeMention");
        AttributeBinding grade = (AttributeBinding) getBindings().getControlBinding("IdGradesFormation");
        AttributeBinding specialite = (AttributeBinding) getBindings().getControlBinding("IdSpecialite");
        AttributeBinding option = (AttributeBinding) getBindings().getControlBinding("IdOption");
        AttributeBinding etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        AttributeBinding annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding lieu = (AttributeBinding) getBindings().getControlBinding("Lieu");
        AttributeBinding date_obt = (AttributeBinding) getBindings().getControlBinding("DateObtention");
        AttributeBinding pays_obt = (AttributeBinding) getBindings().getControlBinding("PaysObtention");        
        AttributeBinding serie = (AttributeBinding) getBindings().getControlBinding("Serie");
        AttributeBinding etab = (AttributeBinding) getBindings().getControlBinding("IdEtab");          
        System.out.println(annee.getInputValue());
        if(annee.getInputValue() == ""){
               FacesMessage msg = new FacesMessage(" L'année universitaire est obligatoire ! ");
               msg.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if(type_mention.getInputValue() == ""){
                   FacesMessage msg = new FacesMessage(" La mention est obligatoire ! ");
                   msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                if(grade.getInputValue() == ""){
                       FacesMessage msg = new FacesMessage(" Le grade de la formation est obligatoire ! ");
                       msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                       FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    if(specialite.getInputValue() == ""){
                           FacesMessage msg = new FacesMessage(" La Spécialité de la formation est obligatoire ! ");
                           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                           FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        if(option.getInputValue() == ""){
                               FacesMessage msg = new FacesMessage(" L'Option de la formation est obligatoire ! ");
                               msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                               FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                        else{
                            if(pays_obt.getInputValue() == ""){
                                   FacesMessage msg = new FacesMessage(" Le pays d'obtention est obligatoire ! ");
                                   msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                   FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                            else{
                                if(etud.getInputValue() == null){
                                       FacesMessage msg = new FacesMessage(" Sélectionnez un étudiant avant de valider ! ");
                                       msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                       FacesContext.getCurrentInstance().addMessage(null, msg);
                                }
                                else{
                                    OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                                    Object result = operationBinding1.execute();
                                    if (!operationBinding1.getErrors().isEmpty()) {
                                           //handle errors if any
                                           //...
                                           return null;
                                    }
                                    else{
                                       OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
                                       Object resultins = operationBinding.execute();
                                       if (!operationBinding.getErrors().isEmpty()) {
                                           return null;
                                       }
                                       
                                       FacesMessage msg = new FacesMessage(" Le diplome est enregistré avec succès ! ");
                                       msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                       FacesContext.getCurrentInstance().addMessage(null, msg);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }      
        
        return null;
    }


}
