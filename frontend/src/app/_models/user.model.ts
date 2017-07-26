import {Results} from "./results.model";
import {ScreeningInfo} from "./screeninginfo.model"
export class User {

    givenName: string;
    familyName: string;
    dateOfBirth: number;
    adminId: number;

    // from application info
    timesApplied: number;
    location: string;
    processStartedAt: number;

    results: Results;

    // from screening info
    screeningDay: string;
    screeningGroupTime: string;
    screeningPersonalTime: string;
    scheduleSignedBack: boolean;

    screeningInfo: ScreeningInfo;

}
