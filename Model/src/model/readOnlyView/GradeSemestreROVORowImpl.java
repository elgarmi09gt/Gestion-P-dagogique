package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Dec 24 15:14:52 UTC 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GradeSemestreROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdSemestre {
            protected Object get(GradeSemestreROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(GradeSemestreROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleLong {
            protected Object get(GradeSemestreROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(GradeSemestreROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LibelleCourt {
            protected Object get(GradeSemestreROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(GradeSemestreROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormationParcours {
            protected Object get(GradeSemestreROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(GradeSemestreROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParcoursUserROVO {
            protected Object get(GradeSemestreROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(GradeSemestreROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(GradeSemestreROVORowImpl object);

        protected abstract void put(GradeSemestreROVORowImpl object, Object value);

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


    public static final int IDSEMESTRE = AttributesEnum.IdSemestre.index();
    public static final int LIBELLELONG = AttributesEnum.LibelleLong.index();
    public static final int LIBELLECOURT = AttributesEnum.LibelleCourt.index();
    public static final int IDNIVEAUFORMATIONPARCOURS = AttributesEnum.IdNiveauFormationParcours.index();
    public static final int PARCOURSUSERROVO = AttributesEnum.ParcoursUserROVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GradeSemestreROVORowImpl() {
    }
}

