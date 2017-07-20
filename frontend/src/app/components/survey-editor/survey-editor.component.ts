import {Component, Input, OnInit} from "@angular/core";
import {Survey} from "../../_models/survey.model";
import {QuestionService} from "../../_services/question.service";
import {Question} from "../../_models/question.model";
import {Option} from "../../_models/option.model";


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
    validate: boolean = true;
    message: string;

    constructor(private questionService: QuestionService){}

    handleChange(): void{
        console.log(this.survey);
    }

    postSurvey(): void{
        let count = 0;

        for (let question of this.survey.questions) {
            for (let option of question.options){
                if (option.isCorrect){
                    count++;
                }
            }
            if (question.type == "checkbox"){
                if (count < 2){
                    this.validate = false;
                    this.message = "CheckBox question must have more then one correct answer!"
                }
            }
            if (question.type == "radio"){
                if (count != 1){
                    this.validate = false;
                    this.message = "Radio box question must have one correct answer!"
                }
            }
            if (question.type == "freetext"){
                if (count != 0){
                    this.validate = false;
                    this.message = "Motivation question don't has a correct answer!"
                }
            }
            count = 0;
        }
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
        this.newQuestion();
        //fixme what to do in edit case
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