package com.kasirkoperasi;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.kasirkoperasi.util.DatabaseConnection;
import com.kasirkoperasi.view.LoginPanel;
import com.kasirkoperasi.view.MainFrame;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test koneksi database
        if (DatabaseConnection.getConnection() == null) {
            JOptionPane.showMessageDialog(null,
                    "Gagal terhubung ke database. Periksa XAMPP dan konfigurasi.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Tampilkan login
        SwingUtilities.invokeLater(() -> {
            LoginPanel login = new LoginPanel(null);
            login.setVisible(true);
        });
    }
}