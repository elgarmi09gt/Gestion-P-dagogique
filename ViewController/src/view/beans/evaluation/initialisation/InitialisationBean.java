package view.beans.evaluation.initialisation;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class InitialisationBean {
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer anne_univers = Integer.parseInt(sessionScope.get("id_annee").toString());
    private Integer session = Integer.parseInt(sessionScope.get("id_session").toString());
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private Integer semestre = Integer.parseInt(sessionScope.get("id_smstre").toString());
    private Long maquette = Long.parseLong(sessionScope.get("id_maquette").toString());
    private Long prcrs_maq = Long.parseLong(sessionScope.get("prcrs_maq_an").toString());
    private RichPanelGridLayout pgl;
    private RichPopup successInitPopup;
    private RichPopup popupNotCalYet;
    private RichPopup noInscPopup;

    public InitialisationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void BeforeOpened(ActionEvent actionEvent) {
        /*System.out.println("User : "+getUtilisateur());
        System.out.println("Session : "+getSession());
        System.out.println("Sem : "+getSemestre());
        System.out.println("An : "+getAnne_univers());*/
        BindingContext cntx = BindingContext.getCurrent();
        // get selected parcours
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciter_parcours = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentParcours = dciter_parcours.getCurrentRow();
        //System.out.println("Parcours : "+ currentParcours.getAttribute("IdNiveauFormationParcours"));
        BindingContainer bindings = getBindings();
        OperationBinding operationParamCal = bindings.getOperationBinding("isParametredCalendar");
        operationParamCal.getParamsMap().put("id_annee", getAnne_univers());
        operationParamCal.getParamsMap()
            .put("id_parcours", Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString()));
        operationParamCal.getParamsMap().put("id_session", getSession());
        operationParamCal.getParamsMap().put("id_semestre", getSemestre());
        operationParamCal.execute();
        DCIteratorBinding is_parametred_cal = (DCIteratorBinding) cbinding.get("isParametredCalendarIterator");
        Row currentCalendrier = is_parametred_cal.getCurrentRow();
        if (currentCalendrier == null) {
            sessionScope.put("is_param_cal", false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPgl());
            /*  RichPopup popup = this.getPopupNotCalYet();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //empty hints renders dialog in center of screen
            popup.show(hints); */
        }else {
            Row currentPMAC = null;
            Integer nbr_insc = 0;
            try {
                OperationBinding opprcrsmaqan = bindings.getOperationBinding("getParcoursMaqAnCal");
                opprcrsmaqan.getParamsMap().put("parcours", currentParcours.getAttribute("IdNiveauFormationParcours"));
                opprcrsmaqan.getParamsMap().put("annee", new Long(getAnne_univers()));
                opprcrsmaqan.getParamsMap().put("semestre", new Long(getSemestre()));
                opprcrsmaqan.getParamsMap().put("sess", new Long(getSession()));
                opprcrsmaqan.getParamsMap().put("maquette", getMaquette());
                opprcrsmaqan.execute();
                DCIteratorBinding iterPMAC = (DCIteratorBinding) cbinding.get("ParcoursMaqAnCalendrierIterator");
                currentPMAC = iterPMAC.getCurrentRow();
                //if (null != currentPMAC) {
                //System.out.println("Calendrier : "+currentPMAC.getAttribute("IdCalendrierDeliberation"));
                //System.out.println("PMA : "+currentPMAC.getAttribute("IdParcoursMaquetAnnee"));
            //} 
            } catch (Exception e) {
                System.out.println(e);
                //System.out.println("in getParcoursMaqAnCal catch");
            }
            //is_inc_exist
            if (null != currentPMAC) {
            
                try {
                    OperationBinding opinsc = bindings.getOperationBinding("getInsc");
                    opinsc.getParamsMap().put("pma", currentPMAC.getAttribute("IdParcoursMaquetAnnee"));
                    nbr_insc = (Integer) opinsc.execute();
                } catch (Exception e) {
                    System.out.println(e);
                    //System.out.println("in getInsc catch");
                } 
                if (0 == nbr_insc) {
                    RichPopup popup = this.getNoInscPopup();
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //empty hints renders dialog in center of screen
                    popup.show(hints);
                } else {
                    try {
                        OperationBinding opinit = bindings.getOperationBinding("Initialiser");
                        opinit.getParamsMap().put("prcrs_mq_an_id", currentPMAC.getAttribute("IdParcoursMaquetAnnee"));
                        //currentPMAC.getAttribute("IdParcoursMaquetAnnee")
                        opinit.getParamsMap().put("calendrier", currentPMAC.getAttribute("IdCalendrierDeliberation"));
                        opinit.getParamsMap().put("utilisateur", new Long(getUtilisateur()));
                        opinit.execute();
                        /*
                OperationBinding operationBinding = bindings.getOperationBinding("initialiserNoteModeEnsFiliere");
                operationBinding.getParamsMap().put("anne_univers", getAnne_univers());
                operationBinding.getParamsMap().put("niv_fomr_parcours", Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString()));
                operationBinding.getParamsMap().put("session_id", getSession());
                operationBinding.getParamsMap().put("utilisateur", getUtilisateur());
                Object result = operationBinding.execute();
    
                OperationBinding operationEmjambist = bindings.getOperationBinding("traitementEnjambiste");
                operationEmjambist.getParamsMap().put("anne_univers", getAnne_univers());
                operationEmjambist.getParamsMap().put("niv_fomr_parcours", Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString()));
                operationEmjambist.getParamsMap().put("utilisateur", getUtilisateur());
                operationEmjambist.execute();*/
    
                        RichPopup popup = this.getSuccessInitPopup();
                        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                        //empty hints renders dialog in center of screen
                        popup.show(hints);
                    } catch (Exception e) {
                        System.out.println(e);
                        //System.out.println("in Initialiser catch");
                    }
                }
            }else{
                System.out.println("Parcours maquette année null !!!");
            }
        }
    }

    public void setAnne_univers(Integer anne_univers) {
        this.anne_univers = anne_univers;
    }

    public Integer getAnne_univers() {
        return anne_univers;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getSession() {
        return session;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    @SuppressWarnings("unchecked")
    public void OnParcoursChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AttributeBinding attrIdBinding = (AttributeBinding) bindings.getControlBinding("IdNiveauFormationParcours");
        Integer IdNiveauFormationParcours = (Integer.parseInt(attrIdBinding.getInputValue().toString()));
        //System.out.println("IdNiveauFormationParcours "+IdNiveauFormationParcours);
        BindingContext cntx = BindingContext.getCurrent();
        // get selected parcours
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciter_parcours = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentParcours = dciter_parcours.getCurrentRow();
        //System.out.println("Parcours : "+ currentParcours.getAttribute("IdNiveauFormationParcours"));
        // isParametredCalendar
        OperationBinding operationParamCal = bindings.getOperationBinding("isParametredCalendar");
        
        operationParamCal.getParamsMap().put("id_annee", getAnne_univers());
        operationParamCal.getParamsMap()
            .put("id_parcours", Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString()));
        operationParamCal.getParamsMap().put("id_session", getSession());
        operationParamCal.getParamsMap().put("id_semestre", getSemestre());
        operationParamCal.execute();
        DCIteratorBinding is_parametred_cal = (DCIteratorBinding) cbinding.get("isParametredCalendarIterator");
        Row currentCalendrier = is_parametred_cal.getCurrentRow();
        if (currentCalendrier == null) {
            sessionScope.put("is_param_cal", false);
            sessionScope.put("icon_param_cal", "/images/delete_icon.png");
        }else {
            sessionScope.put("is_param_cal", true);
            sessionScope.put("icon_param_cal", "/images/commit.png");
        }
        //isClosedInscription
        /* OperationBinding operationCloIns = bindings.getOperationBinding("isClosedInscription");
        
        operationCloIns .getParamsMap().put("id_annee", getAnne_univers());
        operationCloIns .getParamsMap()
            .put("id_parcours", Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString()));
        operationCloIns .execute();
        DCIteratorBinding is_close_insc = (DCIteratorBinding) cbinding.get("isClosedInscriptionIterator");
        Row currentClosedInsc = is_close_insc.getCurrentRow();
        if (currentClosedInsc == null) {
            sessionScope.put("is_close_ins", false);
            sessionScope.put("icon_close_insc", "/images/delete_icon.png");
        }else {
            sessionScope.put("is_close_ins", true);
            sessionScope.put("icon_close_insc", "/images/commit.png");
        } */
        sessionScope.put("is_close_ins", true);
        sessionScope.put("is_valid_maq", true);
        sessionScope.put("is_jury_def", true);
        
        if(Boolean.parseBoolean(sessionScope.get("is_param_cal").toString() )&& 
        Boolean.parseBoolean(sessionScope.get("is_valid_maq").toString() )&&
        Boolean.parseBoolean(sessionScope.get("is_close_ins").toString()) &&
        Boolean.parseBoolean(sessionScope.get("is_jury_def").toString())){
            sessionScope.put("diseableBtn", false);
        }else{
            sessionScope.put("diseableBtn", true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPgl());
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setPgl(RichPanelGridLayout pgl) {
        this.pgl = pgl;
    }

    public RichPanelGridLayout getPgl() {
        return pgl;
    }

    public void setSuccessInitPopup(RichPopup successInitPopup) {
        this.successInitPopup = successInitPopup;
    }

    public RichPopup getSuccessInitPopup() {
        return successInitPopup;
    }

    public void setPopupNotCalYet(RichPopup popupNotCalYet) {
        this.popupNotCalYet = popupNotCalYet;
    }

    public RichPopup getPopupNotCalYet() {
        return popupNotCalYet;
    }

    @SuppressWarnings("unchecked")
    public void checkConfig(String parcours,String maquette){
        BindingContainer bindings = getBindings();
        //System.out.println("Parcours : "+ parcours);
        //System.out.println("Maquette : "+ maquette);
        // isParametredCalendar
        try{
              OperationBinding operationParamCal = bindings.getOperationBinding("isParametredCalendar");
        operationParamCal.getParamsMap().put("id_annee", getAnne_univers());
        operationParamCal.getParamsMap().put("id_parcours", Long.parseLong(parcours));
        operationParamCal.getParamsMap().put("id_session", getSession());
        operationParamCal.getParamsMap().put("id_semestre", getSemestre());
        operationParamCal.execute();
        }catch(Exception e){
            System.out.println(e);
        }
        DCIteratorBinding is_parametred_cal = (DCIteratorBinding) bindings.get("isParametredCalendarIterator");
        Row currentCalendrier = is_parametred_cal.getCurrentRow();
        //System.out.println("is param cal size : "+is_parametred_cal.getEstimatedRowCount());
        if (currentCalendrier == null) {
            sessionScope.put("is_param_cal", false);
            sessionScope.put("icon_param_cal", "/images/delete_icon.png");
        }else {
            sessionScope.put("is_param_cal", true);
            sessionScope.put("icon_param_cal", "/images/ok-24.png");
        }
        try {
            OperationBinding opmaqVal = bindings.getOperationBinding("isMaquetteValide");
            opmaqVal.getParamsMap().put("anne", new Long(getAnne_univers()));
            opmaqVal.getParamsMap().put("parcours", Long.parseLong(parcours));
            opmaqVal.getParamsMap().put("maquette", Long.parseLong(maquette));
            Long is_valid = (Long) opmaqVal.execute();
            //System.out.println(" is_valid: "+is_valid);
            if(0 != is_valid){
                sessionScope.put("is_valid_maq", true);
            sessionScope.put("icon_valide_maq", "/images/ok-24.png");
            }else{
            sessionScope.put("is_valid_maq", false);
            sessionScope.put("icon_valide_maq", "/images/delete_icon.png");
            }
        } catch(Exception e){
            System.out.println(e);
            sessionScope.put("is_valid_maq", false);
            sessionScope.put("icon_valide_maq", "/images/delete_icon.png");
        }
        if(Boolean.parseBoolean(sessionScope.get("is_param_cal").toString() )&& 
        Boolean.parseBoolean(sessionScope.get("is_valid_maq").toString())){
            sessionScope.put("diseableBtn", false);
        }else{
            sessionScope.put("diseableBtn", true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPgl());
    }

    public void setNoInscPopup(RichPopup noInscPopup) {
        this.noInscPopup = noInscPopup;
    }

    public RichPopup getNoInscPopup() {
        return noInscPopup;
    }

    public void setMaquette(Long maquette) {
        this.maquette = maquette;
    }

    public Long getMaquette() {
        return maquette;
    }

    public void setPrcrs_maq(Long prcrs_maq) {
        this.prcrs_maq = prcrs_maq;
    }

    public Long getPrcrs_maq() {
        return prcrs_maq;
    }
}
