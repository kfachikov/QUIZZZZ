package server.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.utils.MultiPlayerStateUtils;


/**
 * Controller responsible for handling the client requests regarding any multiplayer game.
 */
@Configuration
@ComponentScan(basePackageClasses = MultiPlayerStateUtils.class)
@RestController
@RequestMapping("/api/multi")
public class MultiplayerStateController {
}
