package Components.Mood_Component;

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


public class MoodStatisticsCard extends RoundedPanel {

    private JLabel valueLabel;

    public MoodStatisticsCard(
            String icon,
            String label
    ) {

        initComponents(
                icon,
                label
        );
    }

    private void initComponents(
            String icon,
            String label
    ) {

        setLayout(
            new BorderLayout(
                0,
                6
            )
        );

        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(
                        235,
                        235,
                        235
                    ),
                    1,
                    true
                ),
                BorderFactory.createEmptyBorder(
                    16,
                    12,
                    16,
                    12
                )
            )
        );

        setPreferredSize(
            new Dimension(
                140,
                110
            )
        );

        JLabel iconLabel =
                new JLabel(
                    icon,
                    SwingConstants.CENTER
                );

        iconLabel.setFont(
            FontManager.getPoppins(
                22f
            )
        );

        valueLabel =
                new JLabel(
                    "-",
                    SwingConstants.CENTER
                );

        valueLabel.setFont(
            FontManager.getPoppins(
                20f
            ).deriveFont(
                Font.BOLD
            )
        );

        valueLabel.setForeground(
            new Color(
                30,
                41,
                59
            )
        );

        JLabel captionLabel =
                new JLabel(
                    label,
                    SwingConstants.CENTER
                );

        captionLabel.setFont(
            FontManager.getPoppins(
                11f
            )
        );

        captionLabel.setForeground(
            new Color(
                100,
                116,
                139
            )
        );

        JPanel centerPanel =
                new JPanel();

        centerPanel.setOpaque(false);

        centerPanel.setLayout(
            new BoxLayout(
                centerPanel,
                BoxLayout.Y_AXIS
            )
        );

        valueLabel.setAlignmentX(
            CENTER_ALIGNMENT
        );

        centerPanel.add(
            valueLabel
        );

        add(
            iconLabel,
            BorderLayout.NORTH
        );

        add(
            centerPanel,
            BorderLayout.CENTER
        );

        add(
            captionLabel,
            BorderLayout.SOUTH
        );
    }

    public void setValue(
            String value
    ) {

        valueLabel.setText(
            value
        );
    }
}