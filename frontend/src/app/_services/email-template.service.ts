import {Injectable} from '@angular/core';
import {HttpClient} from '../_httpclient/httpclient';
import {Observable} from 'rxjs/Observable';
import {Template} from '../_models/template.model';

@Injectable()
export class EmailTemplateService {

    constructor(private client: HttpClient) {}

    public getTemplates(locationId: string): Observable<Template[]> {
        return this.client.get('/api/templates?location=' + locationId);
    }

}
