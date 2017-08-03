import {Component, OnInit} from '@angular/core';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location} from "../../_models/index";
import {UserMotivation} from "../../_models/user-motivation.model";
import {UserScreening} from "../../_models/user-screening.model";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

    constructor(private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
    }

}
