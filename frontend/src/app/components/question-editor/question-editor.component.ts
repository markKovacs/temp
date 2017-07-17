import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {Question} from "../../_models/question.model";

@Component({
    moduleId: module.id,
    templateUrl: 'question-editor.component.html',
    styleUrls: ['question-editor.component.css'],
    selector: 'question-editor'

})
export class QuestionEditorComponent implements OnInit{

    @Input() question: Question;

    newQuestion(): void{
        this.question =new Question;
    }

    ngOnInit(): void {
    }
}