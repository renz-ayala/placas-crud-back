package gg.renz.placainfo.infraestructure.dtos.http;

public record CloudflareCaptchaRequest(
        String secret,
        String response
) {
}
