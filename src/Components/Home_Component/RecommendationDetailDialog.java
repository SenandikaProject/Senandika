/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import Components.RoundedButton;
import java.awt.*;
import javax.swing.*;
import senandika.Model.Recommendation;
import senandika.FontManager;


public class RecommendationDetailDialog extends JDialog {

    public interface OnActionListener {
        void onAction();
    }

    private OnActionListener onMarkComplete;
    private OnActionListener onSaveFavorite;

    public RecommendationDetailDialog(JFrame parent, Recommendation recommendation) {
        super(parent, true);
        setUndecorated(true);
        setSize(380, 600);
        setLocationRelativeTo(parent);

        // Menggunakan Background PUTIH agar bersih dan kontras dengan teks gelap
        RoundedPanel root = new RoundedPanel(24, Color.WHITE, false);
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // ---- Header image dengan tombol kembali ----
        HeaderImage header = new HeaderImage();
        header.setPreferredSize(new Dimension(380, 220));
        header.setLayout(new BorderLayout());

        // Membuat tombol kembali sirkular kustom tanpa utilitas luar
        JButton backButton = new JButton("←") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        backButton.setFont(FontManager.getPoppins(16f).deriveFont(Font.BOLD));
        backButton.setForeground(new Color(137, 126, 255)); // Warna Ungu Utama
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> dispose());

        JPanel backWrap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backWrap.setOpaque(false);
        backWrap.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 0));
        
        // Membungkus tombol agar benar-benar sirkular (lingkaran) berukuran 40x40
        JPanel btnCircle = new JPanel(new BorderLayout());
        btnCircle.setOpaque(false);
        btnCircle.setPreferredSize(new Dimension(40, 40));
        btnCircle.add(backButton, BorderLayout.CENTER);
        
        backWrap.add(btnCircle);
        header.add(backWrap, BorderLayout.NORTH);

        // ---- Body Panel yang bisa di-scroll ----
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Judul Utama - Warna disesuaikan dengan teks gelap header Journal (30, 41, 59)
        JLabel titleLabel = new JLabel(recommendation.getTitle());
        titleLabel.setFont(FontManager.getPoppins(20f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(0f);

        // Deskripsi - Warna disesuaikan dengan sub-teks abu-abu Journal (100, 116, 139)
        JLabel descLabel = new JLabel("<html><div style='width:320px;'>"
                + recommendation.getDescription() + "</div></html>");
        descLabel.setFont(FontManager.getPoppins(13f));
        descLabel.setForeground(new Color(100, 116, 139));
        descLabel.setAlignmentX(0f);
        descLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 16, 0));

        RoundedPanel stepsCard = buildStepsCard(recommendation);
        stepsCard.setAlignmentX(0f);
        stepsCard.setMaximumSize(new Dimension(340, 220));

        body.add(titleLabel);
        body.add(descLabel);
        body.add(stepsCard);
        body.add(Box.createVerticalStrut(16));

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        // ---- Footer panel untuk tombol aksi ----
        JPanel footer = new JPanel(new GridLayout(1, 2, 10, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Tombol Tandai Selesai - Menggunakan struktur style Journal Button
        RoundedButton completeButton = new RoundedButton();
        completeButton.setText("✓  Selesai");
        completeButton.setCornerRadius(12);
        completeButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        completeButton.setPreferredSize(new Dimension(150, 44));
        completeButton.setBackground(new Color(137, 126, 255)); // Ungu Utama
        completeButton.setForeground(Color.WHITE);
        completeButton.addActionListener(e -> {
            if (onMarkComplete != null) onMarkComplete.onAction();
            dispose();
        });

        // Tombol Favorit - Menggunakan alternatif background halus beraliran netral
        RoundedButton favoriteButton = new RoundedButton();
        favoriteButton.setText("♡  Favorit");
        favoriteButton.setCornerRadius(12);
        favoriteButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        favoriteButton.setPreferredSize(new Dimension(150, 44));
        favoriteButton.setBackground(new Color(241, 245, 249)); // Abu-abu terang netral
        favoriteButton.setForeground(new Color(51, 65, 85));   // Teks kontras gelap
        favoriteButton.addActionListener(e -> {
            if (onSaveFavorite != null) onSaveFavorite.onAction();
        });

        footer.add(completeButton);
        footer.add(favoriteButton);

        root.add(header, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
    }

    public void setOnMarkComplete(OnActionListener listener) {
        this.onMarkComplete = listener;
    }

    public void setOnSaveFavorite(OnActionListener listener) {
        this.onSaveFavorite = listener;
    }

    private RoundedPanel buildStepsCard(Recommendation recommendation) {
        // Menggunakan background abu-abu tipis (250, 250, 250) yang serupa dengan area input form Journal
        RoundedPanel card = new RoundedPanel(16, new Color(250, 250, 250), false);
        card.setCardBorderColor(new Color(226, 232, 240)); // Border subtle
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel headerLabel = new JLabel("Langkah aktivitas");
        headerLabel.setFont(FontManager.getPoppins(14f).deriveFont(Font.BOLD));
        headerLabel.setForeground(new Color(30, 41, 59));

        JPanel stepsList = new JPanel();
        stepsList.setOpaque(false);
        stepsList.setLayout(new BoxLayout(stepsList, BoxLayout.Y_AXIS));
        stepsList.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        String[] steps = recommendation.getSteps();
        if (steps == null || steps.length == 0) {
            steps = defaultSteps();
        }

        for (int i = 0; i < steps.length; i++) {
            JLabel stepLabel = new JLabel((i + 1) + ". " + steps[i]);
            stepLabel.setFont(FontManager.getPoppins(13f));
            stepLabel.setForeground(new Color(51, 65, 85));
            stepLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            stepLabel.setAlignmentX(0f);
            stepsList.add(stepLabel);
        }

        card.add(headerLabel, BorderLayout.NORTH);
        card.add(stepsList, BorderLayout.CENTER);
        return card;
    }

    private String[] defaultSteps() {
        return new String[]{
                "Duduk nyaman",
                "Tarik napas 4 detik",
                "Tahan 2 detik",
                "Hembuskan 6 detik",
                "Ulangi 5x"
        };
    }

    /**
     * Placeholder Header Gradient warna Ungu khas Senandika
     */
    private static class HeaderImage extends JPanel {
        HeaderImage() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Gradient dari Ungu sedikit gelap (Atas) menuju Ungu Terang Utama (Bawah)
            GradientPaint gradient = new GradientPaint(0, 0, new Color(110, 98, 230), 0, h,
                    new Color(137, 126, 255));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, w, h);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
