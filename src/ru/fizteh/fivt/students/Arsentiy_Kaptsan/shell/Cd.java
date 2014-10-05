package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Cd {
    public static void cd(Shell shell, String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Invalid arguments count");
        }

        Path dirRelativePath = Paths.get(args[0]);
        File filePath = shell.getCurrentDirectory().resolve(dirRelativePath).normalize().toFile();

        if (!filePath.exists()) {
            throw new RuntimeException(String.format("%s: directory do not exist", args[0]));
        }

        if (!filePath.isDirectory()) {
            throw new RuntimeException(String.format("%s: is not directory", args[0]));
        }

        shell.setCurrentDirectory(filePath.toPath());
    }
}
