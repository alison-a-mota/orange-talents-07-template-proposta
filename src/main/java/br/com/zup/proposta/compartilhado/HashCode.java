package br.com.zup.proposta.compartilhado;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

@Service
public class HashCode {

    public byte[] gerarHash(String s) throws NoSuchAlgorithmException {
        var algorithm = getInstance("MD5");
        return algorithm.digest(s.getBytes(StandardCharsets.UTF_8));
    }
}
