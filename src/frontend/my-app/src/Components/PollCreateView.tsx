import { useState } from "react";
import { Poll, VoteOptions } from "./Model/Poll";
import VoteOptionsView from "./VoteOptionsView";

export default function PollCreateView({ poll }: { poll: Poll }) {
  const [question, setQuestion] = useState(poll.getQuestion())
  const [showJSON, setShowJSON] = useState(false)
  const [title, setTitle] = useState(poll.getTitle())
 
  const changeQuestion = (name: string) => {
      poll.setQuestion(name);
      setQuestion(poll.getQuestion());

    }

  const updateTitle = (title:string) => {
    poll.setTitle(title)
    setTitle(poll.getTitle())
  }
  return (
    <div className="Poll">
    <>
      {!showJSON && (
        <>
        <input
            className="title"
            placeholder="Set title for the poll"
            onChange={(e) => updateTitle(e.target.value)}
            value={title}>
            required
        </input>
        <div>
          <input 
            value={question}
            placeholder="Question to ask"
            className="Question-field"
            onChange={(e) => ( changeQuestion(e.target.value)

            )}
            required
            >
          </input>
        </div>
        <div className="option-box">
          <VoteOptionsView voteoptions={poll.getVoteOptions()}/>
        </div>  
        </>  
    )
     }
     
    {showJSON && (
      <pre>
    {JSON.stringify(poll.toJSON(), null, 2)}
      </pre>

       )}

    <div className="top">
      <button
      onClick={e => setShowJSON(!showJSON)}
      className="button-json"
      >ShowJson</button>
      </div>
   
</>
</div>
  )
}