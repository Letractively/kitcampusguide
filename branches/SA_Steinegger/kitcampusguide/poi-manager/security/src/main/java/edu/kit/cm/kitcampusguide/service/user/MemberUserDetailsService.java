package edu.kit.cm.kitcampusguide.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.kit.cm.kitcampusguide.model.Member;

/**
 * UserDetailsService that loads member data from member service and combines it
 * with authentication information.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class MemberUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<Authentication> {

    @Autowired
    private MemberService memberService;

    private static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_USER");

    public MemberUserDetailsService() {
        super();
    }

    @Override
    public MemberUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member currentMember = this.memberService.getUser(name);

        return new MemberUserDetails(currentMember, MemberUserDetailsService.DEFAULT_AUTHORITIES);
    }

    @Override
    public MemberUserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {

        return new MemberUserDetails(this.memberService.getUser(token.getName()), token.getAuthorities());
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}
