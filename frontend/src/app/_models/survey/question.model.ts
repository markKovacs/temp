import {Option} from './option.model';

export class Question {

    id: string;
    questionContent: string;
    type: string;
    options: Option[] = [];

    constructor(){
        if (!this.id){
            this.id = Math.random().toString(36).substr(2, 9);
        }
    }

}
