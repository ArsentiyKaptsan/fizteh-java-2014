package ru.fizteh.fivt.Arsentiy_Kaptsan.shell;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Арсентий on 23.09.14.
 */
public class Shell {

    private Path currentDirectory;

    private int bufferSize = 1000;

    private Map<String, Method> methodsMap;

    private boolean interactive = false;

    public Shell(Map<String, Method> methodsMap) {
        init(methodsMap);
    }

    public final int getBufferSize() {
        return bufferSize;
    }

    public final void setCurrentDirectory(Path newDirectory) {
        currentDirectory = newDirectory.normalize();
    }

    public final boolean isInteractive() {
        return interactive;
    }

    public final Path getCurrentDirectory() {
        return currentDirectory;
    }

    public final Iterator<String> getExecutableCommands() {
        return methodsMap.keySet().iterator();
    }

    public final void run(InputStream stream) {
        interactive = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(stream), bufferSize);
        try {
            while (true) {
                System.out.print(String.format("Shell: %s $ ",
                        getCurrentDirectory()));
                String commandsStr = br.readLine();

                if (commandsStr == null) {
                    break;
                }

                String[] cmds = commandsStr.trim().split(";");
                for (int i = 0; i < cmds.length; i++) {
                    boolean errors = executeCommand(cmds[i].trim());
                    if (errors) {
                        break;
                    }
                }
            }
        } catch (IOException exc) {
            System.err.println("Error while reading");
        }
    }

    public final void run(String[] args) {
        interactive = false;

        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(" " + args[i]);
        }
        String[] commands = sb.toString().split(";");
        for (int i = 0; i < commands.length; i++) {
            boolean errors = executeCommand(commands[i].trim());
            if (errors) {
                System.exit(1);
            }
        }
    }

    public final boolean executeCommand(String command) {
        if (command.isEmpty()) {
            return true;
        }
        String[] args = command.trim().split("\\s+");
        Method commandMethod = methodsMap.get(args[0]);
        if (commandMethod == null) {
            System.err.println("No such command");
            if (args[0].equals("help")) {
                Method helpMethod = methodsMap.get("help");
                if (helpMethod != null) {
                    try {
                        helpMethod.invoke(null, this, "");
                    } catch (Throwable exc) {
                        if (exc.getMessage() == null) {
                            System.err.println(String.format("%s: Execution failed with unknown error",
                                    args[0]));
                        } else {
                            System.err.println(String.format("%s: %s", args[0], exc.getMessage()));
                        }
                        return false;
                    }
                }
            }
            return false;
        }
        String[] commandArgs = Arrays.copyOfRange(args, 1 , args.length);
        try {
            commandMethod.invoke(null, this, commandArgs);
            return true;
        } catch (Throwable exc) {
            if (exc.getMessage() == null) {
                System.err.println(String.format("%s: Execution failed with unknown error",
                        args[0]));
            } else {
                System.err.println(String.format("%s: %s", args[0], exc.getMessage()));
            }
            return false;
        }
    }

    private void init(Map<String, Method> methodsMap) {
        this.methodsMap = methodsMap;
        currentDirectory = Paths.get(System.getProperty(("user.home")));
    }
}
