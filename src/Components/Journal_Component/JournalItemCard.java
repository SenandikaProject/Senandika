package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class JournalItemCard extends JPanel {

    private JLabel lblTitle;
    private JLabel lblDate;

    private RoundedButton btnDetail;
    private RoundedButton btnEdit;
    private RoundedButton btnDelete;

    private JCheckBox chkSelect;
    private int journalId;

    public JournalItemCard() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        
        // Memadukan border luar (jarak antar card), border garis, dan padding dalam agar estetik
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(6, 0, 6, 0), // Mengatur jarak antar card (top & bottom)
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 190, 255), 1, true), // Line border ungu soft
                BorderFactory.createEmptyBorder(12, 16, 12, 16) // Padding internal (isi card tidak mepet ke garis)
            )
        ));
        card.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("Judul Jurnal");
        lblTitle.setFont(FontManager.getPoppins(18f));
        lblTitle.setForeground(new Color(137, 126, 255));

        lblDate = new JLabel();
        lblDate.setFont(FontManager.getPoppins(12f));
        lblDate.setForeground(Color.GRAY); // Memberikan warna abu-abu agar hirarki teks lebih kontras

        top.add(lblTitle);
        top.add(Box.createVerticalStrut(6));
        top.add(lblDate);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        bottom.setOpaque(false);

        btnDetail = new RoundedButton();
        btnDetail.setText("Detail");
        btnDetail.setCornerRadius(8);
        
        btnEdit = new RoundedButton();
        btnEdit.setText("Edit");
        btnEdit.setBackground(Color.BLUE);
        btnEdit.setCornerRadius(8);
        
        btnDelete = new RoundedButton();
        btnDelete.setText("Hapus");
        btnDelete.setCornerRadius(8);
        btnDelete.setBackground(new Color(255, 120, 120));

        bottom.add(btnDetail);
        bottom.add(btnEdit);
        bottom.add(btnDelete);

        card.add(top, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
        
        // Checkbox Select
        chkSelect = new JCheckBox();
        chkSelect.setOpaque(false);
        chkSelect.setVisible(false);
        chkSelect.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Styling Checkbox agar tampak lebih modern (Tema Ungu Soft Senandika)
        chkSelect.setFont(new Font("Poppins", Font.PLAIN, 12));
        chkSelect.setForeground(new Color(139, 92, 246)); // Violet-500

        // Berikan margin ekstra di sebelah kanan checkbox agar tidak menempel langsung dengan teks Judul Jurnal
        chkSelect.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 12));

        // Masukkan chkSelect ke posisi paling kiri (WEST) kartu Anda jika menggunakan BorderLayout
        add(chkSelect, BorderLayout.WEST);
    }

    public void setData(int id, String judul, String createdAt) {
        this.journalId = id;
        this.lblTitle.setText(judul);

        // Langsung konversi format mentah Supabase menggunakan fungsi formatDate bawaan halaman mood
        String tanggalDiformat = formatDate(createdAt);
        this.lblDate.setText(tanggalDiformat);
    }
    
    public void setSelectionMode(boolean visible) {
        chkSelect.setVisible(visible);
        if (!visible) {
            chkSelect.setSelected(false); // Reset centang jika mode selection dimatikan
        }
        revalidate();
        repaint();
    }

    public boolean isSelectedForDelete() {
        return chkSelect.isVisible() && chkSelect.isSelected();
    }
    
    private static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) {
            return "";
        }

        try {
            String clean = isoDate.trim();

            // 1. Ambil tanggal dasar (YYYY-MM-DD)
            java.time.LocalDate date = java.time.LocalDate.parse(clean.substring(0, 10));

            // 2. Deteksi pemisah antara Tanggal dan Jam ('T' atau spasi)
            int indexT = clean.contains("T") ? clean.indexOf("T") : clean.indexOf(" ");
            String jamStr = " 23:00"; // Fallback default jika jam tidak ditemukan

            if (indexT != -1 && clean.length() > indexT + 5) {
                // Mengambil bagian jam saja setelah T/spasi (misal: "HH:mm:ss...")
                String waktuMentah = clean.substring(indexT + 1);

                // Split berdasarkan tanda ":" agar mendapatkan Jam dan Menit secara presisi
                String[] parts = waktuMentah.split(":");
                if (parts.length >= 2) {
                    String jamMentah = parts[0];   // Pasti memuat 2 digit jam
                    String menitMentah = parts[1]; // Pasti memuat 2 digit menit

                    int jamAngka = Integer.parseInt(jamMentah);

                    // 3. Terapkan Logika +16 Jam yang sudah sukses di halaman Mood
                    int jamLokal = (jamAngka + 16) % 24; 

                    // Jika penambahan jam melewati tengah malam, naikkan tanggal 1 hari ke depan
                    if (jamAngka + 16 >= 24) {
                        date = date.plusDays(1);
                    }

                    jamStr = String.format(" %02d:%s", jamLokal, menitMentah);
                }
            }

            // 4. Format nama bulan ke Indonesia
            String[] bulan = {
                "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
            };

            return date.getDayOfMonth() + " " + bulan[date.getMonthValue() - 1] + " " + date.getYear() + jamStr;

        } catch (Exception e) {
            // Jika ada eror parsing, kita cetak ke log untuk mempermudah debugging
            System.out.println("Eror parsing tanggal: " + e.getMessage());
            return isoDate; 
        }
    }

    public RoundedButton getBtnDetail(){ return btnDetail; }
    public RoundedButton getBtnEdit(){ return btnEdit; }
    public RoundedButton getBtnDelete(){ return btnDelete; }
    public int getJournalId(){ return journalId; }
}