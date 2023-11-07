package webkit.welfare.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Service
public class WelfareService {

    private final String BASE_URL = "https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations";
    private final String API_PATH = "/LcgvWelfarelist";

    @Value("${public_data_api_key}")
    private String apiKey;

    public void setAllWelfare() throws URISyntaxException {
        URI uri = new URI(BASE_URL + API_PATH + apiKey);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        System.out.println(response);

    }
}
