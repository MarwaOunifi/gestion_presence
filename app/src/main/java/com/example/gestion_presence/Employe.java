package com.example.gestion_presence;

public class Employe {
    private String nom;
    private String poste;
    private double salaire;
    private int id;



    public Employe(String nom, String poste, double salaire) {
        this.id = id;
        this.nom = nom;
        this.poste = poste;
        this.salaire = salaire;
    }

    public String getNom() { return nom; }
    public String getPoste() { return poste; }
    public double getSalaire() { return salaire; }
    public int getId() {
        return id;
    }



}
