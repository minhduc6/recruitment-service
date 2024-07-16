package vn.unigap.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.mongo.RequestResponse;


@Repository
public interface RequestResponseRepository extends MongoRepository<RequestResponse, String> {
}