package info.pionas.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {
        "spring.profiles.active=it",
        "server.port=50000"
})

@Import({DbITConfiguration.class, RestITConfiguration.class})
public class AbstractIT {

    @Autowired
    protected TestRestTemplate apiRestClient;

    @Autowired
    protected TestDbUtil dbUtil;

    @Autowired
    protected ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        dbUtil.clean();
    }
}
