package com.beitech.store.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author James Martinez
 * @since 05/10/2020
 */
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class ApiResponse<T> {
    private T data;
    private Notification notification;

    private ApiResponse() {
    }

    public ApiResponse(Notification notification) {
        this.notification = notification;
    }

    public ApiResponse(T data, Notification notification) {
        this.data = data;
        this.notification = notification;
    }

    public T getData() {
        return this.data;
    }

    public Notification getNotification() {
        return this.notification;
    }
}
