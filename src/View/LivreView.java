package View;

import javax.swing.*;
import java.awt.*;

public class LivreView extends JFrame {
    public JTable livreTable;
    public JButton addButton, listButton, deleteButton, modifyButton, switchViewButton;
    public JTextField titreField, auteurField, categorieField;

    public LivreView() {
        setTitle("Gestion des Livres");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        inputPanel.add(new JLabel("Titre:"));
        titreField = new JTextField();
        inputPanel.add(titreField);

        inputPanel.add(new JLabel("Auteur:"));
        auteurField = new JTextField();
        inputPanel.add(auteurField);

        inputPanel.add(new JLabel("Catégorie:"));
        categorieField = new JTextField();
        inputPanel.add(categorieField);

        add(inputPanel, BorderLayout.NORTH);

        // Table pour afficher les livres
        livreTable = new JTable();
        add(new JScrollPane(livreTable), BorderLayout.CENTER);

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

        // Bouton pour naviguer vers une autre vue (optionnel)
        switchViewButton = new JButton("Gérer les membres");
        switchViewButton = new JButton("Gérer les emprunts");


        buttonPanel.add(switchViewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
