package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;

/**
 * Created by Арсентий on 29.09.14.
 */
public class Help {
    public static void help(Shell shell, String[] args) {
        if (args.length != 0) {
            throw new RuntimeException("Invalid arguments count");
        }

        System.out.println("This shell emulator can execute some simple commands:");
        System.out.println("pwd - current dir");
        System.out.println("ls - files in current dir");
        System.out.println("rm - delete file: 1 param - filename, 2 - '-r' recursively");
        System.out.println("cd - change current dir: 1 param - new dirname");
        System.out.println("cp - copy file: 1 param - from filename, 2 param - to filename");
        System.out.println("mkdir - make dir in current dir: 1 param - dir name");
        System.out.println("cat - show file data: 1 param - filename");
        System.out.println("help - show help");
        System.out.println("exit - exit program");
    }
}
