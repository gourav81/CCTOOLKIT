package sample;
import com.sun.javafx.geom.Edge;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


import javafx.scene.text.Font;

import javafx.util.Pair;

import javax.print.DocFlavor;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

class Vertex
{
    public String name;
    public double x,y;
    public Circle circle;
    public Text text;
    public Vector < Pair < Vertex,Line > > vt = new Vector< Pair<Vertex,Line> >();
    Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        circle = new Circle((double)(x), (double)(y), 25, Color.BLUE);
        circle.setStroke(Color.PEACHPUFF);
        circle.setStrokeWidth(4);
        circle.toFront();
        text = new Text((double)(x-10),(double)(y+10),name);
        text.setFont(new Font(24));
        text.setFill(Color.WHITE);
    }
    boolean add_edge(Vertex a) {
        for(int i=0;i<vt.size();i++){
            if(vt.get(i).getKey().name.equals(a.name)){
                return false;
            }
        }
        Line line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(a.x);
        line.setEndY(a.y);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(4);
        line.toBack();
        Pair<Vertex, Line> p = new Pair<Vertex, Line>(a, line);
        vt.add(p);
        return true;
    }
}

class Weighted_Vertex{
    public String name;
    public double x,y;
    public Circle circle;
    public Text text;
    public Vector <  Pair < Weighted_Vertex,Pair < Integer, Pair <Line,Text> > > > vt = new Vector<  Pair < Weighted_Vertex,Pair < Integer, Pair <Line,Text> > > >();
    Weighted_Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        circle = new Circle((double)(x), (double)(y), 25, Color.BLUE);
        circle.setStroke(Color.PEACHPUFF);
        circle.setStrokeWidth(4);
        circle.toFront();
        text = new Text((double)(x-10),(double)(y+10),name);
        text.setFont(new Font(24));
        text.setFill(Color.WHITE);
    }
    boolean add_edge(Weighted_Vertex a,int w) {
        for(int i=0;i<vt.size();i++){
            if(vt.get(i).getKey().name.equals(a.name)){
                return false;
            }
        }
        Line line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(a.x);
        line.setEndY(a.y);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(4);
        double t1 = (this.x+a.x)/2;
        double t2 = (this.y+a.y)/2;
        Text text = new Text(t1,t2+25,Integer.toString(w));
        text.setFont(new Font(18));
        text.setFill(Color.WHITE);
        Pair <Line,Text> p2 = new Pair<Line,Text>(line,text);
        Pair <Integer , Pair<Line,Text> > p3 = new Pair<Integer, Pair<Line, Text>>(w,p2);
        Pair < Weighted_Vertex,Pair < Integer, Pair <Line,Text> > > p1 = new Pair < Weighted_Vertex,Pair < Integer, Pair <Line,Text> > >(a,p3);
        vt.add(p1);
        return true;
    }
}

public class Algorithm {

    @FXML
    public AnchorPane root;
    @FXML
    public AnchorPane root1;
    @FXML
    TextField from1,to1,from2,to2;

    public static int t=0,t1=0;
    public static Circle cr1,cr2;
    public Vector <Vertex> v = new Vector<Vertex>();
    public Vector <Circle> cr = new Vector<Circle>();
    public SequentialTransition sqt = new SequentialTransition();
    public Vector <Weighted_Vertex> wv = new Vector<Weighted_Vertex>();
    public Vector <Pair<Weighted_Vertex,Pair<Weighted_Vertex,Integer> > > edges = new Vector <Pair<Weighted_Vertex,Pair<Weighted_Vertex,Integer> > > ();
    public int timer=0;

