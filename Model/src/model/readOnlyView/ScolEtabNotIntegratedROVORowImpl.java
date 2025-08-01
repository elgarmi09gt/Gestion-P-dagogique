package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 19 09:01:51 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ScolEtabNotIntegratedROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CodeScol {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Libelle {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Sigle {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Telephone {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Adresse {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TypeEtab {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Libgrh {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Codegrh {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        isEtabSelected {
            protected Object get(ScolEtabNotIntegratedROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtabNotIntegratedROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ScolEtabNotIntegratedROVORowImpl object);

        protected abstract void put(ScolEtabNotIntegratedROVORowImpl object, Object value);

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


    public static final int CODESCOL = AttributesEnum.CodeScol.index();
    public static final int LIBELLE = AttributesEnum.Libelle.index();
    public static final int SIGLE = AttributesEnum.Sigle.index();
    public static final int TELEPHONE = AttributesEnum.Telephone.index();
    public static final int ADRESSE = AttributesEnum.Adresse.index();
    public static final int TYPEETAB = AttributesEnum.TypeEtab.index();
    public static final int LIBGRH = AttributesEnum.Libgrh.index();
    public static final int CODEGRH = AttributesEnum.Codegrh.index();
    public static final int ISETABSELECTED = AttributesEnum.isEtabSelected.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ScolEtabNotIntegratedROVORowImpl() {
    }
}

