package org.study.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String title;

    @JsonProperty
    private boolean completed;

}
