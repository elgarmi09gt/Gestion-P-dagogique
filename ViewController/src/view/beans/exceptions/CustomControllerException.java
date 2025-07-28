package view.beans.exceptions;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;

public class CustomControllerException {
    public CustomControllerException() {
        super();
    }
    
    public void ExceptionHandler(){
        
        ControllerContext ctx = ControllerContext.getInstance();
        ViewPortContext viewprt = ctx.getCurrentViewPort();
        if(viewprt.isExceptionPresent()){
            Exception exc = viewprt.getExceptionData();
            //exc.getMessage()
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Error Appliction", null );
            FacesContext.getCurrentInstance().addMessage(null, message);
            try {
                FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .redirect("/menu.jsf");
            } catch (IOException e) {
            }
        }
    }
}
