package com.oscode.pointr;

import android.net.ParseException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
* Code sample for accessing the Yelp API V2.
*
* This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
* query for businesses by a search term and location, and the Business API to query additional
* information about the top result from the search query.
*
* <p>
* See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
*
*/
public class YelpHandler {

  private static final String API_HOST = "api.yelp.com";
  private static final String DEFAULT_TERM = "dinner";
  private static final String DEFAULT_LOCATION = "San Francisco, CA";
  private static final int SEARCH_LIMIT = 3;
  private static final String SEARCH_PATH = "/v2/search";
  private static final String BUSINESS_PATH = "/v2/business";

  /*
   * Update OAuth credentials below from the Yelp Developers API site:
   * http://www.yelp.com/developers/getting_started/api_access
   */
  private static final String CONSUMER_KEY = "dy5UqjXG8VjyD5WUEohlkA";
  private static final String CONSUMER_SECRET = " CRy8ZgafVJl9JQ9hVLrDrOI1E4o";
  private static final String TOKEN = "w3-nGeLXLZ03oC0uqnqtJmaBj4wPEbM5";
  private static final String TOKEN_SECRET = "JWbA2yAvWzCiPYr2rxcF2mUwBKI";

  OAuthService service;
  Token accessToken;

  /**
   * Setup the Yelp API OAuth credentials.
   *
   * @param consumerKey Consumer key
   * @param consumerSecret Consumer secret
   * @param token Token
   * @param tokenSecret Token secret
   */
  public YelpHandler(String consumerKey, String consumerSecret, String token, String tokenSecret) {
    this.service =
        new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
            .apiSecret(consumerSecret).build();
    this.accessToken = new Token(token, tokenSecret);
  }

  /**
   * Creates and sends a request to the Search API by term and location.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
   * for more info.
   *
   * @param term <tt>String</tt> of the search term to be queried
   * @param location <tt>String</tt> of the location
   * @return <tt>String</tt> JSON Response
   */
  public String searchForBusinessesByLocation(String term, String location) {
    OAuthRequest request = createOAuthRequest(SEARCH_PATH);
      Log.e("IMPORTANT_TAG", "POOP4");
    request.addQuerystringParameter("term", term);
    request.addQuerystringParameter("location", location);
    request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
      Log.e("IMPORTANT_TAG", "POOP6969");
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and sends a request to the Business API by business ID.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
   * for more info.
   *
   * @param businessID <tt>String</tt> business ID of the requested business
   * @return <tt>String</tt> JSON Response
   */
  private String searchByBusinessId(String businessID) {
    OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and returns an {@link org.scribe.model.OAuthRequest} based on the API endpoint specified.
   *
   * @param path API endpoint to be queried
   * @return <tt>OAuthRequest</tt>
   */
  private OAuthRequest createOAuthRequest(String path) {
    OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
    return request;
  }

  /**
   * Sends an {@link org.scribe.model.OAuthRequest} and returns the {@link org.scribe.model.Response} body.
   *
   * @param request {@link org.scribe.model.OAuthRequest} corresponding to the API request
   * @return <tt>String</tt> body of API response
   */
  private String sendRequestAndGetResponse(OAuthRequest request) {
      Log.e("IMPORTANT_TAG","Querying " + request.getCompleteUrl() + " ...");
      Log.e("IMPORTANT_TAG", "POOP69696999");
    this.service.signRequest(this.accessToken, request);
      Log.e("IMPORTANT_TAG", "POOP96969696");
    //Response response = request.send();
      Response response = new OAuthRequest(Verb.GET, "http://www.google.com").send();
      Log.e("IMPORTANT_TAG", "POOP100000");
    return response.getBody();
  }

  /**
   * Queries the Search API based on the command line arguments and takes the first result to query
   * the Business API.
   *
   * @param yelpHandler <tt>YelpAPI</tt> service instance
   * @param yelpApiCli <tt>YelpAPICLI</tt> command line arguments
   */
  private static void queryAPI(YelpHandler yelpHandler, YelpAPICLI yelpApiCli) {
    String searchResponseJSON =
        yelpHandler.searchForBusinessesByLocation(yelpApiCli.term, yelpApiCli.location);

      Log.e("IMPORTANT_TAG", "MID POOP");
    JSONObject response = null;

    try {
      response = new JSONObject(searchResponseJSON);
    } catch (JSONException je) {

        Log.e("IMPORTANT_TAG", "MID poopy POOP");
    }
  }

  /**
   * Command-line interface for the sample Yelp API runner.
   */
  private static class YelpAPICLI {
    @Parameter(names = {"-q", "--term"}, description = "Search Query Term")
    public String term = DEFAULT_TERM;

    @Parameter(names = {"-l", "--location"}, description = "Location to be Queried")
    public String location = DEFAULT_LOCATION;
  }

  /**
   * Main entry for sample Yelp API requests.
   * <p>
   * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
   */
  public static void test() {
      Log.e("IMPORTANT_TAG", "START POOP");
    YelpAPICLI yelpApiCli = new YelpAPICLI();
    new JCommander(yelpApiCli);
      Log.e("IMPORTANT_TAG", "POOP2");
    YelpHandler yelpHandler = new YelpHandler(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
      Log.e("IMPORTANT_TAG", "POOP3");
    queryAPI(yelpHandler, yelpApiCli);
      Log.e("IMPORTANT_TAG", "DONE POOP");
  }
}
