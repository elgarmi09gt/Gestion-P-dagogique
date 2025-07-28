package exoneration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class ExonerationBean {
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
    
    public ExonerationBean() {
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
        sessionScope.put("etudNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("etudNotNull", 1);
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
        sessionScope.put("cinNotNull", 0);
        //tableNotNull
        if(searchString != ""){
            sessionScope.put("cinNotNull", 1);
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
        sessionScope.put("tableNotNull", 0);
        sessionScope.put("etudNotNull", 0);
        //tableNotNull
        //num_etud.setValue("");
        if(searchString != ""){
            //num_etud.setValue("");
            sessionScope.put("tableNotNull", 1);
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

    @SuppressWarnings("unchecked")
    public String onValiderRecherche() {
        // Add event code here...
        Row rw = recherche();
        if(rw != null){
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeExo");
            getRowPers(rw);
            
            OperationBinding list_exo_ant = getBindings().getOperationBinding("getExonerationAnterieur");
            list_exo_ant.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
            //list_exo_ant.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_exo_ant.execute();
            
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
            list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_insc_ped.execute();
            
            DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("ListeInsPedExonerationROIterator");
                //Create RowSetIterato iterate over viewObject
            RowSetIterator rsi_insc_ped = iter_insc_ped.getViewObject().createRowSetIterator(null);


            OperationBinding getExo = getBindings().getOperationBinding("getExoneration");
            getExo.getParamsMap().put("id_insc_ped",  Long.parseLong(iter_insc_ped.getCurrentRow().getAttribute("IdInscriptionPedagogique").toString()));
            getExo.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
            getExo.execute();
            
            DCIteratorBinding iter_getExo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
            RowSetIterator rsi_getExo = iter_getExo.getViewObject().createRowSetIterator(null);
            while (rsi_getExo.hasNext()) {
                Row row_get_exo = rsi_getExo.next();
                if(iter_getExo.getCurrentRow() != null && row_get_exo.getAttribute("Taux") != null){
                    sessionScope.put("par_taux", 1);
                    sessionScope.put("par_montant", 0);
                }
                if(iter_getExo.getCurrentRow() != null && row_get_exo.getAttribute("Montant") != null){
                    sessionScope.put("par_taux", 0);
                    sessionScope.put("par_montant", 1);
                }
            }
            if(rsi_insc_ped.getRowCount() > 0 && iter_getExo.getCurrentRow() == null){
                OperationBinding op_exo = getBindings().getOperationBinding("CreateInsertExoneration");
                Object result_op_exo = op_exo.execute();
                if (!op_exo.getErrors().isEmpty()) {
                        return null;
                }
                else{
                    id_util.setInputValue(Long.parseLong(getUtilisateur()));
                }
            }
            
        }
        else{
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong("0"));
            list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_insc_ped.execute();
            
            OperationBinding list_exo_ant = getBindings().getOperationBinding("getExonerationAnterieur");
            list_exo_ant.getParamsMap().put("idpers",  Long.parseLong("0"));
            //list_exo_ant.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
            list_exo_ant.execute();
            
            OperationBinding getExo = getBindings().getOperationBinding("getExoneration");
            getExo.getParamsMap().put("id_insc_ped",  0);
            getExo.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
            getExo.execute();  
        }
        return null;
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

    @SuppressWarnings("unchecked")
    public void onChangerListenerNature(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding iter_exo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
        Row rw_exo = iter_exo.getCurrentRow();
        System.out.println(rw_exo.getAttribute("IdNatureExoneration"));
        if(rw_exo.getAttribute("IdNatureExoneration") != null && rw_exo.getAttribute("IdMotifExoneration") != null){
            if(Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) != 1 ){
                // Par Taux
                sessionScope.put("par_taux", 1);
                sessionScope.put("par_montant", 0);
                rw_exo.setAttribute("Montant",null) ;
            }
            else if(Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2 && Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) != 1 ){
                // Par Montant
                sessionScope.put("par_montant", 1);
                sessionScope.put("par_taux", 0);
                rw_exo.setAttribute("Taux",null) ;
            }
            else if(Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 ){
                // Par Taux
                sessionScope.put("par_taux", 1);
                sessionScope.put("par_montant", 1);
                rw_exo.setAttribute("Montant",null) ;
                rw_exo.setAttribute("Taux",100) ;
            }
            else if(Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2 && Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 ){
                // Par Montant
                sessionScope.put("par_montant", 1);
                sessionScope.put("par_taux", 1);
                rw_exo.setAttribute("Taux",null) ;
                rw_exo.setAttribute("Montant",25000) ;
            }
            else{
                rw_exo.setAttribute("Montant",null) ;
                rw_exo.setAttribute("Taux",null) ;
            }
            
            
        }
        else{
            sessionScope.put("par_taux", 0);
            sessionScope.put("par_montant", 0);
            
            rw_exo.setAttribute("Montant",null) ;
            rw_exo.setAttribute("Taux",null) ;
        }

    }

    @SuppressWarnings("unchecked")
    public String onValiderExo() {
        // Add event code here...
        Integer somme_ins_ped_red = 0;
        Integer somme_ins_adm_red = 0;
        Integer somme_ins_red = 0; 
        
        
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("ListeInsPedExonerationROIterator");
            //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi_insc_ped = iter_insc_ped.getViewObject().createRowSetIterator(null);
        
        //currente row
        Row rw_currente_insc_ped = iter_insc_ped.getCurrentRow();
        
        
        DCIteratorBinding iter_exo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
        Row rw_exo = iter_exo.getCurrentRow();
        RowSetIterator rsi = iter_exo.getViewObject().createRowSetIterator(null);
        
        if(rsi.getRowCount() == 0){
            FacesMessage msg = new FacesMessage(" Veuillez ajouter une exonération avant de valider ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{ 
            OperationBinding getExo = getBindings().getOperationBinding("getExoneration");
            getExo.getParamsMap().put("id_insc_ped",  Long.parseLong(iter_insc_ped.getCurrentRow().getAttribute("IdInscriptionPedagogique").toString()));
            getExo.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
            getExo.execute();
            
            DCIteratorBinding iter_getExo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
            Row rw_currente_exo = iter_getExo.getCurrentRow();
            if(rw_currente_exo == null){ 
           if(rw_exo.getAttribute("IdTypeExoneration") == null || rw_exo.getAttribute("IdNatureExoneration") == null || 
                    rw_exo.getAttribute("IdMotifExoneration") == null){               
               FacesMessage msg = new FacesMessage(" Veuillez sélectionner les champs sont obliqatoires :");
               msg.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext.getCurrentInstance().addMessage(null, msg);
               
               if(rw_exo.getAttribute("IdTypeExoneration") == null){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Le Type d'Exonération"));
               }
               if(rw_exo.getAttribute("IdNatureExoneration") == null){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> La Nature d'Exonération"));
               }
               if(rw_exo.getAttribute("IdMotifExoneration") == null){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Le Motif d'Exonération"));
               }
               
           }
            else{
                if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1 && 
                    rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Taux") != null){
                    // Exonération Etablissement (sur les droits inscriptions Ped) et par taux
                    Integer taux = Integer.parseInt(rw_exo.getAttribute("Taux").toString());
                    Integer ins = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                    Integer montant_insc_ped = ins - (ins*taux)/100;
                    if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1){    //pupille
                        Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                        Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                        Integer somme_insc = insc_ped + insc_adm;
                        Integer montant_insc_admin = somme_insc - (somme_insc*taux)/100;
                        somme_ins_adm_red = somme_ins_adm_red + (insc_adm*taux)/100;
                        somme_ins_ped_red = somme_ins_ped_red + (insc_ped*taux)/100;
                    }
                    else
                        somme_ins_ped_red = somme_ins_ped_red + (ins*taux)/100;   
                    //System.out.println("(ins*taux)/100"+(ins*taux)/100); System.out.println(" droit"+rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                }
                else{
                    // Exonération Etablissement (sur les droits inscriptions Ped) et par montant
                    if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2 && 
                        rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Montant") != null){
                        
                        Integer montant = Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                        Integer montant_insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                        
                        if((Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 )){    //pupille
                            Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                            Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                            System.out.println("montant_insc_ped"+montant_insc_ped+" montant_insc_ped"+insc_ped+" somme_insc"+insc_adm+" montant "+montant);                            
                            somme_ins_adm_red =  somme_ins_adm_red + insc_adm;
                            somme_ins_ped_red =  somme_ins_ped_red + insc_ped;
                        }
                        else{                      
                            if(Integer.parseInt(rw_exo.getAttribute("Montant").toString()) <= montant_insc_ped){
                                System.out.println("Le restant du montant "+(montant_insc_ped - montant) ); 
                                somme_ins_ped_red = somme_ins_ped_red + Integer.parseInt(rw_exo.getAttribute("Montant").toString());
               
                            }
                            else{
                                FacesMessage msg = new FacesMessage(" Le montant doit être inférieur au droit inscription pédagogique");
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                        }
                    }
                    else{
                        //rectorat
                        if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1 && 
                            rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Taux") != null){
                            // Exonération Etablissement (sur les droits inscriptions Ped) et par taux
                            Integer taux = Integer.parseInt(rw_exo.getAttribute("Taux").toString());
                            Integer ins = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                            Integer montant_insc_admin = ins - (ins*taux)/100;
                            if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1){    //pupille
                                Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                                Integer somme_insc = insc_ped + insc_adm;  
                                somme_ins_adm_red = somme_ins_adm_red + (insc_adm*taux)/100;
                                somme_ins_ped_red = somme_ins_ped_red + (insc_ped*taux)/100;
                            }
                            else
                                somme_ins_adm_red = somme_ins_adm_red + (ins*taux)/100;
            
                        }
                        else{
                            // Exonération Etablissement (sur les droits inscriptions Ped) et par montant
                            if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 2 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2 && 
                                rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Montant") != null){
                                
                                Integer montant = Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                                Integer montant_insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                
                                if((Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 )){    //pupille
                                    Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                    Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                                    //Integer somme_insc = insc_ped + insc_adm; 
                                    System.out.println("montant_insc_adm"+montant_insc_adm+" montant_insc_ped"+insc_ped+" somme_insc"+insc_adm+" montant "+montant);
                                    
                                    somme_ins_adm_red =  somme_ins_adm_red + insc_adm;
                                    somme_ins_ped_red =  somme_ins_ped_red + insc_ped;
                                }
                                else{                               
                                    if(Integer.parseInt(rw_exo.getAttribute("Montant").toString()) <= montant_insc_adm){
                                        somme_ins_adm_red = somme_ins_adm_red + Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                      
                                    }
                                    else{
                                        FacesMessage msg = new FacesMessage(" Le montant doit être inférieur au droit inscription administrative");
                                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext.getCurrentInstance().addMessage(null, msg);
                                    }
                                }
                            }
                            else{
                                //les deux
                                if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1 && 
                                    rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Taux") != null){
                                    // Exonération Etablissement (sur les droits inscriptions Ped) et par taux
                                    Integer taux = Integer.parseInt(rw_exo.getAttribute("Taux").toString());
                                    Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                    Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                                    Integer somme_insc = insc_ped + insc_adm;
                                    Integer montant_insc_admin = somme_insc - (somme_insc*taux)/100;
                                    somme_ins_adm_red = somme_ins_adm_red + (insc_adm*taux)/100;
                                    somme_ins_ped_red = somme_ins_ped_red + (insc_ped*taux)/100;

                                }
                                else{
                                    // Exonération les deux (sur les droits inscriptions Ped et Adm) et par montant
                                    if(Integer.parseInt(rw_exo.getAttribute("IdTypeExoneration").toString()) == 3 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2 && 
                                        rw_exo.getAttribute("IdMotifExoneration") != null &&  rw_exo.getAttribute("Montant") != null){
                                        
                                        Integer montant = Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                                        Integer montant_insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                        Integer montant_insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                                        Integer somme_insc = montant_insc_ped + montant_insc_adm;
                                        System.out.println("ty motif "+rw_exo.getAttribute("IdMotifExoneration") + somme_insc+" mont "+montant);
                                        if((Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 )){    //pupille
                                            Integer insc_adm = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString());
                                            Integer insc_ped = Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString());
                                            //Integer somme_insc = insc_ped + insc_adm; 
                                            System.out.println("montant_insc_adm"+montant_insc_adm+" montant_insc_ped"+montant_insc_ped+" somme_insc"+somme_insc+" montant "+montant);
                                            
                                            somme_ins_adm_red =  somme_ins_adm_red + montant_insc_adm;
                                            somme_ins_ped_red =  somme_ins_ped_red + montant_insc_ped;
                                        }
                                        else{                                           
                                            if(Integer.parseInt(rw_exo.getAttribute("Montant").toString()) <= montant_insc_adm && Integer.parseInt(rw_exo.getAttribute("Montant").toString()) <= montant_insc_ped){
                                                System.out.println("Le restant du montant "+(somme_insc - montant) );
                                                somme_ins_adm_red = somme_ins_adm_red + Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                                                somme_ins_ped_red = somme_ins_ped_red + Integer.parseInt(rw_exo.getAttribute("Montant").toString());
                                            }
                                            else{
                                                FacesMessage msg = new FacesMessage(" Le montant doit être inférieur au droit inscription administrative et pédagogique");
                                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
    
                }
                /*update*/
            
                Integer cout = (Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString()) - somme_ins_adm_red) + (Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString()) - somme_ins_ped_red) - somme_ins_red;
                
                Integer ins_ped = (Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString()) - somme_ins_ped_red);
                Integer ins_adm = (Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString()) - somme_ins_adm_red);
                System.out.println(" cout "+cout+" ins_ped"+ins_ped+" ins_adm "+ins_adm+" somme_ins_adm_red"+somme_ins_adm_red+" somme_ins_ped_red"+somme_ins_ped_red);
                if(ins_adm >= 0 && ins_ped >= 0 &&  cout >= 0){
                    OperationBinding update_insc_ped = getBindings().getOperationBinding("findAndPaieExoneration");
                    update_insc_ped.getParamsMap().put("id_insc_ped",  Long.parseLong(rw_currente_insc_ped.getAttribute("IdInscriptionPedagogique").toString()));
                    update_insc_ped.getParamsMap().put("droit_insc_adm",  Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString()) - somme_ins_adm_red);
                    update_insc_ped.getParamsMap().put("droit_insc_ped",  Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString()) - somme_ins_ped_red);
                    update_insc_ped.getParamsMap().put("cout_form",  cout);
                    update_insc_ped.execute();   
                }
                
                if(iter_exo.getCurrentRow() != null && rw_exo.getAttribute("Taux") != null){
                    sessionScope.put("par_taux", 1);
                    sessionScope.put("par_montant", 0);
                }
                if(iter_exo.getCurrentRow() != null && rw_exo.getAttribute("Montant") != null){
                    sessionScope.put("par_taux", 0);
                    sessionScope.put("par_montant", 1);
                }

            Row rw = recherche();
            if(rw != null){
                getRowPers(rw);
                OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
                list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
                list_insc_ped.getParamsMap().put("annee",  Long.parseLong(getAnne_univers()));
                list_insc_ped.execute();
            }
            if(Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdm").toString()) == 0 
               && Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPed").toString()) == 0 
               && Integer.parseInt(rw_currente_insc_ped.getAttribute("CoutFormation").toString()) == 0){
                // creer une ligne de paiement pour les pupille de la nation ou tout autre etudiant ou on exonere tout son droit d'acquitement
                AttributeBinding numero_identite = (AttributeBinding) getBindings().getControlBinding("NumeroIdentite");
                AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
                AttributeBinding num_quittance = (AttributeBinding) getBindings().getControlBinding("NumQuittance");
                AttributeBinding id_hist = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");
                AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
                AttributeBinding etab_code_parent = (AttributeBinding) getBindings().getControlBinding("EtabCodeParent");
                AttributeBinding id_pays_nationalite = (AttributeBinding) getBindings().getControlBinding("CodeNationalite");
                AttributeBinding id_insc_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedagogique");
                AttributeBinding code_quittancier = (AttributeBinding) getBindings().getControlBinding("CodeQuittancier");
                AttributeBinding id_mode_paiement = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
                AttributeBinding id_operateur = (AttributeBinding) getBindings().getControlBinding("IdOperateur");
                AttributeBinding montant_percu = (AttributeBinding) getBindings().getControlBinding("MontantPercu");
                AttributeBinding id_niv_parc = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
                AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreePaieEtud");
                AttributeBinding montant = (AttributeBinding) getBindings().getControlBinding("Montant");
                AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("Valider");
                
                OperationBinding op_exo = getBindings().getOperationBinding("CreateInsertPaieEtudiant");
                Object result_op_exo = op_exo.execute();
                if (!op_exo.getErrors().isEmpty()) {
                        return null;
                }
                else{
                    numero_identite.setInputValue(rw_currente_insc_ped.getAttribute("PieceIdentification").toString());
                    id_annee.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdAnneesUnivers").toString()));
                    num_quittance.setInputValue(0);        //rw_currente_insc_ped.getAttribute("Quittance")
                    id_hist.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdHistoriquesStructure").toString()));
                    id_etud.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdEtudiant").toString()));
                    etab_code_parent.setInputValue("p");
                    id_pays_nationalite.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdPaysNationalite").toString()));
                    id_insc_ped.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdInscriptionPedagogique").toString()));
                    code_quittancier.setInputValue(0);  //rw_currente_insc_ped.getAttribute("Quittance")
                    id_mode_paiement.setInputValue(1);    //rw_currente_insc_ped.getAttribute("IdModePaiement")
                    id_operateur.setInputValue(rw_currente_insc_ped.getAttribute("IdOperateur"));
                    montant_percu.setInputValue(0);
                    montant.setInputValue(0);
                    valide.setInputValue(1);
                    id_niv_parc.setInputValue(Long.parseLong(rw_currente_insc_ped.getAttribute("IdNiveauFormationParcours").toString()));
                    id_util.setInputValue(Long.parseLong(getUtilisateur()));
                    
                    OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                    Object result_op_commit = op_commit.execute();
                    if (!op_commit.getErrors().isEmpty()) {
                            return null;
                    }
                    else{
                        System.out.println(" reussi");
                    }
                }
            }
        }else
            System.out.println("non mod");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String onAjouterNewExo() {
        // Add event code here...
        sessionScope.put("par_taux", 0);
        sessionScope.put("par_montant", 0);
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("ListeInsPedExonerationROIterator");
            //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi_insc_ped = iter_insc_ped.getViewObject().createRowSetIterator(null);
        if(iter_insc_ped.getCurrentRow().getAttribute("CoutFormation") != null && Integer.parseInt(iter_insc_ped.getCurrentRow().getAttribute("CoutFormation").toString()) > 0){
            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeExo");
            OperationBinding op_exo = getBindings().getOperationBinding("CreateInsertExoneration");
            Object result_op_exo = op_exo.execute();
            if (!op_exo.getErrors().isEmpty()) {
                    return null;
            }
            else{
                id_util.setInputValue(Long.parseLong(getUtilisateur()));
            }
        }
        
        return null;
    }

    @SuppressWarnings("unchecked")
    public void onChangerListenerMotif(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
        
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("ListeInsPedExonerationROIterator");
        //currente row
        Row rw_currente_insc_ped = iter_insc_ped.getCurrentRow();
        
        DCIteratorBinding iter_exo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
        Row rw_exo = iter_exo.getCurrentRow();
        System.out.println(rw_exo.getAttribute("IdMotifExoneration"));
        if(rw_exo.getAttribute("IdMotifExoneration") != null && rw_exo.getAttribute("IdNatureExoneration") != null){
            if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1){
                // Par Taux
                sessionScope.put("par_taux", 1);
                sessionScope.put("par_montant", 1);
                rw_exo.setAttribute("Taux",100) ;
            }
            else if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) == 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2){
                // Par Montant
                sessionScope.put("par_montant", 1);
                sessionScope.put("par_taux", 1);
                rw_exo.setAttribute("Montant",(Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsAdmPays").toString()) + Integer.parseInt(rw_currente_insc_ped.getAttribute("DroitInsPedPays").toString()))) ;
            }
            else
            if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) != 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 1){
                // Par Taux
                sessionScope.put("par_taux", 1);
                sessionScope.put("par_montant", 0);
                rw_exo.setAttribute("Taux",null) ;
            }
            else if(Integer.parseInt(rw_exo.getAttribute("IdMotifExoneration").toString()) != 1 && Integer.parseInt(rw_exo.getAttribute("IdNatureExoneration").toString()) == 2){
                // Par Montant
                sessionScope.put("par_montant", 1);
                sessionScope.put("par_taux", 0);
                rw_exo.setAttribute("Montant",null) ;
            }
            
            else{
                rw_exo.setAttribute("Montant",null) ;
                rw_exo.setAttribute("Taux",null) ;
                //sessionScope.put("par_taux", 0);
                //sessionScope.put("par_montant", 0);
            }
        }
        else{
            sessionScope.put("par_taux", 0);
            sessionScope.put("par_montant", 0);
            
            rw_exo.setAttribute("Montant",null) ;
            rw_exo.setAttribute("Taux",null) ;
        }
        
    }

    @SuppressWarnings("unchecked")
    public void handleTableClick(ClientEvent clientEvent) {
        // Add event code here...

        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeExo");

        DCIteratorBinding iter_insc_ped = (DCIteratorBinding)getBindings().get("ListeInsPedExonerationROIterator");
        RowSetIterator rsi_insc_ped = iter_insc_ped.getViewObject().createRowSetIterator(null);
        
        OperationBinding getExo = getBindings().getOperationBinding("getExoneration");
        getExo.getParamsMap().put("id_insc_ped",  Long.parseLong(iter_insc_ped.getCurrentRow().getAttribute("IdInscriptionPedagogique").toString()));
        getExo.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        getExo.execute();
        
        DCIteratorBinding iter_getExo = (DCIteratorBinding)getBindings().get("ExonerationIterator");
        RowSetIterator rsi_getExo = iter_getExo.getViewObject().createRowSetIterator(null);
        while (rsi_getExo.hasNext()) {
            Row row_get_exo = rsi_getExo.next();
            if(iter_getExo.getCurrentRow() != null && row_get_exo.getAttribute("Taux") != null){
                sessionScope.put("par_taux", 1);
                sessionScope.put("par_montant", 0);
            }
            if(iter_getExo.getCurrentRow() != null && row_get_exo.getAttribute("Montant") != null){
                sessionScope.put("par_taux", 0);
                sessionScope.put("par_montant", 1);
            }
        }
        if(rsi_insc_ped.getRowCount() > 0 && iter_getExo.getCurrentRow() == null){
            OperationBinding op_exo = getBindings().getOperationBinding("CreateInsertExoneration");
            Object result_op_exo = op_exo.execute();
            if (!op_exo.getErrors().isEmpty()) {
                    return ;
            }
            else{
                id_util.setInputValue(Long.parseLong(getUtilisateur()));
            }
        }
    }
}
