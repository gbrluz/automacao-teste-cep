package viaceptest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ViaCepTest {

    // Base URI da API
    private static final String BASE_URI = "https://viacep.com.br/ws";

    @Test
    public void testCepValido() {
        String cepValido = "01001000";

        Response response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/{cep}/json", cepValido)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assertions.assertNotNull(response.jsonPath().get("logradouro"), "Logradouro não deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().get("bairro"), "Bairro não deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().get("localidade"), "Localidade não deve ser nula");
        Assertions.assertNotNull(response.jsonPath().get("uf"), "UF não deve ser nula");
    }

    @Test
    public void testCepInvalido() {
        String cepInvalido = "00000000";

        Response response = given()
                .baseUri(BASE_URI)
                .when()
                .get("/{cep}/json", cepInvalido)
                .then()
                .statusCode(400)
                .extract()
                .response();

        Assertions.assertNotNull(response.jsonPath().get("erro"), "Erro deve estar presente para CEP inválido");
    }
}

