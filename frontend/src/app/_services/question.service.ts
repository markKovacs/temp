import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";
import {Observable} from "rxjs";
import {Survey} from "../_models/survey/survey.model";
import {PostResponse} from "../_models/post-response.model";

@Injectable()
export class QuestionService {

    constructor(private client: HttpClient){}

    public postSurvey(survey: Survey): Observable<PostResponse>{
        return this.client.post('/api/question/save', JSON.stringify(survey) );
    }
}

