package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;

public class DemoApplicationTests {

    @Test
    public void test() {
        System.out.println(-3 & 0x7FFFFFFF);
        System.out.println(-30 & 0x7FFFFFFF);


//        int[] bits = new int[32];
//        int n = -1;
//        for (int i = 0; i < 32; i++) {
//            bits[i] = (n >> i) & 1;
//        }
//
//        for (int i = 31; i >= 0; i--) {
//            System.out.print(bits[i]);
//        }
    }
}
