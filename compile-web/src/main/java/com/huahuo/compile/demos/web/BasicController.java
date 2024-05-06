package com.huahuo.compile.demos.web;

import com.huahuo.compile.entity.Result;
import com.huahuo.compile.entity.Table;
import com.huahuo.compile.entity.request.GenerateRequest;
import com.huahuo.compile.service.BasicService;
import com.huahuo.compile.utils.CmdUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@CrossOrigin
@RequestMapping("/basic")
public class BasicController {
    @Resource
    private BasicService basicService;

    public static String removeTrailingSpacesAndEmptyLines(String str) {
        // 去除末尾空格
        String trimmedStr = str.trim();
        // 从后向前遍历字符串，找到第一个非空白字符的位置
        int lastIndex = trimmedStr.length() - 1;
        while (lastIndex >= 0 && Character.isWhitespace(trimmedStr.charAt(lastIndex))) {
            lastIndex--;
        }
        // 截取字符串，去除末尾的空行
        return trimmedStr.substring(0, lastIndex + 1);
    }

    @PostMapping("/reset")
    public String reset() {
        basicService.initGrammar();
        return CmdUtils.readFileAsString("grammar.txt");
    }

    @PostMapping("/code")
    public List<Result> code(@RequestBody GenerateRequest request) {
        try {
            // 写入文件并处理代码 有bug 暂时不处理
//            basicService.overwriteFile(removeTrailingSpacesAndEmptyLines(request.getInput()), "test.in");
//            basicService.handleCode();
            // 读取文件内容
            List<String> actions = Files.readAllLines(Paths.get("D:\\compile\\action.txt"));
            List<String> stacks = Files.readAllLines(Paths.get("D:\\compile\\stack.txt"));
            // 构建结果列表
            return IntStream.range(0, actions.size())
                    .mapToObj(i -> new Result(stacks.get(i), actions.get(i)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList(); // 处理文件读取失败情况
        }
    }

    @PostMapping("/generate")
    public Table generate(@RequestBody GenerateRequest request) throws InterruptedException {
        //去除末尾空格空行
        //  basicService.generateBefore();
        basicService.overwriteFile(removeTrailingSpacesAndEmptyLines(request.getInput()), "grammar.txt");
        CmdUtils.executeLabTable();
        //等待语法分析完成
        Thread.sleep(500);
        Table table = new Table();
        table.setTable(CmdUtils.readFileAsString("table.txt"));
        table.setFollow(CmdUtils.readFileAsString("follow.txt"));
        table.setFirst(CmdUtils.readFileAsString("first.txt"));
        return table;
    }

    @PostMapping("/code/example")
    public String codeExample() {
        return CmdUtils.readFileAsString("code.txt");
    }
}

