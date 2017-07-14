import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";
import {DomSanitizer} from '@angular/platform-browser';

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: any;
    public usersLocation: Location;

    constructor(private sanitizer: DomSanitizer,
                private route: ActivatedRoute,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.route.params.subscribe(
            (params) => {
                console.log(params.id);
                let replaceIdForMock = 15;
                this.getUser(replaceIdForMock).subscribe(
                    (user: any) => {
                        this.user = user;
                        console.log(user)
                    },
                    (error) => console.log(error),
                    () => console.log("User set")
                )
            }
        )
    }

    getUser(id) {
        return this.client.get('api/applicants/' + id)
    }

    getMotivationVideo() {
        let videoUrl = this.user.testResults.find(testResult => testResult.name == "motivation").motivation;
        let videoID = videoUrl.split("watch?v=")[1];
        if (!this.isValidVideoId(videoID)) {
            videoID = "";
        }
        let embedCode = "https://www.youtube.com/embed/" + videoID;
        let safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedCode);
        return safeUrl;
    }

    isValidVideoId(id) {
        let pattern = new RegExp(/^[a-z0-9]+$/i);
        return pattern.test(id)
    }

}
