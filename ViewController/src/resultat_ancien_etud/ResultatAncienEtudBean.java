package resultat_ancien_etud;

import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.ReturnEvent;

public class ResultatAncienEtudBean {
    private RichInputText etudAncUpdate;
    private RichInputText numDiplomeUpdate;

    public ResultatAncienEtudBean() {
    }

    public void onReturnEtud(ReturnEvent returnEvent) {
        // Add event code here...
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        
        Object cancelFlag = pageFlowScope.get("submitOrcancelEtud");
        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                Object etudId = returnEvent.getReturnValue();
                etudAncUpdate.resetValue();
                etudAncUpdate.setValue(etudId);
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(etudAncUpdate);
            }
        }
    }

    public void onReturnDiplome(ReturnEvent returnEvent) {
        // Add event code here...
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        
        Object cancelFlag = pageFlowScope.get("submitOrCanDiplom");
        if(cancelFlag!=null){
           if (((String)cancelFlag).equalsIgnoreCase("submit")) {
                Object numId = returnEvent.getReturnValue();
                numDiplomeUpdate.resetValue();
                numDiplomeUpdate.setValue(numId);
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(numDiplomeUpdate);
            }
        }
    }

    public void setEtudAncUpdate(RichInputText etudAncUpdate) {
        this.etudAncUpdate = etudAncUpdate;
    }

    public RichInputText getEtudAncUpdate() {
        return etudAncUpdate;
    }

    public void setNumDiplomeUpdate(RichInputText numDiplomeUpdate) {
        this.numDiplomeUpdate = numDiplomeUpdate;
    }

    public RichInputText getNumDiplomeUpdate() {
        return numDiplomeUpdate;
    }
}
