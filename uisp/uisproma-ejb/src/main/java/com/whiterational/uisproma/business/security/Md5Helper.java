package com.whiterational.uisproma.business.security;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Helper {
  
  private static final Logger      LOGGER = LoggerFactory.getLogger(Md5Helper.class);
  
  public static String hashPassword(String password) {
    return new String(hashPassword(password.toCharArray()));
  }

  public static char[] hashPassword(char[] password) {
    char[] encoded = null;

    try {
      ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
      byte[] passwdBytes = passwdBuffer.array();
      MessageDigest mdEnc = MessageDigest.getInstance("MD5");
      mdEnc.update(passwdBytes, 0, password.length);
      encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
    } catch (NoSuchAlgorithmException ex) {
      LOGGER.error(ex.getMessage());
    }

    return encoded;
  }

}
