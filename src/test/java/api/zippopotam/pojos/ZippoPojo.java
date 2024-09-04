package api.zippopotam.pojos;

import api.zippopotam.apiCalls.ZippoApi;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.junit.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZippoPojo {


    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    @JsonProperty("country")
    private String country;

    @JsonProperty("place name")
    private String placeName;

    @JsonProperty("state")
    private String state;

    @JsonProperty("state abbreviation")
    private String stateAbbreviation;

    @JsonProperty("post code")
    private String postCode;

    @JsonProperty("places")
    private List<Places> places;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Places {

        @JsonProperty("place name")
        private String placeName;

        @JsonProperty("longitude")
        private String longitude;

        @JsonProperty("latitude")
        private String latitude;

        @JsonProperty("state")
        private String state;

        @JsonProperty("state abbreviation")
        private String stateAbbreviation;

        @JsonProperty("post code")
        private String postCode;
    }

    public static void postalCodeChecker(String postalCode, String placeName){
        ZippoPojo zippo = ZippoApi.response.getBody().as(ZippoPojo.class);
        ZippoPojo.Places specificPlace = null;
        for (ZippoPojo.Places place : zippo.getPlaces()) {
            if (postalCode.equals(place.getPostCode())) {
                specificPlace = place;
                break;
            }
        }
        Assert.assertEquals(postalCode, specificPlace.getPostCode());
        Assert.assertEquals(placeName, specificPlace.getPlaceName());
    }

    public static void responseTimeChecker(int second){
        long responseTime = ZippoApi.response.getTimeIn(TimeUnit.MILLISECONDS);
        Assert.assertTrue(responseTime < second * 1000);
    }
}

