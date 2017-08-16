import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep, Criteria, User, UsersScreeningStep} from "../../_models/index";
import {DateFormatPipe} from "angular2-moment";
import {Message} from 'primeng/primeng';
import {ScreeningInfo} from "../../_models/screeninginfo.model";
import {AlertService} from "../../_services/alert.service";

@Component({
    moduleId: module.id,
    templateUrl: 'evaluateScreenings.component.html',
    styleUrls: ['evaluateScreenings.component.css']
})
export class EvaluateScreeningsComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];
    public users: ScreeningInfo[];
    public user: ScreeningInfo;

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager
    )
    {
        this.eventsManager.showNavBar(true);
        this.getLocation();
        this.getApplicants();
        this.getSteps();
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getApplicants(){
        this.client.get('/api/screening/list?location=' + this.location.id + '&signedback=true').subscribe(
            (users: ScreeningInfo[]) => this.users = users.sort((one, other) => {
                return one.personalTime > other.personalTime ? 1 : -1;
            }),
            (error) => error
        )
    }

    getSteps(){
        this.client.get('/api/screeningsteps?location=' + this.location.id).subscribe(
            (steps: ScreeningStep[]) => this.screeningSteps = steps,
            (error) => error
        )
    }

    chooseUser(user: ScreeningInfo){
        this.router.navigate(['/evaluateuser/' + user.adminId])
    }

    profile(user: ScreeningInfo){
        this.router.navigate(['/applicants/' + user.adminId])
    }

}
