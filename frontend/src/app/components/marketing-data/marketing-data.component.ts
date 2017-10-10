import {Component} from "@angular/core";
import {ApplicantService} from "../../_services/applicants.service";
import {Applicant} from "../../_models/applicant.model";
import {isNullOrUndefined} from "util";
import {ScreeningService} from "../../_services/screening.service";
import {UsersScreeningStep} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: './marketing-data.component.html',
    styleUrls: ['./marketing-data.component.css']
})
export class MarketingDataPageComponent {

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

        const postData = [];

        for (let appl of this.data) {
            if(!isNullOrUndefined(appl.finalResult)) {
                postData.push({
                    id: appl.id,
                    result: appl.finalResult
                });
            }
        }

        this.applicantService.setFinished(postData).subscribe(
            (data: any) => {},
            error2 => console.log(error2)
        )

    }


}