package javacalendar.util;

import java.awt.Color;

public enum Colors {
    PINK("#f291eb"), RED("#fa3e3d"), ORANGE("#f58547"), YELLOW("#fde668"),
    LIGHT_GREEN("#b8fd68"), GREEN("#41a630"), LIGHT_BLUE("#92fff6"), BLUE("#41b3ff"),
    DARK_BLUE("#2e63cd"), LAVENDER("#c493d3"), PURPLE("#8c48ff"), MAGENTA("#ff34ef"),
    WHITE("#ffffff"), LIGHT_GRAY("#cdcdcd"), GRAY("#818081"), BLACK("#404040");

    Colors(String hexColor) {
        this.hexColor = hexColor;
    }

    private String hexColor;

    public String getHexColor() {
        return hexColor;
    }

    public Color getProperTextColor() {
        switch (this) {
            case PINK:
            case RED:
            case ORANGE:
            case YELLOW:
            case LIGHT_GREEN:
            case LIGHT_BLUE:
            case BLUE:
            case LAVENDER:
            case WHITE:
            case LIGHT_GRAY:
                return Color.black;
            case MAGENTA:
            case GREEN:
            case DARK_BLUE:
            case PURPLE:
            case GRAY:
            case BLACK:
                return Color.white;
            default:
        }
        return Color.darkGray;
    }

    public static Colors getColorFromHex(String color) {
        switch (color) {
            case "#f291eb":
                return PINK;
            case "#fa3e3d":
                return RED;
            case "#f58547":
                return ORANGE;
            case "#fde668":
                return YELLOW;
            case "#b8fd68":
                return LIGHT_GREEN;
            case "#41a630":
                return GREEN;
            case "#92fff6":
                return LIGHT_BLUE;
            case "#41b3ff":
                return BLUE;
            case "#2e63cd":
                return DARK_BLUE;
            case "#c493d3":
                return LAVENDER;
            case "#8c48ff":
                return PURPLE;
            case "#ff34ef":
                return MAGENTA;
            case "#ffffff":
                return WHITE;
            case "#cdcdcd":
                return LIGHT_GRAY;
            case "#818081":
                return GRAY;
            case "#404040":
                return BLACK;
            default:
        }
        return WHITE;
    }

    public static Colors getColorFromName(String color) {
        switch (color.toUpperCase()) {
            case "PINK":
                return PINK;
            case "RED":
                return RED;
            case "ORANGE":
                return ORANGE;
            case "YELLOW":
                return YELLOW;
            case "LIGHT GREEN":
                return LIGHT_GREEN;
            case "GREEN":
                return GREEN;
            case "LIGHT BLUE":
                return LIGHT_BLUE;
            case "BLUE":
                return BLUE;
            case "DARK BLUE":
                return DARK_BLUE;
            case "LAVENDER":
                return LAVENDER;
            case "PURPLE":
                return PURPLE;
            case "MAGENTA":
                return MAGENTA;
            case "WHITE":
                return WHITE;
            case "LIGHT GRAY":
                return LIGHT_GRAY;
            case "GRAY":
                return GRAY;
            case "BLACK":
                return BLACK;
            default:
        }
        return WHITE;
    }

    public static String getPrettyNameFromColor(Colors colors) {
        switch (colors) {
            case PINK:
                return "Pink";
            case RED:
                return "Red";
            case ORANGE:
                return "Orange";
            case YELLOW:
                return "Yellow";
            case LIGHT_GREEN:
                return "Light green";
            case LIGHT_BLUE:
                return "Light blue";
            case BLUE:
                return "Blue";
            case LAVENDER:
                return "Lavender";
            case WHITE:
                return "White";
            case LIGHT_GRAY:
                return "Light gray";
            case MAGENTA:
                return "Magenta";
            case GREEN:
                return "Green";
            case DARK_BLUE:
                return "Dark blue";
            case PURPLE:
                return "Purple";
            case GRAY:
                return "Gray";
            case BLACK:
                return "Black";
            default:
        }
        return "idk";
    }
}
