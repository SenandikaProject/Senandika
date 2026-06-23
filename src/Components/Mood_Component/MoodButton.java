package Components.Mood_Component;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import senandika.FontManager;

/**
 * MoodButton
 * Digunakan pada halaman Mood untuk memilih mood pengguna.
 */
public class MoodButton extends JLabel {

    public interface OnSelectListener {
        void onSelect(int level);
    }

    private final int level;
    private boolean isSelected = false;
    private ImageIcon iconGray;
    private ImageIcon iconPurple;
    private OnSelectListener listener;

    public MoodButton(int level, String placeholderText) {
    this.level = level;
    
        setPreferredSize(new Dimension(50, 50));
        setMinimumSize(new Dimension(50, 50));

        // Mengizinkan penangkapan event grafis di atas panel transparan
        setOpaque(false); 

        try {
            java.net.URL grayURL = getClass().getClassLoader().getResource("Asset/aset-utama/mood" + level + "-gray.png");
            java.net.URL purpleURL = getClass().getClassLoader().getResource("Asset/aset-utama/mood" + level + "-purple.png");

            if (grayURL != null && purpleURL != null) {
                iconGray = new ImageIcon(grayURL);
                iconPurple = new ImageIcon(purpleURL);
                setIcon(iconGray);
                setText(""); 
            } else {
                setText(placeholderText);
            }
        } catch (Exception e) {
            setText(placeholderText);
            setFont(senandika.FontManager.getPoppins(13f));
        }

        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER); // Pastikan posisi vertikalnya di tengah
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onSelect(level);
                }
            }
        });
    }

    public int getLevel() {
        return level;
    }

    public void setOnSelect(OnSelectListener listener) {
        this.listener = listener;
    }

    /**
     * Mengubah icon secara dinamis saat status tombol dipilih/dilepas
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (iconPurple != null && iconGray != null) {
            // Jika selected ganti ke ungu, jika tidak kembalikan ke abu-abu
            setIcon(isSelected ? iconPurple : iconGray);
        } else {
            // Fallback warna teks jika menggunakan aset font teks biasa
            setForeground(isSelected ? new java.awt.Color(137, 126, 255) : java.awt.Color.GRAY);
        }
        repaint();
    }
}