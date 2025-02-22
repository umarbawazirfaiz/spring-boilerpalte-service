package id.iztechnology.boilerplate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ListRequest {
    String sort;
    String sortBy;
    String search;
    String searchBy;
    Integer page;
    Integer perPage;
}