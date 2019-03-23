package uk.gov.dwp.hack;

import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.dwp.hack.dao.FoodBankDao;
import uk.gov.dwp.hack.resource.FoodBankResource;
import uk.gov.dwp.hack.util.MongoConnectionUtil;

import java.io.IOException;
import java.util.List;

public class FoodBankApplication extends Application<FoodBankConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FoodBankApplication().run(args);
    }

    @Override
    public String getName() {
        return "food-bank";
    }

    @Override
    public void initialize(final Bootstrap<FoodBankConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(true)));
    }

    @Override
    public void run(final FoodBankConfiguration configuration,
                    final Environment environment) throws IOException {
        final FoodBankDao foodBankDao = new FoodBankDao(MongoConnectionUtil.getDatabase(
                configuration.getMongoConfig()).getCollection(configuration.getMongoConfig().getFoodBankCollection()));
        List<JsonNode> foodBanks = foodBankDao.getAll();
        final FoodBankResource resource = new FoodBankResource(foodBanks);
        environment.jersey().register(resource);
    }

}
