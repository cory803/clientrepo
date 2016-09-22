package org.chaos.client.world;

import org.chaos.client.Client;

/**
 * Custom object spawns
 * 
 * @author Gabbe
 */
public class CustomObjects {

	/**
	 * Spawns the objects to the map
	 */
	public static void spawn() {
		for (int i = 0; i < CUSTOM_OBJECTS.length; i++) {
			int id = CUSTOM_OBJECTS[i][0];
			int x = CUSTOM_OBJECTS[i][1];
			int y = CUSTOM_OBJECTS[i][2];
			int z = CUSTOM_OBJECTS[i][3];
			int face = CUSTOM_OBJECTS[i][4];
			Client.instance.addObject(x, y, id, face, 10, z);
		}
	}

	public static final int[][] CUSTOM_OBJECTS = {
			/* Training Grounds Skillzone */
			//dummies
			{ -1, 2516, 3369, 0, 0 },
			{ -1, 2514, 3369, 0, 0 },
			{ -1, 2514, 3367, 0, 0 },
			{ -1, 2515, 3365, 0, 0 },
			{ -1, 2516, 3370, 0, 0 },
			{ -1, 2513, 3371, 0, 0 },
			{ -1, 2511, 3365, 0, 0 },
			{ -1, 2511, 3369, 0, 0 },
			{ -1, 2511, 3373, 0, 0 },
			{ -1, 2510, 3367, 0, 0 },
			{ -1, 2508, 3366, 0, 0 },
			{ -1, 2509, 3371, 0, 0 },
			{ -1, 2507, 3368, 0, 0 },
			{ -1, 2505, 3370, 0, 0 },
			{ -1, 2514, 3384, 0, 0 }, // table
			//bank booths
			{ 2213, 2515, 3383, 0, 2 },
			{ 2213, 2514, 3383, 0, 2 },
			//ores
			{ 9709, 2523, 3377, 0, 0 },
			{ 9708, 2524, 3377, 0, 0 },
			{ 9715, 2525, 3377, 0, 0 },
			{ 9714, 2526, 3377, 0, 0 },
			{ 9718, 2527, 3377, 0, 0 },
			{ 9717, 2528, 3377, 0, 0 },
			{ 29217, 2529, 3377, 0, 0 },
			{ 29215, 2530, 3377, 0, 0 },
			{ 9720, 2531, 3377, 0, 0 },
			{ 25370, 2532, 3377, 0, 0 },
			{ 11941, 2533, 3377, 0, 0 },
			{ 9708, 2523, 3373, 0, 0 },
			{ 9714, 2524, 3373, 0, 0 },
			{ 9717, 2525, 3373, 0, 0 },
			{ 29217, 2526, 3373, 0, 0 },
			{ 29215, 2527, 3373, 0, 0 },
			{ 29215, 2528, 3373, 0, 0 },
			{ 9720, 2529, 3373, 0, 0 },
			{ 9720, 2530, 3373, 0, 0 },
			{ 25368, 2531, 3373, 0, 0 },
			{ 25370, 2532, 3373, 0, 0 },
			{ 11941, 2533, 3373, 0, 0 },
			//furnace & anvil
			{ 6189, 2532, 3370, 0, 2 },
			{ 2783, 2531, 3369, 0, 2 },
			//stalls

			{ 6552, 3232, 2887, 0, 0 },
			{ 9326, 3001, 3960, 0, 0 },
			// 1 = west
			// 2 = north
			// 3 = east
			// 4 = south
	};

	public class GameObject {

		public GameObject(int id, int x, int y, int z, int face) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.z = z;
			this.face = face;
		}

		public int id;
		public int x, y, z;
		public int face;
	}

}
