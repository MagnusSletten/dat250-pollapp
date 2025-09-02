export class Poll {
    curr_question_id = 0;  
    title: string
    questions: Question[]

    constructor(questions: Question[] = []){
        this.questions = questions;
        for (const q of questions){
            q.setID(this.getNextID())
        }
    }

    setTitle(name: string){
        this.title = name; 
    }
    getTitle(){
        return this.title;
    }
    getNextID(){
        this.curr_question_id ++
        return this.curr_question_id
    }
    addQuestion(question: Question){
        this.curr_question_id ++;
        question.setID(this.curr_question_id)
        this.questions = [...this.questions, question]
    }

    getQuestions(){
        return this.questions; 
    }

    removeQuestion(id:number){
        this.questions = this.questions.filter(question => question.id != id)

    }

    toJSON(){
        const list = this.questions.map( question => (
            question.toJSON
        ))
        const questionJSON = Object.fromEntries(list.map( (item, index) => [item])
        )
        const pollJson = {
            "title": this.title,
            "questions": list
        }
        return pollJson; 
    }
}  



export class Question{
    id: number 
    question: string
    pollOptions: pollOption[]
    currPollId = 0; 
    choice: number | null = null; 

    constructor(question = "", pollOptions: pollOption[] = []) {
    this.question = question;
    this.pollOptions = pollOptions; 
    this.setPollIDs; 
    
    }
    setPollIDs(){
        for(const option of this.pollOptions){
            option.setId(this.getNextPollOptionID())
        }
    }

    getNextPollOptionID(){
        this.currPollId++
        return this.currPollId
    }
    setID(id:number){
        this.id = id;  
    }  

    get toJSON(){ 
        const json = {
        "question": this.question,
        "pollOptions": this.getOptions()  }
        return json 
    }
    setChoice(number:number){
        this.choice = number
    }

    getPollOptions(){
        return this.pollOptions;
    }
    getOptions(){
        return this.pollOptions.map(option => option.getOption())
    }
    addOption(option: pollOption){
       option.setId(this.getNextPollOptionID());
       this.pollOptions = [...this.pollOptions,option]
    }
    setQuestion(question:string){
        this.question = question; 
    }
    getQuestion(){
        return this.question
    }

   removeOption(id:number){
        this.pollOptions = this.pollOptions.filter(pollOption => pollOption.id != id)

    }
    getId(){
        return this.id; 
    }

    
}

export class pollOption{
    id:number
    option: string 

    constructor(option:string){
        this.option = option; 
    }

    setId(id:number){
        this.id = id
    }
    getOption(){
        return this.option; 
    }

    

}