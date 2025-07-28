package historique_inscription;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.RowSetIterator;

public class HistoriqueInscBean {
    private String taskFlowId = "/inscription/historique_inscription/dynamic-tk.xml#dynamic-tk";
    
    private String num_etud;
    private String num_table;
    private String num_cin;

    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String anne_univers = sessionScope.get("id_annee").toString();

    public HistoriqueInscBean() {
    }

    public void setAnne_univers(String anne_univers) {
        this.anne_univers = anne_univers;
    }

    public String getAnne_univers() {
        return anne_univers;
    }

    public void setNum_etud(String num_etud) {
        this.num_etud = num_etud;
    }

    public String getNum_etud() {
        return num_etud;
    }

    public void setNum_table(String num_table) {
        this.num_table = num_table;
    }

    public String getNum_table() {
        return num_table;
    }

    public void setNum_cin(String num_cin) {
        this.num_cin = num_cin;
    }

    public String getNum_cin() {
        return num_cin;
    }

    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void onRechercheHistInsc(ActionEvent actionEvent) {
        // Add event code here...
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();

        if(((String)this.getNum_etud() == null)&&((String)this.getNum_table() == null)&&((String)this.getNum_cin() == null)){
            sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");            
            FacesMessage msg = new FacesMessage(" Veuillez saisir au moins un numéro ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else 
            if(((String)this.getNum_etud() != null)&&((String)this.getNum_table()!= null)&&((String)this.getNum_cin() != null)){
                sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");
                FacesMessage msg = new FacesMessage(" Veuillez saisir soit numéro étudiant, soit numéro table, soit la CIN ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else 
            if((((String)this.getNum_etud() != null)&&((String)this.getNum_table()!= null))||(((String)this.getNum_cin() != null)&&((String)this.getNum_table()!= null))||(((String)this.getNum_etud() != null)&&((String)this.getNum_cin()!= null))){
                sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");
                FacesMessage msg = new FacesMessage(" Veuillez saisir soit numéro étudiant, soit numéro table, soit la CIN ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else{
            if((String)this.getNum_etud() != null){
                // verifier si numéro étudiant est valide 
                OperationBinding isEtudiant = getBindings().getOperationBinding("isNumEtudiant");
                isEtudiant.getParamsMap().put("num_etud",  num_etud);
                
                Integer result = (Integer)isEtudiant.execute();
                //numéro étudiant non valide
                if(result == 0){
                    sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");
                    FacesMessage msg = new FacesMessage("Le numéro étudiant: "+num_etud+" n'existe pas");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                else{
                    //numéro étudiant non valide
                    // recuperer id personn de l etudiant
                    OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersEtudiant");
                    getIdPersEtudiant.getParamsMap().put("num_etud",  num_etud);                           
                    String result_getid = (String)getIdPersEtudiant.execute();
                    
                    OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                    detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                    detpers.execute();
                    //getEtudiant
                    OperationBinding getEtud = getBindings().getOperationBinding("getEtudiant");
                    getEtud.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                    getEtud.execute();
                    //Détail des historiques d'inscription
                    OperationBinding det_histo = getBindings().getOperationBinding("ExecuteWithParamsDetailHisto");
                    det_histo.getParamsMap().put("idpers",  Long.parseLong(result_getid));                            
                    det_histo.execute();
                                      
                    //task flow dynamic
                    sessionScope.put("TfHistInscID", "/inscription/historique_inscription/detail-historique-inscription.xml#detail-historique-inscription");
                    
                }
            }
            else{
                if((String)this.getNum_table() != null){
                    // vérifier si le numéro table est valide              
                    OperationBinding is_nouv_bac = getBindings().getOperationBinding("isNumNouvBac");
                    is_nouv_bac.getParamsMap().put("num_table",  Long.parseLong(num_table));
                    is_nouv_bac.getParamsMap().put("id_annee",  getAnne_univers());
                    Integer result = (Integer)is_nouv_bac.execute();
                    //numéro table non valide
                    if(result == 0){
                        sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");
                        FacesMessage msg = new FacesMessage("Le numéro table: "+num_table+" n'existe pas");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        //numéro table valide
                        // recuperer id personn du nouveau bachelier
                        OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersBac");
                        getIdPersEtudiant.getParamsMap().put("num_table",  num_table); 
                        getIdPersEtudiant.getParamsMap().put("id_annee",  getAnne_univers());
                        String result_getid = (String)getIdPersEtudiant.execute();  
                        
                        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                        detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                        detpers.execute();
                        //getEtudiant
                        OperationBinding getEtud = getBindings().getOperationBinding("getEtudiant");
                        getEtud.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                        getEtud.execute();
                        //Détail des historiques d'inscription
                        OperationBinding det_histo = getBindings().getOperationBinding("ExecuteWithParamsDetailHisto");
                        det_histo.getParamsMap().put("idpers",  Long.parseLong(result_getid));                                
                        det_histo.execute();

                        //task flow dynamic
                        sessionScope.put("TfHistInscID", "/inscription/historique_inscription/detail-historique-inscription.xml#detail-historique-inscription");
                        
                    }
                }
                else{
                    System.out.println(num_table);
                    // vérifier si le numéro d'identité est valide
                    OperationBinding is_num_cin = getBindings().getOperationBinding("isNumCin");
                    is_num_cin.getParamsMap().put("num_cin",  num_cin);
                    
                    Integer result = (Integer)is_num_cin.execute();
                    //numéro d'identité non valide
                    if(result == 0){
                        System.out.println("num table no valide");
                        sessionScope.put("TfHistInscID", "/inscription/historique_inscription/task-flow-default.xml#task-flow-default");
                        FacesMessage msg = new FacesMessage("Le numéro d'identification : "+num_cin+" n'existe pas");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    else{
                        //numéro d'identité est valide
                        // recuperer id personn à partir de CIN saisie
                        OperationBinding getIdPersEtudiant = getBindings().getOperationBinding("getIdPersCin");
                        getIdPersEtudiant.getParamsMap().put("cin",  num_cin);                                
                        String result_getid = (String)getIdPersEtudiant.execute(); 
                        
                        OperationBinding detpers = getBindings().getOperationBinding("getDetailPers");
                        detpers.getParamsMap().put("idpers_var",  Long.parseLong(result_getid));
                        detpers.execute();
                        //getEtudiant
                        OperationBinding getEtud = getBindings().getOperationBinding("getEtudiant");
                        getEtud.getParamsMap().put("idpers",  Long.parseLong(result_getid));
                        getEtud.execute();
                        //Détail des historiques d'inscription
                        OperationBinding det_histo = getBindings().getOperationBinding("ExecuteWithParamsDetailHisto");
                        det_histo.getParamsMap().put("idpers",  Long.parseLong(result_getid));                                
                        det_histo.execute();

                        //task flow dynamic
                        sessionScope.put("TfHistInscID", "/inscription/historique_inscription/detail-historique-inscription.xml#detail-historique-inscription");
                        
                    }
                }
            }
            
        }
   
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }
}
