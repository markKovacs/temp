import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";
import {Observable} from "rxjs/Observable";
import {ScreeningInfo} from "../_models/screeninginfo.model";
import {PostResponse} from "../_models/post-response.model";

@Injectable()
export class ScreeningService {

    constructor(
        private http: HttpClient
    ) { }

    findCandidates(): Observable<ScreeningInfo[]> {
        return this.http.get("/api/screening/candidates" + "?location=" + JSON.parse(localStorage.getItem('chosenLocation')).id);
    }

    saveGroupTimes(data: any): Observable<PostResponse>{
        return this.http.post("/api/screening/group", data);
    }

    savePersonalTimes(data: any): Observable<PostResponse>{
        return this.http.post("/api/screening/personal", data);
    }
}