package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class for some controller.
 */
@Controller
@RequestMapping("/")
public class SomeController {

    /**
     * Write "Hello world!".
     *
     * @return string message.
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }
}