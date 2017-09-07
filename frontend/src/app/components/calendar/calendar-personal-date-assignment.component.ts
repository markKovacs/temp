import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";
import {ScreeningInfo} from "../../_models/screeninginfo.model";
import {ScreeningService} from "../../_services/screening.service";
import { DatePipe } from '@angular/common';
import {Message} from 'primeng/primeng';
import {DateFormatPipe} from "angular2-moment";
import {PostResponse} from "../../_models/post-response.model";

@Component({
    moduleId: module.id,
    selector: 'calendar-personal-date-assignment',
    templateUrl: 'calendar-personal-date-assignment.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarPersonalDateAssignmentComponent implements OnInit {

    hu: any;

    @Input() user: ScreeningInfo;

    constructor() { }

    ngOnInit() {
        this.hu = {
            firstDayOfWeek: 1,
            dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
            dayNamesMin: ["Su","Mo","Tu","We","Th","Fr","Sa"],
            monthNames: [ "January","February","March","April","May","June","July","August","September","October","November","December" ],
            monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ]
        };

        if(this.user.personalTime){
            this.user.personalTime = new Date(this.user.personalTime);
        }


    }

}
