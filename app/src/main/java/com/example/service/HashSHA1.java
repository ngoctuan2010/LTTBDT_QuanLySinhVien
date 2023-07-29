package com.example.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSHA1 {
    private static String convertToHex(byte[] data){
        StringBuffer buf =new StringBuffer();
        for(byte b : data){
            int halfbyte = (b >>> 4) & 0x0F;
            int two_haft = 0;
            do{
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            }while(two_haft++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] textByte = text.getBytes("UTF-8");
            digest.update(textByte, 0, textByte.length);
            byte[] sha1hash = digest.digest();
            return convertToHex(sha1hash);

        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
