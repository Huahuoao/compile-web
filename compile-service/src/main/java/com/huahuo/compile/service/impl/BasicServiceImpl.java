package com.huahuo.compile.service.impl;

import com.huahuo.compile.service.BasicService;
import com.huahuo.compile.utils.CmdUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BasicServiceImpl implements BasicService {
    public void deleteFile(String fileName) {
        String filePath = "D:\\compile\\" + fileName;
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
        String filePath = "D:\\compile\\" + fileName;
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
    public void initGrammar() {
        // 定义源文件和目标文件路径
        String sourceFilePath = "D:\\compile\\grammar_init.txt";
        String targetFilePath = "D:\\compile\\grammar.txt";

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
