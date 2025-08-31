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
    pollOptions: string[]
    choice: number | null = null; 

    constructor(question = "", pollOptions: string[] = []) {
    this.question = question;
    this.pollOptions = pollOptions; 
    
    }
    setID(id:number){
        this.id = id;  
    }  

    get toJSON(){ 
        const json = {
        "question": this.question,
        "pollOptions": this.pollOptions  }
        return json 
    }
    setChoice(number:number){
        this.choice = number
    }

    getOptions(){
        return this.pollOptions;
    }
    addOption(option: string){
       this.pollOptions = [...this.pollOptions,option]
    }
    setQuestion(question:string){
        this.question = question; 
    }
    getQuestion(){
        return this.question
    }

    removeOption(option_number: number){
        return [...this.pollOptions.slice(0,option_number),...this.pollOptions.slice(option_number+1)]

    }
    getId(){
        return this.id; 
    }

    
}

