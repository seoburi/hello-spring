package hello.hellospring.controller;


import hello.hellospring.dto.MemberDto;
import hello.hellospring.dto.MemberModifyDto;
import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public MemberDto getMemberFromKakao(@RequestParam("accessToken") String accessToken) {
        log.info("access Token");
        log.info(accessToken);

        return memberService.getKakaoMember(accessToken);
    }

    @PutMapping("/api/member/modify")
    public Map<String, String> modify(@RequestBody MemberModifyDto memberModifyDto) {
        log.info("member modify: " + memberModifyDto);
        memberService.modifyMember(memberModifyDto);

        return Map.of("result", "modified");
    }
}
