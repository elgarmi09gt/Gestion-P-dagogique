package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 19 16:51:25 UTC 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class isFilUeExistROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdFiliereUeSemstre {
            protected Object get(isFilUeExistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(isFilUeExistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(isFilUeExistROVORowImpl object);

        protected abstract void put(isFilUeExistROVORowImpl object, Object value);

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
    public static final int IDFILIEREUESEMSTRE = AttributesEnum.IdFiliereUeSemstre.index();

    /**
     * This is the default constructor (do not remove).
     */
    public isFilUeExistROVORowImpl() {
    }
}

