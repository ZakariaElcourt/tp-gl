package Model;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;

    // Constructeur avec tous les paramètres
    public Livre(int id, String titre, String auteur, String categorie) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
    }
    public Livre(String titre) {
        this.titre = titre;
    }

    // Nouveau constructeur sans l'ID
    public Livre(String titre, String auteur, String categorie) {
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Livre [ID=" + id + ", Titre=" + titre + ", Auteur=" + auteur + ", Categorie=" + categorie + "]";
    }
}

