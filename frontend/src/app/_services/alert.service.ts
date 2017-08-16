import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';


@Injectable()
export class AlertService {

    private subject = new Subject<any>();


    constructor() { }

    showAlert(severity: string, summary: string, detail: string) {
        this.subject.next(
            { severity: severity,
              summary: summary,
              detail: detail
            }
            );
    }


    getMessage(): Observable<any> {
        return this.subject.asObservable();
    }


}

