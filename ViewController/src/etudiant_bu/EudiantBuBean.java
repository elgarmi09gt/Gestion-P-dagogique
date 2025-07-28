package etudiant_bu;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class EudiantBuBean {
    private RichSelectBooleanCheckbox regle;

    public EudiantBuBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public String onValiderRegleBu() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        //id_utilisateur.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        AttributeBinding id_utilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        id_utilisateur.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));

        DCIteratorBinding iter_etud_bu = (DCIteratorBinding)getBindings().get("EtudiantBuIterator");
        
        Row row_etud_bu = iter_etud_bu.getCurrentRow();
                   
            if (id_struct.getInputValue() == null) {
                FacesMessage msg = new FacesMessage(" L'étudiant n'est inscrit dans la structure :"+sessionScope.get("lib_struct"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
           else{
                row_etud_bu.setAttribute("IdEtudiant", Long.parseLong(id_etud.getInputValue().toString()));
                row_etud_bu.setAttribute("IdAnneesUnivers", Long.parseLong(id_annee.getInputValue().toString()));
                row_etud_bu.setAttribute("IdStructure", Long.parseLong(id_struct.getInputValue().toString()));
                row_etud_bu.setAttribute("UtiCree", Long.parseLong(sessionScope.get("id_user").toString()));
                Integer regle = 0;
                AttributeBinding en_regle_val = (AttributeBinding) getBindings().getControlBinding("EnRegle");
                if(Boolean.parseBoolean(en_regle_val.getInputValue().toString()))
                    regle = 1;
                row_etud_bu.setAttribute("EnRegle", regle);
                //Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())
                OperationBinding op_commit_etud_bu = getBindings().getOperationBinding("Commit");
                Object result = op_commit_etud_bu.execute();
                if (!op_commit_etud_bu.getErrors().isEmpty()) {
                       return null;
                }
                else{                    
                   if(Boolean.parseBoolean(en_regle_val.getInputValue().toString()))
                        sessionScope.put("is_en_regle",1);
                    sessionScope.put("TfEtudBuID","/inscription/etudiant_bu/task-etud-bu-edit.xml#task-etud-bu-edit");
                    OperationBinding op_ex_etud_bu = getBindings().getOperationBinding("Execute");
                    Object result_ex = op_ex_etud_bu.execute();
                   }
            }
        return null;
    }

    public void setRegle(RichSelectBooleanCheckbox regle) {
        this.regle = regle;
    }

    public RichSelectBooleanCheckbox getRegle() {
        return regle;
    }

    public void onChangeStructure(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        
        AttributeBinding id_etud = (AttributeBinding) getBindings().getControlBinding("IdEtudiant");
        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        AttributeBinding id_struct = (AttributeBinding) getBindings().getControlBinding("IdStructure");
        
        //isEtudEnRegleBu
        OperationBinding isEtudEnRegleBu = getBindings().getOperationBinding("isEtudEnRegleBu");
        isEtudEnRegleBu.getParamsMap().put("id_struct",  Long.parseLong(id_struct.getInputValue().toString()));  
        isEtudEnRegleBu.getParamsMap().put("id_annee",  Long.parseLong(id_annee.getInputValue().toString()));  
        isEtudEnRegleBu.getParamsMap().put("id_etud",  Long.parseLong(id_etud.getInputValue().toString()));  
        isEtudEnRegleBu.getParamsMap().put("en_regle",  1);  // l'etudiant est déja en régle avec la Bu
        Integer result_en_regle = (Integer)isEtudEnRegleBu.execute(); 

        if(result_en_regle > 0){
            sessionScope.put("is_en_regle",1);
        }
        
    }

    public void onValider(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println(" is_en_regle is_en_regle");
    }
}
