package server.api;

import commons.queue.QueueState;
import commons.queue.QueueUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.utils.QueueUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

class QueueControllerTest {

    private QueueController lobbyCtrl;
    private QueueUtils queueUtils;


    @BeforeEach
    public void setup() {
        queueUtils = new QueueUtils();
        lobbyCtrl = new QueueController(queueUtils);
    }
}