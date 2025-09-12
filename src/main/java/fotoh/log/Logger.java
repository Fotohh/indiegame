package fotoh.log;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Map;

public final class Logger {

    public static final char COLOR_SYMBOL = '&';
    public static final Map<String, String> keys = Map.of(
            "f", "white",
            "1", "blue",
            "2", "green",
            "3", "cyan",
            "4", "red",
            "5", "purple",
            "e", "yellow",
            "d", "magenta",
            "l", "bold",
            "r", "reset"
    );

    static {
        AnsiConsole.systemInstall();
        Ansi.setEnabled(true);
    }

    public static void main(String[] args) {
        print("&2 hey w&3ha&3t oka&ey &1well &d ok&2ay"); // TODO doesnt remove the &2 at the end??
    }

    public static void clearScreen() {
        Ansi.ansi().eraseScreen();
        Ansi.ansi().eraseScreen(Ansi.Erase.ALL);
        Ansi.ansi().eraseLine();
        Ansi.ansi().eraseLine(Ansi.Erase.ALL);
    }

    public synchronized static void print(String message) {

        String msg = parseString(message);
        Ansi ansi = Ansi.ansi().render(msg);
        String string = ansi.toString();

        while (string.contains("@|") && string.contains("|@")) {
            ansi = Ansi.ansi().render(string);
            string = ansi.toString();
        }

        System.out.println(ansi);
    }

    private synchronized static String parseString(String s) { //TODO bold and reset cause algorithm to bug
        final StringBuilder builder = new StringBuilder(s);
        int size = builder.length();
        boolean searching = false;
        boolean bold = false;
        int boldCount = 0;
        String recentSymbol = null;

        for (int i = 0; i < size; i++) {

            char character = builder.charAt(i);

            if (character == COLOR_SYMBOL) {

                if (i + 1 >= size) break;

                String currentChar = String.valueOf(builder.charAt(i + 1));
                String colorSymbol = keys.getOrDefault(currentChar, null);
                if (colorSymbol == null) continue;

                if (bold && colorSymbol.equals("reset")) {
                    bold = false;
                    boldCount--;
                    if (!recentSymbol.equals("bold")) builder.insert(i, "|@");
                    builder.insert(i, "|@");
                    searching = false;
                    continue;
                }
                if (searching) {
                    if (!bold || !recentSymbol.equals("bold")) {
                        builder.insert(i, "|@");
                        searching = false;
                        continue;
                    }
                }
                if (!searching) {
                    if (i + 2 >= size) break;
                    searching = true;
                    builder.setCharAt(i, '@');
                    builder.setCharAt(i + 1, '|');
                    if (String.valueOf(builder.charAt(i + 2)).equals(" ")) {
                        builder.insert(i + 2, colorSymbol);
                    } else {
                        builder.insert(i + 2, colorSymbol + " ");
                    }
                    size = builder.length();
                    if (currentChar.equals("l")) bold = true;
                    recentSymbol = colorSymbol;
                    if (colorSymbol.equals("bold")) {
                        boldCount++;
                    }
                }
                continue;
            }

            if (i == size - 1 && searching) {
                builder.insert(builder.length(), "|@");
            }
            if (i == size - 1) {
                if (boldCount > 0) {
                    char c1 = builder.charAt(builder.length() - 1);
                    char c2 = builder.charAt(builder.length() - 2);
                    if (c1 == '@' && c2 == '|') {
                        boldCount--;
                    }
                    while (boldCount > 0) {
                        builder.insert(builder.length(), "|@");
                        boldCount--;
                    }
                }
            }

        }

        return builder.toString();
    }

    public static void info(String message) {
        print("&4[&eINFO&4]&r  " + message);
    }

    public static void severe(String message) {
        print("&e[&l&4SEVERE&r&e]&r  " + message);
    }

    public static void warn(String message) {
        print("&e[&4WARN&e]&r  " + message);
    }

    public static void debug(String message) {
        print("&e[&1DEBUG&e]&r  " + message);
    }

    public static void lethal(String message) {
        print("&4[&lLETHAL&r&4]&r  " + message);
    }

}