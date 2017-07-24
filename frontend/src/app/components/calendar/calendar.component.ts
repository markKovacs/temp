import {Component, OnInit} from '@angular/core';
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
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent implements OnInit {

    hu: any;

    public users: ScreeningInfo[] = [];

    public dates: Date[] = [];
    public loaded: boolean = false;
    public dateSelectorOn: boolean = false;
    public date: Date;
    public chosenDate: Date;
    public sourceList: ScreeningInfo[] = [];
    public targetList: ScreeningInfo[] = [];
    public isPostingGroupTimes: boolean = false;
    public isPostingIndividualTimes: boolean = false;
    public groupTimesSet: boolean = false;
    public individualTimesSet: boolean = false;
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

        for (let user of this.users) {

            if (user.groupTime != null && !this.dates.includes(user.groupTime)) {
                this.dates.push(user.groupTime)
            }

        }

        this.loaded = true;
    }

    openDateSelector() {
        this.dateSelectorOn = true;
        this.date = null;
    }

    addDate() {
        if (this.date && !this.dates.includes(this.date)) {
            this.dates.push(this.date);
            this.dateSelectorOn = false;
        }
    }

    selectDate(date) {
        this.chosenDate = date;
        this.groupTimesSet = null;
        this.sourceList = this.users.filter((user) => user.groupTime == null);
        this.targetList = this.users.filter((user) => user.groupTime == this.chosenDate);
    }

    showGroupTimes() {
        return this.chosenDate != null && !this.isPostingGroupTimes && !this.groupTimesSet;
    }

    showIndividualTimes() {
        return this.groupTimesSet && !this.isPostingIndividualTimes;
    }

    showLoading() {
        return this.isPostingGroupTimes || this.isPostingIndividualTimes;
    }

    setGroupTimes() {
        for (let user of this.targetList) {
            user.groupTime = this.chosenDate;
        }
    }

    saveGroupTimes() {
        this.setGroupTimes();
        this.postGroupTimes();
    }

    saveIndividualTimes() {
        this.postIndividualTimes();
    }

    getIndividual(user) {
        return user.personalTime ? user.personalTime : user.groupTime
    }

    postGroupTimes() {
        let sendData = [];
        for (let user of this.targetList) {
            sendData.push({id: user.adminId, time: user.groupTime})
        }
        console.log("should post this:", {groupTimes: sendData});

        this.isPostingGroupTimes = true;

        this.screeningService.saveGroupTimes(sendData).subscribe(
            (data: PostResponse) => {
                if(data.success){
                    this.isPostingGroupTimes = false;
                    this.groupTimesSet = true;
                    this.messages.push(
                        {
                            severity: 'info',
                            summary: 'Group times saved.',
                            detail: 'for: ' + this.dateFormatter.transform(this.chosenDate, "y.MM.dd. HH:mm")
                        }
                    );
                    if (this.targetList.length == 0) { this.chosenDate = null; };
                }
            },
            error2 => {
                this.messages.push(
                    {
                        severity: 'error',
                        summary: 'Error while saving data',
                        detail: error2
                    }
                )
            }
        );
    }

    postIndividualTimes() {
        let sendData = [];

        for (let user of this.targetList) {
            sendData.push({id: user.adminId, time: user.personalTime})
        }

        this.isPostingIndividualTimes = true;
        this.individualTimesSet = false;

        this.screeningService.savePersonalTimes(sendData).subscribe(
            (data: PostResponse) => {
                if(data.success){
                    this.messages.push(
                        {
                            severity: 'info',
                            summary: 'Individual times saved.',
                            detail: 'for: ' + this.dateFormatter.transform(this.chosenDate, "y.MM.dd. HH:mm")
                        }
                    );
                    this.isPostingIndividualTimes = false;
                    this.individualTimesSet = true;
                    this.chosenDate = null;
                    this.groupTimesSet = false;
                }
            },
            error2 => {
                this.messages.push(
                    {
                        severity: 'error',
                        summary: 'Error while saving data',
                        detail: error2
                    }
                )
            }
        );
    }
}
