package webkit.welfare.sceduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.service.WelfareService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application-api-key.properties")
public class WelfareScheduler {

    private final String BASE_URL = "https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations";
    private final String API_PATH = "/LcgvWelfarelist";
    @Value("${public_data_api_key}")
    private String API_KEY;
    private final String NUM_OF_ROWS = "&numOfRows=99999";
    private final WelfareService welfareService;

    @Scheduled(cron = "0 0 4 * * *")
    public void updateWelfare() throws URISyntaxException, JsonProcessingException {
        URI uri = new URI(BASE_URL + API_PATH + API_KEY + NUM_OF_ROWS);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String json = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);

        String resultCode = node.get("resultCode").asText();
        String resultMessage = node.get("resultMessage").asText();
        String numOfRows = node.get("numOfRows").asText();
        String pageNo = node.get("pageNo").asText();
        String totalCount = node.get("totalCount").asText();
        JsonNode nodes = node.get("servList");
        ArrayNode servList = (ArrayNode) nodes;

        List<WelfareEntity> welfareList = mapper.convertValue(servList, new TypeReference<List<WelfareEntity>>() {});
        welfareService.saveAllWelfareList(welfareList);

    }
}
