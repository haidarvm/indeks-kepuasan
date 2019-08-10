package com.haidarvm.indekskepuasan;

import com.haidarvm.indekskepuasan.Services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootApplication
public class IndeksKepuasanApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndeksKepuasanApplication.class, args);
    }


}
