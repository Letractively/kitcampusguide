package edu.kit.cm.kitcampusguide.model;

/**
 * A mapping from a member to a group an vice versa.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class MemberToGroupMapping {

	private Member member = null;
	private Group group = null;
	private String role = null;

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
