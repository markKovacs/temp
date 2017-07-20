import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey.model";
import {QuestionService} from "../../_services/question.service";
import {Question} from "../../_models/question.model";


@Component({
    moduleId: module.id,
    templateUrl: 'survey-editor.component.html',
    styleUrls: ['survey-editor.component.css'],
    selector: 'survey-editor',
    providers: [QuestionService]

})
export class SurveyEditorComponent implements OnInit{

    @Input() survey: Survey;
    @Input() motivation: boolean;

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

    newQuestion(): void{

        if (!this.survey.motivationVideo) {
            this.survey.questions.push(new Question());
        }
        if (this.survey.motivationVideo && this.survey.questions.length < 1){
            this.survey.questions.push(new Question());
        }
        console.log(this.survey.questions);
    }

    setMotivation(event):void{
        console.log(event.target.checked); // true | false
        if(event.target.checked){
            this.survey.motivationVideo = true;
            this.survey.questions[0].type = "freetext";
            this.motivation = true;
        } else {
            this.motivation = false;
            this.survey.motivationVideo = false;
        }
    }

    ngOnInit(): void {
        console.log("CHILD SURVEY " + JSON.stringify(this.survey));

    }
}