import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Question} from "../../_models/question.model";
import {Option} from "../../_models/option.model";

@Component({
    moduleId: module.id,
    templateUrl: 'question-editor.component.html',
    styleUrls: ['question-editor.component.css'],
    selector: 'question-editor'

})
export class QuestionEditorComponent implements OnInit{

    @Input() question: Question;
    options: Option [] = [];

    newQuestion(): void{
        this.question =new Question;
    }

    ngOnInit(): void {
        this.options = this.question.options
    }
}