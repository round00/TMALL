package com.gjk.service;

import com.gjk.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoryList();
    /**
     * 为每一个category填充product域
     * */
    void fillProductFields(List<Category> categories);
    /**
     * 将每一个分类里的产品分成多行
     * */
    void fillProductFieldsByRow(List<Category> categories);
}
