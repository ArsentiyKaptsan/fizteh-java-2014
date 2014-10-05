package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Cp {
    public static void cp(Shell shell, String[] args) {
        Path fromPath = null;
        Path toPath = null;
        boolean recursevly = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                recursevly = true;
            } else if (fromPath == null) {
                fromPath = Paths.get(args[i]);
            } else if (toPath == null) {
                toPath = Paths.get(args[i]);
            } else {
                throw new RuntimeException("Invalid arguments count");
            }
        }

        if (fromPath == null || toPath == null) {
            throw new RuntimeException("Invalid arguments count");
        }

        File fromFile = shell.getCurrentDirectory().resolve(fromPath).normalize().toFile();
        File toFile = shell.getCurrentDirectory().resolve(toPath).normalize().toFile();

        if (!fromFile.exists()) {
            throw new RuntimeException(String.format("%s - source file does not exist", fromPath.toString()));
        }

        if (toFile.toPath().startsWith(fromFile.toPath())) {
            throw new RuntimeException(String.format("%s is subPath of %s",
                    fromPath.toString(), toPath.toString()));
        }

        if (fromFile.toPath().equals(toFile.toPath())) {
            return;
        }

        if (fromFile.isDirectory() && !recursevly) {
            throw new RuntimeException(String.format("%s is a directory(not copied)",
                    fromPath.toString()));
        }

        if (fromFile.isFile()) {
            if (toFile.isDirectory()) {
                if (!toFile.exists()) {
                    throw new RuntimeException(String.format("%s: target directory does not exist",
                            toPath.toString()));
                }
                toFile = toFile.toPath().resolve(fromFile.toPath().getFileName()).toFile();
            }

            try {
                Files.copy(fromFile.toPath(), toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exc) {
                throw new RuntimeException(String.format("can not copy"));
            }
        } else {
            if (toFile.exists()) {
                if (toFile.isFile()) {
                    throw new RuntimeException(String.format("%s: target file already exist(can not overwrite)",
                            toPath.toString()));
                } else {
                    toFile = toFile.toPath().resolve(fromFile.toPath().getFileName()).toFile();
                }
            } else if (!toFile.mkdir()) {
                      throw new RuntimeException(String.format("%s: can not create directory",
                                toPath.toString()));
            }

            final Path source = fromFile.toPath();
            final Path target = toFile.toPath();
            try {
                Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                        new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                                    throws IOException {
                                Path targetdir = target.resolve(source.relativize(dir));
                                try {
                                    Files.copy(dir, targetdir);
                                } catch (FileAlreadyExistsException e) {
                                    if (!Files.isDirectory(targetdir)) {
                                        throw e;
                                    }
                                }
                                return FileVisitResult.CONTINUE;
                            }
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                    throws IOException {
                                Files.copy(file, target.resolve(source.relativize(file)));
                                return FileVisitResult.CONTINUE;
                            }
                        });
            } catch (Throwable exp) {
                throw new RuntimeException(String.format("can not copy"));
            }
        }
    }
}
