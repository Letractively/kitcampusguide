package edu.kit.pse.ass.realdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.entity.Workplace;

/**
 * This class provides real data for the prototype run
 * 
 * @author Lennart
 * 
 */
public class RealData {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * empty constructor
	 */
	/*
	 * public RealData() { loadAllData(); }
	 */

	/**
	 * Load all data.
	 */
	@Transactional
	public void loadAllData() {
		realFacilities();
		realUsers();
	}

	/**
	 * Adds users to the database
	 */
	@Transactional
	public void realUsers() {
		// 4 letter email part, used as id
		List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe", "bbbf", "bbbg", "bbbh", "bbbi", "bbbj",
				"bbbk", "bbbl", "bbbm");
		if (null != jpaTemplate.find(User.class, "u" + email.get(0) + "@student.kit.edu")) {
			return;
		}
		for (int i = 0; i < email.size(); i++) {
			User u = new User();
			HashSet<String> s = new HashSet<String>();
			s.add("ROLE_STUDENT");
			u.setRoles(s);
			u.setEmail("u" + email.get(i) + "@student.kit.edu");
			u.setPassword(passwordEncoder.encodePassword(email.get(i) + email.get(i), null));
			jpaTemplate.persist(u);
		}
	}

	/**
	 * Adds real facilities to the database.
	 * 
	 */
	@Transactional
	public void realFacilities() {
		ArrayList<Property> emptyList = new ArrayList<Property>();
		// 01 building - Fakultät für Informatik
		ArrayList<Property> buildingProperties = new ArrayList<Property>(Arrays.asList(new Property("WLAN"),
				new Property("Barrierefrei")));
		Building building1 = createPersistedBuilding("Fakultät für Informatik", "50.34", buildingProperties);
		// 01 rooms
		ArrayList<Property> roomPropertiesSR = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("Beamer"), new Property("Tafel"), new Property("Overhead Projektor")));
		ArrayList<Property> roomProperties = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("Whiteboard")));
		ArrayList<Property> atisWorkplaces = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("PC")));
		Room room106 = createPersistedRoom("Seminarraum", "-106", -1, roomPropertiesSR);
		Room room107 = createPersistedRoom("Seminarraum", "-107", -1, roomPropertiesSR);
		Room room108 = createPersistedRoom("Seminarraum", "-108", -1, roomPropertiesSR);
		Room room109 = createPersistedRoom("Seminarraum", "-109", -1, roomPropertiesSR);
		Room room118 = createPersistedRoom("Seminarraum", "-118", -1, roomPropertiesSR);
		Room room119 = createPersistedRoom("Seminarraum", "-119", -1, roomPropertiesSR);
		Room room120 = createPersistedRoom("Seminarraum", "-120", -1, roomPropertiesSR);
		Room room130 = createPersistedRoom("Arbeitsraum", "-130", -1, roomProperties);
		Room room131 = createPersistedRoom("Arbeitsraum", "-131", -1, roomProperties);
		Room room132 = createPersistedRoom("Arbeitsraum", "-132", -1, roomProperties);
		Room room140 = createPersistedRoom("ATIS-Pool 1", "-140", -1, new ArrayList<Property>());
		Room room141 = createPersistedRoom("ATIS-Pool 2", "-141", -1, new ArrayList<Property>());
		Collection<Room> rooms50341 = new ArrayList<Room>(Arrays.asList(room106, room107, room108, room109,
				room118, room119, room120, room130, room131, room132));
		Collection<Room> rooms50342 = new ArrayList<Room>(Arrays.asList(room140, room141));

		// 01 workplaces
		for (Room tmp : rooms50341) {
			for (int j = 1; j < 6; j++) {
				String name = "1." + j;
				tmp.addContainedFacility(createPersistedWorkplace(name, emptyList));
			}
			building1.addContainedFacility(tmp);
		}

		for (Room tmp : rooms50342) {
			for (int i = 1; i < 11; i++) {
				for (int j = 1; j < 6; j++) {
					String name = i + "." + j;
					tmp.addContainedFacility(createPersistedWorkplace(name, atisWorkplaces));
				}
			}
			building1.addContainedFacility(tmp);
		}
		// 02 building - Zähringerhaus
		Building building2 = createPersistedBuilding("Zähringerhaus", "01.85", emptyList);
		ArrayList<Property> mathbibProperties = new ArrayList<Property>(Arrays.asList(new Property("WLAN"),
				new Property("Kopierer")));
		Room roomMathBib = createPersistedRoom("Mathematische Bibliothek", "", 1, mathbibProperties);
		Room roomVorZ1 = createPersistedRoom("Z1 Vorraum", "", 1,
				new ArrayList<Property>(Arrays.asList(new Property("WLAN"))));
		// 02 workplaces
		for (int i = 1; i < 6; i++) {
			for (int j = 1; j < 6; j++) {
				String name = i + "." + j;
				roomMathBib.addContainedFacility(createPersistedWorkplace(name, emptyList));
			}
		}
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 6; j++) {
				String name = i + "." + j;
				roomVorZ1.addContainedFacility(createPersistedWorkplace(name, emptyList));
			}
		}
		building2.addContainedFacility(roomMathBib);
		building2.addContainedFacility(roomVorZ1);

		// 03 building - Bibliothek
		Building building3 = createPersistedBuilding("Bibliothek", "30.50",
				new ArrayList<Property>(Arrays.asList(new Property("WLAN"))));
		ArrayList<Property> bibWorkplaces = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN")));
		Room roomBib1 = createPersistedRoom("Lesesaal 1", "Altbau", 0, bibWorkplaces);
		Room roomBib2 = createPersistedRoom("Lesesaal 2", "Neubau", 1, bibWorkplaces);
		Room roomBib3 = createPersistedRoom("Gruppenraum 1", "Altbau", 1, bibWorkplaces);
		Room roomBib4 = createPersistedRoom("Gruppenraum 2", "Altbau", 1, bibWorkplaces);
		Room roomBib5 = createPersistedRoom("Gruppenraum 3", "Altbau", 1, bibWorkplaces);
		Room roomBib6 = createPersistedRoom("Gruppenraum 4", "Altbau", 1, bibWorkplaces);
		Room roomBib7 = createPersistedRoom("Gruppenraum 5", "Altbau", 1, bibWorkplaces);
		Collection<Room> rooms30502 = new ArrayList<Room>(Arrays.asList(roomBib3, roomBib4, roomBib5, roomBib6,
				roomBib7));
		// 03 workplaces
		for (int j = 1; j < 13; j++) {
			String name = "1." + j;
			roomBib1.addContainedFacility(createPersistedWorkplace(name, emptyList));
		}
		building3.addContainedFacility(roomBib1);
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				String name = i + "." + j;
				roomBib2.addContainedFacility(createPersistedWorkplace(name, emptyList));
			}
		}
		building3.addContainedFacility(roomBib2);
		for (Room tmp : rooms30502) {
			for (int i = 1; i < 3; i++) {
				for (int j = 1; j < 7; j++) {
					String name = i + "." + j;
					tmp.addContainedFacility(createPersistedWorkplace(name, atisWorkplaces));
				}
			}
			building3.addContainedFacility(tmp);
		}
	}

	private Building createPersistedBuilding(String name, String number, ArrayList<Property> properties) {
		Building t = new Building();
		t.setName(name);
		t.setNumber(number);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}

	private Room createPersistedRoom(String name, String number, int level, ArrayList<Property> properties) {
		Room t = new Room();
		t.setName(name);
		t.setNumber(number);
		t.setLevel(level);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}

	private Workplace createPersistedWorkplace(String name, ArrayList<Property> properties) {
		Workplace t = new Workplace();
		t.setName(name);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}
}
