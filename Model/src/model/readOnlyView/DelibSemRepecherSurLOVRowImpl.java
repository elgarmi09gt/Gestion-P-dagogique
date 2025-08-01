package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Oct 16 09:34:00 GMT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DelibSemRepecherSurLOVRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        coderep {
            protected Object get(DelibSemRepecherSurLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DelibSemRepecherSurLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        libellerep {
            protected Object get(DelibSemRepecherSurLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DelibSemRepecherSurLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DelibSemRepecherSurLOV1 {
            protected Object get(DelibSemRepecherSurLOVRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DelibSemRepecherSurLOVRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(DelibSemRepecherSurLOVRowImpl object);

        protected abstract void put(DelibSemRepecherSurLOVRowImpl object, Object value);

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


    public static final int CODEREP = AttributesEnum.coderep.index();
    public static final int LIBELLEREP = AttributesEnum.libellerep.index();
    public static final int DELIBSEMREPECHERSURLOV1 = AttributesEnum.DelibSemRepecherSurLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DelibSemRepecherSurLOVRowImpl() {
    }
}

