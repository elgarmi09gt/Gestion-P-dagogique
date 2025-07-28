package responsable_etud;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class ResponsableBean {
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();
    
    public ResponsableBean() {
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getcu(){
        return "test";
    }
    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void getResponsable(String id_etud_resp){
        System.out.println("id_etud_resp "+id_etud_resp);
        OperationBinding responsable_etud = getBindings().getOperationBinding("getResponsableEtud");
        responsable_etud.getParamsMap().put("id_etudiant",  Long.parseLong(id_etud_resp));
        responsable_etud.execute();
        
        DCIteratorBinding iter_etud_resp = (DCIteratorBinding)getBindings().get("ResponsablesIterator");
            //Create RowSetIterato iterate over viewObject
        RowSetIterator rsi_etud_resp = iter_etud_resp.getViewObject().createRowSetIterator(null);
        Row row_etud_resp = rsi_etud_resp.getCurrentRow();
        
        if(row_etud_resp == null){
            AttributeBinding id_utilisateur = (AttributeBinding) getBindings().getControlBinding("UtiCreeResp");
            AttributeBinding id_etudiant = (AttributeBinding) getBindings().getControlBinding("IdEtudiantResp");
            
            OperationBinding op_etud_resp = getBindings().getOperationBinding("CreateInsertResponsable");
            Object res_etud_resp = op_etud_resp.execute();
            if (!op_etud_resp.getErrors().isEmpty()) {
                return ;
            }
            else{
                id_utilisateur.setInputValue(Long.parseLong(getUtilisateur()));
                id_etudiant.setInputValue(Long.parseLong(id_etud_resp));
            }
        }
    }

    public String onValiderResponsableEtud() {
        // Add event code here...
        DCIteratorBinding iter_etud_resp = (DCIteratorBinding)getBindings().get("ResponsablesIterator");
        Row row_etud_resp = iter_etud_resp.getCurrentRow();
        
        row_etud_resp.setAttribute("UtiCree", Long.parseLong(getUtilisateur()));
        row_etud_resp.setAttribute("IdEtudiant", Long.parseLong(sessionScope.get("id_etud_resp").toString()));
        
       if(row_etud_resp.getAttribute("Nom") == null || row_etud_resp.getAttribute("Prenoms") == null || row_etud_resp.getAttribute("Adresse") == null
           || row_etud_resp.getAttribute("Portable") == null || row_etud_resp.getAttribute("Etudiant") == null || row_etud_resp.getAttribute("Contact") == null || row_etud_resp.getAttribute("Lienparente") == null)
       {
            
            FacesMessage msg = new FacesMessage(" Les champs (*) sont obligatoires :");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            if(row_etud_resp.getAttribute("Prenoms") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Prénom"));
            }
            if(row_etud_resp.getAttribute("Nom") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Nom"));
            }
            if(row_etud_resp.getAttribute("Adresse") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> L'adresse"));
            }
            
            
            if(row_etud_resp.getAttribute("Portable") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Portable"));
            }
            if(row_etud_resp.getAttribute("Etudiant") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Est-il étudiant ?"));
            }
            if(row_etud_resp.getAttribute("Lienparente") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> Votre Lien de Parenté"));
            }
            if(row_etud_resp.getAttribute("Contact") == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" --> A contacter ?"));
            }

        }
       else{
            OperationBinding op_pers_commit = getBindings().getOperationBinding("Commit");
            Object result_pers_commit = op_pers_commit.execute();
            if (!op_pers_commit.getErrors().isEmpty()) {
                return null;
            }
            
            else{
                FacesMessage msg = new FacesMessage(" Responsable enregistré avec succès ");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
       }
        return null;
    }
}
