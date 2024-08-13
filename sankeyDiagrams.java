import javafx.application.Application;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CW3_2256859_sankeyDiagrams extends Application {

    private String filePath1;
    private String filePath2;
    private String filePath3;
    private String filePath4;



    @Override
    public void start(Stage window) {

        setScenes(window); //set window
    }
    private String defaultCasePath ="/Users/wuyiqi/Desktop/CPT111CW3/defaultCase.txt";
    public String enterFilePath(PathInputStation PathInputStation, int number) {

        String enteredFilePath = defaultCasePath;
        String filePathString = null;
        //Call getTextField1() to get the text box named TextField
        // then call the getText method of this text box to get the information in the textbox
        if (number == 0) {
            filePathString = PathInputStation.getTextField1().getText();
        }
        else if(number == 1){
            filePathString = PathInputStation.getTextField2().getText();
        } else if (number == 2) {
            filePathString = PathInputStation.getTextField3().getText();
        } else if (number == 3) {
            filePathString = PathInputStation.getTextField4().getText();
        }

        java.nio.file.Path filePath = Paths.get("");

        try{
            if (Files.exists(filePath) && !filePathString.isEmpty()) {
                enteredFilePath = filePathString;
            }
            else{
                enteredFilePath = defaultCasePath;
                throw new fileNotExists("The file does not exist (Please enter file).");
            }
        }catch (fileNotExists e) {
            System.out.println(e.getMessage());
        }
        if (fileFormat(enteredFilePath)) {
            return enteredFilePath;
        }
        else {
            System.out.println("The format of this filepath(file) is invalid, the case file is used.");
            return defaultCasePath;
        }
    }
    public void setScenes(Stage window) {
        window.setMinWidth(240); // Set minimum width
        window.setMinHeight(160); // Set minimum height
        window.setMaxWidth(1500); // Set maximum width
        window.setMaxHeight(1000); // Set maximum height

        //PathInputStation
        PathInputStation PathInputStation = new PathInputStation();
        Scene setFilesScene = new Scene(PathInputStation,260,320);

        //If the submit button in the first row is pressed
        PathInputStation.getSubmitButton1().setOnAction(e1 -> {

                    filePath1 = enterFilePath(PathInputStation, 0);
            //If the submit button in the second row is pressed
            PathInputStation.getSubmitButton2().setOnAction(e2 -> {

                //Call getTextField1() to get the text box named TextField2
                //then call the getText method of this text box to get the information in the text box
                        filePath2 = enterFilePath(PathInputStation, 1);

                PathInputStation.getSubmitButton3().setOnAction(e3 -> {

                    //Call getTextField1() to get the text box named TextField3
                    //then call the getText method of this text box to get the information in the text box
                            filePath3 = enterFilePath(PathInputStation, 2);

                    PathInputStation.getSubmitButton4().setOnAction(e4 -> {

                        //Call getTextField1() to get the text box named TextField4
                        //then call the getText method of this text box to get the information in the text box
                        filePath4 = enterFilePath(PathInputStation, 3);

                        //if confirm button is clicked
                        PathInputStation.getConfirmButton().setOnAction(e5 -> {

                            //Set diagramPane1 and 2 to place the Sankey Diagram and pass the information in the text box to SankeyDiagramPanes
                            SankeyDiagramPane diagramPane1 = new SankeyDiagramPane(filePath1, window);
                            Scene scene1 = new Scene(diagramPane1, 900, 600);

                            SankeyDiagramPane diagramPane2 = new SankeyDiagramPane(filePath2, window);
                            Scene scene2 = new Scene(diagramPane2, 900, 600);

                            SankeyDiagramPane diagramPane3 = new SankeyDiagramPane(filePath3, window);
                            Scene scene3 = new Scene(diagramPane3, 900, 600);

                            SankeyDiagramPane diagramPane4 = new SankeyDiagramPane(filePath4, window);
                            Scene scene4 = new Scene(diagramPane4, 900, 600);


                            //Create the mainPane to hold the main scene with the action UI
                            MainPane mainPane = new MainPane(window, scene1, scene2, scene3, scene4,
                                    diagramPane1.getTotalTable(filePath1),
                                    diagramPane2.getTotalTable(filePath2),
                                    diagramPane3.getTotalTable(filePath3),
                                    diagramPane4.getTotalTable(filePath4));
                            Scene mainScene = new Scene(mainPane, 600, 400);

                            //control scenes
                            diagramPane1.setScene1(scene1);
                            diagramPane1.setScene2(scene2);
                            diagramPane1.setScene3(scene3);
                            diagramPane1.setScene4(scene4);
                            diagramPane2.setScene1(scene1);
                            diagramPane2.setScene2(scene2);
                            diagramPane2.setScene3(scene3);
                            diagramPane2.setScene4(scene4);
                            diagramPane3.setScene3(scene3);
                            diagramPane3.setScene2(scene2);
                            diagramPane3.setScene4(scene4);
                            diagramPane3.setScene1(scene1);
                            diagramPane4.setScene1(scene1);
                            diagramPane4.setScene2(scene2);
                            diagramPane4.setScene3(scene3);
                            diagramPane4.setScene4(scene4);

                            
                            window.setScene(mainScene); 
                            window.show();
                            window.setTitle("Sankey Diagram");
                        });
                    });
                });
            });
        });
        
        window.setTitle("Sankey Diagram");
        window.setScene(setFilesScene); 
        window.show();

    }

    public boolean fileFormat(String filePath) {

        int rowCount = 0;
        File file = new File(filePath);

        try {
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                input.nextLine();
                rowCount++;
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        if(rowCount < 4) {
            return false;
        }

        try {
            Scanner input = new Scanner(file);

            //pass first two lines
            input.nextLine();
            input.nextLine();

            while (input.hasNextLine()) {
                String name = input.nextLine();

                if(name.split(" ").length == 1) {
                    return false;
                }
                for(int i = 0; i < name.split(" ")[name.split(" ").length - 1].length();i++) {
                    if(!Character.isDigit(name.split(" ")[name.split(" ").length - 1].charAt(i))){
                        return false;
                    }
                }
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//create MainScene with button
class MainPane extends Pane {
    private Stage window;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    private String tableName1;
    private String tableName2;
    private String tableName3;
    private String tableName4;
   
    public MainPane() {
        //Implicit constructor, set for subclasses
    }
    public MainPane(Stage window, Scene scene1,Scene scene2,Scene scene3,Scene scene4,
                    String tableName1,String tableName2,String tableName3,String tableName4) {

        this.window = window;
        this.scene1 = scene1;
        this.scene2 = scene2;
        this.scene3 = scene3;
        this.scene4 = scene4;
        this.tableName1 = tableName1;
        this.tableName2 = tableName2;
        this.tableName3 = tableName3;
        this.tableName4 = tableName4;
    }

    //This method sets up the buttons and other UI on the main scene
    public void setButton(){

        getChildren().clear();

        Text select = new Text("Please select the scene you want to view");
        select.setFont(new Font(getWidth()*0.024+getHeight()*0.024));
        select.setX(getWidth()*0.15);
        select.setY(getHeight()*0.07);

        Button switchScene1Button = new Button(tableName1);
        Button switchScene2Button = new Button(tableName2);
        Button switchScene3Button = new Button(tableName3);
        Button switchScene4Button = new Button(tableName4);
        Button exitButton = new Button("Exit");

        // button location
        switchScene1Button.setLayoutX(getWidth()*0.1);
        switchScene1Button.setLayoutY(getHeight()*0.15);
        switchScene2Button.setLayoutX(getWidth()*0.6);
        switchScene2Button.setLayoutY(getHeight()*0.15);
        switchScene3Button.setLayoutX(getWidth()*0.1);
        switchScene3Button.setLayoutY(getHeight()*0.5);
        switchScene4Button.setLayoutX(getWidth()*0.6);
        switchScene4Button.setLayoutY(getHeight()*0.5);
        exitButton.setLayoutX(getWidth()*0.35);
        exitButton.setLayoutY(getHeight()*0.8);


        double m = (getWidth()*0.03+getHeight()*0.015)*0.08;

        switchScene1Button.setPrefSize(105*m, 35*m);
        switchScene2Button.setPrefSize(105*m, 35*m);
        switchScene3Button.setPrefSize(105*m, 35*m);
        switchScene4Button.setPrefSize(105*m, 35*m);
        exitButton.setPrefSize(105*m, 35*m);

        // usage
        exitButton.setOnAction(e -> window.close());
        switchScene1Button.setOnAction(e -> window.setScene(scene1));
        switchScene2Button.setOnAction(e -> window.setScene(scene2));
        switchScene3Button.setOnAction(e -> window.setScene(scene3));
        switchScene4Button.setOnAction(e -> window.setScene(scene4));

        // add button into Pane
        getChildren().addAll(exitButton, switchScene1Button,switchScene2Button,switchScene3Button,switchScene4Button,select);
    }

    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        setButton();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        setButton();
    }
}
//set PathInputStation, Used to place a UI that allows the user to enter a file path
class PathInputStation extends MainPane{

    //Set two text boxes to allow the user to enter the file path
    private TextField textBox1 = new TextField();
    private TextField textBox2 = new TextField();
    private TextField textBox3 = new TextField();
    private TextField textBox4 = new TextField();
    // Create three buttons that submit two paths
    // confirm the entered path to jump to mainScene
    Button submitButton1 = new Button("Submit");
    Button SubmitButton2 =  new Button("Submit");
    Button SubmitButton3 =  new Button("Submit");
    Button SubmitButton4 =  new Button("Submit");
    Button confirmButton = new Button("Confirm");


    @Override
    public void setButton() {

        //prompt
        textBox1.setPromptText("Please enter your file path...");
        textBox2.setPromptText("Please enter your file path...");
        textBox3.setPromptText("Please enter your file path...");
        textBox4.setPromptText("Please enter your file path...");


        //position
        textBox1.setLayoutX(80);
        textBox1.setLayoutY(50);

        textBox2.setLayoutX(80);
        textBox2.setLayoutY(100);

        textBox3.setLayoutX(80);
        textBox3.setLayoutY(150);

        textBox4.setLayoutX(80);
        textBox4.setLayoutY(200);

        submitButton1.setLayoutX(10);
        submitButton1.setLayoutY(50);

        SubmitButton2.setLayoutX(10);
        SubmitButton2.setLayoutY(100);

        SubmitButton3.setLayoutX(10);
        SubmitButton3.setLayoutY(150);

        SubmitButton4.setLayoutX(10);
        SubmitButton4.setLayoutY(200);

        confirmButton.setLayoutX(100);
        confirmButton.setLayoutY(250);

        getChildren().clear();
        getChildren().addAll(textBox1,textBox2, textBox3,textBox4,submitButton1,SubmitButton2,SubmitButton3,SubmitButton4,confirmButton);
    }

    //return the first button
    public Button getSubmitButton1() {
        return submitButton1;
    }
    //return the second button
    public Button getSubmitButton2() {
        return SubmitButton2;
    }
    //return the third button
    public Button getSubmitButton3() {
        return SubmitButton3;
    }
    //return the fourth button
    public Button getSubmitButton4() {
        return SubmitButton4;
    }
    //return the confirm button
    public Button getConfirmButton() {
        return confirmButton;
    }

    public TextField getTextField1() {
        return textBox1;
    }
    public TextField getTextField2() {
        return textBox2;
    }
    public TextField getTextField3() {
        return textBox3;
    }
    public TextField getTextField4() {
        return textBox4;
    }
}



class fileNotExists extends RuntimeException {
    public fileNotExists(String message) {
        super(message);
    }
}

class SankeyDiagramPane extends Pane{

    private String filePath;
    private Stage window;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;


    public SankeyDiagramPane(String filePath, Stage window) {
        this.filePath = filePath;
        this.window = window;
    }
    public void setScene1(Scene scene1) {
        this.scene1 = scene1;
    }
    public void setScene2(Scene scene2) {
        this.scene2 = scene2;
    }
    public void setScene3(Scene scene3) {
        this.scene3 = scene3;
    }
    public void setScene4(Scene scene4) {
        this.scene4 = scene4;
    }

    public void paint() {
        drawSankeyDiagram(getWidth(), getHeight());
    }// draw Sankey Diagram

    private void drawSankeyDiagram(double width,double height) {

        getChildren().clear();// clear scene

        // Create an ArrayList array to store the names and SortValues returned by the readFile method
        ArrayList[] Data = readFile(filePath);

        // Define categories and values
        String[] categoryNames = ((ArrayList<String>) Data[0]).toArray(new String[0]);
        Double[] categoryValues = ((ArrayList<Double>) Data[1]).toArray(new Double[0]);

        double total = 0;

        // Get total automatically with the for loop
        for(int i = 0;i<categoryValues.length;i++) {
            total +=categoryValues[i];
        }
        double spacing = 8/getFlowNumber(filePath)*height*0.05; // Gap between flows
        double nodeWidth = width * 0.1; // width of node
        double flowHeight;
        double totalFlowHeight = 0;
        for (int i = 0; i < categoryNames.length; i++) {
            flowHeight = categoryValues[i] / total * height*0.3;
            totalFlowHeight += flowHeight; //Calculate the total length of the flow, useful when drawing a rectangle later
        }

        // The Y coordinate of the center of the source node
        double startY = (height - totalFlowHeight + categoryValues[0] / total * height*0.3) * 0.5 ; //Set the initial Y coordinate
        double nextNodeStartY = spacing; // The starting Y coordinate of the next node

        // flow color table
        Map<String, String> flowColorMap = new HashMap<>();

        String[] flowCategoryColors = { "f5bb9e","ebc2ff","e8f1f9",  "fedfc4", "9ff0e4","9eeb99","e8f60d","cccfdc"};
        String[] flowColors = { "lightOrange","lightPurple","lightBlue",  "lightOrange2",  "lightTeal","lightGreen","LightYellow","grey"};

        // Target node color table
        Map<String, String> targetNodeColorMap = new HashMap<>();

        String[] flowColor = {"e86625","d175ff","ccdff1","fdb67a","36e3c9","67ed67","d1dd0c","c3c8d5"};
        String[] nodeColors = {"Orange","Purple", "Blue", "Orange2","Teal","Green","Yellow","grey"};

        //Map color tables and color names
        for (int i = 0; i < flowColor.length; i++) {
            flowColorMap.put(flowColors[i],flowCategoryColors[i]);
            targetNodeColorMap.put(nodeColors[i],flowColor[i]);
        }

        double flowStartX = 0;
        for (int i = 0; i < categoryNames.length; i++) {

            double NextflowHeight = categoryValues[categoryNames.length - 1] / total * height*0.3; //The length of the first reciprocal flow
            flowHeight = categoryValues[i] / total * height*0.3;

            //If the current flow is not the penultimate flow, the length of the next flow is obtained
            if (i + 1 < categoryNames.length) {
                NextflowHeight = categoryValues[i + 1] / total * height*0.3;
            }

            flowStartX = nodeWidth*2.8;
            double flowEndX = width / 2 + nodeWidth*1.8 - spacing; // End at the right side of the scene
            nextNodeStartY += flowHeight; //Location of the next source node

            // Create path
            Path path = new Path();
            path.getElements().add(new MoveTo(flowStartX, startY));

            // The control points are set up to create a flow effect visually
            double controlX1 = flowStartX + nodeWidth;
            double controlY1 = startY;

            double controlX2 = flowEndX - nodeWidth;
            double controlY2 = nextNodeStartY - flowHeight / 2;

            path.getElements().add(new CubicCurveTo(
                    controlX1 + width*0.04, controlY1, // Control point 1 is close to the source node
                    controlX2 - width*0.08, controlY2, // Control point 2 is close to the target node
                    flowEndX, controlY2 // The end point is centered and horizontal at the target node
            ));
            path.setStroke(Color.web(flowColorMap.get(flowColors[(i+8)%8])));
            path.setStrokeWidth(flowHeight);
            path.setFill(null);

            // draw targetNode
            Rectangle targetNode = new Rectangle(flowEndX + nodeWidth * 0.06, nextNodeStartY -  flowHeight, nodeWidth * 0.5, flowHeight);
            targetNode.setFill(Color.web(targetNodeColorMap.get(nodeColors[(i+8)%8])));

            // draw text label
            Text label = new Text(categoryNames[i] + ": " + categoryValues[i]);
            label.setFont(new Font(flowStartX*0.03+height*0.015));
            label.setX(flowEndX + nodeWidth * 0.6 + spacing*0.5); // Set the text to the right of the target node
            label.setY(nextNodeStartY -  flowHeight*0.4);

            nextNodeStartY += flowHeight + spacing; // Update the starting Y coordinate of the next node

            getChildren().addAll(path, targetNode, label);// add to SankeyDiagramPane


            // Draw source node
            Rectangle sourceNode = new Rectangle(flowStartX * 0.8, (height - totalFlowHeight) * 0.5, nodeWidth * 0.55, totalFlowHeight);
            sourceNode.setFill(Color.web("7ab6da"));
            sourceNode.setTranslateZ(100);
            getChildren().add(sourceNode);


            //Update startY to half the sum of this flow plus the width of the next flow
            //because the starting coordinate controlled by startY is the Y coordinate at the center of the flow
            startY += (flowHeight + NextflowHeight) * 0.5;
        }
        // Add a text label to the left of the source node
        Text sourceLabel = new Text(getsourceLabel(filePath));
        sourceLabel.setFont(new Font((flowStartX*0.08+height*0.005)*1.25));
        sourceLabel.setX(flowStartX * 0.12+height*0.01);
        sourceLabel.setY(height* 0.5 + totalFlowHeight*0.08 ); // Adjust the Y coordinate to be placed below the source node
        getChildren().add(sourceLabel);

        // Add a total value text tag below the figure
        Text totalLabel = new Text(getTotalTable(filePath));
        totalLabel.setFont(new Font((flowStartX*0.1 + height*0.06)*0.65));
        totalLabel.setX(width / 2 - totalLabel.getLayoutBounds().getWidth() / 2);
        totalLabel.setY(height*0.96);
        getChildren().add(totalLabel);

        setButton();//add the toggle and exit buttons
    }

    //bufferReader
    public  ArrayList[] readFile(String file) {

        ArrayList<String> names = new ArrayList<>(); //store names
        ArrayList<Double> values  = new ArrayList<>();//store values

        File newFile = new File(file);

        //Read the information in the file to create an Arraylist
        try {
            Scanner input = new Scanner(newFile);

            //Skip the first two lines because the first two lines are not valid information
            input.nextLine();
            input.nextLine();

            while (input.hasNextLine()) {

                String name = input.nextLine();
                String emptyString = ""; //Creates an empty string to concatenate SortNames

                //when i is not last
                for (int i = 0; i<name.split(" ").length-1;i++) {

                    //add i into emptyString
                    emptyString += name.split(" ")[i];

                    //If it is not the penultimate, add a space to emptyString
                    if (i != name.split(" ").length-2) {
                        emptyString += " ";
                    }
                }
                names.add(emptyString);
                values.add(Double.parseDouble(name.split(" ")[name.split(" ").length-1]));
                //Add the penultimate character to values
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        //Create a map that maps names and values after sort
        HashMap<Double, String> valueMap = new HashMap<>();

        //create relationship between names and values
        for (int i = 0; i < names.size(); i++) {
            valueMap.put(values.get(i), names.get(i));
        }

        //Reverse values, which is sorted from largest to smallest
        Collections.sort(values,Collections.reverseOrder());

        //Match the order to the order of values
        for (int i = 0; i < values.size(); i++) {
            names.set(i,valueMap.get(values.get(i)));
        }

        //Create an ArrayList [] with sortNames and values for return
        ArrayList [] arr = {names,values};
        return arr;
    }

    //This method returns the value of TotalTable, which is the value of the first line of the file
    public String getTotalTable(String filePath) {

        try{
            File file = new File(filePath);
            Scanner input = new Scanner(file);
            return input.nextLine();
        }
        catch (FileNotFoundException e) {
            System.out.println("TotalLabel not found");
            e.printStackTrace();  // Handling exception
            return null;  // Or return the appropriate default value
        }

    }

    //This method returns the value of sourceLabel, which is the value of the second line of the fileno
    public String getsourceLabel(String filePath) {

        try{
            File file = new File(filePath);
            Scanner input = new Scanner(file);
            input.nextLine();
            return input.nextLine();
        }
        catch (FileNotFoundException e) {
            System.out.println("SourceLabel not found");
            e.printStackTrace();
            return null;
        }

    }

    //return flowNumber
    public int getFlowNumber(String file) {

        File newFile = new File(file);
        int flowNumber = 0;
        try {
            Scanner input = new Scanner(newFile);
            //pass first two lines
            input.nextLine();
            input.nextLine();
            //count
            while (input.hasNextLine()) {
                input.nextLine();
                flowNumber++;
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return flowNumber;
    }


    //method of switching scenes
    private void switchScene() {

        if (window.getScene() == scene1) {
            // scene1 to scene2
            window.setScene(scene2);
        }else if (window.getScene() == scene2) {
            // scene2 to scene3
            window.setScene(scene3);
        } else if (window.getScene() == scene3) {
            // // scene3 to scene4
            window.setScene(scene4);
        }
    }
    private void switchScene2() {

        if (window.getScene() == scene2) {
            // // scene2 to scene1
            window.setScene(scene1);
        } else if (window.getScene() == scene3) {
            // scene3 to scene2
            window.setScene(scene2);
        } else if (window.getScene() == scene4) {
            // scene4 to scene3
            window.setScene(scene3);
        }
    }


    public void setButton() {


        Button shiftbutton = new Button("next scene");
        Button beforebutton = new Button("last scene");
        Button exitButton = new Button("Exit");

        //usage of button
        shiftbutton.setOnAction(e -> switchScene()); //switch scene
        beforebutton.setOnAction(e -> switchScene2());

        exitButton.setOnAction(e -> window.close());//exit program

        //location
        shiftbutton.setLayoutX(30);
        shiftbutton.setLayoutY(25);
        beforebutton.setLayoutX(30);
        beforebutton.setLayoutY(75);
        exitButton.setLayoutX(getWidth()-120);
        exitButton.setLayoutY(25);
        shiftbutton.setPrefSize(100, 30);
        beforebutton.setPrefSize(100, 30);
        exitButton.setPrefSize(100,30);
        getChildren().addAll(shiftbutton,beforebutton,exitButton);

    }


    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        paint();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        paint();
    }
}
   //The end of the project
