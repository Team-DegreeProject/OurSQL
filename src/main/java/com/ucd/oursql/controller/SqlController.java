package com.ucd.oursql.controller;

import com.ucd.oursql.sql.parsing.ParseException;
import com.ucd.oursql.sql.parsing.SqlParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
public class SqlController {
    @PostMapping(value="/Guest/command")
    public String GetDesText(@RequestParam("command") String text) throws BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ParseException {
        System.out.println("ssssssssssssssssssss");
        InputStream target = new ByteArrayInputStream(text.getBytes());
        SqlParser parser = new SqlParser(target);
        String result=parser.parse();
        return result;
    }
}
