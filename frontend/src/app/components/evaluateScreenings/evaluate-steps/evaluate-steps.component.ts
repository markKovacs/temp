import { Component, Input } from '@angular/core';
import { GlobalEventsManager } from "../../../global.eventsmanager";
import { ScreeningStep, UsersScreeningStep } from "../../../_models/index";
import { ScreeningInfo } from "../../../_models/screeninginfo.model";
import { AlertService } from "../../../_services/alert.service";
import { ScreeningService } from "../../../_services/screening.service";

@Component({
    moduleId: module.id,
    selector: 'evaluate-steps',
    templateUrl: 'evaluate-steps.component.html',
    styleUrls: ['evaluate-steps.component.css']
})
export class EvaluateStepsComponent {

    @Input() user: ScreeningInfo;
    @Input() screeningSteps: ScreeningStep[];

    userScreeningStep: UsersScreeningStep;

    public chosenStep: ScreeningStep;

    constructor(
        private eventsManager: GlobalEventsManager,
        private alertService: AlertService,
        private screeningService: ScreeningService
    )
    {
        this.eventsManager.showNavBar(true);
    }

    getApplicantsStep(step){

        this.chosenStep = step;

        this.screeningService.getStepForUser(this.user.id, step.id).subscribe(
            (data: UsersScreeningStep) => this.userScreeningStep = data,
            (error) => error
        );

    }

    save(){
        this.screeningService.saveEvaluationForStep(this.userScreeningStep.screeningStep).subscribe(
            (data: any) => this.alertService.showAlert('success', 'Save completed', ''),
            (error) => error
        );
    }

    isActiveStepStatus(status){
        return this.userScreeningStep.screeningStep.status == status ? "text-danger" : "";
    }

    isActiveCriteriaStatus(criteria, status){
        return criteria.status == status ? "text-danger" : "";
    }

    isActiveStep(step: ScreeningStep){
        if (this.userScreeningStep) {
            return this.userScreeningStep.screeningStep.step.id == step.id ? "text-danger" : "text-info";
        }
        return "text-info";
    }

}
