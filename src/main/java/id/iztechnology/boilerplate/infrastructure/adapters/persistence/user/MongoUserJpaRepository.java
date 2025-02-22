package id.iztechnology.boilerplate.infrastructure.adapters.persistence.user;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserJpaRepository extends MongoRepository<UserDocument, UUID> {
    
}