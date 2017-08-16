import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../../global.eventsmanager";
import {HttpClient} from "../../../_httpclient/httpclient";
import {ScreeningInfo} from "../../../_models/index";
import {Message} from 'primeng/primeng';

@Component({
    moduleId: module.id,
    templateUrl: 'evaluate-user.component.html',
    styleUrls: ['evaluate-user.component.css']
})
export class EvaluateUserComponent {

    public user: ScreeningInfo;
    public messages: Message[] = [];

    constructor(
        private client: HttpClient,
        private route: ActivatedRoute,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);

        this.route.params.subscribe(
            (params) => {
                this.getUser(params.id).subscribe(
                    (user: ScreeningInfo) => {
                        this.user = user;
                    },
                    (error) => console.log(error)
                )
            }
        );

    }

    getUser(id: number) {
        return this.client.get('/api/screening/' + id);
    }

}
