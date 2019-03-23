package uk.gov.dwp.hack.util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import uk.gov.dwp.hack.FoodBankConfiguration;

public class MongoConnectionUtil {

  public static MongoDatabase getDatabase(FoodBankConfiguration.MongoConfig mongoConfig) {
    return getClient(mongoConfig.getConnectionString()).getDatabase(mongoConfig.getDatabase());
  }

  private static MongoClient getClient(String connectionString) {
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString)).build();

    return MongoClients.create(settings);
  }
}
