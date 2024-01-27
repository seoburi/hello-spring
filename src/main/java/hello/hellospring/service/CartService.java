package hello.hellospring.service;


import hello.hellospring.dto.CartItemDto;
import hello.hellospring.dto.CartItemListDto;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface CartService {
    //장바구니 아이템 추가 혹은 변경
    public List<CartItemListDto> addOrModify(CartItemDto cartItemDto);

    //모든 장바구니 아이템 목록
    public List<CartItemListDto> getCartItems(String email);

    //아이템 삭제
    public List<CartItemListDto> remove(Long cino);
}
