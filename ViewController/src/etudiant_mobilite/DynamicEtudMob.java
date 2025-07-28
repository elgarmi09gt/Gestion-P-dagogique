package etudiant_mobilite;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicEtudMob {
    public DynamicEtudMob() {
    }
    
    private static final String defaultTF="/inscription/etudiant_mobilite/task-flow-default.xml#task-flow-default";
      boolean flag =true;
     
      public TaskFlowId getDynamictaskFlow(){
          System.out.println("Inside TF");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfEtudMobID") != null) {
                  System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfEtudMobID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfEtudMobID")
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
