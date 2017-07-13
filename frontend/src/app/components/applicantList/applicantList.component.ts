import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalEventsManager } from "../../global.eventsmanager";
import { HttpClient } from "../../_httpclient/httpclient";
import { UserInList } from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'applicantList.component.html',
    styleUrls: ['applicantList.component.css']
})
export class ApplicantListComponent {

    public users: UserInList[];

    constructor(
      private route: ActivatedRoute,
      private client: HttpClient,
      private router: Router,
      private eventsManager: GlobalEventsManager)
    {
      this.eventsManager.showNavBar(true);
      this.client.get("/api/applicants?location=BUD").subscribe(
        (users: UserInList[]) => this.users = users,
        (error) => console.log(error),
        () => console.log("users fetched for applicant list", this.users)
      );
    }

    checkApplicant(e){
      this.router.navigate(['applicants/' + e.data.adminId]);
    }

}
