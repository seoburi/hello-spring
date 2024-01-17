package hello.hellospring;


import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.ProductDto;
import hello.hellospring.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void testList() {
        //1 page, 10 size
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .build();

        PageResponseDto<ProductDto> result = productService.getList(pageRequestDto);
        result.getDtoList().forEach(dto -> log.info(dto));
    }
    @Test
    void testRegister() {
        ProductDto productDto = ProductDto.builder()
                .pname("new")
                .pdesc("new product")
                .price(1000)
                .build();
        //UUID가 있어야 함
        productDto.setUploadFileNames(
                List.of(UUID.randomUUID() + "_" + "TEST1.jpg", UUID.randomUUID() + "_" + "TEST2.jpg")
        );

        productService.register(productDto);
    }
    @Test
    void testRead() {
        Long pno = 13L;

        ProductDto productDto = productService.get(pno);

        log.info(productDto);
        log.info(productDto.getUploadFileNames());
    }

    @Test
    void modifyTest() {
        ProductDto productDto = ProductDto.builder()
                .pno(28L)
                .pname("new")
                .pdesc("new product")
                .price(1000)
                .uploadFileNames(Collections.singletonList("weird string"))
                .build();
        productService.modify(productDto);
        ProductDto productDto1 = productService.get(28L);
        log.info(productDto1);
    }

}

