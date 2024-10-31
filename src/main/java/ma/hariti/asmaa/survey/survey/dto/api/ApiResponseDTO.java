package ma.hariti.asmaa.survey.survey.dto.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseDTO<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final String error;
    private final int status;

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, data, null, null, 200);
    }

    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(true, data, message, null, 200);
    }

    public static <T> ApiResponseDTO<T> error(String error, int status) {
        return new ApiResponseDTO<>(false, null, null, error, status);
    }

    public static <T> ApiResponseDTO<T> error(String error, String message, int status) {
        return new ApiResponseDTO<>(false, null, message, error, status);
    }

    // New overloaded method to include a data map for detailed validation errors
    public static <T> ApiResponseDTO<T> error(String error, T data, String message, int status) {
        return new ApiResponseDTO<>(false, data, message, error, status);
    }
}
