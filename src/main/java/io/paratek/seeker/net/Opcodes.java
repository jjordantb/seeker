package io.paratek.seeker.net;

public interface Opcodes {

    // Login
    byte LOGIN_REQUEST = 0; // 0-199 bytes for username, 200-399 for password
    byte LOGIN_SUCCESSFUL = 1;
    byte LOGIN_FAILED_SERVER_FULL = 2;
    byte LOGIN_FAILED_INVALID_CREDENTIALS = 3;
    byte LOGOUT = 4;

    // Game State Updating
    byte ADD_PLAYER = 5;
    byte UPDATE_PLAYER_LOCATION = 6;

}
