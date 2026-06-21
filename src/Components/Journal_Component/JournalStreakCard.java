package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class JournalStreakCard extends JPanel {

    private JLabel lblWeek;
    private JLabel lblStreak;
    public JournalStreakCard(){

        setLayout(
            new BorderLayout()
        );

        setBorder(
            BorderFactory.createLineBorder(
                new Color(215,190,255)
            )
        );

        setBackground(
            new Color(246,255,248)
        );
        
        lblStreak = new JLabel("0 Hari", SwingConstants.CENTER);
        lblStreak.setFont(new Font("Arial", Font.BOLD, 16));
        lblStreak.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblStreak, BorderLayout.SOUTH);

    
        lblWeek = new JLabel(
            "Minggu ke-1",
            SwingConstants.CENTER
        );

        lblWeek.setFont(
            FontManager.getPoppins(20f)
        );

        lblWeek.setForeground(
            new Color(137,126,255)
        );

        add(lblWeek, BorderLayout.NORTH);

        JPanel days = new JPanel(
            new GridLayout(1,7,8,8)
        );

        String[] namaHari = {
            "Sen","Sel","Rab",
            "Kam","Jum","Sab","Min"
        };

        for(String hari : namaHari){

            JPanel p = new JPanel();
            p.setOpaque(false);
            p.setLayout(
                new BoxLayout(
                    p,
                    BoxLayout.Y_AXIS
                )
            );

            JLabel lblHari =
                new JLabel(
                    hari,
                    SwingConstants.CENTER
                );

            JLabel check =
                new JLabel("✓");

            check.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    20
                )
            );

            check.setForeground(
                new Color(
                    137,
                    126,
                    255
                )
            );

            lblHari.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            check.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            p.add(lblHari);
            p.add(check);

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
}