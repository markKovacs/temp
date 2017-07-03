import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

    constructor(private router: Router,
                private eventsManager: GlobalEventsManager) {

        this.eventsManager.showNavBar(true);

    }

}
