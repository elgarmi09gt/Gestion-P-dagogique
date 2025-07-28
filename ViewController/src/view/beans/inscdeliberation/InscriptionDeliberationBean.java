package view.beans.inscdeliberation;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class InscriptionDeliberationBean {
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Long utilisateur = Long.parseLong(sessionScope.get("id_user").toString());
    private Long annee = Long.parseLong(sessionScope.get("id_annee").toString());
    private RichPopup popSuccess;

    public InscriptionDeliberationBean() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnInscriptionUpdated(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding opcommit = getBindings().getOperationBinding("Commit");
        opcommit.execute();
        
        DCIteratorBinding iterP = (DCIteratorBinding) getBindings().get("ParcoursInscriptionDeliberationIterator");
        Row currentP = iterP.getCurrentRow();
        OperationBinding opupdate = getBindings().getOperationBinding("UpdateInscription");
        opupdate.getParamsMap().put("prcrs", currentP.getAttribute("IdNiveauFormationParcours"));
        opupdate.getParamsMap().put("annee", getAnnee());
        opupdate.getParamsMap().put("utilisateur", getUtilisateur());
        opupdate.execute();
        RichPopup popup = this.getPopSuccess();
        RichPopup.PopupHints hs = new RichPopup.PopupHints();
        popup.show(hs);
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

    public void setPopSuccess(RichPopup popSuccess) {
        this.popSuccess = popSuccess;
    }

    public RichPopup getPopSuccess() {
        return popSuccess;
    }
}
