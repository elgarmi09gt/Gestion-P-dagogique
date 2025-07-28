package view.beans.evaluation.releves;

import java.io.Serializable;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import java.util.Map;
import java.util.HashMap;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;

public class RelevesAttestationBean implements Serializable {

    private String taskFlowId = "/evaluation/releves/releveTF.xml#releveTF";
    private Map<String, Integer> parameterMap = new HashMap<String, Integer>();
    private Integer id_parcours;
        
    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String attestationTF() {
        BindingContext cntx = BindingContext.getCurrent();
        DCBindingContainer cbinding = (DCBindingContainer) cntx.getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) cbinding.get("ParcoursIterator");
        Row currentParcours = dciter.getCurrentRow();
        id_parcours = Integer.parseInt(currentParcours.getAttribute("IdNiveauFormationParcours").toString());
        System.out.println("id_parcours : " + id_parcours);
        setParameterVal();
        setDynamicTaskFlowId("/evaluation/releves/attestationTF.xml#attestationTF");
        return null;
    }

    public String releveTF() {
        setDynamicTaskFlowId("/evaluation/releves/releveTF.xml#releveTF");
        return null;
    }
    private void setParameterVal() {
        parameterMap.put("id_annee", 2);
        parameterMap.put("id_parcours", getId_parcours());
        parameterMap.put("id_structure", 5);
    }

    public void setParameterMap(Map<String, Integer> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Integer> getParameterMap() {
        return parameterMap;
    }

    public void setId_parcours(Integer id_parcours) {
        this.id_parcours = id_parcours;
    }

    public Integer getId_parcours() {
        return id_parcours;
    }
}
