package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 21 08:22:43 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AdminByEtablissementROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdUtilisateurRole {
            protected Object get(AdminByEtablissementROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AdminByEtablissementROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Prenoms {
            protected Object get(AdminByEtablissementROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AdminByEtablissementROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Nom {
            protected Object get(AdminByEtablissementROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AdminByEtablissementROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdRole {
            protected Object get(AdminByEtablissementROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AdminByEtablissementROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdUtilisateur {
            protected Object get(AdminByEtablissementROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(AdminByEtablissementROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(AdminByEtablissementROVORowImpl object);

        protected abstract void put(AdminByEtablissementROVORowImpl object, Object value);

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

    public static final int IDUTILISATEURROLE = AttributesEnum.IdUtilisateurRole.index();
    public static final int PRENOMS = AttributesEnum.Prenoms.index();
    public static final int NOM = AttributesEnum.Nom.index();
    public static final int IDROLE = AttributesEnum.IdRole.index();
    public static final int IDUTILISATEUR = AttributesEnum.IdUtilisateur.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AdminByEtablissementROVORowImpl() {
    }
}

