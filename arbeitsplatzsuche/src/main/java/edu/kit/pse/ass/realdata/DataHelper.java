/**
 * 
 */
package edu.kit.pse.ass.realdata;

import java.util.ArrayList;
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

	public Workplace createPersistedWorkplace(String name, Collection<Property> properties) {
		Workplace t = new Workplace();
		t.setName(name);
		for (Property tmp : properties) {
			t.addProperty(tmp);
		}
		t = jpaTemplate.merge(t);
		return t;
	}

	public User createPersistedUser(String email, String password, HashSet<String> roles) {
		User u = new User();
		u.setRoles(roles);
		u.setEmail(email);
		u.setPassword(passwordEncoder.encodePassword(password, null));
		jpaTemplate.persist(u);
		return u;
	}

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
