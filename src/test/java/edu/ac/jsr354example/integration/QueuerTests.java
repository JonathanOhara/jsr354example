package edu.ac.jsr354example.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueuerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldPushMonetaryAmount() throws Exception {
        MvcResult value = mvc.perform(post("/queue/BRL").
                contentType(MediaType.APPLICATION_JSON).
                content("1000")).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.currency.currencyCode", org.hamcrest.Matchers.equalTo("BRL"))).
                andExpect(jsonPath("$.number", org.hamcrest.Matchers.equalTo(1000))).
                andReturn();

        Assert.notNull(value,"null response");


    }

    @Test
    public void shouldPullMonetaryAmount() throws Exception {
        mvc.perform(post("/queue/BRL").
                contentType(MediaType.APPLICATION_JSON).
                content("100"));

        MvcResult value = mvc.perform(get("/queue")).
                andExpect(jsonPath("$.currency.currencyCode", org.hamcrest.Matchers.equalTo("BRL"))).
                andExpect(jsonPath("$.number", org.hamcrest.Matchers.equalTo(100))).
                andExpect(status().isOk()).
                andReturn();

        Assert.notNull(value,"null response");
    }

    @Test
    public void shouldPullFormatted() throws Exception {
        mvc.perform(post("/queue/EUR").
                contentType(MediaType.APPLICATION_JSON).
                content("250"));

        MvcResult value = mvc.perform(get("/queue/formats/de-DE")).
                andExpect(jsonPath("$", org.hamcrest.Matchers.equalTo("250,00 EUR"))).
                andExpect(status().isOk()).
                andReturn();

        Assert.notNull(value,"null response");
    }

    @Test
    public void shouldConvertToOurCryptoCoin() throws Exception {
        mvc.perform(post("/queue/BRL").
                contentType(MediaType.APPLICATION_JSON).
                content("250"));

        MvcResult value = mvc.perform(get("/queue/conversions/CCC")).
                andExpect(jsonPath("$.currency.currencyCode", org.hamcrest.Matchers.equalTo("CCC"))).
                andExpect(jsonPath("$.number", org.hamcrest.Matchers.equalTo(2.5))).
                andExpect(status().isOk()).
                andReturn();

        Assert.notNull(value,"null response");
    }

}
