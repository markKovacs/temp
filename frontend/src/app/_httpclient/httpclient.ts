import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class HttpClient {

    private urlPrefix: string = 'http://localhost:8090';
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private _http: Http) {
    }

    get(url: string, requestOptions: RequestOptions = new RequestOptions({headers: this.headers})): Observable<any> {
        this.enrich();
        return this.wrap(this._http.get(this.urlPrefix + url, requestOptions)).map((response: Response) => response.json());
    }

    post(url: string, data: any, requestOptions: RequestOptions = new RequestOptions({headers: this.headers})) {
        this.enrich();
        return this.wrap(this._http.post(this.urlPrefix + url, data, requestOptions).map((response: Response) => response.json()));
    }

    private enrich(): void {
        const token = localStorage.getItem("adminAuthToken") == 'null' ? undefined : localStorage.getItem("adminAuthToken");
        this.headers.set('Authorization', 'Bearer ' + token);
    }

    private wrap(response: Observable<Response>) {
        return response.catch((res: Response) => {
            console.error('Error while performing HTTP request', res);
            return Observable.throw(res);
        });
    }

}
