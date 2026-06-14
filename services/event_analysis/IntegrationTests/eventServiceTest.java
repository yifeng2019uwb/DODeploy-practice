import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class eventServiceTest {
    static final String BASE_URL = "https://goldfish-app-ligib.ondigitalocean.app";
    static final String BASE_PATH = "/api/v1/events";
    static final HttpClient client = HttpClient.newHttpClient();

    static final UUID customer_id1 = UUID.randomUUID();
    static final UUID customer_id2 = UUID.randomUUID();

    public static void main(String[] args) throws Exception {
        String eventId = createEvent_withValidData_shouldReturnCreatedEvent();
        getEvent_withValidId_shouldReturnEvent(eventId);
        getSummary_withValidCustomer_shouldReturnSummary();
        getTopEvents_withValidCustomer_shouldReturnResults();
        System.out.println("\n==> All tests passed!");
    }

    static String createEvent_withValidData_shouldReturnCreatedEvent() throws Exception {
        System.out.println("==> POST " + BASE_PATH);
        String body = """
            {
                "customer_id": "%s",
                "event_type": "LOGIN",
                "timestamp": "2024-01-15",
                "metadata": %s
            }
            """.formatted(customer_id1, buildMetaDataJson("key", "value", 3));

        var req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + BASE_PATH))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + resp.statusCode() + "  Body: " + resp.body());
        assert resp.statusCode() == 200 : "Expected 200, got " + resp.statusCode();

        String r = resp.body();
        int start = r.indexOf("\"event_id\":\"") + 12;
        return r.substring(start, r.indexOf("\"", start));
    }

    static void getEvent_withValidId_shouldReturnEvent(String eventId) throws Exception {
        System.out.println("\n==> GET " + BASE_PATH + "/" + eventId);
        var req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + BASE_PATH + "/" + eventId))
            .GET().build();
        var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + resp.statusCode() + "  Body: " + resp.body());
        assert resp.statusCode() == 200 : "Expected 200, got " + resp.statusCode();
    }

    static void getSummary_withValidCustomer_shouldReturnSummary() throws Exception {
        System.out.println("\n==> GET " + BASE_PATH + "/summary");
        var req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + BASE_PATH + "/summary?customer_id=" + customer_id1 + "&start_time=2024-01-01&end_time=2024-12-31"))
            .GET().build();
        var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + resp.statusCode() + "  Body: " + resp.body());
        assert resp.statusCode() == 200 : "Expected 200, got " + resp.statusCode();
    }

    static void getTopEvents_withValidCustomer_shouldReturnResults() throws Exception {
        System.out.println("\n==> GET " + BASE_PATH + "/top-events");
        var req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + BASE_PATH + "/top-events?customer_id=" + customer_id1 + "&limit=5"))
            .GET().build();
        var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + resp.statusCode() + "  Body: " + resp.body());
        assert resp.statusCode() == 200 : "Expected 200, got " + resp.statusCode();
    }

    static String buildMetaDataJson(String key, String value, int size) {
        var sb = new StringBuilder("{");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(key).append(i).append("\":\"").append(value).append(i).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }
}
