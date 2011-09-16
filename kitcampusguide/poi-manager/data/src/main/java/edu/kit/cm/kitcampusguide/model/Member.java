package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A member can be in several groups and has a role in each of them.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class Member {

    private final List<MemberToGroupMapping> groupMapping = new ArrayList<MemberToGroupMapping>();

    private String name = null;

    private String password = null;

    public Member() {
        // default constructor for spring bean creation
    }

    public Member(Member member) {
        this.name = member.name;
        this.password = member.password;
        this.groupMapping.addAll(member.groupMapping);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setGroupMappings(List<MemberToGroupMapping> groupMappings) {

        for (MemberToGroupMapping groupMapping : this.groupMapping) {
            removeGroupMapping(groupMapping);
        }
        this.groupMapping.clear();
        for (MemberToGroupMapping groupMapping : groupMappings) {
            addGroupMapping(groupMapping);
        }
    }

    public List<MemberToGroupMapping> getGroupMappings() {
        return new ArrayList<MemberToGroupMapping>(this.groupMapping);
    }

    public void addGroupMapping(MemberToGroupMapping groupMapping) {

        if (!this.groupMapping.contains(groupMapping)) {
            this.groupMapping.add(groupMapping);
            if (groupMapping.getGroup() != null) {
                groupMapping.getGroup().addMemberMapping(groupMapping);
            }
        }
    }

    public void removeGroupMapping(MemberToGroupMapping groupMapping) {

        if (this.groupMapping.contains(groupMapping)) {
            this.groupMapping.remove(groupMapping);
            if (groupMapping.getGroup() != null) {
                groupMapping.getGroup().removeMemberMappingFor(this);
            }
        }
    }

    public void removeGroupMappingFor(Group group) {

        for (MemberToGroupMapping mapping : new ArrayList<MemberToGroupMapping>(this.groupMapping)) {
            if (mapping.getGroup().equals(group)) {
                this.groupMapping.remove(group);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean equality;
        if (obj == null) {
            equality = false;
        } else if (super.equals(obj)) {
            equality = true;
        } else if (obj instanceof Member && ((Member) obj).getName().equals(this.name)) {
            equality = true;
        } else {
            equality = false;
        }
        return equality;
    }
}
