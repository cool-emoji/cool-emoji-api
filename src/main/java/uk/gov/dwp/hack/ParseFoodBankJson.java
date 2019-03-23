package uk.gov.dwp.hack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParseFoodBankJson {

  public JsonNode parseJsonFile() {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode masterJSON = null;
    try {
      InputStream stream = new FileInputStream("src/main/resources/foodbank.json");
       masterJSON = mapper.readTree(stream);

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return masterJSON.get("a");
  }
}
