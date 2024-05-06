package com.huahuo.compile;

import com.huahuo.compile.service.BasicService;
import com.huahuo.compile.utils.CmdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CompileApplicationTests {
    @Autowired
    BasicService basicService;

    @Test
    void contextLoads() {
        List<String> list = Arrays.asList(CmdUtils.readFileAsString("action.txt").split("\n"));
        for (String s : list) {
            System.out.println(s);
        }
    }


}
