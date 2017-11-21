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
    public expand : boolean = false;
    public expandId: string;

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
            (response: any) => this.getScreening(),
            (error) => console.log(error),
            () => console.log("Response arrived")
        )
    }

    addStep() {
        this.screeningSteps.push(new ScreeningStep(this.location));
        this.handleChange();
    }

    addCriteria(step: ScreeningStep) {
        console.log(step);
        step.criteria.push(new Criteria());
        this.handleChange();
    }

    deleteStep(step: ScreeningStep) {
        step.enabled = false;
        step.criteria = step.criteria.map((entry) => {
            let updated = Object.assign({}, entry);
            updated.enabled = false;
            return updated;
        });
        this.handleChange();
    }

    deleteCriteria(criteria: Criteria) {
        criteria.enabled = false;
        this.handleChange();
    }

    evaluateScreenings() {
        this.router.navigate(['evaluatescreenings']);
    }

    expandRubrics(criteriaId: string) {
        if (criteriaId != this.expandId){
            this.expand = true;
            this.expandId = criteriaId;
        }else {
            this.expand = false;
            this.expandId = null;
        }
    }
}
