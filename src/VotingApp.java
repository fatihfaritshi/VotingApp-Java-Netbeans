/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author erik rahman
 */

// VotingApp.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VotingApp extends JFrame {
    private int voteA = 0, voteB = 0;
    private String kandidatA, kandidatB;

    private JLabel resultLabel, winnerLabel;
    private JButton voteButtonA, voteButtonB, resetButton, customizeButton;
    private JProgressBar barA, barB;
    private JTextArea logArea;
    private JScrollPane scrollPane;

    private Color accentA = new Color(100, 149, 237); // Warna awal untuk Kandidat A
    private Color accentB = new Color(255, 99, 71); // Warna awal untuk Kandidat B

    public VotingApp() {
        kandidatA = JOptionPane.showInputDialog(this, "Masukkan nama Kandidat A:", "Kandidat A");
        kandidatB = JOptionPane.showInputDialog(this, "Masukkan nama Kandidat B:", "Kandidat B");

        if (kandidatA == null || kandidatA.trim().isEmpty()) kandidatA = "Kandidat A";
        if (kandidatB == null || kandidatB.trim().isEmpty()) kandidatB = "Kandidat B";

        setTitle("🗳️ VotingApp");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tema Gelap
        Color darkBg = new Color(45, 45, 45);
        Color textColor = Color.WHITE;

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(darkBg);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Membuat resultLabel dengan kotak
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(titleFont);
        resultLabel.setForeground(textColor);

        // Membungkus resultLabel dengan JPanel untuk memberi background dan border
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(darkBg);
        resultPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2)); // Membuat border dengan warna biru
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        winnerLabel = new JLabel("Belum ada pemenang", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        winnerLabel.setForeground(new Color(180, 180, 180));

        // Tombol
        voteButtonA = new JButton("Vote " + kandidatA);
        voteButtonB = new JButton("Vote " + kandidatB);
        resetButton = new JButton("🔄 Reset");
        customizeButton = new JButton("🎨 Kustomisasi");

        voteButtonA.setBackground(accentA);
        voteButtonB.setBackground(accentB);
        resetButton.setBackground(Color.GRAY);
        customizeButton.setBackground(new Color(60, 60, 60));

        voteButtonA.setForeground(textColor);
        voteButtonB.setForeground(textColor);
        resetButton.setForeground(textColor);
        customizeButton.setForeground(textColor);

        voteButtonA.setFont(bodyFont);
        voteButtonB.setFont(bodyFont);
        resetButton.setFont(bodyFont);
        customizeButton.setFont(bodyFont);

        // Grafik Suara
        barA = new JProgressBar(0, 100);
        barB = new JProgressBar(0, 100);
        barA.setForeground(accentA); // Warna sesuai dengan warna tombol A
        barB.setForeground(accentB); // Warna sesuai dengan warna tombol B
        barA.setStringPainted(true);
        barB.setStringPainted(true);

        // Log Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(Color.DARK_GRAY);
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        scrollPane = new JScrollPane(logArea);

        // Event Handler
        voteButtonA.addActionListener(e -> {
            voteA++;
            logVote(kandidatA);
            updateResult();
        });

        voteButtonB.addActionListener(e -> {
            voteB++;
            logVote(kandidatB);
            updateResult();
        });

        resetButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Reset semua voting?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                voteA = 0;
                voteB = 0;
                logArea.setText("");
                updateResult();
            }
        });

        customizeButton.addActionListener(e -> {
            Color newColorA = JColorChooser.showDialog(this, "Warna tombol " + kandidatA, accentA);
            Color newColorB = JColorChooser.showDialog(this, "Warna tombol " + kandidatB, accentB);
            if (newColorA != null) {
                voteButtonA.setBackground(newColorA);
                barA.setForeground(newColorA); // Mengubah warna progress bar sesuai pilihan warna tombol A
                accentA = newColorA; // Menyimpan warna baru untuk kandidat A
            }
            if (newColorB != null) {
                voteButtonB.setBackground(newColorB);
                barB.setForeground(newColorB); // Mengubah warna progress bar sesuai pilihan warna tombol B
                accentB = newColorB; // Menyimpan warna baru untuk kandidat B
            }
        });

        // Layout
        JPanel panelTengah = new JPanel(new GridLayout(2, 1, 10, 10));
        panelTengah.setBackground(darkBg);
        panelTengah.add(voteButtonA);
        panelTengah.add(voteButtonB);

        JPanel panelGrafik = new JPanel(new GridLayout(2, 1));
        panelGrafik.setBackground(darkBg);
        panelGrafik.add(barA);
        panelGrafik.add(barB);

        JPanel panelBawah = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBawah.setBackground(darkBg);
        panelBawah.add(resetButton);
        panelBawah.add(customizeButton);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(darkBg);
        topPanel.add(resultPanel); // Menambahkan JPanel resultPanel ke dalam topPanel
        topPanel.add(winnerLabel);

        add(topPanel, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.WEST);
        add(panelGrafik, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.EAST);

        updateResult();
        setVisible(true);
    }

    private void updateResult() {
        resultLabel.setText(kandidatA + ": " + voteA + " | " + kandidatB + ": " + voteB);
        int total = voteA + voteB;

        int percentA = total == 0 ? 0 : (int)((voteA * 100.0) / total);
        int percentB = total == 0 ? 0 : (int)((voteB * 100.0) / total);

        barA.setValue(percentA);
        barA.setString(kandidatA + ": " + percentA + "%");
        barB.setValue(percentB);
        barB.setString(kandidatB + ": " + percentB + "%");

        if (voteA > voteB) {
            winnerLabel.setText("Unggul: " + kandidatA);
        } else if (voteB > voteA) {
            winnerLabel.setText("Unggul: " + kandidatB);
        } else {
            winnerLabel.setText("Hasil imbang");
        }
    }

    private void logVote(String kandidat) {
        String waktu = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        logArea.append("[" + waktu + "] Vote untuk " + kandidat + "\n");
    }

    public static void main(String[] args) {
        new VotingApp();
    }
}



