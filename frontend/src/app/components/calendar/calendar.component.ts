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
    groupTimeSaveBtnDisabled = true;
    personalTimeSaveBtnDisabled = true;
    controlListVisible = true;

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
    }

    handleGroupTimeButtonAccess(): void {
        this.controlListVisible = false;
        this.groupTimeSaveBtnDisabled = false;
    }

    handlePersonalTimeButtonAccess(): void {
        this.personalTimeSaveBtnDisabled = false;
        this.controlListVisible = false;
    }

    private fetchCandidates(): void {
        this.screeningService.findAssignmentCandidates()
            .subscribe(
            (data: ScreeningInfo[]) => {
                data.forEach((screeningInfo) => {
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
        this.groupTimeSaveBtnDisabled = false;
        this.controlListVisible = false;
        candidate.groupTime = null;
    }

    restorePersonalTimeToNull(candidate: ScreeningInfo): void {
        this.personalTimeSaveBtnDisabled = false;
        this.controlListVisible = false;
        candidate.personalTime = null;
    }

    setGroupTimeToAll(date: Date): void {
        this.candidates.forEach(candidate => candidate.groupTime = date);
    }

    setPersonalTimeToAll(date: Date): void {
        this.candidates.forEach(candidate => candidate.personalTime = date);
    }

    getCandidatesWithValidSchedules(): ScreeningInfo[] {
        return this.candidates.filter(candidate => {
            return candidate.personalTime && candidate.groupTime;
        });
    }

    saveGroupTimes(): void {
        this.loading = true;
        this.groupTimeSaveBtnDisabled = true;
        this.screeningService.saveGroupTimes(this.candidates)
            .subscribe(
                (data: any) => {
                    this.loading = false;

                    if (this.personalTimeSaveBtnDisabled) {
                        this.controlListVisible = true;
                    }

                },
                error => console.log(error)
            );
    }

    savePersonalTimes(): void {

        this.loading = true;
        this.personalTimeSaveBtnDisabled = true;

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
                (data: any) => {
                    this.loading = false;

                    if (this.groupTimeSaveBtnDisabled) {
                        this.controlListVisible = true;
                    }
                },
                error => console.log(error)
            );
    }

    sendEmails(): void {

        const confirmDialog = confirm('Are you sure?');

        if (confirmDialog) {
            this.loading = true;

            const candidatesWithAssignedSchedules = this.candidates.filter(candidate => {
                return candidate.groupTime && candidate.personalTime;
            });

            this.screeningService.sendScreeningInviteEmails(candidatesWithAssignedSchedules)
                .subscribe(
                    (data: any) => {
                        this.loading = false;
                        alert('Invite emails has been sent.');
                    },
                    error => console.log(error)
                );
        }
    }
}
