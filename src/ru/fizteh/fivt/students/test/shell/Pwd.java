package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Pwd {
    public static void pwd(Shell shell, String[] args) {
        if (args.length != 0) {
            throw new RuntimeException("Invalid arguments count");
        }

        System.out.println(shell.getCurrentDirectory().toString());
    }
}
