package hello.hellospring;

import hello.hellospring.domain.Product;
import hello.hellospring.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testInsert() {
        for (int i = 10; i < 20; i++) {
            Product product = Product.builder()
                    .pname("상품" + i)
                    .price(100 * i)
                    .pdesc("상품설명" + i)
                    .build();
            //2개의 이미지 파일 추가
            product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE1.jpg");
            product.addImageString(UUID.randomUUID().toString() + "_" + "IMAGE2.jpg");
            productRepository.save(product);
            log.info("--------------------------");
        }
    }

    @Test
    @Transactional
    void testRead() {
        Long pno =  25L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    @Test
    void testRead2() {
        Long pno = 33L;

        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    void testDelete(){
        Long pno = 2L;

        productRepository.updateToDelete(pno, true);
    }

    @Test
    void testUpdate() {
        Long pno = 10L;

        Product product = productRepository.selectOne(pno).get();

        product.changeName("modified 10번 상품");
        product.changeDesc("modified 10번 상품 설명");
        product.changePrice(50000);

        //첨부파일 수정
        product.clearList();
        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE1.PNG");
        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE2.PNG");
        product.addImageString(UUID.randomUUID().toString() + "_" + "NEWIMAGE3.PNG");

        productRepository.save(product);
    }
    @Test
    void testList() {
        //org.springframework.data.domain 패키지
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);

        //java.util
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

}
