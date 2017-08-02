import {Component, Input} from "@angular/core";
import {Survey} from "../../_models/survey.model";
import {Question} from "../../_models/question.model";

@Component({
    moduleId: module.id,
    templateUrl: 'survey-base-data.component.html',
    selector: 'survey-data'

})
export class SurveyBaseDataComponent {

    @Input() survey: Survey;
    @Input() motivation: boolean;

    setMotivation(event):void{

        this.survey.questions.push(new Question());

        if(event.target.checked){
            this.survey.motivationVideo = true;
            this.survey.questions[0].type = "freetext";
            this.motivation = true;
        } else {
            this.motivation = false;
            this.survey.motivationVideo = false;
        }
    }

    getPercentage(): any {
        if(!this.survey.maxPoints || !this.survey.threshold) {
            return "";
        }
        return Math.round((this.survey.threshold / this.survey.maxPoints)*100) + " %";
    }


}