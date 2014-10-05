package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Cat {
    public static void cat(Shell shell, String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Invalid arguments count");
        }

        File filePath = shell.getCurrentDirectory().resolve(args[0]).normalize().toFile();

        if (!filePath.exists()) {
            throw new RuntimeException(String.format("%s - is not a file or directory", args[0]));
        }

        if (filePath.isDirectory()) {
            throw new RuntimeException(String.format("%s - is a directory", args[0]));
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath), shell.getBufferSize());
            while (br.ready()) {
                System.out.println(br.readLine());
            }
        } catch (Exception exc) {
            throw new RuntimeException(String.format("%s - can not read from file", args[0]));
        }
    }
}
