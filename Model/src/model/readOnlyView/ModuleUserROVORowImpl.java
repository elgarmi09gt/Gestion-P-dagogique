package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 21 16:40:00 UTC 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ModuleUserROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdModule {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CheminImage {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdUtilisateur {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Application {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdApplication {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FontionalitesUserROVO {
            protected Object get(ModuleUserROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModuleUserROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        ;
        private static final int firstIndex = 0;

        protected abstract Object get(ModuleUserROVORowImpl object);

        protected abstract void put(ModuleUserROVORowImpl object, Object value);

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


    public static final int IDMODULE = AttributesEnum.IdModule.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int CHEMINIMAGE = AttributesEnum.CheminImage.index();
    public static final int IDUTILISATEUR = AttributesEnum.IdUtilisateur.index();
    public static final int APPLICATION = AttributesEnum.Application.index();
    public static final int IDAPPLICATION = AttributesEnum.IdApplication.index();
    public static final int FONTIONALITESUSERROVO = AttributesEnum.FontionalitesUserROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ModuleUserROVORowImpl() {
    }
}

