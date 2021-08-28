package com.igorivkin.creditconveyor.requestfilterservice.service;

import com.igorivkin.creditconveyor.requestfilterservice.client.AgreementGeneratorClient;
import com.igorivkin.creditconveyor.requestfilterservice.model.rabbit.CreditRequest;
import com.igorivkin.creditconveyor.requestfilterservice.model.redis.RequestResult;
import com.igorivkin.creditconveyor.requestfilterservice.model.redis.RequestResultType;
import com.igorivkin.creditconveyor.requestfilterservice.model.web.AgreementGeneratorResponse;
import com.igorivkin.creditconveyor.requestfilterservice.repository.RequestResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RequestProcessingService {

    private final RequestResultRepository requestResultRepository;
    private final AgreementGeneratorClient agreementGeneratorClient;

    @Autowired
    public RequestProcessingService(RequestResultRepository requestResultRepository,
                                    AgreementGeneratorClient agreementGeneratorClient) {
        this.requestResultRepository = requestResultRepository;
        this.agreementGeneratorClient = agreementGeneratorClient;
    }

    /**
     * Проверяет переданную заявку, отклоняя ее, если она не соответствует фильтрам.
     *
     * @param request заявка на кредит
     */
    public void processRequest(CreditRequest request) {
        if (!isRequestValid(request)) {
            rejectRequest(request);
        } else {
            approveRequest(request);
        }
    }

    /**
     * Отклоняет поданную заявку, сохраняя ее со статусом "Отклонено".
     *
     * @param request заявка на кредит
     */
    public void rejectRequest(CreditRequest request) {
        log.info("Request is rejected: {}", request);
        RequestResult requestResult = RequestResult.builder()
                .uuid(request.getUuid())
                .result(RequestResultType.REJECTED)
                .build();
        requestResultRepository.save(requestResult);
    }

    /**
     * Подтверждает поданную заявку на кредит. Передаёт заявку в сервис подготовки первичной документации.
     *
     * @param request заявка на кредит
     */
    public void approveRequest(CreditRequest request) {
        log.info("Request is approved: {}", request);

        AgreementGeneratorResponse agreementGeneratorResponse = agreementGeneratorClient.generateAgreement(request);
        log.info("Response from PDF-generator received: {}", agreementGeneratorResponse);

        RequestResult requestResult = RequestResult.builder()
                .uuid(request.getUuid())
                .result(RequestResultType.APPROVED)
                .approvedAmount(request.getRequestedAmount())
                .filename(agreementGeneratorResponse.getFilename())
                .build();
        requestResultRepository.save(requestResult);
    }

    /**
     * Проверяет заявку на кредит на соответствие фильтрам.
     *
     * @param request заявка на кредит
     * @return true, если заявка прошла фильтры
     */
    private boolean isRequestValid(CreditRequest request) {
        Long salary = Optional.ofNullable(request.getSalary()).orElse(0L);
        Long requestedAmount = Optional.ofNullable(request.getRequestedAmount()).orElse(0L);
        int age = Optional.ofNullable(request.getAge()).orElse(0);
        int months = Optional.ofNullable(request.getMonths()).orElse(0);

        return isCorrectAge(age) && hasHalfOfSalaryAfterCreditPayment(salary, requestedAmount, months);
    }

    /**
     * Кредиты не выдаются людям старше 70 и младше 18.
     *
     * @param age возраст подателя заявки
     * @return true, если возраст подходящий
     */
    private boolean isCorrectAge(int age) {
        return age >= 18 && age <= 70;
    }

    /**
     * Кредиты не выдаются, если месячная выплата превосходит половину зарплаты.
     * Ежемесячная выплата считается очень наивно, без учёта процентной ставки.
     *
     * @param salary          месячная зарплата
     * @param requestedAmount запрошенная сумма
     * @param months          количество месяцев
     * @return true, если у человека остается половина зарплаты после выплаты кредита
     */
    private boolean hasHalfOfSalaryAfterCreditPayment(Long salary, Long requestedAmount, int months) {
        Long paymentPerMonth = requestedAmount / months;
        return (salary - paymentPerMonth) >= (salary / 2);
    }
}
