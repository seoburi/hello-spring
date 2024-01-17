package hello.hellospring.service;

import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
public interface ProductService {
    PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto);
    Long register(ProductDto productDto);
    ProductDto get(Long pno);

    void modify(ProductDto productDto);

    void remove(Long pno);
}
