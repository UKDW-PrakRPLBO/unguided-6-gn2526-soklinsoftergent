package org.rplbo.app.Manager;
//import org.rplbo.app.DBConnectionManager;
import org.rplbo.app.Data.RekamMedis;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RekamMedisManager {
    private Connection connection;

    public RekamMedisManager(Connection connection) {
        this.connection = connection;
    }

    public boolean tambahRekamMedis(String namaDokter, String namaPasien, String diagnosis, String tanggal) {
        String query = "INSERT INTO rekam_medis (nama_dokter, nama_pasien, diagnosis, tanggal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaDokter);
            stmt.setString(2, namaPasien);
            stmt.setString(3, diagnosis);
            stmt.setString(4, tanggal);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<RekamMedis> getAllRekamMedis() {
        List<RekamMedis> rekamMedisList = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rekamMedisList.add(new RekamMedis(
                        rs.getInt("id"),
                        rs.getString("nama_pasien"),
                        rs.getString("nama_dokter"),
                        rs.getString("diagnosis"),
                        rs.getString("tanggal")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error read all: " + e.getMessage());
        }
        return rekamMedisList;
    }

    public boolean editRekamMedis(int idRekamMedis, String diagnosisBaru) {
        String query = "UPDATE rekam_medis SET diagnosis = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, diagnosisBaru);
            stmt.setInt(2, idRekamMedis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean hapusRekamMedis(int idRekamMedis) {
        String query = "DELETE FROM rekam_medis WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRekamMedis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<RekamMedis> cariRekamMedisPasien(String nama) {
        List<RekamMedis> resultList = new ArrayList<>();
        // Menggunakan LIKE agar pencarian lebih fleksibel
        String query = "SELECT * FROM rekam_medis WHERE nama_pasien LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nama + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultList.add(new RekamMedis(
                        rs.getInt("id"),
                        rs.getString("nama_pasien"),
                        rs.getString("nama_dokter"),
                        rs.getString("diagnosis"),
                        rs.getString("tanggal")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error cari pasien: " + e.getMessage());
        }
        return resultList;
    }
}