package data;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.data.ConcreteMapLoader;
import edu.kit.cm.kitcampusguide.data.MapLoader;
import edu.kit.cm.kitcampusguide.model.Graph;
import edu.kit.cm.kitcampusguide.model.Point;

/**
 * This class tests the ConcreteMapLoader.
 * 
 * @author Michael Hauber
 */
public class ConcreteMapLoaderTest {

	/**
	 * This test tests the method getGraph() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetGraph() {
		Point[] expectedNodes = { new Point(0.0, 0.0), 
				new Point(0.0, 0.0), 
				new Point(0.0, 0.0), 
				new Point(0.0, 0.0), 
				new Point(0.0, 0.0), 
				new Point(8.40524, 49.01391), 
				new Point(8.4052, 49.01355), 
				new Point(8.40538, 49.01326), 
				new Point(8.40587, 49.01354), 
				new Point(8.40643, 49.01335), 
				new Point(8.40603, 49.01364), 
				new Point(8.40609, 49.01388), 
				new Point(8.40648, 49.01387), 
				new Point(8.40572, 49.01295), 
				new Point(8.4062, 49.0126), 
				new Point(8.40654, 49.0128), 
				new Point(8.40673, 49.0129), 
				new Point(8.40712, 49.01314), 
				new Point(8.40704, 49.01265), 
				new Point(8.40686, 49.01255), 
				new Point(8.40698, 49.01247), 
				new Point(8.40665, 49.01227), 
				new Point(8.40707, 49.01197), 
				new Point(8.40739, 49.01215), 
				new Point(8.40727, 49.01224), 
				new Point(8.40744, 49.01233), 
				new Point(8.40735, 49.01241), 
				new Point(8.40806, 49.01283), 
				new Point(8.4079, 49.01288), 
				new Point(8.40797, 49.01372), 
				new Point(8.40788, 49.01376), 
				new Point(8.40786, 49.01383), 
				new Point(8.40791, 49.01389), 
				new Point(8.40801, 49.01391), 
				new Point(8.4081, 49.01387), 
				new Point(8.40813, 49.01381), 
				new Point(8.40808, 49.01374), 
				new Point(8.41033, 49.0138), 
				new Point(8.41032, 49.01367), 
				new Point(8.41023, 49.01331), 
				new Point(8.41007, 49.01284), 
				new Point(8.4097, 49.01227), 
				new Point(8.40879, 49.01259), 
				new Point(8.40891, 49.01273), 
				new Point(8.40895, 49.01305), 
				new Point(8.40905, 49.01305), 
				new Point(8.40926, 49.01176), 
				new Point(8.40908, 49.01164), 
				new Point(8.40906, 49.01161), 
				new Point(8.40856, 49.01198), 
				new Point(8.40839, 49.01213), 
				new Point(8.40818, 49.01178), 
				new Point(8.40809, 49.01184), 
				new Point(8.40792, 49.01196), 
				new Point(8.40776, 49.01188), 
				new Point(8.40793, 49.01177), 
				new Point(8.40803, 49.0117), 
				new Point(8.40783, 49.01161), 
				new Point(8.40767, 49.01153), 
				new Point(8.40743, 49.01171), 
				new Point(8.40846, 49.01117), 
				new Point(8.40881, 49.01089), 
				new Point(8.40913, 49.01107), 
				new Point(8.40958, 49.01134), 
				new Point(8.41008, 49.0111), 
				new Point(8.40952, 49.01073), 
				new Point(8.40925, 49.01069), 
				new Point(8.40915, 49.01064), 
				new Point(8.40908, 49.0106), 
				new Point(8.41071, 49.01077), 
				new Point(8.41137, 49.01133), 
				new Point(8.41149, 49.01146), 
				new Point(8.41158, 49.01155), 
				new Point(8.41148, 49.01159), 
				new Point(8.41157, 49.01169), 
				new Point(8.41142, 49.01174), 
				new Point(8.41098, 49.01188), 
				new Point(8.41081, 49.01193), 
				new Point(8.41071, 49.01197), 
				new Point(8.41074, 49.01224), 
				new Point(8.41095, 49.01219), 
				new Point(8.41148, 49.01217), 
				new Point(8.41193, 49.01214), 
				new Point(8.41195, 49.01237), 
				new Point(8.41165, 49.01238), 
				new Point(8.41152, 49.01238), 
				new Point(8.41168, 49.0126), 
				new Point(8.41174, 49.01277), 
				new Point(8.4108, 49.01281), 
				new Point(8.41199, 49.01257), 
				new Point(8.41199, 49.01276), 
				new Point(8.41204, 49.01301), 
				new Point(8.4119, 49.01304), 
				new Point(8.41127, 49.01307), 
				new Point(8.41128, 49.01328), 
				new Point(8.41105, 49.01308), 
				new Point(8.41262, 49.01298), 
				new Point(8.41261, 49.01269), 
				new Point(8.41305, 49.01267), 
				new Point(8.4131, 49.01297), 
				new Point(8.41358, 49.01296), 
				new Point(8.4138, 49.01297), 
				new Point(8.4139, 49.01304), 
				new Point(8.41393, 49.01343), 
				new Point(8.41395, 49.01359), 
				new Point(8.41373, 49.0136), 
				new Point(8.41318, 49.01362), 
				new Point(8.41305, 49.01373), 
				new Point(8.41268, 49.01374), 
				new Point(8.41257, 49.01364), 
				new Point(8.41267, 49.01352), 
				new Point(8.41304, 49.01351), 
				new Point(8.41196, 49.01367), 
				new Point(8.41195, 49.01358), 
				new Point(8.41196, 49.01372), 
				new Point(8.41411, 49.01358), 
				new Point(8.41438, 49.01385), 
				new Point(8.41482, 49.01387), 
				new Point(8.41493, 49.01393), 
				new Point(8.41529, 49.01395), 
				new Point(8.41548, 49.01381), 
				new Point(8.41633, 49.01376), 
				new Point(8.41641, 49.01385), 
				new Point(8.41643, 49.01437), 
				new Point(8.41655, 49.01522), 
				new Point(8.41655, 49.01532), 
				new Point(8.41647, 49.01538), 
				new Point(8.41547, 49.01543), 
				new Point(8.41736, 49.01518), 
				new Point(8.41824, 49.01519), 
				new Point(8.4185, 49.01522), 
				new Point(8.4185, 49.01439), 
				new Point(8.41825, 49.01443), 
				new Point(8.41912, 49.01526), 
				new Point(8.41935, 49.01527), 
				new Point(8.41931, 49.01454), 
				new Point(8.41962, 49.01452), 
				new Point(8.41996, 49.0145), 
				new Point(8.4204, 49.01447), 
				new Point(8.42061, 49.01445), 
				new Point(8.42122, 49.01439), 
				new Point(8.42063, 49.0147), 
				new Point(8.42037, 49.01473), 
				new Point(8.42066, 49.01537), 
				new Point(8.42006, 49.01526), 
				new Point(8.41991, 49.01417), 
				new Point(8.41958, 49.01396), 
				new Point(8.41962, 49.01417), 
				new Point(8.41949, 49.01386), 
				new Point(8.41937, 49.01371), 
				new Point(8.41981, 49.01356), 
				new Point(8.41914, 49.01358), 
				new Point(8.4194, 49.01347), 
				new Point(8.42009, 49.01343), 
				new Point(8.42044, 49.01341), 
				new Point(8.42119, 49.01338), 
				new Point(8.42119, 49.01352), 
				new Point(8.42045, 49.01355), 
				new Point(8.42042, 49.01382), 
				new Point(8.42119, 49.0138), 
				new Point(8.42041, 49.01416), 
				new Point(8.41891, 49.01349), 
				new Point(8.41887, 49.01361), 
				new Point(8.41842, 49.01351), 
				new Point(8.4175, 49.01364), 
				new Point(8.41739, 49.01365), 
				new Point(8.4181, 49.01398), 
				new Point(8.41804, 49.01387), 
				new Point(8.41814, 49.01357), 
				new Point(8.41813, 49.01344), 
				new Point(8.41781, 49.01345), 
				new Point(8.41766, 49.01345), 
				new Point(8.41763, 49.01314), 
				new Point(8.41662, 49.01349), 
				new Point(8.41629, 49.0135), 
				new Point(8.41446, 49.01358), 
				new Point(8.41446, 49.01342), 
				new Point(8.41443, 49.01304), 
				new Point(8.41441, 49.0126), 
				new Point(8.41386, 49.01261), 
				new Point(8.41385, 49.01252), 
				new Point(8.41353, 49.01253), 
				new Point(8.4149, 49.01258), 
				new Point(8.41492, 49.01285), 
				new Point(8.415, 49.01294), 
				new Point(8.41514, 49.01302), 
				new Point(8.41523, 49.01301), 
				new Point(8.41526, 49.0132), 
				new Point(8.41528, 49.01337), 
				new Point(8.41585, 49.01319), 
				new Point(8.41626, 49.01318), 
				new Point(8.41576, 49.01301), 
				new Point(8.41587, 49.01285), 
				new Point(8.41621, 49.01282), 
				new Point(8.41585, 49.01257), 
				new Point(8.41559, 49.01257), 
				new Point(8.41518, 49.01252), 
				new Point(8.41554, 49.01251), 
				new Point(8.41584, 49.0125), 
				new Point(8.41599, 49.0125), 
				new Point(8.41597, 49.01229), 
				new Point(8.4162, 49.01255), 
				new Point(8.41618, 49.01248), 
				new Point(8.41615, 49.01227), 
				new Point(8.41614, 49.01219), 
				new Point(8.41692, 49.01215), 
				new Point(8.4169, 49.01197), 
				new Point(8.41608, 49.01156), 
				new Point(8.41682, 49.01153), 
				new Point(8.41701, 49.01151), 
				new Point(8.4174, 49.0115), 
				new Point(8.41748, 49.01193), 
				new Point(8.41696, 49.01123), 
				new Point(8.41737, 49.01121), 
				new Point(8.41794, 49.01123), 
				new Point(8.41787, 49.01092), 
				new Point(8.41742, 49.01095), 
				new Point(8.41741, 49.01086), 
				new Point(8.41784, 49.01084), 
				new Point(8.4182, 49.01092), 
				new Point(8.41824, 49.01117), 
				new Point(8.41858, 49.01116), 
				new Point(8.41937, 49.01111), 
				new Point(8.41944, 49.01097), 
				new Point(8.41933, 49.01082), 
				new Point(8.41891, 49.01043), 
				new Point(8.41857, 49.0108), 
				new Point(8.41834, 49.01067), 
				new Point(8.4182, 49.01067), 
				new Point(8.41821, 49.01036), 
				new Point(8.41851, 49.00985), 
				new Point(8.41864, 49.00979), 
				new Point(8.41895, 49.00978), 
				new Point(8.41933, 49.00992), 
				new Point(8.41919, 49.01007), 
				new Point(8.41895, 49.00954), 
				new Point(8.41872, 49.00943), 
				new Point(8.41852, 49.00935), 
				new Point(8.41854, 49.00898), 
				new Point(8.41784, 49.00912), 
				new Point(8.4186, 49.00953), 
				new Point(8.41758, 49.00917), 
				new Point(8.4172, 49.0092), 
				new Point(8.41703, 49.0091), 
				new Point(8.41636, 49.00912), 
				new Point(8.41445, 49.00921), 
				new Point(8.41693, 49.00911), 
				new Point(8.41694, 49.00935), 
				new Point(8.41681, 49.00935), 
				new Point(8.41658, 49.00947), 
				new Point(8.41641, 49.00947), 
				new Point(8.41612, 49.0095), 
				new Point(8.41611, 49.00964), 
				new Point(8.41659, 49.00962), 
				new Point(8.41663, 49.00978), 
				new Point(8.41672, 49.00986), 
				new Point(8.41724, 49.00985), 
				new Point(8.41764, 49.00982), 
				new Point(8.41759, 49.00966), 
				new Point(8.41774, 49.01004), 
				new Point(8.41732, 49.00999), 
				new Point(8.41733, 49.01022), 
				new Point(8.41726, 49.01025), 
				new Point(8.4173, 49.01086), 
				new Point(8.41673, 49.01087), 
				new Point(8.41667, 49.01048), 
				new Point(8.41621, 49.01048), 
				new Point(8.4162, 49.01024), 
				new Point(8.41667, 49.01027), 
				new Point(8.41586, 49.00974), 
				new Point(8.41586, 49.01002), 
				new Point(8.41507, 49.01006), 
				new Point(8.41507, 49.00975), 
				new Point(8.41505, 49.00955), 
				new Point(8.41465, 49.00956), 
				new Point(8.41447, 49.00948), 
				new Point(8.41448, 49.00955), 
				new Point(8.41449, 49.00972), 
				new Point(8.41389, 49.00975), 
				new Point(8.41387, 49.00957), 
				new Point(8.41309, 49.00962), 
				new Point(8.41305, 49.00926), 
				new Point(8.41224, 49.00929), 
				new Point(8.41135, 49.00933), 
				new Point(8.41137, 49.00953), 
				new Point(8.41141, 49.00987), 
				new Point(8.41223, 49.00983), 
				new Point(8.41224, 49.01009), 
				new Point(8.41273, 49.01007), 
				new Point(8.41271, 49.00981), 
				new Point(8.41272, 49.00964), 
				new Point(8.41226, 49.00965), 
				new Point(8.41036, 49.00955), 
				new Point(8.41027, 49.00963), 
				new Point(8.41003, 49.00951), 
				new Point(8.40997, 49.00929), 
				new Point(8.40949, 49.00932), 
				new Point(8.41032, 49.00937), 
				new Point(8.41038, 49.00918), 
				new Point(8.40964, 49.01018), 
				new Point(8.41144, 49.01039), 
				new Point(8.41147, 49.01057), 
				new Point(8.41139, 49.01061), 
				new Point(8.4117, 49.01057), 
				new Point(8.41171, 49.01064), 
				new Point(8.41179, 49.01069), 
				new Point(8.41181, 49.01081), 
				new Point(8.41195, 49.01082), 
				new Point(8.41166, 49.01087), 
				new Point(8.41198, 49.01113), 
				new Point(8.41186, 49.01125), 
				new Point(8.4121, 49.01139), 
				new Point(8.41218, 49.01145), 
				new Point(8.41186, 49.01157), 
				new Point(8.41237, 49.01138), 
				new Point(8.41252, 49.01133), 
				new Point(8.4128, 49.01126), 
				new Point(8.41279, 49.01153), 
				new Point(8.41244, 49.01156), 
				new Point(8.41289, 49.01213), 
				new Point(8.41324, 49.01213), 
				new Point(8.41352, 49.01213), 
				new Point(8.41382, 49.01212), 
				new Point(8.4135, 49.01167), 
				new Point(8.41321, 49.01167), 
				new Point(8.41318, 49.01113), 
				new Point(8.41312, 49.01052), 
				new Point(8.41277, 49.01053), 
				new Point(8.41229, 49.01055), 
				new Point(8.41235, 49.01066), 
				new Point(8.41239, 49.0108), 
				new Point(8.41369, 49.01137), 
				new Point(8.41369, 49.01117), 
				new Point(8.41369, 49.01099), 
				new Point(8.41378, 49.01098), 
				new Point(8.41356, 49.01102), 
				new Point(8.41354, 49.01073), 
				new Point(8.41377, 49.01072), 
				new Point(8.41375, 49.0105), 
				new Point(8.41351, 49.0105), 
				new Point(8.41403, 49.01047), 
				new Point(8.41416, 49.01047), 
				new Point(8.41399, 49.01007), 
				new Point(8.41352, 49.01009), 
				new Point(8.41312, 49.0101), 
				new Point(8.41313, 49.01024), 
				new Point(8.41352, 49.01022), 
				new Point(8.4145, 49.01069), 
				new Point(8.41459, 49.01069), 
				new Point(8.41459, 49.01076), 
				new Point(8.41458, 49.01046), 
				new Point(8.41481, 49.01046), 
				new Point(8.41455, 49.01026), 
				new Point(8.4148, 49.01026), 
				new Point(8.4148, 49.0101), 
				new Point(8.41452, 49.01008), 
				new Point(8.41502, 49.01026), 
				new Point(8.41508, 49.01091), 
				new Point(8.41484, 49.01093), 
				new Point(8.4146, 49.01094), 
				new Point(8.41405, 49.01096), 
				new Point(8.41407, 49.01116), 
				new Point(8.41408, 49.01129), 
				new Point(8.41483, 49.01126), 
				new Point(8.41519, 49.01127), 
				new Point(8.41519, 49.011), 
				new Point(8.41548, 49.01103), 
				new Point(8.41564, 49.01087), 
				new Point(8.41605, 49.01087), 
				new Point(8.41606, 49.0109), 
				new Point(8.41607, 49.01098), 
				new Point(8.41605, 49.01129), 
				new Point(8.41591, 49.0112), 
				new Point(8.41552, 49.01128), 
				new Point(8.41579, 49.01149), 
				new Point(8.41578, 49.01161), 
				new Point(8.41577, 49.01187), 
				new Point(8.41562, 49.01198), 
				new Point(8.41545, 49.01201), 
				new Point(8.41527, 49.01199), 
				new Point(8.41516, 49.01191), 
				new Point(8.41504, 49.01175), 
				new Point(8.41503, 49.0116), 
				new Point(8.41479, 49.01161), 
				new Point(8.41486, 49.01201), 
				new Point(8.41488, 49.01225), 
				new Point(8.41415, 49.01228), 
				new Point(8.41415, 49.01214), 
				new Point(8.41415, 49.01203), 
				new Point(8.41771, 49.01213), 
				new Point(8.41774, 49.01236), 
				new Point(8.41784, 49.01234), 
				new Point(8.41805, 49.01233), 
				new Point(8.41755, 49.01241), 
				new Point(8.4174, 49.01243), 
				new Point(8.41729, 49.01257), 
				new Point(8.41717, 49.01258), 
				new Point(8.41701, 49.01251), 
				new Point(8.41775, 49.01123), 
				new Point(8.41689, 49.01385), 
				new Point(8.41696, 49.01398), 
				new Point(8.41697, 49.01427), 
				new Point(8.41693, 49.01433), 
				new Point(8.41684, 49.01436), 
				new Point(8.41642, 49.01098), 
				new Point(8.41642, 49.01118) };

		MapLoader maploader = new ConcreteMapLoader();
		
		Graph testGraph = maploader.getGraph();
		Point[] testNodes = testGraph.getNodes();
		
		for (int i = 0; i < expectedNodes.length; i++) {
			Assert.assertEquals(expectedNodes[i].getX(), testNodes[i].getX(), 0.0001);
			Assert.assertEquals(expectedNodes[i].getY(), testNodes[i].getY(), 0.0001);
		}
	}
	

	/**
	 * This test tests the method getLandmarks() for correctness.
	 * 
	 * Attention: Works only with the status of the database created by
	 * the SQL Dumps in Google Docs.
	 */
	@Test
	public void testgetLandmarks() {
		Point[] expectedLandmarks = { new Point(8.40484, 49.01428), 
				new Point(8.40693, 49.01118), 
				new Point(8.40943, 49.00884), 
				new Point(8.41423, 49.00875), 
				new Point(8.41954, 49.00849), 
				new Point(8.42377, 49.01339), 
				new Point(8.42218, 49.01581), 
				new Point(8.4125, 49.0159) };

		MapLoader maploader = new ConcreteMapLoader();

		Point[] testLandmarks = maploader.getLandmarks();
		
		for (int i = 0; i < expectedLandmarks.length; i++) {
			Assert.assertEquals(expectedLandmarks[i].getX(), testLandmarks[i].getX(), 0.0001);
			Assert.assertEquals(expectedLandmarks[i].getY(), testLandmarks[i].getY(), 0.0001);
		}
	}

}
