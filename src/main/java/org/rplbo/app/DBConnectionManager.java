package org.rplbo.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionManager {
    // Gunakan file lokal. SQLite akan membuat file ini jika belum ada.
    private static final String DB_URL = "jdbc:sqlite:Asylum.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // LOAD DRIVER
                Class.forName("org.sqlite.JDBC");
                // HUBUNGKAN (Gunakan variabel DB_URL, jangan tanda petik "DB_URL")
                connection = DriverManager.getConnection(DB_URL);

                // INISIALISASI TABEL JIKA BELUM ADA
                inisialisasiDatabase();
            }
        } catch (Exception e) {
            System.err.println("Gagal koneksi: " + e.getMessage());
        }
        return connection;
    }

    private static void inisialisasiDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Buat Tabel Users
            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "email TEXT," +
                    "password TEXT," +
                    "role TEXT)";

            // Buat Tabel Rekam Medis
            String sqlRekamMedis = "CREATE TABLE IF NOT EXISTS rekam_medis (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nama_pasien TEXT," +
                    "nama_dokter TEXT," +
                    "diagnosis TEXT," +
                    "tanggal TEXT)";

            stmt.execute(sqlUsers);
            stmt.execute(sqlRekamMedis);
        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi tabel: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}