import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey.model";
import {QuestionService} from "../../_services/question.service";


@Component({
    moduleId: module.id,
    templateUrl: 'survey-editor.component.html',
    styleUrls: ['survey-editor.component.css'],
    selector: 'survey-editor',
    providers: [QuestionService]

})
export class SurveyEditorComponent implements OnInit{

    @Input() survey: Survey;

    constructor(private questionService: QuestionService){}

    handleChange(): void{
        console.log(this.survey);
    }

    postSurvey(): void{
        this.questionService.postSurvey(this.survey)
            .subscribe(
                // .() =>{ this.survey = null;}
                error => console.log(error),
                () => console.log('POST - /api/question/save')
            );
    }

    ngOnInit(): void {
        console.log("CHILD SURVEY " + JSON.stringify(this.survey));
    }
}