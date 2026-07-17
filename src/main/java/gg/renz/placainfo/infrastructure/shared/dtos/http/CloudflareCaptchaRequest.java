package gg.renz.placainfo.infrastructure.shared.dtos.http;

public record CloudflareCaptchaRequest(
        String secret,
        String response
) {
}
