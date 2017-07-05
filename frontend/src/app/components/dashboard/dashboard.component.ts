import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalEventsManager } from "../../global.eventsmanager";
import { HttpClient } from "../../_httpclient/httpclient";
import { Location, User } from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

    public locations: Location[] = [];
    public usersWithVideo: User[] = [];
    public usersWithScreening: User[] = [];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager) {
            this.eventsManager.showNavBar(true);
            this.getLocations();
            if (localStorage.chosenLocation) {
              this.getUsersWithVideo();
              this.getUsersWithScreening();
            }
    }

    getLocations() {
        this.client.get('api/locations').subscribe(
            (locations: Location[]) => this.locations = locations,
            (error) => { console.log(error); },
            () => { console.log("GET - api/locations"); }
        )
    }

    getUsersWithVideo() {
        let id = JSON.parse(localStorage.chosenLocation).id
        this.client.get('api/dashboard/motivation?location=' + id).subscribe(
          (users: User[]) => this.usersWithVideo = users,
          (error) => { console.log(error); },
          () => { console.log("GET - api/dashboard/motivation") }
        )
    }

    getUsersWithScreening() {
        let id = JSON.parse(localStorage.chosenLocation).id
        this.client.get('api/dashboard/screening?location=' + id).subscribe(
          (users: User[]) => this.usersWithScreening = users,
          (error) => { console.log(error); },
          () => { console.log("GET - api/dashboard/screening") }
        )
    }

    chooseLocation(id) {
        let chosen = this.locations.filter((location) => location.id == id)[0];
        localStorage.chosenLocation = JSON.stringify(chosen);
        this.getUsersWithVideo();
        this.getUsersWithScreening();
    }

    locationChosen() {
      return localStorage.chosenLocation != undefined;
    }

    isChosen(id) {
      if (!localStorage.chosenLocation) {
        return "unselected-location";
      }
      if (JSON.parse(localStorage.chosenLocation).id != id) {
        return "unselected-location";
      }
      return "selected-location";
    }

    getApplicant(id) {
      let destination = '/applicant/' + id;
      this.router.navigate([destination]);
    }

}
