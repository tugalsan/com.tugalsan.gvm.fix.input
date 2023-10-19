package com.tugalsan.gvm.screensize;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String... s) {
        try {
            if (s.length == 1 && Objects.equals(s[0], "size")) {//WHEN CALLED AS CONSOLE, return size
                out.print(size());
                System.exit(0);
            }
            delLogFile();
            if (!fileExists(fileSize)) {
                log("main", "fileSize not exists, will call console app...", fileSize);
                var process = Runtime.getRuntime().exec(fileConsoleApp + " size");
                process.waitFor();
                String strSize;
                try (var is = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    strSize = is.lines().collect(Collectors.joining("\n"));
                }
                txt2File(strSize, fileSize);
            }
            var strSize = file2txt(fileSize);
            log("main", "size retrived from fileSize", fileSize, strSize);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final public static Path fileSize = FileSystems.getDefault().getPath(Main.class.getPackageName() + ".size").toAbsolutePath();
    final public static Path fileConsoleApp = FileSystems.getDefault().getPath(Main.class.getPackageName() + ".exe").toAbsolutePath();

    //UTILITY FUNC SIZE
    @Deprecated //Warning: GRAALVM NO-CONSOLE NOT WORKING!!!
    public static Rectangle size() {
        var rectangle = new Rectangle(0, 0, 0, 0);
        for (var graphicsDevice : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            rectangle = rectangle.union(graphicsDevice.getDefaultConfiguration().getBounds());
        }
        return rectangle;
    }

    //UTILITY FUNC FILE
    public static boolean fileExists(Path file) {
        return !Files.isDirectory(file) && Files.exists(file);
    }

    public static void txt2File(CharSequence source, Path dest) {
        try {
            Files.writeString(dest, source, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String file2txt(Path source) {
        try {
            return Files.readString(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //UTILITY FUNC LOG
    public static void delLogFile() {
        try {
            Files.deleteIfExists(fileLog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(CharSequence funcName, Object... oa) {
        try {
            var lstStr = Arrays.stream(oa)
                    .map(o -> String.valueOf(o))
                    .collect(Collectors.toCollection(ArrayList::new));
            var str = funcName + " -> " + lstStr + "\n";
            out.print(str);
            Files.writeString(fileLog, str, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    final public static Path fileLog = FileSystems.getDefault().getPath(Main.class.getPackageName() + ".log").toAbsolutePath();
} 
