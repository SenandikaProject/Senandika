package Components.Home_Component;

import Components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import senandika.FontManager;

public class EmptyStatePanel extends RoundedPanel {

    public EmptyStatePanel(
            String icon,
            String title,
            String subtitle
    ) {

        initComponents(
                icon,
                title,
                subtitle
        );
    }

    private void initComponents(
            String icon,
            String title,
            String subtitle
    ) {

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(235, 235, 235),
                    1,
                    true
                ),
                BorderFactory.createEmptyBorder(
                    28,
                    20,
                    28,
                    20
                )
            )
        );

        setPreferredSize(
            new Dimension(
                320,
                150
            )
        );

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        centerPanel.setLayout(
            new BoxLayout(
                centerPanel,
                BoxLayout.Y_AXIS
            )
        );

        JLabel iconLabel = new JLabel(
            icon,
            SwingConstants.CENTER
        );

        iconLabel.setFont(
            FontManager.getPoppins(32f)
        );

        iconLabel.setAlignmentX(
            CENTER_ALIGNMENT
        );

        JLabel titleLabel = new JLabel(
            title,
            SwingConstants.CENTER
        );

        titleLabel.setFont(
            FontManager.getPoppins(14f)
                .deriveFont(Font.BOLD)
        );

        titleLabel.setForeground(
            new Color(30, 41, 59)
        );

        titleLabel.setAlignmentX(
            CENTER_ALIGNMENT
        );

        titleLabel.setBorder(
            BorderFactory.createEmptyBorder(
                10,
                0,
                4,
                0
            )
        );

        JLabel subtitleLabel = new JLabel(
            subtitle,
            SwingConstants.CENTER
        );

        subtitleLabel.setFont(
            FontManager.getPoppins(12f)
        );

        subtitleLabel.setForeground(
            new Color(100, 116, 139)
        );

        subtitleLabel.setAlignmentX(
            CENTER_ALIGNMENT
        );

        centerPanel.add(iconLabel);
        centerPanel.add(titleLabel);
        centerPanel.add(subtitleLabel);

        add(
            centerPanel,
            BorderLayout.CENTER
        );
    }
}