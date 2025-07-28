package view.beans.inscriptionant;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class InscriptionAnterieur {
    private RichInputText inputEtudiant;
    private RichPanelCollection inscriptionPanCollect;
    private RichTable inscriptionsTab;
    private RichPanelGroupLayout inscriptionsPanGrp;
    private RichPopup popNoStudent;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String str = sessionScope.get("id_str_anc").toString();
    

    public InscriptionAnterieur() {
    }

    public void OnAnChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent comp = valueChangeEvent.getComponent();
        comp.processUpdates(FacesContext.getCurrentInstance());
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSearchAction(ActionEvent actionEvent) {
        AttributeBinding anId = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        try {
            OperationBinding opInsc = getBindings().getOperationBinding("getInscriptionAntérieurs");
            opInsc.getParamsMap().put("etd_numero", this.getInputEtudiant().getValue().toString().toUpperCase());
            opInsc.getParamsMap().put("p_annee", anId.getInputValue());
            opInsc.getParamsMap().put("etab_code", getStr());
            opInsc.execute();
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("InscriptionsAntEtdIterator");
            if (0 != iter.getEstimatedRowCount()) {
                //this.getInputEtudiant().resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInscriptionsPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInscriptionPanCollect());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInscriptionsTab());
            }else{
                RichPopup popup = this.getPopNoStudent();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints); 
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    public void setInputEtudiant(RichInputText inputEtudiant) {
        this.inputEtudiant = inputEtudiant;
    }

    public RichInputText getInputEtudiant() {
        return inputEtudiant;
    }

    public void setInscriptionPanCollect(RichPanelCollection inscriptionPanCollect) {
        this.inscriptionPanCollect = inscriptionPanCollect;
    }

    public RichPanelCollection getInscriptionPanCollect() {
        return inscriptionPanCollect;
    }

    public void setInscriptionsTab(RichTable inscriptionsTab) {
        this.inscriptionsTab = inscriptionsTab;
    }

    public RichTable getInscriptionsTab() {
        return inscriptionsTab;
    }

    public void setInscriptionsPanGrp(RichPanelGroupLayout inscriptionsPanGrp) {
        this.inscriptionsPanGrp = inscriptionsPanGrp;
    }

    public RichPanelGroupLayout getInscriptionsPanGrp() {
        return inscriptionsPanGrp;
    }

    public void setPopNoStudent(RichPopup popNoStudent) {
        this.popNoStudent = popNoStudent;
    }

    public RichPopup getPopNoStudent() {
        return popNoStudent;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void onImportAction(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding anId = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding numId = (AttributeBinding) getBindings().getControlBinding("Numero");
        AttributeBinding nivSec = (AttributeBinding) getBindings().getControlBinding("CodeNivSec");
        AttributeBinding opCode = (AttributeBinding) getBindings().getControlBinding("CodeOption");
        AttributeBinding resCode = (AttributeBinding) getBindings().getControlBinding("Resultat");
        
        System.out.println("numId : "+numId.getInputValue()+" nivSec : "+nivSec.getInputValue()+" opCode : "+opCode.getInputValue()+" Resultat : "+resCode.getInputValue());
        
    }
}
