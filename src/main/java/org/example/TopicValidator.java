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
public class TopicValidator implements Validator<Topic> {

    private static Map<String, Range<Integer>> descriptionRuleMap = new HashMap<>();
    private static Map<String, String> nameRuleMap = new HashMap<>();

    private static final Function<Topic, Boolean> topicAValidator = topic ->
            Optional.of(topic)
            .filter(top -> nameRuleMap.get("A").equals(top.getName()))
            .filter(top -> descriptionRuleMap.get("A").contains(top.getDescription().length()))
            .isPresent();

    private static final Function<Topic, Boolean> topicBValidator = topic ->
            Optional.of(topic)
            .filter(top -> nameRuleMap.get("B").equals(top.getName()))
            .filter(top -> descriptionRuleMap.get("B").contains(top.getDescription().length()))
            .isPresent();

    private static Map<String, Function<Topic, Boolean>> VALIDATORS = new HashMap<>();
    static {
        VALIDATORS.put("A", topicAValidator);
        VALIDATORS.put("B", topicBValidator);
    }

    @Override
    public boolean validate (Topic topic) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String objString = Files.readString(Path.of("src/main/resources/TopicDescriptionRules.json"));
            List<DescriptionRule> ruleList = objectMapper.readValue(objString, new TypeReference<List<DescriptionRule>>(){});
            ruleList.forEach(rule -> {
                Range<Integer> range = Range.between(rule.getMinimumLength(), rule.getMaximumLength());
                descriptionRuleMap.put(rule.getTopic(), range);
                nameRuleMap.put(rule.getTopic(), rule.getName());
            });
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return VALIDATORS.get(topic.getTopic()).apply(topic);
    }

}
