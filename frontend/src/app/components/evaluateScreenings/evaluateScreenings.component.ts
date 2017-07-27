import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep, Criteria, User, UsersScreeningStep} from "../../_models/index";
import {DateFormatPipe} from "angular2-moment";
import {Message} from 'primeng/primeng';


@Component({
    moduleId: module.id,
    templateUrl: 'evaluateScreenings.component.html',
    styleUrls: ['evaluateScreenings.component.css']
})
export class EvaluateScreeningsComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];
    public users: User[];
    public user: User;
    public toEvaluate: UsersScreeningStep;
    public chosenStep: ScreeningStep;
    public chosenCriteria: Criteria;
    public messages: Message[] = [];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.getLocation();
        this.getApplicants();
        this.getSteps();
    }

    editScreening(){
        this.router.navigate(['editscreening']);
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getApplicants(){
        this.client.get('/api/screening/list?location=' + this.location.id + '&signedback=true').subscribe(
            (users: User[]) => this.users = users.sort((one, other) => {
                return one.screeningPersonalTime > other.screeningPersonalTime ? 1 : -1;
            }),
            (error) => error,
            () => console.log("Applicants arrived", this.users)
        )
    }

    getSteps(){
        this.client.get('/api/screeningsteps?location=' + this.location.id).subscribe(
            (steps: ScreeningStep[]) => this.screeningSteps = steps,
            (error) => error,
            () => console.log("Steps arrived")
        )
    }

    chooseUser(user){
        this.user = user;
        this.toEvaluate = null;
    }

    getApplicantsStep(step){
        let url = '/api/evalscreening/' + this.user.adminId + '?step=' + step.id;
        this.chosenStep = step;
        this.toEvaluate = null;
        this.client.get(url).subscribe(
            (data: UsersScreeningStep) => this.toEvaluate = data,
            (error) => error,
            () => console.log("Applicant's step arrived", this.toEvaluate)
        )
    }

    setStepStatus(status){
        this.toEvaluate.screeningStep.status = status;
    }

    setCriteriaStatus(criteria, status){
        criteria.status = status;
    }

    postUpdate(){
        console.log("Should post this: ", this.toEvaluate.screeningStep);
        this.client.post('/api/evalscreening', this.toEvaluate.screeningStep).subscribe(
            (data: any) => this.messages.push(
                {
                    severity: 'success',
                    summary: 'Save completed',
                    detail: this.toEvaluate.name
                }),
            (error) => error,
            () => console.log("Applicant's step updated")
        )
    }

    isActiveStepStatus(status){
        return this.toEvaluate.screeningStep.status == status ? "active-button" : "inactive-button";
    }

    isActiveCriteriaStatus(criteria, status){
        return criteria.status == status ? "active-button" : "inactive-button";
    }

    isActiveStep(step){
        if (this.toEvaluate) {
            return this.toEvaluate.screeningStep.stepId == step.id ? "active-button" : "inactive-button";
        }
        return "inactive-button";
    }

    getNameForCriteria(step, criteria){
        let currentStep = this.screeningSteps.filter(entry => entry.id == step.stepId)[0];
        let currentCriteria = currentStep.criterias.filter(entry => entry.id == criteria.criteriaId)[0];
        return currentCriteria.name;
    }

}
