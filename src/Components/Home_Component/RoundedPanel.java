/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private Color backgroundColorOverride;
    private boolean shadowEnabled;
    private Color borderColor; // null = tanpa border

    public RoundedPanel() {
        // Default menggunakan radius 16, background PUTIH, dan mengaktifkan soft shadow
        this(16, Color.WHITE, true);
    }

    public RoundedPanel(int cornerRadius, Color backgroundColor, boolean shadowEnabled) {
        this.cornerRadius = cornerRadius;
        this.backgroundColorOverride = backgroundColor;
        this.shadowEnabled = shadowEnabled;
        setOpaque(false); // Memastikan background default kotak swing tidak ikut digambar
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setCardBackground(Color color) {
        this.backgroundColorOverride = color;
        repaint();
    }

    public void setShadowEnabled(boolean enabled) {
        this.shadowEnabled = enabled;
        repaint();
    }

    public void setCardBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int shadowOffset = shadowEnabled ? 4 : 0;

        // Soft shadow (digambar sedikit bergeser ke bawah area kartu)
        if (shadowEnabled) {
            // Menggunakan warna hitam transparan tipis (alfa = 15) agar bayangan terlihat halus/modern
            g2.setColor(new Color(0, 0, 0, 15));
            g2.fillRoundRect(2, shadowOffset, width - 4, height - shadowOffset - 2,
                    cornerRadius, cornerRadius);
        }

        // Latar belakang utama kartu (default ke Putih jika null)
        g2.setColor(backgroundColorOverride != null ? backgroundColorOverride : Color.WHITE);
        int cardHeight = shadowEnabled ? height - shadowOffset : height;
        g2.fillRoundRect(0, 0, width - (shadowEnabled ? 2 : 0), cardHeight, cornerRadius, cornerRadius);

        // Border opsional halus (jika diset dari luar)
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, width - (shadowEnabled ? 4 : 2), cardHeight - 2,
                    cornerRadius, cornerRadius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
