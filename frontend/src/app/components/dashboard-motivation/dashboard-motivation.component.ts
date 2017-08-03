
import {Component, Input} from "@angular/core";
import {UserMotivation} from "../../_models/user-motivation.model";
import {Router} from "@angular/router";
import {HttpClient} from "../../_httpclient/httpclient";
import {GlobalEventsManager} from "../../global.eventsmanager";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard-motivation.component.html',
    styleUrls: ['dashboard-motivation.component.css'],
    selector: 'dashboard-motivation'
})
export class DashboardMotivationComponent {

    usersWithVideo: UserMotivation[] = [];

    constructor(private router: Router,
                private client: HttpClient,
                private eventsManager: GlobalEventsManager) {

        this.getData();

        this.eventsManager.selectedLocationEmitter.subscribe((loc) => {
                if (loc !== null) {
                    this.usersWithVideo = [];
                    this.getData();
                }
            }
        );
    }

    getApplicant(id){
        this.router.navigate(['applicants/' + id])
    }

    getData() {
        let id = JSON.parse(localStorage.getItem("chosenLocation")).id;
        this.client.get('/api/dashboard/motivation?location=' + id).subscribe(
            (users: UserMotivation[]) => {
                this.usersWithVideo = users;
            },
            (error) => {
                console.log(error);
            }
            //,() => {
            //    console.log("GET - api/dashboard/motivation")
            //}
        )
    }
}