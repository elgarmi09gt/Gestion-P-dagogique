package autorisation_valide_dap;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class AutorisationValDapBean {
    private RichSelectBooleanCheckbox check;
    private RichPopup popup;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private RichPopup popDap;
    private Integer nombreInsc;


    public AutorisationValDapBean() {
    }


    public void setNombreInsc(Integer nombreInsc) {
        this.nombreInsc = nombreInsc;
    }

    public Integer getNombreInsc() {
        return nombreInsc;
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
    
    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    i++;
                }
            }
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public Row getDroitNiv(String niveau, String nationalite){
    //getDroitNiveauPays
        OperationBinding getDroitNiveauPays = getBindings().getOperationBinding("getDroitNiveauPays");
        getDroitNiveauPays.getParamsMap().put("id_niveau",  Long.parseLong(niveau)); 
        getDroitNiveauPays.getParamsMap().put("id_pays_nation",  Long.parseLong(nationalite));
        getDroitNiveauPays.execute(); 
        DCIteratorBinding iter_droit_niv = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DroitNiveauPaysROIterator");
        return iter_droit_niv.getCurrentRow();
    }

    @SuppressWarnings("unchecked")
    public List semestreNiv(Integer niv){
        List tab = new ArrayList();
        if(niv == 1){
            tab.add("1");
            tab.add("2");
        }
        else if(niv == 2){
                tab.add("3");
                tab.add("4");
        }
        else if(niv == 3){
                tab.add("5");
                tab.add("6");
        }
        else if(niv == 4){
                tab.add("1");
                tab.add("2");
        }
        else if(niv == 5){
                tab.add("3");
                tab.add("4");
        }
        return tab;
    }


    @SuppressWarnings("unchecked")
    public String onValideAutDap() {
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AutorisationValideDapROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);

        if(rsi.getRowCount()==0){
            
            FacesMessage msg = new FacesMessage(" Aucune autorisation validée par le Responsable de la Formation ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{                 
            if(nombreCaseCoche("AutorisationValideDapROIterator")==0){
                FacesMessage msg = new FacesMessage(" Veuillez cocher au moins une autorisation avant de valider ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                
            }
            else{
                //
                setNombreInsc(nombreCaseCoche("AutorisationValideDapROIterator"));
                
                RichPopup popup = this.getPopDap();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Integer inscription(String id_parc,String id_etud,String idpers){
        
        OperationBinding op_info_form = getBindings().getOperationBinding("getInfoForm");
        op_info_form.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));
        //op_info_form.getParamsMap().put("id_annee",  Long.parseLong(annee));          
        op_info_form.execute();
        DCIteratorBinding iter_info = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscInfoGlobleFormIterator");
        Row row_info_gl = iter_info.getCurrentRow();
        Integer niveau = Integer.parseInt(row_info_gl.getAttribute("Niveau").toString());         
        Integer nbr_insc = 0;
        List les_semestre = semestreNiv(niveau);
        OperationBinding op_double_insc = getBindings().getOperationBinding("doubleInscription");
        op_double_insc.getParamsMap().put("id_annee",  Long.parseLong(row_info_gl.getAttribute("IdAnneesUnivers").toString()));
        op_double_insc.getParamsMap().put("id_etud",  Long.parseLong(id_etud));         
        op_double_insc.execute();
        DCIteratorBinding iter_double_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscDoubleROIterator");
        Row row_bouble_insc = iter_double_insc.getCurrentRow();

        OperationBinding op_nombre_insc = getBindings().getOperationBinding("getNombreInscription");
        op_nombre_insc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));
        op_nombre_insc.getParamsMap().put("id_etud",  Long.parseLong(id_etud));     // validation du Responsable de la formation    
        op_nombre_insc.execute();
        
        DCIteratorBinding iter_nombre_insc = (DCIteratorBinding) getBindings().get("InscNombreMaxiROIterator");        
        RowSetIterator rsi_nombre_insc = iter_nombre_insc.getViewObject().createRowSetIterator(null);

        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
        detpers.getParamsMap().put("idpers_var",  Long.parseLong(idpers));
        detpers.execute();
        
        DCIteratorBinding iter_det_pers = (DCIteratorBinding)getBindings().get("PersonnesIterator");
                //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi_det_pers = iter_det_pers.getViewObject().createRowSetIterator(null);
        Row row_det_pers = rsi_det_pers.first();
        
        Row row_droit_niv_pays = getDroitNiv(niveau+"", row_det_pers.getAttribute("IdPaysNationalite").toString());
        if(row_droit_niv_pays != null){
            AttributeBinding idTypeFormation = (AttributeBinding) getBindings().getControlBinding("IdTypeFormation");
            AttributeBinding id_annee_insc_admin = (AttributeBinding) getBindings().getControlBinding("IdAnneesInsAdmin");
            AttributeBinding idetud = (AttributeBinding) getBindings().getControlBinding("IdEtudiantInscAdmin");
            AttributeBinding idGrade = (AttributeBinding) getBindings().getControlBinding("IdGrade");
            AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCreeInsAdmin");
            
            OperationBinding op_insert_insc_admin = getBindings().getOperationBinding("CreateInsertInscAdmin");
            Object result_insert_insc_admin = op_insert_insc_admin.execute();
                                    
            if (!op_insert_insc_admin.getErrors().isEmpty()) {
                    return nbr_insc;
            }
            else{
                idTypeFormation.setInputValue(row_info_gl.getAttribute("IdTypeFormation"));
                idGrade.setInputValue(row_info_gl.getAttribute("IdGrade"));
                idetud.setInputValue(id_etud);
                util.setInputValue(getUtilisateur());
                id_annee_insc_admin.setInputValue(getAnne_univers());
                
                OperationBinding op_commit_insc_admin = getBindings().getOperationBinding("Commit");
                Object result = op_commit_insc_admin.execute();
                if (!op_commit_insc_admin.getErrors().isEmpty()) {
                        return nbr_insc;
                }
                // le début d'inscription Pédagogique
                else{
                    AttributeBinding id_insc_admin = (AttributeBinding) getBindings().getControlBinding("IdInscriptionAdmin");
                    
                    //les attributs qui seront modifier lors de l'insc
                    AttributeBinding id_insc_admin_ped = (AttributeBinding) getBindings().getControlBinding("IdInscriptionAdminPed");
                    AttributeBinding id_HorairesTd = (AttributeBinding) getBindings().getControlBinding("IdHorairesTd");
                    AttributeBinding id_Statut = (AttributeBinding) getBindings().getControlBinding("IdStatut");
                    AttributeBinding id_Option = (AttributeBinding) getBindings().getControlBinding("IdOption");
                    AttributeBinding id_Bourse = (AttributeBinding) getBindings().getControlBinding("IdBourse");
                    AttributeBinding id_Cohorte = (AttributeBinding) getBindings().getControlBinding("IdCohorte");
                    AttributeBinding id_TypeConvention = (AttributeBinding) getBindings().getControlBinding("IdTypeConvention");
                    AttributeBinding id_Operateur = (AttributeBinding) getBindings().getControlBinding("IdOperateur");
                    AttributeBinding derniere_insc = (AttributeBinding) getBindings().getControlBinding("DernierInscription");
                    
                    AttributeBinding id_parc_maq_annee = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnneePed");
                    AttributeBinding nb_insc = (AttributeBinding) getBindings().getControlBinding("NbInsc");
                    AttributeBinding util_permet_double_insc = (AttributeBinding) getBindings().getControlBinding("UtiPermetDoublInsPed");
                    AttributeBinding id_etat_insc = (AttributeBinding) getBindings().getControlBinding("EtatInscrEtatInscrId");
                    AttributeBinding tarif = (AttributeBinding) getBindings().getControlBinding("Tarif");
                    AttributeBinding orde_insc = (AttributeBinding) getBindings().getControlBinding("OrdreInscription");
                    AttributeBinding utilisateur_cree = (AttributeBinding) getBindings().getControlBinding("UtiCreeInscPed");
                    
                    AttributeBinding cout_formation = (AttributeBinding) getBindings().getControlBinding("CoutFormation");
                    AttributeBinding droit_ins_adm = (AttributeBinding) getBindings().getControlBinding("DroitInsAdm");
                    AttributeBinding droit_ins_ped = (AttributeBinding) getBindings().getControlBinding("DroitInsPed");
                    AttributeBinding redoublant = (AttributeBinding) getBindings().getControlBinding("Redoublement");
                    
                    OperationBinding op_insc_ped = getBindings().getOperationBinding("CreateInsertInscPed");
                    Object result_op_insc_ped = op_insc_ped.execute();
                    if (!op_insc_ped.getErrors().isEmpty()) {
                            return nbr_insc;
                    }
                    else{
                        // les frais d'inscription
                        Integer droit_insc_admin = 0;
                        Integer droit_insc_ped = 0;
                        if(row_droit_niv_pays.getAttribute("DroitInsAdm") != null && row_droit_niv_pays.getAttribute("DroitInsPed") == null){
                            droit_insc_admin = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsAdm").toString());
                            droit_ins_adm.setInputValue(droit_insc_admin);
                            droit_ins_ped.setInputValue(row_droit_niv_pays.getAttribute("DroitInsPed"));
                            cout_formation.setInputValue( 0 + droit_insc_admin);
                        }
                        else{
                            if(row_droit_niv_pays.getAttribute("DroitInsPed") != null && row_droit_niv_pays.getAttribute("DroitInsAdm") == null){
                                droit_insc_ped = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsPed").toString());
                                droit_ins_ped.setInputValue(droit_insc_ped);
                                droit_ins_adm.setInputValue(row_droit_niv_pays.getAttribute("DroitInsAdm"));
                                cout_formation.setInputValue( 0 + droit_insc_ped);
                            }
                            else{
                                if(row_droit_niv_pays.getAttribute("DroitInsPed") == null && row_droit_niv_pays.getAttribute("DroitInsAdm") == null){
                                    droit_ins_ped.setInputValue(row_droit_niv_pays.getAttribute("DroitInsPed"));
                                    droit_ins_adm.setInputValue(row_droit_niv_pays.getAttribute("DroitInsAdm"));
                                    cout_formation.setInputValue(null);
                                }
                                else{
                                    droit_insc_admin = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsAdm").toString());
                                    droit_insc_ped = Integer.parseInt( row_droit_niv_pays.getAttribute("DroitInsPed").toString());
                                    droit_ins_ped.setInputValue(row_droit_niv_pays.getAttribute("DroitInsPed"));
                                    droit_ins_adm.setInputValue(row_droit_niv_pays.getAttribute("DroitInsAdm"));
                                    cout_formation.setInputValue(droit_insc_admin + droit_insc_ped);
                                }
                            }
                        }
                        
                        id_insc_admin_ped.setInputValue(Long.parseLong(id_insc_admin.getInputValue().toString()));
                        
                        //dernière inscription
                        OperationBinding op_last_insc = getBindings().getOperationBinding("getLastInsc");
                        op_last_insc.getParamsMap().put("idpers",  Long.parseLong(idpers));
                        op_last_insc.execute();
                        DCIteratorBinding iter_op_last_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscAncienneROIterator");
                        Row row_op_last_insce = iter_op_last_insc.getCurrentRow();
                        
                        if(row_op_last_insce != null){
                            id_HorairesTd.setInputValue(row_op_last_insce.getAttribute("IdHorairesTd"));
                            id_Statut.setInputValue(row_op_last_insce.getAttribute("IdStatut"));
                            id_Option.setInputValue(row_op_last_insce.getAttribute("IdOption"));
                            id_Bourse.setInputValue(row_op_last_insce.getAttribute("IdBourse"));
                            id_Cohorte.setInputValue(row_op_last_insce.getAttribute("IdCohorte"));
                            id_TypeConvention.setInputValue(row_op_last_insce.getAttribute("IdTypeConvention"));
                            id_Operateur.setInputValue(row_op_last_insce.getAttribute("IdOperateur"));
                        }
                        else{
                            detailInscPedDefault();
                            DCIteratorBinding iter_def_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("AttrOblInscPedROIterator");
                            Row row_def_insc = iter_def_insc.getCurrentRow();

                            id_HorairesTd.setInputValue(row_def_insc.getAttribute("IdHorairesTd"));
                            id_Statut.setInputValue(row_def_insc.getAttribute("IdStatut"));
                            id_Option.setInputValue(row_def_insc.getAttribute("IdOption"));
                            id_Bourse.setInputValue(row_def_insc.getAttribute("IdBourse"));
                            id_Cohorte.setInputValue(row_def_insc.getAttribute("IdCohorte"));
                            id_TypeConvention.setInputValue(row_def_insc.getAttribute("IdTypeConvention"));
                            id_Operateur.setInputValue(row_def_insc.getAttribute("IdOperateur"));
                        }
                        id_parc_maq_annee.setInputValue(id_parc);
                        nb_insc.setInputValue(rsi_nombre_insc.getRowCount() +1);        //rsi_nombre_insc.getRowCount() nombre d'inscription antérieure pour un même niv de formation
                        util_permet_double_insc.setInputValue(getUtilisateur()); //utilisateur connecté ou un autre
                        id_etat_insc.setInputValue(Long.parseLong("21")); // etat prvisoire
                        utilisateur_cree.setInputValue(getUtilisateur());
                                                    
                        
                        if(row_op_last_insce != null)
                            derniere_insc.setInputValue(row_op_last_insce.getAttribute("IdInscriptionPedagogique"));
                        else
                            derniere_insc.setInputValue(Long.parseLong("0"));
                        
                        if(rsi_nombre_insc.getRowCount() > 0)
                            redoublant.setInputValue(1);
                        else
                            redoublant.setInputValue(0);
                        
                        OperationBinding op_insert_ped_commit = getBindings().getOperationBinding("Commit");
                        Object result_insert_ped_commit = op_insert_ped_commit.execute();
                        if (!op_insert_ped_commit.getErrors().isEmpty()) {
                                return nbr_insc;
                        }                               
                        else{

                            nbr_insc ++;
                        }
                    }
                }
            }

        }
        else{
            System.out.println("Le niveau de formation ne dispose pas de droit de paiement");
        }
        return nbr_insc;
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

    public void detailInscPedDefault(){
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl"); 
        ViewObject vo_insc_ped = am.getAttrOblInscPedRO();
        
        Row r = vo_insc_ped.createRow();
        
        OperationBinding op_bourse = getBindings().getOperationBinding("getBourse");       
        op_bourse.execute();
        DCIteratorBinding iter_bourse = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("BoursesIterator");
        Row row_bourse = iter_bourse.getCurrentRow();
        
        OperationBinding op_cohorte = getBindings().getOperationBinding("getCohorte");       
        op_cohorte.execute();
        DCIteratorBinding iter_cohorte = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CohortesIterator");
        Row row_cohorte = iter_cohorte.getCurrentRow();
        
        OperationBinding op_horaire = getBindings().getOperationBinding("getHoraire");       
        op_horaire.execute();
        DCIteratorBinding iter_horaire = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("HorairesTdIterator");
        Row row_horaire = iter_horaire.getCurrentRow();
        
        OperationBinding op_oper = getBindings().getOperationBinding("getOperateur");       
        op_oper.execute();
        DCIteratorBinding iter_oper = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperateursIterator");
        Row row_oper = iter_oper.getCurrentRow();
        
        OperationBinding op_option = getBindings().getOperationBinding("getOption");       
        op_option.execute();
        DCIteratorBinding iter_option = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OptionsIterator");
        Row row_option = iter_option.getCurrentRow();
        
        OperationBinding op_status = getBindings().getOperationBinding("getStatut");       
        op_status.execute();
        DCIteratorBinding iter_status = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("StatutsIterator");
        Row row_status = iter_status.getCurrentRow();
        
        OperationBinding op_conv = getBindings().getOperationBinding("getTypeConvention");       
        op_conv.execute();
        DCIteratorBinding iter_conv = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("TypeConventionsIterator");
        Row row_conv = iter_conv.getCurrentRow();
        
        r.setAttribute("IdBourse",row_bourse.getAttribute("IdBourse"));
        r.setAttribute("IdCohorte",row_cohorte.getAttribute("IdCohorte"));
        r.setAttribute("IdHorairesTd",row_horaire.getAttribute("IdHorairesTd"));
        r.setAttribute("IdOperateur",row_oper.getAttribute("IdOperateur"));
        r.setAttribute("IdOption",row_option.getAttribute("IdOption"));
        r.setAttribute("IdStatut",row_status.getAttribute("IdStatut"));
        r.setAttribute("IdTypeConvention",row_conv.getAttribute("IdTypeConvention"));
        vo_insc_ped.insertRow(r);
    }
    public static String getRandomStr(int n) 
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ0123456789"; 
    
        StringBuilder s = new StringBuilder(n); 
    
        for (int i = 0; i < n; i++) { 
            int index = (int)(str.length() * Math.random()); 
            s.append(str.charAt(index)); 
        } 
        return s.toString(); 
    }
    public static String getRandomNomber(int n) 
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "123456789"; 
    
        StringBuilder s = new StringBuilder(n); 
    
        for (int i = 0; i < n; i++) { 
            int index = (int)(str.length() * Math.random()); 
            s.append(str.charAt(index)); 
        } 
        return s.toString(); 
    }

    public void setCheck(RichSelectBooleanCheckbox check) {
        this.check = check;
    }

    public RichSelectBooleanCheckbox getCheck() {
        return check;
    }

    public void onSelectAll(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(Boolean.parseBoolean(check.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("AutorisationValideDapROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
            }
        }
        else{
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("AutorisationValideDapROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String onRetirerAutorise() {
        // Add event code here...
        AttributeBinding id_parc_maquette = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("ListePersAutDapROIterator");
        Row currentRow = dciter.getCurrentRow();
        
        OperationBinding annuler_auto = getBindings().getOperationBinding("getAutorisation");

        annuler_auto.getParamsMap().put("id_autorise", Long.parseLong(currentRow.getAttribute("IdAutorise").toString()));
        annuler_auto.execute();
        //Valide
        AttributeBinding valide = (AttributeBinding) getBindings().getControlBinding("Valide");
        //isEtudiant getEtudiant
        OperationBinding is_etudiant = getBindings().getOperationBinding("isEtudiant");
        is_etudiant.getParamsMap().put("id_pers",  Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));               
        Integer res_is_etud = (Integer)is_etudiant.execute(); 
 
        if(res_is_etud == 0){
            RichPopup popup = this.getPopup();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            //info de l'étudiant
            OperationBinding etudiant = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudiant");
            etudiant.getParamsMap().put("idpers",  Long.parseLong(currentRow.getAttribute("IdPersonne").toString()));               
            etudiant.execute(); 
            AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
            
            OperationBinding op_double_insc = getBindings().getOperationBinding("doubleInscription");
            op_double_insc.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));
            op_double_insc.getParamsMap().put("id_etud",  Long.parseLong(id_etudiant.getInputValue().toString()));     //     
            op_double_insc.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc_maquette.getInputValue().toString()));
            op_double_insc.execute();
            DCIteratorBinding iter_double_insc = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscDoubleROIterator");
            RowSetIterator rsi = iter_double_insc.getViewObject().createRowSetIterator(null);
            //une inscription de l'année en cours
            if(rsi.getRowCount() > 0){
                FacesMessage msg = new FacesMessage( " Impossible d'annuler l'autorisation de "+currentRow.getAttribute("Prenoms")+" "+currentRow.getAttribute("Nom")+" ( "+currentRow.getAttribute("Numero")+" )");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);//
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Déjà inscrit pour l'année universitaire en cours"));
            }
            else{
                RichPopup popup = this.getPopup();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        return null;
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    @SuppressWarnings("unchecked")
    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_autorise = (AttributeBinding) getBindings().getControlBinding("IdAutorise");
        
        AttributeBinding id_parc_maquette = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        
        OperationBinding op_autorise = getBindings().getOperationBinding("fingAndUpdateAutorise");
        op_autorise.getParamsMap().put("id_aut",  Long.parseLong(id_autorise.getInputValue().toString()));
        op_autorise.getParamsMap().put("valide",  2);   // annulation de l'autorisation effectuée par le DAP 
        op_autorise.execute();

        //refresh la liste des étudiants autorisés provisoirement
        OperationBinding op_aut_prov = getBindings().getOperationBinding("autorisationDapAValide");
        op_aut_prov.getParamsMap().put("id_parc_maquet",  Long.parseLong(id_parc_maquette.getInputValue().toString()));
        op_aut_prov.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));
        op_aut_prov.execute();
        
        //refresh la liste des étudiants autorisés
       
        OperationBinding op_aut = getBindings().getOperationBinding("autorisationDapValide");
        op_aut.getParamsMap().put("id_parc_maquet",  Long.parseLong(id_parc_maquette.getInputValue().toString()));
        op_aut.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));
        op_aut.execute();
        

    }

    public void onDialogCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopup().hide();
    }

    public void setPopDap(RichPopup popDap) {
        this.popDap = popDap;
    }

    public RichPopup getPopDap() {
        return popDap;
    }

    public void onDialogCanDap(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopDap().hide();
    }

    @SuppressWarnings("unchecked")
    public void onDialogDap(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_parc_maquette = (AttributeBinding) getBindings().getControlBinding("IdParcoursMaquetAnnee");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding niveau_bind = (AttributeBinding) getBindings().getControlBinding("Niveau");
        
        Integer niveau = Integer.parseInt(niveau_bind.getInputValue().toString());
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("AutorisationValideDapROIterator");        
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        
        Integer les_insc =0;
                
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    OperationBinding is_etudiant = getBindings().getOperationBinding("isEtudiant");
                    is_etudiant.getParamsMap().put("id_pers",  Long.parseLong(nextRow.getAttribute("IdPersonne").toString()));               
                    Integer res_is_etud = (Integer)is_etudiant.execute(); 
                    
                    OperationBinding op_autorise = getBindings().getOperationBinding("fingAndUpdateAutorise");
                    op_autorise.getParamsMap().put("id_aut",  Long.parseLong(nextRow.getAttribute("IdAutorise").toString()));
                    op_autorise.getParamsMap().put("valide",  3);     // validation du DAP    
                    
                    //si la personne est deja un etudiant
                    if(res_is_etud > 0){                                                
                        OperationBinding etudiant = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getEtudiant");
                        etudiant.getParamsMap().put("idpers",  Long.parseLong(nextRow.getAttribute("IdPersonne").toString()));               
                        etudiant.execute(); 
                        AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
                        //inscription(String id_parc,String id_etud,String idpers,Integer niveau)
                        //Integer nbr_insc = inscription(id_parc_maquette.getInputValue().toString(), id_etudiant.getInputValue().toString(), nextRow.getAttribute("IdPersonne").toString());
                        inscriptionPed (id_parc_maquette.getInputValue().toString(), id_etudiant.getInputValue().toString(), nextRow.getAttribute("IdPersonne").toString());
                        Integer nbr_insc = 1;
                        les_insc = les_insc + nbr_insc;
                        if (nbr_insc > 0)
                            op_autorise.execute();
                    }
                    // une personne qui n'est pas encore un étudiant
                    else{
                        //enregistrement du nouv bachelier comme un étudaint
                        AttributeBinding util_insert_etud = (AttributeBinding) getBindings().getControlBinding("UtiCreeEtud");
                        AttributeBinding id_pers_etud = (AttributeBinding) getBindings().getControlBinding("IdPersonneEtud");
                        AttributeBinding numero_etud = (AttributeBinding) getBindings().getControlBinding("NumeroEtud");
                        
                        OperationBinding op_insert_etud = getBindings().getOperationBinding("CreateInsertEtudiant");
                        Object result = op_insert_etud.execute();
                        if (!op_insert_etud.getErrors().isEmpty()) {
                                return ;
                        }
                        else{
                            //getAnneeEnCours
                            OperationBinding getAnneeEnCours = getBindings().getOperationBinding("getAnneeEnCours");
                            getAnneeEnCours.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                            getAnneeEnCours.execute();
                            DCIteratorBinding iter_annee_cours = (DCIteratorBinding)getBindings().get("AnneeUniversEnCoursROIterator");
                            String annee_cours = iter_annee_cours.getCurrentRow().getAttribute("AnneeCours").toString();
                                                    
                            util_insert_etud.setInputValue(Long.parseLong(getUtilisateur()));
                            id_pers_etud.setInputValue(Long.parseLong(nextRow.getAttribute("IdPersonne").toString()));
                            String chaine_aleatoire = getRandomStr(3);
                            String nomb_alea = getRandomNomber(1);
                            numero_etud.setInputValue(annee_cours+"0"+nomb_alea+chaine_aleatoire);          // Exemple 200903K3C
                            
                            OperationBinding op_insert_etud_commit = getBindings().getOperationBinding("Commit");
                            Object result_commit = op_insert_etud_commit.execute();
                            if (!op_insert_etud_commit.getErrors().isEmpty()) {
                                    return ;
                            }
                            else{
                                //inscription(String id_parc,String id_etud,String idpers,Integer niveau)
                                AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiantEtud");
                                Integer nbr_insc = inscription(id_parc_maquette.getInputValue().toString(), id_etudiant.getInputValue().toString(), nextRow.getAttribute("IdPersonne").toString());
                                les_insc = les_insc + nbr_insc;
                                if (nbr_insc > 0)
                                    op_autorise.execute();
                            }
                        }
                            
                    }
                }//
            }
        }
        this.getPopDap().hide();
        
        FacesMessage msg = new FacesMessage(nombreCaseCoche("AutorisationValideDapROIterator") - les_insc+" Inscription(s) rejetée(s)");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(les_insc+" Inscription(s) validée(s) avec succès "));
        FacesContext.getCurrentInstance().addMessage(null, msg);               
        OperationBinding op_aut = getBindings().getOperationBinding("autorisationDapValide");
        op_aut.getParamsMap().put("id_parc_maquet",  Long.parseLong(id_parc_maquette.getInputValue().toString()));
        op_aut.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));
        op_aut.execute();
        
        OperationBinding op_aut_prov = getBindings().getOperationBinding("autorisationDapAValide");
        op_aut_prov.getParamsMap().put("id_parc_maquet",  Long.parseLong(id_parc_maquette.getInputValue().toString()));
        op_aut_prov.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));
        op_aut_prov.execute();  
    }

    public void inscriptionUn(String id_insc_ped,Row row_global_form,String id_parc){
        Row row_etat_anc_niv = getEtatInscPed(id_insc_ped);  
        Row row_res_anc_niv = getResultatAnnuelInscPed(id_insc_ped);
        Row row_anc_niv = getNiveauInscPed(id_insc_ped);
        if(row_etat_anc_niv == null ||  row_etat_anc_niv.getAttribute("IdEtatsInscription") == null){
            FacesMessage msg = new FacesMessage(" Impossible d'autoriser l'étudiant "+ row_anc_niv.getAttribute("Prenoms")+"  "+row_anc_niv.getAttribute("Nom")+" ( "+row_anc_niv.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" L'état d'inscription du niveau de formation : "+row_anc_niv.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_anc_niv.getAttribute("LibAnnee")+" n'est pas définitif"));
        
        }
        else{
            if(row_etat_anc_niv != null &&  Integer.parseInt(row_etat_anc_niv.getAttribute("IdEtatsInscription").toString()) != 22){
                System.out.println(" Impossible d'autoriser l'étudiant "+row_anc_niv.getAttribute("Prenoms")+"  "+row_anc_niv.getAttribute("Nom")+" ( "+row_anc_niv.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
               
                System.out.println(" L'état d'inscription du niveau de formation : "+row_anc_niv.getAttribute("LibNivForm")+" ( "+row_etat_anc_niv.getAttribute("Libelle")+" ) inscrit pour l'année universitaire :"+row_anc_niv.getAttribute("LibAnnee")+" n'est pas définitif");
        
            }
            else{                
                if(row_res_anc_niv == null || row_res_anc_niv.getAttribute("Resultat") == null ){
                    System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_anc_niv.getAttribute("Prenoms")+"  "+row_anc_niv.getAttribute("Nom")+" ( "+row_anc_niv.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                   //System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" )" );
                    System.out.println(" Veuillez saisir le resultat de la formation : "+row_anc_niv.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_anc_niv.getAttribute("LibAnnee"));
                     
                }
                else{
                    if(Integer.parseInt(row_res_anc_niv.getAttribute("Resultat").toString()) == 1 ){
                        inscription(id_parc, row_anc_niv.getAttribute("IdEtudiant").toString(), row_anc_niv.getAttribute("IdPersonne").toString());
                    }
                    else{
                        if(Integer.parseInt(row_res_anc_niv.getAttribute("Resultat").toString()) == 2 ){
                            inscription(row_anc_niv.getAttribute("IdParcoursMaquetAnnee").toString(), row_anc_niv.getAttribute("IdEtudiant").toString(), row_anc_niv.getAttribute("IdPersonne").toString());
                            inscription(id_parc, row_anc_niv.getAttribute("IdEtudiant").toString(), row_anc_niv.getAttribute("IdPersonne").toString());
                        }
                        else
                            inscription(row_anc_niv.getAttribute("IdParcoursMaquetAnnee").toString(), row_anc_niv.getAttribute("IdEtudiant").toString(), row_anc_niv.getAttribute("IdPersonne").toString());

                    }
                }
            }
        }
    }
    
    public void inscriptionPed (String id_parc,String id_etud,String idpers){
        
        OperationBinding op_info_form = getBindings().getOperationBinding("getInfoForm");
        op_info_form.getParamsMap().put("id_parc_maq",  Long.parseLong(id_parc));         
        op_info_form.execute();
        DCIteratorBinding iter_info = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscInfoGlobleFormIterator");
        Row row_global_form = iter_info.getCurrentRow();
        Integer niveau_parc = Integer.parseInt(row_global_form.getAttribute("Niveau").toString());
        
        OperationBinding op_insc_annee_passee = getBindings().getOperationBinding("getNombreInscPed");
        op_insc_annee_passee.getParamsMap().put("idpers",  idpers);
        op_insc_annee_passee.execute();
        DCIteratorBinding iter_insc_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NombrInscPedAnneeROIterator");
        Row row_nbr_insc = iter_insc_annee_passee.getCurrentRow();
        
        if(row_nbr_insc == null){
            // pas d'insc
            inscription(id_parc,id_etud,idpers);
        }
        else{
            if(Integer.parseInt(row_nbr_insc.getAttribute("NbrInsc").toString()) == 1){
                // une insc
                OperationBinding op_getNiveau_ant = getBindings().getOperationBinding("getLesInscPed");
                op_getNiveau_ant.getParamsMap().put("id_etud",  id_etud);
                op_getNiveau_ant.getParamsMap().put("id_annee",  row_nbr_insc.getAttribute("IdAnneesUnivers"));
                op_getNiveau_ant.execute();
                DCIteratorBinding iter_insc_un = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("LesInscPedROIterator");
                Row row_insc_un = iter_insc_un.getCurrentRow();
                //inscriptionUn(String id_insc_ped,Row row_global_form,String id_parc)
                inscriptionUn(row_insc_un.getAttribute("IdInscriptionPedagogique").toString(),row_global_form,id_parc);
                
            }
            else{
                if(Integer.parseInt(row_nbr_insc.getAttribute("NbrInsc").toString()) == 2){
                    // deux insc
                    OperationBinding op_getNiveau_ant = getBindings().getOperationBinding("getLesInscPed");
                    op_getNiveau_ant.getParamsMap().put("id_etud",  id_etud);
                    op_getNiveau_ant.getParamsMap().put("id_annee",  row_nbr_insc.getAttribute("IdAnneesUnivers"));
                    op_getNiveau_ant.execute();
                    
                    DCIteratorBinding iter_liste = (DCIteratorBinding)getBindings().get("LesInscPedROIterator");
                        //Create RowSetIterato iterate over viewObject
                    RowSetIterator rsi_iter_liste = iter_liste.getViewObject().createRowSetIterator(null);
                    Row row_insc_ped_niv_inf = rsi_iter_liste.first();
                    Row row_insc_ped_niv_sup = rsi_iter_liste.last();
                    System.out.println("row_insc_ped_anc_niv_inf "+rsi_iter_liste.getRowCount());
                    System.out.println("row_insc_ped_anc_niv_inf "+row_insc_ped_niv_inf);
                    System.out.println("row_insc_ped_niv_sup "+row_insc_ped_niv_sup);
                    
                    String id_insc_ped_anc_niv_inf = row_insc_ped_niv_inf.getAttribute("IdInscriptionPedagogique").toString();
                    String id_insc_ped_anc_niv_sup = row_insc_ped_niv_sup.getAttribute("IdInscriptionPedagogique").toString();
                    System.out.println("id_insc_ped_anc_niv_inf "+row_insc_ped_niv_inf.getAttribute("IdInscriptionPedagogique"));
                    System.out.println("id_insc_ped_anc_niv_sup "+row_insc_ped_niv_sup.getAttribute("IdInscriptionPedagogique"));
                    
                    //    public void inscriptionDoubleInscPed(String id_insc_ped_anc_niv_inf, String id_insc_ped_anc_niv_sup,Integer niv_sup,Row row_global_form,String id_pers, String id_etud, String id_niv_form_parc){
                    inscriptionDoubleInscPed(row_insc_ped_niv_inf.getAttribute("IdInscriptionPedagogique").toString(), row_insc_ped_niv_sup.getAttribute("IdInscriptionPedagogique").toString(), niveau_parc, row_global_form,idpers, id_etud, id_parc);

                }
            }
        }
    }
    public Row getNiveauFormationSup(String id_form,Integer niv, String id_annee){
        OperationBinding op_insc_annee_passee = getBindings().getOperationBinding("getNiveauFormationSup");
        op_insc_annee_passee.getParamsMap().put("id_annee",  id_annee);
        op_insc_annee_passee.getParamsMap().put("id_form",  id_form);
        op_insc_annee_passee.getParamsMap().put("niv",  niv);
        op_insc_annee_passee.execute();
        DCIteratorBinding iter_insc_annee_passee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NiveauFormationSupROIterator");
        Row row_insc_annee_passee = iter_insc_annee_passee.getCurrentRow();
        
        return row_insc_annee_passee;
    }
    public Integer getNiveau(String id_etud, String id_annee){
        OperationBinding op_getNiveau_ant = getBindings().getOperationBinding("getNiveau");
        op_getNiveau_ant.getParamsMap().put("id_etud",  id_etud);
        op_getNiveau_ant.getParamsMap().put("id_annee",  id_annee);
        return (Integer)op_getNiveau_ant.execute();
    }
    public Row getResultatAnnuelInscPed(String id_insc_ped){
        OperationBinding op_getResult = getBindings().getOperationBinding("getResultatAnnuelInscPed");
        op_getResult.getParamsMap().put("id_insc_ped",  id_insc_ped);
        op_getResult.execute();
        DCIteratorBinding iter_op_res_ann = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("ResultatAnnuelInscPedROIterator");
        return  iter_op_res_ann.getCurrentRow();
    }
    public Row getNiveauInscPed(String id_insc_ped){
        OperationBinding op_getResult = getBindings().getOperationBinding("getNiveauInscPed");
        op_getResult.getParamsMap().put("id_insc_ped",  id_insc_ped);
        op_getResult.execute();
        DCIteratorBinding iter_op_res_ann = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("NiveauInscPedROIterator");
        return  iter_op_res_ann.getCurrentRow();
    } 
    @SuppressWarnings("unchecked")
    public Row getEtatInscPed(String id_insc_ped){
        OperationBinding op_getEtatInscPed = getBindings().getOperationBinding("getEtatInscPed");
        op_getEtatInscPed.getParamsMap().put("id_insc_ped",  id_insc_ped);
        op_getEtatInscPed.execute();
        DCIteratorBinding iter_op_res_der = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("InscPedEtatInscROIterator");
        return  iter_op_res_der.getCurrentRow();
    }
    
    public void inscriptionDoubleInscPed(String id_insc_ped_anc_niv_inf, String id_insc_ped_anc_niv_sup,Integer niv_sup,Row row_global_form,String id_pers, String id_etud, String id_niv_form_parc){
        
        
        Row row_niv_anc_niv_inf = getNiveauInscPed(id_insc_ped_anc_niv_inf);
        Integer niveau_inf = Integer.parseInt(row_niv_anc_niv_inf.getAttribute("Niveau").toString());
        
        Row row_niv_anc_niv_sup = getNiveauInscPed(id_insc_ped_anc_niv_sup);
        
        Integer niveau_sup = Integer.parseInt(row_niv_anc_niv_sup.getAttribute("Niveau").toString());
        
        Row row_op_res_ann_anc_niv_inf = getResultatAnnuelInscPed(id_insc_ped_anc_niv_inf);
        
        Row row_op_res_ann_anc_niv_sup = getResultatAnnuelInscPed(id_insc_ped_anc_niv_sup);
        
        Row row_etat_anc_niv_inf = getEtatInscPed(id_insc_ped_anc_niv_inf);
        
        Row row_etat_anc_niv_sup = getEtatInscPed(id_insc_ped_anc_niv_sup);
        
        if(row_etat_anc_niv_inf == null ||  row_etat_anc_niv_sup == null){
            
            System.out.println(" Impossible d'autoriser l'étudiant "+ row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
            if(row_etat_anc_niv_inf == null)
                System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas définitif");
            if(row_etat_anc_niv_sup == null)
                System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas définitif");
            
        }
        else{
            if((row_etat_anc_niv_inf != null &&  row_etat_anc_niv_sup == null) || (row_etat_anc_niv_inf == null &&  row_etat_anc_niv_sup != null)){
                if((row_etat_anc_niv_inf != null &&  row_etat_anc_niv_sup == null)){
                    System.out.println(" Impossible d'autoriser l'étudiant "+ row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));

                    if(Integer.parseInt(row_etat_anc_niv_inf.getAttribute("IdEtatsInscription").toString()) != 22)
                        System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" ( "+row_etat_anc_niv_inf.getAttribute("Libelle")+" ) inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas définitif");
                    if(row_etat_anc_niv_sup == null)
                        System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas définitif");
                    
                }
                else{
                    if((row_etat_anc_niv_inf == null &&  row_etat_anc_niv_sup != null)){
                        System.out.println(" Impossible d'autoriser l'étudiant "+ row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                        
                        if(Integer.parseInt(row_etat_anc_niv_sup.getAttribute("IdEtatsInscription").toString()) != 22)
                            System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" ( "+row_etat_anc_niv_sup.getAttribute("Libelle")+" ) inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas définitif");
                        if(row_etat_anc_niv_sup == null)
                            System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas définitif");
                        
                    }
                }
            }
            else{
                if(Integer.parseInt(row_etat_anc_niv_inf.getAttribute("IdEtatsInscription").toString()) != 22 ||  Integer.parseInt(row_etat_anc_niv_sup.getAttribute("IdEtatsInscription").toString()) != 22){
                    System.out.println(" Impossible d'autoriser l'étudiant "+ row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));                    
                    
                    if(Integer.parseInt(row_etat_anc_niv_inf.getAttribute("IdEtatsInscription").toString()) != 22)
                        System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" ( "+row_etat_anc_niv_inf.getAttribute("Libelle")+" ) inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas définitif");
                    if(Integer.parseInt(row_etat_anc_niv_sup.getAttribute("IdEtatsInscription").toString()) != 22)
                        System.out.println(" L'état d'inscription du niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" ( "+row_etat_anc_niv_sup.getAttribute("Libelle")+" ) inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas définitif");
                    
                }
                else{

                    // Pas de resultat pour sa derniere inscription
                    if(row_op_res_ann_anc_niv_inf == null || row_op_res_ann_anc_niv_inf.getAttribute("Resultat") == null || row_op_res_ann_anc_niv_sup == null || row_op_res_ann_anc_niv_sup.getAttribute("Resultat") == null){
                        System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                        
                        //System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" )" );
                        if(row_op_res_ann_anc_niv_inf == null)
                            //System.out.println(" Veuillez saisir le resultat de la formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm"));
                            System.out.println(" Veuillez saisir le resultat de la formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee"));
                        if(row_op_res_ann_anc_niv_sup == null)
                            //System.out.println(" Veuillez saisir le resultat de la formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm"));
                            System.out.println(" Veuillez saisir le resultat de la formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee"));
                        
                    }
                    else{
                        // le cas L1 et L2
                        if(niveau_sup == 2 ){
                            if(niv_sup == (niveau_sup + 1) ){
                                //noteAnnPre.CREDIT = 60) and Ann.CREDIT = 60)
                                if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 1 ){
                                    //insc niv superieur
                                    //System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+nextRow.getAttribute("Prenomnom")+" ( "+nextRow.getAttribute("Numero")+" )" );
                                    System.out.println(" Veuillez saisir le resultat de la formation : ");
                                    System.out.println(" Insc niv sup : ");
                                    inscription(id_niv_form_parc, id_etud, id_pers);
                                    
                                }
                                else{
                                    //((noteAnnPre.CREDIT = 60) and (noteAnn.CREDIT < 60) and (noteAnn.CREDIT >= 42))
                                    if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 2 ){
                                        //passe en L3
                                        //enjambiliste L2 et L3
                                        //L1 validé
                                        
                                        inscription(row_niv_anc_niv_sup.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                        inscription(id_niv_form_parc, id_etud, id_pers);
                                        //insertAutorisation(id_pers, id_niv_form_parc,getAnne_univers()," L'étudiant du nom & prénom : "+row_det_pers.getAttribute("Nom")+" "+row_det_pers.getAttribute("Prenoms")+" est autorisée à s'inscrire en "+row_global_form.getAttribute("LibNivForm"), " NB: Par Changement de cycle");
            
                                    }
                                    else{
                                        //((noteAnnPre.CREDIT = 60) and (noteAnn.CREDIT < 42))
                                        if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 0 ){
                                            //Redoublant L2 
                                            //L1 validé
                                            System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                            
                                            System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+") : Redoublant");
                                            System.out.println(" Le niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" est validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+")");                            
                                        }
                                        else{
                                            //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT = 60))
                                            if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 3 ){
                                                //Redoublant L1 
                                                //L2 validé
                                                System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                                
                                                System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" est validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+")");
                                                System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+")  : Redoublant");                                 
                                            }
                                            else{
                                                //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT < 60))
                                                if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 4 ){
                                                    //Redoublant L1 
                                                    //Redoublant L2
                                                    System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                                    
                                                    System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+")  : Redoublant");
                                                    System.out.println(" Le niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+") : Redoublant");                                     
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                if(niv_sup == niveau_sup ){
                                    //((noteAnnPre.CREDIT = 60) and (noteAnn.CREDIT < 42))
                                    if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 0 ){
                                        //Redoublant L2 
                                        //L1 validé
                                        inscription(row_niv_anc_niv_sup.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);                          
                                    }
                                    else{
                                        //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT = 60))
                                        if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 3 ){
                                            //Redoublant L1 
                                            //L2 validé 
                                            inscription(row_niv_anc_niv_inf.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);                                
                                        }
                                        else{
                                            //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT < 60))
                                            if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 4 ){
                                                //Redoublant L1 
                                                //Redoublant L2
                                                inscription(row_niv_anc_niv_inf.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                                inscription(row_niv_anc_niv_sup.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            // le cas L2 et L3 ou M1 et M2
                            if(niveau_sup == 3  ||  niveau_sup == 5){
                                if(niv_sup == (niveau_sup + 1) ){
                                    //noteAnnPre.CREDIT = 60) and Ann.CREDIT = 60)
                                    if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 5 ){
                                        //insc niv superieur
                                        //inscValide(id_pers, id_etud, id_niv_form_parc, row_global_form, row_det_pers, row_niv_anc_niv_sup);
                                        //insertAutorisation(id_pers, id_niv_form_parc,getAnne_univers()," L'étudiant du nom & prénom : "+row_det_pers.getAttribute("Nom")+" "+row_det_pers.getAttribute("Prenoms")+" est autorisée à s'inscrire en "+row_global_form.getAttribute("LibNivForm"), " NB: Par Changement de cycle");
                                        inscription(id_niv_form_parc, id_etud, id_pers);
                                        //System.out.println(" L'etudiant a déjà validé le niveau de formation : "+row_insc_annee_passee.getAttribute("LibNivForm"));
                                    }
                                    else{
                                        //((noteAnnPre.CREDIT = 60) and (noteAnn.CREDIT < 60))
                                        if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 0 ){
                                            //Redoublant L3
                                            //L2 validé
                                            System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                            
                                            System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+") : Redoublant");
                                            System.out.println(" Le niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" est validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+")");                            
                                        }
                                        else{
                                        
                                            //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT = 60))
                                            if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 3 ){
                                                //Redoublant L2
                                                //L3 validé
                                                System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                                
                                                System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" est validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+")");
                                                System.out.println(" Le niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+") : Redoublant"); 
                                            }
                                            else{
                                                //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT < 60))
                                                if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 4 ){
                                                    //Redoublant L2
                                                    //Redoublant L3
                                                    System.out.println("Impossible d'inscrire L'étudiant du prénom & nom :  "+row_niv_anc_niv_sup.getAttribute("Prenoms")+"  "+row_niv_anc_niv_sup.getAttribute("Nom")+" ( "+row_niv_anc_niv_sup.getAttribute("Numero")+" ) à s'inscrire en "+row_global_form.getAttribute("LibNivForm"));
                                                    
                                                    System.out.println(" Le niveau de formation : "+row_niv_anc_niv_sup.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_sup.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_sup.getAttribute("Credit")+")  : Redoublant");
                                                    System.out.println(" Le niveau de formation : "+row_niv_anc_niv_inf.getAttribute("LibNivForm")+" inscrit pour l'année universitaire :"+row_niv_anc_niv_inf.getAttribute("LibAnnee")+" n'est pas validé ( crédit obtenu :"+row_op_res_ann_anc_niv_inf.getAttribute("Credit")+") : Redoublant");
                                                }
                                            }
                                            
                                        }
                                    }
                                }
                                else{
                                    if(niv_sup == niveau_sup ){
                                        //((noteAnnPre.CREDIT = 60) and (noteAnn.CREDIT < 60))
                                        if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 3 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 0 ){
                                            //Redoublant L3
                                            //L2 validé
                                            inscription(row_niv_anc_niv_sup.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);                                
                                        }
                                        else{
                                            //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT = 60))
                                            if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 3 ){
                                                //Redoublant L2
                                                //L3 validé 
                                                inscription(row_niv_anc_niv_inf.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                    
                                            }
                                            else{
                                                //((noteAnnPre.CREDIT < 60) and (noteAnn.CREDIT < 60))
                                                if(Integer.parseInt(row_op_res_ann_anc_niv_inf.getAttribute("Resultat").toString()) == 4 && Integer.parseInt(row_op_res_ann_anc_niv_sup.getAttribute("Resultat").toString()) == 4 ){
                                                    //Redoublant L3 
                                                    //Redoublant L2
                                                    inscription(row_niv_anc_niv_inf.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                                    inscription(row_niv_anc_niv_sup.getAttribute("IdParcoursMaquetAnnee").toString(), id_etud, id_pers);
                                        
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                }
            }
        }

    }

}
