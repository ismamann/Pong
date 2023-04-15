package model;

import model.RacketController;

public interface RacketController {
    enum State { GOING_UP, IDLE, GOING_DOWN }
    State getState();
}
