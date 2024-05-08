package com.huahuo.compile.service.impl;

import com.huahuo.compile.service.BasicService;
import com.huahuo.compile.utils.CmdUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BasicServiceImpl implements BasicService {
    public void deleteFile(String fileName) {
        String filePath = "C:\\compile\\" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件删除成功！");
            } else {
                System.err.println("文件删除失败！");
            }
        } else {
            System.err.println("文件不存在！");
        }
    }

    @Override
    public void overwriteFile(String text, String fileName) {
        String filePath = "C:\\compile\\" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        deleteFile("stack.txt");
        deleteFile("action.txt");
        try {
            Thread.sleep(300);
        } catch (Exception e) {

        }
    }

    @Override
    public void generateBefore() {
        deleteFile("table.txt");
        deleteFile("first.txt");
        deleteFile("follow.txt");
    }

    @Override
    public void handleCode() {
        clear();
        CmdUtils.executeLab();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    @Override
    public void handleTable() {
        String s = CmdUtils.readFileAsString("table.txt");
        String[] split = s.split("\n");
        int[] nums = Arrays.stream(split[0].split(" ")).mapToInt(Integer::parseInt).toArray();
        String[] symbols = split[1].split(" ");
        String[][] res = new String[nums[0] + 1][nums[1] + nums[2]];
        String[] actions = Arrays.copyOfRange(symbols, 0, nums[1]);
        String[] gotos = Arrays.copyOfRange(symbols, nums[1], nums[1] + nums[2]);
        for (int i = 2; i < split.length; i++) {
            res[i - 2] = handleTableLine(split[i]);
        }
        System.out.println(Arrays.deepToString(res));
    }

    String[] handleTableLine(String input) {
        List<String> results = new ArrayList<>();
        // Split the input by spaces to analyze tokens
        String[] tokens = input.split(" ");
        // Iterate over the tokens and combine them appropriately
        for (int i = 0; i < tokens.length; i++) {
            String prefix = tokens[i];
            if (i + 1 < tokens.length) {
                String number = tokens[i + 1];
                String temp = prefix + " " + number;
                if (temp.equals("t -1")) {
                    results.add("");
                } else if (temp.equals("t -2")) {
                    results.add("ACC");
                } else if (temp.charAt(0) == 't') {
                    results.add(temp.substring(2));
                } else {
                    results.add(String.valueOf(temp.charAt(0) + temp.substring(2)));
                }
                i++; // Skip the next token since it's already processed
            }
        }
        // Convert to a two-dimensional array format (list of lists)
        return results.stream().flatMap(element -> Stream.of(element.split(" "))).toArray(String[]::new);
    }

    @Override
    public void initGrammar() {
        // 定义源文件和目标文件路径
        String sourceFilePath = "C:\\compile\\grammar_init.txt";
        String targetFilePath = "C:\\compile\\grammar.txt";

        // 将源文件内容复制到目标文件
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine(); // 换行
            }
            System.out.println("文件复制成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
