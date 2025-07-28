package deliberation_semestrielle;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class DeliberationSemestrielleBean {
    private RichInputText valeurComp1;
    private RichInputText valeurComp2;
    private RichPopup popDelibSem;
    private RichPopup popClotSem;
    private RichPopup popComp;

    public DeliberationSemestrielleBean() {
    }
    Integer chgCritere=0;
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void onChangeValueCritere(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("DelibSemRepCritereCompLOVIterator");
        Row currentRow = dciter.getCurrentRow();
        System.out.println(currentRow.getAttribute("code"));
        ADFContext adfCtxDelib = ADFContext.getCurrent();
        Map sessionFlowScopedelib = adfCtxDelib.getSessionScope();
        if((Integer)currentRow.getAttribute("code")!=1)
            chgCritere = 1;
        sessionFlowScopedelib.put("isChange", chgCritere);
    }

    public void onRechercheRepechage(ActionEvent actionEvent) {
        // Add event code here...
        //codeFiltre: 1 moyenne, 0 note
        //codeCritere: 1 entre, 2 superieur, 3 inferieur, 4 egale
        //coderep: 1 un EC, 2 deux EC, 3 trois EC
        AttributeBinding attrCodeFiltre = (AttributeBinding) getBindings().getControlBinding("codeFiltre");
        AttributeBinding attrCodeCritere = (AttributeBinding) getBindings().getControlBinding("code");
        AttributeBinding attrCodeRepecheSur = (AttributeBinding) getBindings().getControlBinding("coderep");
        //AttributeBinding codeCritere = (AttributeBinding) getBindings().getControlBinding("code");
        
        Integer codeFiltre = (Integer)attrCodeFiltre.getInputValue();
        Integer codeCritere = (Integer)attrCodeCritere.getInputValue();
        Integer codeRepecheSur = (Integer)attrCodeRepecheSur.getInputValue();
        Integer valeurComparaison1 = Integer.parseInt(this.getValeurComp1().getValue().toString());
        Integer valeurComparaison2 =null;
        System.out.println("codeFiltre "+codeFiltre);
        System.out.println("codeCritere "+codeCritere);       
        System.out.println("codeRepecheSur "+codeRepecheSur);
        if((Integer)attrCodeCritere.getInputValue() == 1)
            valeurComparaison2 = Integer.parseInt(this.getValeurComp2().getValue().toString());
        
        System.out.println("val1 "+valeurComparaison1);
        System.out.println("val2 "+valeurComparaison2);
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionFlowScope = adfCtx.getSessionScope();
        sessionFlowScope.put("isRequired", 1);
 
    }

    public void setValeurComp1(RichInputText valeurComp1) {
        this.valeurComp1 = valeurComp1;
    }

    public RichInputText getValeurComp1() {
        return valeurComp1;
    }

    public void setValeurComp2(RichInputText valeurComp2) {
        this.valeurComp2 = valeurComp2;
    }

    public RichInputText getValeurComp2() {
        return valeurComp2;
    }

    public void refresh(){
        OperationBinding listeEtudiantsSem = getBindings().getOperationBinding("ExecuteWithParams");
        listeEtudiantsSem.getParamsMap().put("id_annee_univ", 81);
        listeEtudiantsSem.getParamsMap().put("id_semestre", 1);
        listeEtudiantsSem.getParamsMap().put("id_niv_form_parc", 101);
        listeEtudiantsSem.execute();
    }
    public void delibererSemestre(ActionEvent actionEvent) {
        // Add event code here...     
        refresh();
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
       
        OperationBinding isClosedAnnee = getBindings().getOperationBinding("isClosedAnnee");
        
        isClosedAnnee.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
        isClosedAnnee.getParamsMap().put("calendrier", 103);
        System.out.println("parc"+IdNiveauFormationParcours.getInputValue());
        Integer resultClosedAnnee = (Integer)  isClosedAnnee.execute();
        if (resultClosedAnnee == 1) {
            //refresh();                
            FacesMessage msg = new FacesMessage(" Impoosible d'effectuer la délibération du semestre "+IdSemestre+" , parce que l'année scolaire est déjà cloturée ");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }             
        else{
                OperationBinding isClosedSemestre = getBindings().getOperationBinding("isClosedSemestre");
                
                isClosedSemestre.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
                isClosedSemestre.getParamsMap().put("calendrier", 103);
                
                Integer resultClosedSemestre = (Integer)  isClosedSemestre.execute();
                if (resultClosedSemestre == 1) {
                    //refresh();                
                    FacesMessage msg = new FacesMessage("Impoosible d'effectuer la délibération du semestre "+IdSemestre+", parce que la délibération est déjà cloturé");
                    msg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }             
                else{
                        RichPopup popup = this.getPopDelibSem();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                }
        }
        
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...        
        String action = "O";
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        System.out.println(IdNiveauFormationParcours.toString());

        //calculMoyenneSemestrielle(Long calendrier,Long utilisateur)
        
        OperationBinding calculMoyenneSemestrielle = getBindings().getOperationBinding("calculMoyenneSemestrielle");
        calculMoyenneSemestrielle.getParamsMap().put("calendrier", 103);
        calculMoyenneSemestrielle.getParamsMap().put("utilisateur", 33);
        calculMoyenneSemestrielle.execute();
        
        //deliberationSemestrielle(Long parcours,Long calendrier,Long semestre,String action,Long utilisateur)
        
        OperationBinding deliberationSemestrielle = getBindings().getOperationBinding("deliberationSemestrielle");
        
        deliberationSemestrielle.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
        deliberationSemestrielle.getParamsMap().put("calendrier", 103);
        deliberationSemestrielle.getParamsMap().put("semestre", IdSemestre);
        deliberationSemestrielle.getParamsMap().put("action", action);
        deliberationSemestrielle.getParamsMap().put("utilisateur", 33);
        
        Integer result = (Integer)  deliberationSemestrielle.execute();
        if (result == 1) {
            refresh();                
            FacesMessage msg = new FacesMessage("Délibération du semestre "+IdSemestre+" est effectuée avec succès");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }             
        else{
            FacesMessage msg = new FacesMessage("La saisie des UE n'est pas encore cloturée");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        
        
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDelibSem().hide();
    }

    public void setPopDelibSem(RichPopup popDelibSem) {
        this.popDelibSem = popDelibSem;
    }

    public RichPopup getPopDelibSem() {
        return popDelibSem;
    }

    public void clotureSemestre(ActionEvent actionEvent) {
        // Add event code here...
         refresh();
         AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
         AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        
         OperationBinding isClosedAnnee = getBindings().getOperationBinding("isClosedAnnee");
         
         isClosedAnnee.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
         isClosedAnnee.getParamsMap().put("calendrier", 103);
         
         Integer resultClosedAnnee = (Integer)  isClosedAnnee.execute();
         if (resultClosedAnnee == 1) {
             //refresh();                
             FacesMessage msg = new FacesMessage(" Impoosible de clôturer à nouveau la délibération du semestre "+IdSemestre+", parce que l'année scolaire est déjà cloturée ");
             msg.setSeverity(FacesMessage.SEVERITY_WARN);
             FacesContext.getCurrentInstance().addMessage(null, msg);
         }             
         else{
                 OperationBinding DelibratedSemestre = getBindings().getOperationBinding("isDelibratedSemestre");
                 
                 DelibratedSemestre.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
                 DelibratedSemestre.getParamsMap().put("calendrier", 103);
                 
                 Integer resultDelibratedSemestre = (Integer)  DelibratedSemestre.execute();
                 if (resultDelibratedSemestre == 1) {
                     //refresh();                
                     RichPopup popup = this.getPopDelibSem();
                     RichPopup.PopupHints hints = new RichPopup.PopupHints();
                     //empty hints renders dialog in center of screen
                     popup.show(hints);
                 }             
                 else{
                     FacesMessage msg = new FacesMessage("Impoosible de clôturer la délibération du semestre "+IdSemestre+", parce que la délibération n'est pas encore effectuée");
                     msg.setSeverity(FacesMessage.SEVERITY_WARN);
                     FacesContext.getCurrentInstance().addMessage(null, msg);
                 }
         }
        
    }

    public void onDialogClot(DialogEvent dialogEvent) {
        // Add event code here...
        String action = "O";
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        System.out.println(IdNiveauFormationParcours.toString());
        
        //clotureSemestre(Long parcours,Long calendrier,Long semestre,String action,Long utilisateur)
        
        OperationBinding clotureSemestre = getBindings().getOperationBinding("clotureSemestre");
        
        clotureSemestre.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
        clotureSemestre.getParamsMap().put("calendrier", 103);
        clotureSemestre.getParamsMap().put("semestre", IdSemestre);
        clotureSemestre.getParamsMap().put("action", action);
        clotureSemestre.getParamsMap().put("utilisateur", 33);
        
        Integer result = (Integer)  clotureSemestre.execute();
        if (result == 1) {
            //refresh();                
            FacesMessage msg = new FacesMessage("Le Semestre "+IdSemestre+" est clôturé avec succès");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }             
        else{
            FacesMessage msg = new FacesMessage("Le Semestre "+IdSemestre+" n'est pas clôturé");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onDialogCanClot(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopClotSem().hide();
        
    }

    public void setPopClotSem(RichPopup popClotSem) {
        this.popClotSem = popClotSem;
    }

    public RichPopup getPopClotSem() {
        return popClotSem;
    }

    public void compenseSemestre(ActionEvent actionEvent) {
        // Add event code here...
         refresh();        
         AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
         AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        
         OperationBinding isClosedAnnee = getBindings().getOperationBinding("isClosedAnnee");
         
         isClosedAnnee.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
         isClosedAnnee.getParamsMap().put("calendrier", 103);
         
         Integer resultClosedAnnee = (Integer)  isClosedAnnee.execute();
         if (resultClosedAnnee == 1) {
             //refresh();                
             FacesMessage msg = new FacesMessage(" Impoosible d'effectuer la compensation du semestre "+IdSemestre+", parce que l'année scolaire est déjà cloturée ");
             msg.setSeverity(FacesMessage.SEVERITY_WARN);
             FacesContext.getCurrentInstance().addMessage(null, msg);
         }             
         else{
                 OperationBinding DelibratedSemestre = getBindings().getOperationBinding("isDelibratedSemestre");
                 
                 DelibratedSemestre.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
                 DelibratedSemestre.getParamsMap().put("calendrier", 103);
                 
                 Integer resultDelibratedSemestre = (Integer)  DelibratedSemestre.execute();
                 if (resultDelibratedSemestre == 1) {
                     //refresh();                
                     RichPopup popup = this.getPopComp();
                     RichPopup.PopupHints hints = new RichPopup.PopupHints();
                     //empty hints renders dialog in center of screen
                     popup.show(hints);
                 }             
                 else{
                     FacesMessage msg = new FacesMessage("Impoosible d'effectuer la compensation du semestre "+IdSemestre+", parce que la délibération n'est pas encore effectuée");
                     msg.setSeverity(FacesMessage.SEVERITY_WARN);
                     FacesContext.getCurrentInstance().addMessage(null, msg);
                 }
         }
    }

    public void setPopComp(RichPopup popComp) {
        this.popComp = popComp;
    }

    public RichPopup getPopComp() {
        return popComp;
    }

    public void onDialogCanComp(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopComp().hide();
    }

    public void onDialogComp(DialogEvent dialogEvent) {
        // Add event code here...
        String action = "O";
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");
        System.out.println(IdNiveauFormationParcours.toString());
        
        //compenseSemestre(Long parcours,Long calendrier,Long semestre,String action,Long utilisateur)
        
        OperationBinding compenseSemestre = getBindings().getOperationBinding("compenseSemestre");
        
        compenseSemestre.getParamsMap().put("parcours", IdNiveauFormationParcours.getInputValue());
        compenseSemestre.getParamsMap().put("calendrier", 103);
        compenseSemestre.getParamsMap().put("semestre", IdSemestre);
        compenseSemestre.getParamsMap().put("action", action);
        compenseSemestre.getParamsMap().put("utilisateur", 33);
        
        Integer result = (Integer)  compenseSemestre.execute();
        if (result == 1) {
            refresh();                
            FacesMessage msg = new FacesMessage("Le Semestre "+IdSemestre+" est compensable");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }             
        else{
            FacesMessage msg = new FacesMessage("Le Semestre "+IdSemestre+" n'est pas compensable");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
