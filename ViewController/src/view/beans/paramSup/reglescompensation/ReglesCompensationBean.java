package view.beans.paramSup.reglescompensation;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class ReglesCompensationBean {
    private RichSelectOneChoice parcours;
    private RichSelectOneRadio regles;
    private RichPopup popupRuleExist;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichTable parcoursRuleTable;

    public ReglesCompensationBean() {
    }

    public void setParcours(RichSelectOneChoice parcours) {
        this.parcours = parcours;
    }

    public RichSelectOneChoice getParcours() {
        return parcours;
    }

    public void setRegles(RichSelectOneRadio regles) {
        this.regles = regles;
    }

    public RichSelectOneRadio getRegles() {
        return regles;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnAddRule(ActionEvent actionEvent) {
        //Integer IdAnneesUnivers=2;
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationParcours");
        Integer IdNiveauFormationParcours = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        System.out.println("IdNiveauFormationParcours " + IdNiveauFormationParcours);

        attrIdBinding = (AttributeBinding) container.getControlBinding("IdRegleCompens");
        Integer IdRegleCompens = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        System.out.println("IdRegleCompens " + IdRegleCompens);
        // Un parcours pour une année ne peu avoir qu'un type de compensation
        //Verifier si le parcour pour cet année a deja une  régle de compensation
        //IsCompensedRuleExist
        OperationBinding iscompend = container.getOperationBinding("IsCompensedRuleExist");
        iscompend.getParamsMap().put("annee", getAnne_univers());
        iscompend.getParamsMap().put("parcours", IdNiveauFormationParcours);
        iscompend.execute();
        DCIteratorBinding is_exist_iter =
            (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("IsCompensedRuleParcoursExistIterator");
        Row crnt_is = is_exist_iter.getCurrentRow();
        if (crnt_is == null) {
            // Pas encore de regle de compensation

            OperationBinding addRuleParcours = container.getOperationBinding("CreateParcoursRegle");
            addRuleParcours.getParamsMap().put("IdNiveauFormationParcours", IdNiveauFormationParcours);
            addRuleParcours.getParamsMap().put("UtiCree", getUtilisateur());
            addRuleParcours.getParamsMap().put("IdRegleCompens", IdRegleCompens);
            addRuleParcours.getParamsMap().put("IdAnneesUnivers", getAnne_univers());
            addRuleParcours.execute();

            OperationBinding operationCommit = container.getOperationBinding("Commit");
            Object result = operationCommit.execute();
            OperationBinding RuleParcours = container.getOperationBinding("GetRuleCompens");
            RuleParcours.getParamsMap().put("parcours", IdNiveauFormationParcours);
            RuleParcours.getParamsMap().put("annee", getAnne_univers());
            RuleParcours.execute();
            AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(this.getParcoursRuleTable());
        } else {
            System.out.println("Parcour a deja une regle de compensation pour cette annee !!!" +
                               crnt_is.getAttribute("Libelle") + "(" + crnt_is.getAttribute("Description") + ")");
            ADFContext adfctx = ADFContext.getCurrent();
            Map sessionApp = adfctx.getSessionScope();
            sessionApp.put("Libelle", crnt_is.getAttribute("Libelle"));
            sessionApp.put("Description", crnt_is.getAttribute("Description"));
            RichPopup popup = this.getPopupRuleExist();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopupRuleExist(RichPopup popupRuleExist) {
        this.popupRuleExist = popupRuleExist;
    }

    public RichPopup getPopupRuleExist() {
        return popupRuleExist;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursChanged(ValueChangeEvent valueChangeEvent) {
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationParcours");
        System.out.println("Parcours : "+attrIdBinding.getInputValue().toString());
        Integer IdNiveauFormationParcours = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        OperationBinding RuleParcours = container.getOperationBinding("GetRuleCompens");
        RuleParcours.getParamsMap().put("parcours", IdNiveauFormationParcours);
        RuleParcours.getParamsMap().put("annee", getAnne_univers());
        RuleParcours.execute();
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getParcoursRuleTable());
    }

    @SuppressWarnings("unchecked")
    public void getRule(){
        BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationParcours");
        System.out.println("Parcours : "+attrIdBinding.getInputValue().toString());
        Integer IdNiveauFormationParcours = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        OperationBinding RuleParcours = container.getOperationBinding("GetRuleCompens");
        RuleParcours.getParamsMap().put("parcours", IdNiveauFormationParcours);
        RuleParcours.getParamsMap().put("annee", getAnne_univers());
        RuleParcours.execute();
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.getParcoursRuleTable());
    }
    public void setParcoursRuleTable(RichTable parcoursRuleTable) {
        this.parcoursRuleTable = parcoursRuleTable;
    }

    public RichTable getParcoursRuleTable() {
        return parcoursRuleTable;
    }

    @SuppressWarnings("unchecked")
    public void OnConfirmDelete(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.yes) {
            BindingContainer container = BindingContext.getCurrent().getCurrentBindingsEntry();
            AttributeBinding attrIdBinding = (AttributeBinding) container.getControlBinding("IdNiveauFormationParcours");
            Integer IdNiveauFormationParcours = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
            DCIteratorBinding rule_iter =
                (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ReglesCompensationPArcoursROVOIterator");
            Row crnt_rule = rule_iter.getCurrentRow();
            if (crnt_rule != null) {
                OperationBinding opDelete = container.getOperationBinding("deleteRuleNivParc");
                opDelete.getParamsMap().put("id_niv_reg_parc", Integer.parseInt(crnt_rule.getAttribute("IdNivFormParcReglCompens").toString()));
                opDelete.execute();
                OperationBinding operationCommit = container.getOperationBinding("Commit");
                Object result = operationCommit.execute();
                OperationBinding RuleParcours = container.getOperationBinding("GetRuleCompens");
                RuleParcours.getParamsMap().put("parcours", IdNiveauFormationParcours);
                RuleParcours.getParamsMap().put("annee", getAnne_univers());
                RuleParcours.execute();
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(this.getParcoursRuleTable());
            }
        }
    }
}
