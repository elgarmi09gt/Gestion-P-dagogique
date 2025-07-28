package view.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import view.beans.entities.Renonciation;

public class RenonciationBean {
    ADFContext adfCtx = ADFContext.getCurrent();
    Map sessionFlowScope = adfCtx.getSessionScope();
    private Long p_user = Long.parseLong(sessionFlowScope.get("id_user").toString());
    private RichPanelCollection panCol;
    private RichTable etdTable;
    private RichPopup popNostudentSelected;
    private RichPopup popWithNoDate;

    public RenonciationBean() {
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    @SuppressWarnings("unchecked")
    public void OnSaveRenonceAction(ActionEvent actionEvent) {
        DCBindingContainer bindings = (DCBindingContainer) getBindings();
        ArrayList<String> listNoDate = new ArrayList<String>();
        Set<Renonciation> listRenonciation = new HashSet<Renonciation>();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EtudiantsRenonciationNoteIterator");
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if (null != nextRow.getAttribute("isEtdSelcted")) {
                if (1 == Integer.parseInt(nextRow.getAttribute("isEtdSelcted").toString())) {
                    if (null == nextRow.getAttribute("DateRenonciation")) {
                        listNoDate.add(nextRow.getAttribute("Numero").toString());
                    } else {
                        Renonciation r = new Renonciation();
                        r.setEtdId(Long.parseLong(nextRow.getAttribute("IdEtudiant").toString()));
                        r.setFilEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
                        r.setPrcrsMaq(Long.parseLong(nextRow.getAttribute("IdParcoursMaquetAnnee").toString()));
                        r.setRenonceDate(nextRow.getAttribute("DateRenonciation").toString());
                        listRenonciation.add(r);
                    }
                }
            }
        }
        rsi.closeRowSetIterator();
        if ((0 == listNoDate.size()) && (0 == listRenonciation.size())) {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getPopNostudentSelected().show(hints);
        } else if (0 != listNoDate.size()) {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getPopWithNoDate().show(hints);
        } else {
            for (Renonciation rn : listRenonciation) {
                try {
                    OperationBinding op = getBindings().getOperationBinding("RenoncerNote");
                    op.getParamsMap().put("p_etd_id", rn.getEtdId());
                    op.getParamsMap().put("p_prcrs_maq", rn.getPrcrsMaq());
                    op.getParamsMap().put("p_fil_ec", rn.getFilEc());
                    op.getParamsMap().put("p_date", rn.getRenonceDate());
                    op.getParamsMap().put("p_uti", getP_user());
                    op.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ViewObject vo = iter.getViewObject();
            vo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanCol());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdTable());
        }
    }

    public void setP_user(Long p_user) {
        this.p_user = p_user;
    }

    public Long getP_user() {
        return p_user;
    }

    public void setPanCol(RichPanelCollection panCol) {
        this.panCol = panCol;
    }

    public RichPanelCollection getPanCol() {
        return panCol;
    }

    public void setEtdTable(RichTable etdTable) {
        this.etdTable = etdTable;
    }

    public RichTable getEtdTable() {
        return etdTable;
    }

    public void setPopNostudentSelected(RichPopup popNostudentSelected) {
        this.popNostudentSelected = popNostudentSelected;
    }

    public RichPopup getPopNostudentSelected() {
        return popNostudentSelected;
    }

    public void setPopWithNoDate(RichPopup popWithNoDate) {
        this.popWithNoDate = popWithNoDate;
    }

    public RichPopup getPopWithNoDate() {
        return popWithNoDate;
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public void onEcChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        /*DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("EtudiantsRenonciationNoteIterator");

        ViewObject vo = iter.getViewObject();
        vo.applyViewCriteria(null);
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanCol());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEtdTable());
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getEtdTable().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getEtdTable().queueEvent(new QueryEvent(getEtdTable(), queryDescriptor));
        }*/
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getEtdTable().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterConjunctionCriterion() != null) {
            ConjunctionCriterion cc = queryDescriptor.getFilterConjunctionCriterion();
            List<Criterion> lc = cc.getCriterionList();
            for (Criterion c : lc) {
                if (c instanceof AttributeCriterion) {
                    AttributeCriterion ac = (AttributeCriterion) c;
                    ac.setValue(null);
                }
            }
            getEtdTable().queueEvent(new QueryEvent(getEtdTable(), queryDescriptor));
        }
    }
}
