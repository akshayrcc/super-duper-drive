package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    /*add new credential to the database for the given user*/
    public void addCredential(String url,
                              String userName,
                              String credentialUserName,
                              String key,
                              String password) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userId);
        credentialMapper.insert(credential);
    }

    /*fetch all the creds for the given user*/
    public Credential[] getCredentialListings(Integer userId) {
        return credentialMapper.getCredentialListings(userId);
    }

    /*fetch the creds details for the given credId*/
    public Credential getCredential(Integer credId) {
        return credentialMapper.getCredential(credId);
    }

    /*remove the cred details for the given credId*/
    public void deleteCredential(Integer credId) {
        credentialMapper.deleteCredential(credId);
    }

    /*modify the cred details for the given user*/
    public void updateCredential(Integer credentialId,
                                 String newUserName,
                                 String url,
                                 String key,
                                 String password) {
        credentialMapper.updateCredential(credentialId, newUserName, url, key, password);
    }
}
