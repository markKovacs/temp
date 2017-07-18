import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey.model";
import {Question} from "../../_models/question.model";


@Component({
    moduleId: module.id,
    templateUrl: 'survey-editor.component.html',
    styleUrls: ['survey-editor.component.css'],
    selector: 'survey-editor'

})
export class SurveyEditorComponent implements OnInit{

    @Input() survey: Survey;
    questions: Question [] = [];

    handleChange(): void{
        console.log(this.survey);

    }

    ngOnInit(): void {
        console.log("CHILD SURVEY " + this.survey);
        this.questions = this.survey.questions;
    }
}