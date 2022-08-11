package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NameValidator implements Validator<Topic> {

    private static Map<String, String> nameRuleMap = new HashMap<>();
    @Override
    public boolean validate(Topic input) {

        try {
            // Read Rules from JSON and Load in Map
            ObjectMapper objectMapper = new ObjectMapper();
            String objString = Files.readString(Path.of("src/main/resources/TopicDescriptionRules.json"));
            List<DescriptionRule> ruleList = objectMapper.readValue(objString, new TypeReference<List<DescriptionRule>>(){});
            ruleList.forEach(rule -> {
                nameRuleMap.put(rule.getTopic(), rule.getName());
            });

            // Use Optional to Complete the Validation
            return Optional.of(input.getName())
                    .filter(name -> nameRuleMap.get(input.getTopic()).equals(input.getName()))
                    .isPresent();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
