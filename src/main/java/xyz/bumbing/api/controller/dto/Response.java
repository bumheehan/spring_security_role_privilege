package xyz.bumbing.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private final T data;
    private String message;

    public Response(T data, Object message) {
        this.data = data;
        if(message!=null){
            this.message = message.toString();
        }
    }

    public Response(T data) {
        this.data = data;
    }

    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(data, message);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(data);
    }

}
