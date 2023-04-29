import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class ditto {

    @Test
    public void testGetDitto() throws IOException {
        String endpoint = "https://pokeapi.co/api/v2/pokemon/ditto";
        Response response = RestAssured.get(endpoint);
        String dittoResponse = response.asString();
        System.out.println(dittoResponse); // print the raw response to console for debugging
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(dittoResponse, Object.class);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        System.out.println(prettyJson); // print the response as a JSON object with pretty formatting
    }


    @Test
    public void testGetPikachuAbilty() {
        String endpoint = "https://pokeapi.co/api/v2/pokemon/pikachu";
        Response response = RestAssured.get(endpoint);
        String pikachuResponse = response.asString();
//        System.out.println(pikachuresponse); // print the raw response to console for debugging
        List<String> abilityNames = JsonPath.read(pikachuResponse, "$.abilities[*].ability.name");
        System.out.println("Abilities: " + abilityNames); // print the ability names to console

        assertEquals(abilityNames.size(), 2);
        assertTrue(abilityNames.contains("static"));
        assertTrue(abilityNames.contains("lightning-rod"));
        List<Boolean> isHiddenValues = JsonPath.read(pikachuResponse, "$.abilities[*].is_hidden");
        assertFalse(isHiddenValues.get(0));
        assertTrue(isHiddenValues.get(1));
        List<Integer> abilitySlots = JsonPath.read(pikachuResponse, "$.abilities[*].slot");
        assertEquals(abilitySlots.get(0).intValue(), 1); // static
        assertEquals(abilitySlots.get(1).intValue(), 3); // lightning-rod
    }
}

