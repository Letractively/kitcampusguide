/**
 * 
 */
package edu.kit.pse.ass.realdata;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import edu.kit.pse.ass.entity.Building;
import edu.kit.pse.ass.entity.Property;
import edu.kit.pse.ass.entity.Reservation;
import edu.kit.pse.ass.entity.Room;
import edu.kit.pse.ass.entity.User;
import edu.kit.pse.ass.entity.Workplace;

/**
 * @author Oliver Schneider Just to insert test data into db
 */
public class DataHelper {

	/** The jpa template. */
	@Autowired
	private JpaTemplate jpaTemplate;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Add a building to the db with following attributes:
	 * 
	 * @param name
	 *            - the name of the building (e.g. "Informatik Fakultät")
	 * @param number
	 *            - the number of the building (e.g. 50.34)
	 * @param properties
	 *            - a Collection of properties the building owns
	 * @return the persisted building
	 */
	public Building createPersistedBuilding(String name, String number, Collection<Property> properties) {
		Building t = new Building();
		t.setName(name);
		t.setNumber(number);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}

	/**
	 * Add a room to the db with following attributes:
	 * 
	 * @param name
	 *            - the name of the room (e.g. "Seminarraum")
	 * @param number
	 *            - the number of the room (e.g. "-118")
	 * 
	 * @param level
	 *            - the level of the room (e.g. "-1")
	 * @param properties
	 *            - a Collection of properties the room owns
	 * @return the persisted room
	 */
	public Room createPersistedRoom(String name, String number, int level, Collection<Property> properties) {
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

	/**
	 * Add a workplace to the db with following attributes:
	 * 
	 * @param name
	 *            - the name of the workplace (e.g. "1.5", desk 1 place 5)
	 * @param properties
	 *            - a Collection of properties the workplace owns
	 * @return the persisted room
	 */
	public Workplace createPersistedWorkplace(String name, Collection<Property> properties) {
		Workplace t = new Workplace();
		t.setName(name);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}

	/**
	 * Add a user to the db with following attributes:
	 * 
	 * @param email
	 *            - the email of the user (must match: "u[a-z]{4}@student.kit.edu")
	 * @param password
	 *            - the password for the account (must be 8 characters long)
	 * @param roles
	 *            - the roles the users holds
	 * @return the persisted user
	 */
	public User createPersistedUser(String email, String password, HashSet<String> roles) {
		User u = new User();
		u.setRoles(roles);
		u.setEmail(email);
		u.setPassword(passwordEncoder.encodePassword(password, null));
		jpaTemplate.persist(u);
		return u;
	}

	/**
	 * Add a reservation to the db with following attributes:
	 * 
	 * @param userID
	 *            - the id of the reserving user
	 * @param facilityIDs
	 *            - the ids of the reserved facilities
	 * @param start
	 *            - start date of the reservation
	 * @param end
	 *            - end date of the reservation
	 * @return the persisted reservation
	 */
	public Reservation createPersistedReservation(String userID, Collection<String> facilityIDs, Date start,
			Date end) {
		Reservation r = new Reservation();
		r.setBookingUserId(userID);
		r.setBookedFacilityIDs(facilityIDs);
		r.setStartTime(start);
		r.setEndTime(end);
		r = jpaTemplate.merge(r);
		return r;
	}
}
