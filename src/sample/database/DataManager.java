package sample.database;

import com.google.gson.Gson;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.DataShape;
import sample.model.shapes.CustomShape;
import sample.model.shapes.CustomSquare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {
     private Stage stage;
    public DataManager(Stage stage){
        this.stage = stage;
    }

    public void saveObjects(ArrayList<CustomSquare> objectsOnScreen) {
        File file = openSaveFileDialog("Text files","*.txt");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
        if (printWriter != null) {
            for (CustomSquare customSquare : objectsOnScreen) {
                printWriter.println(customSquare.toJsonObject());
            }
        }
        printWriter.close();
        System.out.println("Saving successful :)");
    }

    private File  openSaveFileDialog(String fileDescription,String fileExtension){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter= new FileChooser.ExtensionFilter(fileDescription,fileExtension);
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Select a folder");
        return fileChooser.showSaveDialog(stage);
    }
    public File getFileFromOpenDialog(String fileDescription,String fileExtension){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter= new FileChooser.ExtensionFilter(fileDescription,fileExtension);
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Select an image");
        return fileChooser.showOpenDialog(stage);
    }
    public ArrayList<DataShape> getSaveData() {
        File savedFile = getFileFromOpenDialog(  "Text", "*.txt");
        ArrayList<DataShape> dataShapes = new ArrayList<>();
        Gson gson = new Gson();
        if (savedFile != null) {
            try {
                Scanner scanner = new Scanner(savedFile);
                while (scanner.hasNextLine()) {
                    //check the class type
                    String line = scanner.nextLine();
                       DataShape dataShape = gson.fromJson(line, DataShape.class);
                       dataShapes.add(dataShape);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return dataShapes;
    }
}
