import {Component, OnInit} from '@angular/core';
import {Message} from 'primeng/primeng';
import {AlertService} from '../../_services/alert.service';

@Component({
    moduleId: module.id,
    templateUrl: 'alert.component.html',
    styleUrls: ['alert.component.css'],
    selector: 'alert-component'
})
export class AlertComponent implements OnInit {

    messages: Message[] = [];

    constructor(private alertService: AlertService) {
    }

    ngOnInit() {
        this.alertService.getMessage()
            .subscribe((message: any) => {
            if (message != null) {
                let msgObject = {
                    severity: message.severity,
                    summary: message.summary,
                    detail: message.detail
                };
                this.messages.push(msgObject);
                setTimeout(() => {
                    this.messages.splice(this.messages.indexOf(msgObject), 1);
                }, 5000)
            }
        });
    }

}
