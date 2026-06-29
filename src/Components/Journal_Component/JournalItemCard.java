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
    }

    public void setData(int id, String judul, String createdAt) {
        this.journalId = id;
        this.lblTitle.setText(judul);

        // Langsung konversi format mentah Supabase menggunakan fungsi formatDate bawaan halaman mood
        String tanggalDiformat = formatDate(createdAt);
        this.lblDate.setText(tanggalDiformat);
    }

    private static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) {
            return "";
        }

        try {
            String clean = isoDate.trim();

            // Mengambil yyyy-MM-dd
            java.time.LocalDate date = java.time.LocalDate.parse(clean.substring(0, 10));

            int indexT = clean.contains("T") ? clean.indexOf("T") : clean.indexOf(" ");
            String jamStr = " 00:00"; // Fallback default jika data waktu kosong

            if (indexT != -1 && clean.length() >= indexT + 6) {
                String jamMentah = clean.substring(indexT + 1, indexT + 3); 
                String menitMentah = clean.substring(indexT + 4, indexT + 6); 

                int jamAngka = Integer.parseInt(jamMentah);

                // Perbaikan: Tambahkan 8 Jam untuk konversi UTC ke WITA
                int jamLokal = (jamAngka + 8) % 24; 

                // Jika penambahan jam melewati pukul 23:59, tanggal bertambah 1 hari
                if (jamAngka + 8 >= 24) {
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
            return isoDate; // Fallback jika format string tidak sesuai standar
        }
    }

    public RoundedButton getBtnDetail(){ return btnDetail; }
    public RoundedButton getBtnEdit(){ return btnEdit; }
    public RoundedButton getBtnDelete(){ return btnDelete; }
    public int getJournalId(){ return journalId; }
}