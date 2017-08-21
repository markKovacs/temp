import {Component, Input, OnInit} from '@angular/core';
import {ApplicantService} from '../../_services/applicants.service';
import {Applicant} from '../../_models/applicant.model';
import {User} from '../../_models/user.model';
import {GlobalEventsManager} from '../../global.eventsmanager';

@Component({
    moduleId: module.id,
    selector: 'app-active-applicants-table',
    templateUrl: './active-applicants-table.component.html',
    styleUrls: ['./active-applicants-table.component.css']
})
export class ActiveApplicantsTableComponent implements OnInit {

    private locationId: string;
    private applicants: Applicant[] = [];
    cities = ['miskolc', 'budapest'];

    constructor(
        private applicantService: ApplicantService,
        private eventsManager: GlobalEventsManager
    ) {
        this.eventsManager.selectedLocationEmitter.subscribe((loc) => {
                if (loc !== null) {
                    this.locationId = this.getSelectedLocationId();
                    this.getData();
                }
            }
        );
    }

    ngOnInit(): void {
        this.locationId = this.getSelectedLocationId();
        this.getData();
    }

    getData(): void {
        this.applicantService.getApplicants(this.locationId)
            .subscribe(
                (data: Applicant[]) => {
                    this.applicants = data;
                },
                error => console.log(error)
            );
    }

    handleRowExpand(e): void {
        this.applicantService.getApplicantDetailsById(e.data.adminId)
            .subscribe(
                (data: User) => {
                    for (const applicant of this.applicants) {
                        if (data.adminId === applicant.adminId) {
                            applicant.user = data;
                        }
                    }
                },
                error => console.log(error)
            );
    }

    getSelectedLocationId() {
        return JSON.parse(localStorage.getItem('chosenLocation')).id;
    }

}
