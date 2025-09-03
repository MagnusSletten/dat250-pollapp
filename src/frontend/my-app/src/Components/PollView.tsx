import { useState } from "react";
import { Poll, Question } from "./Poll";
import QuestionView from "./QuestionView";

export default function PollView({ poll }: { poll: Poll }) {
  const [question, setQuestion] = useState(poll.getQuestion())
  const [showJSON, setShowJSON] = useState(false)
  const [title, setTitle] = useState(poll.getTitle())
  const putQuestion = (question: Question) => {
    poll.setQuestion(question)
    setQuestion(poll.getQuestion())
    
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
            value={title}>
        </input>
        <div className="question-box">
          <QuestionView question={poll.getQuestion()}/>
        </div>
                
       
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