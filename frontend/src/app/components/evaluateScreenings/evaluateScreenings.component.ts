import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep, Criteria} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'evaluateScreenings.component.html',
    styleUrls: ['evaluateScreenings.component.css']
})
export class EvaluateScreeningsComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
    }

    editScreening(){
        this.router.navigate(['editscreening']);
    }

}
