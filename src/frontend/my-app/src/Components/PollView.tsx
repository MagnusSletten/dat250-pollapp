import { useState } from "react";
import { Poll, Question } from "./Poll";
import QuestionView from "./QuestionView";

export default function PollView({ poll }: { poll: Poll }) {
  const [questions, setQuestions] = useState(poll.getQuestions())
  const [showJSON, setShowJSON] = useState(false)
  const [title, setTitle] = useState(poll.getTitle())
  const addQuestion = (question: Question) => {
    poll.addQuestion(question)
    setQuestions(poll.getQuestions())
    
  }
  const deleteQuestion = (id:number) =>{
    poll.removeQuestion(id);
    setQuestions(poll.getQuestions())
  }

  const updateTitle = (title:string) => {
    poll.setTitle(title)
    setTitle(poll.getTitle())
  }
  return (
    <div className="Poll">
    <>
      <div className="top">
      <button
      onClick={e => setShowJSON(!showJSON)}
      className="button-json"
      >ShowJson</button>
      </div>
      {!showJSON && (
        <>
      <input
        className="title"
        placeholder="Set title for the poll"
        onChange={(e) => updateTitle(e.target.value)}
        value={title}></input>
        <div>
          {questions.map((question: Question) => (
            <div key={question.id}>
              <div className="question-box">
              <div className="delete-question-box">
              <button 
                className="delete-question-button"
                onClick={()=> deleteQuestion(question.id)}
                >Delete</button>
              </div>
              <QuestionView 
                key = {question.id}
                question={question}/>
              </div>
           </div>
          ))}
        </div>
        <button
        onClick={() => addQuestion(new Question())} 
        >New Question</button>
        </>  
    )
     }
    {showJSON && (
      <pre>
    {JSON.stringify(poll.toJSON(), null, 2)}
      </pre>

       )}
   
</>
</div>
  )
}