package model.entities.java;

import java.io.Serializable;

import java.util.ArrayList;

public class EudiantsUes implements Serializable{
    
    private ArrayList<Etudiant> etudiants;
    private ArrayList<Ue> listeUes;
    
    public EudiantsUes() {
        super();
    }

    public void setListeUes(ArrayList<Ue> listeUes) {
        this.listeUes = listeUes;
    }

    public ArrayList<Ue> getListeUes() {
        return listeUes;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

}
