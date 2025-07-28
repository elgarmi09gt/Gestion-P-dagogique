package view.beans;

import java.io.Serializable;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class DynamicMenuBean implements Serializable{
    @SuppressWarnings("compatibility:-2133577178462225886")
    private static final long serialVersionUID = 1L;

    public DynamicMenuBean() {
    }
   //  private static final String defaultTF="/menu/task-flow-default.xml#task-flow-default";
   //  private static final String defaultTF = "/evaluation/connection/connect-eval-params.xml#enter-params";
   // private static final String defaultTF = "/roleCheck/role-check-tf.xml#role-check-tf";
   private static final String defaultTF = "/modulecheck/check-module-tf.xml#check-module-tf";
      boolean flag =true;

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public TaskFlowId getDynamictaskFlow(){
          //System.out.println("Inside TF");
          try {
              if (ADFContext.getCurrent().getSessionScope().get("TfID") != null) {
                  //System.out.println("Inside methode AdfFacesContext.getCurrentInstance().getPageFlowScope().get(\"TfID\").toString()");
                  return TaskFlowId.parse(ADFContext.getCurrent().getSessionScope()
                                                         .get("TfID")
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
