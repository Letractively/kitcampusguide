package edu.kit.cm.kitcampusguide.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A group has several members that have a specific role in the group.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class Group {

    private final List<MemberToGroupMapping> memberMappings = new ArrayList<MemberToGroupMapping>();

    private String name = null;

    public List<MemberToGroupMapping> getMemberMappings() {

        return new ArrayList<MemberToGroupMapping>(this.memberMappings);
    }

    public void setMemberMappings(List<MemberToGroupMapping> members) {
        for (MemberToGroupMapping memberMapping : new ArrayList<MemberToGroupMapping>(this.memberMappings)) {
            removeMemberMapping(memberMapping);
        }
        this.memberMappings.clear();
        for (MemberToGroupMapping memberMapping : this.memberMappings) {
            addMemberMapping(memberMapping);
        }
    }

    public String getId() {

        return this.name;
    }

    public void setId(String id) {

        this.name = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void addMemberMapping(MemberToGroupMapping memberMapping) {

        if (!this.memberMappings.contains(memberMapping)) {
            this.memberMappings.add(memberMapping);
            if (memberMapping.getMember() != null) {
                memberMapping.getMember().addGroupMapping(memberMapping);
            }
        }
    }

    public void removeMemberMapping(MemberToGroupMapping memberMapping) {

        if (this.memberMappings.contains(memberMapping)) {
            this.memberMappings.remove(memberMapping);
            if (memberMapping.getMember() != null) {
                memberMapping.getMember().removeGroupMappingFor(this);
            }
        }
    }

    public void removeMemberMappingFor(Member member) {

        for (MemberToGroupMapping mapping : new ArrayList<MemberToGroupMapping>(this.memberMappings)) {
            if (mapping.getMember().equals(member)) {
                this.memberMappings.remove(mapping);
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
        } else if (obj instanceof Group && ((Group) obj).getName().equals(this.name)) {
            equality = true;
        } else {
            equality = false;
        }
        return equality;
    }
}
