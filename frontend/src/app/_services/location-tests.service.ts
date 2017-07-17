
import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";
import {Observable} from "rxjs";
import {Survey} from "../_models/survey.model";

@Injectable()
export class LocationTestService {

    constructor(private client: HttpClient){}

    public getTestByLocation(): Observable<Survey[]>{
        // location: Location = JSON.parse(localStorage.getItem("location"));
        // console.log(location);
        return this.client.get('/api/locations/test/BUD' );//+ location.id
    }
}