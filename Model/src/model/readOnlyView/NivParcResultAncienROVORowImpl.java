package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jan 13 10:55:21 GMT 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NivParcResultAncienROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdNiveauFormationParcours {
            protected Object get(NivParcResultAncienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NivParcResultAncienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Libform {
            protected Object get(NivParcResultAncienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NivParcResultAncienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdHistoriquesStructure {
            protected Object get(NivParcResultAncienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NivParcResultAncienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdUtilisateur {
            protected Object get(NivParcResultAncienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NivParcResultAncienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnneesUnivers {
            protected Object get(NivParcResultAncienROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NivParcResultAncienROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(NivParcResultAncienROVORowImpl object);

        protected abstract void put(NivParcResultAncienROVORowImpl object, Object value);

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


    public static final int IDNIVEAUFORMATIONPARCOURS = AttributesEnum.IdNiveauFormationParcours.index();
    public static final int LIBFORM = AttributesEnum.Libform.index();
    public static final int IDHISTORIQUESSTRUCTURE = AttributesEnum.IdHistoriquesStructure.index();
    public static final int IDUTILISATEUR = AttributesEnum.IdUtilisateur.index();
    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();

    /**
     * This is the default constructor (do not remove).
     */
    public NivParcResultAncienROVORowImpl() {
    }
}

