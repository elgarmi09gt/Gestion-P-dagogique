package view.beans.evaluation.textslois;

import java.util.ArrayList;
import java.util.Map;
import view.beans.jsfutils.JSFUtils;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class TextLoisBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popupNewText;
    private RichPopup popupEditText;
    private RichPopup popupDeleteText;
    private RichTable textTable;
    private RichPopup isTextUsed;
    private RichTable textloiStructTable;
    private RichPopup noTextSelected;
    private RichPopup popupNewTextStruct;
    private RichColumn textLoiFormationTable;
    private RichInputText id_struc;
    private RichSelectOneChoice id_text;
    private RichInputText ordre;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer id_hs = Integer.parseInt(sessionScope.get("id_hs").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichInputText user_input;

    public TextLoisBean() {
    }

    public void setPopupNewText(RichPopup popupNewText) {
        this.popupNewText = popupNewText;
    }

    public RichPopup getPopupNewText() {
        return popupNewText;
    }

    public void OnTextLoiDialogAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewText().hide();
            this.getPopupEditText().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextTable());
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void OnNewTextLoiClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TextLoisVOIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        OperationBinding operationBinding = bindings.getOperationBinding("CreateTextLois");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
           return;
        }
        RichPopup popup = this.getPopupNewText();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return;
    }

    public void OnEditTextLoiClicked(ActionEvent actionEvent) {
        RichPopup popup = this.getPopupEditText();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //empty hints renders dialog in center of screen
        popup.show(hints);
    }

    @SuppressWarnings("unchecked")
    public String OnDeleteTextLoiClicked() {
        BindingContainer bindings = getBindings();
        // Recupérer le text choisit
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TextLoisVOIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        Long idtext = Long.parseLong(oldCcurrentRow.getAttribute("IdTextLoi").toString());
        //Verifier si le text est utilisé
        OperationBinding uses = BindingContext.getCurrent()
                                              .getCurrentBindingsEntry()
                                              .getOperationBinding("IsStructTextExist");
        uses.getParamsMap().put("id_txt", idtext);
        uses.execute();
        DCIteratorBinding dciter_is = (DCIteratorBinding) bindings.get("IsStructureTextExistIterator");
        Row isTextUsed = dciter_is.getCurrentRow();
        if (isTextUsed != null) {
            //Text utilisé par une structure
            RichPopup popup = this.getIsTextUsed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        } else {
            // Text n'est utilisé dans aucune structure
            System.out.println("deleting...");
            // Popup de confirmation suppression
            RichPopup popup = this.getPopupDeleteText();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }
        return null;
    }

    public void setPopupEditText(RichPopup popupEditText) {
        this.popupEditText = popupEditText;
    }

    public RichPopup getPopupEditText() {
        return popupEditText;
    }

    public void setPopupDeleteText(RichPopup popupDeleteText) {
        this.popupDeleteText = popupDeleteText;
    }

    public RichPopup getPopupDeleteText() {
        return popupDeleteText;
    }

    public void OnTextLoiDialogCancel(ClientEvent clientEvent) {
        BindingContainer bindings = getBindings();
        this.getPopupNewText().hide();
        this.getPopupEditText().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TextLoisVOIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setTextTable(RichTable textTable) {
        this.textTable = textTable;
    }

    public RichTable getTextTable() {
        return textTable;
    }

    public void setIsTextUsed(RichPopup isTextUsed) {
        this.isTextUsed = isTextUsed;
    }

    public RichPopup getIsTextUsed() {
        return isTextUsed;
    }

    public void OnDeleteTextLoiAction(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            //Suppression confirmé
            System.out.println("Inside Deleting");
            DeleteTextLoi();
        }
    }

    public String DeleteTextLoi() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("DeleteTextLois");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextTable());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void OnFormationChange(ValueChangeEvent valueChangeEvent) {
        System.out.println("Formation value changed !!!");
        OperationBinding text = BindingContext.getCurrent()
                                              .getCurrentBindingsEntry()
                                              .getOperationBinding("TextLoisByStruct");
        text.execute();

        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FormationByStructureIterator");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Row currentform = iter.getCurrentRow();
        Integer id_formation = Integer.parseInt(currentform.getAttribute("IdFormation").toString());
        System.out.println("id_formation " + id_formation);
        OperationBinding textfr = BindingContext.getCurrent()
                                                .getCurrentBindingsEntry()
                                                .getOperationBinding("TextLoiFormation");
        textfr.getParamsMap().put("id_form", id_formation);
        //id_form
        textfr.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextLoiFormationTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextloiStructTable());
    }

    public void setTextloiStructTable(RichTable textloiStructTable) {
        this.textloiStructTable = textloiStructTable;
    }

    public RichTable getTextloiStructTable() {
        return textloiStructTable;
    }

    @SuppressWarnings("unchecked")
    public void OnSavaTextLoiFormationClick(ActionEvent actionEvent) {
        System.out.println("Saving ...");
        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FormationByStructureIterator");
        Row currentform = iter.getCurrentRow();
        Integer id_formation = Integer.parseInt(currentform.getAttribute("IdFormation").toString());
        System.out.println("Formation : " + id_formation);
        try {
            OperationBinding textSelected = bindings.getOperationBinding("getSelectedLois");
            ArrayList<Long> texts = (ArrayList<Long>) textSelected.execute();
            System.out.println("Size textLois " + texts.size());
            if (texts.size() != 0) {
                // Pour chaque formation attribué les texts de lois choisis
                for (int textcounter = 0; textcounter < texts.size(); textcounter++) {
                    //Verifier si la loi a deja ete affecté a cet formation
                    OperationBinding isAffected = bindings.getOperationBinding("IsFormationTextIs");
                    isAffected.getParamsMap().put("id_txt", texts.get(textcounter));
                    isAffected.getParamsMap().put("id_fr", id_formation);
                    Object resul = isAffected.execute();

                    DCIteratorBinding isAffected_iter =
                        (DCIteratorBinding) BindingContext.getCurrent()
                                                          .getCurrentBindingsEntry()
                                                          .get("IsFormationTextIsIterator");
                    Row crnt_is = isAffected_iter.getCurrentRow();
                    if (crnt_is == null) {
                        System.out.println("Formation pas encore ce text ");
                        System.out.println("Inserting ... ");
                        //text de loi pas encore affecté a cette formation
                        OperationBinding opAffect = bindings.getOperationBinding("CreateTextLoiFr");
                        opAffect.getParamsMap().put("IdTextLoi", texts.get(textcounter));
                        opAffect.getParamsMap().put("UtiCree", 33);
                        opAffect.getParamsMap().put("IdFormation", id_formation);
                        resul = opAffect.execute();
                    }else{
                        System.out.println("Text already used by this formation");
                    }
                }
                // Tout ce passe bien On commit
                OperationBinding operationCommit = bindings.getOperationBinding("Commit");
                Object result = operationCommit.execute();
                // Réexecuter les VO pour actualiser les tableaux
                OperationBinding text = BindingContext.getCurrent()
                                                      .getCurrentBindingsEntry()
                                                      .getOperationBinding("TextLoisByStruct");
                text.execute();
                OperationBinding textfr = BindingContext.getCurrent()
                                                        .getCurrentBindingsEntry()
                                                        .getOperationBinding("TextLoiFormation");
                textfr.getParamsMap().put("id_form", id_formation);
                //id_form
                textfr.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextLoiFormationTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextloiStructTable());
                //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAccesEcTable());
            } else {
                System.out.println("Pas de text selectionné !!!");
                RichPopup popup = this.getNoTextSelected();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                //empty hints renders dialog in center of screen
                popup.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String OnSaveTextLoiFormationAction() {
        System.out.println("Saving Action ...");
        return null;
    }

    public void setNoTextSelected(RichPopup noTextSelected) {
        this.noTextSelected = noTextSelected;
    }

    public RichPopup getNoTextSelected() {
        return noTextSelected;
    }

    public void OnNewTextLoiStructClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TextLoisHistoriqueStrVOIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        if (oldCcurrentRow != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        }
        OperationBinding operationBinding = bindings.getOperationBinding("CreateTextLoiStruct");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
           return;
        }
        //getPopupNewTextStruct().clearCachedClientIds();
        RichPopup popup = this.getPopupNewTextStruct();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popup.show(hints);
        return;
    }

    public void setPopupNewTextStruct(RichPopup popupNewTextStruct) {
        this.popupNewTextStruct = popupNewTextStruct;
    }

    public RichPopup getPopupNewTextStruct() {
        return popupNewTextStruct;
    }

    public void OnTextLoiStrDialogCancel(ClientEvent clientEvent) {
        BindingContainer bindings = getBindings();
        this.getPopupNewTextStruct().hide();
        //this.getPopupEditText().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TextLoisHistoriqueStrVOIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextloiStructTable());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }
    
    /**
     * Method for setting a new object into a JSF managed bean
     * Note: will fail silently if the supplied object does
     * not match the type of the managed bean.
     * @param expression EL expression
     * @param newValue new value to set
     */
    public static void setExpressionValue(String expression, Object newValue) {
       FacesContext facesContext = FacesContext.getCurrentInstance();
       Application app = facesContext.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = facesContext.getELContext();
       ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
     
       Class bindClass = valueExp.getType(elContext);
       if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
         valueExp.setValue(elContext, newValue);
       }
     }
        
    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.semantic-warning" })
    public void OnTextLoiStrDialogAction(DialogEvent dialogEvent) {
            Outcome outcome = dialogEvent.getOutcome();
            /* System.out.println("Struct : "+getId_hs());
            System.out.println("Text : "+getId_text().getValue().toString());
            System.out.println("Ordre : "+getOrdre().getValue().toString());
            System.out.println("Utilisateur : "+getUtilisateur()); */
            if (outcome == Outcome.ok) {
                BindingContainer bindings = getBindings();
                //setId_hs(Integer.parseInt(getId_hs().toString()));
                //setUtilisateur(Integer.parseInt(getUtilisateur().toString()));
                //System.out.println("User : "+getUtilisateur());
                System.out.println("User input Before "+this.getUser_input().getValue().toString());
                JSFUtils.setExpressionValue("#{bindings.IdHistoriquesStructure.inputValue}", getId_hs());
                JSFUtils.setExpressionValue("#{bindings.UtiCree1.inputValue}", getUtilisateur());
                System.out.println("User input After "+this.getUser_input().getValue().toString());
                this.getUser_input().setValue(getUtilisateur());
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUser_input());
                //this.setExpressionValue("#{bindings.UtiCree1.inputValue}",getUtilisateur());
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUser_input());
            //setExpressionValue("#{bindings.YOUR_VO_ATTRIBUTE.inputValue}","YOUR VALUE");
                System.out.println("User input value "+this.getUser_input().getValue().toString());
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    //handle errors if any
                    //...
                    return;
                }
            this.getPopupNewTextStruct().hide();
            OperationBinding text = BindingContext.getCurrent()
                                                  .getCurrentBindingsEntry()
                                                  .getOperationBinding("TextLoisByStruct");
            text.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTextloiStructTable());
        }
    }

    public void setTextLoiFormationTable(RichColumn textLoiFormationTable) {
        this.textLoiFormationTable = textLoiFormationTable;
    }

    public RichColumn getTextLoiFormationTable() {
        return textLoiFormationTable;
    }

    public void setId_struc(RichInputText id_struc) {
        this.id_struc = id_struc;
    }

    public RichInputText getId_struc() {
        return id_struc;
    }

    public void setId_text(RichSelectOneChoice id_text) {
        this.id_text = id_text;
    }

    public RichSelectOneChoice getId_text() {
        return id_text;
    }

    public void setOrdre(RichInputText ordre) {
        this.ordre = ordre;
    }

    public RichInputText getOrdre() {
        return ordre;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setId_hs(Integer id_hs) {
        this.id_hs = id_hs;
    }

    public Integer getId_hs() {
        return id_hs;
    }

    public void setUser_input(RichInputText user_input) {
        this.user_input = user_input;
    }

    public RichInputText getUser_input() {
        return user_input;
    }
}
