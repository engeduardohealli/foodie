package healli.foodie.owner.model.repository;

import healli.foodie.owner.model.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OwnerRepository extends MongoRepository<Owner, String> {
}
