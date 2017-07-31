import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location} from "../../_models/index";
import {DomSanitizer} from '@angular/platform-browser';
import {User} from "../../_models/user.model";
import {Message} from 'primeng/primeng';

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: User;
    public usersLocation: Location;
    public messages: Message[] = [];

    constructor(private sanitizer: DomSanitizer,
                private route: ActivatedRoute,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.route.params.subscribe(
            (params) => {
                this.getUser(params.id).subscribe(
                    (user: User) => {
                        this.user = user;
                    },
                    (error) => console.log(error),
                    () => console.log("User set")
                )
            }
        )
    }

    getUser(id) {
        return this.client.get('/api/applicants/' + id)
    }

    setFinalResult(bool){
        let data = {adminId: this.user.adminId, accepted: bool};
        this.client.post("/api/setfinalresult", data).subscribe(
            (response: any) => this.messages.push(
                {
                    severity: 'success',
                    summary: 'Final result set',
                    detail: this.user.givenName + " " + this.user.familyName
                }
            ),
            (error) => this.messages.push(
                {
                    severity: 'error',
                    summary: 'Error',
                    detail: error
                }
            )
        )
    }

}