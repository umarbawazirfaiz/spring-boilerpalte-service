package id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String name;
    private String email;

}
