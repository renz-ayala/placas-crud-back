package gg.renz.placainfo.infraestructure.dtos.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CloudflareCaptchaResponse(
        Boolean success,
        @JsonProperty("error-codes") List<String> errorCodes,
        @JsonProperty("challenge_ts") String challengeTs,
        String hostname
) {
}
