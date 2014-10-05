package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;

import java.io.File;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Ls {
    public static void ls(Shell shell, String[] args) {
        if (args.length != 0) {
            throw new RuntimeException("Invalid arguments count");
        }
        try {
            File[] filesThere = shell.getCurrentDirectory().toFile().listFiles();
            for (int i = 0; i < filesThere.length; i++) {
                System.out.println(filesThere[i].toPath().getFileName());
            }
        } catch (Throwable exc) {
            throw new RuntimeException("Can't show directory " + shell.getCurrentDirectory().toString());
        }
    }
}
