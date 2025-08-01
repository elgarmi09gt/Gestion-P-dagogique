package model.readOnlyView;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Mar 02 10:13:17 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DepartementsEtabROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdHistoriquesStructure {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleCourt {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AncienCode {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStructures {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParcoursSaisieMemoireROVO {
            protected Object get(DepartementsEtabROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DepartementsEtabROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(DepartementsEtabROVORowImpl object);

        protected abstract void put(DepartementsEtabROVORowImpl object, Object value);

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
    public static final int ANCIENCODE = AttributesEnum.AncienCode.index();
    public static final int IDSTRUCTURES = AttributesEnum.IdStructures.index();
    public static final int PARCOURSSAISIEMEMOIREROVO = AttributesEnum.ParcoursSaisieMemoireROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DepartementsEtabROVORowImpl() {
    }
}

