package com.huahuo.compile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result implements java.io.Serializable {
    String stack;
    String action;
}
