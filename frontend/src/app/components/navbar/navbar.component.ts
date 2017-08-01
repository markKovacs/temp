import {Component} from "@angular/core";
import {User} from "../../_models/user.model";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: 'navbar.component.html',
    styleUrls: ['navbar.component.css'],
    selector: 'navbar'
})
export class NavBarComponent {

    public showNavBar: boolean = false;

    constructor(private router: Router,
                private globalEventsManager: GlobalEventsManager) {
        this.globalEventsManager.showNavBarEmitter.subscribe((mode) => {
                if (mode !== null) {
                    this.showNavBar = mode;
                }
            }
        );
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

    logout() {
        localStorage.removeItem("authToken");
        this.globalEventsManager.showNavBar(false);
    }

}
