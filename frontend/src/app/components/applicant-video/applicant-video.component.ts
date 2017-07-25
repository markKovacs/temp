
import {Component, Input, OnInit} from "@angular/core";
import {Results} from "../../_models/results.model";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-video.component.html',
    styleUrls: ['applicant-video.component.css'],
    selector: 'applicant-video'
})
export class ApplicantVideoComponent implements OnInit{

    @Input() testResult: Results;

    ngOnInit(): void {

    }
}