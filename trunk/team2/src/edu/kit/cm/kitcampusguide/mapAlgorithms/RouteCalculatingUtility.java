package edu.kit.cm.kitcampusguide.mapAlgorithms;

import java.util.List;

import edu.kit.cm.kitcampusguide.ConstantData;
import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * This utility class provides several methods used for calculating Routes on a Graph.
 * 
 * @author Tobias Zündorf
 *
 */
public class RouteCalculatingUtility {
	
	/**
	 * The MapLoader Implementation used by this Class
	 */
	public static MapLoader MAP_LOADER = new ConcreteMapLoader();

	/*
	 * Private constructor to prevent object instantiation
	 */
	private RouteCalculatingUtility() {
		assert false;
	}
	
	/**
	 * The method <code>calculateStreetGraph</code> requests a Graph representing the map from its mapLoader.
	 * Afterwards the specified Points <code>nodes</code> are added to the Graph and connected with the other nodes
	 * within the Graph. 
	 * 
	 * @param nodes nodes that should be contained in the requested Graph 
	 * @return a Graph containing the map and all Points specified in <code>nodes</code>
	 */
	public static Graph calculateStreetGraph(Point... nodes) {
		Graph defaultGraph = ConstantData.getGraph();//MAP_LOADER.getGraph();
		Point[] graphNodes = defaultGraph.getNodes();
		
		for (Point node : nodes) {
			if (defaultGraph.getNodeIndex(node) == -1) {
				defaultGraph.addNode(node);
				double[] distances = new double[graphNodes.length];
				int min = 0;
				for (int j = 0; j < graphNodes.length; j++) {
					distances[j] =  getDistance(graphNodes[j], node);
					if (distances[j] < distances[min]) {
						min = j;
					}
				}
				for (int j = 0; j < graphNodes.length; j++) {
					if (distances[j] <= distances[min] * 1) {
						defaultGraph.addEdge(defaultGraph.numberOfNodes() - 1, j, distances[j]);
						defaultGraph.addEdge(j, defaultGraph.numberOfNodes() - 1, distances[j]);
					}
				}
			}
		}
		
		return defaultGraph;
	}
	
	/*
	 * Calculates all necessary information to save the specified point as landmark for A-star algorithm. After that 
	 * the new landmark is saved in the database.
	 * Throws a IllegalArgumentException if the specified point is not part of the street graph.
	 * 
	 * @param point the point to become a landmark 
	 */
	@SuppressWarnings("unused") // use this method to fill the database
	private static void generateLandmark(Point point) {
		Graph streetGraph = RouteCalculatingUtility.calculateStreetGraph();
		if (streetGraph.getNodeIndex(point) == -1) {
			throw new IllegalArgumentException();
		}
		double[] distances = new double[streetGraph.numberOfNodes()];
		for (int i = 0; i < distances.length; i++) {
			List<Point> route = Dijkstra.getSingleton().calculateRoute(streetGraph.getNode(i), point, streetGraph).getRoute();
			System.out.print("    route> " + i + ": ");
			for (Point p : route) {
				System.out.print("(" + p.getX() + " | " + p.getY() + ") ");
			}
			System.out.println();
			for (int j = 0; j < route.size() - 1; j++) {
				distances[i] += streetGraph.getEdge(streetGraph.getNodeIndex(route.get(j)), streetGraph.getNodeIndex(route.get(j + 1)));
			}
		}
		for (int i = 0; i < distances.length; i++) {
			System.out.println("    > " + i + ": " + distances[i]);
		}
		RouteCalculatingUtility.MAP_LOADER.addLandmarkToDatabase(point, distances);
	}
	
	/*
	 * 
	 */
	private static double getLengthByPolarCoordinates(double radius) {
		return radius / 180 * Math.PI;
	}
	
