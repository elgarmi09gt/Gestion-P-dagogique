package groupe_td_tp;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DetailGroupeTdBean {
    
    private String searchbind;
    private String searcheffnouv;
    private String searcheffdoub;
    private List selectedList=new ArrayList();
    private RichSelectManyShuttle empShuttleBinding;
    private RichPanelCollection tableCollection;
    private RichPopup popup;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popEdit;
    private RichPopup popDelEtud;
    private RichSelectBooleanCheckbox check_grouper;
    private RichPopup popMulti;
    
    private String prefix;
    private String nb_groupes;
    private String effectifs;


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setNb_groupes(String nb_groupes) {
        this.nb_groupes = nb_groupes;
    }

    public String getNb_groupes() {
        return nb_groupes;
    }

    public void setEffectifs(String effectifs) {
        this.effectifs = effectifs;
    }

    public String getEffectifs() {
        return effectifs;
    }

    public void setSearcheffnouv(String searcheffnouv) {
        this.searcheffnouv = searcheffnouv;
    }

    public String getSearcheffnouv() {
        return searcheffnouv;
    }

    public void setSearcheffdoub(String searcheffdoub) {
        this.searcheffdoub = searcheffdoub;
    }

    public String getSearcheffdoub() {
        return searcheffdoub;
    }

    public void setSearchbind(String searchbind) {
        this.searchbind = searchbind;
    }

    public String getSearchbind() {
        return searchbind;
    }

    public void setSelectedList(List selectedList) {
        this.selectedList = selectedList;
    }

    public List getSelectedList() {
        return selectedList;
    }
    
    public DCBindingContainer getBindingContainer() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext el = fc.getELContext();
        ValueExpression valueExpr =
            fc.getApplication().getExpressionFactory().createValueExpression(el,
                                                                             "#{bindings}",
                                                                             DCBindingContainer.class);
            
            return ((DCBindingContainer)valueExpr.getValue(el));
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    

    public DetailGroupeTdBean() {
    }

    //methode pour clic sur une ligne du tableau liste des groupe TD
    public void handleTableClick(ClientEvent clientEvent) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        Map resScope =  adfCtx.getRequestScope();
        sessionScope.put("reste", "");
        //sessionScope.put("info_message", 0);
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ListeGroupeTdTpROIterator");
        Row currentRow = iter.getCurrentRow();
        // méthode pour refresh la liste des groupes de TD
        OperationBinding listeEtudGroupeTd = getBindings().getOperationBinding("ExecuteWithParams");

        listeEtudGroupeTd.getParamsMap().put("id_groupe", Long.parseLong(currentRow.getAttribute("IdGroupeTpTd").toString()));
        listeEtudGroupeTd.getParamsMap().put("id_annee", Long.parseLong(currentRow.getAttribute("IdAnneesUnivers").toString()));
        listeEtudGroupeTd.execute();
        
        DCIteratorBinding iter_grp = (DCIteratorBinding)getBindings().get("ListeEtudiantGroupeTdROIterator");
        
        RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
        Integer i =0;
        if(Integer.parseInt(currentRow.getAttribute("Effectif").toString()) > rsi_grp.getRowCount()){
            
            i = Integer.parseInt(currentRow.getAttribute("Effectif").toString()) - rsi_grp.getRowCount();
            sessionScope.put("reste", Integer.parseInt(currentRow.getAttribute("Effectif").toString()) - rsi_grp.getRowCount());

        }
        else{
            sessionScope.put("info_message", 0);
        }
    }

    public void setEmpShuttleBinding(RichSelectManyShuttle empShuttleBinding) {
        this.empShuttleBinding = empShuttleBinding;
    }

    public RichSelectManyShuttle getEmpShuttleBinding() {
        return empShuttleBinding;
    }

