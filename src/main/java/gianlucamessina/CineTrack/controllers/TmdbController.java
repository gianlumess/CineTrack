package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TmdbController {
    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/movies/trending")
    public Map<String,Object>getPopularMovies(){
        return this.tmdbService.getTrendingMovies();
    }

    @GetMapping("/series/trending")
    public Map<String,Object>getPopularSeries(){
        return this.tmdbService.getTrendingSeries();
    }

    @GetMapping("/movies/{movieId}")
    public Map<String,Object>getMovieDetail(@PathVariable long movieId){
        return this.tmdbService.getMovieDetails(movieId);
    }

    @GetMapping("/series/{seriesId}")
    public Map<String,Object>getSeriesDetails(@PathVariable long seriesId){
        return this.tmdbService.getSeriesDetails(seriesId);
    }

    @GetMapping("/movies/top_rated")
    public Map<String,Object>getTopRatedMovies(){
        return this.tmdbService.getTopRatedMovies();
    }

    @GetMapping("/series/top_rated")
    public Map<String,Object>getTopRatedSeries(){
        return this.tmdbService.getTopRatedSeries();
    }


}
