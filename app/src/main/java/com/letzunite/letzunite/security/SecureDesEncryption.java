package com.letzunite.letzunite.security;

import android.util.Base64;

import com.letzunite.letzunite.utils.Logger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by Deven Singh on 19,Aug,2017.
 */

public class SecureDesEncryption {

    private Cipher dCipher, eCipher;
    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final String CHAR_SET_NAME = "UTF8";
    private static final String TAG = "SecureDesEncryption";

    public SecureDesEncryption(String passPhrase) {
        byte[] salt = {(byte) 0xA8, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
                (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03};
        int iterationCount = 20;
        try {
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
                    iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(ALGORITHM)
                    .generateSecret(keySpec);
            eCipher = Cipher.getInstance(key.getAlgorithm());
            dCipher = Cipher.getInstance(key.getAlgorithm());
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
                    iterationCount);
            eCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (InvalidAlgorithmParameterException e) {
            Logger.error(TAG, e.getMessage());
        } catch (InvalidKeySpecException e) {
            Logger.error(TAG, e.getMessage());
        } catch (NoSuchPaddingException e) {
            Logger.error(TAG, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Logger.error(TAG, e.getMessage());
        } catch (InvalidKeyException e) {
            Logger.error(TAG, e.getMessage());
        }
    }

    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes(CHAR_SET_NAME);
            byte[] enc = eCipher.doFinal(utf8);
            String encodedString = Base64.encodeToString(enc, Base64.DEFAULT);
            return encodedString;
        } catch (BadPaddingException e) {
            Logger.error(TAG, e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Logger.error(TAG, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Logger.error(TAG, e.getMessage());
        }
        return null;
    }

    public String decryptSp(String str) {
        try {
            byte[] utf8 = str.getBytes(CHAR_SET_NAME);
            byte[] byteArray = Base64.decode(utf8, Base64.DEFAULT);
            byte[] enc = dCipher.doFinal(byteArray);
            String encodedString = new String(enc);
            return encodedString;
        } catch (BadPaddingException e) {
            Logger.error(TAG, e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Logger.error(TAG, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Logger.error(TAG, e.getMessage());
        }
        return null;
    }

}
