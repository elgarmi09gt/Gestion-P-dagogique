package model.services;

import java.io.ByteArrayOutputStream;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import model.entities.java.NiveauSection;
import model.entities.java.ScolParcours;

import model.readOnlyView.AdminByEtablissementROVOImpl;
import model.readOnlyView.AdminEtabROVOImpl;
import model.readOnlyView.AnneeUniverROVOImpl;
import model.readOnlyView.AnneeUniversROImpl;
import model.readOnlyView.CompensableListImpl;
import model.readOnlyView.EtatDelibUEROVOImpl;
import model.readOnlyView.EtatDeliberationAnROVOImpl;
import model.readOnlyView.EtatDeliberationSemROVOImpl;
import model.readOnlyView.EtudiantInsUeOptROVOImpl;
import model.readOnlyView.EtudiantInscritOptROVOImpl;
import model.readOnlyView.EtudiantScolRefROVOImpl;
import model.readOnlyView.EtudiantToMailRecROVOImpl;
import model.readOnlyView.EtudiantsDiplomesROVOImpl;
import model.readOnlyView.EtudiantsToMailROVOImpl;
import model.readOnlyView.FilEcROVOImpl;
import model.readOnlyView.FiliereUeSemestreInsUeOptROVOImpl;
import model.readOnlyView.FormNivFormFilUeROVOImpl;
import model.readOnlyView.FormNivFormROVOImpl;
import model.readOnlyView.FormationByAncCodeROVOImpl;
import model.readOnlyView.GetMentionImpl;
import model.readOnlyView.GetSpecialiteROVOImpl;
import model.readOnlyView.HStructByStructureImpl;
import model.readOnlyView.HightLevelRolesROVOImpl;
import model.readOnlyView.HistoriqueStructureByUserNameImpl;
import model.readOnlyView.InscriptionACloturerROVOImpl;
import model.readOnlyView.InscriptionCloseROVOImpl;
import model.readOnlyView.InscriptionRefROVOImpl;
import model.readOnlyView.IsAccesFilEcExistImpl;
import model.readOnlyView.IsAccessFilUeExistImpl;
import model.readOnlyView.IsDeptEtabExistImpl;
import model.readOnlyView.IsEtabTypeSectExistImpl;
import model.readOnlyView.IsFilEcExistImpl;
import model.readOnlyView.IsFilUeExistImpl;
import model.readOnlyView.IsFilUeMaquetteExistImpl;
import model.readOnlyView.IsFilUeSemEcFilUeExistImpl;
import model.readOnlyView.IsFormDeptExistImpl;
import model.readOnlyView.IsFormationGradeExistImpl;
import model.readOnlyView.IsFormationMentionExistImpl;
import model.readOnlyView.IsHStructExistImpl;
import model.readOnlyView.IsInsPedSemUeFilUeExistImpl;
import model.readOnlyView.IsMentionDomaineExistImpl;
import model.readOnlyView.IsModeCntrlEcFilEcExistImpl;
import model.readOnlyView.IsNivFromMaqAnnMaquetteExistImpl;
import model.readOnlyView.IsNoteModeEnsFilEcExistImpl;
import model.readOnlyView.IsOptionExistImpl;
import model.readOnlyView.IsPercMaqAnnMaquetteExistImpl;
import model.readOnlyView.IsResultatFilEcExistImpl;
import model.readOnlyView.IsSpectMentionExistImpl;
import model.readOnlyView.IsStructExistImpl;
import model.readOnlyView.IsUeExistROVOImpl;
import model.readOnlyView.IsUserRespNivFormROVOImpl;
import model.readOnlyView.IsUtiEtabExistImpl;
import model.readOnlyView.IsValidationFilEcExistImpl;
import model.readOnlyView.IsValidationFilUeExistImpl;
import model.readOnlyView.MaquetteIntegratedNivSecROVOImpl;
import model.readOnlyView.MaquetteNivFormROImpl;
import model.readOnlyView.MaquetteParcoursAnneeROVOImpl;
import model.readOnlyView.MaquetteStructureROVOImpl;
import model.readOnlyView.MaquetteValideImportedROVOImpl;
import model.readOnlyView.MaquettesParcoursROImpl;
import model.readOnlyView.MentionROVOImpl;
import model.readOnlyView.NivFormOptionsROVOImpl;
import model.readOnlyView.NivFormParcoursROImpl;
import model.readOnlyView.NivFormPrcrsROVOImpl;
import model.readOnlyView.NiveauFormationMqAnHistROVOImpl;
import model.readOnlyView.NiveauFormationROImpl;
import model.readOnlyView.NiveauxDiplomanteROVOImpl;
import model.readOnlyView.NiveauxSectionDtailDAPROVOImpl;
import model.readOnlyView.OptionANCNOParcoursROVOImpl;
import model.readOnlyView.OptionByLibelleROVOImpl;
import model.readOnlyView.OptionScolLOVImpl;
import model.readOnlyView.ParcoursOpNullROVOImpl;
import model.readOnlyView.ParcoursRespFrImpMaqEtdROVOImpl;
import model.readOnlyView.ParcousInscriptionUeOptROVOImpl;
import model.readOnlyView.RoleAAssignerROVOImpl;
import model.readOnlyView.ScolDepartementImpl;
import model.readOnlyView.ScolDeptDetailDapROVOImpl;
import model.readOnlyView.ScolDeptIntegrationFrROVOImpl;
import model.readOnlyView.ScolEtabNotIntegratedROVOImpl;
import model.readOnlyView.ScolEtablissementImpl;
import model.readOnlyView.ScolEtudiantInscritDefImpl;
import model.readOnlyView.ScolFilEcImpl;
import model.readOnlyView.ScolFilUeImpl;
import model.readOnlyView.ScolFilUeOptionsANCNOROVOImpl;
import model.readOnlyView.ScolFilUeOptionsROVOImpl;
import model.readOnlyView.ScolFormationIntegrationROVOImpl;
import model.readOnlyView.ScolFormationROVOImpl;
import model.readOnlyView.ScolNivFormIntegratedROVOImpl;
import model.readOnlyView.ScolNivFormNotIntegratedROVOImpl;
import model.readOnlyView.ScolNivFormPrcrsIntegratedROVOImpl;
import model.readOnlyView.ScolNivFormPrcrsNotIntegratedROVOImpl;
import model.readOnlyView.ScolNivFormationROVOImpl;
import model.readOnlyView.ScolStudentDefInscImpl;
import model.readOnlyView.ScolStudentInscDefImpl;
import model.readOnlyView.SearchUserListImpl;
import model.readOnlyView.StructureAccesROVOImpl;
import model.readOnlyView.UtiRolesROVOImpl;
import model.readOnlyView.UtilisateurEtabROVOImpl;
import model.readOnlyView.getAnneeUniversImpl;
import model.readOnlyView.getBourseImpl;
import model.readOnlyView.getCategorieUEImpl;
import model.readOnlyView.getCiviliteImpl;
import model.readOnlyView.getCohorteImpl;
import model.readOnlyView.getCycleROVOImpl;
import model.readOnlyView.getEtatInscImpl;
import model.readOnlyView.getFilUeROVOImpl;
import model.readOnlyView.getFilUeSemestreImpl;
import model.readOnlyView.getFormationImpl;
import model.readOnlyView.getHoraireTDImpl;
import model.readOnlyView.getInsAdminImpl;
import model.readOnlyView.getInsPedImpl;
import model.readOnlyView.getInsPedSemImpl;
import model.readOnlyView.getModePaiementImpl;
import model.readOnlyView.getNatureEcImpl;
import model.readOnlyView.getNatureUeImpl;
import model.readOnlyView.getNivFormImpl;
import model.readOnlyView.getOperateurImpl;
import model.readOnlyView.getOptionImpl;
import model.readOnlyView.getParcoursMaqAnneeImpl;
import model.readOnlyView.getPaysImpl;
import model.readOnlyView.getSexeImpl;
import model.readOnlyView.getStatusImpl;
import model.readOnlyView.getTypeConventionImpl;
import model.readOnlyView.getTypeFormROVOImpl;
import model.readOnlyView.getsemestregradeROVOImpl;
import model.readOnlyView.grhAllUserImpl;
import model.readOnlyView.isChefDeptExistROVOImpl;
import model.readOnlyView.isDeptExistImpl;
import model.readOnlyView.isDeptExistROVOImpl;
import model.readOnlyView.isDomaineExistROVOImpl;
import model.readOnlyView.isDroitNiveauPaysExistImpl;
import model.readOnlyView.isEcExistROVOImpl;
import model.readOnlyView.isEtabExistROVOImpl;
import model.readOnlyView.isEtbExistROVOImpl;
import model.readOnlyView.isFilEcExistROVOImpl;
import model.readOnlyView.isFilUeExistROVOImpl;
import model.readOnlyView.isFormSpecExistImpl;
import model.readOnlyView.isFormationExistROVOImpl;
import model.readOnlyView.isInsAdminExistImpl;
import model.readOnlyView.isInsPedExistROVOImpl;
import model.readOnlyView.isInsPedSemExistImpl;
import model.readOnlyView.isInsPedSemUeExistImpl;
import model.readOnlyView.isMaquetteExistImpl;
import model.readOnlyView.isMaquetteParcoursExistImpl;
import model.readOnlyView.isMentionExistImpl;
import model.readOnlyView.isNivFormExistROVOImpl;
import model.readOnlyView.isNivFormMaqAnnExistImpl;
import model.readOnlyView.isNivFormMaqAnneeExistROVOImpl;
import model.readOnlyView.isNiveauExistImpl;
import model.readOnlyView.isNiveauFormationNiveauExistImpl;
import model.readOnlyView.isPersExistImpl;
import model.readOnlyView.isSectionExistROVOImpl;
import model.readOnlyView.isSpecialiteExistROVOImpl;
import model.readOnlyView.isStudentExistImpl;
import model.readOnlyView.isUserExisteROVOImpl;
import model.readOnlyView.isUserRespNivFormationImpl;
import model.readOnlyView.isUserResponsableFilEcImpl;
import model.readOnlyView.isUserResponsableFilUeImpl;
import model.readOnlyView.isUserResponsableImpl;

import model.services.common.gestdapApp;

