import {Component, OnInit} from "@angular/core";
import {ApplicantService} from "../../_services/applicants.service";
import {Applicant} from "../../_models/applicant.model";
import {isNullOrUndefined} from "util";
import {ScreeningService} from "../../_services/screening.service";
import {UsersScreeningStep} from "../../_models/index";
import {PersonalData} from "../../_models/personal-data";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: './personal-data.component.html',
    styleUrls: ['./personal-data.component.css']
})
export class PersonalDataPageComponent {

    data: PersonalData[];

    hired: Applicant[];

    courseId: string;

    constructor(
        private applicantService: ApplicantService,
        private router: Router
    ) {
        this.applicantService.getPersonalData()
            .subscribe(
            (data: PersonalData[]) => {
                this.data = data;
                for(let d of this.data){
                    d.birthDate = new Date(d.birthDate);
                }
                this.applicantService.getHired()
                    .subscribe(
                        (data: Applicant[]) => {this.hired = data;

                            for(let pdata of this.data){
                                for(let appl of this.hired){
                                    if(pdata.id === appl.id){
                                        appl.hasPersonalData = true;
                                    }
                                }
                            }

                        },
                        error2 => console.log(error2)
                    )
            },
                    error2 => console.log(error2)
        );



    }

    contractSigned(id: number){
        this.applicantService.contractSigned(id, this.courseId).subscribe(
            (data: any) => {
                this.data = this.data.filter(value => value.id !== id);
            }
        )
    }

    rejected(id: number){
        this.applicantService.rejected(id).subscribe(
            (data: any) => {
                console.log("rejected");
            }
        )
    }

    getUserIconClass(applicant: Applicant){
        if(applicant.hasPersonalData){
            return "text-success";
        }
        return "text-danger";
    }


    navigateToProfile(id: number){
        this.router.navigate(['/applicants/' + id]);
    }

}