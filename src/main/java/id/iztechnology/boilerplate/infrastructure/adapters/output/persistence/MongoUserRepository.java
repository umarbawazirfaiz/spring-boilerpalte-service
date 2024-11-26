package id.iztechnology.boilerplate.infrastructure.adapters.output.persistence;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import id.iztechnology.boilerplate.application.port.output.UserOutputPort;
import id.iztechnology.boilerplate.domain.model.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoUserRepository implements UserOutputPort {

    private final MongoUserJpaRepository mongoRepository;

    @Override
    public List<User> getAllUsers() {
        return mongoRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<User> getUserById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }
    
    @Override
    public Optional<User> createUser(User user) {
        UserDocument userDocument = UserDocument.builder()
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .build();

        this.create(userDocument);
        
        userDocument = mongoRepository.save(userDocument);

        return Optional.of(toDomain(userDocument));
    }
    
    @Override
    public Optional<User> updateUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
    
    private User toDomain(UserDocument document) {
        return User.builder()
            .id(document.getId().toString())
            .name(document.getName())
            .email(document.getEmail())
            .password(document.getPassword())
            .build();
    }

    protected UserDocument create(UserDocument userDocument) {
        userDocument.setCreatedAt(ZonedDateTime.now());
        userDocument.setUpdatedAt(ZonedDateTime.now());
        return userDocument;
    }

}
