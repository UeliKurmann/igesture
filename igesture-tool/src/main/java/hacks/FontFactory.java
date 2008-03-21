package hacks;

import java.awt.Font;



public class FontFactory {

    private static final String HELVETICA = "Helvetica";
    private static final String ARIAL = "Arial";

    public static Font getHelveticaPlain(int size) {
        return new Font(HELVETICA, Font.PLAIN, size);
    }

    public static Font getArialPlain(int size) {
        return new Font(ARIAL, Font.PLAIN, size);
    }

    public static Font getArialBold(int size) {
        return new Font(ARIAL, Font.BOLD, size);
    }

    }
