package com.eidd.robot.rest.controller;

import com.eidd.exceptions.RobotException;
import com.eidd.robot.db.entity.Robot;
import com.eidd.robot.db.service.StdRobotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RobotApiControllerTest {
    private MockMvc mockMvc;
    @Mock
    HttpServletRequest request = new MockHttpServletRequest();
    @Mock
    private StdRobotService robotService;
    @InjectMocks
    private RobotApiController robotApiController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(robotApiController).build();
    }
    /*
    @Test
    void robotGet() {
        // given
        List<Robot> robots = new ArrayList<>();
        robots.add(new Robot(1, 10.0, 20.0, 30.0, 40.0, 50.0));
        robots.add(new Robot(2, 15.0, 25.0, 35.0, 45.0, 55.0));

        when(robotService.getAll()).thenReturn(robots);

        // when
        ResponseEntity<List<Robot>> rs = robotApiController.robotGet();

        // then
        assertThat(rs.getStatusCode().toString()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
     */

    @Test
    void robotGet(){
        // Setting up behavior of dependencies
        when(robotService.getAll()).thenReturn(List.of(new Robot())); // Assuming getAll returns a list of robots

        // Creating the instance of RobotApiController with mocked dependencies
        robotApiController = new RobotApiController(new ObjectMapper(), new MockHttpServletRequest());
        robotApiController.setRobotService(robotService); // Injecting the mocked service

        // Invoking the method
        ResponseEntity<List<Robot>> responseEntity = robotApiController.robotGet();

        // Verifying the result
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void robotIdGet() throws RobotException {
        // Creating a sample robot
        Robot sampleRobot = new Robot();
        sampleRobot.setId(1); // Setting the ID of the robot

        // Mocking the behavior of robotService.find method to return the sample robot when ID 1 is passed
        when(robotService.find(1)).thenReturn(Optional.of(sampleRobot));

        // Creating the instance of RobotApiController with mocked dependencies
        RobotApiController robotApiController = new RobotApiController(new ObjectMapper(), new MockHttpServletRequest());
        robotApiController.setRobotService(robotService); // Injecting the mocked service

        // Invoking the method with ID 1
        ResponseEntity<Robot> responseEntity = robotApiController.robotIdGet(1);

        // Verifying the result
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(sampleRobot);
    }

    @Test
    void robotIdDelete() throws RobotException {
        doNothing().when(robotService).delete(1); // Assuming deletion is successful

        // Creating the instance of RobotApiController with mocked dependencies
        robotApiController = new RobotApiController(new ObjectMapper(), new MockHttpServletRequest());
        robotApiController.setRobotService(robotService); // Injecting the mocked service

        // Invoking the method with ID 1
        ResponseEntity<Void> responseEntity = robotApiController.robotIdDelete(1);

        // Verifying the result
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    /*
    @Test
    void robotPost() throws RobotException {
        // Mocking the service method
        Robot robot = new Robot();
        when(robotService.create(robot)).thenReturn(robot);

        // Calling the controller method
        ResponseEntity<Robot> responseEntity = robotApiController.robotPost(robot);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(robot, responseEntity.getBody());

        // Verifying that service method was called
        verify(robotService, times(1)).create(robot);
    }*/

    /*
    @Test
    void robotPost() throws Exception {
        // Mocking the service behavior
        Robot robot = new Robot(0, 10.0, 23.0, 21.0, 50.0, 40.0);
        ObjectMapper objectMapper = new ObjectMapper();
        given(robotService.create(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        // Performing an HTTP POST request to create an employee
        ResultActions response = mockMvc.perform(post("/api/robot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(robot)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(robot.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.x", CoreMatchers.is(robot.getX())));

    }*/

    @Test
    void robotPost() {
        // necessary behavior of dependencies
        when(robotService.create(any(Robot.class))).thenReturn(new Robot());

        // mocked dependencies
        RobotApiController robotApiController = new RobotApiController(new ObjectMapper(), new MockHttpServletRequest());
        robotApiController.setRobotService(robotService); // Inject the mock service

        // Invoke the method
        ResponseEntity<Robot> responseEntity = robotApiController.robotPost(new Robot());

        // Verify the result
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
