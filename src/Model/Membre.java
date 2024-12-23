package Model;

public class Membre {
    private int id;
    private String nom;
    private String adresse;
    private String telephone;

    public Membre(int id, String nom, String adresse, String telephone) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }
    public Membre(String nom) {
        this.nom = nom;
    }
    public Membre(String nom, String adresse, String telephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Membre [ID=" + id + ", Nom=" + nom + ", Adresse=" + adresse + ", Téléphone=" + telephone + "]";
    }
}
