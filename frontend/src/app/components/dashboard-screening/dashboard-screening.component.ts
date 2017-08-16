import {Component} from "@angular/core";
import {ScreeningService} from "../../_services/screening.service";
import {ScreeningInfo} from "../../_models/screeninginfo.model";
import {Router} from "@angular/router";
import {GlobalEventsManager} from "../../global.eventsmanager";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard-screening.component.html',
    styleUrls: ['dashboard-screening.component.css'],
    selector: 'dashboard-screening'
})
export class DashboardScreeningComponent {

    users: ScreeningInfo[] = [];

    constructor(private screeningService: ScreeningService,
                private router: Router,
                private eventsManager: GlobalEventsManager) {

        this.getData();

        this.eventsManager.selectedLocationEmitter.subscribe((loc) => {
                if (loc !== null) {
                    this.users = [];
                    this.getData();
                }
            }
        );

    }

    navigate(user: ScreeningInfo) {
        if(user.scheduleSignedBack){
            this.router.navigate(['/evaluateuser/' + user.adminId]);
        } else {
            this.router.navigate(['/applicants/' + user.adminId]);
        }

    }

    getData(){
        this.screeningService.findCandidates().subscribe(
            (data: ScreeningInfo[]) => {
                this.users = data;
            },
            error => console.log(error)
        );
    }

}