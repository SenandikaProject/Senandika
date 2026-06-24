package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class JournalStreakCard extends JPanel {

    private JLabel lblWeek;
    private JLabel lblStreak;
    // Kita ganti JLabel biasa menjadi panel lingkaran custom untuk indikator hari
    private DayIndicator[] dayIndicators = new DayIndicator[7];

    public JournalStreakCard() {
        setLayout(new BorderLayout(0, 15));
        // Menggunakan padding dalam (empty border) agar layout bernapas
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 220, 255), 2, true), // rounded border tipis
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        setBackground(new Color(248, 253, 249)); // Soft mint/green background sesuai tema awal
        
        // --- Header Section (Minggu ke-x) ---
        lblWeek = new JLabel("Minggu ini", SwingConstants.LEFT);
        lblWeek.setFont(FontManager.getPoppins(16f)); // Menggunakan Poppins agar modern
        lblWeek.setForeground(new Color(100, 90, 220));
        add(lblWeek, BorderLayout.NORTH);

        // --- Center Section (Grid Hari) ---
        JPanel daysGrid = new JPanel(new GridLayout(1, 7, 12, 0));
        daysGrid.setOpaque(false);

        String[] namaHari = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};

        for (int i = 0; i < namaHari.length; i++) {
            JPanel dayBox = new JPanel();
            dayBox.setOpaque(false);
            dayBox.setLayout(new BoxLayout(dayBox, BoxLayout.Y_AXIS));

            // Label Nama Hari
            JLabel lblHari = new JLabel(namaHari[i]);
            lblHari.setFont(new Font("Poppins", Font.BOLD, 12));
            lblHari.setForeground(new Color(140, 140, 150));
            lblHari.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Indikator Desain Lingkaran Baru (Pengganti emoji ⬜/✓)
            dayIndicators[i] = new DayIndicator();
            dayIndicators[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            dayBox.add(lblHari);
            dayBox.add(Box.createVerticalStrut(8)); // Jarak vertikal yang ideal
            dayBox.add(dayIndicators[i]);

            daysGrid.add(dayBox);
        }
        add(daysGrid, BorderLayout.CENTER);
        
        // --- Footer Section (Total Streak) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        lblStreak = new JLabel("0 Hari", SwingConstants.CENTER);
        lblStreak.setFont(new Font("Poppins", Font.BOLD, 22)); // Ukuran angka streak diperbesar agar fokus utama
        lblStreak.setForeground(new Color(137, 126, 255));
        
        JLabel lblStreakDesc = new JLabel("🔥 Beruntun", SwingConstants.CENTER);
        lblStreakDesc.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblStreakDesc.setForeground(Color.GRAY);
        
        bottomPanel.add(lblStreak, BorderLayout.CENTER);
        bottomPanel.add(lblStreakDesc, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void setStreak(int streak) {
        if (lblStreak == null) return;
        lblStreak.setText(streak + " Hari");

        if (streak >= 7) {
            lblStreak.setForeground(new Color(255, 120, 0)); // Orange menyala untuk perfect week
        } else if (streak >= 3) {
            lblStreak.setForeground(new Color(137, 126, 255)); // Ungu bawaan
        } else {
            lblStreak.setForeground(new Color(160, 160, 170)); // Abu-abu jika baru memulai
        }
    }
    
    // Memperbarui tampilan centang/lingkaran aktif
    public void setStreakStatus(boolean[] activeDays) {
        if (activeDays == null || activeDays.length < 7) return;

        for (int i = 0; i < dayIndicators.length; i++) {
            dayIndicators[i].setActive(activeDays[i]);
        }
        
        // PENTING: Memaksa Swing menggambar ulang komponen secara realtime setelah data masuk
        revalidate();
        repaint();
    }

    // --- INNER CLASS: Komponen Lingkaran Indikator yang Lebih Estetik ---
    private static class DayIndicator extends JComponent {
        private boolean isActive = false;
        private final Color activeColor = new Color(137, 126, 255); // Ungu Tema
        private final Color inactiveColor = new Color(220, 225, 230); // Abu-abu Elegan

        public DayIndicator() {
            setPreferredSize(new Dimension(28, 28));
            setMinimumSize(new Dimension(28, 28));
            setMaximumSize(new Dimension(28, 28));
        }

        public void setActive(boolean active) {
            this.isActive = active;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = 24;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            if (isActive) {
                // Gambar lingkaran penuh warna ungu
                g2.setColor(activeColor);
                g2.fillOval(x, y, size, size);

                // Gambar tanda centang putih minimalis di dalamnya
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x + 7, y + 12, x + 11, y + 16);
                g2.drawLine(x + 11, y + 16, x + 17, y + 8);
            } else {
                // Gambar lingkaran border putus-putus / kosong abu-abu tipis
                g2.setColor(inactiveColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(x, y, size, size);
                
                // Titik kecil di tengah agar tidak terlalu kosong
                g2.fillOval(x + (size/2) - 2, y + (size/2) - 2, 4, 4);
            }
            g2.dispose();
        }
    }
}