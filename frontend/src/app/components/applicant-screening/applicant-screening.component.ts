import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {User} from '../../_models/index';
import {HttpClient} from '../../_httpclient/httpclient';

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-screening.component.html',
    styleUrls: ['applicant-screening.component.css'],
    selector: 'applicant-screening'
})
export class ApplicantScreeningComponent {

    @Input() user: User;
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

    setFinalResult(bool){

        if(this.user.applications[0].finalResult !== undefined){
            return;
        }

        this.user.applications[0].finalResult = bool;

        const data = {
            id: this.user.id,
            accepted: bool
        };

        this.client.post('/api/setfinalresult', data).subscribe(
            (response: any) => this.messages.push(
                {
                    severity: 'success',
                    summary: 'Final result set',
                    detail: this.user.givenName + ' ' + this.user.familyName
                }
            ),
            (error) => this.messages.push(
                {
                    severity: 'error',
                    summary: 'Error',
                    detail: error
                }
            )
        )
    }

}
