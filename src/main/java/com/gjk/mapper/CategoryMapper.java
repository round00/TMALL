package com.gjk.mapper;

import com.gjk.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    /**
     * 获取全部category列表
     * */
    List<Category> getCategoryList();
}
