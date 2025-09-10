export class Poll { 
    title: string
    question: string
    voteOptions : VoteOptions
    creator: string; 

    constructor(question: string){
        this.question = question;
        this.voteOptions = new VoteOptions()
    }

    setCreator(creator:string){
        this.creator = creator; 
    }
    getCreator(){
        return this.creator; 
    }

    setTitle(name: string){
        this.title = name; 
    }
    getTitle(){
        return this.title;
    }

    setQuestion(question: string){
        this.question = question; 
    }

    getQuestion(){
        return this.question; 
    }
    getVoteOptions(){
        return this.voteOptions;
    }

    toJSON(){
 
        const pollJson = {
            "title": this.title,
            "question": this.question,
            "voteOptions": this.voteOptions.toJSON(),
            "creator": this.getCreator()
        }
        return pollJson; 
    }
}  



export class VoteOptions{
    voteOptions: VoteOption[]
    currPollId = 0; 

    constructor(voteOptions: VoteOption[] = []) {
    this.voteOptions = voteOptions; 
    this.setPollIDs; 
    
    }
    setPollIDs(){
        for(const option of this.voteOptions){
            option.setId(this.getNextPollOptionID())
        }
    }

    getNextPollOptionID(){
        this.currPollId++
        return this.currPollId
    }
    toJSON() {
    return this.getOptions().map((caption) => ({ caption }));
    }



    getVoteOptions(){
        return this.voteOptions;
    }
    getOptions(){
        return this.voteOptions.map(option => option.getCaption())
    }
    addOption(option: VoteOption){
       option.setId(this.getNextPollOptionID());
       this.voteOptions = [...this.voteOptions,option]
    }

   removeOption(id:number){
        this.voteOptions = this.voteOptions.filter(pollOption => pollOption.id != id)

    }


    
}

export class VoteOption{
    id:number
    caption: string 

    constructor(caption:string){
        this.caption = caption; 
    }

    setId(id:number){
        this.id = id
    }
    getCaption():string {
        return this.caption; 
    }

    

}

export class Vote{
    optionId:string
    constructor(optionId){
        this.optionId = optionId; 
    }
    toJSON(){
        optionId: this.optionId
    }

}