package model.readOnlyView;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 03 14:29:57 CEST 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class InscPedagogiqueROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdInscriptionPedagogique {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionAdmin {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdHorairesTd {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStatut {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdOption {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdBourse {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdCohorte {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeConvention {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdOperateur {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Motif {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Redoublement {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EtatInscrEtatInscrId {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Quittance {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Tarif {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NbInsc {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DernierInscription {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PermetDoubleInscription {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Assimile {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Cartetiree {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrdreInscription {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CodeAConserver {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateEditionCarte {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateCreation {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DateModification {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiCree {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiModifie {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UtiPermetDoublIns {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdParcoursMaquetAnnee {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        InsEnLigne {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DroitInsAdm {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DroitInsPed {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CoutFormation {
            protected Object get(InscPedagogiqueROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(InscPedagogiqueROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(InscPedagogiqueROVORowImpl object);

        protected abstract void put(InscPedagogiqueROVORowImpl object, Object value);

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

    public static final int IDINSCRIPTIONPEDAGOGIQUE = AttributesEnum.IdInscriptionPedagogique.index();
    public static final int IDINSCRIPTIONADMIN = AttributesEnum.IdInscriptionAdmin.index();
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
    public static final int DATECREATION = AttributesEnum.DateCreation.index();
    public static final int DATEMODIFICATION = AttributesEnum.DateModification.index();
    public static final int UTICREE = AttributesEnum.UtiCree.index();
    public static final int UTIMODIFIE = AttributesEnum.UtiModifie.index();
    public static final int UTIPERMETDOUBLINS = AttributesEnum.UtiPermetDoublIns.index();
    public static final int IDPARCOURSMAQUETANNEE = AttributesEnum.IdParcoursMaquetAnnee.index();
    public static final int INSENLIGNE = AttributesEnum.InsEnLigne.index();
    public static final int DROITINSADM = AttributesEnum.DroitInsAdm.index();
    public static final int DROITINSPED = AttributesEnum.DroitInsPed.index();
    public static final int COUTFORMATION = AttributesEnum.CoutFormation.index();

    /**
     * This is the default constructor (do not remove).
     */
    public InscPedagogiqueROVORowImpl() {
    }
}

