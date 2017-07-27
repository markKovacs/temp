import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {UserInList} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'applicantList.component.html',
    styleUrls: ['applicantList.component.css']
})
export class ApplicantListComponent {

    public users: UserInList[];

    constructor(private route: ActivatedRoute,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        let id = JSON.parse(localStorage.getItem("chosenLocation")).id;
        this.client.get("/api/applicants?location="+id).subscribe(
            (users: UserInList[]) => this.users = users,
            (error) => console.log(error),
            () => console.log("users fetched for applicant list", this.users)
        );
    }

    getDeadline(applicant: UserInList){
        let deadline = new Date();
        deadline.setDate(new Date(applicant.processStartedAt).getDate() + 7);
        return deadline;
    }

    checkApplicant(e) {
        this.router.navigate(['applicants/' + e.data.adminId]);
    }

}
