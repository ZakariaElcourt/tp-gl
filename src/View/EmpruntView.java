package View;

import javax.swing.*;
import java.awt.*;

public class EmpruntView extends JFrame {
    public JTable empruntTable;
    public JButton addButton, listButton, deleteButton, modifyButton, switchViewButton;
    public JTextField membreField, livreField, dateEmpruntField, dateRetourField;

    public EmpruntView() {
        setTitle("Gestion des Emprunts");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        inputPanel.add(new JLabel("Membre:"));
        membreField = new JTextField();
        inputPanel.add(membreField);

        inputPanel.add(new JLabel("Livre:"));
        livreField = new JTextField();
        inputPanel.add(livreField);

        inputPanel.add(new JLabel("Date Emprunt (yyyy-mm-dd):"));
        dateEmpruntField = new JTextField();
        inputPanel.add(dateEmpruntField);

        inputPanel.add(new JLabel("Date Retour (yyyy-mm-dd):"));
        dateRetourField = new JTextField();
        inputPanel.add(dateRetourField);

        add(inputPanel, BorderLayout.NORTH);

        // Table pour afficher les emprunts
        empruntTable = new JTable();
        add(new JScrollPane(empruntTable), BorderLayout.CENTER);

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
        switchViewButton = new JButton("Gérer les livres");
        buttonPanel.add(switchViewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
