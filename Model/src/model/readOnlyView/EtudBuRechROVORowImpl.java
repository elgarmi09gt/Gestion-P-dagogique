package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Mar 05 10:30:43 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EtudBuRechROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        EnRegle {
            protected Object get(EtudBuRechROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtudBuRechROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnneesUnivers {
            protected Object get(EtudBuRechROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtudBuRechROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdEtudiant {
            protected Object get(EtudBuRechROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtudBuRechROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdEtudiantBu {
            protected Object get(EtudBuRechROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtudBuRechROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdStructure {
            protected Object get(EtudBuRechROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(EtudBuRechROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(EtudBuRechROVORowImpl object);

        protected abstract void put(EtudBuRechROVORowImpl object, Object value);

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

    public static final int ENREGLE = AttributesEnum.EnRegle.index();
    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();
    public static final int IDETUDIANT = AttributesEnum.IdEtudiant.index();
    public static final int IDETUDIANTBU = AttributesEnum.IdEtudiantBu.index();
    public static final int IDSTRUCTURE = AttributesEnum.IdStructure.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EtudBuRechROVORowImpl() {
    }
}

