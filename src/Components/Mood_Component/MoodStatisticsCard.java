package Components.Mood_Component;

import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import senandika.FontManager;


public class MoodStatisticsCard extends RoundedPanel {

    private JLabel valueLabel;

    public MoodStatisticsCard(
            String icon,
            String label
    ) {

        initComponents(
                icon,
                label
        );
    }

    private void initComponents(String icon, String label) {
        setLayout(new BorderLayout(0, 2));
        setOpaque(false); 

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(8, 4, 8, 4) // Sederhanakan padding luar
        ));

        // Naikkan tinggi preferred size menjadi 105 agar tulisan multiline ke bawah aman menampung teks
        setPreferredSize(new Dimension(105, 105));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(FontManager.getPoppins(11f).deriveFont(Font.BOLD));
        iconLabel.setForeground(new Color(137, 126, 255)); 

        valueLabel = new JLabel("-", SwingConstants.CENTER);
        valueLabel.setFont(FontManager.getPoppins(18f).deriveFont(Font.BOLD));
        valueLabel.setForeground(new Color(30, 41, 59));

        JLabel captionLabel = new JLabel("<html><center>" + label + "</center></html>", SwingConstants.CENTER);
        captionLabel.setFont(FontManager.getPoppins(10f));
        captionLabel.setForeground(new Color(100, 116, 139));

        add(iconLabel, BorderLayout.NORTH);
        add(valueLabel, BorderLayout.CENTER);
        add(captionLabel, BorderLayout.SOUTH);
    }

    public void setValue(String value) {
        // Jika value berupa teks panjang (bukan angka rata-rata seperti 2.8), kecilkan font-nya
        if (value != null && value.length() > 4) {
            valueLabel.setFont(FontManager.getPoppins(12f).deriveFont(Font.BOLD));
            // Gunakan tag HTML agar teks bisa otomatis turun ke bawah jika space tidak cukup
            valueLabel.setText("<html><center>" + value + "</center></html>");
        } else {
            valueLabel.setFont(FontManager.getPoppins(18f).deriveFont(Font.BOLD));
            valueLabel.setText(value);
        }
    }
}