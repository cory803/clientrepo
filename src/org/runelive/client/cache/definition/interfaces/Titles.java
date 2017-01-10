package org.runelive.client.cache.definition.interfaces;

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
    LUMBERJACK("Chop 2500 Magic Logs", 2500),
    DESTROYER("Deal 500K Melee Damage", 500000),
    PROPKER("Hit 700 with Special Attack", 1),
    ALCHEMIST("High Alch 1000 Items", 1000),
    WIZARD("Deal 500K Magic Damage", 500000),
    BLACKSMITH("Smelt 1000 Rune Bars", 1000),
    THE_REAL("Create A Clan Chat", 1),
    TZTOK("Defeat Jad", 1),
    PREMIUM("Become a Premium Donator", 1),
    EXTREME("Become a Extreme Donator", 1),
    LEGENDARY("Become a Legendary Donator", 1),
    UBER("Become a Uber Donator", 1),
    PLATINUM("Become a Platinum Donator", 1),
    KNIGHT("Become a Knight (Game Mode)", 1),
    IRONMAN("Become a Ironman (Game Mode)", 1),
    REALISM("Become a Realism (Game Mode)", 1);

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
        if(this == Titles.TZTOK) {
            return "TzTok";
        }
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
