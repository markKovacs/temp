import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";

@Injectable()
export class StatDataService {

    constructor(private client: HttpClient){}

    public getStatistics(location: String){
        return this.client.get('/api/stats?location=' + location);
    }


}