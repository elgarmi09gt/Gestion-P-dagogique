package etudiant;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class PersEtudiantBean {
    private RichPopup popNew;
    private int isEtud;
    private String titre;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    public PersEtudiantBean() {
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }


    public void setIsEtud(int isEtud) {
        this.isEtud = isEtud;
    }

    public int getIsEtud() {
        return isEtud;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void onNewEtudiant(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("ListePersonnesROIterator");
        Row currentRow = dciter.getCurrentRow();
        System.out.println("IdPersonne Rw"+currentRow.getAttribute("IdPersonne"));
        System.out.println("Nom Rw"+currentRow.getAttribute("Nom"));
        OperationBinding isEtudiant = bindings.getOperationBinding("isEtudiant");
        isEtudiant.getParamsMap().put("id_pers",  Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));
        
        Integer result = (Integer)isEtudiant.execute();
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Map sessionScope = adfCtx.getSessionScope();
        pageFlowScope.put("IdPersonne", currentRow.getAttribute("IdPersonne").toString());

        Integer is_etud = 0;
                                
        AttributeBinding idpersonne = (AttributeBinding) getBindings().getControlBinding("IdPersonne");
        AttributeBinding numero = (AttributeBinding) getBindings().getControlBinding("Numero");
        //AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        setIsEtud(result);
        if(result == 0){
            System.out.println("n est pas etud");
            setTitre("");
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
            Object result1 = operationBinding.execute();
                        
            if (!operationBinding.getErrors().isEmpty()) {
                return ;
            }
            idpersonne.setInputValue(currentRow.getAttribute("IdPersonne").toString());
            numero.setInputValue("");
            //util.setInputValue("");
            System.out.println(idpersonne.getInputValue());

        }
        else{
            System.out.println("etud");
            setTitre("est déjà inscrit comme étudiant");
            is_etud = 1;
            OperationBinding numEtudiant = getBindings().getOperationBinding("getEtudiants");
            numEtudiant.getParamsMap().put("idpers",  Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));
            numEtudiant.execute();
        }
        sessionScope.put("is_etud", is_etud);
        
        RichPopup popup = this.getPopNew();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);

    }

    public void setPopNew(RichPopup popNew) {
        this.popNew = popNew;
    }

    public RichPopup getPopNew() {
        return popNew;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();

        if (outcome == Outcome.ok) {
            //commit
            System.out.println("ok");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                return;
            }
            this.getPopNew().hide();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableBourses());
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        /*if(getIsEtud() ==0){
            System.out.println(" n est");
            
        }
        else
            System.out.println(" oui");
        getPopNew().hide();*/
        //BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        //ADFContext adfCtx = ADFContext.getCurrent();
        //Map pageFlowScope = adfCtx.getPageFlowScope();
        //Map sessionScope = adfCtx.getSessionScope();
        RichPopup popup = this.getPopNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("EtudiantsIterator");
        Row currentRow = dciter.getCurrentRow();
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        System.out.println(sessionScope.get("is_etud").toString()+"can");
        if(Integer.parseInt(sessionScope.get("is_etud").toString())==0)
            dciter.removeCurrentRow();
        OperationBinding operationBinding = getBindings().getOperationBinding("Execute");
        Object result = operationBinding.execute();
        /*String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableBourses());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();*/
    }
}
