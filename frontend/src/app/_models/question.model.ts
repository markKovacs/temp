import {Option} from './option.model';

export class Question {

    id: string;
    questionContent: string;
    type: string;
    options: Option[] = [];

}
