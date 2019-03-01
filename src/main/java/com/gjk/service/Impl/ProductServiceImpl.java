package com.gjk.service.Impl;

import com.gjk.mapper.CategoryMapper;
import com.gjk.mapper.ProductMapper;
import com.gjk.pojo.*;
import com.gjk.service.OrderItemService;
import com.gjk.service.ProductImageService;
import com.gjk.service.ProductService;
import com.gjk.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public List<Product> getProductListByCid(Category category) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(category.getId());
        List<Product> products = productMapper.selectByExample(productExample);
        //填充product的Category
        for(Product p : products){
            p.setCategory(category);
            setFirstProductImage(p);
            setSaleAndReviewCount(p);
        }
        return products;
    }

    @Override
    public Product getProductByPid(int pid) {
        Product product = productMapper.selectByPrimaryKey(pid);
        if(product == null){
            return null;
        }
        Category category = categoryMapper.selectByPrimaryKey(product.getCid());
        product.setCategory(category);
        return product;
    }

    @Override
    public void setProductImages(Product p) {
        List<ProductImage> productSingleImages =
                productImageService.getProductImages(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages =
                productImageService.getProductImages(p.getId(), ProductImageService.type_detail);
        p.setFirstProductImage(productSingleImages.get(0));
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> productImages =
                productImageService.getProductImages(p.getId(), ProductImageService.type_single);
        p.setFirstProductImage(productImages.get(0));
    }

    @Override
    public void setSaleAndReviewCount(Product p) {
        int saleCnt = orderItemService.getSaleCount(p.getId());
        int reviewCnt = reviewService.getReviewCount(p.getId());

        p.setSaleCount(saleCnt);
        p.setReviewCount(reviewCnt);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%"+keyword+"%");
        List<Product> products = productMapper.selectByExample(productExample);
        for (Product p : products){
            setFirstProductImage(p);
            setSaleAndReviewCount(p);
        }
        return products;
    }
}
