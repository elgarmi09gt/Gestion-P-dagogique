package view.beans.adminSupport;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class AdminSupport {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());
    private Long annee = Long.parseLong(sessionScope.get("id_annee").toString());
    private RichPopup popSuccessImport;
    private RichInputText inputEtudiant;
    private RichPanelGroupLayout panelformetu;
    private RichSelectBooleanCheckbox inputIndiv;
    private RichPanelGroupLayout panelBtnClear;
    private RichPopup popupNoNum;
    private RichInputText inputEtd;
    private RichPanelGroupLayout panGrpDetInscRef;
    private RichPanelCollection panColDetInscRef;
    private RichPanelCollection panColEtdScolRef;
    private RichPanelGroupLayout panGrpEtdScolRef;
    private RichPopup popNoStudent;
    private RichPopup popUpdated;

    public AdminSupport() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnImportAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPm = (DCIteratorBinding) bindings.get("MaquetteParcoursAnneeIterator");
        Row currentPm = iterPm.getCurrentRow();
        
        DCIteratorBinding iterFr = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentFr = iterFr.getCurrentRow();
        /*System.out.println("pma : "+currentPm.getAttribute("IdParcoursMaquetAnnee"));
        System.out.println("code_niv_s : "+currentFr.getAttribute("AncienCode"));
        if(null != currentFr.getAttribute("Codeoptionscol"))
            System.out.println("Codeoptionscol : "+currentFr.getAttribute("Codeoptionscol"));
        if(null != currentPm.getAttribute("CodeOption"))
            System.out.println("CODE_OPTION "+currentPm.getAttribute("CodeOption"));
        System.out.println("fr : "+currentFr.getAttribute("Scolcodefrr"));*/
        try {
            OperationBinding opimport = bindings.getOperationBinding("ImportEtudiantsSup");
            opimport.getParamsMap().put("prcrs", currentPm.getAttribute("IdParcoursMaquetAnnee"));
            opimport.getParamsMap().put("code_niv_s", currentFr.getAttribute("AncienCode"));
            opimport.getParamsMap().put("annee", this.getAnnee());
            opimport.getParamsMap().put("code_op", currentFr.getAttribute("Codeoptionscol") == null ? currentPm.getAttribute("CodeOption") :
                     currentFr.getAttribute("Codeoptionscol"));
            opimport.getParamsMap().put("code_fr", currentFr.getAttribute("Scolcodefrr"));
            opimport.getParamsMap().put("utilisateur", getUtilisateur());
            opimport.execute();
            RichPopup popup = this.getPopSuccessImport();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        } catch (Exception e) {
            System.out.println(e);
        }
        /*try {
            OperationBinding opimport = bindings.getOperationBinding("ImportEtudiants");
            opimport.getParamsMap().put("prcrs", currentPm.getAttribute("IdParcoursMaquetAnnee"));
            opimport.getParamsMap().put("code_niv_s", currentFr.getAttribute("AncienCode"));
            opimport.getParamsMap().put("annee", this.getAnnee());
            opimport.getParamsMap().put("code_op", currentFr.getAttribute("Codeoptionscol") == null ? currentPm.getAttribute("CODE_OPTION") : currentFr.getAttribute("Codeoptionscol"));
            opimport.getParamsMap().put("code_fr", currentFr.getAttribute("Scolcodefrr"));
            opimport.getParamsMap().put("utilisateur", getUtilisateur());
            opimport.execute();
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }

    public void setAnnee(Long annee) {
        this.annee = annee;
    }

    public Long getAnnee() {
        return annee;
    }

    public void setPopSuccessImport(RichPopup popSuccessImport) {
        this.popSuccessImport = popSuccessImport;
    }

    public RichPopup getPopSuccessImport() {
        return popSuccessImport;
    }

    public void setInputEtudiant(RichInputText inputEtudiant) {
        this.inputEtudiant = inputEtudiant;
    }

    public RichInputText getInputEtudiant() {
        return inputEtudiant;
    }

    public void setPanelformetu(RichPanelGroupLayout panelformetu) {
        this.panelformetu = panelformetu;
    }

    public RichPanelGroupLayout getPanelformetu() {
        return panelformetu;
    }

    @SuppressWarnings("unchecked")
    public void onShowDetailsClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPM = (DCIteratorBinding) bindings.get("MaquetteParcoursAnneeIterator");
        Row currentPM = iterPM.getCurrentRow();
        if (null != this.getInputIndiv().getValue()) {
            System.out.println("Numero " + this.getInputEtd().getValue().toString()+" pma : "+currentPM.getAttribute("IdParcoursMaquetAnnee"));
            OperationBinding etunum = bindings.getOperationBinding("ExecuteWithParams");
            etunum.getParamsMap().put("an_id",currentPM.getAttribute("IdAnneesUnivers"));
            etunum.getParamsMap().put("numero_etu", this.getInputEtd().getValue().toString().toUpperCase());
            etunum.getParamsMap().put("str_id",currentPM.getAttribute("IdStructure"));
            etunum.execute();
            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("InscriptionRefIterator");
            if (0 == iter.getEstimatedRowCount()) {
                //System.out.println("Popup No Student with num");
                RichPopup popup = this.getPopupNoNum();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }/*else{
                
            }*/
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanGrpDetInscRef());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanColDetInscRef());
        }
        else{
            System.out.println("Enter number");
        }
    }

    public void setInputIndiv(RichSelectBooleanCheckbox inputIndiv) {
        this.inputIndiv = inputIndiv;
    }

    public RichSelectBooleanCheckbox getInputIndiv() {
        return inputIndiv;
    }

    public void OnClearMaquetteAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterPm = (DCIteratorBinding) bindings.get("MaquetteParcoursAnneeIterator");
        Row currentPm = iterPm.getCurrentRow();
        
        DCIteratorBinding iterFr = (DCIteratorBinding) bindings.get("ParcoursRespFrImpMaqEtdIterator");
        Row currentFr = iterFr.getCurrentRow();
    }

    public void onSelectDiselectIndiv(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //System.out.println("New Value indiv : " +valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue().equals(true)) {
            this.getPanelformetu().setVisible(true);
            this.getPanelBtnClear().setVisible(false);
            /*this.getPanedetAn().setVisible(false);
            this.getPanedetSem().setVisible(false);*/
        } else {
            /*this.getPanedetSem().setVisible(false);
            this.getPanedetAn().setVisible(false);*/
            this.getPanelformetu().setVisible(false);
            this.getPanelBtnClear().setVisible(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelformetu());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelBtnClear());
        /*AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetAn());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanedetSem());*/
    }

    public void setPanelBtnClear(RichPanelGroupLayout panelBtnClear) {
        this.panelBtnClear = panelBtnClear;
    }

    public RichPanelGroupLayout getPanelBtnClear() {
        return panelBtnClear;
    }

    public void setPopupNoNum(RichPopup popupNoNum) {
        this.popupNoNum = popupNoNum;
    }

    public RichPopup getPopupNoNum() {
        return popupNoNum;
    }

    public void setInputEtd(RichInputText inputEtd) {
        this.inputEtd = inputEtd;
    }

    public RichInputText getInputEtd() {
        return inputEtd;
    }

    public void setPanGrpDetInscRef(RichPanelGroupLayout panGrpDetInscRef) {
        this.panGrpDetInscRef = panGrpDetInscRef;
    }

    public RichPanelGroupLayout getPanGrpDetInscRef() {
        return panGrpDetInscRef;
    }

    public void setPanColDetInscRef(RichPanelCollection panColDetInscRef) {
        this.panColDetInscRef = panColDetInscRef;
    }

    public RichPanelCollection getPanColDetInscRef() {
        return panColDetInscRef;
    }

    public void onDeleteInscAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterInc = (DCIteratorBinding) bindings.get("InscriptionRefIterator");
        Row currentInsc = iterInc.getCurrentRow();
        System.out.println("Numero : "+currentInsc.getAttribute("Numero"));
        System.out.println("IdParcoursMaquetAnnee : "+currentInsc.getAttribute("IdParcoursMaquetAnnee"));
        System.out.println("IdInscriptionPedagogique : "+currentInsc.getAttribute("IdInscriptionPedagogique"));
    }

    public void onUnactiveInscAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    @SuppressWarnings("unchecked")
    public void onSearchStudentAction(ActionEvent actionEvent) {
        // Add event code here...
        if (null != this.getInputEtudiant().getValue()) {
            BindingContainer bindings = getBindings();
            System.out.println("numero : "+this.getInputEtudiant().getValue().toString().toUpperCase());
            OperationBinding etunum = bindings.getOperationBinding("getEtudiantScolRef");
            etunum.getParamsMap().put("p_numero", this.getInputEtudiant().getValue().toString().toUpperCase());
            etunum.execute();
            DCIteratorBinding iterEtd = (DCIteratorBinding) bindings.get("EtudiantScolRefIterator");
            if(0 == iterEtd.getEstimatedRowCount()){
                RichPopup popup = this.getPopNoStudent();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            }
            //System.out.println("nbre : "+iterEtd.getEstimatedRowCount());
        }
    }

    public void setPanColEtdScolRef(RichPanelCollection panColEtdScolRef) {
        this.panColEtdScolRef = panColEtdScolRef;
    }

    public RichPanelCollection getPanColEtdScolRef() {
        return panColEtdScolRef;
    }

    public void setPanGrpEtdScolRef(RichPanelGroupLayout panGrpEtdScolRef) {
        this.panGrpEtdScolRef = panGrpEtdScolRef;
    }

    public RichPanelGroupLayout getPanGrpEtdScolRef() {
        return panGrpEtdScolRef;
    }

    @SuppressWarnings("unchecked")
    public void onUpdateStudentAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        AttributeBinding num = (AttributeBinding) bindings.getControlBinding("Numero1");
        if(null != num){
            System.out.println("numero : "+num.getInputValue().toString());
            OperationBinding op = bindings.getOperationBinding("synchWithScol");
            op.getParamsMap().put("numEtu", num.getInputValue().toString().toUpperCase());
            op.execute();
            
            OperationBinding etunum = bindings.getOperationBinding("getEtudiantScolRef");
            etunum.getParamsMap().put("p_numero", num.getInputValue().toString().toUpperCase());
            etunum.execute();
            
            RichPopup popup = this.getPopUpdated();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
    }

    public void setPopNoStudent(RichPopup popNoStudent) {
        this.popNoStudent = popNoStudent;
    }

    public RichPopup getPopNoStudent() {
        return popNoStudent;
    }

    public void setPopUpdated(RichPopup popUpdated) {
        this.popUpdated = popUpdated;
    }

    public RichPopup getPopUpdated() {
        return popUpdated;
    }
}
