import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Applicant} from '../../_models/applicant.model';

@Component({
    moduleId: module.id,
    templateUrl: 'applicants.component.html',
    styleUrls: ['applicants.component.css']
})
export class ApplicantsComponent {

    public users: Applicant[];

    constructor(private route: ActivatedRoute,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        let id = JSON.parse(localStorage.getItem("chosenLocation")).id;
        this.client.get("/api/applicants?location="+id).subscribe(
            (users: Applicant[]) => this.users = users,
            (error) => console.log(error),
            () => console.log("users fetched for applicant list", this.users)
        );
    }

    getDeadline(applicant: Applicant){
        let deadline = new Date();
        deadline.setDate(new Date(applicant.processStartedAt).getDate() + 7);
        return deadline;
    }

    getSelectedLocation(): string {
        return JSON.parse(localStorage.getItem('chosenLocation')).name;
    }

}
