package decaissement_etab;

import decaissement_depart.DecaisserDepartBean;

import java.text.NumberFormat;

import java.util.Date;
import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DecaisserEtabBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    
    private RichPopup popRechCmpte;
    private RichInputText inputNumCmpte;
    private String num_cmpte;
    private RichInputText inputBen;
    private RichPopup popRechBen;
    private RichInputText inputNom;
    private RichInputText inputTel;
    private RichInputText inputPrenom;
    private RichInputText inputCmpteBanq;
    private RichInputText inputMandat;
    private String num_ben;
    private String nom;
    private String tel;
    private String prenom;
    private String cmpte_banq;
    private String mandat;
    private RichPopup popInsBen;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichInputText inputNumOp;
    private String num_op;
    private String montant;
    private Date date_op;
    private String objet;
    private String banque;
    private String num_chq_vir;
    private RichPopup popNotCmpt;
    private RichPopup popNotBen;
    private RichPopup popNotMont;
    private RichPopup popMontSup;
    private RichPopup popConfOper;
    private RichInputText inputSoldeAct;
    private String solde_act;
    private RichInputText inputMontant;
    private RichInputText inputObjet;
    private RichInputText inputBanq;
    private RichInputText inputNumChqVir;
    private String somme_total;
    
    public DecaisserEtabBean() {
    }

    @SuppressWarnings("unchecked")
    public void onRechercheCmpte(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op_les_cmpt = getBindings().getOperationBinding("getCompteEtabDec");
        op_les_cmpt.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));
        op_les_cmpt.getParamsMap().put("id_annee",  getAnne_univers());
        op_les_cmpt.getParamsMap().put("id_util",  getUtilisateur());
        op_les_cmpt.execute();

        RichPopup popup = this.getPopRechCmpte();                 
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
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


    public void setPopRechCmpte(RichPopup popRechCmpte) {
        this.popRechCmpte = popRechCmpte;
    }

    public RichPopup getPopRechCmpte() {
        return popRechCmpte;
    }

    public void setInputNumCmpte(RichInputText inputNumCmpte) {
        this.inputNumCmpte = inputNumCmpte;
    }

    public RichInputText getInputNumCmpte() {
        return inputNumCmpte;
    }

    public void setNum_cmpte(String num_cmpte) {
        this.num_cmpte = num_cmpte;
    }

    public String getNum_cmpte() {
        return num_cmpte;
    }

    public void setInputBen(RichInputText inputBen) {
        this.inputBen = inputBen;
    }

    public RichInputText getInputBen() {
        return inputBen;
    }

    public void setPopRechBen(RichPopup popRechBen) {
        this.popRechBen = popRechBen;
    }

    public RichPopup getPopRechBen() {
        return popRechBen;
    }

    public void setInputNom(RichInputText inputNom) {
        this.inputNom = inputNom;
    }

    public RichInputText getInputNom() {
        return inputNom;
    }

    public void setInputTel(RichInputText inputTel) {
        this.inputTel = inputTel;
    }

    public RichInputText getInputTel() {
        return inputTel;
    }

    public void setInputPrenom(RichInputText inputPrenom) {
        this.inputPrenom = inputPrenom;
    }

    public RichInputText getInputPrenom() {
        return inputPrenom;
    }

    public void setInputCmpteBanq(RichInputText inputCmpteBanq) {
        this.inputCmpteBanq = inputCmpteBanq;
    }

    public RichInputText getInputCmpteBanq() {
        return inputCmpteBanq;
    }

    public void setInputMandat(RichInputText inputMandat) {
        this.inputMandat = inputMandat;
    }

    public RichInputText getInputMandat() {
        return inputMandat;
    }

    public void setNum_ben(String num_ben) {
        this.num_ben = num_ben;
    }

    public String getNum_ben() {
        return num_ben;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setCmpte_banq(String cmpte_banq) {
        this.cmpte_banq = cmpte_banq;
    }

    public String getCmpte_banq() {
        return cmpte_banq;
    }

    public void setMandat(String mandat) {
        this.mandat = mandat;
    }

    public String getMandat() {
        return mandat;
    }

    public void setPopInsBen(RichPopup popInsBen) {
        this.popInsBen = popInsBen;
    }

    public RichPopup getPopInsBen() {
        return popInsBen;
    }

    public void setInputNumOp(RichInputText inputNumOp) {
        this.inputNumOp = inputNumOp;
    }

    public RichInputText getInputNumOp() {
        return inputNumOp;
    }

    public void setNum_op(String num_op) {
        this.num_op = num_op;
    }

    public String getNum_op() {
        return num_op;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getMontant() {
        return montant;
    }

    public void setDate_op(Date date_op) {
        this.date_op = date_op;
    }

    public Date getDate_op() {
        return date_op;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getObjet() {
        return objet;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getBanque() {
        return banque;
    }

    public void setNum_chq_vir(String num_chq_vir) {
        this.num_chq_vir = num_chq_vir;
    }

    public String getNum_chq_vir() {
        return num_chq_vir;
    }

    public void setPopNotCmpt(RichPopup popNotCmpt) {
        this.popNotCmpt = popNotCmpt;
    }

    public RichPopup getPopNotCmpt() {
        return popNotCmpt;
    }

    public void setPopNotBen(RichPopup popNotBen) {
        this.popNotBen = popNotBen;
    }

    public RichPopup getPopNotBen() {
        return popNotBen;
    }

    public void setPopNotMont(RichPopup popNotMont) {
        this.popNotMont = popNotMont;
    }

    public RichPopup getPopNotMont() {
        return popNotMont;
    }

    public void setPopMontSup(RichPopup popMontSup) {
        this.popMontSup = popMontSup;
    }

    public RichPopup getPopMontSup() {
        return popMontSup;
    }

    public void setPopConfOper(RichPopup popConfOper) {
        this.popConfOper = popConfOper;
    }

    public RichPopup getPopConfOper() {
        return popConfOper;
    }

    public void setInputSoldeAct(RichInputText inputSoldeAct) {
        this.inputSoldeAct = inputSoldeAct;
    }

    public RichInputText getInputSoldeAct() {
        return inputSoldeAct;
    }

    public void setSolde_act(String solde_act) {
        this.solde_act = solde_act;
    }

    public String getSolde_act() {
        return solde_act;
    }

    public void setInputMontant(RichInputText inputMontant) {
        this.inputMontant = inputMontant;
    }

    public RichInputText getInputMontant() {
        return inputMontant;
    }

    public void setInputObjet(RichInputText inputObjet) {
        this.inputObjet = inputObjet;
    }

    public RichInputText getInputObjet() {
        return inputObjet;
    }

    public void setInputBanq(RichInputText inputBanq) {
        this.inputBanq = inputBanq;
    }

    public RichInputText getInputBanq() {
        return inputBanq;
    }

    public void setInputNumChqVir(RichInputText inputNumChqVir) {
        this.inputNumChqVir = inputNumChqVir;
    }

    public RichInputText getInputNumChqVir() {
        return inputNumChqVir;
    }

    public void setSomme_total(String somme_total) {
        this.somme_total = somme_total;
    }

    public String getSomme_total() {
        return somme_total;
    }


    public void onRechercheBen(ActionEvent actionEvent) {
        // Add event code here...
        RichPopup popup = this.getPopRechBen();                 
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
    }

    public void onInsertBen(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_strc = (AttributeBinding) getBindings().getControlBinding("IdStructureBen");
        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCreeBen");
        //NumeroCompteBen
        AttributeBinding num_cmpt_ben = (AttributeBinding) getBindings().getControlBinding("NumeroCompteBen");
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("BeneficaireIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));    

        DCIteratorBinding iter_op_cmpt_dept = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteEtabDecaisseRechROIterator");
        Row row_op_cmpt_dept = iter_op_cmpt_dept.getCurrentRow();
        if(row_op_cmpt_dept != null) { 
            
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertBeneficiaire");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return ;
            }
            id_strc.setInputValue(row_op_cmpt_dept.getAttribute("IdStructure"));
            id_util.setInputValue(getUtilisateur());
            OperationBinding op_seq = getBindings().getOperationBinding("next_seq_ben");
            Integer res_seq = (Integer)op_seq.execute();
            num_cmpt_ben.setInputValue("BEN"+"_"+(res_seq+1));
            
            RichPopup popup = this.getPopInsBen();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        else{
            //veuillez selectionner un cmpte
            RichPopup popup = this.getPopNotCmpt();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void onValiderOperation(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        //IdModePaiement
        DCIteratorBinding iter_op_cmpt_dept = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteEtabDecaisseRechROIterator");
        Row row_op_cmpt_dept = iter_op_cmpt_dept.getCurrentRow();
        if(row_op_cmpt_dept != null) { 
            System.out.println("getNum_ben() "+sessionScope.get("num_ben_op_var"));
            if(sessionScope.get("num_ben_op_var") != null){

                if(getMontant() != null){
                    
                    if(Integer.parseInt( row_op_cmpt_dept.getAttribute("SoldeActuel").toString()) >= Integer.parseInt(getMontant())){
                        
                        sessionScope.put("num_cmpte_var", getNum_cmpte());                    
                        sessionScope.put("montant_var", getMontant());                    
                        sessionScope.put("objet_var", getObjet());                    
                        sessionScope.put("banque_var", getBanque());                    
                        sessionScope.put("num_chq_vir_var", getNum_chq_vir());                    
                        sessionScope.put("date_op_var", getDate_op());                    
                        sessionScope.put("num_op_var", getNum_op());
                        
                        RichPopup popup = this.getPopConfOper();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }
                    else{
                        // le solde disponible dans le compte est inf au montant de l'op 
                        RichPopup popup = this.getPopMontSup();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        popup.show(hints);
                    }                    
                }
                else{
                    // Veuillez saisir le montant de l'opération
                    RichPopup popup = this.getPopNotMont();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popup.show(hints);
                }
                               
            }
            else{
                // veuillez sélectionner ou ajouter un bénéficiaire
                RichPopup popup = this.getPopNotBen();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
        }
        else{
            // veuillez sélectionner un compte
            RichPopup popup = this.getPopNotCmpt();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        
        String num_recu_prec = "0";
        OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
        op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_annee.execute();
        DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
        Row row_oper_annee = iter_oper_annee.getCurrentRow();
        if(row_oper_annee != null)
            num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
        numero_recu_suivant(num_recu_prec);
        setNum_op(numero_recu_suivant(num_recu_prec));
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumOp());
    }

    public void onDialogRechercheBen(DialogEvent dialogEvent) {
        // Add event code here...
        DCIteratorBinding iter_op_benef = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("BeneficiaireROIterator");
        Row row_op_benef = iter_op_benef.getCurrentRow();
        if(row_op_benef != null){
            if(row_op_benef.getAttribute("NumeroCompte")!= null){
                sessionScope.put("num_ben_op_var", row_op_benef.getAttribute("NumeroCompte").toString());
                setNum_ben(row_op_benef.getAttribute("NumeroCompte").toString());
            }
            if(row_op_benef.getAttribute("Nom")!= null){
                setNom(row_op_benef.getAttribute("Nom").toString());
            }
            if(row_op_benef.getAttribute("Prenom")!= null){
                setPrenom(row_op_benef.getAttribute("Prenom").toString());
            }
            if(row_op_benef.getAttribute("Telephone")!= null){
                setTel(row_op_benef.getAttribute("Telephone").toString());
            }
            if(row_op_benef.getAttribute("NumCompteBanque")!= null){
                setCmpte_banq(row_op_benef.getAttribute("NumCompteBanque").toString());
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBen()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputCmpteBanq()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNom()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumCmpte()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputPrenom()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputTel()); 
        }
        else{
            //
        }
    }

    public void onDialogBenCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopRechBen().hide();
    }

    public void onDialogInsBen(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("Proprietaire");
        
        AttributeBinding nom_bng = (AttributeBinding) getBindings().getControlBinding("Nom");
        AttributeBinding prenom_bng = (AttributeBinding) getBindings().getControlBinding("Prenom");
        AttributeBinding tel_bng = (AttributeBinding) getBindings().getControlBinding("Telephone");
        AttributeBinding cmpt_bng = (AttributeBinding) getBindings().getControlBinding("NumCompteBanque");
        AttributeBinding num_ben_bng = (AttributeBinding) getBindings().getControlBinding("NumeroCompteBen");
        /* AttributeBinding proprietaire = (AttributeBinding) getBindings().getControlBinding("Proprietaire");*/
        
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            System.out.println("getPrenom() "+prenom_bng.getInputValue());
            System.out.println("getNom() "+nom_bng.getInputValue());
            if(prenom_bng.getInputValue() != null && nom_bng.getInputValue() != null)
                proprietaire.setInputValue(prenom_bng.getInputValue()+"  "+nom_bng.getInputValue());
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopInsBen().hide();
            
            if(num_ben_bng.getInputValue()!= null){
                sessionScope.put("num_ben_op_var", num_ben_bng.getInputValue().toString());
                setNum_ben(num_ben_bng.getInputValue().toString());
            }
            if(nom_bng.getInputValue()!= null){
                setNom(nom_bng.getInputValue().toString());
            }
            if(prenom_bng.getInputValue()!= null){
                setPrenom(prenom_bng.getInputValue().toString());
            }
            if(tel_bng.getInputValue()!= null){
                setTel(tel_bng.getInputValue().toString());
            }
            if(cmpt_bng.getInputValue()!= null){
                setCmpte_banq(cmpt_bng.getInputValue().toString());
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBen()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputCmpteBanq()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNom()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumCmpte()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputPrenom()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputTel());
        }
    }

    public void onDialogInsCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopInsBen().hide();
    }

    public void onDialogConfOper(DialogEvent dialogEvent) {
        // Add event code here...
        AttributeBinding id_mode_paie = (AttributeBinding) getBindings().getControlBinding("IdModePaiement");
        
        System.out.println("montant_var "+sessionScope.get("montant_var"));
        System.out.println("objet_var "+sessionScope.get("objet_var"));

        if(sessionScope.get("objet_var") != null)
            setObjet(sessionScope.get("objet_var").toString());
        else
            setObjet("");

        if(sessionScope.get("banque_var") != null)
            setBanque(sessionScope.get("banque_var").toString());
        else
            setBanque("");
        
        if(sessionScope.get("num_chq_vir_var") != null)
            setNum_chq_vir(sessionScope.get("num_chq_vir_var").toString());
        else
            setNum_chq_vir("");

        System.out.println("date_op_var "+sessionScope.get("date_op_var"));
        if(sessionScope.get("date_op_var") != null)
            setDate_op((Date)sessionScope.get("date_op_var"));
        else
            setDate_op(new java.util.Date());
        System.out.println("num_op_var "+sessionScope.get("num_op_var"));
            
        DCIteratorBinding iter_op_cmpt_dept = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteEtabDecaisseRechROIterator");
        Row row_op_cmpt_dept = iter_op_cmpt_dept.getCurrentRow();
        //if(row_op_cmpt_dept != null) { 
          
        Integer res_op = insertOperation(sessionScope.get("montant_var").toString(), "2", id_mode_paie.getInputValue().toString(), row_op_cmpt_dept.getAttribute("IdCompte").toString(), getNum_chq_vir(), getBanque(),  getObjet(), "0", sessionScope.get("num_op_var").toString(), "0",sessionScope.get("num_ben_op_var").toString(),getDate_op(),"2"); 
        
        if(res_op > 0){
            ///getDetailCmpte
            OperationBinding op_detail_cmpt = getBindings().getOperationBinding("getDetailCmpte");
            op_detail_cmpt.getParamsMap().put("id_cmpte", row_op_cmpt_dept.getAttribute("IdCompte"));
            op_detail_cmpt.execute();
            DCIteratorBinding iter_op_detail_cmpt = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DetailsCompteROIterator");
            Row row_op_detail_cmpt = iter_op_detail_cmpt.getCurrentRow();
        
            String num_recu_prec = "0";
            OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
            op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
            op_oper_annee.execute();
            
            DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
            Row row_oper_annee = iter_oper_annee.getCurrentRow();
            if(row_oper_annee != null)
                num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
            numero_recu_suivant(num_recu_prec);        
            setNum_op(numero_recu_suivant(num_recu_prec));
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumOp());
            
            setMontant("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputMontant());
        
            setObjet("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputObjet());
        
            setBanque("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBanq());
        
            setNum_chq_vir("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumChqVir());
        
            
            setNum_ben("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputBen());
        
            setNom("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNom());
        
            setPrenom("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputPrenom());
        
            setTel("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputTel());
        
            setCmpte_banq("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputCmpteBanq());
        }
        
        sessionScope.remove("num_cmpte_var");        
        sessionScope.remove("montant_var");        
        sessionScope.remove("objet_var");       
        sessionScope.remove("banque_var");        
        sessionScope.remove("num_chq_vir_var");        
        sessionScope.remove("date_op_var");       
        sessionScope.remove("num_op_var");
    }

    public void onDialogConfOpCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfOper().hide();
        sessionScope.remove("num_cmpte_var");        
        sessionScope.remove("montant_var");        
        sessionScope.remove("objet_var");       
        sessionScope.remove("banque_var");        
        sessionScope.remove("num_chq_vir_var");        
        sessionScope.remove("date_op_var");       
        sessionScope.remove("num_op_var");
    }

    @SuppressWarnings("unchecked")
    public void onDialogRechercheCmpte(DialogEvent dialogEvent) {
        // Add event code here...
        DCIteratorBinding iter_op_cmpt_dept = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteEtabDecaisseRechROIterator");
        Row row_op_cmpt_dept = iter_op_cmpt_dept.getCurrentRow();
        if(row_op_cmpt_dept != null){
            setNum_cmpte(row_op_cmpt_dept.getAttribute("NumeroCompte").toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputNumCmpte()); 
            
            ///getDetailCmpte
            OperationBinding op_detail_cmpt = getBindings().getOperationBinding("getDetailCmpte");
            op_detail_cmpt.getParamsMap().put("id_cmpte", row_op_cmpt_dept.getAttribute("IdCompte"));
            op_detail_cmpt.execute();
        }
    }

    public void onDialogCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopRechCmpte().hide();
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
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
    public void refresh_decaisse_dpt(){
        String num_recu_prec = "0";
        OperationBinding op_oper_annee = getBindings().getOperationBinding("getOperationAnnee");
        op_oper_annee.getParamsMap().put("id_annee",  getAnne_univers());
        op_oper_annee.execute();
        DCIteratorBinding iter_oper_annee = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAnneeROIterator");
        Row row_oper_annee = iter_oper_annee.getCurrentRow();
        if(row_oper_annee != null)
            num_recu_prec = row_oper_annee.getAttribute("NumeroRecu").toString();
        numero_recu_suivant(num_recu_prec);
        setNum_op(numero_recu_suivant(num_recu_prec));
        java.util.Date utilDate = new java.util.Date();
        setDate_op(utilDate);
        
    }
    @SuppressWarnings("unchecked")
    public void refresh_detail_cmpt_dpt(){
        DCIteratorBinding iter_op_cmpt_dept = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("CompteEtabDecaisseRechROIterator");
        Row row_op_cmpt_dept = iter_op_cmpt_dept.getCurrentRow();
        if(row_op_cmpt_dept != null){
            //getDetailCompteDecaisse
            OperationBinding op_detail_cmpt = getBindings().getOperationBinding("getDetailCompteDecaisse");
            op_detail_cmpt.getParamsMap().put("id_compte",  row_op_cmpt_dept.getAttribute("IdCompte"));
            op_detail_cmpt.getParamsMap().put("id_annee",  getAnne_univers());
            op_detail_cmpt.execute();
            DCIteratorBinding iter_op_det_cmpt = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DetailOpCmpteDecaisseROIterator");
            Row row_op_det_cmpt = iter_op_det_cmpt.getCurrentRow();
            if(row_op_det_cmpt != null){
                RowSetIterator rsi_op_det_cmpt = iter_op_det_cmpt.getViewObject().createRowSetIterator(null);
                Integer i = 0;
                Integer somme = 0;
                while (rsi_op_det_cmpt.hasNext()) {
                    Row nextRow = rsi_op_det_cmpt.next();
                    if(nextRow.getAttribute("Montant") != null){
                       somme = somme +  Integer.parseInt(nextRow.getAttribute("Montant").toString());
                    }

                }
                System.out.println("somme "+somme);
                
                NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);
                System.out.println("som format "+numberFormat.format(somme));
                setSomme_total(numberFormat.format(somme));
            }
        }

    }
        
    @SuppressWarnings("unchecked")
    public Integer insertOperation(String montant,String type,String mode_paie,String compte,String cheq,String banq,String motif_paie,String debiter,String numero_recu,String valider,String beneficiaire,Date date_op,String op_val){
        
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
        
        AttributeBinding op_validee = (AttributeBinding) getBindings().getControlBinding("OpValidee");
        Integer i = 0;
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsertOperation");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return i;
        }

        montant_oper.setInputValue(montant);
        date_oper.setInputValue(date_op);
        type_oper.setInputValue(type);
        id_mode_paie.setInputValue(mode_paie);
        id_compte.setInputValue(compte);
        banque.setInputValue(banq);
        motif.setInputValue(motif_paie);
        debit.setInputValue(debiter);
        num_recu.setInputValue(numero_recu);
        valide.setInputValue(valider);
        id_util_cree.setInputValue(getUtilisateur());
        id_annee.setInputValue(getAnne_univers());
        benif.setInputValue(beneficiaire);
        op_validee.setInputValue(op_val);
    
        if(Integer.parseInt(mode_paie) == 2){
            num_cheq.setInputValue(cheq);  
        }
        else{
            if(Integer.parseInt(mode_paie) == 3){
                num_virement.setInputValue(cheq);
            }
            else{
                
            }
            
        }
        OperationBinding op_commit_op = getBindings().getOperationBinding("Commit");
        Object result_oper = op_commit_op.execute();
        if (!op_commit_op.getErrors().isEmpty()) {
                return i;
        }
        else{
            i ++;
            
        }
       return i; 
    }       

}
