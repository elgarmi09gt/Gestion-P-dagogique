package view.beans.detailNote;

import java.util.Map;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import view.beans.entities.Reclamation;

public class DetailNote {
    private RichInputText inputEtudiant;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    //private Long utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Long annee = Long.parseLong(sessionScope.get("id_annee").toString());
    private Long session = Long.parseLong(sessionScope.get("id_session").toString());
    private Long semestre = Long.parseLong(sessionScope.get("id_smstre").toString());
    private Long prcrs_maq = Long.parseLong(sessionScope.get("prcrs_maq_an").toString());
    private Long calendrier = Long.parseLong(sessionScope.get("id_calendrier").toString());
    private Integer user = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer parcours = Integer.parseInt(sessionScope.get("id_niv_form_parcours").toString());;
    
    private RichPanelCollection panCollectDetails;
    private RichTable tableDetails;
    private RichPanelGroupLayout panGrpDetails;
    private RichPopup popupNotStudent;
    private RichPopup popupRecClosed;
    private RichPopup popupRecNotClosed;
    private RichPopup popupSavedSuccess;
    private Set<Reclamation> etudiantreclamations;
    public DetailNote() {
    }

    public void setInputEtudiant(RichInputText inputEtudiant) {
        this.inputEtudiant = inputEtudiant;
    }

