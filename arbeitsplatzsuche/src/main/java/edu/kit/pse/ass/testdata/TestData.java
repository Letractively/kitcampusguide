package edu.kit.pse.ass.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

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

	// Data for dummy buildings, notice that each array needs the same size!
	private static final String[] BUILDING_NAMES = { "Informatikgebäude", "Bibliothek", "Chemiegebäude",
			"Physikgebäude" };
	private static final String[] BUILDING_NUMBERS = { "34.50", "12.10", "80.51", "24.07" };

	// Data for dummy rooms, notice that each array needs the same size!
	private static final String[] ROOM_NAMES = { "Seminarraum", "Lesesaal", "Seminarraum", "Seminarraum",
			"Computerraum", "Abstellraum", "Lesesaal", "Instiutsraum", "Lernraum", "Nebenraum" };
	private static final String[] ROOM_NUMBERS = { "-119", "123", "364", "-234", "-120", "-118", "107", "204",
			"111", "999" };
	private static final int[] ROOM_LEVELS = { -1, 1, 3, -2, -1, -1, 1, 2, 1, 9 };
	private static final String[] ROOM_DESCRIPTIONS = { "Der Seminarraum ist Olivers Lieblingsraum.",
			"Der Lesesaal belegt Sebastian gerne.", "Der Seminarraum wird stets von Lennart gebucht.",
			"Im Seminarraum arbeitet Fabian gerne.", "Der Computerraum ist Andreas zweites Zuhause",
			"Der Abstellraum wird eigentlich nicht benötigt.", "Im Lesesaal ist kein Platz.",
			"In diesem Instiutsraum gibt es Kaffee für umme.",
			"Der Lernraum wird stets von abschreibenden Studenten bevölkert.", "Der Nebenraum ist nie frei." };

	// Data for dummy workplaces
	private static final String[] WORKPLACE_NAMES = { "Nummer1", "Nummer2", "Nummer3", "Nummer4", "Nummer5",
			"Nummer6", "Nummer7", "Nummer8", "Nummer9", "Nummer10", "Nummer11", "Nummer12", "Nummer13",
			"Nummer14", "Nummer15", "Nummer16", "Nummer17", "Nummer18", "Nummer19", "Nummer20", "Number21",
			"Number22", "Nummer23", "Nummer24", "Nummer25", "Nummer26", "Nummer27", "Nummer28", "Nummer29",
			"Nummer30", "Nummer31", "Nummer32", "Nummer33", "Nummer34", "Nummer35", "Nummer36", "Nummer37",
			"Nummer38", "Nummer39" };

	// Data for dummy properties
	private static final String[] PROPERTY_NAMES = { "W-LAN", "LAN", "Licht", "Steckdose", "PC vorhanden",
			"Drucker vorhanden", "Ruhezone", "Essen / Trinken erlaubt", "Beamer", "FlipChart",
			"Tafel / White Board", "Overheadprojektor", "Barrierefrei" };

	/**
	 * Load all data.
	 */
	@Transactional
	public void loadAllData() {
		DummyUsers du = userFillWithDummies();
		DummyFacilities df = facilityFillWithDummies();
		if (du != null) {
			bookingFillWithDummies(df, du);
		}
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
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe", "bbbf", "bbbg", "bbbh", "bbbi", "bbbj",
				"bbbk", "bbbl", "bbbm");
		if (null != jpaTemplate.find(User.class, "u" + email.get(0) + "@student.kit.edu")) {
			return null;
		}
		for (int i = 0; i < email.size(); i++) {
			User u = new User();
			HashSet<String> s = new HashSet<String>();
			s.add("ROLE_STUDENT");
			u.setRoles(s);
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(passwordEncoder.encodePassword(email.get(i) + email.get(i), null));
			jpaTemplate.persist(u);
			dummies.users.add(u);
		}
		return dummies;
	}

	@Transactional
	public List<String> bookingFillWithDummies(DummyFacilities facilities, DummyUsers dummyusers) {
		// the reservations IDs
		List<String> resvIDs = new ArrayList<String>();
		// the available workplaces
		List<Workplace> allWorkplaces = facilities.places;
		// the available users
		List<User> allUsers = dummyusers.users;
		// the start dates
		List<GregorianCalendar> start = Arrays.asList(new GregorianCalendar(2012, 1, 1, 9, 0),
				new GregorianCalendar(2012, 1, 1, 12, 0), new GregorianCalendar(2012, 1, 1, 15, 0));
		// the end dates, all of them after the latest start
		List<GregorianCalendar> end = Arrays.asList(new GregorianCalendar(2012, 1, 1, 11, 0),
				new GregorianCalendar(2012, 1, 1, 14, 0), new GregorianCalendar(2012, 1, 1, 17, 0));
		int i = 0;
		// create reservations and persist them
		int[] sizes = { allWorkplaces.size(), allUsers.size(), start.size(), end.size() };
		// shortest list
		int s = allWorkplaces.size();
		// longest list
		int l = allWorkplaces.size();
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
			Reservation resvTmp = new Reservation(start.get(i).getTime(), end.get(i).getTime(), allUsers.get(i)
					.getEmail());
			resvTmp.addBookedFacilityId(allWorkplaces.get(i).getId());
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

	/**
	 * Creates facility dummies.
	 * 
	 * @return the created facility dummies
	 */
	@Transactional
	public DummyFacilities facilityFillWithDummies() {

		// create dummy facilities
		DummyFacilities df = new DummyFacilities();
		df.buildings = createBuildings(BUILDING_NAMES, BUILDING_NUMBERS);
		df.rooms = createRooms(ROOM_NAMES, ROOM_NUMBERS, ROOM_LEVELS, ROOM_DESCRIPTIONS);
		df.places = createWorkplaces(WORKPLACE_NAMES);

		// create dependencies between buildings<->rooms and rooms<->workplaces
		addDependenciesRandomized(df.buildings, df.rooms);
		addDependenciesRandomized(df.rooms, df.places);

		// add properties to rooms and workplaces
		ArrayList<Property> properties = createProperties(PROPERTY_NAMES);
		addPropertiesRandomized(df.rooms, properties);
		addPropertiesRandomized(df.places, properties);

		return df;
	}

	private void addDependenciesRandomized(ArrayList<? extends Facility> parents,
			ArrayList<? extends Facility> children) {
		int index = 0;
		Random r = new Random();
		int randomEnd = 0;

		for (int i = 0; i < parents.size(); i++) {
			randomEnd = index + r.nextInt(children.size() - index);

			for (int k = index; k < randomEnd; k++) {
				parents.get(i).addContainedFacility(children.get(index));
			}
			index += randomEnd;

		}

	}

	private void addPropertiesRandomized(ArrayList<? extends Facility> facilities, ArrayList<Property> properties) {
		Random r = new Random();
		Property actual;

		for (int i = 0; i < facilities.size(); i++) {

			for (int k = 0; k < properties.size(); k++) {

				actual = properties.get(r.nextInt(properties.size()));
				if (!facilities.get(i).hasProperty(actual)) {
					facilities.get(i).addProperty(actual);
				}

			}

		}
	}

	private ArrayList<Building> createBuildings(String[] names, String[] numbers) {
		ArrayList<Building> buildings = new ArrayList<Building>();
		for (int i = 0; i < names.length; i++) {
			buildings.add(new Building());
			buildings.get(i).setName(names[i]);
			buildings.get(i).setNumber(numbers[i]);
			buildings.get(i).setParentFacility(null);

			jpaTemplate.merge(buildings.get(i));
		}
		return buildings;
	}

	private ArrayList<Room> createRooms(String[] names, String[] numbers, int[] levels, String[] descriptions) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i < names.length; i++) {
			rooms.add(new Room());
			rooms.get(i).setName(names[i]);
			rooms.get(i).setNumber(numbers[i]);
			rooms.get(i).setLevel(i);
			rooms.get(i).setDescription(descriptions[i]);

			jpaTemplate.merge(rooms.get(i));
		}
		return rooms;
	}

	private ArrayList<Workplace> createWorkplaces(String[] names) {
		ArrayList<Workplace> workplaces = new ArrayList<Workplace>();
		for (int i = 0; i < names.length; i++) {
			workplaces.add(new Workplace());
			workplaces.get(i).setName(names[i]);

			jpaTemplate.merge(workplaces.get(i));
		}
		return workplaces;
	}

	private ArrayList<Property> createProperties(String[] names) {
		ArrayList<Property> properties = new ArrayList<Property>();
		for (int i = 0; i < names.length; i++) {
			properties.add(new Property(names[i]));

			jpaTemplate.merge(properties.get(i));
		}
		return properties;
	}

	/** Class for return type of facility creating method. */
	public class DummyFacilities {
		/** the dummy buildings */
		public ArrayList<Building> buildings;
		/** the dummy rooms */
		public ArrayList<Room> rooms;
		/** the dummy workplaces */
		public ArrayList<Workplace> places;
	}

	/** Class for return type of user creating method. */
	public class DummyUsers {
		/** the dummy users */
		public ArrayList<User> users;
	}

	private <T extends Facility> T createPersisted(Class<T> type, String name) {
		T t = null;
		try {
			t = type.newInstance();
			t.setName(name);
			t = jpaTemplate.merge(t);
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return t;
	}
}
