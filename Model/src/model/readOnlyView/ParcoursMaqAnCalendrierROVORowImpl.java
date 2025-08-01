package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 18 15:38:57 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ParcoursMaqAnCalendrierROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdCalendrierDeliberation {
            protected Object get(ParcoursMaqAnCalendrierROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursMaqAnCalendrierROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdParcoursMaquetAnnee {
            protected Object get(ParcoursMaqAnCalendrierROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ParcoursMaqAnCalendrierROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ParcoursMaqAnCalendrierROVORowImpl object);

        protected abstract void put(ParcoursMaqAnCalendrierROVORowImpl object, Object value);

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


    public static final int IDCALENDRIERDELIBERATION = AttributesEnum.IdCalendrierDeliberation.index();
    public static final int IDPARCOURSMAQUETANNEE = AttributesEnum.IdParcoursMaquetAnnee.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ParcoursMaqAnCalendrierROVORowImpl() {
    }
}

