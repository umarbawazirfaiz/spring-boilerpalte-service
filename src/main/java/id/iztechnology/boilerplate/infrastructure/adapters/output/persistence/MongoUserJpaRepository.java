package id.iztechnology.boilerplate.infrastructure.adapters.output.persistence;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserJpaRepository extends MongoRepository<UserDocument, UUID> {
    
}