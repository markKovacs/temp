import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class HttpClient {

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private _http: Http) {
    }

    get(url: string, requestOptions: RequestOptions = new RequestOptions({headers: this.headers})): Observable<any> {
        this.enrich();
        return this.wrap(this._http.get(url, requestOptions)).map((response: Response) => response.json());
    }

    post(url: string, data: any, requestOptions: RequestOptions = new RequestOptions({headers: this.headers})) {
        this.enrich();
        return this.wrap(this._http.post(url, data, requestOptions).map((response: Response) => response.json()));
    }

    delete(url: string, requestOptions: RequestOptions = new RequestOptions({headers: this.headers})){
        this.enrich();
        return this.wrap(this._http.delete(url, requestOptions).map((response: Response) => response.json()))
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