
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

}