package com.demo.kanban.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * {@link} https://www.magata.net/memo/index.php?RSA%B8%F8%B3%AB%B8%B0%2F%C8%EB%CC%A9%B8%B0%A4%CB%A4%E8%A4%EB%B0%C5%B9%E6%B2%BD%2F%C9%FC%B9%E6%B2%BD
 * 証明書の導入がうまくいかなかったのは、PKSC8変換でPEMフォーマットで出力していたから。byteで読み込むにはDERである必要がある。
 * {@link} https://qiita.com/kunichiko/items/12cbccaadcbf41c72735
 */
@RestController
@RequestMapping(value = "/hoge")
public class HogeController {
    public static final Logger logger = LoggerFactory.getLogger(HogeController.class);

    @GetMapping
    public String index() throws IOException {
        File file = new File("jwt_authentication/private.pk8.pem");
        byte[] key = readKey(file);

        logger.debug("private Key: " + toHexString(key));

        KeySpec keyspec = new PKCS8EncodedKeySpec(key);

        try {
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            Key privateKey = keyfactory.generatePrivate(keyspec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "hoge";
    }

    private byte[] readKey(File file) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(file.getAbsolutePath());
        byte[] data = null;
        try {
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) { }
        }

        return data;
    }

    private String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (byte bt: b){
            sb.append(String.format("%02X", bt)).append(" ");
        }
        return sb.toString();
    }
}