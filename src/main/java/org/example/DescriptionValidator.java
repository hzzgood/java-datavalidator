package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Range;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@NoArgsConstructor
public class DescriptionValidator implements Validator<Topic> {

    private static Map<String, Range<Integer>> descriptionRuleMap = new HashMap<>();

    @Override
    public boolean validate(Topic input) {
        try {
            // Read Rules from JSON and Load in Map
            ObjectMapper objectMapper = new ObjectMapper();
            String objString = Files.readString(Path.of("src/main/resources/TopicDescriptionRules.json"));
            List<DescriptionRule> ruleList = objectMapper.readValue(objString, new TypeReference<List<DescriptionRule>>(){});
            ruleList.forEach(rule -> {
                Range<Integer> range = Range.between(rule.getMinimumLength(), rule.getMaximumLength());
                descriptionRuleMap.put(rule.getTopic(), range);
            });

            // Use Optional to Complete the Validation
            return Optional.of(input.getDescription().length())
                    .filter(num -> descriptionRuleMap.get(input.getTopic()).contains(num))
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
