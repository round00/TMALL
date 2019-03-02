package com.gjk.service.Impl;

import com.gjk.mapper.ReviewMapper;
import com.gjk.pojo.Review;
import com.gjk.pojo.ReviewExample;
import com.gjk.pojo.User;
import com.gjk.service.ReviewService;
import com.gjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserService userService;
    @Override
    public int getReviewCount(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        return (int)reviewMapper.countByExample(reviewExample);
    }

    @Override
    public List<Review> getReviewList(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        return reviewMapper.selectByExample(reviewExample);
    }

    @Override
    public void fillUser(List<Review> reviews) {
        for(Review review : reviews){
            User user = userService.getUserById(review.getUid());
            review.setUser(user);
        }
    }

    @Override
    public void add(Review review) {
        reviewMapper.insertSelective(review);
    }
}
