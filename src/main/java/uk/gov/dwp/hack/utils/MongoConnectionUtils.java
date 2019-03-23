package uk.gov.dwp.hack.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionUtils {

  public MongoDatabase getDatabase() {
    return getClient().getDatabase("heroku_m2n6blzn");
  }
  public MongoClient getClient() {

    MongoClientSettings.Builder clientBuilder = MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://heroku_m2n6blzn:9onrinh4p04r2tgfhvaim5v6hk@ds121636.mlab.com:21636/heroku_m2n6blzn"));

    MongoClientSettings settings = clientBuilder
        .build();

    return MongoClients.create(settings);
  }
}
