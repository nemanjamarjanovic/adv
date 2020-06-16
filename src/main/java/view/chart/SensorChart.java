package view.chart;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.dto.SensorData;

/**
 *
 * @author nemanja.marjanovic
 */
public abstract class SensorChart
{

    private final BorderPane root;

    protected abstract NumberAxis createYAxis();

    protected abstract Number getYValue(SensorData sensorData);

    protected abstract Double getYLowerBound();

    protected abstract Double getYUpperBound();

    private Text currentValue = new Text();
    private final Stack<AxisStackValue> zoomStack = new Stack<>();

    public BorderPane getRoot()
    {
        return root;
    }

    public SensorChart(final LocalDate selectedDate, ObservableList<XYChart.Data<Date, Number>> result)
    {
        DateAxis xAxis = new DateAxis();
        xAxis.setLabel("Vrijeme (sekunda)");
        xAxis.setAutoRanging(false);
        Timestamp t1 = Timestamp.valueOf(selectedDate.atStartOfDay());
        Timestamp t2 = Timestamp.valueOf(selectedDate.plusDays(1).atStartOfDay());
        xAxis.setLowerBound(t1);
        xAxis.setUpperBound(t2);
        NumberAxis yAxis = createYAxis();
        yAxis.setAutoRanging(false);
        final LineChart<Date, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.getStyleClass().add("chart");
        // chart.setTitle("Istorija promjena temperature (stepen Celzijusa)");

        XYChart.Series<Date, Number> series = new XYChart.Series<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        series.setData(result);
        
        //  List<SensorData> result = getData();
//        for (SensorData sensorData : result)
//        {
//            if (sensorData.getTemperature() == 0d)
//            {
//                continue;
//            }
//            series.getData().add(new XYChart.Data(sensorData.getTime(), getYValue(sensorData)));
//        }

        chart.getData().clear();
        chart.getData().setAll(series);
        chart.setLegendVisible(false);

        final StackPane chartContainer = new StackPane();
        chartContainer.getChildren().add(chart);

        final Rectangle zoomRect = new Rectangle();
        zoomRect.setManaged(false);
        zoomRect.setFill(Color.LIGHTSEAGREEN.deriveColor(0, 1, 1, 0.5));
        chartContainer.getChildren().add(zoomRect);

        setUpZooming(zoomRect, chart, chart);

        // final VBox oc = new VBox(10);
        // oc.setPadding(new Insets(10));
//        final HBox controls = new HBox(10);
//        controls.setPadding(new Insets(10));
//        controls.setAlignment(Pos.CENTER);
        final HBox controls2 = new HBox(10);
        controls2.setPadding(new Insets(10));
        controls2.setAlignment(Pos.CENTER);
        controls2.getChildren().addAll(currentValue);
     //   oc.getChildren().addAll( controls2);

        // final Button zoomButton = new Button("Zumiraj");
        // final Button resetButton = new Button("Resetuj");
//        zoomButton.setOnAction(new EventHandler<ActionEvent>()
//        {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                doZoom(zoomRect, chart);
//            }
//        });
//        resetButton.setOnAction(new EventHandler<ActionEvent>()
//        {
//            @Override
//            public void handle(ActionEvent event)
//            {
//                final DateAxis xAxis = (DateAxis) chart.getXAxis();
//                xAxis.setAutoRanging(false);
//                Timestamp t1 = Timestamp.valueOf(selectedDate.atStartOfDay());
//                Timestamp t2 = Timestamp.valueOf(selectedDate.plusDays(1).atStartOfDay());
//                xAxis.setLowerBound(t1);
//                xAxis.setUpperBound(t2);
//                final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
//                yAxis.setLowerBound(getYLowerBound());
//                yAxis.setUpperBound(getYUpperBound());
//
//                zoomRect.setWidth(0);
//                zoomRect.setHeight(0);
//            }
//        });
        chart.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                final DateAxis xAxis = (DateAxis) chart.getXAxis();
                final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
                DecimalFormat df = new DecimalFormat("#00.00");
                currentValue.setText("Vrijeme: " + sdf.format(xAxis.getValueForDisplay(event.getX()))
                        + " - Vrijednost: " + df.format(yAxis.getValueForDisplay(event.getY())));
            }
        });

