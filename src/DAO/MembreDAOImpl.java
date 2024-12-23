package DAO;

import Model.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAOImpl implements GenericDAO<Membre> {

    // SQL Queries
    private static final String INSERT_MEMBRE_SQL = "INSERT INTO membre (nom, adresse, telephone) VALUES (?, ?, ?)";
    private static final String DELETE_MEMBRE_SQL = "DELETE FROM membre WHERE id = ?";
    private static final String SELECT_ALL_MEMBRE_SQL = "SELECT * FROM membre";
    private static final String SELECT_MEMBRE_BY_ID_SQL = "SELECT * FROM membre WHERE id = ?";
    private static final String UPDATE_MEMBRE_SQL = "UPDATE membre SET nom = ?, adresse = ?, telephone = ? WHERE id = ?";

    @Override
    public void add(Membre membre) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_MEMBRE_SQL)) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getAdresse());
            stmt.setString(3, membre.getTelephone());
            stmt.executeUpdate();
            System.out.println("Membre ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du membre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_MEMBRE_SQL)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Membre supprimé avec succès.");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du membre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Membre> listAll() {
        List<Membre> membres = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_MEMBRE_SQL); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone")
                );
                membres.add(membre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres : " + e.getMessage());
            e.printStackTrace();
        }
        return membres;
    }

    @Override
    public Membre findById(int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_MEMBRE_BY_ID_SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du membre : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Membre membre, int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_MEMBRE_SQL)) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getAdresse());
            stmt.setString(3, membre.getTelephone());
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Membre mis à jour avec succès.");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du membre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour rechercher un membre par son nom
    public List<Membre> findByName(String name) {
        List<Membre> membres = new ArrayList<>();
        String query = "SELECT * FROM membre WHERE nom LIKE ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone")
                );
                membres.add(membre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des membres par nom : " + e.getMessage());
            e.printStackTrace();
        }
        return membres;
    }
}
