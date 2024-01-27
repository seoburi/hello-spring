package hello.hellospring.controller;


import hello.hellospring.dto.CartItemDto;
import hello.hellospring.dto.CartItemListDto;
import hello.hellospring.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    //    @PreAuthorize("#cartItemDto.email == authentication.name")
    @PostMapping("/change")
    public List<CartItemListDto> changeCart(@RequestBody CartItemDto cartItemDto) {
        log.info(cartItemDto);

        if (cartItemDto.getQty() <= 0) {
            return cartService.remove(cartItemDto.getCino());
        }
        return cartService.addOrModify(cartItemDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/items")
    public List<CartItemListDto> getCartItems(Principal principal) {
        String email = principal.getName();
        log.info("----------------------------------------");
        log.info("email: " + email);

        return cartService.getCartItems(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDto> removeFromCart(@PathVariable("cino") Long cino) {
        log.info("cart item no : " + cino);

        return cartService.remove(cino);
    }
}
