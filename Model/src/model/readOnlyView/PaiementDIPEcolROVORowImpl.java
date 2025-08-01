package model.readOnlyView;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 03 15:44:22 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PaiementDIPEcolROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdAnneesUnivers {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdEchelonPaiement {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleEchelon {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DatePaiement {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Paye {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Reparti {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NetAPayer {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdPaiement {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdInscriptionPedagogique {
            protected Object get(PaiementDIPEcolROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(PaiementDIPEcolROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(PaiementDIPEcolROVORowImpl object);

        protected abstract void put(PaiementDIPEcolROVORowImpl object, Object value);

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

    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();
    public static final int IDECHELONPAIEMENT = AttributesEnum.IdEchelonPaiement.index();
    public static final int LIBELLEECHELON = AttributesEnum.LibelleEchelon.index();
    public static final int DATEPAIEMENT = AttributesEnum.DatePaiement.index();
    public static final int PAYE = AttributesEnum.Paye.index();
    public static final int REPARTI = AttributesEnum.Reparti.index();
    public static final int NETAPAYER = AttributesEnum.NetAPayer.index();
    public static final int IDPAIEMENT = AttributesEnum.IdPaiement.index();
    public static final int IDINSCRIPTIONPEDAGOGIQUE = AttributesEnum.IdInscriptionPedagogique.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PaiementDIPEcolROVORowImpl() {
    }
}

