package repartition_formation;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.services.ecolageAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;

import oracle.jbo.Row;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class RepFormationBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private RichPopup popCommitForm;
    private String nombre_form;
    private RichPopup popEmptyForm;
    private RichPopup popEmptyStrct;
    private RichPopup popRepExisteCle;

    public RepFormationBean() {
    }

    public void setNombre_form(String nombre_form) {
        this.nombre_form = nombre_form;
    }

    public String getNombre_form() {
        return nombre_form;
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
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
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
    
    @SuppressWarnings("unchecked")
    public void appliquerFormations(ActionEvent actionEvent) {
        // Add event code here...
        
        DCIteratorBinding iter_rep_struct = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepartitionStructROIterator");
        Row row_rep_struct = iter_rep_struct.getCurrentRow();
        
        if(row_rep_struct != null){
            System.out.println("rep currente "+row_rep_struct.getAttribute("IdRepartition"));
            //getListeFormStrc
            AttributeBinding id_rep = (AttributeBinding) getBindings().getControlBinding("IdRepartitionFormRep");
            AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversFormRep");
            AttributeBinding id_form_rep = (AttributeBinding) getBindings().getControlBinding("IdFormationFormRep");
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeFormRep");
            
            OperationBinding op_table_rep = getBindings().getOperationBinding("getListeFormStrc");
            op_table_rep.getParamsMap().put("id_strc",  row_rep_struct.getAttribute("IdStructure"));
            op_table_rep.execute();
            
            DCIteratorBinding iter_les_form = (DCIteratorBinding) getBindings().get("LesFormationsStrctROIterator");       
            Row row_les_form = iter_les_form.getCurrentRow();
            RowSetIterator rsi = iter_les_form.getViewObject().createRowSetIterator(null);
            
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_struct.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
            
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                Integer i = 0;
                if(row_les_form != null){           
                    while (rsi.hasNext()) {
                        Row row_form = rsi.next();
                        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertFormRep");
                        Object result = operationBinding.execute();
                        if (!operationBinding.getErrors().isEmpty()) {
                            return ;
                        }
                        id_rep.setInputValue(row_rep_struct.getAttribute("IdRepartition"));
                        id_form_rep.setInputValue(row_form.getAttribute("IdFormation"));
                        id_annee.setInputValue(getAnne_univers());
                        id_util.setInputValue(getUtilisateur());
                        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding op_commit = bindings.getOperationBinding("Commit");
                        Object result_op_commit = op_commit.execute();
                        if (!op_commit.getErrors().isEmpty()) {
                            //handle errors if any
                            //...
                            return;
                        }
                        i ++;
                    }
                    if(i > 0){
                        setNombre_form(i+"");
                        System.out.println();
                        RichPopup popup = this.getPopCommitForm();                        
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    RichPopup popup = this.getPopEmptyForm();                        
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                RichPopup popup = this.getPopRepExisteCle();                       
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            RichPopup popup = this.getPopEmptyStrct();                        
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
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
    public void refresh(){
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        OperationBinding op_annee_en_cours = getBindings().getOperationBinding("getAnneeEnCours");
        op_annee_en_cours.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_en_cours.execute();
        DCIteratorBinding iter_annee_en_cours = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversEnCoursROIterator");
        Row row_set_annee_en_cours = iter_annee_en_cours.getCurrentRow();
        
        //getRepFormExiste
        OperationBinding op_getRepFormExiste = getBindings().getOperationBinding("getRepFormExiste");
        op_getRepFormExiste.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_getRepFormExiste.getParamsMap().put("id_str",  id_strc.getInputValue());
        //id_str
        op_getRepFormExiste.execute();
        DCIteratorBinding iter_op_getRepFormExiste = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepFormExisteROIterator");
        Row row_op_getRepFormExiste = iter_op_getRepFormExiste.getCurrentRow();
        
        if(row_op_getRepFormExiste != null){
            System.out.println("rep currente "+row_op_getRepFormExiste.getAttribute("IdRepartition"));
            sessionScope.put("rep_form_existe", 1);
            //Primary key value , Specify the data type accordingly
            String id_rep_courrent = row_op_getRepFormExiste.getAttribute("IdRepartition").toString();
            ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");     
            ViewObjectImpl voImp = am.getRepartitionStructRO();
                // 3. Create a Key object
            Key key = new Key(new Object[] {id_rep_courrent});
            System.out.println("rep currente key "+key);
                //4. Get the RowSetIterator Object
            RowSetIterator rsi = voImp.createRowSetIterator(null);
            //RowSetIterator rsi = iter.getRowSetIterator();
            //5. Find the row in the Iterator using findByKey funtion by passing Key object
            Row row = rsi.findByKey(key, 1)[0];
           // 6. Set the Current row in Iterator (findByKey does not Navigate to current row)
            rsi.setCurrentRow(row);
        }
        else{
            sessionScope.put("rep_form_existe", 0);
        }

    }

    public void setPopCommitForm(RichPopup popCommitForm) {
        this.popCommitForm = popCommitForm;
    }

    public RichPopup getPopCommitForm() {
        return popCommitForm;
    }

    public void setPopEmptyForm(RichPopup popEmptyForm) {
        this.popEmptyForm = popEmptyForm;
    }

    public RichPopup getPopEmptyForm() {
        return popEmptyForm;
    }

    public void setPopEmptyStrct(RichPopup popEmptyStrct) {
        this.popEmptyStrct = popEmptyStrct;
    }

    public RichPopup getPopEmptyStrct() {
        return popEmptyStrct;
    }

    @SuppressWarnings("unchecked")
    public void onDialogCompte(DialogEvent dialogEvent) {
        // Add event code here...
        DCIteratorBinding iter_rep_struct = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepartitionStructROIterator");
        Row row_rep_struct = iter_rep_struct.getCurrentRow();
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            if(row_rep_struct != null){
                System.out.println("rep currente "+row_rep_struct.getAttribute("IdRepartition"));
                
                OperationBinding op_table_rep = getBindings().getOperationBinding("getListeFormStrc");
                op_table_rep.getParamsMap().put("id_strc",  row_rep_struct.getAttribute("IdStructure"));
                op_table_rep.execute();
                
                DCIteratorBinding iter_les_form = (DCIteratorBinding) getBindings().get("LesFormationsStrctROIterator");       
                Row row_les_form = iter_les_form.getCurrentRow();
                RowSetIterator rsi = iter_les_form.getViewObject().createRowSetIterator(null);
                
                /*OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
                op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_struct.getAttribute("IdRepartition"));
                op_rep_exist_cle.execute();
                
                DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);*/
                
                Integer i = 0;
                if(row_les_form != null){
                    OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
                    op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_struct.getAttribute("IdRepartition"));
                    op_rep_exist_cle.execute();
                    
                    DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                    RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                    while (rsi_rep_ex_cle.hasNext()) {
                        Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                        if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 2 
                           || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 4 
                           || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 5 ){
                            inscCompteEtbDpt( row_rep_ex_cle.getAttribute("IdTypeCompte").toString(), row_rep_ex_cle.getAttribute("LibelleLong").toString());
                        }
                    } 
                    //Partie pour les comptes formation fonctionnement, investissement et prestataire
                    DCIteratorBinding iter_les_form1 = (DCIteratorBinding) getBindings().get("LesFormationsStrctROIterator");       
                    RowSetIterator rsi1 = iter_les_form1.getViewObject().createRowSetIterator(null);
                    while (rsi1.hasNext()) {
                        Row row_form = rsi1.next();
                        DCIteratorBinding iter_rep_ex_cle1 = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                        RowSetIterator rsi_rep_ex_cle1 = iter_rep_ex_cle1.getViewObject().createRowSetIterator(null);
                        while (rsi_rep_ex_cle1.hasNext()) {
                            Row row_rep_ex_cle = rsi_rep_ex_cle1.next();
                            if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                               || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8 ){
                                inscCompte(row_form.getAttribute("IdFormation").toString(), row_form.getAttribute("LibelleLong").toString(), row_rep_ex_cle.getAttribute("IdTypeCompte").toString(), row_rep_ex_cle.getAttribute("LibelleLong").toString());
                            }
                        }
                        inscCompteGlobal(row_form.getAttribute("IdFormation").toString(), row_form.getAttribute("LibelleLong").toString(),"Formation Globale");
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void inscCompte(String id_form,String lib_form,String type_cmpt, String lib_compte){
        
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");        
        //IdHistoriquesStructureForm
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); 
  
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
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
        op_soldeAncien.getParamsMap().put("id_form",  id_form);
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
                AttributeBinding lib_etab = (AttributeBinding) getBindings().getControlBinding("LibelleLongStruct");
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue(lib_etab.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 5){
                //Département
                AttributeBinding lib_dept = (AttributeBinding) getBindings().getControlBinding("LibHisto");
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                proprietaire.setInputValue(lib_dept.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 6 || Integer.parseInt(type_cmpt) == 7 || Integer.parseInt(type_cmpt) == 8 ){
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                id_form_.setInputValue(id_form);
                proprietaire.setInputValue(lib_form);
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
    
    @SuppressWarnings("unchecked")
    public void inscCompteEtbDpt(String type_cmpt, String lib_compte){
        
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");        
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); 
    
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
    
        OperationBinding op_annee_passee = getBindings().getOperationBinding("getAnneeUniversPassee");
        op_annee_passee.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_passee.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversPasseeROIterator");
        Row row_set_annee_passee = iter_annee_passee.getCurrentRow();
    
        System.out.println("row_set_annee_passee "+row_set_annee_passee+" lib annee "+row_set_annee_passee.getAttribute("IdLibLongPasse")+"id_annee" +row_set_annee_passee.getAttribute("IdAnneePasse"));
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeAnneeAncStrc");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  row_set_annee_passee.getAttribute("IdAnneePasse"));
        op_soldeAncien.getParamsMap().put("id_type_cmpt",  type_cmpt);
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
                AttributeBinding lib_etab = (AttributeBinding) getBindings().getControlBinding("LibelleLongStruct");
                id_strc_att.setInputValue(id_strc.getInputValue());
                proprietaire.setInputValue(lib_etab.getInputValue());
            }
            else if(Integer.parseInt(type_cmpt) == 5){
                //Département
                AttributeBinding lib_dept = (AttributeBinding) getBindings().getControlBinding("LibHisto");
                id_strc_att.setInputValue(id_strc.getInputValue());
                id_dpt.setInputValue(id_hist.getInputValue());
                proprietaire.setInputValue(lib_dept.getInputValue());
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
                    DCIteratorBinding iter_solde_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("SoldeAnneeAncStrcROIterator");
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

    @SuppressWarnings("unchecked")
    public void inscCompteGlobal(String id_form,String lib_form,String lib_compte){
        
        //IdStructure
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure"); //LibNivForm
  
        AttributeBinding num_cmpte = (AttributeBinding) getBindings().getControlBinding("NumeroCompte");
        AttributeBinding id_type_cmpte = (AttributeBinding) getBindings().getControlBinding("IdTypeCompte");
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("ProprietaireCmpte");
        AttributeBinding id_strc_att = (AttributeBinding) getBindings().getControlBinding("IdStructureCmpte");
        AttributeBinding id_dpt = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructureCmpte");
        AttributeBinding id_form_ = (AttributeBinding) getBindings().getControlBinding("IdFormationCmpte");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversCmpte");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeCmpte");
    
        Integer somme_solde_ini = 0;
        Integer somme_solde_act = 0;
        //getSoldeAncien
        
        OperationBinding op_soldeAncien = getBindings().getOperationBinding("getSoldeCompteGlob");
        op_soldeAncien.getParamsMap().put("id_strc", id_strc.getInputValue() );
        op_soldeAncien.getParamsMap().put("id_annee",  getAnne_univers());
        op_soldeAncien.getParamsMap().put("id_form",  id_form);
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
                id_form_.setInputValue(id_form);
                proprietaire.setInputValue(lib_form);
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
    public void onDialogCanCompte(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopCommitForm().hide();
    }

    public void setPopRepExisteCle(RichPopup popRepExisteCle) {
        this.popRepExisteCle = popRepExisteCle;
    }

    public RichPopup getPopRepExisteCle() {
        return popRepExisteCle;
    }
}
