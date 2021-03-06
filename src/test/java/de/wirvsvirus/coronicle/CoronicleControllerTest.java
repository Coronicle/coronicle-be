package de.wirvsvirus.coronicle;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import de.wirvsvirus.coronicle.db.model.InfectedPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoronicleControllerTest {

    public static final LocalDateTime DATE_1 = LocalDateTime.of(2020, Month.MARCH, 21, 12, 0, 0);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPostTrace() {
        String baseUrl = "http://localhost:" + port;

        List<InfectedPoint> infectedTraces = Collections.singletonList(new InfectedPoint(DATE_1, 45.5, 13.5));
        HttpEntity<List<InfectedPoint>> infectedEntity = new HttpEntity<>(infectedTraces);
        ResponseEntity<String> response = restTemplate.exchange(URI.create(baseUrl
                + "/infectedtrace"), HttpMethod.POST, infectedEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());

        ResponseEntity<Boolean> checkResponse = restTemplate.exchange(URI.create(baseUrl
                + "/checktrace"), HttpMethod.POST, infectedEntity, Boolean.class);

        assertEquals(HttpStatus.OK, checkResponse.getStatusCode());
        assertNotNull(checkResponse.getBody());

        assertTrue(checkResponse.getBody());
    }

    @Test
    void testNonInfection() {
        String baseUrl = "http://localhost:" + port;

        List<InfectedPoint> infectedTraces = Collections.singletonList(new InfectedPoint(DATE_1, 50.9451, 7.2483));
        HttpEntity<List<InfectedPoint>> infectedEntity = new HttpEntity<>(infectedTraces);
        ResponseEntity<String> response = restTemplate.exchange(URI.create(baseUrl
                + "/infectedtrace"), HttpMethod.POST, infectedEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());

        List<InfectedPoint> infectedTraces1 = Collections.singletonList(new InfectedPoint(DATE_1, 50.9448, 7.2485));
        HttpEntity<List<InfectedPoint>> infectedEntity1 = new HttpEntity<>(infectedTraces1);

        ResponseEntity<Boolean> checkResponse = restTemplate.exchange(URI.create(baseUrl
                + "/checktrace"), HttpMethod.POST, infectedEntity1, Boolean.class);

        assertEquals(HttpStatus.OK, checkResponse.getStatusCode());
        assertNotNull(checkResponse.getBody());


        assertFalse(checkResponse.getBody());
    }

}
