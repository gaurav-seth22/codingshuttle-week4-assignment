package com.codingshuttle.currencyconverter.clients;



import com.codingshuttle.currencyconverter.dto.CurrencyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



/*

@Service
public class CurrencyClient {

  private final RestClient restClient;
  private final String API_KEY="fca_live_916oBgZtcjbXu10nMHKW72ySF4UVfJ0ranCap1Wg";

  //private final String BASE_URL="https://api.freecurrencyapi.com/v1/latest";


    Logger logger= LoggerFactory.getLogger(CurrencyClient.class);

    public CurrencyClient(RestClient restClient) {
        this.restClient = restClient;
    }


*/
/*public CurrencyClient(RestClient restClient) {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }*//*




    //https://freecurrencyapi.com/docs/latest#request-parameters
    public double getConvertedCurrency(String from,String to,double units){
      CurrencyDTO response=restClient.get()
              .uri(uriBuilder -> uriBuilder
                      .path("/v1/latest")
                      .queryParam("apikey",API_KEY)
                      .queryParam("base_currency",from)
                      .queryParam("currencies",to)
                      .build()).retrieve()
              .body(new ParameterizedTypeReference<>() {
              });
      if (response.getData() != null) {
        double rate = response.getData().get(to);
        return units * rate;
      }
      throw new RuntimeException("Unable to get currency conversion rate");

    }

}


*/


@Service

public class CurrencyClient {

  private final RestClient restClient;
  @Value("${currency.api.key}")
  private  String API_KEY;

  //For url check in logs
  @Value("${currencyService.base.url}")
  private String BASE_URL;

  Logger logger = LoggerFactory.getLogger(CurrencyClient.class);

  public CurrencyClient(@Qualifier("currencyRestClient") RestClient restClient) {
    this.restClient = restClient;

  }


  public double getConvertedCurrency(String from, String to, double units) {
    try {
      logger.info("Converted currency retrieval");
      CurrencyDTO response = restClient.get()
              .uri(uriBuilder -> uriBuilder
                      .path("/v1/latest")
                      .queryParam("apikey", API_KEY)
                      .queryParam("base_currency", from)
                      .queryParam("currencies", to)
                      .build())
              .retrieve()
              .body(new ParameterizedTypeReference<>() {});






//URL check
      UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(BASE_URL)
              .path("/v1/latest")  // Specify the path
              .queryParam("apikey", API_KEY)
              .queryParam("base_currency", from)
              .queryParam("currencies", to)
              .build();

      logger.info("Constructed URL: {}", uriComponents.toString());


      if (response.getData() != null) {
        double rate = response.getData().get(to);
        return units * rate;
      }

      throw new RuntimeException("Currency conversion data is unavailable.");
    } catch (Exception e) {
      logger.error("Error fetching currency conversion: {}", e.getMessage());
      throw new RuntimeException("Failed to fetch currency conversion rate.", e);
    }
  }




}
