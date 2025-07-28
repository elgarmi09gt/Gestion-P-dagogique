package exclu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class ExcluBean {
    
    private String numero_table;
    private String numero_etud;
    private String numero_cin;
    private String nom;
    private String prenom;
    private String date_naiss;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String taskFlowId = "/inscription/exclu/dynamic-tf.xml#dynamic-tf";

    public ExcluBean() {
    }

    public void setNumero_table(String numero_table) {
        this.numero_table = numero_table;
    }

    public String getNumero_table() {
        return numero_table;
    }

    public void setNumero_etud(String numero_etud) {
        this.numero_etud = numero_etud;
    }

    public String getNumero_etud() {
        return numero_etud;
    }

    public void setNumero_cin(String numero_cin) {
        this.numero_cin = numero_cin;
    }

    public String getNumero_cin() {
        return numero_cin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setDate_naiss(String date_naiss) {
        this.date_naiss = date_naiss;
    }

    public String getDate_naiss() {
        return date_naiss;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void getRowPers(Row row_det_pers){
        
        setPrenom(row_det_pers.getAttribute("Prenoms").toString());
        setNom(row_det_pers.getAttribute("Nom").toString());
        java.util.Date utilDate = new java.util.Date(((Date)row_det_pers.getAttribute("DateNaissance")).getTime());
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date_naissance = dateFormat.format(utilDate);
        setDate_naiss(date_naissance);
    }

    @SuppressWarnings("unchecked")
    public Row recherche(){
        Row row_det_pers = null;
        if((String)this.getNumero_etud() == null && (String)this.getNumero_table() == null && (String)this.getNumero_cin() == null){

            if((String)this.getPrenom() == null && (String)this.getNom() == null 
                && (String)this.getDate_naiss() == null ){
                
                FacesMessage msg = new FacesMessage(" Veuillez saisir soit :");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro de Table"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro Etudiant"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro d'Identification Nationale (CIN)"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Les informations personnelles "));
            }

            else if((String)this.getPrenom() == null || (String)this.getNom() == null || (String)this.getDate_naiss() == null){
            
                FacesMessage msg = new FacesMessage(" Tous les champs sont obligatoires :");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
                if((String)this.getPrenom() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Prénom"));
                }
                if((String)this.getNom() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Nom"));
                }
                if((String)this.getDate_naiss() == null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Date Naissance"));
                }

            }
            else if((String)this.getPrenom() != null && (String)this.getNom() != null && (String)this.getDate_naiss() != null){

                // formatage de date de naiss
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                java.sql.Date date2 = null;
                try {
                    date = dateFormat.parse(getDate_naiss());
                    System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

                    String output = dateFormat.format(date);
                    System.out.println(output); // 2013-12-04
                } 
                catch (ParseException e) {
                    e.printStackTrace();
                }
                // si personne existe deja avec toutes les infos personnelles
                OperationBinding pers_existe = getBindings().getOperationBinding("getPersonneSearch");
                pers_existe.getParamsMap().put("nom_pers",  getNom());
                pers_existe.getParamsMap().put("prenom_pers",  getPrenom());
                pers_existe.getParamsMap().put("date_naiss",  getDate_naiss());
                pers_existe.execute();
                
                DCIteratorBinding iter_pers_existe = (DCIteratorBinding) getBindings().get("PersonneSearchSimpleIterator");
                Row cuurrent_row = iter_pers_existe.getCurrentRow();
                if(cuurrent_row != null){
                    row_det_pers = cuurrent_row;
                }
                else{
                    //la personne n existe pas
                    FacesMessage msg = new FacesMessage(" L'étudiant du :");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Prénom "+getPrenom()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Nom "+getNom()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Date de Naissance "+getDate_naiss()));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" n'existe pas"));
                }
        }
            
        }
        else{
            if((String)this.getNumero_table() != null){
                OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString()));
                is_nouv_bac.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                Integer result = (Integer)is_nouv_bac.execute();
                //numéro table non valide
                if(result != 0){
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                    getIdPersEtudiant.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString())); 
                    getIdPersEtudiant.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    row_det_pers = rsi_det_pers.first();
    
                }
                else{
                    FacesMessage msg = new FacesMessage(" Le numéro de table : "+getNumero_table()+" n'existe pas");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    setNumero_table("");
                    sessionScope.put("tableNotNullSusp", 0);
                }
    
            }
            else {
                if((String)this.getNumero_etud() != null ){
                    OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                    isEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());
                    
                    Integer result = (Integer)isEtudiant.execute();
                    //numéro étudiant non valide
                    if(result != 0){
                        //numéro étudiant valide
                        // recuperer id personn de l etudiant
                        OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                        getIdPersEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());                            
                        String result_getid = (String)getIdPersEtudiant.execute(); 
        
                        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                        detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                        detpers.execute();
                        
                        DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                            //Create RowSetIterato iterate over viewObject
                        RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                        row_det_pers = rsi_det_pers.first();
    
                    }
                    else{
                        FacesMessage msg = new FacesMessage(" Le numéro d'étudiant : "+getNumero_etud()+" n'existe pas");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        setNumero_etud("");
                        sessionScope.put("etudNotNullSusp", 0);
                    }
                }
                else {
                    if((String)this.getNumero_cin() != null ){
                        //vérifier le numéro d'identification
                        OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
                        isEtudiant.getParamsMap().put("num_cin",  (String)this.getNumero_cin());
                        
                        Integer result = (Integer)isEtudiant.execute();
                        // le numéro d'identification non valide            
                        if(result != 0){
                            // le numéro d'identification valide
                            // recuperer id personn de l etudiant
                            OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                            getIdPersEtudiant.getParamsMap().put("cin",  (String)this.getNumero_cin());  
                            String result_getid = (String)getIdPersEtudiant.execute(); 
        
                            OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                            detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                            detpers.execute();
                            
                            DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                                //Create RowSetIterato iterate over viewObject
                            RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                            row_det_pers = rsi_det_pers.first();
                        }
                        else{
                            FacesMessage msg = new FacesMessage(" Le numéro d'identification : "+getNumero_cin()+" n'existe pas");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            setNumero_cin("");
                            sessionScope.put("cinNotNullSusp", 0);
                        }
                    }
                }
            }
        }
        return row_det_pers;
    }


    @SuppressWarnings("unchecked")
    public String onValiderRecherche() {
        // Add event code here...
        Row rw = recherche();
        if(rw != null){
            
            getRowPers(rw);
            
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
            list_insc_ped.execute();
            
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("InscPedExcluROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            if(row_list_insc_ped != null){
                sessionScope.put("id_pers_insc_exclu",rw.getAttribute("IdPersonne").toString());
                sessionScope.put("nom_etud",rw.getAttribute("Nom").toString());
                sessionScope.put("prenom_etud",rw.getAttribute("Prenoms").toString());
            
                sessionScope.put("TfExcluID", "/inscription/exclu/detail-exclu-tf.xml#detail-exclu-tf");
            }
            else{
                sessionScope.put("TfExcluID", "/inscription/exclu/task-flow-default.xml#task-flow-default");
                
                FacesMessage msg = new FacesMessage("Aucune inscription pédagogique pour l'étudiant du prénom & nom : "+rw.getAttribute("Prenoms")+" "+rw.getAttribute("Nom"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        }
        else{           
            sessionScope.put("TfExcluID", "/inscription/exclu/task-flow-default.xml#task-flow-default");
        }
        
        return null;
    }


    @SuppressWarnings("unchecked")
    public void onChangeNumeroEtud(ClientEvent clientEvent) {
        // Add event code here...
        //System.out.println("value apres "+getNum_etud().getValue());
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        sessionScope.put("etudNotNullExclu", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("etudNotNullExclu", 1);
            try{
                OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                isEtudiant.getParamsMap().put("num_etud",  searchString);
                
                Integer result = (Integer)isEtudiant.execute();
                //numéro étudiant non valide
                if(result != 0){
                    //numéro étudiant valide
                    // recuperer id personn de l etudiant
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                    getIdPersEtudiant.getParamsMap().put("num_etud",  searchString);                            
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    @SuppressWarnings("unchecked")
    public void onChangeNumeroCin(ClientEvent clientEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        sessionScope.put("cinNotNullExclu", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("cinNotNullExclu", 1);
            try{
                //vérifier le numéro d'identification
                OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
                isEtudiant.getParamsMap().put("num_cin",  searchString);
                
                Integer result = (Integer)isEtudiant.execute();
                // le numéro d'identification non valide            
                if(result != 0){
                    // le numéro d'identification valide
                    // recuperer id personn de l etudiant
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                    getIdPersEtudiant.getParamsMap().put("cin",  searchString);  
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    System.out.println("result_getid" +result_getid);
                    sessionScope.put("idpers", Long.parseLong(result_getid));
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    @SuppressWarnings("unchecked")
    public void onChangeNumeroTable(ClientEvent clientEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString "+searchString);
        sessionScope.put("tableNotNullExclu", 0);
        sessionScope.put("etudNotNullExclu", 0);
        //tableNotNull
        //num_etud.setValue("");
        if(searchString != ""){
            //num_etud.setValue("");
            sessionScope.put("tableNotNullExclu", 1);
            try{
                OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong(searchString));
                is_nouv_bac.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                Integer result = (Integer)is_nouv_bac.execute();
                //numéro table non valide
                if(result != 0){
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                    getIdPersEtudiant.getParamsMap().put("num_table",  Long.parseLong(searchString)); 
                    getIdPersEtudiant.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    Row row_det_pers = rsi_det_pers.first();
                    if(row_det_pers != null){
                        getRowPers(row_det_pers);
                    }

                }
                else{

                }
                
            }
            catch( Exception e){}
        }
        else{

        }
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }
}
