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

    public showNavBar: boolean = true;
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
        this.router.navigate(['dashboard']);
    }

    getDashboard() {
        this.router.navigate(['dashboard'])
    }

    getApplicants() {
        this.router.navigate(['applicants'])
    }

    getFinalResults() {
        this.router.navigate(['final-result'])
    }

    getPersonalData() {
        this.router.navigate(['personal-data'])
    }

    getMarketing() {
        this.router.navigate(['marketing-data'])
    }

    getSurvey() {
        this.router.navigate(['surveygenerator'])
    }

    getCalendar() {
        this.router.navigate(['calendar'])
    }

    getScreening(){
        this.router.navigate(['evaluate'])
    }

    getEditor(){
        this.router.navigate(['edittemplate'])
    }

    hasLocation(){
        return localStorage.getItem('chosenLocation') != undefined;
    }

    getLocation(){
        if(localStorage.getItem('chosenLocation')) {
            return JSON.parse(localStorage.getItem('chosenLocation')).name;
        }
        return null;
    }

    logout() {
        localStorage.removeItem("adminAuthToken");
        this.globalEventsManager.showNavBar(false);
    }

}
