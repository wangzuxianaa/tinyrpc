package com.johnchan.tinyrpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;

    private  String message;
}
