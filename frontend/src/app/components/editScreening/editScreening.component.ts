import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'editScreening.component.html',
    styleUrls: ['editScreening.component.css']
})
export class EditScreeningComponent {

    public location: Location;
    public screening: Screening;

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.getLocation();
        this.getScreening();
    }

    getLocation(){
        this.location = JSON.parse(localStorage.location);
    }

    getScreening(){
        this.client.get("/api/editscreening?location=" + this.location.id).subscribe(
            (screening: Screening) => this.screening = screening;
            (error) => console.log(error);
            () => console.log("Screening fetched");
        )
    }

}
