package gg.renz.placainfo.infraestructure.adapters.out.web;

import gg.renz.placainfo.infraestructure.shared.dtos.http.CloudflareCaptchaRequest;
import gg.renz.placainfo.infraestructure.shared.dtos.http.CloudflareCaptchaResponse;
import gg.renz.placainfo.domain.ports.out.CaptchaPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaAdapter implements CaptchaPort {
    private final RestClient restClient;

    @Value("${secret.key}") private String secretKey;

    @Override
    public Boolean verifyCaptcha(String captcha) {
        try {
            var input = new CloudflareCaptchaRequest(
                    secretKey,
                    captcha
            );
            CloudflareCaptchaResponse response = restClient.post()
                    .uri("https://challenges.cloudflare.com/turnstile/v0/siteverify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(input)
                    .retrieve()
                    .body(CloudflareCaptchaResponse.class);

            return response != null && response.success();
        }catch (Exception e) {
            log.error("Error verifying captcha", e);
            return false;
        }
    }
}
