package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Aug 21 15:44:22 UTC 2024
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SexeLOVRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdSexe {
            protected Object get(SexeLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(SexeLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(SexeLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(SexeLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(SexeLOVRowImpl object);

        protected abstract void put(SexeLOVRowImpl object, Object value);

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
    public static final int IDSEXE = AttributesEnum.IdSexe.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SexeLOVRowImpl() {
    }
}

