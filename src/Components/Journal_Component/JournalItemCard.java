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

        lblDate = new JLabel("21 Juni 2026");

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

        btnEdit = new RoundedButton();
        btnEdit.setText("Edit");

        btnDelete = new RoundedButton();
        btnDelete.setText("Hapus");

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

    public void setData(
        int id,
        String title,
        String date
    ){

        this.journalId = id;

        lblTitle.setText(title);
        lblDate.setText(date);
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