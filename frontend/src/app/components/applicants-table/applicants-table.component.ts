import {Component, Input, OnInit} from '@angular/core';
import {ApplicantService} from '../../_services/applicants.service';
import {Applicant} from '../../_models/applicant.model';
import {User} from '../../_models/user.model';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {Router} from '@angular/router';

@Component({
    moduleId: module.id,
    selector: 'app-applicants-table',
    templateUrl: './applicants-table.component.html',
    styleUrls: ['./applicants-table.component.css']
})
export class ApplicantsTableComponent implements OnInit {

    @Input() fetchAll: boolean; // grab all or active applicants
    private locationId: string;
    private applicants: Applicant[] = [];

    constructor(
        private applicantService: ApplicantService,
        private eventsManager: GlobalEventsManager,
        private router: Router
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
        this.applicantService.getApplicants(this.locationId, this.fetchAll)
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

    handleRowClick(e): void {
        console.log(e.data.adminId);
        this.router.navigate(['/applicants', e.data.adminId])
    }

    getSelectedLocationId() {
        return JSON.parse(localStorage.getItem('chosenLocation')).id;
    }

}
