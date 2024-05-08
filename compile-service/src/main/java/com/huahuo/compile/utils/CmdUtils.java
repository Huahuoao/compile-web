package com.huahuo.compile.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CmdUtils {
    public static void executeLab() {
        try {
            // 定义 CMD 命令
            String command = "cmd /c start C:\\compile\\lab.exe";

            // 调用 CMD，并执行命令
            Process process = Runtime.getRuntime().exec(command);

            // 获取命令执行的输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            // 读取输出流内容
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完毕
            int exitCode = process.waitFor();
            System.out.println("Exited with error code " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void executeLabTable() {

    }

    public static String readFileAsString(String fileName) {
        try {
            return Files.readString(Paths.get("C:\\compile\\" + fileName), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
