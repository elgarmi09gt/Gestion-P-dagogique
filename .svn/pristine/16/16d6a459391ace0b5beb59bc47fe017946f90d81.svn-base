package etudiant_bu;

import java.util.Map;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class EtudBuEditBean {
    public EtudBuEditBean() {
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    @SuppressWarnings("unchecked")
    public String onValiderEtudBu() {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        OperationBinding op_commit_etud_bu = getBindings().getOperationBinding("Commit");
        Object result = op_commit_etud_bu.execute();
        if (!op_commit_etud_bu.getErrors().isEmpty()) {
               return null;
        }
        else{ 
               Integer regle = 0;
               AttributeBinding en_regle_val = (AttributeBinding) getBindings().getControlBinding("EnRegle");
            if(en_regle_val.getInputValue() == null)
                sessionScope.put("is_en_regle",0);
            else
               if(Boolean.parseBoolean(en_regle_val.getInputValue().toString()))
                    sessionScope.put("is_en_regle",1);
                else
                    sessionScope.put("is_en_regle",0);
                sessionScope.put("TfEtudBuID","/inscription/etudiant_bu/task-etud-bu-edit.xml#task-etud-bu-edit");
            }
        return null;
    }
}
