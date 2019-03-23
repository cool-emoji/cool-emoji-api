package uk.gov.dwp.hack;

import io.dropwizard.Configuration;

public class FoodBankConfiguration extends Configuration {
    private MongoConfig mongoConfig;

    public MongoConfig getMongoConfig() {
        return mongoConfig;
    }

    public class MongoConfig {
        private String connectionString;
        private String database;
        private String foodBankCollection;

        public String getConnectionString() {
            return connectionString;
        }

        public String getDatabase() {
            return database;
        }

        public String getFoodBankCollection() {
            return foodBankCollection;
        }
    }

}
