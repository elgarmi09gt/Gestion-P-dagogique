package view.beans.consultation;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class ConsultationBean {
    private RichInputText input_num;
    private RichPanelGroupLayout inscriptionPanelGrp;
    private RichPanelCollection inscriptionPanelCollect;
    private RichPanelGroupLayout noInscrPanGrp;
    private RichPanelGroupLayout resultatSemEtdPanGrp;
    private RichPanelCollection resSemEtdPanCollect;
    private RichPanelGroupLayout detEtuPanGrp;
    private RichPopup popNoInsc;
    private String input_search;
    public ConsultationBean() {
    }
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }

    @SuppressWarnings("unchecked")
    public void OnSearchClicked(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("Numero : "+this.getInput_search());

        BindingContainer bindings = getBindings();
        
        try{
            OperationBinding opsearch = bindings.getOperationBinding("getInscriptionEtudiant");
            opsearch.getParamsMap().put("p_numero", (String)this.getInput_search());
            opsearch.execute();
            DCIteratorBinding iterInsc = (DCIteratorBinding) bindings.get("InscriptionsEtudiantIterator");
            //System.out.println("nbre : "+iterInsc.getEstimatedRowCount());
            if(0 != iterInsc.getEstimatedRowCount()){
                this.getInscriptionPanelGrp().setVisible(true);
                this.getResultatSemEtdPanGrp().setVisible(true);
                this.getDetEtuPanGrp().setVisible(true);
                this.getNoInscrPanGrp().setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInscriptionPanelGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInscriptionPanelCollect());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultatSemEtdPanGrp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResSemEtdPanCollect());
            }else{
                RichPopup popup = this.getPopNoInsc();
                RichPopup.PopupHints hint = new RichPopup.PopupHints();
                popup.show(hint);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void setInput_num(RichInputText input_num) {
        this.input_num = input_num;
    }

    public RichInputText getInput_num() {
        return input_num;
    }

    public void setInscriptionPanelGrp(RichPanelGroupLayout inscriptionPanelGrp) {
        this.inscriptionPanelGrp = inscriptionPanelGrp;
    }

    public RichPanelGroupLayout getInscriptionPanelGrp() {
        return inscriptionPanelGrp;
    }

    public void setInscriptionPanelCollect(RichPanelCollection inscriptionPanelCollect) {
        this.inscriptionPanelCollect = inscriptionPanelCollect;
    }

    public RichPanelCollection getInscriptionPanelCollect() {
        return inscriptionPanelCollect;
    }

    public void onShowDetails(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("InscriptionsEtudiantIterator");
        Row CcurrentInsc = dciter.getCurrentRow();
        System.out.println(CcurrentInsc.getAttribute("IdParcoursMaquetAnnee").toString());
        
    }

    public void setNoInscrPanGrp(RichPanelGroupLayout noInscrPanGrp) {
        this.noInscrPanGrp = noInscrPanGrp;
    }

    public RichPanelGroupLayout getNoInscrPanGrp() {
        return noInscrPanGrp;
    }

    public void setResultatSemEtdPanGrp(RichPanelGroupLayout resultatSemEtdPanGrp) {
        this.resultatSemEtdPanGrp = resultatSemEtdPanGrp;
    }

    public RichPanelGroupLayout getResultatSemEtdPanGrp() {
        return resultatSemEtdPanGrp;
    }

    public void setResSemEtdPanCollect(RichPanelCollection resSemEtdPanCollect) {
        this.resSemEtdPanCollect = resSemEtdPanCollect;
    }

    public RichPanelCollection getResSemEtdPanCollect() {
        return resSemEtdPanCollect;
    }

    public void setDetEtuPanGrp(RichPanelGroupLayout detEtuPanGrp) {
        this.detEtuPanGrp = detEtuPanGrp;
    }

    public RichPanelGroupLayout getDetEtuPanGrp() {
        return detEtuPanGrp;
    }

    public void setPopNoInsc(RichPopup popNoInsc) {
        this.popNoInsc = popNoInsc;
    }

    public RichPopup getPopNoInsc() {
        return popNoInsc;
    }

    public void setInput_search(String input_search) {
        this.input_search = input_search;
    }

    public String getInput_search() {
        return input_search;
    }
}