    public RichInputText getInputEtudiant() {
        return inputEtudiant;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSearchEtudiant(ActionEvent actionEvent) {
        try {
            /* System.out.println("num : " + this.getInputEtudiant()
                                              .getValue()
                                              .toString()
                                              .toUpperCase());
            System.out.println("pma : " + getPrcrs_maq());
            System.out.println("cal : " + getCalendrier());
           DCIteratorBinding iterNote = (DCIteratorBinding) getBindings().get("NotesModeEnseignementInterIterator");
            System.out.println("size Note: " + iterNote.getEstimatedRowCount());
            */
            OperationBinding op_detail = getBindings().getOperationBinding("opSearchDetails");
            op_detail.getParamsMap().put("p_numero", this.getInputEtudiant()
                                                         .getValue()
                                                         .toString()
                                                         .toUpperCase());
            //op_detail.getParamsMap().put("p_annee", getAnnee());
            op_detail.getParamsMap().put("p_calendrier", getCalendrier());
            op_detail.getParamsMap().put("p_pma", getPrcrs_maq());
            op_detail.execute();
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("DetailNoteIterator");
            //System.out.println("size : " + iter.getEstimatedRowCount());
            if (0 != iter.getEstimatedRowCount()) {
                this.getInputEtudiant().resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDetails());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanCollectDetails());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTableDetails());
            }else{
                RichPopup popup = this.getPopupNotStudent();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }

        } catch (Exception e) {
            System.out.println();
        }
    }

    public void setAnnee(Long annee) {
        this.annee = annee;
    }

    public Long getAnnee() {
        return annee;
    }

    public void setPrcrs_maq(Long prcrs_maq) {
        this.prcrs_maq = prcrs_maq;
    }

    public Long getPrcrs_maq() {
        return prcrs_maq;
    }

    public void setCalendrier(Long calendrier) {
        this.calendrier = calendrier;
    }

    public Long getCalendrier() {
        return calendrier;
    }

    public void setPanCollectDetails(RichPanelCollection panCollectDetails) {
        this.panCollectDetails = panCollectDetails;
    }

    public RichPanelCollection getPanCollectDetails() {
        return panCollectDetails;
    }

    public void setTableDetails(RichTable tableDetails) {
        this.tableDetails = tableDetails;
    }

    public RichTable getTableDetails() {
        return tableDetails;
    }

    public void onIsAbsentChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("NotesModeEnseignementInterNewIterator");
        Row currentR = iter.getCurrentRow();
        if (null != currentR) {
            if (null != currentR.getAttribute("Note")) {
                currentR.setAttribute("Style", null);
                if (valueChangeEvent.getNewValue().equals(true)) {
                    currentR.setAttribute("IdTypeVerrou", 3);
                } else {
                    currentR.setAttribute("IdTypeVerrou", 1);
                }
            } else {
                //Si la note est vide et que le champs est décoché
                if (valueChangeEvent.getNewValue().equals(false)) {
                    currentR.setAttribute("Style", "border-style: solid; border-color: #c00; border-width: 2px;");
                } else {
                    currentR.setAttribute("Style", null);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onSaveDetails(ActionEvent actionEvent) {
        long startTime = System.currentTimeMillis();
        BindingContext bindingContext = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("DetailNoteIterator");
        Row currentR = iter.getCurrentRow();
        if (null != currentR) {
            try {
                OperationBinding op_commit = getBindings().getOperationBinding("Commit");
                Object resul = op_commit.execute();
                // System.out.println("IdInscriptionPedSemestre : "+currentR.getAttribute("IdInscriptionPedSemestre").toString()+" pma : "+getPrcrs_maq()+" cal : "+getCalendrier());
                try {
                    OperationBinding op_update = getBindings().getOperationBinding("UpdateIsAbsent");
                    op_update.getParamsMap().put("parcours_maq", getPrcrs_maq());
                    op_update.getParamsMap().put("calendrier_id", getCalendrier());
                    op_update.getParamsMap()
                        .put("ips_id", Long.parseLong(currentR.getAttribute("IdInscriptionPedSemestre").toString()));
                    op_update.getParamsMap().put("utilisateur", getUser());
                    op_update.execute();
                    
                    /*UpdateIsToPublish(calendrier_id, ips_id);
                     * try {
                        OperationBinding op_delib = getBindings().getOperationBinding("DelibererDetailNote");
                        op_delib.getParamsMap().put("parcours_maq", getPrcrs_maq());
                        op_delib.getParamsMap().put("calendrier_id", getCalendrier());
                        op_delib.getParamsMap().put("ips_id", Long.parseLong(currentR.getAttribute("IdInscriptionPedSemestre").toString()));
                        op_delib.getParamsMap().put("utilisateur", getUser());
                        op_delib.execute();
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    
               } catch (Exception e) {
                    System.out.println(e);
                }
                try {
                    OperationBinding op_delib = getBindings().getOperationBinding("UpdateIsToPublish");
                    op_delib.getParamsMap().put("calendrier_id", getCalendrier());
                    op_delib.getParamsMap()
                        .put("ips_id", Long.parseLong(currentR.getAttribute("IdInscriptionPedSemestre").toString()));
                    op_delib.execute();
                } catch (Exception e) {
                    System.out.println(e);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Temps d'exécution de la procédure UpdateIsAbsent : " + elapsedTime + " ms");
                    
            RichPopup popup = this.getPopupSavedSuccess();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getUser() {
        return user;
    }

    public void setPanGrpDetails(RichPanelGroupLayout panGrpDetails) {
        this.panGrpDetails = panGrpDetails;
    }

    public RichPanelGroupLayout getPanGrpDetails() {
        return panGrpDetails;
    }

    public void setPopupNotStudent(RichPopup popupNotStudent) {
        this.popupNotStudent = popupNotStudent;
    }

    public RichPopup getPopupNotStudent() {
        return popupNotStudent;
    }

    @SuppressWarnings("unchecked")
    public void opPdtJury(){
        BindingContainer bindings = getBindings();
        try{
            OperationBinding opjury = bindings.getOperationBinding("getUtilisateurPdtJury");
            opjury.getParamsMap().put("parcours", new Long(getParcours()));
            opjury.getParamsMap().put("annee", getAnnee());
            opjury.getParamsMap().put("semestre", getSemestre());
            opjury.getParamsMap().put("utilisateur", new Long(getUser()));
            opjury.execute();
        
            DCIteratorBinding iterpdt = (DCIteratorBinding) bindings.get("UtilisateurPDTJuryIterator");
            Row current = iterpdt.getCurrentRow();
            if(null != current){
                sessionScope.put("ispdtjury", true);
            }else{
                sessionScope.put("ispdtjury", false);
            }
            
        }catch(Exception e){
            System.out.println(e);
        } 
    }
    @SuppressWarnings("unchecked")
    public void onCloseReclamationConfirm(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            BindingContainer bindings = getBindings();
            OperationBinding opCloseRec = bindings.getOperationBinding("cloturerReclamation");
            opCloseRec.getParamsMap().put("parcours_maq", getPrcrs_maq());
            opCloseRec.getParamsMap().put("calendrier", getCalendrier());
            opCloseRec.getParamsMap().put("utilisateur", getUser());
            Integer res = (Integer) opCloseRec.execute();
            if(0 != res){
                RichPopup popup = this.getPopupRecClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }else{
                RichPopup popup = this.getPopupRecNotClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }
        }
    }

    public void setPopupRecClosed(RichPopup popupRecClosed) {
        this.popupRecClosed = popupRecClosed;
    }

    public RichPopup getPopupRecClosed() {
        return popupRecClosed;
    }

    public void setPopupRecNotClosed(RichPopup popupRecNotClosed) {
        this.popupRecNotClosed = popupRecNotClosed;
    }

    public RichPopup getPopupRecNotClosed() {
        return popupRecNotClosed;
    }

    @SuppressWarnings("unchecked")
    public void onClosedReclamation(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        try{
            OperationBinding opClose = bindings.getOperationBinding("isAllUeClosed");
            opClose.getParamsMap().put("parcours_maq", getPrcrs_maq());
            opClose.getParamsMap().put("calendrier", getCalendrier());
            opClose.getParamsMap().put("utilisateur", new Long(getUser()));      
            Integer res = (Integer) opClose.execute();
            if(0 != res){
                RichPopup popup = this.getPopupRecClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }else{
                RichPopup popup = this.getPopupRecClosed();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void setSession(Long session) {
        this.session = session;
    }

    public Long getSession() {
        return session;
    }

    public void setSemestre(Long semestre) {
        this.semestre = semestre;
    }

    public Long getSemestre() {
        return semestre;
    }

    public void setPopupSavedSuccess(RichPopup popupSavedSuccess) {
        this.popupSavedSuccess = popupSavedSuccess;
    }

    public RichPopup getPopupSavedSuccess() {
        return popupSavedSuccess;
    }

    public void OnNoteChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        Reclamation rl = new Reclamation();
        DCBindingContainer bindings = (DCBindingContainer) bindingContext.getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("DetailNoteIterator");
        Row currentR = iter.getCurrentRow();
        System.out.println("fec : "+currentR.getAttribute("IdFiliereUeSemstreEc").toString()+" pma : "+currentR.getAttribute("IdParcoursMaquetteAnnee").toString());
        System.out.println("new value : "+valueChangeEvent.getNewValue()+" old : "+valueChangeEvent.getOldValue());
        if(valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()){
            rl.setFilEc(Long.parseLong(currentR.getAttribute("IdFiliereUeSemstreEc").toString()));
            rl.setFilUe(Long.parseLong(currentR.getAttribute("IdFiliereUeSemstre").toString()));
            rl.setTpCntrl(Long.parseLong(currentR.getAttribute("IdTypeControle").toString()));
            rl.setPmaId(Long.parseLong(currentR.getAttribute("IdParcoursMaquetteAnnee").toString()));
            rl.setCalId(Long.parseLong(currentR.getAttribute("IdCalendrierDeliberation").toString()));
        }
        etudiantreclamations.add(rl);
    }

    public void setParcours(Integer parcours) {
        this.parcours = parcours;
    }

    public Integer getParcours() {
        return parcours;
    }
}
