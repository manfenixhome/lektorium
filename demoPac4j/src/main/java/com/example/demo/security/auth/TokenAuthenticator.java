package com.example.demo.security.auth;

import com.example.demo.model.RoleType;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.profile.CommonProfile;

public class TokenAuthenticator implements Authenticator<TokenCredentials> {

    @Override
    public void validate(TokenCredentials credentials, WebContext context) {
        if ("user-token-1234".equals(credentials.getToken())) {
            CommonProfile profile = new CommonProfile();
            profile.addRole(RoleType.USER.toString());
            credentials.setUserProfile(profile);
        }
        if ("admin-token-1234".equals(credentials.getToken())) {
            CommonProfile profile = new CommonProfile();
            profile.addRole(RoleType.ADMIN.toString());
            credentials.setUserProfile(profile);
        }
    }
}
