import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {ScreeningInfo} from '../../_models/screeninginfo.model';
import {ScreeningService} from '../../_services/screening.service';
import {DatePipe} from '@angular/common';
import {Message, SelectItem} from 'primeng/primeng';
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
    selectDates: SelectItem[] = [];
    screeningDates: Date[] = [];
    date: Date;
    loading = false;

    constructor(
        private eventsManager: GlobalEventsManager,
        private screeningService: ScreeningService,
        private dateFormatter: DatePipe,
        private alertService: AlertService
    ) {
        // this.loading = true;
        this.eventsManager.showNavBar(true);
        this.fetchCandidates();
    }

    ngOnInit(): void {
        this.setCalendarLocalization();
        this.candidates.filter(candidate => !isNullOrUndefined(candidate.groupTime));
    }

    private setCalendarLocalization(): void {
        this.calendarLocalization = {
            firstDayOfWeek: 1,
            dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
            dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNamesShort: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
            monthNames: [
                'January',
                'February',
                'March',
                'April',
                'May',
                'June',
                'July',
                'August',
                'September',
                'October',
                'November',
                'December'
            ]
        };
    }

    private fetchCandidates(): void {
        this.screeningService.findAssignmentCandidates()
            .subscribe(
            (data: ScreeningInfo[]) => {
                this.candidates = data;
                console.log('this.candidates = ', this.candidates);
                this.setScreeningDates();
                // this.loadDates();
            },
            error => console.log(error)
        );
    }

    private setScreeningDates(): void {
        this.candidates.forEach(candidate => {
            if (candidate.groupTime != null && !this.screeningDates.includes(candidate.groupTime)) {
                this.screeningDates.push(candidate.groupTime);
            }
        });
        console.log('screeningDates = ', this.screeningDates);
    }

    private loadDates(): void {
        this.candidates.forEach(candidate => {
            if (candidate.groupTime != null && !this.screeningDates.includes(candidate.groupTime)) {
                this.screeningDates.push(candidate.groupTime);
                this.selectDates.push(
                    {
                        label: this.dateFormatter.transform(candidate.groupTime, 'yyyy.MM.dd. HH:mm'),
                        value: candidate.groupTime
                    }
                );
            }
        });
        this.loading = false;
    }

    addDate() {
        if (this.date && !this.screeningDates.includes(this.date)) {
            this.screeningDates.push(this.date);
            this.selectDates.push(
                {
                    label: this.dateFormatter.transform(this.date, 'yyyy.MM.dd. HH:mm'),
                    value: this.date
                }
            );
        }
        this.date = null;
        this.screeningDates.sort();
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
