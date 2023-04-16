package files;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static  org.hamcrest.Matchers.*;
public class GraphQLScript {

    public static void main(String[] args) {

        /**
          Query
         */
        String responseQuery = given().header("Content-Type","application/json").body("{\"query\":\"query($characterId:Int!, $locationId:Int!,$episodeId:Int!) {\\n  character(characterId:$characterId){\\n    " +
                "name\\n    type\\n    status\\n    species\\n    gender\\n    image\\n  }\\n  \\n  location(locationId:$locationId){\\n    name\\n    type\\n    " +
                "dimension\\n  }\\n  \\n  episode(episodeId:$episodeId){\\n    " +
                "name\\n    air_date\\n    episode\\n  }\\n}\\n\",\"variables\":{\"characterId\":2012,\"locationId\":2555,\"episodeId\":1441}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        JsonPath jsonPath = new JsonPath(responseQuery);
        System.out.println(jsonPath.getString("data.character.name"));

        //Mutation
        String responseMutation = given().header("Content-Type","application/json").body("{\"query\":" +
                        "\"mutation($locationName:String!,$characterName:String!,$episodName:String!) {\\n  createLocation(location: {name: $locationName, type: \\\"Southzone\\\", " +
                        "dimension: \\\"234\\\"}) {\\n    id\\n  }\\n  \\n  createCharacter(character: {name:$characterName,type:\\\"Macho\\\",status:\\\"dead\\\",species:\\\"fantasy\\\"," +
                        "gender:\\\"male\\\",image:\\\"png\\\",originId:2552,locationId:2552}){\\n   id \\n  }\\n  \\n  createEpisode(episode:{name:$episodName,air_date:\\\"2023\\\",episode:\\\"25\\\"}){\\n  " +
                        "  id\\n  }\\n  deleteLocations(locationIds:[2555]){\\n    locationsDeleted\\n  }\\n " +
                        " \\n}\\n\",\"variables\":{\"locationName\":\"SouthAfrica\"," +
                        "\"characterName\":\"BaskerRobber\",\"episodName\":\"Manifest2\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();
        JsonPath jsonPathMutation = new JsonPath(responseMutation);
        System.out.println(jsonPathMutation.getString("data.createLocation.id"));


    }

}
