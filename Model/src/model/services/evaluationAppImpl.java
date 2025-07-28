package model.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.assoc.DelibSemUeEtudiantROVOImpl;

import model.entities.java.Ec;
import model.entities.java.Etudiant;
import model.entities.java.EtudiantComparator;
import model.entities.java.EtudiantUe;
import model.entities.java.EtudiantUeComparator;
import model.entities.java.EtudiantsEcs;
import model.entities.java.EudiantsUes;
import model.entities.java.NoteEc;
import model.entities.java.NoteTpCntrole;
import model.entities.java.ReclamationNote;
import model.entities.java.ReclamationResponse;
import model.entities.java.ResultatUe;
import model.entities.java.TypeControle;
import model.entities.java.Ue;
import model.entities.java.UeComparator;

import model.readOnlyView.AccesEcListImpl;
import model.readOnlyView.AccesFormationListImpl;
import model.readOnlyView.AccesNiveauFormationListImpl;
import model.readOnlyView.AccesUeListImpl;
import model.readOnlyView.AllEcFilUeImpl;
import model.readOnlyView.AllInteFilEcROVOImpl;
import model.readOnlyView.AnneeUniverROVOImpl;
import model.readOnlyView.AnoGenParcoursImpl;
import model.readOnlyView.AnonymatExecuteROVOImpl;
import model.readOnlyView.AnonymatParcoursROVOImpl;
import model.readOnlyView.CalParcoursAnneeImpl;
import model.readOnlyView.DelibAnPvROVOImpl;
import model.readOnlyView.DelibDataROVOImpl;
import model.readOnlyView.DelibSemDataDetailNoteROVOImpl;
import model.readOnlyView.DelibSemDataROVOImpl;
import model.readOnlyView.DelibSemEtuRepecheROVOImpl;
import model.readOnlyView.DelibSemEtudiantROVOImpl;
import model.readOnlyView.DelibSemFilUeNewROVOImpl;
import model.readOnlyView.DelibSemRepCritereCompLOVImpl;
import model.readOnlyView.DelibSemRepFiltreLOVImpl;
import model.readOnlyView.DelibSemRepecherSurLOVImpl;
import model.readOnlyView.DelibUeFilEcTypeCntrlROVOImpl;
import model.readOnlyView.DelibUeNoteTypeCntrleROVOImpl;
import model.readOnlyView.DeliberationSemestrielleImpl;
import model.readOnlyView.DeliberationStaticListImpl;
import model.readOnlyView.DeliberationUeDataROVOImpl;
import model.readOnlyView.DepartementByUserROVOImpl;
import model.readOnlyView.DepartementUserROVOImpl;
import model.readOnlyView.DepartementsEtabROVOImpl;
import model.readOnlyView.DepartementsStructureImpl;
import model.readOnlyView.DetailNotesROVOImpl;
import model.readOnlyView.DetailsAnnEtudiantImpl;
import model.readOnlyView.DetailsSemestreEtudiantImpl;
import model.readOnlyView.DocumentsROVOImpl;
import model.readOnlyView.EcEvalExportEtdROVOImpl;
import model.readOnlyView.EcEvalNewROVOImpl;
import model.readOnlyView.EcEvalROVOImpl;
import model.readOnlyView.EcEvalTypeCntrModeCntrllNewROVOImpl;
import model.readOnlyView.EcEvalTypeCntrlNewROVOImpl;
import model.readOnlyView.EcEvalTypeCtrlModeCtrlROVOImpl;
import model.readOnlyView.EcEvalTypeCtrlROVOImpl;
import model.readOnlyView.EcNotClosedDelibUeROVOImpl;
import model.readOnlyView.EcROVOImpl;
import model.readOnlyView.EtudiantEtaInscrEnjambisteROVOImpl;
import model.readOnlyView.EtudiantEtatInscrROVOImpl;
import model.readOnlyView.EtudiantExportListROVOImpl;
import model.readOnlyView.EtudiantMemoireROVOImpl;
import model.readOnlyView.EtudiantNoteInterImpl;
import model.readOnlyView.EtudiantResultAnnuelDelibROVOImpl;
import model.readOnlyView.EtudiantResultUeDelibROVOImpl;
import model.readOnlyView.EtudiantSaisieMemoireROVOImpl;
import model.readOnlyView.EtudiantSaisieNoteInterImpl;
import model.readOnlyView.EtudiantSaisieNoteInterNewROVOImpl;
import model.readOnlyView.EtudiantsRenonciationNoteImpl;
import model.readOnlyView.FilEcInsPedSemUeImpl;
import model.readOnlyView.FilEcROVOImpl;
import model.readOnlyView.FilUeByFilEcImpl;
import model.readOnlyView.FilUeInsPedSemImpl;
import model.readOnlyView.FilUeInscDelibROVOImpl;
import model.readOnlyView.FilereEcInvalideRepechROVOImpl;
import model.readOnlyView.FiliereEcRenonciationNoteROVOImpl;
import model.readOnlyView.FiliereUEInvalidRepechROVOImpl;
import model.readOnlyView.FiliereUeRenonciationROVOImpl;
import model.readOnlyView.FiliereUeSemEcByRespROVOImpl;
import model.readOnlyView.FiliereUeSemestreROVOImpl;
import model.readOnlyView.FiltreAnuelleROVOImpl;
import model.readOnlyView.FiltreModeDelibAnImpl;
import model.readOnlyView.FormationByStructureImpl;
import model.readOnlyView.GenAnonymatNewROVOImpl;
import model.readOnlyView.GenAnonymatROVOImpl;
import model.readOnlyView.GetRoleROVOImpl;
import model.readOnlyView.GradeSemestreROVOImpl;
import model.readOnlyView.GroupeSaisieNoteROVOImpl;
import model.readOnlyView.HistoriqueStructureByUserNameImpl;
import model.readOnlyView.InscriptionsAntEtdROVOImpl;
import model.readOnlyView.InscriptionsEtudiantROVOImpl;
import model.readOnlyView.IsAnParcoursClosedImpl;
import model.readOnlyView.IsCalExistImpl;
import model.readOnlyView.IsDelibCalendierROVOImpl;
import model.readOnlyView.IsDelibDefEndImpl;
import model.readOnlyView.IsDelibDefStartImpl;
import model.readOnlyView.IsDelibProvEndImpl;
import model.readOnlyView.IsDelibProvStartImpl;
import model.readOnlyView.IsExistEtuAnoImpl;
import model.readOnlyView.IsFonctionUserExistImpl;
import model.readOnlyView.IsFormationAllowedAccessImpl;
import model.readOnlyView.IsFormationTextIsImpl;
import model.readOnlyView.IsJuryEmptyImpl;
import model.readOnlyView.IsMaquetteUsedROVOImpl;
import model.readOnlyView.IsModeControlEcUsedROVOImpl;
import model.readOnlyView.IsParamModeCntrlExistROVOImpl;
import model.readOnlyView.IsParamModeCntrlTerExistROVOImpl;
import model.readOnlyView.IsReclEndImpl;
import model.readOnlyView.IsReclStartImpl;
import model.readOnlyView.IsResponsableFilEcImpl;
import model.readOnlyView.IsResponsableFilUeImpl;
import model.readOnlyView.IsRoleUserExistImpl;
import model.readOnlyView.IsStructureTextExistImpl;
import model.readOnlyView.IsUsedFormationTextImpl;
import model.readOnlyView.IsUserRespNivFormROVOImpl;
import model.readOnlyView.IsUserRoleExistROVOImpl;
import model.readOnlyView.JuryMembrePresentROVOImpl;
import model.readOnlyView.JuryParcoursAnneeROVOImpl;
import model.readOnlyView.JuryRoleROVOImpl;
import model.readOnlyView.ListAutorisationSess1ROVOImpl;
import model.readOnlyView.ListAutorisationSess2ROVOImpl;
import model.readOnlyView.ListFilUeDeliSemROVOImpl;
import model.readOnlyView.ListRelevesChoiceImpl;
import model.readOnlyView.ListeEcImpl;
import model.readOnlyView.ListeEtudiantInscImpl;
import model.readOnlyView.ListeModeControlImpl;
import model.readOnlyView.MaquetteParcoursAnneeROVOImpl;
import model.readOnlyView.ModeControleEvalROVOImpl;
import model.readOnlyView.ModeControleROVOImpl;
import model.readOnlyView.MoyenneUEImpl;
import model.readOnlyView.NivFormSpecOptionROVOImpl;
import model.readOnlyView.NivFormSpecialiteROVOImpl;
import model.readOnlyView.NiveauFormationListImpl;
import model.readOnlyView.NiveauxSectionsSupROVOImpl;
import model.readOnlyView.NiveauxSuperieursOptionsROVOImpl;
import model.readOnlyView.NoteModeEnsGenAnonymROVOImpl;
import model.readOnlyView.NoteModeEnsInterByGroupeSaisieImpl;
import model.readOnlyView.NoteModeEnsInterGenAnonymROVOImpl;
import model.readOnlyView.ParcoursAttSpecialeROVOImpl;
import model.readOnlyView.ParcoursDelibCycleImpl;
import model.readOnlyView.ParcoursInfoROVOImpl;
import model.readOnlyView.ParcoursInscDelibROVOImpl;
import model.readOnlyView.ParcoursMaqAnCalendrierROVOImpl;
import model.readOnlyView.ParcoursSaisieMemoireROVOImpl;
import model.readOnlyView.ParcoursSaisieModeCntrlROVOImpl;
import model.readOnlyView.ParcoursUserROVOImpl;
import model.readOnlyView.ParcrsParamModeSaisieECROVOImpl;
import model.readOnlyView.PourcentageCC_CT_ROVOImpl;
import model.readOnlyView.PrcrsMaquetteAnROVOImpl;
import model.readOnlyView.PvAnnuelROVOImpl;
import model.readOnlyView.PvAnnuelSess2ROVOImpl;
import model.readOnlyView.ReglesCompensationPArcoursROVOImpl;
import model.readOnlyView.RepechableSemestreROVOImpl;
import model.readOnlyView.RepecheFilUeImpl;
import model.readOnlyView.RepecheSemROVOImpl;
import model.readOnlyView.ResponsableUePrcrsMaqAnImpl;
import model.readOnlyView.ResultFilUeRepechableMedROVOImpl;
import model.readOnlyView.ResultatCycleROVOImpl;
import model.readOnlyView.ResultatFilUERepechableROVOImpl;
import model.readOnlyView.ResultatFilUeSemestreROVOImpl;
import model.readOnlyView.ResultatSemestreEtudiantROVOImpl;
import model.readOnlyView.SaisieNoteEtudiantImpl;
import model.readOnlyView.SaisieNotesFiltresLOVImpl;
import model.readOnlyView.SaisieNotesGroupesSaisieLOVImpl;
import model.readOnlyView.SemestreROVOImpl;
import model.readOnlyView.SemestresByParcousMaqROVOImpl;
import model.readOnlyView.StatistiquesDepartementROVOImpl;
import model.readOnlyView.StatistiquesNiveauxFormationROVOImpl;
import model.readOnlyView.StructureAccesByUsernameImpl;
import model.readOnlyView.StructureAccesROVOImpl;
import model.readOnlyView.StructureAttestationROVOImpl;
import model.readOnlyView.StructureRegleRepecheROVOImpl;
import model.readOnlyView.StudentParcrsAnSemSessImpl;
import model.readOnlyView.TextLoiFormationROVOImpl;
import model.readOnlyView.TextLoisByStructureImpl;
import model.readOnlyView.TextsLoisROVOImpl;
import model.readOnlyView.TypeControleROVOImpl;
import model.readOnlyView.UeDelibROVOImpl;
import model.readOnlyView.UeEvalExportListEtdROVOImpl;
import model.readOnlyView.UeEvalNewROVOImpl;
import model.readOnlyView.UeEvalROVOImpl;
import model.readOnlyView.UeNoteClosedDelibSemROVOImpl;
import model.readOnlyView.UsedAnoImpl;
import model.readOnlyView.UserAccesEcROVOImpl;
import model.readOnlyView.UserByUserNameImpl;
import model.readOnlyView.UtilisateurAccessFormationROVOImpl;
import model.readOnlyView.UtilisateurEtabROVOImpl;
import model.readOnlyView.UtilisateurFormationJuryROVOImpl;
import model.readOnlyView.UtilisateurNiveauFormationROImpl;
import model.readOnlyView.UtilisateurPDTJuryROVOImpl;
import model.readOnlyView.UtilisateursRolesROVOImpl;
import model.readOnlyView.common.RValidation;
import model.readOnlyView.grhUserEtabImpl;
import model.readOnlyView.grhUserImpl;
import model.readOnlyView.grhUserROVOImpl;
import model.readOnlyView.isClosedInscriptionImpl;
import model.readOnlyView.isEtudiantNumeroValideImpl;
import model.readOnlyView.isJuryAllowedAccessImpl;
import model.readOnlyView.isJuryExistROVOImpl;
import model.readOnlyView.isJuryPdtROVOImpl;
import model.readOnlyView.isNivFormAccessAllowedImpl;
import model.readOnlyView.isNivFormSpecAccessAllowedImpl;
import model.readOnlyView.isNivFormSpecOptAccessAllowedImpl;
import model.readOnlyView.isParametredCalendarImpl;
import model.readOnlyView.isRespFormationExistImpl;
import model.readOnlyView.isRespNivFormExistImpl;
import model.readOnlyView.isRespNivFormSpecExistImpl;
import model.readOnlyView.isRespNivFormSpecOptExistImpl;
import model.readOnlyView.isSess1ClosedImpl;

import model.services.common.evaluationApp;

