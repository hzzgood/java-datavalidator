package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DescriptionRule {
    private String topic;
    private String name;
    private int minimumLength;
    private int maximumLength;
}
