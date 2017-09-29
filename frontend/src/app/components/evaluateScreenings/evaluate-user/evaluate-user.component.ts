import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../../global.eventsmanager";
import {ScreeningInfo, ScreeningStep} from "../../../_models/index";
import {ScreeningService} from "../../../_services/screening.service";

@Component({
    moduleId: module.id,
    templateUrl: 'evaluate-user.component.html',
    styleUrls: ['evaluate-user.component.css']
})
export class EvaluateUserComponent {

    user: ScreeningInfo;
    screeningSteps: ScreeningStep[] = [];

    constructor(
        private screeningService: ScreeningService,
        private route: ActivatedRoute,
        private eventsManager: GlobalEventsManager
    ) {

        this.eventsManager.showNavBar(true);

        this.route.params.subscribe(
            (params) => {
                this.screeningService.getUser(params.id).subscribe(
                    (user: ScreeningInfo) => {
                        this.user = user;
                    },
                    (error) => console.log(error)
                )
            }
        );

        this.screeningService.findScreeningSteps().subscribe(
            (steps: ScreeningStep[]) => this.screeningSteps = steps,
            (error) => error
        );

    }

}
