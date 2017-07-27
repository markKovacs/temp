
import {Component, Input, OnInit} from "@angular/core";
import {Results} from "../../_models/results.model";
import {DomSanitizer} from "@angular/platform-browser";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-video.component.html',
    styleUrls: ['applicant-video.component.css'],
    selector: 'applicant-video'
})
export class ApplicantVideoComponent implements OnInit{

    @Input() testResult: Results;
    comment: string;
    adminId: string;

    constructor(private sanitizer: DomSanitizer,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
        this.eventsManager.showNavBar(true);
    }

    ngOnInit(): void {

    }

    saveComment(comment){
        this.comment = comment;
    }

    gradeMotivation(accepted: boolean): void{
        this.postMotivationGrade(accepted)
            .subscribe(
                // .() =>{ this.survey = null;}
                error => console.log(error)
            );
        this.router.navigate(['/dashboard']);
    }


    postMotivationGrade(accepeted:boolean) {
        let grade= {adminId: this.adminId, passed: accepeted, comment: this.comment, testResultId:this.testResult.id };
        return this.client.post('/api/grademotivation', grade );
    }


    getMotivationVideo() {
            let videoUrl = this.testResult.answer;
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

    isVideo(){
        return this.testResult.answer.startsWith("http");
    }
}