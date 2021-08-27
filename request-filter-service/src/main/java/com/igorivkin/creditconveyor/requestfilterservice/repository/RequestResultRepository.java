package com.igorivkin.creditconveyor.requestfilterservice.repository;

import com.igorivkin.creditconveyor.requestfilterservice.model.redis.RequestResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestResultRepository extends CrudRepository<RequestResult, UUID> {
}
