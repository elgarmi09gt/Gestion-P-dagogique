package equivalence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class EquivalenceBean {
    
    private String numero_table;
    private String numero_etud;
    private String numero_cin;
    private String nom;
    private String prenom;
    private String date_naiss;
    private String cin_nouv;
    private String lieu_naiss_nouv;
    private String email_pers_nouv;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    
    public EquivalenceBean() {
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setCin_nouv(String cin_nouv) {
        this.cin_nouv = cin_nouv;
    }

    public String getCin_nouv() {
        return cin_nouv;
    }

    public void setLieu_naiss_nouv(String lieu_naiss_nouv) {
        this.lieu_naiss_nouv = lieu_naiss_nouv;
    }

    public String getLieu_naiss_nouv() {
        return lieu_naiss_nouv;
    }

    public void setEmail_pers_nouv(String email_pers_nouv) {
        this.email_pers_nouv = email_pers_nouv;
    }

    public String getEmail_pers_nouv() {
        return email_pers_nouv;
    }

    public void getRowPers(Row row_det_pers){
        
        setPrenom(row_det_pers.getAttribute("Prenoms").toString());
        setNom(row_det_pers.getAttribute("Nom").toString());
        java.util.Date utilDate = new java.util.Date(((Date)row_det_pers.getAttribute("DateNaissance")).getTime());
        setCin_nouv(row_det_pers.getAttribute("PieceIdentification").toString());
        setLieu_naiss_nouv(row_det_pers.getAttribute("LieuNaissance").toString());
        setEmail_pers_nouv(row_det_pers.getAttribute("EmailPersonnel").toString());    
            
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
            
                FacesMessage msg = new FacesMessage(" Tous les champs sont obliqatoires :");
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
                    sessionScope.put("tableNotNull", 0);
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
                        sessionScope.put("etudNotNull", 0);
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
                            sessionScope.put("cinNotNull", 0);
                        }
                    }
                }
            }
        }
        return row_det_pers;
    }


    @SuppressWarnings("unchecked")
    public void onChangeNumeroEtud(ClientEvent clientEvent) {
        // Add event code here...
        //System.out.println("value apres "+getNum_etud().getValue());
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("searchString searchString "+searchString);
        sessionScope.put("etudEqNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("etudEqNotNull", 1);
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
                        OperationBinding detpays_nation = getBindings().getOperationBinding("getPaysNationalite");
                        detpays_nation.getParamsMap().put("id_pays",  row_det_pers.getAttribute("IdPaysNationalite"));
                        detpays_nation.execute();
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
        sessionScope.put("cinEqNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("cinEqNotNull", 1);
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
                        OperationBinding detpays_nation = getBindings().getOperationBinding("getPaysNationalite");
                        detpays_nation.getParamsMap().put("id_pays",  row_det_pers.getAttribute("IdPaysNationalite"));
                        detpays_nation.execute();
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
        sessionScope.put("tableEqNotNull", 0);
        sessionScope.put("etudEqNotNull", 0);
        //tableNotNull
        //num_etud.setValue("");
        if(searchString != ""){
            //num_etud.setValue("");
            sessionScope.put("tableEqNotNull", 1);
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
                        OperationBinding detpays_nation = getBindings().getOperationBinding("getPaysNationalite");
                        detpays_nation.getParamsMap().put("id_pays",  row_det_pers.getAttribute("IdPaysNationalite"));
                        detpays_nation.execute();
                        getRowPers(row_det_pers);
                    }

                }
                else{
                    //Le pays de residance de l'étudiant
                    /*OperationBinding detpays = getBindings().getOperationBinding("getPaysPers");
                    detpays.execute();

                    //Nationalité de l'étudiant
                    OperationBinding detpays_nation = getBindings().getOperationBinding("getPaysNationalite");
                    detpays_nation.execute();
                    
                    // civilité de l'étudiant
                    OperationBinding op_civilite = getBindings().getOperationBinding("getCivilitePers");
                    op_civilite.execute();*/
                }
                
            }
            catch( Exception e){}
        }
        else{

        }
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

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setNumero_table(String numero_table) {
        this.numero_table = numero_table;
    }

    @SuppressWarnings("unchecked")
    public String onValiderEquivalence() {
        // Add event code here...
        @SuppressWarnings("unchecked")
        //public Row recherche(){
            Row row_det_pers = null;
            AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationForm");
            AttributeBinding pays_nation = (AttributeBinding) getBindings().getControlBinding("IdPays");
            if((String)this.getNumero_table() == null && (String)this.getNumero_cin() == null && (String)this.getNumero_etud() == null){

                if((String)this.getPrenom() == null && (String)this.getNom() == null 
                    && (String)this.getDate_naiss() == null && (String)this.getLieu_naiss_nouv() == null
                    && (String)this.getCin_nouv() == null){
                    
                    FacesMessage msg = new FacesMessage(" Veuillez saisir soit :");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro de Table"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro Etudiant"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Un numéro d'Identification Nationale (CIN)"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Les informations personnelles "));
                }
                
                else if((String)this.getPrenom() == null || (String)this.getNom() == null || (String)this.getDate_naiss() == null || (String)this.getLieu_naiss_nouv() == null
                || (String)this.getCin_nouv() == null  ){
                
                    FacesMessage msg = new FacesMessage(" Tous les champs sont obliqatoires :");
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
                    if((String)this.getLieu_naiss_nouv() == null){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Lieu de Naissance"));
                    }
                    if((String)this.getCin_nouv() == null){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Numéro d'Identification Nationale"));
                    }
                    

                }
                else if((String)this.getPrenom() != null && (String)this.getNom() != null && (String)this.getDate_naiss() != null && (String)this.getLieu_naiss_nouv() != null
                
                && (String)this.getCin_nouv() != null ){
                        // formatage de date de naiss
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = dateFormat.parse(getDate_naiss());
                            System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013
                        
                            String output = dateFormat.format(date);
                            System.out.println(output); // 2013-12-04
                        } 
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                        insertEq(getNom(), getPrenom(), date, getLieu_naiss_nouv(), getCin_nouv(), pays_nation.getInputValue().toString(), getEmail_pers_nouv(), id_niv_form.getInputValue().toString());
                        OperationBinding equival = getBindings().getOperationBinding("refreshEquivalence");
                        equival.getParamsMap().put("id_annee",  getAnne_univers());
                        equival.getParamsMap().put("id_niv_form",  id_niv_form.getInputValue().toString());
                        equival.execute();
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
                        
                        String cin = row_det_pers.getAttribute("PieceIdentification").toString();
                        String nom_det = row_det_pers.getAttribute("Nom").toString();
                        String prenom_det = row_det_pers.getAttribute("Prenoms").toString();
                        String lieu_nais = row_det_pers.getAttribute("LieuNaissance").toString();
                        String email = row_det_pers.getAttribute("EmailPersonnel").toString();
                        
                        // formatage de date de naiss
                        Date date = (Date)row_det_pers.getAttribute("DateNaissance");
                        
                        insertEq(nom_det, prenom_det, date, lieu_nais, cin, pays_nation.getInputValue().toString(), email, id_niv_form.getInputValue().toString());
                        
                        
                        OperationBinding equival = getBindings().getOperationBinding("refreshEquivalence");
                        equival.getParamsMap().put("id_annee",  getAnne_univers());
                        equival.getParamsMap().put("id_niv_form",  id_niv_form.getInputValue());
                        equival.execute();
                        setNumero_table("");
                        sessionScope.put("tableEqNotNull", 0);
        
                    }
                    else{
                        FacesMessage msg = new FacesMessage(" Le numéro de table : "+getNumero_table()+" n'existe pas");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
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
                            String cin = row_det_pers.getAttribute("PieceIdentification").toString();
                            String nom_det = row_det_pers.getAttribute("Nom").toString();
                            String prenom_det = row_det_pers.getAttribute("Prenoms").toString();
                            String lieu_nais = row_det_pers.getAttribute("LieuNaissance").toString();
                            String email = row_det_pers.getAttribute("EmailPersonnel").toString();
                            
                            // formatage de date de naiss
                            Date date = (Date)row_det_pers.getAttribute("DateNaissance");
                            
                            insertEq(nom_det, prenom_det, date, lieu_nais, cin, pays_nation.getInputValue().toString(), email, id_niv_form.getInputValue().toString());

                            OperationBinding equival = getBindings().getOperationBinding("refreshEquivalence");
                            equival.getParamsMap().put("id_annee",  getAnne_univers());
                            equival.getParamsMap().put("id_niv_form",  id_niv_form.getInputValue());
                            equival.execute();
                            setNumero_etud("");
                            sessionScope.put("etudEqNotNull",0);
                        }
                        else{
                            FacesMessage msg = new FacesMessage(" Le numéro d'étudiant : "+getNumero_etud()+" n'existe pas");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            
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
                                
                                String cin = row_det_pers.getAttribute("PieceIdentification").toString();
                                String nom_det = row_det_pers.getAttribute("Nom").toString();
                                String prenom_det = row_det_pers.getAttribute("Prenoms").toString();
                                String lieu_nais = row_det_pers.getAttribute("LieuNaissance").toString();
                                String email = row_det_pers.getAttribute("EmailPersonnel").toString();
                                
                                // formatage de date de naiss
                                Date date = (Date)row_det_pers.getAttribute("DateNaissance");
                                
                                insertEq(nom_det, prenom_det, date, lieu_nais, cin, pays_nation.getInputValue().toString(), email, id_niv_form.getInputValue().toString());

                                OperationBinding equival = getBindings().getOperationBinding("refreshEquivalence");
                                equival.getParamsMap().put("id_annee",  getAnne_univers());
                                equival.getParamsMap().put("id_niv_form",  id_niv_form.getInputValue());
                                equival.execute();
                                setNumero_cin("");
                                sessionScope.put("cinEqNotNull", 0);
                            }
                            else{
                                FacesMessage msg = new FacesMessage(" Le numéro d'identification : "+getNumero_cin()+" n'existe pas");
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                
                            }
                        }
                    }
                }
 
        }
        return null;
    }
    public void insertEq(String nom, String prenom,Date date_naiss, String lieu_naiss,String cin, String nation,String email,String niv_form){
        
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding nom_attr = (AttributeBinding) getBindings().getControlBinding("Nom");
        AttributeBinding prenom_attr = (AttributeBinding) getBindings().getControlBinding("Prenoms");
        AttributeBinding date_nais = (AttributeBinding) getBindings().getControlBinding("DateNaissance");
        AttributeBinding lieu = (AttributeBinding) getBindings().getControlBinding("LieuNaissance");
        AttributeBinding cin_attr = (AttributeBinding) getBindings().getControlBinding("NumeroCin");
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");
        AttributeBinding nationalite = (AttributeBinding) getBindings().getControlBinding("Nationalite");
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        AttributeBinding email_attr = (AttributeBinding) getBindings().getControlBinding("Email");

        
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertEquival");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        else{
            id_annee.setInputValue(getAnne_univers());
            nom_attr.setInputValue(nom);
            prenom_attr.setInputValue(prenom);
            lieu.setInputValue(lieu_naiss);
            date_nais.setInputValue(date_naiss);
            cin_attr.setInputValue(cin);
            id_niv_form.setInputValue(niv_form);
            nationalite.setInputValue(nation); 
            util.setInputValue(getUtilisateur()); 
            email_attr.setInputValue(email);

            
            OperationBinding op_pers_commit = getBindings().getOperationBinding("Commit");
            Object result_pers_commit = op_pers_commit.execute();
            if (!op_pers_commit.getErrors().isEmpty()) {
                return ;
            }
            
            else{
                setPrenom("");
                setNom("");
                setDate_naiss("");
                setLieu_naiss_nouv("");
                setCin_nouv("");
                setEmail_pers_nouv("");
                OperationBinding detpays_nation = getBindings().getOperationBinding("getPaysNationalite");
                detpays_nation.execute();
            }
        
        }
    }
}
