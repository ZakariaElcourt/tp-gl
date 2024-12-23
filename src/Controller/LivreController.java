package Controller;

import DAO.GenericDAO;
import DAO.LivreDAOImpl;
import Model.Livre;
import View.LivreView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LivreController {
    private final LivreView view;
    private final GenericDAO<Livre> dao;

    public LivreController(LivreView view) {
        this.view = view;
        this.dao = new LivreDAOImpl();

        // Écouteurs pour les boutons de la vue
        view.addButton.addActionListener(e -> addLivre());
        view.listButton.addActionListener(e -> listLivres());
        view.deleteButton.addActionListener(e -> deleteLivre());
        view.modifyButton.addActionListener(e -> modifyLivre());

        // Ajouter un écouteur de sélection sur la table des livres
        view.livreTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.livreTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Remplir les champs de modification avec les données sélectionnées
                    int id = (int) view.livreTable.getValueAt(selectedRow, 0);
                    String titre = (String) view.livreTable.getValueAt(selectedRow, 1);
                    String auteur = (String) view.livreTable.getValueAt(selectedRow, 2);
                    String categorie = (String) view.livreTable.getValueAt(selectedRow, 3);

                    view.titreField.setText(titre);
                    view.auteurField.setText(auteur);
                    view.categorieField.setText(categorie);
                    view.modifyButton.setActionCommand(String.valueOf(id)); // Stocker l'ID dans le bouton
                }
            }
        });

        // Initialisation de la table
        initializeTable();

        // Charger la liste des livres au démarrage
        listLivres();
    }

    // Ajouter un livre
    private void addLivre() {
        try {
            String titre = view.titreField.getText().trim();
            String auteur = view.auteurField.getText().trim();
            String categorie = view.categorieField.getText().trim();

            // Validation des champs
            if (titre.isEmpty() || auteur.isEmpty() || categorie.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                return;
            }

            Livre livre = new Livre(titre, auteur, categorie);
            dao.add(livre);

            JOptionPane.showMessageDialog(view, "Livre ajouté avec succès.");
            listLivres(); // Rafraîchir la liste
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Lister tous les livres
    private void listLivres() {
        List<Livre> livres = dao.listAll();
        DefaultTableModel model = (DefaultTableModel) view.livreTable.getModel();
        model.setRowCount(0); // Effacer les anciennes lignes

        for (Livre livre : livres) {
            Object[] row = {livre.getId(), livre.getTitre(), livre.getAuteur(), livre.getCategorie()};
            model.addRow(row);
        }
    }

    // Supprimer un livre
    private void deleteLivre() {
        try {
            int selectedRow = view.livreTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un livre à supprimer.");
                return;
            }

            int id = (int) view.livreTable.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Êtes-vous sûr de vouloir supprimer le livre avec l'ID " + id + " ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(id);
                JOptionPane.showMessageDialog(view, "Livre supprimé avec succès.");
                listLivres();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Modifier un livre
    private void modifyLivre() {
        try {
            String actionCommand = view.modifyButton.getActionCommand();
            if (actionCommand != null && !actionCommand.trim().isEmpty()) {
                int id = Integer.parseInt(actionCommand.trim());

                String titre = view.titreField.getText().trim();
                String auteur = view.auteurField.getText().trim();
                String categorie = view.categorieField.getText().trim();

                // Validation des champs
                if (titre.isEmpty() || auteur.isEmpty() || categorie.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                    return;
                }

                Livre livre = new Livre(titre, auteur, categorie);
                dao.update(livre, id);

                JOptionPane.showMessageDialog(view, "Livre mis à jour avec succès.");
                listLivres();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un livre à modifier.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Initialiser la table
    private void initializeTable() {
        String[] columnNames = {"ID", "Titre", "Auteur", "Catégorie"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        view.livreTable.setModel(model);
    }

    // Effacer les champs de la vue
    private void clearFields() {
        view.titreField.setText("");
        view.auteurField.setText("");
        view.categorieField.setText("");
        view.modifyButton.setActionCommand(""); // Effacer l'ID stocké
    }
}
