package sample;
import javafx.animation.PathTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.sun.source.tree.Tree;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

public class Generator {
    @FXML
    TextField num_test_number, min_number, max_number,num_test_array, array_size, min_array, max_array;
    @FXML
    TextField num_test_matrix, min_matrix, max_matrix, rows_matrix, col_matrix;
    @FXML
    TextField num_test_str, str_size,characters,num_test_charmatrix,row_charmatrix,col_charmatrix,characters_matrix;
    @FXML
    TextField num_test_un_graph, nodes_un_graph, edges_un_graph, num_test_w_graph, nodes_w_graph, edges_w_graph, max_w_graph;
    @FXML
    TextField num_test_un_tree, num_test_w_tree, nodes_un_tree, nodes_w_tree, edges_un_tree, edges_w_tree, max_w_tree;
    @FXML
    TextArea print_test_num, print_test_array, print_test_string, print_test_graph, print_test_tree;
    @FXML
    Button gen_num, gen_array, gen_string, gen_tree, gen_graph;
    @FXML
    TitledPane random_number, random_array, random_matrix, random_string, random_charmatrix, random_ungraph, random_wgraph, random_wtree, random_untree;
    @FXML
    RadioButton n_flag_number, n_flag_array, n_flag_matrix, n_flag_string, n_flag_charmatrix, n_flag_un_dir,n_flag_w_dir, n_flag_un_NE_graph, n_flag_w_NE_graph, n_flag_un_NE_tree, n_flag_w_NE_tree;
    public void generate_number(ActionEvent E){
        try{
            if(random_number.isExpanded()){
                String num_testcases = num_test_number.getText();
                String min_val_testcases = min_number.getText();
                String max_val_testcases = max_number.getText();
                int num_test = Integer.parseInt(num_testcases);
                int lowerBound = Integer.parseInt(min_val_testcases);
                int upperBound = Integer.parseInt(max_val_testcases);
                Random random = new Random();
                ArrayList<Integer>numbers = new ArrayList<Integer>();
                for(int i=0;i<num_test;i++){
                    int a = random.nextInt(upperBound+1 - lowerBound) + lowerBound;
                    numbers.add(a);
                }
                String printing = "";
                if(n_flag_number.isSelected()){
                    printing += num_testcases.toString() + "\n";
                }
                for(int i=0;i<numbers.size();i++){
                    printing += numbers.get(i).toString();
                    printing += "\n";
                }
                print_test_num.setText(printing);
            }
            else{
                print_test_num.setText("please expand");
            }
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }
    }
    public void generate_array(ActionEvent E){
        try{
            if(random_array.isExpanded() && !random_matrix.isExpanded()){
                String num_testcases = num_test_array.getText();
                String arr_size = array_size.getText();
                String min_val = min_array.getText();
                String max_val = max_array.getText();
                int num_test = Integer.parseInt(num_testcases);
                int lowerBound = Integer.parseInt(min_val);
                int upperBound = Integer.parseInt(max_val);
                int minSize = 1;
                int maxSize = Integer.parseInt(arr_size);
                Random random = new Random();
                String printing = "";
                for(int i = 0; i < num_test; i++)
                {
//                Integer size = maxSize;
                    Integer size = random.nextInt(maxSize - minSize) + minSize;
                    if(n_flag_array.isSelected()){
                        printing += size.toString();
                    }
                    printing += "\n";
//                    System.out.println(size);
                    for(int j = 0; j < size; j++)
                    {
                        Integer a = random.nextInt(upperBound+1 - lowerBound) + lowerBound;
                        printing += a.toString() + " ";
//                        System.out.print(a + " ");
                    }
                    printing += "\n";
//                    System.out.println();
                }
                print_test_array.setText(printing);
            }
            else if(!random_array.isExpanded() && random_matrix.isExpanded()){
                String num_testcases = num_test_matrix.getText();
                String min_val = min_matrix.getText();
                String max_val = max_matrix.getText();
                String mxrow = rows_matrix.getText();
                String mxcol = col_matrix.getText();
                int num_test = Integer.parseInt(num_testcases);
                int lowerBound = Integer.parseInt(min_val);
                int upperBound = Integer.parseInt(max_val);
                Integer maxColomn = Integer.parseInt(mxcol);
                Integer maxRow = Integer.parseInt(mxrow);
                Random random = new Random();
                String printing = "";
                for(int i = 0; i < num_test; i++)
                {
                    Integer row = random.nextInt(maxRow - 1) + 1;
                    Integer colomn = random.nextInt(maxColomn - 1) + 1;
                    if(n_flag_matrix.isSelected()){
                        printing += row.toString() + " " + colomn.toString() + "\n";
                    }
                    //            System.out.println(row + " " + colomn);
                    for(int j = 0; j < row; j++) {
                        for(int k = 0; k < colomn; k++) {
                            Integer a = random.nextInt(upperBound+1 - lowerBound) + lowerBound;
                            printing += a.toString() + " ";
//                        System.out.print(a + " ");
                        }
                        printing += "\n";
//                    System.out.println();
                    }
                    printing += "\n";
//                System.out.println();
                }
                print_test_array.setText(printing);
            }
            else{
                print_test_array.setText("please expand only one");
            }
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }
    }
    public void generate_string(ActionEvent E){
        try{
            if(random_string.isExpanded() && !random_charmatrix.isExpanded()){
                String num_testcases = num_test_str.getText();
                String string_size = str_size.getText();
                String chars = characters.getText();
                int num_test = Integer.parseInt(num_testcases);
                int upperBound = chars.length();
                int maxSize = Integer.parseInt(string_size);
                Random random = new Random();
                String printing = "";
                for(int i = 0; i < num_test; i++) {
                    Integer size = random.nextInt(maxSize) + 1;
                    if(n_flag_string.isSelected()){
                        printing += size.toString();
                    }
                    printing += "\n";
                    for(int j = 0; j < size; j++) {
                        Integer idx = random.nextInt(upperBound);
                        printing += chars.charAt(idx);
                    }
                    printing += "\n";
//                    System.out.println();
                }
                print_test_string.setText(printing);
            }
            else if(!random_string.isExpanded() && random_charmatrix.isExpanded()){
                String num_testcases = num_test_charmatrix.getText();
                String r_mat = row_charmatrix.getText();
                String c_mat = col_charmatrix.getText();
                String chars = characters_matrix.getText();
                int num_test = Integer.parseInt(num_testcases);
                int upperBound = chars.length();
                int mxRow = Integer.parseInt(r_mat);
                int mxCol = Integer.parseInt(c_mat);
                Random random = new Random();
                String printing = "";
                for(int i=0;i<num_test;i++){
                    Integer Rows = random.nextInt(mxRow) + 1;
                    Integer Colomns = random.nextInt(mxCol) + 1;
                    if(n_flag_charmatrix.isSelected()){
                        printing += Rows.toString() + " " + Colomns.toString();
                    }
                    printing += "\n";
                    for(int j=0;j<Rows;j++){
                        for(int k=0;k<Colomns;k++){
                            Integer idx = random.nextInt(upperBound);
                            printing += chars.charAt(idx);
                        }
                        printing += "\n";
                    }
                }
                print_test_string.setText(printing);
            }
            else{
                print_test_string.setText("please expand only one");
            }
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void generate_graph(ActionEvent E){
        try{
            if(random_ungraph.isExpanded() && !random_wgraph.isExpanded()){
                if(n_flag_un_dir.isSelected()){ // directed unweighted graph
                    directed_unweighted_graph();
                }
                else{ //  bidirected unweighted graph
                    bidirected_unweighted_graph();
                }
            }
            else if(!random_ungraph.isExpanded() && random_wgraph.isExpanded()){
                if(n_flag_w_dir.isSelected()){ // directed weighted graph
                    directed_weighted_graph();
                }
                else{ // biderected weighted graph
                    bidirected_weighted_graph();
                }
            }
            else{
                print_test_graph.setText("please expand only one");
            }
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void generate_tree(ActionEvent E){
        try{
            if(random_untree.isExpanded() && !random_wtree.isExpanded()){
                unweighted_tree();
            }
            else if(!random_untree.isExpanded() && random_wtree.isExpanded()){
                weighted_tree();
            }
            else{
                print_test_tree.setText("please expand only one");
            }
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }
    }
    Set<Integer> path = new HashSet<Integer>();
    Integer visited[] = new Integer[100005];
    ArrayList<Integer>[] adj = new ArrayList[100005];
    boolean cycle = false;
    public void unweighted_tree(){
        try{
            String num_testcases = num_test_un_tree.getText();
            String max_nodes = nodes_un_tree.getText();
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
//                System.out.println("%%%%%%%%%%%\n\n");
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                cycle= false;
                for(int j=0;j<=num_nodes;j++){
                    visited[j] = 0;
                    adj[j] = new ArrayList<Integer>();
                }
                if(n_flag_un_NE_tree.isSelected()){
                    printing += "\n" + num_nodes.toString() + "\n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_nodes-1;j++){
                    for(int x=0;x<=num_nodes;x++){
                        visited[x] = 0;
                    }
                    path.clear();
                    cycle = false;
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    adj[a].add(b);
                    adj[b].add(a);
                    for(int x=1;x<=num_nodes;x++){
                        if(visited[x] == 0){
                            istreeCyclic(x,-1);
                        }
                    }
                    while(mark.containsKey(p1) || mark.containsKey(p2) || cycle){
                        path.clear();
                        for(int x=0;x<=num_nodes;x++){
                            visited[x] = 0;
                        }
                        for(int k=0;k<adj[a].size();k++){
                            if(adj[a].get(k) == b){
                                adj[a].remove(k);
                                break;
                            }
                        }
                        for (int k=0;k<adj[b].size();k++){
                            if(adj[b].get(k) == a){
                                adj[b].remove(k);
                                break;
                            }
                        }
                        cycle = false;
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        adj[a].add(b);
                        adj[b].add(a);
                        p1 = new Pair < Integer, Integer>(a,b);
                        p2 = new Pair < Integer, Integer>(b,a);
                        for(int x=1;x<=num_nodes;x++){
                            if(visited[x] == 0){
                                istreeCyclic(x,-1);
                            }
                        }
                    }
                    mark.put(p1,1);
                    mark.put(p2,1);
//                    System.out.println(a + "********" + b);
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + "\n";
                }
            }
            print_test_tree.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void weighted_tree(){
        try{
            String num_testcases = num_test_w_tree.getText();
            String max_nodes = nodes_w_tree.getText();
            String max_weight = max_w_tree.getText();
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            int mx_weight = Integer.parseInt(max_weight);
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
//                System.out.println("%%%%%%%%%%%\n\n");
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                cycle= false;
                for(int j=0;j<=num_nodes;j++){
                    visited[j] = 0;
                    adj[j] = new ArrayList<Integer>();
                }
                if(n_flag_w_NE_tree.isSelected()){
                    printing += "\n" + num_nodes.toString() + "\n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_nodes-1;j++){
                    for(int x=0;x<=num_nodes;x++){
                        visited[x] = 0;
                    }
                    path.clear();
                    cycle = false;
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    adj[a].add(b);
                    adj[b].add(a);
                    for(int x=1;x<=num_nodes;x++){
                        if(visited[x] == 0){
                            istreeCyclic(x,-1);
                        }
                    }
                    while(mark.containsKey(p1) || mark.containsKey(p2) || cycle){
                        path.clear();
                        for(int x=0;x<=num_nodes;x++){
                            visited[x] = 0;
                        }
                        for(int k=0;k<adj[a].size();k++){
                            if(adj[a].get(k) == b){
                                adj[a].remove(k);
                                break;
                            }
                        }
                        for (int k=0;k<adj[b].size();k++){
                            if(adj[b].get(k) == a){
                                adj[b].remove(k);
                                break;
                            }
                        }
                        cycle = false;
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        adj[a].add(b);
                        adj[b].add(a);
                        p1 = new Pair < Integer, Integer>(a,b);
                        p2 = new Pair < Integer, Integer>(b,a);
                        for(int x=1;x<=num_nodes;x++){
                            if(visited[x] == 0){
                                istreeCyclic(x,-1);
                            }
                        }
                    }
                    mark.put(p1,1);
                    mark.put(p2,1);
//                    System.out.println(a + "********" + b);
                    Integer wt = 1+ random.nextInt(mx_weight);
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + " " + wt.toString()+ "\n";
                }
            }
            print_test_tree.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void istreeCyclic(Integer node, Integer parent){
        try{
            if(cycle){
                return;
            }
            visited[node] = 1;
            path.add(node);
            for(int i=0;i<adj[node].size();i++){
                if(visited[adj[node].get(i)] == 1 && adj[node].get(i) != parent){
                    if(path.contains(adj[node].get(i))){
                        cycle = true;
//                        System.out.println(adj[node].get(i) + " ^ " + node);
                        path.remove(node);
                        return;
                    }
                }
                else if(visited[adj[node].get(i)] == 0){
                    istreeCyclic(adj[node].get(i),node);
                }
            }
            path.remove(node);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void directed_unweighted_graph(){
        try{
            String num_testcases = num_test_un_graph.getText();
            String max_nodes = nodes_un_graph.getText();
            String max_edges = edges_un_graph.getText();
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            int mx_edges = Integer.parseInt(max_edges);
            if(mx_edges > (mx_nodes * mx_nodes-1) / 2){
                mx_edges = (mx_nodes * mx_nodes-1) / 2;
            }
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                Integer num_edges = 1 + random.nextInt(mx_edges);
                if(num_edges > (num_nodes*(num_nodes-1))/2){
                    num_edges = (num_nodes*(num_nodes-1))/2;
                }
                while (num_edges > (num_nodes*(num_nodes-1))/2 && num_nodes != 1){
                    num_edges = 1 + random.nextInt(mx_edges);
                }
                if(n_flag_un_NE_graph.isSelected()){
                    printing += num_nodes.toString() + " " + num_edges.toString() + " \n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_edges;j++){
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    while(mark.containsKey(p1)){
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        p1 = new Pair < Integer, Integer>(a,b);
                        p1 = new Pair < Integer, Integer>(b,a);
                    }
                    mark.put(p1,1);
                    if(mark.containsKey(p2)){
//                        System.out.println("********");
                    }
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + "\n";
                }
                printing += "\n";
            }
            print_test_graph.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void bidirected_unweighted_graph(){
        try{
            String num_testcases = num_test_un_graph.getText();
            String max_nodes = nodes_un_graph.getText();
            String max_edges = edges_un_graph.getText();
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            int mx_edges = Integer.parseInt(max_edges);
            if(mx_edges > (mx_nodes * mx_nodes-1) / 2){
                mx_edges = (mx_nodes * mx_nodes-1) / 2;
            }
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                Integer num_edges = 1 + random.nextInt(mx_edges);
                if(num_edges > (num_nodes*(num_nodes-1))/2){
                    num_edges = (num_nodes*(num_nodes-1))/2;
                }
                while (num_edges > (num_nodes*(num_nodes-1))/2 && num_nodes != 1){
                    num_edges = 1 + random.nextInt(mx_edges);
                }
                if(n_flag_un_NE_graph.isSelected()){
                    printing += num_nodes.toString() + " " + num_edges.toString() + " \n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_edges;j++){
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    while(mark.containsKey(p1) || mark.containsKey(p2)){
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        p1 = new Pair < Integer, Integer>(a,b);
                        p2 = new Pair < Integer, Integer>(b,a);
                    }
                    mark.put(p1,1);
                    mark.put(p2,1);
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + "\n";
                }
                printing += "\n";
            }
            print_test_graph.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }

    }
    public void directed_weighted_graph(){
        try{
            String num_testcases = num_test_w_graph.getText();
            String max_nodes = nodes_w_graph.getText();
            String max_edges = edges_w_graph.getText();
            String max_weight = max_w_graph.getText();
            int mx_weight = Integer.parseInt(max_weight);
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            int mx_edges = Integer.parseInt(max_edges);
            if(mx_edges > (mx_nodes * mx_nodes-1) / 2){
                mx_edges = (mx_nodes * mx_nodes-1) / 2;
            }
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                Integer num_edges = 1 + random.nextInt(mx_edges);
                if(num_edges > (num_nodes*(num_nodes-1))/2){
                    num_edges = (num_nodes*(num_nodes-1))/2;
                }
                while (num_edges > (num_nodes*(num_nodes-1))/2 && num_nodes != 1){
                    num_edges = 1 + random.nextInt(mx_edges);
                }
                if(n_flag_w_NE_graph.isSelected()){
                    printing += num_nodes.toString() + " " + num_edges.toString() + " \n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_edges;j++){
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    while(mark.containsKey(p1)){
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        p1 = new Pair < Integer, Integer>(a,b);
                    }
                    mark.put(p1,1);
                    Integer weight = 1 + random.nextInt(mx_weight);
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + " " + weight + "\n";
                }
                printing += "\n";
            }
            print_test_graph.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }
    }
    public void bidirected_weighted_graph(){
        try{
            String num_testcases = num_test_w_graph.getText();
            String max_nodes = nodes_w_graph.getText();
            String max_edges = edges_w_graph.getText();
            String max_weight = max_w_graph.getText();
            int mx_weight = Integer.parseInt(max_weight);
            String printing = "";
            int mx_nodes = Integer.parseInt(max_nodes);
            int mx_edges = Integer.parseInt(max_edges);
            if(mx_edges > (mx_nodes * mx_nodes-1) / 2){
                mx_edges = (mx_nodes * mx_nodes-1) / 2;
            }
            Random random = new Random();
            int num_test = Integer.parseInt(num_testcases);
            for(int i=0;i<num_test;i++){
                Integer num_nodes = 1 + random.nextInt(mx_nodes);
                Integer num_edges = 1 + random.nextInt(mx_edges);
                if(num_edges > (num_nodes*(num_nodes-1))/2){
                    num_edges = (num_nodes*(num_nodes-1))/2;
                }
                while (num_edges > (num_nodes*(num_nodes-1))/2 && num_nodes != 1){
                    num_edges = 1 + random.nextInt(mx_edges);
                }
                if(n_flag_w_NE_graph.isSelected()){
                    printing += num_nodes.toString() + " " + num_edges.toString() + " \n";
                }
                Map < Pair <Integer, Integer> , Integer > mark = new HashMap< Pair <Integer, Integer>, Integer >();
                for(int j=0;j<num_edges;j++){
                    Integer a = 1 + random.nextInt(num_nodes);
                    Integer b = 1 + random.nextInt(num_nodes);
                    Pair <Integer,Integer> p1 = new Pair < Integer, Integer>(a,b);
                    Pair <Integer,Integer> p2 = new Pair < Integer, Integer>(b,a);
                    while(mark.containsKey(p1) || mark.containsKey(p2)){
                        a = 1 + random.nextInt(num_nodes);
                        b = 1 + random.nextInt(num_nodes);
                        p1 = new Pair < Integer, Integer>(a,b);
                        p2 = new Pair < Integer, Integer>(b,a);
                    }
                    mark.put(p1,1);
                    Integer weight = 1 + random.nextInt(mx_weight);
                    printing += p1.getKey().toString() + " " + p1.getValue().toString() + " " + weight + "\n";
                }
                printing += "\n";
            }
            print_test_graph.setText(printing);
        }
        catch (Exception e){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Please input correct details");
            al.setHeaderText("!!! Wrong !!!");
            al.showAndWait();
        }
    }
}