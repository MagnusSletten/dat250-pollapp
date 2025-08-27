import { useState } from "react";
import { Poll, Question } from "./Poll";
import QuestionView from "./QuestionView";

export default function PollView({ poll }: { poll: Poll }) {
  const [questions, setQuestions] = useState(poll.getQuestions())
  const [showJSON, setShowJSON] = useState(false)
  const addQuestion = (question: Question) => {
    poll.addQuestion(question)
    setQuestions(poll.getQuestions())

  }
  const deleteQuestion = (id:number) =>{
    poll.removeQuestion(id);
    setQuestions(poll.getQuestions())
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
      <h1>{poll.getName()}</h1>
        <div>
          {questions.map((question: Question) => (
            <div key={question.id}>
            <button 
             onClick={()=> deleteQuestion(question.id)}
            >Delete</button>
            <QuestionView 
            key = {question.id}
            question={question}/>
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