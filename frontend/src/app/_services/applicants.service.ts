import {Injectable} from '@angular/core';
import {HttpClient} from '../_httpclient/httpclient';
import {Observable} from 'rxjs/Observable';
import {Applicant} from '../_models/applicant.model';

@Injectable()
export class ApplicantService {

    constructor(private client: HttpClient) {}

    public getApplicants(locationId: string, all: boolean = false): Observable<Applicant[]> {
        return this.client.get('/api/applicants?location=' + locationId + '&all=' + all);
    }

}

