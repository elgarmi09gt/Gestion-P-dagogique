package exclu;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicExcluBean {
    public DynamicExcluBean() {
    }
    private static final String defaultTF="/inscription/exclu/task-flow-default.xml#task-flow-default";
      boolean flag =true;
     
      public TaskFlowId getDynamictaskFlow(){
          //System.out.println("Inside TF 1");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfExcluID") != null) {
                  //System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfExcluID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfExcluID")
                                                         .toString());
              }
          } catch (Exception e) {
          }
          return  TaskFlowId.parse(defaultTF);
      }

      public void setFlag(boolean flag) {
          this.flag = flag;
      }

}
