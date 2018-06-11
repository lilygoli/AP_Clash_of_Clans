package com.company.UIs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;

public class SceneGestures {
    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = 0.8d;
    private static final boolean canZoom = true;


    PannableCanvas canvas;

    private DragContext sceneDragContext = new DragContext();

    public SceneGestures(PannableCanvas canvas) {
        this.canvas = canvas;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.05;
            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;
            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double fy = (scale / oldScale) - 1;

            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight() / 2 + canvas.getBoundsInParent().getMinY()));
            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth() / 2 + canvas.getBoundsInParent().getMinX()));

            canvas.setScale(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            canvas.setPivot(fy * dx, fy * dy);

            event.consume();
        }

    };

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {
            if (!event.isPrimaryButtonDown())
                return;

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = canvas.getTranslateX();
            sceneDragContext.translateAnchorY = canvas.getTranslateY();

        }

    };
    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            if (!event.isPrimaryButtonDown())
                return;

            double x = sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX;
            double y = sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY;
//            Circle circle = new Circle(x , y , 10);
//            circle.setFill(Color.RED);
//            canvas.getChildren().add(circle);
//            if (x < 0){
//                x = 0;
//            }
            canvas.setTranslateY(y);
            canvas.setTranslateX(x);

            event.consume();
        }
    };


    public static double clamp(double value, double min, double max) {
        if (Double.compare(value, min) < 0)
            return min;

        if (Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
