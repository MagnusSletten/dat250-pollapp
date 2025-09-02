package com.example.backend.Model.Poll;

import java.util.Objects;

public class PollOption {
    private String optionName;

    public PollOption(String optionName){
        this.optionName = optionName; 

    }
    public PollOption(){
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollOption that = (PollOption) o;
        return Objects.equals(optionName, that.optionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionName);
    }

    
}
