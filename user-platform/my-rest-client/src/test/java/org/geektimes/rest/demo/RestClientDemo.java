package org.geektimes.rest.demo;

import org.geektimes.projects.user.domain.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClientDemo {

    public static void main(String[] args) {
        doRequestBodyPost();
        doFormPost();
    }

    public static void doGet() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);
    }

    public static void doRequestBodyPost() {
        User user = new User();
        user.setName("xunqirui");
        user.setPhoneNumber("23333333333");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://127.0.0.1:8080/postRegister")
                .request()
                .method("POST", userEntity);

        String content = response.readEntity(String.class);
        System.out.println(content);
    }

    public static void doFormPost() {
        User user = new User();
        user.setName("xunqirui");
        user.setPhoneNumber("13333333333");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://127.0.0.1:8080/postRegister")
                .request()
                .method("POST", userEntity);

        String content = response.readEntity(String.class);
        System.out.println(content);
    }
}
