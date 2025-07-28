package view.beans.evaluation.calendrier;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import org.apache.myfaces.trinidad.model.RowKeySet;

import view.beans.jsfutils.JSFUtils;

public class CalendrierBean {
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichPopup popupNewCalendrier;
    private RichPopup popupEditCalendrier;
    private RichPopup popupDelete;
    private RichListView calendrierListView;
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    //private Integer id_hs = Integer.parseInt(sessionScope.get("id_hs").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer calendrier = Integer.parseInt(sessionScope.get("id_calendrier").toString());
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer sess = Integer.parseInt(sessionScope.get("id_session").toString());
    private RichInputText user_input;
    private RichPopup popupmanycal;
    private RichPopup calexist;
    private RichPopup popupCalDelibarated;
    private RichPopup popAnClosed;

    public CalendrierBean() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnNewCalendrierCLicked(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        
        
        //IsAnParcoursClosed
        AttributeBinding parcours_id = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        
        OperationBinding isprcrsanclosed = bindings.getOperationBinding("IsAnParcoursClosed");
        isprcrsanclosed.getParamsMap().put("id_annee", getAnne_univers());
        isprcrsanclosed.getParamsMap().put("parcours_id", Integer.parseInt(parcours_id.getInputValue().toString()));
        isprcrsanclosed.execute();
        DCIteratorBinding isprcrsanclosediter = (DCIteratorBinding) bindings.get("IsAnParcoursClosedIterator");
        if(isprcrsanclosediter.getEstimatedRowCount()>0){
            //popupAnClosed
            RichPopup popup = this.getPopAnClosed();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }else{
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CalendrierDeliberationVOIterator");
            AttributeBinding uticre = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
        
            Row oldCcurrentRow = dciter.getCurrentRow();
            if (oldCcurrentRow != null) {
                System.out.println("Calendrier id "+oldCcurrentRow.getAttribute("IdCalendrierDeliberation").toString());
                ADFContext adfCtx = ADFContext.getCurrent();
                adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
            }
            //perform row create
            OperationBinding operationBinding = bindings.getOperationBinding("CreateCalendrierDelib");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            uticre.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopupNewCalendrier();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
            return;
        }
    }

    public void setPopupNewCalendrier(RichPopup popupNewCalendrier) {
        this.popupNewCalendrier = popupNewCalendrier;
    }

    public RichPopup getPopupNewCalendrier() {
        return popupNewCalendrier;
    }

    @SuppressWarnings("unchecked")
    public void OnCalendrierDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            DCIteratorBinding iterP = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("NiveauFormationParcoursIterator");
            Row currentP = iterP.getCurrentRow();
            
            DCIteratorBinding iterC = (DCIteratorBinding) BindingContext.getCurrent()
                                                                        .getCurrentBindingsEntry()
                                                                        .get("CalendrierDeliberationVOIterator");
            Row currentC = iterC.getCurrentRow();
            
            //Calendrier Parcours annéet
            OperationBinding cparcours = BindingContext.getCurrent()
                                                       .getCurrentBindingsEntry()
                                                       .getOperationBinding("CalParcoursAnnee");
            cparcours.getParamsMap().put("annee", Integer.parseInt(currentC.getAttribute("IdAnneesUnivers").toString()));//IdAnneesUnivers
            cparcours.getParamsMap()
                .put("parcours", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
            cparcours.execute();
            DCIteratorBinding iterCP = (DCIteratorBinding) BindingContext.getCurrent()
                                                                         .getCurrentBindingsEntry()
                                                                         .get("CalParcoursAnneeIterator");      
            System.out.println("Nombre Calendrier : " + iterCP.getEstimatedRowCount());
            if (iterCP.getEstimatedRowCount() >= 4) {
                this.getPopupNewCalendrier().hide();
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CalendrierDeliberationVOIterator");
                Row currentRow = dciter.getCurrentRow();
                dciter.removeCurrentRow();
                //set current row back to original row
                ADFContext adfCtx = ADFContext.getCurrent();
                String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
                if(oldCurrentRowKey != null){
                    dciter.setCurrentRowWithKey(oldCurrentRowKey);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
                FacesContext fctx = FacesContext.getCurrentInstance();
                //we don't want to continue with the remainder of the lifecycle and
                //thus skip the rest
                fctx.renderResponse();
                RichPopup popup = this.getPopupmanycal();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
                OperationBinding is_cal_exist = BindingContext.getCurrent()
                                                           .getCurrentBindingsEntry()
                                                           .getOperationBinding("IsCalExist");
                is_cal_exist.getParamsMap().put("annee", Integer.parseInt(currentC.getAttribute("IdAnneesUnivers").toString()));//IdAnneesUnivers
                is_cal_exist.getParamsMap()
                    .put("parcours", Integer.parseInt(currentP.getAttribute("IdNiveauFormationParcours").toString()));
                is_cal_exist.getParamsMap().put("sem", Integer.parseInt(currentC.getAttribute("IdSemestre").toString()));//IdAnneesUnivers
                is_cal_exist.getParamsMap().put("sess", Integer.parseInt(currentC.getAttribute("IdSession").toString()));//IdSession
                is_cal_exist.execute();
                DCIteratorBinding iterExist = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("IsCalExistIterator");
                if(iterExist.getEstimatedRowCount()>0){
                DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CalendrierDeliberationVOIterator");
                Row currentRow = dciter.getCurrentRow();
                dciter.removeCurrentRow();
                //set current row back to original row
                ADFContext adfCtx = ADFContext.getCurrent();
                String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
                if(oldCurrentRowKey != null){
                    dciter.setCurrentRowWithKey(oldCurrentRowKey);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
                FacesContext fctx = FacesContext.getCurrentInstance();
                //we don't want to continue with the remainder of the lifecycle and
                //thus skip the rest
                fctx.renderResponse();
                RichPopup popup = this.getCalexist();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                popup.show(hints);
            } else {
            this.getPopupNewCalendrier().hide();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
               // return;
           }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
            }
            }
        }
    }

