/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senandika;
import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author lenovo
 */
public class FontManager {

    private static Font poppinsRegular;

    static {
        try {
            InputStream is = FontManager.class.getResourceAsStream(
                "/asset/font/Poppins-Regular.ttf");

            poppinsRegular = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font getPoppins(float size) {
        return poppinsRegular.deriveFont(size);
    }
}
