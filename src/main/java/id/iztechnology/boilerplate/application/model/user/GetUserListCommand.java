package id.iztechnology.boilerplate.application.model.user;

import id.iztechnology.boilerplate.domain.model.ListRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class GetUserListCommand extends ListRequest {
    
}
