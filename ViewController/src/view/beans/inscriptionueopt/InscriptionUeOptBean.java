package view.beans.inscriptionueopt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class InscriptionUeOptBean {
    private RichPopup popuNotitemSelected;

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private Integer utilisateur = Integer.parseInt(sessionScope.get("id_user").toString());
    private RichPopup popupSuccessInsc;
    private RichPopup popupFailedInsc;
    private RichTable filUeTable;
    private RichPanelCollection filUePanCol;
    private RichPanelHeader filUePanHdr;
    private RichTable etdTable;
    private RichPanelCollection etdPanCol;
    private RichPanelHeader etdPanHdr;
    private RichSelectBooleanCheckbox is_ue;
    private RichSelectBooleanCheckbox is_etd;

    public InscriptionUeOptBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnInscriptionClicked(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        Integer error_count = 0;
        try {
            OperationBinding selectedetd = bindings.getOperationBinding("getSelectedEtudiant");
            OperationBinding selectedfilue = bindings.getOperationBinding("getSelectedFilUe");
            @SuppressWarnings("unchecked")
            ArrayList<Long> etudiants = (ArrayList<Long>) selectedetd.execute();
            HashMap<String, String> filues = (HashMap<String, String>) selectedfilue.execute();
            if ((0 != filues.size()) && (0 != etudiants.size())) {
                for (int etucounter = 0; etucounter < etudiants.size(); etucounter++) {
                    //System.out.println("Ins_ped : "+etudiants.get(etucounter));
                    for (String i : filues.keySet()) {
                        //System.out.println("IdFiliereUeSemstre: " + i + " IdSemestre: " + filues.get(i));
                        
                        Long inspedsem_id = new Long(0);
                        try {
                            OperationBinding opinspedSem = bindings.getOperationBinding("getInspedSem");
                            opinspedSem.getParamsMap().put("ins_ped", etudiants.get(etucounter));
                            opinspedSem.getParamsMap().put("sem_id", filues.get(i));
                            inspedsem_id = (Long) opinspedSem.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                            error_count++;
                        }
                        if (0 != inspedsem_id) {
                            //System.out.println("Insped sem " + inspedsem_id);
                           Long inspedsemue_id = new Long(0);
                            try {
                                OperationBinding opinspedSemUe =
                                    bindings.getOperationBinding("createOrUpdateInsPedSemUe");
                                opinspedSemUe.getParamsMap().put("ins_ped_sem_id", inspedsem_id);
                                opinspedSemUe.getParamsMap().put("fil_ue_id", i);
                                opinspedSemUe.getParamsMap().put("utilisateur", getUtilisateur());
                                inspedsemue_id = (Long) opinspedSemUe.execute();
                            } catch (Exception e) {
                                error_count++;
                                System.out.println(e);
                            }
                        }
                    }
                }
                this.getIs_ue().setValue(false);
                this.getIs_etd().setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUeTable());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdTable());
                RichPopup popup = this.getPopupSuccessInsc();
                RichPopup.PopupHints hs = new RichPopup.PopupHints();
                popup.show(hs);
            } else {
                //error_count = -1;
                RichPopup pop = getPopuNotitemSelected();
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                pop.show(hints);
            }
        } catch (Exception e) {
            System.out.println(e);
            error_count++;
        }
        if (error_count > 0){
            RichPopup popup = this.getPopupFailedInsc();
            RichPopup.PopupHints hs = new RichPopup.PopupHints();
            popup.show(hs);
        }
        System.out.println("error_count : "+error_count);
    }

    public void setPopuNotitemSelected(RichPopup popuNotitemSelected) {
        this.popuNotitemSelected = popuNotitemSelected;
    }

    public RichPopup getPopuNotitemSelected() {
        return popuNotitemSelected;
    }

    public void setUtilisateur(Integer utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public void setPopupSuccessInsc(RichPopup popupSuccessInsc) {
        this.popupSuccessInsc = popupSuccessInsc;
    }

    public RichPopup getPopupSuccessInsc() {
        return popupSuccessInsc;
    }

    public void setPopupFailedInsc(RichPopup popupFailedInsc) {
        this.popupFailedInsc = popupFailedInsc;
    }

    public RichPopup getPopupFailedInsc() {
        return popupFailedInsc;
    }

    public void onSelectedFilUe(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("FiliereUeSemestreInsUeOptIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isFilUeSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUeTable());
    }

    public void setFilUeTable(RichTable filUeTable) {
        this.filUeTable = filUeTable;
    }

    public RichTable getFilUeTable() {
        return filUeTable;
    }

    public void setFilUePanCol(RichPanelCollection filUePanCol) {
        this.filUePanCol = filUePanCol;
    }

    public RichPanelCollection getFilUePanCol() {
        return filUePanCol;
    }

    public void setFilUePanHdr(RichPanelHeader filUePanHdr) {
        this.filUePanHdr = filUePanHdr;
    }

    public RichPanelHeader getFilUePanHdr() {
        return filUePanHdr;
    }

    public void onSelectedUser(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("EtudiantInsUeOptIterator");
         RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
         while (rsi.hasNext()) {
             Row nextRow = rsi.next();
             nextRow.setAttribute("isSelected", valueChangeEvent.getNewValue());
         }
        rsi.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanHdr());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdTable());
    }

    public void setEtdTable(RichTable etdTable) {
        this.etdTable = etdTable;
    }

    public RichTable getEtdTable() {
        return etdTable;
    }

    public void setEtdPanCol(RichPanelCollection etdPanCol) {
        this.etdPanCol = etdPanCol;
    }

    public RichPanelCollection getEtdPanCol() {
        return etdPanCol;
    }

    public void setEtdPanHdr(RichPanelHeader etdPanHdr) {
        this.etdPanHdr = etdPanHdr;
    }

    public RichPanelHeader getEtdPanHdr() {
        return etdPanHdr;
    }

    public void setIs_ue(RichSelectBooleanCheckbox is_ue) {
        this.is_ue = is_ue;
    }

    public RichSelectBooleanCheckbox getIs_ue() {
        return is_ue;
    }

    public void setIs_etd(RichSelectBooleanCheckbox is_etd) {
        this.is_etd = is_etd;
    }

    public RichSelectBooleanCheckbox getIs_etd() {
        return is_etd;
    }
    
    public void initTopEtd(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("EtudiantInsUeOptIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isSelected")){
                if(nextRow.getAttribute("isSelected").equals(false)){
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_etd().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initEtd(){
        if (null != this.getIs_etd().getValue()) {
            if (this.getIs_etd().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("EtudiantInsUeOptIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdPanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEtdTable());
            } 
        }
    }
    
    public void initTopUe(){
        DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                   .getCurrentBindingsEntry()
                                                                   .get("FiliereUeSemestreInsUeOptIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Boolean is_ = true;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            //Si null
            if(null == nextRow.getAttribute("isFilUeSelected")){
                is_ = false;
                break;
            }
            //Si au moins un est décocher
            if(null != nextRow.getAttribute("isFilUeSelected")){
                if(nextRow.getAttribute("isFilUeSelected").equals(false)){
                    is_ = false;
                    break;
                }
            }
        }
        rsi.closeRowSetIterator();
        this.getIs_ue().setValue(is_);
    }  
    //Permet de tout cocher si "le cocher tout" est active
    public void initUe(){
        if (null != this.getIs_ue().getValue()) {
            if (this.getIs_ue().getValue().equals(true)) {
                DCIteratorBinding iter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                           .getCurrentBindingsEntry()
                                                                           .get("FiliereUeSemestreInsUeOptIterator");
                RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
                while (rsi.hasNext()){
                    Row nextRow = rsi.next();
                    nextRow.setAttribute("isFilUeSelected", true);
                }
                rsi.closeRowSetIterator();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanHdr());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUePanCol());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getFilUeTable());
            } 
        }
    }
}
