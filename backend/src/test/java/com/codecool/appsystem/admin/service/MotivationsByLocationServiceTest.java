package com.codecool.appsystem.admin.service;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class MotivationsByLocationServiceTest extends MockData {

    @Mock
    MotivationsUtilService motivationsUtilService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void ungradedUsersTest() {

        when(motivationsUtilService.getUngradedUsers(Collections.singletonList(user), location.getId())).thenReturn(Collections.singletonList(motivDto));

        List<MotivationDTO> motivList =  motivationsUtilService.getUngradedUsers(Collections.singletonList(user), location.getId());

        MotivationDTO dto = motivList.get(0);

        Assert.assertEquals("Doe John", dto.getName());
        Assert.assertEquals(false, dto.getIsVideo());
        Assert.assertEquals(1, motivList.size());
    }

    @Test
    public void cantFindUserFromKrakkow(){

        List<MotivationDTO> poland =  motivationsUtilService.getUngradedUsers(Collections.singletonList(user), "KRK");

        Assert.assertEquals(0,poland.size());
    }

}