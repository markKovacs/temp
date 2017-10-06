import {Component} from "@angular/core";
import {ApplicantService} from "../../_services/applicants.service";
import {Applicant} from "../../_models/applicant.model";

@Component({
    moduleId: module.id,
    templateUrl: 'final-result.component.html',
    styleUrls: ['final-result.component.css']
})
export class FinalResultPageComponent {

    data: Applicant[];

    constructor(
        private applicantService: ApplicantService
    ){
        this.applicantService.getFinished().subscribe(
            (data: Applicant[]) => this.data = data,
            error2 => console.log(error2)
        )
    }



}