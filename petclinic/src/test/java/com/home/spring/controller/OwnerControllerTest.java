package com.home.spring.controller;

import com.home.spring.Application;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.home.spring.dto.Vet;
import com.home.spring.security.dto.DomainUser;
import com.home.spring.security.dto.Role;
import com.home.spring.service.OwnerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, ControllerTestConfiguration.class})
@WebAppConfiguration
public class OwnerControllerTest {

    private static final String TEST_PET_NAME = "Cat";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Autowired
    private OwnerService ownerService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
        reset(ownerService);
    }

    @Test
    public void testGetPetsForOwnerUnauthenticated() throws Exception {
        mvc.perform(get("/pets/ownera")).andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(username = "ownera", roles = {"OWNER"})
    public void testGetPetsForOwner() throws Exception {
        //given
        when(ownerService.getPetsForOwner(anyString())).thenReturn(Collections.singletonList(createPet(TEST_PET_NAME)));

        //when
        MvcResult resultActions = mvc.perform(get("/pets/ownera")).andExpect(status().isOk()).andReturn();

        //then
        Assert.assertTrue(resultActions.getResponse().getContentAsString().contains(TEST_PET_NAME));
    }

    private Pet createPet(String petName) {
        DomainUser domainUser = new DomainUser("ownera", "password", Role.ROLE_OWNER);
        Owner owner = new Owner("Owner", "A", domainUser);
        Vet vet = new Vet("Vet", "A", null);
        Pet pet = new Pet(petName, owner, vet, Collections.emptyList(), new Date());
        owner.setPets(Collections.singletonList(pet));
        return pet;
    }
}