package org.runelive;

/**
 * The client's features can easily be toggled/changed here.
 * 
 * @author Chaos Team
 */
public class Configuration {

	/** CONNECTION **/
	public final static boolean LOCAL = true;
	public static String SERVER_HOST = LOCAL ? "127.0.0.1" : "74.63.248.118"; // 158.69.125.71
	public final static int SERVER_PORT = 59018;

	public static boolean MAC = false;

	public final static String[][] WORLDS = {
			{"Economy", LOCAL ? "127.0.0.1" : "74.63.248.118"}, //World 1
			{"Beta", LOCAL ? "127.0.0.1" : "gameserver.rune.live"} //World 2
	};

	/** FILE SERVER **/
	public final static boolean FILE_SERVER_ENABLED = true;
	public final static boolean DEVELOPER_FILE_SERVER = false;
	public final static boolean LOCAL_JAGCACH = false;
	public final static String JAGCACHED_HOST = LOCAL_JAGCACH ? "127.0.0.1" : "74.63.248.118"; // 158.69.125.71
	public final static int JAGGRAB_PORT = DEVELOPER_FILE_SERVER ? 43596 : 43595;
	public final static int ONDEMAND_PORT = DEVELOPER_FILE_SERVER ? 43592 : 43593;

	/** MAIN CONSTANTS **/
	public static final String CLIENT_VERSION = "1.28";
	public final static String CLIENT_NAME = "RuneLive " + CLIENT_VERSION + "";
	public final static String CACHE_DIRECTORY_NAME = "chaos";
	public final static int USELESS_VERSION = 3;

	/** UPDATING **/
	public final static int NPC_BITS = 18;
	public static final int CACHE_INDEX_COUNT = 7;

	/** FEATURES **/
	public static boolean SAVE_ACCOUNTS = false;
	public static boolean DISPLAY_HP_ABOVE_HEAD = false;
	public static boolean DISPLAY_USERNAMES_ABOVE_HEAD = false;
	public static boolean TWEENING_ENABLED = true;

	public static boolean NEW_HITMARKS = true;
	public static boolean PARTICLES = true;
	public static boolean CHRISTMAS_THEME = true;
	public static boolean CONSTITUTION_ENABLED = true;
	public static boolean NEW_HEALTH_BARS = true;

	public static boolean MONEY_POUCH_ENABLED = false;
	public static boolean SMILIES_ENABLED = true;
	public static boolean NOTIFICATIONS_ENABLED = true;
	public static boolean NEW_CURSORS = true;
	public static boolean NEW_FUNCTION_KEYS = true;

	public static boolean TOGGLE_CTRL = false;
	public static boolean QUICK_SPECIAL = true;

	public static boolean FOG_ENABLED = true;
	/**
	 * The client will run in high memory with textures rendering
	 */
	public static boolean HIGH_DETAIL = true;
	public static boolean hdTexturing = true;
	public static boolean hdMinimap = true;
	public static boolean hdShading = true;

	public static boolean COMPLETED_ACHIEVEMENTS = false;

	/**
	 * Roofs should be displayed
	 */
	public static boolean TOGGLE_ROOF_OFF = false;

	/**
	 * Should the new fov be displayed?
	 */
	public static boolean TOGGLE_FOV = true;

}