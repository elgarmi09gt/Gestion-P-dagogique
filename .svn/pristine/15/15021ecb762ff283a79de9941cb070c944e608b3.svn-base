package comptable_etab;

import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class ComptableEtabBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    private String calendrier = sessionScope.get("id_calendrier").toString();
    private String semestre = sessionScope.get("id_smstre").toString();
    private String nbr_valider;
    private RichPopup popConfVal;
    private RichPopup popAucunOper;
    private RichPopup popNotOper;
    private RichPopup popConfOperCmptable;
    
    private String nbr_op_valider;
    private String nbr_op_non_valider;
    private RichSelectBooleanCheckbox chek;

    public ComptableEtabBean() {
    }

    public void setNbr_op_valider(String nbr_op_valider) {
        this.nbr_op_valider = nbr_op_valider;
    }

    public String getNbr_op_valider() {
        return nbr_op_valider;
    }

    public void setNbr_op_non_valider(String nbr_op_non_valider) {
        this.nbr_op_non_valider = nbr_op_non_valider;
    }

    public String getNbr_op_non_valider() {
        return nbr_op_non_valider;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row row_op = rsi.next();
            if(row_op.getAttribute("OpValidee")!=null){
                if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 3 ){
                    i++;
                }
            }
        }
        return i;
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

    public void setNbr_valider(String nbr_valider) {
        this.nbr_valider = nbr_valider;
    }

    public String getNbr_valider() {
        return nbr_valider;
    }

    public String onValidationCmptableEtab() {
        // Add event code here...
        System.out.println("dans la methode recu ");
        DCIteratorBinding iter_op_avalide = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAValCmptableROIterator");
        Row row_op_avalide = iter_op_avalide.getCurrentRow();
        RowSetIterator rsi_op_avalide = iter_op_avalide.getViewObject().createRowSetIterator(null);
        
        if(row_op_avalide != null){
            if(nombreCaseCoche("OperationAValCmptableROIterator") > 0){
                setNbr_valider(nombreCaseCoche("OperationAValCmptableROIterator")+"");
                RichPopup popup = this.getPopConfVal();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);

            }
            else{
                //veuillez au moins une operation à valideer
                RichPopup popup = this.getPopAucunOper();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }

        }
        else{
            //Aucune opération à valider
            RichPopup popup = this.getPopNotOper();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        
        return null;
    }

    public void setPopConfVal(RichPopup popConfVal) {
        this.popConfVal = popConfVal;
    }

    public RichPopup getPopConfVal() {
        return popConfVal;
    }

    @SuppressWarnings("unchecked")
    public void onDialogValOper(DialogEvent dialogEvent) {
        // Add event code here...
        
        DCIteratorBinding iter_op_avalide = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("OperationAValCmptableROIterator");
        Row row_op_avalide = iter_op_avalide.getCurrentRow();
        RowSetIterator rsi_op_avalide = iter_op_avalide.getViewObject().createRowSetIterator(null);

        Integer i = 0;
        Integer j = 0;
        
        while (rsi_op_avalide.hasNext()) {
            Row row_op = rsi_op_avalide.next();
            if(row_op.getAttribute("OpValidee")!=null){
                if(Integer.parseInt(row_op.getAttribute("OpValidee").toString()) == 3 ){
                    OperationBinding getDetailCmpte = getBindings().getOperationBinding("getDetailsCompte");
                    getDetailCmpte.getParamsMap().put("id_cmpte",  row_op.getAttribute("IdCompte"));  
                    getDetailCmpte.execute();
                    DCIteratorBinding iter_op_detail_cmpt = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("DetailsCompteROIterator");
                    Row row_op_detail_cmpt = iter_op_detail_cmpt.getCurrentRow();
                    if(row_op_detail_cmpt.getAttribute("SoldeActuel") != null && row_op.getAttribute("Montant") != null){
                        if(Integer.parseInt(row_op_detail_cmpt.getAttribute("SoldeActuel").toString()) >= Integer.parseInt(row_op.getAttribute("Montant").toString())) {                            
                            
                            Integer solde = ( Integer.parseInt(row_op_detail_cmpt.getAttribute("SoldeActuel").toString()) - Integer.parseInt(row_op.getAttribute("Montant").toString()));
                            
                            //update l'operation courante faite par le comptable de l'établissement
                            // le montant de la dépense est bien débité dans le compte courant; l'opération est validée
                            OperationBinding getUpdateValOper = getBindings().getOperationBinding("findAndUpdateValOperCmptable");
                            getUpdateValOper.getParamsMap().put("id_oper",  row_op.getAttribute("IdOperation"));  
                            getUpdateValOper.getParamsMap().put("debit",  "1");  
                            getUpdateValOper.getParamsMap().put("valide",  "1");  
                            getUpdateValOper.getParamsMap().put("opvalide",  "3");  
                            getUpdateValOper.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateValOper.execute();
                            // validation du nouveau solde
                            OperationBinding getUpdateCmpte = getBindings().getOperationBinding("findAndUpdateCompteEtud");
                            getUpdateCmpte.getParamsMap().put("id_solde_compte",  row_op.getAttribute("IdSoldeCompte"));  
                            getUpdateCmpte.getParamsMap().put("solde_act",  solde);  
                            getUpdateCmpte.getParamsMap().put("util",  getUtilisateur());  
                            getUpdateCmpte.execute();
                            //si le compte est un compte d'une formation
                            //il est necessaire d'appliqué la même modification dans le compte global de la formation
                            if(Integer.parseInt(row_op_detail_cmpt.getAttribute("IdTypeCompte").toString()) == 6 
                               || Integer.parseInt(row_op_detail_cmpt.getAttribute("IdTypeCompte").toString()) == 7 
                               || Integer.parseInt(row_op_detail_cmpt.getAttribute("IdTypeCompte").toString()) == 8){
                                refresh_cmpte_glob(row_op_detail_cmpt.getAttribute("IdStructure").toString(), sessionScope.get("id_hs").toString(), row_op.getAttribute("Montant").toString());
                               }
                            
                            i ++;

                        }
                        else{
                            // le solde du compte est insuffisant
                            j ++;
                        }
                    }
                    else{
                        // le solde du compte ou le montant est null
                        j ++;
                    }                    
                }
            }
        }
        
        setNbr_op_non_valider(j+"");
        setNbr_op_valider(i+"");
        RichPopup popup = this.getPopConfOperCmptable();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        
        OperationBinding getOperVal = getBindings().getOperationBinding("getOperationValCptableEtab");
        getOperVal.getParamsMap().put("id_annee",  getAnne_univers());  
        getOperVal.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));  
        getOperVal.getParamsMap().put("id_util",  getUtilisateur());  
        getOperVal.execute();
        
        OperationBinding getOperAVal = getBindings().getOperationBinding("getOperationAValCptableEtab");
        getOperAVal.getParamsMap().put("id_annee",  getAnne_univers());  
        getOperAVal.getParamsMap().put("id_hs",  sessionScope.get("id_hs"));  
        getOperAVal.getParamsMap().put("id_util",  getUtilisateur()); 
        getOperAVal.execute();
        chek.setValue(false);
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

    public void onDialogValOperCan(ClientEvent clientEvent) {
        // Add event code here...
        this.getPopConfVal().hide();
    }

    public void setPopAucunOper(RichPopup popAucunOper) {
        this.popAucunOper = popAucunOper;
    }

    public RichPopup getPopAucunOper() {
        return popAucunOper;
    }

    public void setPopNotOper(RichPopup popNotOper) {
        this.popNotOper = popNotOper;
    }

    public RichPopup getPopNotOper() {
        return popNotOper;
    }

    public void setPopConfOperCmptable(RichPopup popConfOperCmptable) {
        this.popConfOperCmptable = popConfOperCmptable;
    }

    public RichPopup getPopConfOperCmptable() {
        return popConfOperCmptable;
    }

    public void onSelectTout(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(Boolean.parseBoolean(chek.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("OperationAValCmptableROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("OpValidee", 3);
            }
        }
        else{
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("OperationAValCmptableROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("OpValidee", 2);
            }
        }
    }

    public void setChek(RichSelectBooleanCheckbox chek) {
        this.chek = chek;
    }

    public RichSelectBooleanCheckbox getChek() {
        return chek;
    }
}
