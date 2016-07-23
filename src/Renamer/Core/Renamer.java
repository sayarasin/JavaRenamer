package Renamer.Core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * This is core class of renamer
 * Created by sayarasin on 07/20/2016.
 */
public class Renamer {

    private File mainPath = null;
    private Rule rule = null;
    private FileNotifier notifier = null;
    private FileNamer namer = null;
    private int maxDeeps = 0;
    private List<File> files = new LinkedList<File>();


    /**
     * Renamer construction
     * @param rootPath    scan root path
     * @param rule        scan rule
     * @throws IOException
     */
    public Renamer(String rootPath, Rule rule, FileNamer namer)
            throws IOException {

        // Check file exist?
        mainPath   = new File(rootPath);
        if(!mainPath.exists())
            throw new IOException("Renamer root path not found");
        this.rule  = rule;
        this.namer = namer;
    }


    /**
     * Iterate read file information from 'mainPath'
     * @return True or false
     */
    public boolean init() {

        maxDeeps = 0;
        this.files.clear();
        return ReadDir(mainPath, maxDeeps);
    }


    /**
     * Get file counts
     * @return Files count
     */
    public int getFileCount() {
        return files.size();
    }


    /**
     *
     * @param notifier
     * @return
     * @throws IOException
     */
    public boolean Run(FileNotifier notifier) throws IOException {
        this.notifier = notifier;
        return Run();
    }

    /**
     *
     * @return True or false
     */
    public boolean Run() throws IOException {

        int i = 0;
        for(File file : this.files) {

            boolean allRight = false;
            File rename = null;
            if(this.namer != null) {
                String newName = this.namer.newName(file.getParentFile().getCanonicalPath(),
                        file.getName());
                if(newName != null && !newName.isEmpty()) {

                    rename = new File(newName);
                    if(rename.exists()) {
                        if(rule.getCovert()) {
                            if(file.getCanonicalPath().compareToIgnoreCase(rename.getCanonicalPath()) != 0
                                    && rename.delete()
                                    && file.renameTo(rename)) {
                                allRight = true;
                            }
                        }
                    } else {
                        file.renameTo(rename);
                        allRight = true;
                    }
                }
            }
            if(this.notifier != null) {
                if(!this.notifier.Notify(file, rename, allRight,
                        getFileCount(), ++i)) {
                    // Stop loop
                    break;
                }
            }
        }

        return true;
    }


    /**
     *
     * @param dir Current scan root dir
     * @param deeps Directory deeps
     * @return True or false
     */
    public boolean ReadDir(File dir, int deeps) {


        File [] list = dir.listFiles();

        if(list == null)
            return true;

        deeps++;
        if(deeps > maxDeeps)
            maxDeeps = deeps;

        for(File file : list) {
            if(file.isDirectory()
                    && (rule.getDeeps() == -1 || deeps <= rule.getDeeps())) {
                ReadDir(file, deeps);
            }
            if(file.isFile()
                    && file.getName().compareTo(".") != 0
                    && file.getName().compareTo("..") != 0) {
                if(rule.getSuffix() == null
                        || rule.getSuffix().isEmpty()
                        || rule.getSuffix().compareTo("*") == 0) {
                    this.files.add(file);
                } else {
                    if(file.getName().endsWith("." + rule.getSuffix()))
                        this.files.add(file);
                }

            }
        }

        return true;
    }
}
