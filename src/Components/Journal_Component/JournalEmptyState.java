
package Components.Journal_Component;

import java.awt.*;
import javax.swing.*;
import senandika.FontManager;

public class JournalEmptyState extends JPanel {

    public JournalEmptyState(){

        setOpaque(false);

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        JLabel image =
            new JLabel();

        image.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        JLabel text =
            new JLabel(
               "<html><center>Kamu belum membuat<br>jurnal apapun...</center></html>"
            );

        text.setFont(
            FontManager.getPoppins(24f)
        );

        text.setForeground(
            new Color(
                137,
                126,
                255
            )
        );

        text.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        add(Box.createVerticalGlue());
        add(image);
        add(Box.createVerticalStrut(20));
        add(text);
        add(Box.createVerticalGlue());
    }
}
