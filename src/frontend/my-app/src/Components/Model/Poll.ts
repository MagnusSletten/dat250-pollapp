import { use } from "react";

export class Poll {
  title: string;
  question: string;
  voteOptions: VoteOptions;
  creator: string;
  publishedAt: string;   
  validUntil: string;   
  constructor() {
    this.title = "";
    this.question = "";
    this.voteOptions = new VoteOptions();
    this.creator = "";
    const now = new Date();
    this.publishedAt = now.toISOString();
    this.validUntil = new Date(now.getTime() + 60 * 15 * 1000).toISOString();}

  setCreator(creator: string) {
    this.creator = creator;
  }

  getCreator() {
    return this.creator;
  }

  setTitle(name: string) {
    this.title = name;
  }

  getTitle() {
    return this.title;
  }

  setQuestion(question: string) {
    this.question = question;
  }

  getQuestion() {
    return this.question;
  }

  getVoteOptions() {
    return this.voteOptions;
  }

  setPublishedAt(date: Date) {
    this.publishedAt = date.toISOString();
  }

  setValidUntil(date: Date) {
    this.validUntil = date.toISOString();
  }

  toJSON() {
    return {
      title: this.title,
      question: this.question,
      voteOptions: this.voteOptions.toJSON().pollOptions,
      creator: this.creator,
      publishedAt: this.publishedAt,
      validUntil: this.validUntil,
    };
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
            option.setOptionId(this.getNextPollOptionID())
        }
    }

    getNextPollOptionID(){
        this.currPollId++
        return this.currPollId
    }
    toJSON() {
     return { pollOptions: this.voteOptions.map(o => ({
    caption: o.caption,
    presentationOrder: o.presentationOrder
     })) };
    }



    getVoteOptions(){
        return this.voteOptions;
    }
    getOptions(){
        return this.voteOptions.map(option => (option.getCaption()))
    }
    addOption(option: VoteOption){
       option.setOptionId(this.getNextPollOptionID());
       this.voteOptions = [...this.voteOptions,option]
    }

    removeOption(id: number) {
    this.voteOptions = this.voteOptions.filter(opt => opt.optionId !== id);

    this.voteOptions.forEach((opt, idx) => {
        opt.presentationOrder = idx + 1;
    });
    }


  fromJSON(optionsJSON: any[]): VoteOptions {
    this.voteOptions = optionsJSON.map(
      (opt) => new VoteOption(opt.caption, opt.presentationOrder,this.getNextPollOptionID())
    );
    return this; 
  }


    
}

export class VoteOption{
    presentationOrder:number
    caption: string
    optionId: number 

    constructor(caption:string,presentationOrder:number, optionId:number){
        this.caption = caption; 
        this.presentationOrder = presentationOrder
        this.optionId = optionId; 
    }

    getCaption():string {
        return this.caption; 
    }

    setOptionId(id: number){
        this.optionId = id; 

    }

    

}

export class Vote{
    pollId;
    presentationOrder:number
    userName:String | null
    constructor(presentationOrder: number,pollId: number, userName: string|null){
        this.presentationOrder = presentationOrder;
        this.pollId = pollId; 
        this.userName = userName;  
    }
    toJSON(){
        if(!this.userName){
        return {
        "presentationOrder": this.presentationOrder,
        "pollId": this.pollId
        }
        }
        else return {
            
            "presentationOrder": this.presentationOrder,
            "pollId": this.pollId,
            "userName": this.userName
        }

        }
}

