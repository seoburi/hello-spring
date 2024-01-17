package hello.hellospring;


import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.TodoDto;
import hello.hellospring.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class TodoServiceTests {

    @Autowired
    private TodoService todoService;

    @Test
    void testRegister() {
        TodoDto todoDto = TodoDto.builder()
                .title("서비스 테스트")
                .writer("tester")
                .dueDate(LocalDate.of(2024, 1, 8))
                .build();
        Long tno = todoService.register(todoDto);
        log.info("TNO: " + tno);
    }

    @Test
    void testGet() {
        Long tno = 400L;
        TodoDto todoDto = todoService.get(tno);
        log.info(todoDto);
    }

    @Test
    void testList() {

        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(2)
                .size(10)
                .build();
        PageResponseDto<TodoDto> response = todoService.list(pageRequestDto);

        log.info(response);
    }
}
