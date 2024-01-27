package hello.hellospring.dto;


import lombok.Data;

@Data
public class CartItemDto {

    private String email;
    private Long pno;
    private int qty;
    private Long cino;
}
