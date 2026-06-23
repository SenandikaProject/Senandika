package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;

public class JournalFilterBar extends JPanel {

    private JComboBox<String> cbFilter;

    public JournalFilterBar(){
        setOpaque(false);
        setLayout(new BorderLayout()); // Gunakan border layout agar menyesuaikan wrapper

        cbFilter = new JComboBox<>(new String[]{
            "Semua", "Hari Ini", "Minggu Ini", "Bulan Ini"
        });
        
        cbFilter.setPreferredSize(new Dimension(110, 35)); // Tinggi disamakan dengan SearchBar
        cbFilter.setBackground(Color.WHITE);
        
        add(cbFilter, BorderLayout.CENTER);
    }

    public String getSelectedFilter(){
        return cbFilter.getSelectedItem().toString();
    }

    // Listener ketika filter combobox diubah oleh user
    public void addFilterListener(java.awt.event.ActionListener listener) {
        cbFilter.addActionListener(listener);
    }
}