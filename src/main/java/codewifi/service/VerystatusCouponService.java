package codewifi.service;

import codewifi.request.very.VerystatusCouponListRequest;
import codewifi.response.very.VerystatusCouponListResponse;
import org.springframework.stereotype.Service;


@Service
public interface VerystatusCouponService {
    VerystatusCouponListResponse getList(VerystatusCouponListRequest request);
}
