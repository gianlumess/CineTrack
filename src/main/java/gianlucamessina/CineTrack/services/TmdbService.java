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
    //TRENDING MOVIES
    public Map<String,Object> getTrendingMovies(){
        String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/trending/movie/day")
                .queryParam("language","it-IT")
                .toUriString();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String>entity=new HttpEntity<>(headers);
        ResponseEntity<Map>response=restTemplate.exchange(url,HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }
    //TRENDING SERIES
    public Map<String,Object> getTrendingSeries(){
        String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/trending/tv/day")
                .queryParam("language","it-IT")
                .toUriString();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String>entity=new HttpEntity<>(headers);
        ResponseEntity<Map>response=restTemplate.exchange(url,HttpMethod.GET,entity, Map.class);
        return response.getBody();
    }
    //MOVIE DETAILS
    public Map<String, Object> getMovieDetails(Long movieId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/" + movieId)
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //SERIES DETAILS
    public Map<String, Object> getSeriesDetails(Long seriesId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tv/" + seriesId)
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //MOST RATED MOVIES
    public Map<String, Object> getTopRatedMovies() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/top_rated")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //MOST RATED SERIES
    public Map<String, Object> getTopRatedSeries() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tv/top_rated")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //MOVIE CREDITS
    public Map<String, Object> getMovieCredits(Long movieId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/" + movieId + "/credits")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //SERIES CREDITS
    public Map<String, Object> getSeriesCredits(Long seriesId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tv/" + seriesId + "/credits")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //MOVIE IMAGES
    public Map<String, Object> getMovieImages(Long movieId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/" + movieId + "/images")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //SERIES IMAGES
    public Map<String, Object> getSeriesImages(Long seriesId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tv/" + seriesId + "/images")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //MOVIE VIDEOS
    public Map<String, Object> getMovieVideos(Long movieId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/movie/" + movieId + "/videos")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }

    //SERIES VIDEOS
    public Map<String, Object> getSeriesVideos(Long seriesId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/tv/" + seriesId + "/videos")
                .queryParam("language", "it-IT")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody();
    }
}
