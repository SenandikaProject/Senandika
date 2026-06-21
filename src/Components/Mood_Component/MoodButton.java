package Components.Mood_Component;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import senandika.FontManager;

/**
 * MoodButton
 * Digunakan pada halaman Mood untuk memilih mood pengguna.
 */
public class MoodButton extends JPanel {

    public interface OnSelectListener {
        void onSelect(int level);
    }

    private final int level;
    private final String emoji;

    private boolean selected = false;
    private boolean hovered = false;

    private OnSelectListener listener;

    private JLabel emojiLabel;

    public MoodButton(
            int level,
            String emoji
    ) {

        this.level = level;
        this.emoji = emoji;

        initComponents();
        initListeners();
    }

    private void initComponents() {

        setOpaque(false);

        setLayout(
            new BorderLayout()
        );

        setCursor(
            new Cursor(
                Cursor.HAND_CURSOR
            )
        );

        setPreferredSize(
            new Dimension(
                72,
                84
            )
        );

        emojiLabel = new JLabel(
            emoji,
            SwingConstants.CENTER
        );

        emojiLabel.setFont(
            FontManager.getPoppins(30f)
        );

        add(
            emojiLabel,
            BorderLayout.CENTER
        );
    }

    private void initListeners() {

        addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseEntered(
                        MouseEvent e
                ) {

                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(
                        MouseEvent e
                ) {

                    hovered = false;
                    repaint();
                }

                @Override
                public void mouseClicked(
                        MouseEvent e
                ) {

                    if (listener != null) {

                        listener.onSelect(
                            level
                        );
                    }
                }
            }
        );
    }

    public void setOnSelect(
            OnSelectListener listener
    ) {

        this.listener = listener;
    }

    public int getLevel() {

        return level;
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(
            boolean selected
    ) {

        this.selected = selected;

        emojiLabel.setFont(
            FontManager.getPoppins(
                selected ? 36f : 30f
            )
        );

        repaint();
    }

    @Override
    protected void paintComponent(
            Graphics g
    ) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        int radius = 18;

        Color fillColor;

        if (selected) {

            fillColor =
                    new Color(
                        241,
                        236,
                        255
                    );

        } else if (hovered) {

            fillColor =
                    new Color(
                        248,
                        245,
                        255
                    );

        } else {

            fillColor =
                    new Color(
                        250,
                        250,
                        250
                    );
        }

        g2.setColor(
            fillColor
        );

        g2.fillRoundRect(
            2,
            2,
            width - 4,
            height - 4,
            radius,
            radius
        );

        if (selected) {

            g2.setColor(
                new Color(
                    137,
                    126,
                    255
                )
            );

            g2.setStroke(
                new BasicStroke(
                    2.2f
                )
            );

            g2.drawRoundRect(
                2,
                2,
                width - 5,
                height - 5,
                radius,
                radius
            );
        }

        g2.dispose();

        super.paintComponent(g);
    }
}