package com.gjk.service.Impl;

import com.gjk.mapper.CategoryMapper;
import com.gjk.pojo.Category;
import com.gjk.pojo.CategoryExample;
import com.gjk.pojo.Product;
import com.gjk.service.CategoryService;
import com.gjk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductService productService;

    @Override
    public List<Category> getCategoryList() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }

    @Override
    public void fillProductFields(Category category) {
        List<Product> products = productService.getProductListByCid(category);
        category.setProducts(products);
    }

    @Override
    public void fillProductFields(List<Category> categories) {
        for(Category c : categories){
            fillProductFields(c);
        }
    }

    @Override
    public void fillProductFieldsByRow(List<Category> categories) {
        int countOfItemOneRow = 7;  //每行放多少个item
        for(Category c : categories){
            List<Product> products = c.getProducts();
            List<List<Product>> productListByRow = new ArrayList<>();
            for(int i = 0; i<products.size(); i+=countOfItemOneRow){
                List<Product> ps = new ArrayList<>();
                int upBounder = Math.min(i+countOfItemOneRow, products.size());
                for(int j = i; j<upBounder; ++j){
                    ps.add(products.get(j));
                }
                productListByRow.add(ps);
            }
            c.setProductsByRow(productListByRow);
        }
    }

    @Override
    public Category getCategoryById(int cid) {
        return categoryMapper.selectByPrimaryKey(cid);
    }
}
