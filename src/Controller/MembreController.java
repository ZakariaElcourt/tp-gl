package Controller;

import DAO.GenericDAO;
import DAO.MembreDAOImpl;
import Model.Membre;
import View.MembreView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MembreController {
    private final MembreView view;
    private final GenericDAO<Membre> dao;

    public MembreController(MembreView view) {
        this.view = view;
        this.dao = new MembreDAOImpl();

        // Écouteurs pour les boutons de la vue
        view.addButton.addActionListener(e -> addMembre());
        view.listButton.addActionListener(e -> listMembres());
        view.deleteButton.addActionListener(e -> deleteMembre());
        view.modifyButton.addActionListener(e -> modifyMembre());
        view.searchButton.addActionListener(e -> searchMembreByName());

        // Ajouter un écouteur pour la sélection dans la table
        view.membreTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.membreTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) view.membreTable.getValueAt(selectedRow, 0);
                    String nom = (String) view.membreTable.getValueAt(selectedRow, 1);
                    String adresse = (String) view.membreTable.getValueAt(selectedRow, 2);
                    String telephone = (String) view.membreTable.getValueAt(selectedRow, 3);

                    view.nomField.setText(nom);
                    view.adresseField.setText(adresse);
                    view.telephoneField.setText(telephone);
                    view.modifyButton.setActionCommand(String.valueOf(id)); // Stocker l'ID
                }
            }
        });

        // Initialiser la table
        initializeTable();

        // Charger la liste des membres au démarrage
        listMembres();
    }

    // Ajouter un membre
    private void addMembre() {
        try {
            String nom = view.nomField.getText().trim();
            String adresse = view.adresseField.getText().trim();
            String telephone = view.telephoneField.getText().trim();

            if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                return;
            }

            Membre membre = new Membre(nom, adresse, telephone);
            dao.add(membre);

            JOptionPane.showMessageDialog(view, "Membre ajouté avec succès.");
            listMembres();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Lister tous les membres
    private void listMembres() {
        List<Membre> membres = dao.listAll();
        DefaultTableModel model = (DefaultTableModel) view.membreTable.getModel();
        model.setRowCount(0); // Effacer les anciennes données

        for (Membre membre : membres) {
            Object[] row = {membre.getId(), membre.getNom(), membre.getAdresse(), membre.getTelephone()};
            model.addRow(row);
        }
    }

    // Supprimer un membre
    private void deleteMembre() {
        try {
            int selectedRow = view.membreTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un membre à supprimer.");
                return;
            }

            int id = (int) view.membreTable.getValueAt(selectedRow, 0);
            dao.delete(id);

            JOptionPane.showMessageDialog(view, "Membre supprimé avec succès.");
            listMembres();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Modifier un membre
    private void modifyMembre() {
        try {
            String actionCommand = view.modifyButton.getActionCommand();
            if (actionCommand != null && !actionCommand.isEmpty()) {
                int id = Integer.parseInt(actionCommand);

                String nom = view.nomField.getText().trim();
                String adresse = view.adresseField.getText().trim();
                String telephone = view.telephoneField.getText().trim();

                if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Tous les champs sont obligatoires.");
                    return;
                }

                Membre membre = new Membre(nom, adresse, telephone);
                dao.update(membre, id);

                JOptionPane.showMessageDialog(view, "Membre mis à jour avec succès.");
                listMembres();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un membre à modifier.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Rechercher des membres par nom
    private void searchMembreByName() {
        try {
            String name = view.searchField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Veuillez saisir un nom pour rechercher.");
                return;
            }

            List<Membre> membres = ((MembreDAOImpl) dao).findByName(name);
            DefaultTableModel model = (DefaultTableModel) view.membreTable.getModel();
            model.setRowCount(0);

            for (Membre membre : membres) {
                Object[] row = {membre.getId(), membre.getNom(), membre.getAdresse(), membre.getTelephone()};
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }

    // Initialiser la table
    private void initializeTable() {
        String[] columnNames = {"ID", "Nom", "Adresse", "Téléphone"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        view.membreTable.setModel(model);
    }

    // Effacer les champs de saisie
    private void clearFields() {
        view.nomField.setText("");
        view.adresseField.setText("");
        view.telephoneField.setText("");
        view.modifyButton.setActionCommand("");
    }
}
