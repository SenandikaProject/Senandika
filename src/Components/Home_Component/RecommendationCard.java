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

public class RecommendationCard extends RoundedPanel {

    public interface OnOpenDetailListener {
        void onOpenDetail();
    }

    private final Recommendation recommendation;
    private OnOpenDetailListener listener;

    public RecommendationCard(Recommendation recommendation) {
        // Rounded Corner 16, Background PUTIH, DropShadow aktif
        super(16, Color.WHITE, true);
        this.recommendation = recommendation;

        setLayout(new BorderLayout());
        // Ukuran Card dipatenkan agar tidak didorong oleh layout internal
        setPreferredSize(new Dimension(170, 240)); 
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Border luar halus yang senada dengan border Journal
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // 1. AREA THUMBNAIL DENGAN ANTELUBERAN & OBJECT-FIT COVER
        JPanel thumbnailWrapper = new JPanel() {
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
                        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
                        if (imgURL != null) {
                            Image image = new javax.swing.ImageIcon(imgURL).getImage();
                            
                            int imgW = image.getWidth(this);
                            int imgH = image.getHeight(this);
                            
                            // Dimensi kanvas internal 2x lipat agar tajam (HD Supersampling)
                            int targetW = w * 2;
                            int targetH = h * 2;
                            java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(targetW, targetH, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2d = bi.createGraphics();
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                            int renderW = targetW;
                            int renderH = targetH;
                            int x = 0;
                            int y = 0;
                            
                            double containerRatio = (double) targetW / targetH;
                            double imageRatio = (double) imgW / imgH;
                            
                            if (imageRatio > containerRatio) {
                                renderW = (int) (targetH * imageRatio);
                                x = (targetW - renderW) / 2;
                            } else {
                                renderH = (int) (targetW / imageRatio);
                                y = (targetH - renderH) / 2;
                            }
                            
                            // Cetakan rounded di sudut atas (Radius dinaikkan ke 32 karena resolusi 2x)
                            java.awt.geom.Area roundedArea = new java.awt.geom.Area(new java.awt.geom.RoundRectangle2D.Float(0, 0, targetW, targetH, 32, 32));
                            java.awt.geom.Area bottomSquare = new java.awt.geom.Area(new java.awt.Rectangle(0, targetH / 2, targetW, targetH / 2));
                            roundedArea.add(bottomSquare); 
                            
                            g2d.setClip(roundedArea);
                            g2d.drawImage(image, x, y, renderW, renderH, null);
                            g2d.dispose();

                            // Gambar diletakkan presisi
                            g2.drawImage(bi, 0, 0, w, h, null);
                        } else {
                            // Fallback gradasi jika gambar tidak ditemukan
                            drawFallback(g2, w, h);
                        }
                    } else {
                        drawFallback(g2, w, h);
                    }
                } catch (Exception e) {
                    drawFallback(g2, w, h);
                }
                g2.dispose();
            }

            private void drawFallback(Graphics2D g2, int w, int h) {
                GradientPaint gradient = new GradientPaint(0, 0, new Color(137, 126, 255), w, h, new Color(110, 98, 230));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, 16, 16);
                g2.fillRect(0, h / 2, w, h / 2); // Bawah rata
                g2.setColor(new Color(255, 255, 255, 150));
                g2.setFont(FontManager.getPoppins(24f));
                // Matriks helper sederhana untuk menengahkan teks
                FontMetrics fm = g2.getFontMetrics();
                String text = "🌱";
                g2.drawString(text, (w - fm.stringWidth(text))/2, (h - fm.getHeight())/2 + fm.getAscent());
            }
        };
        thumbnailWrapper.setPreferredSize(new Dimension(170, 100));
        thumbnailWrapper.setOpaque(false);

        // 2. AREA TEKS & TOMBOL BAWAH
        JPanel bottomArea = new JPanel();
        bottomArea.setOpaque(false);
        bottomArea.setLayout(new BoxLayout(bottomArea, BoxLayout.Y_AXIS));
        // Margin dalam area teks: Atas=8, Kiri=12, Bawah=12, Kanan=12
        bottomArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        // Judul Rekomendasi
        JLabel titleLabel = new JLabel("<html><div style='width:146px; font-family:Poppins;'>"
                + recommendation.getTitle() + "</div></html>");
        titleLabel.setFont(FontManager.getPoppins(13f).deriveFont(Font.BOLD));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(0f);

        // Deskripsi Rekomendasi (DIPOTONG DI HOME)
        JLabel descLabel = new JLabel("<html><div style='width:146px; font-family:Poppins;'>"
                + recommendation.getDescription() + "</div></html>"); 
        descLabel.setFont(FontManager.getPoppins(11f));
        descLabel.setForeground(new Color(100, 116, 139));
        descLabel.setAlignmentX(0f);
        // Padding aman antara deskripsi dan tombol (Bawah=10)
        descLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 10, 0));

        // Tombol Mulai
        RoundedButton startButton = new RoundedButton();
        startButton.setText("Mulai");
        startButton.setCornerRadius(10);
        startButton.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
        startButton.setPreferredSize(new Dimension(85, 30));
        startButton.setMaximumSize(new Dimension(85, 30));
        startButton.setBackground(new Color(137, 126, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setAlignmentX(0f);
        // Listener tombol dibedakan dari listener card untuk performa
        startButton.addActionListener(e -> fireOpenDetail());

        bottomArea.add(titleLabel);
        bottomArea.add(descLabel);
        bottomArea.add(startButton);

        // Susun Card
        add(thumbnailWrapper, BorderLayout.NORTH);
        add(bottomArea, BorderLayout.CENTER);

        // Listener klik area kartu
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Periksa apakah tombol yang diklik (agar tidak double event)
                if(!startButton.getBounds().contains(e.getPoint())) {
                   fireOpenDetail();
                }
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
}