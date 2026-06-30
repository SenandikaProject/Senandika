package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import senandika.Model.JournalData;
import senandika.ServiceLayer.JournalService;
import senandika.UILayer.CreateJournal;
import senandika.UILayer.DetailJournal;
import senandika.UILayer.EditJournal;

public class JournalCard extends JPanel {

    private JPanel contentPanel;
    private JPanel listJurnalContainer; 
    private JFrame parentFrame;
    
    private JournalSearchBar searchBar;
    private JournalStreakCard streakCard;
    private JournalService service;
    
    private RoundedButton btnToggleSelect;
    private RoundedButton btnExecuteDelete;
    private RoundedButton btnTambah;
    
    private boolean isSelectionMode = false;
    private java.util.List<JournalItemCard> renderedCards = new java.util.ArrayList<>();
    
    private List<JournalData> masterJournals; 

    public JournalCard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.service = new JournalService();
        
        setLayout(new BorderLayout());
        setOpaque(false);

        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 16, 20, 16));
        
        // 1. PASANG KARTU STREAK
        streakCard = new JournalStreakCard();
        streakCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                
        // Ambil data awal saat komponen pertama kali dibuat
        loadData();

        // Listener Navigasi Rentang Minggu (Mencegah reset otomatis ke minggu ini saat klik prev/next)
        streakCard.setOnWeekChangeListener(e -> {
            String tanggalMulaiISO = e.getActionCommand(); 
            updateStreakCardData(tanggalMulaiISO);
        });
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    public void loadData() {
        try {
            List<JournalData> allJournals = service.getFilteredJournals("Semua", "");
            contentPanel.removeAll();
            // Ambil tanggal awal senin dari minggu yang saat ini sedang dibuka di UI
            String currentWeekStartISO = streakCard.getSelectedWeekStartISO();
            
            // 1. HITUNG STREAK DAN UPDATE STATE INDIKATORNYA
            int currentStreak = hitungTotalStreakAkumulatif(allJournals, currentWeekStartISO);
            // Mengambil tanggal batas awal minggu langsung dari status streakCard saat ini
            boolean[] activeDays = hitungHariAktifBerdasarkanMinggu(allJournals, currentWeekStartISO);

            streakCard.setStreak(currentStreak);
            streakCard.setStreakStatus(activeDays);
            
            // Kartu streak SELALU ditambahkan di paling atas, baik data kosong maupun ada
            contentPanel.add(streakCard);
            contentPanel.add(Box.createVerticalStrut(20));
            
            if (allJournals.isEmpty()) {
                contentPanel.add(new JournalEmptyState(parentFrame));
            } else {
                allJournals.sort((j1, j2) -> Integer.compare(j2.getId(), j1.getId()));
                this.masterJournals = allJournals;

                // ==========================================
                // Panel Aksi Utama (Tambah Jurnal & Seleksi)
                // ==========================================
                JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                actionPanel.setOpaque(false);
                actionPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
                actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // 1. Tombol Hapus Terpilih (Desain Modern Merah)
                btnExecuteDelete = new RoundedButton();
                btnExecuteDelete.setFont(new Font("Poppins", Font.BOLD, 12));
                btnExecuteDelete.setText("Hapus Terpilih");
                btnExecuteDelete.setBackground(new Color(239, 68, 68)); // Red-500 modern
                btnExecuteDelete.setForeground(Color.WHITE);
                btnExecuteDelete.setCornerRadius(8);
                btnExecuteDelete.setFocusPainted(false);
                btnExecuteDelete.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                btnExecuteDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnExecuteDelete.setVisible(false); // Sembunyikan awal

                // 2. Tombol Pilih Data / Batal (Desain Modern Hijau / Abu-abu)
                btnToggleSelect = new RoundedButton();
                btnToggleSelect.setFont(new Font("Poppins", Font.BOLD, 12));
                btnToggleSelect.setText("Pilih Data");
                btnToggleSelect.setBackground(new Color(34, 197, 94)); // Green-500 modern
                btnToggleSelect.setForeground(Color.WHITE);
                btnToggleSelect.setCornerRadius(8);
                btnToggleSelect.setFocusPainted(false);
                btnToggleSelect.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                btnToggleSelect.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // 3. Tombol Tambah Jurnal (Pertahankan tombol lamamu, sesuaikan padding jika perlu)
                // Misal: btnTambahJurnal = new JButton("+ Tambah Jurnal");
                btnTambah = new RoundedButton();
                btnTambah.setCornerRadius(8);
                btnTambah.setText("+ Tambah Jurnal");
                btnTambah.setBackground(new Color(137, 126, 255));
                btnTambah.setForeground(Color.WHITE);                

                // Pasang Event Listener
                btnToggleSelect.addActionListener(e -> toggleSelectionMode());
                btnExecuteDelete.addActionListener(e -> executeBulkDelete());
                btnTambah.addActionListener(e -> {
                    new CreateJournal().setVisible(true);
                    if(parentFrame != null) parentFrame.dispose();
                });

                // Masukkan ke dalam panel dengan urutan dari kanan ke kiri
                actionPanel.add(btnExecuteDelete);
                actionPanel.add(btnToggleSelect);
                actionPanel.add(btnTambah);

                // Masukkan actionPanel ini ke contentPanel utama sebelum komponen "Daftar Jurnal"
                contentPanel.add(actionPanel);
                contentPanel.add(Box.createVerticalStrut(20));
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

                controlWrapper.add(searchBar, BorderLayout.CENTER);

                contentPanel.add(controlWrapper);
                contentPanel.add(Box.createVerticalStrut(20));
                
                // 5. Container List Jurnal
                listJurnalContainer = new JPanel();
                listJurnalContainer.setOpaque(false);
                listJurnalContainer.setLayout(new BoxLayout(listJurnalContainer, BoxLayout.Y_AXIS));
                listJurnalContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(listJurnalContainer);

                // Logika Search
                searchBar.addSearchListener(() -> {
                    String query = searchBar.getSearchQuery().toLowerCase();
                    listJurnalContainer.removeAll();

                    if (query.isEmpty()) {
                        renderJurnalList(masterJournals);
                    } else {
                        java.util.ArrayList<JournalData> matched = new java.util.ArrayList<>();
                        for (JournalData journal : masterJournals) {
                            if (journal.getJudul().toLowerCase().contains(query)) {
                                matched.add(journal);
                            }
                        }
                        if (matched.isEmpty()) {
                            JLabel lblEmpty = new JLabel("Tidak ada judul jurnal yang cocok.");
                            lblEmpty.setFont(new Font("Poppins", Font.ITALIC, 14));
                            lblEmpty.setForeground(Color.GRAY);
                            lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
                            listJurnalContainer.add(lblEmpty);
                        } else {
                            renderJurnalList(matched);
                        }
                    }
                    listJurnalContainer.revalidate();
                    listJurnalContainer.repaint();
                });

                renderJurnalList(masterJournals);
                contentPanel.add(Box.createVerticalGlue());
            }

            revalidate();
            repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat: " + e.getMessage());
        }
    }
    
    // Fungsi pembantu untuk memperbarui tampilan mingguan saat tombol panah navigasi ditekan
    private void updateStreakCardData(String tanggalMulaiISO) {
        if (masterJournals == null) return;

        // Hitung ulang status centang ungu untuk minggu tersebut
        boolean[] activeDays = hitungHariAktifBerdasarkanMinggu(masterJournals, tanggalMulaiISO);
        streakCard.setStreakStatus(activeDays);

        // Hitung ulang total akumulasi streak historis mundur dari minggu tersebut
        int historicalStreak = hitungTotalStreakAkumulatif(masterJournals, tanggalMulaiISO);
        streakCard.setStreak(historicalStreak);
    }
    
    private void renderJurnalList(List<JournalData> journals) {
        renderedCards.clear(); // Kosongkan pelacak kartu lama
        for (JournalData journal : journals) {
            JournalItemCard card = new JournalItemCard();
            String tanggalData = (journal.getCreatedAt() != null) ? journal.getCreatedAt() : journal.getTanggal();
            card.setData(journal.getId(), journal.getJudul(), tanggalData);

            // Tetapkan mode kartu saat ini (apakah sedang dalam mode edit massal atau normal)
            card.setSelectionMode(isSelectionMode);

            setupCardActions(card);
            renderedCards.add(card); // Simpan referensi kartu ke list pelacak
            listJurnalContainer.add(card);
            listJurnalContainer.add(Box.createVerticalStrut(15));
        }
    }

    // Fungsi untuk mengubah mode UI dari normal ke pilihan massal (dan sebaliknya)
    private void toggleSelectionMode() {
        isSelectionMode = !isSelectionMode;

        if (isSelectionMode) {
            btnToggleSelect.setText("Batal");
            btnToggleSelect.setBackground(new Color(156, 163, 175)); // Gray-400 modern
            btnExecuteDelete.setVisible(true);
        } else {
            btnToggleSelect.setText("Pilih Data");
            btnToggleSelect.setBackground(new Color(34, 197, 94)); // Kembali ke Green-500
            btnExecuteDelete.setVisible(false);
        }

        // Update visibilitas checkbox pada kartu jurnal
        for (JournalItemCard card : renderedCards) {
            card.setSelectionMode(isSelectionMode);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Fungsi eksekusi penghapusan massal ke service API backend
    private void executeBulkDelete() {
        // 1. Kumpulkan semua ID yang dicentang oleh user
        java.util.ArrayList<Integer> selectedIds = new java.util.ArrayList<>();
        for (JournalItemCard card : renderedCards) {
            if (card.isSelectedForDelete()) {
                selectedIds.add(card.getJournalId());
            }
        }

        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan pilih minimal satu jurnal untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Konfirmasi penghapusan
        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Apakah Anda yakin ingin menghapus " + selectedIds.size() + " jurnal yang dipilih?", 
                "Konfirmasi Hapus Massal", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Gabungkan list ID menjadi format string csv (contoh: "14,15,18")
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < selectedIds.size(); i++) {
                    sb.append(selectedIds.get(i));
                    if (i < selectedIds.size() - 1) {
                        sb.append(",");
                    }
                }

                // 3. Panggil HTTP request via Service Layer
                // Pastikan method di JournalService kamu bisa menerima string params atau panggil langsung HTTP di sini
                deleteBulkViaService(sb.toString());

                JOptionPane.showMessageDialog(this, selectedIds.size() + " Jurnal berhasil dihapus.");

                // 4. Kembalikan mode UI ke normal & muat ulang data terbaru
                isSelectionMode = false;
                loadData(); 

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus jurnal secara massal: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Helper koneksi API untuk bulk delete (Bisa dipindah ke JournalService jika ingin lebih rapi)
    private void deleteBulkViaService(String csvIds) throws Exception {
        // Bersihkan spasi yang tidak sengaja terbentuk (misal "1, 2" menjadi "1,2")
        String cleanedIds = csvIds.trim().replace(" ", "");

        java.net.URL url = new java.net.URL(service.BASE_URL + "?ids=" + cleanedIds); 
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        conn.setRequestProperty("Authorization", "Bearer " + senandika.ServiceLayer.Session.TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");

        int code = conn.getResponseCode();
        if (code != 200) {
            // Membaca pesan eror detail dari server jika ada untuk mempermudah debug
            java.io.InputStream es = conn.getErrorStream();
            String errorResponse = "";
            if (es != null) {
                java.util.Scanner s = new java.util.Scanner(es).useDelimiter("\\A");
                errorResponse = s.hasNext() ? s.next() : "";
            }
            throw new RuntimeException("Server Error 500: " + errorResponse);
        }
    }

    // =========================================================================
    // LOGIKA HISTORIS & TIMEZONE SECARA AKURAT (MENCEGAH ERROR RESET / DELETE)
    // =========================================================================

    private int hitungTotalStreakAkumulatif(List<JournalData> journals, String tanggalMulaiISO) {
        if (journals == null || journals.isEmpty()) return 0;

        Set<String> tanggalMenulisSet = new HashSet<>();

        for (JournalData j : journals) {
            String tgl = (j.getCreatedAt() != null && !j.getCreatedAt().isEmpty()) ? j.getCreatedAt() : j.getTanggal();
            if (tgl == null || tgl.isEmpty()) continue;

            try {
                ZonedDateTime zdt = ZonedDateTime.parse(tgl);
                LocalDate localDate = zdt.toLocalDate(); 
                tanggalMenulisSet.add(localDate.toString());
            } catch (Exception e) {
                String cleanStr = tgl.replace("T", " ").split("\\.")[0].split(" ")[0];
                tanggalMenulisSet.add(cleanStr);
            }
        }

        // Tentukan pointer mulai hitung mundur dari hari MINGGU di pekan yang dipilih
        LocalDate seninTerpilih = LocalDate.parse(tanggalMulaiISO.split("T")[0]);
        LocalDate mingguTerpilih = seninTerpilih.plusDays(6);
        LocalDate hariIni = LocalDate.now();

        // Jika minggu terpilih adalah minggu berjalan (sekarang), pointer mulai dari HARI INI
        LocalDate pointerHari = mingguTerpilih;
        if (!mingguTerpilih.isBefore(hariIni) && !seninTerpilih.isAfter(hariIni)) {
            pointerHari = hariIni;
        }

        int totalStreak = 0;

        // Proteksi: Jika di tanggal evaluasi awal kosong, toleransi cek 1 hari sebelumnya
        if (!tanggalMenulisSet.contains(pointerHari.toString())) {
            LocalDate kemarin = pointerHari.minusDays(1);
            if (tanggalMenulisSet.contains(kemarin.toString())) {
                pointerHari = kemarin;
            } else {
                return 0; // Tidak ada streak aktif yang menyentuh rentang minggu ini
            }
        }

        // Loop mundur tanpa batas selama rantai penulisan jurnal tidak putus
        while (tanggalMenulisSet.contains(pointerHari.toString())) {
            totalStreak++;
            pointerHari = pointerHari.minusDays(1);
        }

        return totalStreak;
    }

    // FIX: Menggunakan prioritas tanggal yang sama agar sinkronisasi centang grid ungu akurat
    private boolean[] hitungHariAktifBerdasarkanMinggu(List<JournalData> journals, String tanggalMulaiISO) {
        boolean[] activeDays = new boolean[7];
        if (journals == null || journals.isEmpty()) return activeDays;

        try {
            LocalDate batasAwalMinggu = LocalDate.parse(tanggalMulaiISO.split("T")[0]);

            for (JournalData j : journals) {
                String tgl = (j.getCreatedAt() != null && !j.getCreatedAt().isEmpty()) ? j.getCreatedAt() : j.getTanggal();
                if (tgl == null || tgl.isEmpty()) continue;

                LocalDate tanggalJurnal;
                try {
                    ZonedDateTime zdt = ZonedDateTime.parse(tgl);
                    tanggalJurnal = zdt.toLocalDate();
                } catch (Exception e) {
                    String cleanStr = tgl.replace("T", " ").split("\\.")[0].split(" ")[0];
                    tanggalJurnal = LocalDate.parse(cleanStr);
                }

                long selisihHari = ChronoUnit.DAYS.between(batasAwalMinggu, tanggalJurnal);

                if (selisihHari >= 0 && selisihHari < 7) {
                    activeDays[(int) selisihHari] = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeDays;
    }

    private void setupCardActions(JournalItemCard card) {
        card.getBtnDetail().addActionListener(e -> {
            try {
                // 1. Ambil ID jurnal dari card yang sedang diklik oleh user
                int idTerpilih = card.getJournalId();

                // 2. Buka frame DetailJournal dengan melempar parameter ID-nya
                DetailJournal frameDetail = new DetailJournal(idTerpilih);
                frameDetail.setVisible(true);
                // AKSI CLOSE: Menghancurkan/menutup frame utama lamamu
            if (parentFrame != null) {
                parentFrame.dispose();
            }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal membuka rincian: " + ex.getMessage());
            }
        });

        // ================== PERBAIKAN DI SINI ==================
        card.getBtnEdit().addActionListener(e -> {
            // AMBIL ID langsung dari komponen card yang bersangkutan
            int idYgDipilih = card.getJournalId(); 

            System.out.println("ID yang dikirim ke EditJournal: " + idYgDipilih); 

            EditJournal frameEdit = new EditJournal(idYgDipilih, this);
            frameEdit.setVisible(true);
            // AKSI CLOSE: Menghancurkan/menutup frame utama lamamu
            if (parentFrame != null) {
                parentFrame.dispose();
            }
        });
        // =======================================================

        // Hubungkan ke tombol Hapus bawaan kartu jurnal tunggal
        card.getBtnDelete().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus jurnal ini?",
                "Konfirmasi Hapus Jurnal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    service.deleteJournal(card.getJournalId());
                    JOptionPane.showMessageDialog(this, "Jurnal berhasil dihapus.");
                    loadData(); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus jurnal: " + ex.getMessage(), "Eror", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }
}