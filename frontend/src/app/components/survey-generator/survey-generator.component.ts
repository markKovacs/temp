import {Component, OnInit} from "@angular/core";
import {LocationTestService} from "../../_services/location-tests.service";
import {Router} from "@angular/router";
import {Survey} from "../../_models/survey.model";

@Component({
    moduleId: module.id,
    templateUrl: 'survey-generator.component.html',
    styleUrls: ['survey-generator.component.css'],
    selector: 'survey-generator'

})
export class SurveyGeneratorComponent implements OnInit{

    surveys: Survey[] = [];
    model: Survey;
    locationId: string;

    constructor(
        private testsByLocation: LocationTestService,
        private router: Router
    ){
    }

    currentTest(test: Survey){
        if (test == null){
            this.model = new Survey();
            this.model.enabled = true;
            this.model.motivationVideo = false;
            this.model.locationId = this.locationId;//localStorage.
        } else{
            this.model = test;
        }
    }


    ngOnInit(): void {
        this.testsByLocation.getTestByLocation()
            .subscribe(
                (data: Survey[]) => {
                    console.log("api test2");
                    for (const test of data) {
                        this.surveys.push(test);
                    }
                    //this.surveys = JSON.parse(data.formAsJson);
                },
                error => console.log(error),
                // () => console.log('getSurvey().subscribe() finished running.')
            );

        if (localStorage.chosenLocation != null) {
            this.locationId = JSON.parse(localStorage.chosenLocation).id;
        }
    }
}
