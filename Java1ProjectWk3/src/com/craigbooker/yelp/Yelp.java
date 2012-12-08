package com.craigbooker.yelp;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.craigbooker.yelp.YelpV2API;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
* Example for accessing the Yelp API.
*/
public class Yelp {

 OAuthService service;
 Token accessToken;
 // Update tokens here from Yelp developers site, Manage API access.
 String consumerKey = "8hYTZBUuiTxOwmTjQtFnTw";
 String consumerSecret = "2mTa_1uggZVU2aWoIWQ8VViSC6s";
 String token = "BAr8f7RjszQjh3_4A8VrCI1TjDLw5uMt";
 String tokenSecret = "mPgSRUOXlheC1c5Zr8I7-I3dVj0";
 
 /**
  * Setup the Yelp API OAuth credentials.
  *
  * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
  *
  * @param consumerKey Consumer key
  * @param consumerSecret Consumer secret
  * @param token Token
  * @param tokenSecret Token secret
  */
 public Yelp(String consumerKey, String consumerSecret, String token, String tokenSecret) {
   this.service = new ServiceBuilder().provider(YelpV2API.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
   this.accessToken = new Token(token, tokenSecret);
 }

 /**
  * Search with term and location.
  *
  * @param term Search term
  * @param latitude Latitude
  * @param longitude Longitude
  * @return JSON string response
  */
 public String search(String term, double latitude, double longitude) {
   OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
   request.addQuerystringParameter("term", term);
   request.addQuerystringParameter("ll", latitude + "," + longitude);
   this.service.signRequest(this.accessToken, request);
   Response response = request.send();
   return response.getBody();
 }
}