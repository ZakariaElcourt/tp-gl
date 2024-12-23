package Controller;

import View.EmpruntView;
import View.LivreView;
import View.MembreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController {

    private EmpruntView empruntView;
    private LivreView livreView;
    private MembreView membreView;

    public ViewController() {
        // Instantiate views
        empruntView = new EmpruntView();
        livreView = new LivreView();
        membreView = new MembreView();

        // Set up action listeners for switch buttons
        empruntView.switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView(empruntView, livreView);
            }
        });

        livreView.switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView(livreView, membreView);
            }
        });

        membreView.switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView(membreView, empruntView);
            }
        });
    }

    // Switch between views by hiding the current view and showing the next one
    private void switchView(JFrame currentView, JFrame newView) {
        currentView.setVisible(false);
        newView.setVisible(true);
    }

    // Start the application with the first view
    public void start() {
        empruntView.setVisible(true); // Show the first view (EmpruntView)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the controller and start the application
            ViewController controller = new ViewController();
            controller.start();
        });
    }
}
