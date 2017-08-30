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
                        this.user.screeningGroupTime = new Date(this.user.screeningGroupTime);
                        this.user.screeningPersonalTime = new Date(this.user.screeningPersonalTime);
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
        return this.user.testResults.filter(mot => mot.isMotivation && mot.passed).length === 1;
    }

    save(){
        this.client.post('/api/applicants/' + this.user.adminId + '/savedate',
            {
                    group: this.user.screeningGroupTime.getTime(),
                    personal: this.user.screeningPersonalTime.getTime()
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