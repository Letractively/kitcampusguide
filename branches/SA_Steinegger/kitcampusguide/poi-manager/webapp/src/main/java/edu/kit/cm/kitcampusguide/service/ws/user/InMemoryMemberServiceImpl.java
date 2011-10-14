package edu.kit.cm.kitcampusguide.service.user;

import java.util.List;

import edu.kit.cm.kitcampusguide.model.Group;
import edu.kit.cm.kitcampusguide.model.Member;

public class InMemoryMemberServiceImpl implements MemberService {

    private List<Member> users;

    private List<Group> groups;

    public InMemoryMemberServiceImpl() {
        super();
    }

    public InMemoryMemberServiceImpl(List<Member> users, List<Group> groups) {
        super();
        this.users = users;
        this.groups = groups;
    }

    @Override
    public Member getUser(Object uid) {
        Member memberWithCorrectUid = null;
        for (Member member : this.users) {
            if (member.getName().equals(uid)) {
                memberWithCorrectUid = member;
            }
        }
        return memberWithCorrectUid;
    }

    @Override
    public void saveUser(Member member) {
        if (memberIsLoaded(member)) {
            updateMember(member);
        } else {
            addMember(member);
        }
    }

    private boolean memberIsLoaded(Member memberToFind) {
        return getUser(memberToFind.getName()) == null;
    }

    private void updateMember(Member updatedMember) {
        Member oldMember = getUser(updatedMember.getName());
        this.users.remove(oldMember);
        addMember(updatedMember);
    }

    private void addMember(Member member) {
        this.users.add(member);
    }

    @Override
    public Group getGroup(Object uid) {
        Group groupWithCorrectUid = null;
        for (Group group : this.groups) {
            if (group.getName().equals(uid)) {
                groupWithCorrectUid = group;
            }
        }
        return groupWithCorrectUid;
    }

    @Override
    public void saveGroup(Group group) {
        if (groupIsLoaded(group)) {
            updateGroup(group);
        } else {
            addGroup(group);
        }
    }

    private boolean groupIsLoaded(Group groupToFind) {
        return getGroup(groupToFind.getName()) == null;
    }

    private void updateGroup(Group updatedGroup) {
        Group oldGroup = getGroup(updatedGroup.getName());
        this.groups.remove(oldGroup);
        addGroup(updatedGroup);
    }

    private void addGroup(Group group) {
        this.groups.add(group);
    }

    public List<Member> getUsers() {
        return this.users;
    }

    public void setUsers(List<Member> users) {
        this.users = users;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
