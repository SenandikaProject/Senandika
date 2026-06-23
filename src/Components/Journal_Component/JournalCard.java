package Components.Journal_Component;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;
import senandika.UILayer.CreateJournal;
import senandika.UILayer.DetailJournal;
import senandika.UILayer.EditJournal;

public class JournalCard extends JPanel {

    private JPanel contentPanel;
    private JPanel listJurnalContainer; // Panel khusus menampung item-item card hasil filter
    private JFrame parentFrame;
    
    private JournalSearchBar searchBar;
    private JournalFilterBar filterBar;
    private JournalStreakCard streakCard;
    private JournalService service;

    public JournalCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.service = new JournalService();
        
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
            // Ambil semua data awal dari database untuk menghitung status streak minggu ini
            List<JournalData> allJournals = service.getFilteredJournals("Semua", "");
            contentPanel.removeAll();

            if (allJournals.isEmpty()) {
                contentPanel.add(new JournalEmptyState(parentFrame));
            } else {
                // ====================================================================
                // URUTKAN DATA BERDASARKAN ID JURNAL SECARA DESCENDING (Terbaru di Atas)
                // ====================================================================
                allJournals.sort((j1, j2) -> Integer.compare(j2.getId(), j1.getId()));
                // ====================================================================

                // 1. Pasang Kartu Streak
                int streak = service.getStreak();
                streakCard = new JournalStreakCard();
                streakCard.setStreak(streak);
                streakCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                // Kalkulasi otomatis centang hari senin-minggu dari database
                boolean[] activeDays = hitungHariAktifMingguIni(allJournals);
                streakCard.setStreakStatus(activeDays);
                contentPanel.add(streakCard);
                contentPanel.add(Box.createVerticalStrut(20));
                
                // 2. Pasang Tombol Tambah Jurnal
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
                
                // 3. Judul Section
                JLabel title = new JLabel("Daftar Jurnal");
                title.setFont(new Font("Poppins", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(15));
                
                // 4. Panel Kontrol (Search & Filter)
                JPanel controlWrapper = new JPanel(new BorderLayout(10, 0));
                controlWrapper.setOpaque(false);
                controlWrapper.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
                controlWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

                searchBar = new JournalSearchBar();
                filterBar = new JournalFilterBar();

                controlWrapper.add(searchBar, BorderLayout.CENTER);
                controlWrapper.add(filterBar, BorderLayout.EAST);
                contentPanel.add(controlWrapper);
                contentPanel.add(Box.createVerticalStrut(20));
                
                // Container list jurnal
                listJurnalContainer = new JPanel();
                listJurnalContainer.setOpaque(false);
                listJurnalContainer.setLayout(new BoxLayout(listJurnalContainer, BoxLayout.Y_AXIS));
                listJurnalContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(listJurnalContainer);

                // Ambil list master dari database pertama kali saat halaman dibuka
                List<JournalData> masterJournals = allJournals; 

                // 1. Logika Search: Realtime menyesuaikan ketikan user
                searchBar.addSearchListener(() -> {
                    String query = searchBar.getSearchQuery().toLowerCase();
                    listJurnalContainer.removeAll();

                    if (query.isEmpty()) {
                        for (JournalData journal : masterJournals) {
                            JournalItemCard card = new JournalItemCard();
                            card.setData(journal.getId(), journal.getJudul(), journal.getTanggal());
                            setupCardActions(card);
                            listJurnalContainer.add(card);
                            listJurnalContainer.add(Box.createVerticalStrut(15));
                        }
                    } else {
                        int matchCount = 0;
                        for (JournalData journal : masterJournals) {
                            if (journal.getJudul().toLowerCase().contains(query)) {
                                JournalItemCard card = new JournalItemCard();
                                card.setData(journal.getId(), journal.getJudul(), journal.getTanggal());
                                setupCardActions(card);
                                listJurnalContainer.add(card);
                                listJurnalContainer.add(Box.createVerticalStrut(15));
                                matchCount++;
                            }
                        }
                        if (matchCount == 0) {
                            JLabel lblEmpty = new JLabel("Tidak ada judul jurnal yang cocok.");
                            lblEmpty.setFont(new Font("Poppins", Font.ITALIC, 14));
                            lblEmpty.setForeground(Color.GRAY);
                            lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
                            listJurnalContainer.add(lblEmpty);
                        }
                    }
                    listJurnalContainer.revalidate();
                    listJurnalContainer.repaint();
                });

                // 2. Logika Filter Dropdown dengan Konfirmasi Manual
                filterBar.addFilterListener(e -> {
                    String selectedFilter = filterBar.getSelectedFilter();

                    int konfirmasi = JOptionPane.showConfirmDialog(
                        this, 
                        "Apakah Anda ingin menerapkan filter \"" + selectedFilter + "\"?", 
                        "Konfirmasi Filter", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );

                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        try {
                            List<JournalData> filteredJournals = service.getFilteredJournals(selectedFilter, "");

                            // Pastikan data hasil filter dari backend juga ikut diurutkan ke yang terbaru
                            filteredJournals.sort((j1, j2) -> Integer.compare(j2.getId(), j1.getId()));

                            allJournals.clear();
                            allJournals.addAll(filteredJournals);

                            listJurnalContainer.removeAll();

                            if (filteredJournals.isEmpty()) {
                                JLabel lblEmpty = new JLabel("Tidak ada jurnal di periode ini.");
                                lblEmpty.setFont(new Font("Poppins", Font.ITALIC, 14));
                                lblEmpty.setForeground(Color.GRAY);
                                lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
                                listJurnalContainer.add(lblEmpty);
                            } else {
                                for (JournalData journal : filteredJournals) {
                                    JournalItemCard card = new JournalItemCard();
                                    card.setData(journal.getId(), journal.getJudul(), journal.getTanggal());
                                    setupCardActions(card);
                                    listJurnalContainer.add(card);
                                    listJurnalContainer.add(Box.createVerticalStrut(15));
                                }
                            }

                            listJurnalContainer.revalidate();
                            listJurnalContainer.repaint();
                            contentPanel.revalidate();
                            contentPanel.repaint();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Gagal memfilter data: " + ex.getMessage());
                        }
                    }
                });

                // Tampilkan data awal secara utuh saat pertama kali dimuat (Sudah urut DESC)
                for (JournalData journal : allJournals) {
                    JournalItemCard card = new JournalItemCard();
                    card.setData(journal.getId(), journal.getJudul(), journal.getTanggal());
                    setupCardActions(card);
                    listJurnalContainer.add(card);
                    listJurnalContainer.add(Box.createVerticalStrut(15));
                }
                contentPanel.add(Box.createVerticalGlue());
            }

