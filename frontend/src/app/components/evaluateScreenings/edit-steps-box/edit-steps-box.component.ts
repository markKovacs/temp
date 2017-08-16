import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from "../../../_httpclient/httpclient";
import {GlobalEventsManager} from "../../../global.eventsmanager";

@Component({
    moduleId: module.id,
    selector: 'edit-steps-box',
    templateUrl: 'edit-steps-box.component.html',
    styleUrls: ['edit-steps-box.component.css']
})
export class EditStepsBoxComponent {

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
    }

    editScreening(){
        this.router.navigate(['editscreening']);
    }

    getLocation(){
        return JSON.parse(localStorage.getItem("chosenLocation")).name;
    }


}
