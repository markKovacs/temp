import {Component, OnInit, Input} from "@angular/core";
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
    @Input() motivation: boolean;

    newOption(): void{
        this.question.options.push(new Option());
    }

    deleteQuestion(): void{
        this.question = null;
    }

    ngOnInit(): void {
    }
}