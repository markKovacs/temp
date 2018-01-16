import {Injectable} from '@angular/core';
import {HttpClient} from '../_httpclient/httpclient';
import {Observable} from 'rxjs/Observable';
import {Applicant} from '../_models/applicant.model';
import {User} from '../_models/user.model';
import {PersonalData} from "../_models/personal-data";

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

    public getHired(): Observable<Applicant[]> {
        return this.client.get('/api/applicants/hired');
    }

    public getPersonalData(): Observable<PersonalData[]> {
        return this.client.get('/api/applicants/personaldata');
    }

    public setFinished(data: any) {
        return this.client.post('/api/setfinalresult', data);
    }

    public terminate(id: number) {
        return this.client.get('/api/applicants/' + id + '/terminate');
    }

    public delete(id: number) {
        return this.client.get('/api/applicants/' + id + '/delete');
    }

    public restore(applicationId: string) {
        return this.client.get('/api/applicants/restore/' + applicationId)
    }

    public contractSigned(id: number, courseId: string){
        return this.client.get('/api/applicants/contractsigned/' + id + "?courseId=" + courseId)
    }

    public rejected(id: number){
        return this.client.get('/api/applicants/rejected/' + id)
    }
}

