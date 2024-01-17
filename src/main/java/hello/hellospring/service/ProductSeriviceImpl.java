package hello.hellospring.service;

import hello.hellospring.domain.Product;
import hello.hellospring.domain.ProductImage;
import hello.hellospring.dto.PageRequestDto;
import hello.hellospring.dto.PageResponseDto;
import hello.hellospring.dto.ProductDto;
import hello.hellospring.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductSeriviceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto) {
        log.info("getlist.............");
        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDto> dtoList = result.get().map(arr -> {
            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            ProductDto productDto = ProductDto.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .price(product.getPrice())
                    .pdesc(product.getPdesc())
                    .build();

            String imageStr = productImage.getFileName();
            productDto.setUploadFileNames(List.of(imageStr));
            return productDto;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDto.<ProductDto>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDto(pageRequestDto)
                .build();

    }

    @Override
    public Long register(ProductDto productDto) {
        Product product = dtoToEntity(productDto);

        Product result = productRepository.save(product);

        return result.getPno();
    }

    @Override
    public ProductDto get(Long pno) {

        Product product = productRepository.selectOne(pno).get();
        ProductDto productDto = entityToDto(product);
        return productDto;
    }

    @Override
    public void modify(ProductDto productDto) {
        //step1 read
        Product product = productRepository.selectOne(productDto.getPno()).get();

        //change name, pdesc, price
        product.changeName(productDto.getPname());
        product.changePrice(productDto.getPrice());
        product.changeDesc(productDto.getPdesc());

        //upload File -- clear first
        product.clearList();

        List<String> uploadFileNames = productDto.getUploadFileNames();

        if (uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(uploadName -> {
                product.addImageString(uploadName);
            });
        }

        productRepository.save(product);
    }

    @Override
    public void remove(Long pno) {
        productRepository.updateToDelete(pno, true);
    }

    private Product dtoToEntity(ProductDto productDto) {
        Product product = Product.builder()
                .pno(productDto.getPno())
                .pname(productDto.getPname())
                .pdesc(productDto.getPdesc())
                .price(productDto.getPrice())
                .build();

        List<String> uploadFileNames = productDto.getUploadFileNames();
        if (uploadFileNames == null) {
            return product;
        }

        uploadFileNames.stream().forEach(uploadName -> {
            product.addImageString(uploadName);
        });
        return product;
    }

    private ProductDto entityToDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .build();
        List<ProductImage> imageList = product.getImageList();

        if (imageList == null || imageList.size() == 0) {
            return productDto;
        }
        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        productDto.setUploadFileNames(fileNameList);
        return productDto;
    }
}