import model.updatableview.DroitNiveauPaysVOImpl;
import model.updatableview.FormationsVOImpl;
import model.updatableview.getGradeImpl;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DBTransactionImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jdbc.internal.OracleTypes;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Oct 15 09:54:22 UTC 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class gestdapAppImpl extends ApplicationModuleImpl implements gestdapApp {
    /**
     * This is the default constructor (do not remove).
     */
    public gestdapAppImpl() {
    }

    /**
     * Container's getter for Domaines.
     * @return Domaines
     */
    public ViewObjectImpl getDomaines() {
        return (ViewObjectImpl) findViewObject("Domaines");
    }

    /**
     * Container's getter for MentionsDomaines.
     * @return MentionsDomaines
     */
    public ViewObjectImpl getMentionsDomaines() {
        return (ViewObjectImpl) findViewObject("MentionsDomaines");
    }

    /**
     * Container's getter for Niveaux.
     * @return Niveaux
     */
    public ViewObjectImpl getNiveaux() {
        return (ViewObjectImpl) findViewObject("Niveaux");
    }

    /**
     * Container's getter for GradesFormation.
     * @return GradesFormation
     */
    public ViewObjectImpl getGradesFormation() {
        return (ViewObjectImpl) findViewObject("GradesFormation");
    }

    /**
     * Container's getter for Structures.
     * @return Structures
     */
    public ViewObjectImpl getStructures() {
        return (ViewObjectImpl) findViewObject("Structures");
    }

    /**
     * Container's getter for TypeSections.
     * @return TypeSections
     */
    public ViewObjectImpl getTypeSections() {
        return (ViewObjectImpl) findViewObject("TypeSections");
    }

    /**
     * Container's getter for HistoriquesStructures.
     * @return HistoriquesStructures
     */
    public ViewObjectImpl getHistoriquesStructures() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructures");
    }

    /**
     * Container's getter for HistoriquesStructuresStruct.
     * @return HistoriquesStructuresStruct
     */
    public ViewObjectImpl getHistoriquesStructuresStruct() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresStruct");
    }

    /**
     * Container's getter for Ecs.
     * @return Ecs
     */
    public ViewObjectImpl getEcs() {
        return (ViewObjectImpl) findViewObject("Ecs");
    }

    /**
     * Container's getter for Ues.
     * @return Ues
     */
    public ViewObjectImpl getUes() {
        return (ViewObjectImpl) findViewObject("Ues");
    }

    /**
     * Container's getter for Maquettes.
     * @return Maquettes
     */
    public ViewObjectImpl getMaquettes() {
        return (ViewObjectImpl) findViewObject("Maquettes");
    }

    /**
     * Container's getter for NiveauFormationMaquetteAnnes.
     * @return NiveauFormationMaquetteAnnes
     */
    public ViewObjectImpl getNiveauFormationMaquetteAnnes() {
        return (ViewObjectImpl) findViewObject("NiveauFormationMaquetteAnnes");
    }

    /**
     * Container's getter for FiliereUeSemstres.
     * @return FiliereUeSemstres
     */
    public ViewObjectImpl getFiliereUeSemstres() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemstres");
    }

    /**
     * Container's getter for FiliereUeSemstreEc.
     * @return FiliereUeSemstreEc
     */
    public ViewObjectImpl getFiliereUeSemstreEc() {
        return (ViewObjectImpl) findViewObject("FiliereUeSemstreEc");
    }

    /**
     * Container's getter for MentionROVO1.
     * @return MentionROVO1
     */
    public MentionROVOImpl getMentionROVO1() {
        return (MentionROVOImpl) findViewObject("MentionROVO1");
    }

    /**
     * Container's getter for CompensableList1.
     * @return CompensableList1
     */
    public CompensableListImpl getCompensableList1() {
        return (CompensableListImpl) findViewObject("CompensableList1");
    }

    /**
     * Container's getter for HistoriquesStructuresRO.
     * @return HistoriquesStructuresRO
     */
    public ViewObjectImpl getHistoriquesStructuresRO() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresRO");
    }

    /**
     * Container's getter for FormationsVO1.
     * @return FormationsVO1
     */
    public FormationsVOImpl getFormationsVO1() {
        return (FormationsVOImpl) findViewObject("FormationsVO1");
    }

    /**
     * Container's getter for FormationsSpecialites.
     * @return FormationsSpecialites
     */
    public ViewObjectImpl getFormationsSpecialites() {
        return (ViewObjectImpl) findViewObject("FormationsSpecialites");
    }

    /**
     * Container's getter for FormationsOptions.
     * @return FormationsOptions
     */
    public ViewObjectImpl getFormationsOptions() {
        return (ViewObjectImpl) findViewObject("FormationsOptions");
    }

    /**
     * Container's getter for FormationAcces.
     * @return FormationAcces
     */
    public ViewObjectImpl getFormationAcces() {
        return (ViewObjectImpl) findViewObject("FormationAcces");
    }

    /**
     * Container's getter for FormationConditionAcces.
     * @return FormationConditionAcces
     */
    public ViewObjectImpl getFormationConditionAcces() {
        return (ViewObjectImpl) findViewObject("FormationConditionAcces");
    }

    /**
     * Container's getter for FormationLangue.
     * @return FormationLangue
     */
    public ViewObjectImpl getFormationLangue() {
        return (ViewObjectImpl) findViewObject("FormationLangue");
    }

    /**
     * Container's getter for FormationOrganisatioEtude.
     * @return FormationOrganisatioEtude
     */
    public ViewObjectImpl getFormationOrganisatioEtude() {
        return (ViewObjectImpl) findViewObject("FormationOrganisatioEtude");
    }

    /**
     * Container's getter for FormationInstituts.
     * @return FormationInstituts
     */
    public ViewObjectImpl getFormationInstituts() {
        return (ViewObjectImpl) findViewObject("FormationInstituts");
    }

    /**
     * Container's getter for NiveauxFormations.
     * @return NiveauxFormations
     */
    public ViewObjectImpl getNiveauxFormations() {
        return (ViewObjectImpl) findViewObject("NiveauxFormations");
    }

    /**
     * Container's getter for NiveauFormationCohortes.
     * @return NiveauFormationCohortes
     */
    public ViewObjectImpl getNiveauFormationCohortes() {
        return (ViewObjectImpl) findViewObject("NiveauFormationCohortes");
    }

    /**
     * Container's getter for NiveauxFormationParcours.
     * @return NiveauxFormationParcours
     */
    public ViewObjectImpl getNiveauxFormationParcours() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationParcours");
    }

    /**
     * Container's getter for SpecialitesMentions.
     * @return SpecialitesMentions
     */
    public ViewObjectImpl getSpecialitesMentions() {
        return (ViewObjectImpl) findViewObject("SpecialitesMentions");
    }

    /**
     * Container's getter for ModeControleEcVO.
     * @return ModeControleEcVO
     */
    public ViewObjectImpl getModeControleEcVO() {
        return (ViewObjectImpl) findViewObject("ModeControleEcVO");
    }

    /**
     * Container's getter for ParcoursMaquetteAnneeVO.
     * @return ParcoursMaquetteAnneeVO
     */
    public ViewObjectImpl getParcoursMaquetteAnneeVO() {
        return (ViewObjectImpl) findViewObject("ParcoursMaquetteAnneeVO");
    }

    /**
     * Container's getter for isSectionExist.
     * @return isSectionExist
     */
    public isSectionExistROVOImpl getisSectionExist() {
        return (isSectionExistROVOImpl) findViewObject("isSectionExist");
    }

    /**
     * Container's getter for IsUeExistROVO1.
     * @return IsUeExistROVO1
     */
    public IsUeExistROVOImpl getIsUeExistROVO1() {
        return (IsUeExistROVOImpl) findViewObject("IsUeExistROVO1");
    }

    /**
     * Container's getter for isEcExistROVO1.
     * @return isEcExistROVO1
     */
    public isEcExistROVOImpl getisEcExistROVO1() {
        return (isEcExistROVOImpl) findViewObject("isEcExistROVO1");
    }

    /**
     * Container's getter for isEtabExistROVO1.
     * @return isEtabExistROVO1
     */
    public isEtabExistROVOImpl getisEtabExistROVO1() {
        return (isEtabExistROVOImpl) findViewObject("isEtabExistROVO1");
    }

    /**
     * Container's getter for isDeptExist1.
     * @return isDeptExist1
     */
    public isDeptExistImpl getisDeptExist1() {
        return (isDeptExistImpl) findViewObject("isDeptExist1");
    }

    /**
     * Container's getter for isDomaineExistROVO1.
     * @return isDomaineExistROVO1
     */
    public isDomaineExistROVOImpl getisDomaineExistROVO1() {
        return (isDomaineExistROVOImpl) findViewObject("isDomaineExistROVO1");
    }

    /**
     * Container's getter for isMentionExist1.
     * @return isMentionExist1
     */
    public isMentionExistImpl getisMentionExist1() {
        return (isMentionExistImpl) findViewObject("isMentionExist1");
    }

    /**
     * Container's getter for isSpecialiteExistROVO1.
     * @return isSpecialiteExistROVO1
     */
    public isSpecialiteExistROVOImpl getisSpecialiteExistROVO1() {
        return (isSpecialiteExistROVOImpl) findViewObject("isSpecialiteExistROVO1");
    }

    /**
     * Container's getter for isFormationExistROVO1.
     * @return isFormationExistROVO1
     */
    public isFormationExistROVOImpl getisFormationExistROVO1() {
        return (isFormationExistROVOImpl) findViewObject("isFormationExistROVO1");
    }

    /**
     * Container's getter for isNivFormExistROVO1.
     * @return isNivFormExistROVO1
     */
    public isNivFormExistROVOImpl getisNivFormExistROVO1() {
        return (isNivFormExistROVOImpl) findViewObject("isNivFormExistROVO1");
    }

    /**
     * Container's getter for isMaquetteExist1.
     * @return isMaquetteExist1
     */
    public isMaquetteExistImpl getisMaquetteExist1() {
        return (isMaquetteExistImpl) findViewObject("isMaquetteExist1");
    }

    /**
     * Container's getter for isFilUeExistROVO1.
     * @return isFilUeExistROVO1
     */
    public isFilUeExistROVOImpl getisFilUeExistROVO1() {
        return (isFilUeExistROVOImpl) findViewObject("isFilUeExistROVO1");
    }

    /**
     * Container's getter for isFilEcExistROVO1.
     * @return isFilEcExistROVO1
     */
    public isFilEcExistROVOImpl getisFilEcExistROVO1() {
        return (isFilEcExistROVOImpl) findViewObject("isFilEcExistROVO1");
    }

    /**
     * Container's getter for isNivFormMaqAnneeExistROVO1.
     * @return isNivFormMaqAnneeExistROVO1
     */
    public isNivFormMaqAnneeExistROVOImpl getisNivFormMaqAnneeExistROVO1() {
        return (isNivFormMaqAnneeExistROVOImpl) findViewObject("isNivFormMaqAnneeExistROVO1");
    }

    /**
     * Container's getter for getGrade1.
     * @return getGrade1
     */
    public getGradeImpl getgetGrade1() {
        return (getGradeImpl) findViewObject("getGrade1");
    }

    /**
     * Container's getter for getCycleROVO1.
     * @return getCycleROVO1
     */
    public getCycleROVOImpl getgetCycleROVO1() {
        return (getCycleROVOImpl) findViewObject("getCycleROVO1");
    }

    /**
     * Container's getter for getTypeFormROVO1.
     * @return getTypeFormROVO1
     */
    public getTypeFormROVOImpl getgetTypeFormROVO1() {
        return (getTypeFormROVOImpl) findViewObject("getTypeFormROVO1");
    }

    /**
     * Container's getter for isNiveauExist1.
     * @return isNiveauExist1
     */
    public isNiveauExistImpl getisNiveauExist1() {
        return (isNiveauExistImpl) findViewObject("isNiveauExist1");
    }

    /**
     * Container's getter for getCategorieUE1.
     * @return getCategorieUE1
     */
    public getCategorieUEImpl getgetCategorieUE1() {
        return (getCategorieUEImpl) findViewObject("getCategorieUE1");
    }

    /**
     * Container's getter for getNatureUe1.
     * @return getNatureUe1
     */
    public getNatureUeImpl getgetNatureUe1() {
        return (getNatureUeImpl) findViewObject("getNatureUe1");
    }

    /**
     * Container's getter for getNatureUe2.
     * @return getNatureUe2
     */
    public getNatureUeImpl getgetNatureUe2() {
        return (getNatureUeImpl) findViewObject("getNatureUe2");
    }

    /**
     * Container's getter for getFilUeROVO1.
     * @return getFilUeROVO1
     */
    public getFilUeROVOImpl getgetFilUeROVO1() {
        return (getFilUeROVOImpl) findViewObject("getFilUeROVO1");
    }

    /**
     * Container's getter for getNatureEc1.
     * @return getNatureEc1
     */
    public getNatureEcImpl getgetNatureEc1() {
        return (getNatureEcImpl) findViewObject("getNatureEc1");
    }

    /**
     * Container's getter for getModePaiement1.
     * @return getModePaiement1
     */
    public getModePaiementImpl getgetModePaiement1() {
        return (getModePaiementImpl) findViewObject("getModePaiement1");
    }

    /**
     * Container's getter for getPays1.
     * @return getPays1
     */
    public getPaysImpl getgetPays1() {
        return (getPaysImpl) findViewObject("getPays1");
    }

    /**
     * Container's getter for DroitNiveauPaysVO.
     * @return DroitNiveauPaysVO
     */
    public DroitNiveauPaysVOImpl getDroitNiveauPaysVO() {
        return (DroitNiveauPaysVOImpl) findViewObject("DroitNiveauPaysVO");
    }

    /**
     * Container's getter for isDroitNiveauPaysExist1.
     * @return isDroitNiveauPaysExist1
     */
    public isDroitNiveauPaysExistImpl getisDroitNiveauPaysExist1() {
        return (isDroitNiveauPaysExistImpl) findViewObject("isDroitNiveauPaysExist1");
    }

    /**
     * Container's getter for GetMention1.
     * @return GetMention1
     */
    public GetMentionImpl getGetMention1() {
        return (GetMentionImpl) findViewObject("GetMention1");
    }

    /**
     * Container's getter for getFormation1.
     * @return getFormation1
     */
    public getFormationImpl getgetFormation1() {
        return (getFormationImpl) findViewObject("getFormation1");
    }

    /**
     * Container's getter for getNivForm1.
     * @return getNivForm1
     */
    public getNivFormImpl getgetNivForm1() {
        return (getNivFormImpl) findViewObject("getNivForm1");
    }

    /**
     * Container's getter for getAnneeUnivers1.
     * @return getAnneeUnivers1
     */
    public getAnneeUniversImpl getgetAnneeUnivers1() {
        return (getAnneeUniversImpl) findViewObject("getAnneeUnivers1");
    }

    /**
     * Container's getter for OptionsSpecialites.
     * @return OptionsSpecialites
     */
    public ViewObjectImpl getOptionsSpecialites() {
        return (ViewObjectImpl) findViewObject("OptionsSpecialites");
    }

    /**
     * Container's getter for GetSpecialiteROVO1.
     * @return GetSpecialiteROVO1
     */
    public GetSpecialiteROVOImpl getGetSpecialiteROVO1() {
        return (GetSpecialiteROVOImpl) findViewObject("GetSpecialiteROVO1");
    }

    /**
     * Container's getter for IsOptionExist1.
     * @return IsOptionExist1
     */
    public IsOptionExistImpl getIsOptionExist1() {
        return (IsOptionExistImpl) findViewObject("IsOptionExist1");
    }

    /**
     * Container's getter for isFormSpecExist1.
     * @return isFormSpecExist1
     */
    public isFormSpecExistImpl getisFormSpecExist1() {
        return (isFormSpecExistImpl) findViewObject("isFormSpecExist1");
    }

    /**
     * Container's getter for FormationsSpecialitesVO1.
     * @return FormationsSpecialitesVO1
     */
    public ViewObjectImpl getFormationsSpecialitesVO1() {
        return (ViewObjectImpl) findViewObject("FormationsSpecialitesVO1");
    }

    /**
     * Container's getter for Personnes.
     * @return Personnes
     */
    public ViewObjectImpl getPersonnes() {
        return (ViewObjectImpl) findViewObject("Personnes");
    }

    /**
     * Container's getter for Etudiants.
     * @return Etudiants
     */
    public ViewObjectImpl getEtudiants() {
        return (ViewObjectImpl) findViewObject("Etudiants");
    }

    /**
     * Container's getter for InscriptionsAdmin.
     * @return InscriptionsAdmin
     */
    public ViewObjectImpl getInscriptionsAdmin() {
        return (ViewObjectImpl) findViewObject("InscriptionsAdmin");
    }

    /**
     * Container's getter for InscriptionsPedagogiques.
     * @return InscriptionsPedagogiques
     */
    public ViewObjectImpl getInscriptionsPedagogiques() {
        return (ViewObjectImpl) findViewObject("InscriptionsPedagogiques");
    }

    /**
     * Container's getter for InscriptionPedSemestre.
     * @return InscriptionPedSemestre
     */
    public ViewObjectImpl getInscriptionPedSemestre() {
        return (ViewObjectImpl) findViewObject("InscriptionPedSemestre");
    }

    /**
     * Container's getter for getCivilite1.
     * @return getCivilite1
     */
    public getCiviliteImpl getgetCivilite1() {
        return (getCiviliteImpl) findViewObject("getCivilite1");
    }

    /**
     * Container's getter for getSexe1.
     * @return getSexe1
     */
    public getSexeImpl getgetSexe1() {
        return (getSexeImpl) findViewObject("getSexe1");
    }

    /**
     * Container's getter for isPersExist1.
     * @return isPersExist1
     */
    public isPersExistImpl getisPersExist1() {
        return (isPersExistImpl) findViewObject("isPersExist1");
    }

    /**
     * Container's getter for isStudentExist1.
     * @return isStudentExist1
     */
    public isStudentExistImpl getisStudentExist1() {
        return (isStudentExistImpl) findViewObject("isStudentExist1");
    }

    /**
     * Container's getter for InscriptionPedSemUe.
     * @return InscriptionPedSemUe
     */
    public ViewObjectImpl getInscriptionPedSemUe() {
        return (ViewObjectImpl) findViewObject("InscriptionPedSemUe");
    }

    /**
     * Container's getter for isInsAdminExist1.
     * @return isInsAdminExist1
     */
    public isInsAdminExistImpl getisInsAdminExist1() {
        return (isInsAdminExistImpl) findViewObject("isInsAdminExist1");
    }

    /**
     * Container's getter for isInsPedExistROVO1.
     * @return isInsPedExistROVO1
     */
    public isInsPedExistROVOImpl getisInsPedExistROVO1() {
        return (isInsPedExistROVOImpl) findViewObject("isInsPedExistROVO1");
    }

    /**
     * Container's getter for getStatus1.
     * @return getStatus1
     */
    public getStatusImpl getgetStatus1() {
        return (getStatusImpl) findViewObject("getStatus1");
    }

    /**
     * Container's getter for getBourse1.
     * @return getBourse1
     */
    public getBourseImpl getgetBourse1() {
        return (getBourseImpl) findViewObject("getBourse1");
    }

    /**
     * Container's getter for getCohorte1.
     * @return getCohorte1
     */
    public getCohorteImpl getgetCohorte1() {
        return (getCohorteImpl) findViewObject("getCohorte1");
    }

    /**
     * Container's getter for getEtatInsc1.
     * @return getEtatInsc1
     */
    public getEtatInscImpl getgetEtatInsc1() {
        return (getEtatInscImpl) findViewObject("getEtatInsc1");
    }

    /**
     * Container's getter for getTypeConvention1.
     * @return getTypeConvention1
     */
    public getTypeConventionImpl getgetTypeConvention1() {
        return (getTypeConventionImpl) findViewObject("getTypeConvention1");
    }

    /**
     * Container's getter for getHoraireTD1.
     * @return getHoraireTD1
     */
    public getHoraireTDImpl getgetHoraireTD1() {
        return (getHoraireTDImpl) findViewObject("getHoraireTD1");
    }

    /**
     * Container's getter for getOperateur1.
     * @return getOperateur1
     */
    public getOperateurImpl getgetOperateur1() {
        return (getOperateurImpl) findViewObject("getOperateur1");
    }

    /**
     * Container's getter for getOption1.
     * @return getOption1
     */
    public getOptionImpl getgetOption1() {
        return (getOptionImpl) findViewObject("getOption1");
    }

    /**
     * Container's getter for getParcoursMaqAnnee1.
     * @return getParcoursMaqAnnee1
     */
    public getParcoursMaqAnneeImpl getgetParcoursMaqAnnee1() {
        return (getParcoursMaqAnneeImpl) findViewObject("getParcoursMaqAnnee1");
    }

    /**
     * Container's getter for getInsAdmin1.
     * @return getInsAdmin1
     */
    public getInsAdminImpl getgetInsAdmin1() {
        return (getInsAdminImpl) findViewObject("getInsAdmin1");
    }

    /**
     * Container's getter for getInsPed1.
     * @return getInsPed1
     */
    public getInsPedImpl getgetInsPed1() {
        return (getInsPedImpl) findViewObject("getInsPed1");
    }

    /**
     * Container's getter for isInsPedSemExist1.
     * @return isInsPedSemExist1
     */
    public isInsPedSemExistImpl getisInsPedSemExist1() {
        return (isInsPedSemExistImpl) findViewObject("isInsPedSemExist1");
    }

    /**
     * Container's getter for getFilUeSemestre1.
     * @return getFilUeSemestre1
     */
    public getFilUeSemestreImpl getgetFilUeSemestre1() {
        return (getFilUeSemestreImpl) findViewObject("getFilUeSemestre1");
    }

    /**
     * Container's getter for getInsPedSem1.
     * @return getInsPedSem1
     */
    public getInsPedSemImpl getgetInsPedSem1() {
        return (getInsPedSemImpl) findViewObject("getInsPedSem1");
    }

    /**
     * Container's getter for isInsPedSemUeExist1.
     * @return isInsPedSemUeExist1
     */
    public isInsPedSemUeExistImpl getisInsPedSemUeExist1() {
        return (isInsPedSemUeExistImpl) findViewObject("isInsPedSemUeExist1");
    }

    /**
     * Container's getter for StructuresVO1.
     * @return StructuresVO1
     */
    public ViewObjectImpl getStructuresVO1() {
        return (ViewObjectImpl) findViewObject("StructuresVO1");
    }

    /**
     * Container's getter for HistoriquesStructuresROVO1.
     * @return HistoriquesStructuresROVO1
     */
    public ViewObjectImpl getHistoriquesStructuresROVO1() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresROVO1");
    }

    /**
     * Container's getter for FormationsVO2.
     * @return FormationsVO2
     */
    public FormationsVOImpl getFormationsVO2() {
        return (FormationsVOImpl) findViewObject("FormationsVO2");
    }

    /**
     * Container's getter for FormationAccesVO1.
     * @return FormationAccesVO1
     */
    public ViewObjectImpl getFormationAccesVO1() {
        return (ViewObjectImpl) findViewObject("FormationAccesVO1");
    }

    /**
     * Container's getter for FormationConditionAccesVO1.
     * @return FormationConditionAccesVO1
     */
    public ViewObjectImpl getFormationConditionAccesVO1() {
        return (ViewObjectImpl) findViewObject("FormationConditionAccesVO1");
    }

    /**
     * Container's getter for FormationInstitutsVO1.
     * @return FormationInstitutsVO1
     */
    public ViewObjectImpl getFormationInstitutsVO1() {
        return (ViewObjectImpl) findViewObject("FormationInstitutsVO1");
    }

    /**
     * Container's getter for FormationLangueVO1.
     * @return FormationLangueVO1
     */
    public ViewObjectImpl getFormationLangueVO1() {
        return (ViewObjectImpl) findViewObject("FormationLangueVO1");
    }

    /**
     * Container's getter for FormationOrganisatioEtudeVO1.
     * @return FormationOrganisatioEtudeVO1
     */
    public ViewObjectImpl getFormationOrganisatioEtudeVO1() {
        return (ViewObjectImpl) findViewObject("FormationOrganisatioEtudeVO1");
    }

    /**
     * Container's getter for FormationsSpecialitesVO2.
     * @return FormationsSpecialitesVO2
     */
    public ViewObjectImpl getFormationsSpecialitesVO2() {
        return (ViewObjectImpl) findViewObject("FormationsSpecialitesVO2");
    }

    /**
     * Container's getter for NiveauxFormationsVO1.
     * @return NiveauxFormationsVO1
     */
    public ViewObjectImpl getNiveauxFormationsVO1() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationsVO1");
    }

    /**
     * Container's getter for NiveauFormationCohortesVO1.
     * @return NiveauFormationCohortesVO1
     */
    public ViewObjectImpl getNiveauFormationCohortesVO1() {
        return (ViewObjectImpl) findViewObject("NiveauFormationCohortesVO1");
    }

    /**
     * Container's getter for NiveauxFormationParcoursVO1.
     * @return NiveauxFormationParcoursVO1
     */
    public ViewObjectImpl getNiveauxFormationParcoursVO1() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationParcoursVO1");
    }

    /**
     * Container's getter for FormationsOptionsVO1.
     * @return FormationsOptionsVO1
     */
    public ViewObjectImpl getFormationsOptionsVO1() {
        return (ViewObjectImpl) findViewObject("FormationsOptionsVO1");
    }

    /**
     * Container's getter for StructureAcces.
     * @return StructureAcces
     */
    public StructureAccesROVOImpl getStructureAcces() {
        return (StructureAccesROVOImpl) findViewObject("StructureAcces");
    }

    /**
     * Container's getter for HistoriquesStructuresROVO.
     * @return HistoriquesStructuresROVO
     */
    public ViewObjectImpl getHistoriquesStructuresROVO() {
        return (ViewObjectImpl) findViewObject("HistoriquesStructuresROVO");
    }

    /**
     * Container's getter for FormationsVO.
     * @return FormationsVO
     */
    public FormationsVOImpl getFormationsVO() {
        return (FormationsVOImpl) findViewObject("FormationsVO");
    }

    /**
     * Container's getter for NiveauxFormationsVO.
     * @return NiveauxFormationsVO
     */
    public ViewObjectImpl getNiveauxFormationsVO() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationsVO");
    }

    /**
     * Container's getter for FormationConditionAccesVO.
     * @return FormationConditionAccesVO
     */
    public ViewObjectImpl getFormationConditionAccesVO() {
        return (ViewObjectImpl) findViewObject("FormationConditionAccesVO");
    }

    /**
     * Container's getter for FormationInstitutsVO.
     * @return FormationInstitutsVO
     */
    public ViewObjectImpl getFormationInstitutsVO() {
        return (ViewObjectImpl) findViewObject("FormationInstitutsVO");
    }

    /**
     * Container's getter for FormationLangueVO.
     * @return FormationLangueVO
     */
    public ViewObjectImpl getFormationLangueVO() {
        return (ViewObjectImpl) findViewObject("FormationLangueVO");
    }

    /**
     * Container's getter for FormationOrganisatioEtudeVO.
     * @return FormationOrganisatioEtudeVO
     */
    public ViewObjectImpl getFormationOrganisatioEtudeVO() {
        return (ViewObjectImpl) findViewObject("FormationOrganisatioEtudeVO");
    }

    /**
     * Container's getter for FormationsSpecialitesVO.
     * @return FormationsSpecialitesVO
     */
    public ViewObjectImpl getFormationsSpecialitesVO() {
        return (ViewObjectImpl) findViewObject("FormationsSpecialitesVO");
    }

    /**
     * Container's getter for FormationsOptionsVO.
     * @return FormationsOptionsVO
     */
    public ViewObjectImpl getFormationsOptionsVO() {
        return (ViewObjectImpl) findViewObject("FormationsOptionsVO");
    }

    /**
     * Container's getter for NiveauFormationCohortesVO.
     * @return NiveauFormationCohortesVO
     */
    public ViewObjectImpl getNiveauFormationCohortesVO() {
        return (ViewObjectImpl) findViewObject("NiveauFormationCohortesVO");
    }

    /**
     * Container's getter for NiveauxFormationParcoursVO.
     * @return NiveauxFormationParcoursVO
     */
    public ViewObjectImpl getNiveauxFormationParcoursVO() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationParcoursVO");
    }

    /**
     * Container's getter for FormationAccesVO.
     * @return FormationAccesVO
     */
    public ViewObjectImpl getFormationAccesVO() {
        return (ViewObjectImpl) findViewObject("FormationAccesVO");
    }

    /**
     * Container's getter for IsMentionDomaineExist.
     * @return IsMentionDomaineExist
     */
    public IsMentionDomaineExistImpl getIsMentionDomaineExist() {
        return (IsMentionDomaineExistImpl) findViewObject("IsMentionDomaineExist");
    }

    /**
     * Container's getter for IsFormationMentionExist.
     * @return IsFormationMentionExist
     */
    public IsFormationMentionExistImpl getIsFormationMentionExist() {
        return (IsFormationMentionExistImpl) findViewObject("IsFormationMentionExist");
    }

    /**
     * Container's getter for IsSpectMentionExist.
     * @return IsSpectMentionExist
     */
    public IsSpectMentionExistImpl getIsSpectMentionExist() {
        return (IsSpectMentionExistImpl) findViewObject("IsSpectMentionExist");
    }

    /**
     * Container's getter for IsFormationGradeExist.
     * @return IsFormationGradeExist
     */
    public IsFormationGradeExistImpl getIsFormationGradeExist() {
        return (IsFormationGradeExistImpl) findViewObject("IsFormationGradeExist");
    }

    /**
     * Container's getter for IsEtabTypeSectExist.
     * @return IsEtabTypeSectExist
     */
    public IsEtabTypeSectExistImpl getIsEtabTypeSectExist() {
        return (IsEtabTypeSectExistImpl) findViewObject("IsEtabTypeSectExist");
    }

    /**
     * Container's getter for isNiveauFormationNiveauExist.
     * @return isNiveauFormationNiveauExist
     */
    public isNiveauFormationNiveauExistImpl getisNiveauFormationNiveauExist() {
        return (isNiveauFormationNiveauExistImpl) findViewObject("isNiveauFormationNiveauExist");
    }

    /**
     * Container's getter for IsDeptEtabExist.
     * @return IsDeptEtabExist
     */
    public IsDeptEtabExistImpl getIsDeptEtabExist() {
        return (IsDeptEtabExistImpl) findViewObject("IsDeptEtabExist");
    }

    /**
     * Container's getter for IsUtiEtabExist.
     * @return IsUtiEtabExist
     */
    public IsUtiEtabExistImpl getIsUtiEtabExist() {
        return (IsUtiEtabExistImpl) findViewObject("IsUtiEtabExist");
    }

    /**
     * Container's getter for IsFormDeptExist.
     * @return IsFormDeptExist
     */
    public IsFormDeptExistImpl getIsFormDeptExist() {
        return (IsFormDeptExistImpl) findViewObject("IsFormDeptExist");
    }

    /**
     * Container's getter for IsFilEcExist.
     * @return IsFilEcExist
     */
    public IsFilEcExistImpl getIsFilEcExist() {
        return (IsFilEcExistImpl) findViewObject("IsFilEcExist");
    }

    /**
     * Container's getter for IsFilUeExist.
     * @return IsFilUeExist
     */
    public IsFilUeExistImpl getIsFilUeExist() {
        return (IsFilUeExistImpl) findViewObject("IsFilUeExist");
    }

    /**
     * Container's getter for IsFilUeMaquetteExist.
     * @return IsFilUeMaquetteExist
     */
    public IsFilUeMaquetteExistImpl getIsFilUeMaquetteExist() {
        return (IsFilUeMaquetteExistImpl) findViewObject("IsFilUeMaquetteExist");
    }

    /**
     * Container's getter for IsNivFromMaqAnnMaquetteExist.
     * @return IsNivFromMaqAnnMaquetteExist
     */
    public IsNivFromMaqAnnMaquetteExistImpl getIsNivFromMaqAnnMaquetteExist() {
        return (IsNivFromMaqAnnMaquetteExistImpl) findViewObject("IsNivFromMaqAnnMaquetteExist");
    }

    /**
     * Container's getter for IsPercMaqAnnMaquetteExist.
     * @return IsPercMaqAnnMaquetteExist
     */
    public IsPercMaqAnnMaquetteExistImpl getIsPercMaqAnnMaquetteExist() {
        return (IsPercMaqAnnMaquetteExistImpl) findViewObject("IsPercMaqAnnMaquetteExist");
    }

    /**
     * Container's getter for IsValidationFilUeExist.
     * @return IsValidationFilUeExist
     */
    public IsValidationFilUeExistImpl getIsValidationFilUeExist() {
        return (IsValidationFilUeExistImpl) findViewObject("IsValidationFilUeExist");
    }

    /**
     * Container's getter for IsInsPedSemUeFilUeExist.
     * @return IsInsPedSemUeFilUeExist
     */
    public IsInsPedSemUeFilUeExistImpl getIsInsPedSemUeFilUeExist() {
        return (IsInsPedSemUeFilUeExistImpl) findViewObject("IsInsPedSemUeFilUeExist");
    }

    /**
     * Container's getter for IsFilUeSemEcFilUeExist.
     * @return IsFilUeSemEcFilUeExist
     */
    public IsFilUeSemEcFilUeExistImpl getIsFilUeSemEcFilUeExist() {
        return (IsFilUeSemEcFilUeExistImpl) findViewObject("IsFilUeSemEcFilUeExist");
    }

    /**
     * Container's getter for IsAccessFilUeExist.
     * @return IsAccessFilUeExist
     */
    public IsAccessFilUeExistImpl getIsAccessFilUeExist() {
        return (IsAccessFilUeExistImpl) findViewObject("IsAccessFilUeExist");
    }

    /**
     * Container's getter for IsModeCntrlEcFilEcExist.
     * @return IsModeCntrlEcFilEcExist
     */
    public IsModeCntrlEcFilEcExistImpl getIsModeCntrlEcFilEcExist() {
        return (IsModeCntrlEcFilEcExistImpl) findViewObject("IsModeCntrlEcFilEcExist");
    }

    /**
     * Container's getter for IsResultatFilEcExist.
     * @return IsResultatFilEcExist
     */
    public IsResultatFilEcExistImpl getIsResultatFilEcExist() {
        return (IsResultatFilEcExistImpl) findViewObject("IsResultatFilEcExist");
    }

    /**
     * Container's getter for IsValidationFilEcExist.
     * @return IsValidationFilEcExist
     */
    public IsValidationFilEcExistImpl getIsValidationFilEcExist() {
        return (IsValidationFilEcExistImpl) findViewObject("IsValidationFilEcExist");
    }

    /**
     * Container's getter for IsNoteModeEnsFilEcExist.
     * @return IsNoteModeEnsFilEcExist
     */
    public IsNoteModeEnsFilEcExistImpl getIsNoteModeEnsFilEcExist() {
        return (IsNoteModeEnsFilEcExistImpl) findViewObject("IsNoteModeEnsFilEcExist");
    }

    /**
     * Container's getter for IsAccesFilEcExist.
     * @return IsAccesFilEcExist
     */
    public IsAccesFilEcExistImpl getIsAccesFilEcExist() {
        return (IsAccesFilEcExistImpl) findViewObject("IsAccesFilEcExist");
    }

    /**
     * Container's getter for AnneeUniverROVO.
     * @return AnneeUniverROVO
     */
    public AnneeUniverROVOImpl getAnneeUniverROVO() {
        return (AnneeUniverROVOImpl) findViewObject("AnneeUniverROVO");
    }

    /**
     * Container's getter for NiveauFormationRO.
     * @return NiveauFormationRO
     */
    public NiveauFormationROImpl getNiveauFormationRO() {
        return (NiveauFormationROImpl) findViewObject("NiveauFormationRO");
    }

    /**
     * Container's getter for NivFormParcoursRO.
     * @return NivFormParcoursRO
     */
    public NivFormParcoursROImpl getNivFormParcoursRO() {
        return (NivFormParcoursROImpl) findViewObject("NivFormParcoursRO");
    }

    /**
     * Container's getter for DomainesToMentionsVL1.
     * @return DomainesToMentionsVL1
     */
    public ViewLinkImpl getDomainesToMentionsVL1() {
        return (ViewLinkImpl) findViewLink("DomainesToMentionsVL1");
    }

    /**
     * Container's getter for StructureToHStructureVL1.
     * @return StructureToHStructureVL1
     */
    public ViewLinkImpl getStructureToHStructureVL1() {
        return (ViewLinkImpl) findViewLink("StructureToHStructureVL1");
    }

    /**
     * Container's getter for MaquetteToNivForMaqAnVL1.
     * @return MaquetteToNivForMaqAnVL1
     */
    public ViewLinkImpl getMaquetteToNivForMaqAnVL1() {
        return (ViewLinkImpl) findViewLink("MaquetteToNivForMaqAnVL1");
    }

    /**
     * Container's getter for MaquetteToFiliereUeSemestreAssoc1.
     * @return MaquetteToFiliereUeSemestreAssoc1
     */
    public ViewLinkImpl getMaquetteToFiliereUeSemestreAssoc1() {
        return (ViewLinkImpl) findViewLink("MaquetteToFiliereUeSemestreAssoc1");
    }

    /**
     * Container's getter for FiliereUeSemTOFiliereUeSemEcVL1.
     * @return FiliereUeSemTOFiliereUeSemEcVL1
     */
    public ViewLinkImpl getFiliereUeSemTOFiliereUeSemEcVL1() {
        return (ViewLinkImpl) findViewLink("FiliereUeSemTOFiliereUeSemEcVL1");
    }

    /**
     * Container's getter for HistoriquesStructuresROToFormations1.
     * @return HistoriquesStructuresROToFormations1
     */
    public ViewLinkImpl getHistoriquesStructuresROToFormations1() {
        return (ViewLinkImpl) findViewLink("HistoriquesStructuresROToFormations1");
    }

    /**
     * Container's getter for FormationsToFormationsSpecialitesVL1.
     * @return FormationsToFormationsSpecialitesVL1
     */
    public ViewLinkImpl getFormationsToFormationsSpecialitesVL1() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationsSpecialitesVL1");
    }

    /**
     * Container's getter for FormationsSpecialitesToFormationsOptionsVL1.
     * @return FormationsSpecialitesToFormationsOptionsVL1
     */
    public ViewLinkImpl getFormationsSpecialitesToFormationsOptionsVL1() {
        return (ViewLinkImpl) findViewLink("FormationsSpecialitesToFormationsOptionsVL1");
    }

    /**
     * Container's getter for FormationsToFormationAccesVL1.
     * @return FormationsToFormationAccesVL1
     */
    public ViewLinkImpl getFormationsToFormationAccesVL1() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationAccesVL1");
    }

    /**
     * Container's getter for FormationToFormationConditionAccesVL1.
     * @return FormationToFormationConditionAccesVL1
     */
    public ViewLinkImpl getFormationToFormationConditionAccesVL1() {
        return (ViewLinkImpl) findViewLink("FormationToFormationConditionAccesVL1");
    }

    /**
     * Container's getter for FormationToFormationLangueVL1.
     * @return FormationToFormationLangueVL1
     */
    public ViewLinkImpl getFormationToFormationLangueVL1() {
        return (ViewLinkImpl) findViewLink("FormationToFormationLangueVL1");
    }

    /**
     * Container's getter for FormationsToFormationOrganisationEtudeVL1.
     * @return FormationsToFormationOrganisationEtudeVL1
     */
    public ViewLinkImpl getFormationsToFormationOrganisationEtudeVL1() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationOrganisationEtudeVL1");
    }

    /**
     * Container's getter for FormationsToFormationInstitutsVL1.
     * @return FormationsToFormationInstitutsVL1
     */
    public ViewLinkImpl getFormationsToFormationInstitutsVL1() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationInstitutsVL1");
    }

    /**
     * Container's getter for FormationsToNiveauxFormationsVL1.
     * @return FormationsToNiveauxFormationsVL1
     */
    public ViewLinkImpl getFormationsToNiveauxFormationsVL1() {
        return (ViewLinkImpl) findViewLink("FormationsToNiveauxFormationsVL1");
    }

    /**
     * Container's getter for NiveauxFormationsToNiveauFormationsCohortesVL1.
     * @return NiveauxFormationsToNiveauFormationsCohortesVL1
     */
    public ViewLinkImpl getNiveauxFormationsToNiveauFormationsCohortesVL1() {
        return (ViewLinkImpl) findViewLink("NiveauxFormationsToNiveauFormationsCohortesVL1");
    }

    /**
     * Container's getter for NiveauFormationsCohortesToNiveauxFormationParcoursVL1.
     * @return NiveauFormationsCohortesToNiveauxFormationParcoursVL1
     */
    public ViewLinkImpl getNiveauFormationsCohortesToNiveauxFormationParcoursVL1() {
        return (ViewLinkImpl) findViewLink("NiveauFormationsCohortesToNiveauxFormationParcoursVL1");
    }

    /**
     * Container's getter for MentionsToSpecialitesVL1.
     * @return MentionsToSpecialitesVL1
     */
    public ViewLinkImpl getMentionsToSpecialitesVL1() {
        return (ViewLinkImpl) findViewLink("MentionsToSpecialitesVL1");
    }

    /**
     * Container's getter for FiliererUeSemEcVoToModeControleEcVo1.
     * @return FiliererUeSemEcVoToModeControleEcVo1
     */
    public ViewLinkImpl getFiliererUeSemEcVoToModeControleEcVo1() {
        return (ViewLinkImpl) findViewLink("FiliererUeSemEcVoToModeControleEcVo1");
    }

    /**
     * Container's getter for MaquetteToParcoursMaquetteAnneeVL1.
     * @return MaquetteToParcoursMaquetteAnneeVL1
     */
    public ViewLinkImpl getMaquetteToParcoursMaquetteAnneeVL1() {
        return (ViewLinkImpl) findViewLink("MaquetteToParcoursMaquetteAnneeVL1");
    }

    /**
     * Container's getter for SpectialiteToOption1.
     * @return SpectialiteToOption1
     */
    public ViewLinkImpl getSpectialiteToOption1() {
        return (ViewLinkImpl) findViewLink("SpectialiteToOption1");
    }

    /**
     * Container's getter for PersonnesToEtudiantVL1.
     * @return PersonnesToEtudiantVL1
     */
    public ViewLinkImpl getPersonnesToEtudiantVL1() {
        return (ViewLinkImpl) findViewLink("PersonnesToEtudiantVL1");
    }

    /**
     * Container's getter for StructureVOToHistoriqueStructROVOVL2.
     * @return StructureVOToHistoriqueStructROVOVL2
     */
    public ViewLinkImpl getStructureVOToHistoriqueStructROVOVL2() {
        return (ViewLinkImpl) findViewLink("StructureVOToHistoriqueStructROVOVL2");
    }

    /**
     * Container's getter for HistoriquesStructuresROToFormations2.
     * @return HistoriquesStructuresROToFormations2
     */
    public ViewLinkImpl getHistoriquesStructuresROToFormations2() {
        return (ViewLinkImpl) findViewLink("HistoriquesStructuresROToFormations2");
    }

    /**
     * Container's getter for FormationsToFormationAccesVL2.
     * @return FormationsToFormationAccesVL2
     */
    public ViewLinkImpl getFormationsToFormationAccesVL2() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationAccesVL2");
    }

    /**
     * Container's getter for FormationToFormationConditionAccesVL2.
     * @return FormationToFormationConditionAccesVL2
     */
    public ViewLinkImpl getFormationToFormationConditionAccesVL2() {
        return (ViewLinkImpl) findViewLink("FormationToFormationConditionAccesVL2");
    }

    /**
     * Container's getter for FormationsToFormationInstitutsVL2.
     * @return FormationsToFormationInstitutsVL2
     */
    public ViewLinkImpl getFormationsToFormationInstitutsVL2() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationInstitutsVL2");
    }

    /**
     * Container's getter for FormationToFormationLangueVL2.
     * @return FormationToFormationLangueVL2
     */
    public ViewLinkImpl getFormationToFormationLangueVL2() {
        return (ViewLinkImpl) findViewLink("FormationToFormationLangueVL2");
    }

    /**
     * Container's getter for FormationsToFormationOrganisationEtudeVL2.
     * @return FormationsToFormationOrganisationEtudeVL2
     */
    public ViewLinkImpl getFormationsToFormationOrganisationEtudeVL2() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationOrganisationEtudeVL2");
    }

    /**
     * Container's getter for FormationsToFormationsSpecialitesVL2.
     * @return FormationsToFormationsSpecialitesVL2
     */
    public ViewLinkImpl getFormationsToFormationsSpecialitesVL2() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationsSpecialitesVL2");
    }

    /**
     * Container's getter for FormationsToNiveauxFormationsVL2.
     * @return FormationsToNiveauxFormationsVL2
     */
    public ViewLinkImpl getFormationsToNiveauxFormationsVL2() {
        return (ViewLinkImpl) findViewLink("FormationsToNiveauxFormationsVL2");
    }

    /**
     * Container's getter for NiveauxFormationsToNiveauFormationsCohortesVL2.
     * @return NiveauxFormationsToNiveauFormationsCohortesVL2
     */
    public ViewLinkImpl getNiveauxFormationsToNiveauFormationsCohortesVL2() {
        return (ViewLinkImpl) findViewLink("NiveauxFormationsToNiveauFormationsCohortesVL2");
    }

    /**
     * Container's getter for NiveauFormationsCohortesToNiveauxFormationParcoursVL2.
     * @return NiveauFormationsCohortesToNiveauxFormationParcoursVL2
     */
    public ViewLinkImpl getNiveauFormationsCohortesToNiveauxFormationParcoursVL2() {
        return (ViewLinkImpl) findViewLink("NiveauFormationsCohortesToNiveauxFormationParcoursVL2");
    }

    /**
     * Container's getter for FormationsSpecialitesToFormationsOptionsVL2.
     * @return FormationsSpecialitesToFormationsOptionsVL2
     */
    public ViewLinkImpl getFormationsSpecialitesToFormationsOptionsVL2() {
        return (ViewLinkImpl) findViewLink("FormationsSpecialitesToFormationsOptionsVL2");
    }

    /**
     * Container's getter for StructureAccessROVOToHStructVL2.
     * @return StructureAccessROVOToHStructVL2
     */
    public ViewLinkImpl getStructureAccessROVOToHStructVL2() {
        return (ViewLinkImpl) findViewLink("StructureAccessROVOToHStructVL2");
    }

    /**
     * Container's getter for HistoriquesStructuresROToFormations3.
     * @return HistoriquesStructuresROToFormations3
     */
    public ViewLinkImpl getHistoriquesStructuresROToFormations3() {
        return (ViewLinkImpl) findViewLink("HistoriquesStructuresROToFormations3");
    }

    /**
     * Container's getter for FormationsToNiveauxFormationsVL3.
     * @return FormationsToNiveauxFormationsVL3
     */
    public ViewLinkImpl getFormationsToNiveauxFormationsVL3() {
        return (ViewLinkImpl) findViewLink("FormationsToNiveauxFormationsVL3");
    }

    /**
     * Container's getter for FormationToFormationConditionAccesVL3.
     * @return FormationToFormationConditionAccesVL3
     */
    public ViewLinkImpl getFormationToFormationConditionAccesVL3() {
        return (ViewLinkImpl) findViewLink("FormationToFormationConditionAccesVL3");
    }

    /**
     * Container's getter for FormationsToFormationInstitutsVL3.
     * @return FormationsToFormationInstitutsVL3
     */
    public ViewLinkImpl getFormationsToFormationInstitutsVL3() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationInstitutsVL3");
    }

    /**
     * Container's getter for FormationToFormationLangueVL3.
     * @return FormationToFormationLangueVL3
     */
    public ViewLinkImpl getFormationToFormationLangueVL3() {
        return (ViewLinkImpl) findViewLink("FormationToFormationLangueVL3");
    }

    /**
     * Container's getter for FormationsToFormationOrganisationEtudeVL3.
     * @return FormationsToFormationOrganisationEtudeVL3
     */
    public ViewLinkImpl getFormationsToFormationOrganisationEtudeVL3() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationOrganisationEtudeVL3");
    }

    /**
     * Container's getter for FormationsToFormationsSpecialitesVL3.
     * @return FormationsToFormationsSpecialitesVL3
     */
    public ViewLinkImpl getFormationsToFormationsSpecialitesVL3() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationsSpecialitesVL3");
    }

    /**
     * Container's getter for FormationsSpecialitesToFormationsOptionsVL3.
     * @return FormationsSpecialitesToFormationsOptionsVL3
     */
    public ViewLinkImpl getFormationsSpecialitesToFormationsOptionsVL3() {
        return (ViewLinkImpl) findViewLink("FormationsSpecialitesToFormationsOptionsVL3");
    }

    /**
     * Container's getter for NiveauxFormationsToNiveauFormationsCohortesVL3.
     * @return NiveauxFormationsToNiveauFormationsCohortesVL3
     */
    public ViewLinkImpl getNiveauxFormationsToNiveauFormationsCohortesVL3() {
        return (ViewLinkImpl) findViewLink("NiveauxFormationsToNiveauFormationsCohortesVL3");
    }

    /**
     * Container's getter for NiveauFormationsCohortesToNiveauxFormationParcoursVL3.
     * @return NiveauFormationsCohortesToNiveauxFormationParcoursVL3
     */
    public ViewLinkImpl getNiveauFormationsCohortesToNiveauxFormationParcoursVL3() {
        return (ViewLinkImpl) findViewLink("NiveauFormationsCohortesToNiveauxFormationParcoursVL3");
    }

    /**
     * Container's getter for FormationsToFormationAccesVL3.
     * @return FormationsToFormationAccesVL3
     */
    public ViewLinkImpl getFormationsToFormationAccesVL3() {
        return (ViewLinkImpl) findViewLink("FormationsToFormationAccesVL3");
    }

    /**
     * Container's getter for NivFormRoToNivFormPrcrsRoVL1.
     * @return NivFormRoToNivFormPrcrsRoVL1
     */
    public ViewLinkImpl getNivFormRoToNivFormPrcrsRoVL1() {
        return (ViewLinkImpl) findViewLink("NivFormRoToNivFormPrcrsRoVL1");
    }

    public List<Long> getSelectedParcours() {
        List<Long> parcourslist = new ArrayList<>();
        ViewObject vo = getNivFormParcoursRO();
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
    
    public List<Long> getSelectedRole() {
        List<Long> roleslist = new ArrayList<>();
        ViewObject vo = getRoleAAssigner();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isRoleSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                roleslist.add(Long.parseLong(rw.getAttribute("IdRoles").toString()));
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
    
    public List<Long> getSelectedNiveaux() {
        List<Long> niveauxlist = new ArrayList<>();
        ViewObject vo = getScolNivFormNotIntegrated();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isNivSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                niveauxlist.add(Long.parseLong(rw.getAttribute("CodeNivSec").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return niveauxlist;
    }
    
    public List<NiveauSection> getSelectedNiveauSections(){
        List<NiveauSection> niveauxlist = new ArrayList<>();
        ViewObject vo = getScolNivFormNotIntegrated();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isNivSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                NiveauSection ns = new NiveauSection();
                ns.setCode_niv_sec(rw.getAttribute("CodeNivSec").toString());
                ns.setCode_fr(rw.getAttribute("CodeFormation").toString());
                ns.setGrade(rw.getAttribute("CodeGradeDiplome").toString());
                ns.setActif(rw.getAttribute("Actif") != null ? rw.getAttribute("Actif").toString() : "0");
                ns.setCrt_code(null != rw.getAttribute("Cohorte") ? rw.getAttribute("Cohorte").toString(): null);
                ns.setNbreEtudiant(null != rw.getAttribute("NombreEtudiant") ? rw.getAttribute("NombreEtudiant").toString() : "0");
                ns.setSpec(null != rw.getAttribute("IdSpecialite") ? rw.getAttribute("IdSpecialite").toString() : null);
                ns.setOpt(null != rw.getAttribute("IdOption") ? rw.getAttribute("IdOption").toString() : null);
                ns.setNiveau(rw.getAttribute("NiveauCode").toString());
                ns.setEmail(null != rw.getAttribute("Emailucad") ? rw.getAttribute("Emailucad").toString() : null);
                ns.setSrt(null != rw.getAttribute("Etablissement") ? rw.getAttribute("Etablissement").toString() : null);
                ns.setMatricule(null != rw.getAttribute("CodeMatricule") ? rw.getAttribute("CodeMatricule").toString() : null);
                niveauxlist.add(ns);
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return niveauxlist;
    }
    
    public List<ScolParcours> getSelectedScolParcours(){
        List<ScolParcours> prcrslist = new ArrayList<>();
        ViewObject vo = getScolNivFormPrcrsNotIntegrated();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isNivSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                ScolParcours ns = new ScolParcours();
                ns.setCode_niv_sec(rw.getAttribute("CodeNivSec").toString());
                ns.setCode_fr(rw.getAttribute("CodeFormation").toString());
                ns.setGrade(rw.getAttribute("CodeGradeDiplome").toString());
                ns.setActif(rw.getAttribute("Actif") != null ? rw.getAttribute("Actif").toString() : "0");
                ns.setCrt_code(null != rw.getAttribute("Cohorte") ? rw.getAttribute("Cohorte").toString(): null);
                ns.setNbreEtudiant(null != rw.getAttribute("NombreEtudiant") ? rw.getAttribute("NombreEtudiant").toString() : "0");
                ns.setSpec(null != rw.getAttribute("IdSpecialite") ? rw.getAttribute("IdSpecialite").toString() : null);
                ns.setOpt(null != rw.getAttribute("IdOption") ? rw.getAttribute("IdOption").toString() : null);
                ns.setNiveau(rw.getAttribute("NiveauCode").toString());
                ns.setEmail(null != rw.getAttribute("Emailucad") ? rw.getAttribute("Emailucad").toString() : null);
                ns.setSrt(null != rw.getAttribute("Etablissement") ? rw.getAttribute("Etablissement").toString() : null);
                ns.setMatricule(null != rw.getAttribute("CodeMatricule") ? rw.getAttribute("CodeMatricule").toString() : null);
                ns.setScolpot(null != rw.getAttribute("CodeOption") ? rw.getAttribute("CodeOption").toString() : null);
                ns.setScolspec(null != rw.getAttribute("CodeSpecialiteDiplome") ? rw.getAttribute("CodeSpecialiteDiplome").toString() : null);
                prcrslist.add(ns);
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return prcrslist;
    }
    
    public HashMap<String,String> getSelectedFilUe() {
        HashMap<String,String> filuelist = new HashMap<>();
        ViewObject vo = getFiliereUeSemestreInsUeOpt();
        RowSet duplicateRowSet = vo.createRowSet("duplicateRowSet");
        duplicateRowSet.first();
        Row currentRow = vo.getCurrentRow();
        boolean currentRowAdded = false;
        Row[] rowsToAdd = duplicateRowSet.getFilteredRows("isFilUeSelected", true);
        if (rowsToAdd.length > 0) {
            for (Row rw : rowsToAdd) {
                if (rw.getKey().equals(currentRow.getKey())) {
                    currentRowAdded = true;
                }
                filuelist.put(rw.getAttribute("IdFiliereUeSemstre").toString(),
                              rw.getAttribute("IdSemestre").toString());
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
    
    public List<Long> getSelectedEtudiant() {
        List<Long> etudiantlist = new ArrayList<>();
        ViewObject vo = getEtudiantInsUeOpt();
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
                etudiantlist.add(Long.parseLong(rw.getAttribute("IdInscriptionPedagogique").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return etudiantlist;
    }
    
    public List<Long> getSelecteduser() {
        List<Long> userlist = new ArrayList<>();
        ViewObject vo = getUtilisateursROVO();
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
                userlist.add(Long.parseLong(rw.getAttribute("IdUtilisateur").toString()));
            }
            vo.executeQuery();
            if (!currentRowAdded) {
                vo.setCurrentRow(currentRow);
            }
            duplicateRowSet.closeRowSet();
        }
        duplicateRowSet.closeRowSet();
        return userlist;
    }
    
    /*getParcoursMaquetteAnnee(parcours_id PARCOURS_MAQUETTE_ANNEE.ID_NIVEAU_FORMATION_PARCOURS%TYPE, annee PARCOURS_MAQUETTE_ANNEE.ID_ANNEES_UNIVERS%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long getParcoursMaquetteAnnee(Long parcours, Long annee) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getParcoursMaquetteAnnee(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, annee);
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
    public Long getNiveau(String anc_code) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getNiveau(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
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
    public Long getUserRespFr(Long fr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserRespFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fr_id);
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
    public Long getUserRespNivFr(Long niv_fr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserRespNivFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_fr_id);
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
//getUserRespFilUe(fil_ue_id UTILIS_FILIERE_UE_SEMESTRE.ID_FILIERE_UE_SEMSTRE%TYPE, pma_id PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long getUserRespFilUe(Long fil_ue_id, Long pma_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserRespFilUe(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue_id);
            cls.setLong(3, pma_id);
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
    public Long getUserRespFilEc(Long fil_ec_id, Long pma_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserRespFilEc(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ec_id);
            cls.setLong(3, pma_id);
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
    public Long getResponsableFr(Long fr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getResponsableFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fr_id);
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

/*UpdateResponsableFr(p_old UTILISATEUR_FORMATIONS.ID_UTILISATEUR%TYPE, p_new UTILISATEUR_FORMATIONS.ID_UTILISATEUR%TYPE,
  p_fr UTILISATEUR_FORMATIONS.ID_FORMATION%TYPE, p_user UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long UpdateResponsableFr(Long p_old, Long p_new, Long p_fr, Integer p_user) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.UpdateResponsableFr(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, p_old);
            cls.setLong(3, p_new);
            cls.setLong(4, p_fr);
            cls.setInt(5, p_user);
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

    /*Function UpdateResponsableNivFr(p_old UTILISATEUR_NIVEAUX_FORMATIONS.ID_UTILISATEUR%TYPE, p_new UTILISATEUR_NIVEAUX_FORMATIONS.ID_UTILISATEUR%TYPE,
      p_niv_fr UTILISATEUR_NIVEAUX_FORMATIONS.ID_NIVEAU_FORMATION%TYPE, p_user UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long UpdateResponsableNivFr(Long p_old, Long p_new, Long p_niv_fr, Integer p_user) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.UpdateResponsableNivFr(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, p_old);
            cls.setLong(3, p_new);
            cls.setLong(4, p_niv_fr);
            cls.setInt(5, p_user);
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
    public Long getResponsableFilUe(Long fil_ue_id,Long pma) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getResponsableFilUe(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ue_id);
            cls.setLong(3, pma);
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
    public Long getResponsableFilEc(Long fil_ec_id,Long pma) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getResponsableFilEc(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ec_id);
            cls.setLong(3, pma);
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
    public Long getUserResponsableFr(Long u_fr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserResponsableFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, u_fr_id);
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
    public Long getUserResponsableFilEc(Long u_fil_ec_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserResponsableFilEc(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, u_fil_ec_id);
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
    public Long getUserResponsableFilUe(Long u_fil_ue_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserResponsableFilUe(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, u_fil_ue_id);
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
    public Long getUserResponsableNivFr(Long u_nfr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUserResponsableNivFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, u_nfr_id);
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
    public Long getResponsableNvFr(Long niv_fr_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getResponsableNivFr(?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_fr_id);
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

    /*
     * getInsPedSem(ins_ped INSCRIPTIONS_PEDAGOGIQUES.ID_INSCRIPTION_PEDAGOGIQUE%TYPE,sem_id INSCRIPTION_PED_SEMESTRE.ID_SEMESTRE%TYPE)
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long getInspedSem(Long ins_ped, Long sem_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getInsPedSem(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ins_ped);
            cls.setLong(3, sem_id);
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
    
    /*IsMaquetteValidated(niv_fr_parcrs NIVEAUX_FORMATION_PARCOURS.ID_NIVEAU_FORMATION_PARCOURS%TYPE,
                                annee ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long IsMaquetteValidated(Long prcrs, Long an_id,String  mq_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.IsMaquetteValidated(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs);
            cls.setLong(3, an_id);
            cls.setString(4, mq_id);
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
    public Integer IsOneInterClosed(Long prcrs, Long an_id,String  mq_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.is_closed_one_inter(?,?,?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs);
            cls.setLong(3, an_id);
            cls.setString(4, mq_id);
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
    
    /*ValidateMaquette(maquette MAQUETTEVALIDEE.ID_MAQUETTE%TYPE, annee MAQUETTEVALIDEE.ID_ANNEE%TYPE, 
                                                utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer ValidateMaquette(Long maq_id, Long an_id, Long parcours, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.ValidateMaquette(?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, maq_id);
            cls.setLong(3, an_id);
            cls.setLong(4, parcours);
            cls.setLong(5, utilisateur);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
                                                                                           
    /*CreateOrUpdateUserRole( user_ UTILISATEURS.ID_UTILISATEUR%TYPE,role_ UTILISATEURS_ROLES.ID_ROLE%TYPE,
                                      utilisateur UTILIS_FILIERE_UE_SEMESTRE_EC.UTI_CREE%TYPE ) Return UTILISATEURS_ROLES.ID_UTILISATEUR%TYPE*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer CreateOrUpdateUserRole(Long user_id, Long role_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUserRole(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_id);
            cls.setLong(3, role_id);
            cls.setLong(4, utilisateur);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    
    /*getusername(user_id UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public String getusername(Long user_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getusername(?); end ;";
        String result = null;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_id);
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
    
    /*getrole(role_id ROLES.ID_ROLE%TYPE) */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public String getrole(Long role_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getrole(?); end ;";
        String result = null;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, role_id);
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
    
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer getUtiRoles(String username) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getUtiRoles(?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, username);
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
    
    /*CreateOrUpdateCalendar(niv_crt_id NIVEAU_FORMATION_COHORTES.ID_NIVEAU_FORMATION_COHORTE%TYPE,sem_id SEMESTRES.ID_SEMESTRE%TYPE,an_id ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE
                                      ,utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer CreateOrUpdateCalendar(Long niv_crt_id, Long sem_id, Long an_id, Integer utilisateur, Integer sess_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateCalendar(?,?,?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_crt_id);
            cls.setLong(3, sem_id);
            cls.setLong(4, an_id);
            cls.setInt(5, utilisateur);
            cls.setInt(6, sess_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Integer isCalendrierExist(Long niv_crt_id, Long sem_id, Long an_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.isCalendrierExist(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_crt_id);
            cls.setLong(3, sem_id);
            cls.setLong(4, an_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Long getgradeFormation(String anc_code) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getGradeFormation(?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
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

    /*CreateOrUpdateFormation(anc_code FORMATIONS.ANCIEN_CODE%TYPE, libelle FORMATIONS.LIBELLE_LONG%TYPE,departement HISTORIQUES_STRUCTURES.ANCIEN_CODE%TYPE,
                                      type_fr FORMATIONS.ANCIEN_CODE%TYPE,  payante FORMATIONS.ANCIEN_CODE%TYPE,  presentielle FORMATIONS.ANCIEN_CODE%TYPE,
                                      grade_fr GRADES_FORMATION.ANCIEN_CODE%TYPE , mention MENTIONS.ANCIEN_CODE%TYPE,ouvert FORMATIONS.ANCIEN_CODE%TYPE,
                                      valide FORMATIONS.ANCIEN_CODE%TYPE, professionalisante FORMATIONS.ANCIEN_CODE%TYPE,cycl FORMATIONS.ANCIEN_CODE%TYPE,
                                      reconnue FORMATIONS.ANCIEN_CODE%TYPE,obs FORMATIONS.OBSERVATIONS%TYPE, spec FORMATIONS.ANCIEN_CODE%TYPE,
                                      opt FORMATIONS.ANCIEN_CODE%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long integrateForm(String anc_code, String libelle, String departement, String type_fr, String payante,
                              String presentielle, String grade_fr, String mention, String ouvert, String valide,
                              String professionalisante, String cycl, String reconnue, String obs, String spec,
                              String opt, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql =
            "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateFormation(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, libelle);
            cls.setString(4, departement);
            cls.setString(5, type_fr);
            cls.setString(6, payante);
            cls.setString(7, presentielle);
            cls.setString(8, grade_fr);
            cls.setString(9, mention);
            cls.setString(10, ouvert);
            cls.setString(11, valide);
            cls.setString(12, professionalisante);
            cls.setString(13, cycl);
            cls.setString(14, reconnue);
            cls.setString(15, obs);
            cls.setString(16, spec);
            cls.setString(17, opt);
            cls.setInt(18, utilisateur);
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
    /*
    CreateOrUpdateNivFormation(anc_code NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE ,libelle NIVEAUX_FORMATIONS.LIBELLE_LONG%TYPE,abrev NIVEAUX_FORMATIONS.LIBELLE_COURT%TYPE,
                                          a_select NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,aut_permise NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,presentielle NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                          nbr_insc_permis NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,diplomante NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,formation FORMATIONS.ANCIEN_CODE%TYPE,
                                          ouvert NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,niveau NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )
 */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long integrateNiveauForm(String anc_code, String libelle, String abrev, String a_select, String aut_permise,
                                    String presentielle, String nbr_insc_permis, String diplomante, String formation,
                                    String ouvert, String niveau, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormation(?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, libelle);
            cls.setString(4, abrev);
            cls.setString(5, a_select);
            cls.setString(6, aut_permise);
            cls.setString(7, presentielle);
            cls.setString(8, nbr_insc_permis);
            cls.setString(9, diplomante);
            cls.setString(10, formation);
            cls.setString(11, ouvert);
            cls.setString(12, niveau);
            cls.setInt(13, utilisateur);
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

    /*CreateOrUpdateNivFormCohrte(anc_code NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE ,
                                            crt_code NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                            actif NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                            nbr_etd NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                            utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )
*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long integrateNiveauFormCrt(Long niv_fr, String crt_code, String actif, String nbr_etd,
                                       Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormCohrte(?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_fr);
            cls.setString(3, crt_code);
            cls.setString(4, actif);
            cls.setString(5, nbr_etd);
            cls.setInt(6, utilisateur);
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
    public Long integrateNiveauFormSpec(Long niv_fr, String spec_code, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormSpec(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_fr);
            cls.setString(3, spec_code);
            cls.setInt(4, utilisateur);
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
    public Long integrateNiveauFormOpt(Long niv_fr, Long niv_fr_spec, String opt_code, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormOpt(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_fr);
            cls.setLong(3, niv_fr_spec);
            cls.setString(4, opt_code);
            cls.setInt(5, utilisateur);
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


    /*CreateOrUpdateNivFormPrcrs(anc_code FORMATIONS.ANCIEN_CODE%TYPE,
                                            niv_form_crt NIVEAU_FORMATION_COHORTES.ID_NIVEAU_FORMATION_COHORTE%TYPE,
                                            spec NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                            opt NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                            utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )
*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long integrateNiveauFormPrcrs(Long fr_id, Long niv_form_crt, String spec, String opt, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormPrcrs(?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fr_id);
            cls.setLong(3, niv_form_crt);
            cls.setString(4, spec);
            cls.setString(5, opt);
            cls.setInt(6, utilisateur);
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
    public Long integrateNivFormPrcrs(Long niv_form_crt, Long niv_form_spec, Long niv_form_opt, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormPrcrs(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_form_crt);
            cls.setLong(3, niv_form_spec);
            cls.setLong(4, niv_form_opt);
            cls.setInt(5, utilisateur);
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
    /*Function IsAncMaquetteExiste(annee ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE, code_niv NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE,
                                code_fr FORMATIONS.ANCIEN_CODE%TYPE)  Return NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE
          IS */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long isAncMaquetteExiste(Long annee, String code_niv_sec ,String code_fr) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.IsAncMaquetteExiste(?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, annee);
            cls.setString(3, code_niv_sec);
            cls.setString(4, code_fr);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
            cls.executeUpdate();
            result = cls.getLong(1);
            //System.out.println("result_mq : "+result);
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
    public Long createUserDet(Long deptId, Long userId, String role, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.createOrUpdateUserDept(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, deptId);
            cls.setLong(3, userId);
            cls.setString(4, role);
            cls.setInt(5, utilisateur);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
            cls.executeUpdate();
            result = cls.getLong(1);
            //System.out.println("result_mq : "+result);
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
    
    //AllowAccesTo(utilisateur UTILISATEURS.UTI_CREE%TYPE
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void AllowAccesFr(Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.AllowAccesToFr(?);" + " END;"), 0);
        try {
            statement.setLong(1, utilisateur);
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
    public void AllowAccesNFr(Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.AllowAccesToNFr(?);" + " END;"), 0);
        try {
            statement.setLong(1, utilisateur);
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
     * Function CreateOrUpdateMaquette(niv_fr_parcrs NIVEAUX_FORMATION_PARCOURS.ID_NIVEAU_FORMATION_PARCOURS%TYPE,
     * str_id MAQUETTES.ID_STRUCTURE%TYPE, 
        annee ANNEES_UNIVERS.ID_ANNEES_UNIVERS%TYPE, 
        code_niv NIVEAUX_FORMATIONS.ANCIEN_CODE%TYPE, 
        op_code OPTIONNIVSECSCOL.CODE%TYPE,
        code_fr FORMATIONS.ANCIEN_CODE%TYPE,utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)   Return MAQUETTES.ID_MAQUETTE%TYPE
     * */  
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long generateMaquette(Long niv_form_prcrt, Long str_id, Long annee_id, String code_niv_sec, String op_code, String code_fr,Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.CreateOrUpdateMaquette(?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_form_prcrt);
            cls.setLong(3, str_id);
            cls.setLong(4, annee_id);
            cls.setString(5, code_niv_sec);
            cls.setString(6, op_code);
            cls.setString(7, code_fr);
            cls.setInt(8, utilisateur);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
            cls.executeUpdate();
            result = cls.getLong(1);
            //System.out.println("result_mq : "+result);
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
//test_package.return_table
    public void testType(){
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  test_package.return_table(); end ;";
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.registerOutParameter(1, OracleTypes.ARRAY, "SCOL_REFONTE.T_TABLE");
            cls.execute();
            /*System.out.println("getFetchSize : "+cls.getFetchSize());
            System.out.println("cls.getArray(1) : "+cls.getArray(1));
            System.out.println("cls.getArray(1).getClass() : "+cls.getArray(1).getClass());*/
            /*for (int i=0; i<2; i++){
                System.out.println("");
            }*/
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
    public List<Long> generateMaquetteRec(Long niv_form_prcrt,Long str_id, Long annee_id, String code_niv_sec , String op_code, String fr_code ,Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.CreateOrUpdateMaquetteRec(?,?,?,?,?,?); end ;";
        List<Long> maquettelist = new ArrayList<>();
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_form_prcrt);
            cls.setLong(3, str_id);
            cls.setLong(4, annee_id);
            cls.setString(5, code_niv_sec);
            cls.setString(6, op_code);
            cls.setString(7, op_code);
            cls.setInt(8, utilisateur);
            cls.registerOutParameter(1, OracleTypes.STRUCT, "t_table_maquette");
            cls.executeUpdate();
            //for (int i=0; i<)
            //code = (Integer) transformRValidationToDomain((java.sql.Struct) statement.getObject(1)).getAttribute(0);
            //result = cls.getLong(1);
            //System.out.println("result_mq : "+result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return maquettelist;
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
        return maquettelist;
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateNivFormMaqAnn(Long niv_form, Long mq, Long an, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdateNivFormMaqAnn(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_form);
            cls.setLong(3, mq);
            cls.setLong(4, an);
            cls.setInt(5, utilisateur);
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
    public Long createOrUpdatePrcrsMaqAnn(Long parcours, Long mq, Long an, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdatePrcrsMaqAnn(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, parcours);
            cls.setLong(3, mq);
            cls.setLong(4, an);
            cls.setInt(5, utilisateur);
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

    /*FUNCTION createOrUpdateUe(anc_code UE.ANCIEN_CODE%TYPE, libelle UE.LIBELLE_LONG%TYPE,prefix UE.PREFIXE%TYPE,com UE.COMMENTAIRE%TYPE ,
                                  utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE) Return UE.ID_UE%TYPE*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUe(String anc_code, String libelle, String prefix, String com, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdateUe(?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, libelle);
            cls.setString(4, prefix);
            cls.setString(5, com);
            cls.setInt(6, utilisateur);
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
    public Long createOrUpdateUeExcel(String libelle, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.createOrUpdateUe(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, libelle);
            cls.setInt(3, utilisateur);
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
    public Long createOrUpdateFilUe(String anc_code, Long ue_id, Long sem_id, String cat_ue_id, String codif, Long cred,
                                    Float coef, Long maq, Integer moy_v, Integer moy_e, String nat, String comp, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdateFilUe(?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setLong(3, ue_id);
            cls.setLong(4, sem_id);
            cls.setString(5, cat_ue_id);
            cls.setString(6, codif);
            cls.setLong(7, cred);
            cls.setFloat(8, coef);
            cls.setLong(9, maq);
            cls.setInt(10, moy_v);
            cls.setInt(11, moy_e);
            cls.setString(12, nat);
            cls.setString(13, comp);
            cls.setInt(14, utilisateur);
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
    public Long createOrUpdateFilUeExcel(Long ue_id, Long sem_id, Long cat_ue_id, String codif, Long cred,
                                    Float coef, Long maq, Integer moy_v, Integer moy_e, Long nat, Integer comp,Integer is_sout, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.createOrUpdateFilUe(?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ue_id);
            cls.setLong(3, sem_id);
            cls.setLong(4, cat_ue_id);
            cls.setString(5, codif);
            cls.setLong(6, cred);
            cls.setFloat(7, coef);
            cls.setLong(8, maq);
            cls.setInt(9, moy_v);
            cls.setInt(10, moy_e);
            cls.setLong(11, nat);
            cls.setInt(12, comp);
            cls.setInt(13, is_sout);
            cls.setInt(14, utilisateur);
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


    /*createOrUpdateEc(anc_code EC.ANCIEN_CODE%TYPE,libelle EC.LIBELLE_LONG%TYPE, prefix EC.PREFIXE%TYPE, com EC.COMMENTAIRE%TYPE,
                                utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE) Return EC.ID_EC%TYPE*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateEc(String anc_code, String libelle, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdateEc(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, libelle);
            //cls.setString(4, prefix);
            //cls.setString(5, com);
            cls.setInt(4, utilisateur);
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
    public Long createOrUpdateEcExcel(String libelle, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.createOrUpdateEc(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, libelle);
            cls.setInt(3, utilisateur);
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
    public Long createOrUpdateFilEc(String anc_code, Long ec_id, String nat, String codif, Float coef, Float moy_v,
                                    Float moy_e, Float cc, Float ct, Long fus, String hcm, String htd,
                                    String htp, String htpe, String hs, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.createOrUpdateFilEc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setLong(3, ec_id);
            cls.setString(4, nat);
            cls.setString(5, codif);
            cls.setFloat(6, coef);
            cls.setFloat(7, moy_v);
            cls.setFloat(8, moy_e);
            cls.setFloat(9, cc);
            cls.setFloat(10, ct);
            cls.setLong(11, fus);
            cls.setString(12, hcm);
            cls.setString(13, htd);
            cls.setString(14, htp);
            cls.setString(15, htpe);
            cls.setString(16, hs);
            cls.setInt(17, utilisateur);
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
    public Long createOrUpdateFilEcExcel(Long ec_id, Long nat, String codif, Float coef, Float moy_v,
                                    Float moy_e, Float cc, Float ct, Long fus, String hcm, String htd,
                                    String htp, String htpe, String hs, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.createOrUpdateFilEc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ec_id);
            cls.setLong(3, nat);
            cls.setString(4, codif);
            cls.setFloat(5, coef);
            cls.setFloat(6, moy_v);
            cls.setFloat(7, moy_e);
            cls.setFloat(8, cc);
            cls.setFloat(9, ct);
            cls.setLong(10, fus);
            cls.setString(11, hcm);
            cls.setString(12, htd);
            cls.setString(13, htp);
            cls.setString(14, htpe);
            cls.setString(15, hs);
            cls.setInt(16, utilisateur);
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
    /*CreateOrUpdateMaqExcel(libelle MAQUETTES.LIBELLE_LONG%TYPE, str_id MAQUETTES.ID_STRUCTURE%TYPE,
                                    utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateMaqExcel(String libelle, Long str, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.CreateOrUpdateMaqExcel(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, libelle);
            cls.setLong(3, str);
            cls.setInt(4, utilisateur);
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
    
    /*CreateOrUpdateUtilisateur(  matricule PERSONNELS.MATRICULE%TYPE, struct_ UTILISATEURS.ID_STRUCTURE%TYPE,
                                        utilisateur UTILISATEURS.UTI_CREE%TYPE) Return UTILISATEURS.ID_UTILISATEUR%TYPE;*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUser(String email, String struct_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtilisateur(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, email);
            cls.setString(3, struct_);
            cls.setInt(4, utilisateur);
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
    
    /*CreateOrUpdateUtilisateur( email_ PERSONNES.EMAIL_INSTITUTIONNEL%TYPE, str_id STRUCTURES.ID_STRUCTURE%TYPE,
                                        p_id PERSONNES.ID_PERSONNE%TYPE,utilisateur UTILISATEURS.UTI_CREE%TYPE ) 
                                      Return UTILISATEURS.ID_UTILISATEUR%TYPE*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createUser(String login, Long str_id, Long p_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.CreateOrUpdateUtilisateur(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, login);
            cls.setLong(3, str_id);
            cls.setLong(4, p_id);
            cls.setInt(5, utilisateur);
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
    /*                         opgs.getParamsMap().put("", currentPrcrs.getAttribute("IdNiveau"));
                        opgs.getParamsMap().put("", currentPrcrs.getAttribute("IdGradesFormation"));
                        opgs.getParamsMap().put("", sem_id);
                        opgs.getParamsMap().put("", getUtilisateur());
    (niv_id GRADE_SEMESTRE.ID_NIVEAU%TYPE, sem_id GRADE_SEMESTRE.ID_SEMESTRE%TYPE, grd_id GRADE_SEMESTRE.ID_GRADE%TYPE,
                                        utilisateur PERSONNES.UTI_CREE%TYPE )
*/
    public Long CreateOrUpdateGrdSemestre(Long niv_id, Long sem_id ,Long gf_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateGradeSemestre(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, niv_id);
            cls.setLong(3, sem_id);
            cls.setLong(4, gf_id);
            cls.setInt(5, utilisateur);
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
    public Long createOrUpdateEtudiant(String num_etd, String num_table, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateEtudiant(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, num_etd);
            cls.setString(3, num_table);
            cls.setInt(4, utilisateur);
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
    /*
     * CreateOrUpdateInsAdmin( etd_id INSCRIPTIONS_ADMIN.ID_ETUDIANT%TYPE,type_fr_anc_code TYPE_FORMATION.ANCIEN_CODE%TYPE, grade_fr_anc_code GRADES_FORMATION.ANCIEN_CODE%TYPE,
                                    annee_univ INSCRIPTIONS_ADMIN.ID_ANNEES_UNIVERS%TYPE, ins_en_ligne INSCRIPTIONS_ADMIN.INSC_LIGNE%TYPE ,utilisateur ETUDIANTS.UTI_CREE%TYPE) */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateInsAdmin(Long etu_id, String tfr_anc_code, String gfr_anc_code, Long an_id, Integer ins_en_ligne, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateInsAdmin(?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, etu_id);
            cls.setString(3, tfr_anc_code);
            cls.setString(4, gfr_anc_code);
            cls.setLong(5, an_id);
            cls.setInt(6, ins_en_ligne);
            cls.setInt(7, utilisateur);
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
    public Integer getCountFilEc(Long an_id, Long prcrs_id, Long sem_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getCounterFilEc(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, an_id);
            cls.setLong(3, prcrs_id);
            cls.setLong(4, sem_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Integer getCountFilEcMaq(Long prcrs_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getCountFilEcMaq(?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Integer getCountFilUe(Long prcrs_id, Long sem_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getCounterFilUe(?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_id);
            cls.setLong(3, sem_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Integer getCountFilUeMaq(Long prcrs_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.getCountFilUeMaq(?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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
    public Integer isCCExiste(Long prcrs_id) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.isCCExiste(?); end ;";
        Integer result = 0;
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs_id);
            cls.registerOutParameter(1, Types.LONGNVARCHAR);
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

    /*CreateOrUpdateInsPed( ins_adm_id INSCRIPTIONS_ADMIN.ID_INSCRIPTION_ADMIN%TYPE, h_td HORAIRES_TD.ANCIEN_CODE%TYPE, stat STATUTS.ANCIEN_CODE%TYPE,opt OPTIONS.ANCIEN_CODE%Type,
                                  crt COHORTES.ANCIEN_CODE%TYPE, motif INSCRIPTIONS_PEDAGOGIQUES.MOTIF%TYPE, rdblmt INSCRIPTIONS_PEDAGOGIQUES.REDOUBLEMENT%TYPE,
                                  etat_ins ETATS_INSCRIPTION.ANCIEN_CODE%TYPE, quitance INSCRIPTIONS_PEDAGOGIQUES.QUITTANCE%TYPE,
                                  nbr_insc INSCRIPTIONS_PEDAGOGIQUES.NB_INSC%TYPE, pm_dl_insc INSCRIPTIONS_PEDAGOGIQUES.PERMET_DOUBLE_INSCRIPTION%TYPE,
                                  crt_tire INSCRIPTIONS_PEDAGOGIQUES.CARTETIREE%TYPE, ord_inc INSCRIPTIONS_PEDAGOGIQUES.ORDRE_INSCRIPTION%TYPE,
                                  cde_a_cons INSCRIPTIONS_PEDAGOGIQUES.CODE_A_CONSERVER%TYPE, dte_edi_crt INSCRIPTIONS_PEDAGOGIQUES.DATE_EDITION_CARTE%TYPE, prcrs_mq_an INSCRIPTIONS_PEDAGOGIQUES.ID_PARCOURS_MAQUET_ANNEE%TYPE,
                                  ins_en_lign INSCRIPTIONS_PEDAGOGIQUES.INS_EN_LIGNE%TYPE, utilisateur INSCRIPTIONS_PEDAGOGIQUES.UTI_CREE%TYPE )
     *
     * CreateOrUpdateInsPed(21, etd.horaire_td_code, etd.statut_code, etd.code_option, etd.cohorte, etd.motif, etd.redoublement, etd.etat_inscription, etd.quittance,
    etd.nb_insc, etd.permet_double_inscription,etd.cartetiree, etd.ordre_inscription, etd.code_a_conserver, etd.date_edition_carte,1, etd.ins_en_ligne, 85)
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateInsPed(Long ins_ad_id, String h_td, String stat, String opt, String crt, String motif, String rdblmt, String etat_ins, 
                                     String quitance, String nbr_insc, String pm_dl_insc, String crt_tire, String ord_inc, String cde_a_cons, 
                                     String dte_edi_crt, Long prcrs_mq_an, String ins_en_ligne, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateInsPed(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ins_ad_id);
            cls.setString(3, h_td);
            cls.setString(4, stat);
            cls.setString(5, opt);
            cls.setString(6, crt);
            cls.setString(7, motif);
            cls.setString(8, rdblmt);
            cls.setString(9, etat_ins);
            cls.setString(10, quitance);
            cls.setString(11, nbr_insc);
            cls.setString(12, pm_dl_insc);
            cls.setString(13, crt_tire);
            cls.setString(14, ord_inc);
            cls.setString(15, cde_a_cons);
            cls.setString(16, dte_edi_crt);
            cls.setLong(17, prcrs_mq_an);
            cls.setString(18, ins_en_ligne);
            cls.setInt(19, utilisateur);
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
    /*CreateOrUpdateInsPedSem(  ins_ped INSCRIPTIONS_PEDAGOGIQUES.ID_INSCRIPTION_PEDAGOGIQUE%TYPE,sem_id INSCRIPTION_PED_SEMESTRE.ID_SEMESTRE%TYPE,
                                    ins_pd_valide INSCRIPTION_PED_SEMESTRE.INS_PED_VALIDE%TYPE ,pass_exam INSCRIPTION_PED_SEMESTRE.PASS_EXAM%TYPE ,
                                    cumul_cred INSCRIPTION_PED_SEMESTRE.CUMUL_CREDIT%TYPE ,dette_cred INSCRIPTION_PED_SEMESTRE.DETTE_CREDIT%TYPE,
                                      utilisateur INSCRIPTION_PED_SEMESTRE.UTI_CREE%TYPE)
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateInsPedSem(Long ins_ped, Long sem_id, String ins_pd_valide, String pass_exam, String cumul_cred, String dette_cred, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateInsPedSem(?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ins_ped);
            cls.setLong(3, sem_id);
            cls.setString(4, ins_pd_valide);
            cls.setString(5, pass_exam);
            cls.setString(6, cumul_cred);
            cls.setString(7, dette_cred);
            cls.setInt(8, utilisateur);
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
    
    /*CreateOrUpdateInsPedSemUe( ins_ped_sem_id INSCRIPTION_PED_SEMESTRE.ID_INSCRIPTION_PED_SEMESTRE%TYPE,fil_ue_id INSCRIPTION_PED_SEM_UE.ID_FILIERE_UE_SEMSTRE%TYPE,
                                      utilisateur INSCRIPTION_PED_SEM_UE.UTI_CREE%TYPE)
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateInsPedSemUe(Long ins_ped_sem_id, Long fil_ue_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateInsPedSemUe(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, ins_ped_sem_id);
            cls.setLong(3, fil_ue_id);
            cls.setInt(4, utilisateur);
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
    
    /*CreateOrUpdateUtiForm( user_ UTILISATEURS.ID_UTILISATEUR%TYPE,role_ UTILISATEUR_FORMATIONS.ROLE%TYPE,form_ UTILISATEUR_FORMATIONS.ID_FORMATION%TYPE,
                                      utilisateur UTILISATEUR_FORMATIONS.UTI_CREE%TYPE )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUserForm(Long user_, String role, Long form_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        //SYNCHRONISATIONSCOL.CreateOrUpdateUtiForm( 184,'ACCES SIMPLE',35,85 )
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiForm(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, form_);
            cls.setInt(5, utilisateur);
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
    public Long createOrUpdateUserForm1(Long user_, String role, Long form_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        //SYNCHRONISATIONSCOL.CreateOrUpdateUtiForm( 184,'ACCES SIMPLE',35,85 )
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiForm1(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, form_);
            cls.setInt(5, utilisateur);
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
    
   /* CreateOrUpdateUtiNivForm( user_ UTILISATEURS.ID_UTILISATEUR%TYPE,role_ UTILISATEUR_NIVEAUX_FORMATIONS.ROLE%TYPE,niv_form_ UTILISATEUR_NIVEAUX_FORMATIONS.ID_NIVEAU_FORMATION%TYPE,
                                          utilisateur UTILISATEUR_NIVEAUX_FORMATIONS.UTI_CREE%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUserNivForm(Long user_, String role, Long niv_form_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiNivForm(?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, niv_form_);
            cls.setInt(5, utilisateur);
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
    public Long createOrUpdateUserNivForm1(Long user_, String role, Long niv_form_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiNivForm1(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, niv_form_);
            cls.setInt(5, utilisateur);
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
    public Long createOrUpdateStruct(String anc_code, String lib,String abrev,  String tel, String addr, String type_etab, String grh_code, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateStructure(?,?,?,?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, lib);
            cls.setString(4, abrev);
            cls.setString(5, tel);
            cls.setString(6, addr);
            cls.setString(7, type_etab);
            cls.setString(8, grh_code);
            cls.setInt(9, utilisateur);
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
    
    /*                    opcreatedept.getParamsMap().put("", CurrentStr.getAttribute("Code"));
                    opcreatedept.getParamsMap().put("", CurrentStr.getAttribute("LibelleLong"));
                    opcreatedept.getParamsMap().put("", CurrentStr.getAttribute("LibelleCourt"));
                    opcreatedept.getParamsMap().put("", str_id);
                    opcreatedept.getParamsMap().put("utilisateur", getUtilisateur());
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateDept(String anc_code, String lib,String abrev,  Long str_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDepartement(?,?,?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, anc_code);
            cls.setString(3, lib);
            cls.setString(4, abrev);
            cls.setLong(5, str_id);
            cls.setInt(6, utilisateur);
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
    
    /* CreateOrUpdateDeptByNiveau(niv_sec HISTORIQUES_STRUCTURES.ANCIEN_CODE%TYPE,utilisateur STRUCTURES.UTI_CREE%TYPE)
     * */
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDeptByNiveau(String niv_sec,Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDeptByNiveau(?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, niv_sec);
            cls.setInt(3, utilisateur);
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
    
    //CreateOrUpdateFormByNiv
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateFormByNiv(String niv_sec,String form_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateFormByNiv(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, niv_sec);
            cls.setString(3, form_);
            cls.setInt(4, utilisateur);
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

    //CreateOrUpdateNivFormByNiv
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateNivFormByNiv(String niv_sec, String form_, Long id_fr, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateNivFormByNiv(?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, niv_sec);
            cls.setString(3, form_);
            cls.setLong(4, id_fr);
            cls.setInt(5, utilisateur);
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

    /*DeleteUserRole(user_id UTILISATEURS_ROLES.ID_UTILISATEUR%TYPE, role_id UTILISATEURS_ROLES.ID_ROLE%TYPE)*/
    public void DeleteUserRole(Long user_id, Long role_id){
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.DeleteUserRole(?,?);" + " END;"), 0);
        try {
            statement.setLong(1, user_id);
            statement.setLong(2, role_id);
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
    
    /*CreateOrUpdateUtiFilUe( user_ UTILISATEURS.ID_UTILISATEUR%TYPE,role_ UTILIS_FILIERE_UE_SEMESTRE.ROLE%TYPE,fil_ue_ UTILIS_FILIERE_UE_SEMESTRE.ID_FILIERE_UE_SEMSTRE%TYPE,
                                      utilisateur UTILIS_FILIERE_UE_SEMESTRE.UTI_CREE%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUserFilUe(Long user_, String role, Long fil_ue_, Long pma_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiFilUe(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, fil_ue_);
            cls.setLong(5, pma_);
            cls.setInt(6, utilisateur);
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

/*UpdateResponsableUe(p_old UTILIS_FILIERE_UE_SEMESTRE.ID_UTILISATEUR%TYPE, p_new UTILIS_FILIERE_UE_SEMESTRE.ID_UTILISATEUR%TYPE,
            p_ue_id UTILIS_FILIERE_UE_SEMESTRE.ID_FILIERE_UE_SEMSTRE%TYPE, pma_id UTILIS_FILIERE_UE_SEMESTRE.ID_PARCOURS_MAQ_ANNEE%TYPE,
            p_user UTILISATEURS.ID_UTILISATEUR%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long UpdateResponsableUe(Long p_old, Long p_new, Long p_ue_id, Long pma_id, Integer p_user) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.UpdateResponsableUe(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, p_old);
            cls.setLong(3, p_new);
            cls.setLong(4, p_ue_id);
            cls.setLong(5, pma_id);
            cls.setInt(6, p_user);
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
    public Long UpdateResponsableEc(Long p_old, Long p_new, Long p_ec_id, Long pma_id, Integer p_user) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.UpdateResponsableEc(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, p_old);
            cls.setLong(3, p_new);
            cls.setLong(4, p_ec_id);
            cls.setLong(5, pma_id);
            cls.setInt(6, p_user);
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
    public Long createOrUpdateUserFilUe1(Long user_, String role, Long fil_ue_, Long pma_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiFilUe1(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, fil_ue_);
            cls.setLong(5, pma_);
            cls.setInt(6, utilisateur);
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

    /*CreateOrUpdateUtiFilEc( user_ UTILISATEURS.ID_UTILISATEUR%TYPE,role_ UTILIS_FILIERE_UE_SEMESTRE_EC.ROLE%TYPE,fil_ec_ UTILIS_FILIERE_UE_SEMESTRE_EC.ID_FILIERE_UE_SEMSTRE_EC%TYPE,
                                      utilisateur UTILIS_FILIERE_UE_SEMESTRE_EC.UTI_CREE%TYPE )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long createOrUpdateUserFilEc(Long user_, String role, Long fil_ec_, Long pma_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiFilEc(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, fil_ec_);
            cls.setLong(5, pma_);
            cls.setInt(6, utilisateur);
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
    
    /*CreateOrUpdateModeCntrlEC(fil_ec MODE_CONTROLE_EC.ID_FILIERE_UE_SEMSTRE_EC%TYPE, tc MODE_CONTROLE_EC.ID_TYPE_CONTROLE%TYPE, mc MODE_CONTROLE_EC.ID_MODE_CONTROLE%TYPE,
                                          pma MODE_CONTROLE_EC.ID_PARCOURS_MAQUETTE_ANNEE%TYPE,coef MODE_CONTROLE_EC.COEFFICIENT%TYPE,utilisateur MODE_CONTROLE_EC.UTI_CREE%TYPE) 
                                          Return MODE_CONTROLE_EC.ID_MODE_CONTROLE_EC%TYPE*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateModeCntrlEC(Long fil_ec, Long tc, Long mc, Long pma, Integer coef, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateModeCntrlEC(?,?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fil_ec);
            cls.setLong(3, tc);
            cls.setLong(4, mc);
            cls.setLong(5, pma);
            cls.setInt(6, coef);
            cls.setInt(7, utilisateur);
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
    public Long createOrUpdateUserFilEc1(Long user_, String role, Long fil_ec_, Long pma_, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateUtiFilEc1(?,?,?,?,?); end ;";
        Long result = new Long(0);
        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, user_);
            cls.setString(3, role);
            cls.setLong(4, fil_ec_);
            cls.setLong(5, pma_);
            cls.setInt(6, utilisateur);
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
    
    //CreateOrUpdateDisiForm(hs_id HISTORIQUES_STRUCTURES.ID_HISTORIQUES_STRUCTURE%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE)
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiForm(Long hs, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDisiForm(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, hs);
            cls.setInt(3, utilisateur);
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
    
    /*CreateOrUpdateDISI(log_ UTILISATEURS.LOGIN%TYPE, struct_ STRUCTURES.ANCIEN_CODE%TYPE,
                                    utilisateur UTILISATEURS.UTI_CREE%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiUser(String log, Long str_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDISI(?,?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setString(2, log);
            cls.setLong(3, str_id);
            cls.setInt(4, utilisateur);
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
    
    /*CreateOrUpdateDisiNivFormation(fr_id NIVEAUX_FORMATIONS.ID_FORMATION%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiNivFormation(Long fr_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDisiNivFormation(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, fr_id);
            cls.setInt(3, utilisateur);
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
    
    /*CreateOrUpdateDisiNivFormCohrte(niv_fr NIVEAUX_FORMATIONS.ID_NIVEAU_FORMATION%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiNivFormCohrte(Long nfr_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDisiNivFormCrt(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, nfr_id);
            cls.setInt(3, utilisateur);
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
    
    /*CreateOrUpdateDisiNivFormPrcrs(niv_fr_crt NIVEAU_FORMATION_COHORTES.ID_NIVEAU_FORMATION_COHORTE%TYPE, utilisateur UTILISATEURS.ID_UTILISATEUR%TYPE )*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiNivFormPrcrs(Long nfr_crt_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDisiNivFormPrcrs(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, nfr_crt_id);
            cls.setInt(3, utilisateur);
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
    
    /*CreateOrUpdateDisiDept(str_id HISTORIQUES_STRUCTURES.ID_STRUCTURES%TYPE, utilisateur STRUCTURES.UTI_CREE%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Long CreateOrUpdateDisiDept(Long str_id, Integer utilisateur) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  SYNCHRONISATIONSCOL.CreateOrUpdateDisiDept(?,?); end ;";
        Long result = new Long(0);

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, str_id);
            cls.setInt(3, utilisateur);
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
    public void ImportEtudiants(Long prcrs, String code_niv_s, Long annee, String code_op ,String code_fr, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.ImportEtudiants(?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs);
            statement.setString(2, code_niv_s);
            statement.setLong(3, annee);
            statement.setString(4, code_op);
            statement.setString(5, code_fr);
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
    public void ImportEtudiantsSup(Long prcrs, String code_niv_s, Long annee, String code_op ,String code_fr, Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.ImportEtudiantsSup(?,?,?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, prcrs);
            statement.setString(2, code_niv_s);
            statement.setLong(3, annee);
            statement.setString(4, code_op);
            statement.setString(5, code_fr);
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
    public void CloturerInscription(Long p_an, Long p_niv, Integer p_type, Long p_uti) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.FERMER_NIV_SEC(?,?,?,?);" + " END;"), 0);
        try {
            statement.setLong(1, p_an);
            statement.setLong(2, p_niv);
            statement.setLong(3, p_type);
            statement.setLong(4, p_uti);
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
    public void UpdateResultatSemAdm(Long prcrs, Long annee , Long utilisateur) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.UpdateResultatSemAdm(?,?,?);" + " END;"), 0);
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void DeleteCCUpdatePrc(Long pma_id, Long str_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.DeleteCCUpdatePrct(?,?);" + " END;"), 0);
        try {
            statement.setLong(1, pma_id);
            statement.setLong(2, str_id);
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
    public Integer TotalImpoted(Long prcrs, String code_niv_s, Long annee ,String code_op ,String code_fr) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.TotalImpoted(?,?,?,?,?);  end ;";
         Integer result = 0;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, prcrs);
             cls.setString(3, code_niv_s);
             cls.setLong(4, annee);
             cls.setString(5, code_op);
             cls.setString(6, code_fr);
             cls.registerOutParameter(1, Types.INTEGER);
             cls.execute();
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
    */
    
   public Integer TotalImpoted(Long prcrs, String code_niv_s, String code_fr) {
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  REFONTEPKG.TotalImpoted(?,?,?);  end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setLong(2, prcrs);
            cls.setString(3, code_niv_s);
            cls.setString(4, code_fr);
            cls.registerOutParameter(1, Types.INTEGER);
            cls.execute();
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
   
    public String getMatricule(Long utilisateur) {
         CallableStatement cls = null;
         ResultSet rs = null;
         String sql = "begin ? :=  REFONTEPKG.GetMatricule(?);  end ;";
         String result = null;

         try {
             cls = getDBTransaction().createCallableStatement(sql, 0);
             cls.setLong(2, utilisateur);
             cls.registerOutParameter(1, Types.VARCHAR);
             cls.execute();
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

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public Integer IsValidatedMaquette(Integer mqId, Integer AnId, Integer prcrsId) {
        //    FUNCTION cloturer_ue(fil_ue,calendrier, action,utilisateur)
        CallableStatement cls = null;
        ResultSet rs = null;
        String sql = "begin ? :=  VALIDATION.is_maquette_validated(?,?,?); end ;";
        Integer result = 0;

        try {
            cls = getDBTransaction().createCallableStatement(sql, 0);
            cls.setInt(2, mqId);
            cls.setInt(3, AnId);
            cls.setInt(4, prcrsId);
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
    /*############################ 2025 ##############################*/
    
    public void sendEmail(String username, String to, String subject, String body, ByteArrayOutputStream baos) {
            
            //final String username = "noreplyexamen@ucad.edu.sn";
            final String password = "P@290942831046of";
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
                message.addFrom(InternetAddress.parse(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setContent(body, "text/html; charset=UTF-8");
                
                byte[] bytes = baos.toByteArray();
                javax.mail.util.ByteArrayDataSource dataSourceFile = new ByteArrayDataSource(bytes, "application/pdf");
                MimeBodyPart pdfBodyPart = new MimeBodyPart();
                pdfBodyPart.setDataHandler(new DataHandler(dataSourceFile));
                pdfBodyPart.setFileName("Releve.pdf");
                MimeMultipart mimeMultipart = new MimeMultipart();
                mimeMultipart.addBodyPart(textBodyPart);
                mimeMultipart.addBodyPart(pdfBodyPart);
                message.setContent(mimeMultipart);
                //message.setContent(body, "text/html; charset=UTF-8");
                // message.setText(body);
                Transport.send(message);
                //System.out.println("Email envoyé à "+to+" avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateToMailSem(Long rsem_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "VALIDATION.UpdateToMailSem(?);" + " END;"), 0);
        try {
            statement.setLong(1, rsem_id);
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
    
    /*UpdateToMail(prcrs_maq_annee PARCOURS_MAQUETTE_ANNEE.ID_PARCOURS_MAQUET_ANNEE%TYPE,
      calendrier CALENDRIER_DELIBERATION.ID_CALENDRIER_DELIBERATION%TYPE)*/
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void UpdateToMail(Long pm_id, Long cal_id) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "REFONTEPKG.UpdateToMail(?,?);" + " END;"), 0);
        try {
            statement.setLong(1, pm_id);
            statement.setLong(2, cal_id);
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
    public boolean genererJetonEtEnvoyerEmail(String username) {
            DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
            CallableStatement statement =
                dbti.createCallableStatement(("BEGIN " + "VALIDATION.InsetPassToken(?, ?);" + " END;"), 0);
            String token = UUID.randomUUID().toString();
            try {
                statement.setString(1, username);
                statement.setString(2, token);
                statement.execute();
            } catch (SQLException sqlerr) {
                return false;
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException closeerr) {
                    throw new JboException(closeerr);
                }
            }
                // Construire le lien
                String lien = "<span style = \"font-family : Century Gothic;font-size : 16px;line-height : 30px;\">Bonjour<br/>" +
                    "Veuillez utilisez ce lien ci-dessous pour réinitialiser votre mot de passe<br/>" +
                    "<a href=\"http://http://127.0.0.1:7101/GestionPedagogique/faces//resetPassword.jsf?token=" + token+"\">Réninitialiser</a></span>";

                // Envoyer l'email
                sendEmail(null, username, "Réinitialisation de mot de passe",lien, null);
                return true;
        }
    
    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void synchWithScol(String numEtu) {
        DBTransactionImpl dbti = (DBTransactionImpl) getDBTransaction();
        CallableStatement statement =
            dbti.createCallableStatement(("BEGIN " + "SYNCHRONISATIONSCOL.UpdateEtudiant(?);" + " END;"), 0);
        try {
            statement.setString(1, numEtu);
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
     * Container's getter for ParcoursMaquetteAnneeVO2.
     * @return ParcoursMaquetteAnneeVO2
     */
    public ViewObjectImpl getParcoursMaquetteAnneeVO1() {
        return (ViewObjectImpl) findViewObject("ParcoursMaquetteAnneeVO1");
    }

    /**
     * Container's getter for isMaquetteParcoursExist1.
     * @return isMaquetteParcoursExist1
     */
    public isMaquetteParcoursExistImpl getisMaquetteParcoursExist() {
        return (isMaquetteParcoursExistImpl) findViewObject("isMaquetteParcoursExist");
    }

    /**
     * Container's getter for MaquettesParcoursRO1.
     * @return MaquettesParcoursRO1
     */
    public MaquettesParcoursROImpl getMaquettesParcoursRO() {
        return (MaquettesParcoursROImpl) findViewObject("MaquettesParcoursRO");
    }

    /**
     * Container's getter for isNivFormMaqAnnExist1.
     * @return isNivFormMaqAnnExist1
     */
    public isNivFormMaqAnnExistImpl getisNivFormMaqAnnExist() {
        return (isNivFormMaqAnnExistImpl) findViewObject("isNivFormMaqAnnExist");
    }

    /**
     * Container's getter for NiveauFormationMaquetteAnneVO1.
     * @return NiveauFormationMaquetteAnneVO1
     */
    public ViewObjectImpl getNiveauFormationMaquetteAnneVO() {
        return (ViewObjectImpl) findViewObject("NiveauFormationMaquetteAnneVO");
    }

    /**
     * Container's getter for AnneeUniversRO1.
     * @return AnneeUniversRO1
     */
    public AnneeUniversROImpl getAnneeUniversRO() {
        return (AnneeUniversROImpl) findViewObject("AnneeUniversRO");
    }

    /**
     * Container's getter for IsStructExist1.
     * @return IsStructExist1
     */
    public IsStructExistImpl getIsStructExist() {
        return (IsStructExistImpl) findViewObject("IsStructExist");
    }

    /**
     * Container's getter for IsHStructExist1.
     * @return IsHStructExist1
     */
    public IsHStructExistImpl getIsHStructExist() {
        return (IsHStructExistImpl) findViewObject("IsHStructExist");
    }

    /**
     * Container's getter for MaquetteNivFormRO1.
     * @return MaquetteNivFormRO1
     */
    public MaquetteNivFormROImpl getMaquetteNivFormRO() {
        return (MaquetteNivFormROImpl) findViewObject("MaquetteNivFormRO");
    }

    /**
     * Container's getter for EtudiantsVO1.
     * @return EtudiantsVO1
     */
    public ViewObjectImpl getEtudiantsVO1() {
        return (ViewObjectImpl) findViewObject("EtudiantsVO1");
    }


    /**
     * Container's getter for HStructByStructure1.
     * @return HStructByStructure1
     */
    public HStructByStructureImpl getHStructByStructure() {
        return (HStructByStructureImpl) findViewObject("HStructByStructure");
    }


    /**
     * Container's getter for ScolFilUe1.
     * @return ScolFilUe1
     */
    public ScolFilUeImpl getScolFilUe() {
        return (ScolFilUeImpl) findViewObject("ScolFilUe");
    }

    /**
     * Container's getter for ScolFilEc1.
     * @return ScolFilEc1
     */
    public ScolFilEcImpl getScolFilEc() {
        return (ScolFilEcImpl) findViewObject("ScolFilEc");
    }

    /**
     * Container's getter for ScolFilUeToScolFilEc1.
     * @return ScolFilUeToScolFilEc1
     */
    public ViewLinkImpl getScolFilUeToScolFilEc1() {
        return (ViewLinkImpl) findViewLink("ScolFilUeToScolFilEc1");
    }

    /**
     * Container's getter for ScolEtudiantInscritDef1.
     * @return ScolEtudiantInscritDef1
     */
    public ScolEtudiantInscritDefImpl getScolEtudiantInscritDef() {
        return (ScolEtudiantInscritDefImpl) findViewObject("ScolEtudiantInscritDef");
    }
    
    public void getMaquetteStruct(Long strId) {
        ViewObject vo = getMaquettes();
        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        ViewCriteria vc = vcm.getViewCriteria("MaquettesByStructureVc");
        VariableValueManager vvm = vc.ensureVariableManager();
        vvm.setVariableValue("str_id", strId);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
    }
    
    /**
     * Container's getter for ScolFormationROVO1.
     * @return ScolFormationROVO1
     */
    public ScolFormationROVOImpl getScolFormationROVO1() {
        return (ScolFormationROVOImpl) findViewObject("ScolFormationROVO1");
    }

    /**
     * Container's getter for HStructToScolFormation2.
     * @return HStructToScolFormation2
     */
    public ViewLinkImpl getHStructToScolFormation2() {
        return (ViewLinkImpl) findViewLink("HStructToScolFormation2");
    }

    /**
     * Container's getter for ScolNivFormationROVO1.
     * @return ScolNivFormationROVO1
     */
    public ScolNivFormationROVOImpl getScolNivFormationROVO1() {
        return (ScolNivFormationROVOImpl) findViewObject("ScolNivFormationROVO1");
    }

    /**
     * Container's getter for ScolFormationToNivFormationVL2.
     * @return ScolFormationToNivFormationVL2
     */
    public ViewLinkImpl getScolFormationToNivFormationVL2() {
        return (ViewLinkImpl) findViewLink("ScolFormationToNivFormationVL2");
    }

    /**
     * Container's getter for getsemestregradeROVO1.
     * @return getsemestregradeROVO1
     */
    public getsemestregradeROVOImpl getgetsemestregrade() {
        return (getsemestregradeROVOImpl) findViewObject("getsemestregrade");
    }

    /**
     * Container's getter for ParcousInscriptionUeOptROVO1.
     * @return ParcousInscriptionUeOptROVO1
     */
    public ParcousInscriptionUeOptROVOImpl getParcousInscriptionUeOpt() {
        return (ParcousInscriptionUeOptROVOImpl) findViewObject("ParcousInscriptionUeOpt");
    }


    /**
     * Container's getter for EtudiantInsUeOptROVO1.
     * @return EtudiantInsUeOptROVO1
     */
    public EtudiantInsUeOptROVOImpl getEtudiantInsUeOpt() {
        return (EtudiantInsUeOptROVOImpl) findViewObject("EtudiantInsUeOpt");
    }

    /**
     * Container's getter for PrcrsToEtudiantInsUeOptVL1.
     * @return PrcrsToEtudiantInsUeOptVL1
     */
    public ViewLinkImpl getPrcrsToEtudiantInsUeOptVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsToEtudiantInsUeOptVL1");
    }

    /**
     * Container's getter for ScolEtablissement1.
     * @return ScolEtablissement1
     */
    public ScolEtablissementImpl getScolEtablissement() {
        return (ScolEtablissementImpl) findViewObject("ScolEtablissement");
    }

    /**
     * Container's getter for ScolDepartement1.
     * @return ScolDepartement1
     */
    public ScolDepartementImpl getScolDepartement() {
        return (ScolDepartementImpl) findViewObject("ScolDepartement");
    }

    /**
     * Container's getter for ScolEtabToScolDeptVL1.
     * @return ScolEtabToScolDeptVL1
     */
    public ViewLinkImpl getScolEtabToScolDeptVL1() {
        return (ViewLinkImpl) findViewLink("ScolEtabToScolDeptVL1");
    }

    /**
     * Container's getter for ScolFormationROVO2.
     * @return ScolFormationROVO2
     */
    public ScolFormationROVOImpl getScolFormationROVO() {
        return (ScolFormationROVOImpl) findViewObject("ScolFormationROVO");
    }

    /**
     * Container's getter for ScolDeptToScolFormation1.
     * @return ScolDeptToScolFormation1
     */
    public ViewLinkImpl getScolDeptToScolFormation1() {
        return (ViewLinkImpl) findViewLink("ScolDeptToScolFormation1");
    }

    /**
     * Container's getter for ScolNivFormationROVO2.
     * @return ScolNivFormationROVO2
     */
    public ScolNivFormationROVOImpl getScolNivFormationROVO() {
        return (ScolNivFormationROVOImpl) findViewObject("ScolNivFormationROVO");
    }

    /**
     * Container's getter for ScolFormationToNivFormationVL1.
     * @return ScolFormationToNivFormationVL1
     */
    public ViewLinkImpl getScolFormationToNivFormationVL1() {
        return (ViewLinkImpl) findViewLink("ScolFormationToNivFormationVL1");
    }

    /**
     * Container's getter for isDeptExistROVO1.
     * @return isDeptExistROVO1
     */
    public isDeptExistROVOImpl getisDeptExistROVO() {
        return (isDeptExistROVOImpl) findViewObject("isDeptExistROVO");
    }

    /**
     * Container's getter for isEtbExistROVO1.
     * @return isEtbExistROVO1
     */
    public isEtbExistROVOImpl getisEtbExistROVO() {
        return (isEtbExistROVOImpl) findViewObject("isEtbExistROVO");
    }

    /**
     * Container's getter for grhAllUser1.
     * @return grhAllUser1
     */
    public grhAllUserImpl getgrhAllUser() {
        return (grhAllUserImpl) findViewObject("grhAllUser");
    }

    /**
     * Container's getter for UtilisateurEtabROVO1.
     * @return UtilisateurEtabROVO1
     */
    public UtilisateurEtabROVOImpl getUtilisateurEtabROVO() {
        return (UtilisateurEtabROVOImpl) findViewObject("UtilisateurEtabROVO");
    }

    /**
     * Container's getter for isUserResponsable1.
     * @return isUserResponsable1
     */
    public isUserResponsableImpl getisUserResponsable() {
        return (isUserResponsableImpl) findViewObject("isUserResponsable");
    }

    /**
     * Container's getter for isUserRespNivFormation1.
     * @return isUserRespNivFormation1
     */
    public isUserRespNivFormationImpl getisUserRespNivFormation() {
        return (isUserRespNivFormationImpl) findViewObject("isUserRespNivFormation");
    }

    /**
     * Container's getter for isUserResponsableFilUe1.
     * @return isUserResponsableFilUe1
     */
    public isUserResponsableFilUeImpl getisUserResponsableFilUe() {
        return (isUserResponsableFilUeImpl) findViewObject("isUserResponsableFilUe");
    }

    /**
     * Container's getter for isUserResponsableFilEc1.
     * @return isUserResponsableFilEc1
     */
    public isUserResponsableFilEcImpl getisUserResponsableFilEc() {
        return (isUserResponsableFilEcImpl) findViewObject("isUserResponsableFilEc");
    }

    /**
     * Container's getter for UtilisateursROVO1.
     * @return UtilisateursROVO1
     */
    public ViewObjectImpl getUtilisateursROVO() {
        return (ViewObjectImpl) findViewObject("UtilisateursROVO");
    }

    /**
     * Container's getter for FormNivFormFilUeROVO1.
     * @return FormNivFormFilUeROVO1
     */
    public FormNivFormFilUeROVOImpl getFormNivFormFilUe() {
        return (FormNivFormFilUeROVOImpl) findViewObject("FormNivFormFilUe");
    }

    /**
     * Container's getter for FormNivFormROVO2.
     * @return FormNivFormROVO2
     */
    public FormNivFormROVOImpl getFormNivForm() {
        return (FormNivFormROVOImpl) findViewObject("FormNivForm");
    }

    /**
     * Container's getter for ScolDeptIntegrationFrROVO1.
     * @return ScolDeptIntegrationFrROVO1
     */
    public ScolDeptIntegrationFrROVOImpl getScolDeptIntegrationFr() {
        return (ScolDeptIntegrationFrROVOImpl) findViewObject("ScolDeptIntegrationFr");
    }

    /**
     * Container's getter for ScolStudentInscDef1.
     * @return ScolStudentInscDef1
     */
    public ScolStudentInscDefImpl getScolStudentInscDef() {
        return (ScolStudentInscDefImpl) findViewObject("ScolStudentInscDef");
    }

    /**
     * Container's getter for ScolEtabNotIntegratedROVO1.
     * @return ScolEtabNotIntegratedROVO1
     */
    public ScolEtabNotIntegratedROVOImpl getScolEtabNotIntegrated() {
        return (ScolEtabNotIntegratedROVOImpl) findViewObject("ScolEtabNotIntegrated");
    }

    /**
     * Container's getter for ScolStudentDefInsc1.
     * @return ScolStudentDefInsc1
     */
    public ScolStudentDefInscImpl getScolStudentDefInsc() {
        return (ScolStudentDefInscImpl) findViewObject("ScolStudentDefInsc");
    }

    /**
     * Container's getter for AdminByEtablissementROVO1.
     * @return AdminByEtablissementROVO1
     */
    public AdminByEtablissementROVOImpl getAdminByEtablissement() {
        return (AdminByEtablissementROVOImpl) findViewObject("AdminByEtablissement");
    }

    /**
     * Container's getter for ScolNivFormIntegratedROVO1.
     * @return ScolNivFormIntegratedROVO1
     */
    public ScolNivFormIntegratedROVOImpl getScolNivFormIntegrated() {
        return (ScolNivFormIntegratedROVOImpl) findViewObject("ScolNivFormIntegrated");
    }

    /**
     * Container's getter for ScolDeptToScolNivFormIntegratedVL1.
     * @return ScolDeptToScolNivFormIntegratedVL1
     */
    public ViewLinkImpl getScolDeptToScolNivFormIntegratedVL1() {
        return (ViewLinkImpl) findViewLink("ScolDeptToScolNivFormIntegratedVL1");
    }

    /**
     * Container's getter for ScolNivFormNotIntegratedROVO1.
     * @return ScolNivFormNotIntegratedROVO1
     */
    public ScolNivFormNotIntegratedROVOImpl getScolNivFormNotIntegrated() {
        return (ScolNivFormNotIntegratedROVOImpl) findViewObject("ScolNivFormNotIntegrated");
    }

    /**
     * Container's getter for ScolDeptToScolNivFormNotIntegratedVL1.
     * @return ScolDeptToScolNivFormNotIntegratedVL1
     */
    public ViewLinkImpl getScolDeptToScolNivFormNotIntegratedVL1() {
        return (ViewLinkImpl) findViewLink("ScolDeptToScolNivFormNotIntegratedVL1");
    }

    /**
     * Container's getter for HistoriqueStructureByUserName1.
     * @return HistoriqueStructureByUserName1
     */
    public HistoriqueStructureByUserNameImpl getHistoriqueStructureByUserName() {
        return (HistoriqueStructureByUserNameImpl) findViewObject("HistoriqueStructureByUserName");
    }

    /**
     * Container's getter for ParcoursRespFrImpMaqEtdROVO1.
     * @return ParcoursRespFrImpMaqEtdROVO1
     */
    public ParcoursRespFrImpMaqEtdROVOImpl getParcoursRespFrImpMaqEtd() {
        return (ParcoursRespFrImpMaqEtdROVOImpl) findViewObject("ParcoursRespFrImpMaqEtd");
    }

    /**
     * Container's getter for HsByUserNameToPrcrsRespFrMaqEtdVL1.
     * @return HsByUserNameToPrcrsRespFrMaqEtdVL1
     */
    public ViewLinkImpl getHsByUserNameToPrcrsRespFrMaqEtdVL1() {
        return (ViewLinkImpl) findViewLink("HsByUserNameToPrcrsRespFrMaqEtdVL1");
    }


    /**
     * Container's getter for RolesVO1.
     * @return RolesVO1
     */
    public ViewObjectImpl getRolesVO() {
        return (ViewObjectImpl) findViewObject("RolesVO");
    }


    /**
     * Container's getter for RoleAAssignerVO1.
     * @return RoleAAssignerVO1
     */
    public ViewObjectImpl getRoleAAssignerVO() {
        return (ViewObjectImpl) findViewObject("RoleAAssignerVO");
    }

    /**
     * Container's getter for UtiRolesROVO1.
     * @return UtiRolesROVO1
     */
    public UtiRolesROVOImpl getUtiRolesROVO() {
        return (UtiRolesROVOImpl) findViewObject("UtiRolesROVO");
    }

    /**
     * Container's getter for SearchUserList1.
     * @return SearchUserList1
     */
    public SearchUserListImpl getSearchUserList() {
        return (SearchUserListImpl) findViewObject("SearchUserList");
    }

    /**
     * Container's getter for RoleAAssignerROVO1.
     * @return RoleAAssignerROVO1
     */
    public RoleAAssignerROVOImpl getRoleAAssigner() {
        return (RoleAAssignerROVOImpl) findViewObject("RoleAAssigner");
    }

    /**
     * Container's getter for AdminEtabROVO1.
     * @return AdminEtabROVO1
     */
    public AdminEtabROVOImpl getAdminEtab() {
        return (AdminEtabROVOImpl) findViewObject("AdminEtab");
    }

    /**
     * Container's getter for HightLevelRolesROVO1.
     * @return HightLevelRolesROVO1
     */
    public HightLevelRolesROVOImpl getHightLevelRoles() {
        return (HightLevelRolesROVOImpl) findViewObject("HightLevelRoles");
    }

    /**
     * Container's getter for FormationByAncCodeROVO1.
     * @return FormationByAncCodeROVO1
     */
    public FormationByAncCodeROVOImpl getFormationByAncCode() {
        return (FormationByAncCodeROVOImpl) findViewObject("FormationByAncCode");
    }

    /**
     * Container's getter for IsUserRespNivFormROVO1.
     * @return IsUserRespNivFormROVO1
     */
    public IsUserRespNivFormROVOImpl getIsUserRespNivForm() {
        return (IsUserRespNivFormROVOImpl) findViewObject("IsUserRespNivForm");
    }

    /**
     * Container's getter for EtudiantInscritOptROVO1.
     * @return EtudiantInscritOptROVO1
     */
    public EtudiantInscritOptROVOImpl getEtudiantInscritOpt() {
        return (EtudiantInscritOptROVOImpl) findViewObject("EtudiantInscritOpt");
    }

    /**
     * Container's getter for PrcrsUeOptToEtuInscVL1.
     * @return PrcrsUeOptToEtuInscVL1
     */
    public ViewLinkImpl getPrcrsUeOptToEtuInscVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsUeOptToEtuInscVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreInsUeOptROVO1.
     * @return FiliereUeSemestreInsUeOptROVO1
     */
    public FiliereUeSemestreInsUeOptROVOImpl getFiliereUeSemestreInsUeOpt() {
        return (FiliereUeSemestreInsUeOptROVOImpl) findViewObject("FiliereUeSemestreInsUeOpt");
    }

    /**
     * Container's getter for PrcrsToFiliereUeInscOptVL1.
     * @return PrcrsToFiliereUeInscOptVL1
     */
    public ViewLinkImpl getPrcrsToFiliereUeInscOptVL1() {
        return (ViewLinkImpl) findViewLink("PrcrsToFiliereUeInscOptVL1");
    }

    /**
     * Container's getter for FiliereUeSemestreInsUeOptROVO1.
     * @return FiliereUeSemestreInsUeOptROVO1
     */
    public FiliereUeSemestreInsUeOptROVOImpl getFiliereUeSemestreInsUeOptROVO1() {
        return (FiliereUeSemestreInsUeOptROVOImpl) findViewObject("FiliereUeSemestreInsUeOptROVO1");
    }

    /**
     * Container's getter for NiveauFormationMqAnHistROVO1.
     * @return NiveauFormationMqAnHistROVO1
     */
    public NiveauFormationMqAnHistROVOImpl getNiveauFormationMqAnHistRO() {
        return (NiveauFormationMqAnHistROVOImpl) findViewObject("NiveauFormationMqAnHistRO");
    }

    /**
     * Container's getter for NiveauFormationRoTONivFrMaqAnHistVL1.
     * @return NiveauFormationRoTONivFrMaqAnHistVL1
     */
    public ViewLinkImpl getNiveauFormationRoTONivFrMaqAnHistVL1() {
        return (ViewLinkImpl) findViewLink("NiveauFormationRoTONivFrMaqAnHistVL1");
    }

    /**
     * Container's getter for MaquetteStructureROVO1.
     * @return MaquetteStructureROVO1
     */
    public MaquetteStructureROVOImpl getMaquetteStructure() {
        return (MaquetteStructureROVOImpl) findViewObject("MaquetteStructure");
    }

    /**
     * Container's getter for ScolFormationIntegrationROVO1.
     * @return ScolFormationIntegrationROVO1
     */
    public ScolFormationIntegrationROVOImpl getScolFormationIntegration() {
        return (ScolFormationIntegrationROVOImpl) findViewObject("ScolFormationIntegration");
    }

    /**
     * Container's getter for ScolDeptToScolFormIntegrationVL1.
     * @return ScolDeptToScolFormIntegrationVL1
     */
    public ViewLinkImpl getScolDeptToScolFormIntegrationVL1() {
        return (ViewLinkImpl) findViewLink("ScolDeptToScolFormIntegrationVL1");
    }


    /**
     * Container's getter for ScolNivFormPrcrsNotIntegratedROVO1.
     * @return ScolNivFormPrcrsNotIntegratedROVO1
     */
    public ScolNivFormPrcrsNotIntegratedROVOImpl getScolNivFormPrcrsNotIntegrated() {
        return (ScolNivFormPrcrsNotIntegratedROVOImpl) findViewObject("ScolNivFormPrcrsNotIntegrated");
    }

    /**
     * Container's getter for ScolFrToScolNivFrPrcrsNotIntegratedVL1.
     * @return ScolFrToScolNivFrPrcrsNotIntegratedVL1
     */
    public ViewLinkImpl getScolFrToScolNivFrPrcrsNotIntegratedVL1() {
        return (ViewLinkImpl) findViewLink("ScolFrToScolNivFrPrcrsNotIntegratedVL1");
    }

    /**
     * Container's getter for ScolNivFormPrcrsIntegratedROVO1.
     * @return ScolNivFormPrcrsIntegratedROVO1
     */
    public ScolNivFormPrcrsIntegratedROVOImpl getScolNivFormPrcrsIntegrated() {
        return (ScolNivFormPrcrsIntegratedROVOImpl) findViewObject("ScolNivFormPrcrsIntegrated");
    }

    /**
     * Container's getter for ScolFrToScolNivPrcrsIntegratedVL1.
     * @return ScolFrToScolNivPrcrsIntegratedVL1
     */
    public ViewLinkImpl getScolFrToScolNivPrcrsIntegratedVL1() {
        return (ViewLinkImpl) findViewLink("ScolFrToScolNivPrcrsIntegratedVL1");
    }

    /**
     * Container's getter for ScolFilUeOptionsROVO1.
     * @return ScolFilUeOptionsROVO1
     */
    public ScolFilUeOptionsROVOImpl getScolFilUeOptions() {
        return (ScolFilUeOptionsROVOImpl) findViewObject("ScolFilUeOptions");
    }

    /**
     * Container's getter for ScolFilEc1.
     * @return ScolFilEc1
     */
    public ScolFilEcImpl getScolFilEc1() {
        return (ScolFilEcImpl) findViewObject("ScolFilEc1");
    }

    /**
     * Container's getter for ScolFilUeOptionToScolFilEcVL1.
     * @return ScolFilUeOptionToScolFilEcVL1
     */
    public ViewLinkImpl getScolFilUeOptionToScolFilEcVL1() {
        return (ViewLinkImpl) findViewLink("ScolFilUeOptionToScolFilEcVL1");
    }

    /**
     * Container's getter for OptionANCNOParcoursROVO1.
     * @return OptionANCNOParcoursROVO1
     */
    public OptionANCNOParcoursROVOImpl getOptionANCNOParcours() {
        return (OptionANCNOParcoursROVOImpl) findViewObject("OptionANCNOParcours");
    }

    /**
     * Container's getter for ScolFilUeOptionsANCNOROVO1.
     * @return ScolFilUeOptionsANCNOROVO1
     */
    public ScolFilUeOptionsANCNOROVOImpl getScolFilUeOptionsANCNO() {
        return (ScolFilUeOptionsANCNOROVOImpl) findViewObject("ScolFilUeOptionsANCNO");
    }

    /**
     * Container's getter for OptionsANCNOToFilUeOtionsANCNOVL1.
     * @return OptionsANCNOToFilUeOtionsANCNOVL1
     */
    public ViewLinkImpl getOptionsANCNOToFilUeOtionsANCNOVL1() {
        return (ViewLinkImpl) findViewLink("OptionsANCNOToFilUeOtionsANCNOVL1");
    }

    /**
     * Container's getter for ScolFilEc2.
     * @return ScolFilEc2
     */
    public ScolFilEcImpl getScolFilEc2() {
        return (ScolFilEcImpl) findViewObject("ScolFilEc2");
    }

    /**
     * Container's getter for ScolFilUeOptionANCNOToScolEcVL1.
     * @return ScolFilUeOptionANCNOToScolEcVL1
     */
    public ViewLinkImpl getScolFilUeOptionANCNOToScolEcVL1() {
        return (ViewLinkImpl) findViewLink("ScolFilUeOptionANCNOToScolEcVL1");
    }

    /**
     * Container's getter for MaquetteValideImportedROVO1.
     * @return MaquetteValideImportedROVO1
     */
    public MaquetteValideImportedROVOImpl getMaquetteValideImportedROVO1() {
        return (MaquetteValideImportedROVOImpl) findViewObject("MaquetteValideImportedROVO1");
    }

    /**
     * Container's getter for PrcrsRespToMaqValidImportedROVO1.
     * @return PrcrsRespToMaqValidImportedROVO1
     */
    public ViewLinkImpl getPrcrsRespToMaqValidImportedROVO1() {
        return (ViewLinkImpl) findViewLink("PrcrsRespToMaqValidImportedROVO1");
    }

    /**
     * Container's getter for MaquetteValideImported.
     * @return MaquetteValideImported
     */
    public MaquetteValideImportedROVOImpl getMaquetteValideImported() {
        return (MaquetteValideImportedROVOImpl) findViewObject("MaquetteValideImported");
    }


    /**
     * Container's getter for MaquetteIntegratedNivSecROVO1.
     * @return MaquetteIntegratedNivSecROVO1
     */
    public MaquetteIntegratedNivSecROVOImpl getMaquetteIntegratedNivSec1() {
        return (MaquetteIntegratedNivSecROVOImpl) findViewObject("MaquetteIntegratedNivSec1");
    }

    /**
     * Container's getter for MaqValidToMaqIntagratedVL1.
     * @return MaqValidToMaqIntagratedVL1
     */
    public ViewLinkImpl getMaqValidToMaqIntagratedVL1() {
        return (ViewLinkImpl) findViewLink("MaqValidToMaqIntagratedVL1");
    }

    /**
     * Container's getter for FilEcROVO1.
     * @return FilEcROVO1
     */
    public FilEcROVOImpl getFilEc1() {
        return (FilEcROVOImpl) findViewObject("FilEc1");
    }

    /**
     * Container's getter for MaqIntegNivSecToFilEcVL2.
     * @return MaqIntegNivSecToFilEcVL2
     */
    public ViewLinkImpl getMaqIntegNivSecToFilEcVL2() {
        return (ViewLinkImpl) findViewLink("MaqIntegNivSecToFilEcVL2");
    }

    /**
     * Container's getter for OptionByLibelleROVO1.
     * @return OptionByLibelleROVO1
     */
    public OptionByLibelleROVOImpl getOptionByLibelle() {
        return (OptionByLibelleROVOImpl) findViewObject("OptionByLibelle");
    }

    /**
     * Container's getter for getPrcrsMaquetteAnneeROVO1.
     * @return getPrcrsMaquetteAnneeROVO1
     */
    public ViewObjectImpl getgetPrcrsMaquetteAnneeROVO1() {
        return (ViewObjectImpl) findViewObject("getPrcrsMaquetteAnneeROVO1");
    }

    /**
     * Container's getter for FormationsVO3.
     * @return FormationsVO3
     */
    public FormationsVOImpl getFormationsVO3() {
        return (FormationsVOImpl) findViewObject("FormationsVO3");
    }

    /**
     * Container's getter for HistoriquesStructuresToFormationsVL1.
     * @return HistoriquesStructuresToFormationsVL1
     */
    public ViewLinkImpl getHistoriquesStructuresToFormationsVL1() {
        return (ViewLinkImpl) findViewLink("HistoriquesStructuresToFormationsVL1");
    }

    /**
     * Container's getter for NiveauxFormationsVO2.
     * @return NiveauxFormationsVO2
     */
    public ViewObjectImpl getNiveauxFormationsVO2() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationsVO2");
    }

    /**
     * Container's getter for FormationsToNiveauxFormationsVL4.
     * @return FormationsToNiveauxFormationsVL4
     */
    public ViewLinkImpl getFormationsToNiveauxFormationsVL4() {
        return (ViewLinkImpl) findViewLink("FormationsToNiveauxFormationsVL4");
    }

    /**
     * Container's getter for ScolDeptDetailDapROVO1.
     * @return ScolDeptDetailDapROVO1
     */
    public ScolDeptDetailDapROVOImpl getScolDeptDetailDap() {
        return (ScolDeptDetailDapROVOImpl) findViewObject("ScolDeptDetailDap");
    }

    /**
     * Container's getter for ScolEtabScolDepDetDapVL1.
     * @return ScolEtabScolDepDetDapVL1
     */
    public ViewLinkImpl getScolEtabScolDepDetDapVL1() {
        return (ViewLinkImpl) findViewLink("ScolEtabScolDepDetDapVL1");
    }

    /**
     * Container's getter for ScolFormationIntegrationROVO1.
     * @return ScolFormationIntegrationROVO1
     */
    public ScolFormationIntegrationROVOImpl getScolFormationIntegrationDap() {
        return (ScolFormationIntegrationROVOImpl) findViewObject("ScolFormationIntegrationDap");
    }

    /**
     * Container's getter for ScolDepDetDapToScolFrIntegrationVL1.
     * @return ScolDepDetDapToScolFrIntegrationVL1
     */
    public ViewLinkImpl getScolDepDetDapToScolFrIntegrationVL1() {
        return (ViewLinkImpl) findViewLink("ScolDepDetDapToScolFrIntegrationVL1");
    }

    /**
     * Container's getter for NiveauxSectionDtailDAPROVO1.
     * @return NiveauxSectionDtailDAPROVO1
     */
    public NiveauxSectionDtailDAPROVOImpl getNiveauxSectionDtailDAP() {
        return (NiveauxSectionDtailDAPROVOImpl) findViewObject("NiveauxSectionDtailDAP");
    }

    /**
     * Container's getter for ScolFrIntegrationToNivSecDetailDapVL1.
     * @return ScolFrIntegrationToNivSecDetailDapVL1
     */
    public ViewLinkImpl getScolFrIntegrationToNivSecDetailDapVL1() {
        return (ViewLinkImpl) findViewLink("ScolFrIntegrationToNivSecDetailDapVL1");
    }

    /**
     * Container's getter for NiveauFormationCohortesVO2.
     * @return NiveauFormationCohortesVO2
     */
    public ViewObjectImpl getNiveauFormationCohortesVO2() {
        return (ViewObjectImpl) findViewObject("NiveauFormationCohortesVO2");
    }

    /**
     * Container's getter for NiveauxFormationsToNiveauFormationsCohortesVL4.
     * @return NiveauxFormationsToNiveauFormationsCohortesVL4
     */
    public ViewLinkImpl getNiveauxFormationsToNiveauFormationsCohortesVL4() {
        return (ViewLinkImpl) findViewLink("NiveauxFormationsToNiveauFormationsCohortesVL4");
    }

    /**
     * Container's getter for NiveauxFormationParcoursVO2.
     * @return NiveauxFormationParcoursVO2
     */
    public ViewObjectImpl getNiveauxFormationParcoursVO2() {
        return (ViewObjectImpl) findViewObject("NiveauxFormationParcoursVO2");
    }

    /**
     * Container's getter for NiveauFormationsCohortesToNiveauxFormationParcoursVL4.
     * @return NiveauFormationsCohortesToNiveauxFormationParcoursVL4
     */
    public ViewLinkImpl getNiveauFormationsCohortesToNiveauxFormationParcoursVL4() {
        return (ViewLinkImpl) findViewLink("NiveauFormationsCohortesToNiveauxFormationParcoursVL4");
    }

    /**
     * Container's getter for TemputilisateursVO1.
     * @return TemputilisateursVO1
     */
    public ViewObjectImpl getTemputilisateurs() {
        return (ViewObjectImpl) findViewObject("Temputilisateurs");
    }

    /**
     * Container's getter for isUserExisteROVO1.
     * @return isUserExisteROVO1
     */
    public isUserExisteROVOImpl getisUserExisteROVO1() {
        return (isUserExisteROVOImpl) findViewObject("isUserExisteROVO1");
    }


    /**
     * Container's getter for ParcoursOpNullROVO1.
     * @return ParcoursOpNullROVO1
     */
    public ParcoursOpNullROVOImpl getParcoursOpNull() {
        return (ParcoursOpNullROVOImpl) findViewObject("ParcoursOpNull");
    }

    /**
     * Container's getter for OptionScolLOV1.
     * @return OptionScolLOV1
     */
    public OptionScolLOVImpl getOptionScol() {
        return (OptionScolLOVImpl) findViewObject("OptionScol");
    }

    /**
     * Container's getter for OptionnivsecscolVO1.
     * @return OptionnivsecscolVO1
     */
    public ViewObjectImpl getOptionnivsecscolVO() {
        return (ViewObjectImpl) findViewObject("OptionnivsecscolVO");
    }

    /**
     * Container's getter for NivFormSpecialiteOptionVO1_1.
     * @return NivFormSpecialiteOptionVO1_1
     */
    public ViewObjectImpl getNivFormSpecialiteOptionVO() {
        return (ViewObjectImpl) findViewObject("NivFormSpecialiteOptionVO");
    }

    /**
     * Container's getter for NivFormToNivFormOptVL1.
     * @return NivFormToNivFormOptVL1
     */
    public ViewLinkImpl getNivFormToNivFormOptVL1() {
        return (ViewLinkImpl) findViewLink("NivFormToNivFormOptVL1");
    }

    /**
     * Container's getter for NivFormOptionsROVO1.
     * @return NivFormOptionsROVO1
     */
    public NivFormOptionsROVOImpl getNivFormOptions() {
        return (NivFormOptionsROVOImpl) findViewObject("NivFormOptions");
    }

    /**
     * Container's getter for NivFormPrcrsROVO1.
     * @return NivFormPrcrsROVO1
     */
    public NivFormPrcrsROVOImpl getNivFormPrcrs() {
        return (NivFormPrcrsROVOImpl) findViewObject("NivFormPrcrs");
    }

    /**
     * Container's getter for EtatDeliberationAnROVO1.
     * @return EtatDeliberationAnROVO1
     */
    public EtatDeliberationAnROVOImpl getEtatDeliberationAn() {
        return (EtatDeliberationAnROVOImpl) findViewObject("EtatDeliberationAn");
    }

    /**
     * Container's getter for MaquetteValidatedToEtatDelibVL1.
     * @return MaquetteValidatedToEtatDelibVL1
     */
    public ViewLinkImpl getMaquetteValidatedToEtatDelibVL1() {
        return (ViewLinkImpl) findViewLink("MaquetteValidatedToEtatDelibVL1");
    }

    /**
     * Container's getter for EtatDeliberationSemROVO1.
     * @return EtatDeliberationSemROVO1
     */
    public EtatDeliberationSemROVOImpl getEtatDeliberationSem() {
        return (EtatDeliberationSemROVOImpl) findViewObject("EtatDeliberationSem");
    }

    /**
     * Container's getter for MaquetteImportedToEtatDelibSemVL1.
     * @return MaquetteImportedToEtatDelibSemVL1
     */
    public ViewLinkImpl getMaquetteImportedToEtatDelibSemVL1() {
        return (ViewLinkImpl) findViewLink("MaquetteImportedToEtatDelibSemVL1");
    }

    /**
     * Container's getter for EtatDelibUEROVO1.
     * @return EtatDelibUEROVO1
     */
    public EtatDelibUEROVOImpl getEtatDelibUE() {
        return (EtatDelibUEROVOImpl) findViewObject("EtatDelibUE");
    }

    /**
     * Container's getter for EtatDelibSemToEtatDelibUeVL1.
     * @return EtatDelibSemToEtatDelibUeVL1
     */
    public ViewLinkImpl getEtatDelibSemToEtatDelibUeVL1() {
        return (ViewLinkImpl) findViewLink("EtatDelibSemToEtatDelibUeVL1");
    }

    /**
     * Container's getter for isChefDeptExistROVO1.
     * @return isChefDeptExistROVO1
     */
    public isChefDeptExistROVOImpl getisChefDeptExist() {
        return (isChefDeptExistROVOImpl) findViewObject("isChefDeptExist");
    }

    /**
     * Container's getter for InscriptionCloseROVO1.
     * @return InscriptionCloseROVO1
     */
    public InscriptionCloseROVOImpl getInscriptionClose() {
        return (InscriptionCloseROVOImpl) findViewObject("InscriptionClose");
    }

    /**
     * Container's getter for InscriptionACloturerROVO1.
     * @return InscriptionACloturerROVO1
     */
    public InscriptionACloturerROVOImpl getInscriptionACloturer() {
        return (InscriptionACloturerROVOImpl) findViewObject("InscriptionACloturer");
    }

    /**
     * Container's getter for EtudiantsToMailROVO1.
     * @return EtudiantsToMailROVO1
     */
    public EtudiantsToMailROVOImpl getEtudiantsToMail() {
        return (EtudiantsToMailROVOImpl) findViewObject("EtudiantsToMail");
    }

    /**
     * Container's getter for EtudiantToMailRecROVO1.
     * @return EtudiantToMailRecROVO1
     */
    public EtudiantToMailRecROVOImpl getEtudiantsToMailRec() {
        return (EtudiantToMailRecROVOImpl) findViewObject("EtudiantsToMailRec");
    }

    /**
     * Container's getter for MaquetteParcoursAnneeROVO1.
     * @return MaquetteParcoursAnneeROVO1
     */
    public MaquetteParcoursAnneeROVOImpl getMaquetteParcoursAnnee() {
        return (MaquetteParcoursAnneeROVOImpl) findViewObject("MaquetteParcoursAnnee");
    }

    /**
     * Container's getter for ParcoursRespFrMaqAnVL1.
     * @return ParcoursRespFrMaqAnVL1
     */
    public ViewLinkImpl getParcoursRespFrMaqAnVL1() {
        return (ViewLinkImpl) findViewLink("ParcoursRespFrMaqAnVL1");
    }

    /**
     * Container's getter for InscriptionRefROVO1.
     * @return InscriptionRefROVO1
     */
    public InscriptionRefROVOImpl getInscriptionRef() {
        return (InscriptionRefROVOImpl) findViewObject("InscriptionRef");
    }

    /**
     * Container's getter for EtudiantScolRefROVO1.
     * @return EtudiantScolRefROVO1
     */
    public EtudiantScolRefROVOImpl getEtudiantScolRef() {
        return (EtudiantScolRefROVOImpl) findViewObject("EtudiantScolRef");
    }

    /**
     * Container's getter for NiveauxDiplomanteROVO1.
     * @return NiveauxDiplomanteROVO1
     */
    public NiveauxDiplomanteROVOImpl getNiveauxDiplomante() {
        return (NiveauxDiplomanteROVOImpl) findViewObject("NiveauxDiplomante");
    }

    /**
     * Container's getter for HistoriqueStrToNivDiplomanteVL1.
     * @return HistoriqueStrToNivDiplomanteVL1
     */
    public ViewLinkImpl getHistoriqueStrToNivDiplomanteVL1() {
        return (ViewLinkImpl) findViewLink("HistoriqueStrToNivDiplomanteVL1");
    }

    /**
     * Container's getter for MaquetteParcoursAnneeROVO1.
     * @return MaquetteParcoursAnneeROVO1
     */
    public MaquetteParcoursAnneeROVOImpl getMaquetteParcoursAnneeDipl() {
        return (MaquetteParcoursAnneeROVOImpl) findViewObject("MaquetteParcoursAnneeDipl");
    }

    /**
     * Container's getter for NivDilpomanteToMaqPrcrsAnVL1.
     * @return NivDilpomanteToMaqPrcrsAnVL1
     */
    public ViewLinkImpl getNivDilpomanteToMaqPrcrsAnVL1() {
        return (ViewLinkImpl) findViewLink("NivDilpomanteToMaqPrcrsAnVL1");
    }

    /**
     * Container's getter for EtudiantsDiplomesROVO1.
     * @return EtudiantsDiplomesROVO1
     */
    public EtudiantsDiplomesROVOImpl getEtudiantsDiplomes() {
        return (EtudiantsDiplomesROVOImpl) findViewObject("EtudiantsDiplomes");
    }

    /**
     * Container's getter for MaqPrcrsToEtdDiplomesVL1.
     * @return MaqPrcrsToEtdDiplomesVL1
     */
    public ViewLinkImpl getMaqPrcrsToEtdDiplomesVL1() {
        return (ViewLinkImpl) findViewLink("MaqPrcrsToEtdDiplomesVL1");
    }
}
