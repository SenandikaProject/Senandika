package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;

public class JournalFilterBar extends JPanel {

    private JComboBox<String> cbFilter;

    public JournalFilterBar(){

        setOpaque(false);

        cbFilter =
            new JComboBox<>(
                new String[]{
                    "Semua",
                    "Hari Ini",
                    "Minggu Ini",
                    "Bulan Ini"
                }
            );

        add(cbFilter);
    }

    public String getSelectedFilter(){
        return cbFilter
            .getSelectedItem()
            .toString();
    }
}
