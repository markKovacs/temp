import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {User} from '../../_models/index';
import {HttpClient} from '../../_httpclient/httpclient';
import {Application} from "../../_models/application";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-screening.component.html',
    styleUrls: ['applicant-screening.component.css'],
    selector: 'applicant-screening'
})
export class ApplicantScreeningComponent {

    @Input() user: User;
    @Input() application: Application;

    messages: any [] = [];

    constructor(private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {

    }

    getClass(step: any){
        if (step.status === 'maybe'){
            return 'panel-warning';
        }
        if (step.status === 'no'){
            return 'panel-danger';
        }
        if (step.status === 'yes'){
            return 'panel-success';
        }
        return 'panel-default';
    }

}
