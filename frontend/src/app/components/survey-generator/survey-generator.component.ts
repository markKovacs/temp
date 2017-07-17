import {Component, OnInit, Input, ViewChild, AfterViewInit} from '@angular/core';
import {LocationTestService} from "../../_services/location-tests.service";
import {Router} from "@angular/router";
import {Survey} from "../../_models/survey.model";
import {SurveyEditorComponent} from "../survey-editor/survey-editor.component";
import {Option} from "../../_models/option.model";
import {Question} from "../../_models/question.model";
import {QuestionEditorComponent} from "../question-editor/question-editor.component";

@Component({
    moduleId: module.id,
    templateUrl: 'survey-generator.component.html',
    styleUrls: ['survey-generator.component.css'],
    selector: 'survey-generator'

})
export class SurveyGeneratorComponent implements OnInit, AfterViewInit{

    model: Survey[] = [];

    constructor(
        private testsByLocation: LocationTestService,
        private router: Router
    ){
    }

    sendInfo() : void{
        console.log(this.model);
        console.log("New info recieved");
}
    currentTest(test: Survey){
        console.log(test);
    }

    ngAfterViewInit(): void {
    }


    ngOnInit(): void {
        this.testsByLocation.getTestByLocation()
            .subscribe(
                (data: Survey[]) => {
                    console.log("api test2");
                    for (const test of data) {
                        this.model.push(test);
                        console.log("test" + test);
                    }
                    //this.model = JSON.parse(data.formAsJson);
                },
                error => console.log(error),
                // () => console.log('getSurvey().subscribe() finished running.')
            );
    }
}