import model.updatableview.NotesModeEnseignementImpl;
import model.updatableview.NotesModeEnseignementInterNewVOImpl;
import model.updatableview.NotesModeEnseignementInterVOImpl;
import model.updatableview.NotesModeEnseignementVOImpl;
import model.updatableview.ResultatsFilUeSemestreVOImpl;
import model.updatableview.common.Semestre;
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
// ---    Mon Oct 05 10:35:14 UTC 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class evaluationAppImpl extends ApplicationModuleImpl implements evaluationApp {
    /**
     * This is the default constructor (do not remove).
     */

    private Set<Ue> uelists=null;
    public evaluationAppImpl() {
    }
  
    /**
     * Container's getter for Parcours.
     * @return Parcours
     */
    public ViewObjectImpl getParcours() {
        return (ViewObjectImpl) findViewObject("Parcours");
    }

    /**
     * Container's getter for Semestres.
     * @return Semestres
     */
    public SemestreROVOImpl getSemestres() {
        return (SemestreROVOImpl) findViewObject("Semestres");
    }

    /**
     * Container's getter for HistoriquesStructures.
     * @return HistoriquesStructures
     */
    public ViewObjectImpl getHistoriquesStructures() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructures");
    }

    /**
     * Container's getter for Sessions.
     * @return Sessions
     */
    public ViewObjectImpl getSessions() {
        return (ViewObjectImpl) findViewObject("Sessions");
    }

    /**
     * Container's getter for AnneeUnivers.
     * @return AnneeUnivers
     */
    public AnneeUniverROVOImpl getAnneeUnivers() {
        return (AnneeUniverROVOImpl) findViewObject("AnneeUnivers");
    }

    /**
     * Container's getter for Etablissements.
     * @return Etablissements
     */
    public ViewObjectImpl getEtablissements() {
        return (ViewObjectImpl) findViewObject("Etablissements");
    }

    /**
     * Container's getter for Departements.
     * @return Departements
     */
    public ViewObjectImpl getDepartements() {
        return (ViewObjectImpl) findViewObject("Departements");
    }

    /**
     * Container's getter for CalendrierDeliberationsRO.
     * @return CalendrierDeliberationsRO
     */
    public ViewObjectImpl getCalendrierDeliberationsRO() {
        return (ViewObjectImpl) findViewObject("CalendrierDeliberationsRO");
    }
 
    /**
     * Container's getter for CalendrierDeliberations.
     * @return CalendrierDeliberations
     */
    public ViewObjectImpl getCalendrierDeliberations() {
        return (ViewObjectImpl) findViewObject("CalendrierDeliberations");
    }

    /**
     * Container's getter for NiveauFormationParcours.
     * @return NiveauFormationParcours
     */
    public ViewObjectImpl getNiveauFormationParcours() {
        return (ViewObjectImpl) findViewObject("NiveauFormationParcours");
    }

    /**
     * Container's getter for CalendrierDeliberationVO.
     * @return CalendrierDeliberationVO
     */
    public ViewObjectImpl getCalendrierDeliberationVO() {
        return (ViewObjectImpl) findViewObject("CalendrierDeliberationVO");
    }

    /**
     * Container's getter for ParcoursParamSaisieNoteEc.
     * @return ParcoursParamSaisieNoteEc
     */
    public ViewObjectImpl getParcoursParamSaisieNoteEc() {
        return (ViewObjectImpl) findViewObject("ParcoursParamSaisieNoteEc");
    }


    /**
     * Container's getter for TypeControles.
     * @return TypeControles
     */
    public TypeControleROVOImpl getTypeControles() {
        return (TypeControleROVOImpl) findViewObject("TypeControles");
    }

    /**
     * Container's getter for ModeControles.
     * @return ModeControles
     */
    public ModeControleROVOImpl getModeControles() {
        return (ModeControleROVOImpl) findViewObject("ModeControles");
    }

    /**
     * Container's getter for ModeControleEc.
     * @return ModeControleEc
     */
    public ViewObjectImpl getModeControleEc() {
        return (ViewObjectImpl) findViewObject("ModeControleEc");
    }


    /**
     * Container's getter for EtablissementToDepartement1.
     * @return EtablissementToDepartement1
     */
    public ViewLinkImpl getEtablissementToDepartement1() {
        return (ViewLinkImpl) findViewLink("EtablissementToDepartement1");
    }

    /**
     * Container's getter for NiveauFormationParcourToCalendrierDelib1.
     * @return NiveauFormationParcourToCalendrierDelib1
     */
    public ViewLinkImpl getNiveauFormationParcourToCalendrierDelib1() {
        return (ViewLinkImpl) findViewLink("NiveauFormationParcourToCalendrierDelib1");
    }


    public void initAno(Integer id_ano){
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "EVAL.InitAnonymat(?);" + " END;"), 0);
        try {
            statement.setInt(1, id_ano);
            statement.execute();
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
    
    public List<Long> getSelectedUserFor() {
        List<Long> usersformslist = new ArrayList<>();
        ViewObject vo = getUtilisateurFormationJuryROVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                usersformslist.add(Long.parseLong(rw.getAttribute("IdUtilisateur").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return usersformslist;
    }
    
    public Map<Long,Long> getSelectedFilEcPrcrs() {
        Map<Long,Long> fileclist = new HashMap<Long,Long>();
        //ViewObject vo = getFiliereUeSemestreEcPrcrs();
        ViewObject vo = getFiliereUeSemEcByResp();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isEcSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                fileclist.put(Long.parseLong(rw.getAttribute("IdFiliereUeSemstreEc").toString()),
                              Long.parseLong(rw.getAttribute("IdParcoursMaquetAnnee").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return fileclist;
    }
    
    public HashMap<String,String> getSelectedFiliereUePrcrs() {
        HashMap<String,String> filuelist = new HashMap<>();
        ViewObject vo = getFiliereUeSemestres();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                //IdFiliereUeSemstre
                filuelist.put(rw.getAttribute("Codification").toString(),
                              rw.getAttribute("IdParcoursMaquetAnnee").toString());
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return filuelist;
    }
    
    public List<Long> getSelectedRole() {
        List<Long> roleslist = new ArrayList<>();
        ViewObject vo = getRolesVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isRoleSeleced", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                roleslist.add(Long.parseLong(rw.getAttribute("IdRole").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return roleslist;
    }
    
    public List<Long> getSelectedFonction() {
        List<Long> fonctionslist = new ArrayList<>();
        ViewObject vo = getFonctionnalitesVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isFonctionSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                fonctionslist.add(Long.parseLong(rw.getAttribute("IdFonctionnalite").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return fonctionslist;
    }
    
    public Integer clotureEcUe(Integer fil_ue, Integer calendrier, String action, Integer utilisateur, Long prcrs_maq) {
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.close_all_saisie_nme(?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(2, fil_ue);
            cls.setInt(3, calendrier);
            cls.setString(4, action);
            cls.setInt(5, utilisateur);
            cls.setLong(6, prcrs_maq);
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
        // getDBTransaction().clearEntityCache(null);
        cls = null;
        return result;
    }
    
    public void InitialiserNoteModeEns(Integer fil_ue_sem_ec, Integer calendrier_deliberation, Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INITIALISATION.initialiserNoteModeEns(?,?,?);" + " END;"), 0);
        try {
            statement.setInt(1, fil_ue_sem_ec);
            statement.setInt(2, calendrier_deliberation);
            statement.setInt(3, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
        /*
        CallableStatement cstmt = null;

        String initModeEnsSql = "begin INITIALISATION.initialiserNoteModeEns(?,?,?); end;";
        //String initModeEnsSql = "begin initialisation.initialisernotemodeens(?,?,?); end ;";
        try {
            cstmt = this.getDBTransaction().createCallableStatement(initModeEnsSql, 0);
            cstmt.setInt(1, fil_ue_sem_ec);
            cstmt.setInt(2, calendrier_deliberation);
            cstmt.setInt(3, utilisateur);
            cstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
*/
    }
    /*
     * PROCEDURE Initialiser(
  prcrs_mq_an_id PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, 
  calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE,
  utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE
)
     * */
   
    public void initialiserNoteModeEnsFiliere(Integer anne_univers, Integer niv_fomr_parcours, Integer session_id,
                                              Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INITIALISATION.initialiserNoteModeEnsFiliere(?,?,?,?);" +
                                          " END;"), 0);
        try {
            statement.setInt(1, anne_univers);
            statement.setInt(2, niv_fomr_parcours);
            statement.setInt(3, session_id);
            statement.setInt(4, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    /*
     * traitement_enjambiste(parcours,anne,utilisateur);
     * */
    public void traitementEnjambiste(Integer niv_fomr_parcours,Integer anne_univers, Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "EVAL.traitement_enjambiste(?,?,?);" +
                                          " END;"), 0);
        try {
            statement.setInt(1, niv_fomr_parcours);
            statement.setInt(2, anne_univers);
            statement.setInt(3, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }

    public void reconduireRedoublantToSess2(Integer anne_univers, Integer niv_fomr_parcours, Integer session_id,
                                            Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INITIALISATION.reconduireRedoublantToSess2(?,?,?,?);" + " END;"),
                                         0);
        try {
            statement.setInt(1, anne_univers);
            statement.setInt(2, niv_fomr_parcours);
            statement.setInt(3, session_id);
            statement.setInt(4, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    //reconduireToSess2(anne_univers,niv_fomr_parcours,session_id ,semestre_id ,utilisateur )
    public void reconduireToSess2(Integer anne_univers, Integer niv_fomr_parcours, Integer session_id,Integer semestre_id,
                                            Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INITIALISATION.reconduireToSess2(?,?,?,?,?);" + " END;"),
                                         0);
        try {
            statement.setInt(1, anne_univers);
            statement.setInt(2, niv_fomr_parcours);
            statement.setInt(3, session_id);
            statement.setInt(4, semestre_id);
            statement.setInt(5, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }

    public void CalculMoyenneAnnuel(Integer anne_univers, Integer parcours, Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "EVAL.CalculMoyenneAnnuelProc(?,?,?);" + " END;"), 0);
        try {
            statement.setInt(1, anne_univers);
            statement.setInt(2, parcours);
            statement.setInt(3, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }

    public void UpdateResultat(Integer anne_univers, Integer parcours, Integer formation, Integer utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "EVAL.UpdateResultatProc(?,?,?,?);" + " END;"), 0);
        try {
            statement.setInt(1, anne_univers);
            statement.setInt(2, parcours);
            statement.setInt(3, formation);
            statement.setInt(4, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    /**
     * Container's getter for EtudiantResultAnnuelDelibROVO1.
     * @return EtudiantResultAnnuelDelibROVO1
     */
    public EtudiantResultAnnuelDelibROVOImpl getDeliberationAnnuelle() {
        return (EtudiantResultAnnuelDelibROVOImpl) findViewObject("DeliberationAnnuelle");
    }

    /**
     * Container's getter for UeEvalROVO1.
     * @return UeEvalROVO1
     */
    public UeEvalROVOImpl getUeEvalRO() {
        return (UeEvalROVOImpl) findViewObject("UeEvalRO");
    }

    /**
     * Container's getter for EcEvalROVO1.
     * @return EcEvalROVO1
     */
    public EcEvalROVOImpl getEcEvalRO() {
        return (EcEvalROVOImpl) findViewObject("EcEvalRO");
    }

    /**
     * Container's getter for UeEvalROToEcEvalROVL1.
     * @return UeEvalROToEcEvalROVL1
     */
    public ViewLinkImpl getUeEvalROToEcEvalROVL1() {
        return (ViewLinkImpl) findViewLink("UeEvalROToEcEvalROVL1");
    }

    public Integer closeAllUe(Integer parcours, Integer calendrier, Integer semestre, String action,
                                 Integer utilisateur) {
        Integer code = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.cloturer_all_ue(?,?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, parcours);
            statement.setInt(3, calendrier);
            statement.setInt(4, semestre);
            statement.setString(5, action);
            statement.setInt(6, utilisateur);
            statement.executeUpdate();
            code = statement.getInt(1);

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
        // getDBTransaction().clearEntityCache(null);
        return code;
    }
    
    //getSess2CalendByAnAndSem(annee , semestre ,parcours)
    public Long getCalSess2(Long annee, Long semestre, Long parcours) {
        Long code = new Long(0);
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  initialisation.getSess2CalendByAnAndSem(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.LONGVARCHAR);
            statement.setLong(2, annee);
            statement.setLong(3, semestre);
            statement.setLong(4, parcours);
            statement.executeUpdate();
            code = statement.getLong(1);

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
        // getDBTransaction().clearEntityCache(null);
        return code;
    }

    public Integer isCompensRuleDefined(Long parcours, Integer annee) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  EVAL.iscompensable(?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcours);
            statement.setInt(3, annee);
            statement.executeUpdate();
            code = statement.getInt(1);

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
        // getDBTransaction().clearEntityCache(null);
        return code;
    }

    /**
     * Container's getter for ModeControleEvalROVO1.
     * @return ModeControleEvalROVO1
     */
    public ModeControleEvalROVOImpl getModeControleEvalRO() {
        return (ModeControleEvalROVOImpl) findViewObject("ModeControleEvalRO");
    }

    /**
     * Container's getter for EcEvalROToTypeControleEvalROVL1.
     * @return EcEvalROToTypeControleEvalROVL1
     */
    public ViewLinkImpl getEcEvalROToTypeControleEvalROVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalROToTypeControleEvalROVL1");
    }

    /**
     * Container's getter for TypeControleVO1.
     * @return TypeControleVO1
     */
    public ViewObjectImpl getTypeControle() {
        return (ViewObjectImpl) findViewObject("TypeControle");
    }

    /**
     * Container's getter for NotesModeEnseignementInterVO1.
     * @return NotesModeEnseignementInterVO1
     */
    public NotesModeEnseignementInterVOImpl getNotesModeEnseignementInterVO1() {
        return (NotesModeEnseignementInterVOImpl) findViewObject("NotesModeEnseignementInterVO1");
    }

    /**
     * Container's getter for SaisieNotesGroupesSaisieLOV2.
     * @return SaisieNotesGroupesSaisieLOV2
     */
    public SaisieNotesGroupesSaisieLOVImpl getSaisieNotesGroupesSaisieLOV() {
        return (SaisieNotesGroupesSaisieLOVImpl) findViewObject("SaisieNotesGroupesSaisieLOV");
    }

    /**
     * Container's getter for SaisieNotesFiltresLOV1.
     * @return SaisieNotesFiltresLOV1
     */
    public SaisieNotesFiltresLOVImpl getSaisieNotesFiltresLOV() {
        return (SaisieNotesFiltresLOVImpl) findViewObject("SaisieNotesFiltresLOV");
    }

    /**
     * Container's getter for NotesModeEnseignementVO1.
     * @return NotesModeEnseignementVO1
     */
    public NotesModeEnseignementVOImpl getNotesModeEnseignement() {
        return (NotesModeEnseignementVOImpl) findViewObject("NotesModeEnseignement");
    }

    /**
     * Container's getter for UeDelibROVO1.
     * @return UeDelibROVO1
     */
    public UeDelibROVOImpl getUeDelibRO() {
        return (UeDelibROVOImpl) findViewObject("UeDelibRO");
    }

    /**
     * Container's getter for EtudiantResultUeDelibROVO1.
     * @return EtudiantResultUeDelibROVO1
     */
    public EtudiantResultUeDelibROVOImpl getEtudiantResultUeDelibRO() {
        return (EtudiantResultUeDelibROVOImpl) findViewObject("EtudiantResultUeDelibRO");
    }

    public void getNotesEtudiants(Long type_ctrl_ec, Long id_cal_del) {
        ViewObject vo = getNotesModeEnseignementInterVO1();
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementInterVOCriteria");
        VariableValueManager vvm = vc.ensureVariableManager();
        vvm.setVariableValue("id_mode_ctrl_ec_var", type_ctrl_ec);
        vvm.setVariableValue("id_cal_delib_var", id_cal_del);
        vo.applyViewCriteria(vc);
        vo.executeQuery();

    }

    public void getNotesEtudiantsModeEns(Long type_ctrl, Long id_cal_del) {
        ViewObject vo = getNotesModeEnseignement();
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("NotesModeEnseignementVOCriteria");
        VariableValueManager vvm = vc.ensureVariableManager();
        vvm.setVariableValue("id_type_ctrl_var", type_ctrl);
        vvm.setVariableValue("id_cal_delib_var", id_cal_del);
        vo.applyViewCriteria(vc);
        vo.executeQuery();

    }

    public Integer clotureSaisieNotesInter(Long fil_sem_ec, Long type_control, Long mode_control, Long calendrier,
                                           String action, Long utilisateur) {
        //(fil_sem_ec, type_control, mode_controle, calendrier, actionE, utilisateur )
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.cloturer_saisie_inter(?,?,?,?,?,?); end ;";
        Integer result = 0;
 
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_sem_ec);
            cls.setLong(3, type_control);
            cls.setLong(4, mode_control);
            cls.setLong(5, calendrier);
            cls.setString(6, action);
            cls.setLong(7, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
/*
 * 
 * ouvrir_saisie_inter(
    parcours ,fil_ue ,fil_sem_ec ,type_control ,mode_controle ,calendrier , utilisateur   ) 
 * */
    public Integer reouvrirSaisieNotesInter(Long parcours, Long fil_ue,
    Long fil_sem_ec, Long type_control, Long mode_control, Long calendrier, Long utilisateur) {
        //(fil_sem_ec, type_control, mode_controle, calendrier, actionE, utilisateur )
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_saisie_inter(?,?,?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, fil_ue);
            cls.setLong(4, fil_sem_ec);
            cls.setLong(5, type_control);
            cls.setLong(6, mode_control);
            cls.setLong(7, calendrier);
            cls.setLong(8, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    public Integer reouvrirInter(Long fil_sem_ec, Long type_control, Long mode_control, Long calendrier, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_inter(?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_sem_ec);
            cls.setLong(3, type_control);
            cls.setLong(4, mode_control);
            cls.setLong(5, calendrier);
            cls.setLong(6, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    /*ouvrir_fil_ec(
    fil_ue validation_delib_ue.id_filiere_ue_semstre%TYPE,
    fil_sem_ec validation_saisie_note.id_filiere_ue_semstre_ec%TYPE,
    type_control validation_saisie_note.id_type_controle%TYPE, 
    calendrier validation_saisie_note.id_calendrier_deliberation%TYPE,
    utilisateur utilisateurs.id_utilisateur%TYPE,
    parcours_maq PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE
  )*/
    public Integer reouvrirFilEc(Long fil_ue, Long fil_sem_ec, Long type_control, Long calendrier, Long utilisateur, Long prcrs_maq) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_fil_ec(?,?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue);
            cls.setLong(3, fil_sem_ec);
            cls.setLong(4, type_control);
            cls.setLong(5, calendrier);
            cls.setLong(6, utilisateur);
            cls.setLong(7, prcrs_maq);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    public Integer reouvrirFilUe(Long parcours, Long fil_ue, Long calendrier, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_fil_ue(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, fil_ue);
            cls.setLong(4, calendrier);
            cls.setLong(5, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    public Integer reouvrirPrcrsSem(Long parcours_maq, Long calendrier, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_parcours_sem(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setLong(3, calendrier);
            cls.setLong(4, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    public Integer reouvrirPrcrsAn(Long parcours, Long calendrier, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_parcours_an(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
            cls.setLong(4, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isClosedSaisieNotesInter(Long fil_sem_ec, Long type_control, Long mode_control, Long calendrier) {
        //is_closed_note_inter(fil_sem_ec,type_control, mode_controle,calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.is_closed_note_inter(?,?,?,?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_sem_ec);
            cls.setLong(3, type_control);
            cls.setLong(4, mode_control);
            cls.setLong(5, calendrier);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long isPrcrsAttSpecExist(Long parcours_maq) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.IsParcoursAttSpecExist(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.registerOutParameter(1, Types.BIGINT);
            cls.executeUpdate();

            result = cls.getLong(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isFilUeComplexe(Long parcours_maq ,Long fil_ue) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.IsFilUeComplexe(?,?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setLong(3, fil_ue);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    public Integer isAllFilEcComplexe(Long parcours_maq ,Long fil_ue) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.Is_AllFilEC_Complexe(?,?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setLong(3, fil_ue);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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

    public void getEtudiantSansNote(){
        ViewObject vo = getNotesModeEnseignementInterNew();
        ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("EtudiantsSansNotes");
        vo.applyViewCriteria(vc);
        vo.executeQuery();
    }
    
    public void resetEtudiantSansNote(){
        ViewObject vo = getNotesModeEnseignementInterNew();
        vo.getViewCriteriaManager().clearViewCriterias();
        vo.executeQuery();
    }
    
    /*public void getEtudiantSansNote(){
        ViewObject vo = this.getViewObjects();
            //Get all it's ViewCriteria using the ViewCriteriaManager of the ViewObject
            ViewCriteriaManager vcm = vo.getViewCriteriaManager();
            //Get the specified View Criteria
            ViewCriteria vc = vcm.getViewCriteria(MyViewCriteriaName);
            //Apply the ViewCriteria to the ViewObject
            vo.applyViewCriteria(vc);ViewObject vo = this.getViewObjectFromIteratorName(MyViewObjectIteratorName)
        //Get all it's ViewCriteria using the ViewCriteriaManager of the ViewObject
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        //Get the specified View Criteria
        ViewCriteria vc = vcm.getViewCriteria(MyViewCriteriaName);
        //Apply the ViewCriteria to the ViewObject
        vo.applyViewCriteria(vc);
    }*/
    /*
     * isJuryDefined( parcours ,annee ,sess ,semestre)
     * */
    public Integer isJuryDefined(Integer parcours, Integer annee, Integer sess, Integer semestre) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.is_jury_defined(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, annee);
            cls.setLong(4, sess);
            cls.setLong(5, semestre);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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

    public Integer isClosedSaisieNotes(Long fil_sem_ec, Long type_control, Long calendrier, Long prcrs_maq) {
        //is_closed_note_inter(fil_sem_ec,type_control, mode_controle,calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.is_closed_note_nme(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_sem_ec);
            cls.setLong(3, type_control);
            cls.setLong(4, calendrier);
            cls.setLong(5, prcrs_maq);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();

            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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

    public void findAndUpdateNoteInter(Long idNoteModeEnsInter, Float note, String uti_modif) {
        NotesModeEnseignementInterVOImpl noteModeEnsInter = getNotesModeEnseignementInterVO1();
        Key key = new Key(new Object[] { idNoteModeEnsInter });
        RowSetIterator rsi = noteModeEnsInter.createRowSetIterator(null);
        Row row = rsi.findByKey(key, 1)[0];
        row.setAttribute("Note", note);
        row.setAttribute("UtiModifie", uti_modif);
    }

    public void findAndUpdateNote(Long idNoteModeEns, Float note) {
       // NotesModeEnseignementVOImpl noteModeEns = getNotesModeEnseignement();
        NotesModeEnseignementImpl noteModeEns = getNotesModeEnseignement1();
        Key key = new Key(new Object[] { idNoteModeEns });
        RowSetIterator rsi = noteModeEns.createRowSetIterator(null);
        Row row = rsi.findByKey(key, 1)[0];
        row.setAttribute("Note", note);

    }

    public Integer clotureSaisieNotes(Long fil_sem_ec, Long type_control, Long calendrier, String action,
                                      Long utilisateur, Long prcrs_maq) {
        /* cloturer_saisie_nme(fil_sem_ec, type_control, calendrier,action, utilisateur ) */
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.cloturer_saisie_nme(?,?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_sem_ec);
            cls.setLong(3, type_control);
            cls.setLong(4, calendrier);
            cls.setString(5, action);
            cls.setLong(6, utilisateur);
            cls.setLong(7, prcrs_maq);
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

    public Integer reouvrirSaisieNotes(Long parcours, Long fil_ue,
    Long fil_sem_ec, Long type_control, Long calendrier, Long utilisateur) {
        //(fil_sem_ec, type_control, mode_controle, calendrier, actionE, utilisateur )
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_saisie(?,?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, fil_ue);
            cls.setLong(4, fil_sem_ec);
            cls.setLong(5, type_control);
            cls.setLong(6, calendrier);
            cls.setLong(7, utilisateur);
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

/*
 * CalculMoyenneEc(Integer anne ,Integer semestre ,Integer parcours ,Integer fileUesem ,Integer idFilieUeSemEc, Integer typeControle ,Integer calendrDelib ,Integer utilisateur)
 * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void calculMoyenneEc(Integer anne ,Integer semestre ,Integer parcours ,Integer fileUesem ,Integer idFilieUeSemEc, Integer typeControle ,Integer calendrDelib ,Integer utilisateur) {
        CallableStatement callableStat = null;
        String formSql = "begin eval.CalculMoyenneEc(?,?,?,?,?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setInt(1, anne);
            callableStat.setInt(2, semestre);
            callableStat.setInt(3, parcours);
            callableStat.setInt(4, fileUesem);
            callableStat.setInt(5, idFilieUeSemEc);
            callableStat.setInt(6, typeControle);
            callableStat.setInt(7, calendrDelib);
            callableStat.setInt(8, utilisateur);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);
    }
    
    /*
     *CalculMoyenneEcFinalProc(anne ,semestre ,parcours ,fileUesem ,idFilieUeSemEc,calendrDelib, utilisateur) 
     * */
    public void CalculMoyenneEcFinalProc(Integer anne ,Integer semestre ,Integer parcours ,Integer fileUesem ,Integer idFilieUeSemEc,Integer calendrDelib, Integer utilisateur) {
        CallableStatement callableStat = null;
        String formSql = "begin eval.CalculMoyenneEcFinalProc(?,?,?,?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setInt(1, anne);
            callableStat.setInt(2, semestre);
            callableStat.setInt(3, parcours);
            callableStat.setInt(4, fileUesem);
            callableStat.setInt(5, idFilieUeSemEc);
            callableStat.setInt(6, calendrDelib);
            callableStat.setInt(7, utilisateur);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);
    }
    
    public void calculMoyenneUe(Integer calendrier, Integer fileUesem, Integer utilisateur) {
        //---CalculMoyenneUeProc(calendrier,fileUesem,utilisateur)

        CallableStatement callableStat = null;
        String formSql = "begin eval.CalculMoyenneUeProc(?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setInt(1, calendrier);
            callableStat.setInt(2, fileUesem);
            callableStat.setInt(3, utilisateur);

            callableStat.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);
    }
    
    public Integer nbrColonneUe() {
        //---CalculMoyenneUeProc(calendrier,fileUesem,utilisateur)
        Integer i = null;
        ResultSet rs = null;
        CallableStatement callableStat = null;
        String formSql =
            "begin select  max(count(nmei.id_note_mode_ens_inter) ) as max from notes_mode_enseignement_inter nmei where  nmei.id_calendrier_deliberation=103 group by nmei.id_inscription_ped_sem_ue; end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);


            callableStat.executeUpdate();
            rs = (ResultSet) callableStat.getObject(0);
            i = rs.getInt("max");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception c) {
                }
        }
        return i;
    }

    public Integer clotureUe(Long fil_ue, Long calendrier, String action, Long utilisateur) {
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.cloturer_ue(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue);
            cls.setLong(3, calendrier);
            cls.setString(4, action);
            cls.setLong(5, utilisateur);
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

    public Integer publierUe(Long fil_ue, Long parcours_maq, Long calendrier, String action, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.publier_ue(?,?,?,?,?); end;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue);
            cls.setLong(3, calendrier);
            cls.setString(4, action);
            cls.setLong(5, utilisateur);
            cls.setLong(6, parcours_maq);
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

    public Integer deliberationUe(Long fil_ue, Long calendrier, String action, Long utilisateur) {
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.deliberer_ue(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue);
            cls.setLong(3, calendrier);
            cls.setString(4, action);
            cls.setLong(5, utilisateur);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateRoleNivFormation(Integer idUser, Integer idNivFormation, String role,Integer utimodif,Integer id_uti_niv_formation) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin UPDATE UTILISATEUR_NIVEAUX_FORMATIONS unf SET unf.ROLE=?, unf.UTI_MODIFIE=?\n" + 
        "                    WHERE unf.ID_NIVEAU_FORMATION=?\n" + 
        "                    AND unf.ID_UTILIS_NIVEAU_FORMATION=?\n" + 
        "                    AND unf.ID_UTILISATEUR=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, idNivFormation);
            cls.setInt(4, id_uti_niv_formation);
            cls.setInt(5, idUser);
            cls.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // getDBTransaction().clearEntityCache(null);

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
        // getDBTransaction().clearEntityCache(null);

    }
    

    public Integer isClosedUe(Long fileUesem, Long calendrier) {
        //---is_closed_ue(fileUesem,calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_closed_ue(?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fileUesem);
            cls.setLong(3, calendrier);
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
        // getDBTransaction().clearEntityCache(null);

        return result;
    }

    public Integer isDelibrateUe(Long fileUesem, Long calendrier, Long prcrs_maq) {
        //---is_delibrate_ue(fileUesem,calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_delibrate_ue(?,?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fileUesem);
            cls.setLong(3, calendrier);
            cls.setLong(4, prcrs_maq);
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
        // getDBTransaction().clearEntityCache(null);

        return result;
    }

    private RValidation transformStructToDomain(java.sql.Struct rawObject) throws SQLException {
        RValidation validation = new RValidation();
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            validation.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return validation;
    }

    public void calculMoyenneSemestrielle(Long calendrier, Long utilisateur) {
        //---PROCEDURE CalculMoyenneSemestreProc(calendrier,utilisateur)

        CallableStatement callableStat = null;
        String formSql = "begin eval.CalculMoyenneSemestreProc(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, calendrier);
            callableStat.setLong(2, utilisateur);

            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);

    }

/*compenser(parcours ,anne ,calendrier,utilisateur)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void compenser(Integer parcours, Integer anne,Integer calendrier, Integer utilisateur) {
        CallableStatement callableStat = null;
        String formSql = "begin eval.compenser(?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setInt(1, parcours);
            callableStat.setInt(2, anne);
            callableStat.setInt(3, calendrier);
            callableStat.setInt(4, utilisateur);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);

    }
   /* updateAbsentDetailNote(pma_id PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, 
                                    calendrier_id CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE, 
                                    ips_id INSCRIPTION_PED_SEMESTRE.ID_INSCRIPTION_PED_SEMESTRE%TYPE,
                                    utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateIsAbsent(Long parcours_maq, Long calendrier_id, Long ips_id, Integer utilisateur) {
        //long startTime = System.currentTimeMillis();
        CallableStatement callableStat = null;
        String formSql = "begin REFONTEPKG.updateAbsentDetailNote(?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, parcours_maq);
            callableStat.setLong(2, calendrier_id);
            callableStat.setLong(3, ips_id);
            callableStat.setInt(4, utilisateur);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
    /*updateAbsentDetailNote(pma_id PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, 
                                    calendrier_id CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateIsAbs(Long parcours_maq, Long calendrier_id) {
        //long startTime = System.currentTimeMillis();
        CallableStatement callableStat = null;
        String formSql = "begin SYNCHRONISATIONSCOL.updateAbsentDetailNote(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, parcours_maq);
            callableStat.setLong(2, calendrier_id);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
    /*DelibererDefSemestre(calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE,
  prcrs_maq_annee PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DelibererDefSemestre(Long parcours_maq, Long calendrier_id) {
        //long startTime = System.currentTimeMillis();
        CallableStatement callableStat = null;
        String formSql = "begin SYNCHRONISATIONSCOL.DelibererDefSemestre(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, calendrier_id);
            callableStat.setLong(2, parcours_maq);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DeliReclamation(Long parcours_maq, Long calendrier_id) {
        //long startTime = System.currentTimeMillis();
        CallableStatement callableStat = null;
        String formSql = "begin SYNCHRONISATIONSCOL.DelibererDetailNote(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, parcours_maq);
            callableStat.setLong(2, calendrier_id);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateIsToPublish(Long calendrier_id, Long ips_id) {
        //long startTime = System.currentTimeMillis();
        CallableStatement callableStat = null;
        String formSql = "begin SYNCHRONISATIONSCOL.UpdateIsToPublish(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, calendrier_id);
            callableStat.setLong(2, ips_id);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
  /*  DelibererDetailNote(pma_id PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, 
                                    calendrier_id CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE, 
                                    ips_id INSCRIPTION_PED_SEMESTRE.ID_INSCRIPTION_PED_SEMESTRE%TYPE,
                                    utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/
  @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
  public void DelibererDetailNote(Long parcours_maq, Long calendrier_id, Long ips_id, Integer utilisateur) {
      // startTime = System.currentTimeMillis();
      CallableStatement callableStat = null;
      String formSql = "begin REFONTEPKG.DelibererDetailNote(?,?,?,?);  end ;";
      try {
          callableStat = getDBTransaction().createCallableStatement(formSql, 0);
          callableStat.setLong(1, parcours_maq);
          callableStat.setLong(2, calendrier_id);
          callableStat.setLong(3, ips_id);
          callableStat.setInt(4, utilisateur);
          callableStat.executeUpdate();
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      } finally {
          if (callableStat != null) {
              try {
                  callableStat.close();
              } catch (Exception c) {
              }
          }
      }
      /*long endTime = System.currentTimeMillis();
      long elapsedTime = endTime - startTime;
      System.out.println("Temps d'exécution de la procédure DelibererDetailNote : " + elapsedTime + " ms");
      */
      // getDBTransaction().clearEntityCache(null);

  }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateResultatCycle(Long code_fr, Long parcours_maq, Long annee ,Integer utilisateur) {
        CallableStatement callableStat = null;
        String formSql = "begin REFONTEPKG.UpdateResultatCycle(?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, code_fr);
            callableStat.setLong(2, parcours_maq);
            callableStat.setLong(3, annee);
            callableStat.setInt(4, utilisateur);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DelibDetailsNote() {
        CallableStatement callableStat = null;
        String formSql = "begin SYNCHRONISATIONSCOL.DelibDetailsNote; end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }
    
    /*RenonciationNote(p_etd_id ETUDIANTS.ID_ETUDIANT%TYPE, p_prcrs_maq PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, 
      p_fil_ec FILIERE_UE_SEMSTRE_EC.ID_FILIERE_UE_SEMSTRE_EC%TYPE, p_user UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void RenoncerNote(Long p_etd_id, Long p_prcrs_maq, Long p_fil_ec, Long p_uti, String p_date) {
        CallableStatement callableStat = null;
        String formSql = "begin REFONTEPKG.RenoncerNoteEtd(?,?,?,?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, p_etd_id);
            callableStat.setLong(2, p_prcrs_maq);
            callableStat.setLong(3, p_fil_ec);
            callableStat.setLong(4, p_uti);
            callableStat.setString(5, p_date);
            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
        // getDBTransaction().clearEntityCache(null);
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isDelibratedSemestre(Long parcours, Long calendrier) {
        //  FUNCTION is_delibrated_semestre(parcours_maq, calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_delibrated_semestre(?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isDelibSemestre(Long prcrsMaqId, Long semId, Long anId, Long sessId) {
        //  FUNCTION is_delib_semestre(parcours_maq ,sem_id ,an_id ,sess_id  ) 
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_delib_semestre(?,?,?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrsMaqId);
            cls.setLong(3, semId);
            cls.setLong(4, anId);
            cls.setLong(5, sessId);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isDiplomante(Long parcours_maq) {
        //  FUNCTION getDiplomanteParcous(parcours_maq)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.getDiplomanteParcous(?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
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
    

    public Integer isClosedSemestre(Long parcours, Long calendrier) {
        //    FUNCTION is_closed_semestre(parcours_maq ,calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_closed_semestre(?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }

    public Integer CompenserSemestre(Long prcrs_maq, Integer calendrier, String action, Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.compenser_semestre(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setLong(2, prcrs_maq);
            statement.setLong(3, calendrier);
            statement.setString(4, action);
            statement.setLong(5, utilisateur);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Compensation en cours ... ");
            System.out.println("Erreur message : " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)));*/

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
        return code;
    }
    
    /**
     * Container's getter for DelibSemRepCritereCompLOV1.
     * @return DelibSemRepCritereCompLOV1
     */
    public DelibSemRepCritereCompLOVImpl getDelibSemRepCritereCompLOV() {
        return (DelibSemRepCritereCompLOVImpl) findViewObject("DelibSemRepCritereCompLOV");
    }

    /**
     * Container's getter for DelibSemRepFiltreLOV1.
     * @return DelibSemRepFiltreLOV1
     */

    public DelibSemRepFiltreLOVImpl getDelibSemRepFiltreLOV() {
        return (DelibSemRepFiltreLOVImpl) findViewObject("DelibSemRepFiltreLOV");
    }

    /**
     * Container's getter for DelibSemRepecherSurLOV1.
     * @return DelibSemRepecherSurLOV1
     */
    public DelibSemRepecherSurLOVImpl getDelibSemRepecherSurLOV() {
        return (DelibSemRepecherSurLOVImpl) findViewObject("DelibSemRepecherSurLOV");
    }

    /**
     * Container's getter for DeliberationSemestrielle1.
     * @return DeliberationSemestrielle1
     */
    public DeliberationSemestrielleImpl getDeliberationSemestrielle() {
        return (DeliberationSemestrielleImpl) findViewObject("DeliberationSemestrielle");
    }

    /**
     * Container's getter for EtudiantNoteInter1.
     * @return EtudiantNoteInter1
     */
    public EtudiantNoteInterImpl getEtudiantNoteInter() {
        return (EtudiantNoteInterImpl) findViewObject("EtudiantNoteInter");
    }

    /**
     * Container's getter for ListeEtudiantInsc1.
     * @return ListeEtudiantInsc1
     */
    public ListeEtudiantInscImpl getListeEtudiantInsc() {
        return (ListeEtudiantInscImpl) findViewObject("ListeEtudiantInsc");
    }

    /**
     * Container's getter for ListeModeControl1.
     * @return ListeModeControl1
     */
    public ListeModeControlImpl getListeModeControl() {
        return (ListeModeControlImpl) findViewObject("ListeModeControl");
    }

    /**
     * Container's getter for ListeEc1.
     * @return ListeEc1
     */
    public ListeEcImpl getListeEc() {
        return (ListeEcImpl) findViewObject("ListeEc");
    }

    /**
     * Container's getter for FiltreAnuelleROVO1.
     * @return FiltreAnuelleROVO1
     */
    public FiltreAnuelleROVOImpl getFiltreAnuelleROVO1() {
        return (FiltreAnuelleROVOImpl) findViewObject("FiltreAnuelleROVO1");
    }

    /**
     * Container's getter for FiltreModeDelibAn1.
     * @return FiltreModeDelibAn1
     */
    public FiltreModeDelibAnImpl getFiltreModeDelibAn1() {
        return (FiltreModeDelibAnImpl) findViewObject("FiltreModeDelibAn1");
    }

    public void SortByCredit(Integer credit1, Integer credit2) {
        ViewObjectImpl voi = this.getDeliberationAnnuelle();
        ViewCriteria vc = voi.getViewCriteria("SortByCredit");
        voi.appendViewCriteria(vc);
        voi.setNamedWhereClauseParam("cred1", credit1);
        voi.setNamedWhereClauseParam("cred2", credit2);
        //ResultSet rs =
        voi.executeQuery();
    }

    public void SortByMoyenne(Integer moyenne1, Integer moyenne2) {
        ViewObjectImpl voi = this.getDeliberationAnnuelle();
        ViewCriteria vc = voi.getViewCriteria("SortByMoyenne");
        voi.appendViewCriteria(vc);
        voi.setNamedWhereClauseParam("moy1", moyenne1);
        voi.setNamedWhereClauseParam("moy2", moyenne2);
        //ResultSet rs =
        voi.executeQuery();
    }

    public Integer isPublishedAllSemestre(Long parcours_maq, Long sess) {
        Integer is_publish = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_published_all_semestre(?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, sess);
            statement.executeUpdate();
            is_publish = statement.getInt(1);
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
        return is_publish;
    }

    public Integer isCosedAllSemestre(Integer parcours, Integer annee, Integer session) {
        Integer isclosed = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_closed_all_semestre(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, parcours);
            statement.setInt(3, annee);
            statement.setInt(4, session);
            statement.executeUpdate();

            isclosed = statement.getInt(1);
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
        return isclosed;
    }
    /*FUNCTION cloturer_semestre(
         validation_delib_semestre.id_niveau_formation_parcours%TYPE,
        calendrier validation_delib_semestre.id_calendrier_deliberation%TYPE,
        semestre calendrier_deliberation.id_semestre%TYPE,
        action validation_delib_ue.ue_deliberee%TYPE,
        utilisateur utilisateurs.id_utilisateur%TYPE
      )*/
    public Integer closeSemestre(Long parcours_maq, Integer calendrier,  String action,
                                 Integer utilisateur) {
        //Integer semestre,
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.cloturer_semestre(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setLong(2, parcours_maq);
            statement.setInt(3, calendrier);
            //statement.setInt(4, semestre);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Erreur message : " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)));
*/
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
        return code;
    } 
    
    /*cloturer_reclamation(
          parcours_maq validation_delib_semestre.id_niveau_formation_parcours%TYPE,
          calendrier validation_delib_semestre.id_calendrier_deliberation%TYPE,
          utilisateur utilisateurs.id_utilisateur%TYPE
      )*/
    public Integer cloturerReclamation(Long parcours_maq, Long calendrier, Long utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.cloturer_reclamation(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, calendrier);
            statement.setLong(4, utilisateur);
            statement.executeUpdate();
            code = statement.getInt(1);
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
        return code;
    }
                                                      
    public Integer repecherSemestre(Long parcours_maq, Long calendrier, String action, Long utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.repecher_semestre(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, calendrier);
            statement.setString(4, action);
            statement.setLong(5, utilisateur);
            statement.executeUpdate();
            code = statement.getInt(1);
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
        return code;
    }
    
    public Integer publishSemestre(Long prcrs_maq, Integer calendrier, String action,Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.publier_semestre(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.INTEGER);
            statement.setLong(2, prcrs_maq);
            statement.setInt(3, calendrier); 
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.executeUpdate();
            code = statement.getInt(1);

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
        return code;
    }
    
    public Integer publierAnnee(Long parcours_maq, Long session_id, String action, Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.publier_annee(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setLong(2, parcours_maq);
            statement.setLong(3, session_id);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Message " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(1) +
                               " Code " +
                               (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1))
                               .getAttribute(0));*/
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
        return code;
    }

    public Integer cloturerAnnee(Long parcours_maq, Long session_id, String action, Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.cloturer_annee(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, session_id);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.execute();
            code = statement.getInt(1);
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
        return code;
    }

    public Integer Deliberer(Integer parcours_maq, Integer session_id, String action, Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.deliberer_annee(?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.INTEGER);
            statement.setInt(2, parcours_maq);
            statement.setInt(3, session_id);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.executeUpdate();
            code = statement.getInt(1);
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
        return code;
    }
    
    public Integer isCosedAllNoteModeEns(Integer fil_ue, Integer calendrier, Long prcrs_maq) {
        Integer isclosed = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_closed_all_note_nme(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, fil_ue);
            statement.setInt(3, calendrier);
            statement.setLong(4, prcrs_maq);
            statement.executeUpdate();

            isclosed = statement.getInt(1);
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
        return isclosed;
    }

    // is_closed_ue(fil_ue ,calendrier) return integer AS
    public Integer isCosedUe(Integer fil_ue, Integer calendrier, Long prcrs_maq) {
        Integer isclosed = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_closed_ue(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, fil_ue);
            statement.setInt(3, calendrier);
            statement.setLong(4, prcrs_maq);
            //statement.setInt(4, session);
            statement.executeUpdate();

            isclosed = statement.getInt(1);
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
        return isclosed;
    }

    // deliberer_ue(fil_ue,calendrier,action,user)
    public Integer delibereUe(Integer fil_ue, Integer calendrier, String action, Integer utilisateur, Long prcrs_maq) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.deliberer_ue2(?,?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setInt(2, fil_ue);
            statement.setInt(3, calendrier);
            //statement.setInt(4, semestre);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.setLong(6, prcrs_maq);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Erreur message : " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)));
*/
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
        return code;
    }

    /*cloturer_ue(fil_ue,calendrier ,action ,utilisateur) RETURN integer AS*/
    public Integer ClosedUe(Integer fil_ue, Integer calendrier, String action, Integer utilisateur, Long prcrs_maq) {
        Integer isclosed = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.cloturer_ue(?,?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, fil_ue);
            statement.setInt(3, calendrier);
            statement.setString(4, action);
            statement.setInt(5, utilisateur);
            statement.setLong(6, prcrs_maq);
            statement.executeUpdate();

            isclosed = statement.getInt(1);
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
        return isclosed;
    }
    
    /*FUNCTION is_delibrated_annee(
        parcours validation_delib_semestre.id_niveau_formation_parcours%TYPE,
        calendrier validation_delib_semestre.id_calendrier_deliberation%TYPE
      )*/
    public Integer IsDelibarateAn(Long parcours_maq, Long session_id) {
        Integer isdelib = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_delibrated_annee(?, ?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, session_id);
            statement.executeUpdate();
            isdelib = statement.getInt(1);
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
        return isdelib;
    }
    
    public Integer IsOpenDoc(Long parcours_maq, Long session_id) {
        Integer isdelib = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_open_doc(?, ?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, session_id);
            statement.executeUpdate();
            isdelib = statement.getInt(1);
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
        return isdelib;
    }
    
    public Integer IsClosedAn(Long parcours_maq, Long session_id) {
        Integer isclose = -1;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ? :=  VALIDATION.is_closed_annee2(?, ?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, session_id);
            statement.executeUpdate();
            isclose = statement.getInt(1);
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
        return isclose;
    }
    
    @SuppressWarnings("deprecation")
    public RowSetIterator DeliberationAnnuelle(Integer annee, Integer parcours, Integer session) {
        //System.out.println("DeliberationAnnuelle Debut");
        ViewObjectImpl delibVO = this.getEtudiantSemestreDeliberationAn();
        delibVO.clearCache();
        DBTransactionImpl transaction = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            transaction.createCallableStatement(("BEGIN ?:=  MYPKG.DeliberationAnnuelle(?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.ARRAY, "TABLE_ETUDIANTSEMESTRE");
            statement.setInt(2, annee);
            statement.setInt(3, parcours);
            statement.setInt(4, session);
            statement.execute();
            Integer count_ = transformArrayToList((java.sql.Array) statement.getObject(1)).size();

            for (int i = 0; i < count_;
                 i++) {
                // Creates a row in ViewObject
                Row r =delibVO.createRow();
                // You can set attribute values in this new row
                try {
                r.setAttribute("Numero",
                               transformArrayToList((java.sql.Array) statement.getObject(1)).get(i).getnumero());
                } catch (Exception e) {
                    r.setAttribute("Numero", "");
                }
                try {
                r.setAttribute("PrenomNom",
                               transformArrayToList((java.sql.Array) statement.getObject(1)).get(i).getprenom_nom());
                } catch (Exception e) {
                    r.setAttribute("PrenomNom","");
                }
                try {
                   /* if(getSemestre()%2 == 0){
                        r.setAttribute("Semestre1"+(getSemestre()-1),
                                           transformSemestreToDomain((oracle.sql.STRUCT) transformArrayToList((java.sql.Array) statement.getObject(1)).get(i).getsemestre().getList().get(0)).getAttribute(0));
                    }else{
                        r.setAttribute("Semestre"+getSemestre(),
                                           transformSemestreToDomain((oracle.sql.STRUCT) transformArrayToList((java.sql.Array) statement.getObject(1)).get(i).getsemestre().getList().get(0)).getAttribute(0));
                    }*/
                    r.setAttribute("Semestre1",
                                       transformSemestreToDomain((oracle.sql.STRUCT) transformArrayToList((java.sql.Array) statement.getObject(1)).get(i).getsemestre().getList().get(0)).getAttribute(0));
                    
                } catch (Exception e) {
                    r.setAttribute("Semestre1","");
                }
                try {
                r.setAttribute("Semestre2",
                               transformSemestreToDomain((oracle.sql.STRUCT) transformArrayToList((java.sql.Array) statement.getObject(1)).get(i)
                                                                                                                                                       .getsemestre()
                                                                                                                                                       .getList()
                                                                                                                                                       .get(1))
                  .getAttribute(0));
                } catch (Exception e) {
                    r.setAttribute("Semestre2","");
                }
                try {
                r.setAttribute("Moyenne",
                               transformArrayToList((java.sql.Array) statement.getObject(1)).get(i)
                               .getAttribute("moyenne"));
                //Insert that row in ViewObject
                } catch (Exception e) {
                    r.setAttribute("Moyenne","");
                }
                delibVO.insertRow(r);
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

    private RValidation transformRValidationToDomain(java.sql.Struct rawObject) throws SQLException {
        RValidation r_v = new RValidation();
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            r_v.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return r_v;
    }

    private Semestre transformSemestreToDomain(@SuppressWarnings("deprecation")
                                               oracle.sql.STRUCT rawObject) throws SQLException {
        Semestre r_v = new Semestre();
        @SuppressWarnings("deprecation")
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            r_v.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return r_v;
    }

    private TableEtudiantsemestre transformEtudiantsemestreToDomain(java.sql.Struct rawObject) throws SQLException {
        TableEtudiantsemestre r_v = new TableEtudiantsemestre();
        Object[] attributes = rawObject.getAttributes();
        for (int attributeNumber = 0; attributeNumber < attributes.length; attributeNumber++) {
            r_v.setAttribute(attributeNumber, attributes[attributeNumber]);
        }
        return r_v;
    }

    private java.util.List<TableEtudiantsemestre> transformArrayToList(java.sql.Array array) throws SQLException {
        Object[] objectArray = (Object[]) array.getArray();
        java.util.List<TableEtudiantsemestre> table = new java.util.ArrayList<>();
        for (Object obj : objectArray) {
            java.sql.Struct struct = (java.sql.Struct) obj;
            table.add(transformEtudiantsemestreToDomain(struct));
        }
        return table;
    }

    /**
     * Container's getter for EtudiantSemestreDeliberationAn2.
     * @return EtudiantSemestreDeliberationAn2
     */
    public ViewObjectImpl getEtudiantSemestreDeliberationAn() {
        return (ViewObjectImpl) findViewObject("EtudiantSemestreDeliberationAn");
    }


    /*#############################################DELIBERATION SEMESTRIELLE######################################################*/
    /*@SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void calculMoyenneSemestrielle(Long calendrier, Long utilisateur) {
        //---PROCEDURE CalculMoyenneSemestreProc(calendrier,utilisateur)
        CallableStatement callableStat = null;
        String formSql = "begin eval.CalculMoyenneSemestreProc(?,?);  end ;";
        try {
            callableStat = getDBTransaction().createCallableStatement(formSql, 0);
            callableStat.setLong(1, calendrier);
            callableStat.setLong(2, utilisateur);

            callableStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStat != null) {
                try {
                    callableStat.close();
                } catch (Exception c) {
                }
            }
        }
    }*/

    /*@SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer clotureSemestre(Long parcours, Long calendrier, Long semestre, String action, Long utilisateur) {
        //    FUNCTION cloturer_semestre(parcours,calendrier,semestre,action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.cloturer_semestre(?,?,?,?,?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
            cls.setLong(4, semestre);
            cls.setString(5, action);
            cls.setLong(6, utilisateur);
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
*/
    /*@SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isDelibratedSemestre(Long parcours, Long calendrier) {
        //  FUNCTION is_delibrated_semestre(parcours, calendrier)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_delibrated_semestre(?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
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
    */
    // Update Jury Role
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateRole(Integer idUser, Integer idJury, String role,Integer utimodif) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Update UTILISATEURS_JURY SET ROLE=?, UTI_MODIFIE=? WHERE ID_UTILISATEUR=? AND ID_JURY=? ; end ;";
        //String sqlcommit = "begin ? := Update UTILISATEURS_JURY SET ROLE=?, UTI_MODIFIE=? WHERE ID_UTILISATEUR=? AND ID_JURY=? ; end ;";
        
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, idUser);
            cls.setInt(4, idJury);
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
    public void UpdateFormationAccess(Integer id_acces, String role,Integer utimodif) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Update UTILISATEUR_FORMATIONS SET ROLE=?, UTI_MODIFIE=? WHERE ID_UTILISATEUR_FORMATION=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, id_acces);
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
    public void DeleteFormationAccess(Integer id_acces) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Delete from UTILISATEUR_FORMATIONS where ID_UTILISATEUR_FORMATION=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(1, id_acces);
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
    
    public void refreshJury(Integer parcours_id){
        ViewObjectImpl vo = this.getJuryVO1();
        ViewCriteria vc = vo.getViewCriteria("JuryVOCriteria");
        vo.applyViewCriteria(vc);
        vo.setNamedWhereClauseParam("parcours", parcours_id);
        vo.executeQuery();
    }

    //Insert into UTILISATEURS_ROLES (ID_UTILISATEUR,ID_ROLE,UTI_CREE) values (?,?,?)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void AllowRoleTo(Integer role_id, Integer uti_id, Integer uticre) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin Insert into UTILISATEURS_ROLES (ID_UTILISATEUR,ID_ROLE,UTI_CREE) values (?,?,?) ; end ;";
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(1, uti_id);
            cls.setInt(2, role_id);
            cls.setInt(3, uticre);
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
//Delete from UTILISATEURS_ROLES where ID_UTILISATEUR_ROLE=:
        @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateUserRole(Integer user_role_id,Integer uti_id, Integer uti_modif) {
        CallableStatement cls = null;
        ResultSet rs = null;
        //Update UTILISATEUR_FORMATIONS SET ROLE=?, UTI_MODIFIE=? WHERE ID_UTILISATEUR_FORMATION=?
        String sql = "Begin Update UTILISATEURS_ROLES set ID_UTILISATEUR=?, UTI_MODIFIE=? where ID_UTILISATEUR_ROLE=? ; end ;";
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(1, uti_id);
            cls.setInt(2, uti_modif);
            cls.setInt(3, user_role_id);
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
    public void deleteRuleNivParc(Integer id_niv_reg_parc){
    //Delete from NIV_FORM_PARC_REGL_COMPENS nfr where nfr.ID_NIV_FORM_PARC_REGL_COMPENS=:id_niv_reg_parc
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql =
            "Begin Delete from NIV_FORM_PARC_REGL_COMPENS nfr where nfr.ID_NIV_FORM_PARC_REGL_COMPENS=? ; end ;";
        //String sqlcommit = "begin ? := Update UTILISATEURS_JURY SET ROLE=?, UTI_MODIFIE=? WHERE ID_UTILISATEUR=? AND ID_JURY=? ; end ;";

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(1, id_niv_reg_parc);
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
    public void UpdateResponsableEc(Integer idUser, Integer idFilEc, String role,Integer utimodif,Integer id_uti_fil_ec) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin UPDATE UTILIS_FILIERE_UE_SEMESTRE_EC ufec Set ufec.ROLE=?, ufec.UTI_MODIFIE=? \n" + 
        "WHERE ufec.ID_FILIERE_UE_SEMSTRE_EC=? AND ufec.ID_UTILISATEUR =? \n" + 
        "AND ufec.ID_UTILIS_FILIER_UE_SEM_EC=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, idFilEc);
            cls.setInt(4, idUser);
            cls.setInt(5, id_uti_fil_ec);
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
    public void UpdateResponsableUe(Integer idUser, Integer idFilUe, String role,Integer utimodif,Integer id_uti_fil_ue) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin UPDATE UTILIS_FILIERE_UE_SEMESTRE ufs SET ufs.ROLE=?, ufs.UTI_MODIFIE=? \n" + 
        "WHERE ufs.ID_FILIERE_UE_SEMSTRE=? \n" + 
        "AND ufs.ID_UTILIS_FILIER_UE_SEM=? \n" + 
        "AND ufs.ID_UTILISATEUR=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, idFilUe);
            cls.setInt(4, id_uti_fil_ue);
            cls.setInt(5, idUser);
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
    
    // Verifier si toutes les ues sont cloturer : appele par deliberation semestrielle
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isClosedAllUe(Long parcours_maq, Integer calendrier, Integer semestre) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_closed_all_note_ue2(?,?,?);  end ;";
        Integer result = 0;
        try { 
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setInt(3, calendrier);
            cls.setInt(4, semestre);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();
            result = cls.getInt(1);
            //System.out.println("result : " + result);
        } catch (SQLException e) {
            //System.out.println("In the Catch is close all ue");
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isAllUeClosed(Long prcrs_maq, Integer calendrier, Integer semestre) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_closed_all_ue(?,?,?);  end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_maq);
            cls.setInt(3, calendrier);
            cls.setInt(4, semestre);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();
            result = cls.getInt(1);
            //System.out.println("result : " + result);
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isAllUe2Closed(Long parcours_maq, Integer calendrier, Integer semestre) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_all_note_ue2_closed(?,?,?);  end ;";
        Integer result = 0;
        try { 
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setInt(3, calendrier);
            cls.setInt(4, semestre);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();
            result = cls.getInt(1);
            //System.out.println("result : " + result);
        } catch (SQLException e) {
            //System.out.println("In the Catch is close all ue");
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
    
    public List<Long> addSelectedToGroupe() {
        List<Long> etulists = new ArrayList<>();
        ViewObject vo = getEtudiantGroupeSaisieEtudiant();
        //create a second row set to not impact the row set
        //used in ADF
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        //set rowset to first row to avoid "attempt to access
        //dead row" exception
        duplicateRowSet.first();
        //get the current row of the table to set it back after
        //re-executing the VO
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        //get all rows that have the transoent attribute
        //"SelectedStudent" set to true
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("SelectedStudent", true);
        if (rowsToAdd.length > 0) {
            //only run throizgh this code if there is something to
            //delete
            for (Row rw : rowsToAdd) {
                //if row is ts marked as the current in VO, set
                //boolean flag
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                etulists.add(Long.parseLong(rw.getAttribute("IdEtudiant").toString()));
            }
            //re-execute VO
            vo.executeQuery();
            //reset current row if it hasn't been deleted
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return etulists;
    }
    
    public List<Long> getSelectedFormations() {
        List<Long> formationlists = new ArrayList<>();
        ViewObject vo = getFormationByStructure();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isSelectedStructure", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                formationlists.add(Long.parseLong(rw.getAttribute("IdFormation").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return formationlists;
    }
    
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateResponsableFormation(Integer idUser, Integer idFormation, String role,Integer utimodif,Integer id_uti_formation) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "Begin UPDATE UTILISATEUR_FORMATIONS uf SET uf.ROLE=?, uf.UTI_MODIFIE=? \n" + 
                    "WHERE uf.ID_FORMATION=? \n" + 
                    "AND uf.ID_UTILISATEUR_FORMATION=? \n" + 
                    "AND uf.ID_UTILISATEUR=? ; end ;";
       try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(1, role);
            cls.setInt(2, utimodif);
            cls.setInt(3, idFormation);
            cls.setInt(4, id_uti_formation);
            cls.setInt(5, idUser);
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
    

    public List<Long> getSelectedParcours() {
        List<Long> parcourslist = new ArrayList<>();
        ViewObject vo = getParcours();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isChecked", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                parcourslist.add(Long.parseLong(rw.getAttribute("IdNiveauFormationParcours").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return parcourslist;
    }

    public List<Long> getSelectedUsers() {
        List<Long> userlists = new ArrayList<>();
        ViewObject vo = getUtilisateurStructure();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isUserSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                userlists.add(Long.parseLong(rw.getAttribute("IdUtilisateur").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return userlists;
    }

    public List<Long> getSelectedNiveauFormations() {
        List<Long> niveaulists = new ArrayList<>();
        ViewObject vo = getAccesNiveauFormation();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("selectedFormation", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                niveaulists.add(Long.parseLong(rw.getAttribute("IdNiveauFormation").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return niveaulists;
    }
    
    public List<Long> getSelectedUes() {
        List<Long> ueslists = new ArrayList<>();
        ViewObject vo = getUeNiveauFormationUtilisateursROVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isUeSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                ueslists.add(Long.parseLong(rw.getAttribute("IdFiliereUeSemstre").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return ueslists;
    }

    public List<Long> getSelectedLois() {
        List<Long> textlists = new ArrayList<>();
        ViewObject vo = getTextLoisByStructure();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                textlists.add(Long.parseLong(rw.getAttribute("IdTextLoi").toString()));
                //textlists.put(Long.parseLong(rw.getAttribute("IdTextLoi").toString()), Integer.parseInt(rw.getAttribute("Ordre").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return textlists;
    }
    
    public List<Long> getSelectedEcs() {
        List<Long> ecslists = new ArrayList<>();
        ViewObject vo = getEcNiveauFormationUtilisateurROVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isEcSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                ecslists.add(Long.parseLong(rw.getAttribute("IdFiliereUeSemstreEc").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return ecslists;
    }
    
    public List<Long> getSelectedJury() {
        List<Long> jurylists = new ArrayList<>();
        ViewObject vo = getJuryROVO();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("IsJurySelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                jurylists.add(Long.parseLong(rw.getAttribute("IdJury").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return jurylists;
    }
    
    public Integer isClosedAnnee(Long parcours_pma, Long session_id) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.is_closed_annee(?, ?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setLong(2, parcours_pma);
            statement.setLong(3, session_id);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Message " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(1) +
                               " Code " +
                               (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1))
                               .getAttribute(0));*/
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
        return code;
    }
 
    public Integer delibererSemestre(Long parcours_maq, Integer calendrier, Integer semestre, String action,
                                     Integer utilisateur) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  VALIDATION.deliberer_semestre(?,?,?,?,?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, OracleTypes.STRUCT, "R_VALIDATION");
            statement.setLong(2, parcours_maq);
            statement.setInt(3, calendrier);
            statement.setInt(4, semestre);
            statement.setString(5, action);
            statement.setInt(6, utilisateur);
            statement.execute();
            // Verifier le code de retour et faire le traitement en fonction
            code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            /*System.out.println("Message " +
                               transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(1));*/
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
        return code;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer getEtatDeliberation(Long parcours_maq, Integer calendrier) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.getEtatDeliberation(?,?);  end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setLong(3, calendrier);
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer isCompensedSemestre(Long parcours_maq, Integer calendrier) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  validation.is_compensed_semestre(?,?);  end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours_maq);
            cls.setLong(3, calendrier);
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
            return null;
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
    //is_genere_anonymat
    
    public Integer isGenereAnonymat(Long parcours, Long anne,Long semestre, Long sessions) {
    //FUNCTION is_genere_anonymat(parcours,anne,semestre,sessions)
    CallableStatement cls = null;
    ResultSet rs = null;
    String sql = "begin ? :=  eval.is_genere_anonymat(?,?,?,?);  end ;";
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

    /**
     * Container's getter for DelibSemEtudiantROVO1.
     * @return DelibSemEtudiantROVO1
     */
    public DelibSemEtudiantROVOImpl getDelibSemEtudiant() {
        return (DelibSemEtudiantROVOImpl) findViewObject("DelibSemEtudiant");
    }

    /**
     * Container's getter for DelibSemFilEcROVO1.
     * @return DelibSemFilEcROVO1
     */
    public ViewObjectImpl getDelibSemFilEc() {
        return (ViewObjectImpl) findViewObject("DelibSemFilEc");
    }

    /**
     * Container's getter for DelibSemFilUeROVO1.
     * @return DelibSemFilUeROVO1
     */
    public ViewObjectImpl getDelibSemFilUe() {
        return (ViewObjectImpl) findViewObject("DelibSemFilUe");
    }

    /**
     * Container's getter for DelibSemInsPedSemUeROVO1.
     * @return DelibSemInsPedSemUeROVO1
     */
    public ViewObjectImpl getDelibSemInsPedSemUe() {
        return (ViewObjectImpl) findViewObject("DelibSemInsPedSemUe");
    }

    /**
     * Container's getter for DelibSemNoteEcROVO1.
     * @return DelibSemNoteEcROVO1
     */
    public ViewObjectImpl getDelibSemNoteEc() {
        return (ViewObjectImpl) findViewObject("DelibSemNoteEc");
    }

    /**
     * Container's getter for DelibResUeROVO1.
     * @return DelibResUeROVO1
     */
    public ViewObjectImpl getDelibResUe() {
        return (ViewObjectImpl) findViewObject("DelibResUe");
    }

    /**
     * Container's getter for DelibSemInsPedSem1.
     * @return DelibSemInsPedSem1
     */
    public ViewObjectImpl getDelibSemInsPedSem() {
        return (ViewObjectImpl) findViewObject("DelibSemInsPedSem");
    }

    /**
     * Container's getter for DelibSemResSemROVO1.
     * @return DelibSemResSemROVO1
     */
    public ViewObjectImpl getDelibSemResSem() {
        return (ViewObjectImpl) findViewObject("DelibSemResSem");
    }

    /**
     * Container's getter for DelibUeModeCntrlROVO1.
     * @return DelibUeModeCntrlROVO1
     */
    public ViewObjectImpl getDelibUeModeCntrl() {
        return (ViewObjectImpl) findViewObject("DelibUeModeCntrl");
    }

    /**
     * Container's getter for DelibUeNoteModeCntrl1.
     * @return DelibUeNoteModeCntrl1
     */
    public ViewObjectImpl getDelibUeNoteModeCntrl() {
        return (ViewObjectImpl) findViewObject("DelibUeNoteModeCntrl");
    }

    /**
     * Container's getter for DelibResEc1.
     * @return DelibResEc1
     */
    public ViewObjectImpl getDelibResEc() {
        return (ViewObjectImpl) findViewObject("DelibResEc");
    }

    /**
     * Container's getter for GroupeSaisieVO1.
     * @return GroupeSaisieVO1
     */
    public ViewObjectImpl getGroupeSaisie() {
        return (ViewObjectImpl) findViewObject("GroupeSaisie");
    }


    /**
     * Container's getter for EtudiantGroupeSaisieEtudiant1.
     * @return EtudiantGroupeSaisieEtudiant1
     */
    public ViewObjectImpl getEtudiantGroupeSaisieEtudiant() {
        return (ViewObjectImpl) findViewObject("EtudiantGroupeSaisieEtudiant");
    }

    /**
     * Container's getter for GroupeSaisieEtudiantVO1.
     * @return GroupeSaisieEtudiantVO1
     */
    public ViewObjectImpl getGroupeSaisieEtudiant() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieEtudiant");
    }

    /**
     * Container's getter for StructuresVO1.
     * @return StructuresVO1
     */
    public ViewObjectImpl getStructures() {
        return (ViewObjectImpl) findViewObject("Structures");
    }

    /**
     * Container's getter for UtilisateurStructureVO1.
     * @return UtilisateurStructureVO1
     */
    public ViewObjectImpl getUtilisateurStructure() {
        return (ViewObjectImpl) findViewObject("UtilisateurStructure");
    }

    /**
     * Container's getter for AccesNiveauFormationROVO1.
     * @return AccesNiveauFormationROVO1
     */
    public ViewObjectImpl getAccesNiveauFormation() {
        return (ViewObjectImpl) findViewObject("AccesNiveauFormation");
    }

    /**
     * Container's getter for UtilisateurNiveauFormationROVO1.
     * @return UtilisateurNiveauFormationROVO1
     */
    public ViewObjectImpl getUtilisateurNiveauFormation() {
        return (ViewObjectImpl) findViewObject("UtilisateurNiveauFormation");
    }

    /**
     * Container's getter for UtilisateurNiveauxFormationsVO1.
     * @return UtilisateurNiveauxFormationsVO1
     */
    public ViewObjectImpl getUtilisateurNiveauxFormationsVO() {
        return (ViewObjectImpl) findViewObject("UtilisateurNiveauxFormationsVO");
    }

    /**
     * Container's getter for GroupeSaisieEtudiantROVO1.
     * @return GroupeSaisieEtudiantROVO1
     */
    public ViewObjectImpl getGroupeSaisieEtudiantROVO() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieEtudiantROVO");
    }

    /**
     * Container's getter for GroupeSaisieParcoursROVO1.
     * @return GroupeSaisieParcoursROVO1
     */
    public ViewObjectImpl getGroupeSaisieParcours() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieParcours");
    }

    /**
     * Container's getter for UeNiveauFormationUtilisateursROVO1.
     * @return UeNiveauFormationUtilisateursROVO1
     */
    public ViewObjectImpl getUeNiveauFormationUtilisateursROVO() {
        return (ViewObjectImpl) findViewObject("UeNiveauFormationUtilisateursROVO");
    }

    /**
     * Container's getter for UtilisFiliereUeSemestreVO1.
     * @return UtilisFiliereUeSemestreVO1
     */
    public ViewObjectImpl getUtilisFiliereUeSemestreVO() {
        return (ViewObjectImpl) findViewObject("UtilisFiliereUeSemestreVO");
    }

    /**
     * Container's getter for UtilisFiliereUeSemestreEcVO1.
     * @return UtilisFiliereUeSemestreEcVO1
     */
    public ViewObjectImpl getUtilisFiliereUeSemestreEcVO() {
        return (ViewObjectImpl) findViewObject("UtilisFiliereUeSemestreEcVO");
    }

    /**
     * Container's getter for AccesUeList1.
     * @return AccesUeList1
     */
    public AccesUeListImpl getAccesUeList() {
        return (AccesUeListImpl) findViewObject("AccesUeList");
    }

    /**
     * Container's getter for UtilisateurAccesUeROVO1.
     * @return UtilisateurAccesUeROVO1
     */
    public ViewObjectImpl getUtilisateurAccesUeROVO() {
        return (ViewObjectImpl) findViewObject("UtilisateurAccesUeROVO");
    }

    /**
     * Container's getter for EcNiveauFormationUtilisateurROVO1.
     * @return EcNiveauFormationUtilisateurROVO1
     */
    public ViewObjectImpl getEcNiveauFormationUtilisateurROVO() {
        return (ViewObjectImpl) findViewObject("EcNiveauFormationUtilisateurROVO");
    }

    /**
     * Container's getter for AccesEcList1.
     * @return AccesEcList1
     */
    public AccesEcListImpl getAccesEcList() {
        return (AccesEcListImpl) findViewObject("AccesEcList");
    }

    /**
     * Container's getter for UtilisateursAccesEcsROVO1.
     * @return UtilisateursAccesEcsROVO1
     */
    public ViewObjectImpl getUtilisateursAccesEcsROVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursAccesEcsROVO");
    }

    /**
     * Container's getter for IsEcAccesAllowedROVO1.
     * @return IsEcAccesAllowedROVO1
     */
    public ViewObjectImpl getIsEcAccesAllowedROVO() {
        return (ViewObjectImpl) findViewObject("IsEcAccesAllowedROVO");
    }

    /**
     * Container's getter for IsUeAccesAllowedROVO1.
     * @return IsUeAccesAllowedROVO1
     */
    public ViewObjectImpl getIsUeAccesAllowedROVO() {
        return (ViewObjectImpl) findViewObject("IsUeAccesAllowedROVO");
    }


    /**
     * Container's getter for OptionROVO1.
     * @return OptionROVO1
     */
    public ViewObjectImpl getOptionROVO() {
        return (ViewObjectImpl) findViewObject("OptionROVO");
    }

    /**
     * Container's getter for SpecialiteROVO1.
     * @return SpecialiteROVO1
     */
    public ViewObjectImpl getSpecialiteROVO1() {
        return (ViewObjectImpl) findViewObject("SpecialiteROVO1");
    }

    /**
     * Container's getter for IsNivFormAccesAllowed1.
     * @return IsNivFormAccesAllowed1
     */
    public ViewObjectImpl getIsNivFormAccesAllowed() {
        return (ViewObjectImpl) findViewObject("IsNivFormAccesAllowed");
    }

    /**
     * Container's getter for JuryROVO1.
     * @return JuryROVO1
     */
    public ViewObjectImpl getJuryROVO() {
        return (ViewObjectImpl) findViewObject("JuryROVO");
    }

    /**
     * Container's getter for JuryVO1.
     * @return JuryVO1
     */
    public ViewObjectImpl getJuryVO() {
        return (ViewObjectImpl) findViewObject("JuryVO");
    }

    /**
     * Container's getter for UtilisateursJuryVO1.
     * @return UtilisateursJuryVO1
     */
    public ViewObjectImpl getUtilisateursJuryVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursJuryVO");
    }

    /**
     * Container's getter for IsJuryAffectedROVO1.
     * @return IsJuryAffectedROVO1
     */
    public ViewObjectImpl getIsJuryAffectedROVO() {
        return (ViewObjectImpl) findViewObject("IsJuryAffectedROVO");
    }

    /**
     * Container's getter for JuryRoleROVO1.
     * @return JuryRoleROVO1
     */
    public JuryRoleROVOImpl getJuryRoleROVO() {
        return (JuryRoleROVOImpl) findViewObject("JuryRoleROVO");
    }

    /**
     * Container's getter for IsJuryPresidentExist1.
     * @return IsJuryPresidentExist1
     */
    public ViewObjectImpl getIsJuryPresidentExist() {
        return (ViewObjectImpl) findViewObject("IsJuryPresidentExist");
    }

    /**
     * Container's getter for IsResponsableUeExist1.
     * @return IsResponsableUeExist1
     */
    public ViewObjectImpl getIsResponsableUeExist() {
        return (ViewObjectImpl) findViewObject("IsResponsableUeExist");
    }

    /**
     * Container's getter for IsEcResponsableExist1.
     * @return IsEcResponsableExist1
     */
    public ViewObjectImpl getIsEcResponsableExist() {
        return (ViewObjectImpl) findViewObject("IsEcResponsableExist");
    }

    /**
     * Container's getter for UtilisateurJuryROVO1.
     * @return UtilisateurJuryROVO1
     */
    public ViewObjectImpl getUtilisateurJuryROVO1() {
        return (ViewObjectImpl) findViewObject("UtilisateurJuryROVO1");
    }

    /**
     * Container's getter for UtilisateurJuryROVO2.
     * @return UtilisateurJuryROVO2
     */
    public ViewObjectImpl getUtilisateurJuryROVO2() {
        return (ViewObjectImpl) findViewObject("UtilisateurJuryROVO2");
    }

    /**
     * Container's getter for JuryToUtilisateurJuryVL1.
     * @return JuryToUtilisateurJuryVL1
     */
    public ViewLinkImpl getJuryToUtilisateurJuryVL1() {
        return (ViewLinkImpl) findViewLink("JuryToUtilisateurJuryVL1");
    }

    /**
     * Container's getter for ReglesCompensationROVO1.
     * @return ReglesCompensationROVO1
     */
    public ViewObjectImpl getReglesCompensation() {
        return (ViewObjectImpl) findViewObject("ReglesCompensation");
    }

    /**
     * Container's getter for NivFormParcReglCompensVO1.
     * @return NivFormParcReglCompensVO1
     */
    public ViewObjectImpl getNivFormParcReglCompens() {
        return (ViewObjectImpl) findViewObject("NivFormParcReglCompens");
    }

    /**
     * Container's getter for IsCompensedRuleParcoursExist1.
     * @return IsCompensedRuleParcoursExist1
     */
    public ViewObjectImpl getIsCompensedRuleParcoursExist() {
        return (ViewObjectImpl) findViewObject("IsCompensedRuleParcoursExist");
    }

    /**
     * Container's getter for EtudiantReleves1.
     * @return EtudiantReleves1
     */
    public ViewObjectImpl getEtudiantReleves() {
        return (ViewObjectImpl) findViewObject("EtudiantReleves");
    }

    /**
     * Container's getter for EtudiantAttestation1.
     * @return EtudiantAttestation1
     */
    public ViewObjectImpl getEtudiantAttestation() {
        return (ViewObjectImpl) findViewObject("EtudiantAttestation");
    }

    /**
     * Container's getter for DocumentsROVO1.
     * @return DocumentsROVO1
     */
    public DocumentsROVOImpl getDocuments() {
        return (DocumentsROVOImpl) findViewObject("Documents");
    }

    /**
     * Container's getter for UserByUserName1.
     * @return UserByUserName1
     */
    public UserByUserNameImpl getUserByUserName() {
        return (UserByUserNameImpl) findViewObject("UserByUserName");
    }

    /**
     * Container's getter for TextLoisVO1.
     * @return TextLoisVO1
     */
    public ViewObjectImpl getTextLoisVO() {
        return (ViewObjectImpl) findViewObject("TextLoisVO");
    }

    /**
     * Container's getter for TextsLoisROVO1.
     * @return TextsLoisROVO1
     */
    public TextsLoisROVOImpl getTextsLoisROVO() {
        return (TextsLoisROVOImpl) findViewObject("TextsLoisROVO");
    }

    /**
     * Container's getter for DeliberationStaticList1.
     * @return DeliberationStaticList1
     */
    public DeliberationStaticListImpl getDeliberationStaticList() {
        return (DeliberationStaticListImpl) findViewObject("DeliberationStaticList");
    }

    /**
     * Container's getter for IsUsedFormationText1.
     * @return IsUsedFormationText1
     */
    public IsUsedFormationTextImpl getIsUsedFormationText() {
        return (IsUsedFormationTextImpl) findViewObject("IsUsedFormationText");
    }


    /**
     * Container's getter for FormationByStructure1.
     * @return FormationByStructure1
     */
    public FormationByStructureImpl getFormationByStructure() {
        return (FormationByStructureImpl) findViewObject("FormationByStructure");
    }

    /**
     * Container's getter for TextLoisByStructure1.
     * @return TextLoisByStructure1
     */
    public TextLoisByStructureImpl getTextLoisByStructure() {
        return (TextLoisByStructureImpl) findViewObject("TextLoisByStructure");
    }

    /**
     * Container's getter for IsFormationTextIs1.
     * @return IsFormationTextIs1
     */
    public IsFormationTextIsImpl getIsFormationTextIs() {
        return (IsFormationTextIsImpl) findViewObject("IsFormationTextIs");
    }


    /**
     * Container's getter for IsStructureTextExist1.
     * @return IsStructureTextExist1
     */
    public IsStructureTextExistImpl getIsStructureTextExist() {
        return (IsStructureTextExistImpl) findViewObject("IsStructureTextExist");
    }


    /**
     * Container's getter for TextLoiFormationROVO1.
     * @return TextLoiFormationROVO1
     */
    public TextLoiFormationROVOImpl getTextLoiFormationROVO() {
        return (TextLoiFormationROVOImpl) findViewObject("TextLoiFormationROVO");
    }

    /**
     * Container's getter for TextLoisHistoriqueStrVO2.
     * @return TextLoisHistoriqueStrVO2
     */
    public ViewObjectImpl getTextLoisHistoriqueStrVO() {
        return (ViewObjectImpl) findViewObject("TextLoisHistoriqueStrVO");
    }


    /**
     * Container's getter for ReglesAnonymatVO1.
     * @return ReglesAnonymatVO1
     */
    public ViewObjectImpl getReglesAnonymatVO() {
        return (ViewObjectImpl) findViewObject("ReglesAnonymatVO");
    }

    /**
     * Container's getter for IsRuleUsed1.
     * @return IsRuleUsed1
     */
    public ViewObjectImpl getIsRuleUsed() {
        return (ViewObjectImpl) findViewObject("IsRuleUsed");
    }

    /**
     * Container's getter for gerRuleUsed1.
     * @return gerRuleUsed1
     */
    public ViewObjectImpl getgerRuleUsed() {
        return (ViewObjectImpl) findViewObject("gerRuleUsed");
    }

    /**
     * Container's getter for AnonymatVO1.
     * @return AnonymatVO1
     */
    public ViewObjectImpl getAnonymatVO() {
        return (ViewObjectImpl) findViewObject("AnonymatVO");
    }

    /**
     * Container's getter for AnonymatVO1.
     * @return AnonymatVO1
     */
    public ViewObjectImpl getAnonymatVO1() {
        return (ViewObjectImpl) findViewObject("AnonymatVO1");
    }

    /**
     * Container's getter for RegleToAnonymatVL1.
     * @return RegleToAnonymatVL1
     */
    public ViewLinkImpl getRegleToAnonymatVL1() {
        return (ViewLinkImpl) findViewLink("RegleToAnonymatVL1");
    }

    /**
     * Container's getter for StudentParcrsAnSemSess1.
     * @return StudentParcrsAnSemSess1
     */
    public StudentParcrsAnSemSessImpl getStudentParcrsAnSemSess() {
        return (StudentParcrsAnSemSessImpl) findViewObject("StudentParcrsAnSemSess");
    }

    /**
     * Container's getter for GenAnonymatVO1.
     * @return GenAnonymatVO1
     */
    public ViewObjectImpl getGenAnonymatVO() {
        return (ViewObjectImpl) findViewObject("GenAnonymatVO");
    }

    /**
     * Container's getter for IsExistEtuAno1.
     * @return IsExistEtuAno1
     */
    public IsExistEtuAnoImpl getIsExistEtuAno() {
        return (IsExistEtuAnoImpl) findViewObject("IsExistEtuAno");
    }

    /**
     * Container's getter for UsedAno1.
     * @return UsedAno1
     */
    public UsedAnoImpl getUsedAno() {
        return (UsedAnoImpl) findViewObject("UsedAno");
    }

    /**
     * Container's getter for GenAnonymatROVO1.
     * @return GenAnonymatROVO1
     */
    public GenAnonymatROVOImpl getGenAnonymatROVO() {
        return (GenAnonymatROVOImpl) findViewObject("GenAnonymatROVO");
    }

    /**
     * Container's getter for isParametredCalendar1.
     * @return isParametredCalendar1
     */
    /*public isParametredCalendarImpl getisParametredCalendar() {
        return (isParametredCalendarImpl) findViewObject("isParametredCalendar");
    }*/

    /**
     * Container's getter for isClosedInscription1.
     * @return isClosedInscription1
     */
    /*public isClosedInscriptionImpl getisClosedInscription() {
        return (isClosedInscriptionImpl) findViewObject("isClosedInscription");
    }*/


    /**
     * Container's getter for EcROVO1.
     * @return EcROVO1
     */
    public EcROVOImpl getEcROVO() {
        return (EcROVOImpl) findViewObject("EcROVO");
    }

    /**
     * Container's getter for EcEvalTypeCtrlRO.
     * @return EcEvalTypeCtrlRO
     */
    public EcEvalTypeCtrlROVOImpl getEcEvalTypeCtrlRO() {
        return (EcEvalTypeCtrlROVOImpl) findViewObject("EcEvalTypeCtrlRO");
    }

    /**
     * Container's getter for EcEvalTypeCtrlModeCtrlRO.
     * @return EcEvalTypeCtrlModeCtrlRO
     */
    public EcEvalTypeCtrlModeCtrlROVOImpl getEcEvalTypeCtrlModeCtrlRO() {
        return (EcEvalTypeCtrlModeCtrlROVOImpl) findViewObject("EcEvalTypeCtrlModeCtrlRO");
    }

    /**
     * Container's getter for AnonymatExecuteROVO1.
     * @return AnonymatExecuteROVO1
     */
    public AnonymatExecuteROVOImpl getAnonymatExecuteRO() {
        return (AnonymatExecuteROVOImpl) findViewObject("AnonymatExecuteRO");
    }

    /**
     * Container's getter for EcEvalToEcEvalTypeCtrlROVL1.
     * @return EcEvalToEcEvalTypeCtrlROVL1
     */
    public ViewLinkImpl getEcEvalToEcEvalTypeCtrlROVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalToEcEvalTypeCtrlROVL1");
    }

    /**
     * Container's getter for EcEvalTypeCtrlToModeCtrlROVOVL1.
     * @return EcEvalTypeCtrlToModeCtrlROVOVL1
     */
    public ViewLinkImpl getEcEvalTypeCtrlToModeCtrlROVOVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalTypeCtrlToModeCtrlROVOVL1");
    }

    /**
     * Container's getter for NoteModeEnsInterGenAnonymROVO1.
     * @return NoteModeEnsInterGenAnonymROVO1
     */
    public NoteModeEnsInterGenAnonymROVOImpl getNoteModeEnsInterGenAnonymRO() {
        return (NoteModeEnsInterGenAnonymROVOImpl) findViewObject("NoteModeEnsInterGenAnonymRO");
    }

    /**
     * Container's getter for NoteModeEnsGenAnonymROVO1.
     * @return NoteModeEnsGenAnonymROVO1
     */
    public NoteModeEnsGenAnonymROVOImpl getNoteModeEnsGenAnonymRO() {
        return (NoteModeEnsGenAnonymROVOImpl) findViewObject("NoteModeEnsGenAnonymRO");
    }

    /**
     * Container's getter for NoteModeEnsGenAnonymROVO2.
     * @return NoteModeEnsGenAnonymROVO2
     */
    public NoteModeEnsGenAnonymROVOImpl getNoteModeEnsGenAnonymROVO2() {
        return (NoteModeEnsGenAnonymROVOImpl) findViewObject("NoteModeEnsGenAnonymROVO2");
    }

    /**
     * Container's getter for isClosedInscription1.
     * @return isClosedInscription1
     */
    public isClosedInscriptionImpl getisClosedInscription() {
        return (isClosedInscriptionImpl) findViewObject("isClosedInscription");
    }

    /**
     * Container's getter for isParametredCalendar1.
     * @return isParametredCalendar1
     */
    public isParametredCalendarImpl getisParametredCalendar() {
        return (isParametredCalendarImpl) findViewObject("isParametredCalendar");
    }

    /**
     * Container's getter for GenAnonymatNewROVO1.
     * @return GenAnonymatNewROVO1
     */
    public GenAnonymatNewROVOImpl getGenAnonymatNewRO() {
        return (GenAnonymatNewROVOImpl) findViewObject("GenAnonymatNewRO");
    }

    /**
     * Container's getter for EtudiantsVO1.
     * @return EtudiantsVO1
     */
    public ViewObjectImpl getEtudiants() {
        return (ViewObjectImpl) findViewObject("Etudiants");
    }

    /**
     * Container's getter for AnneeUniverROVO1.
     * @return AnneeUniverROVO1
     */
    public AnneeUniverROVOImpl getAnneeUniverROVO() {
        return (AnneeUniverROVOImpl) findViewObject("AnneeUniverROVO");
    }

    /**
     * Container's getter for AnneesUniversVO1.
     * @return AnneesUniversVO1
     */
    public ViewObjectImpl getAnneesUniversVO() {
        return (ViewObjectImpl) findViewObject("AnneesUniversVO");
    }
    
    
    public void RepecheUECriteria(Long calendrier, BigDecimal superieur, BigDecimal inferieur){
        ViewObjectImpl vo = this.getResultatsFilUeSemestreVO();
        ViewCriteria vc = vo.getViewCriteria("RepechUeCriteria");
        vo.applyViewCriteria(vc,true);
        vo.setNamedWhereClauseParam("calendrier", calendrier);
        vo.setNamedWhereClauseParam("inferieur", inferieur);
        vo.setNamedWhereClauseParam("superieur", superieur);
        vo.executeQuery();
        
    }

    /**
     * Container's getter for FilUeInsPedSem1.
     * @return FilUeInsPedSem1
     */
    public FilUeInsPedSemImpl getFilUeInsPedSem() {
        return (FilUeInsPedSemImpl) findViewObject("FilUeInsPedSem");
    }

    /**
     * Container's getter for FilEcInsPedSemUe1.
     * @return FilEcInsPedSemUe1
     */
    public FilEcInsPedSemUeImpl getFilEcInsPedSemUe() {
        return (FilEcInsPedSemUeImpl) findViewObject("FilEcInsPedSemUe");
    }

    /**
     * Container's getter for ResultatsFilUeSemestreVO1.
     * @return ResultatsFilUeSemestreVO1
     */
    public ResultatsFilUeSemestreVOImpl getResultatsFilUeSemestreVO() {
        return (ResultatsFilUeSemestreVOImpl) findViewObject("ResultatsFilUeSemestreVO");
    }


    /**
     * Container's getter for ResultatUEToNoteModEnsVL1.
     * @return ResultatUEToNoteModEnsVL1
     */
    public ViewLinkImpl getResultatUEToNoteModEnsVL1() {
        return (ViewLinkImpl) findViewLink("ResultatUEToNoteModEnsVL1");
    }

    /**
     * Container's getter for MoyenneUE1.
     * @return MoyenneUE1
     */
    public MoyenneUEImpl getMoyenneUE() {
        return (MoyenneUEImpl) findViewObject("MoyenneUE");
    }

    /**
     * Container's getter for PourcentageCC_CT_ROVO1.
     * @return PourcentageCC_CT_ROVO1
     */
    public PourcentageCC_CT_ROVOImpl getPourcentageCC_CT_ROVO() {
        return (PourcentageCC_CT_ROVOImpl) findViewObject("PourcentageCC_CT_ROVO");
    }

    /**
     * Container's getter for FilUeByFilEc1.
     * @return FilUeByFilEc1
     */
    public FilUeByFilEcImpl getFilUeByFilEc() {
        return (FilUeByFilEcImpl) findViewObject("FilUeByFilEc");
    }


    /**
     * Container's getter for ResultatFilUeSemestreROVO1.
     * @return ResultatFilUeSemestreROVO1
     */
    public ResultatFilUeSemestreROVOImpl getResultatFilUeSemestreROVO() {
        return (ResultatFilUeSemestreROVOImpl) findViewObject("ResultatFilUeSemestreROVO");
    }

    /**
     * Container's getter for ResultatSemestreToResultatFilUeSemROVOVL1.
     * @return ResultatSemestreToResultatFilUeSemROVOVL1
     */
    public ViewLinkImpl getResultatSemestreToResultatFilUeSemROVOVL1() {
        return (ViewLinkImpl) findViewLink("ResultatSemestreToResultatFilUeSemROVOVL1");
    }


    /**
     * Container's getter for ResultatFilUESemestreROVOToNoteModeEns1.
     * @return ResultatFilUESemestreROVOToNoteModeEns1
     */
    public ViewLinkImpl getResultatFilUESemestreROVOToNoteModeEns1() {
        return (ViewLinkImpl) findViewLink("ResultatFilUESemestreROVOToNoteModeEns1");
    }

    /**
     * Container's getter for AnonymatParcoursROVO1.
     * @return AnonymatParcoursROVO1
     */
    public AnonymatParcoursROVOImpl getAnonymatParcours() {
        return (AnonymatParcoursROVOImpl) findViewObject("AnonymatParcours");
    }

    /**
     * Container's getter for AnoGenParcours1.
     * @return AnoGenParcours1
     */
    public AnoGenParcoursImpl getAnoGenParcours() {
        return (AnoGenParcoursImpl) findViewObject("AnoGenParcours");
    }

    /**
     * Container's getter for AllInteFilEcROVO1.
     * @return AllInteFilEcROVO1
     */
    public AllInteFilEcROVOImpl getAllInteFilEcROVO() {
        return (AllInteFilEcROVOImpl) findViewObject("AllInteFilEcROVO");
    }

    /**
     * Container's getter for AllEcFilUe1.
     * @return AllEcFilUe1
    
    public AllEcFilUeImpl getAllEcFilUe() {
        return (AllEcFilUeImpl) findViewObject("AllEcFilUe");
    } */

    /**
     * Container's getter for ReglesCompensationPArcoursROVO1.
     * @return ReglesCompensationPArcoursROVO1
     */
    public ReglesCompensationPArcoursROVOImpl getReglesCompensationPArcoursROVO() {
        return (ReglesCompensationPArcoursROVOImpl) findViewObject("ReglesCompensationPArcoursROVO");
    }

    /**
     * Container's getter for IsMaquetteUsedROVO1.
     * @return IsMaquetteUsedROVO1
     */
    public IsMaquetteUsedROVOImpl getIsMaquetteUsedROVO() {
        return (IsMaquetteUsedROVOImpl) findViewObject("IsMaquetteUsedROVO");
    }

    /**
     * Container's getter for DelibSemUeEtudiantROVO1.
     * @return DelibSemUeEtudiantROVO1
     */
    public DelibSemUeEtudiantROVOImpl getDelibSemUeEtudiant() {
        return (DelibSemUeEtudiantROVOImpl) findViewObject("DelibSemUeEtudiant");
    }

    /**
     * Container's getter for isEtudiantNumeroValide1.
     * @return isEtudiantNumeroValide1
     */
    public isEtudiantNumeroValideImpl getisEtudiantNumeroValide() {
        return (isEtudiantNumeroValideImpl) findViewObject("isEtudiantNumeroValide");
    }

    /**
     * Container's getter for DetailsSemestreEtudiant1.
     * @return DetailsSemestreEtudiant1
     */
    public DetailsSemestreEtudiantImpl getDetailsSemestreEtudiant() {
        return (DetailsSemestreEtudiantImpl) findViewObject("DetailsSemestreEtudiant");
    }

    /**
     * Container's getter for DetailsAnnEtudiant1.
     * @return DetailsAnnEtudiant1
     */
    public DetailsAnnEtudiantImpl getDetailsAnnEtudiant() {
        return (DetailsAnnEtudiantImpl) findViewObject("DetailsAnnEtudiant");
    }

    /**
     * Container's getter for isNoteModeEnsInterExist1.
     * @return isNoteModeEnsInterExist1
     */
    public ViewObjectImpl getisNoteModeEnsInterExist1() {
        return (ViewObjectImpl) findViewObject("isNoteModeEnsInterExist1");
    }

    /**
     * Container's getter for HistoriquesStructuresVO1.
     * @return HistoriquesStructuresVO1
     */
    public ViewObjectImpl getHistoriquesStructuresVO1() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresVO1");
    }

    /**
     * Container's getter for StructureToHStructureVL1.
     * @return StructureToHStructureVL1
     */
    public ViewLinkImpl getStructureToHStructureVL1() {
        return (ViewLinkImpl) findViewLink("StructureToHStructureVL1");
    }

    /**
     * Container's getter for GroupeSaisieEtudiantROVO1.
     * @return GroupeSaisieEtudiantROVO1
     */
    public ViewObjectImpl getGroupeSaisieEtudiantROVO1() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieEtudiantROVO1");
    }

    /**
     * Container's getter for GroupeSaisiePcrsToGroupeSaisieEtdVL1.
     * @return GroupeSaisiePcrsToGroupeSaisieEtdVL1
     */
    public ViewLinkImpl getGroupeSaisiePcrsToGroupeSaisieEtdVL1() {
        return (ViewLinkImpl) findViewLink("GroupeSaisiePcrsToGroupeSaisieEtdVL1");
    }

    /**
     * Container's getter for isSess1Closed1.
     * @return isSess1Closed1
     */
    public isSess1ClosedImpl getisSess1Closed1() {
        return (isSess1ClosedImpl) findViewObject("isSess1Closed1");
    }

    /**
     * Container's getter for isJuryPdtROVO1.
     * @return isJuryPdtROVO1
     */
    public isJuryPdtROVOImpl getisJuryPdtROVO() {
        return (isJuryPdtROVOImpl) findViewObject("isJuryPdtROVO");
    }

    /**
     * Container's getter for CalParcoursAnnee1.
     * @return CalParcoursAnnee1
     */
    public CalParcoursAnneeImpl getCalParcoursAnnee() {
        return (CalParcoursAnneeImpl) findViewObject("CalParcoursAnnee");
    }

    /**
     * Container's getter for IsCalExist1.
     * @return IsCalExist1
     */
    public IsCalExistImpl getIsCalExist() {
        return (IsCalExistImpl) findViewObject("IsCalExist");
    }


    /**
     * Container's getter for UtilisateursROVO1.
     * @return UtilisateursROVO1
     */
    public ViewObjectImpl getUtilisateursROVO1() {
        return (ViewObjectImpl) findViewObject("UtilisateursROVO1");
    }

    /**
     * Container's getter for UserToStructureVL1.
     * @return UserToStructureVL1
     */
    public ViewLinkImpl getUserToStructureVL1() {
        return (ViewLinkImpl) findViewLink("UserToStructureVL1");
    }

    /**
     * Container's getter for IsResponsableFilUe1.
     * @return IsResponsableFilUe1
     */
    public IsResponsableFilUeImpl getIsResponsableFilUe() {
        return (IsResponsableFilUeImpl) findViewObject("IsResponsableFilUe");
    }

    /**
     * Container's getter for IsResponsableFilEc1.
     * @return IsResponsableFilEc1
     */
    public IsResponsableFilEcImpl getIsResponsableFilEc() {
        return (IsResponsableFilEcImpl) findViewObject("IsResponsableFilEc");
    }

    /**
     * Container's getter for StructureAccesROVO1.
     * @return StructureAccesROVO1
     */
    public StructureAccesROVOImpl getStructureAcces() {
        return (StructureAccesROVOImpl) findViewObject("StructureAcces");
    }
    /**
     * Container's getter for IsDelibCalendierROVO1.
     * @return IsDelibCalendierROVO1
     */
    public IsDelibCalendierROVOImpl getIsDelibCalendier() {
        return (IsDelibCalendierROVOImpl) findViewObject("IsDelibCalendier");
    }


    /**
     * Container's getter for UtilisateurNiveauFormationRO1.
     * @return UtilisateurNiveauFormationRO1
     */
    public UtilisateurNiveauFormationROImpl getUtilisateurNiveauFormationRO1() {
        return (UtilisateurNiveauFormationROImpl) findViewObject("UtilisateurNiveauFormationRO1");
    }

    /**
     * Container's getter for UtilisateurToUtilisateurNiveau1.
     * @return UtilisateurToUtilisateurNiveau1
     */
    public ViewLinkImpl getUtilisateurToUtilisateurNiveau1() {
        return (ViewLinkImpl) findViewLink("UtilisateurToUtilisateurNiveau1");
    }

    /**
     * Container's getter for GetRoleROVO1.
     * @return GetRoleROVO1
     */
    public GetRoleROVOImpl getGetRoleROVO() {
        return (GetRoleROVOImpl) findViewObject("GetRoleROVO");
    }

    /**
     * Container's getter for IsUserRoleExistROVO1.
     * @return IsUserRoleExistROVO1
     */
    public IsUserRoleExistROVOImpl getIsUserRoleExistROVO() {
        return (IsUserRoleExistROVOImpl) findViewObject("IsUserRoleExistROVO");
    }


    /**
     * Container's getter for AnoGenParcours1.
     * @return AnoGenParcours1
     
    public AnoGenParcoursImpl getAnoGenParcours() {
        return (AnoGenParcoursImpl) findViewObject("AnoGenParcours");
    }*/

    /**
     * Container's getter for AllInteFilEcROVO1.
     * @return AllInteFilEcROVO1
     
    public AllInteFilEcROVOImpl getAllInteFilEcROVO() {
        return (AllInteFilEcROVOImpl) findViewObject("AllInteFilEcROVO");
    }*/

    /**
     * Container's getter for AllEcFilUe1.
     * @return AllEcFilUe1
     */
    public AllEcFilUeImpl getAllEcFilUe() {
        return (AllEcFilUeImpl) findViewObject("AllEcFilUe");
    }

    /**
     * Container's getter for ReglesCompensationPArcoursROVO1.
     * @return ReglesCompensationPArcoursROVO1
     
    public ReglesCompensationPArcoursROVOImpl getReglesCompensationPArcoursROVO() {
        return (ReglesCompensationPArcoursROVOImpl) findViewObject("ReglesCompensationPArcoursROVO");
    }*/

    /**
     * Container's getter for IsMaquetteUsedROVO1.
     * @return IsMaquetteUsedROVO1
     
    public IsMaquetteUsedROVOImpl getIsMaquetteUsedROVO() {
        return (IsMaquetteUsedROVOImpl) findViewObject("IsMaquetteUsedROVO");
    }*/

    /**
     * Container's getter for DelibSemUeEtudiantROVO1.
     * @return DelibSemUeEtudiantROVO1
     
    public DelibSemUeEtudiantROVOImpl getDelibSemUeEtudiant() {
        return (DelibSemUeEtudiantROVOImpl) findViewObject("DelibSemUeEtudiant");
    }*/

    /**
     * Container's getter for isEtudiantNumeroValide1.
     * @return isEtudiantNumeroValide1
     
    public isEtudiantNumeroValideImpl getisEtudiantNumeroValide() {
        return (isEtudiantNumeroValideImpl) findViewObject("isEtudiantNumeroValide");
    }*/

    /**
     * Container's getter for DetailsSemestreEtudiant1.
     * @return DetailsSemestreEtudiant1
     
    public DetailsSemestreEtudiantImpl getDetailsSemestreEtudiant() {
        return (DetailsSemestreEtudiantImpl) findViewObject("DetailsSemestreEtudiant");
    }*/

    /**
     * Container's getter for DetailsAnnEtudiant1.
     * @return DetailsAnnEtudiant1
     
    public DetailsAnnEtudiantImpl getDetailsAnnEtudiant() {
        return (DetailsAnnEtudiantImpl) findViewObject("DetailsAnnEtudiant");
    }*/

    /**
     * Container's getter for isNoteModeEnsInterExist1.
     * @return isNoteModeEnsInterExist1
     
    public ViewObjectImpl getisNoteModeEnsInterExist1() {
        return (ViewObjectImpl) findViewObject("isNoteModeEnsInterExist1");
    }*/

    /**
     * Container's getter for HistoriquesStructuresVO1.
     * @return HistoriquesStructuresVO1
     
    public ViewObjectImpl getHistoriquesStructuresVO1() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresVO1");
    }*/

    /**
     * Container's getter for StructureToHStructureVL1.
     * @return StructureToHStructureVL1
     
    public ViewLinkImpl getStructureToHStructureVL1() {
        return (ViewLinkImpl) findViewLink("StructureToHStructureVL1");
    }*/

    /**
     * Container's getter for GroupeSaisieEtudiantROVO1.
     * @return GroupeSaisieEtudiantROVO1
     
    public ViewObjectImpl getGroupeSaisieEtudiantROVO1() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieEtudiantROVO1");
    }*/

    /**
     * Container's getter for GroupeSaisiePcrsToGroupeSaisieEtdVL1.
     * @return GroupeSaisiePcrsToGroupeSaisieEtdVL1
     
    public ViewLinkImpl getGroupeSaisiePcrsToGroupeSaisieEtdVL1() {
        return (ViewLinkImpl) findViewLink("GroupeSaisiePcrsToGroupeSaisieEtdVL1");
    }*/

    /**
     * Container's getter for isSess1Closed1.
     * @return isSess1Closed1
     
    public isSess1ClosedImpl getisSess1Closed1() {
        return (isSess1ClosedImpl) findViewObject("isSess1Closed1");
    }*/

    /**
     * Container's getter for isJuryPdtROVO1.
     * @return isJuryPdtROVO1
     
    public isJuryPdtROVOImpl getisJuryPdtROVO() {
        return (isJuryPdtROVOImpl) findViewObject("isJuryPdtROVO");
    }*/

    /**
     * Container's getter for CalParcoursAnnee1.
     * @return CalParcoursAnnee1
     
    public CalParcoursAnneeImpl getCalParcoursAnnee() {
        return (CalParcoursAnneeImpl) findViewObject("CalParcoursAnnee");
    }*/

    /**
     * Container's getter for IsCalExist1.
     * @return IsCalExist1
    
    public IsCalExistImpl getIsCalExist() {
        return (IsCalExistImpl) findViewObject("IsCalExist");
    } */


    /**
     * Container's getter for UtilisateursROVO1.
     * @return UtilisateursROVO1
     
    public ViewObjectImpl getUtilisateursROVO1() {
        return (ViewObjectImpl) findViewObject("UtilisateursROVO1");
    }*/

    /**
     * Container's getter for UserToStructureVL1.
     * @return UserToStructureVL1
     
    public ViewLinkImpl getUserToStructureVL1() {
        return (ViewLinkImpl) findViewLink("UserToStructureVL1");
    }*/

    /**
     * Container's getter for IsResponsableFilUe1.
     * @return IsResponsableFilUe1
     
    public IsResponsableFilUeImpl getIsResponsableFilUe() {
        return (IsResponsableFilUeImpl) findViewObject("IsResponsableFilUe");
    }*/

    /**
     * Container's getter for IsResponsableFilEc1.
     * @return IsResponsableFilEc1
     
    public IsResponsableFilEcImpl getIsResponsableFilEc() {
        return (IsResponsableFilEcImpl) findViewObject("IsResponsableFilEc");
    }*/

    /**
     * Container's getter for StructureAccesROVO1.
     * @return StructureAccesROVO1
     
    public StructureAccesROVOImpl getStructureAcces() {
        return (StructureAccesROVOImpl) findViewObject("StructureAcces");
    }*/

    /**
     * Container's getter for IsDelibCalendierROVO1.
     * @return IsDelibCalendierROVO1
     
    public IsDelibCalendierROVOImpl getIsDelibCalendier() {
        return (IsDelibCalendierROVOImpl) findViewObject("IsDelibCalendier");
    }*/


    /**
     * Container's getter for UtilisateurNiveauFormationRO1.
     * @return UtilisateurNiveauFormationRO1
     
    public UtilisateurNiveauFormationROImpl getUtilisateurNiveauFormationRO1() {
        return (UtilisateurNiveauFormationROImpl) findViewObject("UtilisateurNiveauFormationRO1");
    }*/

    /**
     * Container's getter for UtilisateurToUtilisateurNiveau1.
     * @return UtilisateurToUtilisateurNiveau1
     
    public ViewLinkImpl getUtilisateurToUtilisateurNiveau1() {
        return (ViewLinkImpl) findViewLink("UtilisateurToUtilisateurNiveau1");
    }*/

    /**
     * Container's getter for GetRoleROVO1.
     * @return GetRoleROVO1
     
    public GetRoleROVOImpl getGetRoleROVO() {
        return (GetRoleROVOImpl) findViewObject("GetRoleROVO");
    }*/

    /**
     * Container's getter for IsUserRoleExistROVO1.
     * @return IsUserRoleExistROVO1

    public IsUserRoleExistROVOImpl getIsUserRoleExistROVO() {
        return (IsUserRoleExistROVOImpl) findViewObject("IsUserRoleExistROVO");
    }*/

    /**
     * Container's getter for IsAnParcoursClosed1.
     * @return IsAnParcoursClosed1
     */
    public IsAnParcoursClosedImpl getIsAnParcoursClosed() {
        return (IsAnParcoursClosedImpl) findViewObject("IsAnParcoursClosed");
    }

    /**
     * Container's getter for IsDelibDefEnd1.
     * @return IsDelibDefEnd1
     */
    public IsDelibDefEndImpl getIsDelibDefEnd() {
        return (IsDelibDefEndImpl) findViewObject("IsDelibDefEnd");
    }

    /**
     * Container's getter for IsDelibDefStart1.
     * @return IsDelibDefStart1
     */
    public IsDelibDefStartImpl getIsDelibDefStart() {
        return (IsDelibDefStartImpl) findViewObject("IsDelibDefStart");
    }

    /**
     * Container's getter for IsDelibProvEnd1.
     * @return IsDelibProvEnd1
     */
    public IsDelibProvEndImpl getIsDelibProvEnd() {
        return (IsDelibProvEndImpl) findViewObject("IsDelibProvEnd");
    }

    /**
     * Container's getter for IsDelibProvStart1.
     * @return IsDelibProvStart1
     */
    public IsDelibProvStartImpl getIsDelibProvStart() {
        return (IsDelibProvStartImpl) findViewObject("IsDelibProvStart");
    }

    /**
     * Container's getter for IsReclStart1.
     * @return IsReclStart1
     */
    public IsReclStartImpl getIsReclStart() {
        return (IsReclStartImpl) findViewObject("IsReclStart");
    }

    /**
     * Container's getter for IsReclEnd1.
     * @return IsReclEnd1
     */
    public IsReclEndImpl getIsReclEnd() {
        return (IsReclEndImpl) findViewObject("IsReclEnd");
    }


    /**
     * Container's getter for JuryVO1.
     * @return JuryVO1
     */
    public ViewObjectImpl getJuryVO1() {
        return (ViewObjectImpl) findViewObject("JuryVO1");
    }

    /**
     * Container's getter for ParcoursToJury1.
     * @return ParcoursToJury1
     */
    public ViewLinkImpl getParcoursToJury1() {
        return (ViewLinkImpl) findViewLink("ParcoursToJury1");
    }

    /**
     * Container's getter for UtilisateursJuryVO1.
     * @return UtilisateursJuryVO1
     */
    public ViewObjectImpl getUtilisateursJuryVO3() {
        return (ViewObjectImpl) findViewObject("UtilisateursJuryVO3");
    }

    /**
     * Container's getter for JuryToMembre1.
     * @return JuryToMembre1
     */
    public ViewLinkImpl getJuryToMembre1() {
        return (ViewLinkImpl) findViewLink("JuryToMembre1");
    }

    /**
     * Container's getter for UtilisateurFormationJuryROVO1.
     * @return UtilisateurFormationJuryROVO1
     */
    public UtilisateurFormationJuryROVOImpl getUtilisateurFormationJuryROVO() {
        return (UtilisateurFormationJuryROVOImpl) findViewObject("UtilisateurFormationJuryROVO");
    }

    /**
     * Container's getter for UtilisateurFormationsVO1.
     * @return UtilisateurFormationsVO1
     */
    public ViewObjectImpl getUtilisateurFormationsVO() {
        return (ViewObjectImpl) findViewObject("UtilisateurFormationsVO");
    }

    /**
     * Container's getter for AccesFormationList1.
     * @return AccesFormationList1
     */
    public AccesFormationListImpl getAccesFormationList() {
        return (AccesFormationListImpl) findViewObject("AccesFormationList");
    }

    /**
     * Container's getter for NivFormSpecialiteROVO1.
     * @return NivFormSpecialiteROVO1
     */
    public NivFormSpecialiteROVOImpl getNivFormSpecialiteROVO() {
        return (NivFormSpecialiteROVOImpl) findViewObject("NivFormSpecialiteROVO");
    }

    /**
     * Container's getter for AccessNivFormToNivFormSpec1.
     * @return AccessNivFormToNivFormSpec1
     */
    public ViewLinkImpl getAccessNivFormToNivFormSpec1() {
        return (ViewLinkImpl) findViewLink("AccessNivFormToNivFormSpec1");
    }

    /**
     * Container's getter for NivFormSpecOptionROVO1.
     * @return NivFormSpecOptionROVO1
     */
    public NivFormSpecOptionROVOImpl getNivFormSpecOptionROVO() {
        return (NivFormSpecOptionROVOImpl) findViewObject("NivFormSpecOptionROVO");
    }

    /**
     * Container's getter for NivFormSpecToNivFormSpecOpt1.
     * @return NivFormSpecToNivFormSpecOpt1
     */
    public ViewLinkImpl getNivFormSpecToNivFormSpecOpt1() {
        return (ViewLinkImpl) findViewLink("NivFormSpecToNivFormSpecOpt1");
    }

    /**
     * Container's getter for AccesNiveauFormationList1.
     * @return AccesNiveauFormationList1
     */
    public AccesNiveauFormationListImpl getAccesNiveauFormationList() {
        return (AccesNiveauFormationListImpl) findViewObject("AccesNiveauFormationList");
    }

    /**
     * Container's getter for DelibSemEtuRepecheROVO1.
     * @return DelibSemEtuRepecheROVO1
     */
    public DelibSemEtuRepecheROVOImpl getDelibSemEtuRepecheROVO() {
        return (DelibSemEtuRepecheROVOImpl) findViewObject("DelibSemEtuRepecheROVO");
    }

    /**
     * Container's getter for FilEcInsPedSemUe1.
     * @return FilEcInsPedSemUe1
     
    public FilEcInsPedSemUeImpl getFilEcInsPedSemUe() {
        return (FilEcInsPedSemUeImpl) findViewObject("FilEcInsPedSemUe");
    }*/

    /**
     * Container's getter for FilUeByFilEc1.
     * @return FilUeByFilEc1
     
    public FilUeByFilEcImpl getFilUeByFilEc() {
        return (FilUeByFilEcImpl) findViewObject("FilUeByFilEc");
    }*/

    /**
     * Container's getter for FilUeInsPedSem1.
     * @return FilUeInsPedSem1
     
    public FilUeInsPedSemImpl getFilUeInsPedSem() {
        return (FilUeInsPedSemImpl) findViewObject("FilUeInsPedSem");
    }*/

    /**
     * Container's getter for IsFormationAllowedAccess1.
     * @return IsFormationAllowedAccess1
     
    public IsFormationAllowedAccessImpl getIsFormationAllowedAccess() {
        return (IsFormationAllowedAccessImpl) findViewObject("IsFormationAllowedAccess");
    }*/

    /**
     * Container's getter for isJuryAllowedAccess1.
     * @return isJuryAllowedAccess1
     */
    public isJuryAllowedAccessImpl getisJuryAllowedAccess() {
        return (isJuryAllowedAccessImpl) findViewObject("isJuryAllowedAccess");
    }

    /**
     * Container's getter for IsJuryEmpty1.
     * @return IsJuryEmpty1
     */
    public IsJuryEmptyImpl getIsJuryEmpty() {
        return (IsJuryEmptyImpl) findViewObject("IsJuryEmpty");
    }

    /**
     * Container's getter for isJuryExistROVO1.
     * @return isJuryExistROVO1
     */
    public isJuryExistROVOImpl getisJuryExistROVO() {
        return (isJuryExistROVOImpl) findViewObject("isJuryExistROVO");
    }

    /**
     * Container's getter for isNivFormAccessAllowed1.
     * @return isNivFormAccessAllowed1
     */
    public isNivFormAccessAllowedImpl getisNivFormAccessAllowed() {
        return (isNivFormAccessAllowedImpl) findViewObject("isNivFormAccessAllowed");
    }

    /**
     * Container's getter for isNivFormSpecAccessAllowed1.
     * @return isNivFormSpecAccessAllowed1
     */
    public isNivFormSpecAccessAllowedImpl getisNivFormSpecAccessAllowed() {
        return (isNivFormSpecAccessAllowedImpl) findViewObject("isNivFormSpecAccessAllowed");
    }

    /**
     * Container's getter for isNivFormSpecOptAccessAllowed1.
     * @return isNivFormSpecOptAccessAllowed1
     */
    public isNivFormSpecOptAccessAllowedImpl getisNivFormSpecOptAccessAllowed() {
        return (isNivFormSpecOptAccessAllowedImpl) findViewObject("isNivFormSpecOptAccessAllowed");
    }

    /**
     * Container's getter for isRespFormationExist1.
     * @return isRespFormationExist1
     */
    public isRespFormationExistImpl getisRespFormationExist() {
        return (isRespFormationExistImpl) findViewObject("isRespFormationExist");
    }

    /**
     * Container's getter for isRespNivFormExist1.
     * @return isRespNivFormExist1
     */
    public isRespNivFormExistImpl getisRespNivFormExist() {
        return (isRespNivFormExistImpl) findViewObject("isRespNivFormExist");
    }

    /**
     * Container's getter for isRespNivFormSpecExist1.
     * @return isRespNivFormSpecExist1
     */
    public isRespNivFormSpecExistImpl getisRespNivFormSpecExist() {
        return (isRespNivFormSpecExistImpl) findViewObject("isRespNivFormSpecExist");
    }

    /**
     * Container's getter for isRespNivFormSpecOptExist1.
     * @return isRespNivFormSpecOptExist1
     */
    public isRespNivFormSpecOptExistImpl getisRespNivFormSpecOptExist() {
        return (isRespNivFormSpecOptExistImpl) findViewObject("isRespNivFormSpecOptExist");
    }

    /**
     * Container's getter for JuryParcoursAnneeROVO1.
     * @return JuryParcoursAnneeROVO1
     */
    public JuryParcoursAnneeROVOImpl getJuryParcoursAnneeROVO() {
        return (JuryParcoursAnneeROVOImpl) findViewObject("JuryParcoursAnneeROVO");
    }

    /**
     * Container's getter for MoyenneUE1.
     * @return MoyenneUE1
     
    public MoyenneUEImpl getMoyenneUE() {
        return (MoyenneUEImpl) findViewObject("MoyenneUE");
    }*/

    /**
     * Container's getter for NiveauFormationList1.
     * @return NiveauFormationList1
     */
    public NiveauFormationListImpl getNiveauFormationList() {
        return (NiveauFormationListImpl) findViewObject("NiveauFormationList");
    }

    /**
     * Container's getter for PourcentageCC_CT_ROVO1.
     * @return PourcentageCC_CT_ROVO1
     
    public PourcentageCC_CT_ROVOImpl getPourcentageCC_CT_ROVO() {
        return (PourcentageCC_CT_ROVOImpl) findViewObject("PourcentageCC_CT_ROVO");
    }*/

    /**
     * Container's getter for ResultatsFilUeSemestreVO1.
     * @return ResultatsFilUeSemestreVO1
     
    public ViewObjectImpl getResultatsFilUeSemestreVO() {
        return (ViewObjectImpl) findViewObject("ResultatsFilUeSemestreVO");
    }*/

    /**
     * Container's getter for NotesModeEnseignementVO1.
     * @return NotesModeEnseignementVO1
     
    public NotesModeEnseignementVOImpl getNotesModeEnseignementVO() {
        return (NotesModeEnseignementVOImpl) findViewObject("NotesModeEnseignementVO");
    }*/

    /**
     * Container's getter for ResultatsSemestreVO1.
     * @return ResultatsSemestreVO1
     */
    public ViewObjectImpl getResultatsSemestreVO() {
        return (ViewObjectImpl) findViewObject("ResultatsSemestreVO");
    }

    /**
     * Container's getter for ResultatFilUeSemestreROVO1.
     * @return ResultatFilUeSemestreROVO1
     
    public ResultatFilUeSemestreROVOImpl getResultatFilUeSemestreROVO() {
        return (ResultatFilUeSemestreROVOImpl) findViewObject("ResultatFilUeSemestreROVO");
    }*/

    /**
     * Container's getter for NotesModeEnseignementVO1.
     * @return NotesModeEnseignementVO1
     
    public NotesModeEnseignementVOImpl getNotesModeEnseignementVO() {
        return (NotesModeEnseignementVOImpl) findViewObject("NotesModeEnseignementVO");
    }*/

    /**
     * Container's getter for UtilisateurAccessFormationROVO1.
     * @return UtilisateurAccessFormationROVO1
     */
    public UtilisateurAccessFormationROVOImpl getUtilisateurAccessFormationROVO() {
        return (UtilisateurAccessFormationROVOImpl) findViewObject("UtilisateurAccessFormationROVO");
    }

    /**
     * Container's getter for UtilisateursROVO2.
     * @return UtilisateursROVO2
     */
    public ViewObjectImpl getUtilisateurs() {
        return (ViewObjectImpl) findViewObject("Utilisateurs");
    }

    /**
     * Container's getter for IsFormationAllowedAccess.
     * @return IsFormationAllowedAccess
     */
    public IsFormationAllowedAccessImpl getIsFormationAllowedAccess() {
        return (IsFormationAllowedAccessImpl) findViewObject("IsFormationAllowedAccess");
    }

    /**
     * Container's getter for IsParamModeCntrlExistROVO1.
     * @return IsParamModeCntrlExistROVO1
     */
    public IsParamModeCntrlExistROVOImpl getIsParamModeCntrlExistROVO() {
        return (IsParamModeCntrlExistROVOImpl) findViewObject("IsParamModeCntrlExistROVO");
    }

    /**
     * Container's getter for IsParamModeCntrlTerExistROVO1.
     * @return IsParamModeCntrlTerExistROVO1
     */
    public IsParamModeCntrlTerExistROVOImpl getIsParamModeCntrlTerExistROVO() {
        return (IsParamModeCntrlTerExistROVOImpl) findViewObject("IsParamModeCntrlTerExistROVO");
    }

    /**
     * Container's getter for IsModeControlEcUsedROVO1.
     * @return IsModeControlEcUsedROVO1
     */
    public IsModeControlEcUsedROVOImpl getIsModeControlEcUsedROVO() {
        return (IsModeControlEcUsedROVOImpl) findViewObject("IsModeControlEcUsedROVO");
    }

    /**
     * Container's getter for StructureAccesByUsername1.
     * @return StructureAccesByUsername1
     */
    public StructureAccesByUsernameImpl getStructureAccesByUsername() {
        return (StructureAccesByUsernameImpl) findViewObject("StructureAccesByUsername");
    }

    /**
     * Container's getter for HistoriqueStructureByUserName1.
     * @return HistoriqueStructureByUserName1
     */
    public HistoriqueStructureByUserNameImpl getHistoriqueStructureByUserName() {
        return (HistoriqueStructureByUserNameImpl) findViewObject("HistoriqueStructureByUserName");
    }

    /**
     * Container's getter for GradeSemestreROVO1.
     * @return GradeSemestreROVO1
     */
    public GradeSemestreROVOImpl getGradeSemestreROVO() {
        return (GradeSemestreROVOImpl) findViewObject("GradeSemestreROVO");
    }

    /**
     * Container's getter for RolesVO1.
     * @return RolesVO1
     */
    public ViewObjectImpl getRolesVO() {
        return (ViewObjectImpl) findViewObject("RolesVO");
    }


    /**
     * Container's getter for IsRoleUserExist1.
     * @return IsRoleUserExist1
     */
    public IsRoleUserExistImpl getIsRoleUserExist() {
        return (IsRoleUserExistImpl) findViewObject("IsRoleUserExist");
    }

    /**
     * Container's getter for FonctionnalitesVO1.
     * @return FonctionnalitesVO1
     */
    public ViewObjectImpl getFonctionnalitesVO() {
        return (ViewObjectImpl) findViewObject("FonctionnalitesVO");
    }

    /**
     * Container's getter for IsFonctionUserExist1.
     * @return IsFonctionUserExist1
     */
    public IsFonctionUserExistImpl getIsFonctionUserExist() {
        return (IsFonctionUserExistImpl) findViewObject("IsFonctionUserExist");
    }

    /**
     * Container's getter for UtilisateursFonctionnalitesVO1.
     * @return UtilisateursFonctionnalitesVO1
     */
    public ViewObjectImpl getUtilisateursFonctionnalitesVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursFonctionnalitesVO");
    }

    /**
     * Container's getter for UtilisateursRolesVO1.
     * @return UtilisateursRolesVO1
     */
    public ViewObjectImpl getUtilisateursRoles() {
        return (ViewObjectImpl) findViewObject("UtilisateursRoles");
    }

    /**
     * Container's getter for UtiStructureToUtiRole1.
     * @return UtiStructureToUtiRole1
     */
    public ViewLinkImpl getUtiStructureToUtiRole1() {
        return (ViewLinkImpl) findViewLink("UtiStructureToUtiRole1");
    }

    /**
     * Container's getter for UtilisateursFonctionnalitesVO1.
     * @return UtilisateursFonctionnalitesVO1
     */
    public ViewObjectImpl getUtilisateursFonctionnalites() {
        return (ViewObjectImpl) findViewObject("UtilisateursFonctionnalites");
    }

    /**
     * Container's getter for UtuStructureToUtiFonction1.
     * @return UtuStructureToUtiFonction1
     */
    public ViewLinkImpl getUtuStructureToUtiFonction1() {
        return (ViewLinkImpl) findViewLink("UtuStructureToUtiFonction1");
    }

    /**
     * Container's getter for NoteModeEnsInterByGroupeSaisie1.
     * @return NoteModeEnsInterByGroupeSaisie1
     */
    public NoteModeEnsInterByGroupeSaisieImpl getNoteModeEnsInterByGroupeSaisie() {
        return (NoteModeEnsInterByGroupeSaisieImpl) findViewObject("NoteModeEnsInterByGroupeSaisie");
    }

    /**
     * Container's getter for GroupeSaisieNoteROVO1.
     * @return GroupeSaisieNoteROVO1
     */
    public GroupeSaisieNoteROVOImpl getGroupeSaisieNoteROVO() {
        return (GroupeSaisieNoteROVOImpl) findViewObject("GroupeSaisieNoteROVO");
    }


    /**
     * Container's getter for grhUser1.
     * @return grhUser1
     */
    public grhUserImpl getgrhUser() {
        return (grhUserImpl) findViewObject("grhUser");
    }

    /**
     * Container's getter for grhUserEtab1.
     * @return grhUserEtab1
     */
    public grhUserEtabImpl getgrhUserEtab() {
        return (grhUserEtabImpl) findViewObject("grhUserEtab");
    }

    /**
     * Container's getter for UtilisateurEtabROVO1.
     * @return UtilisateurEtabROVO1
     */
    public UtilisateurEtabROVOImpl getUtilisateurEtabROVO() {
        return (UtilisateurEtabROVOImpl) findViewObject("UtilisateurEtabROVO");
    }

    /**
     * Container's getter for DepartementsEtabROVO1.
     * @return DepartementsEtabROVO1
     */
    public DepartementsEtabROVOImpl getDepartementsEtabROVO() {
        return (DepartementsEtabROVOImpl) findViewObject("DepartementsEtabROVO");
    }



    /**
     * Container's getter for UtilisateursROVO2.
     * @return UtilisateursROVO2
     */
    /*public ViewObjectImpl getUtilisateurs() {
        return (ViewObjectImpl) findViewObject("Utilisateurs");
    }*/

    /**
     * Container's getter for UserToStructureVL2.
     * @return UserToStructureVL2
     */
    public ViewLinkImpl getUserToStructureVL2() {
        return (ViewLinkImpl) findViewLink("UserToStructureVL2");
    }

    /**
     * Container's getter for StructureAccesByUsername1.
     * @return StructureAccesByUsername1
     */
    /*public StructureAccesByUsernameImpl getStructureAccesByUsername() {
        return (StructureAccesByUsernameImpl) findViewObject("StructureAccesByUsername");
    }*/

    /**
     * Container's getter for HistoriqueStructureByUserName1.
     * @return HistoriqueStructureByUserName1
     */
    /*public HistoriqueStructureByUserNameImpl getHistoriqueStructureByUserName() {
        return (HistoriqueStructureByUserNameImpl) findViewObject("HistoriqueStructureByUserName");
    }*/

    /**
     * Container's getter for GradeSemestreROVO1.
     * @return GradeSemestreROVO1
     */
    /*public GradeSemestreROVOImpl getGradeSemestreROVO() {
        return (GradeSemestreROVOImpl) findViewObject("GradeSemestreROVO");
    }*/

    /**
     * Container's getter for GroupeSaisieROVO1.
     * @return GroupeSaisieROVO1
     */
    public ViewObjectImpl getGroupeSaisieROVO() {
        return (ViewObjectImpl) findViewObject("GroupeSaisieROVO");
    }

    /**
     * Container's getter for GroupeSaisieNoteROVO1.
     * @return GroupeSaisieNoteROVO1
     */
    /*public GroupeSaisieNoteROVOImpl getGroupeSaisieNoteROVO() {
        return (GroupeSaisieNoteROVOImpl) findViewObject("GroupeSaisieNoteROVO");
    }*/

    /**
     * Container's getter for NoteModeEnsInterByGroupeSaisie1.
     * @return NoteModeEnsInterByGroupeSaisie1
     */
    /*public NoteModeEnsInterByGroupeSaisieImpl getNoteModeEnsInterByGroupeSaisie() {
        return (NoteModeEnsInterByGroupeSaisieImpl) findViewObject("NoteModeEnsInterByGroupeSaisie");
    }*/

    /**
     * Container's getter for FonctionnalitesVO1.
     * @return FonctionnalitesVO1
     */
    /*public ViewObjectImpl getFonctionnalitesVO() {
        return (ViewObjectImpl) findViewObject("FonctionnalitesVO");
    }*/

    /**
     * Container's getter for IsFonctionUserExist1.
     * @return IsFonctionUserExist1
     */
    /*public IsFonctionUserExistImpl getIsFonctionUserExist() {
        return (IsFonctionUserExistImpl) findViewObject("IsFonctionUserExist");
    }*/

    /**
     * Container's getter for UtilisateursFonctionnalitesVO1.
     * @return UtilisateursFonctionnalitesVO1
     */
    /*public ViewObjectImpl getUtilisateursFonctionnalitesVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursFonctionnalitesVO");
    }*/

    /**
     * Container's getter for UtilisateursFonctionnalitesVO1.
     * @return UtilisateursFonctionnalitesVO1
     */
    /*public ViewObjectImpl getUtilisateursFonctionnalites() {
        return (ViewObjectImpl) findViewObject("UtilisateursFonctionnalites");
    }*/

    /**
     * Container's getter for RolesVO1.
     * @return RolesVO1
     */
    /*public ViewObjectImpl getRolesVO() {
        return (ViewObjectImpl) findViewObject("RolesVO");
    }*/

    /**
     * Container's getter for UtilisateursRolesVO1.
     * @return UtilisateursRolesVO1
     */
    /*public ViewObjectImpl getUtilisateursRolesVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursRolesVO");
    }*/

    /**
     * Container's getter for IsRoleUserExist1.
     * @return IsRoleUserExist1
     */
   /* public IsRoleUserExistImpl getIsRoleUserExist() {
        return (IsRoleUserExistImpl) findViewObject("IsRoleUserExist");
    }*/

    /**
     * Container's getter for UtilisateursRolesVO1.
     * @return UtilisateursRolesVO1
     */
    /*public ViewObjectImpl getUtilisateursRoles() {
        return (ViewObjectImpl) findViewObject("UtilisateursRoles");
    }*/

    /**
     * Container's getter for grhUser1.
     * @return grhUser1
     */
    /*public grhUserImpl getgrhUser() {
        return (grhUserImpl) findViewObject("grhUser");
    }*/

    /**
     * Container's getter for grhUserEtab1.
     * @return grhUserEtab1
     */
    /*public grhUserEtabImpl getgrhUserEtab() {
        return (grhUserEtabImpl) findViewObject("grhUserEtab");
    }*/

    /**
     * Container's getter for UtilisateurEtabROVO2.
     * @return UtilisateurEtabROVO2
     */
    /*public UtilisateurEtabROVOImpl getUtilisateurEtabROVO() {
        return (UtilisateurEtabROVOImpl) findViewObject("UtilisateurEtabROVO");
    }*/

    /**
     * Container's getter for DepartementsEtabROVO1.
     * @return DepartementsEtabROVO1
     */
    /*public DepartementsEtabROVOImpl getDepartementsEtabROVO() {
        return (DepartementsEtabROVOImpl) findViewObject("DepartementsEtabROVO");
    }*/

    /**
     * Container's getter for UtilisateursRolesVO1.
     * @return UtilisateursRolesVO1
     */
    public ViewObjectImpl getUtilisateursRolesVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursRolesVO");
    }

    /**
     * Container's getter for UtilisateursJuryVO2.
     * @return UtilisateursJuryVO2
     */
    public ViewObjectImpl getUtilisateursJuryVO1() {
        return (ViewObjectImpl) findViewObject("UtilisateursJuryVO1");
    }


    /**
     * Container's getter for ParcrsParamModeSaisieECROVO1.
     * @return ParcrsParamModeSaisieECROVO1
     */
    public ParcrsParamModeSaisieECROVOImpl getParcrsParamModeSaisieEC() {
        return (ParcrsParamModeSaisieECROVOImpl) findViewObject("ParcrsParamModeSaisieEC");
    }

    /**
     * Container's getter for FiliereUeSemestreROVO1.
     * @return FiliereUeSemestreROVO1
     */
    public FiliereUeSemestreROVOImpl getFiliereUeSemestreROVO() {
        return (FiliereUeSemestreROVOImpl) findViewObject("FiliereUeSemestreROVO");
    }

    /**
     * Container's getter for PrcrsModeSaisieToFilUeSemVL1.
     * @return PrcrsModeSaisieToFilUeSemVL1
     */
    public ViewLinkImpl getPrcrsModeSaisieToFilUeSemVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsModeSaisieToFilUeSemVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreEcROVO1.
     * @return FiliereUeSemestreEcROVO1
     */
    public ViewObjectImpl getFiliereUeSemestreEcROVO() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemestreEcROVO");
    }

    /**
     * Container's getter for FiliereUeSemestreToFiliereUeSemestreEc2.
     * @return FiliereUeSemestreToFiliereUeSemestreEc2
     */
    public ViewLinkImpl getFiliereUeSemestreToFiliereUeSemestreEc2() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemestreToFiliereUeSemestreEc2");
    }

    /**
     * Container's getter for ModeControleEcVO1.
     * @return ModeControleEcVO1
     */
    public ViewObjectImpl getModeControleEcVO() {
        return (ViewObjectImpl) findViewObject("ModeControleEcVO");
    }

    /**
     * Container's getter for FiliereUeSemestreEcToModeControleEc2.
     * @return FiliereUeSemestreEcToModeControleEc2
     */
    public ViewLinkImpl getFiliereUeSemestreEcToModeControleEc2() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemestreEcToModeControleEc2");
    }

    /**
     * Container's getter for MoyenneUE1.
     * @return MoyenneUE1
     
    public MoyenneUEImpl getMoyenneUE() {
        return (MoyenneUEImpl) findViewObject("MoyenneUE");
    }*/

    /**
     * Container's getter for PourcentageCC_CT_ROVO1.
     * @return PourcentageCC_CT_ROVO1
     
    public PourcentageCC_CT_ROVOImpl getPourcentageCC_CT_ROVO() {
        return (PourcentageCC_CT_ROVOImpl) findViewObject("PourcentageCC_CT_ROVO");
    }*/

    /**
     * Container's getter for FilUeByFilEc1.
     * @return FilUeByFilEc1
     
    public FilUeByFilEcImpl getFilUeByFilEc() {
        return (FilUeByFilEcImpl) findViewObject("FilUeByFilEc");
    }*/

    /**
     * Container's getter for ResultatsFilUeSemestreVO1.
     * @return ResultatsFilUeSemestreVO1

    public ViewObjectImpl getResultatsFilUeSemestreVO() {
        return (ViewObjectImpl) findViewObject("ResultatsFilUeSemestreVO");
    }*/

    /**
     * Container's getter for NotesModeEnseignementVO1.
     * @return NotesModeEnseignementVO1
     */
    public NotesModeEnseignementVOImpl getNotesModeEnseignementVO() {
        return (NotesModeEnseignementVOImpl) findViewObject("NotesModeEnseignementVO");
    }


    /**
     * Container's getter for UtilisateursVO1.
     * @return UtilisateursVO1
     */
    public ViewObjectImpl getUtilisateursVO1() {
        return (ViewObjectImpl) findViewObject("UtilisateursVO1");
    }

    /**
     * Container's getter for RoleAAssignerVO2.
     * @return RoleAAssignerVO2
     */
    public ViewObjectImpl getRoleAAssignerVO2() {
        return (ViewObjectImpl) findViewObject("RoleAAssignerVO2");
    }

    /**
     * Container's getter for UtilisateursVO2.
     * @return UtilisateursVO2
     */
    public ViewObjectImpl getUtilisateursVO2() {
        return (ViewObjectImpl) findViewObject("UtilisateursVO2");
    }

    /**
     * Container's getter for RoleAAssignerVO3.
     * @return RoleAAssignerVO3
     */
    public ViewObjectImpl getRoleAAssignerVO3() {
        return (ViewObjectImpl) findViewObject("RoleAAssignerVO3");
    }

    /**
     * Container's getter for FkRoleassignUticreeLink.
     * @return FkRoleassignUticreeLink
     */
    public ViewLinkImpl getFkRoleassignUticreeLink() {
        return (ViewLinkImpl) findViewLink("FkRoleassignUticreeLink");
    }

    /**
     * Container's getter for DepartementUserROVO1.
     * @return DepartementUserROVO1
     */
    public DepartementUserROVOImpl getDepartementUser() {
        return (DepartementUserROVOImpl) findViewObject("DepartementUser");
    }

    /**
     * Container's getter for EtabUserToDeptUserVL1.
     * @return EtabUserToDeptUserVL1
     */
    public ViewLinkImpl getEtabUserToDeptUserVL1() {
        return (ViewLinkImpl) findViewLink("EtabUserToDeptUserVL1");
    }

    /**
     * Container's getter for ParcoursUserROVO1.
     * @return ParcoursUserROVO1
     */
    public ParcoursUserROVOImpl getParcoursUser() {
        return (ParcoursUserROVOImpl) findViewObject("ParcoursUser");
    }

    /**
     * Container's getter for DeptUserToParcoursUserVL1.
     * @return DeptUserToParcoursUserVL1
     */
    public ViewLinkImpl getDeptUserToParcoursUserVL1() {
        return (ViewLinkImpl) findViewLink("DeptUserToParcoursUserVL1");
    }

    /**
     * Container's getter for UtilisateursRolesROVO1.
     * @return UtilisateursRolesROVO1
     */
    public UtilisateursRolesROVOImpl getUtilisateursRolesROVO() {
        return (UtilisateursRolesROVOImpl) findViewObject("UtilisateursRolesROVO");
    }

    /**
     * Container's getter for UtiStructureToUtiRoleVl1.
     * @return UtiStructureToUtiRoleVl1
     */
    public ViewLinkImpl getUtiStructureToUtiRoleVl1() {
        return (ViewLinkImpl) findViewLink("UtiStructureToUtiRoleVl1");
    }

    /**
     * Container's getter for GradeSemestreROVO1.
     * @return GradeSemestreROVO1
     */
    public GradeSemestreROVOImpl getGradeSemestre() {
        return (GradeSemestreROVOImpl) findViewObject("GradeSemestre");
    }

    /**
     * Container's getter for ParcoursUserToGradeSemestreVL1.
     * @return ParcoursUserToGradeSemestreVL1
     */
    public ViewLinkImpl getParcoursUserToGradeSemestreVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursUserToGradeSemestreVL1");
    }

    /**
     * Container's getter for ParcoursMaqAnCalendrierROVO1.
     * @return ParcoursMaqAnCalendrierROVO1
     */
    public ParcoursMaqAnCalendrierROVOImpl getParcoursMaqAnCalendrier() {
        return (ParcoursMaqAnCalendrierROVOImpl) findViewObject("ParcoursMaqAnCalendrier");
    }

    /**
     * Container's getter for ListRelevesChoice1.
     * @return ListRelevesChoice1
     */
    public ListRelevesChoiceImpl getListRelevesChoice() {
        return (ListRelevesChoiceImpl) findViewObject("ListRelevesChoice");
    }


    /* New Package optimizing traitement*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void Initialiser(Long prcrs_mq_an_id, Long calendrier, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.Initialiser(?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_mq_an_id);
            statement.setLong(2, calendrier);
            statement.setLong(3, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateInscription(Long prcrs, Long annee, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.UpdateInscription(?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs);
            statement.setLong(2, annee);
            statement.setLong(3, utilisateur);
            statement.execute();
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
    
/*FindAndUpdateNote(id_mode_ctrl_ec,cal_id,d_type_ctrl,numero,note,utilisateur)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void FindAndUpdateNote(Long id_mode_ctrl_ec, Long calendrier, Long id_type_ctrl, String numero, Float note, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.FindAndUpdateNote(?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, id_mode_ctrl_ec);
            statement.setLong(2, calendrier);
            statement.setLong(3, id_type_ctrl);
            statement.setString(4, numero);
            statement.setFloat(5, note);
            statement.setLong(6, utilisateur);
            statement.execute();
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
   
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void ClearNote(Long id_mode_ctrl_ec, Long calendrier, Long id_type_ctrl, String numero, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.ClearNote(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, id_mode_ctrl_ec);
            statement.setLong(2, calendrier);
            statement.setLong(3, id_type_ctrl);
            statement.setString(4, numero);
            statement.setLong(5, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long FindAndUpdateNoteEtd(Long id_mode_ctrl_ec, Long calendrier, Long id_type_ctrl, String numero, Float note, Long utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.FindAndUpdateNote(?,?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, id_mode_ctrl_ec);
            cls.setLong(3, calendrier);
            cls.setLong(4, id_type_ctrl);
            cls.setString(5, numero);
            cls.setFloat(6, note);
            cls.setLong(7, utilisateur);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
            cls.executeUpdate();

            result = cls.getLong(1);

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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DupliquerNote(Long calendrier, Long fil_ue, Long fil_ec, Long type_cntrl, Long mode_cntrl_ec, Long prcrs_mq, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DupliquerNote(?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, fil_ue);
            statement.setLong(3, fil_ec);
            statement.setLong(4, type_cntrl);
            statement.setLong(5, mode_cntrl_ec);
            statement.setLong(6, prcrs_mq);
            statement.setLong(7, utilisateur);
            statement.execute();
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

    /*DupliquerNoteSess2(cal_id , fil_ec, prcrs_mq, utilisateur)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DupliquerNoteSess2(Long calendrier, Long fil_ec, Long prcrs_mq, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DupliquerNoteSess2(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, fil_ec);
            statement.setLong(3, prcrs_mq);
            statement.setLong(4, utilisateur);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void AllowAcessFusFecTo(Long prcrs_id, Long annee_id, Long sem_id, Long pdt_id,Long user_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.AllowAcessFusFecTo(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_id);
            statement.setLong(2, annee_id);
            statement.setLong(3, sem_id);
            statement.setLong(4, pdt_id);
            statement.setLong(5, user_id);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void CalculerMoyenneEcTypeControle(Long calendrier, Long parcours_maq, Long fil_ec, Long type_cntrl,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.CalculMoyenneEcTypeControle(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, fil_ec);
            statement.setLong(4, type_cntrl);
            statement.setLong(5, utilisateur);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void CalculerMoyenneEc(Long calendrier, Long parcours_maq, Long fil_ec, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.CalculMoyenneEc(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, fil_ec);
            statement.setLong(4, utilisateur);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DelibererUe(Long calendrier, Long parcours_maq, Long fil_ue,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DelibererUe(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, fil_ue);
            statement.setLong(4, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    
    public Integer getTotalCoefUe(Long fil_ue) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.getTotalCoefUe(?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, fil_ue);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    public Integer getTotalCoef(Long calendrier,Long prcrs_maq) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.getTotalCoef(?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, calendrier);
             cls.setLong(3, prcrs_maq);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    public Integer getTotalCoefEcInvalid(Long fil_ue, Long inspedUe, Long calendrier, BigDecimal moyenne_valid) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.getTotalCoefEcInvalid(?,?,?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setBigDecimal(2, moyenne_valid);
             cls.setLong(3, fil_ue);
             cls.setLong(4, inspedUe);
             cls.setLong(5, calendrier);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    public Integer getTotalCoefUENonValid(Long inspedSem, Long calendrier, BigDecimal moyenne_valid) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.getTotalCoefUENonValid(?,?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, calendrier);
             cls.setBigDecimal(3, moyenne_valid);
             cls.setLong(4, inspedSem);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void RepecherUe(Long calendrier, Long fil_ue,BigDecimal moyenne_valid, Long inspedUe, Long inspedSem, Long prcrs_maq, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.RepecherUe(?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, fil_ue);
            statement.setBigDecimal(3, moyenne_valid);
            statement.setLong(4, inspedUe);
            statement.setLong(5, inspedSem);
            statement.setLong(6, prcrs_maq);
            statement.setLong(7, utilisateur);
            statement.execute();
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
    /*
     * PROCEDURE RepecheMed( FILIERE_UE_COMPLEXE.ID_PARCOURS_MAQUET_ANNEE%TYPE,
           INSCRIPTION_PED_SEMESTRE.ID_INSCRIPTION_PED_SEMESTRE%TYPE,
          inspedUe INSCRIPTION_PED_SEM_UE.ID_INSCRIPTION_PED_SEM_UE%TYPE,
          calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE,
          fil_ue FILIERE_UE_COMPLEXE.ID_FILIERE_UE_SEMSTRE%TYPE,
          fil_ec FILIERE_UE_COMPLEXE.ID_FILIERE_UE_SEMSTRE%TYPE,
          utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)
     */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    // BigDecimal moyenne_valid,, Long prcrs_maq, Long utilisateur
    public void RepecherUeMed(Long parcours_maq, Long inspedSem, Long inspedUe, Long calendrier, Long fil_ue, Long  fil_ec, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.RepecheMed(?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, parcours_maq);
            statement.setLong(2, inspedSem);
            statement.setLong(3, inspedUe);
            statement.setLong(4, calendrier);
            statement.setLong(5, fil_ue);
            statement.setLong(6, fil_ec);
            statement.setLong(7, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DelibererSemestre(Long calendrier, Long parcours_maq ,Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DelibererSemestre(?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void Compenser(Long calendrier,  Long prcrs_maq ,Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.Compenser(?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, prcrs_maq);
            statement.setLong(3, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateResultatSemAdm(Long prcrs_maq,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.UpdateResultatSemAdm(?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq);
            statement.setLong(2, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    /*ChargerNote(calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE,
                            parcours NIVEAUX_FORMATION_PARCOURS.ID_NIVEAU_FORMATION_PARCOURS%TYPE,
                            id_annee in ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE,
                            codifUe FILIERE_UE_SEMSTRE.CODIFICATION%TYPE,
                            code_ec in EC.ANCIEN_CODE%TYPE, sess in SESSIONS.ID_SESSION%TYPE,
                            type_cntrle TYPE_CONTROLE.ID_TYPE_CONTROLE%TYPE,
                            utilisateur utilisateurs.id_utilisateur%TYPE)*/
    public void ChargerNoteAnc(Long calendrier, Long parcours,  String codifUe, Long code_ec, Long sess, Long type_cntrle,Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SyncNote.ChargerNote(?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours);
            //statement.setLong(3, id_annee);
            statement.setString(3, codifUe);
            statement.setLong(4, code_ec);
            statement.setLong(5, sess);
            statement.setLong(6, type_cntrle);
            statement.setLong(7, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void RepechSem(Long calendrier, BigDecimal moyenne_valid, Long inspedSem, Long prcrs_maq, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.RepecherSemestre(?,?,?,?,?);" + " END;"), 0);
        try { 
            statement.setLong(1, calendrier);
            statement.setBigDecimal(2, moyenne_valid);
            statement.setLong(3, inspedSem);
            statement.setLong(4, prcrs_maq);
            statement.setLong(5, utilisateur);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void CalculerMoyenneAnnuelle(Long annee,Long sess, Long parcours,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.CalculMoyenneAnnuelle(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, annee);
            statement.setLong(2, sess);
            statement.setLong(3, parcours);
            statement.setLong(4, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DelibererAnnee(Long annee,Long parcours_maq, Long formation, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DelibererAnnee(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, annee);
            statement.setLong(2, parcours_maq);
            statement.setLong(3, formation);
            statement.setLong(4, utilisateur);
            statement.execute();
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
        // getDBTransaction().clearEntityCache(null);
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateEnjembiste(Long annee, Long parcours, Long formation, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.UpdateResultAnEnjambiste(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, annee);
            statement.setLong(2, parcours);
            statement.setLong(3, formation);
            statement.setLong(4, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void createUserJury(Long idjury, Long id_user, String role_ ,Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.CreateOrUpdateUserJury(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, idjury);
            statement.setLong(2, id_user);
            statement.setString(3, role_);
            statement.setLong(4, utilisateur);
            statement.execute();
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
    
   public Long isMaquetteValide(Long parcours, Long anne, Long maquette) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  eval.isMaquetteValide(?,?,?);  end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, anne);
            cls.setLong(4, maquette);
            cls.registerOutParameter(1, Types.BIGINT);
            cls.executeUpdate();
            result = cls.getLong(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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
   
    public Integer getCompensationRule(Long cal_id) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.GetCompensationRule(?);  end ;";
         Integer result = 0;
         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, cal_id);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);

         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    //getNbreControle(fil_ec MODE_CONTROLE_EC.ID_FILIERE_UE_SEMSTRE_EC%TYPE, type_cntrl MODE_CONTROLE_EC.ID_TYPE_CONTROLE%TYPE)
    public Integer getNbreControle(Long fil_ec, Long type_cntrl, Long prcrs_maq) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.getNbreControle(?,?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, fil_ec);
             cls.setLong(3, type_cntrl);
             cls.setLong(4, prcrs_maq);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();

             result = cls.getInt(1);

         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    public Integer getInsc(Long pma) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.GetCountInsc(?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, pma);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);

         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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

    public Integer getNbreNivSup(Long niv_sec) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.GetNombreNivSup(?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_sec);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.executeUpdate();
            result = cls.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
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

    public Integer isEcAccessAllowed(Long filiere_ec_id, Long prcrs_maq, Long utilisateur) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.IsUserAccessAllowed(?,?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, filiere_ec_id);
             cls.setLong(3, prcrs_maq);
             cls.setLong(4, utilisateur);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    
    public Long CreateOrUpdateJury(Long niv_prcrs_id, Long sem_id, Long an_id, Long utilisateur) {
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.CreateOrUpdateJury(?,?,?,?); end ;";
        Long result = new Long (0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_prcrs_id);
            cls.setLong(3, sem_id);
            cls.setLong(4, an_id);
            cls.setLong(5, utilisateur);
            cls.registerOutParameter(1, Types.LONGVARCHAR);
            cls.executeUpdate();

            result = cls.getLong(1);

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
    
    public String GetNoteModeEnsInter(Long parcours, Long calendrier, Long  mde_cntrl_ec , Long inspedue ){
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.GetNoteModeEnsInter(?,?,?,?); end ;";
        String result = null;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, calendrier);
            cls.setLong(4, mde_cntrl_ec);
            cls.setLong(5, inspedue);
            cls.registerOutParameter(1, Types.VARCHAR);
            cls.executeUpdate();

            result = cls.getString(1);

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
    
    public Integer isSoutenableUeExist(Long calendrier, Long parcours) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.IsSoutenableUeExist(?,?);  end ;";
         Integer result = 0;
         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, calendrier);
             cls.setLong(3, parcours);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.executeUpdate();
             result = cls.getInt(1);

         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
    /*PROCEDURE ReconduireToSess2(
      calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE,
      parcours_maq NIVEAUX_FORMATION_PARCOURS.ID_NIVEAU_FORMATION_PARCOURS%TYPE,
      --annee PARCOURS_MAQUETTE_ANNEE.ID_ANNEES_UNIVERS%TYPE,
      --sess RESULTATS_ANNUELS.SESSION_DELIBERATION%TYPE,
      utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE 
    )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void ReconduireToSession2(Long calendrier, Long parcours_maq, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.ReconduireToSess2(?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, calendrier);
            statement.setLong(2, parcours_maq);
            //statement.setLong(3, annee);
            //statement.setLong(4, sess);
            statement.setLong(3, utilisateur);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void CloseSaisieCCSess2(Long parcours_maq,Long semestre,Long calsess2,Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.CloseSaisieCCSess2(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, parcours_maq);
            statement.setLong(2, semestre);
            statement.setLong(3, calsess2);
            statement.setLong(4, utilisateur);
            statement.execute();
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
     
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void RepecherSemestre(Long result_sem, Integer ref_moyenne,  Long calendrier,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.RepecherMoyenneSemestre(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, result_sem);
            statement.setInt(2, ref_moyenne);
            statement.setLong(3, calendrier);
            statement.setLong(4, utilisateur);
            statement.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void RepecherMention(Long result_sem, Integer ref_moyenne,  Long calendrier,  Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.RepecherMentionSemestre(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, result_sem);
            statement.setInt(2, ref_moyenne);
            statement.setLong(3, calendrier);
            statement.setLong(4, utilisateur);
            statement.execute();
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
    
    //2024
    public String getNoteEc(String p_num, Long p_fil_ue, Long p_calendrier, Long p_fec, Long p_prcrs_maq) {
         CallableStatement cls = null;
         ResultSet rs = null; 
         String sql = "begin ? :=  REFONTEPKG.getNoteEc(?, ?, ?, ?, ?);  end ;";
         String result = null;
         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setString(2, p_num);
            cls.setLong(3, p_fil_ue);
             cls.setLong(4, p_calendrier);
            cls.setLong(5, p_fec);
            cls.setLong(6, p_prcrs_maq);
             cls.registerOutParameter(1, Types.NUMERIC);
             cls.executeUpdate();
             result = cls.getString(1);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
             return null;
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
 
    public EudiantsUes getEtudiantUe(Long anId, Long semId, Long prcrsMaqId, Long sessId){
        //long startTime = System.currentTimeMillis();
        EudiantsUes etudiantUe= new EudiantsUes ();
        
        Set<Etudiant> listEtudiants = new HashSet<Etudiant>();
        Set<Ue> listUes = new HashSet<Ue>();
        Set<Ec> listEcs = new HashSet<Ec>();
        Set<NoteEc> listNotes = new HashSet<NoteEc>();
        
        Set<ResultatUe> listResultatUe = new HashSet<ResultatUe>();
        
        ArrayList<Etudiant> listEtd = new ArrayList<Etudiant>();
        ArrayList<Ue> listUe = new ArrayList<Ue>();
        
        ViewObject vo = getDelibSemData();
        vo.setNamedWhereClauseParam("annee_id", anId);
        vo.setNamedWhereClauseParam("sem_id", semId);
        vo.setNamedWhereClauseParam("prcrs_maq", prcrsMaqId);
        vo.setNamedWhereClauseParam("sess_id", sessId);
        vo.clearCache();
        vo.executeQuery();
        //System.out.println("nbre : "+vo.getEstimatedRowCount());
        while (vo.hasNext()) {
            Row nextRow = vo.next();
            Etudiant et = new Etudiant();
            et.setNumero(nextRow.getAttribute("Numero").toString());
            et.setPrenom(nextRow.getAttribute("Prenoms").toString());
            et.setNom(nextRow.getAttribute("Nom").toString());
            et.setDateNaissance(nextRow.getAttribute("DateNaissance").toString());
            et.setLieuNaissance(nextRow.getAttribute("LieuNaissance").toString());
            et.setNote(nextRow.getAttribute("Notesemestre") == null ? "-" : nextRow.getAttribute("Notesemestre").toString());
            et.setResultat(nextRow.getAttribute("Resultatsemestre") == null ? "-" : Integer.parseInt(nextRow.getAttribute("Resultatsemestre").toString()) == 2 ? "V" : (Integer.parseInt(nextRow.getAttribute("Resultatsemestre").toString()) == 3 ? "ADM" : "NV"));
            et.setCredit(nextRow.getAttribute("Creditsemestre") == null ? 0 : Integer.parseInt(nextRow.getAttribute("Creditsemestre").toString()));
            listEtudiants.add(et);
            
            Ue u = new Ue();
            u.setIdUe(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
            u.setLibelleUe(nextRow.getAttribute("LibUe").toString());
            u.setCodification(nextRow.getAttribute("Codification").toString());
            listUes.add(u);

            Ec e = new Ec();
            e.setIdEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            e.setIdFilUe(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
            e.setCoef(Double.parseDouble(nextRow.getAttribute("Coefficient").toString()));
            e.setLibelleEc(nextRow.getAttribute("LibEc").toString());
            listEcs.add(e);
            
            NoteEc nec = new NoteEc();
            nec.setIdNote(Long.parseLong(nextRow.getAttribute("IdNoteEc").toString()));
            nec.setIdFilEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            nec.setNumero(nextRow.getAttribute("Numero").toString());
            nec.setNote(nextRow.getAttribute("Note") == null ? "ABS" : nextRow.getAttribute("Note").toString());
            listNotes.add(nec);
            
            ResultatUe ru = new ResultatUe();
            ru.setIdResultatUe(Long.parseLong(nextRow.getAttribute("IdResultatFilUeSemestre").toString()));
            ru.setIdFiliereUe(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
            ru.setNumero(nextRow.getAttribute("Numero").toString());
            ru.setCredit(Integer.parseInt(nextRow.getAttribute("Creditue").toString()));
            ru.setResultat(Integer.parseInt(nextRow.getAttribute("Resultatue").toString()) == 2 ? "V" :
                           (Integer.parseInt(nextRow.getAttribute("Resultatue").toString()) == 3 ? "VC" : "NV"));
            ru.setNote(nextRow.getAttribute("Noteue") == null ? "-" : nextRow.getAttribute("Noteue").toString());
            listResultatUe.add(ru);

           }
        vo.closeRowSetIterator();
        for(Ec ec : listEcs){
            Map<String, String> listNotesEc = new HashMap<String, String>();
            Set<NoteEc> lNotesEc = listNotes.stream().filter(x -> x.getIdFilEc().equals(ec.getIdEc())).collect(Collectors.toSet());
            for(NoteEc note: lNotesEc){
                 listNotesEc.put(note.getNumero(), note.getNote());              
             }
            ec.setNotesEcEtudiant(listNotesEc);
        }
        
        for (Ue ue : listUes) {
            Map<String, ResultatUe> listResUe = new HashMap<String, ResultatUe>();
            Set<Ec> EcsOfUe = listEcs.stream()
                             .filter(x -> x.getIdFilUe().equals(ue.getIdUe()))
                             .collect(Collectors.toSet());
            ue.setListEc(EcsOfUe);
            
            Set<ResultatUe> resultOfUe = listResultatUe.stream()
                                                       .filter(x -> x.getIdFiliereUe().equals(ue.getIdUe()))
                                                       .collect(Collectors.toSet());
            for (ResultatUe rs : resultOfUe) {
                ResultatUe rUe = new ResultatUe();
                rUe.setIdResultatUe(rs.getIdResultatUe());
                rUe.setResultat(rs.getResultat());
                rUe.setCredit(rs.getCredit());
                rUe.setNote(rs.getNote());
                listResUe.put(rs.getNumero(), rUe);
            } 
            ue.setResulatsUe(listResUe);
        
        }
        listEtd = new ArrayList<Etudiant>(listEtudiants);
        Collections.sort(listEtd, new EtudiantComparator());
        listUe = new ArrayList<Ue>(listUes);
        Collections.sort(listUe, new UeComparator());
        etudiantUe.setEtudiants(listEtd); 
        etudiantUe.setListeUes(listUe);
        /*long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Temps d'exécution de la procédure etudiantUe : " + elapsedTime + " ms");
        */
        // Commit des modifications en cours
        // getDBTransaction().rollback();
        // Vider le cache des entités
         //getDBTransaction().clearEntityCache("vo");
        return etudiantUe;
    }
    
    public EtudiantsEcs getEtudiantEc(Long anId, Long semId, Long prcrsMaqId, Long sessId, Long filUeId){
        EtudiantsEcs etudiantEc = new EtudiantsEcs();
        Set<EtudiantUe> listEtudiants = new HashSet<EtudiantUe>();
        
        Set<Ec> listEcs = new HashSet<Ec>();
        Set<TypeControle> listTypeCntrle = new HashSet<TypeControle>();
        Set<NoteTpCntrole> listNotesTc = new HashSet<NoteTpCntrole>();
        Set<NoteEc> listNotesEc = new HashSet<NoteEc>();

        ArrayList<EtudiantUe> listEtd = new ArrayList<EtudiantUe>();
        ArrayList<Ec> listEc = new ArrayList<Ec>();

        ViewObject vo = getDeliberationUeData();
        vo.setNamedWhereClauseParam("annee_id", anId);
        vo.setNamedWhereClauseParam("sem_id", semId);
        vo.setNamedWhereClauseParam("prcrs_maq", prcrsMaqId);
        vo.setNamedWhereClauseParam("sess_id", sessId);
        vo.setNamedWhereClauseParam("id_fil_ue", filUeId);
        vo.clearCache();
        vo.executeQuery();
        //System.out.println("size  getDeliberationUeData : "+vo.getEstimatedRowCount());
        while (vo.hasNext()) {
            Row nextRow = vo.next();
            EtudiantUe et = new EtudiantUe();
            et.setNumero(nextRow.getAttribute("Numero").toString());
            et.setPrenom(nextRow.getAttribute("Prenoms").toString());
            et.setNom(nextRow.getAttribute("Nom").toString());
            et.setDateNaissance(nextRow.getAttribute("DateNaissance").toString());
            et.setLieuNaissance(nextRow.getAttribute("LieuNaissance").toString());
            et.setNote(nextRow.getAttribute("Noteue") == null ? "-" : nextRow.getAttribute("Noteue").toString());
            et.setResultat(nextRow.getAttribute("Resultatue") == null ? "-": Integer.parseInt(nextRow.getAttribute("Resultatue").toString()) == 2 ? "V" : (Integer.parseInt(nextRow.getAttribute("Resultatue").toString()) == 3 ? "VC" : "NV"));
            et.setCredit(nextRow.getAttribute("Creditue") == null ? 0 : Integer.parseInt(nextRow.getAttribute("Creditue").toString()));
            listEtudiants.add(et);

            Ec e = new Ec();
            e.setIdEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            e.setLibelleEc(nextRow.getAttribute("LibEc").toString());
            e.setCoef(Double.parseDouble(nextRow.getAttribute("Coefficient").toString()));
            e.setIdFilUe(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()));
            listEcs.add(e);
            
            NoteEc nc = new NoteEc();
            nc.setIdNote(Long.parseLong(nextRow.getAttribute("IdNoteEc").toString()));
            nc.setIdFilEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            nc.setNote(nextRow.getAttribute("Noteec") == null ? "-" : nextRow.getAttribute("Noteec").toString());
            nc.setNumero(nextRow.getAttribute("Numero").toString());
            listNotesEc.add(nc);
            
            TypeControle tc = new TypeControle();
            tc.setIdFiliereEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            tc.setIdTypeControle(Long.parseLong(nextRow.getAttribute("IdTypeControle").toString()));
            tc.setLibelle(nextRow.getAttribute("Modecntrl").toString());
            listTypeCntrle.add(tc);
            
            NoteTpCntrole nt = new NoteTpCntrole();
            nt.setIdNote(Long.parseLong(nextRow.getAttribute("IdNoteModeEnseignement").toString()));
            nt.setIdTypeCntrle(Long.parseLong(nextRow.getAttribute("IdTypeControle").toString()));
            nt.setNote(nextRow.getAttribute("Note") == null ? "ABS" : nextRow.getAttribute("Note").toString());
            nt.setNumero(nextRow.getAttribute("Numero").toString());
            nt.setIdFiliereEc(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()));
            listNotesTc.add(nt);
            
        }
        vo.closeRowSetIterator();

        for (TypeControle tc : listTypeCntrle) {
            Map<String, String> listTpCntrlEc = new HashMap<String, String>();
            Set<NoteTpCntrole> notesTc = listNotesTc.stream().filter(x -> (x.getIdFiliereEc().equals(tc.getIdFiliereEc()) && (x.getIdTypeCntrle().equals(tc.getIdTypeControle()))))
                                                             .collect(Collectors.toSet());
            for (NoteTpCntrole note : notesTc) {
                listTpCntrlEc.put(note.getNumero(), note.getNote());
            }
            tc.setNotesTpCntrlEtudiant(listTpCntrlEc);

        }
        
        for (Ec ec : listEcs) {
            Map<String, String> listNoteEc = new HashMap<String, String>();
            
            Set<TypeControle> typeCntrlrOfEc = listTypeCntrle.stream()
                                     .filter(x -> x.getIdFiliereEc().equals(ec.getIdEc()))
                                     .collect(Collectors.toSet());
            ec.setLisTypeCntrle(typeCntrlrOfEc);
            
            Set<NoteEc> noteEcEtudiant = listNotesEc.stream()
                                                       .filter(x -> x.getIdFilEc().equals(ec.getIdEc()))
                                                       .collect(Collectors.toSet());
            for (NoteEc nc : noteEcEtudiant) {
                listNoteEc.put(nc.getNumero(), nc.getNote());
            }
            ec.setNotesEcEtudiant(listNoteEc);
        }

        listEtd = new ArrayList<EtudiantUe>(listEtudiants);
        Collections.sort(listEtd, new EtudiantUeComparator());
        listEc = new ArrayList<Ec>(listEcs);
        etudiantEc.setEtudiants(listEtd);
        etudiantEc.setListeEcs(listEc);
        return etudiantEc;
    }

    public EtudiantsEcs getEtudiantUeEc(Long anId, Long userId, Long semId, Long pmaId, Long sessId) {
        EtudiantsEcs etdtEc = new EtudiantsEcs ();
        ViewObject vo = getDelibSemFilUeNew();
        vo.setNamedWhereClauseParam("user_id", userId);
        vo.setNamedWhereClauseParam("sem_id", semId);
        vo.setNamedWhereClauseParam("prcrs_maq_id", pmaId);
        vo.executeQuery();
        //System.out.println("size :"+vo.getEstimatedRowCount());
        if(0 != vo.getEstimatedRowCount()){
        Row ue = vo.first();
        if (!(null == ue)) {
            etdtEc = (EtudiantsEcs) getEtudiantEc(anId, semId, pmaId, sessId, Long.parseLong(ue.getAttribute("IdFiliereUeSemstre").toString()));
        } 
        }
        return etdtEc;
    }
    /*public ArrayList<Ue> getUeParcousAnSemSess(Long an_id, Long sem_id, Long prcrs_maq_id, Long sess_id) {
        //uelists = new HashSet<Ue>();

        Set<Ec> listEc = new HashSet<Ec>();
        ArrayList<Ue> listUe = new ArrayList<Ue>();
        ArrayList<Ec> listUeEc = new ArrayList<Ec>();
        ViewObject vo = getDelibData();
        vo.setNamedWhereClauseParam("an_id", an_id);
        vo.setNamedWhereClauseParam("sem_id", sem_id);
        vo.setNamedWhereClauseParam("pma_id", prcrs_maq_id);
        vo.setNamedWhereClauseParam("sess_id", sess_id);
        vo.executeQuery();
        while (vo.hasNext()) {
            Row nextRow = vo.next();
            listUe.add(new Ue(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()),
                               nextRow.getAttribute("LibelleLong").toString()));

            listUeEc.add(new Ec(Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstreEc").toString()),
                                Long.parseLong(nextRow.getAttribute("IdFiliereUeSemstre").toString()),
                                nextRow.getAttribute("LibEc").toString(),
                                Double.parseDouble(nextRow.getAttribute("Coefficient").toString())));

        }
        vo.closeRowSetIterator();
        Set<Ue> uelistsDistinct = listUe.stream().collect(Collectors.toSet());
        for (Ue ue : uelistsDistinct) {
            listEc = listUeEc.stream().filter(x->x.getId_fil_ue().equals(ue.getId_ue())).collect(Collectors.toSet());
            ue.setList_ec(listEc);
        }

        return new ArrayList<Ue>(uelis tsDistinct);
    }*/
    //ChargerResExamen(pma_id, code_niv_s, annee, sess)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void ChargerResultat(Long prcrs_maq_id, Long code_niv_sec, Long an_id,Long sess_id, String op_code_sup) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.ChargerResExamen(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);
            statement.setLong(2, code_niv_sec);
            statement.setLong(3, an_id);
            statement.setLong(4, sess_id);
            statement.setString(5, op_code_sup);
            statement.execute();
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
    //ChargerResExamenSpec(pma_id, code_niv_s, code_niv_sup, annee, sess)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void ChargerResultatSpec(Long prcrs_maq_id, Long code_niv_sec, Long code_niv_sec_sup, Long an_id, Long sess_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.ChargerResExamenSpec(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);
            statement.setLong(2, code_niv_sec);
            statement.setLong(3, code_niv_sec_sup);
            statement.setLong(4, an_id);
            statement.setLong(5, sess_id);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    //pma_id, :code_niv_s, :code_niv_sup, :annee, :sess, :p_numero, :op_code_sup
    public void ChargerResultatEtuOpt(Long prcrs_maq_id, Long code_niv_sec, Long code_niv_sec_sup, Long an_id, Long sess_id, String p_num, String op_code) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.ChargerResExamEtuOpt(?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);
            statement.setLong(2, code_niv_sec);
            statement.setLong(3, code_niv_sec_sup);
            statement.setLong(4, an_id);
            statement.setLong(5, sess_id);
            statement.setString(6, p_num);
            statement.setString(7, op_code);
            statement.execute();
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
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void ChargerResultatRdbt(Long prcrs_maq_id, Long code_niv_sec, Long an_id, Long sess_id, String op_code) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.ChargerResultatRdblt(?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);
            statement.setLong(2, code_niv_sec);
            statement.setLong(3, an_id);
            statement.setLong(4, sess_id);
            statement.setString(5, op_code);
            statement.execute();
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

    public Integer openDoc(Long prcrs_maq_id, Long sess_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.ouvrir_document(?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_maq_id);
            cls.setLong(3, sess_id);
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
        // getDBTransaction().clearEntityCache(null);
        return result;
    }
    
    /*INSERTION_DONNEES.getPrcrsMaquette(idparcours PARCOURS_MAQUETTE_ANNEE.ID_NIVEAU_FORMATION_PARCOURS%TYPE,
                              idmaquette PARCOURS_MAQUETTE_ANNEE.ID_MAQUETTE%TYPE,
                              idannee PARCOURS_MAQUETTE_ANNEE.ID_ANNEES_UNIVERS%TYPE)
                      RETURN PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE;*/
    public Integer getPrcrsMaquette(Long parcoursId, Long maquetteId, Long anneeId) {
        Integer code = 0;
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN ?:=  INSERTION_DONNEES.getPrcrsMaquette(?, ?, ?) ; END;"), 0);
        try {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setLong(2, parcoursId);
            statement.setLong(3, maquetteId);
            statement.setLong(4, anneeId);
            statement.execute();
            code = statement.getInt(1);
            
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
        return code;
    }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void clearData(Long prcrs_maq_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INSERTION_DONNEES.CLEAR_DATA(?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);;
            statement.execute();
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
    
    //INSERT_DATA(id_prcrs_maq PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void insertData(Long prcrs_maq_id, Integer uti_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INSERTION_DONNEES.INSERT_DATA(?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs_maq_id);
            statement.setInt(2, uti_id);
            statement.execute();
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
    
    /*INSERT_TEMPS (num TEMPSETUDIANTS1.NUMERO%TYPE, p_nom TEMPSETUDIANTS1.PRENOM%TYPE, nom TEMPSETUDIANTS1.NOM%TYPE, 
  d_naiss TEMPSETUDIANTS1.DATE_NAISSANCE%TYPE, l_naiss TEMPSETUDIANTS1.LIEU_NAISSANCE%TYPE, nation TEMPSETUDIANTS1.NATIONALITE%TYPE, civ TEMPSETUDIANTS1.CIVILITE%TYPE,
  sex TEMPSETUDIANTS1.SEXE%TYPE, tel TEMPSETUDIANTS1.TELEPHONE%TYPE, port TEMPSETUDIANTS1.PORTABLE%TYPE, e_inst TEMPSETUDIANTS1.EMAILUCAD%TYPE,
  e_pers TEMPSETUDIANTS1.EMAIL%TYPE, cin TEMPSETUDIANTS1.CIN%TYPE, pma TEMPSETUDIANTS1.ID_PARCOURS_MAQ%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void insertTemps(String num_, String pnom_, String nom_, String dnaiss_
                            , String lnaiss_, String nat_, String civ_, String sex_, String tel_
                            , String prt_, String einst_, String epers_, String cin_, Long pma_) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "INSERTION_DONNEES.INSERT_TEMPS(?,?,?,?,?,?,?,?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setString(1, num_);
            statement.setString(2, pnom_);
            statement.setString(3, nom_);
            statement.setString(4, dnaiss_);
            statement.setString(5, lnaiss_);
            statement.setString(6, nat_);
            statement.setString(7, civ_);
            statement.setString(8, sex_);
            statement.setString(9, tel_);
            statement.setString(10, prt_);
            statement.setString(11, einst_);
            statement.setString(12, epers_);
            statement.setString(13, cin_);
            statement.setLong(14, pma_);
            statement.execute();
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
    /*############################# 2025 ##############################*/
    /*SYNCHRONISATIONSCOL.CREATEORUPDATERECLAMATIONS(p_id_reclamation,p_NUM_ETUDIANT,p_prenoms,p_nom,p_email,p_etablissement,p_id_annee,
      p_lib_annee, p_code_niv_sec, p_lib_nivsec, p_code_semestre, p_lib_semestre,  p_code_session,  p_lib_session,  p_code_ue,
      p_lib_ue,  p_note_ue,  p_code_ec,  p_lib_ec,  p_note_ec, p_objet, p_motif_rejet, p_date_reclamation, p_date_traitement,
      p_etat) */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void insertReclamations(Integer pId, String pNUM, String pPnoms, String pNom, String pEmail, Integer pAnId, 
    String pAn, Integer pNivCode, String pNiv, Integer pSemCode, String pSem, Integer pSesCode,  
    String pSess,  Integer pUeCode, String pUe,  Integer pEcCode,  String pEc, String pObjet, String pDateRec, Integer pEtat,
    String pOpCode, String pOp, String pCodifUe, String pCodifEc) 
    {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.CREATEORUPDATERECLAMATIONS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setInt(1, pId);
            statement.setString(2, pNUM);
            statement.setString(3, pPnoms);
            statement.setString(4, pNom);
            statement.setString(5, pEmail);
            statement.setInt(6, pAnId);
            statement.setString(7, pAn);
            statement.setInt(8, pNivCode);
            statement.setString(9, pNiv);
            statement.setInt(10, pSemCode);
            statement.setString(11, pSem);
           statement.setInt(12, pSesCode);
            statement.setString(13, pSess);
            statement.setInt(14, pUeCode);
            statement.setString(15, pUe);
            statement.setInt(16, pEcCode);
            statement.setString(17, pEc);
            statement.setString(18, pObjet);
            statement.setString(19, pDateRec);
            statement.setInt(20, pEtat);
            statement.setString(21, pOpCode);
            statement.setString(22, pOp);
            statement.setString(23, pCodifUe);
            statement.setString(24, pCodifEc);
            statement.execute();
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
    
    public List<ReclamationNote> callApiWithUnnamedParams(String nivsec, String an, String sem) {
        //Set<ReclamationNote> students = new HashSet<>();
        List<ReclamationNote> notes = null;
        String username = "papembaye1.gaye@ucad.edu.sn";
        String password = "1";
        String baseUrl = "https://teststudentcenter.ucad.sn/api/admin/reclamations-notes/";
        try {
            // Construct the URL by appending parameters sequentially
            StringBuilder apiUrl = new StringBuilder(baseUrl);
            apiUrl.append(nivsec).append("/").append(an).append("/").append(sem);
            // Open connection
            URL url = new URL(apiUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Set HTTP method
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            // Basic Authentication header
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
            // Read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
            // Parse JSON into ReclamationResponse
            ObjectMapper objectMapper = new ObjectMapper();
            ReclamationResponse reclamationResponse = objectMapper.readValue(response.toString(), ReclamationResponse.class);

            // Get list of notes
            notes = reclamationResponse.getReclamationNotes();
            
            if (notes != null && !notes.isEmpty()) {
                System.out.println("Fetched note:");
                for (ReclamationNote note : notes) {
                    insertReclamations(note.getId(), note.getNumero_carte(), note.getPrenom(), note.getNom(), note.getEmail_ucad(), 
                        note.getCode_annee(), note.getAnnee(), note.getCode_niv_sec(), note.getNivSection(), note.getCode_semestre(), note.getSemestre(), note.getCode_session(),
                        note.getSession(),  note.getCode_ue(), note.getLibUe(), note.getCode_ec(),  note.getLibEc(), note.getObjet(), note.getDate_reclamation(), note.getEtat(),
                        note.getCode_option(), note.getLib_option(), note.getCodification_ue(), note.getCodification_ec());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
    
    public void sendEmail(String to, String subject, String body) {
            final String username = "noreply@ucad.edu.sn";
            final String password = "n$dx$n)P^@XfcWv";
            final String smtpHost = "smtp.office365.com"; // Remplacez par votre SMTP

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); // Changez si nécessaire

            //#For sending email
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "587");

            //# Other properties
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            //# TLS , port 587
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.office365.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Email envoyé avec succès !");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void saveDateSoutenance(Long etu_id, Long prcrs_maq_id, Long u_id, String d_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.CreateOrUpdateDateSoutenance(?, ?, ?, ?);" + " END;"), 0);
        try {
            statement.setLong(1, etu_id);
            statement.setLong(2, prcrs_maq_id);
            statement.setLong(3, u_id);
            statement.setString(4, d_id);
            statement.execute();
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
    
    /**
     * Container's getter for SaisieNoteEtudiant1.
     * @return SaisieNoteEtudiant1
     */
    public SaisieNoteEtudiantImpl getSaisieNoteEtudiant() {
        return (SaisieNoteEtudiantImpl) findViewObject("SaisieNoteEtudiant");
    }

    /**
     * Container's getter for NotesModeEnseignement1.
     * @return NotesModeEnseignement1
     */
    public NotesModeEnseignementImpl getNotesModeEnseignement1() {
        return (NotesModeEnseignementImpl) findViewObject("NotesModeEnseignement1");
    }

    /**
     * Container's getter for EtudiantSaisieNoteToNoteModeEnseignementVL1.
     * @return EtudiantSaisieNoteToNoteModeEnseignementVL1
     */
    public ViewLinkImpl getEtudiantSaisieNoteToNoteModeEnseignementVL1() {
        return (ViewLinkImpl) findViewLink("EtudiantSaisieNoteToNoteModeEnseignementVL1");
    }


    /**
     * Container's getter for EtudiantSaisieNoteInter1.
     * @return EtudiantSaisieNoteInter1
     */
    public EtudiantSaisieNoteInterImpl getEtudiantSaisieNoteInter1() {
        return (EtudiantSaisieNoteInterImpl) findViewObject("EtudiantSaisieNoteInter1");
    }

    /**
     * Container's getter for NoteModeEnsInterVO1.
     * @return NoteModeEnsInterVO1
     */
    public ViewObjectImpl getNoteModeEnsInterVO1() {
        return (ViewObjectImpl) findViewObject("NoteModeEnsInterVO1");
    }

    /**
     * Container's getter for EtudiantSaisieNoteInterToNoteModeEnseignementInterVL1.
     * @return EtudiantSaisieNoteInterToNoteModeEnseignementInterVL1
     */
    public ViewLinkImpl getEtudiantSaisieNoteInterToNoteModeEnseignementInterVL1() {
        return (ViewLinkImpl) findViewLink("EtudiantSaisieNoteInterToNoteModeEnseignementInterVL1");
    }

    /**
     * Container's getter for UtilisateurPDTJuryROVO1.
     * @return UtilisateurPDTJuryROVO1
     */
    public UtilisateurPDTJuryROVOImpl getUtilisateurPDTJury() {
        return (UtilisateurPDTJuryROVOImpl) findViewObject("UtilisateurPDTJury");
    }

    /**
     * Container's getter for IsUserRespNivFormROVO1.
     * @return IsUserRespNivFormROVO1
     */
    public IsUserRespNivFormROVOImpl getIsUserRespNivForm() {
        return (IsUserRespNivFormROVOImpl) findViewObject("IsUserRespNivForm");
    }

    /**
     * Container's getter for ParcoursInfoROVO1.
     * @return ParcoursInfoROVO1
     */
    public ParcoursInfoROVOImpl getParcoursInfo() {
        return (ParcoursInfoROVOImpl) findViewObject("ParcoursInfo");
    }


    /**
     * Container's getter for RepecheSemROVO1.
     * @return RepecheSemROVO1
     */
    public RepecheSemROVOImpl getRepecheSemestre() {
        return (RepecheSemROVOImpl) findViewObject("RepecheSemestre");
    }

    /**
     * Container's getter for RepecheFilUe1.
     * @return RepecheFilUe1
     */
    public RepecheFilUeImpl getRepecheFilUe() {
        return (RepecheFilUeImpl) findViewObject("RepecheFilUe");
    }

    /**
     * Container's getter for ResultatFilUERepechableROVO1.
     * @return ResultatFilUERepechableROVO1
     */
    public ResultatFilUERepechableROVOImpl getResultatFilUERepechable() {
        return (ResultatFilUERepechableROVOImpl) findViewObject("ResultatFilUERepechable");
    }

    /**
     * Container's getter for RepechableSemestreROVO1.
     * @return RepechableSemestreROVO1
     */
    public RepechableSemestreROVOImpl getRepechableSemestre() {
        return (RepechableSemestreROVOImpl) findViewObject("RepechableSemestre");
    }

    /**
     * Container's getter for FilereEcInvalideRepechROVO1.
     * @return FilereEcInvalideRepechROVO1
     */
    public FilereEcInvalideRepechROVOImpl getFilereEcInvalideRepech() {
        return (FilereEcInvalideRepechROVOImpl) findViewObject("FilereEcInvalideRepech");
    }

    /**
     * Container's getter for FiliereUEInvalidRepechROVO1.
     * @return FiliereUEInvalidRepechROVO1
     */
    public FiliereUEInvalidRepechROVOImpl getFiliereUEInvalidRepech() {
        return (FiliereUEInvalidRepechROVOImpl) findViewObject("FiliereUEInvalidRepech");
    }

    /**
     * Container's getter for ResponsableUePrcrsMaqAn1.
     * @return ResponsableUePrcrsMaqAn1
     */
    public ResponsableUePrcrsMaqAnImpl getResponsableUePrcrsMaqAn1() {
        return (ResponsableUePrcrsMaqAnImpl) findViewObject("ResponsableUePrcrsMaqAn1");
    }

    /**
     * Container's getter for FilUeInscDelibROVO1.
     * @return FilUeInscDelibROVO1
     */
    public FilUeInscDelibROVOImpl getFilUeInscDelib() {
        return (FilUeInscDelibROVOImpl) findViewObject("FilUeInscDelib");
    }

    /**
     * Container's getter for PrcrUserToFilUEInscDelibVL1.
     * @return PrcrUserToFilUEInscDelibVL1
     */
    public ViewLinkImpl getPrcrUserToFilUEInscDelibVL1() {
        return (ViewLinkImpl) findViewLink("PrcrUserToFilUEInscDelibVL1");
    }

    /**
     * Container's getter for StudentInscDelibUeROVO1.
     * @return StudentInscDelibUeROVO1
     */
    public ViewObjectImpl getStudentInscDelibUe() {
        return (ViewObjectImpl) findViewObject("StudentInscDelibUe");
    }

    /**
     * Container's getter for FilUEInscDelibToStudentInscFilUEVL1.
     * @return FilUEInscDelibToStudentInscFilUEVL1
     */
    public ViewLinkImpl getFilUEInscDelibToStudentInscFilUEVL1() {
        return (ViewLinkImpl) findViewLink("FilUEInscDelibToStudentInscFilUEVL1");
    }

    /**
     * Container's getter for ParcoursInscDelibROVO1.
     * @return ParcoursInscDelibROVO1
     */
    public ParcoursInscDelibROVOImpl getParcoursInscriptionDeliberation() {
        return (ParcoursInscDelibROVOImpl) findViewObject("ParcoursInscriptionDeliberation");
    }

    /**
     * Container's getter for StudentInsDelibROVO1.
     * @return StudentInsDelibROVO1
     */
    public ViewObjectImpl getStudentInsDeliberation() {
        return (ViewObjectImpl) findViewObject("StudentInsDeliberation");
    }

    /**
     * Container's getter for ParcoursInscToStudentDelibVL1.
     * @return ParcoursInscToStudentDelibVL1
     */
    public ViewLinkImpl getParcoursInscToStudentDelibVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursInscToStudentDelibVL1");
    }

    /**
     * Container's getter for ListFilUeDeliSemROVO1.
     * @return ListFilUeDeliSemROVO1
     */
    public ListFilUeDeliSemROVOImpl getListFilUeDeliSem() {
        return (ListFilUeDeliSemROVOImpl) findViewObject("ListFilUeDeliSem");
    }

    /**
     * Container's getter for FiliereUeSemEcByRespROVO1.
     * @return FiliereUeSemEcByRespROVO1
     */
    public FiliereUeSemEcByRespROVOImpl getFiliereUeSemEcByRespROVO() {
        return (FiliereUeSemEcByRespROVOImpl) findViewObject("FiliereUeSemEcByRespROVO");
    }

    /**
     * Container's getter for MaquetteParcoursAnneeROVO1.
     * @return MaquetteParcoursAnneeROVO1
     */
    public MaquetteParcoursAnneeROVOImpl getMaquetteParcoursAnnee() {
        return (MaquetteParcoursAnneeROVOImpl) findViewObject("MaquetteParcoursAnnee");
    }

    /**
     * Container's getter for ParcoursUserToMaquetteParcoursVL1.
     * @return ParcoursUserToMaquetteParcoursVL1
     */
    public ViewLinkImpl getParcoursUserToMaquetteParcoursVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursUserToMaquetteParcoursVL1");
    }


    /**
     * Container's getter for PrcrsMaquetteAnROVO1.
     * @return PrcrsMaquetteAnROVO1
     */
    public PrcrsMaquetteAnROVOImpl getPrcrsMaquette() {
        return (PrcrsMaquetteAnROVOImpl) findViewObject("PrcrsMaquette");
    }

    /**
     * Container's getter for PrcrsParamSaisieEcToPrcrsMaqAnVL1.
     * @return PrcrsParamSaisieEcToPrcrsMaqAnVL1
     */
    public ViewLinkImpl getPrcrsParamSaisieEcToPrcrsMaqAnVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsParamSaisieEcToPrcrsMaqAnVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreROVO1.
     * @return FiliereUeSemestreROVO1
     */
    public FiliereUeSemestreROVOImpl getFiliereUeSemestres() {
        return (FiliereUeSemestreROVOImpl) findViewObject("FiliereUeSemestres");
    }

    /**
     * Container's getter for PrcrsMaqAnToFilUESemVL1.
     * @return PrcrsMaqAnToFilUESemVL1
     */
    public ViewLinkImpl getPrcrsMaqAnToFilUESemVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsMaqAnToFilUESemVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreEcROVO1.
     * @return FiliereUeSemestreEcROVO1
     */
    public ViewObjectImpl getFiliereUeSemestreEcs() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemestreEcs");
    }

    /**
     * Container's getter for FiliereUeSemestreToFiliereUeSemestreEc1.
     * @return FiliereUeSemestreToFiliereUeSemestreEc1
     */
    public ViewLinkImpl getFiliereUeSemestreToFiliereUeSemestreEc1() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemestreToFiliereUeSemestreEc1");
    }

    /**
     * Container's getter for ModeControleEcVO1.
     * @return ModeControleEcVO1
     */
    public ViewObjectImpl getModeControleEcs() {
        return (ViewObjectImpl) findViewObject("ModeControleEcs");
    }

    /**
     * Container's getter for FiliereUeSemestreEcToModeControleEc1.
     * @return FiliereUeSemestreEcToModeControleEc1
     */
    public ViewLinkImpl getFiliereUeSemestreEcToModeControleEc1() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemestreEcToModeControleEc1");
    }

    /**
     * Container's getter for FiliereUeSemEcByRespROVO1.
     * @return FiliereUeSemEcByRespROVO1
     */
    public FiliereUeSemEcByRespROVOImpl getFiliereUeSemEcByResp() {
        return (FiliereUeSemEcByRespROVOImpl) findViewObject("FiliereUeSemEcByResp");
    }

    /**
     * Container's getter for PrcrsMaqAnToFilUeSemByRespVL1.
     * @return PrcrsMaqAnToFilUeSemByRespVL1
     */
    public ViewLinkImpl getPrcrsMaqAnToFilUeSemByRespVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsMaqAnToFilUeSemByRespVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreEcROVO1.
     * @return FiliereUeSemestreEcROVO1
     */
    public ViewObjectImpl getFiliereUeSemestreEcPrcrs() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemestreEcPrcrs");
    }

    /**
     * Container's getter for PrcrsToFilUeSemEcVL1.
     * @return PrcrsToFilUeSemEcVL1
     */
    public ViewLinkImpl getPrcrsToFilUeSemEcVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsToFilUeSemEcVL1");
    }

    /**
     * Container's getter for UserAccesEcROVO1.
     * @return UserAccesEcROVO1
     */
    public UserAccesEcROVOImpl getUserAccesEc() {
        return (UserAccesEcROVOImpl) findViewObject("UserAccesEc");
    }

    /**
     * Container's getter for PrcrsMaqAnToUserAccessEcVL1.
     * @return PrcrsMaqAnToUserAccessEcVL1
     */
    public ViewLinkImpl getPrcrsMaqAnToUserAccessEcVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsMaqAnToUserAccessEcVL1");
    }

    /**
     * Container's getter for PvAnnuelROVO1.
     * @return PvAnnuelROVO1
     */
    public PvAnnuelROVOImpl getPvAnnuel() {
        return (PvAnnuelROVOImpl) findViewObject("PvAnnuel");
    }

    /**
     * Container's getter for MaquetteParcoursAnneeROVO1.
     * @return MaquetteParcoursAnneeROVO1
     */
    public MaquetteParcoursAnneeROVOImpl getMaquetteParcoursAn() {
        return (MaquetteParcoursAnneeROVOImpl) findViewObject("MaquetteParcoursAn");
    }

    /**
     * Container's getter for ParcoursToMaquettePrcrsAnneeVL1.
     * @return ParcoursToMaquettePrcrsAnneeVL1
     */
    public ViewLinkImpl getParcoursToMaquettePrcrsAnneeVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursToMaquettePrcrsAnneeVL1");
    }

    /**
     * Container's getter for DepartementByUserROVO1.
     * @return DepartementByUserROVO1
     */
    public DepartementByUserROVOImpl getDepartementByUser() {
        return (DepartementByUserROVOImpl) findViewObject("DepartementByUser");
    }

    /**
     * Container's getter for ParcoursSaisieModeCntrlROVO1.
     * @return ParcoursSaisieModeCntrlROVO1
     */
    public ParcoursSaisieModeCntrlROVOImpl getParcoursSaisieModeCntrl() {
        return (ParcoursSaisieModeCntrlROVOImpl) findViewObject("ParcoursSaisieModeCntrl");
    }

    /**
     * Container's getter for DeptByUserToPrcrsModeCntrlVl1.
     * @return DeptByUserToPrcrsModeCntrlVl1
     */
    public ViewLinkImpl getDeptByUserToPrcrsModeCntrlVl1() {
        return (ViewLinkImpl) findViewLink("DeptByUserToPrcrsModeCntrlVl1");
    }

    /**
     * Container's getter for PrcrsMaquetteAnROVO1.
     * @return PrcrsMaquetteAnROVO1
     */
    public PrcrsMaquetteAnROVOImpl getPrcrsMaquetteAn() {
        return (PrcrsMaquetteAnROVOImpl) findViewObject("PrcrsMaquetteAn");
    }

    /**
     * Container's getter for PrcrsModeCntrlToPrcrsMaqAnVL1.
     * @return PrcrsModeCntrlToPrcrsMaqAnVL1
     */
    public ViewLinkImpl getPrcrsModeCntrlToPrcrsMaqAnVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsModeCntrlToPrcrsMaqAnVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreEcROVO1.
     * @return FiliereUeSemestreEcROVO1
     */
    public ViewObjectImpl getFiliereUeSemestreEc() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemestreEc");
    }

    /**
     * Container's getter for PrcrsMaqAnToFilEcSemVL1.
     * @return PrcrsMaqAnToFilEcSemVL1
     */
    public ViewLinkImpl getPrcrsMaqAnToFilEcSemVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsMaqAnToFilEcSemVL1");
    }

    /**
     * Container's getter for ModeControleEcVO1.
     * @return ModeControleEcVO1
     */
    public ViewObjectImpl getModeControleEcVO1() {
        return (ViewObjectImpl) findViewObject("ModeControleEcVO1");
    }

    /**
     * Container's getter for FiliereUeSemestreEcToModeControleEc3.
     * @return FiliereUeSemestreEcToModeControleEc3
     */
    public ViewLinkImpl getFiliereUeSemestreEcToModeControleEc3() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemestreEcToModeControleEc3");
    }


    /**
     * Container's getter for FilEcROVO1.
     * @return FilEcROVO1
     */
    public FilEcROVOImpl getFilEc() {
        return (FilEcROVOImpl) findViewObject("FilEc");
    }

    /**
     * Container's getter for RepecheFilUeToFilEc1.
     * @return RepecheFilUeToFilEc1
     */
    public ViewLinkImpl getRepecheFilUeToFilEc1() {
        return (ViewLinkImpl) findViewLink("RepecheFilUeToFilEc1");
    }

    /**
     * Container's getter for StructureAttestationROVO1.
     * @return StructureAttestationROVO1
     */
    public StructureAttestationROVOImpl getStructureAttestation() {
        return (StructureAttestationROVOImpl) findViewObject("StructureAttestation");
    }

    /**
     * Container's getter for StructureRegleRepecheROVO1.
     * @return StructureRegleRepecheROVO1
     */
    public StructureRegleRepecheROVOImpl getStructureRegleRepeche() {
        return (StructureRegleRepecheROVOImpl) findViewObject("StructureRegleRepeche");
    }

    /**
     * Container's getter for ResultFilUeRepechableMedROVO1.
     * @return ResultFilUeRepechableMedROVO1
     */
    public ResultFilUeRepechableMedROVOImpl getResultFilUeRepechableMed() {
        return (ResultFilUeRepechableMedROVOImpl) findViewObject("ResultFilUeRepechableMed");
    }

    /**
     * Container's getter for ParcoursDelibCycle1.
     * @return ParcoursDelibCycle1
     */
    public ParcoursDelibCycleImpl getParcoursDelibCycle() {
        return (ParcoursDelibCycleImpl) findViewObject("ParcoursDelibCycle");
    }

    /**
     * Container's getter for ResultatCycleROVO1.
     * @return ResultatCycleROVO1
     */
    public ResultatCycleROVOImpl getResultatCycle() {
        return (ResultatCycleROVOImpl) findViewObject("ResultatCycle");
    }

    /**
     * Container's getter for PrcrsDelibCycleToResultCycleVL1.
     * @return PrcrsDelibCycleToResultCycleVL1
     */
    public ViewLinkImpl getPrcrsDelibCycleToResultCycleVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsDelibCycleToResultCycleVL1");
    }

    /**
     * Container's getter for DelibAnPvROVO1.
     * @return DelibAnPvROVO1
     */
    public DelibAnPvROVOImpl getDelibAnPv() {
        return (DelibAnPvROVOImpl) findViewObject("DelibAnPv");
    }

    /**
     * Container's getter for ParcoursAttSpecialeROVO1.
     * @return ParcoursAttSpecialeROVO1
     */
    public ParcoursAttSpecialeROVOImpl getParcoursAttSpeciale() {
        return (ParcoursAttSpecialeROVOImpl) findViewObject("ParcoursAttSpeciale");
    }


    /**
     * Container's getter for EtudiantSaisieMemoireROVO1.
     * @return EtudiantSaisieMemoireROVO1
     */
    public EtudiantSaisieMemoireROVOImpl getEtudiantSaisieMemoireROVO1() {
        return (EtudiantSaisieMemoireROVOImpl) findViewObject("EtudiantSaisieMemoireROVO1");
    }

    /**
     * Container's getter for grhUserROVO1.
     * @return grhUserROVO1
     */
    public grhUserROVOImpl getgrhUsers() {
        return (grhUserROVOImpl) findViewObject("grhUsers");
    }

    /**
     * Container's getter for UeEvalNewROVO1.
     * @return UeEvalNewROVO1
     */
    public UeEvalNewROVOImpl getUeEvalNew() {
        return (UeEvalNewROVOImpl) findViewObject("UeEvalNew");
    }

    /**
     * Container's getter for EcEvalNewROVO1.
     * @return EcEvalNewROVO1
     */
    public EcEvalNewROVOImpl getEcEvalNew() {
        return (EcEvalNewROVOImpl) findViewObject("EcEvalNew");
    }

    /**
     * Container's getter for UeEvalNewToEcEvalNewVL1.
     * @return UeEvalNewToEcEvalNewVL1
     */
    public ViewLinkImpl getUeEvalNewToEcEvalNewVL1() {
        return (ViewLinkImpl) findViewLink("UeEvalNewToEcEvalNewVL1");
    }

    /**
     * Container's getter for EcEvalTypeCntrlNewROVO1.
     * @return EcEvalTypeCntrlNewROVO1
     */
    public EcEvalTypeCntrlNewROVOImpl getEcEvalTypeCntrlNew() {
        return (EcEvalTypeCntrlNewROVOImpl) findViewObject("EcEvalTypeCntrlNew");
    }

    /**
     * Container's getter for EcEvalNewToTypeCntrlEcEvalNewVL1.
     * @return EcEvalNewToTypeCntrlEcEvalNewVL1
     */
    public ViewLinkImpl getEcEvalNewToTypeCntrlEcEvalNewVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalNewToTypeCntrlEcEvalNewVL1");
    }

    /**
     * Container's getter for EcEvalTypeCntrModeCntrllNewROVO1.
     * @return EcEvalTypeCntrModeCntrllNewROVO1
     */
    public EcEvalTypeCntrModeCntrllNewROVOImpl getEcEvalTypeCntrModeCntrllNew() {
        return (EcEvalTypeCntrModeCntrllNewROVOImpl) findViewObject("EcEvalTypeCntrModeCntrllNew");
    }

    /**
     * Container's getter for EcEvalTypeCtrlNewToModeCtrlNewROVOVL1.
     * @return EcEvalTypeCtrlNewToModeCtrlNewROVOVL1
     */
    public ViewLinkImpl getEcEvalTypeCtrlNewToModeCtrlNewROVOVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalTypeCtrlNewToModeCtrlNewROVOVL1");
    }


    /**
     * Container's getter for NotesModeEnseignementInterNewVO1.
     * @return NotesModeEnseignementInterNewVO1
     */
    public NotesModeEnseignementInterNewVOImpl getNotesModeEnseignementInterNew() {
        return (NotesModeEnseignementInterNewVOImpl) findViewObject("NotesModeEnseignementInterNew");
    }

    /**
     * Container's getter for EcTpeCntrlMdeCntrlToNoteInterVL1.
     * @return EcTpeCntrlMdeCntrlToNoteInterVL1
     */
    public ViewLinkImpl getEcTpeCntrlMdeCntrlToNoteInterVL1() {
        return (ViewLinkImpl) findViewLink("EcTpeCntrlMdeCntrlToNoteInterVL1");
    }


    /**
     * Container's getter for EtudiantEtatInscrROVO1.
     * @return EtudiantEtatInscrROVO1
     */
    public EtudiantEtatInscrROVOImpl getEtudiantEtatInscr() {
        return (EtudiantEtatInscrROVOImpl) findViewObject("EtudiantEtatInscr");
    }

    /**
     * Container's getter for EtudiantEtaInscrEnjambisteROVO1.
     * @return EtudiantEtaInscrEnjambisteROVO1
     */
    public EtudiantEtaInscrEnjambisteROVOImpl getEtudiantEtaInscrEnjambiste() {
        return (EtudiantEtaInscrEnjambisteROVOImpl) findViewObject("EtudiantEtaInscrEnjambiste");
    }


    /**
     * Container's getter for UeNoteClosedDelibSemROVO1.
     * @return UeNoteClosedDelibSemROVO1
     */
    public UeNoteClosedDelibSemROVOImpl getUeNoteClosedDelibSem() {
        return (UeNoteClosedDelibSemROVOImpl) findViewObject("UeNoteClosedDelibSem");
    }

    /**
     * Container's getter for EcNotClosedDelibUeROVO1.
     * @return EcNotClosedDelibUeROVO1
     */
    public EcNotClosedDelibUeROVOImpl getEcNotClosedDelibUe() {
        return (EcNotClosedDelibUeROVOImpl) findViewObject("EcNotClosedDelibUe");
    }

    /**
     * Container's getter for UeEvalExportListEtdROVO1.
     * @return UeEvalExportListEtdROVO1
     */
    public UeEvalExportListEtdROVOImpl getUeEvalExportListEtd() {
        return (UeEvalExportListEtdROVOImpl) findViewObject("UeEvalExportListEtd");
    }

    /**
     * Container's getter for EcEvalExportEtdROVO1.
     * @return EcEvalExportEtdROVO1
     */
    public EcEvalExportEtdROVOImpl getEcEvalExportEtd() {
        return (EcEvalExportEtdROVOImpl) findViewObject("EcEvalExportEtd");
    }

    /**
     * Container's getter for UeEvalExportToEcEvalExportVL1.
     * @return UeEvalExportToEcEvalExportVL1
     */
    public ViewLinkImpl getUeEvalExportToEcEvalExportVL1() {
        return (ViewLinkImpl) findViewLink("UeEvalExportToEcEvalExportVL1");
    }


    /**
     * Container's getter for EtudiantExportListROVO1.
     * @return EtudiantExportListROVO1
     */
    public EtudiantExportListROVOImpl getEtudiantExportList() {
        return (EtudiantExportListROVOImpl) findViewObject("EtudiantExportList");
    }

    /**
     * Container's getter for EcEvalExportToEtdEvalExportVL1.
     * @return EcEvalExportToEtdEvalExportVL1
     */
    public ViewLinkImpl getEcEvalExportToEtdEvalExportVL1() {
        return (ViewLinkImpl) findViewLink("EcEvalExportToEtdEvalExportVL1");
    }

    /**
     * Container's getter for DelibUeFilEcTypeCntrlROVO1.
     * @return DelibUeFilEcTypeCntrlROVO1
     */
    public DelibUeFilEcTypeCntrlROVOImpl getDelibUeFilEcTypeCntrl() {
        return (DelibUeFilEcTypeCntrlROVOImpl) findViewObject("DelibUeFilEcTypeCntrl");
    }

    /**
     * Container's getter for DelibUeNoteTypeCntrleROVO1.
     * @return DelibUeNoteTypeCntrleROVO1
     */
    public DelibUeNoteTypeCntrleROVOImpl getDelibUeNoteTypeCntrle() {
        return (DelibUeNoteTypeCntrleROVOImpl) findViewObject("DelibUeNoteTypeCntrle");
    }

    /**
     * Container's getter for JuryMembrePresentROVO1.
     * @return JuryMembrePresentROVO1
     */
    public JuryMembrePresentROVOImpl getJuryMembrePresent() {
        return (JuryMembrePresentROVOImpl) findViewObject("JuryMembrePresent");
    }

    /**
     * Container's getter for ParcoursSaisieMemoireROVO1.
     * @return ParcoursSaisieMemoireROVO1
     */
    public ParcoursSaisieMemoireROVOImpl getParcoursSaisieMemoire() {
        return (ParcoursSaisieMemoireROVOImpl) findViewObject("ParcoursSaisieMemoire");
    }

    /**
     * Container's getter for DeptEtabToPrcrsSaisieMemoireVL1.
     * @return DeptEtabToPrcrsSaisieMemoireVL1
     */
    public ViewLinkImpl getDeptEtabToPrcrsSaisieMemoireVL1() {
        return (ViewLinkImpl) findViewLink("DeptEtabToPrcrsSaisieMemoireVL1");
    }

    /**
     * Container's getter for MemoireEtudiantsVO1.
     * @return MemoireEtudiantsVO1
     */
    public ViewObjectImpl getMemoireEtudiants() {
        return (ViewObjectImpl) findViewObject("MemoireEtudiants");
    }

    /**
     * Container's getter for ParcoursAttSpecMemoireEtdVL1.
     * @return ParcoursAttSpecMemoireEtdVL1
     */
    public ViewLinkImpl getParcoursAttSpecMemoireEtdVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursAttSpecMemoireEtdVL1");
    }


    /**
     * Container's getter for EtudiantMemoireROVO1.
     * @return EtudiantMemoireROVO1
     */
    public EtudiantMemoireROVOImpl getEtudiantMemoire() {
        return (EtudiantMemoireROVOImpl) findViewObject("EtudiantMemoire");
    }

    /**
     * Container's getter for ParcoursSaisieMemoireToEtdMemoireVL1.
     * @return ParcoursSaisieMemoireToEtdMemoireVL1
     */
    public ViewLinkImpl getParcoursSaisieMemoireToEtdMemoireVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursSaisieMemoireToEtdMemoireVL1");
    }

    /**
     * Container's getter for EtudiantSaisieNoteInterNewROVO1.
     * @return EtudiantSaisieNoteInterNewROVO1
     */
    public EtudiantSaisieNoteInterNewROVOImpl getEtudiantSaisieNoteInterNewROVO() {
        return (EtudiantSaisieNoteInterNewROVOImpl) findViewObject("EtudiantSaisieNoteInterNewROVO");
    }

    /**
     * Container's getter for EtudiantSaisieInterNewToNoteMdeEnsInterVL1.
     * @return EtudiantSaisieInterNewToNoteMdeEnsInterVL1
     */
    public ViewLinkImpl getEtudiantSaisieInterNewToNoteMdeEnsInterVL1() {
        return (ViewLinkImpl) findViewLink("EtudiantSaisieInterNewToNoteMdeEnsInterVL1");
    }

    /**
     * Container's getter for DelibSemFilUeNewROVO1.
     * @return DelibSemFilUeNewROVO1
     */
    public DelibSemFilUeNewROVOImpl getDelibSemFilUeNew() {
        return (DelibSemFilUeNewROVOImpl) findViewObject("DelibSemFilUeNew");
    }

    /**
     * Container's getter for DelibDataROVO1.
     * @return DelibDataROVO1
     */
    public DelibDataROVOImpl getDelibData() {
        return (DelibDataROVOImpl) findViewObject("DelibData");
    }

    /**
     * Container's getter for DelibSemDataROVO1.
     * @return DelibSemDataROVO1
     */
    public DelibSemDataROVOImpl getDelibSemData() {
        return (DelibSemDataROVOImpl) findViewObject("DelibSemData");
    }

    /**
     * Container's getter for DeliberationUeDataROVO1.
     * @return DeliberationUeDataROVO1
     */
    public DeliberationUeDataROVOImpl getDeliberationUeData() {
        return (DeliberationUeDataROVOImpl) findViewObject("DeliberationUeData");
    }

    /**
     * Container's getter for DelibSemDataDetailNoteROVO1.
     * @return DelibSemDataDetailNoteROVO1
     */
    public DelibSemDataDetailNoteROVOImpl getDelibSemDataDetailNote() {
        return (DelibSemDataDetailNoteROVOImpl) findViewObject("DelibSemDataDetailNote");
    }

    /**
     * Container's getter for FiliereUeRenonciationROVO1.
     * @return FiliereUeRenonciationROVO1
     */
    public FiliereUeRenonciationROVOImpl getFiliereUeRenonciation() {
        return (FiliereUeRenonciationROVOImpl) findViewObject("FiliereUeRenonciation");
    }

    /**
     * Container's getter for FiliereEcRenonciationNoteROVO1.
     * @return FiliereEcRenonciationNoteROVO1
     */
    public FiliereEcRenonciationNoteROVOImpl getFiliereEcRenonciationNote() {
        return (FiliereEcRenonciationNoteROVOImpl) findViewObject("FiliereEcRenonciationNote");
    }

    /**
     * Container's getter for FilUEtoFilECRenonciationNoteVL1.
     * @return FilUEtoFilECRenonciationNoteVL1
     */
    public ViewLinkImpl getFilUEtoFilECRenonciationNoteVL1() {
        return (ViewLinkImpl) findViewLink("FilUEtoFilECRenonciationNoteVL1");
    }

    /**
     * Container's getter for EtudiantsRenonciationNote1.
     * @return EtudiantsRenonciationNote1
     */
    public EtudiantsRenonciationNoteImpl getEtudiantsRenonciationNote() {
        return (EtudiantsRenonciationNoteImpl) findViewObject("EtudiantsRenonciationNote");
    }

    /**
     * Container's getter for FilEcNewToEtdRenoncNoteVL2.
     * @return FilEcNewToEtdRenoncNoteVL2
     */
    public ViewLinkImpl getFilEcNewToEtdRenoncNoteVL2() {
        return (ViewLinkImpl) findViewLink("FilEcNewToEtdRenoncNoteVL2");
    }

    /**
     * Container's getter for NiveauxSectionsSupROVO1.
     * @return NiveauxSectionsSupROVO1
     */
    public NiveauxSectionsSupROVOImpl getNiveauxSectionsSup() {
        return (NiveauxSectionsSupROVOImpl) findViewObject("NiveauxSectionsSup");
    }

    /**
     * Container's getter for PvAnnuelSess2ROVO1.
     * @return PvAnnuelSess2ROVO1
     */
    public PvAnnuelSess2ROVOImpl getPvAnnuelSess2() {
        return (PvAnnuelSess2ROVOImpl) findViewObject("PvAnnuelSess2");
    }

    /**
     * Container's getter for SemestresByParcousMaqROVO1.
     * @return SemestresByParcousMaqROVO1
     */
    public SemestresByParcousMaqROVOImpl getSemestresByParcousMaq() {
        return (SemestresByParcousMaqROVOImpl) findViewObject("SemestresByParcousMaq");
    }

    /**
     * Container's getter for MaqPrcrsToSemestreVL1.
     * @return MaqPrcrsToSemestreVL1
     */
    public ViewLinkImpl getMaqPrcrsToSemestreVL1() {
        return (ViewLinkImpl) findViewLink("MaqPrcrsToSemestreVL1");
    }

    /**
     * Container's getter for DepartementsStructure1.
     * @return DepartementsStructure1
     */
    public DepartementsStructureImpl getDepartementsStructure() {
        return (DepartementsStructureImpl) findViewObject("DepartementsStructure");
    }

    /**
     * Container's getter for StatistiquesDepartementROVO1.
     * @return StatistiquesDepartementROVO1
     */
    public StatistiquesDepartementROVOImpl getStatistiquesDepartement() {
        return (StatistiquesDepartementROVOImpl) findViewObject("StatistiquesDepartement");
    }

    /**
     * Container's getter for DeptStructureToStatDeptVL1.
     * @return DeptStructureToStatDeptVL1
     */
    public ViewLinkImpl getDeptStructureToStatDeptVL1() {
        return (ViewLinkImpl) findViewLink("DeptStructureToStatDeptVL1");
    }

    /**
     * Container's getter for StatistiquesNiveauxFormationROVO1.
     * @return StatistiquesNiveauxFormationROVO1
     */
    public StatistiquesNiveauxFormationROVOImpl getStatistiquesNiveauxFormation() {
        return (StatistiquesNiveauxFormationROVOImpl) findViewObject("StatistiquesNiveauxFormation");
    }

    /**
     * Container's getter for StatDeptToStatNivFormVL1.
     * @return StatDeptToStatNivFormVL1
     */
    public ViewLinkImpl getStatDeptToStatNivFormVL1() {
        return (ViewLinkImpl) findViewLink("StatDeptToStatNivFormVL1");
    }

    /**
     * Container's getter for InscriptionsEtudiantROVO1.
     * @return InscriptionsEtudiantROVO1
     */
    public InscriptionsEtudiantROVOImpl getInscriptionsEtudiant() {
        return (InscriptionsEtudiantROVOImpl) findViewObject("InscriptionsEtudiant");
    }

    /**
     * Container's getter for ResultatSemestreEtudiantROVO1.
     * @return ResultatSemestreEtudiantROVO1
     */
    public ResultatSemestreEtudiantROVOImpl getResultatSemestreEtudiant() {
        return (ResultatSemestreEtudiantROVOImpl) findViewObject("ResultatSemestreEtudiant");
    }

    /**
     * Container's getter for InscEtudiantToResSemEtdVL1.
     * @return InscEtudiantToResSemEtdVL1
     */
    public ViewLinkImpl getInscEtudiantToResSemEtdVL1() {
        return (ViewLinkImpl) findViewLink("InscEtudiantToResSemEtdVL1");
    }


    /**
     * Container's getter for NotesModeEnseignementInterNewVO1.
     * @return NotesModeEnseignementInterNewVO1
     */
    public NotesModeEnseignementInterNewVOImpl getNotesModeEnseignementInter() {
        return (NotesModeEnseignementInterNewVOImpl) findViewObject("NotesModeEnseignementInter");
    }

    /**
     * Container's getter for DetailNotesROVO1.
     * @return DetailNotesROVO1
     */
    public DetailNotesROVOImpl getDetailNotes() {
        return (DetailNotesROVOImpl) findViewObject("DetailNotes");
    }

    /**
     * Container's getter for DetailNoteToNOteInterNewVL1.
     * @return DetailNoteToNOteInterNewVL1
     */
    public ViewLinkImpl getDetailNoteToNOteInterNewVL1() {
        return (ViewLinkImpl) findViewLink("DetailNoteToNOteInterNewVL1");
    }

    /**
     * Container's getter for DetailNotesROVO1.
     * @return DetailNotesROVO1
     */
    public DetailNotesROVOImpl getDetailNote() {
        return (DetailNotesROVOImpl) findViewObject("DetailNote");
    }

    /**
     * Container's getter for NotesModeEnseignementInterNewVO1.
     * @return NotesModeEnseignementInterNewVO1
     */
    public NotesModeEnseignementInterNewVOImpl getNotesModeEnseInter() {
        return (NotesModeEnseignementInterNewVOImpl) findViewObject("NotesModeEnseInter");
    }

    /**
     * Container's getter for DetailNoteToNOteInterNewVL2.
     * @return DetailNoteToNOteInterNewVL2
     */
    public ViewLinkImpl getDetailNoteToNOteInterNewVL2() {
        return (ViewLinkImpl) findViewLink("DetailNoteToNOteInterNewVL2");
    }


    /**
     * Container's getter for NiveauxSuperieursOptionsROVO1.
     * @return NiveauxSuperieursOptionsROVO1
     */
    public NiveauxSuperieursOptionsROVOImpl getNiveauxSuperieursOptions() {
        return (NiveauxSuperieursOptionsROVOImpl) findViewObject("NiveauxSuperieursOptions");
    }

    /**
     * Container's getter for ListAutorisationSess1ROVO1.
     * @return ListAutorisationSess1ROVO1
     */
    public ListAutorisationSess1ROVOImpl getListAutorisationSess1() {
        return (ListAutorisationSess1ROVOImpl) findViewObject("ListAutorisationSess1");
    }

    /**
     * Container's getter for ListAutorisationSess2ROVO1.
     * @return ListAutorisationSess2ROVO1
     */
    public ListAutorisationSess2ROVOImpl getListAutorisationSess2() {
        return (ListAutorisationSess2ROVOImpl) findViewObject("ListAutorisationSess2");
    }

    /**
     * Container's getter for InscriptionsAntEtdROVO1.
     * @return InscriptionsAntEtdROVO1
     */
    public InscriptionsAntEtdROVOImpl getInscriptionsAntEtd() {
        return (InscriptionsAntEtdROVOImpl) findViewObject("InscriptionsAntEtd");
    }
}
