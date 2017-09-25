import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {DomSanitizer} from '@angular/platform-browser';
import {User} from "../../_models/user.model";
import {Application} from "../../_models/application";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: User;

    application: Application;

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
                        if(user.applications && user.applications.length > 0) {
                            this.application = user.applications[0];
                            this.application.screeningGroupTime = new Date(user.applications[0].screeningGroupTime);
                            this.application.screeningPersonalTime = new Date(user.applications[0].screeningPersonalTime);
                        }
                    },
                    (error) => console.log(error)
                )
            }
        )
    }

    getUser(id) {
        return this.client.get('/api/applicants/' + id)
    }

    hasSuccessMotivation(): boolean {
        return this.application.testResults.filter(mot => mot.isMotivation && mot.passed).length === 1;
    }

    save(){
        this.client.post('/api/applicants/' + this.user.id + '/savedate',
            {
                    group: this.application.screeningGroupTime.getTime(),
                    personal: this.application.screeningPersonalTime.getTime()
                  }
            )
            .subscribe(
                (success:any) => console.log("success"),
                    (error:any) => console.log(error)
            )
    }

    getDate(date: number){
        return new Date(date);
    }
}