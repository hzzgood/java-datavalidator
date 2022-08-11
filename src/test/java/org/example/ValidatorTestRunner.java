package org.example;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ValidatorTestRunner {
    public static void main(String[] args) {
//        ValidatorUnitTest validatorUnitTest = new ValidatorUnitTest();
//        System.out.println("Test");
//        validatorUnitTest.testTopicValidator();
        Result result = JUnitCore.runClasses(ValidatorUnitTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
