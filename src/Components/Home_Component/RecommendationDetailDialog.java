/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import javax.swing.*;
import java.awt.*;
import senandika.FontManager;
import senandika.Model.Recommendation;

public class RecommendationDetailDialog extends JDialog {

    private final Recommendation recommendation;

    public RecommendationDetailDialog(Frame parent, Recommendation recommendation) {
        super(parent, true);
        this.recommendation = recommendation;
        setUndecorated(true);
        initUI();
    }

    private void initUI() {
        setSize(394, 720);
        setLocationRelativeTo(getOwner());
        setBackground(new Color(0, 0, 0, 0));

        JPanel mainPanel = new RoundedPanel(20, Color.WHITE, true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(null);

        // --- HEADER ---
        JPanel headerArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int w = getWidth();
                int h = getHeight();

                try {
                    String path = recommendation.getThumbnailUrl();
                    if (path != null && !path.trim().isEmpty()) {
                        // [FIX GAMBAR] Coba dua variasi path: dengan dan tanpa leading slash
                        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
                        if (imgURL == null) {
                            // Fallback: coba tambah slash di depan via getClass().getResource()
                            imgURL = getClass().getResource("/" + path);
                        }
                        
                        if (imgURL != null) {
                            Image image = new ImageIcon(imgURL).getImage();
                            int imgW = image.getWidth(this);
                            int imgH = image.getHeight(this);

                            if (imgW > 0 && imgH > 0) {
                                double containerRatio = (double) w / h;
                                double imageRatio = (double) imgW / imgH;

                                int renderW, renderH, x = 0, y = 0;
                                if (imageRatio > containerRatio) {
                                    renderH = h;
                                    renderW = (int) (h * imageRatio);
                                    x = (w - renderW) / 2;
                                } else {
                                    renderW = w;
                                    renderH = (int) (w / imageRatio);
                                    y = (h - renderH) / 2;
                                }
                                g2.drawImage(image, x, y, renderW, renderH, this);
                                g2.dispose();
                                return; // Berhasil gambar, keluar
                            }
                        }
                    }
                } catch (Exception e) {
                    // Lanjut ke fallback
                }
                drawFallback(g2, w, h);
                g2.dispose();
            }

            private void drawFallback(Graphics2D g2, int w, int h) {
                GradientPaint gradient = new GradientPaint(0, 0, new Color(137, 126, 255), w, h, new Color(110, 98, 230));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, w, h);
            }
        };
        headerArea.setLayout(null);
        headerArea.setPreferredSize(new Dimension(394, 250));
        headerArea.setOpaque(false);

        JButton btnBack = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, 45, 45);
                g2.setColor(new Color(137, 126, 255));
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                String backSym = "‹";
                g2.drawString(backSym, (45 - fm.stringWidth(backSym)) / 2, (45 - fm.getHeight()) / 2 + fm.getAscent() - 2);
                g2.dispose();
            }
        };
        btnBack.setBounds(20, 20, 45, 45);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        headerArea.add(btnBack);
        mainPanel.add(headerArea, BorderLayout.NORTH);

        // --- CONTENT AREA ---
        // [FIX TEKS] Gunakan JTextPane untuk deskripsi agar wrap otomatis mengikuti lebar container
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        // Padding kiri-kanan 24px → ruang teks efektif = 394 - 48 = 346px
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 24, 25, 24));

        // JUDUL
        JLabel titleLabel = new JLabel("<html><div style='width:320px;'>" + recommendation.getTitle() + "</div></html>");
        titleLabel.setFont(FontManager.getPoppins(24f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // [FIX TEKS] Batasi lebar maksimum agar tidak melebihi panel
        titleLabel.setMaximumSize(new Dimension(346, Integer.MAX_VALUE));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));

        // [FIX TEKS] Ganti JLabel deskripsi dengan JTextArea agar wrap benar-benar mengikuti lebar container
        JTextArea descArea = new JTextArea(recommendation.getDescription());
        descArea.setFont(FontManager.getPoppins(14f));
        descArea.setForeground(new Color(100, 116, 139));
        descArea.setBackground(Color.WHITE);
        descArea.setLineWrap(true);       // Aktifkan text wrap
        descArea.setWrapStyleWord(true);  // Wrap per kata, bukan per karakter
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setOpaque(false);
        descArea.setBorder(null);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Kunci lebar maksimum sesuai ruang efektif
        descArea.setMaximumSize(new Dimension(346, Integer.MAX_VALUE));
        contentPanel.add(descArea);
        contentPanel.add(Box.createVerticalStrut(25));

        // STEPS CARD
        JPanel stepsCard = new RoundedPanel(16, new Color(250, 251, 255), true);
        stepsCard.setLayout(new BoxLayout(stepsCard, BoxLayout.Y_AXIS));
        stepsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 238, 250), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        stepsCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepsCard.setMaximumSize(new Dimension(346, Integer.MAX_VALUE)); // Sesuai ruang efektif

        JLabel labelHeader = new JLabel("Langkah aktivitas");
        labelHeader.setFont(FontManager.getPoppins(16f).deriveFont(Font.BOLD));
        labelHeader.setForeground(new Color(30, 41, 59));
        labelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelHeader.setMaximumSize(new Dimension(306, Integer.MAX_VALUE));
        stepsCard.add(labelHeader);
        stepsCard.add(Box.createVerticalStrut(15));

        String[] steps = recommendation.getSteps();
        if (steps != null && steps.length > 0) {
            for (int i = 0; i < steps.length; i++) {
                // [FIX TEKS] Gunakan JTextArea untuk tiap langkah juga agar wrap konsisten
                JTextArea stepArea = new JTextArea((i + 1) + ". " + steps[i]);
                stepArea.setFont(FontManager.getPoppins(14f));
                stepArea.setForeground(new Color(51, 65, 85));
                stepArea.setBackground(new Color(250, 251, 255));
                stepArea.setLineWrap(true);
                stepArea.setWrapStyleWord(true);
                stepArea.setEditable(false);
                stepArea.setFocusable(false);
                stepArea.setOpaque(false);
                stepArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                stepArea.setAlignmentX(Component.LEFT_ALIGNMENT); // Kunci alignment kiri
                stepArea.setMaximumSize(new Dimension(306, Integer.MAX_VALUE));
                stepsCard.add(stepArea);
            }
        } else {
            JLabel emptySteps = new JLabel("Memuat langkah aktivitas...");
            emptySteps.setFont(FontManager.getPoppins(13f));
            emptySteps.setForeground(new Color(100, 116, 139));
            emptySteps.setAlignmentX(Component.LEFT_ALIGNMENT);
            stepsCard.add(emptySteps);
        }
        contentPanel.add(stepsCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footerArea = new JPanel(new GridLayout(1, 2, 12, 0));
        footerArea.setBackground(Color.WHITE);
        footerArea.setBorder(BorderFactory.createEmptyBorder(15, 24, 25, 24));

        JButton btnSelesai = createFooterButton("Selesai", new Color(137, 126, 255), Color.WHITE);
        btnSelesai.addActionListener(e -> dispose());

        JButton btnFav = createFooterButton("Favorit", new Color(241, 245, 249), new Color(30, 41, 59));

        footerArea.add(btnSelesai);
        footerArea.add(btnFav);
        mainPanel.add(footerArea, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createFooterButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(FontManager.getPoppins(14f).deriveFont(Font.BOLD));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}