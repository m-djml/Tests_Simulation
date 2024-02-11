package com.eidd.robot.db.service;

import com.eidd.exceptions.RobotException;
import com.eidd.robot.db.entity.Robot;
import com.eidd.robot.db.repository.RobotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StdRobotServiceTest {

    @Mock
    private RobotRepository robotRepository;

    @InjectMocks
    private StdRobotService robotService;

    @Test
    void getAll() throws RobotException {
        // Given
        List<Robot> robots = new ArrayList<>();
        robots.add(new Robot(1, 10.0, 20.0, 30.0, 40.0, 50.0));
        robots.add(new Robot(2, 15.0, 25.0, 35.0, 45.0, 55.0));
        when(robotRepository.findAll()).thenReturn(robots);

        // When
        List<Robot> result = robotService.getAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(1).getId()).isEqualTo(2);
    }

    @Test
    void find() throws RobotException {
        // Given
        int id = 1;
        Robot robot = new Robot(1, 10.0, 20.0, 30.0, 40.0, 50.0);
        when(robotRepository.findById(id)).thenReturn(Optional.of(robot));

        // When
        Optional<Robot> result = robotService.find(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getX()).isEqualTo(10.0);
        assertThat(result.get().getY()).isEqualTo(20.0);
        assertThat(result.get().getTheta()).isEqualTo(30.0);
        assertThat(result.get().getV()).isEqualTo(40.0);
        assertThat(result.get().getUltraSound()).isEqualTo(50.0);
    }

    @Test
    void delete() throws RobotException {
        // Given
        int id = 1;

        // When
        robotService.delete(id);

        // Then
        verify(robotRepository, times(1)).deleteById(id);
    }

    @Test
    void create() throws RobotException {
        // Given
        Robot robot = new Robot(1, 10.0, 20.0, 30.0, 40.0, 50.0);
        when(robotRepository.save(robot)).thenReturn(robot);

        // When
        Robot result = robotService.create(robot);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getX()).isEqualTo(10.0);
        assertThat(result.getY()).isEqualTo(20.0);
        assertThat(result.getTheta()).isEqualTo(30.0);
        assertThat(result.getV()).isEqualTo(40.0);
        assertThat(result.getUltraSound()).isEqualTo(50.0);
    }

    @Test
    void update() throws RobotException {
        // Given
        Robot robot = new Robot(0, 10.0, 20.0, 30.0, 40.0, 50.0);
        doReturn(Optional.of(robot)).when(robotRepository).findById(robot.getId());

        // When
        robotService.update(robot);

        // Then
        verify(robotRepository).save(robot);
    }

}