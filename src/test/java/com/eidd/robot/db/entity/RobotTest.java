package com.eidd.robot.db.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void setId() {
        Robot robot = new Robot();
        robot.setId(1);
        assertEquals(1, robot.getId());
    }

    @Test
    void setX() {
        Robot robot = new Robot();
        robot.setX(10.0);
        assertEquals(10.0, robot.getX(), 0.0001);
    }

    @Test
    void setY() {
        Robot robot = new Robot();
        robot.setY(20.0);
        assertEquals(20.0, robot.getY(), 0.0001);
    }

    @Test
    void setTheta() {
        Robot robot = new Robot();
        robot.setTheta(45.0);
        assertEquals(45.0, robot.getTheta(), 0.0001);
    }

    @Test
    void setV() {
        Robot robot = new Robot();
        robot.setV(5.0);
        assertEquals(5.0, robot.getV(), 0.0001);
    }

    @Test
    void setUltraSound() {
        Robot robot = new Robot();
        robot.setUltraSound(30.0);
        assertEquals(30.0, robot.getUltraSound(), 0.0001);
    }
}
