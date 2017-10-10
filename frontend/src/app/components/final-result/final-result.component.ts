import {Component} from "@angular/core";
import {ApplicantService} from "../../_services/applicants.service";
import {Applicant} from "../../_models/applicant.model";
import {isNullOrUndefined} from "util";
import {ScreeningService} from "../../_services/screening.service";
import {UsersScreeningStep} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'final-result.component.html',
    styleUrls: ['final-result.component.css']
})
export class FinalResultPageComponent {

    data: Applicant[];

    constructor(
        private applicantService: ApplicantService,
        private screeningService: ScreeningService
    ) {
        this.applicantService.getFinished()
            .subscribe(
            (data: Applicant[]) => this.data = data,
            error2 => console.log(error2)
        )
    }

    saveResult(){
        const confirmDialog = confirm('Are you sure?');
        if(confirmDialog){
            this.postResults();
        }
    }

    fetchDetails(applicant: Applicant){
        this.screeningService.getStepsForUser(applicant.id).subscribe(
            (data: UsersScreeningStep) => applicant.details = data,
            error2 => console.log(error2)
        )
    }

    private postResults() {

        for (let appl of this.data) {
            if(!isNullOrUndefined(appl.finalResult)) {
                let data = {
                    id: appl.id,
                    accepted: appl.finalResult
                };

                this.applicantService.setFinished(data).subscribe(
                    (data: any) => {
                        appl.finalResultSent = true;
                    },
                    error2 => console.log(error2)
                )
            }
        }

    }

}

