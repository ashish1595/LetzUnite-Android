package com.letzunite.letzunite.security;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;

/**
 * This class provide symmetric encryption(AES) based on randomly generated text, salt & IV
 */

/**
 * Created by Deven Singh on 17,Aug,2017.
 */
@Singleton
public class SecureAesEncryption {

    private final String ALGORITHM = "AES";
    private final String SECRET_KEY_ALGO = "PBKDF2WithHmacSHA1";
    private final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";
    private final String CHAR_SET_NAME = "UTF-8";
    private final static int DEFAULT_KEY_LENGTH = 256;
    private final static int DEFAULT_ITERATION_COUNT = 1000;
    private final int KEY_LENGTH;
    private final int ITERATION_COUNT;

    /**
     * for default configuration
     */
    public SecureAesEncryption() {
        this(DEFAULT_KEY_LENGTH, DEFAULT_ITERATION_COUNT);
    }

    /**
     * for manual configuration
     *
     * @param keyLength
     * @param iterationCount
     */
    public SecureAesEncryption(int keyLength, int iterationCount) {
        this.KEY_LENGTH = keyLength;
        this.ITERATION_COUNT = iterationCount;
    }

    /**
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public EncryptedData encrypt(String data) throws UnsupportedEncodingException,
            NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        byte[] bytes = data.toString().getBytes(CHAR_SET_NAME);
        return encryptData(bytes);
    }

    /**
     * @param rawData
     * @return {@link EncryptedData}
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private EncryptedData encryptData(byte[] rawData) throws InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String passKey = UUID.randomUUID().toString().substring(0, 12);
        int saltLength = KEY_LENGTH / 8; // same size as key output
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        SecretKeySpec sKeySpec = new SecretKeySpec(getKey(passKey, salt), ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
        byte[] iv = new byte[cipher.getBlockSize()];
        random.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParams);
        byte[] cipherText = cipher.doFinal(rawData);
        EncryptedData encryptedData = new EncryptedData();
        encryptedData.setKey(passKey);
        encryptedData.setSalt(Base64.encodeToString(salt, Base64.DEFAULT));
        encryptedData.setIv(Base64.encodeToString(iv, Base64.DEFAULT));
        encryptedData.setEncryptedData(Base64.encodeToString(cipherText, Base64.DEFAULT));
        return encryptedData;
    }

    /**
     * this method generates {@link javax.crypto.SecretKey} and
     * return that into encoded #byteArray
     *
     * @param passKey
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private byte[] getKey(String passKey, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec keySpec = new PBEKeySpec(passKey.toCharArray(), salt,
                ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(SECRET_KEY_ALGO);
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        return keyBytes;
    }
}
