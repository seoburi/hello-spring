package hello.hellospring.repository;

import hello.hellospring.domain.Cart;
import hello.hellospring.domain.CartItem;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Product;
import hello.hellospring.dto.CartItemListDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @Commit
    @Transactional
    void testInsertByProduct() {
        log.info("test1-----------------------");

        //사용자가 전송하는 정보
        String email = "user1@aaa.com";
        Long pno = 5L;
        int qty = 1;

        //만일 기존에 사용자의 장바구니 아이템이 있었다면

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        if (cartItem != null) {
            cartItem.changeQty(cartItem.getQty() + qty);
            cartItemRepository.save(cartItem);
            return;
        }

        //장바구니 아이템이 없었다면 장바구니부터 확인 필요

        //사용자가 장바구니를 만든적이 있는 지 확인
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart = null;

        if (result.isEmpty()) {
            log.info("MemberCart is not exist!!");

            Member member = Member.builder().email(email).build();

            Cart tempCart = Cart.builder().owner(member).build();

            cart = cartRepository.save(tempCart);

        } else {
            cart = result.get();
        }
        log.info(cart);
        //------------------------------------------------------------------
        if (cartItem == null) {
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build();
        }
        cartItemRepository.save(cartItem);
    }

    @Test
    @Commit
    public void 업데이트BycartItemNo() {
        Long cino = 2L;

        int qty = 4;

        Optional<CartItem> result = cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);
    }

    @Test
    void ListOfMemberTest() {
        String email = "user1@aaa.com";

        List<CartItemListDto> cartItemList = cartItemRepository
                .getItemsOfCartDTOByEmail(email);

        for (CartItemListDto dto : cartItemList) {
            log.info(dto);
        }
    }

    @Test
    void testDeleteThenList() {
        Long cino = 3L;

        //장바구니 번호
        Long cno = cartItemRepository.getCartFromItem(cino);

        //삭제는 임시로 주석처리
//        cartItemRepository.deleteById(cino);

        //목록
        List<CartItemListDto> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        cartItemList.stream().forEach(dto -> {
            log.info(dto);
        });
    }
}
