package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ValidatorUnitTest {


    private Topic input;
    private Boolean expectedResult;
    private TopicValidator topicValidator;

    @Before
    public void initialize() {
        topicValidator = new TopicValidator();
    }

    public ValidatorUnitTest(Topic input, Boolean expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection testInputs() {
        List<Topic> topicList = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String objString = Files.readString(Path.of("src/main/resources/Tests.json"));
            topicList = objectMapper.readValue(objString, new TypeReference<List<Topic>>(){});
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(new Object[][] {
            { topicList.get(0), true },
            { topicList.get(1), false },
            { topicList.get(2), false },
            { topicList.get(3), true },
            { topicList.get(4), false },
            { topicList.get(5), false }
        });
    }

    @Test
    @MethodSource("testInputs")
    public void testTopicValidator () {
        System.out.println(input.getTopic());
        Assert.assertEquals(expectedResult, topicValidator.validate(input));

//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String objString = Files.readString(Path.of("src/main/resources/Tests.json"));
//            List<Topic> topicList = objectMapper.readValue(objString, new TypeReference<List<Topic>>(){});
//            List<Boolean> actualOutputs = new ArrayList<>();
//            topicList.forEach(top -> {
//                actualOutputs.add(topicValidator.validate(top));
//            });
//            System.out.println(actualOutputs);
//            Assert.assertArrayEquals(expectedOutputs.toArray(), actualOutputs.toArray());
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
