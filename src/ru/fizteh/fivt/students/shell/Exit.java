package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Exit {
    public static void exit(Shell shell, String[] args) {
        if (args.length != 0) {
            throw new RuntimeException("Invalid arguments count");
        }
        System.exit(0);
    }
}
