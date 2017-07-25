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
    public isFetchingSteps: boolean = false;
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
        this.isFetchingSteps = true;
        this.client.get("/api/editscreening?location=" + this.location.id).subscribe(
            (screeningSteps: ScreeningStep[]) => {
                this.screeningSteps = screeningSteps;
                this.isFetchingSteps = false;
            },
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

    generateId(){
      return Math.random() * 1000000000
    }

    allowPost(){
      let allowed = true;
      for (let step of this.screeningSteps) {
          if (step.name == "") { allowed = false; }
          for (let criteria of step.criterias) {
              if (criteria.name == "") { allowed = false; }
          }
      }
      return allowed;
    }

    addStep(){
        this.handleChange();
        let id = this.generateId()
        let newStep = {id: id, name: "", locationId: "BUD", criterias: []};
        this.screeningSteps.push(newStep);
    }

    addCriteria(stepId){
        this.handleChange();
        let id = this.generateId()
        let newCriteria = {id: id, name: ""}
        console.log(stepId);
        for (let step of this.screeningSteps) {
            console.log(step.id);
            if (step.id == stepId) { step.criterias.push(newCriteria); }
        }
    }

    deleteStep(stepId){
        this.handleChange();
        this.screeningSteps = this.screeningSteps.filter((step) => {
            return step.id != stepId;
        })
    }

    deleteCriteria(criteriaId){
        this.handleChange();
        for (let step of this.screeningSteps) {
            step.criterias = step.criterias.filter((criteria) => {
                return criteriaId != criteria.id
            })
        }
    }

    evaluateScreenings(){
        this.router.navigate(['evaluatescreenings']);
    }

    mockGetScreening(){
        this.isFetchingSteps = true;
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
            this.isFetchingSteps = false;
            console.log("Screening mocked", this.screeningSteps);
        }, 1000)
    }

    mockPostUpdate(){
        if (this.allowPost()){
            console.log("Should post this", this.screeningSteps);
            this.isPosting = true;
            setTimeout(()=>{
                this.isPosting = false;
                this.changeHappened = false;
            }, 1000)
        }
    }

}
