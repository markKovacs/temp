
import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";

@Injectable()
export class TestResultService{

    constructor(private client:HttpClient){}

    public removeTestResult(id: String){
        return this.client.get('api/testResults/delete/'+ id);
    }
}