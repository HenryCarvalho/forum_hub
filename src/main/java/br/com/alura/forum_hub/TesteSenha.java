package br.com.alura.forum_hub;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("123456 -> " + encoder.encode("123456"));
        System.out.println("654321 -> " + encoder.encode("654321"));
    }
}