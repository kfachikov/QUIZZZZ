package server.utils;

import commons.question.AbstractQuestion;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Mock class for testing other classes that use GenerateQuestionUtils.
 */
public class MockGenerateQuestionUtils extends GenerateQuestionUtils {

    /**
     * List of methods that were called on this instance.
     */
    public ArrayList<String> calledMethods = new ArrayList<>();
    /**
     * Return value of any called method.
     */
    public Object returnValue = null;

    /**
     * Constructor matching super.
     *
     * @param repo   Activity repository
     * @param random Instance of random
     */
    public MockGenerateQuestionUtils(ActivityRepository repo, Random random) {
        super(repo, random);
    }

    /**
     * Mock "generate20Questions" method.
     *
     * @return value specified in returnValue
     */
    @Override
    public List<AbstractQuestion> generate20Questions() {
        calledMethods.add("generate20Questions");
        return (List<AbstractQuestion>) returnValue;
    }
}
