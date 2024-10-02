package gianlucamessina.CineTrack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class TmdbService {
    @Value("${tmdb.key}")
    private String apiKey;
    @Value("${tmdb.access.token}")
    private String accessToken;
    private final String BASE_URL = "https://api.themoviedb.org/3";
    @Autowired
    private RestTemplate restTemplate;

    public TmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String,Object> searchMovie(String query){
        String url= UriComponentsBuilder.fromHttpUrl(BASE_URL+"/search/movie")
                .queryParam("query",query)
                .queryParam("language","it-IT")
                .toUriString();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity=new HttpEntity<>(headers);

        ResponseEntity<Map>response=restTemplate.exchange(url, HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }

    public Map<String,Object> searchSeries(String query){
        String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/search/tv")
                .queryParam("query",query)
                .queryParam("language","it-IT")
                .toUriString();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity=new HttpEntity<>(headers);
        ResponseEntity<Map>response=restTemplate.exchange(url,HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }

    public Map<String,Object> getPopularMovies(){
        String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/movie/popular")
                .queryParam("language","it-IT")
                .toUriString();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String>entity=new HttpEntity<>(headers);
        ResponseEntity<Map>response=restTemplate.exchange(url,HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }

    public Map<String,Object> getPopularSeries(){
        String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/tv/popular")
                .queryParam("language","it-IT")
                .toUriString();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String>entity=new HttpEntity<>(headers);
        ResponseEntity<Map>response=restTemplate.exchange(url,HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }
}
