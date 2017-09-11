import {Results} from './results.model';
import {ScreeningInfo} from './screeninginfo.model'
import {UserScreeningDisplayModel} from './user-screening-display.model';
export class User {

    givenName: string;
    familyName: string;
    dateOfBirth: number;
    id: number;

    phoneNumber: string;
    email: string;

    comment;

    // from application info
    timesApplied: number;
    location: string;
    processStartedAt: number;
    testResults: Results[]= [];

    // from screening info
    screeningGroupTime: Date;
    screeningPersonalTime: Date;
    screeningInfo: ScreeningInfo;
    finalResult: boolean;
    screeningSteps: UserScreeningDisplayModel[];

}
