/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class WellnessProgressCard extends RoundedPanel {

    private final JLabel titleLabel;
    private final JLabel streakLabel;
    private final FlameIcon flameIcon;

    public WellnessProgressCard() {
        // Menggunakan radius 16, background PUTIH, dan mengaktifkan soft shadow bawaan RoundedPanel
        super(16, Color.WHITE, true);
        setLayout(new BorderLayout(16, 0));
        setPreferredSize(new Dimension(320, 96));
        
        // Membuat border gabungan: border luar tipis halus + padding kosong di dalam kartu
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        flameIcon = new FlameIcon();
        flameIcon.setPreferredSize(new Dimension(48, 56));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Subtitle - Warna disesuaikan dengan warna sub-teks Journal (100, 116, 139)
        titleLabel = new JLabel("Journal Streak");
        titleLabel.setFont(FontManager.getPoppins(13f));
        titleLabel.setForeground(new Color(100, 116, 139));
        titleLabel.setAlignmentX(0f);

        // Angka/Informasi Utama - Warna disesuaikan dengan teks gelap utama Journal (30, 41, 59)
        streakLabel = new JLabel("0 Hari Konsisten");
        streakLabel.setFont(FontManager.getPoppins(18f).deriveFont(Font.BOLD));
        streakLabel.setForeground(new Color(30, 41, 59));
        streakLabel.setAlignmentX(0f);
        streakLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        textPanel.add(titleLabel);
        textPanel.add(streakLabel);

        add(flameIcon, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }

    public void setStreak(int days) {
        streakLabel.setText(days + " Hari Konsisten");
    }

    public void setLoading() {
        streakLabel.setText("Memuat...");
    }

    public void setUnavailable() {
        streakLabel.setText("Belum tersedia");
    }

    /**
     * Ilustrasi icon api kustom yang digambar langsung menggunakan paintComponent vector graphics.
     */
    private static class FlameIcon extends JPanel {
        FlameIcon() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Bentuk Api Luar (Outer Flame)
            java.awt.geom.Path2D outer = new java.awt.geom.Path2D.Double();
            outer.moveTo(w * 0.5, h * 0.02);
            outer.curveTo(w * 0.85, h * 0.35, w * 0.95, h * 0.55, w * 0.78, h * 0.78);
            outer.curveTo(w * 0.68, h * 0.92, w * 0.32, h * 0.92, w * 0.22, h * 0.78);
            outer.curveTo(w * 0.05, h * 0.55, w * 0.15, h * 0.35, w * 0.5, h * 0.02);
            outer.closePath();

            GradientPaint outerPaint = new GradientPaint(0, 0, Color.decode("#FFB874"),
                    0, h, Color.decode("#FF8C5A"));
            g2.setPaint(outerPaint);
            g2.fill(outer);

            // Bentuk Inti Api Dalam (Inner Flame)
            java.awt.geom.Path2D inner = new java.awt.geom.Path2D.Double();
            inner.moveTo(w * 0.5, h * 0.32);
            inner.curveTo(w * 0.68, h * 0.5, w * 0.7, h * 0.62, w * 0.58, h * 0.74);
            inner.curveTo(w * 0.52, h * 0.8, w * 0.42, h * 0.78, w * 0.4, h * 0.68);
            inner.curveTo(w * 0.35, h * 0.55, w * 0.4, h * 0.45, w * 0.5, h * 0.32);
            inner.closePath();

            GradientPaint innerPaint = new GradientPaint(0, h * 0.3f, Color.decode("#FFE3A8"),
                    0, h, Color.decode("#FFC36B"));
            g2.setPaint(innerPaint);
            g2.fill(inner);

            g2.dispose();
        }
    }
}
