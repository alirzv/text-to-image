import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class AvailableFonts {

    public Font[] getAllFonts(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();

        for (Font font : fonts) {
            System.out.print(font.getFontName() + " : ");
            System.out.print(font.getFamily() + ":");
            System.out.println(font.getName());
        }
        return fonts;
    }

    public String[] getAllFontNames(){
        Font[] fonts = getAllFonts();
        String[] fontNames = new String[fonts.length];
        for (int i=0; i<fonts.length; i++) {
            fontNames[i] = fonts[i].getFontName();
        }
        return fontNames;
    }
}

