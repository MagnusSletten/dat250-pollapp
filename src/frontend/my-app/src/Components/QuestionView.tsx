import { use, useState } from "react";
import { Question } from "./Poll";

export default function QuestionView({ question }: { question: Question }){
    const [choice, setChoice] = useState(-1);
    const [name, setName] = useState(question.getQuestion())
    const [newOption, setNewOption] = useState("")
    const changeName = (name: string) => {
        setName(name);
        question.setQuestion(name);

    }
    const [options,setOptions] = useState(question.getOptions())
    const addOption = (option:string) => {
        setOptions([...question.getOptions(), option])
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
          className="Question_box"
          onChange={(e) => ( changeName(e.target.value)

          )}
          >
        </input>
        <div>
            {question.options.map((option,o_i) => (
                <button 
                onClick={ e => setChosenOption(o_i)}
                style = {{backgroundColor: choice === o_i ? "blue" : "green"}    
                }>{option}</button>
            ))}
            <div>
                <input
                value= {newOption}
                placeholder="New option"
                onChange={e => setNewOption(e.target.value)}
                ></input>
                <button
                onClick={e => addOption(newOption)}>
                
            Add Option button</button>
            </div>
        </div>

        </div>
         
    )

}