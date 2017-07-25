import {Component} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location} from "../../_models/index";
import {DomSanitizer} from '@angular/platform-browser';
import {User} from "../../_models/user.model";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant.component.html',
    styleUrls: ['applicant.component.css']
})
export class ApplicantComponent {

    public user: User;
    public usersLocation: Location;

    constructor(private sanitizer: DomSanitizer,
                private route: ActivatedRoute,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
        this.route.params.subscribe(
            (params) => {
                this.getUser(params.id).subscribe(
                    (user: User) => {
                        this.user = user;
                    },
                    (error) => console.log(error),
                    () => console.log("User set")
                )
            }
        )
    }

    getUser(id) {
        return this.client.get('/api/applicants/' + id)
    }
    // getMotivationVideo() {
    //
    //     let videoUrl;
    //     console.log("user");
    //     console.log(this.user);
    //     if(this.user.testResults.length > 0) {
    //     console.log("user results");
    //     console.log(this.user.testResults);
    //         for (let result of this.user.testResults) {
    //             if (result.isMotivation) {
    //                 videoUrl = result.answer;//find(testResult => testResult.name == "motivation").
    //                 console.log("videoUrl");
    //                 console.log(videoUrl);
    //             }
    //         }
    //     }
    //         let videoID = videoUrl.split("watch?v=")[1];
    //         if (!this.isValidVideoId(videoID)) {
    //             videoID = "";
    //         }
    //
    //         console.log("videoID");
    //         console.log(videoID);
    //         let embedCode = "https://www.youtube.com/embed/" + videoID;
    //         let safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedCode);
    //         return safeUrl;
    //     }
    //
    // isValidVideoId(id) {
    //     let pattern = new RegExp(/^[a-z0-9]+$/i);
    //     return pattern.test(id)
    // }

}