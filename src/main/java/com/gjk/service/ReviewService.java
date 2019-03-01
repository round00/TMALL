package com.gjk.service;

public interface ReviewService {
    /**
     * 获得指定产品的评论数
     * */
    int getReviewCount(int pid);
}
