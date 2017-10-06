import {Injectable} from '@angular/core';
import {HttpClient} from '../_httpclient/httpclient';
import {Observable} from 'rxjs/Observable';
import {Applicant} from '../_models/applicant.model';
import {User} from '../_models/user.model';

@Injectable()
export class ApplicantService {

    constructor(private client: HttpClient) {}

    public getApplicants(locationId: string, all: boolean = false): Observable<Applicant[]> {
        return this.client.get('/api/applicants?location=' + locationId + '&all=' + all);
    }

    public getApplicantDetailsById(id: number): Observable<User> {
        return this.client.get('/api/applicants/' + id);
    }

    public getFinished(): Observable<Applicant[]> {
        return this.client.get('/api/applicants/finished');
    }

    public terminate(id: number) {
        return this.client.get('/api/applicants/' + id + '/terminate');
    }

    public delete(id: number) {
        return this.client.get('/api/applicants/' + id + '/delete');
    }
}

