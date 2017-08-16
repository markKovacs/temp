import {Component, Input, OnInit} from '@angular/core';
import {ApplicantService} from '../../_services/applicants.service';
import {Applicant} from '../../_models/applicant.model';

@Component({
    moduleId: module.id,
    selector: 'app-active-applicants-table',
    templateUrl: './active-applicants-table.component.html',
    styleUrls: ['./active-applicants-table.component.css']
})
export class ActiveApplicantsTableComponent implements OnInit {

    @Input() locationId: string;
    private applicants: Applicant[];

    constructor(private applicantService: ApplicantService) {
        this.applicants = [];
    }

    ngOnInit(): void {
        this.applicantService.getApplicants(this.locationId)
            .subscribe(
                (data: Applicant[]) => {
                    this.applicants = data;
                    console.log('megjottek', data);
                },
                error => console.log(error)
            );
    }



}
