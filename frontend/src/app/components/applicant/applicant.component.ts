import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalEventsManager } from "../../global.eventsmanager";
import { HttpClient } from "../../_httpclient/httpclient";
import { Location, User } from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: User;
    public usersLocation: Location;

    constructor(
        private route: ActivatedRoute,
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager) {
            this.eventsManager.showNavBar(true);
            this.route.params.subscribe(
                (id) => this.getUser(id).subscribe(
                    (user: User) => this.user = user,
                    (error) => console.log(error),
                    () => console.log("User set")
                )
            )
    }

    getUser(id) {
        return this.client.get('api/applicant/' + id)
    }

}
