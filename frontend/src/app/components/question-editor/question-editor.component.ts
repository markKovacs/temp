import {Component, OnInit, Input} from "@angular/core";
import {Question} from "../../_models/survey/question.model";
import {Option} from "../../_models/survey/option.model";

@Component({
    moduleId: module.id,
    templateUrl: 'question-editor.component.html',
    styleUrls: ['question-editor.component.css'],
    selector: 'question-editor'

})
export class QuestionEditorComponent implements OnInit{

    @Input() question: Question;
    @Input() motivation: boolean;
    @Input() i: number;

    newOption(): void{
        this.question.options.push(new Option());
    }

    ngOnInit(): void {
    }

    getQuestionNumber(){
        return this.i + 1 + "";
    }
}