package etudiant_mobilite;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class DetailEtudModileBean {
    private RichSelectBooleanCheckbox check;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String id_sem = sessionScope.get("id_smstre").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    public DetailEtudModileBean() {
    }


    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setId_sem(String id_sem) {
        this.id_sem = id_sem;
    }

    public String getId_sem() {
        return id_sem;
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

    public void onSelectAll(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(Boolean.parseBoolean(check.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("HistoMobiliteROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
                System.out.println("case_cocher case_cocher"+nextRow.getAttribute("case_cocher"));
            }
        }
        else{
            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("HistoMobiliteROIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    public void setCheck(RichSelectBooleanCheckbox check) {
        this.check = check;
    }

    public RichSelectBooleanCheckbox getCheck() {
        return check;
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
    public String onValiderInscPedSemUe() {
        // Add event code here...
        DCIteratorBinding iter_list_insc_ped = (DCIteratorBinding)getBindings().get("HistoMobiliteROIterator");
        RowSetIterator row_list_ue = iter_list_insc_ped.getViewObject().createRowSetIterator(null);
        
        Integer nbr_ue = 0;
        //verifie si le tableau est vide
        if(row_list_ue.getRowCount() == 0){
            FacesMessage msg = new FacesMessage(" Aucune UE disponible dans le département sélectionné ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            // pas de UE sélectionnées
            if(nombreCaseCoche("HistoMobiliteROIterator") == 0){
                FacesMessage msg = new FacesMessage(" Veuillez sélectionner au moins une UE ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{
                while (row_list_ue.hasNext()) {
                    Row nextRow = row_list_ue.next();
                    if(nextRow.getAttribute("case_cocher")!=null){
                        if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                            AttributeBinding id_fil_ue_sem = (AttributeBinding) getBindings().getControlBinding("IdFiliereUeSemstre");
                            AttributeBinding id_insc_sem_ue = (AttributeBinding) getBindings().getControlBinding("IdInscriptionPedSemestre");
                            AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
                            // insertion dans la table inscriptionPedSemUe
                            OperationBinding op_insc_ue = getBindings().getOperationBinding("CreateInsertInscPedSemUe");
                            Object result_insert_insc_admin = op_insc_ue.execute();
                                                    
                            if (!op_insc_ue.getErrors().isEmpty()) {
                                    return null;
                            }
                            else{
                                id_fil_ue_sem.setInputValue(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
                                id_insc_sem_ue.setInputValue(Long.parseLong(sessionScope.get("insc_ped_sem_etud_mob").toString()));
                                id_util.setInputValue(Long.parseLong(getUtilisateur()));
                                // validation de l'insertion
                                OperationBinding op_commit_insc_admin = getBindings().getOperationBinding("Commit");
                                Object result = op_commit_insc_admin.execute();
                                if (!op_commit_insc_admin.getErrors().isEmpty()) {
                                        return null;
                                }
                                // nombre de UE validées
                                else{
                                    nbr_ue ++;
                                }
                            }
                        }
                    }
                }
                // le message une fois qu'il ya au moins une Ue à valider
                if(nbr_ue > 0){
                    FacesMessage msg = new FacesMessage(" Inscription de "+nbr_ue+" UE(s) validée(s) avec succès ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    // refresh le tableau des Ue
                    OperationBinding list_insc_ped = getBindings().getOperationBinding("getListeUe");
                    list_insc_ped.getParamsMap().put("id_pers",  Long.parseLong(sessionScope.get("id_pers_etud_mob").toString()));
                    list_insc_ped.getParamsMap().put("id_sem",  Long.parseLong(getId_sem()));
                    list_insc_ped.getParamsMap().put("id_annee",  Long.parseLong(getAnne_univers()));
                    list_insc_ped.execute();
                    
                    OperationBinding findAndUpdateEtud = getBindings().getOperationBinding("findAndUpdateEtud");//id_etud_mob
                    findAndUpdateEtud.getParamsMap().put("id_etud", Long.parseLong(sessionScope.get("id_etud_mob").toString()));
                    findAndUpdateEtud.getParamsMap().put("mobilite", Long.parseLong("1"));
                    findAndUpdateEtud.execute();
                    
                }
                else{
                    FacesMessage msg = new FacesMessage(" Aucune Inscription UE validée ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
        
        return null;
    }
}