	/*
	 * Returns the distance in meters between two points on the map with help of their geographical coordinates.
	 * @param pointFrom is the point, from which we calculate the distance.
	 * @param pointTo is the point, to which we calculate the distance.
	 * @return the distance between the two points in meters.
	 */
	private static double getDistance(Point pointFrom, Point pointTo){
		double heightFrom = getLengthByPolarCoordinates(pointFrom.getX());
		double heightTo = getLengthByPolarCoordinates(pointTo.getX());
		double widthFrom = getLengthByPolarCoordinates(pointFrom.getY());
		double widthTo = getLengthByPolarCoordinates(pointTo.getY());
		double e = Math.acos(Math.sin(widthFrom) * Math.sin(widthTo) + Math.cos(widthFrom) * Math.cos(widthTo) * Math.cos(heightTo - heightFrom));
		return e * 6378.137 * 1000;
	}
	
	/**
	 * Generates the Streets for the database.
	 * 
	 * @param args Command-line arguments
	 */
	 // use this method to fill the database
	public static void main(String[] args) {
		int[][] streets = {
				{5, 6}, {5, 11}, {6, 7}, {7, 8}, {7, 13}, {8, 9}, {8, 10}, {9, 12}, {9, 13}, {9, 17}, {10, 11}, {11, 12}, 
				{12, 31}, {13, 14}, {14, 15}, {14, 21}, {15, 19}, {15, 16}, {16, 17}, {16, 18}, {17, 28}, {18, 19},
				{18, 26}, {19, 20}, {20, 21}, {20, 24}, {21, 22}, {22, 23}, {22, 59}, {23, 24}, {23, 54}, {24, 25},
				{25, 26}, {25, 53}, {26, 27}, {27, 28}, {27, 42}, {28, 29}, {29, 30}, {29, 36}, {30, 31}, {31, 32},
				{32, 33}, {33, 34}, {34, 35}, {34, 37}, {35, 36}, {36, 38}, {37, 38}, {37, 114}, {38, 39}, {38, 113},
				{39, 40}, {39, 94}, {40, 41}, {40, 88}, {41, 42}, {41, 46}, {41, 78}, {42, 43}, {43, 44}, {44, 45}, 
				{46, 47}, {47, 49}, {47, 48}, {48, 60}, {48, 63}, {49, 50}, {49, 51}, {51, 52}, {51, 56}, {52, 53}, 
				{52, 55}, {53, 54}, {54, 55}, {54, 59}, {55, 56}, {56, 57}, {57, 58}, {57, 50}, {58, 59}, {60, 61}, 
				{61, 62}, {61, 67}, {62, 63}, {63, 64}, {64, 65}, {64, 69}, {64, 77}, {65, 66}, {66, 67}, {67, 68}, 
				{68, 299}, {69, 70}, {69, 300}, {70, 71}, {70, 310}, {71, 72}, {72, 73}, {73, 74}, {74, 75}, {74, 313}, 
				{75, 76}, {75, 81}, {76, 77}, {77, 78}, {78, 79}, {79, 80}, {79, 88}, {80, 81}, {81, 82}, {81, 85}, 
				{82, 83}, {82, 313}, {83, 84}, {83, 89}, {84, 85}, {84, 86}, {86, 87}, {87, 88}, {87, 90}, {89, 90}, 
				{89, 181}, {90, 91}, {91, 92}, {91, 96}, {92, 93}, {92, 113}, {93, 94}, {93, 95}, {96, 97}, {96, 99}, 
				{97, 98}, {98, 99}, {99, 100}, {100, 101}, {100, 181}, {101, 102}, {102, 103}, {103, 176}, {103, 104}, 
				{104, 105}, {104, 115}, {105, 106}, {106, 107}, {106, 111}, {107, 108}, {108, 109}, {109, 110}, 
				{109, 112}, {110, 11}, {112, 113}, {112, 114}, {115, 116}, {115, 175}, {116, 117}, {117, 118}, 
				{118, 119}, {119, 120}, {119, 127}, {120, 121}, {121, 122}, {121, 174}, {122, 123}, {123, 124}, 
				{124, 125}, {124, 128}, {125, 126}, {126, 127}, {128, 129}, {129, 130}, {129, 132}, {130, 131}, 
				{130, 133}, {131, 163}, {132, 166}, {132, 168}, {133, 134}, {134, 135}, {134, 144}, {135, 136}, 
				{136, 137}, {136, 147}, {137, 138}, {137, 144}, {137, 145}, {138, 139}, {138, 145}, {138, 160}, {139, 140}, 
				{139, 141}, {140, 159}, {141, 142}, {141, 143}, {145, 147}, {145, 146}, {145, 160}, {146, 147}, 
				{146, 148}, {148, 149}, {148, 150}, {149, 151}, {150, 153}, {150, 157}, {151, 152}, {151, 161}, 
				{151, 162}, {152, 153}, {153, 154}, {154, 155}, {154, 157}, {155, 156}, {156, 157}, {156, 159}, 
				{157, 158}, {158, 160}, {158, 159}, {161, 163}, {162, 164}, {163, 220}, {164, 165}, {164, 170}, 
				{165, 166}, {165, 173}, {166, 167}, {167, 168}, {168, 169}, {169, 170}, {169, 392}, {170, 171}, 
				{171, 172}, {171, 173}, {173, 174}, {174, 175}, {174, 190}, {175, 176}, {176, 177}, {177, 178}, 
				{177, 182}, {177, 185}, {178, 179}, {178, 182}, {179, 180}, {180, 181}, {180, 322}, {181, 321}, 
				{182, 183}, {182, 196}, {182, 385}, {183, 184}, {184, 185}, {185, 186}, {186, 187}, {186, 191}, 
				{187, 188}, {187, 189}, {189, 190}, {189, 191}, {190, 193}, {191, 192}, {192, 193}, {192, 194}, 
				{193, 201}, {194, 195}, {194, 198}, {195, 197}, {196, 197}, {197, 198}, {197, 200}, {198, 199}, 
				{199, 202}, {199, 200}, {200, 203}, {201, 202}, {201, 397}, {202, 203}, {203, 204}, {204, 205}, 
				{204, 207}, {205, 206}, {205, 391}, {206, 208}, {206, 211}, {207, 208}, {207, 371}, {207, 375}, 
				{208, 209}, {209, 212}, {209, 210}, {210, 211}, {210, 213}, {212, 213}, {213, 398}, {213, 216}, 
				{213, 215}, {214, 215}, {214, 216}, {214, 392}, {214, 398}, {215, 216}, {215, 218}, {215, 220}, 
				{216, 217}, {217, 218}, {217, 263}, {218, 259}, {219, 220}, {219, 228}, {220, 221}, {221, 222}, 
				{221, 226}, {222, 223}, {223, 224}, {224, 225}, {225, 226}, {225, 234}, {226, 227}, {227, 228}, 
				{228, 229}, {229, 230}, {230, 231}, {231, 232}, {231, 235}, {232, 233}, {233, 234}, {235, 236}, 
				{236, 237}, {236, 240}, {237, 238}, {237, 239}, {238, 239}, {239, 241}, {240, 241}, {241, 242}, 
				{242, 243}, {242, 258}, {243, 246}, {244, 245}, {244, 246}, {244, 250}, {245, 275}, {245, 281}, 
				{246, 247}, {247, 248}, {248, 249}, {249, 250}, {249, 253}, {250, 251}, {251, 252}, {252, 253}, 
				{252, 269}, {253, 254}, {254, 255}, {255, 256}, {256, 257}, {256, 260}, {257, 258}, {257, 259}, 
				{260, 261}, {261, 262}, {262, 263}, {262, 268}, {263, 264}, {264, 265}, {264, 369}, {265, 266}, 
				{265, 268}, {266, 267}, {267, 268}, {269, 270}, {269, 272}, {270, 271}, {271, 272}, {271, 354}, 
				{272, 273}, {272, 274}, {273, 274}, {274, 275}, {274, 276}, {275, 276}, {276, 277}, {276, 279}, 
				{277, 278}, {277, 355}, {278, 279}, {279, 280}, {280, 281}, {280, 290}, {281, 282}, {282, 283}, 
				{282, 291}, {283, 284}, {283, 297}, {284, 285}, {284, 292}, {285, 286}, {285, 300}, {286, 287}, 
				{286, 288}, {286, 289}, {286, 290}, {286, 291}, {287, 288}, {287, 289}, {287, 290}, {287, 328}, 
				{288, 289}, {288, 291}, {288, 327}, {289, 290}, {289, 291}, {290, 291}, {292, 293}, {293, 294}, 
				{292, 297}, {293, 299}, {294, 295}, {295, 296}, {297, 298}, {300, 301}, {301, 302}, {301, 303}, 
				{302, 308}, {303, 304}, {303, 328}, {304, 305}, {305, 306}, {305, 329}, {306, 307}, {306, 308}, 
				{307, 330}, {308, 309}, {309, 310}, {310, 311}, {311, 312}, {312, 313}, {312, 314}, {314, 315}, 
				{314, 318}, {315, 316}, {315, 330}, {316, 317}, {316, 325}, {317, 318}, {317, 319}, {319, 320}, 
				{320, 321}, {320, 324}, {321, 322}, {321, 323}, {322, 331}, {322, 387}, {323, 324}, {324, 325},
				{325, 326}, {325, 335}, {326, 327}, {326, 339}, {327, 328}, {328, 329}, {329, 330}, {331, 332}, 
				{332, 333}, {332, 361}, {333, 334}, {333, 335}, {334, 337}, {334, 360}, {335, 336}, {336, 337}, 
				{336, 339}, {337, 347}, {337, 338}, {338, 339}, {338, 340}, {340, 341}, {341, 347}, {341, 350}, 
				{340, 342}, {342, 343}, {343, 344}, {343, 346}, {344, 345}, {345, 346}, {347, 348}, {347, 349}, 
				{348, 350}, {348, 349}, {349, 359}, {349, 358}, {350, 351}, {350, 352}, {351, 353}, {351, 358}, 
				{352, 353}, {352, 355}, {353, 356}, {353, 354}, {354, 355}, {356, 357}, {357, 358}, {357, 365}, 
				{357, 367}, {358, 359}, {359, 360}, {360, 361}, {361, 362}, {362, 363}, {363, 364}, {363, 383}, 
				{364, 365}, {364, 373}, {365, 366}, {366, 367}, {366, 372}, {367, 368}, {368, 369}, {369, 370}, 
				{370, 371}, {371, 372}, {373, 374}, {374, 375}, {375, 376}, {375, 380}, {376, 377}, {377, 378}, 
				{378, 379}, {379, 380}, {380, 381}, {380, 384}, {381, 382}, {382, 383}, {383, 384}, {384, 385}, 
				{384, 388}, {385, 386}, {386, 387}, {387, 388}, {389, 390}, {390, 391}, {390, 393}, {391, 392}, 
				{391, 398}, {393, 394}, {394, 395}, {395, 396}, {396, 397},
				{399, 121}, {399, 165}, {399, 400}, {400, 401}, {401, 402}, {402, 403}, {403, 123}, {404, 370}, 
				{404, 216}, {404, 405}};
		MapLoader ml = new ConcreteMapLoader();
		Graph g = ml.getGraph();
		for (int i = 0; i < streets.length; i++) {
			ml.addStreetToDatabase(streets[i][0], streets[i][1], getDistance(g.getNode(streets[i][0]), g.getNode(streets[i][1])));
			ml.addStreetToDatabase(streets[i][1], streets[i][0], getDistance(g.getNode(streets[i][0]), g.getNode(streets[i][1])));
			System.out.println((i + 1) + " of " + streets.length + " done");
		}
	}
}
