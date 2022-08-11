package org.example;

public class Main {
    public static void main(String[] args) {
       Topic topicA = new Topic("A", "a", "aaa");

       DescriptionValidator descriptionValidator = new DescriptionValidator();
       NameValidator nameValidator = new NameValidator();
       TopicValidator topicValidator = new TopicValidator();

       descriptionValidator.validate(topicA);
       nameValidator.validate(topicA);
       System.out.println(topicValidator.validate(topicA));
    }
}