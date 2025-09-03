export class Poll { 
    title: string
    questionText: Question

    constructor(question: Question){
        this.questionText = question;
    }

    setTitle(name: string){
        this.title = name; 
    }
    getTitle(){
        return this.title;
    }

    setQuestion(question: Question){
        this.questionText = question; 
    }

    getQuestion(){
        return this.questionText; 
    }

    toJSON(){
 
        const pollJson = {
            "title": this.title,
            "questionText": this.questionText.toJSON()
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

    toJSON(){ 
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