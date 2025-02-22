package id.iztechnology.boilerplate.application.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddUserCommand {
    private String name;
    private String email;
    private String password;
}
