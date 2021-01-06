package com.beitech.store.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author James Martinez
 */

public class Notification {

    private SimpleDateFormat colombianDateFormat;
    private static final String COLOMBIA_TIME_ZONE = "America/Bogota";
    private String description;
    private String code;
    private String responseTime;

    public Notification() {
        this.colombianDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms z");
    }

    public Notification(Notification.Builder builder) {
        this.colombianDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms z");
        this.description = builder.description;
        this.code = builder.code;
        this.colombianDateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        this.responseTime = this.colombianDateFormat.format(new Date());
    }

    public static Notification.Builder builder(String description, String code) {
        return new Notification.Builder(description, code);
    }

    public String getDescription() {
        return this.description;
    }

    public String getResponseTime() {
        return this.responseTime;
    }

    public String getCode() {
        return this.code;
    }



    public static class Builder {
        private String description;
        private String code;

        public Builder(String description, String code) {
            this.description = description;
            this.code = code;
        }

        public Notification build() {
            return new Notification(this);
        }

    }


}
