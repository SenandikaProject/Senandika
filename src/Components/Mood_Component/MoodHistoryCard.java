package Components.Mood_Component;

import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import senandika.Model.Mood;
import senandika.FontManager;

/**
 * Mood History Card
 * Menampilkan riwayat mood pengguna dengan icon gambar transparan dan format jam.
 */
public class MoodHistoryCard extends RoundedPanel {

    public MoodHistoryCard(Mood mood) {
        initComponents(mood);
    }

    private void initComponents(Mood mood) {
        setLayout(new BorderLayout(16, 0)); // Tambahkan ruang gap agar icon gambar tidak terlalu mepet teks
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        // Berikan ruang tinggi maksimum yang cukup untuk menampung teks multiline
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        setAlignmentX(LEFT_ALIGNMENT);

        // ---- 1. MEMUAT GAMBAR ICON MOOD UNGU SECARA DINAMIS ----
        JLabel emojiLabel = new JLabel();
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("Asset/aset-utama/mood" + mood.getTingkatMood() + "-purple.png");
            if (imgURL != null) {
                emojiLabel.setIcon(new ImageIcon(imgURL));
            } else {
                // Teks cadangan jika file gambar terhapus/tidak terbaca
                emojiLabel.setText("[" + mood.getTingkatMood() + "]");
                emojiLabel.setFont(FontManager.getPoppins(16f).deriveFont(Font.BOLD));
                emojiLabel.setForeground(new Color(137, 126, 255));
            }
        } catch (Exception e) {
            emojiLabel.setText(String.valueOf(mood.getTingkatMood()));
        }

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Menampilkan label kustom baru (Sedih, Murung, Marah, Senang, Ceria)
        JLabel moodLabel = new JLabel(mood.getMoodLabel());
        moodLabel.setFont(FontManager.getPoppins(14f).deriveFont(Font.BOLD));
        moodLabel.setForeground(new Color(30, 41, 59));
        moodLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Menampilkan Tanggal beserta Jam entri data
        JLabel dateLabel = new JLabel(formatDate(mood.getTanggal()));
        dateLabel.setFont(FontManager.getPoppins(12f));
        dateLabel.setForeground(new Color(100, 116, 139));
        dateLabel.setAlignmentX(LEFT_ALIGNMENT);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        textPanel.add(moodLabel);
        textPanel.add(dateLabel);

        // Menampilkan Catatan Harian pengguna
        if (mood.getCatatan() != null && !mood.getCatatan().trim().isEmpty()) {
            JLabel noteLabel = new JLabel("<html><div style='width:220px;'>“" + mood.getCatatan() + "”</div></html>");
            noteLabel.setFont(FontManager.getPoppins(11f));
            noteLabel.setForeground(new Color(100, 116, 139));
            noteLabel.setAlignmentX(LEFT_ALIGNMENT);
            noteLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
            textPanel.add(noteLabel);
        }

        add(emojiLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }

    /**
     * Memformat string tanggal "yyyy-MM-dd HH:mm" atau "yyyy-MM-dd" menjadi "21 Juni 2026 10:00"
     */
    private static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) {
            return "";
        }

        try {
            String clean = isoDate.trim();

            java.time.LocalDate date = java.time.LocalDate.parse(clean.substring(0, 10));

            int indexT = clean.contains("T") ? clean.indexOf("T") : clean.indexOf(" ");
            String jamStr = " 23:00"; // Fallback default

            if (indexT != -1 && clean.length() >= indexT + 6) {
                String jamMentah = clean.substring(indexT + 1, indexT + 3); 
                String menitMentah = clean.substring(indexT + 4, indexT + 6); 

                int jamAngka = Integer.parseInt(jamMentah);

                int jamLokal = (jamAngka + 16) % 24; 

                if (jamAngka + 16 >= 24) {
                    date = date.plusDays(1);
                }

                jamStr = String.format(" %02d:%s", jamLokal, menitMentah);
            }

            String[] bulan = {
                "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
            };

            return date.getDayOfMonth() + " " + bulan[date.getMonthValue() - 1] + " " + date.getYear() + jamStr;

        } catch (Exception e) {
            return isoDate; // Fallback aman jika gagal parsing
        }
    }
}