package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 06 13:11:02 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class isUserResponsableFilEcRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdUtilisFilierUeSemEc {
            protected Object get(isUserResponsableFilEcRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(isUserResponsableFilEcRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdFiliereUeSemstreEc {
            protected Object get(isUserResponsableFilEcRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(isUserResponsableFilEcRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(isUserResponsableFilEcRowImpl object);

        protected abstract void put(isUserResponsableFilEcRowImpl object, Object value);

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
    public static final int IDUTILISFILIERUESEMEC = AttributesEnum.IdUtilisFilierUeSemEc.index();
    public static final int IDFILIEREUESEMSTREEC = AttributesEnum.IdFiliereUeSemstreEc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public isUserResponsableFilEcRowImpl() {
    }
}

