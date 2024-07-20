package healli.foodie.common.http;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class SuccessfulResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, Object> properties;

    public SuccessfulResponse(LocalDateTime timestamp, int status, String message) {

        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public void setProperty(String key, Object value) {

        if (properties == null) {
            this.properties = new LinkedHashMap<>();
        }
        this.properties.put(key, value);
    }
}
