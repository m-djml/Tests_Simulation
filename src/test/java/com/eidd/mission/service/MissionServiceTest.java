package com.eidd.mission.service;

import com.eidd.exceptions.RobotException;
import com.eidd.mission.db.entity.Mission;
import com.eidd.mission.db.repository.MissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.PessimisticLockingFailureException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {
    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        // Given
        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission(1, 10.0, 20.0, 30.0));
        missions.add(new Mission(2, 15.0, 25.0, 35.0));
        when(missionRepository.findAll()).thenReturn(missions);

        // When
        List<Mission> result = missionService.getAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(1).getId()).isEqualTo(2);
    }

    @Test
    void getAllShouldCatchException() {
        doThrow(PessimisticLockingFailureException.class).when(missionRepository).findAll();
        assertThatThrownBy(() -> missionService.getAll()).isInstanceOf(RobotException.class);
    }

    @Test
    void create() {
        // Given
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        when(missionRepository.save(mission)).thenReturn(mission);

        // When
        Mission createdMission = missionService.create(mission);

        // Then
        assertThat(createdMission).isNotNull();
        assertThat(createdMission.getId()).isEqualTo(1);
        assertThat(createdMission.getX()).isEqualTo(10.0);
        assertThat(createdMission.getY()).isEqualTo(20.0);
        assertThat(createdMission.getTheta()).isEqualTo(30.0);
    }

    @Test
    void init() {
        // Given
        Mission mission1 = new Mission(1, 0.0, 0.0, 0.0);
        Mission mission2 = new Mission(2, 0.0, 10.0, 0.0);
        Mission mission3 = new Mission(3, 10.0, 10.0, -90.0);
        Mission mission4 = new Mission(4, 10.0, 0.0, -180.0);
        Mission mission5 = new Mission(5, 0.0, 0.0, -270.0);
        List<Mission> missions = Arrays.asList(mission1, mission2, mission3, mission4, mission5);

        // When
        missionService.init();

        // Then
        // Verify that missionRepository.save() is called for each mission
        verify(missionRepository, times(1)).save(mission1);
        verify(missionRepository, times(1)).save(mission2);
        verify(missionRepository, times(1)).save(mission3);
        verify(missionRepository, times(1)).save(mission4);
        verify(missionRepository, times(1)).save(mission5);
    }

    @Test
    void createMissionMap() {
        // Given
        List<Mission> missions = Arrays.asList(new Mission(1, 10.0, 20.0, 30.0), new Mission(2, 15.0, 25.0, 35.0));
        when(missionRepository.findAll()).thenReturn(missions);

        // When
        missionService.createMissionMap();

        // Then
        assertThat(missionService.getMissionsMap().size()).isEqualTo(2);
        assertThat(missionService.getMissionsMap().get(0).getId()).isEqualTo(1);
        assertThat(missionService.getMissionsMap().get(1).getId()).isEqualTo(2);
    }

    @Test
    void getMission() {
        // Given
        missionService.create(new Mission(1, 10.0, 20.0, 30.0));
        missionService.create(new Mission(2, 15.0, 25.0, 35.0));

        // When
        Mission mission1 = missionService.getMission();
        Mission mission2 = missionService.getMission();
        Mission mission3 = missionService.getMission();

        // Then
        assertThat(mission1.getId()).isEqualTo(1);
        assertThat(mission2.getId()).isEqualTo(2);
        assertThat(mission3.getId()).isEqualTo(1); // Cycling back to the first mission
    }

    @Test
    void readRobotsFromJsonFile() throws IOException {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/test_missions.json");

        Mission[] expectedMissions = new Mission[]{
        new Mission(
                 1,
         0.0,
         0.0,
         0.0
        ),
        new Mission(
             2,
                 0.0,
                 10.0,
                 0.0
        ),
        new Mission(
             3,
                 10.0,
                 10.0,
                 -90.0
        ),
        new Mission(
             4,
                 10.0,
                 0.0,
                 -180.0
        ),
        new Mission(
             5,
                 0.0,
                 0.0,
                 -270.0
        )
        };

        // When
        List<Mission> missions = missionService.readRobotsFromJsonFile();

        // Then
        assertThat(missions).containsExactly(expectedMissions);
    }
}
