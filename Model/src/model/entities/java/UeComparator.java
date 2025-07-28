package model.entities.java;

import java.util.Comparator;

public class UeComparator implements Comparator<Ue> {
    public UeComparator() {
        super();
    }

    @Override
    public int compare(Ue ue1, Ue ue2) {
        // TODO Implement this method
        return ue1.getCodification().compareTo(ue2.getCodification());
        /*
         *         if(0 == (etd1.getNom().compareTo(etd2.getNom()))){
            if(0 == (etd1.getPrenom().compareTo(etd2.getPrenom()))){
                return etd1.getNumero().compareTo(etd2.getNumero());
            }
            return etd1.getPrenom().compareTo(etd2.getPrenom());
        }
        return etd1.getNom().compareTo(etd2.getNom());
         * */
    }
}
