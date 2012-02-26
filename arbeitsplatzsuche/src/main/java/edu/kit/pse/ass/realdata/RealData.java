package edu.kit.pse.ass.realdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;

/**
 * This class provides real data for the prototype run
 * 
 * @author Lennart
 * 
 */
public class RealData {

	/**
	 * The autowired DataHelper, provides methods for easier persisting
	 */
	@Autowired
	private DataHelper dataHelper;

	/**
	 * ensures users are only added once
	 */
	private static boolean userInserted = false;

	/**
	 * ensure facilities are only added once
	 */
	private static boolean facilitiesInserted = false;

	/**
	 * Load facilities and users to the database
	 */
	@Transactional
	public void loadAllData() {
		realFacilities();
		realUsers();
	}

	/**
	 * Adds users to the database, only students are added. For more users add strings to the list email matching the
	 * pattern: [a-z]{4}
	 */
	@Transactional
	public void realUsers() {
		if (!userInserted) {
			// 4 letter email part, used as id
			List<String> email = Arrays.asList("bbbb", "bbbc", "bbbd", "bbbe", "bbbf", "bbbg", "bbbh", "bbbi",
					"bbbj", "bbbk", "bbbl", "bbbm");
			for (int i = 0; i < email.size(); i++) {
				HashSet<String> s = new HashSet<String>();
				s.add("ROLE_STUDENT");
				dataHelper.createPersistedUser("u" + email.get(i) + "@student.kit.edu",
						email.get(i) + email.get(i), s);

			}
			userInserted = true;
		}

	}

	/**
	 * Adds the real facilities to the database. Each private method adds a building(with contained facilities). For new
	 * buildings create new private methods.
	 * 
	 */
	@Transactional
	public void realFacilities() {
		if (!facilitiesInserted) {
			buildFakultaetInfo();
			buildZaehringerHaus();
			buildBibliothek();
			facilitiesInserted = true;
		}
	}

