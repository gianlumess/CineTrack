package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.services.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TmdbController {
    @Autowired
    private TmdbService tmdbService;

    @GetMapping("/movies/trending")
    public Map<String, Object> getTrendingMovies(@RequestParam(defaultValue = "1") Integer page) {
        return this.tmdbService.getTrendingMovies(page);
    }

    @GetMapping("/series/trending")
    public Map<String, Object> getTrendingSeries(@RequestParam(defaultValue = "1") Integer page) {
        return this.tmdbService.getTrendingSeries(page);
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
    public Map<String, Object> getTopRatedMovies(@RequestParam(defaultValue = "1") Integer page) {
        return this.tmdbService.getTopRatedMovies(page);
    }

    @GetMapping("/series/top_rated")
    public Map<String, Object> getTopRatedSeries(@RequestParam(defaultValue = "1") Integer page) {
        return this.tmdbService.getTopRatedSeries(page);
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

    @GetMapping("/movies/{movieId}/similar")
    public Map<String, Object> getSimilarMovies(@PathVariable long movieId) {
        return this.tmdbService.getSimilarMovies(movieId);
    }

    @GetMapping("/series/{seriesId}/similar")
    public Map<String, Object> getSimilarSeries(@PathVariable long seriesId) {
        return this.tmdbService.getSimilarSeries(seriesId);
    }

    @GetMapping("/movies/search")
    public Map<String, Object> searchMovie(@RequestParam("query") String query) {
        return this.tmdbService.searchMovie(query);
    }

    @GetMapping("series/search")
    public Map<String, Object> searchSeries(@RequestParam("query") String query) {
        return this.tmdbService.searchSeries(query);
    }


}
