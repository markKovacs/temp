import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey/survey.model";
import {QuestionService} from "../../_services/question.service";
import {Question} from "../../_models/survey/question.model";
import {Option} from "../../_models/survey/option.model";


@Component({
    moduleId: module.id,
    templateUrl: 'survey-editor.component.html',
    styleUrls: ['survey-editor.component.css'],
    selector: 'survey-editor',
    providers: [QuestionService]

})
export class SurveyEditorComponent {

    @Input() survey: Survey;
    @Input() motivation: boolean;
    validate: boolean = true;
    message: string;

    activeSlideIndex = 0;

    constructor(private questionService: QuestionService){}

    postSurvey(): void{
        let count = 0;

        for (let question of this.survey.questions) {
            for (let option of question.options){
                if (option.isCorrect){
                    count++;
                }
            }
            if (count > 0){
                this.validate = true;
            } else {
                this.validate = false;
                this.message = "Questions must have one correct answer!";
                break;
            }
            count = 0;
        }
        if (this.validate) {
            this.questionService.postSurvey(this.survey)
                .subscribe(
                    error => console.log(error),
                    () => console.log('POST - /api/question/save')
                );
        this.survey = null;
        }
    }

    newQuestion(): void{

        if (!this.survey.motivationVideo) {
            this.survey.questions.push(new Question());
        }

    }

    asText(i: any){
        return i + 1 + '';
    }


}