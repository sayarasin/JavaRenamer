package Renamer.Core;

import java.io.IOException;

/**
 * Created by sayarasin on 07/20/2016.
 */
public interface FileNamer {
    public String newName(String path, String oldName) throws IOException;
}
