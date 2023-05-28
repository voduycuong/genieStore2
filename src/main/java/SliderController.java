package main.java;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SliderController {

    @FXML
    private ImageView movieImageSlider;

    @FXML
    private ImageView gameImageSlider;

    private List<Image> movieImages;
    private List<Image> gameImages;
    private int movieIndex;
    private int gameIndex;

    @FXML
    public void initialize() {
        String movieImagePath = "src/resources/img/";
        File movieImageDirectory = new File(movieImagePath);
        File[] movieImageFiles = movieImageDirectory.listFiles((dir, name) -> name.toLowerCase().contains("movie"));

        String gameImagePath = "src/resources/img/";
        File gameImageDirectory = new File(gameImagePath);
        File[] gameImageFiles = gameImageDirectory.listFiles((dir, name) -> name.toLowerCase().contains("game"));

        if (movieImageFiles != null && movieImageFiles.length > 0) {
            movieImages = new ArrayList<>();
            Arrays.stream(movieImageFiles).forEach(file -> {
                String fileUrl = file.toURI().toString();
                Image image = new Image(fileUrl);
                movieImages.add(image);
            });

            startMovieSlider();
        }

        if (gameImageFiles != null && gameImageFiles.length > 0) {
            gameImages = new ArrayList<>();
            Arrays.stream(gameImageFiles).forEach(file -> {
                String fileUrl = file.toURI().toString();
                Image image = new Image(fileUrl);
                gameImages.add(image);
            });

            startGameSlider();
        }
    }

    private void startMovieSlider() {
        if (movieImages != null && !movieImages.isEmpty()) {
            movieIndex = 0;
            movieImageSlider.setImage(movieImages.get(movieIndex));

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                movieIndex = (movieIndex + 1) % movieImages.size();
                movieImageSlider.setImage(movieImages.get(movieIndex));
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    private void startGameSlider() {
        if (gameImages != null && !gameImages.isEmpty()) {
            gameIndex = 0;
            gameImageSlider.setImage(gameImages.get(gameIndex));

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                gameIndex = (gameIndex + 1) % gameImages.size();
                gameImageSlider.setImage(gameImages.get(gameIndex));
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
}
