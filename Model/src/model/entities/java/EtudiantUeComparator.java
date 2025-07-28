package model.entities.java;

import java.util.Comparator;

public class EtudiantUeComparator implements Comparator<EtudiantUe> {
    public EtudiantUeComparator() {
        super();
    }

    @Override
    public int compare(EtudiantUe etd1, EtudiantUe etd2) {
        // TODO Implement this method
        if (0 == (etd1.getNom().compareTo(etd2.getNom()))) {
            if (0 == (etd1.getPrenom().compareTo(etd2.getPrenom()))) {
                return etd1.getNumero().compareTo(etd2.getNumero());
            }
            return etd1.getPrenom().compareTo(etd2.getPrenom());
        }
        return etd1.getNom().compareTo(etd2.getNom());
    }
}
