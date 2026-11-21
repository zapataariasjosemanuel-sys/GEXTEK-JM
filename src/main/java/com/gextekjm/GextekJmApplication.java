package com.gextekjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class GextekJmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GextekJmApplication.class, args);
    }
}
