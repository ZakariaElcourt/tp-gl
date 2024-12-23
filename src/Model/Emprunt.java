package Model;

import java.time.LocalDate;

public class Emprunt {
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
    public String toString() {
        return "Emprunt [Membre=" + membre.getNom() + ", Livre=" + livre.getTitre() +
                ", Date Emprunt=" + dateEmprunt + ", Date Retour=" + dateRetour + "]";
    }
}
