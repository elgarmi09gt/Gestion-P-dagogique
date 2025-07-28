package model.services;

import java.sql.CallableStatement;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
//import model.readOnlyView.T
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.readOnlyView.AnneeUnivAnscInscROVOImpl;
import model.readOnlyView.AnneeUniverROVOImpl;
import model.readOnlyView.AnneeUniversEnCoursROVOImpl;
import model.readOnlyView.AnneeUniversPasseeROVOImpl;
import model.readOnlyView.AnneeUniversSupROVOImpl;
import model.readOnlyView.AnnulerSuspendreLOVImpl;
import model.readOnlyView.AptePreinsImpl;
import model.readOnlyView.AttEnregistrementDiplROVOImpl;
import model.readOnlyView.AttrComplAutorisationImpl;
import model.readOnlyView.AttrOblInscPedROVOImpl;
import model.readOnlyView.AttrParcoursImpl;
import model.readOnlyView.AttrResulancienROVOImpl;
import model.readOnlyView.AttrSearchNumDiplomeROVOImpl;
import model.readOnlyView.AttrSearchPersAutImpl;
import model.readOnlyView.AttrSearchPersImpl;
import model.readOnlyView.AttributSearchROVOImpl;
import model.readOnlyView.AutoInscPreinsROVOImpl;
import model.readOnlyView.AutoResulAnnValideROVOImpl;
import model.readOnlyView.AutorisationInscriptionROVOImpl;
import model.readOnlyView.AutorisationValideDapROVOImpl;
import model.readOnlyView.AutorisationValideROVOImpl;
import model.readOnlyView.AutorisationValideRespROVOImpl;
import model.readOnlyView.BoursesRecuLOVImpl;
import model.readOnlyView.BoursierLOVImpl;

import model.readOnlyView.DerogationEtudROVOImpl;
import model.readOnlyView.DetailStrcDerogationROVOImpl;
import model.readOnlyView.DiplomeEtudiantGradeROVOImpl;
import model.readOnlyView.DroitNiveauPaysROVOImpl;
import model.readOnlyView.EquivalenceListeROVOImpl;
import model.readOnlyView.EquivalenceRechercheROVOImpl;
import model.readOnlyView.EtudAcnienROVOImpl;
import model.readOnlyView.EtudBuPreinsROVOImpl;
import model.readOnlyView.EtudEnRegleBuROVOImpl;
import model.readOnlyView.EtudPaiementROVOImpl;
import model.readOnlyView.EtudiantBuROVOImpl;
import model.readOnlyView.EtudiantExcluSuspenduROVOImpl;
import model.readOnlyView.EtudiantResultatCycleROVOImpl;
import model.readOnlyView.EtudiantTicROVOImpl;
import model.readOnlyView.ExcluLOVImpl;
import model.readOnlyView.ExonerationEcolageROVOImpl;
import model.readOnlyView.FormPaiementEtudROVOImpl;
import model.readOnlyView.FormPayCompteEtudROVOImpl;
import model.readOnlyView.FormPayEcolRegleROVOImpl;
import model.readOnlyView.GroupeParcSemestreImpl;
import model.readOnlyView.GroupeTdTpParcoursImpl;
import model.readOnlyView.HistResultAncienROVOImpl;
import model.readOnlyView.HistStructAncInscROVOImpl;
import model.readOnlyView.HistoMobiliteROVOImpl;
import model.readOnlyView.HistoPaiementEtudROVOImpl;
import model.readOnlyView.InfoExonerationAnterieurROVOImpl;
import model.readOnlyView.InsChangementCycleROVOImpl;
import model.readOnlyView.InsPedSemUe2Impl;
import model.readOnlyView.InscAncienneNiv1Impl;
import model.readOnlyView.InscAncienneNiv2Impl;
import model.readOnlyView.InscAncienneROVOImpl;
import model.readOnlyView.InscDoubleROVOImpl;
import model.readOnlyView.InscInfoGlobleFormImpl;
import model.readOnlyView.InscNivFormationROVOImpl;
import model.readOnlyView.InscNombreMaxiROVOImpl;
import model.readOnlyView.InscPedAnneeROVOImpl;
import model.readOnlyView.InscPedChangeNationaliteROVOImpl;
import model.readOnlyView.InscPedEnCoursExcluROVOImpl;
import model.readOnlyView.InscPedEtatInscROVOImpl;
import model.readOnlyView.InscPedExcluROVOImpl;
import model.readOnlyView.InscPedFormPayEcolROVOImpl;
import model.readOnlyView.InscPedParcEnCoursROVOImpl;
import model.readOnlyView.InscPedSemAncienROVOImpl;
import model.readOnlyView.InscPedSemEtudMobiliteROVOImpl;
import model.readOnlyView.InscPedSemParcROVOImpl;
import model.readOnlyView.InscPedSemUeImpl;
import model.readOnlyView.InscPedagogiqueROVOImpl;
import model.readOnlyView.InscrAdminPedROVOImpl;
import model.readOnlyView.InscriptPedSemROVOImpl;
import model.readOnlyView.InscriptPedSemUeROVOImpl;
import model.readOnlyView.InscriptionPedSemUeROVOImpl;
import model.readOnlyView.LesInscPedROVOImpl;
import model.readOnlyView.LesInscPedSemImpl;
import model.readOnlyView.LesPersonnesROVOImpl;
import model.readOnlyView.LesSuspenduROVOImpl;
import model.readOnlyView.LesUeInscPedSemROVOImpl;
import model.readOnlyView.ListeEtudAnnSuspROVOImpl;
import model.readOnlyView.ListeEtudFinalAnnSuspROVOImpl;
import model.readOnlyView.ListeEtudInscProvROVOImpl;
import model.readOnlyView.ListeEtudNotPreInsROVOImpl;
import model.readOnlyView.ListeEtudPasGroupeTdROVOImpl;
import model.readOnlyView.ListeEtudROVOImpl;
import model.readOnlyView.ListeEtudiantGroupeTdROVOImpl;
import model.readOnlyView.ListeGroupeTdTpROVOImpl;
import model.readOnlyView.ListeInsPedExonerationROVOImpl;
import model.readOnlyView.ListeInsPedIsAnnulDesistROVOImpl;
import model.readOnlyView.ListeInscAncROVOImpl;
import model.readOnlyView.ListeInscPedAnnulDesistROVOImpl;
import model.readOnlyView.ListePersAutDapROVOImpl;
import model.readOnlyView.ListePersAutDerogationROVOImpl;
import model.readOnlyView.ListePersAutNewROVOImpl;
import model.readOnlyView.ListePersAutValROVOImpl;
import model.readOnlyView.ListePersAutValRespROVOImpl;
import model.readOnlyView.ListePersAutoriseImpl;
import model.readOnlyView.ListePersSaisieResultatROVOImpl;
import model.readOnlyView.ListePersonnesROVOImpl;
import model.readOnlyView.ListeSuspensioneEtudROVOImpl;
import model.readOnlyView.NivFormAncInsROVOImpl;
import model.readOnlyView.NivFormAnnulSuspendROVOImpl;
import model.readOnlyView.NivFormAutoriseDapROVOImpl;
import model.readOnlyView.NivFormAutoriseROVOImpl;
import model.readOnlyView.NivFormAutoriseResROVOImpl;
import model.readOnlyView.NivFormHistoInscROVOImpl;
import model.readOnlyView.NivFormPreinsImpl;
import model.readOnlyView.NivFormSaisieResultROVOImpl;
import model.readOnlyView.NivFormationROVOImpl;
import model.readOnlyView.NiveauFormationSupROVOImpl;
import model.readOnlyView.NiveauInscPedROVOImpl;
import model.readOnlyView.NombrInscPedAnneeROVOImpl;
import model.readOnlyView.NumDiplomeInsAncROVOImpl;
import model.readOnlyView.OptionFormROVOImpl;
import model.readOnlyView.PaieEchelonEcolModFormROVOImpl;
import model.readOnlyView.PaieEtudGenROVOImpl;
import model.readOnlyView.PaieEtudInscPedROVOImpl;
import model.readOnlyView.PaiementEtudPreinsImpl;
import model.readOnlyView.PersDejaAutoriserROVOImpl;
import model.readOnlyView.PersNouvBachelierROVOImpl;
import model.readOnlyView.PersonneExisteROVOImpl;
import model.readOnlyView.PersonneLastInsertROVOImpl;
import model.readOnlyView.PersonneSearchSimpleImpl;
import model.readOnlyView.PersonnesROVOImpl;


import model.readOnlyView.ResponsableROVOImpl;
import model.readOnlyView.ResultParcoursSupROVOImpl;
import model.readOnlyView.ResultatAnnuelInscPedROVOImpl;
import model.readOnlyView.ResultatAnnuelROVOImpl;
import model.readOnlyView.ResultatInscPedSenROVOImpl;
import model.readOnlyView.ResultatUeFilUeSemROVOImpl;
import model.readOnlyView.SaisieResultModImpl;
import model.readOnlyView.SaisieResultatDetailPedROVOImpl;
import model.readOnlyView.SemRedoubleROVOImpl;
import model.readOnlyView.SemestreReDoubleROVOImpl;
import model.readOnlyView.StructureEtudBuROVOImpl;
import model.readOnlyView.UeInscImpl;
import model.readOnlyView.UtiInsplSemImpl;

import model.readOnlyView.common.TableTypePreinscrp;
import model.readOnlyView.lesUeInscPedSemUeROVOImpl;
import model.readOnlyView.listeEtudMobUeROVOImpl;
import model.readOnlyView.listeEtudMobiliteROVOImpl;
import model.readOnlyView.listeInsPedImpl;
import model.readOnlyView.listeUeValideImpl;
import model.readOnlyView.persEtudiantROVOImpl;
import model.readOnlyView.reInsResultUeImpl;
import model.readOnlyView.reInscResultSemROVOImpl;
import model.readOnlyView.reInscripAdminPedROVOImpl;
import model.readOnlyView.reInscripAdminROVO2Impl;
import model.readOnlyView.reInscripAdminROVOImpl;

import model.readOnlyView.reInscriptionResultROVOImpl;

import model.services.common.inscriptionApp;

//import model.updatableview.AutorisesVOImpl;
//import model.updatableview.DynamicVOImpl;
import model.updatableview.EudiantPersonneROVOImpl;

import model.updatableview.ExonerationVOImpl;
import model.updatableview.ResultatVOImpl;

import model.updatableview.common.TableEtudiantprov;
import model.updatableview.common.TableEtudiantsemestre;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DBTransactionImpl;
import oracle.jbo.server.ViewLinkImpl;


import oracle.jbo.server.ViewObjectImpl;

