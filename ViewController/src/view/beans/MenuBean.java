package view.beans;

import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

public class MenuBean {
    public MenuBean() {
    }

    public void OnMenuClicked(ActionEvent actionEvent) {
        // Add event code here...
    }
    private static final String defaultTF="/WEB-INF/menu/task-flow-default.xml#task-flow-default";
      boolean flag =true;
     
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
