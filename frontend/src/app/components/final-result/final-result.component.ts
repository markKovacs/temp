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
            (data: Applicant[]) => {
                    this.data = data;
                    for(let d of this.data){
                        d.finalResult = true;

                        const saved: any[] = this.getSaved();
                        for(let s of saved){
                            if(s.id === d.id){
                                d.finalResult = s.checked;
                            }
                        }

                    }
                },
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

    handleChange(event: any, applicant: Applicant){
        console.log(applicant.id + ": " + event.checked);

        const data: any[] = this.getSaved();
        data.push({id: applicant.id, checked: event.checked});

        localStorage.setItem('savedFinalResults', JSON.stringify(data));

    }

    private getSaved(): any[]{
        let saved = localStorage.getItem('savedFinalResults');

        if(saved === undefined || saved === null){
            return [];
        }

        return JSON.parse(saved);
    }

    private postResults() {

        localStorage.removeItem('savedFinalResults');

        for (let appl of this.data) {

            if(appl.send === true) {
                let data = {
                    id: appl.id,
                    accepted: appl.finalResult ? appl.finalResult : false
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

