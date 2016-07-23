package Renamer.Core;

import java.io.File;

/**
 * This is a file iterator
 * Created by sayarasin on 07/20/2016.
 */
public interface FileNotifier {
    public boolean Notify(File oldFile, File newName, boolean trueFalse,
               int total, int current);
}
