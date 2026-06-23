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
        card.setBorder(
            BorderFactory.createLineBorder(
                new Color(215,190,255)
            )
        );
        card.setBorder(BorderFactory.createCompoundBorder(
           BorderFactory.createEmptyBorder(10, 0, 10, 0), // Mengatur jarak antar card (top & bottom)
            this.getBorder() // Mempertahankan border asli (jika ada rounded border/line border)
        ));
        card.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("Judul Jurnal");

        lblTitle.setFont(
            FontManager.getPoppins(18f)
        );

        lblTitle.setForeground(
            new Color(137,126,255)
        );

        lblDate = new JLabel();

        lblDate.setFont(
            FontManager.getPoppins(12f)
        );

        top.add(lblTitle);
        top.add(Box.createVerticalStrut(8));
        top.add(lblDate);

        JPanel bottom = new JPanel(
            new FlowLayout(
                FlowLayout.RIGHT
            )
        );

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

        btnDelete.setBackground(
            new Color(255,120,120)
        );

        bottom.add(btnDetail);
        bottom.add(btnEdit);
        bottom.add(btnDelete);

        card.add(top, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);
    }

    public void setData(int id, String judul, String tanggalMentah) {
        this.journalId = id;
        this.lblTitle.setText(judul);

        String tanggalBersih = tanggalMentah;
        if (tanggalMentah != null && tanggalMentah.contains("T")) {
            // Mengambil bagian sebelum huruf 'T' (Contoh: "2026-06-20")
            String ymd = tanggalMentah.split("T")[0]; 

            // Opsional: Jika ingin mengubah susunan dari YYYY-MM-DD menjadi DD-MM-YYYY
            String[] parts = ymd.split("-");
            if (parts.length == 3) {
                tanggalBersih = parts[2] + "-" + parts[1] + "-" + parts[0]; // Hasil: 20-06-2026
            } else {
                tanggalBersih = ymd;
            }
        }

        this.lblDate.setText(tanggalBersih);
        
    }

    public RoundedButton getBtnDetail(){
        return btnDetail;
    }

    public RoundedButton getBtnEdit(){
        return btnEdit;
    }

    public RoundedButton getBtnDelete(){
        return btnDelete;
    }

    public int getJournalId(){
        return journalId;
    }
}