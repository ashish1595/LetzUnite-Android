package com.letzunite.letzunite.security;

import android.content.res.AssetManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 17,Aug,2017.
 */
@Singleton
public class SecureRsaEncryption {

    private static final String CHAR_SET = "UTF-8";
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String PUBLIC_KEY_FILE_NAME = "public_java.key";
    private Cipher mCipher;

    public SecureRsaEncryption(AssetManager assetManager) {
        try {
            mCipher = initCipher(assetManager);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private Cipher initCipher(AssetManager assetManager) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        InputStream in = assetManager.open(PUBLIC_KEY_FILE_NAME);
        byte[] bytes = getBytes(in);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(ALGORITHM).generatePublic(x509EncodedKeySpec));
        return cipher;
    }

    /**
     * @param keys
     * @param secureData
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public RequestData encrypt(final String keys, String secureData) throws UnsupportedEncodingException,
            BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedBytes;
        encryptedBytes = mCipher.doFinal(keys.getBytes(CHAR_SET));
        String base64EncodedKey = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        RequestData requestData=new RequestData();
        requestData.setData(secureData);
        requestData.setKey(base64EncodedKey);
        return requestData;
    }

    /**
     * @param is {@link InputStream}
     * @return
     * @throws IOException
     */
    private static byte[] getBytes(InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] buf;
        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
        }
        return buf;
    }
}
