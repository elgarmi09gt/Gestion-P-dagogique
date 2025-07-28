package derogation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import oracle.adf.share.ADFContext;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DerogationBean {
    private String numero_table;
    private String numero_etud;
    private String numero_cin;
    private String nom;
    private String prenom;
    private String date_naiss;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    
    public DerogationBean() {
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void setNumero_table(String numero_table) {
        this.numero_table = numero_table;
    }

    public void getRowPers(Row row_det_pers){
        
        setPrenom(row_det_pers.getAttribute("Prenoms").toString());
        setNom(row_det_pers.getAttribute("Nom").toString());
        java.util.Date utilDate = new java.util.Date(((Date)row_det_pers.getAttribute("DateNaissance")).getTime());
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date_naissance = dateFormat.format(utilDate);
        setDate_naiss(date_naissance);
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
        sessionScope.put("etudDerNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("etudDerNotNull", 1);
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
        sessionScope.put("cinDerNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("cinDerNotNull", 1);
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
        sessionScope.put("tableDerNotNull", 0);
        sessionScope.put("etudDerNotNull", 0);
        //tableNotNull
        //num_etud.setValue("");
        if(searchString != ""){
            //num_etud.setValue("");
            sessionScope.put("tableDerNotNull", 1);
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

// valider
    public String onAutoriserDerogation() {
        // Add event code here...
        Row rw = recherche();
        if(rw != null){
            AttributeBinding id_parc_maquette = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
            
            Row info_auto_encours =  autoriserInsc(rw.getAttribute("IdPersonne").toString(), id_parc_maquette.getInputValue().toString(), getAnne_univers());
            Row row_global_form = infoGlobalForm(id_parc_maquette.getInputValue().toString());
            //getInscPedEnCours
            OperationBinding op_parc_cours = getBindings().getOperationBinding("getInscPedEnCours");
            op_parc_cours.getParamsMap().put("id_parc",  Long.parseLong(id_parc_maquette.getInputValue().toString())); 
            op_parc_cours.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
            op_parc_cours.execute();
            
            DCIteratorBinding iter_parc_cours = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedParcEnCoursROIterator");
            RowSetIterator rsi_parc_cours = iter_parc_cours.getViewObject().createRowSetIterator(null);
            
            Integer nombre_insc = rsi_parc_cours.getRowCount();
            
            if(info_auto_encours != null){
                FacesMessage msg = new FacesMessage( " L'étudiant du nom & prénom : "+ rw.getAttribute("Nom").toString()+" "+rw.getAttribute("Prenoms").toString()+" est déjà autorisé à s'inscrire en "+row_global_form.getAttribute("LibNivForm")+" pour l'année encours ( "+row_global_form.getAttribute("LibAnnee")+" )");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getRowPers(rw);
            }
            else{
                if(row_global_form.getAttribute("NbreInsPermise") == null){
                    FacesMessage msg = new FacesMessage( " Le nombre d'inscription permise n'est pas renseignée pour le niveau de Formation :  "+row_global_form.getAttribute("LibNivForm"));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    getRowPers(rw);
                }
                else{System.out.println("NbreInsPermise "+row_global_form.getAttribute("NbreInsPermise"));
                    if(Integer.parseInt( row_global_form.getAttribute("NbreInsPermise").toString()) == nombre_insc){
                        insertAutorisation(rw.getAttribute("IdPersonne").toString(), id_parc_maquette.getInputValue().toString());
                        //,getAnne_univers()," L'étudiant du nom & prénom : "+rw.getAttribute("Nom")+" "+rw.getAttribute("Prenoms")+" est autorisée à s'inscrire en "+row_global_form.getAttribute("LibNivForm"), " NB: Par Dérogation du DAP"
                        //getRefreshDerogation
                        OperationBinding op_ref_table_der = getBindings().getOperationBinding("getRefreshDerogation");
                        op_ref_table_der.getParamsMap().put("id_parc",  Long.parseLong(id_parc_maquette.getInputValue().toString())); 
                        op_ref_table_der.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                        op_ref_table_der.execute();
                    }
                    else{
                        if(Integer.parseInt( row_global_form.getAttribute("NbreInsPermise").toString()) > nombre_insc){
                            FacesMessage msg = new FacesMessage(" Impossible d'autoriser l'étudiant "+rw.getAttribute("Nom").toString()+" "+rw.getAttribute("Prenoms").toString()+" à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Pas de dérogation du DAP (  le nombre d'inscription permis ("+row_global_form.getAttribute("NbreInsPermise")+" ) n'est pas atteint ("+nombre_insc+") )"));
                            getRowPers(rw);
                        }
                        else{
                            if(Integer.parseInt( row_global_form.getAttribute("NbreInsPermise").toString()) < nombre_insc){
                                FacesMessage msg = new FacesMessage(" Impossible d'autoriser l'étudiant "+rw.getAttribute("Nom").toString()+" "+rw.getAttribute("Prenoms").toString()+" à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Pas de dérogation du DAP ( L'étudiant a déjà bénéficié du dérogation du DAP )"));
                                getRowPers(rw);
                            }
                        }
                    }
                }
                
            }
            
               
            
            //Integer.parseInt( row_global_form.getAttribute("NbreInsPermise").toString())
            System.out.println("rw "+rw);
            //getRowPers(rw);
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public Row autoriserInsc(String id_pers,String id_niv_form_parc,String id_annee){
        OperationBinding op_auto_insc = getBindings().getOperationBinding("getInfoAutorise");
        op_auto_insc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_niv_form_parc));
        op_auto_insc.getParamsMap().put("id_annee",  Long.parseLong(id_annee));  
        op_auto_insc.getParamsMap().put("id_pers",  Long.parseLong(id_pers));
        op_auto_insc.execute();
        DCIteratorBinding iter_info = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("PersDejaAutoriserROIterator");
        return iter_info.getCurrentRow();
    }    
    @SuppressWarnings("unchecked")
    public Row infoGlobalForm(String id_niv_form_parc){
        OperationBinding op_info_form = getBindings().getOperationBinding("getInfoForm");
        op_info_form.getParamsMap().put("id_parc_maq",  Long.parseLong(id_niv_form_parc));
        //op_info_form.getParamsMap().put("id_annee",  Long.parseLong(id_annee));          
        op_info_form.execute();
        DCIteratorBinding iter_info = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscInfoGlobleFormIterator");
        return iter_info.getCurrentRow();
    }
    
    public void insertAutorisation(String id_pers, String id_niv_form_parc){
    
        Row detail = getDetailStrc(id_niv_form_parc);
        /*AttributeBinding id_annee_auto = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_pers_auto = (AttributeBinding) getBindings().getControlBinding("IdPersonne");
        AttributeBinding id_parc_aut = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneeAuto");
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCreeAutorise");
        
        AttributeBinding derogation = (AttributeBinding) getBindings().getControlBinding("Derogation");
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("Valide");*/
        //les attributs à modifier
        
        AttributeBinding lib_fac = (AttributeBinding) getBindings().getControlBinding("LibelleLongFac");
        AttributeBinding sigle = (AttributeBinding) getBindings().getControlBinding("Sigle");
        AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversDer");
        
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        AttributeBinding id_parc = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneeDer");
        
        
        OperationBinding getEtud = getBindings().getOperationBinding("getEtudiant");
        getEtud.getParamsMap().put("idpers",  Long.parseLong(id_pers));
        getEtud.execute();
        DCIteratorBinding iter_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudiantsIterator");
        Row row_etud = iter_etud.getCurrentRow();
        //String id_etud = row_etud.getAttribute("IdEtudiant").toString();
        
        
        OperationBinding op_autorise = getBindings().getOperationBinding("CreateInsertDerogation");
        Object result = op_autorise.execute();
        if (!op_autorise.getErrors().isEmpty()) {
            return ;
        }
        else{
            util.setInputValue(getUtilisateur());
            id_parc.setInputValue(id_niv_form_parc);
            id_annee.setInputValue(getAnne_univers());
            id_etudiant.setInputValue(row_etud.getAttribute("IdEtudiant"));
            
            lib_fac.setInputValue(detail.getAttribute("LibStrc"));            
            sigle.setInputValue(detail.getAttribute("SigleStrc"));       
            
            //annee_bac.setInputValue(getBac_nouv());
            
            OperationBinding op_autorise_commit = getBindings().getOperationBinding("Commit");
            Object result_commit = op_autorise_commit.execute();
            if (!op_autorise_commit.getErrors().isEmpty()) {
                return;
            }           
            else{
                
                if((String)this.getNumero_etud() != null ){
                    setNumero_etud("");
                    setPrenom("");
                    setNom("");
                    setDate_naiss("");
                }
                else
                    if((String)this.getNumero_cin() != null ){
                        setNumero_cin("");
                        setPrenom("");
                        setNom("");
                        setDate_naiss("");
                    }
                    else
                        if((String)this.getNumero_table() != null ){
                            setNumero_table("");
                            setPrenom("");
                            setNom("");
                            setDate_naiss("");
                        }
                    }   
                } 
        }
    public Row getDetailStrc(String id_parc){
        OperationBinding op_insc_annee_passee = getBindings().getOperationBinding("getDetailStrc");
        op_insc_annee_passee.getParamsMap().put("id_parc",  id_parc);
        op_insc_annee_passee.execute();
        DCIteratorBinding iter_insc_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DetailStrcDerogationROIterator");
        Row row_insc_annee_passee = iter_insc_annee_passee.getCurrentRow();
        
        return row_insc_annee_passee;
    }
}
