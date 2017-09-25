import {Results} from './results.model';
import {ScreeningInfo} from './screeninginfo.model'
import {UserScreeningDisplayModel} from './user-screening-display.model';
import {Application} from "./application";
export class User {

    id: number;
    givenName: string;
    familyName: string;
    dateOfBirth: number;
    phoneNumber: string;
    email: string;

    timesApplied: number;
    location: string;

    applications: Application[] = [];

}
