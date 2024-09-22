package com.litecase.boot.web.common;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ApiResponse<T> {
    /**
     * redirect：当客户端需要去访问另一个 URL。
     * pending：当请求正在处理中，客户端需要等待。
     * success：当请求处理成功并返回结果。
     * error：当请求处理失败，需要传达错误信息。
     * warning：当请求处理成功但有警告信息。
     * info：当需要传递额外的说明信息。
     * <p>
     * private String errorDetails;      // 错误详细信息
     * private List<String> validationErrors; // 数据验证错误
     * private String path;              // 请求路径
     * private boolean success;          // 是否成功
     * private Map<String, Object> metadata;  // 附加元数据
     * private Map<String, String> headers;   // 自定义响应头
     */
    private String status;
    private int code;
    private String message;
    private T data;
    // private LocalDateTime timestamp;
    private Long timestamp;
    private boolean success;


    public ApiResponse(boolean success, int code, String status, T data, String message) {
        this.success = success;
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        //  this.timestamp = LocalDateTime.now(); // 当前时间
        //  this.timestamp = System.currentTimeMillis() / 1000L; // Unix 时间戳（毫秒）
        //  this.timestamp = Instant.now().getEpochSecond(); // Unix 时间戳（秒）
        this.timestamp = Instant.now().toEpochMilli(); // Unix 时间戳（毫秒）
        //  this.timestamp = new Date().getTime(); // Unix 时间戳（毫秒）
    }

    // 静态工厂方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "success", data, "Successfully");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, 200, "success", data, message);
    }

    // public static ApiResponse<?> error(int statusCode, String message) {
    // return new ApiResponse<>(statusCode, message, null);
    // }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(false, code, "error", null, message);
    }

    public static <T> ResponseEntity<ApiResponse<T>> successWithStatus(T data, HttpStatus status) {
        return new ResponseEntity<>(ApiResponse.success(data), status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> errorWithStatus(int statusCode, String message, HttpStatus status) {
        return new ResponseEntity<>(ApiResponse.error(statusCode, message), status);
    }

    // =================================================================================
    public static ApiResponse<?> warning(String message) {
        return new ApiResponse<>(true, 200, "warning", null, message);
    }

    public static ApiResponse<?> info(String message) {
        return new ApiResponse<>(true, 200, "info", message, null);
    }

    public static ApiResponse<?> redirect(String url) {
        // 这里的 code 可以自定义以适应你的需求
        return new ApiResponse<>(false, 302, "redirect", "Redirecting to " + url, null);
    }

    public static ApiResponse<?> pending(String message) {
        return new ApiResponse<>(true, 202, "pending", message, null);
    }
}
