package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;

public class Controller implements Initializable {
    @FXML
    private BorderPane border_pane;
    @FXML
    Button click, graph_plotter;
    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    //    @FXML
    public void show_dashboard11(ActionEvent e) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getResource("Algorithm.fxml"));
        border_pane.setCenter(dashboard);
//        border_pane.setCenter();
    }
    public void test_cases(ActionEvent e) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getResource("Generator.fxml"));
        border_pane.setCenter(dashboard);
    }
    public void show_graph(ActionEvent e) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getResource("Graph.fxml"));
        border_pane.setCenter(dashboard);
//        border_pane.setCenter();
    }

}
