package hello.hellospring.service;


import hello.hellospring.domain.Member;
import hello.hellospring.dto.MemberDto;
import hello.hellospring.dto.MemberModifyDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);

    void modifyMember(MemberModifyDto memberModifyDto);

    default MemberDto entityToDto(Member member) {
        MemberDto dto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
        );
        return dto;
    }


}
