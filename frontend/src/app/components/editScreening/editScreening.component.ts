import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep, Criteria} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'editScreening.component.html',
    styleUrls: ['editScreening.component.css']
})
export class EditScreeningComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];
    public changeHappened: boolean = false;
    public isPosting: boolean = false;
    public postList: any[] = [];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        // this.getLocation();
        // this.getScreening();
        this.mockGetScreening();
    }

    getLocation(){
        this.location = JSON.parse(localStorage.location);
    }

    getScreening(){
        this.client.get("/api/editscreening?location=" + this.location.id).subscribe(
            (screeningSteps: ScreeningStep[]) => this.screeningSteps = screeningSteps,
            (error) => console.log(error),
            () => console.log("Screening fetched", this.screeningSteps)
        )
    }

    handleChange(){
        this.changeHappened = true;
    }

    postUpdate(){
        this.changeHappened = false;
        this.client.post("/api/editscreening", this.screeningSteps).subscribe(
            (response: any) => console.log(response),
            (error) => console.log(error),
            () => console.log("Response arrived")
        )
    }

    addStep(){
        let newStep = {id: null, name: "", locationId: "BUD", criterias: []};
        this.screeningSteps.push(newStep);
    }

    addCriteria(stepId){
        let newCriteria = {id: null, name: ""}
        console.log(stepId);
        for (let step of this.screeningSteps) {
            console.log(step.id);
            if (step.id == stepId) { step.criterias.push(newCriteria); }
        }
    }

    deleteStep(stepId){
        this.screeningSteps = this.screeningSteps.filter((step) => {
            return step.id != stepId;
        })
    }

    deleteCriteria(criteriaId){
        for (let step of this.screeningSteps) {
            step.criterias = step.criterias.filter((criteria) => {
                return criteriaId != criteria.id
            })
        }
    }

    mockGetScreening(){
        let data = [
            {id: 1, name: "Group Game", locationId: "BUD", criterias: [
                {id: 1, name: "Potential"}, {id: 2, name: "Something else"}
            ]},
            {id: 2, name: "Mindset", locationId: "BUD", criterias: [
                {id: 3, name: "Potential"}
            ]},
            {id: 3, name: "Life", locationId: "BUD", criterias: [
                {id: 4, name: "Potential"}, {id: 5, name: "Whatever"}, {id: 6, name: "Potential"}
            ]}
        ]
        setTimeout(()=>{
            this.screeningSteps = data;
            console.log("Screening mocked", this.screeningSteps);
        }, 1000)
    }

    mockPostUpdate(){
        console.log("Should post this", this.screeningSteps);
        this.changeHappened = false;
    }

}
