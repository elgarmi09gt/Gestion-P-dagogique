package view.beans.entities;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class clotureIncription {
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionFlowScope = adfCtx.getSessionScope();
    private Long utilisateur = Long.parseLong(sessionFlowScope.get("id_user").toString());

    public clotureIncription() {
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void onAnChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        
        AttributeBinding attrIdDept = (AttributeBinding) bindings.getControlBinding("IdHistoriquesStructure");
        Long deptId = (Long.parseLong(attrIdDept.getInputValue().toString()));
        
        AttributeBinding attrIdAn = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers");
        Long anId = (Long.parseLong(attrIdAn.getInputValue().toString()));
        //System.out.println("deptId : "+deptId+" anId : "+anId);
        
        OperationBinding opgetClosed = getBindings().getOperationBinding("getInscriptionClosed");
        opgetClosed.getParamsMap().put("p_hs", deptId);
        opgetClosed.getParamsMap().put("p_an", anId);
        opgetClosed.execute();
         
        OperationBinding opgettoClosed = getBindings().getOperationBinding("getInsACloturer");
        opgettoClosed.getParamsMap().put("p_hs", deptId);
        opgettoClosed.getParamsMap().put("p_an", anId);
        opgettoClosed.execute();
    }

    @SuppressWarnings("unchecked")
    public void onDeptChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BindingContainer bindings = this.getBindings();
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        
        AttributeBinding attrIdDept = (AttributeBinding) bindings.getControlBinding("IdHistoriquesStructure");
        Long deptId = (Long.parseLong(attrIdDept.getInputValue().toString()));
        
        AttributeBinding attrIdAn = (AttributeBinding) bindings.getControlBinding("IdAnneesUnivers");
        Long anId = (Long.parseLong(attrIdAn.getInputValue().toString()));
        //System.out.println("deptId : "+deptId+" anId : "+anId);
        
        OperationBinding opgetClosed = getBindings().getOperationBinding("getInscriptionClosed");
        opgetClosed.getParamsMap().put("p_hs", deptId);
        opgetClosed.getParamsMap().put("p_an", anId);
        opgetClosed.execute();
         
        OperationBinding opgettoClosed = getBindings().getOperationBinding("getInsACloturer");
        opgettoClosed.getParamsMap().put("p_hs", deptId);
        opgettoClosed.getParamsMap().put("p_an", anId);
        opgettoClosed.execute();
    }

    @SuppressWarnings("unchecked")
    public void OnCloseInscriptionSaved(ActionEvent actionEvent) {
        // Add event code here...
        AttributeBinding attrIdAn = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        Long anId = (Long.parseLong(attrIdAn.getInputValue().toString()));
        
        AttributeBinding attrIdDept = (AttributeBinding) getBindings().getControlBinding("IdHistoriquesStructure");
        Long deptId = (Long.parseLong(attrIdDept.getInputValue().toString()));
        
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("InscriptionACloturerIterator");
        if (iter.getEstimatedRowCount() > 0) {
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                if(null != nextRow.getAttribute("isSelected")){
                    if(nextRow.getAttribute("isSelected").equals(true)){
                        OperationBinding clotureInsc = getBindings().getOperationBinding("CloturerInscription");
                        clotureInsc.getParamsMap().put("p_an", anId);
                        clotureInsc.getParamsMap().put("p_niv", Long.parseLong(nextRow.getAttribute("IdNiveauFormation").toString()));
                        clotureInsc.getParamsMap().put("p_type", 1);
                        clotureInsc.getParamsMap().put("p_uti", getUtilisateur());
                        clotureInsc.execute(); 
                    }
                }
            }
            rsi.closeRowSetIterator();
            
            OperationBinding opgetClosed = getBindings().getOperationBinding("getInscriptionClosed");
            opgetClosed.getParamsMap().put("p_hs", deptId);
            opgetClosed.getParamsMap().put("p_an", anId);
            opgetClosed.execute();
             
            OperationBinding opgettoClosed = getBindings().getOperationBinding("getInsACloturer");
            opgettoClosed.getParamsMap().put("p_hs", deptId);
            opgettoClosed.getParamsMap().put("p_an", anId);
            opgettoClosed.execute();
        }
    }

    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getUtilisateur() {
        return utilisateur;
    }
}
