package ru.fizteh.fivt.students.Arsentiy_Kaptsan.shell;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Арсентий on 29.09.14.
 */
public class RunClass {
    public static void main(String[] args) {
        /*try {
            throw new RuntimeException("Hello");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }*/
        Map<String, Method> methodsMap = init();
        if (args.length == 0) {
            new Shell(methodsMap).run(System.in);
        } else {
            new Shell(methodsMap).run(args);
        }
    }
    private static Map<String, Method> init() {
        HashMap<String, Method> hMap = new HashMap<String, Method>();
        hMap.put(Cat.class.getMethods()[0].getName(), Cat.class.getMethods()[0]);
        hMap.put(Cd.class.getMethods()[0].getName(), Cd.class.getMethods()[0]);
        hMap.put(Cp.class.getMethods()[0].getName(), Cp.class.getMethods()[0]);
        hMap.put(Exit.class.getMethods()[0].getName(), Exit.class.getMethods()[0]);
        hMap.put(Help.class.getMethods()[0].getName(), Help.class.getMethods()[0]);
        hMap.put(Ls.class.getMethods()[0].getName(), Ls.class.getMethods()[0]);
        hMap.put(Mkdir.class.getMethods()[0].getName(), Mkdir.class.getMethods()[0]);
        hMap.put(Pwd.class.getMethods()[0].getName(), Pwd.class.getMethods()[0]);
        hMap.put(Rm.class.getMethods()[0].getName(), Rm.class.getMethods()[0]);
        hMap.put(Mv.class.getMethods()[0].getName(), Mv.class.getMethods()[0]);
        return hMap;
    }
}
