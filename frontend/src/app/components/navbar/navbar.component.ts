import {Component, EventEmitter, Output} from "@angular/core";
import {User} from "../../_models/user.model";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {Router} from "@angular/router";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location} from "../../_models/location.model";

@Component({
    moduleId: module.id,
    templateUrl: 'navbar.component.html',
    styleUrls: ['navbar.component.css'],
    selector: 'navbar'
})
export class NavBarComponent {

    public showNavBar: boolean = false;
    locations: Location[] = [];

    constructor(private router: Router,
                private globalEventsManager: GlobalEventsManager,
                private client: HttpClient) {
        this.globalEventsManager.showNavBarEmitter.subscribe((mode) => {
                if (mode !== null) {
                    this.showNavBar = mode;
                }
            }
        );
        this.getLocations();
    }

    getLocations() {
        this.client.get('/api/locations').subscribe(
            (locations: Location[]) => this.locations = locations,
            (error) => {
                console.log(error);
            }
            //,() => {
            //    console.log("GET - api/locations");
            //}
        )
    }


    chooseLocation(id) {
        let chosen: Location = this.locations.filter((location) => location.id == id)[0];
        localStorage.setItem("chosenLocation", JSON.stringify(chosen));
        this.globalEventsManager.setSelectedLocation(chosen.id);
    }

    getDashboard() {
        this.router.navigate(['dashboard'])
    }

    getApplicants() {
        this.router.navigate(['applicants'])
    }

    getSurvey() {
        this.router.navigate(['surveygenerator'])
    }

    getCalendar() {
        this.router.navigate(['calendar'])
    }

    getScreening(){
        this.router.navigate(['evaluatescreenings'])
    }

    getEditor(){
        this.router.navigate(['edittemplate'])
    }

    hasLocation(){
        return localStorage.getItem('chosenLocation') != undefined;
    }

    getLocation(){
        return JSON.parse(localStorage.getItem('chosenLocation')).name;
    }

    logout() {
        localStorage.removeItem("authToken");
        this.globalEventsManager.showNavBar(false);
    }

}
