package com.home.spring.controller;

import com.home.spring.Application;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.home.spring.dto.Vet;
import com.home.spring.service.OwnerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, OwnerControllerTestConfiguration.class})
//@WebAppConfiguration
public class OwnerControllerTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mvc;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerController ownerController;

    @Before
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "ownera", roles = {"OWNER"})
    public void testName() throws Exception {
        ownerController.getPetsForOwner("ownera");

    }

    @Test
//    @WithMockUser(username = "ownera", password = "pass", roles = {"OWNER"})
    public void testGetPetsForOwner() throws Exception {
        //given
//        when(ownerService.getPetsForOwner(anyString())).thenReturn(Collections.singletonList(createPet()));
//
//        when
//        MvcResult resultActions = mvc.perform(get("/pets/ownera").with(httpBasic("ownera", "pass"))).andExpect(status().isOk()).andReturn();
//
//        then
//        Assert.assertTrue(resultActions.getResponse().getContentAsString().contains("Cat"));
    }

    private Pet createPet() {
        Owner owner = new Owner("Owner", "A", null);
        Vet vet = new Vet("Vet", "A", null);
        Pet pet = new Pet("Cat", owner, vet, Collections.emptyList(), new Date());
        owner.setPets(Collections.singletonList(pet));
        return pet;
    }
}