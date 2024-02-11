package com.eidd.mission.db.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MissionTest {

    @Test
    void getId() {
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals(1, mission.getId());
    }

    @Test
    void setId() {
        Mission mission = new Mission();
        mission.setId(1);
        assertEquals(1, mission.getId());
    }

    @Test
    void getX() {
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals(10.0, mission.getX());
    }

    @Test
    void setX() {
        Mission mission = new Mission();
        mission.setX(10.0);
        assertEquals(10.0, mission.getX());
    }

    @Test
    void getY() {
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals(20.0, mission.getY());
    }

    @Test
    void setY() {
        Mission mission = new Mission();
        mission.setY(20.0);
        assertEquals(20.0, mission.getY());
    }

    @Test
    void getTheta() {
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals(30.0, mission.getTheta());
    }

    @Test
    void setTheta() {
        Mission mission = new Mission();
        mission.setTheta(30.0);
        assertEquals(30.0, mission.getTheta());
    }

    @Test
    void testEquals() {
        Mission mission1 = new Mission(1, 10.0, 20.0, 30.0);
        Mission mission2 = new Mission(1, 10.0, 20.0, 30.0);
        assertTrue(mission1.equals(mission2));
    }

    @Test
    void testHashCode() {
        Mission mission1 = new Mission(1, 10.0, 20.0, 30.0);
        Mission mission2 = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals(mission1.hashCode(), mission2.hashCode());
    }

    @Test
    void testToString() {
        Mission mission = new Mission(1, 10.0, 20.0, 30.0);
        assertEquals("Mission{id=1, x=10.0, y=20.0, theta=30.0}", mission.toString());
    }
}
