package Components.Journal_Component;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import javax.swing.*;
import senandika.FontManager;

public class JournalStreakCard extends JPanel {

    private JLabel lblWeek;
    private JLabel lblStreak;
    private JButton btnPrev;
    private JButton btnNext;
    private DayIndicator[] dayIndicators = new DayIndicator[7];
    
    private LocalDate currentWeekStart;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM", new Locale("id", "ID"));
    private ActionListener weekChangeListener;

    public JournalStreakCard() {
        this.currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 220, 255), 2, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        setBackground(new Color(248, 253, 249));
        
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);

        // MODIFIKASI: Menggunakan Ikon Chevron Modern Tipis (‹ dan ›)
        btnPrev = createNavButton("‹");
        btnPrev.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        btnNext = createNavButton("›");
        btnNext.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        lblWeek = new JLabel("", SwingConstants.CENTER);
        lblWeek.setFont(FontManager.getPoppins(14f));
        lblWeek.setForeground(new Color(100, 90, 220));
        updateWeekLabel();

        headerPanel.add(btnPrev, BorderLayout.WEST);
        headerPanel.add(lblWeek, BorderLayout.CENTER);
        headerPanel.add(btnNext, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        btnPrev.addActionListener(e -> {
            currentWeekStart = currentWeekStart.minusWeeks(1);
            updateWeekLabel();
            triggerWeekChange();
        });

        btnNext.addActionListener(e -> {
            // MODIFIKASI: Membatasi agar user TIDAK BISA maju lebih dari 1 minggu ke depan dari hari ini
            LocalDate maxWeekAllowed = LocalDate.now().plusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            if (currentWeekStart.plusWeeks(1).isAfter(maxWeekAllowed)) {
                JOptionPane.showMessageDialog(this, "Anda tidak dapat melihat rentang minggu terlalu jauh ke depan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            currentWeekStart = currentWeekStart.plusWeeks(1);
            updateWeekLabel();
            triggerWeekChange();
        });

        JPanel daysGrid = new JPanel(new GridLayout(1, 7, 10, 0));
        daysGrid.setOpaque(false);

        String[] namaHari = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};

        for (int i = 0; i < namaHari.length; i++) {
            JPanel dayBox = new JPanel();
            dayBox.setOpaque(false);
            dayBox.setLayout(new BoxLayout(dayBox, BoxLayout.Y_AXIS));

            JLabel lblHari = new JLabel(namaHari[i]);
            lblHari.setFont(new Font("Poppins", Font.BOLD, 11));
            lblHari.setForeground(new Color(140, 140, 150));
            lblHari.setAlignmentX(Component.CENTER_ALIGNMENT);

            dayIndicators[i] = new DayIndicator();
            dayIndicators[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            dayBox.add(lblHari);
            dayBox.add(Box.createVerticalStrut(6));
            dayBox.add(dayIndicators[i]);

            daysGrid.add(dayBox);
        }
        add(daysGrid, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        lblStreak = new JLabel("0 Hari", SwingConstants.CENTER);
        lblStreak.setFont(new Font("Poppins", Font.BOLD, 20));
        lblStreak.setForeground(new Color(137, 126, 255));
        
        JLabel lblStreakDesc = new JLabel("🔥 Beruntun", SwingConstants.CENTER);
        lblStreakDesc.setFont(new Font("Poppins", Font.PLAIN, 11));
        lblStreakDesc.setForeground(Color.GRAY);
        
        bottomPanel.add(lblStreak, BorderLayout.CENTER);
        bottomPanel.add(lblStreakDesc, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(new Color(137, 126, 255));
        return btn;
    }

    private void updateWeekLabel() {
        LocalDate currentWeekEnd = currentWeekStart.plusDays(6);
        LocalDate today = LocalDate.now();
        LocalDate startOfThisWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        
        if (currentWeekStart.equals(startOfThisWeek)) {
            lblWeek.setText("Minggu Ini (" + currentWeekStart.format(dateFormatter) + " - " + currentWeekEnd.format(dateFormatter) + ")");
        } else {
            lblWeek.setText(currentWeekStart.format(dateFormatter) + " - " + currentWeekEnd.format(dateFormatter));
        }
    }

    public void setOnWeekChangeListener(ActionListener listener) {
        this.weekChangeListener = listener;
    }

    private void triggerWeekChange() {
        if (weekChangeListener != null) {
            weekChangeListener.actionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, getSelectedWeekStartISO()));
        }
    }

    public String getSelectedWeekStartISO() {
        return currentWeekStart.toString();
    }
    
    public void setStreak(int streak) {
        if (lblStreak == null) return;
        lblStreak.setText(streak + " Hari");

        if (streak >= 7) {
            lblStreak.setForeground(new Color(255, 120, 0));
        } else if (streak >= 3) {
            lblStreak.setForeground(new Color(137, 126, 255));
        } else {
            lblStreak.setForeground(new Color(160, 160, 170));
        }
    }
    
    public void setStreakStatus(boolean[] activeDays) {
        if (activeDays == null || activeDays.length < 7) return;

        for (int i = 0; i < dayIndicators.length; i++) {
            dayIndicators[i].setActive(activeDays[i]);
        }
        revalidate();
        repaint();
    }

    private static class DayIndicator extends JComponent {
        private boolean isActive = false;
        private final Color activeColor = new Color(137, 126, 255);
        private final Color inactiveColor = new Color(220, 225, 230);

        public DayIndicator() {
            setPreferredSize(new Dimension(26, 26));
            setMinimumSize(new Dimension(26, 26));
            setMaximumSize(new Dimension(26, 26));
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

            int size = 22;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            if (isActive) {
                g2.setColor(activeColor);
                g2.fillOval(x, y, size, size);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x + 6, y + 11, x + 10, y + 15);
                g2.drawLine(x + 10, y + 15, x + 16, y + 7);
            } else {
                g2.setColor(inactiveColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(x, y, size, size);
                g2.fillOval(x + (size/2) - 1, y + (size/2) - 1, 3, 3);
            }
            g2.dispose();
        }
    }
}