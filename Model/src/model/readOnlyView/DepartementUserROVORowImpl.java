package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 11 11:08:23 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DepartementUserROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdHistoriquesStructure {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleCourt {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStructure {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        StructureAccesByUsername {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParcoursUserROVO {
            protected Object get(DepartementUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(DepartementUserROVORowImpl object);

        protected abstract void put(DepartementUserROVORowImpl object, Object value);

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


    public static final int IDHISTORIQUESSTRUCTURE = AttributesEnum.IdHistoriquesStructure.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int LIBELLECOURT = AttributesEnum.LibelleCourt.index();
    public static final int IDSTRUCTURE = AttributesEnum.IdStructure.index();
    public static final int STRUCTUREACCESBYUSERNAME = AttributesEnum.StructureAccesByUsername.index();
    public static final int PARCOURSUSERROVO = AttributesEnum.ParcoursUserROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DepartementUserROVORowImpl() {
    }
}

