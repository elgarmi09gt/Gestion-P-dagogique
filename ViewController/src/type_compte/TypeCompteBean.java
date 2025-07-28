package type_compte;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class TypeCompteBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection tableCollectRep;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private RichPopup popNewType;
    private RichPopup popEditType;
    private RichPopup popEditTypErr;
    private RichPopup popDelType;
    private RichPopup popDelTypErr;

    public TypeCompteBean() {
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void onTypeCompteNew(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TypeCompteIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmp");
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertTypeCmp");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        id_util.setInputValue(getUtilisateur());
        RichPopup popup = this.getPopNewType();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onTypeCompteEdit(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_type = (DCIteratorBinding) bindings.get("TypeCompteROIterator");
        Row current_type = iter_type.getCurrentRow();
        if(current_type != null){
            OperationBinding op_get_type = bindings.getOperationBinding("getTypeCmptCurrent");
            op_get_type.getParamsMap().put("id_type_cmpt",  Long.parseLong(current_type.getAttribute("IdTypeCompte").toString()));
            op_get_type.execute();
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiModifieTypeCmp");
            id_util.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopEditType();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            RichPopup popup = this.getPopEditTypErr();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void onTypeCompteDel(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_type = (DCIteratorBinding) bindings.get("TypeCompteROIterator");
        Row current_type = iter_type.getCurrentRow();
        if(current_type != null){
            OperationBinding op_get_pers = bindings.getOperationBinding("getTypeCmptCurrent");
            op_get_pers.getParamsMap().put("id_type_cmpt",  Long.parseLong(current_type.getAttribute("IdTypeCompte").toString()));
            op_get_pers.execute();
            RichPopup popup = this.getPopDelType();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            RichPopup popup = this.getPopDelTypErr();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopNewType(RichPopup popNewType) {
        this.popNewType = popNewType;
    }

    public RichPopup getPopNewType() {
        return popNewType;
    }

    public void onDialogType(DialogEvent dialogEvent) {
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
            this.getPopNewType().hide();
            OperationBinding op_table_type_cmpt = getBindings().getOperationBinding("refreshTableTypeCmp");
            op_table_type_cmpt.execute();
        }
    }
    public void onDialogCanNew(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopNewType().hide();
    }

    public void setPopEditType(RichPopup popEditType) {
        this.popEditType = popEditType;
    }

    public RichPopup getPopEditType() {
        return popEditType;
    }

    public void onDialogCanEdit(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopEditType().hide();
    }

    public void setPopEditTypErr(RichPopup popEditTypErr) {
        this.popEditTypErr = popEditTypErr;
    }

    public RichPopup getPopEditTypErr() {
        return popEditTypErr;
    }

    public void setPopDelType(RichPopup popDelType) {
        this.popDelType = popDelType;
    }

    public RichPopup getPopDelType() {
        return popDelType;
    }

    public void onDialogDelType(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteTypeCmp");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                              
                OperationBinding op_table_type_cmpt = getBindings().getOperationBinding("refreshTableTypeCmp");
                op_table_type_cmpt.execute();
                
                return ;
            }
        }
    }

    public void onDialogTypeCanDel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDelType().hide();
    }

    public void setPopDelTypErr(RichPopup popDelTypErr) {
        this.popDelTypErr = popDelTypErr;
    }

    public RichPopup getPopDelTypErr() {
        return popDelTypErr;
    }
}
