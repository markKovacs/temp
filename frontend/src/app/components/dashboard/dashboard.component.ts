import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

    public locations: Location[];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager) {
            this.eventsManager.showNavBar(true);
    }

    // getLocations(){
    //     this.client.get()
    // }

}
