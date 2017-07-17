import {Question} from './question.model';

export class Survey {

    id: string;
    locationId:string;
    orderInBundle:number;
    description:string;
    name:string;
    threshold:number;
    maxPoint:number;
    enabled:boolean;
    estimatedTime:number;
    motivationVideo:string;
    surveyContent: string;
    questions: Question[] = [];

}
