import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalEventsManager } from "../../global.eventsmanager";
import { ScreeningInfo } from "../../_models/screeninginfo.model";
import { ScreeningService } from "../../_services/screening.service";

@Component({
    moduleId: module.id,
    templateUrl: 'evaluateScreenings.component.html',
    styleUrls: ['evaluateScreenings.component.css']
})
export class EvaluateScreeningsComponent {

    public users: ScreeningInfo[];

    constructor(
        private router: Router,
        private eventsManager: GlobalEventsManager,
        private screeningService: ScreeningService
    )
    {
        this.eventsManager.showNavBar(true);

        this.screeningService.findEvaluationCandidates().subscribe(
            (users: ScreeningInfo[]) => this.users = users,
            (error) => console.log(error)
        );

    }

    chooseUser(user: ScreeningInfo){
        this.router.navigate(['/evaluateuser/' + user.id])
    }

    profile(user: ScreeningInfo){
        this.router.navigate(['/applicants/' + user.id])
    }

}