    public void OnDialogHandler(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewCalendrier().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CalendrierDeliberationVOIterator");
        Row currentRow = dciter.getCurrentRow();
        System.out.println("Calendrier id "+currentRow.getAttribute("IdCalendrierDeliberation").toString());
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopupEditCalendrier(RichPopup popupEditCalendrier) {
        this.popupEditCalendrier = popupEditCalendrier;
    }

    public RichPopup getPopupEditCalendrier() {
        return popupEditCalendrier;
    }

    @SuppressWarnings("unchecked")
    public void OnEditClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
        AttributeBinding cal = (AttributeBinding) getBindings().getControlBinding("IdCalendrierDeliberation");
        //IsDelibarateCalendar
        OperationBinding is_delib_cal = BindingContext.getCurrent()
                                                   .getCurrentBindingsEntry()
                                                   .getOperationBinding("IsDelibarateCalendar");
        System.out.println("id_calendrier : "+cal.getInputValue().toString());
        is_delib_cal.getParamsMap().put("idcalendrier", Integer.parseInt(cal.getInputValue().toString()));//IdAnneesUnivers
        is_delib_cal.execute();
        DCIteratorBinding itercal = (DCIteratorBinding) bindings.get("IsDelibCalendierIterator");
        if(itercal.getEstimatedRowCount()>0){
            RichPopup popup = this.getPopupCalDelibarated();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }else{
            OperationBinding isdelibprovstart = bindings.getOperationBinding("IsDelibProvStart");
            isdelibprovstart.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isdelibprovstart.execute();
            DCIteratorBinding isdelibprovstartiter = (DCIteratorBinding) bindings.get("IsDelibProvStartIterator");
            if(isdelibprovstartiter.getEstimatedRowCount()>0){
                //Date_debut_delib_prov < today : delib Start
                sessionScope.put("isdelibprovstart", 1);
            }else{
                //Date_debut_delib_prov < today : delib Start
                sessionScope.put("isdelibprovstart", 0);
            }
            OperationBinding isdelibprovend = bindings.getOperationBinding("IsDelibProvEnd");
            isdelibprovend.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isdelibprovend.execute();
            DCIteratorBinding isdelibprovenditer = (DCIteratorBinding) bindings.get("IsDelibProvEndIterator");
            if(isdelibprovenditer.getEstimatedRowCount()>0){
                //Date_fin_delib_prov < today : delib End
                sessionScope.put("isdelibprovend", 1);
            }else{
                //Date_fin_delib_prov < today : delib End
                sessionScope.put("isdelibprovend", 0);
            }
            OperationBinding isdelibdefstart = bindings.getOperationBinding("IsDelibDefStart");
            isdelibdefstart.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isdelibdefstart.execute();
            DCIteratorBinding isdelibdefstartiter = (DCIteratorBinding) bindings.get("IsDelibDefStartIterator");
            if(isdelibdefstartiter.getEstimatedRowCount()>0){
                //Date_debut_delib_def < today : delib Start
                sessionScope.put("isdelibdefstart", 1);
            }else{
                //Date_debut_delib_def < today : delib Start
                sessionScope.put("isdelibdefstart", 0);
            }
            OperationBinding isdelibdefend = bindings.getOperationBinding("IsDelibDefEnd");
            isdelibdefend.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isdelibdefend.execute();
            DCIteratorBinding isdelibdefenditer = (DCIteratorBinding) bindings.get("IsDelibDefEndIterator");
            if(isdelibdefenditer.getEstimatedRowCount()>0){
                //Date_fint_delib_def < today : delib end
                sessionScope.put("isdelibdefend", 1);
            }else{
                //Date_fint_delib_def < today : delib end
                sessionScope.put("isdelibdefend", 0);
            }
            OperationBinding isrecstart = bindings.getOperationBinding("IsReclStart");
            isrecstart.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isrecstart.execute();
            DCIteratorBinding isrecstartiter = (DCIteratorBinding) bindings.get("IsReclStartIterator");
            if(isrecstartiter.getEstimatedRowCount()>0){
                //Date_debut_rec < today : rec Start
                sessionScope.put("isrecstart", 1);
            }else{
                //Date_debut_rec < today : rec Start
                sessionScope.put("isrecstart", 0);
            }
            OperationBinding isrecend = bindings.getOperationBinding("IsReclEnd");
            isrecend.getParamsMap().put("calendrier_id", Integer.parseInt(cal.getInputValue().toString()));
            isrecend.execute();
            DCIteratorBinding isrecenditer = (DCIteratorBinding) bindings.get("IsReclEndIterator");
            if(isrecenditer.getEstimatedRowCount()>0){
                //Date_fin_reclamation < today : fini les réclamation
                sessionScope.put("isrecend", 1);
            }else{
                //Date_fin_reclamation < today : fini les réclamation
                sessionScope.put("isrecend", 0);
            }
            System.out.println("isdelibprovstart : "+sessionScope.get("isdelibprovstart").toString()+
                               " isdelibprovend : "+sessionScope.get("isdelibprovend").toString()+
                               " isrecstart : "+sessionScope.get("isrecstart").toString()+
                               " isrecend : "+sessionScope.get("isrecend").toString()+
                               " isdelibdefstart : "+sessionScope.get("isdelibdefstart").toString()+
                               " isdelibdefend : "+sessionScope.get("isdelibdefend").toString());
            utimodif.setInputValue(getUtilisateur());
            RichPopup popup = this.getPopupEditCalendrier();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints);
        }
    }

    public void setPopupDelete(RichPopup popupDelete) {
        this.popupDelete = popupDelete;
    }

    public RichPopup getPopupDelete() {
        return popupDelete;
    }

    public void OnDeleteDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(Outcome.yes)) {
            OnDeleteClicked();
        }
    }
    public String OnDeleteClicked() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } else {
            OperationBinding operationCommit = bindings.getOperationBinding("Commit");
            Object commitResult = operationCommit.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
            return null;
        }
    }

    public void setCalendrierListView(RichListView calendrierListView) {
        this.calendrierListView = calendrierListView;
    }

    public RichListView getCalendrierListView() {
        return calendrierListView;
    }

    public String OnFormatClicked() {
        // Add event code here...
        return null;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setUser_input(RichInputText user_input) {
        this.user_input = user_input;
    }

    public RichInputText getUser_input() {
        return user_input;
    }

    public void onEditCalendrierAction(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            //commit
            BindingContainer bindings = getBindings();
            AttributeBinding utimodif = (AttributeBinding) getBindings().getControlBinding("UtiModifie");
            utimodif.setInputValue(getUtilisateur());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //handle errors if any
                //...
                return;
            }
            this.getPopupNewCalendrier().hide();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
        }
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    public void setPopupmanycal(RichPopup popupmanycal) {
        this.popupmanycal = popupmanycal;
    }

    public RichPopup getPopupmanycal() {
        return popupmanycal;
    }

    public void setCalexist(RichPopup calexist) {
        this.calexist = calexist;
    }

    public RichPopup getCalexist() {
        return calexist;
    }

    public void setCalendrier(Integer calendrier) {
        this.calendrier = calendrier;
    }

    public Integer getCalendrier() {
        return calendrier;
    }

    public void setPopupCalDelibarated(RichPopup popupCalDelibarated) {
        this.popupCalDelibarated = popupCalDelibarated;
    }

    public RichPopup getPopupCalDelibarated() {
        return popupCalDelibarated;
    }

    public void setSess(Integer sess) {
        this.sess = sess;
    }

    public Integer getSess() {
        return sess;
    }

    public void OnDialogCalCancel(ClientEvent clientEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        this.getPopupNewCalendrier().hide();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("CalendrierDeliberationVOIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        //set current row back to original row
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        if(oldCurrentRowKey != null){
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCalendrierListView());
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.renderResponse();
    }

    public void setPopAnClosed(RichPopup popAnClosed) {
        this.popAnClosed = popAnClosed;
    }

    public RichPopup getPopAnClosed() {
        return popAnClosed;
    }
}
