package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class JournalStreakCard extends JPanel {

    private JLabel lblWeek;
    private JLabel lblStreak;
    // PENTING: Sediakan array global untuk menyimpan instance JLabel centang/hari
    private JLabel[] checkLabels = new JLabel[7];

    public JournalStreakCard(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(new Color(215,190,255)));
        setBackground(new Color(246,255,248));
        
        lblStreak = new JLabel("0 Hari", SwingConstants.CENTER);
        lblStreak.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblStreak, BorderLayout.SOUTH);
    
        lblWeek = new JLabel("Minggu ke-1", SwingConstants.CENTER);
        lblWeek.setFont(FontManager.getPoppins(20f));
        lblWeek.setForeground(new Color(137,126,255));
        add(lblWeek, BorderLayout.NORTH);

        JPanel days = new JPanel(new GridLayout(1, 7, 8, 8));
        days.setOpaque(false);

        String[] namaHari = {"Sen","Sel","Rab","Kam","Jum","Sab","Min"};

        for(int i = 0; i < namaHari.length; i++) {
            JPanel p = new JPanel();
            p.setOpaque(false);
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

            JLabel lblHari = new JLabel(namaHari[i], SwingConstants.CENTER);
            lblHari.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Simpan ke dalam array global checkLabels
            checkLabels[i] = new JLabel("⬜"); // Default-nya kotak kosong
            checkLabels[i].setFont(new Font("Arial", Font.BOLD, 18));
            checkLabels[i].setForeground(Color.GRAY);
            checkLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            p.add(lblHari);
            p.add(Box.createVerticalStrut(4)); // Beri jarak sedikit
            p.add(checkLabels[i]);

            days.add(p);
        }

        add(days, BorderLayout.CENTER);
    }
    
    public void setStreak(int streak) {
        if (lblStreak == null) return;
        lblStreak.setText(streak + " Hari");

        if (streak >= 7) {
            lblStreak.setForeground(new Color(255, 140, 0)); // orange
        } else if (streak >= 3) {
            lblStreak.setForeground(new Color(137, 126, 255)); // ungu default
        } else {
            lblStreak.setForeground(Color.GRAY);
        }
    }
    
    // Memperbarui tampilan centang berdasarkan array boolean dari JournalCard
    public void setStreakStatus(boolean[] activeDays) {
        if (activeDays == null || activeDays.length < 7) return;

        for (int i = 0; i < checkLabels.length; i++) {
            if (activeDays[i]) {
                checkLabels[i].setText("✓"); 
                checkLabels[i].setForeground(new Color(137, 126, 255)); // Ungu aktif
            } else {
                checkLabels[i].setText("⬜"); 
                checkLabels[i].setForeground(Color.GRAY);
            }
        }
    }
}