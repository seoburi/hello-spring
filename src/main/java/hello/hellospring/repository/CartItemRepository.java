package hello.hellospring.repository;


import hello.hellospring.domain.CartItem;
import hello.hellospring.dto.CartItemListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //Projection을 이용해서 Repository에서 직접 Dto를 반환
    @Query("select " +
            " new hello.hellospring.dto.CartItemListDto(ci.cino,  ci.qty,  p.pno, p.pname, p.price , pi.fileName )  " +
            " from " +
            "   CartItem ci inner join Cart mc on ci.cart = mc " +
            "   left join Product p on ci.product = p " +
            "   left join p.imageList pi" +
            " where " +
            "   mc.owner.email = :email and pi.ord = 0 " +
            " order by ci.cino desc ")
    public List<CartItemListDto> getItemsOfCartDTOByEmail(@Param("email") String email);

    @Query("select" +
            " ci "+
            " from " +
            "   CartItem ci inner join Cart c on ci.cart = c " +
            " where " +
            "   c.owner.email = :email and ci.product.pno = :pno")
    public CartItem getItemOfPno(@Param("email") String email, @Param("pno") Long pno );

    @Query("select " +
            "  c.id " +
            "from " +
            "  Cart c inner join CartItem ci on ci.cart = c " +
            " where " +
            "  ci.cino = :cino")
    public Long getCartFromItem( @Param("cino") Long cino);

    @Query("select new hello.hellospring.dto.CartItemListDto(ci.cino,  ci.qty,  p.pno, p.pname, p.price , pi.fileName )  " +
            " from " +
            "   CartItem ci inner join Cart mc on ci.cart = mc " +
            "   left join Product p on ci.product = p " +
            "   left join p.imageList pi" +
            " where " +
            "  mc.id = :cno and pi.ord = 0 " +
            " order by ci.cino desc ")
    public List<CartItemListDto> getItemsOfCartDTOByCart(@Param("cno") Long cno);

}
