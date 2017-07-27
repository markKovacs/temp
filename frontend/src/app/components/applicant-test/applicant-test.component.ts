
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
        return this.testResult.passed ? "panel-success" : "panel-danger";
    }

    getBgClass(): string {
        return this.testResult.passed ? "bg-success" : "bg-danger";
    }

    getTextClass(): string {
        return this.testResult.passed ? "text-success" : "text-danger";
    }
}