/**
 * This class represents an user.
 */
package edu.kit.pse.ass.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author Sebastian
 */
@Entity(name = "t_user")
public class User implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 79025855355861427L;

	/**
	 * the role of the user, e.g. student, tutor, ...
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<String>();

	/** the email of the user. */
	@Column(nullable = false)
	@Id
	private String email;

	/** the password of the user. */
	private String password;

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
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
	 * Gets the password.
	 * 
	 * @return the password or the stored hash
	 */
	@Override
	public String getPassword() {
		// required even if password hash is stored
		// (to check credentials in Spring Security)
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the password to set
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void setPassword(String password) throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("password must not be null");
		} else if (password.length() < 8) {
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
		Set<String> roles = getRoles();
		if (roles == null) {
			return null;
		}
		Object[] ar = roles.toArray();
		if (ar == null || ar.length == 0 || !(ar instanceof String[])) {
			return null;
		}
		return AuthorityUtils.createAuthorityList((String[]) ar);
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
