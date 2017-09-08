import {Component, Input, OnInit} from "@angular/core";
import {Results} from "../../_models/results.model";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-test.component.html',
    styleUrls: ['applicant-test.component.css'],
    selector: 'applicant-test'
})
export class ApplicantTestComponent implements OnInit{

    @Input() testResult: Results;

    ngOnInit(): void {

    }

    getPanelClass(): string {
        if(this.testResult.passed === true){
            return "panel-success";
        } else if(this.testResult.passed === false) {
            return "panel-danger";
        }
        return "panel-default";
    }

    getBgClass(): string {
        if(this.testResult.passed === true){
            return "bg-success";
        } else if(this.testResult.passed === false) {
            return "bg-danger";
        }
        return "bg-default";
    }

    getTextClass(): string {
        if(this.testResult.passed === true){
            return "text-success";
        } else if(this.testResult.passed === false) {
            return "text-danger";
        }
        return "text-info";
    }

    inProgress(): boolean {
        return this.testResult.passed === null || this.testResult.passed === undefined;
    }
}