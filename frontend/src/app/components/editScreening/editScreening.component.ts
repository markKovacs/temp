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
        this.getLocation();
        this.getScreening();
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
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
        console.log("should post this", this.screeningSteps);
        this.client.post("/api/editscreening", this.screeningSteps).subscribe(
            (response: any) => console.log(response),
            (error) => console.log(error),
            () => console.log("Response arrived")
        )
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
        this.screeningSteps.push(new ScreeningStep(this.location.id));
        this.handleChange();
    }

    addCriteria(step){
        step.criterias.push(new Criteria());
        this.handleChange();
    }

    deleteStep(step){
        step.enabled = false;
        step.criterias = step.criterias.map((entry) => {
            let updated = Object.assign({}, entry);
            updated.enabled = false;
            return updated;
        })
        this.handleChange();
    }

    deleteCriteria(criteria){
        criteria.enabled = false;
        this.handleChange();
    }

    evaluateScreenings(){
        this.router.navigate(['evaluatescreenings']);
    }

}
