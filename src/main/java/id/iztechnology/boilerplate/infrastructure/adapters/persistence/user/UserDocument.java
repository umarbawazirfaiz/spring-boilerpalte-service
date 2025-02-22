package id.iztechnology.boilerplate.infrastructure.adapters.persistence.user;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class UserDocument {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    
    private String createdBy;
    private ZonedDateTime createdAt;
    private String updatedBy;
    private ZonedDateTime updatedAt;
}