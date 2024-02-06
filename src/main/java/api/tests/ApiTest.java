package api.tests;

import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;


public class ApiTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9876);

    @Test
    public void basicApiTest() {

        //create the mock of a random url
        stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")));

        //Hit the mocked endpoint with RestAssured Request Spec and get a response
        Response response = given().
                baseUri("http://localhost").
                port(9876).
                when().
                get("/some/thing").
                then().extract().response();

        Assert.assertEquals(200, response.statusCode());

        System.out.println(response.getBody().prettyPrint());
    }
    
}