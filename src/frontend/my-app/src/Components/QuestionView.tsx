import { use, useState } from "react";
import { pollOption, Question } from "./Poll";
import { useId } from 'react';

export default function QuestionView({ question }: { question: Question }){
    const [choice, setChoice] = useState(-1);
    const [name, setName] = useState(question.getQuestion())
    const [newOption, setNewOption] = useState("")
    const changeName = (name: string) => {
        setName(name);
        question.setQuestion(name);

    }
    const [options,setOptions] = useState(question.getOptions())
    const addOption = (option:pollOption) => {
        setOptions([...question.getOptions(), option.getOption()])
        question.addOption(option)
    }
   

    const setChosenOption= (optionNumber: number) => {
        setChoice(optionNumber)
        question.setChoice(optionNumber)
    }
    return(
        <div className="Question">
        <input
          value={name}
          placeholder="Question to ask"
          className="Question-field"
          onChange={(e) => ( changeName(e.target.value)

          )}
          >
        </input>
        <div></div>
        <div className="option-box">
            {question.pollOptions.map((option,o_i) => (
                <div className="pollButtonBox">
                <button
                key={option.id} 
                onClick={ e => setChosenOption(o_i)}
                style = {{backgroundColor: choice === o_i ? "blue" : "green"}    
                }>{option.getOption()}</button>
                </div>
            ))}
            <div>
                <input
                value= {newOption}
                id={useId()}
                placeholder="New option"
                onChange={e => setNewOption(e.target.value)}
                ></input>
                <button
                onClick={e => addOption(new pollOption(newOption))}>
                
            Add Option button</button>
            </div>
        </div>

        </div>
         
    )

}