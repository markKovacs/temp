import {Component, Input, OnInit} from '@angular/core';
import {Results} from '../../_models/results.model';

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-test.component.html',
    styleUrls: ['applicant-test.component.css'],
    selector: 'applicant-test'
})
export class ApplicantTestComponent implements OnInit {

    @Input() testResult: Results;
    x = '5';

    ngOnInit(): void {
    }

    getPanelClass(): string {
        if (this.testResult.passed === true) {
            return 'progress-bar-success';
        } else if (this.testResult.passed === false) {
            return 'progress-bar-danger';
        }
        return 'panel-default';
    }

    getRoundedValue(no: number){
        return Math.round(no);
    }
}
