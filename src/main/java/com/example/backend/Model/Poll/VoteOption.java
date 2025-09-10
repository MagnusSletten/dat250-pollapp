package com.example.backend.Model.Poll;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteOption {
    private String caption;
    private Integer presentationOrder;
    @JsonIgnore
    private Integer optionId; 

    public VoteOption(String caption){
        this.caption = caption; 

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteOption that = (VoteOption) o;
        return Objects.equals(caption, that.caption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caption);
    }

    
}