    Map <String,Integer> visited = new HashMap<>();
    Map <String,Integer> tin = new HashMap<>();
    Map <String,Integer> low = new HashMap<>();
    public void add_vertex(String name, double x, double y){
        Vertex a = new Vertex(name,x,y);
        v.add(a);
        cr.add(a.circle);
        a.circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.isControlDown()) {
                    t++;
                    if (Algorithm.t % 2 == 1) {
                        a.circle.setFill(Color.YELLOW);
                        cr1=a.circle;
                    } else {
                        a.circle.setFill(Color.SPRINGGREEN);
                        cr2=a.circle;
                        add_edge(cr1,cr2);
                        cr1.setFill(Color.BLUE);
                        cr2.setFill(Color.BLUE);
                    }
                }
            }
        });
        root.getChildren().add(a.circle);
        root.getChildren().add(a.text);
    }
    public void add_vertex1(String name, double x, double y){
        Weighted_Vertex a = new Weighted_Vertex(name,x,y);
        wv.add(a);
        a.circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.isControlDown()) {
                    t1++;
                    if (Algorithm.t1 % 2 == 1) {
                        a.circle.setFill(Color.YELLOW);
                        cr1=a.circle;
                    } else {
                        a.circle.setFill(Color.SPRINGGREEN);
                        cr2=a.circle;
                        add_edge1(cr1,cr2);
                        cr1.setFill(Color.BLUE);
                        cr2.setFill(Color.BLUE);
                    }
                }
            }
        });
        root1.getChildren().add(a.circle);
        root1.getChildren().add(a.text);
    }
    public void add_edge(Circle a, Circle b){
        for(int i=0;i<v.size();i++){
            if(v.get(i).circle == a){
                for(int j=0;j<v.size();j++){
                    if(v.get(j).circle == b){
                        boolean temp = v.get(i).add_edge(v.get(j));
                        if(temp ==false){
                            return;
                        }
                        v.get(j).add_edge(v.get(i));
                        for(int k=0;k<v.get(i).vt.size();k++){
                            if(v.get(i).vt.get(k).getKey() == v.get(j)){
                                root.getChildren().add(v.get(i).vt.get(k).getValue());
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    public void add_edge1(Circle a, Circle b){
        for(int i=0;i<wv.size();i++){
            if(wv.get(i).circle == a){
                for(int j=0;j<wv.size();j++){
                    if(wv.get(j).circle == b){
                        TextInputDialog dialog = new TextInputDialog("Enter weight to be added");
                        dialog.showAndWait();
                        String w = dialog.getEditor().getText();
                        int weight = Integer.parseInt(w);
                        boolean temp = wv.get(i).add_edge(wv.get(j),weight);
                        if(temp == false){
                            return;
                        }
                        Pair <Weighted_Vertex,Integer> p1 = new Pair<Weighted_Vertex,Integer>(wv.get(j),weight);
                        Pair <Weighted_Vertex,Pair <Weighted_Vertex,Integer>> p2 = new Pair<>(wv.get(i),p1);
                        edges.add(p2);
                        for(int k=0;k<wv.get(i).vt.size();k++){
                            if(wv.get(i).vt.get(k).getKey() == wv.get(j)){
                                root1.getChildren().add(wv.get(i).vt.get(k).getValue().getValue().getKey());
                                root1.getChildren().add(wv.get(i).vt.get(k).getValue().getValue().getValue());
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    public void add_on_click(MouseEvent e){
        try {
            if (!e.isControlDown()) {
                double x = e.getX();
                double y = e.getY();
                TextInputDialog dialog = new TextInputDialog("Enter name of vertex to be added");
                dialog.showAndWait();
                String str = dialog.getEditor().getText();
                for (int i=0;i<v.size();i++){
                    if(v.get(i).name.equals(str)) {
                        throw new Exception();
                    }
                }
                add_vertex(str, x, y);
            }
        }
        catch (Exception a){
            Alert sh = new Alert(Alert.AlertType.ERROR);
            sh.setContentText("Please Input Correct Details");
            sh.setHeaderText("Something Gone Wrong");
            sh.showAndWait();
        }
    }
    public void add_on_click1(MouseEvent e){
        try {
            if (!e.isControlDown()) {
                double x = e.getX();
                double y = e.getY();
                TextInputDialog dialog = new TextInputDialog("Enter name of vertex to be added");
                dialog.showAndWait();
                String str = dialog.getEditor().getText();
                for (int i=0;i<wv.size();i++){
                    if(wv.get(i).name.equals(str)) {
                        throw new Exception();
                    }
                }
                add_vertex1(str, x, y);
            }
        }
        catch (Exception a){
            Alert sh = new Alert(Alert.AlertType.ERROR);
            sh.setContentText("Please Input Correct Details");
            sh.setHeaderText("Something Gone Wrong");
            sh.showAndWait();
        }
    }
    public void dfs2(String u,Map<String,Integer> mp){
        mp.put(u,new Integer(1));
        for(int i=0;i<v.size();i++){
            if(v.get(i).name == u){
                FillTransition fill = new FillTransition();
                fill.setDuration(Duration.millis(750));
                fill.setFromValue(Color.BLUE);
                fill.setToValue(Color.YELLOW);
                fill.setShape(v.get(i).circle);
                sqt.getChildren().add(fill);
                for(int j=0;j<v.get(i).vt.size();j++){
                    if(!mp.containsKey(v.get(i).vt.get(j).getKey().name)){
                        dfs2(v.get(i).vt.get(j).getKey().name,mp);
                    }
                }
                FillTransition fill1 = new FillTransition();
                fill1.setDuration(Duration.millis(750));
                fill1.setFromValue(Color.YELLOW);
                fill1.setToValue(Color.BLUE);
                fill1.setShape(v.get(i).circle);
                sqt.getChildren().add(fill1);
                break;
            }
        }
    }
    public void dfs(ActionEvent e){
        sqt.getChildren().clear();
        Map<String,Integer> mp = new HashMap<String, Integer>();
        for(int i=0;i<v.size();i++){
            if(!mp.containsKey(v.get(i).name))
                dfs2(v.get(i).name, mp);
        }
        sqt.play();
    }
    public void bfs(ActionEvent e){
        sqt.getChildren().clear();
        Map<String,Integer> mp = new HashMap<String, Integer>();
        for(int i=0;i<v.size();i++) {
            if(!mp.containsKey(v.get(i).name)) {
                Queue<String> q = new LinkedList<String>();
                q.add(v.get(i).name);
                mp.put(v.get(i).name,new Integer(1));
                FillTransition fill = new FillTransition();
                fill.setDuration(Duration.millis(750));
                fill.setFromValue(Color.BLUE);
                fill.setToValue(Color.YELLOW);
                fill.setShape(v.get(i).circle);
                sqt.getChildren().add(fill);
                while(q.size()!=0){
                    String str = q.peek();
                    q.remove();
                    for(int j=0;j<v.size();j++){
                        if(v.get(j).name == str){

                            for(int k=0;k<v.get(j).vt.size();k++){
                                if(!mp.containsKey(v.get(j).vt.get(k).getKey().name)){
                                    FillTransition fill2 = new FillTransition();
                                    fill2.setDuration(Duration.millis(750));
                                    fill2.setFromValue(Color.BLUE);
                                    fill2.setToValue(Color.YELLOW);
                                    fill2.setShape(v.get(j).vt.get(k).getKey().circle);
                                    sqt.getChildren().add(fill2);
                                    mp.put(v.get(j).vt.get(k).getKey().name,new Integer(1));
                                    q.add(v.get(j).vt.get(k).getKey().name);
                                }
                            }
                            FillTransition fill1 = new FillTransition();
                            fill1.setDuration(Duration.millis(750));
                            fill1.setFromValue(Color.YELLOW);
                            fill1.setToValue(Color.BLUE);
                            fill1.setShape(v.get(j).circle);
                            sqt.getChildren().add(fill1);
                            break;
                        }
                    }
                }
            }
        }
        sqt.play();
    }

    public void djkistra(ActionEvent e){
        String from = from1.getText();
        String to =to1.getText();
        Map<String, Integer> spt = new HashMap<String, Integer>();
        Map<String, Integer> level = new HashMap<String, Integer>();
        Map<String, String> par = new HashMap<String, String>();
        for (int i = 0; i < wv.size(); i++) {
            String name = wv.get(i).name;
            spt.put(name, 0);
            level.put(name, 100000);
            par.put(name, "");
        }
        level.put(from, 0);
        par.put(from, from);
        for (int i = 0; i < wv.size(); i++) {
            int mn = 100000;
            int min_ind = -1;
            for (int j = 0; j < wv.size(); j++) {
                String name = wv.get(j).name;
                if (spt.get(name) == 0 && level.get(name) < mn) {
                    mn = level.get(name);
                    min_ind = j;
                }
            }
            if (min_ind == -1)
                break;
            String str = wv.get(min_ind).name;
            spt.put(str, 1);
            if (str.equals(to))
                break;
            for (int j = 0; j < wv.get(min_ind).vt.size(); j++) {
                String name = wv.get(min_ind).vt.get(j).getKey().name;
                int w = wv.get(min_ind).vt.get(j).getValue().getKey();
                if (spt.get(name) == 0 && level.get(str) + w < level.get(name)) {
                    par.put(name, str);
                    level.put(name, level.get(str) + w);
                }
            }
        }
        if(!level.containsKey(to) || level.get(to)==100000){
            return;
        }
        Vector < Pair <Double,Double> > position = new Vector<Pair<Double,Double>>();
        for (int i = 0; i < wv.size(); i++) {
            if (to.equals(wv.get(i).name)) {
                position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                break;
            }
        }
        while (!par.get(to).equals(from)) {
            String str = par.get(to);
            for (int i = 0; i < wv.size(); i++) {
                if (str.equals(wv.get(i).name)) {
                    position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                    break;
                }
            }
            to = str;
        }
        for (int i = 0; i < wv.size(); i++) {
            if (from.equals(wv.get(i).name)) {
                position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                break;
            }
        }
        for (int i = position.size() - 1; i >= 1; i--) {
            double a = position.get(i).getKey();
            double b = position.get(i).getValue();
            double c = position.get(i - 1).getKey();
            double d = position.get(i - 1).getValue();
            Line line = new Line();
            line.setStartX(a);
            line.setStartY(b);
            line.setEndX(c);
            line.setEndY(d);
            line.setStroke(Color.GREENYELLOW);
            line.setStrokeWidth(6);
            root1.getChildren().add(line);
        }
        PathTransition pathTransition = new PathTransition();
        Circle circle = new Circle();
        circle.setCenterX(1);
        circle.setCenterY(1);
        circle.setRadius(15);
        circle.setFill(Color.HOTPINK);
        circle.setStrokeWidth(20);
        root1.getChildren().add(circle);
        pathTransition.setNode(circle);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(position.get(position.size() - 1).getKey(), position.get(position.size() - 1).getValue());
        path.getElements().add(moveTo);
        for (int i = position.size() - 2; i >= 0; i--) {
            double a = position.get(i).getKey();
            double b = position.get(i).getValue();
            CubicCurveTo cubicCurveTo = new CubicCurveTo(a, b, a, b, a, b);
            path.getElements().add(cubicCurveTo);
        }

        pathTransition.setDuration(Duration.seconds(position.size() - 1));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1000);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }
    public void floyd_warshall(ActionEvent e){
        String from = from2.getText();
        String to = to2.getText();
        Map<String, Integer> level = new HashMap<String, Integer>();
        Map<String, String> par = new HashMap<String, String>();
        for (int i = 0; i < wv.size(); i++) {
            String name = wv.get(i).name;
            level.put(name, 100000);
            par.put(name, "");
        }
        level.put(from, 0);
        par.put(from, from);
        for(int i=0;i<wv.size()-1;i++){
            for(int j=0;j<edges.size();j++){
                String a = edges.get(j).getKey().name;
                String b = edges.get(j).getValue().getKey().name;
                int weight = edges.get(j).getValue().getValue();
                if(level.get(a)<100000 && (level.get(a)+weight <level.get(b)) ){
                    level.put(b,level.get(a)+weight);
                    par.put(b,a);
                }
            }
        }
        if(!level.containsKey(to) || level.get(to)==100000){
            return;
        }
        Vector < Pair <Double,Double> > position = new Vector<Pair<Double,Double>>();
        for (int i = 0; i < wv.size(); i++) {
            if (to.equals(wv.get(i).name)) {
                position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                break;
            }
        }
        while (!par.get(to).equals(from)) {
            String str = par.get(to);
            for (int i = 0; i < wv.size(); i++) {
                if (str.equals(wv.get(i).name)) {
                    position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                    break;
                }
            }
            to = str;
        }
        for (int i = 0; i < wv.size(); i++) {
            if (from.equals(wv.get(i).name)) {
                position.add(new Pair<Double, Double>(wv.get(i).x, wv.get(i).y));
                break;
            }
        }
        for (int i = position.size() - 1; i >= 1; i--) {
            double a = position.get(i).getKey();
            double b = position.get(i).getValue();
            double c = position.get(i - 1).getKey();
            double d = position.get(i - 1).getValue();
            Line line = new Line();
            line.setStartX(a);
            line.setStartY(b);
            line.setEndX(c);
            line.setEndY(d);
            line.setStroke(Color.LIME);
            line.setStrokeWidth(6);
            root1.getChildren().add(line);
        }
        PathTransition pathTransition = new PathTransition();
        Circle circle = new Circle();
        circle.setCenterX(1);
        circle.setCenterY(1);
        circle.setRadius(15);
        circle.setFill(Color.PURPLE);
        circle.setStrokeWidth(24);
        root1.getChildren().add(circle);
        pathTransition.setNode(circle);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(position.get(position.size() - 1).getKey(), position.get(position.size() - 1).getValue());
        path.getElements().add(moveTo);
        for (int i = position.size() - 2; i >= 0; i--) {
            double a = position.get(i).getKey();
            double b = position.get(i).getValue();
            CubicCurveTo cubicCurveTo = new CubicCurveTo(a, b, a, b, a, b);
            path.getElements().add(cubicCurveTo);
        }

        pathTransition.setDuration(Duration.seconds(position.size() - 1));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1000);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }
    public void dfs_bridges(String name, String pr){
        visited.put(name,1);
        timer++;
        tin.put(name,timer);
        low.put(name,timer);
        for(int i=0;i<v.size();i++){
            if(v.get(i).name==name){
                for(int j=0;j<v.get(i).vt.size();j++){
                    String to = v.get(i).vt.get(j).getKey().name;
                    if(to == pr){
                        continue;
                    }
                    if(visited.get(to)==1){
                        low.put(name,Math.min(low.get(name),tin.get(to)));
                    }
                    else{
                        dfs_bridges(to,name);
                        low.put(name,Math.min(low.get(name),low.get(to)));
                        if(low.get(to) > tin.get(name)){
                            v.get(i).circle.setFill(Color.PURPLE);
                            v.get(i).vt.get(j).getKey().circle.setFill(Color.PURPLE);
                            v.get(i).vt.get(j).getValue().setStroke(Color.YELLOW);
                        }
                    }
                }
            }
        }
    }
    public void bridges(ActionEvent e){
        timer=0;
        for(int i=0;i<v.size();i++){
            visited.put(v.get(i).name,0);
            tin.put(v.get(i).name,-1);
            low.put(v.get(i).name,-1);
        }
        for(int i=0;i<v.size();i++){
            if(visited.get(v.get(i).name)==0){
                dfs_bridges(v.get(i).name,"");
            }
        }
    }
    public void clear_animations (ActionEvent e) {
        sqt.stop();
        for(int i=0;i<v.size();i++) {
            v.get(i).circle.setFill(Color.BLUE);
            for(int j=0;j<v.get(i).vt.size();j++){
                v.get(i).vt.get(j).getValue().setStroke(Color.WHITE);
            }
        }
    }
}
