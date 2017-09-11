import {Component, Input} from '@angular/core';
import {GlobalEventsManager} from "../../../global.eventsmanager";
import {HttpClient} from "../../../_httpclient/httpclient";
import {Location, ScreeningStep, UsersScreeningStep} from "../../../_models/index";
import {Message} from 'primeng/primeng';
import {ScreeningInfo} from "../../../_models/screeninginfo.model";
import {AlertService} from "../../../_services/alert.service";

@Component({
    moduleId: module.id,
    selector: 'evaluate-steps',
    templateUrl: 'evaluate-steps.component.html',
    styleUrls: ['evaluate-steps.component.css']
})
export class EvaluateStepsComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];
    public users: ScreeningInfo[];
    @Input() user: ScreeningInfo;
    public toEvaluate: UsersScreeningStep;
    public chosenStep: ScreeningStep;
    public messages: Message[] = [];

    constructor(
        private client: HttpClient,
        private eventsManager: GlobalEventsManager,
        private alertService: AlertService
    )
    {
        this.eventsManager.showNavBar(true);
        this.getLocation();
        this.getApplicants();
        this.getSteps();
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getApplicants(){
        this.client.get('/api/screening/list?location=' + this.location.id + '&signedback=true').subscribe(
            (users: ScreeningInfo[]) => this.users = users.sort((one, other) => {
                return one.personalTime > other.personalTime ? 1 : -1;
            }),
            (error) => error
        )
    }

    getSteps(){
        this.client.get('/api/screeningsteps?location=' + this.location.id).subscribe(
            (steps: ScreeningStep[]) => this.screeningSteps = steps,
            (error) => error
        )
    }

    getApplicantsStep(step){
        let url = '/api/evalscreening/' + this.user.id + '?step=' + step.id;
        this.chosenStep = step;
        this.client.get(url).subscribe(
            (data: UsersScreeningStep) => this.toEvaluate = data,
            (error) => error
        )
    }

    setStepStatus(status){
        this.toEvaluate.screeningStep.status = status;
    }

    setCriteriaStatus(criteria, status){
        criteria.status = status;
    }

    postUpdate(){
        this.client.post('/api/evalscreening', this.toEvaluate.screeningStep).subscribe(
            (data: any) => this.alertService.showAlert('success', 'Save completed', ''),
            (error) => error
        )
    }

    isActiveStepStatus(status){
        return this.toEvaluate.screeningStep.status == status ? "text-danger" : "";
    }

    isActiveCriteriaStatus(criteria, status){
        return criteria.status == status ? "text-danger" : "";
    }

    isActiveStep(step){
        if (this.toEvaluate) {
            return this.toEvaluate.screeningStep.stepId == step.id ? "text-danger" : "text-info";
        }
        return "text-info";
    }

    getNameForCriteria(step, criteria){
        let currentStep = this.screeningSteps.filter(entry => entry.id == step.stepId)[0];
        let currentCriteria = currentStep.criterias.filter(entry => entry.id == criteria.criteriaId)[0];
        return currentCriteria.name;
    }

}
