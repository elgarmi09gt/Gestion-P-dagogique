package autorisation_inscription;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

public class ListeAutoriseBean {
    private RichInputText nom;
    private RichInputText prenom;
    private RichInputDate date_naiss;
    private RichInputText lieu_naiss;
    private RichInputText adresse;
    //private RichInputText email;
    private RichInputText cin;
    //private RichInputText num_portable;
    private RichPopup popup;
    private RichPopup popPers;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichInputText nom_pers_aut;
    private RichInputText prenom_pers_aut;
    private RichInputText cin_pers_aut;

    public ListeAutoriseBean() {
    }

    public void setNom(RichInputText nom) {
        this.nom = nom;
    }

    public RichInputText getNom() {
        return nom;
    }
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public String onRecherche() {
        // Add event code here...
        
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject liste_persVo = am.getListePersonnesRO();
        
        ViewCriteriaManager vcm = liste_persVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("ListePersonnesROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_bn", nom.getValue());
        vvm.setVariableValue("prenom", prenom.getValue());
        vvm.setVariableValue("date_naiss", date_naiss.getValue());
        vvm.setVariableValue("lieu_naiss", lieu_naiss.getValue());
        vvm.setVariableValue("adresse_bn", adresse.getValue());
        vvm.setVariableValue("cin", cin.getValue());                                                          
        liste_persVo.applyViewCriteria(vc);
        liste_persVo.executeQuery();
        
        return null;
    }

    public void setPrenom(RichInputText prenom) {
        this.prenom = prenom;
    }

    public RichInputText getPrenom() {
        return prenom;
    }

    public void setDate_naiss(RichInputDate date_naiss) {
        this.date_naiss = date_naiss;
    }

    public RichInputDate getDate_naiss() {
        return date_naiss;
    }

    public void setLieu_naiss(RichInputText lieu_naiss) {
        this.lieu_naiss = lieu_naiss;
    }

    public RichInputText getLieu_naiss() {
        return lieu_naiss;
    }

    public void setAdresse(RichInputText adresse) {
        this.adresse = adresse;
    }

    public RichInputText getAdresse() {
        return adresse;
    }


    public void setCin(RichInputText cin) {
        this.cin = cin;
    }

    public RichInputText getCin() {
        return cin;
    }



    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String autoriser() {
        // Add event code here...
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        AttributeBinding IdNiveauFormation = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        AttributeBinding IdPersonne = (AttributeBinding) getBindings().getControlBinding("IdPersonne1");
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree");

        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("ListePersonnesROIterator");
        Row currentRow = dciter.getCurrentRow();
        sessionScope.put("Nom", currentRow.getAttribute("Nom").toString());
        sessionScope.put("Prenom", currentRow.getAttribute("Prenoms").toString());
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result1 = operationBinding.execute();
                    
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        else{
            IdNiveauFormation.setInputValue(Long.parseLong(sessionScope.get("IdNiveauFormation").toString()));
            IdPersonne.setInputValue(Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));
            util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));   // utilisateur connecté
            
            RichPopup popup = this.getPopup();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }

        return null;
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

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            
            else{                
                OperationBinding liste_form = getBindings().getOperationBinding("ExecuteWithParams");
                liste_form.getParamsMap().put("id_niv_formation",  Long.parseLong(sessionScope.get("IdNiveauFormation").toString()));
                liste_form.getParamsMap().put("id_parc_maquette",  Long.parseLong(sessionScope.get("id_parc_maquette").toString()));
                liste_form.getParamsMap().put("id_annee",  Long.parseLong(sessionScope.get("id_annee").toString()));                
                liste_form.execute();                
                
                OperationBinding liste_pers_aut = getBindings().getOperationBinding("ExecuteWithParams1");
                liste_pers_aut.getParamsMap().put("id_niv_form",  Long.parseLong(sessionScope.get("IdNiveauFormation").toString()));
                liste_pers_aut.getParamsMap().put("id_annee",  Long.parseLong(sessionScope.get("id_annee").toString()));
                liste_pers_aut.getParamsMap().put("id_parc_maquette",  Long.parseLong(sessionScope.get("id_parc_maquette").toString()));
                liste_pers_aut.execute();
                
            }
            this.getPopup().hide();
        }
    }

    public void onNewPersonne(ActionEvent actionEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree1");

        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("PersonnesIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        else{
            util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));
            RichPopup popup = this.getPopPers();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void onDialogPers(DialogEvent dialogEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {        // commit
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            
            else{
                OperationBinding liste_form = getBindings().getOperationBinding("ExecuteWithParams");
                liste_form.getParamsMap().put("id_niv_formation",  Long.parseLong(sessionScope.get("IdNiveauFormation").toString()));
                liste_form.execute();
            }
            this.getPopPers().hide();
        }
    }

    public void setPopPers(RichPopup popPers) {
        this.popPers = popPers;
    }

    public RichPopup getPopPers() {
        return popPers;
    }

    public void onDialogCanPers(ClientEvent clientEvent) {
        RichPopup popup = this.getPopPers();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("PersonnesIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
    }

    public String resetAutorisePers() {
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject empVo = am.getListePersonnesRO();
        ViewObject attrVo=am.getAttrSearchPers();
        ViewCriteriaManager vcm = empVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("ListePersonnesROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_bn", null);
        vvm.setVariableValue("prenom", null);
        vvm.setVariableValue("date_naiss", null);
        vvm.setVariableValue("lieu_naiss", null);
        vvm.setVariableValue("adresse_bn", null);
        vvm.setVariableValue("cin", null);
        empVo.applyViewCriteria(vc);
        empVo.executeQuery();
        empVo.executeQuery();
        attrVo.executeQuery();

        return null;
    }

    public void setNom_pers_aut(RichInputText nom_pers_aut) {
        this.nom_pers_aut = nom_pers_aut;
    }

    public RichInputText getNom_pers_aut() {
        return nom_pers_aut;
    }

    public void setPrenom_pers_aut(RichInputText prenom_pers_aut) {
        this.prenom_pers_aut = prenom_pers_aut;
    }

    public RichInputText getPrenom_pers_aut() {
        return prenom_pers_aut;
    }

    public void setCin_pers_aut(RichInputText cin_pers_aut) {
        this.cin_pers_aut = cin_pers_aut;
    }

    public RichInputText getCin_pers_aut() {
        return cin_pers_aut;
    }

    public String searchPersAutorise() {
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject liste_pers_autoVo = am.getListePersAutorise();
        ViewCriteriaManager vcm = liste_pers_autoVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("ListePersAutoriseCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_var_bn", nom_pers_aut.getValue());
        vvm.setVariableValue("prenom_var_bn", prenom_pers_aut.getValue());
        vvm.setVariableValue("cin_var_bn", cin_pers_aut.getValue());                                                            
        liste_pers_autoVo.applyViewCriteria(vc);
        liste_pers_autoVo.executeQuery();
        
        return null;
    }

    public String resetPersAutorise() {
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject liste_pers_autoVo = am.getListePersAutorise();
        ViewObject attrVo=am.getAttrSearchPers();   // VO attribut de recherche
        ViewCriteriaManager vcm = liste_pers_autoVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("ListePersAutoriseCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_var_bn", null);
        vvm.setVariableValue("prenom_var_bn", null);
        vvm.setVariableValue("cin_var_bn", null);
        liste_pers_autoVo.applyViewCriteria(vc);
        liste_pers_autoVo.executeQuery();
        attrVo.executeQuery();
        
        return null;
    }
}
