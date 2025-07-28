package historique_inscription;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicBean {
    public DynamicBean() {
    }
    
    private static final String defaultTF="/inscription/historique_inscription/task-flow-default.xml#task-flow-default";
      boolean flag =true;
     
      public TaskFlowId getDynamictaskFlow(){
          System.out.println("Inside TF");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfHistInscID") != null) {
                  System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfHistInscID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfHistInscID")
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
