package api.zippopotam.apiCalls;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ui.eurowings.utilities.Environment;

public class ZippoApi {
    public static Response response;

    public static Response getStuttgart() {
        response = RestAssured.given()
                .baseUri(Environment.API_baseURL)
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("/de/bw/stuttgart");
       // response.prettyPrint();
        return response;
    }

    public static Response getCountryAndPostalCode(String country, String postalCode) {
        response = RestAssured.given()
                .baseUri(Environment.API_baseURL)
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get(country + "/" + postalCode);
       //  response.prettyPrint();
        return response;
    }
}
