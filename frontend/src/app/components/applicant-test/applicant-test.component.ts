import {Component, Input, OnInit} from '@angular/core';
import {Results} from '../../_models/results.model';
import {TestResultService} from "../../_services/testResult.service";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-test.component.html',
    styleUrls: ['applicant-test.component.css'],
    selector: 'applicant-test'
})
export class ApplicantTestComponent implements OnInit {

    @Input() testResult: Results;
    @Input() index: number;
    @Input() size: number;

    x = '5';

    constructor(private testResultService: TestResultService){
    }

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

    deleteTestResult() {
        const confirmDialog = confirm('Are you sure to delete this test result?');
        if (confirmDialog){
            this.testResultService.removeTestResult(this.testResult.id)
                .subscribe( (success:boolean) => {
                    alert('Test Result Deleted');
                    window.location.reload();
                })
        }
    }
}
