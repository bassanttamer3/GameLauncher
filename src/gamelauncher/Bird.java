package gamelauncher;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Bird {

    private ImageView graphics = new ImageView(); // ImageView for displaying the bird graphics
    private Image frames[]; // Array of images representing different frames of the bird animation
    private int frameCounter = 0; // Counter for tracking the current frame
    public boolean jumping = false; // Flag indicating if the bird is currently jumping
    Ellipse bounds; // Ellipse representing the bounding shape of the bird

    public ImageView getGraphics() {
        return graphics;
    }

    public Ellipse getBounds() {
        return bounds;
    }

    public Bird(Image[] frames) {
        this.frames = frames;
        this.bounds = new Ellipse(frames[0].getWidth() / 2.0, 11.5); // Create an Ellipse with the appropriate dimensions
        graphics.setImage(frames[0]); // Set the initial image frame for the bird graphics
        bounds.setFill(Color.TRANSPARENT); // Set the fill color of the bounding shape to transparent
        bounds.setStroke(Color.BLACK); // Set the stroke color of the bounding shape to black
        bounds.centerXProperty().bind(graphics.translateXProperty().add(frames[0].getWidth() / 2.0)); // Bind the centerX property of the bounding shape to the translateX property of the bird graphics
        bounds.centerYProperty().bind(graphics.translateYProperty().add(12.0)); // Bind the centerY property of the bounding shape to the translateY property of the bird graphics
        bounds.rotateProperty().bind(graphics.rotateProperty()); // Bind the rotate property of the bounding shape to the rotate property of the bird graphics
    }

    public void refreshBird() {
        graphics.setImage(frames[frameCounter++]); // Set the image frame of the bird to the next frame in the array
        if (frameCounter == 3) {
            frameCounter = 0; // Reset the frame counter to 0 if it reaches the end of the frames array
        }
    }

}
