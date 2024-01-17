package hello.hellospring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_todo")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    private String title;
    private String writer;
    private boolean complete;
    private LocalDate dueDate;

    public Todo(String title, String writer, LocalDate dueDate) {
        this.title = title;
        this.writer = writer;
        this.dueDate = dueDate;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeComplete(boolean conplete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}
