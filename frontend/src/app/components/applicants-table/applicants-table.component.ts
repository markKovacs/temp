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
    applicants: Applicant[] = [];

    loading: boolean = false;

    constructor(
        private applicantService: ApplicantService,
        private eventsManager: GlobalEventsManager,
        private router: Router
    ) {
        this.loading = true;
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
                error => console.log(error),
                () => this.loading = false
            );
    }

    handleRowExpand(e): void {
        this.applicantService.getApplicantDetailsById(e.data.id)
            .subscribe(
                (data: User) => {
                    for (const applicant of this.applicants) {
                        if (data.id === applicant.id) {
                            applicant.user = data;
                        }
                    }
                },
                error => console.log(error)
            );
    }

    handleRowClick(e): void {
        console.log(e.data.id);
        this.router.navigate(['/applicants', e.data.id])
    }

    getSelectedLocationId() {
        return JSON.parse(localStorage.getItem('chosenLocation')).id;
    }

}
