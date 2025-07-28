package personne_etudiant;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.services.inscriptionAppImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

public class PersonneEtudBean {
    private RichInputText nom;
    private RichInputText prenom;
    private RichInputDate date_naiss;
    private RichInputText lieu_naiss;
    private RichInputText adresse;
    private RichInputText cin;
    private RichPopup pop;
    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    
    Map sessionScope = ADFContext.getCurrent().getSessionScope();
    private String parcours = sessionScope.get("id_niv_form_parcours").toString();
    private String anne_univers = sessionScope.get("id_annee").toString();
    private String session = sessionScope.get("id_session").toString();
    private String utilisateur = sessionScope.get("id_user").toString();

    public PersonneEtudBean() {
    }


    public BindingContainer getBindings() {
        return (BindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String onRechercheEtudiant() {
        // Add event code here...
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject liste_persVo = am.getpersEtudiantRO();
        
        ViewCriteriaManager vcm = liste_persVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("persEtudiantROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_etud", nom.getValue());
        vvm.setVariableValue("prenom_etud", prenom.getValue());
        vvm.setVariableValue("date_naiss", date_naiss.getValue());
        vvm.setVariableValue("lieu_naiss", lieu_naiss.getValue());
        vvm.setVariableValue("adress", adresse.getValue());
        vvm.setVariableValue("cin", cin.getValue());                                                          
        liste_persVo.applyViewCriteria(vc);
        liste_persVo.executeQuery();
        return null;
    }
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setNom(RichInputText nom) {
        this.nom = nom;
    }

    public RichInputText getNom() {
        return nom;
    }

    public void setPrenom(RichInputText prenom) {
        this.prenom = prenom;
    }

    public RichInputText getPrenom() {
        return prenom;
    }

    public void setDate_naiss(RichInputDate date_naiss) {
        this.date_naiss = date_naiss;
    }

    public RichInputDate getDate_naiss() {
        return date_naiss;
    }

    public void setLieu_naiss(RichInputText lieu_naiss) {
        this.lieu_naiss = lieu_naiss;
    }

    public RichInputText getLieu_naiss() {
        return lieu_naiss;
    }

    public void setAdresse(RichInputText adresse) {
        this.adresse = adresse;
    }

    public RichInputText getAdresse() {
        return adresse;
    }

    public void setCin(RichInputText cin) {
        this.cin = cin;
    }

    public RichInputText getCin() {
        return cin;
    }

    public String onReset() {
        // Add event code here...
        inscriptionAppImpl am = (inscriptionAppImpl)resolvElDC("inscriptionAppDataControl");
        ViewObject liste_persVo = am.getpersEtudiantRO();
        ViewObject attrVo=am.getAttrSearchPers();   // VO attribut de recherche
        ViewCriteriaManager vcm = liste_persVo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("persEtudiantROVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("nom_etud", null);
        vvm.setVariableValue("prenom_etud", null);
        vvm.setVariableValue("date_naiss", null);
        vvm.setVariableValue("lieu_naiss", null);
        vvm.setVariableValue("adress", null);
        vvm.setVariableValue("cin", null);   
        liste_persVo.applyViewCriteria(vc);
        liste_persVo.executeQuery();
        attrVo.executeQuery();
        return null;
    }
    public static String getRandomStr(int n) 
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
    
        StringBuilder s = new StringBuilder(n); 
    
        for (int i = 0; i < n; i++) { 
            int index = (int)(str.length() * Math.random()); 
            s.append(str.charAt(index)); 
        } 
        return s.toString(); 
    } 

    public static String getRandomNomber(int n) 
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "123456789"; 
    
        StringBuilder s = new StringBuilder(n); 
    
        for (int i = 0; i < n; i++) { 
            int index = (int)(str.length() * Math.random()); 
            s.append(str.charAt(index)); 
        } 
        return s.toString(); 
    }
    
    public String onAddEtudiant() {
        // Add event code here...
        
        ADFContext adfCtx = ADFContext.getCurrent();
        Map sessionScope = adfCtx.getSessionScope();
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter_pers = (DCIteratorBinding) bindings.get("persEtudiantROIterator");
        Row current_pers = iter_pers.getCurrentRow();
        
        sessionScope.put("nom", current_pers.getAttribute("Nom").toString());
        sessionScope.put("prenom", current_pers.getAttribute("Prenoms").toString());
        
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("EtudiantsIterator");
        Row oldCcurrentRow = dciter.getCurrentRow();
        
        AttributeBinding util = (AttributeBinding) getBindings().getControlBinding("UtiCree");
        AttributeBinding id_pers = (AttributeBinding) getBindings().getControlBinding("IdPersonne");
        AttributeBinding numero_etud = (AttributeBinding) getBindings().getControlBinding("Numero");

        if(oldCcurrentRow!=null)
            adfCtx.getViewScope().put(OLD_CURR_KEY_VIEWSCOPE_ATTR, oldCcurrentRow.getKey().toStringFormat(true));
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        else{
            util.setInputValue(Long.parseLong(sessionScope.get("id_user").toString()));
            id_pers.setInputValue(Long.parseLong(current_pers.getAttribute("IdPersonne").toString()));
            String chaine_aleatoire = getRandomStr(3);
            //numero_etud.setInputValue("202109"+chaine_aleatoire);
            
            OperationBinding getAnneeEnCours = getBindings().getOperationBinding("getAnneeEnCours");
            getAnneeEnCours.getParamsMap().put("id_annee",  Long.parseLong(sessionScope.get("id_annee").toString()));
            getAnneeEnCours.execute();
            DCIteratorBinding iter_annee_cours = (DCIteratorBinding)getBindings().get("AnneeUniversEnCoursROIterator");
            String annee_cours = iter_annee_cours.getCurrentRow().getAttribute("AnneeCours").toString();

            String nomb_alea = getRandomNomber(1);
            numero_etud.setInputValue(annee_cours+"0"+nomb_alea+chaine_aleatoire);      // Exemple 200903K3C
            
            RichPopup popup = this.getPop();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popup.show(hints);
        }
        
        return null;
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void onDialog(DialogEvent dialogEvent) {
        // Add event code here...        
        AttributeBinding nom_bd = (AttributeBinding) getBindings().getControlBinding("Nom1");
        AttributeBinding prenom_bd = (AttributeBinding) getBindings().getControlBinding("Prenoms1");
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {        // commit
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }            
            else{
                FacesMessage msg = new FacesMessage("La personne du prénom & nom : "+prenom_bd+" "+nom_bd+" est enregistré(e) comme un(e) étudiant(e)");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                onReset();          // pour refresh la page
                //Execute pour refresh l'onglet Etudiant
                OperationBinding op_execute = getBindings().getOperationBinding("Execute");
                op_execute.execute();
            }
            this.getPop().hide();
        }
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        // Add event code here...
        RichPopup popup = this.getPop();
        popup.hide();
        DCIteratorBinding dciter = (DCIteratorBinding) getBindings().get("EtudiantsIterator");
        Row currentRow = dciter.getCurrentRow();
        dciter.removeCurrentRow();
        ADFContext adfCtx = ADFContext.getCurrent();
        String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
        dciter.setCurrentRowWithKey(oldCurrentRowKey);
    }

}
