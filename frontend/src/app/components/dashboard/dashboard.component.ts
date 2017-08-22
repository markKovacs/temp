import {Component} from '@angular/core';
import {GlobalEventsManager} from '../../global.eventsmanager';

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.css']
})
export class DashboardComponent {

    constructor(private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
    }

    hasLocation(): boolean {
        return localStorage.getItem('chosenLocation') !== null;
    }

    getSelectedLocation(): string {
        return JSON.parse(localStorage.getItem('chosenLocation')).name;
    }

}
