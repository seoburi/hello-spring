package hello.hellospring.security;


import hello.hellospring.domain.Member;
import hello.hellospring.dto.MemberDto;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * CutomUserDetailsService
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("--------------loadUserByUserName---------------------");

        Member member = memberRepository.getWithRoles(username);

        if (member == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        MemberDto memberDto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole ->
                        memberRole.name()).collect(Collectors.toList())
        );
        log.info(memberDto);
        return memberDto;
    }
}
