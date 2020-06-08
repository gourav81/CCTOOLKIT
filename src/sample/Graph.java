package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
class myException extends Exception{
    myException(String s){
        super(s);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);

        // set content text
        String message = s;
        a.setContentText(message);

        // show the dialog
        a.show();
    }
}
public class Graph implements Initializable {
    @FXML
    AnchorPane root;
    @FXML
    Button but;
    @FXML
    TextArea txtAr;
    @FXML
    CheckBox isDirectedCheckBox;
    @FXML
    CheckBox isWeightedCheckBox;



    boolean firstTime = true;
    int HEIGHT = 650, WIDTH = 900;
    int totalNodes, totalEdges;
    ArrayList<Vertex1> nodes = new ArrayList<>();// NOTE :: i index node is at index i-1
    ArrayList<Edge> edges = new ArrayList<>();
    ArrayList<Pair<Integer, Integer>>[] graph = new ArrayList[100];
    ArrayList<Arrow> arrows = new ArrayList<>();
    ArrayList<Pair<Text, Pair<Integer, Integer>>> weightsDisplay = new ArrayList<>();

    //Dragging stuff
    HashMap<Circle, Integer> circToIndexMap = new HashMap<>();//index being dragged
    Vertex1 draggedVertex = null; // vertex being dragged
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    ArrayList<Edge>[] edgesStartToIndex = new ArrayList[100];//edges starting with this index
    ArrayList<Edge>[] edgesEndToIndex = new ArrayList[100];//edges ending with this index
    boolean dragDirected = false;
    boolean isWeighted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        isDirectedCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue){
                if(newValue){
                    isWeightedCheckBox.setSelected(!newValue);
                }
            }
        });

        isWeightedCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    isDirectedCheckBox.setSelected(!newValue);
                }
            }
        });
    }
    public void plotClicked(ActionEvent E){
        ArrayList<Integer> input;
        input = initInput();
        if(firstTime == false){
            clearGraph();
        }
        firstTime = false;
        if(isDirectedCheckBox == null || isDirectedCheckBox.isSelected() == false){
            //Undirected Graph
            dragDirected = false;
            if((isWeightedCheckBox == null || isWeightedCheckBox.isSelected() == false)){
                isWeighted = false;
            }else{
                isWeighted = true;
            }
            makeGraphUndirected(input);
            addToRoot();
        }else{
            //Directed Graph
            isWeighted = false;
            dragDirected = true;
            makeGraphDirected(input);
            addToRoot();
        }
    }
    void addToRoot(){
        //Adding Graph to root
        //Adding edges
        for(int i=0; edges.size()>i; i++){
            root.getChildren().add(edges.get(i).edgeShow);
        }
        for(int i=0; nodes.size()>i; i++){
            //Adding nodes to root

            //Dragging feature
            nodes.get(i).cir.setCursor(Cursor.CROSSHAIR);
            nodes.get(i).cir.setOnMousePressed(circleOnMousePressedEventHandler);
            nodes.get(i).cir.setOnMouseDragged(circleOnMouseDraggedEventHandler);

            circToIndexMap.put(nodes.get(i).cir, i+1);
            root.getChildren().add(nodes.get(i).cir);
            if(dragDirected == false){
                root.getChildren().add(nodes.get(i).indexShow);
            }
        }
        if(isWeighted){
            for(int i=0; weightsDisplay.size()>i; i++){
                root.getChildren().add(weightsDisplay.get(i).getKey());
            }
        }
        if(dragDirected){
            //adding arrows
            for(int i=0;edges.size()>i; i++){
                Arrow temp = new Arrow(edges.get(i).v1.x, edges.get(i).v1.y, edges.get(i).v2.x, edges.get(i).v2.y);
                arrows.add(temp);
                root.getChildren().add(temp);
            }
            for(int i=0; nodes.size()>i; i++){
                root.getChildren().add(nodes.get(i).indexShow);
            }
        }
    }

    //converts input from textArea into list of integers
    public ArrayList<Integer> initInput(){

        ArrayList<Integer> ans = new ArrayList<>();
        try{
            String inText = txtAr.getText();
            ArrayList<String> fileOutput = new ArrayList<>();
            String [] temp;
            temp = inText.split("\\s+");
            for(int i=0; temp.length>i; i++){
                ans.add(Integer.parseInt(temp[i]));
            }
            return ans;
        }catch (Exception ex){
            try{
                clearGraph();
                throw new myException("Invalid Input");
            }catch (myException ex1){

            }
        }
        return ans;
    }
    public void makeGraphUndirected(ArrayList<Integer> input){
        try {
            totalNodes = input.get(0);
            totalEdges = input.get(1);
            //Init graph array lists
            for (int i = 1; totalNodes >= i; i++) {
                graph[i] = new ArrayList<>();
                edgesEndToIndex[i] = new ArrayList<>();
                edgesStartToIndex[i] = new ArrayList<>();
            }
            //give random x, y to node
            for (int i = 1; totalNodes >= i; i++) {
                nodes.add(new Vertex1(HEIGHT, WIDTH, i));
            }
            if (isWeighted == false) {
                for (int i = 2, j = 0; input.size() > i && totalEdges > j; i += 2, j++) {
                    graph[input.get(i)].add(new Pair(input.get(i + 1), -1));
                    graph[input.get(i + 1)].add(new Pair(input.get(i), -1));
                }
            } else {
                for (int i = 2, j = 0; input.size() > i && totalEdges > j; i += 3, j++) {
                    Text wdisplay = new Text(Integer.valueOf(input.get(i + 2)).toString());
                    Vertex1 v1 = nodes.get(input.get(i) - 1);
                    Vertex1 v2 = nodes.get(input.get(i + 1) - 1);
                    wdisplay.setX((v1.x + v2.x) / 2);
                    wdisplay.setY((v1.y + v2.y) / 2);
                    wdisplay.setStroke(Color.DARKSLATEGRAY);
                    wdisplay.setFill(Color.YELLOW);
                    wdisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 25));

                    weightsDisplay.add(new Pair(wdisplay, new Pair(v1.index, v2.index)));

                    graph[input.get(i)].add(new Pair(input.get(i + 1), input.get(i + 2)));
                    graph[input.get(i + 1)].add(new Pair(input.get(i), input.get(i + 2)));
                }
            }


            for (int i = 1; totalNodes >= i; i++) {
                Vertex1 v1 = nodes.get(i - 1);
                for (int j = 0; graph[i].size() > j; j++) {
                    Vertex1 v2 = nodes.get(graph[i].get(j).getKey() - 1);
                    Edge ed = new Edge(v1, v2, false, false);
                    edges.add(ed);
                    edgesStartToIndex[v1.index].add(ed);
                    edgesEndToIndex[v2.index].add(ed);
                }
            }
        }catch (Exception ex){
            try{
                clearGraph();
                throw new myException("Invalid Input");
            }catch (myException ex1){

            }
        }
    }


    void makeGraphDirected(ArrayList<Integer> input){
        try {
            totalNodes = input.get(0);
            totalEdges = input.get(1);
            //Init graph array lists
            for (int i = 1; totalNodes >= i; i++) {
                graph[i] = new ArrayList<>();
                edgesEndToIndex[i] = new ArrayList<>();
                edgesStartToIndex[i] = new ArrayList<>();
            }
            //give random x, y to node
            for (int i = 1; totalNodes >= i; i++) {
                nodes.add(new Vertex1(HEIGHT, WIDTH, i));
            }
            arrows = new ArrayList<>();
            for (int i = 2, j=0; input.size() > i && totalEdges>j; i += 2, j++) {
                graph[input.get(i)].add(new Pair(input.get(i + 1), -1));
            }

            for (int i = 1; totalNodes >= i; i++) {
                Vertex1 v1 = nodes.get(i - 1);
                for (int j = 0; graph[i].size() > j; j++) {
                    Vertex1 v2 = nodes.get(graph[i].get(j).getKey() - 1);
                    Edge ed = new Edge(v1, v2, false, true);
                    edges.add(ed);
                    edgesStartToIndex[v1.index].add(ed);
                    edgesEndToIndex[v2.index].add(ed);
                }
            }
        }catch (Exception ex){
            try{
                clearGraph();
                throw new myException("Invalid Input");
            }catch (myException ex1){

            }
        }
    }

    //Clear Graph
    public void clearGraph(){
        //remove from root
        for(int i=0; nodes.size()>i; i++){
            root.getChildren().remove(nodes.get(i).cir);
            root.getChildren().remove(nodes.get(i).indexShow);
        }
        for(int i=0; edges.size()>i; i++){
            root.getChildren().remove(edges.get(i).edgeShow);
        }
        if(dragDirected == true){
            for(int i=0; arrows.size()>i; i++){
                root.getChildren().remove(arrows.get(i));
            }
            arrows.clear();
        }
        if(isWeighted == true){
            for(int i=0; weightsDisplay.size()>i; i++){
                root.getChildren().remove(weightsDisplay.get(i).getKey());
            }
            weightsDisplay.clear();
        }
        nodes.clear();
        edges.clear();
        for(int i=1; totalNodes>=i; i++){
            graph[i].clear();
            edgesStartToIndex[i].clear();
            edgesEndToIndex[i].clear();
        }
        circToIndexMap.clear();
    }

    //Dragging Functions
    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                    int index = circToIndexMap.get((Circle)(t.getSource()));
                    draggedVertex = nodes.get(index-1);
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);

                    draggedVertex.indexShow.setTranslateX(newTranslateX);
                    draggedVertex.indexShow.setTranslateY(newTranslateY);

                    int index = draggedVertex.index;
                    //Actual x and y of the dragged node
                    double x = draggedVertex.indexShow.getX() + draggedVertex.indexShow.getTranslateX();
                    double y = draggedVertex.indexShow.getY() + draggedVertex.indexShow.getTranslateY();
                    for(int i=0; edgesStartToIndex[index].size()>i; i++){
                        edgesStartToIndex[index].get(i).edgeShow.setStartX(x);
                        edgesStartToIndex[index].get(i).edgeShow.setStartY(y);
                    }
                    for(int i=0; edgesEndToIndex[index].size()>i; i++){
                        edgesEndToIndex[index].get(i).edgeShow.setEndX(x);
                        edgesEndToIndex[index].get(i).edgeShow.setEndY(y);
                    }

                    //set arrows
                    if(dragDirected == true){
                        for(int i=0; arrows.size()>i; i++){
                            root.getChildren().remove(arrows.get(i));
                        }
                        arrows.clear();
                        arrows = new ArrayList<>();
                        //adding arrows
                        for(int i=0; edgesStartToIndex[index].size()>i; i++){
                            edgesStartToIndex[index].get(i).edgeShow.setStartX(x);
                            edgesStartToIndex[index].get(i).edgeShow.setStartY(y);
                            Arrow temp = new Arrow(edgesStartToIndex[index].get(i).edgeShow.getStartX(), edgesStartToIndex[index].get(i).edgeShow.getStartY(),
                                    edgesStartToIndex[index].get(i).edgeShow.getEndX(), edgesStartToIndex[index].get(i).edgeShow.getEndY()
                            );
                            arrows.add(temp);
                            root.getChildren().add(temp);
                        }
                        for(int i=0; edgesEndToIndex[index].size()>i; i++){
                            edgesEndToIndex[index].get(i).edgeShow.setEndX(x);
                            edgesEndToIndex[index].get(i).edgeShow.setEndY(y);
                            Arrow temp = new Arrow(edgesEndToIndex[index].get(i).edgeShow.getStartX(), edgesEndToIndex[index].get(i).edgeShow.getStartY(),
                                    edgesEndToIndex[index].get(i).edgeShow.getEndX(), edgesEndToIndex[index].get(i).edgeShow.getEndY()
                            );
                            arrows.add(temp);
                            root.getChildren().add(temp);
                        }
                        for(int i=0; arrows.size()>i; i++){
                            arrows.get(i).toFront();
                        }
                        for(int i=0; nodes.size()>i; i++){
                            nodes.get(i).indexShow.toFront();
                        }
                    }
                    if(isWeighted == true){
                        for(int i=0; weightsDisplay.size()>i; i++){
                            root.getChildren().remove(weightsDisplay.get(i).getKey());
                        }
                        for(int i=0; weightsDisplay.size()>i; i++){
                            Vertex1 v1 = nodes.get(weightsDisplay.get(i).getValue().getKey() - 1);
                            Vertex1 v2 = nodes.get(weightsDisplay.get(i).getValue().getValue() - 1);
                            weightsDisplay.get(i).getKey().setX(((v1.indexShow.getX() + v1.indexShow.getTranslateX() + v2.indexShow.getX())
                                    + v2.indexShow.getTranslateX())/2);
                            weightsDisplay.get(i).getKey().setY(((v1.indexShow.getY() + v1.indexShow.getTranslateY() + v2.indexShow.getY())
                                    + v2.indexShow.getTranslateY())/2);
                        }
                        for(int i=0; weightsDisplay.size()>i; i++){
                            root.getChildren().add(weightsDisplay.get(i).getKey());
                        }
                    }
                }
            };


}
class Vertex1{
    int index;
    double x, y;
    double radius;
    Circle cir;
    Text indexShow;
    Vertex1(double HEIGHT, double WIDTH, int index){
        radius = 30;
        x = 4*radius + Math.random()*(WIDTH - 8*radius);
        y =  4*radius + Math.random()*(HEIGHT - 8*radius);


        cir = new Circle(x, y, radius, Color.hsb(150 + Math.floor(Math.random()*50),1,1));
        cir.setStroke(Color.BLACK);
        this.index = index;
        indexShow = new Text((Integer.valueOf(index)).toString());

        indexShow.setX(this.x);
        indexShow.setY(this.y);
        indexShow.setX(indexShow.getX() - indexShow.getLayoutBounds().getWidth() / 2);
        indexShow.setY(indexShow.getY() + indexShow.getLayoutBounds().getHeight() / 4);
        indexShow.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        indexShow.setTextAlignment(TextAlignment.RIGHT);

    }

}
class Edge{
    Vertex1 v1, v2;
    Boolean weighted;
    Boolean directed;
    Line edgeShow;
    Edge(Vertex1 v1, Vertex1 v2, boolean weighted, boolean directed){
        this.v1 = v1;
        this.v2 = v2;
        this.weighted = weighted;
        this.directed = directed;

        edgeShow = new Line();
        edgeShow.setStrokeWidth(5);
//        edgeShow.setFill(Color.RED);
        edgeShow.setStartX(v1.x);
        edgeShow.setStartY(v1.y);
        edgeShow.setEndX(v2.x);
        edgeShow.setEndY(v2.y);
        edgeShow.setStroke(Color.WHITE);
    }
}
class Arrow extends Path{
    private static final double defaultArrowHeadSize = 12.0;

    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize){
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.WHITE);
        setStrokeWidth(6);
//        setOpacity(0.8);
        //Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));

        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }

    public Arrow(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
}