package edu.kit.pse.ass.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.entity.Workplace;

/**
 * The Class TestData.
 */
public class TestData {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Load all data.
	 */
	@Transactional
	public void loadAllData() {
		userFillWithDummies();
		facilityFillWithDummies();
	}

	/**
	 * User fill with dummies.
	 */
	@Transactional
	public void userFillWithDummies() {
		// TODO Auto-generated method stub
		// 4 letter email part, used as id
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe",
				"bbbf", "bbbg", "bbbh", "bbbi", "bbbj", "bbbk", "bbbl", "bbbm");
		if (null != jpaTemplate.find(User.class, "u" + email.get(0)
				+ "@student.kit.edu")) {
			return;
		}
		for (int i = 0; i < email.size(); i++) {
			User u = new User();
			HashSet<String> s = new HashSet<String>();
			s.add("ROLE_STUDENT");
			u.setRoles(s);
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(passwordEncoder.encodePassword(
					email.get(i) + email.get(i), null));
			jpaTemplate.persist(u);
		}
	}

	@Transactional
	public DummyFacilities facilityFillWithDummies() {
		if (null != jpaTemplate.find(Facility.class, "place1")) {
			return null;
		}
		// TODO create dummy values

		Building build1 = createPersisted(Building.class, "BUILDING#1");
		Building build2 = createPersisted(Building.class, "BUILDING#2");
		build2.setName("Informatikgebäude");
		build2.setNumber("08.15");

		Property prop1 = new Property("WLAN");
		Property prop2 = new Property("Steckdose");
		Property prop3 = new Property("LAN");
		Property prop4 = new Property("Barrierefrei");
		Property prop5 = new Property("Licht");
		Property prop6 = new Property("PC");
		prop1 = jpaTemplate.merge(prop1);
		prop2 = jpaTemplate.merge(prop2);
		prop3 = jpaTemplate.merge(prop3);
		prop4 = jpaTemplate.merge(prop4);
		prop5 = jpaTemplate.merge(prop5);
		prop6 = jpaTemplate.merge(prop6);

		Room facil1 = createPersisted(Room.class, "ID###1");
		Room facil2 = createPersisted(Room.class, "ID###2");
		Room facil3 = createPersisted(Room.class, "ID###3");
		Room facil4 = createPersisted(Room.class, "ID###4");
		Room facil5 = createPersisted(Room.class, "ID###5");

		facil1.setName("Raum 1");
		facil2.setName("Raum 2");
		facil3.setName("Raum 3");
		facil4.setName("Raum 4");
		facil5.setName("Raum 5");

		Workplace place1 = createPersisted(Workplace.class, "place1");
		Workplace place2 = createPersisted(Workplace.class, "place2");
		Workplace place3 = createPersisted(Workplace.class, "place3");

		Workplace place2_1 = createPersisted(Workplace.class, "place2_1");
		Workplace place3_1 = createPersisted(Workplace.class, "place3_1");
		Workplace place3_2 = createPersisted(Workplace.class, "place3_2");
		Workplace place3_3 = createPersisted(Workplace.class, "place3_3");
		Workplace place4_1 = createPersisted(Workplace.class, "place4_1");
		Workplace place4_2 = createPersisted(Workplace.class, "place4_2");
		Workplace place4_3 = createPersisted(Workplace.class, "place4_3");
		Workplace place4_4 = createPersisted(Workplace.class, "place4_4");

		facil1.addProperty(prop1);
		facil1.addContainedFacility(place1);
		facil1.addContainedFacility(place2);
		facil1.addContainedFacility(place3);

		facil2.addProperty(prop2);
		facil2.addContainedFacility(place2_1);

		facil3.addProperty(prop1);
		facil3.addProperty(prop2);
		facil3.addContainedFacility(place3_1);
		facil3.addContainedFacility(place3_2);
		facil3.addContainedFacility(place3_3);

		facil4.addProperty(prop1);
		facil4.addProperty(prop2);
		facil4.addContainedFacility(place4_1);
		facil4.addContainedFacility(place4_2);
		facil4.addContainedFacility(place4_3);
		facil4.addContainedFacility(place4_4);

		facil5.addProperty(prop3);
		facil5.addProperty(prop4);
		facil5.addProperty(prop5);
		facil5.addProperty(prop6);

		build1.addContainedFacility(facil1);
		build1.addContainedFacility(facil2);
		build1.addContainedFacility(facil3);
		build1.addContainedFacility(facil4);
		build1.addProperty(prop1);

		// jpaTemplate.merge(facil1);
		// jpaTemplate.merge(facil2);
		// jpaTemplate.merge(facil3);
		// jpaTemplate.merge(facil4);
		// jpaTemplate.merge(facil5);

		DummyFacilities addedFacilities = new DummyFacilities();

		// building
		addedFacilities.buildings = new ArrayList<Building>();
		addedFacilities.buildings.add(build1);
		addedFacilities.buildings.add(build2);

		// Rooms
		addedFacilities.rooms = new ArrayList<Room>();
		addedFacilities.rooms.add(facil1);
		addedFacilities.rooms.add(facil2);
		addedFacilities.rooms.add(facil3);
		addedFacilities.rooms.add(facil4);
		addedFacilities.rooms.add(facil5);

		// workplaces
		addedFacilities.places = new ArrayList<Workplace>();
		addedFacilities.places.add(place1);
		addedFacilities.places.add(place2);
		addedFacilities.places.add(place3);
		addedFacilities.places.add(place2_1);
		addedFacilities.places.add(place3_1);
		addedFacilities.places.add(place3_2);
		addedFacilities.places.add(place3_3);
		addedFacilities.places.add(place4_1);
		addedFacilities.places.add(place4_2);
		addedFacilities.places.add(place4_3);
		addedFacilities.places.add(place4_4);
		return addedFacilities;
	}

	public class DummyFacilities {
		public ArrayList<Building> buildings;
		public ArrayList<Room> rooms;
		public ArrayList<Workplace> places;
	}

	@SuppressWarnings("deprecation")
	private <T extends Facility> T createPersisted(Class<T> type, String id) {
		T t = null;
		try {
			t = type.newInstance();
			t.setId(id);
			t = jpaTemplate.merge(t);
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return t;
	}
}
