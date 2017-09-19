import {Injectable} from '@angular/core';
import {HttpClient} from '../_httpclient/httpclient';
import {Observable} from 'rxjs/Observable';
import {ScreeningInfo} from '../_models/screeninginfo.model';
import {PostResponse} from '../_models/post-response.model';

@Injectable()
export class ScreeningService {

    constructor(private http: HttpClient) { }

    findCandidates(): Observable<ScreeningInfo[]> {
        return this.http.get('/api/screening/candidates' + '?location=' + JSON.parse(localStorage.getItem('chosenLocation')).id);
    }

    findAssignmentCandidates(): Observable<ScreeningInfo[]> {
        return this.http.get('/api/screening/assignmentcandidates' + '?location=' + JSON.parse(localStorage.getItem('chosenLocation')).id);
    }

    saveGroupTimes(candidates: ScreeningInfo[]): Observable<PostResponse> {
        const dataToSend = candidates.map(candidate => {
            return { id: candidate.id, time: candidate.groupTime }
        });
        return this.http.post('/api/screening/group', dataToSend);
    }

    savePersonalTimes(candidates: ScreeningInfo[]): Observable<PostResponse> {
        const dataToSend = candidates.map(candidate => {
            return { id: candidate.id, time: candidate.personalTime }
        });
        return this.http.post('/api/screening/personal', dataToSend);
    }

    sendScreeningInviteEmails(candidates: ScreeningInfo[]): Observable<PostResponse> {
        const dataToSend = candidates.map(candidate => {
            return candidate.id;
        });
        console.log(JSON.stringify(dataToSend));
        return this.http.post('/api/screening/sendmails', dataToSend);
    }

}
