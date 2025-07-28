package paiement_etudiant;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicPaie {
    public DynamicPaie() {
    }
    private static final String defaultTF="/inscription/etudiant_bu/task-etud-bu.xml#task-etud-bu";
      boolean flag =true;
     
      public TaskFlowId getDynamictaskFlow(){
          //System.out.println("Inside TF");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfEtudPaieID") != null) {
                  //System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfEtudPaieID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfEtudPaieID")
                                                         .toString());
              }
          } catch (Exception e) {
          }
          return  TaskFlowId.parse(defaultTF);
      }

      public void setFlag(boolean flag) {
          this.flag = flag;
      }

      public boolean isFlag() {
          return flag;
      }
}
