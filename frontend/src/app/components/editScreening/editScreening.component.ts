import {Component, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep} from "../../_models/index";
import {Criteria} from "../../_models/screening/criteria";

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

    constructor(private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.getLocation();
        this.getScreening();
    }

    getLocation() {
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getScreening() {
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

    handleChange() {
        this.changeHappened = true;
    }

    postUpdate() {
        this.changeHappened = false;
        console.log("should post this", this.screeningSteps);
        this.client.post("/api/editscreening", this.screeningSteps).subscribe(
            (response: any) => {
                window.location.reload();
            },
                    (error) => console.log(error),
                    () => console.log("Response arrived")
        )
    }

    addStep() {
        this.screeningSteps.push(new ScreeningStep(this.location));
        this.handleChange();
    }

    addCriteria(step: ScreeningStep) {
        step.criteria.push(new Criteria());
        this.handleChange();
    }

    deleteStep(step: ScreeningStep) {
        this.screeningSteps = this.screeningSteps.filter(st => st !== step);
        this.handleChange();
    }

    deleteCriteria(criteria: Criteria, step: ScreeningStep) {
        step.criteria = step.criteria.filter(crit => crit !== criteria);
        this.handleChange();
    }

    evaluateScreenings() {
        this.router.navigate(['evaluatescreenings']);
    }

}
