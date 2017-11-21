import {Injectable} from "@angular/core";
import {HttpClient} from "../_httpclient/httpclient";
import {Observable} from "rxjs";
import {Rubric} from "../_models/screening/rubric";

@Injectable()
export class RubricService {

    constructor(private client: HttpClient){}

    public getRubrics(criteriaId: String): Observable<Rubric[]>{
        return this.client.get('/api/rubrics?criteriaId=' + criteriaId );
    }

    deleteRubric(rubric: Rubric) {
        return this.client.delete('/api/rubrics/' + rubric.id);
    }

    saveRubrics(rubrics: Rubric[]) {
        return this.client.post('/api/rubrics', rubrics);
    }
}