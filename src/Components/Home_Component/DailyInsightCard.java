package Components.Home_Component;

import Components.RoundedPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import senandika.FontManager;

public class DailyInsightCard extends RoundedPanel {

    private JLabel quoteLabel;

    public DailyInsightCard() {
        initComponents("Apapun suasana hatimu hari ini, perasaanmu berharga. Tetap melangkah pelan-pelan ya.");
    }

    private void initComponents(String quote) {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
            )
        );

        // Mengubah tag HTML menjadi rata kiri (<p align='left'>) dan SwingConstants.LEFT
        quoteLabel = new JLabel("<html><p align='left'>" + quote + "</p></html>", SwingConstants.LEFT);
        quoteLabel.setFont(FontManager.getPoppins(12.5f).deriveFont(Font.PLAIN));
        quoteLabel.setForeground(new Color(71, 85, 105));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; // Mengunci posisi komponen di sebelah kiri

        add(quoteLabel, gbc);
        setPreferredSize(new Dimension(344, 110));
    }

    public void updateByMood(int tingkatMood) {
        String teksSapaanClara = "Apapun suasana hatimu hari ini, perasaanmu berharga. Tetap melangkah pelan-pelan ya.";

        if (tingkatMood == 1) {
            teksSapaanClara = "Perasaanmu valid, apapun itu. Beri dirimu ruang untuk merasakannya. Clara ada di sini menemani.";
        } else if (tingkatMood == 2) {
            teksSapaanClara = "Hari yang terasa sepi ya? Tidak apa-apa untuk rehat sejenak. Ambil napas dalam-dalam, kamu sudah berusaha.";
        } else if (tingkatMood == 3) {
            teksSapaanClara = "Rasanya kesal sekali, ya? Salurkan energimu pelan-pelan. Tarik napas... embuskan. Semua akan mereda.";
        } else if (tingkatMood == 4) {
            teksSapaanClara = "Senang melihatmu tersenyum! Pertahankan energi positif ini dan bagikan kebahagiaanmu hari ini!";
        } else if (tingkatMood == 5) {
            teksSapaanClara = "Wah, harimu penuh warna! Semoga keceriaan yang luar biasa ini terus bertahan menemani aktivitas serumu!";
        }

        // Memastikan teks baru tetap rata kiri
        quoteLabel.setText("<html><p align='left'>" + teksSapaanClara + "</p></html>");
    }
}