package com.kasirkoperasi.view;

import com.kasirkoperasi.util.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DashboardPimpinanPanel extends JPanel {
    public DashboardPimpinanPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dashboard Pimpinan", SwingConstants.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        cards.add(createCard("Total Barang", getSingleValue("SELECT COUNT(*) FROM barang")));
        cards.add(createCard("Total Transaksi", getSingleValue("SELECT COUNT(*) FROM transaksi")));
        cards.add(createCard("Total Pendapatan", "Rp " + getFormattedValue("SELECT COALESCE(SUM(total),0) FROM transaksi")));

        add(cards, BorderLayout.CENTER);
    }

    private JPanel createCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setBackground(Color.WHITE);
        JLabel lblLabel = new JLabel(label, SwingConstants.CENTER);
        lblLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 28));
        card.add(lblLabel, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    private String getSingleValue(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return "0";
    }

    private String getFormattedValue(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return String.format("%,.0f", rs.getDouble(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return "0";
    }
}