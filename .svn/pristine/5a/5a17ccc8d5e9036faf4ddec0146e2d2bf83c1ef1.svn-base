package groupe_ling_niv_form;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class NivFormGrpLangBean {
    private RichSelectBooleanCheckbox checkNivLang;

    public NivFormGrpLangBean() {
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    // donne le nombre de cases cochées 
    public Integer nombreCaseCoche(String bind_interator){
        DCIteratorBinding iter = (DCIteratorBinding) getBindings().get(bind_interator);       
        RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null);
        Integer i = 0;
        while (rsi.hasNext()) {
            Row nextRow = rsi.next();
            if(nextRow.getAttribute("case_cocher")!=null){
                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                    i++;
                }
            }
        }
        return i;
    }
    
    public String onValiderNivFormLang() {
        // Add event code here...
        
        DCIteratorBinding iter_niv_form = (DCIteratorBinding) getBindings().get("NivFormationROIterator");        
        RowSetIterator rsi_niv_form = iter_niv_form.getViewObject().createRowSetIterator(null);
        
        DCIteratorBinding iter_grp = (DCIteratorBinding) getBindings().get("NivFormGrpLingLangROIterator");        
        RowSetIterator rsi_grp = iter_grp.getViewObject().createRowSetIterator(null);
        
        DCIteratorBinding iter_grp_lang = (DCIteratorBinding) getBindings().get("NivFormGrpLangIterator");        
        RowSetIterator rsi_grp_lang = iter_grp_lang.getViewObject().createRowSetIterator(null);
        
        
        Integer i =0;
        if(rsi_niv_form.getRowCount()==0){
            FacesMessage msg = new FacesMessage(" Aucune formation disponible pour l'utilisateur connecté ! ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        else{ 
            String lib_niv_form = iter_niv_form.getCurrentRow().getAttribute("Libform").toString();
            
            if(rsi_grp.getRowCount()==0){
                FacesMessage msg = new FacesMessage(" Pas de Groupe Linguistique disponible dans "+lib_niv_form+" ! ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            else{ 
                String lib_grp = iter_grp.getCurrentRow().getAttribute("LibelleLong").toString();
                
                if(rsi_grp_lang.getRowCount()==0){
                    FacesMessage msg = new FacesMessage(" Pas de Langue disponible dans "+lib_grp+" ! ");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{ 
                    if(nombreCaseCoche("NivFormGrpLangIterator")==0){
                        FacesMessage msg = new FacesMessage(" Veuillez cocher une Langue avant de valider ! ");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        AttributeBinding id_grp_ling = (AttributeBinding) getBindings().getControlBinding("IdGrpLing");  
                        AttributeBinding id_niv_from = (AttributeBinding) getBindings().getControlBinding("IdNiveauFormationParcours");
                        AttributeBinding id_annee = (AttributeBinding) getBindings().getControlBinding("IdAnnee");
                        AttributeBinding id_util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
                    
                        AttributeBinding id_grp_ling_lang = (AttributeBinding) getBindings().getControlBinding("IdGrpLingLang");  
                        AttributeBinding id_niv_form_grp_lang = (AttributeBinding) getBindings().getControlBinding("IdNivFormGrpLing1");
                        AttributeBinding id_annee_lang = (AttributeBinding) getBindings().getControlBinding("IdAnnee1");
                        AttributeBinding id_util_lang = (AttributeBinding) getBindings().getControlBinding("UtiCree1");
                    
                        while (rsi_grp_lang.hasNext()) {
                            Row nextRow = rsi_grp_lang.next();
                            if(nextRow.getAttribute("case_cocher")!=null){
                                if(Boolean.parseBoolean(nextRow.getAttribute("case_cocher").toString())){
                                    OperationBinding op_grp = getBindings().getOperationBinding("CreateInsertNivFormGrp");
                                    Object result_ped = op_grp.execute();
                                                  
                                    if (!op_grp.getErrors().isEmpty()) {
                                       return null;
                                    }
                                    else{
                                        id_grp_ling.setInputValue(Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
                                        id_niv_from.setInputValue(Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()));
                                        id_annee.setInputValue(Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
                                        id_util.setInputValue(Long.parseLong("33"));
                                       
                                       OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
                                       Object result = operationBinding1.execute();
                                       if (!operationBinding1.getErrors().isEmpty()) {
                                               //handle errors if any
                                               //...
                                               return null;
                                       }
                                       else{
                                               //derniere valeur inseree du groupe linguistique
                                               AttributeBinding id_niv_form_grp = (AttributeBinding) getBindings().getControlBinding("IdNivFormGrpLing");
                                           
                                               OperationBinding op_grp_lang = getBindings().getOperationBinding("CreateInsertNivFormLang");
                                               Object result_lang = op_grp_lang.execute();
                                                             
                                               if (!op_grp.getErrors().isEmpty()) {
                                                  return null;
                                               }
                                               else{
                                                   id_niv_form_grp_lang.setInputValue(Long.parseLong(id_niv_form_grp.getInputValue().toString()));
                                                   id_grp_ling_lang.setInputValue(Long.parseLong(iter_grp_lang.getCurrentRow().getAttribute("IdGrpLingLangue").toString()));
                                                   id_annee_lang.setInputValue(Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
                                                   id_util_lang.setInputValue(Long.parseLong("33"));
                                                  
                                                  OperationBinding op_grp_lang_commit = getBindings().getOperationBinding("Commit");
                                                  Object result_lang_commit = op_grp_lang_commit.execute();
                                                  if (!op_grp_lang_commit.getErrors().isEmpty()) {
                                                          return null;
                                                  }
                                                  else{
                                                       i++;
                                                      }
                                                  }
                                           }
                                       }
                                }
                            }
                        }
   
                    if (i < 1) {
                        FacesMessage msg = new FacesMessage(" Impossible d'ajouter une langue dans le groupe "+lib_grp+" ! ");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{                       
                            FacesMessage msg = new FacesMessage(" "+ i+" Langue(s) ajoutées dans le groupe "+lib_grp+" ! ");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            //refresh les langues à selectionner
                            OperationBinding op_lang = getBindings().getOperationBinding("ExecuteWithParamsLang");
                            op_lang.getParamsMap().put("id_grp",  Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
                            op_lang.getParamsMap().put("id_parcours",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()));
                            op_lang.getParamsMap().put("id_annee",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
                            op_lang.execute();
                            //refresh la liste des langues du groupe selectionné
                            OperationBinding op_liste_lang = getBindings().getOperationBinding("ExecuteWithParamsListeLang");
                            op_liste_lang.getParamsMap().put("id_grp",  Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
                            op_liste_lang.getParamsMap().put("id_parc",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()));
                            op_liste_lang.getParamsMap().put("id_annee",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
                            op_liste_lang.execute();
                        }
                    }
                }
            }
        }
 
        return null;
    }

    public void onSelectAllNivLang(ValueChangeEvent valueChangeEvent) {
        // cocher tous
        if(Boolean.parseBoolean(checkNivLang.getValue().toString())){
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NivFormGrpLangIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.TRUE);
            }
        }
        else{
            //décocher tous
            DCIteratorBinding iter = (DCIteratorBinding) getBindings().get("NivFormGrpLangIterator");        
            RowSetIterator rsi = iter.getViewObject().createRowSetIterator(null); 
            while (rsi.hasNext()) {
                Row nextRow = rsi.next();
                nextRow.setAttribute("case_cocher", Boolean.FALSE);
            }
        }
    }

    public void setCheckNivLang(RichSelectBooleanCheckbox checkNivLang) {
        this.checkNivLang = checkNivLang;
    }

    public RichSelectBooleanCheckbox getCheckNivLang() {
        return checkNivLang;
    }

    public void handleTableClick(ClientEvent clientEvent) {
        DCIteratorBinding iter_niv_form = (DCIteratorBinding) getBindings().get("NivFormationROIterator");        
        
        DCIteratorBinding iter_grp = (DCIteratorBinding) getBindings().get("NivFormGrpLingLangROIterator");        
        
        OperationBinding op_lang = getBindings().getOperationBinding("ExecuteWithParamsLang");
        op_lang.getParamsMap().put("id_grp",  Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
        op_lang.getParamsMap().put("id_parcours",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()));
        op_lang.getParamsMap().put("id_annee",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
        op_lang.execute();
        
        OperationBinding op_liste_lang = getBindings().getOperationBinding("ExecuteWithParamsListeLang");
        op_liste_lang.getParamsMap().put("id_grp",  Long.parseLong(iter_grp.getCurrentRow().getAttribute("IdGrpLing").toString()));
        op_liste_lang.getParamsMap().put("id_parc",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdNiveauFormationParcours").toString()));
        op_liste_lang.getParamsMap().put("id_annee",  Long.parseLong(iter_niv_form.getCurrentRow().getAttribute("IdAnneesUnivers").toString()));
        op_liste_lang.execute();
        
    }
}
