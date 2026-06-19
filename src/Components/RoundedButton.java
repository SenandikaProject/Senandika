/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 *
 * @author lenovo
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;


import javax.swing.JButton;

public class RoundedButton extends JButton {
    private int cornerRadius = 20;
    public RoundedButton() {
        
        setOpaque(false);     // Allows custom painting
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        
        // Optional hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(getBackground().brighter());
            }
        });
    }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Enable anti-aliasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill rounded rectangle
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

            // Draw text
            super.paintComponent(g);

            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.draw(new RoundRectangle2D.Double(
                    0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
            g2.dispose();
        }

        // Allow changing the radius
        public void setCornerRadius(int radius) {
            this.cornerRadius = radius;
            repaint();
        }
}
