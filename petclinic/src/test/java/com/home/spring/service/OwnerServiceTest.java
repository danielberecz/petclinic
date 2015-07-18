package com.home.spring.service;

import com.home.spring.dto.Pet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@Transactional
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test-db-setup.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:test-db-teardown.sql")
})
public class OwnerServiceTest {

    private static final long OWNER_ID = 1l;
    public static final long PET_ID = 1l;

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testGetPetsForOwner() throws Exception {
        List<Pet> petsForOwner = ownerService.getPetsForOwner(OWNER_ID);
        Assert.assertTrue(petsForOwner.stream().allMatch(p -> p.getName().equals("Cat") || p.getName().equals("Dog")));
    }

    @Test
    public void testGetPet() throws Exception {
        Pet pet = ownerService.getPet(PET_ID);
        Assert.assertEquals("Cat", pet.getName());
    }
}