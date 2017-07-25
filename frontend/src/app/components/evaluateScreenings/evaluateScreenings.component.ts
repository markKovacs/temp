import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, ScreeningStep, Criteria} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'evaluateScreenings.component.html',
    styleUrls: ['evaluateScreenings.component.css']
})
export class EvaluateScreeningsComponent {

    public location: Location;
    public screeningSteps: ScreeningStep[];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.getLocation();
    }

    editScreening(){
        this.router.navigate(['editscreening']);
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getApplicants(){
        this.client.get('/api/screening/list?location=' + this.location.id + '&signedback=true').subscribe(
            (data: any) => console.log(data),
            (error) => error,
            () => console.log("Applicants arrived")
        )
    }

    getSteps(){
        this.client.get('/api/screeningsteps?location=' + this.location.id).subscribe(
            (data: any) => console.log(data),
            (error) => error,
            () => console.log("Steps arrived")
        )
    }

    getApplicantsStep(){
        this.client.get('/api/evalscreening/101?step=d25b6c11-46d8-43f7-a390-91f25f19fdf5').subscribe(
            (data: any) => console.log(data),
            (error) => error,
            () => console.log("Applicant's step arrived")
        )
    }

    postUpdate(){
        let data = {};
        this.client.post('/api/evalscreening', data).subscribe(
            (data: any) => console.log(data),
            (error) => error,
            () => console.log("Applicant's step updated")
        )
    }

}
