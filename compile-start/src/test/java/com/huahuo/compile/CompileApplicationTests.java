package com.huahuo.compile;

import com.huahuo.compile.service.BasicService;
import com.huahuo.compile.utils.CmdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class CompileApplicationTests {
    @Autowired
    BasicService basicService;


    @Test
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


}