	/**
	 * Creates the level -1 of the Informatik Fakultät
	 */
	private void buildFakultaetInfo() {
		ArrayList<Property> emptyList = new ArrayList<Property>();
		// 01 building - Fakultät für Informatik
		ArrayList<Property> buildingProperties = new ArrayList<Property>(Arrays.asList(new Property("WLAN"),
				new Property("Barrierefrei")));
		Building building1 = dataHelper.createPersistedBuilding("Fakultät für Informatik", "50.34",
				buildingProperties);
		// 01 rooms
		ArrayList<Property> roomPropertiesSR = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("Beamer"), new Property("Tafel"), new Property("Overhead Projektor")));
		ArrayList<Property> roomProperties = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("Whiteboard")));
		ArrayList<Property> atisWorkplaces = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("PC")));
		Room room106 = dataHelper.createPersistedRoom("Seminarraum", "-106", -1, roomPropertiesSR);
		Room room107 = dataHelper.createPersistedRoom("Seminarraum", "-107", -1, roomPropertiesSR);
		Room room108 = dataHelper.createPersistedRoom("Seminarraum", "-108", -1, roomPropertiesSR);
		Room room109 = dataHelper.createPersistedRoom("Seminarraum", "-109", -1, roomPropertiesSR);
		Room room118 = dataHelper.createPersistedRoom("Seminarraum", "-118", -1, roomPropertiesSR);
		Room room119 = dataHelper.createPersistedRoom("Seminarraum", "-119", -1, roomPropertiesSR);
		Room room120 = dataHelper.createPersistedRoom("Seminarraum", "-120", -1, roomPropertiesSR);
		Room room130 = dataHelper.createPersistedRoom("Arbeitsraum", "-130", -1, roomProperties);
		Room room131 = dataHelper.createPersistedRoom("Arbeitsraum", "-131", -1, roomProperties);
		Room room132 = dataHelper.createPersistedRoom("Arbeitsraum", "-132", -1, roomProperties);
		Room room140 = dataHelper.createPersistedRoom("ATIS-Pool 1", "-140", -1, new ArrayList<Property>());
		Room room141 = dataHelper.createPersistedRoom("ATIS-Pool 2", "-141", -1, new ArrayList<Property>());
		Collection<Room> rooms50341 = new ArrayList<Room>(Arrays.asList(room106, room107, room108, room109,
				room118, room119, room120, room130, room131, room132));
		Collection<Room> rooms50342 = new ArrayList<Room>(Arrays.asList(room140, room141));

		// 01 workplaces
		for (Room tmp : rooms50341) {
			for (int j = 1; j < 6; j++) {
				String name = "1." + j;
				tmp.addContainedFacility(dataHelper.createPersistedWorkplace(name, emptyList));
			}
			building1.addContainedFacility(tmp);
		}

		for (Room tmp : rooms50342) {
			for (int i = 1; i < 11; i++) {
				for (int j = 1; j < 6; j++) {
					String name = i + "." + j;
					tmp.addContainedFacility(dataHelper.createPersistedWorkplace(name, atisWorkplaces));
				}
			}
			building1.addContainedFacility(tmp);
		}
	}

	/**
	 * Creates the Zähringerhaus, only mathematical library and the room in front of Z1.
	 */
	private void buildZaehringerHaus() {
		ArrayList<Property> emptyList = new ArrayList<Property>();
		// 02 building - Zähringerhaus
		Building building2 = dataHelper.createPersistedBuilding("Zähringerhaus", "01.85", emptyList);
		ArrayList<Property> mathbibProperties = new ArrayList<Property>(Arrays.asList(new Property("WLAN"),
				new Property("Kopierer")));
		Room roomMathBib = dataHelper.createPersistedRoom("Mathematische Bibliothek", "", 1, mathbibProperties);
		Room roomVorZ1 = dataHelper.createPersistedRoom("Z1 Vorraum", "", 1,
				new ArrayList<Property>(Arrays.asList(new Property("WLAN"))));
		// 02 workplaces
		for (int i = 1; i < 6; i++) {
			for (int j = 1; j < 6; j++) {
				String name = i + "." + j;
				roomMathBib.addContainedFacility(dataHelper.createPersistedWorkplace(name, emptyList));
			}
		}
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 6; j++) {
				String name = i + "." + j;
				roomVorZ1.addContainedFacility(dataHelper.createPersistedWorkplace(name, emptyList));
			}
		}
		building2.addContainedFacility(roomMathBib);
		building2.addContainedFacility(roomVorZ1);
	}

	/**
	 * Creates the library, only the reading rooms in the old building level 0 and new building level 1, as well as the
	 * group rooms in the first level of the old building.
	 */
	private void buildBibliothek() {
		ArrayList<Property> emptyList = new ArrayList<Property>();
		// 03 building - Bibliothek
		Building building3 = dataHelper.createPersistedBuilding("Bibliothek", "30.50", new ArrayList<Property>(
				Arrays.asList(new Property("WLAN"))));
		ArrayList<Property> bibRooms = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN")));
		ArrayList<Property> bibWorkplaces = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("LAN"), new Property("PC")));
		Room roomBib1 = dataHelper.createPersistedRoom("Lesesaal 1", "Altbau", 0, bibRooms);
		Room roomBib2 = dataHelper.createPersistedRoom("Lesesaal 2", "Neubau", 1, bibRooms);
		Room roomBib3 = dataHelper.createPersistedRoom("Gruppenraum 1", "Altbau", 1, bibRooms);
		Room roomBib4 = dataHelper.createPersistedRoom("Gruppenraum 2", "Altbau", 1, bibRooms);
		Room roomBib5 = dataHelper.createPersistedRoom("Gruppenraum 3", "Altbau", 1, bibRooms);
		Room roomBib6 = dataHelper.createPersistedRoom("Gruppenraum 4", "Altbau", 1, bibRooms);
		Room roomBib7 = dataHelper.createPersistedRoom("Gruppenraum 5", "Altbau", 1, bibRooms);
		Collection<Room> rooms30502 = new ArrayList<Room>(Arrays.asList(roomBib3, roomBib4, roomBib5, roomBib6,
				roomBib7));
		// 03 workplaces
		for (int j = 1; j < 13; j++) {
			String name = "1." + j;
			roomBib1.addContainedFacility(dataHelper.createPersistedWorkplace(name, emptyList));
		}
		building3.addContainedFacility(roomBib1);
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				String name = i + "." + j;
				roomBib2.addContainedFacility(dataHelper.createPersistedWorkplace(name, emptyList));
			}
		}
		building3.addContainedFacility(roomBib2);
		for (Room tmp : rooms30502) {
			for (int i = 1; i < 3; i++) {
				for (int j = 1; j < 7; j++) {
					String name = i + "." + j;
					tmp.addContainedFacility(dataHelper.createPersistedWorkplace(name, bibWorkplaces));
				}
			}
			building3.addContainedFacility(tmp);
		}
	}
}
