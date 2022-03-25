package server.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

/**
 * Controller responsible for extracting the images from the activity repository.
 */
@RestController
@RequestMapping("/api/images")
public class ImagesController {

    private final ActivityRepository repo;

    /**
     * Constructor for the Images Controller.
     *
     * @param repo the activity repository from which we extract the images.
     */
    public ImagesController(ActivityRepository repo) {
        this.repo = repo;
    }


}
