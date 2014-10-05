package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;


/**
 * Created by Арсентий on 30.09.14.
 */
public class Mv {
    public static void mv(Shell shell, String[] args) {
        String fromPath = null;
        String toPath = null;
        for (int i = 0; i < args.length; i++) {
            if (fromPath == null) {
                fromPath = args[i];
            } else if (toPath == null) {
                toPath = args[i];
            } else {
                throw new RuntimeException("Invalid arguments count");
            }
        }

        try {
            Cp.cp(shell, new String[] {"-r", fromPath, toPath});
            Rm.rm(shell, new String[] {"-r", fromPath});
        } catch (RuntimeException exc) {
            StringBuilder strExc = new StringBuilder(exc.getMessage());
            strExc = strExc.replace(0, 3 , "mv");
            throw new RuntimeException(strExc.toString());
        }
    }
}
