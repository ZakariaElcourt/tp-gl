package DAO;

import Model.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAOImpl implements GenericDAO<Livre> {

    // SQL queries
    private static final String INSERT_LIVRE_SQL = "INSERT INTO livre (titre, auteur, categorie) VALUES (?, ?, ?)";
    private static final String DELETE_LIVRE_SQL = "DELETE FROM livre WHERE id = ?";
    private static final String SELECT_ALL_LIVRE_SQL = "SELECT * FROM livre";
    private static final String SELECT_LIVRE_BY_ID_SQL = "SELECT * FROM livre WHERE id = ?";
    private static final String UPDATE_LIVRE_SQL = "UPDATE livre SET titre = ?, auteur = ?, categorie = ? WHERE id = ?";

    @Override
    public void add(Livre livre) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_LIVRE_SQL)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getCategorie());
            stmt.executeUpdate();
            System.out.println("Livre ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_LIVRE_SQL)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livre supprimé avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Livre> listAll() {
        List<Livre> livres = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_LIVRE_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
            e.printStackTrace();
        }
        return livres;
    }

    @Override
    public Livre findById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_LIVRE_BY_ID_SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du livre : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Livre livre, int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_LIVRE_SQL)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getCategorie());
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livre mis à jour avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
