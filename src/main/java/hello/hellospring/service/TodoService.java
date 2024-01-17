package hello.hellospring.service;

import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.TodoDto;

import java.util.List;

public interface TodoService {
    Long register(TodoDto todoDto);
    TodoDto get(Long tno);
    void modify(TodoDto todoDto);
    void remove(Long tno);
    List<TodoDto> search(String keyword);
    PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto);
}
