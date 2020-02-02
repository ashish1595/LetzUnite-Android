package com.letzunite.letzunite.data.manager;

import com.letzunite.letzunite.security.EncryptedData;
import com.letzunite.letzunite.security.RequestData;
import com.letzunite.letzunite.security.SecureAesEncryption;
import com.letzunite.letzunite.security.SecureRsaEncryption;
import com.letzunite.letzunite.utils.Logger;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 17,Aug,2017.
 */
@Singleton
public class EncryptedRequestGenerator {

    private static final String SEP = "letzunite";

    private final SecureAesEncryption aesEncryption;
    private final SecureRsaEncryption rsaEncryption;

    public EncryptedRequestGenerator(SecureAesEncryption aesEncryption, SecureRsaEncryption rsaEncryption){
        this.aesEncryption=aesEncryption;
        this.rsaEncryption=rsaEncryption;
    }

    /**
     *  this method use {@link SecureAesEncryption#encrypt(String)} method to encrypt data and
     *  return {@link EncryptedData}, then pass that object to {@link #getSecureKey(EncryptedData)},
     *  which is passed in {@link SecureRsaEncryption#encrypt(String, String)} to encrypt key
     *  and {@link SecureRsaEncryption#encrypt(String, String)} method return {@link RequestData} which
     *  is return by this method
     *
     * @param data
     * @return {@link RequestData}
     */
    public RequestData getEncryptedRequestData(String data) {
        RequestData requestData = null;
        try {
            EncryptedData encryptedData = aesEncryption.encrypt(data);
            requestData = rsaEncryption.encrypt(getSecureKey(encryptedData), encryptedData.getEncryptedData());
        } catch (IOException e) {
            Logger.error("IOException", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Logger.error("NoSuchAlgorithmException", e.getMessage());
        } catch (InvalidKeyException e) {
            Logger.error("InvalidKeyException", e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            Logger.error("InvalidAlgorithmParameterException", e.getMessage());
        } catch (NoSuchPaddingException e) {
            Logger.error("NoSuchPaddingException", e.getMessage());
        } catch (BadPaddingException e) {
            Logger.error("BadPaddingException", e.getMessage());
        } catch (InvalidKeySpecException e) {
            Logger.error("InvalidKeySpecException", e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Logger.error("IllegalBlockSizeException", e.getMessage());
        } catch (Exception e) {
            Logger.error("Exception", e.getMessage());
        }
        Logger.debug("key", "key: " + requestData.getKey());
        Logger.debug("data", "data: " + requestData.getData());
        return requestData;
    }

    /**
     * this method append key, salt and IV in {@link String} format
     *
     * @param encryptedData
     * @return {@link String} key
     */
    private static String getSecureKey(EncryptedData encryptedData) {
        StringBuilder builder = new StringBuilder();
        builder.append(encryptedData.getKey());
        builder.append(SEP);
        builder.append(encryptedData.getSalt());
        builder.append(SEP);
        builder.append(encryptedData.getIv());
        return builder.toString();
    }

    /**
     * this method return {@link Map} of encrypted data & encrypted key
     * @see #getEncryptedRequestData(String)
     *
     * @param data
     * @return
     */
    public Map<String, String> getRequestBody(String data) {
        Logger.warn("<==========EncryptedRequestGenerator.getRequestBody ","Request: "+data+"  ==========>");
        RequestData requestData = getEncryptedRequestData(data);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("data", requestData.getData());
        requestMap.put("key", requestData.getKey());
        return requestMap;
    }
}
