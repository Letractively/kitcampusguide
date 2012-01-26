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
	public RealData() {
		loadAllData();
	}

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
		// 01 building
		ArrayList<Property> buildingProperties = new ArrayList<Property>(Arrays.asList(new Property("WLAN"),
				new Property("Barrierefrei")));
		Building building1 = createPersistedBuilding("Fakultät für Informatik", "50.34", buildingProperties);
		// 01 rooms
		ArrayList<Property> roomPropertiesSR = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("Beamer"), new Property("Tafel"), new Property("Overhead Projektor")));
		ArrayList<Property> roomProperties = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("Whiteboard")));
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
		/*
		 * test when connected to GUI instead of testdata
		 * 
		 * for (Room tmp : rooms50341) { int i = 1; for (int j = 0; j < 5; j++) { String name = "1." + i;
		 * tmp.addContainedFacility(createPersistedWorkplace(name)); i++; } building1.addContainedFacility(tmp); }
		 */

		/*
		 * for (Room tmp : rooms50342) { for(int i = 1; i < 11; i++) { for (int j = 1; j < 6; j++) { String name = i+"."
		 * + j; tmp.addContainedFacility(createPersistedWorkplace(name));} }building1.addContainedFacility(tmp); }
		 */
		// -106
		room106.addContainedFacility(createPersistedWorkplace("01.01"));
		room106.addContainedFacility(createPersistedWorkplace("01.02"));
		room106.addContainedFacility(createPersistedWorkplace("01.03"));
		room106.addContainedFacility(createPersistedWorkplace("01.04"));
		room106.addContainedFacility(createPersistedWorkplace("01.05"));
		// -107
		room107.addContainedFacility(createPersistedWorkplace("01.01"));
		room107.addContainedFacility(createPersistedWorkplace("01.02"));
		room107.addContainedFacility(createPersistedWorkplace("01.03"));
		room107.addContainedFacility(createPersistedWorkplace("01.04"));
		room107.addContainedFacility(createPersistedWorkplace("01.05"));
		// -108
		room108.addContainedFacility(createPersistedWorkplace("01.01"));
		room108.addContainedFacility(createPersistedWorkplace("01.02"));
		room108.addContainedFacility(createPersistedWorkplace("01.03"));
		room108.addContainedFacility(createPersistedWorkplace("01.04"));
		room108.addContainedFacility(createPersistedWorkplace("01.05"));
		// -109
		room109.addContainedFacility(createPersistedWorkplace("01.01"));
		room109.addContainedFacility(createPersistedWorkplace("01.02"));
		room109.addContainedFacility(createPersistedWorkplace("01.03"));
		room109.addContainedFacility(createPersistedWorkplace("01.04"));
		room109.addContainedFacility(createPersistedWorkplace("01.05"));
		// -118
		room118.addContainedFacility(createPersistedWorkplace("01.01"));
		room118.addContainedFacility(createPersistedWorkplace("01.02"));
		room118.addContainedFacility(createPersistedWorkplace("01.03"));
		room118.addContainedFacility(createPersistedWorkplace("01.04"));
		room118.addContainedFacility(createPersistedWorkplace("01.05"));
		// -119
		room119.addContainedFacility(createPersistedWorkplace("01.01"));
		room119.addContainedFacility(createPersistedWorkplace("01.02"));
		room119.addContainedFacility(createPersistedWorkplace("01.03"));
		room119.addContainedFacility(createPersistedWorkplace("01.04"));
		room119.addContainedFacility(createPersistedWorkplace("01.05"));
		// -120
		room120.addContainedFacility(createPersistedWorkplace("01.01"));
		room120.addContainedFacility(createPersistedWorkplace("01.02"));
		room120.addContainedFacility(createPersistedWorkplace("01.03"));
		room120.addContainedFacility(createPersistedWorkplace("01.04"));
		room120.addContainedFacility(createPersistedWorkplace("01.05"));
		room120.addContainedFacility(createPersistedWorkplace("01.06"));
		room120.addContainedFacility(createPersistedWorkplace("01.07"));
		// TODO put in for loop adding the workplaces after all are added
		for (Room tmp : rooms50341) {
			building1.addContainedFacility(tmp);
		}
		for (Room tmp : rooms50342) {
			building1.addContainedFacility(tmp);
		}
	}

	private Building createPersistedBuilding(String name, String number, ArrayList<Property> properties) {
		Building t = new Building();
		t.setName(name);
		t.setNumber(number);
		t = jpaTemplate.merge(t);
		return t;
	}

	private Room createPersistedRoom(String name, String number, int level, ArrayList<Property> properties) {
		Room t = new Room();
		t.setName(name);
		t.setNumber(number);
		t.setLevel(level);
		t = jpaTemplate.merge(t);
		return t;
	}

	private Workplace createPersistedWorkplace(String name) {
		Workplace t = new Workplace();
		t.setName(name);
		t = jpaTemplate.merge(t);
		return t;
	}
}
