package ma.hariti.asmaa.survey.survey.dto.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseDTO<T> {
    private final boolean success;
    private final T data;
    private final String error;
    private final int totalElements;

    public static <T> ApiResponseDTO<T> success(T data, int totalElements) {
        return new ApiResponseDTO<>(true, data, null, totalElements);
    }

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, data, null, 0);
    }

    public static <T> ApiResponseDTO<T> error(String error, int status) {
        return new ApiResponseDTO<>(false, null, error, status);
    }

    public static <T> ApiResponseDTO<T> error(String error, T data, int status) {
        return new ApiResponseDTO<>(false, data, error, status);
    }
}