package ma.hariti.asmaa.survey.survey.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final String error;
    private final int status;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null, 200);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null, 200);
    }

    public static <T> ApiResponse<T> error(String error, int status) {
        return new ApiResponse<>(false, null, null, error, status);
    }

    public static <T> ApiResponse<T> error(String error, String message, int status) {
        return new ApiResponse<>(false, null, message, error, status);
    }
}