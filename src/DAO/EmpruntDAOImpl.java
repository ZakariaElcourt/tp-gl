package DAO;

import Model.Emprunt;
import Model.Livre;
import Model.Membre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAOImpl implements GenericDAO<Emprunt> {

    // SQL Queries
    private static final String INSERT_EMPRUNT_SQL = "INSERT INTO emprunt (membre_id, livre_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
    private static final String DELETE_EMPRUNT_SQL = "DELETE FROM emprunt WHERE id = ?";
    private static final String SELECT_ALL_EMPRUNT_SQL = "SELECT e.id, m.nom AS membre_nom, l.titre AS livre_titre, e.date_emprunt, e.date_retour " +
                                                         "FROM emprunt e " +
                                                         "JOIN membre m ON e.membre_id = m.id " +
                                                         "JOIN livre l ON e.livre_id = l.id";
    private static final String SELECT_EMPRUNT_BY_ID_SQL = "SELECT e.id, m.nom AS membre_nom, l.titre AS livre_titre, e.date_emprunt, e.date_retour " +
                                                           "FROM emprunt e " +
                                                           "JOIN membre m ON e.membre_id = m.id " +
                                                           "JOIN livre l ON e.livre_id = l.id " +
                                                           "WHERE e.id = ?";
    private static final String UPDATE_EMPRUNT_SQL = "UPDATE emprunt SET membre_id = ?, livre_id = ?, date_emprunt = ?, date_retour = ? WHERE id = ?";

    @Override
    public void add(Emprunt emprunt) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_EMPRUNT_SQL)) {
            stmt.setInt(1, emprunt.getMembre().getId());
            stmt.setInt(2, emprunt.getLivre().getId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            stmt.executeUpdate();
            System.out.println("Emprunt ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'emprunt : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_EMPRUNT_SQL)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Emprunt supprimé avec succès.");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'emprunt : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Emprunt> listAll() {
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_EMPRUNT_SQL); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Emprunt emprunt = new Emprunt(
                        new Membre(rs.getInt("id"), rs.getString("membre_nom"), null, null),
                        new Livre(rs.getInt("id"), rs.getString("livre_titre"), null, null),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour").toLocalDate()
                );
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des emprunts : " + e.getMessage());
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public Emprunt findById(int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_EMPRUNT_BY_ID_SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Emprunt(
                        new Membre(rs.getInt("id"), rs.getString("membre_nom"), null, null),
                        new Livre(rs.getInt("id"), rs.getString("livre_titre"), null, null),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour").toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'emprunt : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Emprunt emprunt, int id) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPRUNT_SQL)) {
            stmt.setInt(1, emprunt.getMembre().getId());
            stmt.setInt(2, emprunt.getLivre().getId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Emprunt mis à jour avec succès.");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'emprunt : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
