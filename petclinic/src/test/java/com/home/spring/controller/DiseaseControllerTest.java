package com.home.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.home.spring.Application;
import com.home.spring.dto.Disease;
import com.home.spring.service.DiseaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, ControllerTestConfiguration.class})
@WebAppConfiguration
public class DiseaseControllerTest {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
        reset(diseaseService);
    }

    @Test
    @WithMockUser(roles = {"VET"})
    public void testCreateDisease() throws Exception {
        //when
        ObjectWriter objectWriter = new ObjectMapper().writer();
        mvc.perform(post("/disease")
                .content(objectWriter.writeValueAsString(createDisease()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //then
        verify(diseaseService, times(1)).saveDisease(any(Disease.class));
    }


    private Disease createDisease() {
        return new Disease("disease", "treatment");
    }

}