import oracle.jdbc.OracleTypes;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Nov 12 10:01:23 GMT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class inscriptionAppImpl extends ApplicationModuleImpl implements inscriptionApp {
    /**
     * This is the default constructor (do not remove).
     */
    public inscriptionAppImpl() {
    }


    /**
     * Container's getter for GradesVO1.
     * @return GradesVO1
     */
    public ViewObjectImpl getGrades() {
        return (ViewObjectImpl) findViewObject("Grades");
    }


    /**
     * Container's getter for BoursesVO1.
     * @return BoursesVO1
     */
    public ViewObjectImpl getBourses() {
        return (ViewObjectImpl) findViewObject("Bourses");
    }

    /**
     * Container's getter for OptionsVO1.
     * @return OptionsVO1
     */
    public ViewObjectImpl getOptions() {
        return (ViewObjectImpl) findViewObject("Options");
    }

    /**
     * Container's getter for FiliereUeSemstreEcVO1.
     * @return FiliereUeSemstreEcVO1
     */
    public ViewObjectImpl getFiliereUeSemstreEc() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemstreEc");
    }

    /**
     * Container's getter for FiliereUeSemstreVO1.
     * @return FiliereUeSemstreVO1
     */
    public ViewObjectImpl getFiliereUeSemstre() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemstre");
    }

    /**
     * Container's getter for BoursierLOV1.
     * @return BoursierLOV1
     */
    public BoursierLOVImpl getBoursierLOV() {
        return (BoursierLOVImpl) findViewObject("BoursierLOV");
    }

    /**
     * Container's getter for BoursesRecuLOV1.
     * @return BoursesRecuLOV1
     */
    public BoursesRecuLOVImpl getBoursesRecuLOV() {
        return (BoursesRecuLOVImpl) findViewObject("BoursesRecuLOV");
    }

    /**
     * Container's getter for FiliereUeSemstreEcVO1.
     * @return FiliereUeSemstreEcVO1
     */
    public ViewObjectImpl getFiliereUeSemstreEcVO1() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemstreEcVO1");
    }

    /**
     * Container's getter for FiliereUeSemTOFiliereUeSemEcVL1.
     * @return FiliereUeSemTOFiliereUeSemEcVL1
     */
    public ViewLinkImpl getFiliereUeSemTOFiliereUeSemEcVL1() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemTOFiliereUeSemEcVL1");
    }

    /**
     * Container's getter for PersonnesVO1.
     * @return PersonnesVO1
     */
    public ViewObjectImpl getPersonnes() {
        return (ViewObjectImpl) findViewObject("Personnes");
    }

    /**
     * Container's getter for EtudiantsVO1.
     * @return EtudiantsVO1
     */
    public ViewObjectImpl getEtudiants() {
        return (ViewObjectImpl) findViewObject("Etudiants");
    }
    
    public void getEtudiants(Long idpers){
        ViewObject vo = getEtudiants();
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("EtudiantsVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("idpers", idpers);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
       
    }
    public void getNouvBac(Long idpers){
        ViewObject vo = getEtudiants();
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("NouveauxBacheliersVOCriteria");
        VariableValueManager vvm =vc.ensureVariableManager();
        vvm.setVariableValue("idpers", idpers);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
       
    }    
    public Integer isEtudiant(Long id_pers) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_etudiant(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_pers);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isNouvBac(Long id_pers) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_bachelier(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_pers);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }


    public Integer isNumEtudiant(String num_etud) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.numEtudiant(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, num_etud);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isNumNouvBac(Long num_table, Long id_annee) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.numBachelier(?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, num_table);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }


    public String getNumEtudiant(Long id_pers) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.get_etudiant(?); end ;";
        String result = null;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_pers);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result =  cls.getString(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    
    public String getIdPersEtudiant(String num_etud) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.get_id_pers(?); end ;";
        String result = null;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, num_etud);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result =  cls.getString(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public String getIdPersCin(String cin) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.get_id_pers_cin(?); end ;";
        String result = null;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, cin);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result =  cls.getString(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public String getIdPersBac(Long num_table,Long id_annee) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.get_id_pers_bac(?,?); end ;";
        String result = null;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, num_table);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result =  cls.getString(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }


    public Integer isNumCin(String num_cin) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.numCin(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, num_cin);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
        
    public Integer nombrUeInsc(Long parcours,Long pers,Long semestre,Long annee,Long util) {
        
        //parcours,util,annee,semestre,pers
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.nombrUeInsc(?,?,?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(6, pers);
            cls.setLong(5, semestre);
            cls.setLong(4, annee);
            cls.setLong(3, util);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
        
    public Integer nombreResultSem(Long type_form,Long grade,Long id_etud) {
        
        //parcours,util,annee,semestre,pers
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.nombreResultSem(?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, type_form);
            cls.setLong(3, grade);
            cls.setLong(4, id_etud);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }                                               
                                               
    public Integer isInscrpAdmin(Long type_form,Long annee,Long grade,Long id_etud) {
        
        /*FUNCTION isInscrpAdmin(
          type_form inscriptions_admin.ID_TYPE_FORMATION%TYPE,
          annee inscriptions_admin.ID_ANNEES_UNIVERS%type,
          grade inscriptions_admin.ID_GRADE%type,
          id_etud inscriptions_admin.ID_ETUDIANT%type
        ) RETURN integer AS*/
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isInscrpAdmin(?,?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, type_form);
            cls.setLong(3, annee);
            cls.setLong(4, grade);
            cls.setLong(5, id_etud);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    } 
    
    
    public String getIdInscrpAdmin(Long type_form,Long annee,Long grade,Long id_etud) {
        
        /* FUNCTION getIdInscrpAdmin(
        type_form inscriptions_admin.ID_TYPE_FORMATION%TYPE,
        annee inscriptions_admin.ID_ANNEES_UNIVERS%type,
        grade inscriptions_admin.ID_GRADE%type,
        id_etud inscriptions_admin.ID_ETUDIANT%type
      ) RETURN inscriptions_admin.ID_INSCRIPTION_ADMIN%type AS*/
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.getIdInscrpAdmin(?,?,?,?); end ;";
        String result = null;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, type_form);
            cls.setLong(3, annee);
            cls.setLong(4, grade);
            cls.setLong(5, id_etud);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result =  cls.getString(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isResultSem(Long id_ins_ped_sem) {
        
        /*FUNCTION isResultSem(
          id_ins_ped_sem resultats_semestre.id_inscription_ped_semestre%TYPE
        ) RETURN integer AS*/
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isResultSem(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_ins_ped_sem);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }


    public Integer isInsPedSemUe(Long id_ins_ped_sem) {
        
        /*FUNCTION isInsPedSemUe(
        id_ins_ped_sem inscription_ped_sem_ue.id_inscription_ped_semestre%TYPE
      ) RETURN integer AS*/
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isInsPedSemUe(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_ins_ped_sem);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }    

    public Integer isUeResultSemUe(Long id_ins_ped_sem_ue) {
        
        /*FUNCTION isUeResultSemUe(
        id_ins_ped_sem_ue resultats_fil_ue_semestre.id_inscription_ped_sem_ue%TYPE
      ) RETURN integer AS*/
        //ViewObjectImpl oj =this.getInscPedSemUe();
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isUeResultSemUe(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_ins_ped_sem_ue);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isInsPedSem(Long id_ins_ped) {
        
        /*FUNCTION FUNCTION isInsPedSem(
        id_ins_ped inscription_ped_semestre.id_inscription_pedagogique%TYPE
      ) RETURN integer AS*/

        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isInsPedSem(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_ins_ped);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    public Integer isEtudEnRegleBu(Long id_struct,Long id_annee,Long id_etud) {
        
        //FUNCTION isEtudEnRegleBu(id_struct,id_annee,id_etud,en_regle) RETURN integer AS
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isEtudEnRegleBu(?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_struct);
            cls.setLong(3, id_annee);
            cls.setLong(4, id_etud);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    public Integer isAcquitePaiementEtud(Long id_ins_ped,Long id_annee,Long id_histo,Long valide) {
        
        //    function isAcquitePaiementEtud(id_ins_ped,id_annee,id_histo,valide) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.isAcquitePaiementEtud(?,?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_ins_ped);
            cls.setLong(3, id_annee);
            cls.setLong(4, id_histo);
            cls.setLong(5, valide);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    public Integer isEnRegleBu(Long id_etud ,Long id_annee ,Long hs) {
    
        //FUNCTION is_en_regle_bu(id_etud etudiants.id_etudiant,id_annee,hs) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_en_regle_bu(?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.setLong(3, id_annee);
            cls.setLong(4, hs);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    public Integer isApte(Long id_etud ,Long id_annee) {
        
        //FUNCTION is_apte(id_etud,id_annee) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_apte(?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    } 
    
    public Integer isResponsableEtud(Long id_etud) {
        
        //FUNCTION is_responsable_etud(id_etud) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_responsable_etud(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isInscPedVal(Long id_insc_ped) {
        
        //FUNCTION is_insc_ped_val(id_insc_ped inscriptions_pedagogiques.id_inscription_pedagogique%type ) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_insc_ped_val(?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_insc_ped);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    
    public Integer isEtudTic(Long id_etud ,Long id_annee) {
        
        //FUNCTION is_etud_tic(id_etud etudiants.id_etudiant%type,id_annee ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_etud_tic(?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    } 
    
    public Integer isPaiementEtud(Long id_insc_ped ,Long parcours,Long id_etud ,Long id_annee) {
        
        //FUNCTION is_paiement_etud(id_insc_ped inscriptions_pedagogiques.id_inscription_pedagogique%type,parcours NIVEAUX_FORMATION_PARCOURS.ID_NIVEAU_FORMATION_PARCOURS%TYPE,id_etud etudiants.id_etudiant%type,id_annee ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE ) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.is_paiement_etud(?,?,?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_insc_ped);
            cls.setLong(3, parcours);
            cls.setLong(4, id_etud);
            cls.setLong(5, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public RowSetIterator lesEtudInsc(Integer annee, Integer parcours, Integer session) {
        
        ViewObjectImpl delibVO = this.getListeProvEtudRO();
        delibVO.clearCache();
        
        DBTransactionImpl transaction = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            transaction.createCallableStatement(("BEGIN ?:=  testdelib.InscriptionGroupee(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, oracle.jdbc.internal.OracleTypes.ARRAY, "TABLE_ETUDIANTPROV");
            statement.setInt(2, annee);
            statement.setInt(3, parcours);
            statement.setInt(4, session);
            statement.execute();
            Integer count_ = transformArrayToListProv((java.sql.Array) statement.getObject(1)).size();System.out.println("DeliberationAnnuelle Début");
            System.out.println("count_ "+count_);
            for (int i = 0; i < count_;i++) {
                // Creates a row in ViewObject
                Row r = delibVO.createRow();
                // You can set attribute values in this new row
                System.out.println("etud  "+transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("IdEtud"));
                System.out.println("id_insc_ped  "+transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("IdInscPed"));
                r.setAttribute("IdEtud",
                               transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("IdEtud"));
                r.setAttribute("Numero",
                               transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("Numero"));
                r.setAttribute("PrenomNom",
                               transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("PrenomNom"));
                //java.util.Date utilDate = new java.util.Date(((Date)transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("DateNaiss")).getTime());
                //System.out.println("utilDate "+utilDate);
                r.setAttribute("DateNaiss",
                                transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("DateNaiss"));
                String ins = transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i).getAttribute("IdInscPed").toString();
                r.setAttribute("InscVal",isInscPedVal(Long.parseLong(ins)));
                r.setAttribute("EtudBu","1");
                r.setAttribute("EtudTic","1");
                r.setAttribute("EtudResp","1");
                r.setAttribute("EtudPaie","1");
                r.setAttribute("EtudCmp","1");
                                                                                                                                  
                /*r.setAttribute("Moyenne",
                               transformArrayToListProv((java.sql.Array) statement.getObject(1)).get(i)
                               .getAttribute("moyenne"));*/
                //Insert that row in ViewObject
                delibVO.insertRow(r);
            }
            RowSet rs = delibVO.getRowSet();
            RowSetIterator rsi = delibVO.getRowSetIterator();
            //System.out.println(rsi.getRowCount());
            while(rs.hasNext()){
                System.out.println("****************************************************");
                 System.out.println(rs.getCurrentRow().getAttribute("IdEtud")+
                      " "+rs.getCurrentRow().getAttribute("Numero") +
                      " "+rs.getCurrentRow().getAttribute("PrenomNom")+
                      " "+rs.getCurrentRow().getAttribute("DateNaiss")
                      //" "+rs.getCurrentRow().getAttribute("IdInscPed")
                    );
                System.out.println("****************************************************");
                rsi.next();
            }

            return delibVO.getRowSetIterator();
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException closeerr) {
                throw new JboException(closeerr);
            }
        }
    }

    private TableEtudiantprov transformEtudiantProvToDomain(java.sql.Struct rawObject) throws SQLException {
        TableEtudiantprov r_v = new TableEtudiantprov();
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            r_v.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return r_v;
    }

    private java.util.List<TableEtudiantprov> transformArrayToListProv(java.sql.Array array) throws SQLException {
        Object[] objectArray = (Object[]) array.getArray();
        java.util.List<TableEtudiantprov> table = new java.util.ArrayList<>();
        for (Object obj : objectArray) {
            java.sql.Struct struct = (java.sql.Struct) obj;
            table.add(transformEtudiantProvToDomain(struct));
        }
        return table;
    }    
    /**
     * Container's getter for NouveauxBacheliersVO1.
     * @return NouveauxBacheliersVO1
     */
    public ViewObjectImpl getNouveauxBacheliers() {
        return (ViewObjectImpl) findViewObject("NouveauxBacheliers");
    }

    /**
     * Container's getter for PersonnesROVO1.
     * @return PersonnesROVO1
     */
    public PersonnesROVOImpl getPersonnesROVO() {
        return (PersonnesROVOImpl) findViewObject("PersonnesROVO");
    }

    /**
     * Container's getter for AutorisationInscriptionROVO1.
     * @return AutorisationInscriptionROVO1
     */
    public AutorisationInscriptionROVOImpl getAutorisationInscriptionROVO() {
        return (AutorisationInscriptionROVOImpl) findViewObject("AutorisationInscriptionROVO");
    }

    /**
     * Container's getter for InscriptionsAdminVO1.
     * @return InscriptionsAdminVO1
     */
    public ViewObjectImpl getInscriptionsAdmin() {
        return (ViewObjectImpl) findViewObject("InscriptionsAdmin");
    }

    /**
     * Container's getter for InscriptionsPedagogiquesVO1.
     * @return InscriptionsPedagogiquesVO1
     */
    public ViewObjectImpl getInscriptionsPedagogiques() {
        return (ViewObjectImpl) findViewObject("InscriptionsPedagogiques");
    }

    /**
     * Container's getter for InscriptionPedSemestreVO1.
     * @return InscriptionPedSemestreVO1
     */
    public ViewObjectImpl getInscriptionPedSemestre() {
        return (ViewObjectImpl) findViewObject("InscriptionPedSemestre");
    }

    /**
     * Container's getter for InscriptionPedSemUeVO1.
     * @return InscriptionPedSemUeVO1
     */
    public ViewObjectImpl getInscriptionPedSemUe() {
        return (ViewObjectImpl) findViewObject("InscriptionPedSemUe");
    }

    /**
     * Container's getter for InscPedSemUe1.
     * @return InscPedSemUe1
     */
    public InscPedSemUeImpl getInscPedSemUe() {
        return (InscPedSemUeImpl) findViewObject("InscPedSemUe");
    }

    /**
     * Container's getter for InscrAdminPedROVO1.
     * @return InscrAdminPedROVO1
     */
    public InscrAdminPedROVOImpl getInscrAdminPedRO() {
        return (InscrAdminPedROVOImpl) findViewObject("InscrAdminPedRO");
    }

    /**
     * Container's getter for UtiInsplSem2.
     * @return UtiInsplSem2
     */
    public UtiInsplSemImpl getUtiInsplSem() {
        return (UtiInsplSemImpl) findViewObject("UtiInsplSem");
    }

    /**
     * Container's getter for reInscripAdminROVO1.
     * @return reInscripAdminROVO1
     */
    public reInscripAdminROVOImpl getreInscripAdminRO() {
        return (reInscripAdminROVOImpl) findViewObject("reInscripAdminRO");
    }

    /**
     * Container's getter for reInscResultSemROVO1.
     * @return reInscResultSemROVO1
     */
    public reInscResultSemROVOImpl getreInscResultSemRO() {
        return (reInscResultSemROVOImpl) findViewObject("reInscResultSemRO");
    }

    /**
     * Container's getter for reInscripAdminROVO2_1.
     * @return reInscripAdminROVO2_1
     */
    public reInscripAdminROVO2Impl getreInscripAdminROVO2() {
        return (reInscripAdminROVO2Impl) findViewObject("reInscripAdminROVO2");
    }

    /**
     * Container's getter for reInsResultUe1.
     * @return reInsResultUe1
     */
    public reInsResultUeImpl getreInsResultUe() {
        return (reInsResultUeImpl) findViewObject("reInsResultUe");
    }

    /**
     * Container's getter for InsPedSemUe2_1.
     * @return InsPedSemUe2_1
     */
    public InsPedSemUe2Impl getInsPedSemUe2() {
        return (InsPedSemUe2Impl) findViewObject("InsPedSemUe2");
    }

    /**
     * Container's getter for InscriptionPedSemUeROVO1.
     * @return InscriptionPedSemUeROVO1
     */
    public InscriptionPedSemUeROVOImpl getInscriptionPedSemUeRO() {
        return (InscriptionPedSemUeROVOImpl) findViewObject("InscriptionPedSemUeRO");
    }

    /**
     * Container's getter for reInscriptionResultROVO1.
     * @return reInscriptionResultROVO1
     */
    public reInscriptionResultROVOImpl getreInscriptionResultRO() {
        return (reInscriptionResultROVOImpl) findViewObject("reInscriptionResultRO");
    }

    /**
     * Container's getter for reInscripAdminPedROVO1.
     * @return reInscripAdminPedROVO1
     */
    public reInscripAdminPedROVOImpl getreInscripAdminPedRO() {
        return (reInscripAdminPedROVOImpl) findViewObject("reInscripAdminPedRO");
    }

    /**
     * Container's getter for listeInsPed1.
     * @return listeInsPed1
     */
    public listeInsPedImpl getlisteInsPed() {
        return (listeInsPedImpl) findViewObject("listeInsPed");
    }

    /**
     * Container's getter for InscriptPedSemROVO1.
     * @return InscriptPedSemROVO1
     */
    public InscriptPedSemROVOImpl getInscriptPedSemRO() {
        return (InscriptPedSemROVOImpl) findViewObject("InscriptPedSemRO");
    }

    /**
     * Container's getter for InscriptPedSemUeROVO1.
     * @return InscriptPedSemUeROVO1
     */
    public InscriptPedSemUeROVOImpl getInscriptPedSemUeRO() {
        return (InscriptPedSemUeROVOImpl) findViewObject("InscriptPedSemUeRO");
    }

    /**
     * Container's getter for NivFormAutoriseROVO1.
     * @return NivFormAutoriseROVO1
     */
    public NivFormAutoriseROVOImpl getNivFormAutoriseRO() {
        return (NivFormAutoriseROVOImpl) findViewObject("NivFormAutoriseRO");
    }

    /**
     * Container's getter for AttributSearchROVO1.
     * @return AttributSearchROVO1
     */
    public AttributSearchROVOImpl getAttributSearchRO() {
        return (AttributSearchROVOImpl) findViewObject("AttributSearchRO");
    }

    /**
     * Container's getter for ListePersAutorise1.
     * @return ListePersAutorise1
     */
    public ListePersAutoriseImpl getListePersAutorise() {
        return (ListePersAutoriseImpl) findViewObject("ListePersAutorise");
    }

    /**
     * Container's getter for ListePersonnesROVO1.
     * @return ListePersonnesROVO1
     */
    public ListePersonnesROVOImpl getListePersonnesRO() {
        return (ListePersonnesROVOImpl) findViewObject("ListePersonnesRO");
    }

    /**
     * Container's getter for AttrSearchPers1.
     * @return AttrSearchPers1
     */
    public AttrSearchPersImpl getAttrSearchPers() {
        return (AttrSearchPersImpl) findViewObject("AttrSearchPers");
    }


    /**
     * Container's getter for AttrSearchPersAut1.
     * @return AttrSearchPersAut1
     */
    public AttrSearchPersAutImpl getAttrSearchPersAut() {
        return (AttrSearchPersAutImpl) findViewObject("AttrSearchPersAut");
    }

    /**
     * Container's getter for GroupeTdTpParcours1.
     * @return GroupeTdTpParcours1
     */
    public GroupeTdTpParcoursImpl getGroupeTdTpParcours() {
        return (GroupeTdTpParcoursImpl) findViewObject("GroupeTdTpParcours");
    }

    /**
     * Container's getter for GroupeTpTdVO1.
     * @return GroupeTpTdVO1
     */
    public ViewObjectImpl getGroupeTpTd() {
        return (ViewObjectImpl) findViewObject("GroupeTpTd");
    }

    /**
     * Container's getter for AttrParcours1.
     * @return AttrParcours1
     */
    public AttrParcoursImpl getAttrParcours() {
        return (AttrParcoursImpl) findViewObject("AttrParcours");
    }

    /**
     * Container's getter for GroupeParcSemestre2.
     * @return GroupeParcSemestre2
     */
    public GroupeParcSemestreImpl getGroupeParcSemestre() {
        return (GroupeParcSemestreImpl) findViewObject("GroupeParcSemestre");
    }

    /**
     * Container's getter for GroupeTdTpParcToGroupeParcSemVL2.
     * @return GroupeTdTpParcToGroupeParcSemVL2
     */
    public ViewLinkImpl getGroupeTdTpParcToGroupeParcSemVL2() {
        return (ViewLinkImpl) findViewLink("GroupeTdTpParcToGroupeParcSemVL2");
    }

    /**
     * Container's getter for ListeGroupeTdTpROVO1.
     * @return ListeGroupeTdTpROVO1
     */
    public ListeGroupeTdTpROVOImpl getListeGroupeTdTpRO() {
        return (ListeGroupeTdTpROVOImpl) findViewObject("ListeGroupeTdTpRO");
    }

    /**
     * Container's getter for AutorisationValideROVO1.
     * @return AutorisationValideROVO1
     */
    public AutorisationValideROVOImpl getAutorisationValideRO() {
        return (AutorisationValideROVOImpl) findViewObject("AutorisationValideRO");
    }

    /**
     * Container's getter for NivFormAutToAutoValideVL1.
     * @return NivFormAutToAutoValideVL1
     */
    public ViewLinkImpl getNivFormAutToAutoValideVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToAutoValideVL1");
    }
    
    public void fingAndUpdateAutorise(Long id_aut,Integer valide){
        
        ViewObjectImpl voImp;
        voImp = this.getAutorises();
        Key key = new Key (new Object [] {id_aut});
        RowSetIterator rsi = voImp.createRowSetIterator(null);
        Row row = rsi.findByKey(key,1)[0];
        row.setAttribute("Valide", valide);
        this.getDBTransaction().commit();
    }

    public void fingAndUpdateInsPed(Long id_ins_ped,Long etat){
        ViewObjectImpl voImp = this.getInscriptionsPedagogiques();
        Key key=new Key (new Object [] {id_ins_ped});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("EtatInscrEtatInscrId", etat);
        this.getDBTransaction().commit();
    }
    public void findAndUpdateInsPed(Long id_ins_ped,Long etat){
        ViewObjectImpl voImp = this.getInscriptionsPedagogiques();
        Key key=new Key (new Object [] {id_ins_ped});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("EtatInscrEtatInscrId", etat);
        this.getDBTransaction().commit();
    }
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateEtatInsc(Long id_ins_ped, Long etat) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Update inscriptions_pedagogiques SET etat_inscr_etat_inscr_id=? WHERE id_inscription_pedagogique=? ;  commit; end ;";

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(1, etat);
            cls.setLong(2, id_ins_ped);
            cls.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (cls != null)
                try {
                    cls.close();
                } catch (Exception c) {
                }
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception c) {
                }
        }
    }
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateSuspension(Long id_susp, Long levee, Long util) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Update suspension SET levee=?, uti_levee=? WHERE id_suspension=? ; commit; end ;";

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(1, levee);
            cls.setLong(2, util);
            cls.setLong(3, id_susp);   
            cls.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (cls != null)
                try {
                    cls.close();
                } catch (Exception c) {
                }
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception c) {
                }
        }
    }
    public void findAndUpdateResultat(Long id_resultat,Long insc_ped_sup){
        ViewObjectImpl voImp = this.getResultat();
        Key key=new Key (new Object [] {id_resultat});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("IdInscriptionPedagogiqueSup", insc_ped_sup);
        this.getDBTransaction().commit();
    }
    public void findAndUpdateSusp(Long id_susp,Long levee, Long util){
        ViewObjectImpl voImp = this.getSuspension();
        Key key=new Key (new Object [] {id_susp});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("Levee", levee);//
        row.setAttribute("UtiLevee", util);
        this.getDBTransaction().commit();
    }    
    public void findAndPaieExoneration(Long id_insc_ped,Long droit_insc_adm,Long droit_insc_ped,Long cout_form){
        ViewObjectImpl voImp = this.getInscriptionsPedagogiques();
        Key key=new Key (new Object [] {id_insc_ped});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("DroitInsAdm", droit_insc_adm);
        row.setAttribute("DroitInsPed", droit_insc_ped);
        row.setAttribute("CoutFormation", cout_form);
        //this.getTransaction().commit();
        this.getDBTransaction().commit();
    }
    
    public void findAndUpdateInsPedVal(Long id_insc_ped_sem,Integer insc_ped_val){
        ViewObjectImpl voImp = this.getInscriptionPedSemestre();
        Key key=new Key (new Object [] {id_insc_ped_sem});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("InsPedValide", insc_ped_val);
        this.getDBTransaction().commit();
    }
    public void findAndUpdateNationalite(Long id_pers,Long id_nouv_nat){
        ViewObjectImpl voImp = this.getPersonnes();
        Key key=new Key (new Object [] {id_pers});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("IdPaysNationalite", id_nouv_nat);
        this.getDBTransaction().commit();
    }
    
    public void findAndUpdateEtud(Long id_etud,Long mobilite){
        ViewObjectImpl voImp = this.getEtudiants();
        Key key=new Key (new Object [] {id_etud});
        RowSetIterator rsi=voImp.createRowSetIterator(null);
        Row row= rsi.findByKey(key,1)[0];
        row.setAttribute("Mobilite", mobilite);
        this.getDBTransaction().commit();
    }
    /**
     * Container's getter for ListePersAutValROVO1.
     * @return ListePersAutValROVO1
     */
    public ListePersAutValROVOImpl getListePersAutValRO() {
        return (ListePersAutValROVOImpl) findViewObject("ListePersAutValRO");
    }

    /**
     * Container's getter for NivFormAutToListePersAutValVL1.
     * @return NivFormAutToListePersAutValVL1
     */
    public ViewLinkImpl getNivFormAutToListePersAutValVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToListePersAutValVL1");
    }

    /**
     * Container's getter for ListeEtudPasGroupeTdROVO1.
     * @return ListeEtudPasGroupeTdROVO1
     */
    public ListeEtudPasGroupeTdROVOImpl getListeEtudPasGroupeTdRO() {
        return (ListeEtudPasGroupeTdROVOImpl) findViewObject("ListeEtudPasGroupeTdRO");
    }
    
    public void filterEtudiantsTd(String searchString,String selectedList){
        ViewObjectImpl list_etudVo = getListeEtudPasGroupeTdRO();
        String whereClause="";
        whereClause="upper(NUMERO || ' ' || NOM || ' ' || PRENOMS) like upper('%"+searchString+"%')";
        if(!"".equals(selectedList))
            whereClause=whereClause+" OR "+ "ID_INSCRIPTION_PEDAGOGIQUE in ("+selectedList+")";
        if(!"".equals(whereClause))
            list_etudVo.setWhereClause(whereClause);
        list_etudVo.executeQuery();
        
    }

    /**
     * Container's getter for GroupeTpTdEtudiantsVO1.
     * @return GroupeTpTdEtudiantsVO1
     */
    public ViewObjectImpl getGroupeTpTdEtudiants() {
        return (ViewObjectImpl) findViewObject("GroupeTpTdEtudiants");
    }

    /**
     * Container's getter for ListeEtudiantGroupeTdROVO1.
     * @return ListeEtudiantGroupeTdROVO1
     */
    public ListeEtudiantGroupeTdROVOImpl getListeEtudiantGroupeTdRO() {
        return (ListeEtudiantGroupeTdROVOImpl) findViewObject("ListeEtudiantGroupeTdRO");
    }

    /**
     * Container's getter for NivFormAutoriseResROVO1.
     * @return NivFormAutoriseResROVO1
     */
    public NivFormAutoriseResROVOImpl getNivFormAutoriseResRO() {
        return (NivFormAutoriseResROVOImpl) findViewObject("NivFormAutoriseResRO");
    }

    /**
     * Container's getter for AutorisationValideRespROVO1.
     * @return AutorisationValideRespROVO1
     */
    public AutorisationValideRespROVOImpl getAutorisationValideRespRO() {
        return (AutorisationValideRespROVOImpl) findViewObject("AutorisationValideRespRO");
    }

    /**
     * Container's getter for NivFormAutResToAutValRespVL1.
     * @return NivFormAutResToAutValRespVL1
     */
    public ViewLinkImpl getNivFormAutResToAutValRespVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutResToAutValRespVL1");
    }

    /**
     * Container's getter for ListePersAutValRespROVO1.
     * @return ListePersAutValRespROVO1
     */
    public ListePersAutValRespROVOImpl getListePersAutValRespRO() {
        return (ListePersAutValRespROVOImpl) findViewObject("ListePersAutValRespRO");
    }

    /**
     * Container's getter for NivFormAutResToListPersAutValRespVL1.
     * @return NivFormAutResToListPersAutValRespVL1
     */
    public ViewLinkImpl getNivFormAutResToListPersAutValRespVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutResToListPersAutValRespVL1");
    }

    /**
     * Container's getter for NivFormAutoriseDapROVO1.
     * @return NivFormAutoriseDapROVO1
     */
    public NivFormAutoriseDapROVOImpl getNivFormAutoriseDapRO() {
        return (NivFormAutoriseDapROVOImpl) findViewObject("NivFormAutoriseDapRO");
    }

    /**
     * Container's getter for AutorisationValideDapROVO1.
     * @return AutorisationValideDapROVO1
     */
    public AutorisationValideDapROVOImpl getAutorisationValideDapRO() {
        return (AutorisationValideDapROVOImpl) findViewObject("AutorisationValideDapRO");
    }

    /**
     * Container's getter for NivFormAutDapToAutorisationValDap1.
     * @return NivFormAutDapToAutorisationValDap1
     */
    public ViewLinkImpl getNivFormAutDapToAutorisationValDap1() {
        return (ViewLinkImpl) findViewLink("NivFormAutDapToAutorisationValDap1");
    }

    /**
     * Container's getter for ListePersAutDapROVO1.
     * @return ListePersAutDapROVO1
     */
    public ListePersAutDapROVOImpl getListePersAutDapRO() {
        return (ListePersAutDapROVOImpl) findViewObject("ListePersAutDapRO");
    }

    /**
     * Container's getter for NivFormAutDapToListePersValDap1.
     * @return NivFormAutDapToListePersValDap1
     */
    public ViewLinkImpl getNivFormAutDapToListePersValDap1() {
        return (ViewLinkImpl) findViewLink("NivFormAutDapToListePersValDap1");
    }

    /**
     * Container's getter for NivFormationROVO1.
     * @return NivFormationROVO1
     */
    public NivFormationROVOImpl getNivFormationRO() {
        return (NivFormationROVOImpl) findViewObject("NivFormationRO");
    }

    /**
     * Container's getter for ListeEtudROVO1.
     * @return ListeEtudROVO1
     */
    public ListeEtudROVOImpl getListeEtudRO() {
        return (ListeEtudROVOImpl) findViewObject("ListeEtudRO");
    }

    /**
     * Container's getter for NivFormationToListeEtudVL1.
     * @return NivFormationToListeEtudVL1
     */
    public ViewLinkImpl getNivFormationToListeEtudVL1() {
        return (ViewLinkImpl) findViewLink("NivFormationToListeEtudVL1");
    }
    
    public java.util.List<TableTypePreinscrp> getEudPreinscrption(Integer annee, Integer parcours) {            
        //ViewObjectImpl delibVO = this.getEtudiantSemestreDeliberationAn();  
        //Get current data RowSetIterator
        //RowSetIterator rsi = delibVO.getRowSetIterator();
        //System.out.println(rsi.getRowCount());
        DBTransactionImpl transaction = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            transaction.createCallableStatement(("BEGIN ?:=  testdelib.getEtudPreinscrp(?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_TYPE_PREINSCRP");
            statement.setInt(2, annee);
            statement.setInt(3, parcours);
            statement.execute();
            Integer count_ = transformArrayToList((java.sql.Array) statement.getObject(1)).size();
            System.out.println("count"+count_);
            /*for (int i = 0; i < count_; i++) {
                

            }*/

             
            return transformArrayToList((java.sql.Array) statement.getObject(1));
        //return rsi;
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException closeerr) {
                throw new JboException(closeerr);
            }
        }
    }
    private TableTypePreinscrp transformEtudiantsemestreToDomain(java.sql.Struct rawObject) throws SQLException {
        TableTypePreinscrp r_v = new TableTypePreinscrp();
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            r_v.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return r_v;
    }
    private java.util.List<TableTypePreinscrp> transformArrayToList(java.sql.Array array) throws SQLException {
        Object[] objectArray = (Object[]) array.getArray();
        java.util.List<TableTypePreinscrp> table = new java.util.ArrayList<>();       
        for (Object obj : objectArray) {
            java.sql.Struct struct = (java.sql.Struct) obj;
            table.add(transformEtudiantsemestreToDomain(struct));
        }
        return table;
    }

    /**
     * Container's getter for ListeEtudNotPreInsROVO1.
     * @return ListeEtudNotPreInsROVO1
     */
    public ListeEtudNotPreInsROVOImpl getListeEtudNotPreInsRO() {
        return (ListeEtudNotPreInsROVOImpl) findViewObject("ListeEtudNotPreInsRO");
    }

    /**
     * Container's getter for NivFormmationToListeEtudNotPreInsVL1.
     * @return NivFormmationToListeEtudNotPreInsVL1
     */
    public ViewLinkImpl getNivFormmationToListeEtudNotPreInsVL1() {
        return (ViewLinkImpl) findViewLink("NivFormmationToListeEtudNotPreInsVL1");
    }

    /**
     * Container's getter for DiplomeEtudiantGradeROVO1.
     * @return DiplomeEtudiantGradeROVO1
     */
    public DiplomeEtudiantGradeROVOImpl getDiplomeEtudiantGradeRO() {
        return (DiplomeEtudiantGradeROVOImpl) findViewObject("DiplomeEtudiantGradeRO");
    }


    /**
     * Container's getter for DiplomesEtudiantsVO1.
     * @return DiplomesEtudiantsVO1
     */
    public ViewObjectImpl getDiplomesEtudiants() {
        return (ViewObjectImpl) findViewObject("DiplomesEtudiants");
    }

    /**
     * Container's getter for EudiantPersonneROVO1.
     * @return EudiantPersonneROVO1
     */
    public EudiantPersonneROVOImpl getEudiantPersonneRO() {
        return (EudiantPersonneROVOImpl) findViewObject("EudiantPersonneRO");
    }

    /**
     * Container's getter for AttEnregistrementDiplROVO1.
     * @return AttEnregistrementDiplROVO1
     */
    public AttEnregistrementDiplROVOImpl getAttEnregistrementDiplRO() {
        return (AttEnregistrementDiplROVOImpl) findViewObject("AttEnregistrementDiplRO");
    }

    /**
     * Container's getter for HistResultAncienROVO1.
     * @return HistResultAncienROVO1
     */
    public HistResultAncienROVOImpl getHistResultAncienRO() {
        return (HistResultAncienROVOImpl) findViewObject("HistResultAncienRO");
    }


    /**
     * Container's getter for AttrResulancienROVO1.
     * @return AttrResulancienROVO1
     */
    public AttrResulancienROVOImpl getAttrResulancienRO() {
        return (AttrResulancienROVOImpl) findViewObject("AttrResulancienRO");
    }

    /**
     * Container's getter for EtudiantAncienVO1.
     * @return EtudiantAncienVO1
     */
    public ViewObjectImpl getEtudiantAncien() {
        return (ViewObjectImpl) findViewObject("EtudiantAncien");
    }

    /**
     * Container's getter for AttrSearchNumDiplomeROVO1.
     * @return AttrSearchNumDiplomeROVO1
     */
    public AttrSearchNumDiplomeROVOImpl getAttrSearchNumDiplomeRO() {
        return (AttrSearchNumDiplomeROVOImpl) findViewObject("AttrSearchNumDiplomeRO");
    }

    /**
     * Container's getter for NumeroDiplomeVO1.
     * @return NumeroDiplomeVO1
     */
    public ViewObjectImpl getNumeroDiplome() {
        return (ViewObjectImpl) findViewObject("NumeroDiplome");
    }

    /**
     * Container's getter for EtudAcnienROVO1.
     * @return EtudAcnienROVO1
     */
    public EtudAcnienROVOImpl getEtudAcnienRO() {
        return (EtudAcnienROVOImpl) findViewObject("EtudAcnienRO");
    }

    /**
     * Container's getter for HistStructAncInscROVO1.
     * @return HistStructAncInscROVO1
     */
    public HistStructAncInscROVOImpl getHistStructAncInscRO() {
        return (HistStructAncInscROVOImpl) findViewObject("HistStructAncInscRO");
    }


    /**
     * Container's getter for AnneeUnivAnscInscROVO1.
     * @return AnneeUnivAnscInscROVO1
     */
    public AnneeUnivAnscInscROVOImpl getAnneeUnivAnscInscRO() {
        return (AnneeUnivAnscInscROVOImpl) findViewObject("AnneeUnivAnscInscRO");
    }

    /**
     * Container's getter for HistStructToAnneeUnivAnsInscVL1.
     * @return HistStructToAnneeUnivAnsInscVL1
     */
    public ViewLinkImpl getHistStructToAnneeUnivAnsInscVL1() {
        return (ViewLinkImpl) findViewLink("HistStructToAnneeUnivAnsInscVL1");
    }

    /**
     * Container's getter for NivFormAncInsROVO1.
     * @return NivFormAncInsROVO1
     */
    public NivFormAncInsROVOImpl getNivFormAncInsRO() {
        return (NivFormAncInsROVOImpl) findViewObject("NivFormAncInsRO");
    }

    /**
     * Container's getter for AnneeUnivNivFormAncInscVL1.
     * @return AnneeUnivNivFormAncInscVL1
     */
    public ViewLinkImpl getAnneeUnivNivFormAncInscVL1() {
        return (ViewLinkImpl) findViewLink("AnneeUnivNivFormAncInscVL1");
    }

    /**
     * Container's getter for InscriptionAncienneVO1.
     * @return InscriptionAncienneVO1
     */
    public ViewObjectImpl getInscriptionAncienne() {
        return (ViewObjectImpl) findViewObject("InscriptionAncienne");
    }

    /**
     * Container's getter for NivFormAncInsToInscAncienVL1.
     * @return NivFormAncInsToInscAncienVL1
     */
    public ViewLinkImpl getNivFormAncInsToInscAncienVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAncInsToInscAncienVL1");
    }

    /**
     * Container's getter for NumDiplomeInsAncROVO1.
     * @return NumDiplomeInsAncROVO1
     */
    public NumDiplomeInsAncROVOImpl getNumDiplomeInsAncRO() {
        return (NumDiplomeInsAncROVOImpl) findViewObject("NumDiplomeInsAncRO");
    }

    /**
     * Container's getter for ListeInscAncROVO1.
     * @return ListeInscAncROVO1
     */
    public ListeInscAncROVOImpl getListeInscAncRO() {
        return (ListeInscAncROVOImpl) findViewObject("ListeInscAncRO");
    }

    /**
     * Container's getter for NivFormAncInsROToListeInscAncROVL1.
     * @return NivFormAncInsROToListeInscAncROVL1
     */
    public ViewLinkImpl getNivFormAncInsROToListeInscAncROVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAncInsROToListeInscAncROVL1");
    }

    /**
     * Container's getter for NivFormHistoInscROVO1.
     * @return NivFormHistoInscROVO1
     */
    public NivFormHistoInscROVOImpl getNivFormHistoInscRO() {
        return (NivFormHistoInscROVOImpl) findViewObject("NivFormHistoInscRO");
    }


    /**
     * Container's getter for StructureEtudBuROVO1.
     * @return StructureEtudBuROVO1
     */
    public StructureEtudBuROVOImpl getStructureEtudBuRO() {
        return (StructureEtudBuROVOImpl) findViewObject("StructureEtudBuRO");
    }

    /**
     * Container's getter for AnneeUniverROVO1.
     * @return AnneeUniverROVO1
     */
    public AnneeUniverROVOImpl getAnneeUniverRO() {
        return (AnneeUniverROVOImpl) findViewObject("AnneeUniverRO");
    }

    /**
     * Container's getter for EtudiantBuROVO1.
     * @return EtudiantBuROVO1
     */
    public EtudiantBuROVOImpl getEtudiantBuRO() {
        return (EtudiantBuROVOImpl) findViewObject("EtudiantBuRO");
    }

    /**
     * Container's getter for StructEtudBuROToEtudiantBuROVL1.
     * @return StructEtudBuROToEtudiantBuROVL1
     */
    public ViewLinkImpl getStructEtudBuROToEtudiantBuROVL1() {
        return (ViewLinkImpl) findViewLink("StructEtudBuROToEtudiantBuROVL1");
    }

    /**
     * Container's getter for EtudiantBuVO1.
     * @return EtudiantBuVO1
     */
    public ViewObjectImpl getEtudiantBu() {
        return (ViewObjectImpl) findViewObject("EtudiantBu");
    }

    /**
     * Container's getter for HistoPaiementEtudROVO1.
     * @return HistoPaiementEtudROVO1
     */
    public HistoPaiementEtudROVOImpl getHistoPaiementEtudRO() {
        return (HistoPaiementEtudROVOImpl) findViewObject("HistoPaiementEtudRO");
    }

    /**
     * Container's getter for FormPaiementEtudROVO1.
     * @return FormPaiementEtudROVO1
     */
    public FormPaiementEtudROVOImpl getFormPaiementEtudRO() {
        return (FormPaiementEtudROVOImpl) findViewObject("FormPaiementEtudRO");
    }

    /**
     * Container's getter for HistoPaiEtudROToFormPaiEtudROVL1.
     * @return HistoPaiEtudROToFormPaiEtudROVL1
     */
    public ViewLinkImpl getHistoPaiEtudROToFormPaiEtudROVL1() {
        return (ViewLinkImpl) findViewLink("HistoPaiEtudROToFormPaiEtudROVL1");
    }

    /**
     * Container's getter for PaiementEtudiantVO1.
     * @return PaiementEtudiantVO1
     */
    public ViewObjectImpl getPaiementEtudiant() {
        return (ViewObjectImpl) findViewObject("PaiementEtudiant");
    }

    /**
     * Container's getter for persEtudiantROVO1.
     * @return persEtudiantROVO1
     */
    public persEtudiantROVOImpl getpersEtudiantRO() {
        return (persEtudiantROVOImpl) findViewObject("persEtudiantRO");
    }

    /**
     * Container's getter for PersNouvBachelierROVO1.
     * @return PersNouvBachelierROVO1
     */
    public PersNouvBachelierROVOImpl getPersNouvBachelierRO() {
        return (PersNouvBachelierROVOImpl) findViewObject("PersNouvBachelierRO");
    }

    /**
     * Container's getter for NivFormPreins1.
     * @return NivFormPreins1
     */
    public NivFormPreinsImpl getNivFormPreins() {
        return (NivFormPreinsImpl) findViewObject("NivFormPreins");
    }

    /**
     * Container's getter for NivFormToNivFormPreinsVL1.
     * @return NivFormToNivFormPreinsVL1
     */
    public ViewLinkImpl getNivFormToNivFormPreinsVL1() {
        return (ViewLinkImpl) findViewLink("NivFormToNivFormPreinsVL1");
    }

    /**
     * Container's getter for AutoInscPreinsROVO1.
     * @return AutoInscPreinsROVO1
     */
    public AutoInscPreinsROVOImpl getAutoInscPreinsRO() {
        return (AutoInscPreinsROVOImpl) findViewObject("AutoInscPreinsRO");
    }

    /**
     * Container's getter for EtudBuPreinsROVO1.
     * @return EtudBuPreinsROVO1
     */
    public EtudBuPreinsROVOImpl getEtudBuPreinsRO() {
        return (EtudBuPreinsROVOImpl) findViewObject("EtudBuPreinsRO");
    }

    /**
     * Container's getter for AptePreins1.
     * @return AptePreins1
     */
    public AptePreinsImpl getAptePreins() {
        return (AptePreinsImpl) findViewObject("AptePreins");
    }

    /**
     * Container's getter for PaiementEtudPreins1.
     * @return PaiementEtudPreins1
     */
    public PaiementEtudPreinsImpl getPaiementEtudPreins() {
        return (PaiementEtudPreinsImpl) findViewObject("PaiementEtudPreins");
    }
    public Integer getIdAnonymat(Long parcours, Long anne,Long semestre, Long sessions) {
        //FUNCTION getIdAnonymat(parcours,anne,semestre,sessions)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  eval.getIdAnonymat(?,?,?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, anne);
            cls.setLong(4, semestre);
            cls.setLong(5, sessions);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        } finally {
            if (cls != null)
                try {
                    cls.close();
                } catch (Exception c) {
                }
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception c) {
                }
        }
        return result;
    }
    public Integer getNbreInsPed(Long id_etud ,Long id_annee) {
    
        //FUNCTION is_en_regle_bu(id_etud etudiants.id_etudiant,id_annee,hs) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  inscription.getNbreInsPed(?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }

    public Integer getNiveau(Long id_etud ,Long id_annee) {
    
        //FUNCTION is_en_regle_bu(id_etud etudiants.id_etudiant,id_annee,hs) return integer
        
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  inscription.getNiveau(?,?); end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_etud);
            cls.setLong(3, id_annee);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    public Integer next_seq() {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  testdelib.next_seq; end ;";
        Integer result = 0;
        
        try{
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result =  cls.getInt(1);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally{
            if(cls!=null)
                try{cls.close();} catch(Exception c){}
            if(rs!=null)
                try{rs.close();} catch(Exception c){}    
        }
        return result;
    }
    /**
     * Container's getter for InscInfoGlobleForm1.
     * @return InscInfoGlobleForm1
     */
    public InscInfoGlobleFormImpl getInscInfoGlobleForm() {
        return (InscInfoGlobleFormImpl) findViewObject("InscInfoGlobleForm");
    }

    /**
     * Container's getter for InscDoubleROVO1.
     * @return InscDoubleROVO1
     */
    public InscDoubleROVOImpl getInscDoubleRO() {
        return (InscDoubleROVOImpl) findViewObject("InscDoubleRO");
    }

    /**
     * Container's getter for InscNombreMaxiROVO1.
     * @return InscNombreMaxiROVO1
     */
    public InscNombreMaxiROVOImpl getInscNombreMaxiRO() {
        return (InscNombreMaxiROVOImpl) findViewObject("InscNombreMaxiRO");
    }

    /**
     * Container's getter for AutorisesVO1.
     * @return AutorisesVO1
     */
    public ViewObjectImpl getAutorises() {
        return (ViewObjectImpl) findViewObject("Autorises");
    }

    /**
     * Container's getter for DroitNiveauPaysROVO1.
     * @return DroitNiveauPaysROVO1
     */
    public DroitNiveauPaysROVOImpl getDroitNiveauPaysRO() {
        return (DroitNiveauPaysROVOImpl) findViewObject("DroitNiveauPaysRO");
    }

    /**
     * Container's getter for EtudiantExcluSuspenduROVO1.
     * @return EtudiantExcluSuspenduROVO1
     */
    public EtudiantExcluSuspenduROVOImpl getEtudiantExcluSuspenduRO() {
        return (EtudiantExcluSuspenduROVOImpl) findViewObject("EtudiantExcluSuspenduRO");
    }

    /**
     * Container's getter for EtudiantResultatCycleROVO1.
     * @return EtudiantResultatCycleROVO1
     */
    public EtudiantResultatCycleROVOImpl getEtudiantResultatCycleRO() {
        return (EtudiantResultatCycleROVOImpl) findViewObject("EtudiantResultatCycleRO");
    }

    /**
     * Container's getter for ListePersAutNewROVO1.
     * @return ListePersAutNewROVO1
     */
    public ListePersAutNewROVOImpl getListePersAutNewRO() {
        return (ListePersAutNewROVOImpl) findViewObject("ListePersAutNewRO");
    }

    /**
     * Container's getter for NivFormAutToListePersAutNewVL1.
     * @return NivFormAutToListePersAutNewVL1
     */
    public ViewLinkImpl getNivFormAutToListePersAutNewVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToListePersAutNewVL1");
    }

    /**
     * Container's getter for ListePersonnesROVO1.
     * @return ListePersonnesROVO1
     */
    public ListePersonnesROVOImpl getListePersonnesROVO() {
        return (ListePersonnesROVOImpl) findViewObject("ListePersonnesROVO");
    }

    /**
     * Container's getter for NivFormAutToListePersonnesROVOVL1.
     * @return NivFormAutToListePersonnesROVOVL1
     */
    public ViewLinkImpl getNivFormAutToListePersonnesROVOVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToListePersonnesROVOVL1");
    }

    /**
     * Container's getter for PersDejaAutoriserROVO1.
     * @return PersDejaAutoriserROVO1
     */
    public PersDejaAutoriserROVOImpl getPersDejaAutoriserRO() {
        return (PersDejaAutoriserROVOImpl) findViewObject("PersDejaAutoriserRO");
    }

    /**
     * Container's getter for PersonneLastInsertROVO1.
     * @return PersonneLastInsertROVO1
     */
    public PersonneLastInsertROVOImpl getPersonneLastInsertRO() {
        return (PersonneLastInsertROVOImpl) findViewObject("PersonneLastInsertRO");
    }

    /**
     * Container's getter for PaysVO1.
     * @return PaysVO1
     */
    public ViewObjectImpl getPays() {
        return (ViewObjectImpl) findViewObject("Pays");
    }

    /**
     * Container's getter for CivilitesVO1.
     * @return CivilitesVO1
     */
    public ViewObjectImpl getCivilites() {
        return (ViewObjectImpl) findViewObject("Civilites");
    }

    /**
     * Container's getter for PaysVO1.
     * @return PaysVO1
     */
    public ViewObjectImpl getPaysNationalite() {
        return (ViewObjectImpl) findViewObject("PaysNationalite");
    }

    /**
     * Container's getter for PersonneExisteROVO1.
     * @return PersonneExisteROVO1
     */
    public PersonneExisteROVOImpl getPersonneExiste() {
        return (PersonneExisteROVOImpl) findViewObject("PersonneExiste");
    }

    /**
     * Container's getter for NivFormSaisieResultROVO1.
     * @return NivFormSaisieResultROVO1
     */
    public NivFormSaisieResultROVOImpl getNivFormSaisieResultRO() {
        return (NivFormSaisieResultROVOImpl) findViewObject("NivFormSaisieResultRO");
    }

    /**
     * Container's getter for AnneeUniversSupROVO1.
     * @return AnneeUniversSupROVO1
     */
    public AnneeUniversSupROVOImpl getAnneeUniversSupRO() {
        return (AnneeUniversSupROVOImpl) findViewObject("AnneeUniversSupRO");
    }

    /**
     * Container's getter for ListePersSaisieResultatROVO1.
     * @return ListePersSaisieResultatROVO1
     */
    public ListePersSaisieResultatROVOImpl getListePersSaisieResultatRO() {
        return (ListePersSaisieResultatROVOImpl) findViewObject("ListePersSaisieResultatRO");
    }

    /**
     * Container's getter for DynamicResultVO1.
     * @return DynamicResultVO1
     */

    /**
     * Container's getter for SaisieResultMod1.
     * @return SaisieResultMod1
     */
    public SaisieResultModImpl getSaisieResultMod() {
        return (SaisieResultModImpl) findViewObject("SaisieResultMod");
    }

    /**
     * Container's getter for ResultatVO1.
     * @return ResultatVO1
     */
    public ResultatVOImpl getResultat() {
        return (ResultatVOImpl) findViewObject("Resultat");
    }

    /**
     * Container's getter for ResultParcoursSupROVO1.
     * @return ResultParcoursSupROVO1
     */
    public ResultParcoursSupROVOImpl getResultParcoursSupRO() {
        return (ResultParcoursSupROVOImpl) findViewObject("ResultParcoursSupRO");
    }

    /**
     * Container's getter for SaisieResultatDetailPedROVO1.
     * @return SaisieResultatDetailPedROVO1
     */
    public SaisieResultatDetailPedROVOImpl getSaisieResultatDetailPedRO() {
        return (SaisieResultatDetailPedROVOImpl) findViewObject("SaisieResultatDetailPedRO");
    }

    /**
     * Container's getter for NivFormAnnulSuspendROVO1.
     * @return NivFormAnnulSuspendROVO1
     */
    public NivFormAnnulSuspendROVOImpl getNivFormAnnulSuspendRO() {
        return (NivFormAnnulSuspendROVOImpl) findViewObject("NivFormAnnulSuspendRO");
    }

    /**
     * Container's getter for ListeEtudAnnSuspROVO1.
     * @return ListeEtudAnnSuspROVO1
     */
    public ListeEtudAnnSuspROVOImpl getListeEtudAnnSuspRO() {
        return (ListeEtudAnnSuspROVOImpl) findViewObject("ListeEtudAnnSuspRO");
    }

    /**
     * Container's getter for ListeEtudAnnSuspROVO1.
     * @return ListeEtudAnnSuspROVO1
     */
    public ListeEtudAnnSuspROVOImpl getListeEtudAnnSusp() {
        return (ListeEtudAnnSuspROVOImpl) findViewObject("ListeEtudAnnSusp");
    }

    /**
     * Container's getter for NivFormAnnSuspToListeEtudAnnSuspVL1.
     * @return NivFormAnnSuspToListeEtudAnnSuspVL1
     */
    public ViewLinkImpl getNivFormAnnSuspToListeEtudAnnSuspVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAnnSuspToListeEtudAnnSuspVL1");
    }

    /**
     * Container's getter for ListeEtudFinalAnnSuspROVO1.
     * @return ListeEtudFinalAnnSuspROVO1
     */
    public ListeEtudFinalAnnSuspROVOImpl getListeEtudFinalAnnSuspRO() {
        return (ListeEtudFinalAnnSuspROVOImpl) findViewObject("ListeEtudFinalAnnSuspRO");
    }

    /**
     * Container's getter for NivFormAnnSuspToListeEtudFinalAnnSuspVL1.
     * @return NivFormAnnSuspToListeEtudFinalAnnSuspVL1
     */
    public ViewLinkImpl getNivFormAnnSuspToListeEtudFinalAnnSuspVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAnnSuspToListeEtudFinalAnnSuspVL1");
    }

    /**
     * Container's getter for AnnulerSuspendreLOV1.
     * @return AnnulerSuspendreLOV1
     */
    public AnnulerSuspendreLOVImpl getAnnulerSuspendreLOV() {
        return (AnnulerSuspendreLOVImpl) findViewObject("AnnulerSuspendreLOV");
    }

    /**
     * Container's getter for PersonneSearchSimple1.
     * @return PersonneSearchSimple1
     */
    public PersonneSearchSimpleImpl getPersonneSearchSimple() {
        return (PersonneSearchSimpleImpl) findViewObject("PersonneSearchSimple");
    }

    /**
     * Container's getter for ListeInsPedExonerationROVO1.
     * @return ListeInsPedExonerationROVO1
     */
    public ListeInsPedExonerationROVOImpl getListeInsPedExonerationRO() {
        return (ListeInsPedExonerationROVOImpl) findViewObject("ListeInsPedExonerationRO");
    }

    /**
     * Container's getter for ExonerationVO1.
     * @return ExonerationVO1
     */
    public ExonerationVOImpl getExoneration() {
        return (ExonerationVOImpl) findViewObject("Exoneration");
    }

    /**
     * Container's getter for ListeInsPedExonerationROToExonerationVL1.
     * @return ListeInsPedExonerationROToExonerationVL1
     */
    public ViewLinkImpl getListeInsPedExonerationROToExonerationVL1() {
        return (ViewLinkImpl) findViewLink("ListeInsPedExonerationROToExonerationVL1");
    }


    /**
     * Container's getter for InfoExonerationAnterieurROVO1.
     * @return InfoExonerationAnterieurROVO1
     */
    public InfoExonerationAnterieurROVOImpl getInfoExonerationAnterieurRO() {
        return (InfoExonerationAnterieurROVOImpl) findViewObject("InfoExonerationAnterieurRO");
    }

    /**
     * Container's getter for ListeSuspensioneEtudROVO1.
     * @return ListeSuspensioneEtudROVO1
     */
    public ListeSuspensioneEtudROVOImpl getListeSuspensioneEtudRO() {
        return (ListeSuspensioneEtudROVOImpl) findViewObject("ListeSuspensioneEtudRO");
    }

    /**
     * Container's getter for ListeInscPedAnnulDesistROVO1.
     * @return ListeInscPedAnnulDesistROVO1
     */
    public ListeInscPedAnnulDesistROVOImpl getListeInscPedAnnulDesistRO() {
        return (ListeInscPedAnnulDesistROVOImpl) findViewObject("ListeInscPedAnnulDesistRO");
    }

    /**
     * Container's getter for ListeInsPedIsAnnulDesistROVO1.
     * @return ListeInsPedIsAnnulDesistROVO1
     */
    public ListeInsPedIsAnnulDesistROVOImpl getListeInsPedIsAnnulDesistRO() {
        return (ListeInsPedIsAnnulDesistROVOImpl) findViewObject("ListeInsPedIsAnnulDesistRO");
    }

    /**
     * Container's getter for AnneeUniversEnCoursROVO1.
     * @return AnneeUniversEnCoursROVO1
     */
    public AnneeUniversEnCoursROVOImpl getAnneeUniversEnCoursRO() {
        return (AnneeUniversEnCoursROVOImpl) findViewObject("AnneeUniversEnCoursRO");
    }

    /**
     * Container's getter for LesPersonnesROVO1.
     * @return LesPersonnesROVO1
     */
    public LesPersonnesROVOImpl getLesPersonnesRO() {
        return (LesPersonnesROVOImpl) findViewObject("LesPersonnesRO");
    }

    /**
     * Container's getter for ResponsablesVO1.
     * @return ResponsablesVO1
     */
    public ViewObjectImpl getResponsables() {
        return (ViewObjectImpl) findViewObject("Responsables");
    }

    /**
     * Container's getter for HistoriquesStructuresVO1.
     * @return HistoriquesStructuresVO1
     */
    public ViewObjectImpl getHistoriquesStructures() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructures");
    }

    /**
     * Container's getter for InscPedSemAncienROVO1.
     * @return InscPedSemAncienROVO1
     */
    public InscPedSemAncienROVOImpl getInscPedSemAncienRO() {
        return (InscPedSemAncienROVOImpl) findViewObject("InscPedSemAncienRO");
    }

    /**
     * Container's getter for SemestresVO1.
     * @return SemestresVO1
     */
    public ViewObjectImpl getSemestres() {
        return (ViewObjectImpl) findViewObject("Semestres");
    }

    /**
     * Container's getter for listeUeValide1.
     * @return listeUeValide1
     */
    public listeUeValideImpl getlisteUeValide() {
        return (listeUeValideImpl) findViewObject("listeUeValide");
    }

    /**
     * Container's getter for ResultatsFilUeSemestreVO1.
     * @return ResultatsFilUeSemestreVO1
     */
    public ViewObjectImpl getResultatsFilUeSemestre() {
        return (ViewObjectImpl) findViewObject("ResultatsFilUeSemestre");
    }

    /**
     * Container's getter for ResponsableROVO1.
     * @return ResponsableROVO1
     */
    public ResponsableROVOImpl getResponsableRO() {
        return (ResponsableROVOImpl) findViewObject("ResponsableRO");
    }

    /**
     * Container's getter for lesUeInscPedSemUeROVO1.
     * @return lesUeInscPedSemUeROVO1
     */
    public lesUeInscPedSemUeROVOImpl getlesUeInscPedSemUeRO() {
        return (lesUeInscPedSemUeROVOImpl) findViewObject("lesUeInscPedSemUeRO");
    }

    /**
     * Container's getter for EtudEnRegleBuROVO1.
     * @return EtudEnRegleBuROVO1
     */
    public EtudEnRegleBuROVOImpl getEtudEnRegleBuRO() {
        return (EtudEnRegleBuROVOImpl) findViewObject("EtudEnRegleBuRO");
    }

    /**
     * Container's getter for StructuresVO1.
     * @return StructuresVO1
     */
    public ViewObjectImpl getStructures() {
        return (ViewObjectImpl) findViewObject("Structures");
    }

    /**
     * Container's getter for EtudPaiementROVO1.
     * @return EtudPaiementROVO1
     */
    public EtudPaiementROVOImpl getEtudPaiementRO() {
        return (EtudPaiementROVOImpl) findViewObject("EtudPaiementRO");
    }

    /**
     * Container's getter for AttrComplAutorisation1.
     * @return AttrComplAutorisation1
     */
    public AttrComplAutorisationImpl getAttrComplAutorisation() {
        return (AttrComplAutorisationImpl) findViewObject("AttrComplAutorisation");
    }

    /**
     * Container's getter for SexeVO1.
     * @return SexeVO1
     */
    public ViewObjectImpl getSexe() {
        return (ViewObjectImpl) findViewObject("Sexe");
    }

    /**
     * Container's getter for SerieBacVO1.
     * @return SerieBacVO1
     */
    public ViewObjectImpl getSerieBac() {
        return (ViewObjectImpl) findViewObject("SerieBac");
    }

    /**
     * Container's getter for TypeMentionVO1.
     * @return TypeMentionVO1
     */
    public ViewObjectImpl getTypeMention() {
        return (ViewObjectImpl) findViewObject("TypeMention");
    }

    /**
     * Container's getter for LyceeVO1.
     * @return LyceeVO1
     */
    public ViewObjectImpl getLycee() {
        return (ViewObjectImpl) findViewObject("Lycee");
    }

    /**
     * Container's getter for EtudiantTicROVO1.
     * @return EtudiantTicROVO1
     */
    public EtudiantTicROVOImpl getEtudiantTicRO() {
        return (EtudiantTicROVOImpl) findViewObject("EtudiantTicRO");
    }

    /**
     * Container's getter for OptionFormROVO1.
     * @return OptionFormROVO1
     */
    public OptionFormROVOImpl getOptionFormRO() {
        return (OptionFormROVOImpl) findViewObject("OptionFormRO");
    }

    /**
     * Container's getter for InscPedChangeNationaliteROVO1.
     * @return InscPedChangeNationaliteROVO1
     */
    public InscPedChangeNationaliteROVOImpl getInscPedChangeNationaliteRO() {
        return (InscPedChangeNationaliteROVOImpl) findViewObject("InscPedChangeNationaliteRO");
    }

    /**
     * Container's getter for InscPedExcluROVO1.
     * @return InscPedExcluROVO1
     */
    public InscPedExcluROVOImpl getInscPedExcluRO() {
        return (InscPedExcluROVOImpl) findViewObject("InscPedExcluRO");
    }

    /**
     * Container's getter for ExcluLOV1.
     * @return ExcluLOV1
     */
    public ExcluLOVImpl getExcluLOV() {
        return (ExcluLOVImpl) findViewObject("ExcluLOV");
    }

    /**
     * Container's getter for InscPedEnCoursExcluROVO1.
     * @return InscPedEnCoursExcluROVO1
     */
    public InscPedEnCoursExcluROVOImpl getInscPedEnCoursExcluRO() {
        return (InscPedEnCoursExcluROVOImpl) findViewObject("InscPedEnCoursExcluRO");
    }

    /**
     * Container's getter for HistoChangeNationVO1.
     * @return HistoChangeNationVO1
     */
    public ViewObjectImpl getHistoChangeNationVO1() {
        return (ViewObjectImpl) findViewObject("HistoChangeNationVO1");
}

    /**
     * Container's getter for HistoChangeNation.
     * @return HistoChangeNation
     */
    public ViewObjectImpl getHistoChangeNation() {
        return (ViewObjectImpl) findViewObject("HistoChangeNation");
    }


    /**
     * Container's getter for InscPedSemEtudMobiliteROVO1.
     * @return InscPedSemEtudMobiliteROVO1
     */
    public InscPedSemEtudMobiliteROVOImpl getInscPedSemEtudMobiliteRO() {
        return (InscPedSemEtudMobiliteROVOImpl) findViewObject("InscPedSemEtudMobiliteRO");
    }

    /**
     * Container's getter for HistoMobiliteROVO1.
     * @return HistoMobiliteROVO1
     */
    public HistoMobiliteROVOImpl getHistoMobiliteRO() {
        return (HistoMobiliteROVOImpl) findViewObject("HistoMobiliteRO");
    }

    /**
     * Container's getter for HistoriquesStructToHistoMobiliteVL1.
     * @return HistoriquesStructToHistoMobiliteVL1
     */
    public ViewLinkImpl getHistoriquesStructToHistoMobiliteVL1() {
        return (ViewLinkImpl) findViewLink("HistoriquesStructToHistoMobiliteVL1");
    }

    /**
     * Container's getter for AutoResulAnnValideROVO1.
     * @return AutoResulAnnValideROVO1
     */
    public AutoResulAnnValideROVOImpl getAutoResulAnnValideRO() {
        return (AutoResulAnnValideROVOImpl) findViewObject("AutoResulAnnValideRO");
    }

    /**
     * Container's getter for listeEtudMobiliteROVO1.
     * @return listeEtudMobiliteROVO1
     */
    public listeEtudMobiliteROVOImpl getlisteEtudMobiliteRO() {
        return (listeEtudMobiliteROVOImpl) findViewObject("listeEtudMobiliteRO");
    }

    /**
     * Container's getter for listeEtudMobUeROVO1.
     * @return listeEtudMobUeROVO1
     */
    public listeEtudMobUeROVOImpl getlisteEtudMobUeRO() {
        return (listeEtudMobUeROVOImpl) findViewObject("listeEtudMobUeRO");
    }

    /**
     * Container's getter for EtudiantModEtudModUeVL1.
     * @return EtudiantModEtudModUeVL1
     */
    public ViewLinkImpl getEtudiantModEtudModUeVL1() {
        return (ViewLinkImpl) findViewLink("EtudiantModEtudModUeVL1");
    }

    /**
     * Container's getter for InscPedagogiqueROVO1.
     * @return InscPedagogiqueROVO1
     */
    public InscPedagogiqueROVOImpl getInscPedagogiqueRO() {
        return (InscPedagogiqueROVOImpl) findViewObject("InscPedagogiqueRO");
    }

    /**
     * Container's getter for UeInsc1.
     * @return UeInsc1
     */
    public UeInscImpl getUeInsc() {
        return (UeInscImpl) findViewObject("UeInsc");
    }

    /**
     * Container's getter for AnneeUniversPasseeROVO1.
     * @return AnneeUniversPasseeROVO1
     */
    public AnneeUniversPasseeROVOImpl getAnneeUniversPasseeRO() {
        return (AnneeUniversPasseeROVOImpl) findViewObject("AnneeUniversPasseeRO");
    }

    /**
     * Container's getter for InsChangementCycleROVO1.
     * @return InsChangementCycleROVO1
     */
    public InsChangementCycleROVOImpl getInsChangementCycleRO() {
        return (InsChangementCycleROVOImpl) findViewObject("InsChangementCycleRO");
    }

    /**
     * Container's getter for InscAncienneROVO1.
     * @return InscAncienneROVO1
     */
    public InscAncienneROVOImpl getInscAncienneRO() {
        return (InscAncienneROVOImpl) findViewObject("InscAncienneRO");
    }

    /**
     * Container's getter for ResultatAnnuelROVO1.
     * @return ResultatAnnuelROVO1
     */
    public ResultatAnnuelROVOImpl getResultatAnnuelRO() {
        return (ResultatAnnuelROVOImpl) findViewObject("ResultatAnnuelRO");
    }

    /**
     * Container's getter for ListeEtudInscProvROVO1.
     * @return ListeEtudInscProvROVO1
     */
    public ListeEtudInscProvROVOImpl getListeEtudInscProvRO() {
        return (ListeEtudInscProvROVOImpl) findViewObject("ListeEtudInscProvRO");
    }

    /**
     * Container's getter for ListeProvEtudROVO1.
     * @return ListeProvEtudROVO1
     */
    public ViewObjectImpl getListeProvEtudRO() {
        return (ViewObjectImpl) findViewObject("ListeProvEtudRO");
    }

    /**
     * Container's getter for SemestreReDoubleROVO1.
     * @return SemestreReDoubleROVO1
     */
    public SemestreReDoubleROVOImpl getSemestreReDoubleRO() {
        return (SemestreReDoubleROVOImpl) findViewObject("SemestreReDoubleRO");
    }

    /**
     * Container's getter for SemRedoubleROVO1.
     * @return SemRedoubleROVO1
     */
    public SemRedoubleROVOImpl getSemRedoubleRO() {
        return (SemRedoubleROVOImpl) findViewObject("SemRedoubleRO");
    }

    /**
     * Container's getter for ListePersAutDerogationROVO1.
     * @return ListePersAutDerogationROVO1
     */
    public ListePersAutDerogationROVOImpl getListePersAutDerogationRO() {
        return (ListePersAutDerogationROVOImpl) findViewObject("ListePersAutDerogationRO");
    }

    /**
     * Container's getter for NivFormAutToListePersAutDerogationVL1.
     * @return NivFormAutToListePersAutDerogationVL1
     */
    public ViewLinkImpl getNivFormAutToListePersAutDerogationVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToListePersAutDerogationVL1");
    }

    /**
     * Container's getter for InscPedParcEnCoursROVO1.
     * @return InscPedParcEnCoursROVO1
     */
    public InscPedParcEnCoursROVOImpl getInscPedParcEnCoursRO() {
        return (InscPedParcEnCoursROVOImpl) findViewObject("InscPedParcEnCoursRO");
    }

    /**
     * Container's getter for InscPedSemParcROVO1.
     * @return InscPedSemParcROVO1
     */
    public InscPedSemParcROVOImpl getInscPedSemParcRO() {
        return (InscPedSemParcROVOImpl) findViewObject("InscPedSemParcRO");
    }

    /**
     * Container's getter for InscAncienneNiv1_1.
     * @return InscAncienneNiv1_1
     */
    public InscAncienneNiv1Impl getInscAncienneNiv1() {
        return (InscAncienneNiv1Impl) findViewObject("InscAncienneNiv1");
    }

    /**
     * Container's getter for InscAncienneNiv2_1.
     * @return InscAncienneNiv2_1
     */
    public InscAncienneNiv2Impl getInscAncienneNiv2() {
        return (InscAncienneNiv2Impl) findViewObject("InscAncienneNiv2");
    }

    /**
     * Container's getter for InscNivFormationROVO1.
     * @return InscNivFormationROVO1
     */
    public InscNivFormationROVOImpl getInscNivFormationRO() {
        return (InscNivFormationROVOImpl) findViewObject("InscNivFormationRO");
    }

    /**
     * Container's getter for ResultatAnnuelInscPedROVO1.
     * @return ResultatAnnuelInscPedROVO1
     */
    public ResultatAnnuelInscPedROVOImpl getResultatAnnuelInscPedRO() {
        return (ResultatAnnuelInscPedROVOImpl) findViewObject("ResultatAnnuelInscPedRO");
    }

    /**
     * Container's getter for NiveauInscPedROVO1.
     * @return NiveauInscPedROVO1
     */
    public NiveauInscPedROVOImpl getNiveauInscPedRO() {
        return (NiveauInscPedROVOImpl) findViewObject("NiveauInscPedRO");
    }

    /**
     * Container's getter for LesInscPedROVO1.
     * @return LesInscPedROVO1
     */
    public LesInscPedROVOImpl getLesInscPedRO() {
        return (LesInscPedROVOImpl) findViewObject("LesInscPedRO");
    }

    /**
     * Container's getter for LesUeInscPedSemROVO1.
     * @return LesUeInscPedSemROVO1
     */
    public LesUeInscPedSemROVOImpl getLesUeInscPedSemRO() {
        return (LesUeInscPedSemROVOImpl) findViewObject("LesUeInscPedSemRO");
    }

    /**
     * Container's getter for LesInscPedSem1.
     * @return LesInscPedSem1
     */
    public LesInscPedSemImpl getLesInscPedSem() {
        return (LesInscPedSemImpl) findViewObject("LesInscPedSem");
    }

    /**
     * Container's getter for ResultatUeFilUeSemROVO1.
     * @return ResultatUeFilUeSemROVO1
     */
    public ResultatUeFilUeSemROVOImpl getResultatUeFilUeSemRO() {
        return (ResultatUeFilUeSemROVOImpl) findViewObject("ResultatUeFilUeSemRO");
    }

    /**
     * Container's getter for ResultatInscPedSenROVO1.
     * @return ResultatInscPedSenROVO1
     */
    public ResultatInscPedSenROVOImpl getResultatInscPedSenRO() {
        return (ResultatInscPedSenROVOImpl) findViewObject("ResultatInscPedSenRO");
    }

    /**
     * Container's getter for ResultatsSemestreVO1.
     * @return ResultatsSemestreVO1
     */
    public ViewObjectImpl getResultatsSemestre() {
        return (ViewObjectImpl) findViewObject("ResultatsSemestre");
    }

    /**
     * Container's getter for PaieEtudInscPedROVO1.
     * @return PaieEtudInscPedROVO1
     */
    public PaieEtudInscPedROVOImpl getPaieEtudInscPedRO() {
        return (PaieEtudInscPedROVOImpl) findViewObject("PaieEtudInscPedRO");
    }

    /**
     * Container's getter for DerogationsVO1.
     * @return DerogationsVO1
     */
    public ViewObjectImpl getDerogations() {
        return (ViewObjectImpl) findViewObject("Derogations");
    }

    /**
     * Container's getter for DetailStrcDerogationROVO1.
     * @return DetailStrcDerogationROVO1
     */
    public DetailStrcDerogationROVOImpl getDetailStrcDerogationRO() {
        return (DetailStrcDerogationROVOImpl) findViewObject("DetailStrcDerogationRO");
    }

    /**
     * Container's getter for DerogationEtudROVO1.
     * @return DerogationEtudROVO1
     */
    public DerogationEtudROVOImpl getDerogationEtudRO() {
        return (DerogationEtudROVOImpl) findViewObject("DerogationEtudRO");
    }

    /**
     * Container's getter for InscPedAnneeROVO1.
     * @return InscPedAnneeROVO1
     */
    public InscPedAnneeROVOImpl getInscPedAnneeRO() {
        return (InscPedAnneeROVOImpl) findViewObject("InscPedAnneeRO");
    }

    /**
     * Container's getter for AttrOblInscPedROVO1.
     * @return AttrOblInscPedROVO1
     */
    public AttrOblInscPedROVOImpl getAttrOblInscPedRO() {
        return (AttrOblInscPedROVOImpl) findViewObject("AttrOblInscPedRO");
    }

    /**
     * Container's getter for TypeConventionsVO1.
     * @return TypeConventionsVO1
     */
    public ViewObjectImpl getTypeConventions() {
        return (ViewObjectImpl) findViewObject("TypeConventions");
    }

    /**
     * Container's getter for CohortesVO1.
     * @return CohortesVO1
     */
    public ViewObjectImpl getCohortes() {
        return (ViewObjectImpl) findViewObject("Cohortes");
    }

    /**
     * Container's getter for HorairesTdVO1.
     * @return HorairesTdVO1
     */
    public ViewObjectImpl getHorairesTd() {
        return (ViewObjectImpl) findViewObject("HorairesTd");
    }

    /**
     * Container's getter for OperateursVO1.
     * @return OperateursVO1
     */
    public ViewObjectImpl getOperateurs() {
        return (ViewObjectImpl) findViewObject("Operateurs");
    }

    /**
     * Container's getter for StatutsVO1.
     * @return StatutsVO1
     */
    public ViewObjectImpl getStatuts() {
        return (ViewObjectImpl) findViewObject("Statuts");
    }

    /**
     * Container's getter for InscPedEtatInscROVO1.
     * @return InscPedEtatInscROVO1
     */
    public InscPedEtatInscROVOImpl getInscPedEtatInscRO() {
        return (InscPedEtatInscROVOImpl) findViewObject("InscPedEtatInscRO");
    }

    /**
     * Container's getter for NiveauFormationSupROVO1.
     * @return NiveauFormationSupROVO1
     */
    public NiveauFormationSupROVOImpl getNiveauFormationSupRO() {
        return (NiveauFormationSupROVOImpl) findViewObject("NiveauFormationSupRO");
    }

    /**
     * Container's getter for ModePaiementsVO1.
     * @return ModePaiementsVO1
     */
    public ViewObjectImpl getModePaiements() {
        return (ViewObjectImpl) findViewObject("ModePaiements");
    }

    /**
     * Container's getter for NombrInscPedAnneeROVO1.
     * @return NombrInscPedAnneeROVO1
     */
    public NombrInscPedAnneeROVOImpl getNombrInscPedAnneeRO() {
        return (NombrInscPedAnneeROVOImpl) findViewObject("NombrInscPedAnneeRO");
    }

    /**
     * Container's getter for EquivalenceRechercheROVO1.
     * @return EquivalenceRechercheROVO1
     */
    public EquivalenceRechercheROVOImpl getEquivalenceRechercheRO() {
        return (EquivalenceRechercheROVOImpl) findViewObject("EquivalenceRechercheRO");
    }

    /**
     * Container's getter for EquivalenceVO1.
     * @return EquivalenceVO1
     */
    public ViewObjectImpl getEquivalence() {
        return (ViewObjectImpl) findViewObject("Equivalence");
    }

    /**
     * Container's getter for EquivalenceListeROVO1.
     * @return EquivalenceListeROVO1
     */
    public EquivalenceListeROVOImpl getEquivalenceListe() {
        return (EquivalenceListeROVOImpl) findViewObject("EquivalenceListe");
    }

    /**
     * Container's getter for NivFormAutToEqListeVL1.
     * @return NivFormAutToEqListeVL1
     */
    public ViewLinkImpl getNivFormAutToEqListeVL1() {
        return (ViewLinkImpl) findViewLink("NivFormAutToEqListeVL1");
    }

    /**
     * Container's getter for LesSuspenduROVO1.
     * @return LesSuspenduROVO1
     */
    public LesSuspenduROVOImpl getLesSuspenduRO() {
        return (LesSuspenduROVOImpl) findViewObject("LesSuspenduRO");
    }

    /**
     * Container's getter for SuspensionVO1.
     * @return SuspensionVO1
     */
    public ViewObjectImpl getSuspension() {
        return (ViewObjectImpl) findViewObject("Suspension");
    }

    /**
     * Container's getter for InscPedFormPayEcolROVO1.
     * @return InscPedFormPayEcolROVO1
     */
    public InscPedFormPayEcolROVOImpl getInscPedFormPayEcolRO() {
        return (InscPedFormPayEcolROVOImpl) findViewObject("InscPedFormPayEcolRO");
    }

    /**
     * Container's getter for FormPayEcolRegleROVO1.
     * @return FormPayEcolRegleROVO1
     */
    public FormPayEcolRegleROVOImpl getFormPayEcolRegleRO() {
        return (FormPayEcolRegleROVOImpl) findViewObject("FormPayEcolRegleRO");
    }

    /**
     * Container's getter for PaieEchelonEcolModFormROVO1.
     * @return PaieEchelonEcolModFormROVO1
     */
    public PaieEchelonEcolModFormROVOImpl getPaieEchelonEcolModFormRO() {
        return (PaieEchelonEcolModFormROVOImpl) findViewObject("PaieEchelonEcolModFormRO");
    }

    /**
     * Container's getter for PaieEtudGenROVO1.
     * @return PaieEtudGenROVO1
     */
    public PaieEtudGenROVOImpl getPaieEtudGenRO() {
        return (PaieEtudGenROVOImpl) findViewObject("PaieEtudGenRO");
    }

    /**
     * Container's getter for ExonerationEcolageROVO1.
     * @return ExonerationEcolageROVO1
     */
    public ExonerationEcolageROVOImpl getExonerationEcolageRO() {
        return (ExonerationEcolageROVOImpl) findViewObject("ExonerationEcolageRO");
    }

    /**
     * Container's getter for PaiementsVO1.
     * @return PaiementsVO1
     */
    public ViewObjectImpl getPaiements() {
        return (ViewObjectImpl) findViewObject("Paiements");
    }

    /**
     * Container's getter for ComptesVO1.
     * @return ComptesVO1
     */
    public ViewObjectImpl getComptes() {
        return (ViewObjectImpl) findViewObject("Comptes");
    }

    /**
     * Container's getter for SoldesComptesVO1.
     * @return SoldesComptesVO1
     */
    public ViewObjectImpl getSoldesComptes() {
        return (ViewObjectImpl) findViewObject("SoldesComptes");
    }

    /**
     * Container's getter for FormPayCompteEtudROVO1.
     * @return FormPayCompteEtudROVO1
     */
    public FormPayCompteEtudROVOImpl getFormPayCompteEtudRO() {
        return (FormPayCompteEtudROVOImpl) findViewObject("FormPayCompteEtudRO");
    }

}

