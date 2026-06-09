package com.ecommerce.service;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse addReview(Long userId, ReviewRequest request);
    List<ReviewResponse> getProductReviews(Long productId);
    void deleteReview(Long userId, Long reviewId);
}
