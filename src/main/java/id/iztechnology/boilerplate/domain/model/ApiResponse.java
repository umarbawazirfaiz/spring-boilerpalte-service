package id.iztechnology.boilerplate.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @JsonProperty(value = "is_success")
    private Boolean isSuccess;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonInclude(JsonInclude.Include.ALWAYS) // Always include in JSON, even if null
    @JsonProperty("data")
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .isSuccess(true)
                .statusCode("20000")
                .statusMessage("SUCCESS")
                .data(data)
                .build();

        return apiResponse;
    }
}
