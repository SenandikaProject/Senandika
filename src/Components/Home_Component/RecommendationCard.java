/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import Components.RoundedButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import senandika.Model.Recommendation;
import senandika.FontManager;


/**
 * Card kecil yang digunakan di dalam horizontal list halaman Home.
 * Menggunakan font dan warna yang konsisten dengan CreateJournalCard.
 */
public class RecommendationCard extends RoundedPanel {

    public interface OnOpenDetailListener {
        void onOpenDetail();
    }

    private final Recommendation recommendation;
    private OnOpenDetailListener listener;

    public RecommendationCard(Recommendation recommendation) {
        // Menggunakan corner radius 16, Background PUTIH, dan mengaktifkan properti bawaan RoundedPanel
        super(16, Color.WHITE, true);
        this.recommendation = recommendation;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(170, 210));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Membuat border luar halus yang senada dengan border Journal
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Thumbnail placeholder
        ThumbnailPlaceholder thumbnail = new ThumbnailPlaceholder();
        thumbnail.setPreferredSize(new Dimension(170, 100));

        // Text + button area
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));

        // Judul Rekomendasi - Warna disesuaikan dengan Color(30, 41, 59)
        JLabel titleLabel = new JLabel("<html><div style='width:140px;'>"
                + recommendation.getTitle() + "</div></html>");
        titleLabel.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(0f);

        // Deskripsi Rekomendasi - Warna disesuaikan dengan Color(100, 116, 139)
        JLabel descLabel = new JLabel("<html><div style='width:140px;'>"
                + truncate(recommendation.getDescription(), 50) + "</div></html>");
        descLabel.setFont(FontManager.getPoppins(11f));
        descLabel.setForeground(new Color(100, 116, 139));
        descLabel.setAlignmentX(0f);
        descLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 8, 0));

        // Tombol Mulai - Menggunakan RoundedButton dengan warna aksen Ungu (137, 126, 255)
        RoundedButton startButton = new RoundedButton();
        startButton.setText("Mulai");
        startButton.setCornerRadius(10);
        startButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        startButton.setPreferredSize(new Dimension(80, 30));
        startButton.setMaximumSize(new Dimension(80, 30));
        startButton.setBackground(new Color(137, 126, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(0f);
        startButton.addActionListener(e -> fireOpenDetail());

        bottom.add(titleLabel);
        bottom.add(descLabel);
        bottom.add(startButton);

        add(thumbnail, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);

        // Listener klik area kartu
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireOpenDetail();
            }
        });
    }

    public void setOnOpenDetail(OnOpenDetailListener listener) {
        this.listener = listener;
    }

    private void fireOpenDetail() {
        if (listener != null) {
            listener.onOpenDetail();
        }
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    private static String truncate(String text, int maxLen) {
        if (text == null) return "";
        return text.length() <= maxLen ? text : text.substring(0, maxLen - 1) + "\u2026";
    }

    /**
     * Placeholder dengan Soft Gradient warna Ungu khas Senandika
     */
    private static class ThumbnailPlaceholder extends JPanel {
        ThumbnailPlaceholder() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Gradient disesuaikan dari warna Ungu Utama ke Ungu yang sedikit lebih gelap
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(137, 126, 255), w, h, new Color(110, 98, 230));
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, w, h, 16, 16);
            
            // Memotong sudut bawah agar presisi menempel dengan area teks di bawahnya
            g2.fillRect(0, h / 2, w, h / 2);

            // Glyph Tunas/🌱 di tengah placeholder
            g2.setColor(new Color(255, 255, 255, 200));
            g2.setFont(FontManager.getPoppins(28f));
            FontMetricsHelper.drawCentered(g2, "\uD83C\uDF31", w, h);

            g2.dispose();
        }
    }

    private static class FontMetricsHelper {
        static void drawCentered(Graphics2D g2, String text, int w, int h) {
            FontMetrics fm = g2.getFontMetrics();
            int x = (w - fm.stringWidth(text)) / 2;
            int y = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, x, y);
        }
    }
}
