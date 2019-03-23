package uk.gov.dwp.hack.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodBankDao {

    final MongoCollection<Document> foodBankCollection;
    final ObjectMapper mapper;

    public FoodBankDao(final MongoCollection<Document> foodBankCollection) {
        this.foodBankCollection = foodBankCollection;
        mapper = new ObjectMapper();
    }

    public List<JsonNode> getAll() throws IOException {
        final MongoCursor<Document> foodBanks = foodBankCollection.find().iterator();
        final List<JsonNode> foodBankList = new ArrayList<>();
        try {
            while (foodBanks.hasNext()) {
                final Document foodBankDocument = foodBanks.next();
                String jsonString = foodBankDocument.toJson();

                JsonNode foodBank = mapper.readTree(jsonString);
                foodBankList.add(foodBank);
            }
        } finally {
            foodBanks.close();
        }
        return foodBankList;
    }
}
