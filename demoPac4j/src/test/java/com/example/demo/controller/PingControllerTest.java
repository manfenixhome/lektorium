package com.example.demo.controller;

import com.example.demo.security.Pac4jConfig;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingControllerTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void checkAccessAsAnonymousToAnonymousPing() throws JSONException {
        ResponseEntity<String> response = rest.exchange("/api/anonymous/ping", HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{\"message\":\"Anonymous access granted\"}", response.getBody(), false);
    }

    @Test
    public void checkAccessAsAnonymousToUserPing() {
        ResponseEntity<String> response = rest.exchange("/api/user/ping", HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void checkAccessAsAnonymousWithWrongHeaderCodeToUserPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"user-token");
        ResponseEntity<String> response = rest.exchange("/api/user/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void checkAccessAsUserToUserPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"user-token-1234");
        ResponseEntity<String> response = rest.exchange("/api/user/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{\"message\":\"User role access granted\"}", response.getBody(), false);
    }

    @Test
    public void checkAccessAsAnonymousToAdminPing() {
        ResponseEntity<String> response = rest.exchange("/api/admin/ping", HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void checkAccessAsUserToAdminPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"user-token-1234");
        ResponseEntity<String> response = rest.exchange("/api/admin/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void checkAccessAsAdminToAdminPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"admin-token-1234");
        ResponseEntity<String> response = rest.exchange("/api/admin/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{\"message\":\"Admin role access granted\"}", response.getBody(), false);
    }

    @Test
    public void checkAccessAsAnonymousToMixPing() {
        ResponseEntity<String> response = rest.exchange("/api/mix/ping", HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void checkAccessAsUserToMixPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"user-token-1234");
        ResponseEntity<String> response = rest.exchange("/api/mix/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{\"message\":\"Mix role access granted\"}", response.getBody(), false);
    }

    @Test
    public void checkAccessAsAdminToMixPing() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Pac4jConfig.HEADER_TOKEN_NAME,"admin-token-1234");
        ResponseEntity<String> response = rest.exchange("/api/mix/ping", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{\"message\":\"Mix role access granted\"}", response.getBody(), false);
    }
}
