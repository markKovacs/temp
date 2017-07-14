import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class GlobalEventsManager {

    private _showNavBar: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
    public showNavBarEmitter: Observable<boolean> = this._showNavBar.asObservable();
    private _profileEditEnabled: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
    public profileEditEnabledEmitter: Observable<boolean> = this._profileEditEnabled.asObservable();

    constructor() {
    }

    showNavBar(ifShow: boolean) {
        this._showNavBar.next(ifShow);
    }

}
