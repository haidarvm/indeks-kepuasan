package com.haidarvm.indekskepuasan.controllers;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


@Controller
@RequestMapping("/dept")
public class DeptController {

    @RequestMapping("/{satisfaction}/{departmentIdImg}")
    public ResponseEntity<byte[]> getDeptSatisfyImgPath(@PathVariable String departmentIdImg, @PathVariable String satisfaction) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/dept/"  + satisfaction + "/" + departmentIdImg));
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.CREATED);
    }
}
