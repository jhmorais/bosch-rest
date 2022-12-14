package com.jhmorais.boschrest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jhmorais.boschrest.controller.SubscriptionController;
import com.jhmorais.boschrest.model.Subscription;
import com.jhmorais.boschrest.service.SubscriptionService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SubscriptionController.class)
public class SubscriptionControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private SubscriptionService subscriptionService;
	
	Subscription mockSubscription = new Subscription("123456", 1, "create", "http://www.mock.com", "small", true);
	Mono<Subscription> mockMonoSubscription = Mono.just(mockSubscription);

    String exampleJson = "{\"id\":\"123456\",\"userId\":1,\"event\": \"create\", \"targetUrl\": \"http://www.mock.com\", \"filter\": \"small\", \"status\": true}";

    @Test
    public void getSubscriptionsById() throws Exception {

        Mockito.when(
                subscriptionService.findById(Mockito.anyString())).thenReturn(mockMonoSubscription);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/subscriptions/123456").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":\"123456\",\"userId\":1,\"event\": \"create\", \"targetUrl\": \"http://www.mock.com\", \"filter\": \"small\", \"status\": true}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}
