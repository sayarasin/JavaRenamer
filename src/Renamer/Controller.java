package Renamer;

import Renamer.Core.FileNamer;
import Renamer.Core.FileNotifier;
import Renamer.Core.Rule;
import Renamer.Core.Renamer;
import Renamer.Rule.GeneralRegular;
import Renamer.Rule.JAVFileRegular;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private TextField textDirectory;
    @FXML
    private Label labelNotify;
    @FXML
    private ProgressBar procDeal;

    private GeneralRegular regular = null;


    public Controller() {
        regular = new JAVFileRegular();
    }


    @FXML
    protected void FileSelect(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open RENAME directory");
        File dir = dirChooser.showDialog(null);
        textDirectory.setText(dir.getAbsolutePath());
    }

    @FXML
    protected void Start(ActionEvent event) {

        String path = textDirectory.getText().trim();
        if(!path.isEmpty()) {
            File dir = new File(path);
            if(dir.isDirectory()) {

                Task<Void> task = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {

                        Rule rule = new Rule();
                        try {
                            Renamer renamer = new Renamer(path, rule, new FileNamer() {
                                @Override
                                public String newName(String path, String oldName) throws IOException {

                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    String newName = regular.rename(oldName);
                                    if(newName == null || newName.isEmpty())
                                        return null;

                                    File newFile = new File(path, newName);
                                    return newFile.getCanonicalPath();
                                }
                            });
                            renamer.init();
                            renamer.Run(new FileNotifier() {
                                @Override
                                public boolean Notify(File oldFile, File newName, boolean trueFalse, int total, int current) {
                                    if(trueFalse)
                                        updateMessage(oldFile + "\n" + newName);
                                    else
                                        updateMessage(oldFile + "\nnot change");
                                    updateProgress(current, total);

                                    if(isCancelled()) // exit immediately
                                        return false;

                                    return  true;
                                }
                            });
                            updateMessage("Total " + renamer.getFileCount() + " files have been disposed");
                        } catch(Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                };

                procDeal.progressProperty().bind(task.progressProperty());
                labelNotify.textProperty().bind(task.messageProperty());

                new Thread(task).start();
            } else {
                labelNotify.setText("'" + path + "' is not a regular directory");
            }
        }

    }

    public void setRegular(GeneralRegular regular) {
        this.regular = regular;
    }
}
