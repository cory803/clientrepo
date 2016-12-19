package org.chaos.client.cache.definition.interfaces;

/**
 * Stores all the data for titles
 * @Author Jonny
 */
public enum Titles {

    PKER("Defeat 15 Players", 15),
    GODLIKE("Bury 500 Frost Dragon Bones", 500),
    CHEF("Cook 500 Manta Ray", 500),
    TERMINATOR("Defeat 500 Boss Monsters", 500),
    FISHERMAN("Fish 2000 Rocktails", 1500),
    LUMBERJACK("Chop 2500 Magic Logs", 2500);

    Titles(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    private String description;
    private int amount;

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Returns the formatted name of the slayer task.
     * @return
     */
    public String getName() {
        return formatText(this.toString().toLowerCase().replace("_", " "));
    }

    public static String formatText(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
                            s.substring(i + 2));
                }
            }
        }
        return s.replace("_", " ");
    }
}
