/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Home_Component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import senandika.FontManager;


public class RoundedButton extends JButton {

    public enum Style { FILLED, OUTLINE }

    private boolean hovered = false;
    private Style style;
    private int cornerRadius;
    private Color fillColor;
    private Color hoverFillColor;
    private Color outlineBorderColor;

    public RoundedButton() {
        this("");
    }

    public RoundedButton(String text) {
        this(text, Style.FILLED);
    }

    public RoundedButton(String text, Style style) {
        super(text);
        this.style = style;
        
        // Default radius disesuaikan dengan rata-rata komponen Journal (12px)
        this.cornerRadius = 12; 
        
        // Default token warna utama menggunakan Ungu Senandika (137, 126, 255)
        this.fillColor = new Color(137, 126, 255);
        this.hoverFillColor = new Color(110, 98, 230); // Ungu lebih gelap untuk hover
        this.outlineBorderColor = new Color(226, 232, 240); // Border subtle abu-abu

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Font otomatis menggunakan FontManager Poppins
        setFont(FontManager.getPoppins(13f).deriveFont(java.awt.Font.BOLD));
        setForeground(style == Style.FILLED ? Color.WHITE : new Color(110, 98, 230));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    // Override setBackground agar tetap fleksibel ketika diatur manual dari luar seperti di Journal
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.fillColor = bg;
        // Membuat efek hover otomatis sedikit lebih gelap dari background yang dimasukkan
        this.hoverFillColor = darkenColor(bg);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        repaint();
    }

    public void setHoverFillColor(Color hoverFillColor) {
        this.hoverFillColor = hoverFillColor;
        repaint();
    }

    public void setOutlineBorderColor(Color outlineBorderColor) {
        this.outlineBorderColor = outlineBorderColor;
        repaint();
    }

    /**
     * Fungsi utilitas pembantu untuk menggelapkan warna secara otomatis saat hover
     */
    private Color darkenColor(Color color) {
        if (color == null) return null;
        int r = Math.max(0, color.getRed() - 25);
        int g = Math.max(0, color.getGreen() - 25);
        int b = Math.max(0, color.getBlue() - 25);
        return new Color(r, g, b, color.getAlpha());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (style == Style.FILLED) {
            g2.setColor(hovered ? hoverFillColor : fillColor);
            g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        } else {
            // Style OUTLINE: Latar belakang berganti ungu transparan soft saat di-hover
            g2.setColor(hovered ? new Color(241, 236, 255) : Color.WHITE);
            g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
            
            g2.setColor(outlineBorderColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
