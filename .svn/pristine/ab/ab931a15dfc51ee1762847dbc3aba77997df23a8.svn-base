package repartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import model.services.ecolageAppImpl;

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
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

public class RepartitionBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPanelCollection tableCollectRep;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private RichPopup popNew;
    private RichPopup popEdit;
    private RichPopup popDel;
    private RichPopup popErrEdit;
    private RichPopup popImpDelRep;
    private RichPopup popDelRepVide;
    private RichPopup popErrSomPour;
    private RichPopup popCtrlPourVide;
    private RichPopup popErrSomPourDiff;
    private RichPopup popCommitCleRep;
    //original list item queried from the ADF binding layer
    List<SelectItem> selectItemList = null;
    //select list with enabled/disabled item state rendered on view
    ArrayList<SelectItem> newSelectItemList = null;
    private RichPopup popOccType;

    public RepartitionBean() {
    }

    public void onValiderRepartition(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartition");
        OperationBinding op_table_rep = getBindings().getOperationBinding("getSommePourcentage");
        op_table_rep.getParamsMap().put("id_rep",  id_rep.getInputValue());
        op_table_rep.execute();
        
        DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepAllPourcentageROIterator");       
        Row row_cle = iter_cle.getCurrentRow();
        
        if(row_cle == null){       
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
            Integer i = 0;
            Double somme = 0.00;

            if(typeControlVide("CleRepartition1Iterator") > 0){
                RichPopup popup = this.getPopCtrlPourVide();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else if(totalPourcentageCaseCoche("CleRepartition1Iterator") != 100){
                RichPopup popup = this.getPopErrSomPourDiff();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else if(lesOccurence("CleRepartition1Iterator") > 1){
                RichPopup popup = this.getPopOccType();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else{
                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    return;
                }
                RichPopup popup = this.getPopCommitCleRep();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            
        }
        else{
            //modication
            DCIteratorBinding iter_rep = (DCIteratorBinding) getBindings().get("RepartitionStructROIterator");
            Row current_rep = iter_rep.getCurrentRow();
            if(current_rep != null){
                //getCleCurrente
                OperationBinding op_deleteClesRep = getBindings().getOperationBinding("getCleCurrente");
                op_deleteClesRep.getParamsMap().put("id_rep",  current_rep.getAttribute("IdRepartition"));
                op_deleteClesRep.execute();
            }
            else{
            }
        }
    }
 
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
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
        //AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversRep");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeRep");
        if(oldCcurrentRow != null)//{
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertRep");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return ;
            }
            id_strc_rep.setInputValue(id_strc.getInputValue());
            //id_annee.setInputValue(getAnne_univers());
            id_util.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopNew();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        //}
        /*else{
            RichPopup popup = this.getPopErrNew();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }*/
    }

    @SuppressWarnings("unchecked")
    public void onEditerRepartition(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_rep = (DCIteratorBinding) bindings.get("RepartitionStructROIterator");
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
        //AttributeBinding id_form = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_rep = (DCIteratorBinding) bindings.get("RepartitionStructROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            
            OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
            op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
            //op_rech_form_rep.getParamsMap().put("id_annee",  getAnne_univers());
            op_rech_form_rep.execute();
            DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
            Row current_rep_rech = iter_rep_rech.getCurrentRow();
            if(current_rep_rech == null){
                OperationBinding op_get_rep = bindings.getOperationBinding("getRepartitionCurrent");
                op_get_rep.getParamsMap().put("id_rep",  Long.parseLong(current_rep.getAttribute("IdRepartition").toString()));
                op_get_rep.execute();
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
            op_table_rep.execute();
        }
    }

    public void onDialogCanNew(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopNew().hide();
    }

    public void setPopEdit(RichPopup popEdit) {
        this.popEdit = popEdit;
    }

    public RichPopup getPopEdit() {
        return popEdit;
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
                DCIteratorBinding iter_rep = (DCIteratorBinding) bindings.get("RepartitionStructROIterator");
                Row current_rep = iter_rep.getCurrentRow();
                
                OperationBinding op_deleteClesRep = getBindings().getOperationBinding("deleteClesRep");
                op_deleteClesRep.getParamsMap().put("id_rep",  current_rep.getAttribute("IdRepartition"));
                op_deleteClesRep.execute();
                
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object commitResult = operationCommit.execute();
                              
                OperationBinding op_table_rep = getBindings().getOperationBinding("refreshTableRep");
                op_table_rep.getParamsMap().put("id_struct",  id_strc.getInputValue());
                op_table_rep.execute();
                
                return ;
            }
        }
    }

    public void onDialogCanDel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDel().hide();
    }

    public void setPopErrEdit(RichPopup popErrEdit) {
        this.popErrEdit = popErrEdit;
    }

    public RichPopup getPopErrEdit() {
        return popErrEdit;
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

    public Double totalPourcentageCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        Double somme = 0.00;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();

            if(nextRow.getAttribute("Pourcentage") != null){
               somme = somme +  Double.parseDouble(nextRow.getAttribute("Pourcentage").toString());
            }

        }
        return somme;
    }
    public Integer nombreOccurence(String bind_interator, Integer id_type){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();

            if(nextRow.getAttribute("IdTypeCompte") != null){
               if(Integer.parseInt(nextRow.getAttribute("IdTypeCompte").toString()) == id_type){
                   i ++;
               }
            }
        

        }
        return i;
    }
    public Integer lesOccurence(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer nbr_occ = 1;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nombreOccurence("CleRepartition1Iterator", Integer.parseInt(nextRow.getAttribute("IdTypeCompte").toString()))>1)
                nbr_occ = nombreOccurence("CleRepartition1Iterator", Integer.parseInt(nextRow.getAttribute("IdTypeCompte").toString()));
        }
        return nbr_occ;
    }
    public Integer typeControlVide(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();

            if((nextRow.getAttribute("IdTypeCompte") == null && nextRow.getAttribute("Pourcentage") == null) 
               || (nextRow.getAttribute("IdTypeCompte") == null && nextRow.getAttribute("Pourcentage") != null)
                || (nextRow.getAttribute("IdTypeCompte") != null && nextRow.getAttribute("Pourcentage") == null)){
               i++;
            }

        }
        return i;
    }
    public void onNewCleRep(ActionEvent actionEvent) {
        // Add event code here...
        //CreateInsertCleRep
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CleRepartition1Iterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        //getSommePourcentage
        //CleRepAllPourcentageROIterator
        AttributeBinding id_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartition");
        AttributeBinding id_rep_cle = (AttributeBinding) getBindings().getControlBinding("IdRepartitionCleRep");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCleRep");
        /*if(oldCcurrentRow != null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));*/
        OperationBinding op_table_rep = getBindings().getOperationBinding("getSommePourcentage");
        op_table_rep.getParamsMap().put("id_rep",  id_rep.getInputValue());
        op_table_rep.execute();  //CleRepAllPourcentageROIterator
        if(totalPourcentageCaseCoche("CleRepartition1Iterator") == 100){
            RichPopup popup = this.getPopErrSomPour();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertCleRep");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return ;
            }
            id_rep_cle.setInputValue(id_rep.getInputValue());
            id_util.setInputValue(getUtilisateur());
            getSelectItemList();
            /*RichPopup popup = this.getPopNew();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);*/
        }
    }

    public void setPopErrSomPour(RichPopup popErrSomPour) {
        this.popErrSomPour = popErrSomPour;
    }

    public RichPopup getPopErrSomPour() {
        return popErrSomPour;
    }

    public void setPopCtrlPourVide(RichPopup popCtrlPourVide) {
        this.popCtrlPourVide = popCtrlPourVide;
    }

    public RichPopup getPopCtrlPourVide() {
        return popCtrlPourVide;
    }

    public void setPopErrSomPourDiff(RichPopup popErrSomPourDiff) {
        this.popErrSomPourDiff = popErrSomPourDiff;
    }

    public RichPopup getPopErrSomPourDiff() {
        return popErrSomPourDiff;
    }

    public void setPopCommitCleRep(RichPopup popCommitCleRep) {
        this.popCommitCleRep = popCommitCleRep;
    }

    public RichPopup getPopCommitCleRep() {
        return popCommitCleRep;
    }

    public void onSelectTypeCtrl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
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
    /*@SuppressWarnings("unchecked")
    public void repartition_configurer(){
        
        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");*/
    public void cle_repartition_choisie(){
        // Add event code here...
        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");
        ViewObject vo_liste_prov_etud = am.getTypeCmpteRepRO();
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("TypeCmpteRepROIterator");       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Row row_type = iter.getCurrentRow();
        
        AttributeBinding id_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartition");
        OperationBinding op_table_rep = getBindings().getOperationBinding("getSommePourcentage");
        op_table_rep.getParamsMap().put("id_rep",  id_rep.getInputValue());
        op_table_rep.execute();
        
        DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
        Row row_cle = iter_cle.getCurrentRow();
        RowSetIterator rsi1 = iter_cle.getViewObject().createRowSetIterator(null);
        
        /*DCIteratorBinding iter1 = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
        RowSetIterator rsi1 = iter1.getViewObject().createRowSetIterator(null);*/
        //Row row_type = iter1.getCurrentRow();
        /*if(row_type != null)
                while (rsi.hasNext()) {
                    Row nextRow = rsi.next();
                    System.out.println("type  "+nextRow.getAttribute("IdTypeCompte"));
                    //System.out.println("type  "+nextRow.getAttribute("IdTypeCompte"));
                }
        if(row_cle != null)
                while (rsi1.hasNext()) {
                    Row nextRow = rsi1.next();
                    System.out.println("type cle  "+nextRow.getAttribute("IdTypeCompte"));
                    //System.out.println("type  "+nextRow.getAttribute("IdTypeCompte"));
                }*/
        if(row_type != null)
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            while (rsi1.hasNext()) {
                Row nextRow1 = rsi1.next();
                SelectItem s = new SelectItem();
                //s.setLabel(nextRow1.getAttribute("LibelleLong").toString());
                s.setValue(nextRow1.getAttribute("IdTypeCompte"));
                System.out.println("getLabel "+s.getLabel());
                System.out.println("getValue "+s.getValue());
                System.out.println("type  "+nextRow.getAttribute("IdTypeCompte"));
                System.out.println("type 1  "+nextRow1.getAttribute("IdTypeCompte"));
                if(nextRow1.getAttribute("IdTypeCompte")!=null)
                if(Integer.parseInt(nextRow.getAttribute("IdTypeCompte").toString())  ==  Integer.parseInt(nextRow1.getAttribute("IdTypeCompte").toString()))
                {
                    s.setDisabled(true);
                    //nextRow.remove();
                    
                }
                else{
                    s.setDisabled(false);
                }
            }
        }
    }
    public void setSelectItemList(List<SelectItem> selectItemList) {}

    /**
     * Queries the list data from the ADF bindin layer and returns the list 
     * with disabled set to tru or false dependent on whether displayed item
     * is in location or not
     * @return List<SelectItem>
     */
    public List<SelectItem> getSelectItemList() {        
        //Create Value Expression to access the ADF list for he departments 
        //select many checkbox
        DCIteratorBinding iter_clee = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
                    //Row row_cle = iter_cle.getCurrentRow();
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprfact = fctx.getApplication().getExpressionFactory();
        ValueExpression vexpr = exprfact.createValueExpression(
                                          elctx, 
                                          "#{bindings.LibelleLong1.items}",
                                          Object.class);
        selectItemList = (List<SelectItem>) vexpr.getValue(elctx);
        JUCtrlListBinding deptListBinding = (JUCtrlListBinding) getBindings().get("LibelleLong1");
        System.out.println("deptListBinding "+deptListBinding);
        newSelectItemList = new ArrayList<SelectItem>();

        for(SelectItem li : selectItemList){
            //the departments list queried from the ADF binding layer only
            //returns a list of indx that we need to resolve to determine
            //the department select items LocationId
            Integer listIndex = (Integer) li.getValue();
            Row deptListRow = deptListBinding.getRowAtRangeIndex(listIndex.intValue());
            System.out.println("listIndex "+listIndex);
            System.out.println("deptListRow "+deptListRow.getAttribute("IdTypeCompte"));
            //System.out.println("deptListRow LibelleLong "+deptListRow.getAttribute("LibelleLong"));
            //create a new select item to add to the modified list of
            //departments
            //selectItems.add(new SelectItem(1, "Form 1", null, false));
            //SelectItem s = new SelectItem(Integer.parseInt( deptListRow.getAttribute("IdTypeCompte").toString()), li.getLabel(), null, false);
            SelectItem s = new SelectItem();
            s.setLabel(li.getLabel());
            s.setValue(li.getValue());
            System.out.println("getLabel "+li.getLabel());
            System.out.println("getValue "+li.getValue());
            //determine if the item should be enabled or disabled
            //if(((Number) deptListRow.getAttribute("IdTypeCompte")).equals((Number)locationId)) {
            
            /*DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
            Row row_cle = iter_cle.getCurrentRow();
            RowSetIterator rsi1 = iter_cle.getViewObject().createRowSetIterator(null);
            if(row_cle!=null)
            while (rsi1.hasNext()) {
                Row nextRow1 = rsi1.next();
                System.out.println("getValue IdTypeCompte "+nextRow1.getAttribute("IdTypeCompte"));*/
                /*if(Integer.parseInt(deptListRow.getAttribute("IdTypeCompte").toString())  ==  Integer.parseInt(nextRow1.getAttribute("IdTypeCompte").toString()))
                {s.setDisabled(true);
                }
                else{
                    s.setDisabled(false);
                }*/
            //}
            DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
                        Row row_cle = iter_cle.getCurrentRow();
                        RowSetIterator rsi1 = iter_cle.getViewObject().createRowSetIterator(null);
                        if(row_cle!=null )
                        while (rsi1.hasNext()) {
                            Row nextRow1 = rsi1.next();
                            System.out.println("getValue IdTypeCompte "+nextRow1.getAttribute("IdTypeCompte"));
                            if(nextRow1.getAttribute("IdTypeCompte")!=null )
                            if(Integer.parseInt(deptListRow.getAttribute("IdTypeCompte").toString())  ==  valeurRetour(Integer.parseInt(nextRow1.getAttribute("IdTypeCompte").toString())))
                                            { s.setDisabled(true);
                                                //newSelectItemList.add(new SelectItem(deptListRow.getAttribute("IdTypeCompte").toString(), "Form 1", null, false));
                                            }
                                            else if(nextRow1.getAttribute("IdTypeCompte") == null){
                                                s.setDisabled(true);
                                            }
                            else{
                                                s.setDisabled(false);
                                            }
                            
                        } 
            /*if(Integer.parseInt(deptListRow.getAttribute("IdTypeCompte").toString()) == 8 || Integer.parseInt(deptListRow.getAttribute("IdTypeCompte").toString()) == 4) {                    
                s.setDisabled(true);
            }
            else{
                s.setDisabled(false);
            }*/
            //add item to list
            newSelectItemList.add(s);
        }        
        DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
                    Row row_cle = iter_cle.getCurrentRow();
                    RowSetIterator rsi1 = iter_cle.getViewObject().createRowSetIterator(null);
                    if(row_cle!=null )
                    while (rsi1.hasNext()) {
                        Row nextRow1 = rsi1.next();
                        System.out.println("getValue IdTypeCompte final "+nextRow1.getAttribute("IdTypeCompte"));}
        return newSelectItemList;
    }
    public Integer valeurRetour(Integer o){
        Integer valeur = 0;

        if(o == 2)
            valeur = 4;
        else if(o == 4)
            valeur = 5;
        else if(o == 5)
            valeur = 6;
        else if(o == 6)
            valeur = 7;
        else if(o == 7)
            valeur = 8;
       return valeur;         
    }

    public void setPopOccType(RichPopup popOccType) {
        this.popOccType = popOccType;
    }

    public RichPopup getPopOccType() {
        return popOccType;
    }

    public void onDelCleRep(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter_cle = (DCIteratorBinding) getBindings().get("CleRepartition1Iterator");       
        Row row_cle = iter_cle.getCurrentRow();
        RowSetIterator rsi1 = iter_cle.getViewObject().createRowSetIterator(null);

        DCIteratorBinding iter_rep = (DCIteratorBinding) getBindings().get("RepartitionStructROIterator");
        Row current_rep = iter_rep.getCurrentRow();
        if(current_rep != null){
            String id_rep = current_rep.getAttribute("IdRepartition").toString();
            
            OperationBinding op_rech_form_rep = getBindings().getOperationBinding("getRechFormRep");
            op_rech_form_rep.getParamsMap().put("id_rep",  id_rep);
            //op_rech_form_rep.getParamsMap().put("id_annee",  getAnne_univers());
            op_rech_form_rep.execute();
            DCIteratorBinding iter_rep_rech = (DCIteratorBinding) getBindings().get("FormationRepartitionRechROIterator");
            Row current_rep_rech = iter_rep_rech.getCurrentRow();
            if(current_rep_rech == null){
                iter_cle.removeCurrentRow(); 
            }
            else{
                //getCleRepRecherche
                OperationBinding op_rech_cle_rep = getBindings().getOperationBinding("getCleRepRecherche");
                op_rech_cle_rep.getParamsMap().put("id_rep",  id_rep);
                op_rech_cle_rep.getParamsMap().put("id_type",  row_cle.getAttribute("IdTypeCompte"));
                op_rech_cle_rep.execute();
                DCIteratorBinding iter_rech_cle_rep = (DCIteratorBinding) getBindings().get("CleRepartitionRechROIterator");
                Row current_rech_cle_rep = iter_rech_cle_rep.getCurrentRow();
                if(current_rech_cle_rep == null)
                    iter_cle.removeCurrentRow();
            }
            
        }
        else{
        }
        
    }
}
