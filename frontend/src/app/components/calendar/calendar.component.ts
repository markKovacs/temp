import {Component, OnInit} from '@angular/core';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {ScreeningInfo} from '../../_models/screeninginfo.model';
import {ScreeningService} from '../../_services/screening.service';
import {DatePipe} from '@angular/common';
import {isNullOrUndefined} from 'util';
import {AlertService} from '../../_services/alert.service';


@Component({
    moduleId: module.id,
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent implements OnInit {

    calendarLocalization: any; // used for primeNG calendar to start the weeks with Mondays by default its Sundays
    candidates: ScreeningInfo[] = [];
    date: Date;
    loading = false;

    constructor(
        private eventsManager: GlobalEventsManager,
        private screeningService: ScreeningService,
        private dateFormatter: DatePipe,
        private alertService: AlertService
    ) {
        this.loading = true;
        this.eventsManager.showNavBar(true);
    }

    ngOnInit(): void {
        this.setCalendarLocalization();
        this.fetchCandidates();
        // this.candidates.filter(candidate => !isNullOrUndefined(candidate.groupTime));
    }

    private fetchCandidates(): void {
        this.screeningService.findAssignmentCandidates()
            .subscribe(
            (data: ScreeningInfo[]) => {
                data.forEach((screeningInfo) => {
                   console.log(screeningInfo.groupTime);
                    if (!isNullOrUndefined(screeningInfo.groupTime)) {
                        screeningInfo.groupTime = new Date(screeningInfo.groupTime);
                    }
                    if (!isNullOrUndefined(screeningInfo.personalTime)) {
                        screeningInfo.personalTime = new Date(screeningInfo.personalTime);
                    }
                });
                this.candidates = data;
                this.loading = false;
            },
            error => console.log(error)
        );
    }

    private setCalendarLocalization(): void {
        this.calendarLocalization = {
            firstDayOfWeek: 1,
            dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
            dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNamesShort: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        };
    }

    restoreGroupTimeToNull(candidate: ScreeningInfo): void {
        candidate.groupTime = null;
    }

    restorePersonalTimeToNull(candidate: ScreeningInfo): void {
        candidate.personalTime = null;
    }

    saveGroupTimes(): void {
        this.screeningService.saveGroupTimes(this.candidates)
            .subscribe(
                (data: any) => { },
                error => console.log(error)
            );
    }

    savePersonalTimes(): void {
        let missingGroupTimes = false;

        for (const u of this.candidates) {
            if (!u.groupTime) {
                missingGroupTimes = true;
            }
        }

        if (missingGroupTimes) {
            this.alertService.showAlert('error', 'Missing group time', 'Assign group times to all applicants');
            return;
        }

        this.screeningService.savePersonalTimes(this.candidates)
            .subscribe(
                (data: any) => {},
                error => console.log(error)
            );
    }
}
