/**
 * This class represents an user.
 */
package edu.kit.pse.ass.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Sebastian
 * 
 */
@Entity(name = "t_user")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 79025855365861427L;

	/**
	 * the unique id of the user TODO added an id, because its easier to make
	 * unique than email.
	 */
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	private String id;

	/**
	 * the role of the user, e.g. student, tutor, ...
	 */
	private Set<String> roles;

	/**
	 * the email of the user
	 */
	@Column(unique = true, nullable = false)
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
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param role
	 *            the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("email must be not null");
		}
		// The model must not restrict the use of specific mail addresses.
		// TODO what characters are allowed in a email address???
		if (email.matches("u[a-z]{4}@student.kit.edu")) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("#" + email
					+ "# is not a valid email adress");
		}
	}

	/**
	 * @return the password or the stored hash
	 */
	@Override
	public String getPassword() {
		// required even if password hash is stored
		// (to check credentials in Spring Security)
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("password must not be null");
		} else if (password.length() <= 8) {
			throw new IllegalArgumentException(
					"password is too short. min. 8 characters");
		} else {
			// TODO generate password hash???
			this.password = password;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities
	 * ()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		if (roles == null) {
			return null;
		}
		return AuthorityUtils.createAuthorityList((String[]) roles.toArray());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked
	 * ()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}