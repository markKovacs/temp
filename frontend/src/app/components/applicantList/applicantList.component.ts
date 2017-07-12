import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalEventsManager } from "../../global.eventsmanager";
import { HttpClient } from "../../_httpclient/httpclient";
import { Location, User } from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'applicantList.component.html',
    styleUrls: ['applicantList.component.css']
})
export class ApplicantListComponent {

    public users: any;
    public usersLocation: Location;

    constructor(
      private route: ActivatedRoute,
      private client: HttpClient,
      private router: Router,
      private eventsManager: GlobalEventsManager)
    {
      this.eventsManager.showNavBar(true);
    }

    getUser(id) {
      return this.client.get('api/applicants/' + id)
    }

}
