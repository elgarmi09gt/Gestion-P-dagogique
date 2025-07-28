package view.beans.jsfutils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;


public class JSFUtils {
    public JSFUtils() {
        super();
    }
    
    /**
         * Method for setting a new object into a JSF managed bean
         * Note: will fail silently if the supplied object does
         * not match the providerErrorType of the managed bean.
         * @param expression EL expression
         * @param newValue new value to set
         */
        public static void setExpressionValue(String expression, Object newValue) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Application app = facesContext.getApplication();
            ExpressionFactory elFactory = app.getExpressionFactory();
            ELContext elContext = facesContext.getELContext();
            ValueExpression valueExp =
                elFactory.createValueExpression(elContext, expression,
                                                Object.class);

            //Check that the input newValue can be cast to the property type
            //expected by the managed bean.
            //If the managed Bean expects a primitive we rely on Auto-Unboxing
            //Class bindClass = valueExp.getType(elContext);
            //if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
            //}
        }
}
