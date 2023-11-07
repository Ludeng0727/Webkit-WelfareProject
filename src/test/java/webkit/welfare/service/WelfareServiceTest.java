package webkit.welfare.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class WelfareServiceTest {
    private final WelfareService welfareService = new WelfareService();


    @Test
    void setAllWelfare() throws URISyntaxException {
        welfareService.setAllWelfare();

    }

    @Test
    void setAllWelfare2(){
        welfareService.setAllWelfare2();
    }
}