package suspension;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicBean {
    public DynamicBean() {
    }
    private static final String defaultTF="/inscription/suspension/task-flow-default.xml#task-flow-default";
      boolean flag =true;
     
      public TaskFlowId getDynamictaskFlow(){
          //System.out.println("Inside TF 1");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfSuspID") != null) {
                  //System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfSuspID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfSuspID")
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
