package model.readOnlyView;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Dec 14 10:42:31 GMT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class reInscripAdminPedROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdInscriptionAdmin {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeFormation {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnneesUnivers {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdGrade {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdEtudiant {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateCreation {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateModification {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiCree {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiModifie {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionPedagogique {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionAdmin1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdHorairesTd {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStatut {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdOption {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdBourse {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdCohorte {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeConvention {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdOperateur {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Motif {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Redoublement {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EtatInscrEtatInscrId {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Quittance {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Tarif {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NbInsc {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DernierInscription {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PermetDoubleInscription {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Assimile {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Cartetiree {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrdreInscription {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CodeAConserver {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateEditionCarte {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateCreation1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateModification1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiCree1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiModifie1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiPermetDoublIns {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdParcoursMaquetAnnee {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionPedSemestre {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionPedagogique1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdSemestre {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        InsPedValide {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PassExam {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CumulCredit {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DetteCredit {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Maj {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Maj1 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateCreation2 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateModification2 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiCree2 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiModifie2 {
            protected Object get(reInscripAdminPedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(reInscripAdminPedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(reInscripAdminPedROVORowImpl object);

        protected abstract void put(reInscripAdminPedROVORowImpl object, Object value);

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int IDINSCRIPTIONADMIN = AttributesEnum.IdInscriptionAdmin.index();
    public static final int IDTYPEFORMATION = AttributesEnum.IdTypeFormation.index();
    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();
    public static final int IDGRADE = AttributesEnum.IdGrade.index();
    public static final int IDETUDIANT = AttributesEnum.IdEtudiant.index();
    public static final int DATECREATION = AttributesEnum.DateCreation.index();
    public static final int DATEMODIFICATION = AttributesEnum.DateModification.index();
    public static final int UTICREE = AttributesEnum.UtiCree.index();
    public static final int UTIMODIFIE = AttributesEnum.UtiModifie.index();
    public static final int IDINSCRIPTIONPEDAGOGIQUE = AttributesEnum.IdInscriptionPedagogique.index();
    public static final int IDINSCRIPTIONADMIN1 = AttributesEnum.IdInscriptionAdmin1.index();
    public static final int IDHORAIRESTD = AttributesEnum.IdHorairesTd.index();
    public static final int IDSTATUT = AttributesEnum.IdStatut.index();
    public static final int IDOPTION = AttributesEnum.IdOption.index();
    public static final int IDBOURSE = AttributesEnum.IdBourse.index();
    public static final int IDCOHORTE = AttributesEnum.IdCohorte.index();
    public static final int IDTYPECONVENTION = AttributesEnum.IdTypeConvention.index();
    public static final int IDOPERATEUR = AttributesEnum.IdOperateur.index();
    public static final int MOTIF = AttributesEnum.Motif.index();
    public static final int REDOUBLEMENT = AttributesEnum.Redoublement.index();
    public static final int ETATINSCRETATINSCRID = AttributesEnum.EtatInscrEtatInscrId.index();
    public static final int QUITTANCE = AttributesEnum.Quittance.index();
    public static final int TARIF = AttributesEnum.Tarif.index();
    public static final int NBINSC = AttributesEnum.NbInsc.index();
    public static final int DERNIERINSCRIPTION = AttributesEnum.DernierInscription.index();
    public static final int PERMETDOUBLEINSCRIPTION = AttributesEnum.PermetDoubleInscription.index();
    public static final int ASSIMILE = AttributesEnum.Assimile.index();
    public static final int CARTETIREE = AttributesEnum.Cartetiree.index();
    public static final int ORDREINSCRIPTION = AttributesEnum.OrdreInscription.index();
    public static final int CODEACONSERVER = AttributesEnum.CodeAConserver.index();
    public static final int DATEEDITIONCARTE = AttributesEnum.DateEditionCarte.index();
    public static final int DATECREATION1 = AttributesEnum.DateCreation1.index();
    public static final int DATEMODIFICATION1 = AttributesEnum.DateModification1.index();
    public static final int UTICREE1 = AttributesEnum.UtiCree1.index();
    public static final int UTIMODIFIE1 = AttributesEnum.UtiModifie1.index();
    public static final int UTIPERMETDOUBLINS = AttributesEnum.UtiPermetDoublIns.index();
    public static final int IDPARCOURSMAQUETANNEE = AttributesEnum.IdParcoursMaquetAnnee.index();
    public static final int IDINSCRIPTIONPEDSEMESTRE = AttributesEnum.IdInscriptionPedSemestre.index();
    public static final int IDINSCRIPTIONPEDAGOGIQUE1 = AttributesEnum.IdInscriptionPedagogique1.index();
    public static final int IDSEMESTRE = AttributesEnum.IdSemestre.index();
    public static final int INSPEDVALIDE = AttributesEnum.InsPedValide.index();
    public static final int PASSEXAM = AttributesEnum.PassExam.index();
    public static final int CUMULCREDIT = AttributesEnum.CumulCredit.index();
    public static final int DETTECREDIT = AttributesEnum.DetteCredit.index();
    public static final int MAJ = AttributesEnum.Maj.index();
    public static final int MAJ1 = AttributesEnum.Maj1.index();
    public static final int DATECREATION2 = AttributesEnum.DateCreation2.index();
    public static final int DATEMODIFICATION2 = AttributesEnum.DateModification2.index();
    public static final int UTICREE2 = AttributesEnum.UtiCree2.index();
    public static final int UTIMODIFIE2 = AttributesEnum.UtiModifie2.index();

    /**
     * This is the default constructor (do not remove).
     */
    public reInscripAdminPedROVORowImpl() {
    }
}

