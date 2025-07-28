package encaisse_etudiant;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.ValueChangeEvent;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.adfinternal.view.faces.event.rich.FileDownloadActionListener;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class EncaisseEtudBean {
    private String numero_table;
    private String numero_etud;
    private String numero_cin;
    private String nom;
    private String prenom;
    private String date_naiss;
    private String avoir;
    private String reste_a_payer;
    private String num_recu;
    private String montant;
    private String num_cheque;
    private String num_virment;
    private String banque;
    private String etat;
    private List<HashMap<String, String>> reslists;
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private RichPopup popEtudNotInsc;
    private RichPopup popCmptEtdNot;
    private RichInputText inputAvoir;
    private RichPopup popEtudNotMont;
    private RichPopup popRepExisteCle;
    private RichPopup popRepVide;
    private RichPopup popConfPaie;
    private RichInputText inputRestePayer;
    private RichInputText inputNumRecu;
    private RichInputText inputMontant;
    private RichInputText inputNumChq;
    private RichInputText inputNumVir;
    private RichInputText inputBanq;
    private RichPopup popConfImpPay;
    private RichPopup popConfAnnPaie;
    private String nombre_case;
    private RichPopup popAnnPaieNot;
    private RichPopup popConfAjoutSolde;
    private RichPopup popSoldInsu;
    private RichSelectBooleanCheckbox check;
    private RichPopup popConfAnnOper;
    private RichPopup popConfAnnOperNot;
    private RichPopup popConfAnnOperNotPaie;
    private RichPopup popPaieTsMois;
    private RichInputText inputAvoirSitu;
    private RichInputText inputRestePayerSitua;
    private RichInputText inputMensSitua;
    private RichInputText inputAvoirOper;
    private RichInputText inputRestePayerOper;
    private RichButton boutton1;
    private RichPopup popImpToutOp;
    private RichPopup popEtatPaie;
    private RichPopup popEtatInscNotVal;
    private RichPopup popInfPersAuc;
    private RichPopup popEtudRechNotExist;
    private RichPopup popEtdChpObli;
    private RichPopup popNumTabNotExist;
    private RichPopup popNumEtdNotExist;
    private RichPopup popNumCinNotExist;

    public EncaisseEtudBean() {
    }

    public void setNombre_case(String nombre_case) {
        this.nombre_case = nombre_case;
    }

    public String getNombre_case() {
        return nombre_case;
    }

    public void setReslists(List<HashMap<String, String>> reslists) {
        this.reslists = reslists;
    }

    public List<HashMap<String, String>> getReslists() {
        return reslists;
    }

    public void setNum_recu(String num_recu) {
        this.num_recu = num_recu;
    }

    public String getNum_recu() {
        return num_recu;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getMontant() {
        return montant;
    }

    public void setNum_cheque(String num_cheque) {
        this.num_cheque = num_cheque;
    }

    public String getNum_cheque() {
        return num_cheque;
    }

    public void setNum_virment(String num_virment) {
        this.num_virment = num_virment;
    }

    public String getNum_virment() {
        return num_virment;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getBanque() {
        return banque;
    }

    public String numero_recu_suivant(String num){
        Integer num_int = Integer.parseInt(num);
        num_int++;
        String str_fin="";
        String str = num_int+"";

        for(int i=0;i<(10-str.length());i++)
            str_fin = str_fin+"0";
        return str_fin+str;
        
    }


    @SuppressWarnings("unchecked")
    public void refresh_paie(){
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");

        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        if(row_list_insc_ped != null){
            Integer nb_paie = 0;
            Integer nb_dip = 0;
            Integer nb_not_paie = 0;
            
            DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedPaieEcolROIterator");
            Row row_paie = iter_paie.getCurrentRow();
            RowSetIterator rsi_paie = iter_paie.getViewObject().createRowSetIterator(null);
            while (rsi_paie.hasNext()) {
                Row nextRow = rsi_paie.next();
                if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) != 0){
                   nb_paie ++ ;
                }
                else
                    nb_dip ++ ;
            }
            
            NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);
            Integer somme_a_payer = 0;
            DCIteratorBinding iter_not_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
            Row row_not_paie = iter_not_paie.getCurrentRow();
            RowSetIterator rsi_not_paie = iter_not_paie.getViewObject().createRowSetIterator(null);
            while (rsi_not_paie.hasNext()) {
                Row nextRow = rsi_not_paie.next();
                if(nextRow.getAttribute("NetAPayer") != null){
                   somme_a_payer = somme_a_payer +  Integer.parseInt(nextRow.getAttribute("NetAPayer").toString());
                }
                if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) != 0){
                   nb_not_paie ++ ;
                }
                else
                    nb_dip ++ ;
            }
            Integer nb = (nb_not_paie + nb_paie);
            setReste_a_payer(numberFormat.format(somme_a_payer));
            setEtat(nb_paie +" / "+ nb+" mois ");
            String num_recu_prec = "0";
            OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
            op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
            op_oper_annee.execute();
            DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
            Row row_oper_annee = iter_oper_annee.getCurrentRow();
            if(row_oper_annee != null)
                num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
            numero_recu_suivant(num_recu_prec);
            setNum_recu(numero_recu_suivant(num_recu_prec));
            
            
            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
            op_cmpt_etud_gen.execute();
            DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
            Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
            if(row_cmpt_etud_gen != null){
                if(row_cmpt_etud_gen.getAttribute("SoldeActuel") != null){
                    setAvoir(numberFormat.format(Integer.parseInt(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString())));                        
                }
                
                DCIteratorBinding iter_not_paiement = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
                Row row_not_paiement = iter_not_paiement.getCurrentRow();
                if(row_not_paiement != null)
                    sessionScope.put("paie_auto_var", 1);
            }
            else{
                setAvoir("");
                sessionScope.put("paie_auto_var", 0);
            }

        }
      
        DCIteratorBinding iter_mode_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ModePaiementsIterator");
        Row row_mode_paie = iter_mode_paie.getCurrentRow();
        
        if(row_mode_paie != null){
            if(Integer.parseInt(row_mode_paie.getAttribute("IdModePaiement").toString())== 1){
                sessionScope.put("mode_paie_var", 1);
            }
            else{
                if(Integer.parseInt(row_mode_paie.getAttribute("IdModePaiement").toString())== 2){
                    sessionScope.put("mode_paie_var", 0);
                    sessionScope.put("mode_paie_var_cheq", 1);
                    sessionScope.put("mode_paie_var_virment", 0);
                }
                else{
                    if(Integer.parseInt(row_mode_paie.getAttribute("IdModePaiement").toString())== 3){
                        sessionScope.put("mode_paie_var", 0);
                        sessionScope.put("mode_paie_var_cheq", 0);
                        sessionScope.put("mode_paie_var_virment", 1);
                    }
                }
            }
        }    
    }
    
    @SuppressWarnings("unchecked")
    public void refresh_situation(){
        //setNumero_etud("2020033LD");
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");

        if(sessionScope.get("num_tb_var") != null){
            refresh_num_tab(sessionScope.get("num_tb_var").toString());
        }
        else if(sessionScope.get("num_etd_var")!= null){
            refresh_num_etud(sessionScope.get("num_etd_var").toString());
        }
        else if(sessionScope.get("num_cin_var")!= null){
            refresh_num_cin(sessionScope.get("num_cin_var").toString());
        }

        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        if(row_list_insc_ped != null){
            Integer nb_paie = 0;
            Integer nb_dip = 0;
            Integer nb_not_paie = 0;
            
            DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedPaieEcolROIterator");
            Row row_paie = iter_paie.getCurrentRow();
            RowSetIterator rsi_paie = iter_paie.getViewObject().createRowSetIterator(null);
            while (rsi_paie.hasNext()) {
                Row nextRow = rsi_paie.next();
                if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) != 0){
                   nb_paie ++ ;
                }
                else
                    nb_dip ++ ;
            }
            
            NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
            op_cmpt_etud_gen.execute();
            DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
            Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
            if(row_cmpt_etud_gen != null){
                if(row_cmpt_etud_gen.getAttribute("SoldeActuel") != null){
                    setAvoir(numberFormat.format(Integer.parseInt(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString())));                        
                }

            }
            else
                setAvoir("");
            Integer somme_a_payer = 0;
            DCIteratorBinding iter_not_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
            Row row_not_paie = iter_not_paie.getCurrentRow();
            RowSetIterator rsi_not_paie = iter_not_paie.getViewObject().createRowSetIterator(null);
            while (rsi_not_paie.hasNext()) {
                Row nextRow = rsi_not_paie.next();
                if(nextRow.getAttribute("NetAPayer") != null){
                   somme_a_payer = somme_a_payer +  Integer.parseInt(nextRow.getAttribute("NetAPayer").toString());
                }
                if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) != 0){
                   nb_not_paie ++ ;
                }
                else
                    nb_dip ++ ;
            }
            Integer nb = (nb_not_paie + nb_paie);
            if(nb > 0){
                setReste_a_payer(numberFormat.format(somme_a_payer));
                setEtat(nb_paie +" / "+ nb+" mois ");
            }
            else{
                setReste_a_payer("");
                setEtat("");
            }
            
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoirSitu()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayerSitua()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMensSitua());
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setReste_a_payer(String reste_a_payer) {
        this.reste_a_payer = reste_a_payer;
    }

    public String getReste_a_payer() {
        return reste_a_payer;
    }

    public void setNumero_table(String numero_table) {
        this.numero_table = numero_table;
    }

    public void setAvoir(String avoir) {
        this.avoir = avoir;
    }

    public String getAvoir() {
        return avoir;
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
                
                // veuillez saisir les informations personnelles
                RichPopup popup = this.getPopInfPersAuc();                   
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                refresh_etud_not_insc();
               
            }

            else if((String)this.getPrenom() == null || (String)this.getNom() == null || (String)this.getDate_naiss() == null){
                
                if((String)this.getPrenom() == null){
                    sessionScope.put("etd_prenom_rech_enc", "==> Prénom");
                }
                else{
                    sessionScope.remove("etd_prenom_rech_enc");
                }
                if((String)this.getNom() == null){
                    sessionScope.put("etd_nom_rech_enc", "==> Nom");
                }
                else{
                    sessionScope.remove("etd_nom_rech_enc");
                }
                if((String)this.getDate_naiss() == null){
                    sessionScope.put("etd_date_naiss_rech_enc", "==> Date Naissance");
                }
                else{
                    sessionScope.remove("etd_date_naiss_rech_enc");
                }
                
                RichPopup popup = this.getPopEtdChpObli();                   
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
                refresh_etud_not_insc();

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
                    
                    RichPopup popup = this.getPopEtudRechNotExist();                   
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    refresh_etud_not_insc();
                }
        }
            
        }
        else{
            if((String)this.getNumero_table() != null){
                OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString()));
                is_nouv_bac.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                Integer result = (Integer)is_nouv_bac.execute();
                sessionScope.put("num_tb_var", (String)this.getNumero_table());
                //numéro table non valide
                if(result != 0){
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                    getIdPersEtudiant.getParamsMap().put("num_table",  Long.parseLong((String)this.getNumero_table().toString()));
                    getIdPersEtudiant.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    String result_getid = (String)getIdPersEtudiant.execute();   
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                    row_det_pers = rsi_det_pers.first();
                    
                }
                else{
                    //le numéro table saisi n'existe pas
                    RichPopup popup = this.getPopNumTabNotExist();                   
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                    refresh_etud_not_insc();
                    setNumero_table("");
                    sessionScope.put("tableNotEncEtdNull", 0);
                }
    
            }
            else {
                if((String)this.getNumero_etud() != null ){
                    OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                    isEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());
                    sessionScope.put("num_etd_var", (String)this.getNumero_etud());
                    Integer result = (Integer)isEtudiant.execute();
                    //numéro étudiant non valide
                    if(result != 0){
                        //numéro étudiant valide
                        // recuperer id personn de l etudiant
                        OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                        getIdPersEtudiant.getParamsMap().put("num_etud",  (String)this.getNumero_etud());                            
                        String result_getid = (String)getIdPersEtudiant.execute(); 
        
                        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                        detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                        detpers.execute();
                        
                        DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                            //Create RowSetIterato iterate over viewObject
                        RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                        row_det_pers = rsi_det_pers.first();
                       
                    }
                    else{
                        //le numéro d'étudiant saisi n'existe pas
                        RichPopup popup = this.getPopNumEtdNotExist();                   
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                        refresh_etud_not_insc();
                        setNumero_etud("");
                        sessionScope.put("etudNotEncEtdNull", 0);
                    }
                }
                else {
                    if((String)this.getNumero_cin() != null ){
                        //vérifier le numéro d'identification
                        OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
                        isEtudiant.getParamsMap().put("num_cin",  (String)this.getNumero_cin());
                        sessionScope.put("num_cin_var", (String)this.getNumero_cin());
                        Integer result = (Integer)isEtudiant.execute();
                        // le numéro d'identification non valide            
                        if(result != 0){
                            // le numéro d'identification valide
                            // recuperer id personn de l etudiant
                            OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                            getIdPersEtudiant.getParamsMap().put("cin",  (String)this.getNumero_cin());  
                            String result_getid = (String)getIdPersEtudiant.execute(); 
        
                            OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                            detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                            detpers.execute();
                            
                            DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                                //Create RowSetIterato iterate over viewObject
                            RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                            row_det_pers = rsi_det_pers.first();
                        }
                        else{
                            //le numéro d'identification saisi n'existe pas
                            RichPopup popup = this.getPopNumCinNotExist();                   
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                            refresh_etud_not_insc();
                            setNumero_cin("");
                            sessionScope.put("cinNotEncEtdNull", 0);
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
        System.out.println("searchString searchString "+searchString);sessionScope.remove("num_etd_var") ;
        sessionScope.put("etudNotEncEtdNull", 0);
        if(searchString != ""){
            sessionScope.put("etudNotEncEtdNull", 1);
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
                    detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
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
        System.out.println("searchString searchString "+searchString);sessionScope.remove("num_cin_var") ;
        sessionScope.put("cinNotEncEtdNull", 0);

        if(searchString != ""){
            sessionScope.put("cinNotEncEtdNull", 1);
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
                    detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
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
        System.out.println("searchString "+searchString);sessionScope.remove("num_tb_var") ;
        sessionScope.put("tableNotEncEtdNull", 0);
        sessionScope.put("etudNotEncEtdNull", 0);

        if(searchString != ""){
            sessionScope.put("tableNotEncEtdNull", 1);
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
                    detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                    detpers.execute();
                    
                    DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
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
    public Integer getEtudiantBu(String id_etud, String id_annee) {
        AttributeBinding id_strct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        Integer result = 0;
        OperationBinding op_etud_bu = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudBuRech");
        op_etud_bu.getParamsMap().put("id_struct",  id_strct.getInputValue());
        op_etud_bu.getParamsMap().put("id_etud", id_etud);
        op_etud_bu.getParamsMap().put("id_annee", id_annee);
        op_etud_bu.execute();
        DCIteratorBinding iter_etud_bu = (DCIteratorBinding) getBindings().get("EtudBuRechROIterator");
        Row currentRow_etud_bu = iter_etud_bu.getCurrentRow();
        if (currentRow_etud_bu == null) {
        return result;
        }
        else{
            if(currentRow_etud_bu.getAttribute("EnRegle") == null){
                return result;
            }
            else{
                if(Integer.parseInt(currentRow_etud_bu.getAttribute("EnRegle").toString()) == 1){
                    result = 1;
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getApteEtudiant(String id_etud, String id_annee) {
        //getAuto(String id_etud,String nivForm)
        Integer result = 0;

        OperationBinding opNote = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudApteRech");
        opNote.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        opNote.getParamsMap().put("id_annee", Long.parseLong(id_annee));
        opNote.execute();
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EtudApteRechROIterator");
        Row currentRow = iter.getCurrentRow();
        if (currentRow == null) {
            return result;
        }
        else{   
            if(currentRow.getAttribute("Apte") == null){
                return result;
            }
            else{
                if (Integer.parseInt(currentRow.getAttribute("Apte").toString()) == 1) {
                    result = 1;
                    return result;
                }
                else
                    return result;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getResponsableEtud(String id_etud){    
        
        Integer result = 0;
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getResponsableEtud");
        op_resp_etud.getParamsMap().put("id_etud", Long.parseLong(id_etud));
        op_resp_etud.execute();
        DCIteratorBinding iter_resp_etud = (DCIteratorBinding) getBindings().get("ResponsableROIterator");
        Row currentRow_resp_etud = iter_resp_etud.getCurrentRow();
        if (currentRow_resp_etud == null) {
            return result;
        }
        else{
            result = 1;
            return result;         
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getInscEnLigne(String id_insc_ped){
        Integer result = 0;
        OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
        op_resp_etud.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
        op_resp_etud.execute();
        DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscPedagogiqueROIterator");
        Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
        if (currentRow_insc_ped == null ) {
            return result;
        }
        else{
            if (currentRow_insc_ped.getAttribute("InsEnLigne") == null ) {
                return result;
            }
            else{
                // inscription validée si InsEnLigne (oui : 1) 
                if (Integer.parseInt(currentRow_insc_ped.getAttribute("InsEnLigne").toString()) == 1) {
                    result = 1;
                    return result;
                }
                else{
                    return result;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Integer getEtatInscBis(String id_insc_ped){
       Integer result = 0;
       OperationBinding op_resp_etud = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDetailIncPedEtud");
       op_resp_etud.getParamsMap().put("id_insc_ped", Long.parseLong(id_insc_ped));
       op_resp_etud.execute();
       DCIteratorBinding iter_insc_ped = (DCIteratorBinding) getBindings().get("InscPedagogiqueROIterator");
       Row currentRow_insc_ped = iter_insc_ped.getCurrentRow();
       if (currentRow_insc_ped == null ) {
           return result;
       }
       else{
            if(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId") == null){
                return result;
            }
            else{              
                if(Long.parseLong(currentRow_insc_ped.getAttribute("EtatInscrEtatInscrId").toString()) == 22){
                    result = 1;
                    return result;
                }
                else{                                 
                   return result;
                }
            }
       }
    }

    public Integer getEtatInscEtudAnn(String inscPed , String id_etud,  String id_annee){    
        Integer result = 0 ;
        Integer etud_bu = getEtudiantBu(id_etud, id_annee);
        Integer etud_apte = getApteEtudiant(id_etud, id_annee);
        Integer etud_resp = getResponsableEtud(id_etud);
        Integer etud_insc_ligne = getInscEnLigne(inscPed);
        Integer etud_etat_insc = getEtatInscBis(inscPed);
    
        if(etud_bu == 1 && etud_apte == 1 && etud_resp == 1 &&  etud_insc_ligne == 1 ){        
            result = 1 ;
        }       
        return result ;
    }
    @SuppressWarnings("unchecked")
    public void onValiderRecherche(ActionEvent actionEvent) {
        // Add event code here...
        Row rw = recherche();
        if(rw != null){
            AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
            getRowPers(rw);
            
            OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
            list_insc_ped.getParamsMap().put("idpers",  Long.parseLong(rw.getAttribute("IdPersonne").toString()));
            list_insc_ped.getParamsMap().put("id_annee",  getAnne_univers());
            list_insc_ped.execute();
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            if(row_list_insc_ped != null){
                Integer res_etat_insc = getEtatInscEtudAnn(row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_list_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                if(res_etat_insc == 1){
                    OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                    op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                    op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
                    op_cmpt_etud_gen.execute();
                    DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    if(row_cmpt_etud_gen != null){                   
                        if(row_cmpt_etud_gen.getAttribute("SoldeActuel") != null){
                            System.out.println("SoldeActuel "+row_cmpt_etud_gen.getAttribute("SoldeActuel"));
                            //setAvoir(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString());
                            
                            System.out.println("avoir "+getAvoir());
                            
                        }
                        OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
                        op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
                        op_op_cmpt_etud.getParamsMap().put("id_cmpte",  row_cmpt_etud_gen.getAttribute("IdCompte"));
                        op_op_cmpt_etud.execute();
    
                    }
                    else{
                        RichPopup popup = this.getPopCmptEtdNot();                 
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    //Insc ped n'est pas definitive
                    //insertPaieMensualite(row_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                    String inscPed = row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString();
                    String id_etud = row_list_insc_ped.getAttribute("IdEtudiant").toString();
                    Integer etud_bu = getEtudiantBu(id_etud, getAnne_univers());
                    Integer etud_apte = getApteEtudiant(id_etud, getAnne_univers());
                    Integer etud_resp = getResponsableEtud(id_etud);
                    Integer etud_insc_ligne = getInscEnLigne(inscPed);
                    Integer etud_etat_insc = getEtatInscBis(inscPed);
                    
                    if(etud_bu == 0 || etud_apte == 0 || etud_resp == 0 ||  etud_insc_ligne == 0 ){        
                        if(etud_bu == 0 ){
                            sessionScope.put("etud_bu_enRegle_caisse", "==> En règle avec la BU : Non");
                        }
                        else{
                            sessionScope.put("etud_bu_enRegle_caisse", "");
                        }
                        if(etud_insc_ligne == 0){
                                sessionScope.put("etud_enLigne_caisse", "==> Inscription en ligne : Non");
                        }
                        else{
                                sessionScope.put("etud_enLigne_caisse", "");
                        }
                        if(etud_apte == 0){
                            sessionScope.put("etud_Apte_caisse", "==> Apte : Non");
                        }
                        else{
                            sessionScope.put("etud_Apte_caisse", "");
                        }
                        
                        if(etud_resp == 0){
                            sessionScope.put("etud_Respnsable_caisse", "==> Le responsable( Tuteur) : Non");
                        }
                        else{
                            sessionScope.put("etud_Respnsable_caisse", "");
                        }
                    } 
                    
                    sessionScope.put("etud_num_etud_caisse", row_list_insc_ped.getAttribute("Numero"));

                    OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                    op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                    op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
                    op_cmpt_etud_gen.execute();
                    DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    if(row_cmpt_etud_gen == null){                   
                        OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
                        op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
                        op_op_cmpt_etud.getParamsMap().put("id_cmpte",  0);
                        op_op_cmpt_etud.execute();
                    
                    }
                    
                    RichPopup popup = this.getPopEtatInscNotVal();                    
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                // l'étudiant n'est pas inscrit
                RichPopup popup = this.getPopEtudNotInsc();                  
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

        }
    }

    public void setPopEtudNotInsc(RichPopup popEtudNotInsc) {
        this.popEtudNotInsc = popEtudNotInsc;
    }

    public RichPopup getPopEtudNotInsc() {
        return popEtudNotInsc;
    }

    public void setPopCmptEtdNot(RichPopup popCmptEtdNot) {
        this.popCmptEtdNot = popCmptEtdNot;
    }

    public RichPopup getPopCmptEtdNot() {
        return popCmptEtdNot;
    }

    @SuppressWarnings("unchecked")
    public List<HashMap<String,String>> confirmePaiement(){
        HashMap<String,String> ligne_mois = new HashMap<String,String>();
        List<HashMap<String,String>> tab_mois = new ArrayList<HashMap<String,String>>();
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        //getCmpteEtabGen
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_rep_str = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_rep_str = iter_rep_str.getCurrentRow();
        if(row_rep_str != null){System.out.println("row_rep_str "+row_rep_str);
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_str.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
        
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){        
                //if(getMontant() != null){      
                    DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    if(row_cmpt_etud_gen != null){ 
                        DCIteratorBinding iter_not_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
                        Row row_not_paie = iter_not_paie.getCurrentRow();
                        RowSetIterator rsi_not_paie = iter_not_paie.getViewObject().createRowSetIterator(null);
                        if(row_not_paie != null){
                            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                            op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_cmpt_etud_gen.getAttribute("IdEtudiant"));
                            op_cmpt_etud_gen.execute();
                            DCIteratorBinding iter_cmpt_etud_gen1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                            Row row_cmpt_etud_gen1 = iter_cmpt_etud_gen1.getCurrentRow();
                            Integer solde = Integer.parseInt(row_cmpt_etud_gen1.getAttribute("SoldeActuel").toString());
                            //tab_mois.subList(1, 1);
                            while (rsi_not_paie.hasNext()) {
                                Row nextRow = rsi_not_paie.next();
                                System.out.println("solde iter "+solde);
                                if(nextRow.getAttribute("NetAPayer") != null){
                                   if(solde >= Integer.parseInt(nextRow.getAttribute("NetAPayer").toString())){
                                       System.out.println("solde > "+solde);
                                       ligne_mois.put(nextRow.getAttribute("LibelleEchelon").toString(), nextRow.getAttribute("NetAPayer").toString());
                                       tab_mois.add(ligne_mois);
                                   }
                                   solde = solde - Integer.parseInt(nextRow.getAttribute("NetAPayer").toString());
                                }
                                    
                            }
                        }
                                    
                    }
                /*}
                else{
                    RichPopup popup = this.getPopEtudNotMont();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }*/
            }
            else{
                RichPopup popup = this.getPopRepExisteCle();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            RichPopup popup = this.getPopRepVide();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        return tab_mois;
    }

    @SuppressWarnings("unchecked")
    public void onEnregistrerPaiement(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("sessionScope.get(montant_sess_var) "+getMontant());
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        java.util.Date utilDate = new java.util.Date();
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        List<HashMap<String,String>> tab_mois = new ArrayList<HashMap<String,String>>();

        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        if(row_list_insc_ped != null){            
            Integer res_etat_insc = getEtatInscEtudAnn(row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_list_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
            if(res_etat_insc == 1){
    
                DCIteratorBinding iter_not_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
                Row row_not_paie = iter_not_paie.getCurrentRow();
                if(getMontant() != null){                    
                    sessionScope.put("montant_sess_var", getMontant());
                    sessionScope.put("num_recu_sess_var", getNum_recu());
                    sessionScope.put("num_chq_sess_var", getNum_cheque());
                    sessionScope.put("num_vir_sess_var", getNum_virment());
                    sessionScope.put("banq_sess_var", getBanque());
        
                    
                    RichPopup popup = this.getPopConfAjoutSolde();            
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                else{
                    if(row_not_paie == null && getMontant() == null){
                        //l'étudiant a payé tous les mois
                        RichPopup popup = this.getPopPaieTsMois();                 
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    else if(row_not_paie != null && getMontant() == null){
                            if(confirmePaiement().size()   > 0){
                                tab_mois = confirmePaiement().subList(0, 1);
                                System.out.println("confirmePaiement() "+tab_mois);
                                System.out.println("confirmePaiement().size() "+tab_mois.size());
                                setReslists(tab_mois);   
                                
                                sessionScope.put("montant_sess_var", getMontant());
                                sessionScope.put("num_recu_sess_var", getNum_recu());
                                sessionScope.put("num_chq_sess_var", getNum_cheque());
                                sessionScope.put("num_vir_sess_var", getNum_virment());
                                sessionScope.put("banq_sess_var", getBanque());
                                
                                
                                RichPopup popup = this.getPopConfPaie();                 
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                popup.show(hints);
                            }
                            else{
                                RichPopup popup = this.getPopSoldInsu();                 
                                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                                popup.show(hints);
                            }
                    }
                }
            }
            else{
                //Insc ped n'est pas definitive
                //insertPaieMensualite(row_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped.getAttribute("IdEtudiant").toString(), getAnne_univers());
                String inscPed = row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString();
                String id_etud = row_list_insc_ped.getAttribute("IdEtudiant").toString();
                Integer etud_bu = getEtudiantBu(id_etud, getAnne_univers());
                Integer etud_apte = getApteEtudiant(id_etud, getAnne_univers());
                Integer etud_resp = getResponsableEtud(id_etud);
                Integer etud_insc_ligne = getInscEnLigne(inscPed);
                Integer etud_etat_insc = getEtatInscBis(inscPed);
                
                if(etud_bu == 0 || etud_apte == 0 || etud_resp == 0 ||  etud_insc_ligne == 0 ){        
                    if(etud_bu == 0 ){
                        sessionScope.put("etud_bu_enRegle_caisse", "==> En règle avec la BU : Non");
                    }
                    else{
                        sessionScope.put("etud_bu_enRegle_caisse", "");
                    }
                    if(etud_insc_ligne == 0){
                            sessionScope.put("etud_enLigne_caisse", "==> Inscription en ligne : Non");
                    }
                    else{
                            sessionScope.put("etud_enLigne_caisse", "");
                    }
                    if(etud_apte == 0){
                        sessionScope.put("etud_Apte_caisse", "==> Apte : Non");
                    }
                    else{
                        sessionScope.put("etud_Apte_caisse", "");
                    }
                    
                    if(etud_resp == 0){
                        sessionScope.put("etud_Respnsable_caisse", "==> Le responsable( Tuteur) : Non");
                    }
                    else{
                        sessionScope.put("etud_Respnsable_caisse", "");
                    }
                } 
                
                sessionScope.put("etud_num_etud_caisse", row_list_insc_ped.getAttribute("Numero"));
        
                OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
                op_cmpt_etud_gen.execute();
                DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                if(row_cmpt_etud_gen == null){                   
                    OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
                    op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
                    op_op_cmpt_etud.getParamsMap().put("id_cmpte",  0);
                    op_op_cmpt_etud.execute();
                
                }
                
                RichPopup popup = this.getPopEtatInscNotVal();                    
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            // l'étudiant n'est pas inscrit
            RichPopup popup = this.getPopEtudNotInsc();                  
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    @SuppressWarnings("unchecked")
    public void insertOperationSaisie(String montant,String type,String mode_paie,String compte,String cheq,String banq,String virement,String motif_paie,String debiter,String numero_recu,String valider){
        AttributeBinding montant_oper = (AttributeBinding) getBindings().getControlBinding("MontantOper");
        AttributeBinding date_oper = (AttributeBinding) getBindings().getControlBinding("DateOperation");
        AttributeBinding type_oper = (AttributeBinding) getBindings().getControlBinding("TypeOperation");
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiementOper");
        AttributeBinding id_compte = (AttributeBinding) getBindings().getControlBinding("IdCompteOper");
        AttributeBinding num_cheq = (AttributeBinding) getBindings().getControlBinding("NumeroChequeOper");
        AttributeBinding banque = (AttributeBinding) getBindings().getControlBinding("BanqueOper");
        AttributeBinding num_virement = (AttributeBinding) getBindings().getControlBinding("NumeroVirementOper");
        AttributeBinding motif = (AttributeBinding) getBindings().getControlBinding("MotifOper");
        AttributeBinding benif = (AttributeBinding) getBindings().getControlBinding("BeneficiareOper");
        AttributeBinding debit = (AttributeBinding) getBindings().getControlBinding("DebitOper");
        AttributeBinding num_recu = (AttributeBinding) getBindings().getControlBinding("NumeroRecuOper");
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("ValideOper");
        AttributeBinding num_mandant = (AttributeBinding) getBindings().getControlBinding("NumeroMandatOper");
        AttributeBinding annule = (AttributeBinding) getBindings().getControlBinding("AnnuleOper");
        
        AttributeBinding op_annuler = (AttributeBinding) getBindings().getControlBinding("OpAnnuleOper");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversOper");
        AttributeBinding id_util_cree = (AttributeBinding) getBindings().getControlBinding("UtiCreeOper");
        AttributeBinding id_util_mod = (AttributeBinding) getBindings().getControlBinding("UtiModifieOper");
        
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertOperation");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        java.util.Date utilDate = new java.util.Date();

        montant_oper.setInputValue(montant);
        date_oper.setInputValue(utilDate);
        type_oper.setInputValue(type);
        id_mode_paie.setInputValue(mode_paie);
        id_compte.setInputValue(compte);
        num_cheq.setInputValue(cheq);
        banque.setInputValue(banq);
        num_virement.setInputValue(virement);
        motif.setInputValue(motif_paie);
        debit.setInputValue(debiter);
        num_recu.setInputValue(numero_recu);
        valide.setInputValue(valider);
        id_util_cree.setInputValue(getUtilisateur());
        id_annee.setInputValue(getAnne_univers());
        
        
        OperationBinding op_commit_op = getBindings().getOperationBinding("Commit");
        Object result_oper = op_commit_op.execute();
        if (!op_commit_op.getErrors().isEmpty()) {
                return ;
        }
        else{
            DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
            Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
            if(row_cmpt_etud_gen != null){ 
                Integer solde = 0;
                if(row_cmpt_etud_gen.getAttribute("SoldeActuel") != null)
                    solde = (Integer.parseInt(getMontant()) +  Integer.parseInt(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString()));
                else
                    solde = Integer.parseInt(getMontant());

                OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                getUpdateCmpte.getParamsMap().put("id_solde_compte",  row_cmpt_etud_gen.getAttribute("IdSoldeCompte"));  
                getUpdateCmpte.getParamsMap().put("solde_act",  solde);  
                getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                getUpdateCmpte.execute();
            }
        }
        
    }

    @SuppressWarnings("unchecked")
    public void insertOperationDebit(String montant,String type,String mode_paie,String compte,String cheq,String banq,String virement,String motif_paie,String debiter,String numero_recu,String valider,String paie){
        AttributeBinding montant_oper = (AttributeBinding) getBindings().getControlBinding("MontantOper");
        AttributeBinding date_oper = (AttributeBinding) getBindings().getControlBinding("DateOperation");
        AttributeBinding type_oper = (AttributeBinding) getBindings().getControlBinding("TypeOperation");
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiementOper");
        AttributeBinding id_compte = (AttributeBinding) getBindings().getControlBinding("IdCompteOper");
        AttributeBinding num_cheq = (AttributeBinding) getBindings().getControlBinding("NumeroChequeOper");
        AttributeBinding banque = (AttributeBinding) getBindings().getControlBinding("BanqueOper");
        AttributeBinding num_virement = (AttributeBinding) getBindings().getControlBinding("NumeroVirementOper");
        AttributeBinding motif = (AttributeBinding) getBindings().getControlBinding("MotifOper");
        AttributeBinding benif = (AttributeBinding) getBindings().getControlBinding("BeneficiareOper");
        AttributeBinding debit = (AttributeBinding) getBindings().getControlBinding("DebitOper");
        AttributeBinding num_recu = (AttributeBinding) getBindings().getControlBinding("NumeroRecuOper");
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("ValideOper");
        AttributeBinding num_mandant = (AttributeBinding) getBindings().getControlBinding("NumeroMandatOper");
        AttributeBinding annule = (AttributeBinding) getBindings().getControlBinding("AnnuleOper");
        
        AttributeBinding op_annuler = (AttributeBinding) getBindings().getControlBinding("OpAnnuleOper");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversOper");
        AttributeBinding id_util_cree = (AttributeBinding) getBindings().getControlBinding("UtiCreeOper");
        AttributeBinding id_util_mod = (AttributeBinding) getBindings().getControlBinding("UtiModifieOper");
        //PaiementOper
        AttributeBinding paiement_op = (AttributeBinding) getBindings().getControlBinding("PaiementOper");
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");

        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertOperation");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        java.util.Date utilDate = new java.util.Date();
        
        montant_oper.setInputValue(montant);
        date_oper.setInputValue(utilDate);
        type_oper.setInputValue(type);
        id_mode_paie.setInputValue(mode_paie);
        id_compte.setInputValue(compte);
        num_cheq.setInputValue(cheq);
        banque.setInputValue(banq);
        num_virement.setInputValue(virement);
        motif.setInputValue(motif_paie);
        debit.setInputValue(debiter);
        num_recu.setInputValue(numero_recu);
        valide.setInputValue(valider);
        id_util_cree.setInputValue(getUtilisateur());
        id_annee.setInputValue(getAnne_univers());
        paiement_op.setInputValue(paie);
        
        
        OperationBinding op_commit_op = getBindings().getOperationBinding("Commit");
        Object result_oper = op_commit_op.execute();
        if (!op_commit_op.getErrors().isEmpty()) {
                return ;
        }
        else{
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
            op_cmpt_etud_gen.execute();
            DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
            Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
            if(row_cmpt_etud_gen != null){ 
                Integer solde = 0;
                if(row_cmpt_etud_gen.getAttribute("SoldeActuel") != null){ 
                    solde = ( Integer.parseInt(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString()) - Integer.parseInt(montant));
                    repartition(Integer.parseInt(montant));
                }
                OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                getUpdateCmpte.getParamsMap().put("id_solde_compte",  row_cmpt_etud_gen.getAttribute("IdSoldeCompte"));  
                getUpdateCmpte.getParamsMap().put("solde_act",  solde);  
                getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                getUpdateCmpte.execute();
            }
        }
        
    }

    @SuppressWarnings("unchecked")
    public void repartition(Integer montant_rep){
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        //getCmpteEtabGen
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_cmpt_etb_gen = iter_annee_passee.getCurrentRow();
        if(row_cmpt_etb_gen != null){
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
            
            Integer nb = 0;
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                while (rsi_rep_ex_cle.hasNext()) {
                    Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                    //getCmpteTypeCmptRepart
                    Double solde = ((Double.parseDouble(row_rep_ex_cle.getAttribute("Pourcentage").toString())*montant_rep)/100);
                    OperationBinding op_cmpt_type_repart = getBindings().getOperationBinding("getCmpteTypeCmptRepart");
                    op_cmpt_type_repart.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_type_repart.getParamsMap().put("id_strct",  id_struct.getInputValue());
                    op_cmpt_type_repart.getParamsMap().put("type_cmpte", row_rep_ex_cle.getAttribute("IdTypeCompte"));
                    op_cmpt_type_repart.execute();
                    DCIteratorBinding iter_op_cmpt_type_repart = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteTypeCmpteRepartROIterator");
                    Row row_cmpt_type_repart = iter_op_cmpt_type_repart.getCurrentRow();
                    
                    Double solde_act = null;
                    if(row_cmpt_type_repart.getAttribute("SoldeActuel") != null)
                        solde_act = (solde +  Double.parseDouble(row_cmpt_type_repart.getAttribute("SoldeActuel").toString()));
                    else
                        solde_act = solde;
                    Integer sold = solde_act.intValue();
                    OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                    getUpdateCmpte.getParamsMap().put("id_solde_compte",  row_cmpt_type_repart.getAttribute("IdSoldeCompte"));  
                    getUpdateCmpte.getParamsMap().put("solde_act",  sold);  
                    getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                    getUpdateCmpte.execute();
                    
                    if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                        refresh_cmpte_glob_encaisse(id_struct.getInputValue().toString(), sessionScope.get("id_hs").toString(), solde.intValue()+"");
                       }
                    
                }
            }
        }
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
    
    public void setInputAvoir(RichInputText inputAvoir) {
        this.inputAvoir = inputAvoir;
    }

    public RichInputText getInputAvoir() {
        return inputAvoir;
    }


    public void setPopEtudNotMont(RichPopup popEtudNotMont) {
        this.popEtudNotMont = popEtudNotMont;
    }

    public RichPopup getPopEtudNotMont() {
        return popEtudNotMont;
    }

    public void setPopRepExisteCle(RichPopup popRepExisteCle) {
        this.popRepExisteCle = popRepExisteCle;
    }

    public RichPopup getPopRepExisteCle() {
        return popRepExisteCle;
    }

    public void setPopRepVide(RichPopup popRepVide) {
        this.popRepVide = popRepVide;
    }

    public RichPopup getPopRepVide() {
        return popRepVide;
    }

    public void setPopConfPaie(RichPopup popConfPaie) {
        this.popConfPaie = popConfPaie;
    }

    public RichPopup getPopConfPaie() {
        return popConfPaie;
    }

    @SuppressWarnings("unchecked")
    public void updateInscriptionPed(String insc_ped, String etat_insc){        
        OperationBinding op_insc_ped = getBindings().getOperationBinding("findAndUpdateInsPed");
        op_insc_ped.getParamsMap().put("id_ins_ped", insc_ped);
        op_insc_ped.getParamsMap().put("etat",  etat_insc);  // etzt : 22 etat définitif
        op_insc_ped.getParamsMap().put("util",  getUtilisateur());
        op_insc_ped.execute();
    }

    @SuppressWarnings("unchecked")
    public void onDialogEnc(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        java.util.Date utilDate = new java.util.Date();
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        System.out.println("sessionScope.get(montant_sess_var) "+sessionScope.get("montant_sess_var"));
        if(sessionScope.get("montant_sess_var") != null)
            setMontant(sessionScope.get("montant_sess_var").toString());
        else
            setMontant((String)sessionScope.get("num_chq_sess_var"));
        setNum_recu(sessionScope.get("num_recu_sess_var").toString());
        if(sessionScope.get("num_chq_sess_var") != null)
            setNum_cheque(sessionScope.get("num_chq_sess_var").toString());
        else
            setNum_cheque("");
        if(sessionScope.get("num_vir_sess_var") != null)
            setNum_virment(sessionScope.get("num_vir_sess_var").toString());
        else
            setNum_virment("");
        if(sessionScope.get("banq_sess_var") != null)
            setBanque(sessionScope.get("banq_sess_var").toString());
        else
            setBanque("");
        System.out.println(" get Montant "+getMontant());
        
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_rep_str = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_rep_str = iter_rep_str.getCurrentRow();
        if(row_rep_str != null){
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_str.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
        
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){        
                     
                DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    
                if(row_cmpt_etud_gen != null){ 
                    if(getMontant() != null){                     
                        insertOperationSaisie(getMontant(), "1", id_mode_paie.getInputValue().toString(), row_cmpt_etud_gen.getAttribute("IdCompte").toString(), getNum_cheque(), getBanque(), getNum_virment(), "Paiement", "0", getNum_recu(), "1");                   
                    }
                    else{
                        if(getMontant() == null ){                     
                            DCIteratorBinding iter_not_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedNotPaieEcolROIterator");
                            Row row_not_paie = iter_not_paie.getCurrentRow();
                            RowSetIterator rsi_not_paie = iter_not_paie.getViewObject().createRowSetIterator(null);
                            if(row_not_paie != null){
                                OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                                op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                                op_cmpt_etud_gen.getParamsMap().put("id_strct",  id_struct.getInputValue());
                                op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_cmpt_etud_gen.getAttribute("IdEtudiant"));
                                op_cmpt_etud_gen.execute();
                                DCIteratorBinding iter_cmpt_etud_gen1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                                Row row_cmpt_etud_gen1 = iter_cmpt_etud_gen1.getCurrentRow();
                                Integer solde = Integer.parseInt(row_cmpt_etud_gen1.getAttribute("SoldeActuel").toString());
                                while (rsi_not_paie.hasNext()) {
                                    Row nextRow = rsi_not_paie.next();
                        
                                    if(nextRow.getAttribute("NetAPayer") != null){
                                       if(solde >= Integer.parseInt(nextRow.getAttribute("NetAPayer").toString())){
                                          insertOperationDebit(nextRow.getAttribute("NetAPayer").toString(), "2", id_mode_paie.getInputValue().toString(), row_cmpt_etud_gen.getAttribute("IdCompte").toString(), getNum_cheque(), getBanque(), getNum_virment(), "Paiement", "1", "", "1",nextRow.getAttribute("IdPaiement").toString()); 
                                           java.util.Date date_paie = new java.util.Date();
                                           OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdatePaiement");
                                           getUpdateCmpte.getParamsMap().put("id_paie",  nextRow.getAttribute("IdPaiement"));  
                                           getUpdateCmpte.getParamsMap().put("reparti",  1);  
                                           getUpdateCmpte.getParamsMap().put("etat",  1);  
                                           getUpdateCmpte.getParamsMap().put("date_paie",  date_paie);  
                                           getUpdateCmpte.getParamsMap().put("paye",  1);  
                                           getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                                           getUpdateCmpte.execute();
                                           
                                           if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) == 0){
                                               DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
                                               Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
                                               //modification de l'etat d'inscription ped
                                               updateInscriptionPed(row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), "22");
                                           }
                                       }
                                       solde = solde - Integer.parseInt(nextRow.getAttribute("NetAPayer").toString());
                                    }
                        
                                }
                            }
                        
                        }
                    }

                    OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
                    op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
                    op_op_cmpt_etud.getParamsMap().put("id_cmpte",  row_cmpt_etud_gen.getAttribute("IdCompte"));
                    op_op_cmpt_etud.execute();
                }
                else{
                    RichPopup popup = this.getPopCmptEtdNot();                
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
            RichPopup popup = this.getPopRepVide();                 
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoir()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayer()); 
        
        
        String num_recu_prec = "0";
        OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
        op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_annee.execute();
        DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
        Row row_oper_annee = iter_oper_annee.getCurrentRow();
        if(row_oper_annee != null)
            num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
        numero_recu_suivant(num_recu_prec);
        setNum_recu(numero_recu_suivant(num_recu_prec));
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumRecu()); 
        //refrsh montant
        setMontant("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMontant()); 
        //refresh num virement
        setNum_virment("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumVir()); 
        //refresh num cheque
        setNum_cheque("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumChq()); 
        //refresh banque
        setBanque("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBanq()); 
        
        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        OperationBinding op_ped_not_paie = getBindings().getOperationBinding("getInscPedNotPaie");
        op_ped_not_paie.getParamsMap().put("id_annee",  getAnne_univers());
        op_ped_not_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
        op_ped_not_paie.execute();
        
        
        //getInscPedPaie
        OperationBinding op_ped_paie = getBindings().getOperationBinding("getInscPedPaie");
        op_ped_paie.getParamsMap().put("id_annee",  getAnne_univers());
        op_ped_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
        op_ped_paie.execute();
        
        sessionScope.remove("montant_sess_var");
        sessionScope.remove("num_recu_sess_var");
        sessionScope.remove("num_chq_sess_var");
        sessionScope.remove("num_vir_sess_var");
        sessionScope.remove("banq_sess_var");
    }

    public void onDialogCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfPaie().hide();
        
        sessionScope.remove("montant_sess_var");
        sessionScope.remove("num_recu_sess_var");
        sessionScope.remove("num_chq_sess_var");
        sessionScope.remove("num_vir_sess_var");
        sessionScope.remove("banq_sess_var");
    }

    public void setInputRestePayer(RichInputText inputRestePayer) {
        this.inputRestePayer = inputRestePayer;
    }

    public RichInputText getInputRestePayer() {
        return inputRestePayer;
    }

    public void setInputNumRecu(RichInputText inputNumRecu) {
        this.inputNumRecu = inputNumRecu;
    }

    public RichInputText getInputNumRecu() {
        return inputNumRecu;
    }

    public void setInputMontant(RichInputText inputMontant) {
        this.inputMontant = inputMontant;
    }

    public RichInputText getInputMontant() {
        return inputMontant;
    }

    public void setInputNumChq(RichInputText inputNumChq) {
        this.inputNumChq = inputNumChq;
    }

    public RichInputText getInputNumChq() {
        return inputNumChq;
    }

    public void setInputNumVir(RichInputText inputNumVir) {
        this.inputNumVir = inputNumVir;
    }

    public RichInputText getInputNumVir() {
        return inputNumVir;
    }

    public void setInputBanq(RichInputText inputBanq) {
        this.inputBanq = inputBanq;
    }

    public RichInputText getInputBanq() {
        return inputBanq;
    }

    public void setPopConfImpPay(RichPopup popConfImpPay) {
        this.popConfImpPay = popConfImpPay;
    }

    public RichPopup getPopConfImpPay() {
        return popConfImpPay;
    }

    public void onDialogImpRcu(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void onDialogCanImpRcu(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfImpPay().hide();
    }
    @SuppressWarnings("unchecked")
    public void runReport(String repPath, java.util.Map param) throws Exception {
        System.out.println("Running ...");
        Connection conn = null;
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        
        
        try {
            HttpServletResponse response = getResponse();
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Cache-Control", "max-age=0");
            response.setContentType("application/pdf");
                            
                                
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String path = servletContext.getRealPath(repPath);
            ServletContext context = getContext();
            System.out.println("path "+path);
            FileInputStream fs = new FileInputStream(path);
            JasperReport template = (JasperReport) JRLoader.loadObject(fs);
            template.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            
            conn = getConnection();
            @SuppressWarnings("unchecked")
            JasperPrint print = JasperFillManager.fillReport(template, param, conn);
            //Affichage du report
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, baos);
            out.write(baos.toByteArray());
            
            out.flush();
            out.close();
    
            FacesContext.getCurrentInstance().responseComplete();
            
        } catch (Exception jex) {
            System.out.println("Erreur");
            jex.printStackTrace();
        } finally {
            System.out.println("Finnish");
            this.getPopConfImpPay().hide();
            close(conn);
        }
    }

    public Connection getDataSourceConnection(String dataSourceName) throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(dataSourceName);
        return ds.getConnection();
    }

    private Connection getConnection() throws Exception {
        return getDataSourceConnection("jdbc/refDS"); // datasource name should be defined in weblogic
    }

    public ServletContext getContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    } 
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }
    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.insufficient-catch-block" })
    public void onImprimeRecu(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
       
        Map m = new HashMap();
        System.out.println(" id_operat_imp "+sessionScope.get("operat_imp"));
        m.put("id_util", getUtilisateur());
        m.put("id_etud", sessionScope.get("id_etud_imp"));
        m.put("id_hs", sessionScope.get("id_hs"));
        m.put("id_annee", getAnne_univers());
        m.put("id_strc", Long.parseLong(sessionScope.get("id_strct_imp").toString()));//
        m.put("id_oper", sessionScope.get("id_operat_imp"));
        
        
        try {
            System.out.println("id_util inco");
            runReport("/reports/recu_paie.jasper",m);
        } catch (Exception e) {
            System.out.println("erreur "+e.getMessage());
        }
        this.getPopConfImpPay().hide();
        /*sessionScope.remove("id_etud_imp");
        sessionScope.remove("id_strct_imp");
        sessionScope.remove("id_operat_imp");*/

    }

    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("Annule")!=null){
                if(Integer.parseInt(nextRow.getAttribute("Annule").toString()) == 1){
                    i++;
                }
            }
        }
        return i;
    }

    public Integer nombreCaseCocheDepot(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row row_op = rsi.next();
            if(row_op.getAttribute("Annule")!=null){
                if(Integer.parseInt(row_op.getAttribute("Annule").toString()) == 1 ){
                    if(Integer.parseInt(row_op.getAttribute("TypeOperation").toString()) == 1){
                        i++;
                    }
                }
            }
        }
        return i;
    }

    public Integer nombreCaseCocheRetrait(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row row_op = rsi.next();
            if(row_op.getAttribute("Annule")!=null){
                if(Integer.parseInt(row_op.getAttribute("Annule").toString()) == 1 ){
                    if(Integer.parseInt(row_op.getAttribute("TypeOperation").toString()) == 2){
                        i++;
                    }
                }
            }
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public void onAnnulerPaiement(ActionEvent actionEvent) {
        OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
        op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
        op_form_hs.execute();
        DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
        Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
        if(row_op_form_hs != null){
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            if(row_list_insc_ped != null){
                
                DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedPaieEcolROIterator");
                Row row_paie = iter_paie.getCurrentRow();
                RowSetIterator rsi_paie = iter_paie.getViewObject().createRowSetIterator(null);
                
                if(nombreCaseCoche("InscPedPaieEcolROIterator") > 0){
                    
                    OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                    op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_etud_gen.getParamsMap().put("id_strct",  row_op_form_hs.getAttribute("IdStr"));
                    op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_paie.getAttribute("IdEtudiant"));
                    op_cmpt_etud_gen.execute();
                    DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    
                    if(row_cmpt_etud_gen != null){
                        
                        setNombre_case(nombreCaseCoche("InscPedPaieEcolROIterator")+"");                   
                        RichPopup popup = this.getPopConfAnnPaie();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    else{
                        // Le compte de l'étud n'est pas encore généré
                        RichPopup popup = this.getPopCmptEtdNot();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    //pas de case coché
                    RichPopup popup = this.getPopAnnPaieNot();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
            }
            else{
                RichPopup popup = this.getPopEtudNotInsc();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void restituer(Integer montant_rep, String id_struct){
        OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
        op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct);
        op_cmpt_etb_gen.execute();
        DCIteratorBinding iter_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
        Row row_cmpt_etb_gen = iter_annee_passee.getCurrentRow();
        if(row_cmpt_etb_gen != null){
            OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
            op_rep_exist_cle.getParamsMap().put("id_rep",  row_cmpt_etb_gen.getAttribute("IdRepartition"));
            op_rep_exist_cle.execute();
            
            Integer nb = 0;
            if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){
                DCIteratorBinding iter_rep_ex_cle = (DCIteratorBinding) getBindings().get("RepExisteCleRepROIterator");       
                RowSetIterator rsi_rep_ex_cle = iter_rep_ex_cle.getViewObject().createRowSetIterator(null);
                while (rsi_rep_ex_cle.hasNext()) {
                    Row row_rep_ex_cle = rsi_rep_ex_cle.next();
                    //getCmpteTypeCmptRepart
                    Double solde = ((Double.parseDouble(row_rep_ex_cle.getAttribute("Pourcentage").toString())*montant_rep)/100);
                    OperationBinding op_cmpt_type_repart = getBindings().getOperationBinding("getCmpteTypeCmptRepart");
                    op_cmpt_type_repart.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_type_repart.getParamsMap().put("id_strct",  id_struct);
                    op_cmpt_type_repart.getParamsMap().put("type_cmpte", row_rep_ex_cle.getAttribute("IdTypeCompte"));
                    op_cmpt_type_repart.execute();
                    DCIteratorBinding iter_op_cmpt_type_repart = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteTypeCmpteRepartROIterator");
                    Row row_cmpt_type_repart = iter_op_cmpt_type_repart.getCurrentRow();
                    
                    Double solde_act = null;
                    if(row_cmpt_type_repart.getAttribute("SoldeActuel") != null)
                        solde_act = (Double.parseDouble(row_cmpt_type_repart.getAttribute("SoldeActuel").toString()) - solde);
                    else
                        solde_act = 0 - solde;
                    Integer sold = solde_act.intValue();
                    OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                    getUpdateCmpte.getParamsMap().put("id_solde_compte",  row_cmpt_type_repart.getAttribute("IdSoldeCompte"));  
                    getUpdateCmpte.getParamsMap().put("solde_act",  sold);  
                    getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                    getUpdateCmpte.execute();
                    
                    if(Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 6 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 7 
                       || Integer.parseInt(row_rep_ex_cle.getAttribute("IdTypeCompte").toString()) == 8){
                        refresh_cmpte_glob(id_struct, sessionScope.get("id_hs").toString(), solde.intValue()+"");
                       }
                    
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void refresh_cmpte_glob(String strc,String hs,String montant){
        Integer solde = 0;
        OperationBinding op_oper_cmpt_glob = getBindings().getOperationBinding("getRechCmpteGlob");
        op_oper_cmpt_glob.getParamsMap().put("id_strct",  strc);
        op_oper_cmpt_glob.getParamsMap().put("type_cmpte",  "21");      // id_type_compte : formation globale
        op_oper_cmpt_glob.getParamsMap().put("id_hs",  hs);
        op_oper_cmpt_glob.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_cmpt_glob.execute();
        DCIteratorBinding iter_oper_cmpt_glob = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RechCompteGlobROIterator");
        Row row_oper_cmpt_glob = iter_oper_cmpt_glob.getCurrentRow();
        if(row_oper_cmpt_glob != null){
            if(row_oper_cmpt_glob.getAttribute("SoldeActuel") != null){ 
                solde = ( Integer.parseInt(row_oper_cmpt_glob.getAttribute("SoldeActuel").toString()) - Integer.parseInt(montant));
            }
            OperationBinding getUpdateCmpteGlob = getBindings().getOperationBinding("findAndUpdateCompteEtud");
            getUpdateCmpteGlob.getParamsMap().put("id_solde_compte",  row_oper_cmpt_glob.getAttribute("IdSoldeCompte"));  
            getUpdateCmpteGlob.getParamsMap().put("solde_act",  solde);  
            getUpdateCmpteGlob.getParamsMap().put("util",  getUtilisateur());  
            getUpdateCmpteGlob.execute();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void refresh_cmpte_glob_encaisse(String strc,String hs,String montant){
        Integer solde = 0;
        OperationBinding op_oper_cmpt_glob = getBindings().getOperationBinding("getRechCmpteGlob");
        op_oper_cmpt_glob.getParamsMap().put("id_strct",  strc);
        op_oper_cmpt_glob.getParamsMap().put("type_cmpte",  "21");      // id_type_compte : formation globale
        op_oper_cmpt_glob.getParamsMap().put("id_hs",  hs);
        op_oper_cmpt_glob.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_cmpt_glob.execute();
        DCIteratorBinding iter_oper_cmpt_glob = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RechCompteGlobROIterator");
        Row row_oper_cmpt_glob = iter_oper_cmpt_glob.getCurrentRow();
        if(row_oper_cmpt_glob != null){
            if(row_oper_cmpt_glob.getAttribute("SoldeActuel") != null){ 
                solde = ( Integer.parseInt(row_oper_cmpt_glob.getAttribute("SoldeActuel").toString()) + Integer.parseInt(montant));
            }
            OperationBinding getUpdateCmpteGlob = getBindings().getOperationBinding("findAndUpdateCompteEtud");
            getUpdateCmpteGlob.getParamsMap().put("id_solde_compte",  row_oper_cmpt_glob.getAttribute("IdSoldeCompte"));  
            getUpdateCmpteGlob.getParamsMap().put("solde_act",  solde);  
            getUpdateCmpteGlob.getParamsMap().put("util",  getUtilisateur());  
            getUpdateCmpteGlob.execute();
        }
    }

    public void setPopConfAnnPaie(RichPopup popConfAnnPaie) {
        this.popConfAnnPaie = popConfAnnPaie;
    }

    public RichPopup getPopConfAnnPaie() {
        return popConfAnnPaie;
    }

    @SuppressWarnings("unchecked")
    public void onDialogAnnPaie(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
            op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
            op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
            op_form_hs.execute();
            DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
            Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
            if(row_op_form_hs != null){
                DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedPaieEcolROIterator");
                Row row_paie = iter_paie.getCurrentRow();
                RowSetIterator rsi_paie = iter_paie.getViewObject().createRowSetIterator(null);
                
                while (rsi_paie.hasNext()) {
                    Row nextRow = rsi_paie.next();
        
                    if(nextRow.getAttribute("Annule")!=null){
                        if(Integer.parseInt(nextRow.getAttribute("Annule").toString()) == 1 ){
                            System.out.println("Annule "+nextRow.getAttribute("Annule"));
                            System.out.println("LibelleEchelon "+nextRow.getAttribute("LibelleEchelon"));
                            
                            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                            op_cmpt_etud_gen.getParamsMap().put("id_strct",  row_op_form_hs.getAttribute("IdStr").toString());
                            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_paie.getAttribute("IdEtudiant"));
                            op_cmpt_etud_gen.execute();
                            DCIteratorBinding iter_cmpt_etud_gen1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                            Row row_cmpt_etud_gen1 = iter_cmpt_etud_gen1.getCurrentRow();
                            Integer solde = (Integer.parseInt(row_cmpt_etud_gen1.getAttribute("SoldeActuel").toString()) + Integer.parseInt(nextRow.getAttribute("Montant").toString()));
                            
                            
                            OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdatePaiement");
                            getUpdateCmpte.getParamsMap().put("id_paie",  nextRow.getAttribute("IdPaiement"));  
                            getUpdateCmpte.getParamsMap().put("reparti",  0);  
                            getUpdateCmpte.getParamsMap().put("etat",  0);  
                            getUpdateCmpte.getParamsMap().put("date_paie",  null);  
                            getUpdateCmpte.getParamsMap().put("paye",  0);  
                            getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateCmpte.execute();
                            
                            if(Integer.parseInt(nextRow.getAttribute("Ordre").toString()) == 0){
                                DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
                                Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
                                //modification de l'etat d'inscription ped
                                updateInscriptionPed(row_list_insc_ped.getAttribute("IdInscriptionPedagogique").toString(), "21");
                            }
                            
                            //restituer
                            OperationBinding getUpdateCmpteEtud = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                            getUpdateCmpteEtud.getParamsMap().put("id_solde_compte",  row_cmpt_etud_gen1.getAttribute("IdSoldeCompte"));  
                            getUpdateCmpteEtud.getParamsMap().put("solde_act",  solde);  
                            getUpdateCmpteEtud.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateCmpteEtud.execute();
                            
                            restituer(Integer.parseInt(nextRow.getAttribute("Montant").toString()), row_op_form_hs.getAttribute("IdStr").toString());
                            
                            OperationBinding op_oper = getBindings().getOperationBinding("getOperPaie");
                            op_oper.getParamsMap().put("id_annee",  getAnne_univers());
                            op_oper.getParamsMap().put("id_cmpt",  row_cmpt_etud_gen1.getAttribute("IdCompte"));
                            op_oper.getParamsMap().put("id_paie",  nextRow.getAttribute("IdPaiement"));
                            op_oper.getParamsMap().put("id_type_op",  "2");
                            op_oper.execute();
                            DCIteratorBinding iter_op_oper = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RecherchOperPaieROIterator");
                            Row row_op_oper = iter_op_oper.getCurrentRow();
                            //findAndUpdateOperation
                            OperationBinding getUpdateOperation = getBindings().getOperationBinding("findAndUpdateOperation");
                            getUpdateOperation.getParamsMap().put("id_oper",  row_op_oper.getAttribute("IdOperation"));  
                            getUpdateOperation.getParamsMap().put("annule",  "1");  
                            getUpdateOperation.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateOperation.execute();

                            String banq = "";
                            String num_vir = "";
                            String numero_chq = "";
                            String motif = "";
                            String val = "";
                            String paie = "";
                            
                            if(row_op_oper.getAttribute("NumeroCheque") != null)
                                numero_chq = row_op_oper.getAttribute("NumeroCheque").toString();
                            if(row_op_oper.getAttribute("Banque") != null)
                                banq = row_op_oper.getAttribute("Banque").toString();
                            if(row_op_oper.getAttribute("NumeroVirement") != null)
                                num_vir = row_op_oper.getAttribute("NumeroVirement").toString();
                            if(row_op_oper.getAttribute("Motif") != null)
                                motif = row_op_oper.getAttribute("Motif").toString();
                            if(row_op_oper.getAttribute("Valide") != null)
                                val = row_op_oper.getAttribute("Valide").toString();
                            if(row_op_oper.getAttribute("Paiement") != null)
                                paie = row_op_oper.getAttribute("Paiement").toString();

                            insertOperationDelete(row_op_oper.getAttribute("Montant").toString(),row_op_oper.getAttribute("TypeOperation").toString(),row_op_oper.getAttribute("IdModePaiement").toString(),row_op_oper.getAttribute("IdCompte").toString(),numero_chq,banq,num_vir,motif,"0",val,paie,row_op_oper.getAttribute("IdOperation").toString());
                            
                        }
                    }
        
                } 
            }
            // refresh les tableaux
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            if(row_list_insc_ped != null){
                
                OperationBinding op_ped_not_paie = getBindings().getOperationBinding("getInscPedNotPaie");
                op_ped_not_paie.getParamsMap().put("id_annee",  getAnne_univers());
                op_ped_not_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
                op_ped_not_paie.execute();
                
                
                //getInscPedPaie
                OperationBinding op_ped_paie = getBindings().getOperationBinding("getInscPedPaie");
                op_ped_paie.getParamsMap().put("id_annee",  getAnne_univers());
                op_ped_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
                op_ped_paie.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoirSitu()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayerSitua()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMensSitua());
        }

    }

    public void onDialogAnnPaieCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfAnnPaie().hide();
    }

    public void setPopAnnPaieNot(RichPopup popAnnPaieNot) {
        this.popAnnPaieNot = popAnnPaieNot;
    }

    public RichPopup getPopAnnPaieNot() {
        return popAnnPaieNot;
    }
    
    @SuppressWarnings("unchecked")
    public void insertOperationDelete(String montant,String type,String mode_paie,String compte,String cheq,String banq,String virement,String motif_paie,String debiter,String valider,String paie,String id_op_annul){
        AttributeBinding montant_oper = (AttributeBinding) getBindings().getControlBinding("MontantOper");
        AttributeBinding date_oper = (AttributeBinding) getBindings().getControlBinding("DateOperation");
        AttributeBinding type_oper = (AttributeBinding) getBindings().getControlBinding("TypeOperation");
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiementOper");
        AttributeBinding id_compte = (AttributeBinding) getBindings().getControlBinding("IdCompteOper");
        AttributeBinding num_cheq = (AttributeBinding) getBindings().getControlBinding("NumeroChequeOper");
        AttributeBinding banque = (AttributeBinding) getBindings().getControlBinding("BanqueOper");
        AttributeBinding num_virement = (AttributeBinding) getBindings().getControlBinding("NumeroVirementOper");
        AttributeBinding motif = (AttributeBinding) getBindings().getControlBinding("MotifOper");
        AttributeBinding benif = (AttributeBinding) getBindings().getControlBinding("BeneficiareOper");
        AttributeBinding debit = (AttributeBinding) getBindings().getControlBinding("DebitOper");
        AttributeBinding num_recu = (AttributeBinding) getBindings().getControlBinding("NumeroRecuOper");
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("ValideOper");
        AttributeBinding num_mandant = (AttributeBinding) getBindings().getControlBinding("NumeroMandatOper");
        AttributeBinding annule = (AttributeBinding) getBindings().getControlBinding("AnnuleOper");
        
        AttributeBinding op_annuler = (AttributeBinding) getBindings().getControlBinding("OpAnnuleOper");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUniversOper");
        AttributeBinding id_util_cree = (AttributeBinding) getBindings().getControlBinding("UtiCreeOper");
        AttributeBinding id_util_mod = (AttributeBinding) getBindings().getControlBinding("UtiModifieOper");
        //PaiementOper
        AttributeBinding paiement_op = (AttributeBinding) getBindings().getControlBinding("PaiementOper");
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");

        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertOperation");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return ;
        }
        java.util.Date utilDate = new java.util.Date();
        
        montant_oper.setInputValue(montant);
        date_oper.setInputValue(utilDate);
        type_oper.setInputValue(type);
        id_mode_paie.setInputValue(mode_paie);
        id_compte.setInputValue(compte);
        num_cheq.setInputValue(cheq);
        banque.setInputValue(banq);
        num_virement.setInputValue(virement);
        motif.setInputValue(motif_paie);
        debit.setInputValue(debiter);
        //num_recu.setInputValue(numero_recu);
        valide.setInputValue(valider);
        id_util_cree.setInputValue(getUtilisateur());
        op_annuler.setInputValue(id_op_annul);
        paiement_op.setInputValue(paie);
        
        
        OperationBinding op_commit_op = getBindings().getOperationBinding("Commit");
        Object result_oper = op_commit_op.execute();
        if (!op_commit_op.getErrors().isEmpty()) {
                return ;
        }
        else{
        }
    }

    public void setPopConfAjoutSolde(RichPopup popConfAjoutSolde) {
        this.popConfAjoutSolde = popConfAjoutSolde;
    }

    public RichPopup getPopConfAjoutSolde() {
        return popConfAjoutSolde;
    }

    @SuppressWarnings("unchecked")
    public void onDialogAjoutSolde(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        java.util.Date utilDate = new java.util.Date();
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructures");
        AttributeBinding num_recu = (AttributeBinding) getBindings().getControlBinding("NumeroRecu");
        if(sessionScope.get("montant_sess_var") != null)
            setMontant(sessionScope.get("montant_sess_var").toString());
        else
            setMontant("");
        setNum_recu(sessionScope.get("num_recu_sess_var").toString());
        if(sessionScope.get("num_chq_sess_var") != null){
            setNum_cheque(sessionScope.get("num_chq_sess_var").toString());           
        }
        else
            setNum_cheque("");
        
        if(sessionScope.get("num_vir_sess_var") != null)
            setNum_virment(sessionScope.get("num_vir_sess_var").toString());
        else
            setNum_virment("");

        if(sessionScope.get("banq_sess_var") != null)
            setBanque(sessionScope.get("banq_sess_var").toString());
        else
            setBanque("");
        System.out.println("sessionScope.get(num_vir_sess_var) "+sessionScope.get("num_vir_sess_var"));
        Map reqScope = ADFContext.getCurrent().getRequestScope();
        reqScope.get("montant_req_var");
        System.out.println("getMontant "+getMontant());
        System.out.println("mont avant "+reqScope.get("montant_req_var"));
        
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            OperationBinding op_cmpt_etb_gen = getBindings().getOperationBinding("getRepRechStrAnn");
            op_cmpt_etb_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etb_gen.getParamsMap().put("id_strc",  id_struct.getInputValue());
            op_cmpt_etb_gen.execute();
            DCIteratorBinding iter_rep_str = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RepRechStrcAnnROIterator");
            Row row_rep_str = iter_rep_str.getCurrentRow();
            if(row_rep_str != null){
                OperationBinding op_rep_exist_cle = getBindings().getOperationBinding("getRepExisteCleRep");
                op_rep_exist_cle.getParamsMap().put("id_rep",  row_rep_str.getAttribute("IdRepartition"));
                op_rep_exist_cle.execute();
      
                if(totalPourcentageCaseCoche("RepExisteCleRepROIterator") == 100){        
                    if(getMontant() != null){      
                        DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                        Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                        if(row_cmpt_etud_gen != null){ 
                            insertOperationSaisie(getMontant(), "1", id_mode_paie.getInputValue().toString(), row_cmpt_etud_gen.getAttribute("IdCompte").toString(), getNum_cheque(), getBanque(), getNum_virment(), "Paiement", "0", getNum_recu(), "1");
                            
                            AttributeBinding id_operation = (AttributeBinding) getBindings().getControlBinding("IdOperation");
                            
                            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
                            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
                            
                            sessionScope.put("id_operat_imp", id_operation.getInputValue());
                            sessionScope.put("id_strct_imp", id_struct.getInputValue());
                            sessionScope.put("id_etud_imp", row_cmpt_etud_gen.getAttribute("IdEtudiant"));
                            
                            sessionScope.put("num_etud_paie", row_list_insc_ped.getAttribute("Numero"));
                            sessionScope.put("prenom_etud_paie", row_list_insc_ped.getAttribute("Prenom"));
                            sessionScope.put("nom_etud_paie", row_list_insc_ped.getAttribute("Nom"));
                            sessionScope.put("num_recu_paie", num_recu.getInputValue());
                            
                            RichPopup popup = this.getPopConfImpPay();                 
                            RichPopup.PopupHints hints = new RichPopup.PopupHints();
                            popup.show(hints);
                        }
                    }
                }
            }       
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoir()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayer()); 
        
        DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
        Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
        
        OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
        op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
        op_op_cmpt_etud.getParamsMap().put("id_cmpte",  row_cmpt_etud_gen.getAttribute("IdCompte"));
        op_op_cmpt_etud.execute();
        
        String num_recu_prec = "0";
        OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
        op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_annee.execute();
        DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
        Row row_oper_annee = iter_oper_annee.getCurrentRow();
        if(row_oper_annee != null)
            num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
        numero_recu_suivant(num_recu_prec);
        setNum_recu(numero_recu_suivant(num_recu_prec));
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumRecu()); 
        //refrsh montant
        setMontant("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMontant()); 
        //refresh num virement
        setNum_virment("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumVir()); 
        //refresh num cheque
        setNum_cheque("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumChq()); 
        //refresh banque

        setBanque("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBanq());

        sessionScope.remove("montant_sess_var");
        sessionScope.remove("num_recu_sess_var");
        sessionScope.remove("num_chq_sess_var");
        sessionScope.remove("num_vir_sess_var");
        sessionScope.remove("banq_sess_var");
        //sessionScope.
    }

    public void onDialogCanAjoutSolde(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfAjoutSolde().hide();
        
        sessionScope.remove("montant_sess_var");
        sessionScope.remove("num_recu_sess_var");
        sessionScope.remove("num_chq_sess_var");
        sessionScope.remove("num_vir_sess_var");
        sessionScope.remove("banq_sess_var");
    }

    public void setPopSoldInsu(RichPopup popSoldInsu) {
        this.popSoldInsu = popSoldInsu;
    }

    public RichPopup getPopSoldInsu() {
        return popSoldInsu;
    }

    public void setCheck(RichSelectBooleanCheckbox check) {
        this.check = check;
    }

    public RichSelectBooleanCheckbox getCheck() {
        return check;
    }

    @SuppressWarnings("unchecked")
    public void onAnnulerOperations(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
        op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
        op_form_hs.execute();
        DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
        Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
        if(row_op_form_hs != null){
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            if(row_list_insc_ped != null){
                               
                DCIteratorBinding iter_paie = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedPaieEcolROIterator");
                Row row_paie = iter_paie.getCurrentRow();
                RowSetIterator rsi_paie = iter_paie.getViewObject().createRowSetIterator(null);
                
                if(nombreCaseCocheDepot("lesOperationsEtudROIterator") > 0){
                    
                    OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                    op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                    op_cmpt_etud_gen.getParamsMap().put("id_strct",  row_op_form_hs.getAttribute("IdStr"));
                    op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_paie.getAttribute("IdEtudiant"));
                    op_cmpt_etud_gen.execute();
                    DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                    Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                    
                    if(row_cmpt_etud_gen != null){
                        
                        setNombre_case(nombreCaseCocheDepot("lesOperationsEtudROIterator")+"");                   
                        RichPopup popup = this.getPopConfAnnOper();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    else{
                        // Le compte de l'étud n'est pas encore généré
                        RichPopup popup = this.getPopCmptEtdNot();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
                else{
                    if(nombreCaseCocheRetrait("lesOperationsEtudROIterator") > 0){
                        // case coché est de type retrait autrement dit un paiement de mensualité
                        setNombre_case(nombreCaseCocheRetrait("lesOperationsEtudROIterator")+"");
                        RichPopup popup = this.getPopConfAnnOperNotPaie();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    else{
                        //pas de case coché
                        RichPopup popup = this.getPopConfAnnOperNot();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                }
            }
            else{
                RichPopup popup = this.getPopEtudNotInsc();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }       
    }

    @SuppressWarnings("unchecked")
    public void annulerOperationEtud(Row nextRow, String montant, String id_etud){   
        OperationBinding op_form_hs = getBindings().getOperationBinding("getFormHist");
        op_form_hs.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_form_hs.getParamsMap().put("niv_form_parc",  sessionScope.get("id_niv_form_parcours"));
        op_form_hs.execute();
        DCIteratorBinding iter_op_form_hs = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayHstFormParcROIterator");
        Row row_op_form_hs = iter_op_form_hs.getCurrentRow();
        if(row_op_form_hs != null){
    
            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
            op_cmpt_etud_gen.getParamsMap().put("id_strct",  row_op_form_hs.getAttribute("IdStr").toString());
            op_cmpt_etud_gen.getParamsMap().put("id_etud",  id_etud);
            op_cmpt_etud_gen.execute();
            DCIteratorBinding iter_cmpt_etud_gen1 = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
            Row row_cmpt_etud_gen1 = iter_cmpt_etud_gen1.getCurrentRow();
            Integer solde = (Integer.parseInt(row_cmpt_etud_gen1.getAttribute("SoldeActuel").toString()) + Integer.parseInt(montant));
            
            
            
            OperationBinding op_oper = getBindings().getOperationBinding("getOperPaie");
            op_oper.getParamsMap().put("id_annee",  getAnne_univers());
            op_oper.getParamsMap().put("id_cmpt",  row_cmpt_etud_gen1.getAttribute("IdCompte"));
            op_oper.getParamsMap().put("id_paie",  nextRow.getAttribute("Paiement"));
            op_oper.getParamsMap().put("id_type_op",  "2");
            op_oper.execute();
            DCIteratorBinding iter_op_oper = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("RecherchOperPaieROIterator");
            Row row_op_oper = iter_op_oper.getCurrentRow();
            //findAndUpdateOperation
            
            String banq = "";
            String num_vir = "";
            String numero_chq = "";
            String motif = "";
            String val = "";
            String paie = "";
            
            if(row_op_oper.getAttribute("NumeroCheque") != null)
                numero_chq = row_op_oper.getAttribute("NumeroCheque").toString();
            if(row_op_oper.getAttribute("Banque") != null)
                banq = row_op_oper.getAttribute("Banque").toString();
            if(row_op_oper.getAttribute("NumeroVirement") != null)
                num_vir = row_op_oper.getAttribute("NumeroVirement").toString();
            if(row_op_oper.getAttribute("Motif") != null)
                motif = row_op_oper.getAttribute("Motif").toString();
            if(row_op_oper.getAttribute("Valide") != null)
                val = row_op_oper.getAttribute("Valide").toString();
            if(row_op_oper.getAttribute("Paiement") != null)
                paie = row_op_oper.getAttribute("Paiement").toString();
    
            insertOperationDelete(row_op_oper.getAttribute("Montant").toString(),row_op_oper.getAttribute("TypeOperation").toString(),row_op_oper.getAttribute("IdModePaiement").toString(),row_op_oper.getAttribute("IdCompte").toString(),numero_chq,banq,num_vir,motif,"0",val,paie,row_op_oper.getAttribute("IdOperation").toString());
            
            
            OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdatePaiement");
            getUpdateCmpte.getParamsMap().put("id_paie",  nextRow.getAttribute("Paiement"));  
            getUpdateCmpte.getParamsMap().put("reparti",  0);  
            getUpdateCmpte.getParamsMap().put("etat",  0);  
            getUpdateCmpte.getParamsMap().put("date_paie",  null);  
            getUpdateCmpte.getParamsMap().put("paye",  0);  
            getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
            getUpdateCmpte.execute();
            
            //getOperPaieAnnuleDIPRO
            OperationBinding getOperAnnDip = getBindings().getOperationBinding("getOperPaieAnnuleDIPRO");
            getOperAnnDip.getParamsMap().put("id_paie",  nextRow.getAttribute("Paiement")); 
            getOperAnnDip.execute();
            DCIteratorBinding iter_oper_ann_dip = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperPaieAnnuleDIPROIterator");
            Row row_oper_ann_dip = iter_oper_ann_dip.getCurrentRow();
            if(row_oper_ann_dip != null){
                //IdInscriptionPedagogique
                updateInscriptionPed(row_oper_ann_dip.getAttribute("IdInscriptionPedagogique").toString(), "21");
            }
            
            
            OperationBinding getUpdateOperation = getBindings().getOperationBinding("findAndUpdateOperation");
            getUpdateOperation.getParamsMap().put("id_oper",  row_op_oper.getAttribute("IdOperation"));  
            getUpdateOperation.getParamsMap().put("annule",  "1");  
            getUpdateOperation.getParamsMap().put("util",  getUtilisateur());  
            getUpdateOperation.execute();
            
            restituer(Integer.parseInt(nextRow.getAttribute("Montant").toString()), row_op_form_hs.getAttribute("IdStr").toString());
            
            OperationBinding getUpdateCmpteEtud = getBindings().getOperationBinding("findAndUpdateCompteEtud");
            getUpdateCmpteEtud.getParamsMap().put("id_solde_compte",  row_cmpt_etud_gen1.getAttribute("IdSoldeCompte"));  
            getUpdateCmpteEtud.getParamsMap().put("solde_act",  solde);  
            getUpdateCmpteEtud.getParamsMap().put("util",  getUtilisateur());  
            getUpdateCmpteEtud.execute(); 
        }
    }

    @SuppressWarnings("unchecked")
    public void updateDaleteOperation(Row row_op_oper){ 
    
        String banq = "";
        String num_vir = "";
        String numero_chq = "";
        String motif = "";
        String val = "";
        String paie = "";
        
        if(row_op_oper.getAttribute("NumeroCheque") != null)
            numero_chq = row_op_oper.getAttribute("NumeroCheque").toString();
        if(row_op_oper.getAttribute("Banque") != null)
            banq = row_op_oper.getAttribute("Banque").toString();
        if(row_op_oper.getAttribute("NumeroVirement") != null)
            num_vir = row_op_oper.getAttribute("NumeroVirement").toString();
        if(row_op_oper.getAttribute("Motif") != null)
            motif = row_op_oper.getAttribute("Motif").toString();
        if(row_op_oper.getAttribute("Valide") != null)
            val = row_op_oper.getAttribute("Valide").toString();
        if(row_op_oper.getAttribute("Paiement") != null)
            paie = row_op_oper.getAttribute("Paiement").toString();
        
        insertOperationDelete(row_op_oper.getAttribute("Montant").toString(),row_op_oper.getAttribute("TypeOperation").toString(),row_op_oper.getAttribute("IdModePaiement").toString(),row_op_oper.getAttribute("IdCompte").toString(),numero_chq,banq,num_vir,motif,"0",val,paie,row_op_oper.getAttribute("IdOperation").toString());
        
        OperationBinding getUpdateOperation = getBindings().getOperationBinding("findAndUpdateOperation");
        getUpdateOperation.getParamsMap().put("id_oper",  row_op_oper.getAttribute("IdOperation"));  
        getUpdateOperation.getParamsMap().put("annule",  "1");  
        getUpdateOperation.getParamsMap().put("util",  getUtilisateur());  
        getUpdateOperation.execute();
    }

    public void setPopConfAnnOper(RichPopup popConfAnnOper) {
        this.popConfAnnOper = popConfAnnOper;
    }

    public RichPopup getPopConfAnnOper() {
        return popConfAnnOper;
    }

    public void onDialogConfAnnOperCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfAnnOper().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogConfAnnOper(DialogEvent dialogEvent) {
        // Add event code here...
        DCIteratorBinding iter_les_op_etud = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("lesOperationsEtudROIterator");
        Row row_les_op_etud = iter_les_op_etud.getCurrentRow();
        RowSetIterator rsi_les_op_etud = iter_les_op_etud.getViewObject().createRowSetIterator(null);
        
        DCIteratorBinding iter_cmpt_etud_gen_anc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
        Row row_cmpt_etud_gen_an = iter_cmpt_etud_gen_anc.getCurrentRow();
        
        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
        
        if(row_list_insc_ped != null){
                
            while (rsi_les_op_etud.hasNext()) {
                Row row_op = rsi_les_op_etud.next();
                if(row_op.getAttribute("Annule")!=null){
                    if(Integer.parseInt(row_op.getAttribute("Annule").toString()) == 1 ){
                        if(Integer.parseInt(row_op.getAttribute("TypeOperation").toString()) == 1){
                            OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
                            op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
                            op_cmpt_etud_gen.getParamsMap().put("id_strct",  row_list_insc_ped.getAttribute("IdStructures"));
                            op_cmpt_etud_gen.getParamsMap().put("id_etud",  row_list_insc_ped.getAttribute("IdEtudiant"));
                            op_cmpt_etud_gen.execute();
                            DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
                            Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                            
                            Integer solde = Integer.parseInt(row_cmpt_etud_gen.getAttribute("SoldeActuel").toString());
                            Integer montant = Integer.parseInt(row_op.getAttribute("Montant").toString());
                            
                            /*
                             * le montant de l'opération a annulé si le montant est > au solde actuel du compte de l'étudiant
                             * (si le reste = 0) entier (montant / mensualite ): donne le nombre de mois à annuler 
                             * (si le reste > 0) entier (montant / mensualite ) + 1 : donne le nombre de mois à annuler
                             * (nombre de mois à annuler * la mensualité) - le montant : le solde à ajouter dans le solde actuel
                             */
                            
                            if(solde >= montant){
                                OperationBinding getUpdateCmpteEtud = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                                getUpdateCmpteEtud.getParamsMap().put("id_solde_compte",  row_cmpt_etud_gen.getAttribute("IdSoldeCompte"));  
                                getUpdateCmpteEtud.getParamsMap().put("solde_act",  (solde - montant));  
                                getUpdateCmpteEtud.getParamsMap().put("util",  getUtilisateur());  
                                getUpdateCmpteEtud.execute();
                            }
                            else{
                                OperationBinding op_operation_payees = getBindings().getOperationBinding("getLesOperationsPayees");
                                op_operation_payees.getParamsMap().put("id_annee",  getAnne_univers());
                                op_operation_payees.getParamsMap().put("id_cmpte",  row_op.getAttribute("IdCompte"));
                                op_operation_payees.getParamsMap().put("type_op",  "2");
                                op_operation_payees.execute();
                                DCIteratorBinding iter_op_payees = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("lesOperationsPayeesROIterator");
                                Row row_op_payees = iter_op_payees.getCurrentRow();
                                //Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
                                RowSetIterator rsi_op_payees = iter_op_payees.getViewObject().createRowSetIterator(null);
        
                                while (rsi_op_payees.hasNext()) {
                                    Row row_op_payes = rsi_op_payees.next();
                                    if(montant >= Integer.parseInt(row_op_payes.getAttribute("Montant").toString())){
                                        annulerOperationEtud(row_op_payes,row_op_payes.getAttribute("Montant").toString(),row_list_insc_ped.getAttribute("IdEtudiant").toString());
                                    }
                                    else{
                                        if(montant < Integer.parseInt(row_op_payes.getAttribute("Montant").toString()) && montant > 0){
                                            Integer monbtant_restant = (Integer.parseInt(row_op_payes.getAttribute("Montant").toString()) - montant);
                                            annulerOperationEtud(row_op_payes,monbtant_restant+"",row_list_insc_ped.getAttribute("IdEtudiant").toString());
        
                                        }
                                    }
                                    montant = montant - Integer.parseInt(row_op_payes.getAttribute("Montant").toString());                                    
                                }                                
                            }
                            //insert l'opération courante avec l'état annulé
                            updateDaleteOperation(row_op);                            
                        }
                    }
                }
            }
            
            OperationBinding op_ped_not_paie = getBindings().getOperationBinding("getInscPedNotPaie");
            op_ped_not_paie.getParamsMap().put("id_annee",  getAnne_univers());
            op_ped_not_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
            op_ped_not_paie.execute();
                        
            //getInscPedPaie
            OperationBinding op_ped_paie = getBindings().getOperationBinding("getInscPedPaie");
            op_ped_paie.getParamsMap().put("id_annee",  getAnne_univers());
            op_ped_paie.getParamsMap().put("id_ped",  row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
            op_ped_paie.execute();
        }
        //refresh le tableau des opérations
        OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
        op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
        op_op_cmpt_etud.getParamsMap().put("id_cmpte",  row_cmpt_etud_gen_an.getAttribute("IdCompte"));
        op_op_cmpt_etud.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoirOper()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayerOper());
    }

    public void setPopConfAnnOperNot(RichPopup popConfAnnOperNot) {
        this.popConfAnnOperNot = popConfAnnOperNot;
    }

    public RichPopup getPopConfAnnOperNot() {
        return popConfAnnOperNot;
    }

    public void setPopConfAnnOperNotPaie(RichPopup popConfAnnOperNotPaie) {
        this.popConfAnnOperNotPaie = popConfAnnOperNotPaie;
    }

    public RichPopup getPopConfAnnOperNotPaie() {
        return popConfAnnOperNotPaie;
    }

    public void setPopPaieTsMois(RichPopup popPaieTsMois) {
        this.popPaieTsMois = popPaieTsMois;
    }

    public RichPopup getPopPaieTsMois() {
        return popPaieTsMois;
    }

    public void setInputAvoirSitu(RichInputText inputAvoirSitu) {
        this.inputAvoirSitu = inputAvoirSitu;
    }

    public RichInputText getInputAvoirSitu() {
        return inputAvoirSitu;
    }

    public void setInputRestePayerSitua(RichInputText inputRestePayerSitua) {
        this.inputRestePayerSitua = inputRestePayerSitua;
    }

    public RichInputText getInputRestePayerSitua() {
        return inputRestePayerSitua;
    }

    public void setInputMensSitua(RichInputText inputMensSitua) {
        this.inputMensSitua = inputMensSitua;
    }

    public RichInputText getInputMensSitua() {
        return inputMensSitua;
    }

    public void setInputAvoirOper(RichInputText inputAvoirOper) {
        this.inputAvoirOper = inputAvoirOper;
    }

    public RichInputText getInputAvoirOper() {
        return inputAvoirOper;
    }

    public void setInputRestePayerOper(RichInputText inputRestePayerOper) {
        this.inputRestePayerOper = inputRestePayerOper;
    }

    public RichInputText getInputRestePayerOper() {
        return inputRestePayerOper;
    }

    public void onActionListenRecu(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("recu avant");
    }

    public void setBoutton1(RichButton boutton1) {
        this.boutton1 = boutton1;
    }

    public RichButton getBoutton1() {
        return boutton1;
    }

    public void onCancelConfImp(ActionEvent actionEvent) {
        // Add event code here...
        this.getPopConfImpPay().hide();
        sessionScope.remove("id_etud_imp");
        sessionScope.remove("id_strct_imp");
        sessionScope.remove("id_operat_imp");
        
        sessionScope.remove("num_etud_paie");
        sessionScope.remove("prenom_etud_paie");
        sessionScope.remove("nom_etud_paie");
        sessionScope.remove("num_recu_paie");
    }

    public String onConfImp() {
        // Add event code here...
        this.getPopConfImpPay().hide();
        return null;
    }

    public void onConfirmeImp(ActionEvent actionEvent) {
        // Add event code here...
        this.getPopConfImpPay().hide();
    }

    public void onReturnConfImp(ReturnEvent returnEvent) {
        // Add event code here...
        this.getPopConfImpPay().hide();
    }

    @SuppressWarnings("unchecked")
    public void onImprimerToutRecu(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter_cmpt_etud_gen = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FormPayCompteEtudROIterator");
        Row row_cmpt_etud_gen = iter_cmpt_etud_gen.getCurrentRow();
        if(row_cmpt_etud_gen != null){  
            OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationImptoutImp");
            op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
            op_op_cmpt_etud.getParamsMap().put("id_cmpte",  row_cmpt_etud_gen.getAttribute("IdCompte"));
            op_op_cmpt_etud.getParamsMap().put("type_op",  "1");
            op_op_cmpt_etud.execute();
            DCIteratorBinding iter_cmpt_etud_op = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("LesOperationsDepotCmptEtudROIterator");
            Row row_cmpt_etud_op = iter_cmpt_etud_op.getCurrentRow();
            if(row_cmpt_etud_op != null){  
                RichPopup popup = this.getPopImpToutOp();                
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            RichPopup popup = this.getPopCmptEtdNot();                 
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopImpToutOp(RichPopup popImpToutOp) {
        this.popImpToutOp = popImpToutOp;
    }

    public RichPopup getPopImpToutOp() {
        return popImpToutOp;
    }

    public void onFermerConfToutOp(ActionEvent actionEvent) {
        // Add event code here...
        this.getPopImpToutOp().hide();
    }

    @SuppressWarnings("unchecked")
    public void onListenImp(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("customHandler ");
        DCIteratorBinding iter_cmpt_etud_op = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("LesOperationsDepotCmptEtudROIterator");
        Row row_cmpt_etud_op = iter_cmpt_etud_op.getCurrentRow();
        if(row_cmpt_etud_op != null){  
            sessionScope.put("id_operat_imp", row_cmpt_etud_op.getAttribute("IdOperation"));
            sessionScope.put("id_strct_imp", row_cmpt_etud_op.getAttribute("IdStructure"));
            sessionScope.put("id_etud_imp", row_cmpt_etud_op.getAttribute("IdEtudiant"));
            
            DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
            Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();
            
            sessionScope.put("num_etud_paie", row_list_insc_ped.getAttribute("Numero"));
            sessionScope.put("prenom_etud_paie", row_list_insc_ped.getAttribute("Prenom"));
            sessionScope.put("nom_etud_paie", row_list_insc_ped.getAttribute("Nom"));
            sessionScope.put("num_recu_paie", row_cmpt_etud_op.getAttribute("NumeroRecu"));
     
            RichPopup popup = this.getPopConfImpPay();                 
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        
    }

    public static MethodExpression fileDownladActionListenerMethodExpression(String fileDownladActionListenerName) { 
            MethodExpression methodExpression = null; 
            Class[] argumentTypes = new Class[2]; 
            argumentTypes[0] = FacesContext.class; 
            argumentTypes[1] = OutputStream.class; 
            FacesContext facesCtx = FacesContext.getCurrentInstance(); 
            Application app = facesCtx.getApplication(); 
            ExpressionFactory elFactory = app.getExpressionFactory(); 
            ELContext elContext = facesCtx.getELContext(); 
            methodExpression = 
                elFactory.createMethodExpression(elContext, fileDownladActionListenerName, null, argumentTypes); 
            try { 
                methodExpression.getMethodInfo(elContext); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return methodExpression; 
        }

    public void onFermerEtatPaie(ActionEvent actionEvent) {
        // Add event code here...
        this.getPopEtatPaie().hide();
        sessionScope.remove("id_insc_ped_etat");
        sessionScope.remove("id_etud_etat");
        sessionScope.remove("num_etud_etat");
        sessionScope.remove("prenom_etud_etat");
        sessionScope.remove("nom_etud_etat");
    }

    public void setPopEtatPaie(RichPopup popEtatPaie) {
        this.popEtatPaie = popEtatPaie;
    }

    public RichPopup getPopEtatPaie() {
        return popEtatPaie;
    }

    @SuppressWarnings("unchecked")
    public void onImprimeEtatPaie(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        Map m = new HashMap();
        
        m.put("id_util", getUtilisateur());
        m.put("id_etud", sessionScope.get("id_etud_etat"));
        m.put("id_hs", sessionScope.get("id_hs"));
        m.put("id_annee", getAnne_univers());
        m.put("id_insc_ped", sessionScope.get("id_insc_ped_etat"));//

        try {
            System.out.println("id_util inco");
            runReport("/reports/etat_paiement.jasper",m);
        } catch (Exception e) {
            System.out.println("erreur "+e.getMessage());
        }       

    }

    @SuppressWarnings("unchecked")
    public void onEtatPaiement(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ListeInscPedEcolROIterator");
        Row row_list_insc_ped = iter_list_insc_ped.getCurrentRow();

        if(row_list_insc_ped != null){
            sessionScope.put("id_insc_ped_etat", row_list_insc_ped.getAttribute("IdInscriptionPedagogique"));
            sessionScope.put("id_etud_etat", row_list_insc_ped.getAttribute("IdEtudiant"));
            
            sessionScope.put("num_etud_etat", row_list_insc_ped.getAttribute("Numero"));
            sessionScope.put("prenom_etud_etat", row_list_insc_ped.getAttribute("Prenom"));
            sessionScope.put("nom_etud_etat", row_list_insc_ped.getAttribute("Nom"));
            
            RichPopup popup = this.getPopEtatPaie();                
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopEtatInscNotVal(RichPopup popEtatInscNotVal) {
        this.popEtatInscNotVal = popEtatInscNotVal;
    }

    public RichPopup getPopEtatInscNotVal() {
        return popEtatInscNotVal;
    }

    public void setPopInfPersAuc(RichPopup popInfPersAuc) {
        this.popInfPersAuc = popInfPersAuc;
    }

    public RichPopup getPopInfPersAuc() {
        return popInfPersAuc;
    }

    public void setPopEtudRechNotExist(RichPopup popEtudRechNotExist) {
        this.popEtudRechNotExist = popEtudRechNotExist;
    }

    public RichPopup getPopEtudRechNotExist() {
        return popEtudRechNotExist;
    }

    public void setPopEtdChpObli(RichPopup popEtdChpObli) {
        this.popEtdChpObli = popEtdChpObli;
    }

    public RichPopup getPopEtdChpObli() {
        return popEtdChpObli;
    }

    public void setPopNumTabNotExist(RichPopup popNumTabNotExist) {
        this.popNumTabNotExist = popNumTabNotExist;
    }

    public RichPopup getPopNumTabNotExist() {
        return popNumTabNotExist;
    }

    public void setPopNumEtdNotExist(RichPopup popNumEtdNotExist) {
        this.popNumEtdNotExist = popNumEtdNotExist;
    }

    public RichPopup getPopNumEtdNotExist() {
        return popNumEtdNotExist;
    }

    public void setPopNumCinNotExist(RichPopup popNumCinNotExist) {
        this.popNumCinNotExist = popNumCinNotExist;
    }

    public RichPopup getPopNumCinNotExist() {
        return popNumCinNotExist;
    }

    public void refresh_num_tab(String num_tab){
        Row row_det_pers = null;
        if(num_tab != null){
            OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
            is_nouv_bac.getParamsMap().put("num_table",  num_tab);
            is_nouv_bac.getParamsMap().put("id_annee",  getAnne_univers());
            Integer result = (Integer)is_nouv_bac.execute();
            setNumero_table(num_tab);
            //numéro table non valide
            if(result != 0){
                OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                getIdPersEtudiant.getParamsMap().put("num_table", num_tab);
                getIdPersEtudiant.getParamsMap().put("id_annee",  getAnne_univers());
                String result_getid = (String)getIdPersEtudiant.execute();   
                OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                detpers.execute();
                
                DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                    //Create RowSetIterato iterate over viewObject
                RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                row_det_pers = rsi_det_pers.first();
                getRowPers(row_det_pers);
                
            }
        
        }
    }
    
    public void refresh_num_etud(String num_etd){
        Row row_det_pers = null;
        if(num_etd != null ){
            OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
            isEtudiant.getParamsMap().put("num_etud",  num_etd);
            //sessionScope.put("num_etd_var", (String)this.getNumero_etud());
            Integer result = (Integer)isEtudiant.execute();
            setNumero_etud(num_etd);
            //numéro étudiant non valide
            if(result != 0){
                //numéro étudiant valide
                // recuperer id personn de l etudiant
                OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                getIdPersEtudiant.getParamsMap().put("num_etud",  num_etd);                            
                String result_getid = (String)getIdPersEtudiant.execute(); 
        
                OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                detpers.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                detpers.execute();
                
                DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                    //Create RowSetIterato iterate over viewObject
                RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                row_det_pers = rsi_det_pers.first();
                getRowPers(row_det_pers);
            }

        }
    }
    public void refresh_num_cin(String num_cin){
        Row row_det_pers = null;
        if(num_cin != null ){
            //vérifier le numéro d'identification
            OperationBinding isEtudiant = getBindings().getOperationBinding("isNumCin");
            isEtudiant.getParamsMap().put("num_cin",  num_cin);
            //sessionScope.put("num_cin_var", (String)this.getNumero_cin());
            Integer result = (Integer)isEtudiant.execute();
            setNumero_etud(num_cin);
            // le numéro d'identification non valide            
            if(result != 0){
                // le numéro d'identification valide
                // recuperer id personn de l etudiant
                OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                getIdPersEtudiant.getParamsMap().put("cin",  num_cin);  
                String result_getid = (String)getIdPersEtudiant.execute(); 
        
                OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                detpers.getParamsMap().put("idpers",  result_getid);
                detpers.execute();
                
                DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesROIterator");
                    //Create RowSetIterato iterate over viewObject
                RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
                row_det_pers = rsi_det_pers.first();
                
            }

            }
    }
    
    public void refresh_etud_not_insc(){
        OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeInscPed");
        list_insc_ped.getParamsMap().put("idpers", 0);
        list_insc_ped.getParamsMap().put("id_annee",  getAnne_univers());
        list_insc_ped.execute();
                    
        OperationBinding op_cmpt_etud_gen = getBindings().getOperationBinding("getCompteEtudGen");
        op_cmpt_etud_gen.getParamsMap().put("id_annee",  getAnne_univers());
        op_cmpt_etud_gen.getParamsMap().put("id_strct",  0);
        op_cmpt_etud_gen.getParamsMap().put("id_etud",  0);
        op_cmpt_etud_gen.execute();
                        
        OperationBinding op_op_cmpt_etud= getBindings().getOperationBinding("getOperationCompteEtud");
        op_op_cmpt_etud.getParamsMap().put("id_annee",  getAnne_univers());
        op_op_cmpt_etud.getParamsMap().put("id_cmpte",  0);
        op_op_cmpt_etud.execute(); 
        setEtat("");
        setAvoir("");
        setReste_a_payer("");
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputAvoirSitu()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputRestePayerSitua()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMensSitua());
    }
}

