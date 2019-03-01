package com.gjk.service.Impl;

import com.gjk.mapper.ProductMapper;
import com.gjk.pojo.Category;
import com.gjk.pojo.Product;
import com.gjk.pojo.ProductExample;
import com.gjk.pojo.ProductImage;
import com.gjk.service.ProductImageService;
import com.gjk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductImageService productImageService;
    @Override
    public List<Product> getProductListByCid(Category category) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(category.getId());
        List<Product> products = productMapper.selectByExample(productExample);
        //填充product的Category
        for(Product p : products){
            p.setCategory(category);
            setProductImage(p);
        }
        return products;
    }

    private void setProductImage(Product p){
        List<ProductImage> productImages = productImageService.getProductImageByPid(p.getId());
        p.setFirstProductImage(productImages.get(0));
    }
}
