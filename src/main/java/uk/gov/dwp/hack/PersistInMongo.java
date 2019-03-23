package uk.gov.dwp.hack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import uk.gov.dwp.hack.utils.MongoConnectionUtils;

import java.util.Iterator;
import java.util.Map;

public class PersistInMongo {
  private MongoConnectionUtils connectionUtils = new MongoConnectionUtils();
  public static void main(String[] args) {
    PersistInMongo persistInMongo = new PersistInMongo();
    JsonNode jsonNode = persistInMongo.parseJson();
    persistInMongo.iterateAndPersist(jsonNode);
  }

  private void iterateAndPersist(JsonNode jsonNode) {
    Iterator<JsonNode> jsonNodeIterator = jsonNode.elements();
    while (jsonNodeIterator.hasNext()) {
      JsonNode foodBankWrapper = jsonNodeIterator.next();
      Iterator<Map.Entry<String, JsonNode>> fields =  foodBankWrapper.fields();

      while (fields.hasNext()) {
        Map.Entry food = fields.next();
        if (StringUtils.equals(food.getKey().toString(), "foodbank_centre")){
          JsonNode foodNode = (JsonNode) food.getValue();
          Iterator<JsonNode> persistNodes = foodNode.elements();
          while (persistNodes.hasNext()) {
            persist(persistNodes.next());
          }

        }
      }
    }
  }

  private JsonNode parseJson() {
    return new ParseFoodBankJson().parseJsonFile();
  }

  private void persist(JsonNode jsonNode) {
    //connectionUtils.getDatabase().createCollection("food-bank");
    ObjectMapper mapper =   new ObjectMapper();
    try {
      String json        =   mapper.writeValueAsString(jsonNode);
      connectionUtils.getDatabase().getCollection("food-bank").insertOne(Document.parse(json));
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }
}
