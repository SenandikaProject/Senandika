package Components.Journal_Component;

import Components.RoundedButton;
import java.awt.*;
import javax.swing.*;
import senandika.FontManager;
import senandika.Model.JournalData;

public class EditJournalCard extends JPanel {

    private JTextField txtJudul;
    private JTextArea txtIsi;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    public EditJournalCard(JFrame parentFrame) {

        setLayout(new BorderLayout(15,15));
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220,220,220)
                ),
                BorderFactory.createEmptyBorder(
                    20,
                    20,
                    20,
                    20
                )
            )
        );

        initComponents();
    }

    private void initComponents() {

        // HEADER

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitle =
                new JLabel(
                        "Edit Jurnal"
                );

        lblTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblTitle.setFont(
                FontManager.getPoppins(20f)
        );

        JLabel lblSub =
                new JLabel(
                        "Perbarui isi jurnal Anda"
                );

        lblSub.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        lblSub.setFont(
                FontManager.getPoppins(12f)
        );

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(lblSub);

        add(
                header,
                BorderLayout.NORTH
        );

        // FORM

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Judul

        gbc.gridy = 0;

        form.add(
                new JLabel(
                        "Judul"
                ),
                gbc
        );

        txtJudul =
                new JTextField();

        gbc.gridy = 1;

        form.add(
                txtJudul,
                gbc
        );

        // Isi

        gbc.gridy = 2;

        form.add(
                new JLabel(
                        "Isi Jurnal"
                ),
                gbc
        );

        txtIsi =
                new JTextArea(
                        10,
                        20
                );

        txtIsi.setLineWrap(true);

        txtIsi.setWrapStyleWord(true);

        JScrollPane scrollIsi =
                new JScrollPane(
                        txtIsi
                );

        gbc.gridy = 3;

        form.add(
                scrollIsi,
                gbc
        );

        add(
                form,
                BorderLayout.CENTER
        );

        // BUTTON

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottom.setOpaque(false);

        btnCancel =
                new RoundedButton();

        btnCancel.setText(
                "Batal"
        );

        btnCancel.setCornerRadius(8);

        btnSave =
                new RoundedButton();

        btnSave.setText(
                "Simpan"
        );

        btnSave.setCornerRadius(8);

        btnSave.setBackground(
                new Color(
                        137,
                        126,
                        255
                )
        );

        btnSave.setForeground(
                Color.WHITE
        );

        bottom.add(btnCancel);
        bottom.add(btnSave);

        add(
                bottom,
                BorderLayout.SOUTH
        );
    }

    // ==========================
    // LOAD DATA DARI DATABASE
    // ==========================

    public void setJournalData(
            JournalData data
    ) {

        txtJudul.setText(
                data.getJudul()
        );

        txtIsi.setText(
                data.getIsi()
        );
    }

    // ==========================
    // GETTER
    // ==========================

    public JTextField getTxtJudul() {
        return txtJudul;
    }

    public JTextArea getTxtIsi() {
        return txtIsi;
    }

    public RoundedButton getBtnSave() {
        return btnSave;
    }

    public RoundedButton getBtnCancel() {
        return btnCancel;
    }
}