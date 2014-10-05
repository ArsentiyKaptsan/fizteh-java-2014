package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Rm {
    public static void rm(Shell shell, String[] args) {
        boolean recursevly = false;
        Path filePath = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                recursevly = true;
            } else if (filePath == null) {
                filePath = Paths.get(args[i]);
            } else {
                throw new RuntimeException("Invalid arguments count");
            }
        }

        if (filePath == null) {
            throw new RuntimeException("Invalid arguments count");
        }


        File file = shell.getCurrentDirectory().resolve(filePath).normalize().toFile();

        if (!file.exists()) {
            throw new RuntimeException(String.format("%s - does not exist", file.toString()));
        }

        if (file.isFile()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException exc) {
                throw new RuntimeException(String.format("%s - can not remove file", file.toString()));
            }
        } else {
            if (!recursevly) {
                throw new RuntimeException(String.format("%s - can not remove directory", file.toString()));
            } else {
                try {
                    Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException e)
                                throws IOException {
                            if (e == null) {
                                Files.delete(dir);
                                return FileVisitResult.CONTINUE;
                            } else {
                                // directory iteration failed
                                throw e;
                            }
                        }
                    });
                } catch (IOException exc) {
                    throw new RuntimeException(String.format("%s - can not remove directory recursively",
                            file.toString()));
                }
            }
        }
    }
}
