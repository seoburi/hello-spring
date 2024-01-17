package hello.hellospring.repository;

import hello.hellospring.domain.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select t from Todo t where lower(t.title) like lower(concat('%', :keyword, '%'))")
    List<Todo> searchTodoByKeyWord(@Param("keyword") String keyword);
}
