package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Mar 15 11:50:35 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ScolEtablissementRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CodeScol {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Libelle {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Sigle {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Telephone {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Adresse {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TypeEtab {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ScolDepartement {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ScolDeptDetailDapROVO {
            protected Object get(ScolEtablissementRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ScolEtablissementRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ScolEtablissementRowImpl object);

        protected abstract void put(ScolEtablissementRowImpl object, Object value);

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
    public static final int SCOLDEPARTEMENT = AttributesEnum.ScolDepartement.index();
    public static final int SCOLDEPTDETAILDAPROVO = AttributesEnum.ScolDeptDetailDapROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ScolEtablissementRowImpl() {
    }
}