// méthode pour filtrer ou rechercher un étudiant
    public void filterShuttleAction(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        StringBuffer selectedListStr=new StringBuffer("");
        if(selectedList!=null)
            for(Object empId:selectedList){
                if(selectedListStr.length()>0)
                    selectedListStr.append(",");
                selectedListStr.append(empId);
            }
        DCBindingContainer bc = getBindingContainer();
        oracle.adf.model.OperationBinding opr = (oracle.adf.model.OperationBinding)bc.get("filterEtudiantsTd");
        opr.getParamsMap().put("searchString", searchString);
        opr.getParamsMap().put("selectedList", selectedListStr);         
        opr.execute();
        getEmpShuttleBinding().resetValue();
    }

    public Integer nombreLigne(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        return rsi.getRowCount();
    }

    public String onValiderGoupeEtud() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        AttributeBinding LibelleLong = (AttributeBinding) getBindings().getControlBinding("LibelleLong");
        AttributeBinding IdGroupeTpTd = (AttributeBinding) getBindings().getControlBinding("IdGroupeTpTd");
        AttributeBinding IdAnneesUnivers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        
        AttributeBinding IdHorairesTd = (AttributeBinding) getBindings().getControlBinding("IdHorairesTd");
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre");     
        
        List lis = (List)getEmpShuttleBinding().getValue();     // la liste des étudiants sélectionnés dans le Shuttle
        
        AttributeBinding id_groupe = (AttributeBinding) getBindings().getControlBinding("IdGroupeTpTd1");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        AttributeBinding id_ins_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogique1");
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ListeGroupeTdTpROIterator");
        Row currentRow_grp = iter.getCurrentRow();
        
        Integer j=0;    //nombre d'étudiants ajoutés
        if(nombreLigne("ListeGroupeTdTpROIterator") < 1){
            FacesMessage msg = new FacesMessage(" Veuillez crééer un groupe pour continuer ! ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if(lis == null){
                FacesMessage msg = new FacesMessage(" Veuillez selectionner au moins un étudiant avant de valider ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                Integer nb_rejet = 0;       // nombre d'étudiants rejetés dans un groupe TD. Autrement dit le groupe est déjà plein
                DCIteratorBinding iter_grp = (DCIteratorBinding)getBindings().get("ListeEtudiantGroupeTdROIterator");
                
                RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
                Integer nb_place_restant =0;        // nombre de places restantes dans un groupe TD
                // s'il ya une place libre dans le groupe (le groupe n'est pas encore plein)
                if(Integer.parseInt(currentRow_grp.getAttribute("Effectif").toString()) > rsi_grp.getRowCount()){
                    
                    nb_place_restant = Integer.parseInt(currentRow_grp.getAttribute("Effectif").toString()) - rsi_grp.getRowCount();
                    for(int i=0;i < lis.size();i++){
                        if(nb_place_restant > j){
                            OperationBinding op_grp_td_etud = getBindings().getOperationBinding("CreateInsert");
                            Object result_grp_td_etud = op_grp_td_etud.execute();
                                          
                            if (!op_grp_td_etud.getErrors().isEmpty()) {
                               return null;
                            }
                            else{
                               id_groupe.setInputValue(Long.parseLong(IdGroupeTpTd.getInputValue().toString()));
                               id_ins_ped.setInputValue(Long.parseLong(lis.get(i).toString()));
                               id_util.setInputValue(Long.parseLong(sessionScope.get("IdUtilisateur").toString()));
                               
                               OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                               Object result = operationBinding1.execute();
                               if (!operationBinding1.getErrors().isEmpty()) {
                                       //handle errors if any
                                       return null;
                               }
                               else{
                                    j++;
                                   }
                               }
                        }
                        else{
                            nb_rejet++;
                        }
                    }
                    
                    if(j > 0){
                        FacesMessage msg = new FacesMessage(" "+j+" Etudiant(s) inscrit(s) dans le groupe : "+LibelleLong.getInputValue());
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    if(nb_rejet > 0){
                        FacesMessage msg = new FacesMessage(" "+nb_rejet+" Etudiant(s) rejeté(s) dans le groupe : "+LibelleLong.getInputValue());
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    getEmpShuttleBinding().setAutoSubmit(true);
                    getEmpShuttleBinding().resetValue();
                    // méthode pour actualiser la table des etudiant du groupe TD
                    OperationBinding listeEtudGroupeTd = getBindings().getOperationBinding("ExecuteWithParams");

                    listeEtudGroupeTd.getParamsMap().put("id_groupe", Long.parseLong(IdGroupeTpTd.getInputValue().toString()));
                    listeEtudGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                    listeEtudGroupeTd.execute();
                    
                    // méthode pour actualiser la table des étudiants qui ne sont pas encore inscrits dans un groupe TD
                    OperationBinding listeEtudPasGroupeTd = getBindings().getOperationBinding("ExecuteWithParams1");

                    listeEtudPasGroupeTd.getParamsMap().put("id_niv_form_parc", Long.parseLong(IdNiveauFormationParcours.getInputValue().toString()));
                    listeEtudPasGroupeTd.getParamsMap().put("id_sem", Long.parseLong(IdSemestre.getInputValue().toString()));
                    listeEtudPasGroupeTd.getParamsMap().put("id_horaire_td", Long.parseLong(IdHorairesTd.getInputValue().toString()));
                    listeEtudPasGroupeTd.getParamsMap().put("id_util", Long.parseLong(sessionScope.get("IdUtilisateur").toString()));
                    listeEtudPasGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                    listeEtudPasGroupeTd.execute();
                }
                else{
                    FacesMessage msg = new FacesMessage(" L'effectif maximun est atteint ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                
                

                
            }
        }
        return null;
    }

    public void onNewGroupeTd(ActionEvent actionEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        //les val recuperees
        AttributeBinding IdNivFormParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours2");
        System.out.println(IdNivFormParcours.getInputValue().toString());
        AttributeBinding IdSem = (AttributeBinding) getBindings().getControlBinding("IdSemestre2");
       
        
        AttributeBinding horaireTd = (AttributeBinding) getBindings().getControlBinding("Parcours");
        AttributeBinding idmodeEns = (AttributeBinding) getBindings().getControlBinding("Idmode");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers2");
        
        
        AttributeBinding IdHorairesTd = (AttributeBinding) getBindings().getControlBinding("IdHorairesTd1");
        AttributeBinding IdModeEnseignement = (AttributeBinding) getBindings().getControlBinding("IdModeEnseignement");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre1");
        AttributeBinding IdAnneesUnivers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding idUtilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours1");
        
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("GroupeTpTdIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        
        adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertGrpTd");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        else{
            IdHorairesTd.setInputValue(Long.parseLong(horaireTd.getInputValue().toString()));
            IdModeEnseignement.setInputValue(Long.parseLong(idmodeEns.getInputValue().toString()));
            IdSemestre.setInputValue(Long.parseLong(IdSem.getInputValue().toString()));
            IdAnneesUnivers.setInputValue(Long.parseLong(id_annee.getInputValue().toString()));
            idUtilisateur.setInputValue(Long.parseLong(sessionScope.get("IdUtilisateur").toString()));
            IdNiveauFormationParcours.setInputValue(Long.parseLong(IdNivFormParcours.getInputValue().toString()));
            RichPopup popup = this.getPopup();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setTableCollection(RichPanelCollection tableCollection) {
        this.tableCollection = tableCollection;
    }

    public RichPanelCollection getTableCollection() {
        return tableCollection;
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
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("GroupeTpTdIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        AttributeBinding IdNivFormParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours2");
        //System.out.println(IdNivFormParcours.getInputValue().toString());
        AttributeBinding IdSem = (AttributeBinding) getBindings().getControlBinding("IdSemestre2");
        AttributeBinding horaireTd = (AttributeBinding) getBindings().getControlBinding("Parcours");
        AttributeBinding idmodeEns = (AttributeBinding) getBindings().getControlBinding("Idmode");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers2");
    
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            AttributeBinding eff_reel_nouv = (AttributeBinding) getBindings().getControlBinding("EffectifReelNouveau");
            AttributeBinding eff_reel_doub = (AttributeBinding) getBindings().getControlBinding("EffectifReelDoublant");
            
            eff_reel_nouv.setInputValue(this.getSearcheffnouv());
            eff_reel_doub.setInputValue(this.getSearcheffdoub());
                      
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            
            // méthode pour actualiser la table  groupes TD
            
            OperationBinding listeGroupeTd = getBindings().getOperationBinding("ExecuteWithParams2");

            listeGroupeTd.getParamsMap().put("id_parc", Long.parseLong(IdNivFormParcours.getInputValue().toString()));
            listeGroupeTd.getParamsMap().put("id_sem", Long.parseLong(IdSem.getInputValue().toString()));
            listeGroupeTd.getParamsMap().put("id_horaire", Long.parseLong(horaireTd.getInputValue().toString()));
            listeGroupeTd.getParamsMap().put("id_mode", Long.parseLong(idmodeEns.getInputValue().toString()));
            listeGroupeTd.getParamsMap().put("id_annee", Long.parseLong(id_annee.getInputValue().toString()));
            listeGroupeTd.execute();
            this.getPopup().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
        }
    }

    @SuppressWarnings("unchecked")
    public void onEditGroupeTd(ActionEvent actionEvent) {
        // Add event code here...
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("ListeGroupeTdTpROIterator");

        if(nombreLigne("ListeGroupeTdTpROIterator") > 0){
            
            Row currentRow = iter.getCurrentRow();System.out.println("currentRow "+currentRow);
            OperationBinding groupeTdSelection = getBindings().getOperationBinding("ExecuteWithParams3");
            groupeTdSelection.getParamsMap().put("id_grp_td", Long.parseLong(currentRow.getAttribute("IdGroupeTpTd").toString()));
            groupeTdSelection.execute();
            
            DCIteratorBinding iter_grp_select = (DCIteratorBinding) getBindings().get("GroupeTpTdIterator");
            Row currentRow_grp_select = iter_grp_select.getCurrentRow();
            System.out.println("EffectifReelNouveau "+currentRow_grp_select.getAttribute("EffectifReelNouveau"));
            System.out.println("EffectifReelDoublant "+currentRow_grp_select.getAttribute("EffectifReelDoublant"));
            //recupération des anciennes valeurs de EffectifReelNouveau et de EffectifReelDoublant
            if(currentRow_grp_select.getAttribute("EffectifReelNouveau") !=null)
                setSearcheffnouv(currentRow_grp_select.getAttribute("EffectifReelNouveau").toString());
            if(currentRow_grp_select.getAttribute("EffectifReelDoublant") !=null)
                setSearcheffdoub(currentRow_grp_select.getAttribute("EffectifReelDoublant").toString());
            System.out.println("pop ");
            RichPopup popup = this.getPopEdit();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            FacesMessage msg = new FacesMessage(" Veuillez ajouter au moins un groupe avant d'éditer ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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

    // pour actualiser le calcul de l'effectif via l'EffectifReelDoublant et l'EffectifReelNouveau
    // s'il ya un changement de valeur l'EffectifReelNouveau 
    public void onChangeEffReeNouv(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        AttributeBinding effectif = (AttributeBinding) getBindings().getControlBinding("Effectif");

        if((searchString == "")&&(getSearcheffdoub()==null))
            effectif.setInputValue(0);
        else if((getSearcheffdoub()==null)&& (searchString !=""))
                effectif.setInputValue(Integer.parseInt(searchString)+ 0);
            else if((getSearcheffdoub()!=null)&& (searchString ==""))
                    effectif.setInputValue(Integer.parseInt(getSearcheffdoub())+ 0);
                else
                    effectif.setInputValue(Integer.parseInt(searchString)+ Integer.parseInt(getSearcheffdoub()));   // actualiser l'Effectif
    }

    // pour actualiser le calcul de l'effectif via l'EffectifReelDoublant et l'EffectifReelNouveau
    // s'il ya un changement de valeur l'EffectifReelDoublant
    public void onChangeEffReelDoub(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        AttributeBinding effectif = (AttributeBinding) getBindings().getControlBinding("Effectif");

        if((searchString == "")&&(getSearcheffnouv()==null))
            effectif.setInputValue(0);
        else if((getSearcheffnouv()==null)&& (searchString !=""))
                effectif.setInputValue(Integer.parseInt(searchString)+ 0);
            else if((getSearcheffnouv()!=null)&& (searchString ==""))
                    effectif.setInputValue(Integer.parseInt(getSearcheffnouv())+ 0);
                else
                    effectif.setInputValue(Integer.parseInt(searchString)+ Integer.parseInt(getSearcheffnouv()));   // actualiser l'Effectif
    }

    @SuppressWarnings("unchecked")
    public String onRetireEtudiant() {
        // Add event code here...
        
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("ListeEtudiantGroupeTdROIterator");
        Row currentRow = dciter.getCurrentRow();
        System.out.println(currentRow.getAttribute("IdGroupeTpTdEtudiant"));
        
        OperationBinding etud_delete = getBindings().getOperationBinding("ExecuteWithParams4");

        etud_delete.getParamsMap().put("id_grp_etud", Long.parseLong(currentRow.getAttribute("IdGroupeTpTdEtudiant").toString()));
        etud_delete.execute();
        
        
        RichPopup popup = this.getPopDelEtud();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public void setPopDelEtud(RichPopup popDelEtud) {
        this.popDelEtud = popDelEtud;
    }

    public RichPopup getPopDelEtud() {
        return popDelEtud;
    }

    public void onDialogCanDel(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDelEtud().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogDel(DialogEvent dialogEvent) {
        // Add event code here...

        AttributeBinding IdGroupeTpTd = (AttributeBinding) getBindings().getControlBinding("IdGroupeTpTd2");
        AttributeBinding IdAnneesUnivers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers3");
        OperationBinding operationDelete = getBindings().getOperationBinding("Delete");
        Object result = operationDelete.execute();
        if (!operationDelete.getErrors().isEmpty()) {
            return;
        }
        else{
           OperationBinding operationCommit = getBindings().getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            if (!operationCommit.getErrors().isEmpty()) {
                return;
            }
            else{
                OperationBinding listeEtudGroupeTd = getBindings().getOperationBinding("ExecuteWithParams");
    
                listeEtudGroupeTd.getParamsMap().put("id_groupe", Long.parseLong(IdGroupeTpTd.getInputValue().toString()));
                listeEtudGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                listeEtudGroupeTd.execute();
            }
            
        }
    }

    public void setCheck_grouper(RichSelectBooleanCheckbox check_grouper) {
        this.check_grouper = check_grouper;
    }

    public RichSelectBooleanCheckbox getCheck_grouper() {
        return check_grouper;
    }

    @SuppressWarnings("unchecked")
    public void onCreerMultiGroupe(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        sessionScope.put("is_grouper", 0);
        if(Boolean.parseBoolean(check_grouper.getValue().toString())){
            sessionScope.put("is_grouper", 1);
            AttributeBinding lib_long = (AttributeBinding) getBindings().getControlBinding("LibelleLong1");
            AttributeBinding lig_court = (AttributeBinding) getBindings().getControlBinding("LibelleCourt");
            
            lib_long.setInputValue("arg0");
            lig_court.setInputValue("arg0");
            
            System.out.println(lib_long.getInputValue());
            System.out.println(lig_court.getInputValue());
        }
        else{
            sessionScope.put("is_grouper", 0);
        }
    }

    public void onNewMultiGrp(ActionEvent actionEvent) {
        // Add event code here...
        
        RichPopup popup = this.getPopMulti();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void setPopMulti(RichPopup popMulti) {
        this.popMulti = popMulti;
    }

    public RichPopup getPopMulti() {
        return popMulti;
    }

    public void onDialogCanMulti(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopMulti().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogMulti(DialogEvent dialogEvent) {
        // Add event code here...
        String pref_grp = "groupe ";
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        //les val recuperees
        AttributeBinding IdNivFormParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours2");
        System.out.println(IdNivFormParcours.getInputValue().toString());
        AttributeBinding IdSem = (AttributeBinding) getBindings().getControlBinding("IdSemestre2");
        
        
        AttributeBinding horaireTd = (AttributeBinding) getBindings().getControlBinding("Parcours");
        AttributeBinding idmodeEns = (AttributeBinding) getBindings().getControlBinding("Idmode");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers2");
        
        
        AttributeBinding IdHorairesTd = (AttributeBinding) getBindings().getControlBinding("IdHorairesTd1");
        AttributeBinding IdModeEnseignement = (AttributeBinding) getBindings().getControlBinding("IdModeEnseignement");
        AttributeBinding IdSemestre = (AttributeBinding) getBindings().getControlBinding("IdSemestre1");
        AttributeBinding IdAnneesUnivers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers1");
        AttributeBinding idUtilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours1");

        AttributeBinding effectif = (AttributeBinding) getBindings().getControlBinding("Effectif");
        AttributeBinding lib_long = (AttributeBinding) getBindings().getControlBinding("LibelleLong1");
        AttributeBinding lib_court = (AttributeBinding) getBindings().getControlBinding("LibelleCourt");        
        
        if((String)this.getPrefix() != null){
            pref_grp = (String)this.getPrefix();
        }
        if((String)this.getNb_groupes() == null){
            FacesMessage msg = new FacesMessage(" Veuillez saisir le nombre de groupes ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if((String)this.getEffectifs() == null){
                FacesMessage msg = new FacesMessage(" Veuillez saisir l'effectif des groupes");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                Integer grp = 0;
                Integer i = 0;
                while(grp < Integer.parseInt(getNb_groupes())){
                    OperationBinding op_grp_td = getBindings().getOperationBinding("CreateInsertGrpTd");
                    Object res_grp_td = op_grp_td.execute();
                    if (!op_grp_td.getErrors().isEmpty()) {
                        return ;
                    }
                    else{
                        IdHorairesTd.setInputValue(Long.parseLong(horaireTd.getInputValue().toString()));
                        IdModeEnseignement.setInputValue(Long.parseLong(idmodeEns.getInputValue().toString()));
                        IdSemestre.setInputValue(Long.parseLong(IdSem.getInputValue().toString()));
                        IdAnneesUnivers.setInputValue(Long.parseLong(id_annee.getInputValue().toString()));
                        idUtilisateur.setInputValue(Long.parseLong(sessionScope.get("IdUtilisateur").toString()));
                        IdNiveauFormationParcours.setInputValue(Long.parseLong(IdNivFormParcours.getInputValue().toString())); 
                    
                        effectif.setInputValue(Integer.parseInt(getEffectifs()));
                        lib_long.setInputValue(pref_grp+" "+(grp +1));
                        lib_court.setInputValue(pref_grp+" "+(grp +1));
                        
                        OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                        Object result = op_commit.execute();
                        if (!op_commit.getErrors().isEmpty()) {
                                //handle errors if any
                                return ;
                        }
                        else{
                            i++;     //nombre de groupes crees
                        }
                        
                    grp ++;
                }
            }
            FacesMessage msg = new FacesMessage(i+ " groupes créés avec succès ");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } 
    // méthode pour actualiser la table  groupes TD
    
    OperationBinding listeGroupeTd = getBindings().getOperationBinding("ExecuteWithParams2");

    listeGroupeTd.getParamsMap().put("id_parc", Long.parseLong(IdNivFormParcours.getInputValue().toString()));
    listeGroupeTd.getParamsMap().put("id_sem", Long.parseLong(IdSem.getInputValue().toString()));
    listeGroupeTd.getParamsMap().put("id_horaire", Long.parseLong(horaireTd.getInputValue().toString()));
    listeGroupeTd.getParamsMap().put("id_mode", Long.parseLong(idmodeEns.getInputValue().toString()));
    listeGroupeTd.getParamsMap().put("id_annee", Long.parseLong(id_annee.getInputValue().toString()));
    listeGroupeTd.execute();
    this.getPopMulti().hide();
    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableCollection());
    }
}
