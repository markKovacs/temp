import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey.model";


@Component({
    moduleId: module.id,
    templateUrl: 'survey-editor.component.html',
    styleUrls: ['survey-editor.component.css'],
    selector: 'survey-editor'

})
export class SurveyEditorComponent implements OnInit{

    @Input() survey: Survey;

    newContent(): void{
        this.survey = new Survey();
        console.log("new content Survey");
    }

    handleChange(): void{
        // this.survey.name = value.name;
        // this.survey.description = value.description;
        // this.survey.order =  value.order;
        console.log(this.survey);

    }

    ngOnInit(): void {
        console.log("CHILD SURVEY " + this.survey);
    }
}