package edu.kit.pse.ass.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Facility;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
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
	public DummyUsers userFillWithDummies() {
		// initialize the return
		DummyUsers dummies = new DummyUsers();
		dummies.users = new ArrayList<User>();
		// 4 letter email part, used as id
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe",
				"bbbf", "bbbg", "bbbh", "bbbi", "bbbj", "bbbk", "bbbl", "bbbm");
		if (null != jpaTemplate.find(User.class, "u" + email.get(0)
				+ "@student.kit.edu")) {
			return null;
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
			dummies.users.add(u);
		}
		return dummies;
	}

	@Transactional
	public DummyFacilities facilityFillWithDummies() {
		if (null != jpaTemplate.find(Facility.class, "place1")) {
			return null;
		}
		// TODO create dummy values

		Building build1 = createPersisted(Building.class, "BUILDING#1");
		build1.setName("Fakultät für Informatik");
		build1.setNumber("50.34");
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
		// contains only dummy properties
		facil5.setName("Raum 5");

		facil1.setDescription("Informatik");
		facil2.setDescription("Informatik");
		facil3.setDescription("Informatik");

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
		build1.addContainedFacility(facil5);
		build1.addProperty(prop1);

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

	@Transactional
	public List<String> bookingFillWithDummies(DummyFacilities facilities,
			DummyUsers dummyusers) {
		// the reservations IDs
		List<String> resvIDs = new ArrayList<String>();
		// the available rooms
		List<Room> allRooms = facilities.rooms;
		// the available users
		List<User> allUsers = dummyusers.users;
		// the start dates
		List<GregorianCalendar> start = Arrays.asList(new GregorianCalendar(
				2012, 1, 1, 9, 0), new GregorianCalendar(2012, 1, 1, 12, 0),
				new GregorianCalendar(2012, 1, 1, 15, 0));
		// the end dates, all of them after the latest start
		List<GregorianCalendar> end = Arrays.asList(new GregorianCalendar(2012,
				1, 1, 11, 0), new GregorianCalendar(2012, 1, 1, 14, 0),
				new GregorianCalendar(2012, 1, 1, 17, 0));
		int i = 0;
		// create reservations and persist them
		int[] sizes = { allRooms.size(), allUsers.size(), start.size(),
				end.size() };
		// shortest list
		int s = allRooms.size();
		// longest list
		int l = allRooms.size();
		// get size of shortest and longest list
		for (int j : sizes) {
			if (j < s) {
				s = j;
			}
			if (j > l) {
				l = j;
			}
		}
		while (resvIDs.size() < l) {
			// makes the lists size independent
			i %= s;
			Reservation resvTmp = new Reservation(start.get(i).getTime(), end
					.get(i).getTime(), allUsers.get(i).getEmail());
			resvTmp.addBookedFacilityId(allRooms.get(i).getId());
			jpaTemplate.merge(resvTmp);
			resvIDs.add(resvTmp.getId());
			// adds a day to each start and end, thus preventing double
			// reservations
			start.get(i).add(Calendar.DATE, 1);
			end.get(i).add(Calendar.DATE, 1);
			i++;
		}
		return resvIDs;
	}

	public class DummyFacilities {
		public ArrayList<Building> buildings;
		public ArrayList<Room> rooms;
		public ArrayList<Workplace> places;
	}

	public class DummyUsers {
		public ArrayList<User> users;
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
