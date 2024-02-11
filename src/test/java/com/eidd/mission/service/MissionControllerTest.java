package com.eidd.mission.service;

import com.eidd.mission.db.entity.Mission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

// Import MockitoExtension to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
class MissionControllerTest {

    // InjectMocks is used to automatically inject mocked dependencies into the Controller
    @InjectMocks
    private MissionController missionController;

    // Mock the MissionService dependency
    @Mock
    private MissionService missionService;

    @Test
    void getMissions() {
        // Given
        Mission mission1 = new Mission(1, 10.0, 20.0, 30.0);
        Mission mission2 = new Mission(2, 15.0, 25.0, 35.0);
        List<Mission> missions = Arrays.asList(mission1, mission2);
        // Mock
        when(missionService.getAll()).thenReturn(missions);

        // When
        List<Mission> result = missionController.getMissions();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(mission1);
        assertThat(result.get(1)).isEqualTo(mission2);
    }

    @Test
    void getMission() {
        // Given
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        // Mock
        when(missionService.getMission()).thenReturn(mission);

        // When
        Mission result = missionController.getMission();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getX()).isEqualTo(10.0);
        assertThat(result.getY()).isEqualTo(20.0);
        assertThat(result.getTheta()).isEqualTo(30.0);
    }
}