            revalidate();
            repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat: " + e.getMessage());
        }
    }

    
    // Logika pengisian centang otomatis berdasarkan tanggal data dari DB
    private boolean[] hitungHariAktifMingguIni(List<JournalData> journals) {
        boolean[] activeDays = new boolean[7];
        Calendar calJurnal = Calendar.getInstance();
        Calendar calSekarang = Calendar.getInstance();
        
        // Format parser tanggal disesuaikan dengan isi format kolom string DB kamu 
        // Contoh penanganan format ISO: "2026-06-20T16:00:00.000Z"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (JournalData j : journals) {
            try {
                // Bersihkan string tanggal jika mengandung token T atau Z jam
                String cleanStr = j.getTanggal().split("T")[0];
                calJurnal.setTime(sdf.parse(cleanStr));
                
                // Pastikan jurnal berada di tahun dan minggu yang sama dengan minggu saat ini
                if (calJurnal.get(Calendar.YEAR) == calSekarang.get(Calendar.YEAR) &&
                    calJurnal.get(Calendar.WEEK_OF_YEAR) == calSekarang.get(Calendar.WEEK_OF_YEAR)) {
                    
                    int dayOfWeek = calJurnal.get(Calendar.DAY_OF_WEEK); 
                    // Konversi format Java (Ahad=1, Senin=2, ... Sabtu=7) ke format index-mu (Senin=0 ... Minggu=6)
                    int indexHari = 0;
                    if (dayOfWeek == Calendar.SUNDAY) {
                        indexHari = 6;
                    } else {
                        indexHari = dayOfWeek - 2;
                    }
                    
                    if (indexHari >= 0 && indexHari < 7) {
                        activeDays[indexHari] = true;
                    }
                }
            } catch (Exception e) {
                // Lewati data baris jika parsing bermasalah
            }
        }
        return activeDays;
    }
    
    private void setupCardActions(
            JournalItemCard card
    ) {
        // DETAIL
        card.getBtnDetail()
            .addActionListener(e -> {

                try {

//                    DetailJournal detail =
//                            new DetailJournal(
//                                    card.getJournalId()
//                            );
//
//                    detail.setVisible(true);

                    if(parentFrame != null){
                        parentFrame.dispose();
                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage()
                    );
                }
            });

        // EDIT
        card.getBtnEdit()
            .addActionListener(e -> {

                try {

                    EditJournal edit =
                            new EditJournal(
                                    card.getJournalId()
                            );

                    edit.setVisible(true);

                    if(parentFrame != null){
                        parentFrame.dispose();
                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage()
                    );
                }
            });

        // DELETE
        card.getBtnDelete()
            .addActionListener(e -> {

                int confirm =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Yakin ingin menghapus jurnal ini?",
                                "Konfirmasi",
                                JOptionPane.YES_NO_OPTION
                        );

                if(confirm ==
                        JOptionPane.YES_OPTION){

                    try {

                        service.deleteJournal(
                                card.getJournalId()
                        );

                        JOptionPane.showMessageDialog(
                                this,
                                "Jurnal berhasil dihapus"
                        );

                        loadData();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage()
                        );
                    }
                }
            });
    }
    
}