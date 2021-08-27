package com.igorivkin.creditconveyor.requestfilterservice.service;

import com.igorivkin.creditconveyor.requestfilterservice.model.rabbit.CreditRequest;
import com.igorivkin.creditconveyor.requestfilterservice.model.redis.RequestResult;
import com.igorivkin.creditconveyor.requestfilterservice.model.redis.RequestResultType;
import com.igorivkin.creditconveyor.requestfilterservice.repository.RequestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestProcessingService {
    private final RequestResultRepository requestResultRepository;

    @Autowired
    public RequestProcessingService(RequestResultRepository requestResultRepository) {
        this.requestResultRepository = requestResultRepository;
    }

    /**
     * Отклоняет поданную заявку, сохраняя ее со статусом "Отклонено".
     *
     * @param request заявка на кредит
     */
    public void rejectRequest(CreditRequest request) {
        RequestResult requestResult = RequestResult.builder()
                .uuid(request.getUuid())
                .result(RequestResultType.REJECTED)
                .build();
        requestResultRepository.save(requestResult);
    }
}
