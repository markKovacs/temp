import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {DomSanitizer} from '@angular/platform-browser';
import {User} from "../../_models/user.model";
import {Application} from "../../_models/application";
import {isNullOrUndefined} from "util";
import {ApplicantService} from "../../_services/applicants.service";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: User;

    application: Application;

    constructor(private sanitizer: DomSanitizer,
                private route: ActivatedRoute,
                private applicantService: ApplicantService,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.route.params.subscribe(
            (params) => {
                this.getUser(params.id).subscribe(
                    (user: User) => {
                        this.user = user;
                        if(user.applications && user.applications.length > 0) {
                            this.application = user.applications[0];
                            if(this.application.screeningGroupTime) {
                                this.application.screeningGroupTime = new Date(user.applications[0].screeningGroupTime);
                            }
                            if(this.application.screeningPersonalTime) {
                                this.application.screeningPersonalTime = new Date(user.applications[0].screeningPersonalTime);
                            }
                        }
                    },
                    (error) => console.log(error)
                )
            }
        )
    }

    getUser(id) {
        return this.client.get('/api/applicants/' + id)
    }

    hasSuccessMotivation(): boolean {
        return this.application.testResults.filter(mot => mot.isMotivation && mot.passed).length === 1;
    }

    save(){
        this.client.post('/api/applicants/' + this.user.id + '/savedate',
            {
                    group: this.application.screeningGroupTime.getTime(),
                    personal: this.application.screeningPersonalTime.getTime()
                  }
            )
            .subscribe(
                (success:any) => console.log("success"),
                    (error:any) => console.log(error)
            )
    }

    calculateage(year): string {
        if (year) {
            return (new Date().getFullYear() - year).toString();
        }
        return 'unable to calculate age';
    }

    disabled(){
        if(this.application && this.application.active === true){
            return '';
        }
        return 'disabled';
    }

    set(appl: Application){
        this.application = appl;
    }

    trackByFn(index, item) {
        return item.processStartedAt;
    }

    getClass(appl: Application){
        if(this.application === appl){
            return "btn-success";
        }
        return "btn-info";
    }

    resetApplication(){
        const confirmDialog = confirm('Are you sure? The applicant will need to restart the whole process.');

        if(confirmDialog){
            this.applicantService.terminate(this.user.id).subscribe(
                (data: any) => {
                    alert('Application closed');
                    this.router.navigate(['/applicants']);
                }
            )
        }

    }

    deleteUser(){
        const confirmDialog = confirm('Are you sure? The user will be wiped our from the system.');

        if(confirmDialog){
            this.applicantService.delete(this.user.id).subscribe(
                (data: any) => {
                    alert('User removed');
                    window.location.reload();
                }
            )
        }

    }

    restoreApplication() {
        const confirmDialog = confirm('Are you sure? The applicant will be active again');
        if (confirmDialog){
            if (this.checkThereIsActiveApplication()){
                alert('Sorry but other application is active');
            }else {
                this.applicantService.restore(this.user.id, this.application.id)
                    .subscribe(
                        (success: boolean) => {
                            if (success){
                                alert('Application restored');
                                window.location.reload();
                            }
                        });
            }
        }
    }

    private checkThereIsActiveApplication(): boolean{
        let haveActiveApplication = false;
        this.user.applications.forEach(application =>{
            if(application.active){
                haveActiveApplication = true;
            }
        });
        return haveActiveApplication;
    }
}