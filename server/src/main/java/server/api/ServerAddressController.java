package server.api;

import commons.misc.GameResponse;
import commons.misc.ServerAddress;
import org.apache.catalina.Server;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.utils.SinglePlayerStateUtils;

@Configuration
@ComponentScan(basePackageClasses = SinglePlayerStateUtils.class)
@RestController
@RequestMapping("/api")
public class ServerAddressController {

    private final SinglePlayerStateUtils singlePlayerStateUtils;

    /**
     * Constructor for the QueueController.
     *
     * @param singlePlayerStateUtils SinglePlayerStateUtils instance, responsible for logic.
     */
    public ServerAddressController(SinglePlayerStateUtils singlePlayerStateUtils) {
        this.singlePlayerStateUtils = singlePlayerStateUtils;
    }

    /**
     * Get the current state of the queue.
     * The state of the queue contains the following items:
     * - List of users in the queue
     *
     * @return t
     */
    @GetMapping("")
    public ResponseEntity<ServerAddress> getServerURLs() {
        ServerAddress serverAddress = singlePlayerStateUtils.getServer();
        return ResponseEntity.ok(serverAddress);
    }

    @PostMapping("/api")
    public ResponseEntity<ServerAddress> postServerURLs(@RequestBody ServerAddress serverAddress) {
        ServerAddress serverURL = singlePlayerStateUtils.postURl(serverAddress);
        if (serverURL != null) {
            return ResponseEntity.ok(serverAddress);
        }
        return ResponseEntity.notFound().build();
    }
}
