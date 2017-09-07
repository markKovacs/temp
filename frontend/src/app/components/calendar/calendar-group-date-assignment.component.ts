import {Component, Input} from '@angular/core';
import {ScreeningInfo} from "../../_models/screeninginfo.model";
import {SelectItem} from 'primeng/primeng';

@Component({
    moduleId: module.id,
    selector: 'calendar-group-date-assignment',
    templateUrl: 'calendar-group-date-assignment.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarGroupDateAssignmentComponent {

    @Input() user: ScreeningInfo;

    @Input() dates: SelectItem[];

    constructor() { }

}
