package com.huahuo.compile.service;

public interface BasicService {
    //重置
    void initGrammar();

    public void overwriteFile(String text, String filePath);

    void clear();

    void generateBefore();

    void handleCode();
}
