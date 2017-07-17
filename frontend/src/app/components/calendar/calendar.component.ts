import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent {

    public usersWithoutScreening: any[] = [];
    public usersWithScreening: any[] = [];
    public dates: Date[] = [];
    public date: Date;
    public chosenDate: Date;
    public dateSelectorOn: boolean = false;

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        // TODO - api endpoint
        this.usersWithoutScreening = [
            {id: 1, name: "Kovács Béla", screeningTime: null, screeningDate: null},
            {id: 2, name: "Varga Lajos", screeningTime: null, screeningDate: null},
            {id: 3, name: "Nagy Kázmér", screeningTime: null, screeningDate: null},
            {id: 4, name: "Kis Ottokár", screeningTime: null, screeningDate: null},
            {id: 5, name: "Kiss Huba", screeningTime: null, screeningDate: null},
            {id: 6, name: "Magyar Oszkár", screeningTime: null, screeningDate: null},
            {id: 7, name: "Horváth Miklós", screeningTime: null, screeningDate: null}
        ]
    }

    doStuff(event){

    }

    openDateSelector(){
        this.dateSelectorOn = true;
        this.date = null;
    }

    addDate(){
        if (this.date && !this.dates.includes(this.date)){
            this.dates.push(this.date);
            this.dateSelectorOn = false;
        }
    }

    selectDate(date){
        this.chosenDate = date;
    }
}
