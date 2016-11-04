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
			//Dungeoneering exit portals
			{ 2477, 3284, 9195, 0, 0 },
			{ 2477, 3279, 9195, 0, 0 },
			{ 2477, 3303, 9195, 0, 0 },
			{ 2477, 3308, 9195, 0, 0 },

			//Dungeoneering war chest
			{ 2403, 3281, 9192, 0, 0 },
			{ 2403, 3282, 9192, 0, 0 },
			{ 2403, 3305, 9192, 0, 0 },
			{ 2403, 3306, 9192, 0, 0 },

			//skilling zone (high)
			{ 2781, 2856, 2966, 0, 0 },
			{ 14859, 2819, 2998, 0, 0 },
			{ 14859, 2821, 3000, 0, 0 },
			{ 14859, 2822, 2999, 0, 2 },
			{ 14851, 2824, 3002, 0, 0 },
			{ 14851, 2819, 2994, 0, 0 },
			{ 14851, 2820, 2995, 0, 1 },
			{ 14851, 2820, 2996, 0, 0 },
			{ 14862, 2824, 2998, 0, 0 },
			{ 14859, 2820, 2999, 0, 0 },
			{ 14862, 2826, 3001, 0, 3 },
			{ 14862, 2823, 2998, 0, 2 },
			{ 14862, 2825, 3000, 0, 0 },
			{ 14862, 2824, 2998, 0, 1 },
			{ 14862, 2826, 3000, 0, 1 },
			{ 14851, 2819, 2995, 0, 0 },
			{ 14851, 2819, 2995, 0, 2 },
			{ 14851, 2823, 3002, 0, 0 },
			{ 14851, 2819, 2995, 0, 0 },
			{ 2213, 2852, 2951, 0, 2 },
			{ 2213, 2853, 2951, 0, 2 },
			{ -1, 2856, 2966, 0, 0 },
			{ 6189, 2856, 2967, 0, 1 },
			{ 2783, 2855, 2965, 0, 3 },
			{ 1309, 2845, 2962, 0, 2 },
			{ 1309, 2843, 2966, 0, 0 },
			{ 1309, 2847, 2965, 0, 3 },
			{ 1309, 2863, 2958, 0, 3 },
			{ 1309, 2864, 2955, 0, 3 },
			{ 1306, 2853, 2992, 0, 2 },
			{ 1306, 2852, 3000, 0, 2 },
			{ 1306, 2855, 3000, 0, 2 },
			{ 1306, 2855, 3003, 0, 2 },
			{ 1306, 2853, 3004, 0, 2 },
			{ 1306, 2831, 3002, 0, 2 },
			{ 1306, 2837, 3003, 0, 1 },
			{ 1306, 2834, 3004, 0, 1 },
			{ 1306, 2831, 2999, 0, 2 },
			{ 1306, 2835, 2999, 0, 0 },
			//Skilling zone (low)
			{ -1, 2805, 2785, 0, 0 },
			{ 1278, 2799, 2779, 0, 1 },
			{ 1278, 2794, 2780, 0, 2 },
			{ 1277, 2787, 2787, 0, 3 },
			{ 1277, 2787, 2784, 0, 1 },
			{ -1, 2791, 2786, 0, 1 },
			{ -1, 2791, 2786, 1, 1 },
			{ -1, 2791, 2786, 2, 1 },
			{ -1, 2790, 2785, 3, 1 },
			{ 1281, 2790, 2785, 0, 1 },
			{ 1307, 2787, 2791, 0, 0 },
			{ 1307, 2787, 2795, 0, 2 },
			{ 1302, 2790, 2792, 0, 1 },
			{ 5551, 2787, 2776, 0, 2 },
			{ 5551, 2786, 2779, 0, 1 },
			{ 5552, 2790, 2778, 0, 1 },
			{ -1, 2807, 2785, 0, 2 },
			{ -1, 2794, 2773, 0, 2 },
			{ 1756, 2807, 2785, 0, 1},
			{ 2, 2792, 2771, 0, 2},
			{ 2, 2383, 4704, 0, 2},
			{ 2728, 2805, 2775, 0, 3},
			{ 26814, 2801, 2778, 0, 3},
			{ 2783, 2805, 2783, 0, 2},

			//Donator zone mining
			{ 2092, 2529, 3896, 0, 0},
			{ 2093, 2528, 3895, 0, 1},
			{ 2093, 2529, 3894, 0, 1},
			{ 7459, 2530, 3893, 0, 2},
			{ 7459, 2531, 3893, 0, 1},
			{ 7459, 2529, 3892, 0, 0},
			{ 2090, 2529, 3890, 0, 1},
			{ 2091, 2529, 3890, 0, 1},
			{ 2090, 2528, 3889, 0, 1},
			{ 2090, 2529, 3888, 0, 2},
			{ 2094, 2527, 3886, 0, 1},
			{ 2095, 2528, 3885, 0, 1},
			{ 2095, 2529, 3885, 0, 2},
			{ 2095, 2520, 3885, 0, 1},
			{ 7493, 2522, 3894, 0, 1},
			{ 7493, 2522, 3893, 0, 2},
			{ 7493, 2523, 3892, 0, 1},
			{ 7493, 2523, 3892, 0, 1},
			{ 7493, 2524, 3891, 0, 3},
			{ 7491, 2524, 3890, 0, 2},
			{ 7491, 2524, 3890, 0, 1},
			{ 7491, 2525, 3890, 0, 2},
			{ 7491, 2525, 3889, 0, 1},
			{ 2097, 2526, 3888, 0, 1},
			{ 2097, 2526, 3893, 0, 1},
			{ 2097, 2527, 3892, 0, 2},
			{ 2097, 2527, 3891, 0, 1},


			{ -1, 3225, 3665, 0, 2},
			{ -1, 3229, 3665, 0, 2},
			{ -1, 3225, 3669, 0, 2},
			{ -1, 3229, 3669, 0, 2},

			{ -1, 3104, 3570, 0, 2},
			{ -1, 3105, 3569, 0, 2},
			{ -1, 3104, 3567, 0, 2},
			{ -1, 3103, 3566, 0, 2},
			{ -1, 3103, 3565, 0, 2},
			{ -1, 3103, 3565, 0, 0},

			{ 7489, 3104, 3570, 0, 2},
			{ 7489, 3105, 3569, 0, 1},
			{ 7489, 3104, 3567, 0, 2},
			{ 7489, 3103, 3566, 0, 1},
			{ 7489, 3103, 3565, 0, 2},
			{ 7489, 3103, 3565, 0, 1},

			{ 2093, 2382, 4725, 0, 1},
			{ 2092, 2381, 4726, 0, 1},
			{ 2093, 2380, 4725, 0, 2},

			{ 2094, 2386, 4724, 0, 2},
			{ 2094, 2385, 4724, 0, 1},
			{ 2095, 2384, 4724, 0, 2},
			{ 2095, 2384, 4722, 0, 1},
			{ 2095, 2383, 4721, 0, 3},

			{ 2091, 2388, 4722, 0, 2},
			{ 2091, 2388, 4724, 0, 1},
			{ 2090, 2386, 4722, 0, 2},

			{ 7491, 2388, 4719, 0, 2},
			{ 7491, 2388, 4718, 0, 1},
			{ 7491, 2388, 4718, 0, 1},

			{ 2097, 2379, 4720, 0, 1},
			{ 2097, 2379, 4721, 0, 1},
			{ 2098, 2381, 4721, 0, 1},

			{ 7459, 2382, 4723, 0, 1},
			{ 7459, 2385, 4719, 0, 1},
			{ 7459, 2386, 4720, 0, 1},
			{ 7459, 2384, 4719, 0, 1},
			{ 7459, 2386, 4717, 0, 1},

			{ 7493, 2382, 4713, 0, 1},
			{ 7493, 2382, 4712, 0, 2},
			{ 7493, 2382, 4715, 0, 1},
			{ 7493, 2385, 4716, 0, 1},

			{ -1, 3190, 3938, 0, 2},


			{ 409, 2501, 3865, 0, 2 },
			{ 2783, 2540, 3890, 0, 1 },

			{ -1, 3095, 3498, 0, 1 },
			{ -1, 3095, 3499, 0, 1 },
			{ 589, 3095, 3498, 0, 1 },

			/* Home Objects */
			{ 409, 3085, 3509, 0, 1 },
			{ 172, 3090, 3495, 0, 1 },
			/* End Home Objects */
			//Rfd Chest & Portal
			{ 2182, 3081, 3495, 0, 3 },
			{ 12356, 3077, 3492, 0, 2 },
			//rune ore @ BH
			{ 14859, 3118, 3690, 0, 1 },
			{ 14859, 3117, 3691, 0, 1 },
			{ 14859, 3119, 3689, 0, 1 },
			{ 14859, 3117, 3688, 0, 1 },
			{ 14859, 3116, 3688, 0, 1 },
			{ 14859, 3115, 3690, 0, 1 },
			{ 14859, 3116, 3689, 0, 1 },
			/* Training Grounds Skillzone */

			//giant crystal
			{ 62, 2508, 3363, 0, 2 },
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
			{ 4876, 2527, 3369, 0, 2 },
			{ 4875, 2526, 3369, 0, 2 },
			{ 4874, 2525, 3369, 0, 2 },
			{ 4877, 2524, 3369, 0, 2 },
			{ 4878, 2523, 3369, 0, 2 },

			{ 6552, 3232, 2887, 0, 0 },
			{ 9326, 3001, 3960, 0, 0 },
			{ -1, 2342, 3807, 0, 0 },
			{ -1, 2344, 3809, 0, 0 },
			{ 2783, 2342, 3807, 0, 0 },

			// ezone frost dragon tele
			{47180, 3363, 9640, 0, 0},
			/**
			 * Ezone Skilling Beings
			 **/
			// skilling anvil
			{4306, 3375, 9660, 0, 4},
			// furnace
			{6189, 3376, 9659, 0, 1},
			// bank booths
			// mining booth
			{2213, 3363, 9652, 0, 0},
			// wc booth
			{2213, 3363, 9627, 0, 0}, {2213, 3351, 9640, 0, 3}, {2213, 3376, 9640, 0, 1},
			// mage trees
			{1306, 3353, 9626, 0, 0}, {1306, 3356, 9626, 0, 0}, {1306, 3359, 9626, 0, 0},
			{1306, 3366, 9626, 0, 0}, {1306, 3369, 9626, 0, 0}, {1306, 3372, 9626, 0, 0},
			// coal
			{29215, 3354, 9653, 0, 0}, {29215, 3355, 9653, 0, 0}, {29215, 3356, 9653, 0, 0},
			{29215, 3357, 9653, 0, 0},
			// gold
			{11183, 3358, 9653, 0, 0}, {11183, 3359, 9653, 0, 0},
			// mithril
			{11945, 3360, 9653, 0, 0}, {11945, 3361, 9653, 0, 0},
			// addy
			{11939, 3365, 9653, 0, 0}, {11939, 3366, 9653, 0, 0},
			// rune
			{14859, 3367, 9653, 0, 0}, {14859, 3368, 9653, 0, 0}, {14859, 3369, 9653, 0, 0},
			{14859, 3370, 9653, 0, 0}, {14859, 3371, 9653, 0, 0}, {14859, 3372, 9653, 0, 0},
			{14859, 3373, 9653, 0, 0},
			/**
			 * Ezone Ends
			 **/
			/** New Member Zone */
			{ 2344, 3421, 2908, 0, 0 }, // Rock blocking
			{ 2345, 3438, 2909, 0, 0 }, { 2344, 3435, 2909, 0, 0 }, { 2344, 3432, 2909, 0, 0 },
			{ 2345, 3431, 2909, 0, 0 }, { 2344, 3428, 2921, 0, 1 }, { 2344, 3428, 2918, 0, 1 },
			{ 2344, 3428, 2915, 0, 1 }, { 2344, 3428, 2912, 0, 1 }, { 2345, 3428, 2911, 0, 1 },
			{ 2344, 3417, 2913, 0, 1 }, { 2344, 3417, 2916, 0, 1 }, { 2344, 3417, 2919, 0, 1 },
			{ 2344, 3417, 2922, 0, 1 }, { 2345, 3417, 2912, 0, 0 }, { 2346, 3418, 2925, 0, 0 },
			{ 8749, 3426, 2923, 0, 2 }, // Altar
			{ -1, 3420, 2909, 0, 10 }, // Remove crate by mining
			{ -1, 3420, 2923, 0, 10 }, // Remove Rockslide by Woodcutting
			{ 14859, 3421, 2909, 0, 0 }, // Mining
			{ 14859, 3419, 2909, 0, 0 }, { 14859, 3418, 2910, 0, 0 }, { 14859, 3418, 2911, 0, 0 },
			{ 4483, 2812, 5508, 0, 2 }, // Fun pk bank chest

			{ 14859, 3422, 2909, 0, 0 }, { 1306, 3418, 2921, 0, 0 }, // Woodcutting
			{ 1306, 3421, 2924, 0, 0 }, { 1306, 3420, 2924, 0, 0 }, { 1306, 3419, 2923, 0, 0 },
			{ 1306, 3418, 2922, 0, 0 }, { -1, 3430, 2912, 0, 2 },
			/** New Member Zone end */

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
