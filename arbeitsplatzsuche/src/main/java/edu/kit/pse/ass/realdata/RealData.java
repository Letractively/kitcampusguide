package edu.kit.pse.ass.realdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Room;
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

	/**
	 * Load all data.
	 */
	@Transactional
	public void loadAllData() {
		realFacilities();
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
		ArrayList<Property> roomProperties = new ArrayList<Property>(Arrays.asList(new Property("Steckdose"),
				new Property("Beamer"), new Property("Tafel"), new Property("Overhead Projektor")));
		Room room106 = createPersistedRoom("Seminarraum", "-106", -1, roomProperties);
		Room room107 = createPersistedRoom("Seminarraum", "-107", -1, roomProperties);
		Room room108 = createPersistedRoom("Seminarraum", "-108", -1, roomProperties);
		Room room109 = createPersistedRoom("Seminarraum", "-109", -1, roomProperties);
		Room room118 = createPersistedRoom("Seminarraum", "-118", -1, roomProperties);
		Room room119 = createPersistedRoom("Seminarraum", "-119", -1, roomProperties);
		Room room120 = createPersistedRoom("Seminarraum", "-120", -1, roomProperties);
		Collection<Room> rooms01 = new ArrayList<Room>(Arrays.asList(room106, room107, room108, room109, room118,
				room119, room120));

		// 01 workplaces
		/*
		 * test when connected to GUI instead of testdata
		 
		for (Room tmp : rooms01) {
			int i = 1;
			for (int j = 0; j < 5; j++) {
				String name = "01.0" + i;
				tmp.addContainedFacility(createPersistedWorkplace(name));
				i++;
			}
		}*/
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
		for (Room tmp : rooms01) {
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