//        chart.setOnScroll(new EventHandler<ScrollEvent>() {
//
//            final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
//
//            @Override
//            public void handle(ScrollEvent event) {
//                mouseAnchor.set(new Point2D(event.getX(), event.getY()));
//                zoomRect.setWidth(0);
//                zoomRect.setHeight(0);
//                if (event.getDeltaY() <= 0) {
//
//                } else {
//                    zoomRect.setX(event.getX() - 10);
//                    zoomRect.setY(event.getY() - 10);
//                    zoomRect.setWidth(event.getX() + 10);
//                    zoomRect.setHeight(event.getY() + 10);
//                     doZoom(zoomRect, chart);
//                }
//               
//            }
//        });
//        final BooleanBinding disableControls
//                = zoomRect.widthProperty().lessThan(5)
//                .or(zoomRect.heightProperty().lessThan(5));
//        zoomButton.disableProperty().bind(disableControls);
//        controls.getChildren().addAll(zoomButton, resetButton);
        root = new BorderPane();
        root.setCenter(chartContainer);
        root.setBottom(controls2);

    }

    private void setUpZooming(final Rectangle rect, final Node zoomingNode, final LineChart chart)
    {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        zoomingNode.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    mouseAnchor.set(new Point2D(event.getX(), event.getY()));
                    rect.setWidth(0);
                    rect.setHeight(0);
                }
                else if (event.getButton().equals(MouseButton.SECONDARY))
                {
                    mouseAnchor.set(new Point2D(event.getX(), event.getY()));
                }
            }
        });
        zoomingNode.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    double x = event.getX();
                    double y = event.getY();
                    rect.setX(Math.min(x, mouseAnchor.get().getX()));
                    rect.setY(Math.min(y, mouseAnchor.get().getY()));
                    rect.setWidth(Math.abs(x - mouseAnchor.get().getX()));
                    rect.setHeight(Math.abs(y - mouseAnchor.get().getY()));
                }
                else if (event.getButton().equals(MouseButton.SECONDARY))
                {
                    long moveX = ((event.getX() - mouseAnchor.get().getX()) > 0) ? 1 : -1;
                    double moveY = ((mouseAnchor.get().getY() - event.getY()) > 0) ? 1 : -1;

                    doMoveX(-moveX, chart);
                    doMoveY(-moveY, chart);
                }
            }
        });

        zoomingNode.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton().equals(MouseButton.PRIMARY)
                        && (rect.getWidth() > 5)
                        && (rect.getHeight() > 5))
                {
                    doZoom(rect, chart);
                }
            }
        });

        zoomingNode.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton().equals(MouseButton.PRIMARY)
                        && event.getClickCount() == 2)
                {
                    resetZoom(chart);
                }
            }
        });

    }

    private void doMoveX(long value, LineChart<Date, Number> chart)
    {
        final DateAxis xAxis = (DateAxis) chart.getXAxis();

        Long factor = (xAxis.getUpperBound().getTime() - xAxis.getLowerBound().getTime()) / 200;

        Date d = new Date(xAxis.getLowerBound().getTime() + factor * value);
        xAxis.setLowerBound(d);

        d = new Date(xAxis.getUpperBound().getTime() + factor * value);
        xAxis.setUpperBound(d);
    }

    private void doMoveY(double value, LineChart<Date, Number> chart)
    {
        final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        double factor = (yAxis.getUpperBound() - yAxis.getLowerBound()) / 200;
        yAxis.setLowerBound(yAxis.getLowerBound() + value * factor);
        yAxis.setUpperBound(yAxis.getUpperBound() + value * factor);
    }

    private boolean isWithinLimit(double x1, double x2, double limit)
    {
        return ((x2 < x1) && (x2 > (x1 - limit))) || ((x2 > x1) && (x2 < (x1 + limit)));
    }

    private void doZoom(Rectangle zoomRect, LineChart<Date, Number> chart)
    {
        final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        final DateAxis xAxis = (DateAxis) chart.getXAxis();

        AxisStackValue asv = new AxisStackValue(yAxis.getLowerBound(), yAxis.getUpperBound(),
                xAxis.getLowerBound(), xAxis.getUpperBound());
        zoomStack.push(asv);

        Point2D zoomTopLeft = new Point2D(zoomRect.getX(), zoomRect.getY());
        Point2D zoomBottomRight = new Point2D(zoomRect.getX() + zoomRect.getWidth(), zoomRect.getY() + zoomRect.getHeight());

        Double lowerYpx = yAxis.getDisplayPosition(yAxis.getLowerBound())
                - (xAxis.localToScene(0, 0).getY() - chart.localToScene(zoomBottomRight).getY());
        Double upperYpx = yAxis.getDisplayPosition(yAxis.getLowerBound())
                - (xAxis.localToScene(0, 0).getY() - chart.localToScene(zoomBottomRight).getY()) - zoomRect.getHeight();

        Double lowerXpx = xAxis.getDisplayPosition(xAxis.getLowerBound())
                + (chart.localToScene(zoomTopLeft).getX() - yAxis.localToScene(0, 0).getX() - yAxis.getWidth());

        Double upperXpx = xAxis.getDisplayPosition(xAxis.getLowerBound())
                + (chart.localToScene(zoomTopLeft).getX() - yAxis.localToScene(0, 0).getX() - yAxis.getWidth()) + zoomRect.getWidth();

        Double ylb = yAxis.getValueForDisplay(lowerYpx).doubleValue();
        Double yub = yAxis.getValueForDisplay(upperYpx).doubleValue();
        Date xlb = xAxis.getValueForDisplay(lowerXpx);
        Date xub = xAxis.getValueForDisplay(upperXpx);

        yAxis.setLowerBound(ylb);
        yAxis.setUpperBound(yub);
        xAxis.setLowerBound(xlb);
        xAxis.setUpperBound(xub);

        zoomRect.setWidth(0);
        zoomRect.setHeight(0);

    }

    private void resetZoom(LineChart<Date, Number> chart)
    {
        final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        final DateAxis xAxis = (DateAxis) chart.getXAxis();

        if (!zoomStack.empty())
        {
            AxisStackValue asv = zoomStack.pop();
            yAxis.setLowerBound(asv.getYlb());
            yAxis.setUpperBound(asv.getYub());
            xAxis.setLowerBound(asv.getXlb());
            xAxis.setUpperBound(asv.getXub());
        }
    }

}
