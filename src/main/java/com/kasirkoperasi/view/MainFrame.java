package com.kasirkoperasi.view;

import com.kasirkoperasi.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private User user;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JPanel sidebarPanel;

    public MainFrame(User user) {
        this.user = user;
        setTitle("KASIR KOPERASI DESA - " + user.getNama());
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setBackground(new Color(33, 37, 41));

        // Tambahkan menu sesuai role
        addSidebarButton("Dashboard", e -> cardLayout.show(contentPanel, "DASHBOARD"));
        if (user.getRole().equals("KASIR")) {
            addSidebarButton("Data Barang", e -> cardLayout.show(contentPanel, "BARANG"));
            addSidebarButton("Transaksi", e -> cardLayout.show(contentPanel, "TRANSAKSI"));
            addSidebarButton("Riwayat Transaksi", e -> cardLayout.show(contentPanel, "RIWAYAT"));
        }
        addSidebarButton("Laporan", e -> cardLayout.show(contentPanel, "LAPORAN"));
        sidebarPanel.add(Box.createVerticalGlue());
        JButton btnLogout = new JButton("Logout");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(180, 40));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> logout());
        sidebarPanel.add(btnLogout);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        add(sidebarPanel, BorderLayout.WEST);

        // Content panel dengan CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Buat panel-panel
        if (user.getRole().equals("KASIR")) {
            contentPanel.add(new DashboardKasirPanel(), "DASHBOARD");
            contentPanel.add(new BarangPanel(), "BARANG");
            contentPanel.add(new TransaksiPanel(user), "TRANSAKSI");
            contentPanel.add(new RiwayatTransaksiPanel(), "RIWAYAT");
        } else {
            contentPanel.add(new DashboardPimpinanPanel(), "DASHBOARD");
        }
        contentPanel.add(new LaporanPanel(), "LAPORAN");

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addSidebarButton(String text, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(new Color(52, 58, 64));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.addActionListener(listener);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarPanel.add(btn);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginPanel(null).setVisible(true);
        }
    }
}