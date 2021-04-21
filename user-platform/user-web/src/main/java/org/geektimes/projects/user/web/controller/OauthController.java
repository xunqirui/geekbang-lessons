package org.geektimes.projects.user.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geektimes.projects.user.domain.GiteeOauth;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * OauthController
 *
 * @author qrXun on 2021/4/21
 */
@Path("")
public class OauthController implements PageController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @GET
    @Path("auth")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "auth.jsp";
    }

    @GET
    @Path("callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        GiteeOauth giteeOauth = new GiteeOauth();
        giteeOauth.setCode(code);
        Entity<GiteeOauth> userEntity = Entity.entity(giteeOauth, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        Client client = ClientBuilder.newClient();
        Response callBackResponse = client.target("https://gitee.com/oauth/token")
                .request()
                .method("POST", userEntity);
        String content = callBackResponse.readEntity(String.class);
        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            request.setAttribute("accessToken", jsonNode.get("access_token"));
            request.setAttribute("refreshToken", jsonNode.get("refresh_token"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success-auth.jsp";
    }
}
