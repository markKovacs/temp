import {Component, Input} from "@angular/core";
import {ScreeningService} from "../../_services/screening.service";
import {Router} from "@angular/router";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {UserScreeningDisplayModel} from "../../_models/user-screening-display.model";
import {User} from "../../_models/index";
import {HttpClient} from "../../_httpclient/httpclient";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-screening.component.html',
    selector: 'applicant-screening'
})
export class ApplicantScreeningComponent {

    @Input() user: User;
    messages: any [] = [];

    constructor(private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {

    }

    getClass(step: any){
        if(step.status === 'maybe'){
            return "panel-warning";
        }
        if(step.status === 'no'){
            return "panel-danger";
        }
        if(step.status === 'yes'){
            return "panel-success";
        }
    }

    getSizeClass(){
        if(this.user.screeningSteps.length === 3){
            return 3;
        }
        return Math.floor(9/this.user.screeningSteps.length);
    }


    setFinalResult(bool){

        if(this.user.finalResult !== undefined){
            return;
        }

        this.user.finalResult = bool;

        let data = {
            adminId: this.user.adminId,
            accepted: bool
        };

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