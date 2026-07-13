package gg.renz.placainfo.infraestructure.shared.dtos.http;

public record CloudflareCaptchaRequest(
        String secret,
        String response
) {
}
