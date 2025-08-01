package model.readOnlyView;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 12 12:55:27 UTC 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NiveauFormationMqAnHistROVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        IdMaquette {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdAnneesUnivers {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Maquette {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Annee {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IdNiveauFormation {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NiveauFormationRO {
            protected Object get(NiveauFormationMqAnHistROVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(NiveauFormationMqAnHistROVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(NiveauFormationMqAnHistROVORowImpl object);

        protected abstract void put(NiveauFormationMqAnHistROVORowImpl object, Object value);

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

    public static final int IDMAQUETTE = AttributesEnum.IdMaquette.index();
    public static final int IDANNEESUNIVERS = AttributesEnum.IdAnneesUnivers.index();
    public static final int MAQUETTE = AttributesEnum.Maquette.index();
    public static final int ANNEE = AttributesEnum.Annee.index();
    public static final int IDNIVEAUFORMATION = AttributesEnum.IdNiveauFormation.index();
    public static final int NIVEAUFORMATIONRO = AttributesEnum.NiveauFormationRO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public NiveauFormationMqAnHistROVORowImpl() {
    }
}

