package com.evweet.auth.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.evweet.auth.dao.UserRepository;
import com.evweet.auth.enums.AuthType;
import com.evweet.auth.model.entity.RcUser;
import com.evweet.auth.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RestTemplate restTemplate;

    @Value("${web-uri}")
    private String webUri;

    public String authenticate(AuthType authType, String code) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(authType.getValue());

        String tokenUri = clientRegistration.getProviderDetails().getTokenUri();
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();
        String redirectUri = clientRegistration.getRedirectUri();

        String getTokenUriTemplate;
        String getTokenUri = "";
        switch (authType) {
            case GITHUB:
                Object[] githubParams = {clientId, clientSecret, code, redirectUri};
                getTokenUriTemplate = tokenUri + "?client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
                getTokenUri = String.format(getTokenUriTemplate, githubParams);
                break;
            case GOOGLE:
                //todo
                break;
            default:
                break;
        }

        // send a POST request to the token endpoint to get the access token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> tokenResponse = restTemplate.exchange(getTokenUri, HttpMethod.POST, httpEntity, String.class);

        // get the access token from the response
        String body = tokenResponse.getBody();
        JSONObject map = JSON.parseObject(body);
        assert map != null;
        String accessToken = map.getString("access_token");

        // get user info with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        String getUserInfoUri = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri();
        ResponseEntity<String> userinfoResponse = restTemplate.exchange(getUserInfoUri, HttpMethod.GET, entity, String.class);

        // get the user info from the response
        String userinfo = userinfoResponse.getBody();
        JSONObject userInfoObject = JSON.parseObject(userinfo);
        assert userInfoObject != null;
        String username = userInfoObject.getString("login");
        String oauth2Id = userInfoObject.getString("node_id");

        RcUser user = userRepository.findByOauth2Id(oauth2Id).orElse(null);
        if (user == null) {
            RcUser userToSave = new RcUser();
            userToSave.setUsername(username);
            userToSave.setOauth2Id(oauth2Id);
            userToSave.setAuthType(AuthType.GITHUB);
            userToSave.setPassword(passwordEncoder.encode(oauth2Id));
            userRepository.save(userToSave);
        }

        return webUri + "/login?id=" + oauth2Id + "&authType=" + authType;
    }
}
