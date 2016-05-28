package com.felipeforbeck.vacuum.interfaces.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by fforbeck on 21/05/16.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.felipeforbeck.vacuum"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
