package gamelauncher;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class TwoTubes extends Group {

    public Rectangle topHead, lowerHead;   // the head of the lower and upper tube
    public Rectangle topBody, lowerBody;  //  the body of the lower and upper tube
    double GAP = 200;   // space between the two tubes
    Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN)};  // used to gradient the color of the tubes from light green to darkgreen
    LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);   //represents the linear color gradient for the tubes. It is defined using the stops color stops.
    Color c2 = new Color(84 / 255.0, 56 / 255.0, 71 / 255.0, 1.0);  // creation new color with specific code
    double oscillationCenter;  //  determines the center position of the tube oscillation. It is used in the animation to change the position of the gap between the tubes.
    Timeline animateTube;    // object animation of the tubes 
    int frames = 0; // determines the number of frames for the tube animation.
    int rotateOffset = 0;   //  that determines the angular offset of the tubes. It is used if the rotate variable is set to true

    
    
    
    // gapLocation object representing the current location of the gap between the tubes.
    // Pane root (container)
    //animate: A boolean value indicating whether the tubes should be animated.
    //rotate: A boolean value indicating whether the tubes should be rotated.
    
    
    public TwoTubes(SimpleDoubleProperty gapLocation, Pane root, boolean animate, boolean rotate) {
        if (rotate) {
            setRotationAxis(Rotate.Z_AXIS);  // The setRotationAxis method sets the axis of rotation to the Z axis, which means the tubes will rotate around a line perpendicular to the screen.
            setRotate(-25 + 50 * Math.random());  // sets the initial rotation of the tubes to a random angle between -25 and 25 degrees. The
            rotateOffset = 80;  //  used later in the code to adjust the position of the lower tube. 
            setTranslateY(-40);  //the tubes will be moved 40 pixels up from their initial position.
        }
        topBody = new Rectangle();   //It represents the top part of a tube.
        oscillationCenter = gapLocation.get();
        if (animate) {
            animateTube = new Timeline(new KeyFrame(Duration.millis(33), e -> {
                gapLocation.set(25 * Math.cos(Math.PI / 60 * frames) + oscillationCenter);
                frames = (frames + 1) % 120;
            }));
            animateTube.setCycleCount(Timeline.INDEFINITE);
            animateTube.play();
        }
        topBody.widthProperty().bind(root.widthProperty().divide(12.3));
        topBody.heightProperty().bind(gapLocation);
        topHead = new Rectangle();
        topHead.widthProperty().bind(root.widthProperty().divide(11.4));
        topHead.heightProperty().bind(root.heightProperty().divide(12));
        topBody.setX(4);
        topHead.yProperty().bind(gapLocation);
        lowerHead = new Rectangle();
        lowerHead.widthProperty().bind(root.widthProperty().divide(11.4));
        lowerHead.heightProperty().bind(root.heightProperty().divide(12));
        lowerBody = new Rectangle();
        lowerBody.widthProperty().bind(root.widthProperty().divide(12.3));
        lowerBody.heightProperty().bind(root.heightProperty().add(-GAP - 50 + rotateOffset).subtract(gapLocation));
        lowerBody.setX(4);
        lowerHead.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(12)));
        lowerBody.yProperty().bind(gapLocation.add(GAP).add(root.heightProperty().divide(6)));
        lowerHead.setFill(lg1);
        lowerBody.setFill(lg1);
        topHead.setFill(lg1);
        topBody.setFill(lg1);
        lowerHead.setStroke(c2);
        lowerBody.setStroke(c2);
        topHead.setStroke(c2);
        topBody.setStroke(c2);
        getChildren().addAll(topBody, topHead, lowerBody, lowerHead);
    }
}
