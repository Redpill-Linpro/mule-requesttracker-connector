package org.mule.extension.requesttracker.integration;

import org.junit.Test;
import org.mule.extension.requesttracker.api.models.response.UpdateItemResponse;
import org.mule.extension.requesttracker.api.models.response.User;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.Assert.assertEquals;

public class RequestTrackerUserOperationsTest extends RequestTrackerOperationsBaseTest {

    /**
     * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
     */
    @Override
    protected String getConfigFile() {
        return "test-user-config.xml";
    }

    @Test
    public void executeGetUserOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/user/14"))
                .willReturn(aResponse()
                        .withBodyFile("user14")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        User payloadValue = ((User) flowRunner("getUserFlow").withVariable("userId", "14").run()
                .getMessage()
                .getPayload()
                .getValue());

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/user/14"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals("14", payloadValue.getId());
        assertEquals("root", payloadValue.getName());
    }

    @Test
    public void executeSearchUserOperation() throws Exception {
        instanceRule.stubFor(get(urlPathEqualTo("/REST/1.0/search/user"))
                .willReturn(aResponse()
                        .withBodyFile("userSearch")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));
        List<User> payloadValue = ((List<User>) flowRunner("searchUsersFlow")
                .run().getMessage().getPayload().getValue());

        instanceRule.verify(getRequestedFor(urlPathEqualTo("/REST/1.0/search/user"))
                .withQueryParam("query", equalTo("id > 0"))
                .withQueryParam("fields", equalTo("id,Name"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password")));

        assertEquals(7, payloadValue.size());
        assertEquals("93", payloadValue.get(0).getId());
    }

    @Test
    public void createUserOperation() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/user/new"))
                .willReturn(aResponse()
                        .withBodyFile("userCreate")
                        .withHeader("Content-Type", "text/plain; charset=utf-8")));

        UpdateItemResponse payloadValue = (UpdateItemResponse) flowRunner("createUserFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/user/new"))
                .withQueryParam("user", equalTo("root"))
                .withQueryParam("pass", equalTo("password"))
                .withRequestBodyPart(aMultipart().withName("content").withBody(containing("Name: test")).build()));

        assertEquals(200, payloadValue.getStatus());
        assertEquals("163", payloadValue.getItemId());
    }

    @Test
    public void updateUserOperation() throws Exception {
        instanceRule.stubFor(post(urlPathEqualTo("/REST/1.0/user/163/edit"))
        .willReturn(aResponse().withBodyFile("userUpdate").withHeader("Content-Type", "text/plain; charset=utf-8")));

        UpdateItemResponse payloadValue = (UpdateItemResponse) flowRunner("updateUserFlow").run().getMessage().getPayload().getValue();

        instanceRule.verify(postRequestedFor(urlPathEqualTo("/REST/1.0/user/163/edit"))
                .withQueryParam("user", equalTo("root"))
        .withQueryParam("pass", equalTo("password"))
        .withRequestBodyPart(aMultipart().withName("content").withBody(containing("EmailAddress: test@test.com")).build()));

        assertEquals(200, payloadValue.getStatus());
        assertEquals("163", payloadValue.getItemId());
    }
}
