import {Component, Input, OnInit} from '@angular/core';
import {Results} from '../../_models/results.model';
import {BrowserModule, DomSanitizer} from '@angular/platform-browser';
import {GlobalEventsManager} from '../../global.eventsmanager';
import {HttpClient} from '../../_httpclient/httpclient';
import {Router} from '@angular/router';

@Component({
    moduleId: module.id,
    templateUrl: 'applicant-video.component.html',
    styleUrls: ['applicant-video.component.css'],
    selector: 'applicant-video',
    providers: [BrowserModule]
})
export class ApplicantVideoComponent implements OnInit {

    @Input() testResult: Results;
    id: number;
    @Input() enabled: boolean;

    constructor(private sanitizer: DomSanitizer,
                private client: HttpClient,
                private router: Router,
                private eventsManager: GlobalEventsManager) {
                this.eventsManager.showNavBar(true);
    }

    ngOnInit(): void {    }

    updateComment(comment) {
        this.testResult.comment = comment;
    }

    saveComment(){
        const grade = {id: this.id, comment: this.testResult.comment, testResultId: this.testResult.id };
        return this.client.post('/api/grademotivation', grade ).subscribe(
            // .() =>{ this.survey = null;}
            error => console.log(error)
        );
    }

    gradeMotivation(accepted: boolean): void{
        this.postMotivationGrade(accepted)
            .subscribe(
                // .() =>{ this.survey = null;}
                error => console.log(error)
            );
        this.router.navigate(['/dashboard']);
    }


    postMotivationGrade(accepted: boolean) {
        const grade = {id: this.id, passed: accepted, comment: this.testResult.comment, testResultId: this.testResult.id };
        return this.client.post('/api/grademotivation', grade );
    }


    // getMotivationVideo() {
    //         const videoUrl = this.testResult.answer;
    //         let videoID = videoUrl.split('watch?v=')[1];
    //         if (!this.isValidVideoId(videoID)) {
    //             videoID = '';
    //         }
    //
    //         const embedCode = 'https://www.youtube.com/embed/' + videoID;
    //         const safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedCode);
    //         return safeUrl;
    // }
    //
    // isValidVideoId(id) {
    //     const pattern = new RegExp(/^[a-z0-9]+$/i);
    //     return pattern.test(id)
    // }
    //
    // isVideo(){
    //     const mystr = 'string wefpiwef wfjowe few sammwdw http://www.google.com';
    //     const re = 'https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)';
    //     console.log('parsed', JSON.stringify(mystr.match(re)));
    //
    //     return this.testResult.answer.startsWith('http');
    // }

    parseTestResultAnswer(): string {
        console.log(this.testResult.answer);
        const re = 'https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)';
        const url = this.testResult.answer.match(re);
        if (url) {
            const htmlUrl = '<a href="' + url[0] + '" target="_blank">' + url[0] + '</a>';
            return this.testResult.answer.replace(url[0], htmlUrl);
        }
        return this.testResult.answer;
    }
}
