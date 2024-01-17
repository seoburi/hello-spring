package hello.hellospring;

import hello.hellospring.domain.Todo;
import hello.hellospring.repository.TodoRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testRead() {
        Long tno = 400L;
        java.util.Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }

    @Test
    void testModify() {
        Long tno = 400L;
        Optional<Todo> result = todoRepository.findById(400L);
        Todo todo = result.orElseThrow();
        todo.changeTitle("modified 400..");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2024, 02 ,01));

        todoRepository.save(todo);
    }

    @Test
    void testDelete() {
        Long tno = 399L;
        todoRepository.deleteById(tno);
    }

    @Test
    void testPaging() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        result.getContent().stream().forEach(todo -> log.info(todo));
    }

    @Test
    public void testinsert() {
        for (int i = 1; i < 100; i++) {
            Todo todo = new Todo("title" + i, "user00", LocalDate.of(2024,01,07));
            todoRepository.save(todo);
        }
    }

    @Test
    void testsearch() {
        List<Todo> result = todoRepository.searchTodoByKeyWord("4");
        log.info(result);
    }
}
