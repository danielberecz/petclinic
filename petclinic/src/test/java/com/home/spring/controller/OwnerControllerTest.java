package com.home.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.home.spring.Application;
import com.home.spring.dto.Disease;
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
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, OwnerControllerTestConfiguration.class})
@WebAppConfiguration
public class OwnerControllerTest {

    public static final String TEST_PET_NAME = "Cat";
    public static final long TEST_PET_ID = 1l;
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

    @Test
    @WithMockUser(roles = {"VET"})
    public void testGetPets() throws Exception {
        //given
        when(ownerService.getPets()).thenReturn(Collections.singletonList(createPet(TEST_PET_NAME)));

        //when
        MvcResult resultActions = mvc.perform(get("/pets")).andExpect(status().isOk()).andReturn();

        //then
        Assert.assertTrue(resultActions.getResponse().getContentAsString().contains(TEST_PET_NAME));
    }

    @Test
    @WithMockUser(roles = {"VET"})
    public void testGetPetWithVet() throws Exception {
        //given
        when(ownerService.getPet(TEST_PET_ID)).thenReturn(createPet(TEST_PET_NAME));

        //when
        MvcResult resultActions = mvc.perform(get("/pet/" + TEST_PET_ID)).andExpect(status().isOk()).andReturn();

        //then
        Assert.assertTrue(resultActions.getResponse().getContentAsString().contains(TEST_PET_NAME));
    }

    @Test
    @WithMockUser(username = "ownera", roles = {"OWNER"})
    public void testGetPetWithOwner() throws Exception {
        //given
        when(ownerService.getPet(TEST_PET_ID)).thenReturn(createPet(TEST_PET_NAME));

        //when
        MvcResult resultActions = mvc.perform(get("/pet/" + TEST_PET_ID)).andExpect(status().isOk()).andReturn();

        //then
        Assert.assertTrue(resultActions.getResponse().getContentAsString().contains(TEST_PET_NAME));
    }

    @Test
    @WithMockUser(username = "ownerb", roles = {"OWNER"})
    public void testGetPetWithUnauthorizedOwner() throws Exception {
        when(ownerService.getPet(TEST_PET_ID)).thenReturn(createPet(TEST_PET_NAME));
        mvc.perform(get("/pet/" + TEST_PET_ID)).andExpect(status().isForbidden());
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
        verify(ownerService, times(1)).saveDisease(any(Disease.class));
    }

    private Disease createDisease() {
        return new Disease("disease", "treatment");
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