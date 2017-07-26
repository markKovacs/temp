import {Component, OnChanges} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";
import {UserMotivation} from "../../_models/user-motivation.model";
import {UserScreening} from "../../_models/user-screening.model";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent implements OnChanges{


    public locations: Location[] = [];
    public usersWithVideo: UserMotivation[] = [];
    public usersWithScreening: UserScreening[] = [];

    constructor(private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.getLocations();

    }

    ngOnChanges(): void {
        if (localStorage.getItem("chosenLocation")) {
            this.getUsersWithVideo();
            this.getUsersWithScreening();
        }
    }
    getLocations() {
        this.client.get('/api/locations').subscribe(
            (locations: Location[]) => this.locations = locations,
            (error) => {
                console.log(error);
            },
            () => {
                console.log("GET - api/locations");
            }
        )
    }

    getUsersWithVideo() {
        let id = JSON.parse(localStorage.getItem("chosenLocation")).id;

        this.client.get('/api/dashboard/motivation?location=' + id).subscribe(
            (users: UserMotivation[]) => {
                this.usersWithVideo = users;
            },
            (error) => {
                console.log(error);
            },
            () => {
                console.log("GET - api/dashboard/motivation")
            }
        )
    }

    getUsersWithScreening() {
        let id = JSON.parse(localStorage.getItem("chosenLocation")).id;
        this.client.get('/api/dashboard/screening?location=' + id).subscribe(
            (users: UserScreening[]) => {
                this.usersWithScreening = users;
            },
            (error) => {
                console.log(error);
            },
            () => {
                console.log("GET - api/dashboard/screening")
            }
        )
    }

    chooseLocation(id) {
        let chosen = this.locations.filter((location) => location.id == id)[0];
        localStorage.setItem("chosenLocation", JSON.stringify(chosen));
        this.getUsersWithVideo();
        this.getUsersWithScreening();
    }

    locationChosen() {
        return localStorage.getItem("chosenLocation") != undefined;
    }

    isChosen(id) {
        if (!localStorage.getItem("chosenLocation")) {
            return "unselected-location";
        }
        if (JSON.parse(localStorage.getItem("chosenLocation")).id != id) {
            return "unselected-location";
        }
        return "selected-location";
    }

    getApplicant(id) {
        this.router.navigate(['applicants/' + id])
    }

}
