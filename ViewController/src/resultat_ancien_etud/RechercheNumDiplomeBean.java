package resultat_ancien_etud;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import model.services.inscriptionAppImpl;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class RechercheNumDiplomeBean {
    private RichSelectOneChoice annee;
    private RichInputText numero;
    private RichSelectOneChoice structure;
    private RichInputText num_diplom;
    private RichTable table;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup pop;
    private RichPanelCollection tableCollection;

    public RechercheNumDiplomeBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    // Expression Factory pour Application Module
    public Object resolvElDC(String data) {
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp =
           elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
       return valueExp.getValue(elContext);
    }

    public void setAnnee(RichSelectOneChoice annee) {
        this.annee = annee;
    }

    public RichSelectOneChoice getAnnee() {
        return annee;
    }

    public void setNumero(RichInputText numero) {
        this.numero = numero;
    }

    public RichInputText getNumero() {
        return numero;
    }

    public void setStructure(RichSelectOneChoice structure) {
        this.structure = structure;
    }

    public RichSelectOneChoice getStructure() {
        return structure;
    }

    public String onSearch() {
        // Add event code here...
        
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject num_diplomeVo = am.getNumDiplomeInsAncRO();
        ViewCriteriaManager vcm = num_diplomeVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("NumDiplomeInsAncROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("id_annee", annee.getValue());
        num_diplomeVo.applyViewCriteria(vc);
        num_diplomeVo.executeQuery();

        return null;
    }

    public String onReset() {
        // Add event code here...
        //retour à l'état initial
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject etudAncVo = am.getNumDiplomeInsAncRO();
        ViewObject attrVo=am.getAttrSearchNumDiplomeRO();       // attributs de recherche
        ViewCriteriaManager vcm = etudAncVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("NumDiplomeInsAncROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("id_annee", null);
        etudAncVo.applyViewCriteria(vc);
        etudAncVo.executeQuery();
        attrVo.executeQuery();
        return null;
    }

    public void setNum_diplom(RichInputText num_diplom) {
        this.num_diplom = num_diplom;
    }

    public RichInputText getNum_diplom() {
        return num_diplom;
    }

    public String onSelectDiplome() {
        // Add event code here...
        
        CollectionModel model = (CollectionModel) table.getValue();
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) model.getWrappedData();
        //table synchronizes row selection with current binding row
        DCIteratorBinding tableIterator = tableBinding.getDCIteratorBinding();
        if (tableIterator.getCurrentRow() != null) {
            Object id_diplome_select = tableIterator.getCurrentRow().getAttribute("IdNumeroDiplome");
            //copy value into the pageFlowScope, which is returned in an task
            //flow param output
            System.out.println("id_diplome val"+id_diplome_select);
            ADFContext adfCtx = ADFContext.getCurrent();
            Map pageFlowScope = adfCtx.getPageFlowScope();
            pageFlowScope.put("id_diplome", id_diplome_select);
        }
        return "return";

    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public String onNewDiplome() {
        // Add event code here...

        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("NumeroDiplomeIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        RichPopup popup = this.getPop();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        
        return null;
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            OperationBinding op_commit = getBindings().getOperationBinding("Commit");
            Object result = op_commit.execute();
            if (!op_commit.getErrors().isEmpty()) {
                //handle errors if any
                return;
            }
            OperationBinding op_refresh = getBindings().getOperationBinding("Execute");
            Object result_refresh = op_refresh.execute();
            if (!op_refresh.getErrors().isEmpty()) {
                //handle errors if any
                return;
            }
            //Execute
            this.getPop().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        }
    }

    public void onDialogCan(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPop();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("NumeroDiplomeIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setTableCollection(RichPanelCollection tableCollection) {
        this.tableCollection = tableCollection;
    }

    public RichPanelCollection getTableCollection() {
        return tableCollection;
    }
}
