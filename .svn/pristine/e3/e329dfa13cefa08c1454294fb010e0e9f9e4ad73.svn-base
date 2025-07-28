package groupe_td_tp;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.event.ItemEvent;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.RowSetIterator;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class GroupeTdTpBean implements Serializable{
    

    private String taskFlowId = "/inscription/Groupe-TD-TP/dynamic-task.xml#dynamic-task";


    public GroupeTdTpBean() {
    }

    public DCBindingContainer getBindingContainer() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext el = fc.getELContext();
        ValueExpression valueExpr =
            fc.getApplication().getExpressionFactory().createValueExpression(el,
                                                                             "#{bindings}",
                                                                             DCBindingContainer.class);
            
            return ((DCBindingContainer)valueExpr.getValue(el));
    }
    
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String onSearch() {
        // Add event code here...
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        Map rep=adfCtx.getRequestScope();
        AttributeBinding IdNiveauFormationParcours = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
        AttributeBinding IdSem = (AttributeBinding) getBindings().getControlBinding("GroupeParcSemestre");
        
        AttributeBinding horaireTd = (AttributeBinding) getBindings().getControlBinding("Parcours");
        AttributeBinding modeEns = (AttributeBinding) getBindings().getControlBinding("Idmode");
        AttributeBinding IdAnneesUnivers = (AttributeBinding) getBindings().getControlBinding("IdAnneesUnivers");
        if(IdNiveauFormationParcours.getInputValue() == null){      // vérifie s'il ya pas de niveau de formation
            sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/task-flow-default.xml#task-flow-default");
            FacesMessage msg = new FacesMessage("Veuillez sélectionner un Niveau de Formation");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{ 
            if(IdSem.getInputValue() == null){  // vérifie si le semestre est null
                sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/task-flow-default.xml#task-flow-default");
                FacesMessage msg = new FacesMessage("Le Niveau de Formation choisi ne possède pas de Semestres");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{ 
                if(horaireTd.getInputValue() == ""){    // vérifie si l'horaire TD est null
                    sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/task-flow-default.xml#task-flow-default");
                    FacesMessage msg = new FacesMessage(" Veuillez choisir un Horaire TD");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{ 
                    if(modeEns.getInputValue() == ""){      // vérifie si le mode enseignement est null
                        sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/task-flow-default.xml#task-flow-default");
                        FacesMessage msg = new FacesMessage(" Veuillez choisir un Mode Enseignement");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/detail-groupe-td-etudiant.xml#detail-groupe-td-etudiant");
                        OperationBinding listeGroupeTd = getBindings().getOperationBinding("ExecuteWithParams");
                        listeGroupeTd.getParamsMap().put("id_parc", Long.parseLong(IdNiveauFormationParcours.getInputValue().toString()));
                        listeGroupeTd.getParamsMap().put("id_sem", Long.parseLong(IdSem.getInputValue().toString()));
                        listeGroupeTd.getParamsMap().put("id_horaire", Long.parseLong(horaireTd.getInputValue().toString()));
                        listeGroupeTd.getParamsMap().put("id_mode", Long.parseLong(modeEns.getInputValue().toString()));
                        listeGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                        listeGroupeTd.execute();
                        
                        DCIteratorBinding iter2 = (DCIteratorBinding)getBindings().get("ListeGroupeTdTpROIterator");
                        RowSetIterator rsi2 = iter2.getViewObject().createRowSetIterator(null);
                        
                        sessionScope.put("info_message", 0);
                        
                        if(rsi2.getRowCount() > 0){
                            Row row_grp_td = rsi2.first();
                            OperationBinding listeEtudGroupeTd = getBindings().getOperationBinding("ExecuteWithParams2");
                            listeEtudGroupeTd.getParamsMap().put("id_groupe", Long.parseLong(row_grp_td.getAttribute("IdGroupeTpTd").toString()));
                            listeEtudGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                            listeEtudGroupeTd.execute();
                            
                            DCIteratorBinding iter_grp = (DCIteratorBinding)getBindings().get("ListeEtudiantGroupeTdROIterator");
                            RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
                            Integer i =0;
                            if(Integer.parseInt(row_grp_td.getAttribute("Effectif").toString()) > rsi_grp.getRowCount()){
                                sessionScope.put("info_message", 1);
                                i = Integer.parseInt(row_grp_td.getAttribute("Effectif").toString()) - rsi_grp.getRowCount();
                                sessionScope.put("reste", i);
                                //rep.put("reste1", i);
                            }
                            else{
                                sessionScope.put("info_message", 0);
                            }
                        }
                        else{
                            OperationBinding listeEtudGroupeTd = getBindings().getOperationBinding("ExecuteWithParams2");
                            listeEtudGroupeTd.getParamsMap().put("id_groupe", 0);
                            listeEtudGroupeTd.getParamsMap().put("id_annee", 0);
                            listeEtudGroupeTd.execute();
                            sessionScope.put("info_message", 0);
                        }
                        OperationBinding listeEtudPasGroupeTd = getBindings().getOperationBinding("ExecuteWithParams1");

                        listeEtudPasGroupeTd.getParamsMap().put("id_niv_form_parc", Long.parseLong(IdNiveauFormationParcours.getInputValue().toString()));
                        listeEtudPasGroupeTd.getParamsMap().put("id_sem", Long.parseLong(IdSem.getInputValue().toString()));
                        listeEtudPasGroupeTd.getParamsMap().put("id_horaire_td", Long.parseLong(horaireTd.getInputValue().toString()));
                        listeEtudPasGroupeTd.getParamsMap().put("id_annee", Long.parseLong(IdAnneesUnivers.getInputValue().toString()));
                        listeEtudPasGroupeTd.execute();

                        AttributeBinding IdUtilisateur = (AttributeBinding) getBindings().getControlBinding("IdUtilisateur");
                        sessionScope.put("IdUtilisateur", IdUtilisateur.getInputValue().toString());
                        DCIteratorBinding iter3 = (DCIteratorBinding)getBindings().get("ListeEtudPasGroupeTdROIterator");
                        RowSetIterator rsi3 = iter3.getViewObject().createRowSetIterator(null);
                        
                        if(rsi3.getRowCount() == 0){
                            sessionScope.put("TfGrpTDID", "/inscription/Groupe-TD-TP/task-flow-default.xml#task-flow-default");
                            FacesMessage msg = new FacesMessage(" Aucun étudiant n'est inscrit dans cet Horaire TD");
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                }
            }
        }
     
        return null;
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }
}
