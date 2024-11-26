package id.iztechnology.boilerplate.domain.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    private String id;
    private String name;
    private String email;
    private String password;
    private String createdBy;
    private ZonedDateTime createdAt;

}
