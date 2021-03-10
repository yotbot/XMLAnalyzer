package XMLAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonPropertyOrder({ "timestamp", "status", "error", "message" })
public class ApiError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(String message, HttpStatus status){
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() { return timestamp; }

    public int getStatus(){ return status.value(); }

    public String getError(){ return status.getReasonPhrase();}

    public String getMessage() {
        return message;
    }

}
