import java.awt.Color;

public class ColorTest {
    public static void main(String[] args) {
        Color color = Color.decode("#f291eb");
        System.out.println(color.getRGB());
        String RGBToHex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        System.out.println(RGBToHex);
    }
}
