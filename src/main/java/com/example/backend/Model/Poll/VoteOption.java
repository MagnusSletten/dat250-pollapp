package com.example.backend.Model.Poll;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class VoteOption {
    private String caption;
    private Integer presentationOrder;
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true) 
    private Poll poll;
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "VoteOption{id=" + optionId +
            ", caption='" + caption + '\'' +
            ", presentationOrder=" + presentationOrder +
            '}';
    }

    
}
