package View;

import javax.swing.*;
import java.awt.*;

public class MembreView extends JFrame {
    public JTable membreTable;
    public JButton addButton, listButton, deleteButton, modifyButton, searchButton, switchViewButton;
    public JTextField nomField, adresseField, telephoneField, searchField;

    public MembreView() {
        setTitle("Gestion des Membres");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        inputPanel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        inputPanel.add(nomField);

        inputPanel.add(new JLabel("Adresse:"));
        adresseField = new JTextField();
        inputPanel.add(adresseField);

        inputPanel.add(new JLabel("Téléphone:"));
        telephoneField = new JTextField();
        inputPanel.add(telephoneField);

        add(inputPanel, BorderLayout.NORTH);

        // Table pour afficher les membres
        membreTable = new JTable();
        add(new JScrollPane(membreTable), BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter");
        buttonPanel.add(addButton);

        listButton = new JButton("Afficher");
        buttonPanel.add(listButton);

        deleteButton = new JButton("Supprimer");
        buttonPanel.add(deleteButton);

        modifyButton = new JButton("Modifier");
        buttonPanel.add(modifyButton);

        // Ajouter la barre de recherche en haut

        // Bouton pour naviguer vers une autre vue (optionnel)
        switchViewButton = new JButton("Gérer les emprunts");
        switchViewButton = new JButton("Gérer les livres");

        buttonPanel.add(switchViewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
