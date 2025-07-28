package parametrage_cle_repartition;

import java.util.Date;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import model.services.ecolageAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class ClesRepartitionBean {
    private RichPopup popNew;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection tableCollectRep;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private RichPopup popEdit;
    private RichPopup popDel;
    private RichSelectBooleanCheckbox chek_all;
    private RichPopup popNewType;
    private RichPopup popEditType;
    private RichPopup popDelType;
    private RichInputText input_ref;
    private RichPopup popErrNew;
    private RichPopup popImpDelRep;
    private RichPopup popDelRepVide;
    private RichPopup popErrEdit;
    private RichPopup popEditTypErr;
    private RichPopup popDelTypErr;

    public ClesRepartitionBean() {
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void onNewRepartition(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("RepartitionIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_strc_rep = (AttributeBinding) getBindings().getControlBinding("IdStructureRep");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversRep");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeRep");
        if(oldCcurrentRow == null){
            //adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertRep");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return ;
            }
            id_strc_rep.setInputValue(id_strc.getInputValue());
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopNew();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            RichPopup popup = this.getPopErrNew();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    @SuppressWarnings("unchecked")
    public void onEditerRepartition(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_rep = (DCIteratorBinding) bindings.get("RepartitionROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            OperationBinding op_get_pers = bindings.getOperationBinding("getRepartitionCurrent");
            op_get_pers.getParamsMap().put("id_rep",  Long.parseLong(current_rep.getAttribute("IdRepartition").toString()));
            op_get_pers.execute();
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiModifieRep");
            id_util.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopEdit();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            RichPopup popup = this.getPopErrEdit();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    @SuppressWarnings("unchecked")
    public void onDeleteRepartition(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_rep = (DCIteratorBinding) bindings.get("RepartitionROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            
            OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
            op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
            op_rech_form_rep.getParamsMap().put("id_form",  id_form);
            op_rech_form_rep.getParamsMap().put("id_annee",  getAnne_univers());
            op_rech_form_rep.execute();
            DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
            Row current_rep_rech = iter_rep_rech.getCurrentRow();
            if(current_rep_rech == null){
                OperationBinding op_get_pers = bindings.getOperationBinding("getRepartitionCurrent");
                op_get_pers.getParamsMap().put("id_rep",  Long.parseLong(current_rep.getAttribute("IdRepartition").toString()));
                op_get_pers.execute();
                RichPopup popup = this.getPopDel();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else{
                //la rep est deja attachée à la formaion
                RichPopup popup = this.getPopImpDelRep();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            //Aucune repartition à supprimer pour la formaion
            RichPopup popup = this.getPopDelRepVide();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
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

    @SuppressWarnings("unchecked")
    public void onDialogNew(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            System.out.println("ok");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopNew().hide();
            OperationBinding op_table_rep = getBindings().getOperationBinding("refreshTableRep");
            op_table_rep.getParamsMap().put("id_struct",  id_strc.getInputValue());
            op_table_rep.getParamsMap().put("id_annee",  getAnne_univers());
            op_table_rep.execute();
        }
    }

    public void onDialogCanNew(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopNew().hide();
    }

    public void setTableCollectRep(RichPanelCollection tableCollectRep) {
        this.tableCollectRep = tableCollectRep;
    }

    public RichPanelCollection getTableCollectRep() {
        return tableCollectRep;
    }

    public void setPopEdit(RichPopup popEdit) {
        this.popEdit = popEdit;
    }

    public RichPopup getPopEdit() {
        return popEdit;
    }

    public void onDialogEdit(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void onDialogCanEdit(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopEdit().hide();
    }

    public void setPopDel(RichPopup popDel) {
        this.popDel = popDel;
    }

    public RichPopup getPopDel() {
        return popDel;
    }

    @SuppressWarnings("unchecked")
    public void onDialogDel(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationDelete = bindings.getOperationBinding("DeleteRep");
            Object result = operationDelete.execute();
            if (!operationDelete.getErrors().isEmpty()) {
                return;
            }
            else{
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                              
                OperationBinding op_table_rep = getBindings().getOperationBinding("refreshTableRep");
                op_table_rep.getParamsMap().put("id_struct",  id_strc.getInputValue());
                op_table_rep.getParamsMap().put("id_annee",  getAnne_univers());
                op_table_rep.execute();
                
                return ;
            }
        }
    }

    public void onDialogCanDel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDel().hide();
    }

    public void setChek_all(RichSelectBooleanCheckbox chek_all) {
        this.chek_all = chek_all;
    }

    public RichSelectBooleanCheckbox getChek_all() {
        return chek_all;
    }

    public void onSelectAll(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(Boolean.parseBoolean(chek_all.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("TypeCompteROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("cocher", Boolean.TRUE);
                System.out.println(nextRow.getAttribute("cocher"));
                System.out.println(chek_all.getValue().toString());
            }
        }
        else{
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("TypeCompteROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("cocher", Boolean.FALSE);System.out.println("decocher "+nextRow.getAttribute("cocher"));
            }
        }
    }

    public void popNewCmp(ActionEvent actionEvent) {
        // Add event code here...
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

    @SuppressWarnings("unchecked")
    public void onTypeCompteEdit(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_type = (DCIteratorBinding) bindings.get("TypeCompteROIterator");
        Row current_type = iter_type.getCurrentRow();
        if(current_type != null){
            OperationBinding op_get_pers = bindings.getOperationBinding("getTypeCmptCurrent");
            op_get_pers.getParamsMap().put("id_type_cmpt",  Long.parseLong(current_type.getAttribute("IdTypeCompte").toString()));
            op_get_pers.execute();
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

    @SuppressWarnings("unchecked")
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

    public void onDialogTypeNew(DialogEvent dialogEvent) {
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

    public void onDialogTypeCanNew(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopNewType().hide();
    }

    public void setPopEditType(RichPopup popEditType) {
        this.popEditType = popEditType;
    }

    public RichPopup getPopEditType() {
        return popEditType;
    }

    public void onDialogEditType(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void onDialogTypeCanEdit(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopEditType().hide();
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
    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("cocher").toString())){
                    i++;
                }
            }
        }
        return i;
    }
    public Integer existeCompteForm(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 6 || Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 7 || Integer.parseInt( nextRow.getAttribute("IdTypeCompte").toString()) == 8){ 
                i++;
            }
        }
        return i;
    }
    public Double totalPourcentageCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        Double somme = 0.00;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("cocher").toString())){
                    if(nextRow.getAttribute("pourcentage") != null){
                       somme = somme +  Double.parseDouble(nextRow.getAttribute("pourcentage").toString());
                    }
                }
            }
        }
        return somme;
    }

    @SuppressWarnings("unchecked")
    public String onValiderCleRepartition() {
        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        AttributeBinding lib_rep = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        DCIteratorBinding iter_rep = (DCIteratorBinding) getBindings().get("RepartitionROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            System.out.println("id_rep id_rep "+id_rep);
            if(nombreCaseCoche("TypeCompteROIterator") > 0){
                if(totalPourcentageCaseCoche("TypeCompteROIterator") == 100){
                    Integer somme = 0;
                    OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
                    op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
                    op_rech_form_rep.getParamsMap().put("id_form",  id_form);
                    op_rech_form_rep.getParamsMap().put("id_annee",  getAnne_univers());
                    op_rech_form_rep.execute();
                    DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
                    Row current_rep_rech = iter_rep_rech.getCurrentRow();
                    
                    DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("TypeCompteROIterator");       
                    RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                    if(current_rep_rech == null){
                        while (rsi.hasNext()) {
                            Row nextRow = rsi.next();
                            if(nextRow.getAttribute("cocher")!=null){
                                if(Boolean.parseBoolean(nextRow.getAttribute("cocher").toString()) && nextRow.getAttribute("pourcentage") != null){
                                    somme = somme + insertCleRep(id_rep, nextRow.getAttribute("pourcentage").toString(), nextRow.getAttribute("IdTypeCompte").toString(), getUtilisateur());
                                    inscCompte(nextRow.getAttribute("IdTypeCompte").toString(), nextRow.getAttribute("LibelleType").toString());
                                    //inscCompteGlobal("Formation Globale");
                                }
                            }
                        }
                        if(somme > 0 && insertFormRep(id_rep, id_form.getInputValue().toString(), getAnne_univers(), getUtilisateur()) == 1){
                            repartition_configurer();
                            sessionScope.remove("editer");
                            inscCompteGlobal("Formation Globale");
                            FacesMessage msg = new FacesMessage(somme+" Type (s) Compte(s) crée(s) pour la répartition :");
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(lib_rep.getInputValue()+""));
                            
                        }
                    }
                    else{
                        OperationBinding op_table_rep = getBindings().getOperationBinding("deleteClesRep");
                        op_table_rep.getParamsMap().put("id_rep",  current_rep_rech.getAttribute("IdRepartition"));
                        op_table_rep.execute();
                        while (rsi.hasNext()) {
                            Row nextRow = rsi.next();
                            if(nextRow.getAttribute("cocher")!=null){
                                if(Boolean.parseBoolean(nextRow.getAttribute("cocher").toString()) && nextRow.getAttribute("pourcentage") != null){    
                                    somme = somme + insertCleRep(id_rep, nextRow.getAttribute("pourcentage").toString(), nextRow.getAttribute("IdTypeCompte").toString(), getUtilisateur());                                       
                                }
                            }
                        }
                        if(somme > 0 ){
                            sessionScope.remove("editer");
                            FacesMessage msg = new FacesMessage(somme+" Type (s) Compte(s) crée(s) pour la répartition :");
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(lib_rep.getInputValue()+""));                    
                        }
                    }
                }
                else{
                    FacesMessage msg = new FacesMessage(" La somme totale des pourcentages doit etre égale à 100");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }                
            }
            else{
                FacesMessage msg = new FacesMessage(" Veuillez sélectionner au moins un type compte ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        else{
            FacesMessage msg = new FacesMessage(" Veuillez ajouter une répartition ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }
    public Integer insertCleRep(String id_repart,String pourc,String type_cmpt,String util ){
        
        AttributeBinding id_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartitionCleRep");
        AttributeBinding pourcentage = (AttributeBinding) getBindings().getControlBinding("PourcentageCleRep");
        AttributeBinding id_type_cmpt = (AttributeBinding) getBindings().getControlBinding("IdTypeCompteCleRep");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCleRep");
        
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCleRep");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return i;
        }
        else{
            id_rep.setInputValue(id_repart);
            id_type_cmpt.setInputValue(type_cmpt);
            pourcentage.setInputValue(pourc);
            id_util.setInputValue(util);
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return i;
            }
            else{
                i++;
            }
        }
        return i;
    }
    
    public Integer insertFormRep(String id_repart,String id_form,String id_annee,String util ){
        
        AttributeBinding id_rep_form_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartitionFormRep");
        AttributeBinding id_form_form_rep = (AttributeBinding) getBindings().getControlBinding("IdFormationFormRep");
        AttributeBinding id_annee_form_rep = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversFormRep");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeFormRep");
        
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertFormRep");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return i;
        }
        else{
            id_rep_form_rep.setInputValue(id_repart);
            id_form_form_rep.setInputValue(id_form);
            id_annee_form_rep.setInputValue(id_annee);
            id_util.setInputValue(util);
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return i;
            }
            else{
                i++;
            }
        }
        return i;
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
    @SuppressWarnings("unchecked")
    public void repartition_configurer(){
        
        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");     
        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        //getRechFormRep
        AttributeBinding lib_rep = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        DCIteratorBinding iter_rep = (DCIteratorBinding) getBindings().get("RepartitionROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            
            OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
            op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
            op_rech_form_rep.getParamsMap().put("id_form",  id_form);
            op_rech_form_rep.getParamsMap().put("id_annee",  current_rep.getAttribute("IdAnneesUnivers"));
            op_rech_form_rep.execute();
            DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
            Row current_rep_rech = iter_rep_rech.getCurrentRow();
            if(current_rep_rech != null){
                sessionScope.put("var_form_rep_existe", 1);
                OperationBinding op_rech_cle_rep = getBindings().getOperationBinding("getRepCleRep");
                op_rech_cle_rep.getParamsMap().put("id_rep",  id_rep);
                op_rech_cle_rep.execute();
                DCIteratorBinding iter_cle_rech = (DCIteratorBinding) getBindings().get("CleRepartitionRechROIterator");
                Row current_cle_rech = iter_cle_rech.getCurrentRow();
                if(current_cle_rech != null){
                    DCIteratorBinding iter_insc2 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("TypeCompteROIterator");
                    RowSetIterator rsi_insc2 = iter_insc2.getViewObject().createRowSetIterator(null);

                    while (rsi_insc2.hasNext()) {
                        Row rw = rsi_insc2.next();
                        DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CleRepartitionRechROIterator");
                        RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
                        while (rsi_insc.hasNext()) {
                            Row nextRow = rsi_insc.next();
                            if(Long.parseLong(rw.getAttribute("IdTypeCompte").toString()) == Long.parseLong(nextRow.getAttribute("IdTypeCompte").toString()) ){
                                rw.setAttribute("cocher", Boolean.TRUE);
                                rw.setAttribute("pourcentage",nextRow.getAttribute("Pourcentage"));
                            }
                            /*else{
                                rw.setAttribute("cocher", Boolean.FALSE);
                                rw.setAttribute("pourcentage",nextRow.getAttribute("Pourcentage"));
                            }*/
                        }
                    }
                }
            }

        }
        else{
        }
    }

    @SuppressWarnings("unchecked")
    public void repartition_configurer_editer(){
        
        
        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");     
        ViewObject vo_liste_prov_etud = am.getTypeCompteRO();
        //vo_liste_prov_etud.reset();

        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        //getRechFormRep
        AttributeBinding lib_rep = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        DCIteratorBinding iter_rep = (DCIteratorBinding) getBindings().get("RepartitionROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            
            OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
            op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
            op_rech_form_rep.getParamsMap().put("id_form",  id_form);
            op_rech_form_rep.getParamsMap().put("id_annee",  current_rep.getAttribute("IdAnneesUnivers"));
            op_rech_form_rep.execute();
            DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
            Row current_rep_rech = iter_rep_rech.getCurrentRow();
            if(current_rep_rech != null){
                sessionScope.put("var_form_rep_existe", 0);
                OperationBinding op_rech_cle_rep = getBindings().getOperationBinding("getRepCleRep");
                op_rech_cle_rep.getParamsMap().put("id_rep",  id_rep);
                op_rech_cle_rep.execute();
                DCIteratorBinding iter_cle_rech = (DCIteratorBinding) getBindings().get("CleRepartitionRechROIterator");
                Row current_cle_rech = iter_cle_rech.getCurrentRow();
                if(current_cle_rech != null){
                    DCIteratorBinding iter_insc2 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("TypeCompteROIterator");
                    RowSetIterator rsi_insc2 = iter_insc2.getViewObject().createRowSetIterator(null);

                    while (rsi_insc2.hasNext()) {
                        Row rw = rsi_insc2.next();
                        DCIteratorBinding iter_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CleRepartitionRechROIterator");
                        RowSetIterator rsi_insc = iter_insc.getViewObject().createRowSetIterator(null);
                        while (rsi_insc.hasNext()) {
                            Row nextRow = rsi_insc.next();
                            if(Long.parseLong(rw.getAttribute("IdTypeCompte").toString()) == Long.parseLong(nextRow.getAttribute("IdTypeCompte").toString()) ){
                                rw.setAttribute("cocher", Boolean.TRUE);
                                rw.setAttribute("pourcentage",nextRow.getAttribute("Pourcentage"));
                            }
                        }
                    }
                }
            }
        }
        else{
        }
    }
    public void setInput_ref(RichInputText input_ref) {
        this.input_ref = input_ref;
    }

    public RichInputText getInput_ref() {
        return input_ref;
    }

    public void setPopErrNew(RichPopup popErrNew) {
        this.popErrNew = popErrNew;
    }

    public RichPopup getPopErrNew() {
        return popErrNew;
    }

    public void setPopImpDelRep(RichPopup popImpDelRep) {
        this.popImpDelRep = popImpDelRep;
    }

    public RichPopup getPopImpDelRep() {
        return popImpDelRep;
    }

    public void setPopDelRepVide(RichPopup popDelRepVide) {
        this.popDelRepVide = popDelRepVide;
    }

    public RichPopup getPopDelRepVide() {
        return popDelRepVide;
    }

    public String onEditerClesRep() {
        // Add event code here...
        System.out.println("onEditerClesRep");
        sessionScope.put("editer", 1);
        return null;
    }

    public void setPopErrEdit(RichPopup popErrEdit) {
        this.popErrEdit = popErrEdit;
    }

    public RichPopup getPopErrEdit() {
        return popErrEdit;
    }

    public void setPopEditTypErr(RichPopup popEditTypErr) {
        this.popEditTypErr = popEditTypErr;
    }

    public RichPopup getPopEditTypErr() {
        return popEditTypErr;
    }

    public void setPopDelTypErr(RichPopup popDelTypErr) {
        this.popDelTypErr = popDelTypErr;
    }

    public RichPopup getPopDelTypErr() {
        return popDelTypErr;
    }


    @SuppressWarnings("unchecked")
    public void inscCompte(String type_cmpt, String lib_compte){
        
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");   
        //IdHistoriquesStructureForm
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureForm"); //LibNivForm
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
            
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiantCmpte");
        AttributeBinding id_fournisseur = (AttributeBinding) getBindings().getControlBinding("IdFournisseurCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        OperationBinding op_annee_passee = getBindings().getOperationBinding("getAnneeUniversPassee");
        op_annee_passee.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_passee.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversPasseeROIterator");
        Row row_set_annee_passee = iter_annee_passee.getCurrentRow();
    
        System.out.println("row_set_annee_passee "+row_set_annee_passee+" lib annee "+row_set_annee_passee.getAttribute("IdLibLongPasse")+"id_annee" +row_set_annee_passee.getAttribute("IdAnneePasse"));
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeAncien");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  row_set_annee_passee.getAttribute("IdAnneePasse"));
        op_soldeAncien.getParamsMap().put("id_type_cmpt",  type_cmpt);
        op_soldeAncien.getParamsMap().put("id_form",  id_form.getInputValue());
        op_soldeAncien.execute();
    
        Integer i = 0;
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return ;
        }
        else{       
            
            if(Integer.parseInt(type_cmpt) == 2){
                //type compte Etablissement Rectorat
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue("Rectorat");
            }
            else if(Integer.parseInt(type_cmpt) == 4){
                // type compte Etablissement
                //LibEtab
                AttributeBinding lib_etab = (AttributeBinding) getBindings().getControlBinding("LibEtab");
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue(lib_etab.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 5){
                //Département
                AttributeBinding lib_dept = (AttributeBinding) getBindings().getControlBinding("LibDept");
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                proprietaire.setInputValue(lib_dept.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 6 || Integer.parseInt(type_cmpt) == 7 || Integer.parseInt(type_cmpt) == 8 || Integer.parseInt(type_cmpt) == 21 ){
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                id_form_.setInputValue(id_form.getInputValue());
                proprietaire.setInputValue(lib_niv_form.getInputValue());
            }
            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue(type_cmpt);//id_type_cmpte.
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return ;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return ;
                }
                else{                
                    DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeAnneeAncROIterator");
                    Row row_solde_anc = iter_solde_anc.getCurrentRow();
                    
                    if(row_solde_anc == null){
                        solde_int.setInputValue(0);
                        solde_act.setInputValue(0);
                    }
                    else{
                        id_annee_anc_type.setInputValue(row_set_annee_passee.getAttribute("IdAnneePasse"));
                        solde_int.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                        solde_act.setInputValue(row_solde_anc.getAttribute("SoldeActuel"));
                    }
                    
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return ;
                    }
                    else{
                    }
                }
            }
        }
    }
    public String form_num_compte(String num_compte){
        String [] tab_cmpte = num_compte.split(" ");
        String num_ret = null;
        if(tab_cmpte.length > 1){
            num_ret = tab_cmpte[0].substring(0, 4);
            for(int i=0; i<tab_cmpte.length ; i++){
                if(i>0){
                    String ss = tab_cmpte[i];
                    if(ss.length() > 4 ){
                        num_ret = num_ret+"_"+ ss.substring(0, 4);
                    }
                    else
                        num_ret = num_ret+"_"+ss;
                }
            }
        }
        else{
            String ss = tab_cmpte[0];
            if(ss.length() > 4 )
                num_ret = ss.substring(0, 4);
            else
                num_ret = ss;
                 
        }
        return num_ret;
    }
    @SuppressWarnings("unchecked")
    public void inscCompteGlobal(String lib_compte){
        
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");   
        //IdHistoriquesStructureForm
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureForm"); //LibNivForm
        AttributeBinding lib_niv_form = (AttributeBinding) getBindings().getControlBinding("LibNivForm");
            
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiantCmpte");
        AttributeBinding id_fournisseur = (AttributeBinding) getBindings().getControlBinding("IdFournisseurCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
        Integer somme_solde_ini = 0;
        Integer somme_solde_act = 0;
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeCompteGlob");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  getAnne_univers());
        //op_soldeAncien.getParamsMap().put("id_type_cmpt",  type_cmpt);
        op_soldeAncien.getParamsMap().put("id_form",  id_form.getInputValue());
        op_soldeAncien.execute();
                
        DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeCompteGlobROIterator");
        Row row_solde = iter_solde_anc.getCurrentRow();
        RowSetIterator rsi = iter_solde_anc.getViewObject().createRowSetIterator(null);
    
        Integer i = 0;
        if(existeCompteForm("SoldeCompteGlobROIterator") > 0){
        OperationBinding op_insert_cle_rep = getBindings().getOperationBinding("CreateInsertCompte");
        Object result_cle_rep = op_insert_cle_rep.execute();
                                
        if (!op_insert_cle_rep.getErrors().isEmpty()) {
                return ;
        }
        else{       
            
            id_strc_att.setInputValue(id_strc.getInputValue());
            id_dpt.setInputValue(id_hist.getInputValue());
            id_form_.setInputValue(id_form.getInputValue());
            proprietaire.setInputValue(lib_niv_form.getInputValue());
            //next_seq
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq");
            Integer res_seq = (Integer)op_seq.execute();
            System.out.println("res_seq res_seq "+res_seq);
            id_type_cmpte.setInputValue("21");//compte global
            num_cmpte.setInputValue(form_num_compte(lib_compte)+"_"+(res_seq+1));
            id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            
            OperationBinding op_commit_cle_rep = getBindings().getOperationBinding("Commit");
            Object result = op_commit_cle_rep.execute();
            if (!op_commit_cle_rep.getErrors().isEmpty()) {
                    return ;
            }
            else{
                AttributeBinding id_cmpte = (AttributeBinding) getBindings().getControlBinding("IdCompte");
    
                AttributeBinding id_cmpte_solde = (AttributeBinding) getBindings().getControlBinding("IdCompteTypeCmpt");
                AttributeBinding solde_int = (AttributeBinding) getBindings().getControlBinding("SoldeInitial");
                AttributeBinding solde_act = (AttributeBinding) getBindings().getControlBinding("SoldeActuel");
                AttributeBinding id_annee_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversTypeCmpt");
                AttributeBinding id_annee_anc_type = (AttributeBinding) getBindings().getControlBinding("IdAnneesDernTypeCmpt");
                AttributeBinding id_util_type = (AttributeBinding) getBindings().getControlBinding("UtiCreeTypeCmpt");
    
                OperationBinding op_insert_sold_anc = getBindings().getOperationBinding("CreateInsertSoldeCmpte");
                Object result_sold_anc = op_insert_sold_anc.execute();
                                        
                if (!op_insert_cle_rep.getErrors().isEmpty()) {
                        return ;
                }
                else{                
                     while (rsi.hasNext()) {
                            Row row_solde_anc = rsi.next();
                            if(Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 6 || Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 7 || Integer.parseInt( row_solde_anc.getAttribute("IdTypeCompte").toString()) == 8){ 
                                somme_solde_ini = somme_solde_ini + Integer.parseInt(row_solde_anc.getAttribute("SoldeInitial").toString());
                                somme_solde_act = somme_solde_act + Integer.parseInt(row_solde_anc.getAttribute("SoldeActuel").toString());
                            }
                            
                    }

                    id_annee_anc_type.setInputValue(row_solde.getAttribute("IdAnneesDern"));
                    solde_int.setInputValue(somme_solde_ini);
                    solde_act.setInputValue(somme_solde_act);
                    
                    id_cmpte_solde.setInputValue(id_cmpte.getInputValue());
                    id_annee_type.setInputValue(getAnne_univers());
                    id_util_type.setInputValue(getUtilisateur());
                    
                    OperationBinding op_commit_sold = getBindings().getOperationBinding("Commit");
                    Object result_commit_sold = op_commit_sold.execute();
                    if (!op_commit_sold.getErrors().isEmpty()) {
                            return ;
                    }
                    else{
                    }
                }
            }
        }
    }
}

}
