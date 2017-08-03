import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class GlobalEventsManager {

    private _showNavBar: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
    public showNavBarEmitter: Observable<boolean> = this._showNavBar.asObservable();

    private _selectedLocation: BehaviorSubject<string> = new BehaviorSubject<string>(null);
    public selectedLocationEmitter: Observable<string> = this._selectedLocation.asObservable();

    constructor() {
    }

    showNavBar(ifShow: boolean) {
        this._showNavBar.next(ifShow);
    }

    setSelectedLocation(loc: string) {
        this._selectedLocation.next(loc);
    }


}
