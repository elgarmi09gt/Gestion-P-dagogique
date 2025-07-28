package resultat_ancien_etud;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import model.services.inscriptionAppImpl;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class RechercheAncienEtudBean {
    private RichInputText nom;
    private RichInputText prenom;
    private RichInputDate date_naiss;
    private RichInputText num;
    private RichTable table;
    private RichPopup popup;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection tableCollection;

    public RechercheAncienEtudBean() {
    }

    public void setNom(RichInputText nom) {
        this.nom = nom;
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public RichInputText getNom() {
        return nom;
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

    public void setNum(RichInputText num) {
        this.num = num;
    }

    public RichInputText getNum() {
        return num;
    }
    //pour expression factory de l'application module
    public Object resolvElDC(String data) {
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp =
           elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
       return valueExp.getValue(elContext);
    }
    
    public String onSearch() {
        // Add event code here...
        
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject etudAncVo = am.getEtudAcnienRO();
        ViewCriteriaManager vcm = etudAncVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("EtudAcnienROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_etud", nom.getValue());
        vvm.setVariableValue("num", num.getValue());
        vvm.setVariableValue("prenom_etud", prenom.getValue());
        etudAncVo.applyViewCriteria(vc);
        etudAncVo.executeQuery();
        
        return null;
    }

    public String onReset() {
        // Add event code here...
        //retour à l'état initial
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject etudAncVo = am.getEtudAcnienRO();
        ViewObject attrVo=am.getAttrSearchPers();
        ViewCriteriaManager vcm = etudAncVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("EtudAcnienROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_etud", null);
        vvm.setVariableValue("num", null);
        vvm.setVariableValue("prenom_etud", null);
        etudAncVo.applyViewCriteria(vc);
        etudAncVo.executeQuery();
        etudAncVo.executeQuery();
        attrVo.executeQuery();
        return null;
    }

    public String onSelectAncEtud() {
        // Add event code here...
        CollectionModel model = (CollectionModel) table.getValue();
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) model.getWrappedData();
        //table synchronizes row selection with current binding row
        DCIteratorBinding tableIterator = tableBinding.getDCIteratorBinding();
        if (tableIterator.getCurrentRow() != null) {
            Object etud_anc_select =
                tableIterator.getCurrentRow().getAttribute("IdEtudiantAncien");
            //copy value into the pageFlowScope, which is returned in an task
            //flow param output
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("id_etud", etud_anc_select);
        }
        return "return_etud";
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        RichPopup popup = this.getPopup();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("EtudiantAncienIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public String onNewAncEtud() {
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("EtudiantAncienIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        RichPopup popup = this.getPopup();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        
        return null;
    }

    public void setTableCollection(RichPanelCollection tableCollection) {
        this.tableCollection = tableCollection;
    }

    public RichPanelCollection getTableCollection() {
        return tableCollection;
    }

    public void onDialog(DialogEvent dialogEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree"); 
        util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));       //utilisateur connecté
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {    //confirmer l'ajout
            //commit
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            //ré-executer la liste des anciens etudiants
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
            Object result1 = operationBinding1.execute();
            if (!operationBinding1.getErrors().isEmpty()) {
                return;
            }
            //Execute
            this.getPopup().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        }
    }
}
