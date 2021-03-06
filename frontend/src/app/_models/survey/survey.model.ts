import {Question} from './question.model';

export class Survey {

    id: string;
    locationId:string;
    orderInBundle:number;
    description:string;
    name:string;
    threshold:number;
    maxPoints:number;
    enabled:boolean;
    estimatedTime:number;
    motivationVideo:boolean;
    surveyContent: string;
    questions: Question[] = [];

    constructor(){
        if (!this.id){
            this.id = Math.random().toString(36).substr(2, 9);
        }
    }

}
