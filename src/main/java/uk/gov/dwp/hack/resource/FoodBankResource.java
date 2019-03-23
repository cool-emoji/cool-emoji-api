package uk.gov.dwp.hack.resource;

import com.fasterxml.jackson.databind.JsonNode;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/food-bank")
@Produces(MediaType.APPLICATION_JSON)
public class FoodBankResource {

    private List<JsonNode> foodBanks;

    public FoodBankResource(List<JsonNode> foodBanks) {
        this.foodBanks = foodBanks;
    }

    @GET
    @Path("/postcode/{postCode}")
    public Response getFoodBankByPostCode(@PathParam("postCode") String postCode) {
        String trimmedPostCode = Objects.isNull(postCode) ? "" : postCode.trim().toLowerCase();
        String postCodePrefix = trimmedPostCode.length() > 3 ? trimmedPostCode.substring(0, 3) : trimmedPostCode;
        List<JsonNode> foodBankList = foodBanks.stream().filter(foodBank -> {
            if (Objects.isNull(foodBank) || !foodBank.has("post_code"))
                return false;
            String foodBankPostCode = foodBank.get("post_code").textValue();
            return !Objects.isNull(foodBankPostCode) && foodBankPostCode.toLowerCase().startsWith(postCodePrefix);
        }).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(foodBankList).build();
    }

    @GET
    @Path("/city/{cityName}")
    public Response getFoodBankByCity(@PathParam("cityName") String cityName) {
        String trimmedCityName = Objects.isNull(cityName) ? "" : cityName.trim().toLowerCase();
        List<JsonNode> foodBankList = foodBanks.stream().filter(foodBank -> {
            if (Objects.isNull(foodBank) || !foodBank.has("centre_geolocation"))
                return false;
            JsonNode foodBankLocation = foodBank.get("centre_geolocation");
            String foodBankAddress = foodBankLocation.has("address") ?
                    foodBankLocation.get("address").textValue() : null;
            return !Objects.isNull(foodBankAddress) && foodBankAddress.toLowerCase().contains(trimmedCityName);
        }).collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(foodBankList).build();
    }
}
