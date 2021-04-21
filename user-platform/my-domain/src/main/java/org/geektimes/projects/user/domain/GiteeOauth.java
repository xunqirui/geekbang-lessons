package org.geektimes.projects.user.domain;

/**
 * GiteeOauth
 *
 * @author qrXun on 2021/4/22
 */
public class GiteeOauth {

    private String grant_type = "authorization_code";

    private String client_id = "80714c350860ef865076c17d7f023a96c787f1ede32b5c19548554fbc489624c";

    private String redirect_uri = "http://127.0.0.1:8080/callback";

    private String client_secret = "ee219c2b39186a360d3ad3671ec1d933bf8ccffdd63328ac26fe77693442ead1";

    private String code;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
