package edu.kit.cm.kitcampusguide.service.ws.user;

import edu.kit.cm.kitcampusguide.model.Group;
import edu.kit.cm.kitcampusguide.model.Member;

public interface MemberService {

	Member getUser(Object uid);

	void saveUser(Member member);

	Group getGroup(Object uid);

	void saveGroup(Group group);

}
