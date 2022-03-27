package server.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Utility class providing functionality for the multi-player game mode.
 */
@Component
@ComponentScan(basePackageClasses = GenerateQuestionUtils.class)
public class MultiPlayerStateUtils {

    private GenerateQuestionUtils generateQuestionUtils;

    /**
     * Constructor for multi-player server-side utility class.
     *
     * @param generateQuestionUtils is the "generate questions" utility bean injected by Spring.
     */
    public MultiPlayerStateUtils(GenerateQuestionUtils generateQuestionUtils) {
        this.generateQuestionUtils = generateQuestionUtils;
    }
}
