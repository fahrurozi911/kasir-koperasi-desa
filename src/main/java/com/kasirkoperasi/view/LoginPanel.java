package com.kasirkoperasi.view;

import com.kasirkoperasi.dao.UserDAO;
import com.kasirkoperasi.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserDAO userDAO = new UserDAO();

    public LoginPanel(JFrame parent) {
        super(parent, "Login - Kasir Koperasi Desa", true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel judul
        JLabel lblTitle = new JLabel("KASIR KOPERASI DESA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Panel form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(e -> login());
        formPanel.add(btnLogin, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        User user = userDAO.login(username, password);
        if (user != null) {
            dispose();
            new MainFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}