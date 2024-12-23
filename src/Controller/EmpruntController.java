package Controller;

import DAO.GenericDAO;
import DAO.EmpruntDAOImpl;
import Model.Emprunt;
import Model.Livre;
import Model.Membre;
import View.EmpruntView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class EmpruntController {
    private final EmpruntView view;
    private final GenericDAO<Emprunt> dao;

    public EmpruntController(EmpruntView view) {
        this.view = view;
        this.dao = new EmpruntDAOImpl();

        // Écouteurs pour les boutons de la vue
        view.addButton.addActionListener(e -> addEmprunt());
        view.listButton.addActionListener(e -> listEmprunts());
        view.deleteButton.addActionListener(e -> deleteEmprunt());
        view.modifyButton.addActionListener(e -> modifyEmprunt());

        // Ajouter un écouteur de sélection sur la table des emprunts
        view.empruntTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.empruntTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Remplir les champs de modification avec les données sélectionnées
                    int id = (int) view.empruntTable.getValueAt(selectedRow, 0);
                    String membreName = (String) view.empruntTable.getValueAt(selectedRow, 1);
                    String livreTitle = (String) view.empruntTable.getValueAt(selectedRow, 2);
                    LocalDate dateEmprunt = (LocalDate) view.empruntTable.getValueAt(selectedRow, 3);
                    LocalDate dateRetour = (LocalDate) view.empruntTable.getValueAt(selectedRow, 4);

                    view.membreField.setText(membreName);
                    view.livreField.setText(livreTitle);
                    view.dateEmpruntField.setText(dateEmprunt.toString());
                    view.dateRetourField.setText(dateRetour.toString());
                    view.modifyButton.setActionCommand(String.valueOf(id)); // Stocker l'ID dans le bouton
                }
            }
        });

        // Initialisation de la table
        initializeTable();

        // Charger la liste des emprunts au démarrage
        listEmprunts();
    }

    // Ajouter un emprunt
    private void addEmprunt() {
        try {
            String membreName = view.membreField.getText().trim();
            String livreTitle = view.livreField.getText().trim();
            String dateEmpruntStr = view.dateEmpruntField.getText().trim();
            String dateRetourStr = view.dateRetourField.getText().trim();

            // Validation des champs
            if (membreName.isEmpty() || livreTitle.isEmpty() || dateEmpruntStr.isEmpty() || dateRetourStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                return;
            }

            // Assuming Membre and Livre have a way to fetch by name/title or ID
            Membre membre = new Membre(membreName);  // Fetch from DB or input fields
            Livre livre = new Livre(livreTitle);    // Same for Livre

            LocalDate dateEmprunt = LocalDate.parse(dateEmpruntStr);
            LocalDate dateRetour = LocalDate.parse(dateRetourStr);

            Emprunt emprunt = new Emprunt(membre, livre, dateEmprunt, dateRetour);
            dao.add(emprunt);

            JOptionPane.showMessageDialog(view, "Emprunt ajouté avec succès.");
            listEmprunts(); // Rafraîchir la liste
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Lister tous les emprunts
    private void listEmprunts() {
        List<Emprunt> emprunts = dao.listAll();
        DefaultTableModel model = (DefaultTableModel) view.empruntTable.getModel();
        model.setRowCount(0); // Effacer les anciennes lignes

        for (Emprunt emprunt : emprunts) {
            Object[] row = {emprunt.getMembre().getNom(), emprunt.getLivre().getTitre(),
                    emprunt.getDateEmprunt(), emprunt.getDateRetour()};
            model.addRow(row);
        }
    }

    // Supprimer un emprunt
    private void deleteEmprunt() {
        try {
            int selectedRow = view.empruntTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un emprunt à supprimer.");
                return;
            }

            int id = (int) view.empruntTable.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Êtes-vous sûr de vouloir supprimer l'emprunt avec l'ID " + id + " ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(id);
                JOptionPane.showMessageDialog(view, "Emprunt supprimé avec succès.");
                listEmprunts();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Modifier un emprunt
    private void modifyEmprunt() {
        try {
            String actionCommand = view.modifyButton.getActionCommand();
            if (actionCommand != null && !actionCommand.trim().isEmpty()) {
                int id = Integer.parseInt(actionCommand.trim());

                String membreName = view.membreField.getText().trim();
                String livreTitle = view.livreField.getText().trim();
                String dateEmpruntStr = view.dateEmpruntField.getText().trim();
                String dateRetourStr = view.dateRetourField.getText().trim();

                // Validation des champs
                if (membreName.isEmpty() || livreTitle.isEmpty() || dateEmpruntStr.isEmpty() || dateRetourStr.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                    return;
                }

                Membre membre = new Membre(membreName);  // Fetch from DB or input fields
                Livre livre = new Livre(livreTitle);    // Same for Livre
                LocalDate dateEmprunt = LocalDate.parse(dateEmpruntStr);
                LocalDate dateRetour = LocalDate.parse(dateRetourStr);

                Emprunt emprunt = new Emprunt(membre, livre, dateEmprunt, dateRetour);
                dao.update(emprunt, id);

                JOptionPane.showMessageDialog(view, "Emprunt mis à jour avec succès.");
                listEmprunts();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un emprunt à modifier.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Initialiser la table
    private void initializeTable() {
        String[] columnNames = {"Membre", "Livre", "Date Emprunt", "Date Retour"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        view.empruntTable.setModel(model);
    }

    // Effacer les champs de la vue
    private void clearFields() {
        view.membreField.setText("");
        view.livreField.setText("");
        view.dateEmpruntField.setText("");
        view.dateRetourField.setText("");
        view.modifyButton.setActionCommand(""); // Effacer l'ID stocké
    }
}
