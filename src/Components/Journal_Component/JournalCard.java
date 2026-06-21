package Components.Journal_Component;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;
import senandika.UILayer.CreateJournal;

public class JournalCard extends JPanel {

    private JPanel contentPanel;
    private JFrame parentFrame;
    
    public JournalCard(JFrame parentFrame) {
        this.parentFrame = parentFrame; // Amankan instance frame
        setLayout(new BorderLayout());
        setOpaque(false);

        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 16, 20, 16));

        add(contentPanel, BorderLayout.CENTER);
    }
    
    public void loadData() {
        try {
            JournalService service = new JournalService();
            List<JournalData> journals = service.getJournals();

            contentPanel.removeAll();

            if (journals.isEmpty()) {
                // SAKTI: Berikan parameter parentFrame ke Empty State
                contentPanel.add(new JournalEmptyState(parentFrame));
            } else {
                int streak = service.getStreak();

                JournalStreakCard streakCard = new JournalStreakCard();
                streakCard.setStreak(streak);
                streakCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                // --- INTEGRASI LOGIKA CENTANG OTOMATIS ---
                // Buat array status hari [Sen, Sel, Rab, Kam, Jum, Sab, Min]
                boolean[] activeDays = new boolean[7];
                
                // Iterasi data jurnal untuk mendeteksi hari apa saja yang sudah terisi
                for (JournalData j : journals) {
                    // Kamu perlu mengekstrak integer hari dari string j.getTanggal() milikmu.
                    // Contoh logika sederhana (kamu bisa sesuaikan dengan format tanggal DB-mu):
                    // int dayIndex = AmbilHariDariTanggal(j.getTanggal()); // menghasilkan 0-6
                    // if(dayIndex >= 0 && dayIndex < 7) activeDays[dayIndex] = true;
                }
                
                // MOCKING TESTING: Sementara uji coba manual untuk melihat hasilnya centang
                activeDays[0] = true; // Senin centang
                activeDays[2] = true; // Rabu centang
                activeDays[3] = true; // Kamis centang
                
                // Kirim array status ke streakCard
                streakCard.setStreakStatus(activeDays);
                // ------------------------------------------

                contentPanel.add(streakCard);
                contentPanel.add(Box.createVerticalStrut(20));
                
                // --- SEKSI TOMBOL TAMBAH JURNAL BARU ---
                JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                btnWrapper.setOpaque(false);
                btnWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                Components.RoundedButton btnTambah = new Components.RoundedButton();
                btnTambah.setText("+ Tambah Jurnal");
                btnTambah.setBackground(new Color(137, 126, 255));
                btnTambah.setForeground(Color.WHITE);

                btnTambah.addActionListener(e -> {
                    new CreateJournal().setVisible(true);
                    if(parentFrame != null) parentFrame.dispose();
                });
                
                btnWrapper.add(btnTambah);
                contentPanel.add(btnWrapper);
                contentPanel.add(Box.createVerticalStrut(15));
                
                JLabel title = new JLabel("Daftar Jurnal");
                title.setFont(new Font("Poppins", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(15));

                for (JournalData journal : journals) {
                    JournalItemCard card = new JournalItemCard();
                    card.setData(journal.getId(), journal.getJudul(), journal.getTanggal());
                    contentPanel.add(card);
                    contentPanel.add(Box.createVerticalStrut(15));
                }
            }

            revalidate();
            repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}