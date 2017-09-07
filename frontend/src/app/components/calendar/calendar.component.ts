import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";
import {ScreeningInfo} from "../../_models/screeninginfo.model";
import {ScreeningService} from "../../_services/screening.service";
import { DatePipe } from '@angular/common';
import {Message, SelectItem} from 'primeng/primeng';
import {DateFormatPipe} from "angular2-moment";
import {PostResponse} from "../../_models/post-response.model";
import {isNullOrUndefined} from "util";


@Component({
    moduleId: module.id,
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent implements OnInit {

    hu: any;

    users: ScreeningInfo[] = [];

    selectDates: SelectItem[] = [];

    public dates: Date[] = [];
    public date: Date;
    public messages: Message[] = [];
    minDate = new Date();

    constructor(private router: Router,
                private eventsManager: GlobalEventsManager,
                private screeningService: ScreeningService,
                private dateFormatter: DatePipe
    ) {
        this.eventsManager.showNavBar(true);
        this.fetchUsers();
    }

    ngOnInit() {
        this.hu = {
            firstDayOfWeek: 1,
            dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
            dayNamesMin: ["Su","Mo","Tu","We","Th","Fr","Sa"],
            monthNames: [ "January","February","March","April","May","June","July","August","September","October","November","December" ],
            monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ]
        };
        this.users.filter(u => !isNullOrUndefined(u.groupTime));
    }

    private fetchUsers() {

        this.screeningService.findCandidates().subscribe(
            (data: ScreeningInfo[]) => {
                this.users = data;
                this.loadDates();
            },
            error => console.log(error)
        );

    }

    private loadDates() {

        this.users.forEach(user => {
            if (user.groupTime != null && !this.dates.includes(user.groupTime)) {
                this.dates.push(user.groupTime);
                this.selectDates.push(
                    {
                        label: this.dateFormatter.transform(user.groupTime, 'yyyy.MM.dd. HH:mm'),
                        value: user.groupTime
                    }
                );
            }
        });

    }

    addDate() {
        if (this.date && !this.dates.includes(this.date)) {
            this.dates.push(this.date);
            this.selectDates.push(
                {
                    label: this.dateFormatter.transform(this.date, 'yyyy.MM.dd. HH:mm'),
                    value: this.date
                }
            );
        }
        this.date = null;
        this.dates.sort();
    }

    saveGroupTimes(){
        this.screeningService.saveGroupTimes(this.users).subscribe(
            (data: any) => {},
            error2 => console.log(error2)
        );
    }

    savePersonalTimes(){
        this.screeningService.savePersonalTimes(this.users).subscribe(
            (data: any) => {},
            error2 => console.log(error2)
        );
    }
}
