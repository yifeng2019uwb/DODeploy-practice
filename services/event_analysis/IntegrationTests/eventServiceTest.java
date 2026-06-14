import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.micrometer.core.annotation.TimedSet;

@DisplayName("Event Endpoint")
public class eventServiceTest {
    private static final String endpoint = "http://goldfish-app-ligib.ondigitalocean.app";
    private static final String basePath = "/api/v1/events";

    private static final String customer_id1 = UUID.randomUUID();
    private

    @Test
    void createEvent_withValidData_shouldReturnCreatedEvent() {
        given()
            .contentType(ContentType.JSON)
            .body(Map.of(
                "customer_id",   customer_id1,
                "type",      "log",
                "metadata",   buildMetaData("test", "value", 3)
                )
            )
        .when()
            .post(basePath)
        .then()
            .statusCode(201);

    }


    private Map<String, String> buildMetaData(String key, String value, int size) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(key + i, value + i);
        }
        return map;
    }
}
