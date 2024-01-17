package hello.hellospring.controller;

import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.TodoDto;
import hello.hellospring.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService service;

    @GetMapping("/{tno}")
    public TodoDto get(@PathVariable(name = "tno") Long tno) {
        return service.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {
        log.info(pageRequestDto);
        return service.list(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDto todoDto) {
        log.info("TodoDto : " + todoDto);
        Long tno = service.register(todoDto);
        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable(name = "tno") Long tno, @RequestBody TodoDto todoDto) {
        todoDto.setTno(tno);
        log.info("TodoDto: " + todoDto);
        service.modify(todoDto);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable(name = "tno") Long tno) {
        log.info("Remove : " + tno);
        service.remove(tno);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/search")
    public List<TodoDto> search(@RequestParam("keywords") String keywords) {
        return service.search(keywords);
    }
}
