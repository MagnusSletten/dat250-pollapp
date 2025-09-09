import { useState } from "react";
import { Poll, VoteOptions } from "./Poll";
import VoteOptionsView from "./VoteOptionsView";

export default function PollView({ poll }: { poll: Poll }) {
  const [question, setQuestion] = useState(poll.getQuestion())
  const [showJSON, setShowJSON] = useState(false)
  const [title, setTitle] = useState(poll.getTitle())
  const [creator,setCreator] = useState(poll.getCreator())

  const changeCreator = (name:string) => {
    poll.setCreator(name);
    setCreator(poll.getCreator());
  }
 
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
        <div>
          <input 
            value={question}
            placeholder="Question to ask"
            className="Question-field"
            onChange={(e) => ( changeQuestion(e.target.value)

            )}
            >
          </input>
        </div>
        <div className="option-box">
          <VoteOptionsView voteoptions={poll.getVoteOptions()}/>
        </div>
        <input
            className="creator"
            placeholder="Set creator for the poll"
            onChange={(e) => changeCreator(e.target.value)}
            value={creator}>
        </input>
                
       
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