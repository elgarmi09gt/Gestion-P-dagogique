package groupe_ling_niv_form;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class GroupeLingBean {
    private RichPopup popNew;
    private RichPanelCollection tableCollection;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popEdit;
    private RichSelectBooleanCheckbox checkGrpLang;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String utilisateur = sessionScope.get("id_user").toString();

    public GroupeLingBean() {
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void onNewGroupeLing(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("GroupeLinguistiqueIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
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

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void onDialog(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree"); 
            id_util.setInputValue(Long.parseLong(getUtilisateur()));    // utilisateur connecté
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            this.getPopNew().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        RichPopup popup = this.getPopNew();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("GroupeLinguistiqueIterator");
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

    public void onDialogCanGrp(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopNew().hide();
    }

    public void onEdit(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter_grp = (DCIteratorBinding) getBindings().get("GroupeLinguistiqueIterator");        
        RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
        
        Integer i =0;
        if(rsi_grp.getRowCount()==0){
            FacesMessage msg = new FacesMessage(" Veuillez ajouter un Groupe Lingustique avant d'éditer ! ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            RichPopup popup = this.getPopEdit();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }



    public String onValiderGrpLang() {
        // Add event code here...
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("LanguesROVO2Iterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        
        DCIteratorBinding iter_grp = (DCIteratorBinding) getBindings().get("GroupeLinguistiqueIterator");        
        RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
        
        Integer i =0;
        if(rsi_grp.getRowCount()==0){
            FacesMessage msg = new FacesMessage(" Veuillez ajouter un Groupe Lingustique avant de valider ! ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        else{ 
            if(rsi.getRowCount()==0){   // si la liste des langues est vide
                FacesMessage msg = new FacesMessage(" Aucune Langue disponible ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{ 
                if(nombreCaseCoche("LanguesROVO2Iterator")==0){     // les cases cochées
                    FacesMessage msg = new FacesMessage(" Veuillez cocher une Langue avant de valider ! ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    AttributeBinding id_grp_ling = (AttributeBinding) getBindings().getControlBinding("IdGrpLing");  
                    AttributeBinding id_lang = (AttributeBinding) getBindings().getControlBinding("IdLangue");
                    AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
                    
                    String lib_grp = iter_grp.getCurrentRow().getAttribute("LibelleLong").toString();
                    // parcours les cases cochées
                    while (rsi.hasNext()) {
                        Row nextRow = rsi.next();
                        if(nextRow.getAttribute("case_cocher")!=null){
                            if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                                OperationBinding op_grp = getBindings().getOperationBinding("CreateInsertGrpLingLang");
                                Object result_ped = op_grp.execute();
                                              
                                if (!op_grp.getErrors().isEmpty()) {
                                   return null;
                                }
                                else{
                                   id_grp_ling.setInputValue(Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
                                   id_lang.setInputValue(Long.parseLong(nextRow.getAttribute("IdLangue").toString()));
                                   id_util.setInputValue(Long.parseLong(getUtilisateur()));
                                   
                                   OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                                   Object result = operationBinding1.execute();
                                   if (!operationBinding1.getErrors().isEmpty()) {
                                           //handle errors if any
                                           //...
                                           return null;
                                   }
                                   else{
                                        i++;    // nombre de cases cochées
                                       }
                                   }
                            }
                        }
                    }
                    //
                    if (i < 1) {
                        FacesMessage msg = new FacesMessage(" Impossible d'ajouter une langue dans le groupe "+lib_grp+" ! ");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        OperationBinding op_ref_lang = getBindings().getOperationBinding("ExecuteLang");
                        op_ref_lang.execute();
                        OperationBinding op_ref_grp_lang = getBindings().getOperationBinding("ExecuteGrpLingLang");
                        op_ref_grp_lang.execute();
                        
                        FacesMessage msg = new FacesMessage(" "+ i+" Langue(s) ajoutées dans le groupe "+lib_grp+" ! ");
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }
        }
        return null;
    }
// donne le nombre de cases cochées    
    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    i++;
                }
            }
        }
        return i;
    }

    public void setPopEdit(RichPopup popEdit) {
        this.popEdit = popEdit;
    }

    public RichPopup getPopEdit() {
        return popEdit;
    }

    public void onSelectAllGrpLang(ValueChangeEvent valueChangeEvent) {
        // cocher tous
        if(Boolean.parseBoolean(checkGrpLang.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("LanguesROVO2Iterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
            }
        }
        else{
            //décocher tous
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("LanguesROVO2Iterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    public void setCheckGrpLang(RichSelectBooleanCheckbox checkGrpLang) {
        this.checkGrpLang = checkGrpLang;
    }

    public RichSelectBooleanCheckbox getCheckGrpLang() {
        return checkGrpLang;
    }
}
