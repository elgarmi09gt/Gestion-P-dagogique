package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 10 13:58:53 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FormNivFormFilUeROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdFormation {
            protected Object get(FormNivFormFilUeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(FormNivFormFilUeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormation {
            protected Object get(FormNivFormFilUeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(FormNivFormFilUeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdFiliereUeSemstre {
            protected Object get(FormNivFormFilUeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(FormNivFormFilUeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Codification {
            protected Object get(FormNivFormFilUeROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(FormNivFormFilUeROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(FormNivFormFilUeROVORowImpl object);

        protected abstract void put(FormNivFormFilUeROVORowImpl object, Object value);

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

    public static final int IDFORMATION = AttributesEnum.IdFormation.index();
    public static final int IDNIVEAUFORMATION = AttributesEnum.IdNiveauFormation.index();
    public static final int IDFILIEREUESEMSTRE = AttributesEnum.IdFiliereUeSemstre.index();
    public static final int CODIFICATION = AttributesEnum.Codification.index();

    /**
     * This is the default constructor (do not remove).
     */
    public FormNivFormFilUeROVORowImpl() {
    }
}

