/**
 * This class represents an user.
 */
package edu.kit.pse.ass.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Sebastian
 *
 */
@Entity(name = "t_user")
public class User {
	
	/**
	 * the unique id of the user
	 * TODO added an id, because its easier to make unique than email.
	 */
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	private String id;
	
	/**
	 * the role of the user, e.g. student, tutor, ...
	 */
	private String role;
	
	/**
	 * the email of the user
	 */
	private String email;
	
	/**
	 * the password of the user
	 */
	private String password;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("email must be not null");
		}
		//TODO what characters are allowed in a email adress???
		if (email.matches("[a-zA-Z.]+[@]student.kit.edu")) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("#"+email+"# is not a valid email adress");
		}
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		//TODO remove this method -> if password hash is saved
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("password must not be null");
		} else if (password.length() <= 8) {
			throw new IllegalArgumentException("password is too short. min. 8 characters");
		} else {
			//TODO generate password hash???
			this.password = password;
		}	
	}
}
