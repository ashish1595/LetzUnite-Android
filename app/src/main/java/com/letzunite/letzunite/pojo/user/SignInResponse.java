package com.letzunite.letzunite.pojo.user;

/**
 * Created by Deven Singh on 5/17/2018.
 */

public class SignInResponse {

    private String tokenId;
    private UserProfile userProfile;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "SignInResponse{" +
                "tokenId='" + tokenId + '\'' +
                ", userProfile=" + userProfile +
                '}';
    }
}
