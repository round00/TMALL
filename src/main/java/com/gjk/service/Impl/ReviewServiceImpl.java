package com.gjk.service.Impl;

import com.gjk.mapper.ReviewMapper;
import com.gjk.pojo.ReviewExample;
import com.gjk.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Override
    public int getReviewCount(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        return (int)reviewMapper.countByExample(reviewExample);
    }
}
