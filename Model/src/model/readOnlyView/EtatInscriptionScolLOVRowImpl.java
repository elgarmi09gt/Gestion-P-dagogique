package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Dec 06 15:48:31 CET 2023
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EtatInscriptionScolLOVRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdEtatsInscription {
            protected Object get(EtatInscriptionScolLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtatInscriptionScolLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Libelle {
            protected Object get(EtatInscriptionScolLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtatInscriptionScolLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AncienCode {
            protected Object get(EtatInscriptionScolLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtatInscriptionScolLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(EtatInscriptionScolLOVRowImpl object);

        protected abstract void put(EtatInscriptionScolLOVRowImpl object, Object value);

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

    public static final int IDETATSINSCRIPTION = AttributesEnum.IdEtatsInscription.index();
    public static final int LIBELLE = AttributesEnum.Libelle.index();
    public static final int ANCIENCODE = AttributesEnum.AncienCode.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EtatInscriptionScolLOVRowImpl() {
    }
}

