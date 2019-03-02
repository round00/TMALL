package com.gjk.service;

import com.gjk.pojo.Review;

import java.util.List;

public interface ReviewService {
    /**
     * 获得指定产品的评论数
     * */
    int getReviewCount(int pid);
    /**
     * 获得指定产品的评论列表
     * */
    List<Review> getReviewList(int pid);
    /**
     * 填充User字段
     * */
    void fillUser(List<Review> reviews);

    void add(Review review);

}
