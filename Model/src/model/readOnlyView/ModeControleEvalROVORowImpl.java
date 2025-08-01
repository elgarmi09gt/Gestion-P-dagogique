package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 28 10:11:37 GMT 2020
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ModeControleEvalROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdFiliereUeSemstreEc {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdModeControleEc {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdTypeControle {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdModeControle {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdParcoursMaquetteAnnee {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EcEvalROVO {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TypeControleVO {
            protected Object get(ModeControleEvalROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(ModeControleEvalROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(ModeControleEvalROVORowImpl object);

        protected abstract void put(ModeControleEvalROVORowImpl object, Object value);

        protected int index() {
            return ModeControleEvalROVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return ModeControleEvalROVORowImpl.AttributesEnum.firstIndex() + ModeControleEvalROVORowImpl.AttributesEnum
                                                                                                        .staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = ModeControleEvalROVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int IDFILIEREUESEMSTREEC = AttributesEnum.IdFiliereUeSemstreEc.index();
    public static final int IDMODECONTROLEEC = AttributesEnum.IdModeControleEc.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int IDTYPECONTROLE = AttributesEnum.IdTypeControle.index();
    public static final int IDMODECONTROLE = AttributesEnum.IdModeControle.index();
    public static final int IDPARCOURSMAQUETTEANNEE = AttributesEnum.IdParcoursMaquetteAnnee.index();
    public static final int ECEVALROVO = AttributesEnum.EcEvalROVO.index();
    public static final int TYPECONTROLEVO = AttributesEnum.TypeControleVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ModeControleEvalROVORowImpl() {
    }
}

