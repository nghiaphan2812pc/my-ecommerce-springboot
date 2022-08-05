package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.GetProductListRequest;
import com.example.ecommerce.dto.ListProductAndTotalPageNumber;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Response;
import com.example.ecommerce.repository.BrandRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private PaginationServiceImpl pagiService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;

    @RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET)
    public Response getProduct(@PathVariable(value = "id") int id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (!optProduct.isPresent()) return new Response(Code.NOT_FOUND_PRODUCT, Message.NOT_FOUND_PRODUCT, null);
        Product product = optProduct.get();
        return new Response(Code.SUCCESS, Message.SUCCESS, product);
    }

    @RequestMapping(value = "/getBrandList", method = RequestMethod.GET)
    public Response getBrandList() {
        return new Response(Code.SUCCESS, Message.SUCCESS, brandRepository.findAll());
    }

    //Mock
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public Response getProductList(@RequestBody GetProductListRequest request) {
        int pageNumber, productNumberPerPage, brandID, minPrice, maxPrice;
        //gan gia tri va set ngoai le
        try{
            pageNumber = request.getPageNumber();
            productNumberPerPage = request.getProductNumberPerPage();
            brandID = request.getBrandID();
            minPrice = request.getMinPrice();
            maxPrice = request.getMaxPrice();
        }catch (Exception e){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        //Tao doi tuong response custom de cho vao response chung
        ListProductAndTotalPageNumber pagination = new ListProductAndTotalPageNumber();
        Page<Product> page = pagiService.findAllProductPagination(pageNumber, productNumberPerPage, brandID, minPrice, maxPrice);
        List<Product> listProducts = page.getContent();
        //Cho vao response custom
        pagination.setTotalPageNumber(page.getTotalPages());
        pagination.setListProduct(listProducts);
        //page hien tai > totalPage
        if (pageNumber > page.getTotalPages() && listProducts.size() != 0) {
            return new Response(Code.NOT_FOUND, Message.NOT_FOUND, null);
        }
        //Cho vao response
        return new Response(Code.SUCCESS, Message.SUCCESS, pagination);
    }
    @RequestMapping(value = "/searchProduct/{key}", method = RequestMethod.GET)
    public Response searchProduct(@PathVariable(value = "key")String key){
        if(key == null || key == ""){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        List<Product> productList = productRepository.findAllByNameContains(key.toLowerCase());
        ListProductAndTotalPageNumber listProductAndTotalPageNumber = new ListProductAndTotalPageNumber(productList,1);
        return new Response(Code.SUCCESS, Message.SUCCESS, listProductAndTotalPageNumber);
    }
}
