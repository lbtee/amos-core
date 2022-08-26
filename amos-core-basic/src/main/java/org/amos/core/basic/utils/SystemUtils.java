package org.amos.core.basic.utils;

/**
 * 操作系统鉴别工具
 */
public class SystemUtils {
    /**
     * 判断操作系统
     *
     * @return {@link String}
     */
    public static String currentSystem() {
        if (isLinux()) {
            return "linux";
        } else if (isWindows()) {
            return "windows";
        } else {
            return "other system";
        }
    }


    /**
     * 判断是否为 Linux
     */
    private static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }


    /**
     * 判断是否为 Windows
     */
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
