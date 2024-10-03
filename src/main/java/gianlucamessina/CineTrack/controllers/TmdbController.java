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
    public Map<String, Object> getTrendingMovies() {
        return this.tmdbService.getTrendingMovies();
    }

    @GetMapping("/series/trending")
    public Map<String, Object> getTrendingSeries() {
        return this.tmdbService.getTrendingSeries();
    }

    @GetMapping("/movies/{movieId}")
    public Map<String, Object> getMovieDetail(@PathVariable long movieId) {
        return this.tmdbService.getMovieDetails(movieId);
    }

    @GetMapping("/series/{seriesId}")
    public Map<String, Object> getSeriesDetails(@PathVariable long seriesId) {
        return this.tmdbService.getSeriesDetails(seriesId);
    }

    @GetMapping("/movies/top_rated")
    public Map<String, Object> getTopRatedMovies() {
        return this.tmdbService.getTopRatedMovies();
    }

    @GetMapping("/series/top_rated")
    public Map<String, Object> getTopRatedSeries() {
        return this.tmdbService.getTopRatedSeries();
    }

    @GetMapping("/movies/{movieId}/credits")
    public Map<String, Object> getMovieCredits(@PathVariable long movieId) {
        return this.tmdbService.getMovieCredits(movieId);
    }

    @GetMapping("/series/{seriesId}/credits")
    public Map<String, Object> getSeriesCredits(@PathVariable long seriesId) {
        return this.tmdbService.getSeriesCredits(seriesId);
    }

    @GetMapping("/movies/{movieId}/images")
    public Map<String, Object> getMovieImages(@PathVariable long movieId) {
        return this.tmdbService.getMovieImages(movieId);
    }

    @GetMapping("/series/{seriesId}/images")
    public Map<String, Object> getSeriesImages(@PathVariable long seriesId) {
        return this.tmdbService.getSeriesImages(seriesId);
    }

    @GetMapping("/movies/{movieId}/videos")
    public Map<String, Object> getMovieVideos(@PathVariable long movieId) {
        return this.tmdbService.getMovieVideos(movieId);
    }

    @GetMapping("/series/{seriesId}/videos")
    public Map<String, Object> getSeriesVideos(@PathVariable long seriesId) {
        return this.tmdbService.getSeriesVideos(seriesId);
    }


}
