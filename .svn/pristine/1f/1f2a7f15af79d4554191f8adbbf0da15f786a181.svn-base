package modzlite_paiement;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import model.services.ecolageAppImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class ModalitePaiementBean {
    private String duree_form; 
    private String annee_fin; 
    private String lib_form;
    private String abr_form;
    private String cout_form;
    private String insc_ped;
    private String insc_adm;
    private String cout_form_etr;
    private String insc_ped_etr;
    private String insc_adm_etr;
    private String mensualite;
    private String mensualite_etr;

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private RichPopup pop;
    private RichPopup pop_obl;
    private RichPopup pop_del;

    public ModalitePaiementBean() {
    }

    public void setMensualite_etr(String mensualite_etr) {
        this.mensualite_etr = mensualite_etr;
    }

    public String getMensualite_etr() {
        return mensualite_etr;
    }

    public void setAbr_form(String abr_form) {
        this.abr_form = abr_form;
    }

    public String getAbr_form() {
        return abr_form;
    }

    public void setCout_form(String cout_form) {
        this.cout_form = cout_form;
    }

    public String getCout_form() {
        return cout_form;
    }

    public void setInsc_ped(String insc_ped) {
        this.insc_ped = insc_ped;
    }

    public String getInsc_ped() {
        return insc_ped;
    }

    public void setInsc_adm(String insc_adm) {
        this.insc_adm = insc_adm;
    }

    public String getInsc_adm() {
        return insc_adm;
    }

    public void setCout_form_etr(String cout_form_etr) {
        this.cout_form_etr = cout_form_etr;
    }

    public String getCout_form_etr() {
        return cout_form_etr;
    }

    public void setInsc_ped_etr(String insc_ped_etr) {
        this.insc_ped_etr = insc_ped_etr;
    }

    public String getInsc_ped_etr() {
        return insc_ped_etr;
    }

    public void setInsc_adm_etr(String insc_adm_etr) {
        this.insc_adm_etr = insc_adm_etr;
    }

    public String getInsc_adm_etr() {
        return insc_adm_etr;
    }

    public void setMensualite(String mensualite) {
        this.mensualite = mensualite;
    }

    public String getMensualite() {
        return mensualite;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setLib_form(String lib_form) {
        this.lib_form = lib_form;
    }

    public String getLib_form() {
        return lib_form;
    }

    public void setDuree_form(String duree_form) {
        this.duree_form = duree_form;
    }

    public String getDuree_form() {
        return duree_form;
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setAnnee_fin(String annee_fin) {
        this.annee_fin = annee_fin;
    }

    public String getAnnee_fin() {
        return annee_fin;
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
    public void refreshAnnee() {
        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");     
        ViewObject vo_liste_prov_etud = am.getAttrAnneeDebutRO();
        vo_liste_prov_etud.reset();
        
        OperationBinding op_annee_en_cours = getBindings().getOperationBinding("getAnneeEnCours");
        op_annee_en_cours.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
        op_annee_en_cours.execute();
        DCIteratorBinding iter_annee_en_cours = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AnneeUniversEnCoursROIterator");
        Row row_set_annee_en_cours = iter_annee_en_cours.getCurrentRow();
        if(row_set_annee_en_cours != null) {
            String annee_cours = row_set_annee_en_cours.getAttribute("LibelleLong").toString();	
            String [] tab_annee_cours = annee_cours.split("-");
            
            String [] tab_ord = new String[2];;
            tab_ord[0] = tab_annee_cours[1];
            tab_ord[1] = tab_annee_cours[0];
            
            for(int i=0 ; i< tab_ord.length ; i++){
                Row r = vo_liste_prov_etud.createRow();
                r.setAttribute("LibAnnee",tab_ord[i]);
                vo_liste_prov_etud.insertRow(r);
            }
            
            setAnnee_fin(tab_annee_cours[1]);
            
            if(sessionScope.get("val_cout_form") != null)
                setCout_form(sessionScope.get("val_cout_form").toString());
            
            if(sessionScope.get("val_cout_form_etr") != null)
                setCout_form_etr(sessionScope.get("val_cout_form_etr").toString());
        }
        
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void onChangeDuree(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");

        AttributeBinding mois = (AttributeBinding) getBindings().getControlBinding("Mois1");

        setDuree_form(searchString);
        if(mois.getInputValue() != null){
            Integer id_mois = Integer.parseInt( mois.getInputValue().toString());
            if(searchString == ""){
                sessionScope.remove("val_duree");
                OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                getMoisFin.getParamsMap().put("id_mois_fin", null);
                getMoisFin.execute();
                // s&n&galais
                if((sessionScope.get("val_insc_ped") == null)){
                    sessionScope.put("val_cout_form","0");
                }
                else if((sessionScope.get("val_insc_ped") != null)){
                    sessionScope.put("val_cout_form",sessionScope.get("val_insc_ped"));
                }
                setCout_form(sessionScope.get("val_cout_form").toString());
                // étrangers
                if((sessionScope.get("val_insc_ped_etr") == null)){
                    sessionScope.put("val_cout_form_etr","0");
                }
                else if((sessionScope.get("val_insc_ped_etr") != null)){
                    sessionScope.put("val_cout_form_etr",sessionScope.get("val_insc_ped_etr"));
                }
                setCout_form_etr(sessionScope.get("val_cout_form_etr").toString());
            }
            else{
                if(is_int(searchString)){
                    sessionScope.put("val_duree", searchString);
                    if((Integer.parseInt(searchString) + id_mois - 1) > 12 ){
                        Integer diff_mois = (Integer.parseInt(searchString) + id_mois - 1) - 12;
                        //getMoisFin
                        OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                        getMoisFin.getParamsMap().put("id_mois_fin", diff_mois);
                        getMoisFin.execute();
                    }
                    else{
                        if((Integer.parseInt(searchString) + id_mois - 1) <= 12 ){
                            Integer add_mois = (Integer.parseInt(searchString) + id_mois -1) ;
                            //getMoisFin
                            OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                            getMoisFin.getParamsMap().put("id_mois_fin", add_mois);
                            getMoisFin.execute();
                        }
                    }
                    if((sessionScope.get("val_insc_ped") == null)&& (sessionScope.get("val_mens") == null)){
                        sessionScope.put("val_cout_form","0");
                    }
                    else if((sessionScope.get("val_insc_ped") != null)&& (sessionScope.get("val_mens") == null)){
                        sessionScope.put("val_cout_form",sessionScope.get("val_insc_ped"));
                    }
                    else if((sessionScope.get("val_insc_ped") != null)&& (sessionScope.get("val_mens") != null)){
                        String cout = ((Integer.parseInt(sessionScope.get("val_mens").toString()) * Integer.parseInt(searchString)) + Integer.parseInt(sessionScope.get("val_insc_ped").toString()))+"";
                        sessionScope.put("val_cout_form",cout);
                    }
                    setCout_form(sessionScope.get("val_cout_form").toString());
                    
                    //étrangers
                    if((sessionScope.get("val_insc_ped_etr") == null)&& (sessionScope.get("val_mens_etr") == null)){
                        sessionScope.put("val_cout_form_etr","0");
                    }
                    else if((sessionScope.get("val_insc_ped_etr") != null)&& (sessionScope.get("val_mens_etr") == null)){
                        sessionScope.put("val_cout_form_etr",sessionScope.get("val_insc_ped_etr"));
                    }
                    else if((sessionScope.get("val_insc_ped_etr") != null)&& (sessionScope.get("val_mens_etr") != null)){
                        String cout = ((Integer.parseInt(sessionScope.get("val_mens_etr").toString()) * Integer.parseInt(searchString)) + Integer.parseInt(sessionScope.get("val_insc_ped_etr").toString()))+"";
                        sessionScope.put("val_cout_form_etr",cout);
                    }
                    setCout_form_etr(sessionScope.get("val_cout_form_etr").toString());
                }
                else
                    sessionScope.remove("val_duree");
            }
        }

    }
    public Boolean is_int(String str) {
        boolean isNumeric = true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                isNumeric = false;
            }
        }
        return isNumeric;
    }
   
    public void onChangeMoisDebut(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        UIComponent c = valueChangeEvent.getComponent();
        //This step actually invokes Update Model phase for this component
        c.processUpdates(FacesContext.getCurrentInstance());
        //Jump to the Render Response phase in order to avoid the validation
        FacesContext.getCurrentInstance().renderResponse();

        AttributeBinding mois = (AttributeBinding) getBindings().getControlBinding("Mois1");

        if(mois.getInputValue() != null){
            Integer id_mois = Integer.parseInt( mois.getInputValue().toString());
            if(sessionScope.get("val_duree") == null){
                System.out.println(" duree mois "+sessionScope.get("val_duree"));
                OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                getMoisFin.getParamsMap().put("id_mois_fin", null);
                getMoisFin.execute();
            }
            else{
                if(is_int(sessionScope.get("val_duree").toString())){
                    if((Integer.parseInt(sessionScope.get("val_duree").toString()) + id_mois -1) > 12 ){
                        Integer diff_mois = (Integer.parseInt(sessionScope.get("val_duree").toString()) + id_mois - 1) - 12;
                        //getMoisFin
                        OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                        getMoisFin.getParamsMap().put("id_mois_fin", diff_mois);
                        getMoisFin.execute();
                    }
                    else{
                        if((Integer.parseInt(sessionScope.get("val_duree").toString()) + id_mois - 1) <= 12 ){
                            Integer add_mois = (Integer.parseInt(sessionScope.get("val_duree").toString()) + id_mois - 1) ;
                            //getMoisFin
                            OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                            getMoisFin.getParamsMap().put("id_mois_fin", add_mois);
                            getMoisFin.execute();
                        }
                    }
                }
                else
                    sessionScope.remove("val_duree");
            }
        }

    }

    @SuppressWarnings("unchecked")
    public String onValiderModPaiie() {
        // Add event code here...
        AttributeBinding id_mois = (AttributeBinding) getBindings().getControlBinding("IdMois");
        AttributeBinding id_mois_fin = (AttributeBinding) getBindings().getControlBinding("IdMoisFin");
        AttributeBinding mois = (AttributeBinding) getBindings().getControlBinding("MoisRO");
        
        AttributeBinding annee_deb = (AttributeBinding) getBindings().getControlBinding("AttrAnneeDebutRO");
        
        System.out.println("lib form "+getLib_form());
        System.out.println("lib abrev "+getAbr_form());
        System.out.println("cout form "+getCout_form());
        System.out.println("insc adm "+getInsc_adm());
        System.out.println("insc ped "+getInsc_ped());
        System.out.println("cout form etr "+getCout_form_etr());
        System.out.println("insc adm etr "+getInsc_adm_etr());
        System.out.println("insc ped etr "+getInsc_ped_etr());
        System.out.println("mens "+getMensualite());
        System.out.println("mois deb "+id_mois.getInputValue());
        //System.out.println("lib "+getLib_form());
        
        System.out.println("mois fin "+id_mois_fin.getInputValue());
        System.out.println("année fin "+getAnnee_fin());
        System.out.println("lib "+annee_deb.getInputValue());
        System.out.println("duree form "+getDuree_form());

        AttributeBinding id_formation = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        AttributeBinding id_niv_formation = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");  
        
        if(id_formation != null && id_niv_formation != null){
            AttributeBinding mois_deb = (AttributeBinding) getBindings().getControlBinding("Mois1");
            System.out.println("mois1mois1"+mois_deb.getInputValue());
            if(getCout_form() == null || getCout_form_etr() == null || getMensualite() == null || getDuree_form() == null || mois_deb.getInputValue() == null || getMensualite_etr() == null ){
                if(getCout_form() == null ){
                    sessionScope.put("cout_form_obli", "Le coût de la Formation est obligatoire");
                }
                else{
                    sessionScope.put("cout_form_obli", "");
                }
                if(getCout_form_etr() == null){
                        sessionScope.put("cout_form_etr_obli", "Le coût de la Formation pour les étrangers est obligatoire");
                }
                else{
                        sessionScope.put("cout_form_etr_obli", "");
                }
                if(getMensualite() == null){
                    sessionScope.put("var_mensualite", "La Mensualité est obligatoire ");
                }
                else{
                    sessionScope.put("var_mensualite", "");
                }
                
                if(getMensualite_etr() == null){
                    sessionScope.put("var_mensualite_etr", "La Mensualité étrangère est obligatoire ");
                }
                else{
                    sessionScope.put("var_mensualite_etr", "");
                }
                
                if(mois_deb.getInputValue() == null){
                    sessionScope.put("var_mois_deb", "Le mois de début est obligatoire");
                }
                else{
                    sessionScope.put("var_mois_deb", "");
                }
                
                if(getDuree_form() == null){
                    sessionScope.put("var_duree_form", "La durée de la Formation est obligatoire");
                }
                else{
                    sessionScope.put("var_duree_form", "");
                }
                sessionScope.put("var_form_mod_cours", "");
                /*if(FormModEnCours() != null){
                    //Libform LibAnneeUnivers
                    AttributeBinding lib_form = (AttributeBinding) getBindings().getControlBinding("Libform");
                    AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnneeUnivers");
                    sessionScope.put("var_form_mod_cours", "Les modalités de paiement sont déjà définies pour ( "+lib_form.getInputValue()+" ) de l'année universitaire en cours ( "+lib_annee.getInputValue()+" )");
                }
                else{
                    sessionScope.put("var_form_mod_cours", "");
                }*/
                
                RichPopup popup = this.getPop_obl();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else{
                if(sessionScope.get("id_form_mod_sess") == null && sessionScope.get("id_mod_sess") == null){
                    if(FormModEnCours() == null){    
                        AttributeBinding lib_mod = (AttributeBinding) getBindings().getControlBinding("LibelleLongMod");
                        AttributeBinding lib_court_mod = (AttributeBinding) getBindings().getControlBinding("LibelleCourtMod");                 
                        
                        AttributeBinding cout_form = (AttributeBinding) getBindings().getControlBinding("CoutFormationMod");
                        AttributeBinding dr_insc_ped_mod = (AttributeBinding) getBindings().getControlBinding("DroitInsPedMod");  
                        
                        AttributeBinding dr_insc_adm_mod = (AttributeBinding) getBindings().getControlBinding("DroitInsAdmMod");
                        AttributeBinding cout_form_etr = (AttributeBinding) getBindings().getControlBinding("CoutFormationEtrMod");  
                        
                        AttributeBinding dr_insc_adm_etr_mod = (AttributeBinding) getBindings().getControlBinding("DroitInsAdmEtrMod");
                        AttributeBinding dr_insc_ped_etr_mod = (AttributeBinding) getBindings().getControlBinding("DroitInsPedEtrMod");  
                        
                        AttributeBinding mensuel = (AttributeBinding) getBindings().getControlBinding("MensualiteMod");
                        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeMod");                 
                        AttributeBinding mensuel_etr = (AttributeBinding) getBindings().getControlBinding("MensualiteEtr");  
                        System.out.println("mlmoml");
                        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertModalites");
                        Object result = operationBinding.execute();
                        if (!operationBinding.getErrors().isEmpty()) {
                            return null;
                        }
                        else{
                            lib_mod.setInputValue(getLib_form());
                            lib_court_mod.setInputValue(getAbr_form());
                            cout_form.setInputValue(getCout_form());
                            dr_insc_ped_mod.setInputValue(getInsc_ped());
                            dr_insc_adm_mod.setInputValue(getInsc_adm());
                            cout_form_etr.setInputValue(getCout_form_etr());
                            dr_insc_adm_etr_mod.setInputValue(getInsc_adm_etr());
                            dr_insc_ped_etr_mod.setInputValue(getInsc_ped_etr()); 
                            mensuel.setInputValue(getMensualite()); 
                            mensuel_etr.setInputValue(getMensualite_etr());
                            id_util.setInputValue(getUtilisateur());
        
                            
                            OperationBinding op_pers_commit = getBindings().getOperationBinding("Commit");
                            Object result_pers_commit = op_pers_commit.execute();
                            if (!op_pers_commit.getErrors().isEmpty()) {
                                return null;
                            }
                            
                            else{
                                AttributeBinding id_modalite_mod = (AttributeBinding) getBindings().getControlBinding("IdModaliteMod");
    
                                AttributeBinding id_mod_form_mod = (AttributeBinding) getBindings().getControlBinding("IdModaliteFormMod");
                                AttributeBinding id_form_form_mod = (AttributeBinding) getBindings().getControlBinding("IdFormationFormMod");                 
                                
                                AttributeBinding id_niv_form_form = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormFormMod");
                                AttributeBinding annee_form_mod = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversFormMod");  
                                
                                AttributeBinding mois_deb_mod = (AttributeBinding) getBindings().getControlBinding("IdMoisDebutFormMod");
                                AttributeBinding annee_deb_form = (AttributeBinding) getBindings().getControlBinding("AnneeDebutFormMod");  
                                
                                AttributeBinding duree_form_mod = (AttributeBinding) getBindings().getControlBinding("DureeFormMod");
                                AttributeBinding mois_fin_mod = (AttributeBinding) getBindings().getControlBinding("IdMoisFinFormMod"); 
                                AttributeBinding annee_fin_mod = (AttributeBinding) getBindings().getControlBinding("AnneeFinFormMod");
                                AttributeBinding util_form_mod = (AttributeBinding) getBindings().getControlBinding("UtiCreeFormMod"); 
                                
                                OperationBinding op_form_mod = getBindings().getOperationBinding("CreateInsertFormMod");
                                Object result_op_form_mod = op_form_mod.execute();
                                if (!op_form_mod.getErrors().isEmpty()) {
                                    return null;
                                }
                                else{
                                    
                                    id_mod_form_mod.setInputValue(id_modalite_mod.getInputValue());
                                    id_form_form_mod.setInputValue(id_formation.getInputValue());
                                    id_niv_form_form.setInputValue(id_niv_formation.getInputValue());
                                    annee_form_mod.setInputValue(getAnne_univers());
                                    mois_deb_mod.setInputValue(mois_deb.getInputValue());
                                    annee_deb_form.setInputValue(annee_deb.getInputValue());
                                    duree_form_mod.setInputValue(getDuree_form());
                                    mois_fin_mod.setInputValue(id_mois_fin.getInputValue()); 
                                    annee_fin_mod.setInputValue(getAnnee_fin()); 
                                    util_form_mod.setInputValue(getUtilisateur());
        
                                    
                                    OperationBinding op_com_form = getBindings().getOperationBinding("Commit");
                                    Object result_op_com_form = op_com_form.execute();
                                    if (!op_com_form.getErrors().isEmpty()) {
                                        return null;
                                    }
                                    
                                    else{
                                        AttributeBinding id_form_mod = (AttributeBinding) getBindings().getControlBinding("IdFormationModalite");
                                        
                                        for(int i=0 ; i <= Integer.parseInt(getDuree_form()); i++){
                                            if(i == 0){
                                                Integer mont = 0;
                                                Integer mont_etr = 0;
                                                if(getInsc_ped() != "")
                                                    mont = Integer.parseInt(getInsc_ped());
                                                if(getInsc_ped_etr() != "")
                                                    mont_etr = Integer.parseInt(getInsc_ped_etr());
                                                insert_echo_paiement_insp(id_niv_formation.getInputValue().toString(), "DIP", i, mont+"", mont_etr+"", 1, id_form_mod.getInputValue().toString());
                                            }
                                            else{
                                            
                                                Integer j = i - 1;
                                                Integer id_mois_deb ;
                                                Integer mois_debut = (Integer.parseInt(mois_deb.getInputValue().toString()) + j);
                                                if(mois_debut > 12)
                                                    id_mois_deb = mois_debut - 12;
                                                else
                                                    id_mois_deb = mois_debut;
                                                Row row_mois =  getLibMois(id_mois_deb+"");
                                                
                                                insert_echo_paiement(id_niv_formation.getInputValue().toString(),row_mois.getAttribute("LibMois").toString(), i, getMensualite(), getMensualite_etr(), 0, id_form_mod.getInputValue().toString(),id_mois_deb);


                                            }
                                        }
                                        
                                        
                                        setLib_form("");
                                        setAbr_form("");
                                        setCout_form("");
                                        setInsc_adm("");
                                        setInsc_ped("");
                                        setCout_form_etr("");
                                        setInsc_adm_etr("");
                                        setInsc_ped_etr("");
                                        setMensualite("");
                                        setMensualite_etr("");
                                        setAnnee_fin("");
                                        setDuree_form("");
                                        mois_deb.setInputValue("");
                                        OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                                        getMoisFin.getParamsMap().put("id_mois_fin", 0);
                                        getMoisFin.execute();
                                        refreshNivFormMod();
                                    }
                                    
                                }
                                
                            }
                        
                        }
                    }
                    else{
                        sessionScope.put("cout_form_obli", "");
                        sessionScope.put("cout_form_etr_obli", "");
                        sessionScope.put("var_mensualite", "");
                        sessionScope.put("var_mois_deb", "");
                        sessionScope.put("var_duree_form", "");
                        
                        AttributeBinding lib_form = (AttributeBinding) getBindings().getControlBinding("Libform");
                        AttributeBinding lib_annee = (AttributeBinding) getBindings().getControlBinding("LibAnneeUnivers");
                        sessionScope.put("var_form_mod_cours", "Les modalités de paiement sont déjà définies pour ( "+lib_form.getInputValue()+" ) de l'année universitaire en cours ( "+lib_annee.getInputValue()+" )");
                        RichPopup popup = this.getPop_obl();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{

                    OperationBinding op_update_mod = getBindings().getOperationBinding("findAndUpdateModlite");
                    op_update_mod.getParamsMap().put("id_mod",  sessionScope.get("id_mod_sess"));
                    op_update_mod.getParamsMap().put("lib_mod",  getLib_form());
                    op_update_mod.getParamsMap().put("lib_court_mod",  getAbr_form());
                    op_update_mod.getParamsMap().put("cout_form",  getCout_form());
                    op_update_mod.getParamsMap().put("dr_insc_ped_mod",  getInsc_ped());
                    op_update_mod.getParamsMap().put("dr_insc_adm_mod",  getInsc_adm());
                    op_update_mod.getParamsMap().put("cout_form_etr",  getCout_form_etr());
                    op_update_mod.getParamsMap().put("dr_insc_adm_etr_mod",  getInsc_adm_etr());
                    op_update_mod.getParamsMap().put("dr_insc_ped_etr_mod",  getInsc_ped_etr());
                    op_update_mod.getParamsMap().put("mensuel",  getMensualite());
                    op_update_mod.getParamsMap().put("mensuel_etr",  getMensualite_etr());
                    op_update_mod.getParamsMap().put("util",  getUtilisateur());
                    op_update_mod.execute();
                  
                    OperationBinding op_update_form_mod = getBindings().getOperationBinding("findAndUpdateFormModlite");
                    op_update_form_mod.getParamsMap().put("id_form_mod",  sessionScope.get("id_form_mod_sess"));
                    op_update_form_mod.getParamsMap().put("mois_deb",  mois_deb.getInputValue());
                    op_update_form_mod.getParamsMap().put("annee_deb",  annee_deb.getInputValue());
                    op_update_form_mod.getParamsMap().put("duree",  getDuree_form());
                    op_update_form_mod.getParamsMap().put("mois_fin",  id_mois_fin.getInputValue());
                    op_update_form_mod.getParamsMap().put("annee_fin",  getAnnee_fin());
                    op_update_form_mod.getParamsMap().put("util",  getUtilisateur());
                    op_update_form_mod.execute();
                    
                    setLib_form("");
                    setAbr_form("");
                    setCout_form("");
                    setInsc_adm("");
                    setInsc_ped("");
                    setCout_form_etr("");
                    setInsc_adm_etr("");
                    setInsc_ped_etr("");
                    setMensualite("");
                    setMensualite_etr("");
                    setAnnee_fin("");
                    setDuree_form("");
                    mois_deb.setInputValue("");
                    OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
                    getMoisFin.getParamsMap().put("id_mois_fin", 0);
                    getMoisFin.execute();
                    
                    sessionScope.put("id_form_mod_sess", null);
                    sessionScope.put("id_mod_sess", null);
                    refreshNivFormMod();
                }
            }                                                                                   
        }
        else{
            if(id_formation != null){
                
                sessionScope.put("erreur_form", "Le Niveau de Formation ");
                RichPopup popup = this.getPop();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            else{
                sessionScope.put("erreur_form", "La Formation ");
                RichPopup popup = this.getPop();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        
        
        return null;
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void onDialogCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop().hide();
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        this.getPop().hide();
    }

    public void setPop_obl(RichPopup pop_obl) {
        this.pop_obl = pop_obl;
    }

    public RichPopup getPop_obl() {
        return pop_obl;
    }

    public void onDialogCanObli(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop_obl().hide();
    }

    public void onDialogObli(DialogEvent dialogEvent) {
        // Add event code here...
        this.getPop_obl().hide();
    }

    public String onEditerModalite() {
        // Add event code here...
        
        DCIteratorBinding iter_niv_form_mod = (DCIteratorBinding) getBindings().get("NivFormPayanteModaliteROIterator");
        Row row_niv_form_mod = iter_niv_form_mod.getCurrentRow();
        System.out.println("id mod form "+row_niv_form_mod.getAttribute("IdFormationModalite"));
        System.out.println("id mod "+row_niv_form_mod.getAttribute("IdModalite"));//IdModalite
        System.out.println("LibelleLong "+row_niv_form_mod.getAttribute("LibelleLong"));
        
        System.out.println("Mensualite "+row_niv_form_mod.getAttribute("Mensualite"));
        System.out.println("CoutFormation "+row_niv_form_mod.getAttribute("CoutFormation"));//IdModalite
        System.out.println("CoutFormationEtr "+row_niv_form_mod.getAttribute("CoutFormationEtr"));
        System.out.println("AnneeFin "+row_niv_form_mod.getAttribute("AnneeFin"));
        System.out.println("Duree "+row_niv_form_mod.getAttribute("Duree"));//IdModalite
        System.out.println("AnneeDebut "+row_niv_form_mod.getAttribute("AnneeDebut"));
        System.out.println("IdMoisDebut "+row_niv_form_mod.getAttribute("IdMoisDebut"));
        System.out.println("IdMoisFin "+row_niv_form_mod.getAttribute("IdMoisFin"));//IdModalite
        System.out.println("DroitInsAdm "+row_niv_form_mod.getAttribute("DroitInsAdm"));
        System.out.println("DroitInsAdmEtr "+row_niv_form_mod.getAttribute("DroitInsAdmEtr"));
        System.out.println("DroitInsPed "+row_niv_form_mod.getAttribute("DroitInsPed"));//IdModalite
        System.out.println("DroitInsPedEtr "+row_niv_form_mod.getAttribute("DroitInsPedEtr"));
        System.out.println("LibelleCourt "+row_niv_form_mod.getAttribute("LibelleCourt"));
        
        if(row_niv_form_mod.getAttribute("LibelleLong") != null)
            setLib_form(row_niv_form_mod.getAttribute("LibelleLong").toString());
        else
            setLib_form("");

        if(row_niv_form_mod.getAttribute("LibelleCourt") != null)
            setAbr_form(row_niv_form_mod.getAttribute("LibelleCourt").toString());
        else
            setAbr_form("");    
        
        if(row_niv_form_mod.getAttribute("CoutFormation") != null)
            setCout_form(row_niv_form_mod.getAttribute("CoutFormation").toString());
        else
            setCout_form("");
        
        if(row_niv_form_mod.getAttribute("DroitInsAdm") != null)
            setInsc_adm(row_niv_form_mod.getAttribute("DroitInsAdm").toString());
        else
            setInsc_adm(""); 
        
        if(row_niv_form_mod.getAttribute("DroitInsPed") != null)
            setInsc_ped(row_niv_form_mod.getAttribute("DroitInsPed").toString());
        else
            setInsc_ped("");
        if(row_niv_form_mod.getAttribute("CoutFormationEtr") != null)
            setCout_form_etr(row_niv_form_mod.getAttribute("CoutFormationEtr").toString());
        else
            setCout_form_etr("");
        if(row_niv_form_mod.getAttribute("DroitInsAdmEtr") != null)
            setInsc_adm_etr(row_niv_form_mod.getAttribute("DroitInsAdmEtr").toString());
        else
            setInsc_adm_etr("");
        if(row_niv_form_mod.getAttribute("DroitInsPedEtr") != null)
            setInsc_ped_etr(row_niv_form_mod.getAttribute("DroitInsPedEtr").toString());
        else
            setInsc_ped_etr("");
        if(row_niv_form_mod.getAttribute("Mensualite") != null)
            setMensualite(row_niv_form_mod.getAttribute("Mensualite").toString());
        else
            setMensualite("");
        
        if(row_niv_form_mod.getAttribute("MensualiteEtr") != null)
            setMensualite_etr(row_niv_form_mod.getAttribute("MensualiteEtr").toString());
        else
            setMensualite_etr("");
        
        if(row_niv_form_mod.getAttribute("AnneeFin") != null)
            setAnnee_fin(row_niv_form_mod.getAttribute("AnneeFin").toString());
        else
            setAnnee_fin("");
        if(row_niv_form_mod.getAttribute("Duree") != null){
            setDuree_form(row_niv_form_mod.getAttribute("Duree").toString());
            sessionScope.put("val_duree",row_niv_form_mod.getAttribute("Duree"));
        }
        else
            setDuree_form("");

        //row_niv_form_mod.getAttribute("IdMoisFin")
        if(row_niv_form_mod.getAttribute("IdMoisFin") != null){
            OperationBinding getMoisFin = getBindings().getOperationBinding("getMoisFin");                    
            getMoisFin.getParamsMap().put("id_mois_fin", row_niv_form_mod.getAttribute("IdMoisFin"));
            getMoisFin.execute();
        }
        
        //getMoisCurent
        System.out.println("mois mois deb "+row_niv_form_mod.getAttribute("IdMoisDebut"));
        System.out.println("lib form "+getLib_form());
        System.out.println("lib abrev "+getAbr_form());
        System.out.println("cout form "+getCout_form());
        System.out.println("insc adm "+getInsc_adm());
        System.out.println("insc ped "+getInsc_ped());
        System.out.println("cout form etr "+getCout_form_etr());
        System.out.println("insc adm etr "+getInsc_adm_etr());
        System.out.println("insc ped etr "+getInsc_ped_etr());
        System.out.println("mens "+getMensualite());
        //System.out.println("mois deb "+id_mois.getInputValue());       
        
        //System.out.println("mois fin "+id_mois_fin.getInputValue());
        System.out.println("année fin "+getAnnee_fin());
        //System.out.println("lib "+annee_deb.getInputValue());
        System.out.println("duree form "+getDuree_form());        

        ecolageAppImpl am = (ecolageAppImpl)resolvElDC("ecolageAppDataControl");     
        ViewObject vo_mois_annee = am.getAttrMoisAnnDebRO();

        Row r = vo_mois_annee.createRow();
        r.setAttribute("Mois",row_niv_form_mod.getAttribute("IdMoisDebut"));
        vo_mois_annee.insertRow(r);

        System.out.println("id mod form "+row_niv_form_mod.getAttribute("IdFormationModalite"));
        System.out.println("id mod "+row_niv_form_mod.getAttribute("IdModalite"));//IdModalite
        
        if(row_niv_form_mod.getAttribute("IdFormationModalite") != null && row_niv_form_mod.getAttribute("IdModalite") != null){
            sessionScope.put("id_form_mod_sess", row_niv_form_mod.getAttribute("IdFormationModalite"));
            sessionScope.put("id_mod_sess", row_niv_form_mod.getAttribute("IdModalite"));
        }
        
        return null;
    }

    public String onDeleteFormMod() {
        // Add event code here...
        DCIteratorBinding iter_niv_form_mod = (DCIteratorBinding) getBindings().get("NivFormPayanteModaliteROIterator");
        Row row_niv_form_mod = iter_niv_form_mod.getCurrentRow();
        
        sessionScope.put("id_form_mod_sess_del", row_niv_form_mod.getAttribute("IdFormationModalite"));
        sessionScope.put("id_mod_sess_del", row_niv_form_mod.getAttribute("IdModalite"));
        
        RichPopup popup = this.getPop_del();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return null;
    }

    public void setPop_del(RichPopup pop_del) {
        this.pop_del = pop_del;
    }

    public RichPopup getPop_del() {
        return pop_del;
    }

    @SuppressWarnings("unchecked")
    public void onDialogDelete(DialogEvent dialogEvent) {
        // Add event code here...
        OperationBinding op_update_form_mod = getBindings().getOperationBinding("deleteFormModalite");
        op_update_form_mod.getParamsMap().put("id_form_mod",  sessionScope.get("id_form_mod_sess_del"));
        op_update_form_mod.execute();
        
        OperationBinding op_update_mod = getBindings().getOperationBinding("deleteModalite");
        op_update_mod.getParamsMap().put("id_mod",  sessionScope.get("id_mod_sess_del"));
        op_update_mod.execute();
        
        refreshNivFormMod();
    }

    public void onDialogDelCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPop_del().hide();
    }

    @SuppressWarnings("unchecked")
    public void refreshNivFormMod(){
        
        AttributeBinding id_formation = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        AttributeBinding id_niv_formation = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");  
        
        OperationBinding op_ref_mod = getBindings().getOperationBinding("refreshNivFormMod");
        op_ref_mod.getParamsMap().put("id_annee",  getAnne_univers());
        op_ref_mod.getParamsMap().put("id_niv_form",  id_niv_formation.getInputValue());
        op_ref_mod.getParamsMap().put("id_form",  id_formation.getInputValue());
        op_ref_mod.execute();
    }
    
    public Row FormModEnCours(){
        
        AttributeBinding id_formation = (AttributeBinding) getBindings().getControlBinding("IdFormation");
        AttributeBinding id_niv_formation = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormation");  
        
        OperationBinding op_ref_mod = getBindings().getOperationBinding("refreshNivFormMod");
        op_ref_mod.getParamsMap().put("id_annee",  getAnne_univers());
        op_ref_mod.getParamsMap().put("id_niv_form",  id_niv_formation.getInputValue());
        op_ref_mod.getParamsMap().put("id_form",  id_formation.getInputValue());
        op_ref_mod.execute();
        DCIteratorBinding iter_niv_form_mod = (DCIteratorBinding) getBindings().get("NivFormPayanteModaliteROIterator");
        return iter_niv_form_mod.getCurrentRow();
    }
    public void insert_echo_paiement(String niv_form,String lib,Integer ordre,String montant,String montant_etr,Integer paie_obl,String id_form_mod,Integer id_mois){    
        
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("NivFormEcho");
        
        AttributeBinding lib_ech = (AttributeBinding) getBindings().getControlBinding("LibelleEchelon");
        AttributeBinding ordre_echo = (AttributeBinding) getBindings().getControlBinding("OrdreEcho");                 
        
        AttributeBinding montant_echo = (AttributeBinding) getBindings().getControlBinding("MontantEcho");
        AttributeBinding montant_etr_echo = (AttributeBinding) getBindings().getControlBinding("MontantEtrEcho");  
        
        AttributeBinding paie_obli_insc = (AttributeBinding) getBindings().getControlBinding("PaieObligInscEcho");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversEcho");  
        
        AttributeBinding id_form_mod_echo = (AttributeBinding) getBindings().getControlBinding("IdFormationModaliteEcho");
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCreeEcho"); 
        AttributeBinding id_mois_echo = (AttributeBinding) getBindings().getControlBinding("IdMoisEcho");
    
        
        OperationBinding op_form_mod = getBindings().getOperationBinding("CreateInsertEchoPaie");
        Object result_op_form_mod = op_form_mod.execute();
        if (!op_form_mod.getErrors().isEmpty()) {
            return ;
        }
        else{
            
            id_niv_form.setInputValue(niv_form);
            lib_ech.setInputValue(lib);
            ordre_echo.setInputValue(ordre);
            montant_echo.setInputValue(montant);
            montant_etr_echo.setInputValue(montant_etr);
            paie_obli_insc.setInputValue(paie_obl);
            id_form_mod_echo.setInputValue(id_form_mod);
            id_mois_echo.setInputValue(id_mois); 
            id_annee.setInputValue(getAnne_univers()); 
            util.setInputValue(getUtilisateur());
        
            
            OperationBinding op_com_form = getBindings().getOperationBinding("Commit");
            Object result_op_com_form = op_com_form.execute();
            if (!op_com_form.getErrors().isEmpty()) {
                return ;
            }
            
            else{
    
            }
            
        }
    }
    
    
    
    public void insert_echo_paiement_insp(String niv_form,String lib,Integer ordre,String montant,String montant_etr,Integer paie_obl,String id_form_mod){    
        
        AttributeBinding id_niv_form = (AttributeBinding) getBindings().getControlBinding("NivFormEcho");
        
        AttributeBinding lib_ech = (AttributeBinding) getBindings().getControlBinding("LibelleEchelon");
        AttributeBinding ordre_echo = (AttributeBinding) getBindings().getControlBinding("OrdreEcho");                 
        
        AttributeBinding montant_echo = (AttributeBinding) getBindings().getControlBinding("MontantEcho");
        AttributeBinding montant_etr_echo = (AttributeBinding) getBindings().getControlBinding("MontantEtrEcho");  
        
        AttributeBinding paie_obli_insc = (AttributeBinding) getBindings().getControlBinding("PaieObligInscEcho");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversEcho");  
        
        AttributeBinding id_form_mod_echo = (AttributeBinding) getBindings().getControlBinding("IdFormationModaliteEcho");
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCreeEcho"); 
        //AttributeBinding id_mois_echo = (AttributeBinding) getBindings().getControlBinding("IdMoisEcho");
    
        
        OperationBinding op_form_mod = getBindings().getOperationBinding("CreateInsertEchoPaie");
        Object result_op_form_mod = op_form_mod.execute();
        if (!op_form_mod.getErrors().isEmpty()) {
            return ;
        }
        else{
            
            id_niv_form.setInputValue(niv_form);
            lib_ech.setInputValue(lib);
            ordre_echo.setInputValue(ordre);
            montant_echo.setInputValue(montant);
            montant_etr_echo.setInputValue(montant_etr);
            paie_obli_insc.setInputValue(paie_obl);
            id_form_mod_echo.setInputValue(id_form_mod);
            //id_mois_echo.setInputValue(id_mois); 
            id_annee.setInputValue(getAnne_univers()); 
            util.setInputValue(getUtilisateur());
        
            
            OperationBinding op_com_form = getBindings().getOperationBinding("Commit");
            Object result_op_com_form = op_com_form.execute();
            if (!op_com_form.getErrors().isEmpty()) {
                return ;
            }
            
            else{
    
            }
            
        }
    }
    
    public Row getLibMois(String id_mois){
        OperationBinding getMois = getBindings().getOperationBinding("getMoisRech");                    
        getMois.getParamsMap().put("id_mois_deb", id_mois);
        getMois.execute();
        DCIteratorBinding iter_getMois = (DCIteratorBinding) getBindings().get("MoisRechROIterator");
        return iter_getMois.getCurrentRow();
    }

    @SuppressWarnings("unchecked")
    public void onFiltreInscPed(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("insc saisie "+searchString);
        System.out.println("getMensualite() "+sessionScope.get("val_mens"));
        System.out.println("val_duree "+sessionScope.get("val_duree"));
        
        if((searchString == "")&&(sessionScope.get("val_duree") == null) && (sessionScope.get("val_mens") == null))
            sessionScope.put("val_cout_form","0");
        else if(((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_mens") == null) && (searchString !=""))
            || ((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens") == null) && (searchString !=""))
            || ((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_mens") != null) && (searchString !=""))){
                if(is_int(searchString)){
                    sessionScope.put("val_insc_ped",searchString);
                    sessionScope.put("val_cout_form",searchString);
                }
            }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens") != null) && (searchString == "")){
            String cout = (Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(sessionScope.get("val_mens").toString()))+"";
            sessionScope.put("val_cout_form",cout);
        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens") != null) && (searchString != ""))
            if(is_int(searchString)){
                sessionScope.put("val_insc_ped",searchString);
                String cout = ((Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(sessionScope.get("val_mens").toString())) + Integer.parseInt(searchString))+"";
                sessionScope.put("val_cout_form",cout);
            }
        System.out.println("setCout_form_insc "+getCout_form());
        setCout_form(sessionScope.get("val_cout_form").toString());
    }

    @SuppressWarnings("unchecked")
    public void onFiltreMensualite(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("mens saisie "+searchString);
        System.out.println("val_insc_ped "+sessionScope.get("val_insc_ped"));
        System.out.println("val_duree "+sessionScope.get("val_duree"));

        if((searchString == "")&&(sessionScope.get("val_duree") == null) && (sessionScope.get("val_insc_ped") == null))
            sessionScope.put("val_cout_form","0");
        else if((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_insc_ped") == null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens",searchString);
                sessionScope.put("val_cout_form","0");
            }
        }
        else if(((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_insc_ped") != null) && (searchString !=""))
            && ((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped") != null) && (searchString == ""))){
                if(is_int(searchString)){
                    sessionScope.put("val_mens",searchString);
                    sessionScope.put("val_cout_form",sessionScope.get("val_insc_ped").toString());
                }
        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped") == null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens",searchString);
                String cout = (Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(searchString))+"";
                sessionScope.put("val_cout_form",cout);
            }

        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped") != null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens",searchString);
                String cout = ((Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(searchString)) + Integer.parseInt(sessionScope.get("val_insc_ped").toString()))+"";
                sessionScope.put("val_cout_form",cout);
            }

        }

        setCout_form(sessionScope.get("val_cout_form").toString());
    }

    public void onFiltreMensualiteEtr(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("mens saisie "+searchString);

        if((searchString == "")&&(sessionScope.get("val_duree") == null) && (sessionScope.get("val_insc_ped_etr") == null))
            sessionScope.put("val_cout_form_etr","0");
        else if((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_insc_ped_etr") == null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens_etr",searchString);
                sessionScope.put("val_cout_form_etr","0");
            }
        }
        else if(((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_insc_ped_etr") != null) && (searchString !=""))
            && ((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped_etr") != null) && (searchString == ""))){
                if(is_int(searchString)){
                    sessionScope.put("val_mens_etr",searchString);
                    sessionScope.put("val_cout_form_etr",sessionScope.get("val_insc_ped_etr").toString());
                }
        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped_etr") == null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens_etr",searchString);
                String cout = (Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(searchString))+"";
                sessionScope.put("val_cout_form_etr",cout);
            }

        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_insc_ped_etr") != null) && (searchString !="")){
            if(is_int(searchString)){
                sessionScope.put("val_mens_etr",searchString);
                String cout = ((Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(searchString)) + Integer.parseInt(sessionScope.get("val_insc_ped_etr").toString()))+"";
                sessionScope.put("val_cout_form_etr",cout);
            }

        }

        setCout_form_etr(sessionScope.get("val_cout_form_etr").toString());
    }

    @SuppressWarnings("unchecked")
    public void onFiltreInscPedEtr(ClientEvent clientEvent) {
        // Add event code here...
        String searchString=(String)clientEvent.getParameters().get("filterKey");
        System.out.println("insc saisie "+searchString);
        System.out.println("val_duree "+sessionScope.get("val_duree"));
        
        if((searchString == "")&&(sessionScope.get("val_duree") == null) && (sessionScope.get("val_mens_etr") == null))
            sessionScope.put("val_cout_form_etr","0");
        else if(((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_mens_etr") == null) && (searchString !=""))
            || ((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens_etr") == null) && (searchString !=""))
            || ((sessionScope.get("val_duree") == null)&& (sessionScope.get("val_mens_etr") != null) && (searchString !=""))){
                if(is_int(searchString)){
                    sessionScope.put("val_insc_ped_etr",searchString);
                    sessionScope.put("val_cout_form_etr",searchString);
                }
            }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens_etr") != null) && (searchString == "")){
            String cout = (Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(sessionScope.get("val_mens_etr").toString()))+"";
            sessionScope.put("val_cout_form_etr",cout);
        }
        else if((sessionScope.get("val_duree") != null)&& (sessionScope.get("val_mens_etr") != null) && (searchString != ""))
            if(is_int(searchString)){
                sessionScope.put("val_insc_ped_etr",searchString);
                String cout = ((Integer.parseInt(sessionScope.get("val_duree").toString()) * Integer.parseInt(sessionScope.get("val_mens_etr").toString())) + Integer.parseInt(searchString))+"";
                sessionScope.put("val_cout_form_etr",cout);
            }
        System.out.println("setCout_form_insc "+getCout_form());
        setCout_form_etr(sessionScope.get("val_cout_form_etr").toString());
    }
}
