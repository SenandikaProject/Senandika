/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 *
 * @author lenovo
 */
import java.awt.Color;

import javax.swing.JLabel;

import senandika.FontManager;

public class MoodCard extends AppCard {

    public MoodCard() {

        setSize(340,220);

        JLabel title =
                new JLabel("Bagaimana hari ini?");

        title.setFont(
                FontManager.getPoppins(18f)
        );

        add(
                title,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        80,
                        20,
                        200,
                        30
                )
        );

        RoundedButton btn =
                new RoundedButton();

        btn.setText("Kirim");

        btn.setBackground(
                new Color(224,201,255)
        );

        btn.setForeground(Color.WHITE);

        add(
                btn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(
                        20,
                        150,
                        300,
                        45
                )
        );
    }
}
