package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JournalSearchBar extends JPanel {

    private JTextField txtSearch;
    private JButton btnSearch;

    public JournalSearchBar() {
        setOpaque(false);
        setLayout(new BorderLayout(5, 0)); // Beri sedikit gap horizontal 5px

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(160, 35)); 
        txtSearch.setText("Cari judul jurnal...");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(215, 190, 255), 2, true),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));

        // Tambah Tombol Search Icon/Teks
        btnSearch = new JButton("🔍");
        btnSearch.setPreferredSize(new Dimension(45, 35));
        btnSearch.setBackground(new Color(137, 126, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createLineBorder(new Color(137, 126, 255), 1, true));

        add(txtSearch, BorderLayout.CENTER);
        add(btnSearch, BorderLayout.EAST);
    }

    public String getSearchQuery() {
        return txtSearch.getText().trim();
    }

    // Listener gabungan: picu aksi baik ketika mengetik ATAU menekan tombol search
    public void addSearchListener(Runnable action) {
        // Ketika tombol diklik
        btnSearch.addActionListener(e -> action.run());
        
        // Ketika menekan enter di textfield
        txtSearch.addActionListener(e -> action.run());

        // Ketika user mengetik (Realtime)
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { action.run(); }
            @Override
            public void removeUpdate(DocumentEvent e) { action.run(); }
            @Override
            public void changedUpdate(DocumentEvent e) { action.run(); }
        });
    }
}