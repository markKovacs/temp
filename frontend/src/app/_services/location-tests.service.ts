
import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";
import {Observable} from "rxjs";
import {Survey} from "../_models/survey/survey.model";

@Injectable()
export class LocationTestService {

    constructor(private client: HttpClient){}

    public getTestByLocation(): Observable<Survey[]>{
        return this.client.get('/api/question/' + JSON.parse(localStorage.getItem("chosenLocation")).id );
    }
}