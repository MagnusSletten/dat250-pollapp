export class Poll {
    curr_question_id = 0;  
    pollName: string
    questions: Question[]

    constructor(questions: Question[] = []){
        this.questions = questions;
        for (const q of questions){
            q.setID(this.getNextID())
        }
    }

    setName(name: string){
        this.pollName = name; 
    }
    getName(){
        return this.pollName;
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
        const questionJSON = Object.fromEntries(list.map( (item, index) => [index,item])
        )
        const pollJson = {
            "name": this.pollName,
            "questions": questionJSON
        }
        return pollJson; 
    }
}  



export class Question{
    id: number 
    question: string
    options: string[]
    choice: number | null = null; 

    constructor(question = "", options: string[] = []) {
    this.question = question;
    this.options = options; 
    
    }
    setID(id:number){
        this.id = id;  
    }  

    get toJSON(){ 
        const json = {
        "question": this.question,
        "options": this.options  }
        return json 
    }
    setChoice(number:number){
        this.choice = number
    }

    getOptions(){
        return this.options;
    }
    addOption(option: string){
       this.options = [...this.options,option]
    }
    setQuestion(question:string){
        this.question = question; 
    }
    getQuestion(){
        return this.question
    }

    removeOption(option_number: number){
        return [...this.options.slice(0,option_number),...this.options.slice(option_number+1)]

    }
    getId(){
        return this.id; 
    }

    
}

