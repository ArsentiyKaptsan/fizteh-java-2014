package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;

import java.io.File;
import java.nio.file.*;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Mkdir {
    public static void mkdir(Shell shell, String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Invalid arguments count");
        }

        Path dirRelativePath = Paths.get(args[0]);
        File filePath = shell.getCurrentDirectory().resolve(dirRelativePath).normalize().toFile();

        if (filePath.exists()) {
            throw new RuntimeException(String.format("mkdir: %s: file or directory already exists", args[0]));
        }

        try {
            if (!filePath.mkdirs()) {
                throw new RuntimeException();
            }
        } catch (Throwable exc) {
            throw new RuntimeException(String.format("mkdir: %s: directory is not created", args[0]));
        }
    }
}
